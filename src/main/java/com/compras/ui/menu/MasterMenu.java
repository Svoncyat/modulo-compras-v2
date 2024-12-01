package com.compras.ui.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import com.compras.model.Articulo;
import com.compras.model.Proveedor;
import com.compras.model.Comprador;
import com.compras.model.OrdenCompra;

import com.compras.dao.ArticulosDAO;
import com.compras.controller.ArticulosController;
import com.compras.dao.ProveedoresDAO;
import com.compras.controller.ProveedoresController;
import com.compras.dao.CompradoresDAO;
import com.compras.controller.CompradoresController;
import com.compras.dao.OrdenCompraDAO;
import com.compras.controller.OrdenCompraController;
import com.compras.dao.IngresoDAO;
import com.compras.controller.IngresoController;
import com.compras.dao.DevolucionDAO;
import com.compras.controller.DevolucionController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MasterMenu {

    public JMenu menuMaestros() {

        JMenu maestros = new JMenu("Maestros");

        // Artículos
        JMenuItem articulosItem = new JMenuItem("Artículos");
        articulosItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarArticulos();
            }
        });

        // Proveedores
        JMenuItem proveedoresItem = new JMenuItem("Proveedores");
        proveedoresItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarProveedores();
            }
        });

        // Compradores
        JMenuItem compradoresItem = new JMenuItem("Compradores");
        compradoresItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarCompradores();
            }
        });

        // Transacciones
        JMenu transacciones = new JMenu("Transacciones");

        JMenuItem ordenCompraItem = new JMenuItem("Orden de compra");
        ordenCompraItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarOrdenCompra();
            }
        });
        JMenuItem ingresoItem = new JMenuItem("Ingreso");
        ingresoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarIngreso();
            }
        });

        JMenuItem devolucionItem = new JMenuItem("Devolución");
        devolucionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrarDevolucion();
            }
        });

        // Agregar items a "Maestros"
        maestros.add(articulosItem);
        maestros.add(proveedoresItem);
        maestros.add(compradoresItem);
        maestros.add(transacciones);

        transacciones.add(ordenCompraItem);
        transacciones.add(ingresoItem);
        transacciones.add(devolucionItem);

        return maestros;
    }

    private void administrarArticulos() {
        JDialog dialog = new JDialog(new JFrame(), "Administrar Artículos", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear tabla y modelo
        String[] columnas = { "ID", "Nombre", "Stock", "Descripción" };
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);

        // Agregar tabla a un ScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de edición y botones usando GridBagLayout
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de edición
        JTextField txtNombre = new JTextField(20);
        JTextField txtStock = new JTextField("0",20);
        JTextArea txtDescripcion = new JTextArea(9, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        // Agregar componentes de edición
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDerecho.add(new JLabel("Nombre: "), gbc);

        gbc.gridy = 1;
        panelDerecho.add(txtNombre, gbc);

        gbc.gridy = 2;
        panelDerecho.add(new JLabel("Stock: "), gbc);

        gbc.gridy = 3;
        panelDerecho.add(txtStock, gbc);

        gbc.gridy = 4;
        panelDerecho.add(new JLabel("Descripción: "), gbc);

        gbc.gridy = 5;
        panelDerecho.add(scrollDescripcion, gbc);

        // Agregar espacio vertical
        gbc.gridy = 6;
        gbc.weighty = 1.0;
        panelDerecho.add(Box.createVerticalStrut(20), gbc);

        // Botones
        JButton btnAgregar = new JButton("Agregar artículo");
        JButton btnModificar = new JButton("Modificar artículo");
        JButton btnEliminar = new JButton("Eliminar artículo");

        gbc.weighty = 0.0;
        gbc.gridy = 7;
        panelDerecho.add(btnAgregar, gbc);

        gbc.gridy = 8;
        panelDerecho.add(btnModificar, gbc);

        gbc.gridy = 9;
        panelDerecho.add(btnEliminar, gbc);

        mainPanel.add(panelDerecho, BorderLayout.EAST);
        dialog.add(mainPanel);

        // Crear el controlador
        ArticulosController controller = new ArticulosController(
                new ArticulosDAO(), tabla, txtNombre, txtStock, txtDescripcion);
        
        controller.configurarTabla();
        btnAgregar.addActionListener(controller.getAgregarListener());
        btnModificar.addActionListener(controller.getModificarListener());
        btnEliminar.addActionListener(controller.getEliminarListener());
        tabla.getSelectionModel().addListSelectionListener(
                controller.getTablaSelectionListener());

        dialog.setVisible(true);
    }

    private void administrarProveedores() {
        JDialog dialog = new JDialog(new JFrame(), "Administrar Proveedores", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear tabla y modelo
        String[] columnas = { "ID", "Nombre", "Contacto" };
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);

        // Agregar tabla a un ScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de edición y botones usando GridBagLayout
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de edición basados en el modelo Proveedor
        JTextField txtNombre = new JTextField(20);
        JTextField txtContacto = new JTextField(20);

        // Agregar componentes de edición
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDerecho.add(new JLabel("Nombre: "), gbc);

        gbc.gridy = 1;
        panelDerecho.add(txtNombre, gbc);

        gbc.gridy = 2;
        panelDerecho.add(new JLabel("Contacto: "), gbc);

        gbc.gridy = 3;
        panelDerecho.add(txtContacto, gbc);

        // Agregar espacio vertical
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        panelDerecho.add(Box.createVerticalStrut(20), gbc);

        // Botones
        JButton btnAgregar = new JButton("Agregar proveedor");
        JButton btnModificar = new JButton("Modificar proveedor");
        JButton btnEliminar = new JButton("Eliminar proveedor");

        gbc.weighty = 0.0;
        gbc.gridy = 5;
        panelDerecho.add(btnAgregar, gbc);

        gbc.gridy = 6;
        panelDerecho.add(btnModificar, gbc);

        gbc.gridy = 7;
        panelDerecho.add(btnEliminar, gbc);

        mainPanel.add(panelDerecho, BorderLayout.EAST);
        dialog.add(mainPanel);

        // Espacio para el controlador (comentado como referencia)
        ProveedoresController controller = new ProveedoresController(
                new ProveedoresDAO(), tabla, txtNombre, txtContacto);
        
        controller.configurarTabla();
        btnAgregar.addActionListener(controller.getAgregarListener());
        btnModificar.addActionListener(controller.getModificarListener());
        btnEliminar.addActionListener(controller.getEliminarListener());
        tabla.getSelectionModel().addListSelectionListener(
                controller.getTablaSelectionListener());

        dialog.setVisible(true);
    }

    private void administrarCompradores() {
        JDialog dialog = new JDialog(new JFrame(), "Administrar Compradores", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear tabla y modelo
        String[] columnas = { "ID", "Nombre", "Contacto" };
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);

        // Agregar tabla a un ScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de edición y botones usando GridBagLayout
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de edición basados en el modelo Comprador
        JTextField txtNombre = new JTextField(20);
        JTextField txtContacto = new JTextField(20);

        // Agregar componentes de edición
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDerecho.add(new JLabel("Nombre: "), gbc);

        gbc.gridy = 1;
        panelDerecho.add(txtNombre, gbc);

        gbc.gridy = 2;
        panelDerecho.add(new JLabel("Contacto: "), gbc);

        gbc.gridy = 3;
        panelDerecho.add(txtContacto, gbc);

        // Agregar espacio vertical
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        panelDerecho.add(Box.createVerticalStrut(20), gbc);

        // Botones
        JButton btnAgregar = new JButton("Agregar comprador");
        JButton btnModificar = new JButton("Modificar comprador");
        JButton btnEliminar = new JButton("Eliminar comprador");

        gbc.weighty = 0.0;
        gbc.gridy = 5;
        panelDerecho.add(btnAgregar, gbc);

        gbc.gridy = 6;
        panelDerecho.add(btnModificar, gbc);

        gbc.gridy = 7;
        panelDerecho.add(btnEliminar, gbc);

        mainPanel.add(panelDerecho, BorderLayout.EAST);
        dialog.add(mainPanel);

        // Espacio para el controlador (comentado como referencia)
        CompradoresController controller = new CompradoresController(
                new CompradoresDAO(), tabla, txtNombre, txtContacto);
        
        controller.configurarTabla();
        btnAgregar.addActionListener(controller.getAgregarListener());
        btnModificar.addActionListener(controller.getModificarListener());
        btnEliminar.addActionListener(controller.getEliminarListener());
        tabla.getSelectionModel().addListSelectionListener(
                controller.getTablaSelectionListener());

        dialog.setVisible(true);
    }

    private void administrarOrdenCompra() {
        JDialog dialog = new JDialog(new JFrame(), "Administrar Orden de Compra", true);
        dialog.setSize(900, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel superior para datos de la orden
        JPanel panelOrden = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Primera fila
        gbc.gridx = 0; gbc.gridy = 0;
        JButton btnNuevaOrden = new JButton("Nueva Orden");
        panelOrden.add(btnNuevaOrden, gbc);

        gbc.gridx = 1;
        JButton btnEliminarOrden = new JButton("Eliminar Orden");
        panelOrden.add(btnEliminarOrden, gbc);

        gbc.gridx = 2;
        panelOrden.add(new JLabel("Número de orden:"), gbc);
        gbc.gridx = 3;
        JComboBox<String> cboNumeroOrden = new JComboBox<>();
        cboNumeroOrden.setPreferredSize(new Dimension(120, 25));
        panelOrden.add(cboNumeroOrden, gbc);

        gbc.gridx = 4;
        panelOrden.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 5;
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(120, 25));
        panelOrden.add(dateChooser, gbc);

        // Segunda fila
        gbc.gridx = 0; gbc.gridy = 1;
        panelOrden.add(new JLabel("Proveedor:"), gbc);
        gbc.gridx = 1;
        JComboBox<Proveedor> cboProveedor = new JComboBox<>();
        panelOrden.add(cboProveedor, gbc);

        gbc.gridx = 2;
        panelOrden.add(new JLabel("Comprador:"), gbc);
        gbc.gridx = 3;
        JComboBox<Comprador> cboComprador = new JComboBox<>();
        panelOrden.add(cboComprador, gbc);

        gbc.gridx = 4;
        panelOrden.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 5;
        JComboBox<String> cboEstado = new JComboBox<>(new String[]{"Emitido", "Recibido", "Backorder"});
        // Emitido es el estado por defecto
        cboEstado.setEnabled(false);
        panelOrden.add(cboEstado, gbc);

        // Panel central para el detalle
        JPanel panelDetalle = new JPanel(new BorderLayout(5, 5));
        panelDetalle.setBorder(BorderFactory.createTitledBorder("Detalle de la Orden"));

        // Tabla de detalle
        String[] columnasDetalle = {"Artículo", "Cantidad", "Precio Unitario", "Subtotal"};
        Object[][] datosDetalle = new Object[0][0];
        JTable tablaDetalle = new JTable(datosDetalle, columnasDetalle);
        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        panelDetalle.add(scrollDetalle, BorderLayout.CENTER);

        // Panel para agregar items al detalle
        JPanel panelAgregarItem = new JPanel(new BorderLayout());

        // Panel izquierdo para campos
        JPanel panelCampos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<Articulo> cboArticulo = new JComboBox<>();
        cboArticulo.setPreferredSize(new Dimension(150, 25));
        JTextField txtCantidad = new JTextField(5);
        JTextField txtPrecioUnitario = new JTextField(5);

        panelCampos.add(new JLabel("Artículo:"));
        panelCampos.add(cboArticulo);
        panelCampos.add(new JLabel("Cantidad:"));
        panelCampos.add(txtCantidad);
        panelCampos.add(new JLabel("Precio:"));
        panelCampos.add(txtPrecioUnitario);

        // Panel derecho para botones
        JPanel panelBotonesItem = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAgregarItem = new JButton("Agregar Item");
        JButton btnQuitarItem = new JButton("Quitar Item");
        panelBotonesItem.add(btnAgregarItem);
        panelBotonesItem.add(btnQuitarItem);

        panelAgregarItem.add(panelCampos, BorderLayout.WEST);
        panelAgregarItem.add(panelBotonesItem, BorderLayout.EAST);

        panelDetalle.add(panelAgregarItem, BorderLayout.NORTH);

        // Panel inferior para totales y botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        
        // Panel de totales
        JPanel panelTotales = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblTotal = new JLabel("Total: S/ ");
        JTextField txtTotal = new JTextField(10);
        txtTotal.setEditable(false);
        panelTotales.add(lblTotal);
        panelTotales.add(txtTotal);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar Orden");
        panelBotones.add(btnGuardar);

        panelInferior.add(panelTotales, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.EAST);

        mainPanel.add(panelOrden, BorderLayout.NORTH);
        mainPanel.add(panelDetalle, BorderLayout.CENTER);
        mainPanel.add(panelInferior, BorderLayout.SOUTH);

        dialog.add(mainPanel);

        // Inicializar el controlador con todos los componentes
        OrdenCompraController controller = new OrdenCompraController(
            new OrdenCompraDAO(), 
            cboNumeroOrden, dateChooser, cboProveedor, cboComprador, cboEstado,
            tablaDetalle, cboArticulo, txtCantidad, txtPrecioUnitario, txtTotal
        );

        btnAgregarItem.addActionListener(controller.getAgregarItemListener());
        btnQuitarItem.addActionListener(controller.getQuitarItemListener());
        btnGuardar.addActionListener(controller.getGuardarListener());
        btnNuevaOrden.addActionListener(controller.getNuevaOrdenListener());
        btnEliminarOrden.addActionListener(controller.getEliminarOrdenListener());

        dialog.setVisible(true);
    }

    private void administrarIngreso() {
        JDialog dialog = new JDialog(new JFrame(), "Administrar Ingreso", true);
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel Central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        
        // Tabla
        String[] columnas = { "ID", "Artículo", "Cantidad Solicitada", "Cantidad Recibida" };
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de totales
        JPanel panelTotales = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotal = new JLabel("Total Solicitado: ");
        JTextField txtTotal = new JTextField(10);
        JLabel lblTotalRecibido = new JLabel("Total Recibido: ");
        JTextField txtTotalRecibido = new JTextField(10);

        txtTotal.setEditable(false);
        txtTotalRecibido.setEditable(false);

        panelTotales.add(lblTotal);
        panelTotales.add(txtTotal);
        panelTotales.add(lblTotalRecibido);
        panelTotales.add(txtTotalRecibido);
        
        panelCentral.add(panelTotales, BorderLayout.SOUTH);
        
        // Panel Derecho
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Componentes del panel derecho
        JComboBox<OrdenCompra> cboOrdenCompra = new JComboBox<>();
        JTextField txtArticulo = new JTextField(20);
        JTextField txtCantidadRecibida = new JTextField(20);
        JButton btnModificar = new JButton("Establecer Cantidad");
        JButton btnProcesar = new JButton("Procesar Ingreso");
        
        txtArticulo.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDerecho.add(new JLabel("Orden de Compra:"), gbc);

        gbc.gridy = 1;
        panelDerecho.add(cboOrdenCompra, gbc);
        
        gbc.gridy = 2;
        panelDerecho.add(new JLabel("Artículo seleccionado:"), gbc);

        gbc.gridy = 3;
        panelDerecho.add(txtArticulo, gbc);

        gbc.gridy = 4;
        panelDerecho.add(new JLabel("Cantidad Recibida:"), gbc);

        gbc.gridy = 5;
        panelDerecho.add(txtCantidadRecibida, gbc);

        gbc.gridy = 6;
        gbc.weighty = 1.0;
        panelDerecho.add(Box.createVerticalGlue(), gbc);

        gbc.weighty = 0.0;
        gbc.gridy = 7;
        panelDerecho.add(btnModificar, gbc);

        gbc.gridy = 8;
        panelDerecho.add(btnProcesar, gbc);

        mainPanel.add(panelCentral, BorderLayout.CENTER);
        mainPanel.add(panelDerecho, BorderLayout.EAST);
        dialog.add(mainPanel);

        IngresoController controller = new IngresoController(
            new IngresoDAO(), tabla, cboOrdenCompra, txtArticulo, txtCantidadRecibida, txtTotal, txtTotalRecibido
        );

        btnModificar.addActionListener(controller.getModificarListener());
        btnProcesar.addActionListener(controller.getProcesarListener());

        dialog.setVisible(true);
    }

    private void administrarDevolucion() {
        JDialog dialog = new JDialog(new JFrame(), "Administrar Devolución", true);
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel Central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        
        // Tabla
        String[] columnas = { "ID", "Artículo", "Cantidad Recibida", "Cantidad a Devolver" };
        Object[][] datos = new Object[0][0];
        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        // Panel Derecho
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Componentes del panel derecho
        JComboBox<Proveedor> cboProveedor = new JComboBox<>();
        JTextField txtArticulo = new JTextField(20);
        JTextField txtCantidadRecibida = new JTextField(20);
        JButton btnModificar = new JButton("Establecer Cantidad");
        JButton btnProcesar = new JButton("Procesar Devolución");

        txtArticulo.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDerecho.add(new JLabel("Proveedor:"), gbc);

        gbc.gridy = 1;
        panelDerecho.add(cboProveedor, gbc);
        
        gbc.gridy = 2;
        panelDerecho.add(new JLabel("Artículo seleccionado:"), gbc);

        gbc.gridy = 3;
        panelDerecho.add(txtArticulo, gbc);

        gbc.gridy = 4;
        panelDerecho.add(new JLabel("Cantidad a Devolver:"), gbc);

        gbc.gridy = 5;
        panelDerecho.add(txtCantidadRecibida, gbc);

        gbc.gridy = 6;
        gbc.weighty = 1.0;
        panelDerecho.add(Box.createVerticalGlue(), gbc);

        gbc.weighty = 0.0;
        gbc.gridy = 7;
        panelDerecho.add(btnModificar, gbc);

        gbc.gridy = 8;
        panelDerecho.add(btnProcesar, gbc);

        mainPanel.add(panelCentral, BorderLayout.CENTER);
        mainPanel.add(panelDerecho, BorderLayout.EAST);
        
        /* DevolucionController controller = new DevolucionController(new DevolucionDAO(), tabla, cboProveedor, txtArticulo, txtCantidadRecibida);
        btnModificar.addActionListener(controller.getModificarListener());      
        btnProcesar.addActionListener(controller.getProcesarListener()); */

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
