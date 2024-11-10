package com.compras.gui;

import com.compras.dao.UsuarioDAO;
import com.compras.db.ConexionDB;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Principal;

public class AutenticacionMenu extends JFrame {
    private JTextField tfusuario;
    private JPasswordField tfcontrasena;
    private UsuarioDAO usuarioDAO;

    public AutenticacionMenu(ConexionDB conexionDB) {
        super("Autenticación");
        this.usuarioDAO = new UsuarioDAO(conexionDB);

        setLayout(new BorderLayout());
        JPanel panelbienvenida = new JPanel();
        JLabel lblbienvenida1 = new JLabel("Bienvenido al Módulo Compras,", SwingConstants.CENTER);
        JLabel lblbienvenida2 = new JLabel("Autentíquese para continuar: ", SwingConstants.CENTER);
        panelbienvenida.setLayout(new GridLayout(2,1));
        panelbienvenida.setBorder(new EmptyBorder(3,10,3,10));

        JPanel panelautenticacion = new JPanel();
        panelautenticacion.setLayout(new GridLayout(3, 2,5,5));
        panelautenticacion.setBorder(new EmptyBorder(10,10,10,10));

        JLabel lblusuario = new JLabel("Usuario: ");
        JLabel lblcontrasena = new JLabel("Contraseña: ");
        tfusuario = new JTextField();
        tfcontrasena = new JPasswordField();
        JButton btningresar = new JButton("Ingresar");

        panelautenticacion.add(lblusuario);
        panelautenticacion.add(tfusuario);
        panelautenticacion.add(lblcontrasena);
        panelautenticacion.add(tfcontrasena);
        panelautenticacion.add(new JLabel());
        panelautenticacion.add(btningresar);

        panelbienvenida.add(lblbienvenida1);
        panelbienvenida.add(lblbienvenida2);

        add(panelbienvenida, BorderLayout.NORTH);
        add(panelautenticacion, BorderLayout.CENTER);

        btningresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = tfusuario.getText();
                String contrasena = new String(tfcontrasena.getPassword());
                if(usuarioDAO.validarCredenciales(usuario, contrasena)) {
                    JOptionPane.showMessageDialog(null, "Autenticación exitosa");
                    new PrincipalMenu().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });
    }
}
