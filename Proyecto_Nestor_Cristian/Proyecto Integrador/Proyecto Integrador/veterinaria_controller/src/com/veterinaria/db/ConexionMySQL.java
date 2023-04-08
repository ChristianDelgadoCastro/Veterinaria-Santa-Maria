package com.veterinaria.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionMySQL {
    
    // Aqui guardamos y gestionamos la conexion con MySQL
    Connection conexion;
    
    /**
     * Realizamos la conexion con la Base de Datos y devolvemos
     * un objeto de tipo Connection.
     */
    public Connection open() throws Exception{
        
        //Establecemos el driver de MySQL
        String drive = "com.mysql.jdbc.Driver";
        
        //Establecemos la ruta de conexion
        String url = "jdbc:mysql://localhost:3307/veterinaria";
        
        //Establecemos el usuario y la contraseña
        String usuario = "root";
        String contrasenia = "Tontolandia1";
        
        //Registramos el Driver
        Class.forName(drive);
        
        //Abrimos la conexion con MySQL
        conexion = DriverManager.getConnection(url, usuario, contrasenia);
        
        //Devolvemos el objeto que mantiene la conexion con MySQL
        return conexion;
    }
    
    /**
     * Cerramos la conexion con MySQL.
     */
    public void close(){
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}