package com.compras.controller;

import com.compras.dao.QueryArticulosDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.Vector;

public class QueryArticulosController {
    private JTable tabla;
    private JComboBox<String> cboFiltrar;
    private JComboBox<String> cboOrdenar;
    private QueryArticulosDAO queryDAO;

    public QueryArticulosController(JTable tabla, JComboBox<String> cboFiltrar, JComboBox<String> cboOrdenar) {
        this.tabla = tabla;
        this.cboFiltrar = cboFiltrar;
        this.cboOrdenar = cboOrdenar;
        this.queryDAO = new QueryArticulosDAO();
        
        // Cargar datos iniciales
        actualizarTabla();
    }

    public ActionListener getBtnFiltrarAction() {
        return e -> actualizarTabla();
    }

    private void actualizarTabla() {
        String filtro = (String) cboFiltrar.getSelectedItem();
        String orden = (String) cboOrdenar.getSelectedItem();

        Vector<Vector<Object>> datos = queryDAO.consultarArticulos(filtro, orden);

        Vector<String> columnas = new Vector<>();
        columnas.add("ID");
        columnas.add("Nombre Artículo");
        columnas.add("Stock");
        columnas.add("Proveedor");
        columnas.add("Comprador");

        DefaultTableModel model = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tabla.setModel(model);
    }
} 