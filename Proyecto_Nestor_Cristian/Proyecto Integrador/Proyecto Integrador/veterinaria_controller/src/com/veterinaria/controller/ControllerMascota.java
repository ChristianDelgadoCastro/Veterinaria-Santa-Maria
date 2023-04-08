package com.veterinaria.controller;

import com.veterinaria.db.ConexionMySQL;
import com.veterinaria.model.Cliente;
import com.veterinaria.model.Mascota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerMascota {

    public List<Mascota> getAll(String filtro) throws Exception {

        //Definimos la consulta
        String sql = "SELECT * FROM mascota WHERE estatus = 1";

        //Aqui se guardaran objetos tipo sucursal contenedor 
        //dinamico de objetos
        List<Mascota> mascotas = new ArrayList<Mascota>();

        //Variable temporal para crear instancias de sucursal
        Mascota m = null;

        //Con este objetos vamos a conectar a la base de datos
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexion
        Connection conn = connMySQL.open();
        //Declaramos e iniciamos el objeto con el que ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //Ejecutamos la consulta y guardamos el resultado
        ResultSet rs = pstmt.executeQuery();

        //recorremos el resultset
        while (rs.next()) {
            m = fill(rs);
            mascotas.add(m);
        }

        //Cerramos los objetos de bd 
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Devolvemos la lista de sucursales
        return mascotas;
    }

    private Mascota fill(ResultSet rs) throws Exception {
        //Creamos una nueva instancia de Producto:
        Mascota m = new Mascota();

        m.setIdMascota(rs.getInt("idMascota"));
        m.setNombre(rs.getString("nombre"));
        m.setRaza(rs.getString("raza"));
        m.setEdad(rs.getString("edad"));
        m.setPeso(rs.getFloat("peso"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setIdCliente(rs.getInt("idCliente"));
        m.setEstatus(rs.getInt("estatus"));

        return m;
    }

    public int insert(Mascota m) throws Exception {
        //Definimos la consulta SQL que realiza la insercion del registro:
        String sql = "INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        //Aqui guardaremos el ID que se generara:
        int idGenerado = -1;

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexion a la Base de Datos:
        Connection conn = connMySQL.open();

        //Con este objeto ejecutarmeos la sentencia SQL que realiza la 
        //insercion en la tabla. Debemos especificarle que queremos que nos 
        //devuelva el ID que se genera al realizar la insercion del registro.
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //Es este objeto guarderemos el resultado de la consulta, la cual
        //nos devolvera los ID's que se generaron. En este caso,
        //solo se generara un ID:
        ResultSet rs = null;

        //Llenaremos el valor de campo de la consulta SQL definida antes:
        pstmt.setString(1, m.getNombre());
        pstmt.setString(2, m.getRaza());
        pstmt.setString(3, m.getEdad());
        pstmt.setFloat(4, m.getPeso());
        pstmt.setString(5, m.getDescripcion());
        pstmt.setInt(6, m.getIdCliente());
        pstmt.setInt(7, 1);

        //Ejecutamos la cosulta:
        pstmt.executeUpdate();

        //Le pedimos al PreparedStatement el valor de las claves primarias
        //generadas, que en este caso, es solo un valor:
        rs = pstmt.getGeneratedKeys();

        //Intentamos movernos al primer registro
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            m.setIdMascota(idGenerado);
        }

        //Cerramos todos los objetos de conexion con la Base de Datos:
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Devolvemos el ID que se genero:
        return idGenerado;
    }

    public void update(Mascota m) throws Exception {
        String sql = "UPDATE mascota SET nombre = ?, "
                + "raza = ?, "
                + "edad = ?, "
                + "peso = ?, "
                + "descripcion = ? "
                + "WHERE idMascota = ?";

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, m.getNombre());
        pstmt.setString(2, m.getRaza());
        pstmt.setString(3, m.getEdad());
        pstmt.setDouble(4, m.getPeso());
        pstmt.setString(5, m.getDescripcion());
        pstmt.setInt(6, m.getIdMascota());

        pstmt.executeUpdate();

        pstmt.close();
        connMySQL.close();
    }

    public void delete(int idMascota) throws Exception {
        //Definimos la consulta SQL que realiza la eliminacion del registro:
        String sql = "UPDATE mascota SET estatus = 0 Where idMascota = ?";
        //Declaramos un objeto de tipo conexion
        ConexionMySQL connMySQL = new ConexionMySQL();
        //Abrimos la conexion a la Base de Datos:
        Connection conn = connMySQL.open();

        //Invocamos la sentencia
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //Ejecutaremos
        pstmt.setInt(1, idMascota);
        pstmt.executeUpdate();
        //Cerramos todos los objetos de conexion con la Base de Datos
        pstmt.close();
        connMySQL.close();
    }

}



