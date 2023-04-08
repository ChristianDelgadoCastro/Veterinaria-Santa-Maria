package org.nerverstrong.veterinaria.rest;

import com.google.gson.Gson;
import com.veterinaria.controller.ControllerCliente;
import com.veterinaria.model.Cliente;
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

@Path("cliente")
public class RESTCliente {
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro)
    {
        String out = null;
        ControllerCliente cc = new ControllerCliente();
        List<Cliente> clientes = null;
        Gson gson = new Gson();
        
        try
        {
            clientes = cc.getAll(filtro);
            out = gson.toJson(clientes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            out = "{\"error\":\"Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador de sistemas.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }

    
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("cliente") @DefaultValue("{}") String jsonCliente)
    {
        System.out.println(jsonCliente);
        String out = null;
        ControllerCliente cc = new ControllerCliente();
        Cliente cliente = null;
        
        try
        {
            if(jsonCliente == null || jsonCliente.equals("{}"))
            {
                out = "{\"error:\":\"No se recibieron datos del Cliente para guardar.\"}";
            }
            else
            {
                cliente = new Gson().fromJson(jsonCliente, Cliente.class);
                if(cliente.getId() == 0)
                {
                    cc.insert(cliente);
                }
                else
                {
                    cc.update(cliente);
                }
                out = new Gson().toJson(cliente);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            out = "{\"error\":\"Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador de sistemas.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("idCliente") @DefaultValue("0") int id)
    {
        String out = null;
        
        //Creamos el objeto de tipo ControllerCliente
        ControllerCliente cc = new ControllerCliente();
        
        try
        {
            cc.delete(id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            out = "{\"error\":\"Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador de sistemas.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
}





