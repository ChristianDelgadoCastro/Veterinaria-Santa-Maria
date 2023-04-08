package org.nerverstrong.veterinaria.rest;

import com.google.gson.Gson;
import com.veterinaria.controller.ControllerEmpleado;

import com.veterinaria.model.Empleado;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("empleado")
public class RESTEmpleado {

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("empleado") @DefaultValue("{}") String jsonEmpleado) {

        System.out.println(jsonEmpleado);

        String out = null;

        ControllerEmpleado ce = new ControllerEmpleado();

        Empleado empleado = null;

        try {
            if (jsonEmpleado == null || jsonEmpleado.equals("{}")) {
                out = "{\"error\":\"No se recibieron los datos para guardar\"}";
            } else {
                empleado = new Gson().fromJson(jsonEmpleado, Empleado.class);
                if (empleado.getId() == 0) {
                    ce.insert(empleado);
                } else {
                    ce.update(empleado);
                }
                out = new Gson().toJson(empleado);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerEmpleado ce = new ControllerEmpleado();
        List<Empleado> empleados = null;
        Gson gson = new Gson();

        try {
            empleados = ce.getAll(filtro);
            out = gson.toJson(empleados);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Algo salio mal. llama al profe gil\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delate(@FormParam("idEmpleado") @DefaultValue("0") int id) {

        ControllerEmpleado cs = new ControllerEmpleado();

        String out = null;
        try {
            cs.delate(id);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"" + e.toString() + "\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}



