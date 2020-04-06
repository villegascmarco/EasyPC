 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.rest;

import com.google.gson.Gson;
import com.node.easypc.comandos.ComandosEstacionamiento;
import com.node.easypc.controlador.ControladorPersona;
import com.node.easypc.modelo.Administrador;
import com.node.easypc.modelo.Estacionamiento;
import com.node.easypc.modelo.Persona;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Esau
 */


@Path("estacionamiento")
public class RestEstacionamiento
{

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("active") String active,
                           @QueryParam("persona") String token)
    {
        ComandosEstacionamiento dao = new ComandosEstacionamiento();
        String out;
        Gson gson = new Gson();
        
        System.out.println(gson.fromJson(token, Administrador.class));

        /*
         * Autenticación usuario
         */
        out = "{\"error\":\"autenticación\"}";
        Administrador admin = gson.fromJson(token, Administrador.class);
        ControladorPersona controladorPersona = new ControladorPersona();
        boolean validado = controladorPersona.validarToken(admin.getPersona());
        System.out.println(validado);
        if (!validado)
            return Response.status(Response.Status.OK).entity(out).build();

        List<Estacionamiento> list = dao.getAll(Boolean.valueOf(active), "EasyPC", admin.getIdAdministrador());
        out = gson.toJson(list);
        // System.out.println(list.toString());
        System.out.println(out);

        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("user/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
    {
        ComandosEstacionamiento dao = new ComandosEstacionamiento();
        String out;
        Gson gson = new Gson();

        List<Estacionamiento> list = dao.getAll(true, "EasyPC");
        out = gson.toJson(list);
        // System.out.println(list.toString());
        System.out.println(out);

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @POST
    @Path("insert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(
            @FormParam("estacionamiento") String estacionamientoStr,
            @FormParam("persona") String token)
    {
        String out;
        Gson gson = new Gson();
        Estacionamiento estacionamiento;
        ComandosEstacionamiento dao = new ComandosEstacionamiento();

        /*
         * Autenticación usuario
         */
        out = "{\"error\":\"autenticación\"}";
        ControladorPersona controladorPersona = new ControladorPersona();
        boolean validado = controladorPersona.validarToken(gson.fromJson(token,
                Administrador.class).getPersona());
        if (!validado)
            return Response.status(Response.Status.OK).entity(out).build();

        out = "{\"ok\":\"operación exitosa\"}";
        estacionamiento = gson.fromJson(estacionamientoStr,
                Estacionamiento.class);
        dao.insert(estacionamiento, "EasyPC");

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(
            @FormParam("estacionamiento") String estacionamientoStr,
            @FormParam("persona") String token)
    {
        String out;
        Gson gson = new Gson();
        Estacionamiento estacionamiento;
        ComandosEstacionamiento dao = new ComandosEstacionamiento();

        /*
         * Autenticación usuario
         */
        out = "{\"error\":\"autenticación\"}";
        ControladorPersona controladorPersona = new ControladorPersona();
        boolean validado = controladorPersona.validarToken(gson.fromJson(token,
                Administrador.class).getPersona());
        if (!validado)
            return Response.status(Response.Status.OK).entity(out).build();

        out = "{\"ok\":\"operación exitosa\"}";
        estacionamiento = gson.fromJson(estacionamientoStr,
                Estacionamiento.class);
        dao.update(estacionamiento, "EasyPC");

        System.out.println(estacionamiento.getId());

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @PUT
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(
            @FormParam("estacionamiento") String estacionamientoStr,
            @FormParam("persona") String token)
    {
        String out;
        Gson gson = new Gson();
        Estacionamiento estacionamiento;
        ComandosEstacionamiento dao = new ComandosEstacionamiento();

        /*
         * Autenticación usuario
         */
        out = "{\"error\":\"autenticación\"}";
        ControladorPersona controladorPersona = new ControladorPersona();
        boolean validado = controladorPersona.validarToken(gson.fromJson(token,
                Administrador.class).getPersona());
        if (!validado)
            return Response.status(Response.Status.OK).entity(out).build();

        out = "{\"ok\":\"operación exitosa\"}";
        estacionamiento = gson.fromJson(estacionamientoStr,
                Estacionamiento.class);
        dao.delete(estacionamiento, "EasyPC");

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @PUT
    @Path("undelete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unDelete(
            @FormParam("estacionamiento") String estacionamientoStr,
            @FormParam("persona") String token)
    {
        String out;
        Gson gson = new Gson();
        Estacionamiento estacionamiento;
        ComandosEstacionamiento dao = new ComandosEstacionamiento();

        /*
         * Autenticación usuario
         */
        out = "{\"error\":\"autenticación\"}";
        ControladorPersona controladorPersona = new ControladorPersona();
        boolean validado = controladorPersona.validarToken(gson.fromJson(token,
                Administrador.class).getPersona());
        if (!validado)
            return Response.status(Response.Status.OK).entity(out).build();

        /*
         * Operación cambiar estatus
         */
        out = "{\"ok\":\"operación exitosa\"}";
        estacionamiento = gson.fromJson(estacionamientoStr,
                Estacionamiento.class);
        dao.unDelete(estacionamiento, "EasyPC");

        return Response.status(Response.Status.OK).entity(out).build();
    }
}
