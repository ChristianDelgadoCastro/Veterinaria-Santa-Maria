/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

import com.at.vsm.db.ConexionMySQL;
import com.at.vsm.model.Cliente;
import com.at.vsm.model.Mascota;
import com.at.vsm.model.Persona;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class ControllerMascotas {
    
    //Vamos a insertar las citas que se agendarán de parte de cualquier lado de la aplicación (cliente o empleado)
    public void insert(Mascota m) throws SQLException {
        Connection conn = new ConexionMySQL().open();
        PreparedStatement pstmt = null;
        try {
            String query = "INSERT INTO mascota (collar, fotografia, nombre, especie, raza, genero, edad, peso, detalles, idCliente)"
                                      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, m.getCollar());
            pstmt.setString(2, m.getFotografia());
            pstmt.setString(3, m.getNombre());
            pstmt.setString(4, m.getEspecie());
            pstmt.setString(5, m.getRaza());
            pstmt.setString(6, m.getGenero());
            pstmt.setString(7, m.getEdad());
            pstmt.setDouble(8, m.getPeso());
            pstmt.setString(9, m.getDetalles());
            pstmt.setInt(10, m.getCliente().getIdCliente());
            
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
    
    //Vamos a actualizar los datos de las mascotas
    public void update(Mascota m) throws SQLException{
        
        String sql = "UPDATE mascota SET collar = ?, "
                                      + "fotografia = ?, "
                                      + "nombre = ?, "
                                      + "especie = ?, "
                                      + "raza = ?, "
                                      + "genero = ?, "
                                      + "edad = ?, "
                                      + "peso = ?, "
                                      + "detalles = ?, "
                                      + "idCliente = ? "
                                      + "WHERE idMascota = ?;";
        
        //Creamos un objeto que conecta a mysql
        ConexionMySQL connMySql = new ConexionMySQL();

        //abrimos la conexión a la base de datos
        Connection conn = connMySql.open();

        //Con este objeto invocaremos al StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);

        //los datos de las citas modificadas
        cstmt.setString(1, m.getCollar());
        cstmt.setString(2, m.getFotografia());
        cstmt.setString(3, m.getNombre());
        cstmt.setString(4, m.getEspecie());
        cstmt.setString(5, m.getRaza());
        cstmt.setString(6, m.getGenero());
        cstmt.setString(7, m.getEdad());
        cstmt.setDouble(8, m.getPeso());
        cstmt.setString(9, m.getDetalles());
        cstmt.setInt(10, m.getCliente().getIdCliente());
        
        //El id de la mascota que vamos a modificar
        cstmt.setInt(11, m.getIdMascota());

        // Ejecutamos la actualización
        cstmt.executeUpdate();
        
    }
    
    public int delete(int idMascota) throws Exception{
        
        //Declaramos una respuesta
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE mascota SET estatus = 0 WHERE idMascota = "+idMascota;
        
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
    
    public int reactive(int idMascota) throws Exception{
        
        //Declaramos una respuesta
        int respuesta = 0;
        
        //Establecemos la query a ejecutar
        String sql = "UPDATE mascota SET estatus = 1 WHERE idMascota = "+idMascota;
        
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
    
    //getAll para mascotas
    public List<Mascota> getAll(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_mascotas";
        
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
    
    //getAll para mascotas activas
    public List<Mascota> getAllActives(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_mascotas_activas";
        
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
    
    //getAll para mascotas inactivas
    public List<Mascota> getAllInactives(String filtro) throws Exception{
        
        //se invoca la consulta a la vista de clientes
        String sql = "SELECT * FROM v_mascotas_inactivas";
        
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
    
    //Buscar mascotas activas
    public List<Mascota> search(String datoBusqueda) throws Exception{
        
        //Nuestra query
        String sql = "CALL buscarMascotas('"+datoBusqueda+"');";
        
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
        List<Mascota> respuesta = new ArrayList<>();
        while (rs.next())
            respuesta.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return respuesta;
    }
    
    //Buscar mascotas activas
    public List<Mascota> searchActives(String datoBusqueda) throws Exception{
        
        //Nuestra query
        String sql = "CALL buscarMascotasActivas('"+datoBusqueda+"');";
        
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
        List<Mascota> respuesta = new ArrayList<>();
        while (rs.next())
            respuesta.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return respuesta;
    }
    
    //Buscar mascotas inactivas
    public List<Mascota> searchInactives(String datoBusqueda) throws Exception{
        
        //Nuestra query
        String sql = "CALL buscarMascotasInactivas('"+datoBusqueda+"');";
        
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
        List<Mascota> respuesta = new ArrayList<>();
        while (rs.next())
            respuesta.add(fill(rs));
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return respuesta;
    }
    
    private Mascota fill(ResultSet rs) throws Exception{
        
        //Objeto de tipo mascota
        Mascota m = new Mascota();
        
        //Datos de la mascota
        m.setIdMascota(rs.getInt("idMascota"));
        m.setNumeroUnico(rs.getString("mascotaNumeroUnico"));
        m.setCollar(rs.getString("collar"));
        m.setFotografia(rs.getString("fotografia"));
        m.setNombre(rs.getString("mascotaNombre"));
        m.setEspecie(rs.getString("especie"));
        m.setRaza(rs.getString("raza"));
        m.setGenero(rs.getString("mascotaGenero"));
        m.setEdad(rs.getString("edad"));
        m.setPeso(rs.getDouble("peso"));
        m.setDetalles(rs.getString("detalles"));
        m.setEstatus(rs.getInt("mascotaEstatus"));
        
        //Objeto de tipo Persona
        Persona p = new Persona();
                
        //Datos de persona
        p.setIdPersona(rs.getInt("idPersonaCliente"));
        p.setNombre(rs.getString("clienteNombre"));
        p.setApellidoP(rs.getString("clienteApellidoP"));
        p.setApellidoM(rs.getString("clienteApellidoM"));
        p.setGenero(rs.getString("clienteGenero"));
        p.setDomicilio(rs.getString("clienteDomicilio"));
        p.setTelefono(rs.getString("clienteTelefono"));
        p.setRfc(rs.getString("clienteRFC"));
        p.setEmail(rs.getString("clienteEmail"));
        
        //Objeto de tipo cliente
        Cliente c = new Cliente();
        
        //Datos de cliente
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNumeroUnico(rs.getString("clienteNumeroUnico"));
        c.setEstatus(rs.getInt("clienteEstatus"));
        c.setPersona(p);
        
        m.setCliente(c);
        //devolvemos a todos los clientes
        return m;
    }
}
