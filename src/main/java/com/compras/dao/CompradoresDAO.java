package com.compras.dao;

import com.compras.config.DatabaseConfig;
import com.compras.model.Comprador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompradoresDAO {
    private DatabaseConfig dbConfig = new DatabaseConfig();

    public List<Comprador> obtenerTodos() {
        List<Comprador> compradores = new ArrayList<>();
        String sql = "SELECT id, nombre, contacto FROM Comprador";
        
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                compradores.add(new Comprador(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("contacto")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener compradores: " + e.getMessage());
        }
        return compradores;
    }

    public boolean insertar(Comprador comprador) {
        String sql = "INSERT INTO Comprador (nombre, contacto) VALUES (?, ?)";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, comprador.getNombre());
            pstmt.setString(2, comprador.getContacto());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar comprador: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Comprador comprador) {
        String sql = "UPDATE Comprador SET nombre = ?, contacto = ? WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, comprador.getNombre());
            pstmt.setString(2, comprador.getContacto());
            pstmt.setInt(3, comprador.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar comprador: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Comprador WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar comprador: " + e.getMessage());
            return false;
        }
    }
}
