package com.compras.controller;

import com.compras.dao.ArticulosDAO;
import com.compras.model.Articulo;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class ArticulosController {
    private ArticulosDAO dao;
    private JTable tabla;
    private JTextField txtNombre;
    private JTextField txtStock;
    private JTextArea txtDescripcion;
    private DefaultTableModel modelo;

    public ArticulosController(ArticulosDAO dao, JTable tabla, JTextField txtNombre, 
                             JTextField txtStock, JTextArea txtDescripcion) {
        this.dao = dao;
        this.tabla = tabla;
        this.txtNombre = txtNombre;
        this.txtStock = txtStock;
        this.txtDescripcion = txtDescripcion;
        this.modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Stock", "Descripción"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tabla.setModel(modelo);
    }

    public void configurarTabla() {
        actualizarTabla();
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        List<Articulo> articulos = dao.obtenerTodos();
        for (Articulo articulo : articulos) {
            modelo.addRow(new Object[]{
                articulo.getId(),
                articulo.getNombre(),
                articulo.getStock(),
                articulo.getDescripcion()
            });
        }
    }

    public ActionListener getAgregarListener() {
        return e -> {
            try {
                String nombre = txtNombre.getText().trim();
                int stock = Integer.parseInt(txtStock.getText().trim());
                String descripcion = txtDescripcion.getText().trim();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                    return;
                }

                Articulo articulo = new Articulo(0, nombre, stock, descripcion);
                if (dao.insertar(articulo)) {
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(null, "Artículo agregado exitosamente");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El stock debe ser un número válido");
            }
        };
    }

    public ActionListener getModificarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                try {
                    int id = (int) tabla.getValueAt(filaSeleccionada, 0);
                    String nombre = txtNombre.getText().trim();
                    int stock = Integer.parseInt(txtStock.getText().trim());
                    String descripcion = txtDescripcion.getText().trim();

                    if (nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                        return;
                    }

                    Articulo articulo = new Articulo(id, nombre, stock, descripcion);
                    if (dao.actualizar(articulo)) {
                        actualizarTabla();
                        limpiarCampos();
                        JOptionPane.showMessageDialog(null, "Artículo modificado exitosamente");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El stock debe ser un número válido");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un artículo para modificar");
            }
        };
    }

    public ActionListener getEliminarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int id = (int) tabla.getValueAt(filaSeleccionada, 0);
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de eliminar este artículo?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (dao.eliminar(id)) {
                        actualizarTabla();
                        limpiarCampos();
                        JOptionPane.showMessageDialog(null, "Artículo eliminado exitosamente");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un artículo para eliminar");
            }
        };
    }

    public ListSelectionListener getTablaSelectionListener() {
        return e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txtNombre.setText(tabla.getValueAt(filaSeleccionada, 1).toString());
                    txtStock.setText(tabla.getValueAt(filaSeleccionada, 2).toString());
                    txtDescripcion.setText(tabla.getValueAt(filaSeleccionada, 3).toString());
                }
            }
        };
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtStock.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
    }
}
