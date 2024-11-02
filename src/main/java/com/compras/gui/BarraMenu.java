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

        articulos.add(articulos);


        return menuBar;
    }
}
