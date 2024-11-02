package com.compras;

import javax.swing.*;
import java.awt.*;

import com.compras.gui.BarraMenu;

public class Menu extends JFrame {

    public Menu() {
        BarraMenu seguridad = new BarraMenu();
        setJMenuBar(seguridad.iniciar());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JLabel("Bienvenido al Sistema de Módulo Compras", JLabel.CENTER));
        add(mainPanel);
        
    }
    
    public static void main(String[] args) {
        Menu app = new Menu();
        app.setTitle("Módulo de Compras");
        app.setVisible(true);
        app.setSize(800, 600);
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}