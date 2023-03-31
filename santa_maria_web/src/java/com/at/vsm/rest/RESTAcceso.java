/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.rest;

import com.google.gson.Gson;
import com.at.vsm.core.ControllerAcceso;
import com.at.vsm.model.Empleado;
import com.at.vsm.model.Cliente;
import com.at.vsm.model.Usuario;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author david
 */
@Path("log")
public class RESTAcceso {

    ControllerAcceso ca = new ControllerAcceso();

    @Path("in")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("nombreUsuario") @DefaultValue("") String nombreUsuario,
            @FormParam("contrasenia") @DefaultValue("") String contrasenia) throws Exception {

        String out = null;
        Cliente c = null;
        Empleado e = null;
        System.out.println("nombre: " + nombreUsuario + ", contraseña: " + contrasenia);
        try {
            c = ca.accederCliente(nombreUsuario, contrasenia);
            if (c != null) {
                out = new Gson().toJson(c);
            } else {
                e = ca.accederEmpleado(nombreUsuario, contrasenia);
                if (e != null) {
                    out = new Gson().toJson(e);
                } else {
                    out = "{\"error\":\"Datos de acceso incorrectos. Intente nuevamente.\"}";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Error en el servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

   @Path("out")
   @POST
   @Produces(MediaType.APPLICATION_JSON)
    public Response out(@FormParam("idUsuario") @DefaultValue("") String idUsuario) throws Exception{
        String out = "";
        try {
           Usuario u = new Usuario();
           u.setIdUsuario(Integer.parseInt(idUsuario));
           ca.eliminarToken(u);
           out = """
                  {"cierre de sesion exitoso":"Token eliminado con exito"}
                  """;
       } catch (Exception e) {
           out = """
                  {"Error":"Cierre de sesion no valida, intente mas tarde"}
                  """;
       }
        System.out.println(out);
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
