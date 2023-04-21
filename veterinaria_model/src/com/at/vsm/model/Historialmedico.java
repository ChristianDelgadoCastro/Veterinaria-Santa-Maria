/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.model;

/**
 *
 * @author david
 */
public class Historialmedico {
    
    int idHistorial;
    Mascota mascota;
    String fecha;
    String diagnostico;

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Historialmedico() {
    }

    public Historialmedico(int idHistorial, Mascota mascota, String fecha, String diagnostico) {
        this.idHistorial = idHistorial;
        this.mascota = mascota;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "Historialmedico{" + "idHistorial=" + idHistorial + ", mascota=" + mascota + ", fecha=" + fecha + ", diagnostico=" + diagnostico + '}';
    }
    
}
