package com.compras.controller;

import com.compras.dao.IngresoDAO;
import com.compras.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class IngresoController {
    private IngresoDAO dao;
    private JTable tabla;
    private JComboBox<OrdenCompra> cboOrdenCompra;
    private JTextField txtArticulo;
    private JTextField txtCantidadRecibida;
    private JTextField txtTotal;
    private JTextField txtTotalRecibido;
    private DefaultTableModel modelo;

    public IngresoController(IngresoDAO dao, JTable tabla, 
                           JComboBox<OrdenCompra> cboOrdenCompra,
                           JTextField txtArticulo, JTextField txtCantidadRecibida,
                           JTextField txtTotal, JTextField txtTotalRecibido) {
        this.dao = dao;
        this.tabla = tabla;
        this.cboOrdenCompra = cboOrdenCompra;
        this.txtArticulo = txtArticulo;
        this.txtCantidadRecibida = txtCantidadRecibida;
        this.txtTotal = txtTotal;
        this.txtTotalRecibido = txtTotalRecibido;
        
        inicializarComponentes();
        cargarOrdenesCompra();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(
            new Object[]{"ID", "Artículo", "Cantidad Solicitada", "Cantidad Recibida"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModel(modelo);
        
        cboOrdenCompra.addItem(null);
        
        // Listener para el combo de órdenes
        cboOrdenCompra.addActionListener(e -> {
            OrdenCompra orden = (OrdenCompra) cboOrdenCompra.getSelectedItem();
            if (orden != null) {
                cargarDetallesOrden(orden);
            } else {
                limpiarTodo();
            }
        });

        // Listener para la selección en la tabla
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtArticulo.setText(modelo.getValueAt(fila, 1).toString());
                    txtCantidadRecibida.setText(modelo.getValueAt(fila, 3).toString());
                }
            }
        });
    }

    private void cargarOrdenesCompra() {
        cboOrdenCompra.removeAllItems();
        cboOrdenCompra.addItem(null);
        List<OrdenCompra> ordenes = dao.obtenerOrdenesPendientes();
        for (OrdenCompra orden : ordenes) {
            cboOrdenCompra.addItem(orden);
        }
    }

    private void cargarDetallesOrden(OrdenCompra orden) {
        modelo.setRowCount(0);
        int totalSolicitado = 0;
        int totalRecibido = 0;

        for (DetalleOrdenCompra detalle : orden.getDetalleOrdenCompra()) {
            // Obtener la cantidad ya recibida para este artículo específico
            int cantidadRecibida = dao.obtenerCantidadRecibida(orden.getId(), detalle.getArticulo().getId());
            
            modelo.addRow(new Object[]{
                detalle.getArticulo().getId(),
                detalle.getArticulo().getNombre(),
                detalle.getCantidad(),
                cantidadRecibida  // Mostrar la cantidad ya recibida desde la base de datos
            });
            
            totalSolicitado += detalle.getCantidad();
            totalRecibido += cantidadRecibida;
        }

        txtTotal.setText(String.valueOf(totalSolicitado));
        txtTotalRecibido.setText(String.valueOf(totalRecibido));
    }

    public ActionListener getModificarListener() {
        return e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                try {
                    OrdenCompra ordenSeleccionada = (OrdenCompra) cboOrdenCompra.getSelectedItem();
                    int cantidadSolicitada = (int) modelo.getValueAt(filaSeleccionada, 2);
                    int cantidadYaRecibida = (int) modelo.getValueAt(filaSeleccionada, 3);
                    int cantidadNueva = Integer.parseInt(txtCantidadRecibida.getText().trim());
                    
                    // Validación de cantidad mayor a 0 solo para órdenes en estado "Emitido"
                    if (ordenSeleccionada.getEstado().equals("Emitido") && cantidadNueva <= 0) {
                        JOptionPane.showMessageDialog(null, 
                            "La cantidad recibida debe ser mayor a 0");
                        return;
                    }
                    
                    if (cantidadNueva > cantidadSolicitada) {
                        JOptionPane.showMessageDialog(null, 
                            "La cantidad recibida no puede ser mayor a la solicitada");
                        txtCantidadRecibida.setText(String.valueOf(cantidadYaRecibida)); // Restaurar valor anterior
                        return;
                    }

                    // Validación para cantidad menor a la ya recibida
                    if (cantidadNueva < cantidadYaRecibida) {
                        int confirmacion = JOptionPane.showConfirmDialog(null,
                            "La cantidad ingresada (" + cantidadNueva + ") es menor a la cantidad ya recibida (" + 
                            cantidadYaRecibida + "). ¿Desea continuar?",
                            "Confirmar cantidad",
                            JOptionPane.YES_NO_OPTION);
                        
                        if (confirmacion != JOptionPane.YES_OPTION) {
                            txtCantidadRecibida.setText(String.valueOf(cantidadYaRecibida)); // Restaurar valor anterior
                            return;
                        }
                    }
                    
                    modelo.setValueAt(cantidadNueva, filaSeleccionada, 3);
                    actualizarTotalRecibido();
                    
                    txtArticulo.setText("");
                    txtCantidadRecibida.setText("");
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
            OrdenCompra orden = (OrdenCompra) cboOrdenCompra.getSelectedItem();
            if (orden == null) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor seleccione una orden de compra");
                return;
            }

            boolean hayIngresos = false;
            boolean hayPendientes = false;
            
            Ingreso ingreso = new Ingreso(0, orden);
            
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int articuloId = (int) modelo.getValueAt(i, 0);
                int cantidadRecibida = (int) modelo.getValueAt(i, 3);
                int cantidadSolicitada = (int) modelo.getValueAt(i, 2);
                
                if (cantidadRecibida > 0) {
                    hayIngresos = true;
                }
                if (cantidadRecibida < cantidadSolicitada) {
                    hayPendientes = true;
                }
                
                ingreso.setCantidadRecibida(articuloId, cantidadRecibida);
            }

            if (!hayIngresos) {
                JOptionPane.showMessageDialog(null, 
                    "Debe especificar al menos una cantidad recibida mayor a cero");
                return;
            }

            String nuevoEstado = hayPendientes ? "Backorder" : "Recibido";
            orden.setEstado(nuevoEstado);
            
            if (dao.registrarIngreso(ingreso)) {
                JOptionPane.showMessageDialog(null, 
                    "Ingreso registrado exitosamente. Estado de la orden: " + nuevoEstado);
                limpiarTodo();
                cargarOrdenesCompra();
                cboOrdenCompra.setSelectedItem(null);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al registrar el ingreso");
            }
        };
    }

    private void actualizarTotalRecibido() {
        int total = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            int cantidadRecibida = (int) modelo.getValueAt(i, 3);
            total += cantidadRecibida;
        }
        txtTotalRecibido.setText(String.valueOf(total));
    }

    private void limpiarTodo() {
        modelo.setRowCount(0);
        txtArticulo.setText("");
        txtCantidadRecibida.setText("");
        txtTotal.setText("0");
        txtTotalRecibido.setText("0");
    }
}
