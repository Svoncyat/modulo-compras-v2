package com.compras.dao;

import com.compras.config.DatabaseConfig;
import com.compras.model.*;
import java.sql.*;
import java.util.*;

public class IngresoDAO {
    private DatabaseConfig dbConfig = new DatabaseConfig();
    private OrdenCompraDAO ordenCompraDAO = new OrdenCompraDAO();

    public List<OrdenCompra> obtenerOrdenesPendientes() {
        List<OrdenCompra> ordenes = new ArrayList<>();
        String sql = "SELECT DISTINCT o.* FROM OrdenCompra o " +
                     "WHERE o.estado IN ('Emitido', 'Backorder')";
        
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                OrdenCompra orden = ordenCompraDAO.obtenerPorId(rs.getInt("id"));
                if (orden != null) {
                    ordenes.add(orden);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener órdenes pendientes: " + e.getMessage());
        }
        return ordenes;
    }

    public boolean registrarIngreso(Ingreso ingreso) {
        String sqlIngreso = "INSERT INTO Ingreso (ordenCompraId, cantidadRecibida) VALUES (?, ?)";
        String sqlUpdateOrden = "UPDATE OrdenCompra SET estado = ? WHERE id = ?";
        String sqlUpdateStock = "UPDATE Articulo SET stock = stock + ? WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = dbConfig.conectar();
            conn.setAutoCommit(false);
            
            // Registrar ingreso
            try (PreparedStatement pstmtIngreso = conn.prepareStatement(sqlIngreso)) {
                pstmtIngreso.setInt(1, ingreso.getOrdenCompra().getId());
                pstmtIngreso.setInt(2, ingreso.getCantidadRecibida());
                pstmtIngreso.executeUpdate();
            }
            
            // Actualizar estado de orden usando el estado calculado en el controller
            try (PreparedStatement pstmtOrden = conn.prepareStatement(sqlUpdateOrden)) {
                pstmtOrden.setString(1, ingreso.getOrdenCompra().getEstado()); // Usar el estado calculado
                pstmtOrden.setInt(2, ingreso.getOrdenCompra().getId());
                pstmtOrden.executeUpdate();
            }
            
            // Actualizar stock de artículos
            try (PreparedStatement pstmtStock = conn.prepareStatement(sqlUpdateStock)) {
                for (DetalleOrdenCompra detalle : ingreso.getOrdenCompra().getDetalleOrdenCompra()) {
                    pstmtStock.setInt(1, ingreso.getCantidadRecibida());
                    pstmtStock.setInt(2, detalle.getArticulo().getId());
                    pstmtStock.addBatch();
                }
                pstmtStock.executeBatch();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.err.println("Error al registrar ingreso: " + e.getMessage());
            return false;
        }
    }

    public int obtenerCantidadRecibida(int ordenCompraId, int articuloId) {
        String sql = "SELECT SUM(cantidadRecibida) as totalRecibido FROM Ingreso WHERE ordenCompraId = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ordenCompraId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("totalRecibido");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cantidad recibida: " + e.getMessage());
        }
        return 0;
    }
}
