package com.compras.gui;

import javax.swing.*;
import java.awt.*;

import com.compras.gui.BarraMenu;

public class PrincipalMenu extends JFrame {
    public PrincipalMenu() {
        super("Módulo Compras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        BarraMenu barramenu = new BarraMenu();
        setJMenuBar(barramenu.inicializarBarraMenu());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JLabel("Bienvenido al Sistema de Módulo Compras", JLabel.CENTER));
        add(mainPanel);
    }
}
