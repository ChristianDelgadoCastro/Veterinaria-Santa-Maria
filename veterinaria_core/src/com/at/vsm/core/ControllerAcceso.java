/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

import com.at.vsm.db.ConexionMySQL;
import com.at.vsm.model.Cliente;
import com.at.vsm.model.Empleado;
import com.at.vsm.model.Persona;
import com.at.vsm.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author david
 */
public class ControllerAcceso {
    //Usuario u
    public Cliente accederCliente(String nombre, String contrasenia) throws Exception{

        //1. Generar la consulta
        String query = "select * from v_clientes_activos where nombreUsuario = ? and contrasenia = ?"
                + "AND estatus = 1 AND (token IS NULL OR token = '') and rol = 'Cliente' ";
        
        //2. Establecer la conexión a la base de datos
        ConexionMySQL objConn = new ConexionMySQL();
        
        //3. Abrimos la conexión
        Connection conn = objConn.open();
        
        //4. Preparar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //5. Enviar los parámetros
        pstmt.setString(1, nombre);
        pstmt.setString(2, contrasenia);
        
        //6. Ejecutar la consulta y recibir los datos de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        //7. Tratar los datos de retorno
        Cliente c = null;
        if (rs.next()){
            c = fillCliente(rs);
            guardarToken(c.getUsuario());
        }
        
        //8. Cerrar los elementos de la conexión
        rs.close();
        pstmt.close();
        conn.close();
        objConn.close();
        return c;
    }
    
    private Cliente fillCliente(ResultSet rs) throws Exception{
        
        //Objeto de tipo cliente
        Cliente c = new Cliente();
        
        //Objeto de tipo persona
        Persona p = new Persona();
        
        //Objeto de tipo Usuario
        Usuario u = new Usuario();
        
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
        u = new Usuario();
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setRol(rs.getString("rol"));
        u.setToken();
        u.setDateLastToken(rs.getString("dateLastToken"));
        
        //Datos de cliente
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNumeroUnico(rs.getString("numeroUnico"));
        c.setEstatus(rs.getInt("estatus"));
        
        c.setPersona(p);
        c.setUsuario(u);
        //devolvemos a todos los clientes
        return c;
    }
    
    public Empleado accederEmpleado(String nombre, String contrasenia) throws Exception{

        //1. Generar la consulta
        String query = "select * from v_empleados_activos where nombreUsuario = ? and contrasenia = ?" 
                     + "AND estatus = 1 AND (token IS NULL OR token = '') and rol = 'Empleado';";
        
        //2. Establecer la conexión a la base de datos
        ConexionMySQL objConn = new ConexionMySQL();
        
        //3. Abrimos la conexión
        Connection conn = objConn.open();
        
        //4. Preparar la sentencia
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //5. Enviar los parámetros
        pstmt.setString(1, nombre);
        pstmt.setString(2, contrasenia);
        
        //6. Ejecutar la consulta y recibir los datos de la consulta
        ResultSet rs = pstmt.executeQuery();
        
        //7. Tratar los datos de retorno
        Empleado e = null;
        if (rs.next()){
            e = fillEmpleado(rs);
            guardarToken(e.getUsuario());
        }
        
        //8. Cerrar los elementos de la conexión
        rs.close();
        pstmt.close();
        conn.close();
        objConn.close();
        return e;
    }
    
    private Empleado fillEmpleado(ResultSet rs) throws Exception{
        
        //Objeto de tipo cliente
        Empleado e = new Empleado();
        
        //Objeto de tipo persona
        Persona p = new Persona();
        
        //Objeto de tipo Usuario
        Usuario u = new Usuario();
        
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
        
        //Datos del usuario
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setRol(rs.getString("rol"));
        u.setToken();
        u.setDateLastToken(rs.getString("dateLastToken"));
        
        //Datos de cliente
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroEmpleado(rs.getString("numeroEmpleado"));
        e.setPuesto(rs.getString("puesto"));
        e.setEstatus(rs.getInt("estatus"));
        
        e.setPersona(p);
        e.setUsuario(u);
        //devolvemos a todos los clientes
        return e;
    }
    
    //Vamos a generarle el pinche token alv
    public void guardarToken(Usuario u) throws Exception{
        
        //Generamos la query obteniendo el token que se genera en el modelo
        String sql = "UPDATE usuario SET token = '"+ u.getToken() +"', dateLastToken = NOW() WHERE idUsuario = " +u.getIdUsuario() + ";";
        
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareCall(sql);
        
        pstmt.execute();
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
    
    //lo eliminamos siempre que el usuario se salga del sistema
    public void eliminarToken(Usuario u) throws Exception{
        String sql = "UPDATE usuario SET token = '' WHERE idUsuario = " + u.getIdUsuario() + ";";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareCall(sql);
        pstmt.execute();
        pstmt.close();
        connMySQL.close();
    }
    
    public boolean validarToken(String token) throws Exception{
        boolean respuesta = false;
        
        String sql = "SELECT * FROM v_empleados_activos where token = '" + token + "';";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        if (rs.next()){
            respuesta = true;
        }
        
        stmt.close();
        conn.close();
        connMySQL.close();
        
        return respuesta;
    }
    
}
