package com.veterinaria.model;

public class Producto {

    int idProducto;
    String nombre;
    String marca;
    int estatus;
    float precioUso;

    public Producto() {
    }

    
    public Producto(String nombre, String marca, int estatus, float precioUso) {
        this.nombre = nombre;
        this.marca = marca;
        this.estatus = estatus;
        this.precioUso = precioUso;
    }

    public Producto(int idProducto, String nombre, String marca, int estatus, float precioUso) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.estatus = estatus;
        this.precioUso = precioUso;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public float getPrecioUso() {
        return precioUso;
    }

    public void setPrecioUso(float precioUso) {
        this.precioUso = precioUso;
    }
}
