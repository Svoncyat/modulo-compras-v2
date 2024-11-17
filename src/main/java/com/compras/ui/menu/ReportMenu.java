package com.compras.ui.menu;

import javax.swing.JMenu;

public class ReportMenu {
    public JMenu menuReportes() {
        JMenu reportes = new JMenu("Reportes");

        JMenu stockReportes = new JMenu("Stock");
        JMenu ordenesReportes = new JMenu("Ordenes");

        reportes.add(stockReportes);
        reportes.add(ordenesReportes);

        return reportes;
    }
}
