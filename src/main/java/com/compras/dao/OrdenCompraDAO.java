package com.compras.dao;

import com.compras.config.DatabaseConfig;
import com.compras.model.*;
import java.sql.*;
import java.util.*;

public class OrdenCompraDAO {
    private DatabaseConfig dbConfig = new DatabaseConfig();

    public String generarNumeroOrden() {
        String sql = "SELECT MAX(CAST(SUBSTRING(numero, 4, LEN(numero)) AS INT)) FROM OrdenCompra";
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int ultimoNumero = rs.getInt(1);
                return String.format("OC-%06d", ultimoNumero + 1);
            }
            return "OC-000001";
        } catch (SQLException e) {
            System.err.println("Error al generar número de orden: " + e.getMessage());
            return null;
        }
    }

    public boolean insertar(OrdenCompra orden) {
        String sqlOrden = "INSERT INTO OrdenCompra (numero, fecha, estado, proveedorId, compradorId, importeTotal) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO DetalleOrdenCompra (ordenCompraId, articuloId, cantidad, precioUnitario, subtotal) " +
                           "VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = dbConfig.conectar();
            conn.setAutoCommit(false);
            
            // Insertar orden
            try (PreparedStatement pstmtOrden = conn.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS)) {
                pstmtOrden.setString(1, orden.getNumero());
                pstmtOrden.setTimestamp(2, new Timestamp(orden.getFecha().getTime()));
                pstmtOrden.setString(3, orden.getEstado());
                pstmtOrden.setInt(4, orden.getProveedor().getId());
                pstmtOrden.setInt(5, orden.getComprador().getId());
                pstmtOrden.setDouble(6, orden.getPrecioUnitario());
                
                pstmtOrden.executeUpdate();
                
                // Obtener ID generado
                try (ResultSet generatedKeys = pstmtOrden.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int ordenId = generatedKeys.getInt(1);
                        
                        // Insertar detalles
                        try (PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle)) {
                            for (DetalleOrdenCompra detalle : orden.getDetalleOrdenCompra()) {
                                pstmtDetalle.setInt(1, ordenId);
                                pstmtDetalle.setInt(2, detalle.getArticulo().getId());
                                pstmtDetalle.setInt(3, detalle.getCantidad());
                                pstmtDetalle.setDouble(4, detalle.getPrecioUnitario());
                                pstmtDetalle.setDouble(5, detalle.getSubtotal());
                                pstmtDetalle.addBatch();
                            }
                            pstmtDetalle.executeBatch();
                        }
                    }
                }
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.err.println("Error al insertar orden de compra: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    public List<OrdenCompra> obtenerTodos() {
        List<OrdenCompra> ordenes = new ArrayList<>();
        String sql = "SELECT o.*, p.nombre as proveedor_nombre, p.contacto as proveedor_contacto, " +
                    "c.nombre as comprador_nombre, c.contacto as comprador_contacto " +
                    "FROM OrdenCompra o " +
                    "JOIN Proveedor p ON o.proveedorId = p.id " +
                    "JOIN Comprador c ON o.compradorId = c.id";
        
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getInt("proveedorId"),
                    rs.getString("proveedor_nombre"),
                    rs.getString("proveedor_contacto")
                );
                
                Comprador comprador = new Comprador(
                    rs.getInt("compradorId"),
                    rs.getString("comprador_nombre"),
                    rs.getString("comprador_contacto")
                );
                
                OrdenCompra orden = new OrdenCompra(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDate("fecha"),
                    proveedor,
                    comprador,
                    rs.getString("estado"),
                    rs.getDouble("importeTotal")
                );
                
                cargarDetalles(orden);
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener órdenes de compra: " + e.getMessage());
        }
        return ordenes;
    }

    private void cargarDetalles(OrdenCompra orden) {
        String sql = "SELECT d.*, a.nombre as articulo_nombre, a.stock, a.descripcion " +
                    "FROM DetalleOrdenCompra d " +
                    "JOIN Articulo a ON d.articuloId = a.id " +
                    "WHERE d.ordenCompraId = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, orden.getId());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Articulo articulo = new Articulo(
                    rs.getInt("articuloId"),
                    rs.getString("articulo_nombre"),
                    rs.getInt("stock"),
                    rs.getString("descripcion")
                );
                
                DetalleOrdenCompra detalle = new DetalleOrdenCompra(
                    articulo,
                    rs.getInt("cantidad"),
                    rs.getDouble("precioUnitario"),
                    rs.getDouble("subtotal")
                );
                
                orden.getDetalleOrdenCompra().add(detalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar detalles de orden: " + e.getMessage());
        }
    }

    public OrdenCompra obtenerPorId(int id) {
        String sql = "SELECT o.*, p.nombre AS proveedor_nombre, p.contacto AS proveedor_contacto, " +
                     "c.nombre AS comprador_nombre, c.contacto AS comprador_contacto " +
                     "FROM OrdenCompra o " +
                     "JOIN Proveedor p ON o.proveedorId = p.id " +
                     "JOIN Comprador c ON o.compradorId = c.id " +
                     "WHERE o.id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getInt("proveedorId"),
                    rs.getString("proveedor_nombre"),
                    rs.getString("proveedor_contacto")
                );
                
                Comprador comprador = new Comprador(
                    rs.getInt("compradorId"),
                    rs.getString("comprador_nombre"),
                    rs.getString("comprador_contacto")
                );
                
                OrdenCompra orden = new OrdenCompra(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getDate("fecha"),
                    proveedor,
                    comprador,
                    rs.getString("estado"),
                    rs.getDouble("importeTotal")
                );
                
                cargarDetalles(orden);
                return orden;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la orden de compra por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminar(String numeroOrden) {
        String sqlEliminarIngreso = "DELETE FROM Ingreso WHERE ordenCompraId IN (SELECT id FROM OrdenCompra WHERE numero = ?)";
        String sqlEliminarDevolucion = "DELETE FROM Devolucion WHERE ordenCompraId IN (SELECT id FROM OrdenCompra WHERE numero = ?)";
        String sqlEliminarDetalles = "DELETE FROM DetalleOrdenCompra WHERE ordenCompraId IN (SELECT id FROM OrdenCompra WHERE numero = ?)";
        String sqlEliminarOrden = "DELETE FROM OrdenCompra WHERE numero = ?";
        
        Connection conn = null;
        try {
            conn = dbConfig.conectar();
            conn.setAutoCommit(false);
            
            // Primero eliminar registros de Ingreso
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEliminarIngreso)) {
                pstmt.setString(1, numeroOrden);
                pstmt.executeUpdate();
            }

            // Luego eliminar registros de Devolucion
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEliminarDevolucion)) {
                pstmt.setString(1, numeroOrden);
                pstmt.executeUpdate();
            }
            
            // Después eliminar los detalles
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEliminarDetalles)) {
                pstmt.setString(1, numeroOrden);
                pstmt.executeUpdate();
            }
            
            // Finalmente eliminar la orden
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEliminarOrden)) {
                pstmt.setString(1, numeroOrden);
                pstmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.err.println("Error al eliminar orden de compra: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public OrdenCompra obtenerPorNumero(String numero) {
        String sql = "SELECT o.*, p.nombre AS proveedor_nombre, p.contacto AS proveedor_contacto, " +
                     "c.nombre AS comprador_nombre, c.contacto AS comprador_contacto " +
                     "FROM OrdenCompra o " +
                     "JOIN Proveedor p ON o.proveedorId = p.id " +
                     "JOIN Comprador c ON o.compradorId = c.id " +
                     "WHERE o.numero = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numero);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                OrdenCompra orden = construirOrdenDesdeResultSet(rs);
                cargarDetalles(orden);
                return orden;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener orden por número: " + e.getMessage());
        }
        return null;
    }

    private OrdenCompra construirOrdenDesdeResultSet(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor(
            rs.getInt("proveedorId"),
            rs.getString("proveedor_nombre"),
            rs.getString("proveedor_contacto")
        );
        
        Comprador comprador = new Comprador(
            rs.getInt("compradorId"),
            rs.getString("comprador_nombre"),
            rs.getString("comprador_contacto")
        );
        
        return new OrdenCompra(
            rs.getInt("id"),
            rs.getString("numero"),
            rs.getDate("fecha"),
            proveedor,
            comprador,
            rs.getString("estado"),
            rs.getDouble("importeTotal")
        );
    }

    public boolean eliminarOrden(int ordenId) {
        String sqlEliminarDetalles = "DELETE FROM DetalleOrdenCompra WHERE ordenCompraId = ?";
        String sqlEliminarOrden = "DELETE FROM OrdenCompra WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmtDetalles = conn.prepareStatement(sqlEliminarDetalles)) {
                pstmtDetalles.setInt(1, ordenId);
                pstmtDetalles.executeUpdate();
            }
            
            try (PreparedStatement pstmtOrden = conn.prepareStatement(sqlEliminarOrden)) {
                pstmtOrden.setInt(1, ordenId);
                pstmtOrden.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la orden de compra: " + e.getMessage());
            return false;
        }
    }
}
