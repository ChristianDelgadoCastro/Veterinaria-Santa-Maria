package com.veterinaria.controller;

import com.veterinaria.db.ConexionMySQL;
import com.veterinaria.model.Cliente;
import com.veterinaria.model.Persona;
import com.veterinaria.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ControllerCliente {

    public int insert(Cliente c) throws Exception {
        String sql = "{call insertarCliente (?, ?, ?, ?, ?, ?,"
                + //Datos de persona 6 parametros
                "?, ?, ?,"
                + //Datos de usuario 3 parametros
                "?, "
                + //Datos de cliente 1 parametro
                "?, ?, ?, ?)}"; //Datos de retorno 4 parametros 

        //Guardamos los ID's que se generarán:
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idClienteGenerado = -1;
        String numClienteGenerado = "";

        //Con este objeto nos conectaremos a la BD
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexion con la BD 
        Connection conn = connMySQL.open();

        //Con este objeto invocamos el procedimiento almacenado
        CallableStatement cstmt = conn.prepareCall(sql);

        //Establecemos los parámetros de los datos personales en el orden que los pide el procedimiento
        //almacenado, comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidos());
        cstmt.setString(3, c.getPersona().getGenero());
        cstmt.setString(4, c.getPersona().getDomicilio());
        cstmt.setString(5, c.getPersona().getTelefono());
        cstmt.setString(6, c.getPersona().getRfc());

        //Establecemos los parámetros de los datos de Usuario
        cstmt.setString(7, c.getUsuario().getNombreUsuario());
        cstmt.setString(8, c.getUsuario().getContrasenia());
        cstmt.setString(9, c.getUsuario().getRol());

        //Establecemos los parámetros de los datos de Clientes
        cstmt.setString(10, c.getCorreo());

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
        idClienteGenerado = cstmt.getInt(13);
        numClienteGenerado = cstmt.getString(14);

        //Los guardamos en el objeto Cliente que nos pasaron como parámetro:
        c.getPersona().setId(idPersonaGenerado);
        c.getUsuario().setId(idUsuarioGenerado);
        c.setId(idClienteGenerado);
        c.setNumeroUnico(numClienteGenerado);

        //Cerramos los objetos de base de datos:
        cstmt.close();
        connMySQL.close();

        return idClienteGenerado;
    }

    public void update(Cliente c) throws Exception {
        String sql = "{call actualizarCliente (?, ?, ?, ?, ?, ?,"
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
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidos());
        cstmt.setString(3, c.getPersona().getGenero());
        cstmt.setString(4, c.getPersona().getDomicilio());
        cstmt.setString(5, c.getPersona().getTelefono());
        cstmt.setString(6, c.getPersona().getRfc());

        //Establecemos los parámetros de los datos de Usuario
        cstmt.setString(7, c.getUsuario().getNombreUsuario());
        cstmt.setString(8, c.getUsuario().getContrasenia());
        cstmt.setString(9, c.getUsuario().getRol());

        //Establecemos los parámetros de los datos de Clientes
        cstmt.setString(10, c.getCorreo());

        //Establecemos los ID relacionados
        cstmt.setInt(11, c.getPersona().getId());
        cstmt.setInt(12, c.getUsuario().getId());
        cstmt.setInt(13, c.getId());
        //Ejecutamos la consulta:
        cstmt.executeUpdate();

        //Cerramos la conexión
        cstmt.close();
        connMySQL.close();
    }

    public void delete(int idCliente) throws Exception {
        String sql = "UPDATE cliente SET estatus = 0 WHERE idCliente = ?";

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, idCliente);

        pstmt.executeUpdate();

        pstmt.close();

        connMySQL.close();
    }

    public List<Cliente> getAll(String filtro) throws Exception {

//La consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_clientes WHERE estatus = 1";

//La lista diamica donde guardaremos objetos de tipo Empleado
//por cadena registro que devuelva la BD:
        List<Cliente> clientes = new ArrayList<Cliente>();

//Una variable temporal para crear nuevos objetos de tipo Empleado:
        Cliente e = null;

//Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

//Abrimos la conexion con la Base de Datos:
        Connection conn = connMySQL.open();

//Con este onjeto ejecutaremos la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);

//Aqui guardaremos los resultados de la consulta:
        ResultSet rs = pstmt.executeQuery();

//Iteramos el conjunto de registros devuelto por la BD.
//"Si hay un siguiente registro, nos movemos":
        while (rs.next()) {
            e = fill(rs);
//Agregamos el objeto de tipo Empleado a la lista dinamica:
            clientes.add(e);
        }

//Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

//Devolvemos la lista dinamica con objetos de tipo Empleado dentro:
        return clientes;
    }

    private Cliente fill(ResultSet rs) throws Exception {

//Una variable temporal para crear nuevos objetos de tipo Empleado:
        Cliente c = new Cliente();

//Una variable temporal para crear nuevos objetos de tipo Persona:
        Persona p = new Persona();

//Una variable temporal para crear nuevos objetos de tipo Usuario:
        Usuario u = new Usuario();

//Llenamos sus datos:
        p.setId(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setGenero(rs.getString("genero"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setTelefono(rs.getString("telefono"));
        p.setRfc(rs.getString("rfc"));

//Creamos un nuevo objeto de tipo Usuario:
        u.setId(rs.getInt("idUsuario"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setRol(rs.getString("rol"));

//Establecmos sus datos personales:
        c.setId(rs.getInt("idCliente"));
        c.setCorreo(rs.getString("correo"));
        c.setNumeroUnico(rs.getString("numeroUnico"));
        c.setEstatus(rs.getInt("estatus"));

//Establecemos su primera:
        c.setPersona(p);

//Establecemos su Usuario:
        c.setUsuario(u);

        return c;
    }
}
