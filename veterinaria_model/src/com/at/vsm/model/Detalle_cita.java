/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.model;

/**
 *
 * @author david
 */
public class Detalle_cita {
    
    int idDetalle_Cita;
    String nombreServicio;
    String nombreTratamiento;
    float costoTratamiento;
    String nombreProducto;
    Cita cita;

    public int getIdDetalle_Cita() {
        return idDetalle_Cita;
    }

    public void setIdDetalle_Cita(int idDetalle_Cita) {
        this.idDetalle_Cita = idDetalle_Cita;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public float getCostoTratamiento() {
        return costoTratamiento;
    }

    public void setCostoTratamiento(float costoTratamiento) {
        this.costoTratamiento = costoTratamiento;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Detalle_cita() {
    }

    public Detalle_cita(int idDetalle_Cita, String nombreServicio, String nombreTratamiento, float costoTratamiento, String nombreProducto, Cita cita) {
        this.idDetalle_Cita = idDetalle_Cita;
        this.nombreServicio = nombreServicio;
        this.nombreTratamiento = nombreTratamiento;
        this.costoTratamiento = costoTratamiento;
        this.nombreProducto = nombreProducto;
        this.cita = cita;
    }

    @Override
    public String toString() {
        return "Detalle_cita{" + "idDetalle_Cita=" + idDetalle_Cita + ", nombreServicio=" + nombreServicio + ", nombreTratamiento=" + nombreTratamiento + ", costoTratamiento=" + costoTratamiento + ", nombreProducto=" + nombreProducto + ", cita=" + cita + '}';
    }
    
}
