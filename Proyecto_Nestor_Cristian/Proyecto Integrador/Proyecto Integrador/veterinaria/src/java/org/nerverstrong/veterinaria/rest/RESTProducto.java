package org.nerverstrong.veterinaria.rest;

import com.google.gson.Gson;
import com.veterinaria.controller.ControllerMascota;
import com.veterinaria.controller.ControllerProducto;
import com.veterinaria.model.Mascota;
import com.veterinaria.model.Producto;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("producto")
public class RESTProducto {
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {

        ControllerProducto cm = new ControllerProducto();

        List<Producto> productos = null;

        String out = null;

        try {
            productos = cm.getAll(filtro);
            out = new Gson().toJson(productos);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"" + e.toString() + "\"}";
        }

        return Response.status(200).entity(out).build();
    }
    
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("idProducto") @DefaultValue("0") int idProducto,
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("marca") @DefaultValue("") String marca,
            @FormParam("precioUso") @DefaultValue("0") float precioUso
            
    ) {
        String out = null;

        ControllerProducto cp = new ControllerProducto(); //Creamos un objeto de tipo
        //ControllerProducto

        Producto productos = new Producto(); //Creamos un objeto de tipo sucursal

        try {
            //Llenamos las propiedades del objeto Producto:
            productos.setIdProducto(idProducto);
            productos.setNombre(nombre);
            productos.setMarca(marca);
            productos.setPrecioUso(precioUso);

            //Evaluamos si hacemos un INSERT o un UPDATE
            //con base en el ID de la sucursal
            if (productos.getIdProducto() == 0) {
                cp.insert(productos);
            } else {
                cp.update(productos);
            }

            out = new Gson().toJson(productos);

        } catch (Exception e) {
            //Imprimos el error en  la consola del servidor
            e.printStackTrace();

            //Devolvemos una descripccion del error
            out = "{\"error\":\"" + e.toString() + "\"}";
        }
        return Response.status(200).entity(out).build();
    }
    
    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("idProducto") @DefaultValue("0") int idProducto) {
        String out = null;
        ControllerProducto cs = new ControllerProducto();

        try {
            cs.delete(idProducto);
            out = "{\"result\":\"Movimiento realizado. Se eliminó de manera correcta el registro.\"}";

        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
}



