package com.compras.ui;

import com.compras.config.DatabaseConfig;
import com.compras.dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int PADDING = 10;
    private static final String TITLE = "Autenticación";
    
    private final JTextField tfUsuario;
    private final JPasswordField tfContrasena;
    private final JButton btnIngresar;
    private final UsuarioDAO usuarioDAO;
    
    public LoginFrame(DatabaseConfig conexionDB) {
        super(TITLE);
        this.usuarioDAO = new UsuarioDAO(conexionDB);
        
        // Inicializar componentes
        tfUsuario = new JTextField(20); // 15 es el tamaño del campo de texto lo usamos para el metodo packet()
        tfContrasena = new JPasswordField(20);
        btnIngresar = new JButton("Ingresar");
        
        initializeUI();
        setupListeners();
        setupWindowProperties();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(PADDING, PADDING));
        
        // Panel de bienvenida
        add(createWelcomePanel(), BorderLayout.NORTH);
        
        // Panel principal de autenticación
        add(createAuthPanel(), BorderLayout.CENTER);
        
        // Panel de botones
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 5));
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        JLabel lblBienvenida1 = new JLabel("Bienvenido al Módulo Compras", SwingConstants.CENTER);
        JLabel lblBienvenida2 = new JLabel("Autentíquese para continuar", SwingConstants.CENTER);
        
        // Estilo para las etiquetas
        lblBienvenida1.setFont(new Font("Dialog", Font.BOLD, 14));
        lblBienvenida2.setFont(new Font("Dialog", Font.PLAIN, 12));
        
        panel.add(lblBienvenida1);
        panel.add(lblBienvenida2);
        return panel;
    }
    
    private JPanel createAuthPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new CompoundBorder(
            new EtchedBorder(),
            new EmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(tfUsuario, gbc);
        
        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(tfContrasena, gbc);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(new EmptyBorder(0, PADDING, PADDING, PADDING));
        panel.add(btnIngresar);
        return panel;
    }
    
    private void setupListeners() {
        // Action Listener para el botón
        btnIngresar.addActionListener(e -> autenticar());
        
        // Key Listeners para los campos de texto
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autenticar();
                }
            }
        };
        
        tfUsuario.addKeyListener(enterKeyAdapter);
        tfContrasena.addKeyListener(enterKeyAdapter);
        
        // Focus Listener para mejorar la UX
        tfUsuario.addFocusListener(new SelectAllFocusListener());
        tfContrasena.addFocusListener(new SelectAllFocusListener());
    }
    
    private void setupWindowProperties() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        
        // Establecer el campo de usuario como foco inicial
        SwingUtilities.invokeLater(() -> tfUsuario.requestFocusInWindow()); // Se ejecuta luego de que se haya creado el frame
    }
    
    private void autenticar() {
        String usuario = tfUsuario.getText().trim();
        String contrasena = new String(tfContrasena.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor, complete todos los campos");
            return;
        }
        
        try {
            if (usuarioDAO.validarCredenciales(usuario, contrasena)) {
                mostrarExito();
                abrirMenuPrincipal();
            } else {
                mostrarError("Usuario o contraseña incorrectos");
                tfContrasena.setText("");
                tfContrasena.requestFocusInWindow();
            }
        } catch (Exception ex) {
            mostrarError("Error al intentar autenticar: " + ex.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void mostrarExito() {
        JOptionPane.showMessageDialog(
            this,
            "Autenticación exitosa",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void abrirMenuPrincipal() {
        SwingUtilities.invokeLater(() -> {
            new MainFrame(usuarioDAO).setVisible(true);
            dispose();
        });
    }
}

// Clase auxiliar para seleccionar todo el texto al obtener el foco
class SelectAllFocusListener extends FocusAdapter {
    @Override
    public void focusGained(FocusEvent e) {
        if (e.getComponent() instanceof JTextComponent) {
            ((JTextComponent) e.getComponent()).selectAll();
        }
    }
}