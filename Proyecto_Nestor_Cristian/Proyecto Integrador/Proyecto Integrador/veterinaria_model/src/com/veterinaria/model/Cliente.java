package com.veterinaria.model;

public class Cliente {
     
    private int id;
    private String numeroUnico;
    private String correo;
    private int estatus;
    private Persona persona;
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public String getCorreo() {
        return correo;
    }

    public int getEstatus() {
        return estatus;
    }

    public Persona getPersona() {
        return persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
   
}
