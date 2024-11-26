package com.compras.ui.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.compras.controller.UsuariosController;
import com.compras.dao.UsuarioDAO;
import com.compras.controller.CuentaController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecurityMenu {
    private final UsuarioDAO usuarioDAO;
    private final JFrame parentFrame;

    public SecurityMenu(UsuarioDAO usuarioDAO, JFrame parentFrame) {
        this.usuarioDAO = usuarioDAO;
        this.parentFrame = parentFrame;
    }

    public JMenu menuSeguridad() {
        JMenu seguridad = new JMenu("Seguridad");

        JMenu usuarios = new JMenu("Usuarios");
        JMenuItem administrarCuenta = new JMenuItem("Restablecer contraseña");
        JMenuItem administrarUsuarios = new JMenuItem("Administrar usuarios");

        if (usuarioDAO.esAdmin()) {
            seguridad.add(usuarios);
            usuarios.add(administrarUsuarios);
        } else {
            seguridad.add(administrarCuenta);
        }

        administrarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarUsuarios();
            }
        });

        administrarCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarCuenta();
            }
        });

        return seguridad;
    }

    private void administrarUsuarios() {
        JDialog dialog = new JDialog(parentFrame, "Administrar Usuarios", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear tabla y modelo
        String[] columnas = { "ID", "Usuario", "Contraseña", "Admin" };
        Object[][] datos = usuarioDAO.obtenerTodosLosUsuarios();
        JTable tabla = new JTable(datos, columnas);

        // Agregar tabla a un ScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de edición y botones usando GridBagLayout
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de edición
        JTextField txtUsuario = new JTextField(15);
        JPasswordField txtContrasena = new JPasswordField(15);
        JCheckBox chkAdministrador = new JCheckBox("Es administrador:   ");
        chkAdministrador.setHorizontalTextPosition(SwingConstants.LEFT);

        // Agregar componentes de edición
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDerecho.add(new JLabel("Usuario: "), gbc);

        gbc.gridy = 1;
        panelDerecho.add(txtUsuario, gbc);

        gbc.gridy = 2;
        panelDerecho.add(new JLabel("Contraseña: "), gbc);

        gbc.gridy = 3;
        panelDerecho.add(txtContrasena, gbc);

        gbc.gridy = 4;
        panelDerecho.add(chkAdministrador, gbc);

        // Agregar espacio vertical
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        panelDerecho.add(Box.createVerticalStrut(20), gbc);

        // Botones
        JButton btnAgregar = new JButton("Agregar usuario");
        JButton btnModificar = new JButton("Modificar usuario");
        JButton btnEliminar = new JButton("Eliminar usuario");

        gbc.weighty = 0.0;
        gbc.gridy = 6;
        panelDerecho.add(btnAgregar, gbc);

        gbc.gridy = 7;
        panelDerecho.add(btnModificar, gbc);

        gbc.gridy = 8;
        panelDerecho.add(btnEliminar, gbc);

        mainPanel.add(panelDerecho, BorderLayout.EAST);
        dialog.add(mainPanel);

        // Crear el controlador
        UsuariosController controller = new UsuariosController(
                usuarioDAO, tabla, txtUsuario, txtContrasena, chkAdministrador);
        
        controller.configurarTabla();
        btnAgregar.addActionListener(controller.getAgregarListener());
        btnModificar.addActionListener(controller.getModificarListener());
        btnEliminar.addActionListener(controller.getEliminarListener());
        tabla.getSelectionModel().addListSelectionListener(
                controller.getTablaSelectionListener());

        dialog.setVisible(true);
    }

    private void administrarCuenta() {
        JDialog dialog = new JDialog(parentFrame, "Administrar cuenta", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        // Campos de texto
        JTextField txtUsuario = new JTextField(usuarioDAO.getUsuarioActual());
        txtUsuario.setEditable(false);
        JPasswordField txtContrasenaActual = new JPasswordField();
        JPasswordField txtNuevaContrasena = new JPasswordField();
        
        panel.add(new JLabel("Cambiar contraseña: "));
        panel.add(new JLabel());
        panel.add(new JLabel("Contraseña actual: "));
        panel.add(txtContrasenaActual);
        panel.add(new JLabel("Nueva Contraseña: "));
        panel.add(txtNuevaContrasena);
        panel.add(new JLabel());
        
        JButton btnActualizar = new JButton("Actualizar datos");
        
        // Crear el controlador
        CuentaController controller = new CuentaController(
            usuarioDAO,
            dialog,
            txtContrasenaActual,
            txtNuevaContrasena
        );
        
        btnActualizar.addActionListener(controller.getActualizarListener());
        panel.add(btnActualizar);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
