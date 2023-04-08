package org.nerverstrong.veterinaria.rest;

import com.google.gson.Gson;
import com.veterinaria.controller.ControllerMascota;
import com.veterinaria.model.Mascota;
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

@Path("mascota")
public class RESTMascota {

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("idMascota") @DefaultValue("0") int idMascota,
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("raza") @DefaultValue("") String raza,
            @FormParam("edad") @DefaultValue("") String edad,
            @FormParam("peso") @DefaultValue("0") float peso,
            @FormParam("descripcion") @DefaultValue("") String descipcion,
            @FormParam("idCliente") @DefaultValue("0") int idCliente
    ) {
        String out = null;

        ControllerMascota cs = new ControllerMascota(); //Creamos un objeto de tipo
        //ControllerProducto

        Mascota mascotas = new Mascota(); //Creamos un objeto de tipo sucursal

        try {
            //Llenamos las propiedades del objeto Producto:
            mascotas.setIdMascota(idMascota);
            mascotas.setNombre(nombre);
            mascotas.setRaza(raza);
            mascotas.setEdad(edad);
            mascotas.setPeso(peso);
            mascotas.setDescripcion(descipcion);
            mascotas.setIdCliente(idCliente);

            //Evaluamos si hacemos un INSERT o un UPDATE
            //con base en el ID de la sucursal
            if (mascotas.getIdMascota() == 0) {
                cs.insert(mascotas);
            } else {
                cs.update(mascotas);
            }

            out = new Gson().toJson(mascotas);

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
    public Response delete(@FormParam("idMascota") @DefaultValue("0") int idMascota) {
        String out = null;
        ControllerMascota cs = new ControllerMascota();

        try {

            cs.delete(idMascota);
            out = "{\"result\":\"Movimiento realizado. Se eliminó de manera correcta el registro.\"}";

        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {

        ControllerMascota cm = new ControllerMascota();

        List<Mascota> mascota = null;

        String out = null;

        try {
            mascota = cm.getAll(filtro);
            out = new Gson().toJson(mascota);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"" + e.toString() + "\"}";
        }

        return Response.status(200).entity(out).build();
    }

}




