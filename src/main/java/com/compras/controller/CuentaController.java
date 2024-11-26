package com.compras.controller;

import com.compras.dao.UsuarioDAO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CuentaController {
    private final UsuarioDAO usuarioDAO;
    private final JPasswordField txtContrasenaActual;
    private final JPasswordField txtNuevaContrasena;
    private final JDialog dialog;

    public CuentaController(UsuarioDAO usuarioDAO, JDialog dialog, JPasswordField txtContrasenaActual,
            JPasswordField txtNuevaContrasena) {
        this.usuarioDAO = usuarioDAO;
        this.dialog = dialog;
        this.txtContrasenaActual = txtContrasenaActual;
        this.txtNuevaContrasena = txtNuevaContrasena;
    }

    public ActionListener getActualizarListener() {
        return e -> {
            String contrasenaActual = new String(txtContrasenaActual.getPassword());
            String nuevaContrasena = new String(txtNuevaContrasena.getPassword());

            // Validaciones
            if (!validarEntradas(contrasenaActual, nuevaContrasena)) {
                return;
            }

            try {
                boolean actualizado = usuarioDAO.cambiarContrasena(
                    usuarioDAO.getUsuarioActual(),
                    contrasenaActual,
                    nuevaContrasena
                );

                if (actualizado) {
                    mostrarMensajeExito();
                    dialog.dispose();
                } else {
                    mostrarError("La contraseña actual es incorrecta");
                }
            } catch (SQLException ex) {
                mostrarError("Error al actualizar la contraseña: " + ex.getMessage());
            }
        };
    }

    private boolean validarEntradas(String contrasenaActual, String nuevaContrasena) {
        if (contrasenaActual.isEmpty() || nuevaContrasena.isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return false;
        }

        if (contrasenaActual.equals(nuevaContrasena)) {
            mostrarError("La nueva contraseña debe ser diferente a la actual");
            return false;
        }
        return true;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(dialog,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensajeExito() {
        JOptionPane.showMessageDialog(dialog,
            "Contraseña actualizada exitosamente",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);
    }
} 