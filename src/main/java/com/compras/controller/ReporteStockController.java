package com.compras.controller;

import com.compras.dao.ReporteStockDAO;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ReporteStockController {
    private JTable tabla;
    private JDateChooser fechaInicio;
    private JDateChooser fechaFin;
    private ReporteStockDAO reporteDAO;

    public ReporteStockController(JTable tabla, JDateChooser fechaInicio, JDateChooser fechaFin) {
        this.tabla = tabla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.reporteDAO = new ReporteStockDAO();
    }

    public ActionListener getBtnReporteAction() {
        return e -> {
            if (fechaInicio.getDate() == null || fechaFin.getDate() == null) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione ambas fechas");
                return;
            }

            if (fechaInicio.getDate().after(fechaFin.getDate())) {
                JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha fin");
                return;
            }

            Vector<Vector<Object>> datos = reporteDAO.obtenerReporteStock(
                fechaInicio.getDate(), 
                fechaFin.getDate()
            );

            Vector<String> columnas = new Vector<>();
            columnas.add("ID");
            columnas.add("Nombre Artículo");
            columnas.add("Stock");

            DefaultTableModel model = new DefaultTableModel(datos, columnas);
            tabla.setModel(model);
        };
    }

    public ActionListener getBtnImprimirAction() {
        return e -> {
            try {
                if (!tabla.print()) {
                    JOptionPane.showMessageDialog(null, "Error al imprimir");
                }
            } catch (java.awt.print.PrinterException pe) {
                JOptionPane.showMessageDialog(null, "Error: " + pe.getMessage());
            }
        };
    }
}
