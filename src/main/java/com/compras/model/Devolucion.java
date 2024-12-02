package com.compras.model;

import java.util.Date;

public class Devolucion {
    private int id;
    private OrdenCompra ordenCompra;
    private Proveedor proveedor;
    private Articulo articulo;
    private int cantidadDevuelta;
    private Date fechaDevolucion;

    public Devolucion(int id, OrdenCompra ordenCompra, Proveedor proveedor, 
                     Articulo articulo, int cantidadDevuelta) {
        this.id = id;
        this.ordenCompra = ordenCompra;
        this.proveedor = proveedor;
        this.articulo = articulo;
        this.cantidadDevuelta = cantidadDevuelta;
        this.fechaDevolucion = new Date();
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

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(int cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getArticuloId() {
        return articulo != null ? articulo.getId() : 0;
    }

}
