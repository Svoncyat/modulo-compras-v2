package com.compras;

import javax.swing.*;
import java.awt.*;

import com.compras.db.ConexionDB;
import com.compras.gui.AutenticacionMenu;

public class Main extends JFrame {

    public static void main(String[] args) {
        ConexionDB conexion = new ConexionDB();
        if (conexion.conectar() != null) {
            AutenticacionMenu app = new AutenticacionMenu(conexion);
            app.setVisible(true);
            app.setSize(315, 175);
            app.setDefaultCloseOperation(EXIT_ON_CLOSE);
            app.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos");
        }
    }
}