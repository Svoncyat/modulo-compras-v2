package com.temp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarraMenu extends JFrame {
    public BarraMenu() {
        setTitle("Gestión de Menú");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar barra de menú
        setJMenuBar(iniciar());
    }

    public JMenuBar iniciar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú principal
        JMenu seguridad = new JMenu("Seguridad");
        JMenu maestros = new JMenu("Maestros");

        // Submenú Seguridad
        JMenuItem usuarios = new JMenuItem("Usuarios");
        usuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioUsuarios();
            }
        });

        // Submenú Maestros
        JMenuItem articulos = new JMenuItem("Artículos");
        articulos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioArticulos();
            }
        });

        JMenuItem proveedores = new JMenuItem("Proveedores");
        proveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioProveedores();
            }
        });

        JMenuItem compradores = new JMenuItem("Compradores");
        compradores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioCompradores();
            }
        });

        JMenuItem transacciones = new JMenuItem("Transacciones");
        transacciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioTransacciones();
            }
        });

        // Añadir elementos de menú
        seguridad.add(usuarios);
        maestros.add(articulos);
        maestros.add(proveedores);
        maestros.add(compradores);
        maestros.add(transacciones);

        menuBar.add(seguridad);
        menuBar.add(maestros);

        return menuBar;
    }

    private void mostrarFormularioUsuarios() {
        JDialog dialog = new JDialog(this, "Gestión de Usuarios", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre de Usuario:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Teléfono de Usuario:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Usuario"));
        botones.add(new JButton("Eliminar Usuario"));
        botones.add(new JButton("Restablecer Contraseña"));

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostrarFormularioArticulos() {
        JDialog dialog = new JDialog(this, "Gestión de Artículos", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

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

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostrarFormularioProveedores() {
        JDialog dialog = new JDialog(this, "Gestión de Proveedores", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre del Proveedor:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Número del Proveedor:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Proveedor"));
        botones.add(new JButton("Eliminar Proveedor"));
        botones.add(new JButton("Actualizar Proveedor"));

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostrarFormularioCompradores() {
        JDialog dialog = new JDialog(this, "Gestión de Compradores", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre del Comprador:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Teléfono del Comprador:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Comprador"));
        botones.add(new JButton("Eliminar Comprador"));
        botones.add(new JButton("Actualizar Comprador"));

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostrarFormularioTransacciones() {
        JDialog dialog = new JDialog(this, "Gestión de Transacciones", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Nombre de la Transacción:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Transacción"));
        botones.add(new JButton("Eliminar Transacción"));
        botones.add(new JButton("Actualizar Transacción"));

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BarraMenu frame = new BarraMenu();
                frame.setVisible(true);
            }
        });
    }
}