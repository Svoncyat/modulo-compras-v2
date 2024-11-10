package com.compras.models;

public class Usuarios {
    private int id;
    private String usuario;
    private String contrasena;
    private boolean esAdmin;
    
    public Usuarios(int id, String usuario, String contrasena, boolean esAdmin) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.esAdmin = esAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    
    
}
