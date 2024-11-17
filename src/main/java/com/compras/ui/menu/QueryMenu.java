package com.compras.ui.menu;

import javax.swing.*;
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
                mostrarFormularioConsultas();
            }
        });

        // Agregar la opción al menú de consultas
        consultas.add(consultarArticulos);

        return consultas;
    }

    private void mostrarFormularioConsultas() {
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

    
}
