package com.compras.ui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MasterMenu {

    public JMenu menuMaestros() {

        JMenu maestros = new JMenu("Maestros");

        // Artículos
        JMenuItem articulosItem = new JMenuItem("Artículos");
        articulosItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioArticulos();
            }
        });

        // Proveedores
        JMenuItem proveedoresItem = new JMenuItem("Proveedores");
        proveedoresItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioProveedores();
            }
        });

        // Compradores
        JMenuItem compradoresItem = new JMenuItem("Compradores");
        compradoresItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioCompradores();
            }
        });

        // Transacciones
        JMenuItem transaccionesItem = new JMenuItem("Transacciones");
        transaccionesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioTransacciones();
            }
        });

        // Orden de compra
        JMenuItem ordenCompraItem = new JMenuItem("Orden de compra");
        ordenCompraItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioOrdenCompra();
            }
        });

        // Agregar items a "Maestros"
        maestros.add(articulosItem);
        maestros.add(proveedoresItem);
        maestros.add(compradoresItem);
        maestros.add(transaccionesItem);
        maestros.add(ordenCompraItem);

        return maestros;
    }

    private void mostrarFormularioArticulos() {
        JFrame frame = new JFrame("Artículos");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Nombre del Artículo:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Stock del Artículo:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Descripción del Artículo:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Artículo"));
        botones.add(new JButton("Eliminar Artículo"));
        botones.add(new JButton("Actualizar Artículo"));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void mostrarFormularioProveedores() {
        JFrame frame = new JFrame("Gestión de Proveedores");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre del Proveedor:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Número del Proveedor:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Proveedor"));
        botones.add(new JButton("Eliminar Proveedor"));
        botones.add(new JButton("Actualizar Proveedor"));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void mostrarFormularioCompradores() {
        JFrame frame = new JFrame("Gestión de Compradores");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre del Comprador:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Teléfono del Comprador:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Comprador"));
        botones.add(new JButton("Eliminar Comprador"));
        botones.add(new JButton("Actualizar Comprador"));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void mostrarFormularioTransacciones() {
        JFrame frame = new JFrame("Gestión de Transacciones");
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Nombre de la Transacción:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Transacción"));
        botones.add(new JButton("Eliminar Transacción"));
        botones.add(new JButton("Actualizar Transacción"));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void mostrarFormularioOrdenCompra() {
        JFrame frame = new JFrame("Gestión de Orden de Compra");
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Número de Orden:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Fecha de Orden:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Proveedor:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Estado de Orden:"));
        panel.add(new JComboBox<>(new String[]{"Emitido", "Recibido", "Parcial", "Cancelado"}));

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Orden"));
        botones.add(new JButton("Eliminar Orden"));
        botones.add(new JButton("Actualizar Orden"));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
