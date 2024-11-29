package com.compras.model;

public class Devolucion {
    private int id;
    private OrdenCompra ordenCompra;
    private Proveedor proveedor;
    private int cantidadDevuelta;

    public Devolucion(int id, OrdenCompra ordenCompra, Proveedor proveedor, int cantidadDevuelta) {
        this.id = id;
        this.ordenCompra = ordenCompra;
        this.proveedor = proveedor;
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(int cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }
}
