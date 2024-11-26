package com.compras.ui.menu;

import javax.swing.JMenuBar;
import javax.swing.JFrame;

import com.compras.dao.UsuarioDAO;

public class MenuBar {
    private final UsuarioDAO usuarioDAO;
    private final JFrame parentFrame;

    public MenuBar(UsuarioDAO usuarioDAO, JFrame parentFrame) {
        this.usuarioDAO = usuarioDAO;
        this.parentFrame = parentFrame;
    }

    public JMenuBar inicializarBarraMenu() {
        JMenuBar menuBar = new JMenuBar();

        SecurityMenu seguridad = new SecurityMenu(usuarioDAO, parentFrame);
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