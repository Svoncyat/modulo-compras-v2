package com.compras.ui.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecurityMenu {

    public JMenu menuSeguridad() {
        JMenu seguridad = new JMenu("Seguridad");

        JMenu usuarios = new JMenu("Usuarios");
        JMenuItem agregarUsuario = new JMenuItem("Agregar usuario");
        JMenuItem eliminarUsuario = new JMenuItem("Eliminar Usuario");
        JMenuItem restablecerContrasena = new JMenuItem("Restablecer contraseña");

        usuarios.add(agregarUsuario);
        usuarios.add(eliminarUsuario);
        usuarios.add(restablecerContrasena);

        seguridad.add(usuarios);

        agregarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuarios();
            }
        });

        eliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });

        restablecerContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restablecerContrasena();
            }
        });

        return seguridad;
    }

    private void agregarUsuarios() {
        JFrame frame = new JFrame("Agregar Usuario");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(new JLabel("Nuevo usuario: "));
        panel.add(new JTextField());
        panel.add(new JLabel("Contraseña: "));
        panel.add(new JPasswordField());
        panel.add(new JLabel("Es administrador: "));
        panel.add(new JCheckBox());
        panel.add(new JLabel());
        panel.add(new JButton("Agregar"));

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void eliminarUsuario() {
        JFrame frame = new JFrame("Eliminar Usuario");
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(new JLabel("Nombre del usuario: "));
        panel.add(new JTextField());
        panel.add(new JLabel());
        panel.add(new JButton("Eliminar"));

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void restablecerContrasena() {
        JFrame frame = new JFrame("Restablecer contraseña");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(new JLabel("Usuario: "));
        panel.add(new JTextField());
        panel.add(new JLabel("Contraseña actual: "));
        panel.add(new JPasswordField());
        panel.add(new JLabel("Nueva Contraseña: "));
        panel.add(new JPasswordField());
        panel.add(new JLabel());
        panel.add(new JButton("Restablecer"));

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
