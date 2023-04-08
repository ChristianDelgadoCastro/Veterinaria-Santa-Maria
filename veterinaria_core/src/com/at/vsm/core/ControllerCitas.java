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

    //Vamos a insertar las citas que se agendarán de parte de cualquier lado de la aplicación (cliente o empleado)
    public void insert(Cita cita) throws SQLException {
        Connection conn = new ConexionMySQL().open();
        PreparedStatement pstmt = null;
        try {
            String query = "INSERT INTO cita (fechaCita, horaCita, idCliente, idEmpleado) "
                    + "VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cita.getFechaCita());
            pstmt.setString(2, cita.getHoraCita());
            pstmt.setInt(3, cita.getCliente().getIdCliente());
            pstmt.setInt(4, cita.getEmpleado().getIdEmpleado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
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
    
    public int delete(int idCita) throws Exception{
        
        //Declaramos una respuesta
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE cita SET estatus = 0 WHERE idCita = "+idCita;
        
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
    
    public int reactive(int idCita) throws Exception{
        
        //Declaramos una respuesta
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE cita SET estatus = 1 WHERE idCita = "+idCita;
        
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
    
    //getAll para las citas agendadas
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
    
    //con este método vamos a llamar al procedimiento almacenado de buscarClientes
    public List<Cita> search(String datoBusqueda) throws Exception{
        
        //Nuestra query
        String sql = "CALL buscarCitas('"+datoBusqueda+"');";
        
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
        while (rs.next())
            respuesta.add(fill(rs));
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
        
        //Datos personales para los empleados
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

    public void update(Cita c) throws SQLException {
        String sql = "UPDATE cita SET fechaCita = ?,"
                + " horaCita = ?,"
                + " idCliente = ?,"
                + " idEmpleado = ?"
                + " WHERE idCita = ?";

        //Creamos un objeto que conecta a mysql
        ConexionMySQL connMySql = new ConexionMySQL();

        //abrimos la conexión a la base de datos
        Connection conn = connMySql.open();

        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);

        //los datos de las citas modificadas
        cstmt.setString(1, c.getFechaCita());
        cstmt.setString(2, c.getHoraCita());
        cstmt.setInt(3, c.getCliente().getIdCliente());
        cstmt.setInt(4, c.getEmpleado().getIdEmpleado());

        //el id de la cita que tenemos que modificar
        cstmt.setInt(5, c.getIdCita());

        // Ejecutamos la actualización
        cstmt.executeUpdate();
    }

}
