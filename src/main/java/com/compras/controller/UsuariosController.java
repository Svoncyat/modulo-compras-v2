package com.compras.controller;

import com.compras.dao.UsuarioDAO;
import javax.swing.*;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.SQLException;

public class UsuariosController {
    private final UsuarioDAO usuarioDAO;
    private final JTable tabla;
    private final JTextField txtUsuario;
    private final JPasswordField txtContrasena;
    private final JCheckBox chkAdministrador;

    public UsuariosController(UsuarioDAO usuarioDAO, JTable tabla,
            JTextField txtUsuario, JPasswordField txtContrasena,
            JCheckBox chkAdministrador) {
        this.usuarioDAO = usuarioDAO;
        this.tabla = tabla;
        this.txtUsuario = txtUsuario;
        this.txtContrasena = txtContrasena;
        this.chkAdministrador = chkAdministrador;
    }

    public ActionListener getAgregarListener() {
        return e -> {
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());
            boolean esAdmin = chkAdministrador.isSelected();

            // Validaciones
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Los campos usuario y contraseña son obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                usuarioDAO.agregarUsuario(usuario, contrasena, esAdmin);
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(null, 
                    "Usuario agregado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                if (ex.getMessage().contains("duplicate")) {
                    JOptionPane.showMessageDialog(null, 
                        "El usuario ya existe en la base de datos", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Error al agregar usuario: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    public ActionListener getModificarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor, seleccione un usuario para modificar", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea modificar este usuario?",
                "Confirmar modificación",
                JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    int id = (int) tabla.getValueAt(filaSeleccionada, 0);
                    String usuario = txtUsuario.getText();
                    String contrasena = new String(txtContrasena.getPassword());
                    boolean esAdmin = chkAdministrador.isSelected();

                    if (usuario.isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "El campo usuario no puede estar vacío", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    usuarioDAO.modificarUsuario(id, usuario, contrasena, esAdmin);
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(null, 
                        "Usuario modificado exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    if (ex.getMessage().contains("duplicate")) {
                        JOptionPane.showMessageDialog(null, 
                            "El nombre de usuario ya existe", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Error al modificar usuario: " + ex.getMessage(), 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
    }

    public ActionListener getEliminarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor, seleccione un usuario para eliminar", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar este usuario?\nEsta acción no se puede deshacer",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    int id = (int) tabla.getValueAt(filaSeleccionada, 0);
                    usuarioDAO.eliminarUsuario(id);
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(null, 
                        "Usuario eliminado exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Error al eliminar usuario: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    public ListSelectionListener getTablaSelectionListener() {
        return e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txtUsuario.setText((String) tabla.getValueAt(filaSeleccionada, 1));
                    txtContrasena.setText(""); // Por seguridad, no mostrar la contraseña
                    chkAdministrador.setSelected((Boolean) tabla.getValueAt(filaSeleccionada, 3));
                }
            }
        };
    }

    public void configurarTabla() {
        tabla.setDefaultEditor(Object.class, null);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer leftPaddingRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
                return this;
            }
        };
        leftPaddingRenderer.setHorizontalAlignment(JLabel.LEFT);

        // Renderer para la columna Admin
        DefaultTableCellRenderer booleanRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setText(Boolean.TRUE.equals(value) ? "Sí" : "No");
                setHorizontalAlignment(JLabel.CENTER);
                return this;
            }
        };

        // Aplicar renderers
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(1).setCellRenderer(leftPaddingRenderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(leftPaddingRenderer);
        tabla.getColumnModel().getColumn(3).setCellRenderer(booleanRenderer);
        
        // Ajustar anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(60);
    }

    private void actualizarTabla() {
        Object[][] nuevosDatos = usuarioDAO.obtenerTodosLosUsuarios();
        tabla.setModel(new DefaultTableModel(nuevosDatos, new String[] { "ID", "Usuario", "Contraseña", "Admin" }));
        configurarTabla();
    }

    // Método auxiliar para limpiar campos
    private void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        chkAdministrador.setSelected(false);
    }
}
