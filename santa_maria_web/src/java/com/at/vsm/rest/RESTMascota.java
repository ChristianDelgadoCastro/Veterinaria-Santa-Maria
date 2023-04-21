/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.rest;

import com.at.vsm.core.ControllerMascotas;
import com.at.vsm.model.Mascota;
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
@Path("mascota")
public class RESTMascota {
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filter") @DefaultValue("") String filter){
        String out = null;
        ControllerMascotas cm = null;
        List<Mascota> mascotas = null;
        try {
            cm = new ControllerMascotas();
            mascotas = cm.getAll(filter);
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
    public Response getAllActives(@QueryParam("filter") @DefaultValue("") String filter){
        String out = null;
        ControllerMascotas cm = null;
        List<Mascota> mascotas = null;
        try {
            cm = new ControllerMascotas();
            mascotas = cm.getAllActives(filter);
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
    public Response getAllInactives(@QueryParam("filter") @DefaultValue("") String filter){
        String out = null;
        ControllerMascotas cm = null;
        List<Mascota> mascotas = null;
        try {
            cm = new ControllerMascotas();
            mascotas = cm.getAllInactives(filter);
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
    public Response search(@QueryParam("datoBusqueda") @DefaultValue("") String datoBusqueda){
        String out = null;
        ControllerMascotas cm = null;
        List<Mascota> mascotas = null;
        try {
            cm = new ControllerMascotas();
            mascotas = cm.search(datoBusqueda);
            out = new Gson().toJson(mascotas);
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
    public Response searchActives(@QueryParam("datoBusqueda") @DefaultValue("") String datoBusqueda){
        String out = null;
        ControllerMascotas cm = null;
        List<Mascota> mascotas = null;
        try {
            cm = new ControllerMascotas();
            mascotas = cm.searchActives(datoBusqueda);
            out = new Gson().toJson(mascotas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("searchInactives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchInactives(@QueryParam("datoBusqueda") @DefaultValue("") String datoBusqueda){
        String out = null;
        ControllerMascotas cm = null;
        List<Mascota> mascotas = null;
        try {
            cm = new ControllerMascotas();
            mascotas = cm.searchInactives(datoBusqueda);
            out = new Gson().toJson(mascotas);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosMascota") @DefaultValue("") String datosMascota){
        String out = null;
        Gson gson = new Gson();
        Mascota mas = null;
        ControllerMascotas cm = new ControllerMascotas();
        try {
            mas = gson.fromJson(datosMascota, Mascota.class);
            if (mas.getIdMascota()== 0){
                cm.insert(mas);
                System.out.println("Entre al insert");
            }
            else{
                cm.update(mas);
                System.out.println("Entre al update");
            }
            out = gson.toJson(mas);
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
            out = """
                  {"exception":"Formato JSON de datos incorrecto."}
                  """;
        } catch (Exception e){
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
    public Response delete(@QueryParam("idMascota") int idMascota) throws Exception{
        String out = "";
        ControllerMascotas cc = new ControllerMascotas();
        try{
            int respuesta = cc.delete(idMascota);
            System.out.println(respuesta);
            if (respuesta == 1){
                out = """
                      {"Mensaje":"Mascota eliminada con éxito"}
                      """;
            }
            else{
                out = """
                      {"Mensaje":"¡Error! Inténtelo más tarde"}
                      """;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("reactive")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactive(@QueryParam("idMascota") int idMascota) throws Exception{
        String out = "";
        ControllerMascotas cc = new ControllerMascotas();
        try{
            int respuesta = cc.reactive(idMascota);
            System.out.println(respuesta);
            if (respuesta == 1){
                out = """
                      {"Mensaje":"Mascota reactivada con éxito"}
                      """;
            }
            else{
                out = """
                      {"Mensaje":"¡Error! Inténtelo más tarde"}
                      """;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
