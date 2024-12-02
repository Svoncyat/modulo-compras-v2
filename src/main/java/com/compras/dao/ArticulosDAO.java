package com.compras.dao;

import com.compras.config.DatabaseConfig;
import com.compras.model.Articulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticulosDAO {
    private DatabaseConfig dbConfig = new DatabaseConfig();

    public List<Articulo> obtenerTodos() {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT id, nombre, stock, descripcion FROM Articulo";
        
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                articulos.add(new Articulo(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("stock"),
                    rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener artículos: " + e.getMessage());
        }
        return articulos;
    }

    public boolean insertar(Articulo articulo) {
        String sql = "INSERT INTO Articulo (nombre, stock, descripcion) VALUES (?, ?, ?)";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, articulo.getNombre());
            pstmt.setInt(2, articulo.getStock());
            pstmt.setString(3, articulo.getDescripcion());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar artículo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Articulo articulo) {
        String sql = "UPDATE Articulo SET nombre = ?, stock = ?, descripcion = ? WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, articulo.getNombre());
            pstmt.setInt(2, articulo.getStock());
            pstmt.setString(3, articulo.getDescripcion());
            pstmt.setInt(4, articulo.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar artículo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Articulo WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar artículo: " + e.getMessage());
            return false;
        }
    }

    public Articulo obtenerPorId(int id) {
        String sql = "SELECT id, nombre, stock, descripcion FROM Articulo WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Articulo(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("stock"),
                    rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener artículo por ID: " + e.getMessage());
        }
        return null;
    }
}
