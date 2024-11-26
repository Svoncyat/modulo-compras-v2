package com.compras.ui;

import javax.swing.*;

import com.compras.dao.UsuarioDAO;
import com.compras.ui.menu.MenuBar;

import java.awt.*;

public class MainFrame extends JFrame {
    private final UsuarioDAO usuarioDAO;

    public MainFrame(UsuarioDAO usuarioDAO) {
        super("Módulo Compras");
        this.usuarioDAO = usuarioDAO;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        MenuBar barramenu = new MenuBar(usuarioDAO, this);
        setJMenuBar(barramenu.inicializarBarraMenu());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JLabel("Bienvenido al Sistema de Módulo Compras", JLabel.CENTER));
        add(mainPanel);
    }
}
