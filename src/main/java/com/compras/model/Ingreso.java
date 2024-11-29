package com.compras.model;

public class Ingreso {
    private int id;
    private OrdenCompra ordenCompra;
    private int cantidadRecibida;

    public Ingreso(int id, OrdenCompra ordenCompra, int cantidadRecibida) {
        this.id = id;
        this.ordenCompra = ordenCompra;
        this.cantidadRecibida = cantidadRecibida;
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

    public int getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(int cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

}
