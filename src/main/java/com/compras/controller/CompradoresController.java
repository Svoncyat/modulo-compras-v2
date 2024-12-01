package com.compras.controller;

import com.compras.dao.CompradoresDAO;
import com.compras.model.Comprador;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class CompradoresController {
    private CompradoresDAO dao;
    private JTable tabla;
    private JTextField txtNombre;
    private JTextField txtContacto;
    private DefaultTableModel modelo;

    public CompradoresController(CompradoresDAO dao, JTable tabla, 
                               JTextField txtNombre, JTextField txtContacto) {
        this.dao = dao;
        this.tabla = tabla;
        this.txtNombre = txtNombre;
        this.txtContacto = txtContacto;
        this.modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Contacto"}, 0
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
        List<Comprador> compradores = dao.obtenerTodos();
        for (Comprador comprador : compradores) {
            modelo.addRow(new Object[]{
                comprador.getId(),
                comprador.getNombre(),
                comprador.getContacto()
            });
        }
    }

    public ActionListener getAgregarListener() {
        return e -> {
            String nombre = txtNombre.getText().trim();
            String contacto = txtContacto.getText().trim();

            if (nombre.isEmpty() || contacto.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Los campos nombre y contacto son obligatorios");
                return;
            }

            Comprador comprador = new Comprador(0, nombre, contacto);
            if (dao.insertar(comprador)) {
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(null, 
                    "Comprador agregado exitosamente");
            }
        };
    }

    public ActionListener getModificarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String nombre = txtNombre.getText().trim();
                String contacto = txtContacto.getText().trim();

                if (nombre.isEmpty() || contacto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, 
                        "Los campos nombre y contacto son obligatorios");
                    return;
                }

                int id = (int) tabla.getValueAt(filaSeleccionada, 0);
                Comprador comprador = new Comprador(id, nombre, contacto);
                
                if (dao.actualizar(comprador)) {
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(null, 
                        "Comprador modificado exitosamente");
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un comprador para modificar");
            }
        };
    }

    public ActionListener getEliminarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int id = (int) tabla.getValueAt(filaSeleccionada, 0);
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de eliminar este comprador?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (dao.eliminar(id)) {
                        actualizarTabla();
                        limpiarCampos();
                        JOptionPane.showMessageDialog(null, 
                            "Comprador eliminado exitosamente");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un comprador para eliminar");
            }
        };
    }

    public ListSelectionListener getTablaSelectionListener() {
        return e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txtNombre.setText(tabla.getValueAt(filaSeleccionada, 1).toString());
                    txtContacto.setText(tabla.getValueAt(filaSeleccionada, 2).toString());
                }
            }
        };
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtContacto.setText("");
        tabla.clearSelection();
    }
}
