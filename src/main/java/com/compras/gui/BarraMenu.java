package com.compras.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.compras.gui.menus.*;

public class BarraMenu extends JFrame {
    public JMenuBar inicializarBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        SeguridadMenu seguridad = new SeguridadMenu();
        MaestrosMenu maestros = new MaestrosMenu();
        ConsultasMenu consultas = new ConsultasMenu();
        ReportesMenu reportes = new ReportesMenu();

        menuBar.add(seguridad.menuSeguridad());
        menuBar.add(maestros.menuMaestros());
        menuBar.add(consultas.menuConsultas());
        menuBar.add(reportes.menuReportes());

        return menuBar;
    }
}