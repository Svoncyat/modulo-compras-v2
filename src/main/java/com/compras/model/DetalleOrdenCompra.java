package com.compras.model;

public class DetalleOrdenCompra {
    private Articulo articulo;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    
    public DetalleOrdenCompra(Articulo articulo, int cantidad, double precioUnitario, double subtotal) {
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }
}
