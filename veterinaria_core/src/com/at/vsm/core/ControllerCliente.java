/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

import com.at.vsm.model.Cliente;
import com.at.vsm.db.ConexionMySQL;
import com.at.vsm.model.Persona;
import com.at.vsm.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class ControllerCliente {
    //método para insertar al cliente
    public int insert(Cliente c) throws Exception{
        
        //Escribimos la consulta a ejecutar
        String sql = "{CALL insertarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?,"//datos personales
                                        + "?, ?, ?,"//datos de usuario
                                        + "?, ?, ?, ?)}";//datos de salida
        
        //guardamos los id's generados
        int idClienteGenerado = -1;
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        String numeroUnicoGenerado = "";
        
        //Creamos un objeto que conecta a mysql
        ConexionMySQL connMySql = new ConexionMySQL();
        
        //abrimos la conexión a la base de datos
        Connection conn = connMySql.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parametros en orden como los pide el procedimiento almacenado
        //1. Datos Personales
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoP());
        cstmt.setString(3, c.getPersona().getApellidoM());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getDomicilio());
        cstmt.setString(6, c.getPersona().getTelefono());
        cstmt.setString(7, c.getPersona().getRfc());
        cstmt.setString(8, c.getPersona().getEmail());
        cstmt.setString(9, c.getPersona().getFotografia());
        
        //2. Datos de Usuario
        cstmt.setString(10, c.getUsuario().getNombreUsuario());
        cstmt.setString(11, c.getUsuario().getContrasenia());
        cstmt.setString(12, c.getUsuario().getRol());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(13, Types.INTEGER);
        cstmt.registerOutParameter(14, Types.INTEGER);
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.VARCHAR);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Recuperamos los ID'S generados
        idClienteGenerado = cstmt.getInt(13);
        idPersonaGenerado = cstmt.getInt(14);
        idUsuarioGenerado = cstmt.getInt(15);
        numeroUnicoGenerado = cstmt.getString(16);
        
        c.setIdCliente(idClienteGenerado);
        c.getPersona().setIdPersona(idPersonaGenerado);
        c.getUsuario().setIdUsuario(idUsuarioGenerado);
        c.setNumeroUnico(numeroUnicoGenerado);
        
        //ya que se realizó toda la inserción, se cierra la conexión a la base de datos
        //con el método close
        cstmt.close();
        connMySql.close();
        
        //regresamos el id del cliente generado
        return idClienteGenerado;
    }
    
    public void update(Cliente c) throws Exception{
        
        //Definimos la consulta que vamos a ejecutar
        String sql = "{CALL actualizarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?,"//Datos personales
                                           + "?, ?,"//Datos de usuario
                                           + "?, ?)}";//id's afectados
        
        //Creamos un objeto que conecta a mysql
        ConexionMySQL connMySql = new ConexionMySQL();
        
        //abrimos la conexión a la base de datos
        Connection conn = connMySql.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parametros en orden como los pide el procedimiento almacenado
        //1. Datos Personales
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoP());
        cstmt.setString(3, c.getPersona().getApellidoM());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getDomicilio());
        cstmt.setString(6, c.getPersona().getTelefono());
        cstmt.setString(7, c.getPersona().getRfc());
        cstmt.setString(8, c.getPersona().getEmail());
        cstmt.setString(9, c.getPersona().getFotografia());
        
        //2. Datos de Usuario
        cstmt.setString(10, c.getUsuario().getNombreUsuario());
        cstmt.setString(11, c.getUsuario().getContrasenia());
        
        //3. ID'S Afectadas
        cstmt.setInt(12, c.getPersona().getIdPersona());
        cstmt.setInt(13, c.getUsuario().getIdUsuario());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //cerramos conexión
        cstmt.close();
        connMySql.close();  
    }
    
    public int delete(int idCliente) throws Exception{
        
        //Declaramos una respuesta
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE cliente SET estatus = 0 WHERE idCliente = "+idCliente;
        
        //objeto para la conexión a MYSQL
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //abrir la conexión
        Connection conn = connMySQL.open();
        
        //objeto para ejecutar el delete
        PreparedStatement pstmt = conn.prepareCall(sql);
        
        //Como es eliminación lógica, se actualiza el estatus a 0, por lo tanto;
        //utilizamos un executeUpdate, ya que utilizamos un update en mysql.
        //Si la función es correcta, devuelve 1, si no, devuelve 0, que es incorrecta
        respuesta = pstmt.executeUpdate();
               
        //se cierra la conexión a la base de datos
        connMySQL.close();
        
        //devuelve la respuesta
        return respuesta;
    }
    
    //hace lo mismo para eliminar pero para reactivarlos xD
    public int reactive(int idCliente) throws Exception{
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE cliente SET estatus = 1 WHERE idCliente = "+idCliente;
        
        //objeto para la conexión a MYSQL
        ConexionMySQL connMySQL = new ConexionMySQL();
        
        //abrir la conexión
        Connection conn = connMySQL.open();
        
        //objeto para ejecutar el delete
        PreparedStatement pstmt = conn.prepareCall(sql);
        
        //Como es eliminación lógica, se actualiza el estatus a 0, por lo tanto;
        //utilizamos un executeUpdate, ya que utilizamos un update en mysql.
        //Si la función es correcta, devuelve 1, si no, devuelve 0, que es incorrecta
        respuesta = pstmt.executeUpdate();
               
        //se cierra la conexión a la base de datos
        connMySQL.close();
        
        return respuesta;
    }
    
    //getAll para clientes activos
    public List<Cliente> getAllActives(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_clientes_activos";
        
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
        List<Cliente> clientes = new ArrayList<>();
        while (rs.next())
            clientes.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return clientes;
    }
    
    //getAll para clientes inactivos
    public List<Cliente> getAllInactives(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_clientes_inactivos";
        
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
        List<Cliente> clientes = new ArrayList<>();
        while (rs.next())
            clientes.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return clientes;
    }
    
    private Cliente fill(ResultSet rs) throws Exception{
        
        //Objeto de tipo cliente
        Cliente c = new Cliente();
        
        //Objeto de tipo persona
        Persona p = new Persona();
        
        //Datos personales
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidoP(rs.getString("apellidoP"));
        p.setApellidoM(rs.getString("apellidoM"));
        p.setGenero(rs.getString("genero"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setTelefono(rs.getString("telefono"));
        p.setRfc(rs.getString("rfc"));
        p.setEmail(rs.getString("email"));
        p.setFotografia(rs.getString("fotografia"));
        
        //Datos del usuario
        c.setUsuario(new Usuario());
        c.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        c.getUsuario().setNombreUsuario(rs.getString("nombreUsuario"));
        c.getUsuario().setContrasenia(rs.getString("contrasenia"));
        c.getUsuario().setRol(rs.getString("rol"));
        c.getUsuario().setToken();
        c.getUsuario().setDateLastToken(rs.getString("dateLastToken"));
        
        //Datos de cliente
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNumeroUnico(rs.getString("numeroUnico"));
        c.setEstatus(rs.getInt("estatus"));
        
        c.setPersona(p);
        //devolvemos a todos los clientes
        return c;
    }
}
