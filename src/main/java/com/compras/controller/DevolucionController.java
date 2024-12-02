package com.compras.controller;

import com.compras.dao.DevolucionDAO;
import com.compras.dao.ProveedoresDAO;
import com.compras.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class DevolucionController {
    private DevolucionDAO dao;
    private JTable tabla;
    private JComboBox<Proveedor> cboProveedor;
    private JTextField txtArticulo;
    private JTextField txtCantidadDevolver;
    private DefaultTableModel modelo;

    public DevolucionController(DevolucionDAO dao, JTable tabla, 
                              JComboBox<Proveedor> cboProveedor,
                              JTextField txtArticulo, 
                              JTextField txtCantidadDevolver) {
        this.dao = dao;
        this.tabla = tabla;
        this.cboProveedor = cboProveedor;
        this.txtArticulo = txtArticulo;
        this.txtCantidadDevolver = txtCantidadDevolver;
        
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(
            new Object[]{"ID Artículo", "Artículo", "Cantidad Recibida", "Cantidad a Devolver"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modelo);
        
        cargarProveedores();
        
        // Listener para el combo de proveedores
        cboProveedor.addActionListener(e -> {
            Proveedor proveedor = (Proveedor) cboProveedor.getSelectedItem();
            if (proveedor != null) {
                cargarOrdenesProveedor(proveedor);
            }
        });

        // Listener para la selección en la tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtArticulo.setText(modelo.getValueAt(fila, 1).toString());
                    txtCantidadDevolver.setText("");
                }
            }
        });
    }

    private void cargarProveedores() {
        cboProveedor.removeAllItems();
        cboProveedor.addItem(null);
        ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
        List<Proveedor> proveedores = proveedoresDAO.obtenerTodos();
        for (Proveedor proveedor : proveedores) {
            cboProveedor.addItem(proveedor);
        }
    }

    private void cargarOrdenesProveedor(Proveedor proveedor) {
        modelo.setRowCount(0);
        List<OrdenCompra> ordenes = dao.obtenerOrdenesRecibidas();
        
        for (OrdenCompra orden : ordenes) {
            if (orden.getProveedor().getId() == proveedor.getId()) {
                for (DetalleOrdenCompra detalle : orden.getDetalleOrdenCompra()) {
                    modelo.addRow(new Object[]{
                        detalle.getArticulo().getId(),
                        detalle.getArticulo().getNombre(),
                        detalle.getCantidad(),
                        0,  // Cantidad a devolver inicial
                    });
                }
            }
        }
    }

    public ActionListener getModificarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                try {
                    int cantidadRecibida = (int) modelo.getValueAt(filaSeleccionada, 2);
                    int cantidadDevolver = Integer.parseInt(txtCantidadDevolver.getText().trim());
                    
                    if (cantidadDevolver <= 0) {
                        JOptionPane.showMessageDialog(null, 
                            "La cantidad a devolver debe ser mayor a 0");
                        return;
                    }
                    
                    if (cantidadDevolver > cantidadRecibida) {
                        JOptionPane.showMessageDialog(null, 
                            "La cantidad a devolver no puede ser mayor a la recibida");
                        return;
                    }
                    
                    modelo.setValueAt(cantidadDevolver, filaSeleccionada, 3);
                    txtArticulo.setText("");
                    txtCantidadDevolver.setText("");
                    tabla.clearSelection();
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Por favor ingrese una cantidad válida");
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Por favor seleccione un artículo de la tabla");
            }
        };
    }

    public ActionListener getProcesarListener() {
        return e -> {
            Proveedor proveedor = (Proveedor) cboProveedor.getSelectedItem();
            if (proveedor == null) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor seleccione un proveedor");
                return;
            }

            // Verificar que al menos un artículo tenga cantidad a devolver
            boolean hayDevoluciones = false;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int cantidadDevolver = (int) modelo.getValueAt(i, 3);
                if (cantidadDevolver > 0) {
                    hayDevoluciones = true;
                    break;
                }
            }

            if (!hayDevoluciones) {
                JOptionPane.showMessageDialog(null, 
                    "Debe especificar al menos una cantidad a devolver");
                return;
            }

            // Modificar la forma en que obtenemos y procesamos las devoluciones
            List<OrdenCompra> ordenes = dao.obtenerOrdenesRecibidas();
            for (OrdenCompra orden : ordenes) {
                if (orden.getProveedor().getId() == proveedor.getId()) {
                    for (int i = 0; i < modelo.getRowCount(); i++) {
                        int cantidadDevolver = (int) modelo.getValueAt(i, 3);
                        if (cantidadDevolver > 0) {
                            int articuloId = (int) modelo.getValueAt(i, 0);
                            // Verificar si el artículo pertenece a esta orden
                            for (DetalleOrdenCompra detalle : orden.getDetalleOrdenCompra()) {
                                if (detalle.getArticulo().getId() == articuloId) {
                                    Devolucion devolucion = new Devolucion(
                                        0, 
                                        orden, 
                                        proveedor,
                                        detalle.getArticulo(),
                                        cantidadDevolver
                                    );
                                    if (dao.registrarDevolucion(devolucion)) {
                                        JOptionPane.showMessageDialog(null, 
                                            "Devolución registrada exitosamente");
                                    } else {
                                        JOptionPane.showMessageDialog(null, 
                                            "Error al registrar la devolución");
                                        return;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            limpiarTodo();
        };
    }

    private void limpiarTodo() {
        modelo.setRowCount(0);
        txtArticulo.setText("");
        txtCantidadDevolver.setText("");
        cboProveedor.setSelectedIndex(-1);
    }
}
