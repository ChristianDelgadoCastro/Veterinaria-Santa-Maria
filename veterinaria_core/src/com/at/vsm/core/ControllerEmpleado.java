/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

import com.at.vsm.model.Empleado;
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
public class ControllerEmpleado {
    
    public int insert(Empleado e) throws Exception{
        
        //Escribimos la consulta a ejecutar
        String sql = "{CALL insertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?,"//datos personales
                                        + "?, ?, ?,"//Datos de usuario
                                        + "?,"//datos de empleado
                                        + "?, ?, ?, ?)}";//datos de salida
        
        //guardamos los id's generados
        int idEmpleadoGenerado = -1;
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
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoP());
        cstmt.setString(3, e.getPersona().getApellidoM());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getDomicilio());
        cstmt.setString(6, e.getPersona().getTelefono());
        cstmt.setString(7, e.getPersona().getRfc());
        cstmt.setString(8, e.getPersona().getEmail());
        cstmt.setString(9, e.getPersona().getFotografia());
        
        //2. Datos de Usuario
        cstmt.setString(10, e.getUsuario().getNombreUsuario());
        cstmt.setString(11, e.getUsuario().getContrasenia());
        cstmt.setString(12, e.getUsuario().getRol());
        
        //3. Datos de Empleado
        cstmt.setString(13, e.getPuesto());
        
        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(14, Types.INTEGER);
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.VARCHAR);
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //Recuperamos los ID'S generados
        idEmpleadoGenerado = cstmt.getInt(14);
        idPersonaGenerado = cstmt.getInt(15);
        idUsuarioGenerado = cstmt.getInt(16);
        numeroUnicoGenerado = cstmt.getString(17);
        
        e.setIdEmpleado(idEmpleadoGenerado);
        e.getPersona().setIdPersona(idPersonaGenerado);
        e.getUsuario().setIdUsuario(idUsuarioGenerado);
        e.setNumeroEmpleado(numeroUnicoGenerado);
        
        //ya que se realizó toda la inserción, se cierra la conexión a la base de datos
        //con el método close
        cstmt.close();
        connMySql.close();
        
        //regresamos el id del empleado generado
        return idEmpleadoGenerado;
    }
    
    public void update(Empleado e) throws Exception{
        
        //Definimos la consulta que vamos a ejecutar
        String sql = "{CALL actualizarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?,"//Datos personales
                                           + "?, ?, ?,"//Datos de usuario
                                           + "?,"//Datos de empleado
                                           + "?, ?, ?)}";//id's afectados
        
        //Creamos un objeto que conecta a mysql
        ConexionMySQL connMySql = new ConexionMySQL();
        
        //abrimos la conexión a la base de datos
        Connection conn = connMySql.open();
        
        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        //Establecemos los parametros en orden como los pide el procedimiento almacenado
        //1. Datos Personales
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoP());
        cstmt.setString(3, e.getPersona().getApellidoM());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getDomicilio());
        cstmt.setString(6, e.getPersona().getTelefono());
        cstmt.setString(7, e.getPersona().getRfc());
        cstmt.setString(8, e.getPersona().getEmail());
        cstmt.setString(9, e.getPersona().getFotografia());
        
        //2. Datos de Usuario
        cstmt.setString(10, e.getUsuario().getNombreUsuario());
        cstmt.setString(11, e.getUsuario().getContrasenia());
        cstmt.setString(12, e.getUsuario().getRol());
        
        //3. Datos de Empleado
        cstmt.setString(13, e.getPuesto());

        //4. ID'S Afectadas
        cstmt.setInt(14, e.getUsuario().getIdUsuario());
        cstmt.setInt(15, e.getPersona().getIdPersona());
        cstmt.setInt(16, e.getIdEmpleado());
        
        //Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        //cerramos conexión
        cstmt.close();
        connMySql.close();  
    }
    
    public int delete(int idEmpleado) throws Exception{
        
        //Declaramos una respuesta
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE empleado SET estatus = 0 WHERE idEmpleado = "+idEmpleado;
        
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
    public int reactive(int idEmpleado) throws Exception{
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE empleado SET estatus = 1 WHERE idEmpleado = "+idEmpleado;
        
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
    
    //getAll para empleados activos
    public List<Empleado> getAllActives(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_empleados_activos;";
        
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
        List<Empleado> empleados = new ArrayList<>();
        while (rs.next())
            empleados.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return empleados;
    }
    
    //getAll para empleados inactivos
    public List<Empleado> getAllInactives(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_empleados_inactivos;";
        
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
        List<Empleado> empleados = new ArrayList<>();
        while (rs.next())
            empleados.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return empleados;
    }
    
    private Empleado fill(ResultSet rs) throws Exception{
        
        //Objeto de tipo cliente
        Empleado e = new Empleado();
        
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
        e.setUsuario(new Usuario());
        e.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        e.getUsuario().setNombreUsuario(rs.getString("nombreUsuario"));
        e.getUsuario().setContrasenia(rs.getString("contrasenia"));
        e.getUsuario().setRol(rs.getString("rol"));
        e.getUsuario().setToken();
        e.getUsuario().setDateLastToken(rs.getString("dateLastToken"));
        
        //Datos de cliente
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroEmpleado(rs.getString("numeroEmpleado"));
        e.setPuesto(rs.getString("puesto"));
        e.setEstatus(rs.getInt("estatus"));
        
        e.setPersona(p);
        //devolvemos a todos los clientes
        return e;
    }
    
}
