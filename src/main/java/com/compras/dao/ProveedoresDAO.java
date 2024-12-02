package com.compras.dao;

import com.compras.config.DatabaseConfig;
import com.compras.model.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresDAO {
    private DatabaseConfig dbConfig = new DatabaseConfig();

    public List<Proveedor> obtenerTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT id, nombre, contacto FROM Proveedor";
        
        try (Connection conn = dbConfig.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                proveedores.add(new Proveedor(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("contacto")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        }
        return proveedores;
    }

    public boolean insertar(Proveedor proveedor) {
        String sql = "INSERT INTO Proveedor (nombre, contacto) VALUES (?, ?)";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getContacto());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Proveedor proveedor) {
        String sql = "UPDATE Proveedor SET nombre = ?, contacto = ? WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getContacto());
            pstmt.setInt(3, proveedor.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Proveedor WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }

    public Proveedor obtenerPorId(int id) {
        String sql = "SELECT id, nombre, contacto FROM Proveedor WHERE id = ?";
        
        try (Connection conn = dbConfig.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Proveedor(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("contacto")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedor por ID: " + e.getMessage());
        }
        return null;
    }
}
