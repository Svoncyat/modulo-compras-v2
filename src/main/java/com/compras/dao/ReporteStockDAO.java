package com.compras.dao;

import com.compras.config.DatabaseConfig;
import java.sql.*;
import java.util.Vector;
import java.util.Date;

public class ReporteStockDAO {
    private DatabaseConfig dbConfig;

    public ReporteStockDAO() {
        this.dbConfig = new DatabaseConfig();
    }

    public Vector<Vector<Object>> obtenerReporteStock(Date fechaInicio, Date fechaFin) {
        Vector<Vector<Object>> datos = new Vector<>();
        String query = """
            SELECT a.id, a.nombre, a.stock
            FROM Articulo a
            WHERE EXISTS (
                SELECT 1 FROM Ingreso i
                WHERE i.articuloId = a.id
                AND i.fechaIngreso BETWEEN ? AND ?
            )
            ORDER BY a.id
        """;

        try (Connection conn = dbConfig.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
            stmt.setTimestamp(2, new Timestamp(fechaFin.getTime()));
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(rs.getInt("id"));
                fila.add(rs.getString("nombre"));
                fila.add(rs.getInt("stock"));
                datos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return datos;
    }
}
