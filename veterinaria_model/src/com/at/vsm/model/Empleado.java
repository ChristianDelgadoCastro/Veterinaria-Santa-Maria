/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.model;

/**
 *
 * @author david
 */
public class Empleado {
    int idEmpleado;
    String numeroEmpleado;
    String puesto;
    int estatus;
    Persona persona;
    Usuario usuario;

    public Empleado(int idEmpleado, String numeroEmpleado, String puesto, int estatus, Persona persona, Usuario usuario) {
        this.idEmpleado = idEmpleado;
        this.numeroEmpleado = numeroEmpleado;
        this.puesto = puesto;
        this.estatus = estatus;
        this.persona = persona;
        this.usuario = usuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empleado() {
    }

    @Override
    public String toString() {
        return "Empleado{" + "idEmpleado=" + idEmpleado + ", numeroEmpleado=" + numeroEmpleado + ", puesto=" + puesto + ", estatus=" + estatus + ", persona=" + persona + ", usuario=" + usuario + '}';
    }
    
}
