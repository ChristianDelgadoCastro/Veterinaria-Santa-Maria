/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.rest;

import com.at.vsm.core.ControllerEmpleado;
import com.at.vsm.model.Empleado;
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
@Path("empleado")
public class RESTEmpleado {
    @GET
    @Path("getAllActives")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActives(@QueryParam("filter") @DefaultValue("") String filter){
        String out = null;
        ControllerEmpleado ce = null;
        List<Empleado> empleados = null;
        try {
            ce = new ControllerEmpleado();
            empleados = ce.getAllActives(filter);
            out = new Gson().toJson(empleados);
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
        ControllerEmpleado ce = null;
        List<Empleado> empleados = null;
        try {
            ce = new ControllerEmpleado();
            empleados = ce.getAllInactives(filter);
            out = new Gson().toJson(empleados);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado){
        String out = null;
        Gson gson = new Gson();
        Empleado emp = null;
        ControllerEmpleado ce = new ControllerEmpleado();
        try {
            emp = gson.fromJson(datosEmpleado, Empleado.class);
            if (emp.getIdEmpleado()== 0){
                ce.insert(emp);
            }
            else{
                ce.update(emp);
            }
            out = gson.toJson(emp);
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
    public Response delete(@QueryParam("idEmpleado") int idEmpleado) throws Exception{
        String out = "";
        ControllerEmpleado ce = new ControllerEmpleado();
        try{
            int respuesta = ce.delete(idEmpleado);
            System.out.println(respuesta);
            if (respuesta == 1){
                out = """
                      {"Mensaje":"Empleado eliminado con éxito"}
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
    public Response reactive(@QueryParam("idEmpleado") int idEmpleado) throws Exception{
        String out = "";
        ControllerEmpleado cc = new ControllerEmpleado();
        try{
            int respuesta = cc.reactive(idEmpleado);
            System.out.println(respuesta);
            if (respuesta == 1){
                out = """
                      {"Mensaje":"Empleado reactivado con éxito"}
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
