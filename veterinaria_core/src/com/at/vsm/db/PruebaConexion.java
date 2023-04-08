/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.at.vsm.db;

import java.sql.Connection;

/**
 *
 * @author david
 */
public class PruebaConexion {
    public static void main(String[] args) {
        ConexionMySQL conexion = new ConexionMySQL();
        try {
            Connection conn = conexion.open();
            if (conn != null) {
                System.out.println("Conexion exitosa!");
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo establecer la conexión");
        }
    }
}
