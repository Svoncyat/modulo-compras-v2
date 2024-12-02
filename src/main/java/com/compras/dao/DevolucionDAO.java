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
        String sql = "SELECT DISTINCT o.* FROM OrdenCompra o " +
                     "WHERE o.estado IN ('Recibido', 'Backorder')";
        
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                OrdenCompra orden = ordenCompraDAO.obtenerPorId(rs.getInt("id"));
                if (orden != null) {
                    boolean tieneDetallesDisponibles = false;
                    for (DetalleOrdenCompra detalle : orden.getDetalleOrdenCompra()) {
                        int cantidadDevuelta = obtenerCantidadDevuelta(orden.getId(), detalle.getArticulo().getId());
                        if (detalle.getCantidad() > cantidadDevuelta) {
                            tieneDetallesDisponibles = true;
                            break;
                        }
                    }
                    if (tieneDetallesDisponibles) {
                        ordenes.add(orden);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener órdenes recibidas: " + e.getMessage());
        }
        return ordenes;
    }

    public int obtenerCantidadDevuelta(int ordenId, int articuloId) {
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

    public int obtenerCantidadRecibida(int ordenCompraId, int articuloId) {
        String sql = "SELECT COALESCE(SUM(cantidadRecibida), 0) as totalRecibido " +
                     "FROM Ingreso i " +
                     "JOIN DetalleOrdenCompra doc ON i.ordenCompraId = doc.ordenCompraId " +
                     "WHERE i.ordenCompraId = ? AND doc.articuloId = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ordenCompraId);
            pstmt.setInt(2, articuloId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("totalRecibido");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cantidad recibida: " + e.getMessage());
        }
        return 0;
    }

    public int obtenerCantidadRecibidaIndividual(int ordenCompraId, int articuloId) {
        String sql = "SELECT cantidadRecibida " +
                     "FROM Ingreso " +
                     "WHERE ordenCompraId = ? AND articuloId = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ordenCompraId);
            pstmt.setInt(2, articuloId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("cantidadRecibida");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cantidad recibida individual: " + e.getMessage());
        }
        return 0;
    }
}
