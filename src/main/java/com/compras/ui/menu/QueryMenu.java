package com.compras.ui.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueryMenu {
    public JMenu menuConsultas() {
        JMenu consultas = new JMenu("Consultas");

        // Crear submenú de Consultar Artículos
        JMenuItem consultarArticulos = new JMenuItem("Consultar Artículos");
        consultarArticulos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablaArticulos();
            }
        });

        // Agregar la opción al menú de consultas
        consultas.add(consultarArticulos);

        return consultas;
    }

    private void tablaArticulos() {
        JDialog dialog = new JDialog(new JFrame(), "Consultar Artículos", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columnas = { "ID", "Nombre Artículo", "Stock", "Estado", "Proveedor", "Comprador" };
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel superior con BorderLayout para separar filtros y botón
        JPanel panelSuperior = new JPanel(new BorderLayout());

        // Panel izquierdo para filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFiltrar = new JLabel("Filtrar por: ");
        JComboBox<String> cboFiltrar = new JComboBox<>(
                new String[] { "Nombre Artículo", "Stock", "Estado", "Proveedor", "Comprador" });
        panelFiltros.add(lblFiltrar);
        panelFiltros.add(cboFiltrar);
        panelSuperior.add(panelFiltros, BorderLayout.WEST);

        JLabel lblOrdenar = new JLabel("Ordenar por: ");
        JComboBox<String> cboOrdenar = new JComboBox<>(new String[] { "Mayor a menor", "Menor a Mayor" });
        panelFiltros.add(lblOrdenar);
        panelFiltros.add(cboOrdenar);
        panelSuperior.add(panelFiltros, BorderLayout.WEST);

        // Panel derecho para botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFiltrar = new JButton("Aplicar filtros");
        panelBoton.add(btnFiltrar);
        panelSuperior.add(panelBoton, BorderLayout.EAST);

        mainPanel.add(panelSuperior, BorderLayout.NORTH);
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}