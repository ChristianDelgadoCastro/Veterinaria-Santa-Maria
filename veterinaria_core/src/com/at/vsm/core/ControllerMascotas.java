/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

import com.at.vsm.db.ConexionMySQL;
import com.at.vsm.model.Cliente;
import com.at.vsm.model.Mascota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class ControllerMascotas {
    //getAll para empleados activos
    public List<Mascota> getAll(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM mascota;";
        
        //objeto para conectar a la base de datos
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //abrimos la conexión
        Connection conn = connMySQL.open();
        
        //Con este objeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        //Aquí guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();
        
        //los resultados de la consulta lo mandamos a un objeto de tipo clientes 
        //y mandarlo a un arraylist (una lista para los compas)
        List<Mascota> mascotas = new ArrayList<>();
        while (rs.next())
            mascotas.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return mascotas;
    }
    
    private Mascota fill(ResultSet rs) throws Exception{
        
        //Objeto de tipo mascota
        Mascota m = new Mascota();
        
        //Objeto de tipo cliente
        Cliente c = new Cliente();
        
        //Datos de la mascota
        m.setIdMascota(rs.getInt("idMascota"));
        m.setNumeroUnico(rs.getString("numeroUnico"));
        m.setNombre(rs.getString("nombre"));
        m.setEspecie(rs.getString("especie"));
        m.setRaza(rs.getString("raza"));
        m.setGenero(rs.getString("genero"));
        m.setEdad(rs.getString("edad"));
        m.setPeso(rs.getDouble("peso"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setEstatus(rs.getInt("estatus"));
                
        //Datos de cliente
        c.setIdCliente(rs.getInt("idCliente"));
                
        m.setCliente(c);
        //devolvemos a todos los clientes
        return m;
    }
}
