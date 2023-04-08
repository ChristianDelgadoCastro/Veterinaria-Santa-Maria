package com.veterinaria.controller;

import com.veterinaria.db.ConexionMySQL;
import com.veterinaria.model.Empleado;
import com.veterinaria.model.Persona;
import com.veterinaria.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ControllerEmpleado {

    public int insert(Empleado e) throws Exception {

        String sql = "{call insertarEmpleado (?, ?, ?, ?, ?, ?,"
                + //Datos de persona 6 parametros
                "?, ?, ?,"
                + //Datos de usuario 3 parametros
                "?, "
                + //Datos de cliente 1 parametro
                "?, ?, ?, ?)}"; //Datos de retorno 4 parametros 

        //Guardamos los ID's que se generarán:
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idEmpleadoGenerado = -1;
        String numEmpleadoGenerado = "";

        //Con este objeto nos conectaremos a la BD
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexion con la BD 
        Connection conn = connMySQL.open();

        //Con este objeto invocamos el procedimiento almacenado
        CallableStatement cstmt = conn.prepareCall(sql);

        //Establecemos los parámetros de los datos personales en el orden que los pide el procedimiento
        //almacenado, comenzando en 1:
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidos());
        cstmt.setString(3, e.getPersona().getGenero());
        cstmt.setString(4, e.getPersona().getDomicilio());
        cstmt.setString(5, e.getPersona().getTelefono());
        cstmt.setString(6, e.getPersona().getRfc());

        //Establecemos los parámetros de los datos de Usuario
        cstmt.setString(7, e.getUsuario().getNombreUsuario());
        cstmt.setString(8, e.getUsuario().getContrasenia());
        cstmt.setString(9, e.getUsuario().getRol());

        //Establecemos los parámetros de los datos de Clientes
        cstmt.setString(10, e.getPuesto());

        //Registramos los parámetros de salida:
        cstmt.registerOutParameter(11, Types.INTEGER);
        cstmt.registerOutParameter(12, Types.INTEGER);
        cstmt.registerOutParameter(13, Types.INTEGER);
        cstmt.registerOutParameter(14, Types.INTEGER);

        //Ejecutamos el procedimiento almacenado
        cstmt.executeUpdate();

        //Recuperamos los ID's generados y el número de cliente generado
        idUsuarioGenerado = cstmt.getInt(11);
        idPersonaGenerado = cstmt.getInt(12);
        idEmpleadoGenerado = cstmt.getInt(13);
        numEmpleadoGenerado = cstmt.getString(14);

        //Los guardamos en el objeto Cliente que nos pasaron como parámetro:
        e.getPersona().setId(idPersonaGenerado);
        e.getUsuario().setId(idUsuarioGenerado);
        e.setId(idEmpleadoGenerado);
        e.setNumeroEmpleado(numEmpleadoGenerado);

        //Cerramos los objetos de base de datos:
        cstmt.close();
        connMySQL.close();

        return idEmpleadoGenerado;

    }

    public List<Empleado> getAll(String filtro) throws Exception {

        String sql = "SELECT * FROM v_empleados WHERE estatus = 1";

        List<Empleado> empleados = new ArrayList<Empleado>();

        Empleado e = null;

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            e = fill(rs);
            empleados.add(e);
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return empleados;
    }

    public void delate(int idEmpleado) throws Exception {
        //Definimos la consulta SQL ue realiza la insercion del registro:
        String sql = "UPDATE empleado SET estatus = 0  WHERE idEmpleado = ?";

        //Con este objeto nos vamos a conectar a la base de datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexiona la base de datos:
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idEmpleado);

        pstmt.executeUpdate();
        pstmt.close();
        connMySQL.close();
    }

    private Empleado fill(ResultSet rs) throws Exception {

        Empleado e = new Empleado();

        Persona p = new Persona();

        Usuario u = new Usuario();

        p.setId(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setGenero(rs.getString("genero"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setTelefono(rs.getString("telefono"));
        p.setRfc(rs.getString("rfc"));

        e.setId(rs.getInt("idEmpleado"));
        e.setNumeroEmpleado(rs.getString("numeroEmpleado"));
        e.setPuesto(rs.getString("puesto"));
        e.setEstatus(rs.getInt("estatus"));

        u.setId(rs.getInt("idUsuario"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setRol(rs.getString("rol"));

        e.setPersona(p);
        e.setUsuario(u);

        return e;
    }

    public void update(Empleado e) throws Exception {
        String sql = "{call actualizarEmpleado (?, ?, ?, ?, ?, ?,"
                + //Datos Persona 6 parametros
                "?, ?, ?,"
                + //Datos Usuario 3 parametros
                "?, "
                + //Datos Cliente 1 parametro
                "?, ?, ?)}"; //Id de las tablas relacionadas(persona,usuario,cliente)

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        CallableStatement cstmt = conn.prepareCall(sql);

        //Establecemos los parámetros de los datos personales en el orden que los pide el procedimiento
        //almacenado, comenzando en 1:
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidos());
        cstmt.setString(3, e.getPersona().getGenero());
        cstmt.setString(4, e.getPersona().getDomicilio());
        cstmt.setString(5, e.getPersona().getTelefono());
        cstmt.setString(6, e.getPersona().getRfc());

        //Establecemos los parámetros de los datos de Usuario
        cstmt.setString(7, e.getUsuario().getNombreUsuario());
        cstmt.setString(8, e.getUsuario().getContrasenia());
        cstmt.setString(9, e.getUsuario().getRol());

        //Establecemos los parámetros de los datos de Clientes
        cstmt.setString(10, e.getPuesto());

        //Establecemos los ID relacionados
        cstmt.setInt(11, e.getUsuario().getId());
        cstmt.setInt(12, e.getPersona().getId());
        cstmt.setInt(13, e.getId());
        //Ejecutamos la consulta:
        cstmt.executeUpdate();

        //Cerramos la conexión
        cstmt.close();

        connMySQL.close();
    }}
