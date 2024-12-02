package com.compras.dao;

import com.compras.config.DatabaseConfig;
import java.sql.*;
import java.util.Vector;

public class QueryArticulosDAO {
    private DatabaseConfig dbConfig;

    public QueryArticulosDAO() {
        this.dbConfig = new DatabaseConfig();
    }

    public Vector<Vector<Object>> consultarArticulos(String filtro, String orden) {
        Vector<Vector<Object>> datos = new Vector<>();
        StringBuilder query = new StringBuilder("""
            SELECT 
                a.id,
                a.nombre,
                a.stock,
                p.nombre as proveedor,
                c.nombre as comprador
            FROM Articulo a
            LEFT JOIN OrdenCompra oc ON oc.id = (
                SELECT TOP 1 oc2.id 
                FROM OrdenCompra oc2
                JOIN DetalleOrdenCompra doc2 ON oc2.id = doc2.ordenCompraId
                WHERE doc2.articuloId = a.id
                ORDER BY oc2.fecha DESC
            )
            LEFT JOIN Proveedor p ON oc.proveedorId = p.id
            LEFT JOIN Comprador c ON oc.compradorId = c.id
            WHERE 1=1
        """);

        // Aplicar filtros
        if (filtro != null && !filtro.isEmpty()) {
            switch (filtro) {
                case "Nombre Artículo":
                    query.append(" ORDER BY a.nombre");
                    break;
                case "Stock":
                    query.append(" ORDER BY a.stock");
                    break;
                case "Proveedor":
                    query.append(" ORDER BY p.nombre");
                    break;
                case "Comprador":
                    query.append(" ORDER BY c.nombre");
                    break;
            }
            
            // Aplicar orden
            if (orden != null && orden.equals("Mayor a menor")) {
                query.append(" DESC");
            }
        }

        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {
            
            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(rs.getInt("id"));
                fila.add(rs.getString("nombre"));
                fila.add(rs.getInt("stock"));
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
