/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

import com.at.vsm.model.Cita;
import com.at.vsm.db.ConexionMySQL;
import com.at.vsm.model.Cliente;
import com.at.vsm.model.Empleado;
import com.at.vsm.model.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class ControllerCitas {

    public void insert(Cita c) throws SQLException, CitaDuplicadaException {
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Verificar si ya hay una cita agendada para la fecha y hora proporcionadas
            String query = "SELECT COUNT(*) FROM v_citas_activas WHERE fechaCita = ? "
                    + "AND horaCita = ? "
                    + "AND idCliente = ? "
                    + "AND idEmpleado = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getFechaCita());
            pstmt.setString(2, c.getHoraCita());
            pstmt.setInt(3, c.getCliente().getIdCliente());
            pstmt.setInt(4, c.getEmpleado().getIdEmpleado());
            rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            // Si ya hay una cita agendada, lanzar una excepción
            if (count > 0) {
                throw new CitaDuplicadaException();
            } else {
                // Si no hay una cita agendada, insertar la nueva cita
                query = "INSERT INTO cita (fechaCita, horaCita, idCliente, idEmpleado) "
                        + "VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, c.getFechaCita());
                pstmt.setString(2, c.getHoraCita());
                pstmt.setInt(3, c.getCliente().getIdCliente());
                pstmt.setInt(4, c.getEmpleado().getIdEmpleado());
                pstmt.executeUpdate();
                System.out.println("Cita agendada correctamente.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Cita c) throws SQLException {

        String query1 = "SELECT COUNT(*) FROM v_citas_activas WHERE fechaCita = ? "
                + "AND horaCita = ? "
                + "AND (idCliente = ? OR idEmpleado = ?)";

        String query2 = "UPDATE cita SET fechaCita = ?,"
                + " horaCita = ?,"
                + " idCliente = ?,"
                + " idEmpleado = ?"
                + " WHERE idCita = ?";

        //Creamos un objeto que conecta a mysql
        ConexionMySQL connMySql = new ConexionMySQL();

        //abrimos la conexión a la base de datos
        Connection conn = connMySql.open();

        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement countStmt = conn.prepareCall(query1);
        countStmt.setString(1, c.getFechaCita());
        countStmt.setString(2, c.getHoraCita());
        countStmt.setInt(3, c.getCliente().getIdCliente());
        countStmt.setInt(4, c.getEmpleado().getIdEmpleado());

        ResultSet rs = countStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        if (count > 0) {
            // si hay citas agendadas en la fecha, hora, cliente o empleado, no se puede actualizar la cita
            System.out.println("No se puede actualizar la cita porque ya hay una cita agendada en la fecha, hora, cliente o empleado");
        } else {
            // si no hay citas agendadas en la fecha, hora, cliente o empleado, se puede actualizar la cita
            CallableStatement updateStmt = conn.prepareCall(query2);
            updateStmt.setString(1, c.getFechaCita());
            updateStmt.setString(2, c.getHoraCita());
            updateStmt.setInt(3, c.getCliente().getIdCliente());
            updateStmt.setInt(4, c.getEmpleado().getIdEmpleado());
            updateStmt.setInt(5, c.getIdCita());

            // Ejecutamos la actualización
            updateStmt.executeUpdate();
        }
    }

    public int delete(int idCita) throws Exception {

        //Declaramos una respuesta
        int respuesta = 0;

        //Establecemos la query a ejecutar
        String sql = "UPDATE cita SET estatus = 0 WHERE idCita = " + idCita;

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

    public int reactive(int idCita) throws Exception {

        //Declaramos una respuesta
        int respuesta = 0;

        //Establecemos la query a ejecutar
        String sql = "UPDATE cita SET estatus = 1 WHERE idCita = " + idCita;

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

    //getAll para todas las citas
    public List<Cita> getAll(String filtro) throws Exception {

        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_citas";

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
        List<Cita> citas = new ArrayList<>();
        while (rs.next()) {
            citas.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();

        return citas;
    }

    //getAll para las citas agendadas
    public List<Cita> getAllActives(String filtro) throws Exception {

        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_citas_activas";

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
        List<Cita> citas = new ArrayList<>();
        while (rs.next()) {
            citas.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();

        return citas;
    }

    //getAll para las citas canceladas o concluidas
    public List<Cita> getAllInactives(String filtro) throws Exception {

        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_citas_inactivas";

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
        List<Cita> citas = new ArrayList<>();
        while (rs.next()) {
            citas.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();

        return citas;
    }

    //una mexicaneada para las citas activas que cada cliente o empleado vea las suyas jajaja
    public List<Cita> searchActives(String datoBusqueda) throws Exception {

        //Nuestra query
        String sql = "CALL buscarCitasActivas('" + datoBusqueda + "');";

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
        List<Cita> respuesta = new ArrayList<>();
        while (rs.next()) {
            respuesta.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();

        return respuesta;
    }

    //una mexicaneada para las citas activas que cada cliente o empleado vea las suyas jajaja
    public List<Cita> searchInactives(String datoBusqueda) throws Exception {

        //Nuestra query
        String sql = "CALL buscarCitasInactivas('" + datoBusqueda + "');";

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
        List<Cita> respuesta = new ArrayList<>();
        while (rs.next()) {
            respuesta.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();

        return respuesta;
    }

    //con este método vamos a llamar al procedimiento almacenado de buscarClientes
    public List<Cita> search(String datoBusqueda) throws Exception {

        //Nuestra query
        String sql = "CALL buscarCitas('" + datoBusqueda + "');";

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
        List<Cita> respuesta = new ArrayList<>();
        while (rs.next()) {
            respuesta.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        connMySQL.close();

        return respuesta;
    }

    private Cita fill(ResultSet rs) throws Exception {

        //Objeto de tipo cita
        Cita c = new Cita();

        c.setIdCita(rs.getInt("idCita"));
        c.setFechaCita(rs.getString("fechaCita"));
        c.setHoraCita(rs.getString("horaCita"));
        c.setEstatus(rs.getInt("citaEstatus"));

        //Objeto de tipo cliente
        Cliente cl = new Cliente();

        //Objeto de tipo persona para los clientes
        Persona pc = new Persona();

        //Datos personales para los clientes
        pc.setIdPersona(rs.getInt("idPersonaCliente"));
        pc.setNombre(rs.getString("clienteNombre"));
        pc.setApellidoP(rs.getString("clienteApellidoP"));
        pc.setApellidoM(rs.getString("clienteApellidoM"));
        pc.setGenero(rs.getString("clienteGenero"));
        pc.setDomicilio(rs.getString("clienteDomicilio"));
        pc.setTelefono(rs.getString("clienteTelefono"));
        pc.setRfc(rs.getString("clienteRFC"));
        pc.setEmail(rs.getString("clienteEmail"));

        //Objeto de tipo persona para los empleados
        Persona pe = new Persona();

        //Datos personales para los empleados
        pe.setIdPersona(rs.getInt("idPersonaEmpleado"));
        pe.setNombre(rs.getString("empleadoNombre"));
        pe.setApellidoP(rs.getString("empleadoApellidoP"));
        pe.setApellidoM(rs.getString("empleadoApellidoM"));
        pe.setGenero(rs.getString("empleadoGenero"));
        pe.setDomicilio(rs.getString("empleadoDomicilio"));
        pe.setTelefono(rs.getString("empleadoTelefono"));
        pe.setRfc(rs.getString("empleadoRFC"));
        pe.setEmail(rs.getString("empleadoEmail"));

        //Datos de cliente
        cl.setIdCliente(rs.getInt("idCliente"));
        cl.setNumeroUnico(rs.getString("clienteNumeroUnico"));
        cl.setEstatus(rs.getInt("clienteEstatus"));

        //Objeto de tipo empleado
        Empleado e = new Empleado();

        //Datos de empleado
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroUnico(rs.getString("empleadoNumeroUnico"));
        e.setPuesto(rs.getString("empleadoPuesto"));
        e.setEstatus(rs.getInt("empleadoEstatus"));

        e.setPersona(pe);
        cl.setPersona(pc);
        c.setCliente(cl);
        c.setEmpleado(e);

        //devolvemos a todos los clientes
        return c;
    }

}
