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

    public Historialmedico() {
    }

    public Historialmedico(int idHistorial, Mascota mascota) {
        this.idHistorial = idHistorial;
        this.mascota = mascota;
    }

    @Override
    public String toString() {
        return "Historialmedico{" + "idHistorial=" + idHistorial + ", mascota=" + mascota + '}';
    }
    
}
