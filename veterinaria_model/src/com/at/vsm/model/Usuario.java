/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.model;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author david
 */
public class Usuario {
    
    int idUsuario;
    String nombreUsuario;
    String contrasenia;
    String rol;
    String token;
    String dateLastToken;

    public Usuario(int idUsuario, String nombreUsuario, String contrasenia, String rol, String token, String dateLastToken) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.token = token;
        this.dateLastToken = dateLastToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(){
        String u = this.nombreUsuario;
        String p = this.contrasenia;
        String k = new Date().toString();
        String x = (DigestUtils.sha256Hex(u + ";" + p + ";" + k));
        this.token = x;
    }

    public String getDateLastToken() {
        return dateLastToken;
    }

    public void setDateLastToken(String dateLastToken) {
        this.dateLastToken = dateLastToken;
    }
    

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", contrasenia=" + contrasenia + ", rol=" + rol + '}';
    }
    
}
