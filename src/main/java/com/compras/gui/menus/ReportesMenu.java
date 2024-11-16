package com.compras.gui.menus;

import javax.swing.JMenu;

public class ReportesMenu {
    public JMenu menuReportes() {
        JMenu reportes = new JMenu("Reportes");

        JMenu stockReportes = new JMenu("Stock");
        JMenu ordenesReportes = new JMenu("Ordenes");

        reportes.add(stockReportes);
        reportes.add(ordenesReportes);

        return reportes;
    }
}
