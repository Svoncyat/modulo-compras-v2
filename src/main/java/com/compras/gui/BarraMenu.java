package com.compras.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BarraMenu extends JFrame {
    public JMenuBar iniciar() {
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

        //submenus para maestros

        JMenu articulos = new JMenu("Articulos");
        JMenuItem nombrearticulo = new JMenuItem("Nombre del articulo");
        JMenuItem stockarticulo = new JMenuItem("Stock de articulo");
        JMenuItem agregararticulos = new JMenuItem("Agregar articulos");
        JMenuItem eliminararticulo = new JMenuItem("Elimianr articulo");

        JMenu proveedores = new JMenu("Proveedores");
        JMenuItem nombreproveedres = new JMenuItem("Nombre de proveedores");



        JMenu compradores = new JMenu("Compradores");
        JMenuItem nombrecomprador = new JMenuItem("Nombre del comprador");



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
        articulos.add(agregararticulos);
        articulos.add(eliminararticulo);

        //PROVEEDORES
        maestros.add(proveedores);
        proveedores.add(nombreproveedres);

        //COMPRADORES 
        maestros.add(compradores);
        compradores.add(nombrecomprador);
        



        return menuBar;
    }
}
