package com.compras.controller;

import com.compras.dao.*;
import com.compras.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class OrdenCompraController {
    private OrdenCompraDAO dao;
    private ProveedoresDAO proveedoresDAO;
    private CompradoresDAO compradoresDAO;
    private ArticulosDAO articulosDAO;
    private JComboBox<String> cboNumeroOrden;
    private JDateChooser dateChooser;
    private JComboBox<Proveedor> cboProveedor;
    private JComboBox<Comprador> cboComprador;
    private JComboBox<String> cboEstado;
    private JTable tablaDetalle;
    private JComboBox<Articulo> cboArticulo;
    private JTextField txtCantidad;
    private JTextField txtPrecioUnitario;
    private JTextField txtTotal;
    private DefaultTableModel modeloDetalle;
    private ArrayList<DetalleOrdenCompra> detalles;
    private boolean esOrdenNueva = false;
    private boolean ignorarEventos = false;

    public OrdenCompraController(OrdenCompraDAO dao, JComboBox<String> cboNumeroOrden,
            JDateChooser dateChooser, JComboBox<Proveedor> cboProveedor,
            JComboBox<Comprador> cboComprador, JComboBox<String> cboEstado,
            JTable tablaDetalle, JComboBox<Articulo> cboArticulo,
            JTextField txtCantidad, JTextField txtPrecioUnitario, JTextField txtTotal) {

        this.dao = dao;
        this.proveedoresDAO = new ProveedoresDAO();
        this.compradoresDAO = new CompradoresDAO();
        this.articulosDAO = new ArticulosDAO();
        this.cboNumeroOrden = cboNumeroOrden;
        this.dateChooser = dateChooser;
        this.cboProveedor = cboProveedor;
        this.cboComprador = cboComprador;
        this.cboEstado = cboEstado;
        this.tablaDetalle = tablaDetalle;
        this.cboArticulo = cboArticulo;
        this.txtCantidad = txtCantidad;
        this.txtPrecioUnitario = txtPrecioUnitario;
        this.txtTotal = txtTotal;
        this.detalles = new ArrayList<>();

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Configurar modelo de tabla con todas las columnas necesarias
        modeloDetalle = new DefaultTableModel(
                new Object[] { "ID", "Artículo", "Cantidad", "Precio Unitario", "Subtotal" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class;    // ID
                    case 1: return String.class;     // Artículo
                    case 2: return Integer.class;    // Cantidad
                    case 3: return Double.class;     // Precio Unitario
                    case 4: return Double.class;     // Subtotal
                    default: return Object.class;
                }
            }
        };
        tablaDetalle.setModel(modeloDetalle);

        // Cargar ComboBoxes
        cargarProveedores();
        cargarCompradores();
        cargarArticulos();

        // Agregar item vacío al cboNumeroOrden
        cboNumeroOrden.addItem("");
        cargarNumerosOrden();

        // Establecer fecha actual
        dateChooser.setDate(new Date());

        // Inicializar total
        txtTotal.setText("0.00");

        // Deshabilitar campos inicialmente
        habilitarCampos(false);
    }

    private void habilitarCampos(boolean habilitar) {
        dateChooser.setEnabled(habilitar);
        cboProveedor.setEnabled(habilitar);
        cboComprador.setEnabled(habilitar);
        cboArticulo.setEnabled(habilitar);
        txtCantidad.setEnabled(habilitar);
        txtPrecioUnitario.setEnabled(habilitar);
    }

    private void cargarProveedores() {
        cboProveedor.removeAllItems();
        List<Proveedor> proveedores = proveedoresDAO.obtenerTodos();
        for (Proveedor proveedor : proveedores) {
            cboProveedor.addItem(proveedor);
        }
    }

    private void cargarCompradores() {
        cboComprador.removeAllItems();
        List<Comprador> compradores = compradoresDAO.obtenerTodos();
        for (Comprador comprador : compradores) {
            cboComprador.addItem(comprador);
        }
    }

    private void cargarArticulos() {
        cboArticulo.removeAllItems();
        List<Articulo> articulos = articulosDAO.obtenerTodos();
        for (Articulo articulo : articulos) {
            cboArticulo.addItem(articulo);
        }
    }

    private void cargarNumerosOrden() {
        try {
            ignorarEventos = true;
            String selectedItem = (String) cboNumeroOrden.getSelectedItem();
            
            cboNumeroOrden.removeAllItems();
            cboNumeroOrden.addItem("");
            
            List<OrdenCompra> ordenes = dao.obtenerTodos();
            for (OrdenCompra orden : ordenes) {
                cboNumeroOrden.addItem(orden.getNumero());
            }
            
            if (selectedItem != null && !selectedItem.isEmpty()) {
                cboNumeroOrden.setSelectedItem(selectedItem);
            }
        } finally {
            ignorarEventos = false;
        }
    }

    private void configurarEventos() {
        cboNumeroOrden.addActionListener(e -> {
            if (ignorarEventos) return;

            String numeroSeleccionado = (String) cboNumeroOrden.getSelectedItem();
            String numeroAnterior = esOrdenNueva ? cboNumeroOrden.getSelectedItem().toString() : "";

            // Verificar si hay cambios sin guardar
            if (esOrdenNueva && !detalles.isEmpty()) {
                int confirmacion = JOptionPane.showConfirmDialog(null,
                    "Hay una orden nueva sin guardar. ¿Desea descartarla?",
                    "Confirmar cambio",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                if (confirmacion == JOptionPane.NO_OPTION) {
                    ignorarEventos = true;
                    cboNumeroOrden.setSelectedItem(numeroAnterior);
                    ignorarEventos = false;
                    return;
                } else {
                    // Si confirma descartar, actualizar el combo box manteniendo la selección nueva
                    ignorarEventos = true;
                    cboNumeroOrden.removeItem(numeroAnterior);
                    cargarNumerosOrden();
                    cboNumeroOrden.setSelectedItem(numeroSeleccionado); // Mantener la selección nueva
                    ignorarEventos = false;
                }
            }

            // Proceder con el cambio de orden
            if (numeroSeleccionado != null && !numeroSeleccionado.isEmpty()) {
                OrdenCompra ordenExistente = dao.obtenerPorNumero(numeroSeleccionado);
                if (ordenExistente != null) {
                    cargarOrdenExistente(numeroSeleccionado);
                    habilitarCampos(false);
                    esOrdenNueva = false;
                    JOptionPane.showMessageDialog(null,
                        "Las órdenes existentes solo pueden ser visualizadas",
                        "Modo visualización",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                limpiarTodo();
                habilitarCampos(false);
                esOrdenNueva = false;
            }
        });
    }

    private void cargarOrdenExistente(String numero) {
        List<OrdenCompra> ordenes = dao.obtenerTodos();
        OrdenCompra ordenSeleccionada = ordenes.stream()
                .filter(o -> o.getNumero().equals(numero))
                .findFirst()
                .orElse(null);

        if (ordenSeleccionada != null) {
            // Cargar datos de la orden
            dateChooser.setDate(ordenSeleccionada.getFecha());
            cboProveedor.setSelectedItem(ordenSeleccionada.getProveedor());
            cboComprador.setSelectedItem(ordenSeleccionada.getComprador());
            cboEstado.setSelectedItem(ordenSeleccionada.getEstado());

            // Cargar detalles
            detalles.clear();
            detalles.addAll(ordenSeleccionada.getDetalleOrdenCompra());
            actualizarTablaDetalle();
            actualizarTotal();
        }
    }

    public ActionListener getAgregarItemListener() {
        return e -> {
            Articulo articulo = (Articulo) cboArticulo.getSelectedItem();
            if (articulo == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un artículo");
                return;
            }

            // Verificar si el artículo ya existe en la tabla
            for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
                int articuloId = (int) modeloDetalle.getValueAt(i, 0);
                if (articuloId == articulo.getId()) {
                    JOptionPane.showMessageDialog(null, 
                        "Este artículo ya está en la orden. Si desea modificar la cantidad, elimine la línea y agréguela nuevamente.");
                    return;
                }
            }

            try {
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precioUnitario = Double.parseDouble(txtPrecioUnitario.getText());

                if (cantidad <= 0 || precioUnitario <= 0) {
                    JOptionPane.showMessageDialog(null, 
                        "La cantidad y el precio unitario deben ser mayores a cero");
                    return;
                }

                double subtotal = cantidad * precioUnitario;
                modeloDetalle.addRow(new Object[]{
                    articulo.getId(),
                    articulo.getNombre(),
                    cantidad,
                    precioUnitario,
                    subtotal
                });

                limpiarCamposDetalle();
                actualizarTotal(); // Actualizar el total después de agregar un item

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Por favor ingrese valores numéricos válidos para cantidad y precio unitario");
            }
        };
    }

    public ActionListener getQuitarItemListener() {
        return e -> {
            int filaSeleccionada = tablaDetalle.getSelectedRow();
            if (filaSeleccionada >= 0) {
                detalles.remove(filaSeleccionada);
                actualizarTablaDetalle();
                actualizarTotal();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Seleccione un item para quitar");
            }
        };
    }

    public ActionListener getGuardarListener() {
        return e -> {
            String numeroSeleccionado = (String) cboNumeroOrden.getSelectedItem();
            if (numeroSeleccionado == null || numeroSeleccionado.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Debe crear una nueva orden o seleccionar una existente");
                return;
            }
            
            // Verificar si la orden ya existe solo si no es una orden nueva
            if (!esOrdenNueva) {
                OrdenCompra ordenExistente = dao.obtenerPorNumero(numeroSeleccionado);
                if (ordenExistente != null) {
                    JOptionPane.showMessageDialog(null,
                        "No se puede modificar una orden existente. Por favor, cree una nueva orden.",
                        "Orden existente",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            // Verificar si hay items en la tabla
            if (modeloDetalle.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, 
                    "Debe agregar al menos un item a la orden");
                return;
            }
            
            try {
                // Crear la lista de detalles desde la tabla
                detalles.clear();
                for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
                    int articuloId = (int) modeloDetalle.getValueAt(i, 0);
                    String nombreArticulo = (String) modeloDetalle.getValueAt(i, 1);
                    int cantidad = (int) modeloDetalle.getValueAt(i, 2);
                    double precioUnitario = (double) modeloDetalle.getValueAt(i, 3);
                    
                    Articulo articulo = new Articulo(articuloId, nombreArticulo, 0, "");
                    DetalleOrdenCompra detalle = new DetalleOrdenCompra(articulo, cantidad, precioUnitario, cantidad * precioUnitario);
                    detalles.add(detalle);
                }
                
                double totalOrden = calcularTotalOrden();
                
                OrdenCompra orden = new OrdenCompra(
                    0,
                    numeroSeleccionado,
                    dateChooser.getDate(),
                    (Proveedor) cboProveedor.getSelectedItem(),
                    (Comprador) cboComprador.getSelectedItem(),
                    cboEstado.getSelectedItem().toString(),
                    totalOrden
                );
                
                orden.setDetalleOrdenCompra(detalles);
                
                if (dao.insertar(orden)) {
                    JOptionPane.showMessageDialog(null, 
                        "Orden de compra guardada exitosamente");
                    esOrdenNueva = false;
                    cargarNumerosOrden();
                    limpiarTodo();
                    cboNumeroOrden.setSelectedItem("");
                    habilitarCampos(false);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error al guardar la orden: " + ex.getMessage());
            }
        };
    }

    private double calcularTotalOrden() {
        double total = 0.0;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            total += (double) modeloDetalle.getValueAt(i, 4); // La columna 4 contiene el subtotal
        }
        return total;
    }

    private void actualizarTotal() {
        double total = calcularTotalOrden();
        txtTotal.setText(String.format("%.2f", total));
    }

    private void limpiarCamposDetalle() {
        txtCantidad.setText("");
        txtPrecioUnitario.setText("");
        cboArticulo.setSelectedIndex(0);
    }

    private void limpiarTodo() {
        dateChooser.setDate(new Date());
        cboProveedor.setSelectedIndex(0);
        cboComprador.setSelectedIndex(0);
        cboEstado.setSelectedIndex(0);
        detalles.clear();
        actualizarTablaDetalle();
        limpiarCamposDetalle();
        txtTotal.setText("0.00");
        esOrdenNueva = false;
    }

    public ActionListener getNuevaOrdenListener() {
        return e -> {
            try {
                ignorarEventos = true;
                limpiarTodo();
                habilitarCampos(true);
                cboEstado.setSelectedItem("Emitido");
                cboEstado.setEnabled(false);
                
                String nuevoNumero = dao.generarNumeroOrden();
                cboNumeroOrden.addItem(nuevoNumero);
                cboNumeroOrden.setSelectedItem(nuevoNumero);
                esOrdenNueva = true;
            } finally {
                ignorarEventos = false;
            }
        };
    }

    public ActionListener getEliminarOrdenListener() {
        return e -> {
            String numeroSeleccionado = (String) cboNumeroOrden.getSelectedItem();
            if (numeroSeleccionado != null && !numeroSeleccionado.startsWith("Nueva Orden")) {
                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de eliminar la orden " + numeroSeleccionado + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (dao.eliminar(numeroSeleccionado)) {
                        JOptionPane.showMessageDialog(null, "Orden eliminada exitosamente");
                        cargarNumerosOrden();
                        limpiarTodo();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar la orden");
                    }
                }
            }
        };
    }

    public void agregarArticulo(Articulo articulo, int cantidad, double precioUnitario) {
        for (DetalleOrdenCompra detalle : detalles) {
            if (detalle.getArticulo().getId() == articulo.getId()) {
                JOptionPane.showMessageDialog(null, "El artículo ya está en la orden. Puede quitar y agregar el artículo con la nueva cantidad de ser necesario.");
                return;
            }
        }
        DetalleOrdenCompra nuevoDetalle = new DetalleOrdenCompra(articulo, cantidad, precioUnitario, cantidad * precioUnitario);
        detalles.add(nuevoDetalle);
        actualizarTablaDetalle();
    }

    private void actualizarTablaDetalle() {
        modeloDetalle.setRowCount(0); // Limpiar la tabla
        
        for (DetalleOrdenCompra detalle : detalles) {
            modeloDetalle.addRow(new Object[]{
                detalle.getArticulo().getId(),
                detalle.getArticulo().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
            });
        }
        
        actualizarTotal(); // Actualizar el total después de modificar la tabla
    }
}
