package com.compras.gui.menus;

import javax.swing.JMenu;

public class ConsultasMenu {
    public JMenu menuConsultas() {
        JMenu consultas = new JMenu("Consultas");

        JMenu stockConsultas = new JMenu("Stock consultas");
        JMenu ordenesConsultas = new JMenu("Ordenes consultas");

        consultas.add(stockConsultas);
        consultas.add(ordenesConsultas);
        
        return consultas;
    }
}
