/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.rest;

import com.at.vsm.core.ControllerCliente;
import com.at.vsm.model.Cliente;
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

@Path("cliente")
public class RESTCliente {
    @GET
    @Path("getAllActives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActives(@QueryParam("filter") @DefaultValue("") String filter){
        String out = null;
        ControllerCliente cl = null;
        List<Cliente> clientes = null;
        try {
            cl = new ControllerCliente();
            clientes = cl.getAllActives(filter);
            out = new Gson().toJson(clientes);
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
        ControllerCliente cl = null;
        List<Cliente> clientes = null;
        try {
            cl = new ControllerCliente();
            clientes = cl.getAllInactives(filter);
            out = new Gson().toJson(clientes);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosCliente") @DefaultValue("") String datosCliente){
        String out = null;
        Gson gson = new Gson();
        Cliente cli = null;
        ControllerCliente cc = new ControllerCliente();
        try {
            cli = gson.fromJson(datosCliente, Cliente.class);
            if (cli.getIdCliente() == 0){
                cc.insert(cli);
            }
            else{
                cc.update(cli);
            }
            out = gson.toJson(cli);
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
    public Response delete(@QueryParam("idCliente") int idCliente) throws Exception{
        String out = "";
        ControllerCliente cc = new ControllerCliente();
        try{
            int respuesta = cc.delete(idCliente);
            System.out.println(respuesta);
            if (respuesta == 1){
                out = """
                      {"Mensaje":"Cliente eliminado con éxito"}
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
    public Response reactive(@QueryParam("idCliente") int idCliente) throws Exception{
        String out = "";
        ControllerCliente cc = new ControllerCliente();
        try{
            int respuesta = cc.reactive(idCliente);
            System.out.println(respuesta);
            if (respuesta == 1){
                out = """
                      {"Mensaje":"Cliente reactivado con éxito"}
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
