package com.compras.ui;

import javax.swing.JMenuBar;

import com.compras.ui.menu.*;

public class MenuBar {
    public JMenuBar inicializarBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        SecurityMenu seguridad = new SecurityMenu();
        MasterMenu maestros = new MasterMenu();
        QueryMenu consultas = new QueryMenu();
        ReportMenu reportes = new ReportMenu();

        menuBar.add(seguridad.menuSeguridad());
        menuBar.add(maestros.menuMaestros());
        menuBar.add(consultas.menuConsultas());
        menuBar.add(reportes.menuReportes());

        return menuBar;
    }
}