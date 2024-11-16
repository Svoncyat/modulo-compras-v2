package com.compras.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BarraMenu extends JFrame {
    public JMenuBar inicializarBarraMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu seguridad = new JMenu("Seguridad");
        JMenu maestros = new JMenu("Maestros");
        JMenu consultas = new JMenu("Consultas");
        JMenu reportes = new JMenu("Reportes");

        //Submenus para seguridad
        JMenu usuarios = new JMenu("Usuarios");
        JMenuItem agregarusuario = new JMenuItem("Agregrar usuario");
        JMenuItem borrarusuario = new JMenuItem("Borrar usuario");
        JMenuItem restablecercontrasena = new JMenuItem("Restablecer contraseña");

        // Submenus para maestros
        JMenu articulos = new JMenu("Articulos");
        JMenuItem nombrearticulo = new JMenuItem("Nombre del articulo");
        JMenuItem stockarticulo = new JMenuItem("Stock de articulo");
        JMenuItem descripcionarticulo = new JMenuItem("Descripcion del articulo");
        JMenuItem agregararticulos = new JMenuItem("Agregar articulos");
        JMenuItem eliminararticulo = new JMenuItem("Eliminar articulo");

        JMenu proveedores = new JMenu("Proveedores");
        JMenuItem nombreproveedres = new JMenuItem("Nombre de proveedores");
        JMenuItem agregarproveedor = new JMenuItem("Agregrar proveedor");
        JMenuItem eliminarproveedor = new JMenuItem("Eliminar proveedor");

        JMenu compradores = new JMenu("Compradores");
        JMenuItem nombrecomprador = new JMenuItem("Nombre del comprador");
        JMenuItem agregarcomprador = new JMenuItem("Agregar comprador");
        JMenuItem eliminarcomprador = new JMenuItem("Eliminar comprador");

        JMenu transacciones = new JMenu("Transacciones");
        JMenuItem agregartransacciones = new JMenuItem("Agregar transacción");
        JMenuItem eliminartransacciones = new JMenuItem("Eliminar transacción");

        JMenu ordendecompra = new JMenu("Orden de compra");
        JMenuItem agregarordendecompra = new JMenuItem("Agregar orden de compra");
        JMenuItem eliminarordendecompra = new JMenuItem("Eliminar orden de compra");

        JMenu ingreso = new JMenu("Ingreso");
        JMenu devolucion = new JMenu("Devolución");

        //Submenus para consultas
        JMenu stock = new JMenu("Stock");
        JMenu ordenes = new JMenu("Ordenes");

        //Submenus para reportes
        JMenu stock01 = new JMenu("Stock");
        JMenu ordenes01 = new JMenu("Ordenes");

        // Opciones de barra de menu
        menuBar.add(seguridad);
        menuBar.add(maestros);
        menuBar.add(consultas);
        menuBar.add(reportes);

        //Opciones del menu seguridad

        seguridad.add(usuarios);
        usuarios.add(agregarusuario);
        usuarios.add(borrarusuario);
        usuarios.add(restablecercontrasena);

        //Opciones del menu de Maestros 

        //ARTICULOS 
        maestros.add(articulos);
        articulos.add(nombrearticulo);
        articulos.add(stockarticulo);
        articulos.add(descripcionarticulo);
        articulos.add(agregararticulos);
        articulos.add(eliminararticulo);

        //Proveedores
        maestros.add(proveedores);
        proveedores.add(nombreproveedres);
        proveedores.add(agregarproveedor);
        proveedores.add(eliminarproveedor);

        //Com
        maestros.add(compradores);
        compradores.add(nombrecomprador);
        compradores.add(agregarcomprador);
        compradores.add(eliminarcomprador);

        //TRANSACCIONES
        maestros.add(transacciones);
        transacciones.add(agregartransacciones);
        transacciones.add(eliminartransacciones);

        //ORDEN DE COMPRA
        maestros.add(ordendecompra);
        ordendecompra.add(agregarordendecompra);
        ordendecompra.add(eliminarordendecompra);

        //INGRESO
        maestros.add(ingreso);


        //DEVOLUCION
        maestros.add(devolucion);

        //Opciones del menu de consultas

        //STOCK
        consultas.add(stock);

        //ORDENES
        consultas.add(ordenes);

        //Opciones del menu de consultas

        //STOCK
        reportes.add(stock01);

        //ORDENES
        reportes.add(ordenes01);

        return menuBar;
    }
}
