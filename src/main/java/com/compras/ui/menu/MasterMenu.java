package com.compras.ui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MasterMenu {

    public JMenu menuMaestros() {

        JMenu maestros = new JMenu("Maestros");

        // Artículos
        JMenu articulos = new JMenu("Artículos");
        JMenuItem agregararticulos = new JMenuItem("Agregar Artículo");
        articulos.add(agregararticulos);

        // Proveedores
        JMenu proveedores = new JMenu("Proveedores");
        JMenuItem agregarproveedor = new JMenuItem("Agregar Proveedor");
        proveedores.add(agregarproveedor);

        // Compradores
        JMenu compradores = new JMenu("Compradores");
        JMenuItem agregarcomprador = new JMenuItem("Agregar Comprador");
        compradores.add(agregarcomprador);

        // Transacciones
        JMenu transacciones = new JMenu("Transacciones");
        JMenuItem agregartransacciones = new JMenuItem("Agregar Transacción");
        transacciones.add(agregartransacciones);

        // Orden de compra
        JMenu ordendecompra = new JMenu("Orden de compra");
        JMenuItem agregarorden = new JMenuItem("Agregar Orden");
        ordendecompra.add(agregarorden);

        // Agregar submenús a "Maestros"
        maestros.add(articulos);
        maestros.add(proveedores);
        maestros.add(compradores);
        maestros.add(transacciones);
        maestros.add(ordendecompra);

        // Añadir ActionListeners
        agregararticulos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioArticulos();
            }
        });

        agregarproveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioProveedores();
            }
        });

        agregarcomprador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioCompradores();
            }
        });

        agregartransacciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioTransacciones();
            }
        });

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
}
