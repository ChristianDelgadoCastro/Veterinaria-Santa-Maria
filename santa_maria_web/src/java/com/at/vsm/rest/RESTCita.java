/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.rest;

import com.at.vsm.core.CitaDuplicadaException;
import com.at.vsm.core.ControllerCitas;
import com.at.vsm.model.Cita;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author david
 */
@Path("cita")
public class RESTCita {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filter") @DefaultValue("") String filter) {
        String out = null;
        ControllerCitas cc = null;
        List<Cita> mascotas = null;
        try {
            cc = new ControllerCitas();
            mascotas = cc.getAll(filter);
            out = new Gson().toJson(mascotas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("getAllActives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActives(@QueryParam("filter") @DefaultValue("") String filter) {
        String out = null;
        ControllerCitas cc = null;
        List<Cita> mascotas = null;
        try {
            cc = new ControllerCitas();
            mascotas = cc.getAllActives(filter);
            out = new Gson().toJson(mascotas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("getAllInactives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInactives(@QueryParam("filter") @DefaultValue("") String filter) {
        String out = null;
        ControllerCitas cc = null;
        List<Cita> mascotas = null;
        try {
            cc = new ControllerCitas();
            mascotas = cc.getAllInactives(filter);
            out = new Gson().toJson(mascotas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("datoBusqueda") @DefaultValue("") String datoBusqueda) {
        String out = null;
        ControllerCitas cc = null;
        List<Cita> citas = null;
        try {
            cc = new ControllerCitas();
            citas = cc.search(datoBusqueda);
            out = new Gson().toJson(citas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    //con estos métodos vamos a filtrar cada una de las citas para que cada quien vea las suyas
    @GET
    @Path("searchActives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchActives(@QueryParam("datoBusqueda") @DefaultValue("") String datoBusqueda) {
        String out = null;
        ControllerCitas cc = null;
        List<Cita> citas = null;
        try {
            cc = new ControllerCitas();
            citas = cc.searchActives(datoBusqueda);
            out = new Gson().toJson(citas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("searchInactives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchInactives(@QueryParam("datoBusqueda") @DefaultValue("") String datoBusqueda) {
        String out = null;
        ControllerCitas cc = null;
        List<Cita> citas = null;
        try {
            cc = new ControllerCitas();
            citas = cc.searchInactives(datoBusqueda);
            out = new Gson().toJson(citas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosCita") @DefaultValue("") String datosCita) {
        String out = null;
        Gson gson = new Gson();
        Cita cit = null;
        ControllerCitas cc = new ControllerCitas();
        try {
            cit = gson.fromJson(datosCita, Cita.class);
            if (cit.getIdCita() == 0) {
                cc.insert(cit);
                System.out.println("Entre al insert");
            } else {
                cc.update(cit);
                System.out.println("Entre al update");
            }
            out = gson.toJson(cit);
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
            out = """
              {"exception":"Formato JSON de datos incorrecto."}
              """;
        } catch (CitaDuplicadaException e) {
            e.printStackTrace();
            out = """
              {"error":"Estos datos ya estan ocupados"}
              """;
        } catch (Exception e) {
            e.printStackTrace();
            out = """
              {"exception":"%s"}
              """;
            out = String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("idCita") int idCita) throws Exception {
        String out = "";
        ControllerCitas cc = new ControllerCitas();
        try {
            int respuesta = cc.delete(idCita);
            System.out.println(respuesta);
            if (respuesta == 1) {
                out = """
                      {"Mensaje":"Cita cancelada con éxito"}
                      """;
            } else {
                out = """
                      {"Mensaje":"¡Error! Inténtelo más tarde"}
                      """;
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("reactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactive(@QueryParam("idCita") int idCita) throws Exception {
        String out = "";
        ControllerCitas cc = new ControllerCitas();
        try {
            int respuesta = cc.reactive(idCita);
            System.out.println(respuesta);
            if (respuesta == 1) {
                out = """
                      {"Mensaje":"Cita reactivada con éxito"}
                      """;
            } else {
                out = """
                      {"Mensaje":"¡Error! Inténtelo más tarde"}
                      """;
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
