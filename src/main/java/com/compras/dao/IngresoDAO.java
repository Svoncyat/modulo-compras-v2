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
        String sqlIngreso = "INSERT INTO Ingreso (ordenCompraId, articuloId, cantidadRecibida) VALUES (?, ?, ?)";
        String sqlUpdateOrden = "UPDATE OrdenCompra SET estado = ? WHERE id = ?";
        String sqlUpdateStock = "UPDATE Articulo SET stock = ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = dbConfig.conectar();
            conn.setAutoCommit(false);

            // Recorrer el Map de cantidades recibidas
            for (Map.Entry<Integer, Integer> entry : ingreso.getCantidadesRecibidas().entrySet()) {
                int articuloId = entry.getKey();
                // int cantidadRecibidaActual = obtenerCantidadRecibidaIndividual(ingreso.getOrdenCompra().getId(), articuloId);
                int cantidadRecibida = entry.getValue();

                if (cantidadRecibida > 0) {
                    // Registrar ingreso
                    try (PreparedStatement pstmtIngreso = conn.prepareStatement(sqlIngreso)) {
                        pstmtIngreso.setInt(1, ingreso.getOrdenCompra().getId());
                        pstmtIngreso.setInt(2, articuloId);
                        pstmtIngreso.setInt(3, cantidadRecibida);
                        pstmtIngreso.executeUpdate();
                    }

                    // Actualizar stock
                    try (PreparedStatement pstmtStock = conn.prepareStatement(sqlUpdateStock)) {
                        pstmtStock.setInt(1, cantidadRecibida);
                        pstmtStock.setInt(2, articuloId);
                        pstmtStock.executeUpdate();
                    }
                }
            }

            // Actualizar estado de la orden
            try (PreparedStatement pstmtOrden = conn.prepareStatement(sqlUpdateOrden)) {
                pstmtOrden.setString(1, ingreso.getOrdenCompra().getEstado());
                pstmtOrden.setInt(2, ingreso.getOrdenCompra().getId());
                pstmtOrden.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.err.println("Error al registrar ingreso: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public int obtenerCantidadRecibida(int ordenCompraId, int articuloId) {
        String sql = "SELECT COALESCE(SUM(cantidadRecibida), 0) as totalRecibido " +
                "FROM Ingreso " +
                "WHERE ordenCompraId = ? AND articuloId = ?";

        try (Connection conn = dbConfig.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordenCompraId);
            stmt.setInt(2, articuloId);
            ResultSet rs = stmt.executeQuery();

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
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordenCompraId);
            stmt.setInt(2, articuloId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("cantidadRecibida");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cantidad recibida individual: " + e.getMessage());
        }
        return 0;
    }
}
