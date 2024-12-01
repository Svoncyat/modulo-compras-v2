package com.compras.model;

public class Articulo {
    private int id;
    private String nombre;
    private int stock;
    private String descripcion;

    public Articulo(int id, String nombre, int stock, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
