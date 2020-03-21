/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.villegas.easypc.rest;

import com.villegas.easypc.controlador.ControladorAdministrador;
import com.villegas.easypc.modelo.Administrador;
import com.villegas.easypc.modelo.Persona;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author marco
 */
@Path("administrador")
public class RestAdministrador extends Application {

    @Path("ingresar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response ingresar(
            @FormParam("administrador")
            @DefaultValue("0") String administrador) {

        ControladorAdministrador ctrlAdministrador = new ControladorAdministrador();
        String json = null;
        try {
            Administrador a = new Administrador(administrador);

            json = ctrlAdministrador.iniciarSesion(a);

        } catch (Exception e) {
            json = null;
        } finally {
            if (json == null) {
                return Response.status(Response.Status.NO_CONTENT).entity(json).build();
            }
        }
        return Response.status(Response.Status.OK).entity(json).build();

    }

    @Path("crear")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response crear(
            @FormParam("administrador")
            @DefaultValue("0") String administrador,
            @FormParam("viejo")
            @DefaultValue("0") String viejo) {

        ControladorAdministrador ctrlAdministrador = new ControladorAdministrador();
        String json = null;
        try {
            Administrador a = new Administrador(administrador);

            Administrador v = new Administrador(viejo);

            json = ctrlAdministrador.crearAdministrador(a, v);

        } catch (Exception e) {
            json = null;
        } finally {
            if (json == null) {
                return Response.status(Response.Status.NO_CONTENT).entity(json).build();
            }

        }
        return Response.status(Response.Status.OK).entity(json).build();

    }

    @Path("modificar")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response modificar(
            @FormParam("administrador")
            @DefaultValue("0") String administrador) {

        ControladorAdministrador ctrlAdministrador = new ControladorAdministrador();
        String json = null;
        try {
            Administrador a = new Administrador(administrador);

            json = ctrlAdministrador.modificarDatos(a, false);

        } catch (Exception e) {
            json = "Ocurrió un problema";
        }
        return Response.status(Response.Status.OK).entity(json).build();

    }

    /**
     * Para este unico caso devolvemos texto con el estado de la actualizacion
     *
     * @param correo
     * @param estatus
     * @param viejo
     * @return
     */
    @Path("modificarEstatus")
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    public Response modificarEstatus(
            @FormParam("correo")
            @DefaultValue("0") String correo,
            @FormParam("estatus")
            @DefaultValue("0") int estatus,
            @FormParam("viejo")
            @DefaultValue("0") String viejo) {

        ControladorAdministrador ctrlAdministrador = new ControladorAdministrador();
        String json = null;
        try {
            Administrador v = new Administrador(viejo);
            Administrador administrador = new Administrador();
            Persona persona = new Persona(estatus, correo);

            administrador.setPersona(persona);

            json = ctrlAdministrador.modificarEstatus(administrador, v);

        } catch (Exception e) {
            json = "Ocurrió un problema";
        }
        return Response.status(Response.Status.OK).entity(json).build();

    }

    @Path("listado")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response listado(
            @FormParam("administrador")
            @DefaultValue("0") String administrador) {

        ControladorAdministrador ctrlAdministrador = new ControladorAdministrador();
        String json = null;
        try {
            Administrador a = new Administrador(administrador);

            json = ctrlAdministrador.listado(a);

        } catch (Exception e) {
            json = "Ocurrió un problema";
        }
        return Response.status(Response.Status.OK).entity(json).build();

    }

    @Path("cerrarSesion")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response cerrarSesion(
            @FormParam("administrador")
            @DefaultValue("0") String administrador) {

        ControladorAdministrador ctrlAdministrador = new ControladorAdministrador();
        String json = null;
        try {
            Administrador a = new Administrador(administrador);

            json = ctrlAdministrador.cerrarSesion(a);

        } catch (Exception e) {
            json = "Ocurrió un problema";
        }
        return Response.status(Response.Status.OK).entity(json).build();

    }
}
