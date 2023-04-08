package com.veterinaria.controller;

import com.veterinaria.db.ConexionMySQL;
import com.veterinaria.model.Mascota;
import com.veterinaria.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerProducto {

    public List<Producto> getAll(String filtro) throws Exception {

        //Definimos la consulta
        String sql = "SELECT * FROM producto WHERE estatus = 1";

        //Aqui se guardaran objetos tipo sucursal contenedor 
        //dinamico de objetos
        List<Producto> productos = new ArrayList<Producto>();

        //Variable temporal para crear instancias de sucursal
        Producto p = null;

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
            p = fill(rs);
            productos.add(p);
        }

        //Cerramos los objetos de bd 
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Devolvemos la lista de sucursales
        return productos;
    }

    private Producto fill(ResultSet rs) throws Exception {
        //Creamos una nueva instancia de Producto:
        Producto p = new Producto();

        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setMarca(rs.getString("marca"));
        p.setPrecioUso(rs.getFloat("precioUso"));
        p.setEstatus(rs.getInt("estatus"));

        return p;
    }

    public int insert(Producto p) throws Exception {
        //Definimos la consulta SQL que realiza la insercion del registro:
        String sql = "INSERT INTO producto(nombre, marca, precioUso, estatus)" + "VALUES(?, ?, ?, ?)";

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
        pstmt.setString(1, p.getNombre());
        pstmt.setString(2, p.getMarca());
        pstmt.setFloat(3, p.getPrecioUso());
        pstmt.setInt(4, 1);

        //Ejecutamos la cosulta:
        pstmt.executeUpdate();

        //Le pedimos al PreparedStatement el valor de las claves primarias
        //generadas, que en este caso, es solo un valor:
        rs = pstmt.getGeneratedKeys();

        //Intentamos movernos al primer registro
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            p.setIdProducto(idGenerado);
        }

        //Cerramos todos los objetos de conexion con la Base de Datos:
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Devolvemos el ID que se genero:
        return idGenerado;
    }

    public void update(Producto p) throws Exception {
        String sql = "UPDATE producto SET nombre = ?, "
                + "marca = ?, "
                + "precioUso = ?"
                + "WHERE idProducto = ?";

        ConexionMySQL connMySQL = new ConexionMySQL();

        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, p.getNombre());
        pstmt.setString(2, p.getMarca());
        pstmt.setFloat(3, p.getPrecioUso());
        pstmt.setInt(4, p.getIdProducto());

        pstmt.executeUpdate();

        pstmt.close();
        connMySQL.close();
    }
    
    public void delete(int idProducto) throws Exception {
        //Definimos la consulta SQL que realiza la eliminacion del registro:
        String sql = "UPDATE producto SET estatus = 0 WHERE idProducto = ?";
        //Declaramos un objeto de tipo conexion
        ConexionMySQL connMySQL = new ConexionMySQL();
        //Abrimos la conexion a la Base de Datos:
        Connection conn = connMySQL.open();

        //Invocamos la sentencia
        PreparedStatement pstmt = conn.prepareStatement(sql);
        //Ejecutaremos
        pstmt.setInt(1, idProducto);
        pstmt.executeUpdate();
        //Cerramos todos los objetos de conexion con la Base de Datos
        pstmt.close();
        connMySQL.close();
    }
}



