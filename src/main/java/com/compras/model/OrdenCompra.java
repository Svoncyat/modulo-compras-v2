package com.compras.model;

import java.util.*;

public class OrdenCompra {
    private int id;
    private String numero;
    private Date fecha;
    private Proveedor proveedor;
    private Comprador comprador;
    private String estado;
    private double precioUnitario;
    private List<DetalleOrdenCompra> detalleOrdenCompra;

    public OrdenCompra(int id, String numero, Date fecha, Proveedor proveedor, Comprador comprador, String estado,
            double precioUnitario) {
        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.comprador = comprador;
        this.estado = estado;
        this.precioUnitario = precioUnitario;
        this.detalleOrdenCompra = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public List<DetalleOrdenCompra> getDetalleOrdenCompra() {
        return detalleOrdenCompra;
    }

    public void setDetalleOrdenCompra(List<DetalleOrdenCompra> detalleOrdenCompra) {
        this.detalleOrdenCompra = detalleOrdenCompra;
    }

    @Override
    public String toString() {
        return numero + " - " + proveedor.getNombre() + " (" + fecha + ")";
    }
}
