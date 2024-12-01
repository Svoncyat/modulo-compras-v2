package com.compras.controller;

import com.compras.dao.ProveedoresDAO;
import com.compras.model.Proveedor;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class ProveedoresController {
    private ProveedoresDAO dao;
    private JTable tabla;
    private JTextField txtNombre;
    private JTextField txtContacto;
    private DefaultTableModel modelo;

    public ProveedoresController(ProveedoresDAO dao, JTable tabla, 
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
        List<Proveedor> proveedores = dao.obtenerTodos();
        for (Proveedor proveedor : proveedores) {
            modelo.addRow(new Object[]{
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getContacto()
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

            Proveedor proveedor = new Proveedor(0, nombre, contacto);
            if (dao.insertar(proveedor)) {
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(null, 
                    "Proveedor agregado exitosamente");
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
                Proveedor proveedor = new Proveedor(id, nombre, contacto);
                
                if (dao.actualizar(proveedor)) {
                    actualizarTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(null, 
                        "Proveedor modificado exitosamente");
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un proveedor para modificar");
            }
        };
    }

    public ActionListener getEliminarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                int id = (int) tabla.getValueAt(filaSeleccionada, 0);
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                    "¿Está seguro de eliminar este proveedor?", 
                    "Confirmar eliminación", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (dao.eliminar(id)) {
                        actualizarTabla();
                        limpiarCampos();
                        JOptionPane.showMessageDialog(null, 
                            "Proveedor eliminado exitosamente");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un proveedor para eliminar");
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
