/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.model;

/**
 *
 * @author david
 */
public class Cita {
    int idCita;
    String fechaCita;
    String horaCita;
    int estatus;
    Cliente cliente;
    Empleado empleado;

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cita(int idCita, String fechaCita, String horaCita, int estatus, Cliente cliente, Empleado empleado) {
        this.idCita = idCita;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.estatus = estatus;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    public Cita() {
    }

    @Override
    public String toString() {
        return "Cita{" + "idCita=" + idCita + ", fechaCita=" + fechaCita + ", horaCita=" + horaCita + ", estatus=" + estatus + ", cliente=" + cliente + ", empleado=" + empleado + '}';
    }
    
    
}
