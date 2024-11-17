package com.compras.ui.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportMenu {
    public JMenu menuReportes() {
        JMenu reportes = new JMenu("Reportes");

        // Crear submenú de Generar Reportes
        JMenuItem generarReporte = new JMenuItem("Generar Reportes");
        generarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioReportes();
            }
        });

        // Agregar la opción al menú de reportes
        reportes.add(generarReporte);

        return reportes;
    }

    private void mostrarFormularioReportes() {
        JFrame frame = new JFrame("Reportes");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Tipo de Reporte:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Fecha de Inicio:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Fecha de Fin:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Generar Reporte"));
        botones.add(new JButton("Exportar Reporte"));
        botones.add(new JButton("Cerrar"));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}

