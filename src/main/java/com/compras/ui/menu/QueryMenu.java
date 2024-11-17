package com.compras.ui.menu;

import javax.swing.JMenu;

public class QueryMenu {
    public JMenu menuConsultas() {
        JMenu consultas = new JMenu("Consultas");

        JMenu stockConsultas = new JMenu("Stock consultas");
        JMenu ordenesConsultas = new JMenu("Ordenes consultas");

        consultas.add(stockConsultas);
        consultas.add(ordenesConsultas);
        
        return consultas;
    }
}
