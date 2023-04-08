package com.veterinaria.model;

public class Mascota {
    
    int idMascota;
    String nombre;
    String raza;
    String edad;
    float peso;
    int estatus;
    String descripcion;
    int idCliente;

    public Mascota() {
    }

    public Mascota(String nombre, String raza, String edad, float peso, int estatus, String descripcion, int idCliente) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.estatus = estatus;
        this.descripcion = descripcion;
        this.idCliente = idCliente;
    }

    public Mascota(int idMascota, String nombre, String raza, String edad, float peso, int estatus, String descripcion, int idCliente) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.estatus = estatus;
        this.descripcion = descripcion;
        this.idCliente = idCliente;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    
}
