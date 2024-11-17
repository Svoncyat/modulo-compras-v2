package com.compras;

import javax.swing.*;

import com.compras.config.DatabaseConfig;
import com.compras.ui.LoginFrame;

public class Application extends JFrame {

    public static void main(String[] args) {
        DatabaseConfig conexion = new DatabaseConfig();
        if (conexion.conectar() != null) {  
            LoginFrame app = new LoginFrame(conexion);
            app.setVisible(true);
            app.setDefaultCloseOperation(EXIT_ON_CLOSE);
            app.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
    }
}