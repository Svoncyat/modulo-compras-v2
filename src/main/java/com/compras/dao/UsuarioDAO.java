package com.compras.dao;

import java.sql.*;

import com.compras.config.DatabaseConfig;

public class UsuarioDAO {
    private final Connection conexion;

    public UsuarioDAO(DatabaseConfig conexionDB) {
        this.conexion = conexionDB.conectar();
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        String consulta = "SELECT COUNT(*) FROM Usuarios WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error en la validación de credenciales: " + e.getMessage());
        }
        return false;
    }
}
