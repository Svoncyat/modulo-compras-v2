package com.compras.dao;

import com.compras.config.DatabaseConfig;
import java.sql.*;
import java.util.Vector;
import java.util.Date;

public class ReporteOrdenesDAO {
    private DatabaseConfig dbConfig;

    public ReporteOrdenesDAO() {
        this.dbConfig = new DatabaseConfig();
    }

    public Vector<Vector<Object>> obtenerReporteOrdenes(Date fechaInicio, Date fechaFin) {
        Vector<Vector<Object>> datos = new Vector<>();
        String query = """
            SELECT 
                oc.numero,
                a.nombre as articulo,
                doc.precioUnitario,
                oc.estado,
                oc.fecha,
                p.nombre as proveedor,
                c.nombre as comprador
            FROM OrdenCompra oc
            INNER JOIN DetalleOrdenCompra doc ON oc.id = doc.ordenCompraId
            INNER JOIN Articulo a ON doc.articuloId = a.id
            INNER JOIN Proveedor p ON oc.proveedorId = p.id
            INNER JOIN Comprador c ON oc.compradorId = c.id
            WHERE oc.fecha BETWEEN ? AND ?
            ORDER BY oc.fecha DESC
        """;

        try (Connection conn = dbConfig.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
            stmt.setTimestamp(2, new Timestamp(fechaFin.getTime()));
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(rs.getString("numero"));
                fila.add(rs.getString("articulo"));
                fila.add(rs.getDouble("precioUnitario"));
                fila.add(rs.getString("estado"));
                fila.add(rs.getTimestamp("fecha"));
                fila.add(rs.getString("proveedor"));
                fila.add(rs.getString("comprador"));
                datos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return datos;
    }
}
