package com.compras.model;

import java.util.HashMap;
import java.util.Map;

public class Ingreso {
    private int id;
    private OrdenCompra ordenCompra;
    private Map<Integer, Integer> cantidadesRecibidas;

    public Ingreso(int id, OrdenCompra ordenCompra) {
        this.id = id;
        this.ordenCompra = ordenCompra;
        this.cantidadesRecibidas = new HashMap<>();
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

    public void setCantidadRecibida(int articuloId, int cantidad) {
        cantidadesRecibidas.put(articuloId, cantidad);
    }

    public int getCantidadRecibida(int articuloId) {
        return cantidadesRecibidas.getOrDefault(articuloId, 0);
    }

    public Map<Integer, Integer> getCantidadesRecibidas() {
        return cantidadesRecibidas;
    }
}
