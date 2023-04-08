/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.model;

/**
 *
 * @author david
 */
public class Mascota {
    
    int idMascota;
    String numeroUnico;
    String nombre;
    String especie;
    String raza;
    String genero;
    String edad;
    double peso;
    String descripcion;
    int estatus;
    Cliente cliente;

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mascota() {
    }

    public Mascota(int idMascota, String numeroUnico, String nombre, String especie, String raza, String genero, String edad, double peso, String descripcion, int estatus, Cliente cliente) {
        this.idMascota = idMascota;
        this.numeroUnico = numeroUnico;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.genero = genero;
        this.edad = edad;
        this.peso = peso;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Mascota{" + "idMascota=" + idMascota + ", numeroUnico=" + numeroUnico + ", nombre=" + nombre + ", especie=" + especie + ", raza=" + raza + ", genero=" + genero + ", edad=" + edad + ", peso=" + peso + ", descripcion=" + descripcion + ", estatus=" + estatus + ", cliente=" + cliente + '}';
    }
    
}
