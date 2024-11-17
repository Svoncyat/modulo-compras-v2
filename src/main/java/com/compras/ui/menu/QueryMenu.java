package com.compras.ui.menu;

import javax.swing.JMenu;

public class QueryMenu {
    JMenu consultas = new JMenu("Consultas");

        // Crear submenú de Consultar Artículos
        JMenuItem consultarArticulos = new JMenuItem("Consultar Artículos");
        consultarArticulos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioConsultas();
            }
        });

        // Agregar la opción al menú de consultas
        consultas.add(consultarArticulos);

        return consultas;
    }

    private void mostrarFormularioConsultas() {
        JFrame frame = new JFrame("Artículos");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Nombre del Artículo:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Stock del Artículo:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Descripción del Artículo:"));
        panel.add(new JTextField());

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 0));
        botones.add(new JButton("Agregar Artículo"));
        botones.add(new JButton("Eliminar Artículo"));
        botones.add(new JButton("Actualizar Artículo"));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Método principal para probar la clase
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menú de Consultas");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        ConsultasMenu consultasMenu = new ConsultasMenu();
        menuBar.add(consultasMenu.menuConsultas());

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

