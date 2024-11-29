package com.compras.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.compras.config.DatabaseConfig;

public class UsuarioDAO {
    private final Connection conexion;
    private boolean esAdmin;
    private String usuarioActual;

    public UsuarioDAO(DatabaseConfig conexionDB) {
        this.conexion = conexionDB.conectar();
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        String consulta = "SELECT esAdmin FROM Usuarios WHERE usuario = ? AND contrasena = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.esAdmin = rs.getBoolean("esAdmin");
                this.setUsuarioActual(usuario);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error en la validación de credenciales: " + e.getMessage());
        }
        return false;
    }

    public boolean esAdmin() {
        return this.esAdmin;
    }

    public Object[][] obtenerTodosLosUsuarios() {
        String consulta = "SELECT id, usuario, contrasena, esAdmin FROM Usuarios";
        List<Object[]> listaUsuarios = new ArrayList<>();
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {
            
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("usuario");
                fila[2] = "********"; // Por seguridad no mostramos la contraseña real
                fila[3] = rs.getBoolean("esAdmin");
                listaUsuarios.add(fila);
            }
            
            // Convertir la lista a array bidimensional
            Object[][] datos = new Object[listaUsuarios.size()][4];
            for (int i = 0; i < listaUsuarios.size(); i++) {
                datos[i] = listaUsuarios.get(i);
            }
            
            return datos;
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
            return new Object[0][4];
        }
    }

    public void agregarUsuario(String usuario, String contrasena, boolean esAdmin) throws SQLException {
        String consulta = "INSERT INTO Usuarios (usuario, contrasena, esAdmin) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ps.setBoolean(3, esAdmin);
            ps.executeUpdate();
        }
    }

    public void modificarUsuario(int id, String usuario, String contrasena, boolean esAdmin) throws SQLException {
        String consulta;
        if (contrasena.isEmpty()) {
            consulta = "UPDATE Usuarios SET usuario = ?, esAdmin = ? WHERE id = ?";
        } else {
            consulta = "UPDATE Usuarios SET usuario = ?, contrasena = ?, esAdmin = ? WHERE id = ?";
        }
        
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            if (contrasena.isEmpty()) {
                ps.setString(1, usuario);
                ps.setBoolean(2, esAdmin);
                ps.setInt(3, id);
            } else {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);
                ps.setBoolean(3, esAdmin);
                ps.setInt(4, id);
            }
            ps.executeUpdate();
        }
    }

    public void eliminarUsuario(int id) throws SQLException {
        String consulta = "DELETE FROM Usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public boolean cambiarContrasena(String usuario, String contrasenaActual, String nuevaContrasena) throws SQLException {
        if (!validarCredenciales(usuario, contrasenaActual)) {
            return false;
        }

        String consulta = "UPDATE Usuarios SET contrasena = ? WHERE usuario = ? AND contrasena = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, nuevaContrasena);
            ps.setString(2, usuario);
            ps.setString(3, contrasenaActual);
            return ps.executeUpdate() > 0;
        }
    }

    public void setUsuarioActual(String usuario) {
        this.usuarioActual = usuario;
    }

    public String getUsuarioActual() {
        return this.usuarioActual;
    }
}
