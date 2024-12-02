package com.compras.ui.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.compras.controller.ReporteOrdenesController;
import com.compras.controller.ReporteStockController;
import com.toedter.calendar.JDateChooser;

public class ReportMenu {
    public JMenu menuReportes() {
        JMenu reportes = new JMenu("Reportes");

        JMenu generarReportes = new JMenu("Generar Reportes");
        JMenuItem reporteStock = new JMenuItem("Reporte de Stock");
        JMenuItem reporteOrdenes = new JMenuItem("Reporte de Ordenes");

        reporteStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reporteStock();
            }
        });

        reporteOrdenes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reporteOrdenes();
            }
        });

        reportes.add(generarReportes);
        generarReportes.add(reporteStock);
        generarReportes.add(reporteOrdenes);

        return reportes;
    }

    private void reporteStock() {
        JDialog dialog = new JDialog(new JFrame(), "Reportes", true);
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel central para la tabla
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        
        // Configuración de la tabla
        String[] columnas = { "ID", "Nombre Artículo", "Stock"};
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        // Panel superior para los filtros de fecha
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        // Panel izquierdo para las fechas
        JPanel panelFechas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        // Selector de fecha inicial
        JPanel panelFechaInicio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFechaInicio = new JLabel("Fecha de Inicio: ");
        JDateChooser dateChooserInicio = new JDateChooser();
        dateChooserInicio.setPreferredSize(new Dimension(130, 25));
        panelFechaInicio.add(lblFechaInicio);
        panelFechaInicio.add(dateChooserInicio);
        
        // Selector de fecha final
        JPanel panelFechaFin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFechaFin = new JLabel("Fecha de Fin: ");
        JDateChooser dateChooserFin = new JDateChooser();
        dateChooserFin.setPreferredSize(new Dimension(130, 25));
        panelFechaFin.add(lblFechaFin);
        panelFechaFin.add(dateChooserFin);
        
        
        // Botón de generar reporte y imprimir
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnImprimir = new JButton("Imprimir Reporte");
        panelBoton.add(btnImprimir);

        JButton btnReporte = new JButton("Generar Reporte");
        panelBoton.add(btnReporte);
        
        // Agregar componentes al panel de fechas
        panelFechas.add(panelFechaInicio);
        panelFechas.add(panelFechaFin);

        // Agregar paneles al panel superior
        panelSuperior.add(panelFechas, BorderLayout.WEST);
        panelSuperior.add(panelBoton, BorderLayout.EAST);

        // Agregar paneles al panel principal
        mainPanel.add(panelSuperior, BorderLayout.NORTH);
        mainPanel.add(panelCentral, BorderLayout.CENTER);
        
        ReporteStockController controller = new ReporteStockController(tabla, dateChooserInicio, dateChooserFin);
        btnImprimir.addActionListener(controller.getBtnImprimirAction());
        btnReporte.addActionListener(controller.getBtnReporteAction());

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void reporteOrdenes() {
        JDialog dialog = new JDialog(new JFrame(), "Reportes", true);
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel central para la tabla
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        
        // Configuración de la tabla
        String[] columnas = {"Nro de Orden", "Artículo", "Precio Unitario", "Estado", "Fecha de Ingreso", "Proveedor", "Comprador" };
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        // Panel superior para los filtros de fecha
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        // Panel izquierdo para las fechas
        JPanel panelFechas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        // Selector de fecha inicial
        JPanel panelFechaInicio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFechaInicio = new JLabel("Fecha de Inicio: ");
        JDateChooser dateChooserInicio = new JDateChooser();
        dateChooserInicio.setPreferredSize(new Dimension(130, 25));
        panelFechaInicio.add(lblFechaInicio);
        panelFechaInicio.add(dateChooserInicio);
        
        // Selector de fecha final
        JPanel panelFechaFin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFechaFin = new JLabel("Fecha de Fin: ");
        JDateChooser dateChooserFin = new JDateChooser();
        dateChooserFin.setPreferredSize(new Dimension(130, 25));
        panelFechaFin.add(lblFechaFin);
        panelFechaFin.add(dateChooserFin);
        
        // Botón de generar reporte y imprimir
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnImprimir = new JButton("Imprimir Reporte");
        panelBoton.add(btnImprimir);

        JButton btnReporte = new JButton("Generar Reporte");
        panelBoton.add(btnReporte);
        
        // Agregar componentes al panel de fechas
        panelFechas.add(panelFechaInicio);
        panelFechas.add(panelFechaFin);

        // Agregar paneles al panel superior
        panelSuperior.add(panelFechas, BorderLayout.WEST);
        panelSuperior.add(panelBoton, BorderLayout.EAST);

        // Agregar paneles al panel principal
        mainPanel.add(panelSuperior, BorderLayout.NORTH);
        mainPanel.add(panelCentral, BorderLayout.CENTER);
        
        ReporteOrdenesController controller = new ReporteOrdenesController(tabla, dateChooserInicio, dateChooserFin);
        btnImprimir.addActionListener(controller.getBtnImprimirAction());
        btnReporte.addActionListener(controller.getBtnReporteAction());

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
