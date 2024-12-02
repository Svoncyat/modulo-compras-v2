package com.compras.dao;

import com.compras.config.DatabaseConfig;
import com.compras.model.*;
import java.sql.*;
import java.util.*;

public class DevolucionDAO {
    private DatabaseConfig dbConfig = new DatabaseConfig();
    private OrdenCompraDAO ordenCompraDAO = new OrdenCompraDAO();
    private ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
    private ArticulosDAO articulosDAO = new ArticulosDAO();

    public List<OrdenCompra> obtenerOrdenesRecibidas() {
        List<OrdenCompra> ordenes = new ArrayList<>();
        String sql = "SELECT o.* FROM OrdenCompra o " +
                    "WHERE o.estado = 'Recibido' " +
                    "AND EXISTS (" +
                    "    SELECT 1 FROM DetalleOrdenCompra doc " +
                    "    LEFT JOIN (" +
                    "        SELECT ordenCompraId, SUM(cantidadDevuelta) as totalDevuelto " +
                    "        FROM Devolucion " +
                    "        GROUP BY ordenCompraId" +
                    "    ) d ON doc.ordenCompraId = d.ordenCompraId " +
                    "    WHERE doc.ordenCompraId = o.id " +
                    "    AND (d.totalDevuelto IS NULL OR doc.cantidad > d.totalDevuelto)" +
                    ")";
        
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                OrdenCompra orden = ordenCompraDAO.obtenerPorId(rs.getInt("id"));
                if (orden != null) {
                    // Filtrar los detalles que aún tienen cantidad disponible para devolver
                    List<DetalleOrdenCompra> detallesDisponibles = new ArrayList<>();
                    for (DetalleOrdenCompra detalle : orden.getDetalleOrdenCompra()) {
                        int cantidadDevuelta = obtenerCantidadDevuelta(orden.getId(), detalle.getArticulo().getId());
                        int cantidadDisponible = detalle.getCantidad() - cantidadDevuelta;
                        if (cantidadDisponible > 0) {
                            detalle.setCantidad(cantidadDisponible);
                            detallesDisponibles.add(detalle);
                        }
                    }
                    orden.setDetalleOrdenCompra(detallesDisponibles);
                    if (!detallesDisponibles.isEmpty()) {
                        ordenes.add(orden);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener órdenes recibidas: " + e.getMessage());
        }
        return ordenes;
    }

    private int obtenerCantidadDevuelta(int ordenId, int articuloId) {
        String sql = "SELECT COALESCE(SUM(cantidadDevuelta), 0) as totalDevuelto " +
                    "FROM Devolucion " +
                    "WHERE ordenCompraId = ? AND articuloId = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ordenId);
            pstmt.setInt(2, articuloId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("totalDevuelto");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cantidad devuelta: " + e.getMessage());
        }
        return 0;
    }

    public boolean registrarDevolucion(Devolucion devolucion) {
        String sqlDevolucion = "INSERT INTO Devolucion (ordenCompraId, proveedorId, cantidadDevuelta, articuloId) " +
                              "VALUES (?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE Articulo SET stock = stock - ? WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = dbConfig.conectar();
            conn.setAutoCommit(false);
            
            // Registrar devolución
            try (PreparedStatement pstmtDevolucion = conn.prepareStatement(sqlDevolucion)) {
                pstmtDevolucion.setInt(1, devolucion.getOrdenCompra().getId());
                pstmtDevolucion.setInt(2, devolucion.getProveedor().getId());
                pstmtDevolucion.setInt(3, devolucion.getCantidadDevuelta());
                pstmtDevolucion.setInt(4, devolucion.getArticulo().getId());
                pstmtDevolucion.executeUpdate();
            }
            
            // Actualizar stock
            try (PreparedStatement pstmtStock = conn.prepareStatement(sqlUpdateStock)) {
                pstmtStock.setInt(1, devolucion.getCantidadDevuelta());
                pstmtStock.setInt(2, devolucion.getArticulo().getId());
                pstmtStock.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.err.println("Error al registrar devolución: " + e.getMessage());
            return false;
        }
    }

    public List<Devolucion> obtenerPorProveedor(int proveedorId) {
        List<Devolucion> devoluciones = new ArrayList<>();
        String sql = "SELECT * FROM Devolucion WHERE proveedorId = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, proveedorId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrdenCompra orden = ordenCompraDAO.obtenerPorId(rs.getInt("ordenCompraId"));
                Proveedor proveedor = proveedoresDAO.obtenerPorId(rs.getInt("proveedorId"));
                Articulo articulo = articulosDAO.obtenerPorId(rs.getInt("articuloId"));

                devoluciones.add(new Devolucion(
                    rs.getInt("id"),
                    orden,
                    proveedor,
                    articulo,
                    rs.getInt("cantidadDevuelta")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener devoluciones: " + e.getMessage());
        }
        return devoluciones;
    }
}
