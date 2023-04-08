/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.model;

/**
 *
 * @author david
 */
public class Cliente {
    int idCliente;
    String numeroUnico;
    int estatus;
    Persona persona;
    Usuario usuario;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
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

    public Cliente() {
    }

    public Cliente(int idCliente, String numeroUnico, int estatus, Persona persona, Usuario usuario) {
        this.idCliente = idCliente;
        this.numeroUnico = numeroUnico;
        this.estatus = estatus;
        this.persona = persona;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", numeroUnico=" + numeroUnico + ", estatus=" + estatus + ", persona=" + persona + ", usuario=" + usuario + '}';
    }
    
}
