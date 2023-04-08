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
}
