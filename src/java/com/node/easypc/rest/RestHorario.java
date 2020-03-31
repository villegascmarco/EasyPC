/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.rest;

import com.google.gson.Gson;
//import com.node.easypc.comandos.ComandosSesion;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.node.easypc.controlador.ControladorHorario;
import com.node.easypc.modelo.*;
import java.sql.Timestamp;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;

@Path("horarios")
public class RestHorario extends Application {

    @POST
    @Path("listado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar(@FormParam("idPersona") @DefaultValue("") String idPersona,
            @FormParam("token") @DefaultValue("") String token) {
        ControladorHorario cH = new ControladorHorario();
        Persona persona = new Persona(idPersona, token);

        String res = cH.listarHorarios(persona);
        String out = new Gson().toJson(res);
        System.out.println(res);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @POST
    @Path("actualizarHorario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@FormParam("idHorario") @DefaultValue("") String idHorario,
            @FormParam("diaServicio") @DefaultValue("") String diaServicio,
            @FormParam("horaInicio") @DefaultValue("") String horaInicio,
            @FormParam("horaFin") @DefaultValue("") String horaFin,
            @FormParam("token") @DefaultValue("") String token,
            @FormParam("idPersona") @DefaultValue("") String idPersona) {

        ControladorHorario cH = new ControladorHorario();
        Horario h = new Horario();
        Persona p = new Persona(idPersona, token);

        h.setIdHorario(idHorario);
        h.setDiaServicio(diaServicio);
        h.setHoraInicio(horaInicio);
        h.setHoraFin(horaFin);
        String res = "";

        cH.actualizarHorario(h, p);
        res = cH.listarHorarios(p);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @POST
    @Path("eliminarHorario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@FormParam("idHorario") @DefaultValue("") String idHorario,
            @FormParam("diaServicio") @DefaultValue("") String diaServicio,
            @FormParam("horaInicio") @DefaultValue("") String horaInicio,
            @FormParam("horaFin") @DefaultValue("") String horaFin,
            @FormParam("token") @DefaultValue("") String token,
            @FormParam("idPersona") @DefaultValue("") String idPersona) {

        ControladorHorario cH = new ControladorHorario();

        Horario h = new Horario();
        Persona persona = new Persona(idPersona, token);

        h.setIdHorario(idHorario);
        h.setDiaServicio(diaServicio);
        h.setHoraInicio(horaInicio);
        h.setHoraFin(horaFin);
        String res = "";

        cH.eliminarHorario(h, persona);
        res = cH.listarHorarios(persona);

        return Response.status(Response.Status.OK).entity(res).build();
    }

    @POST
    @Path("insertarHorario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertar(@FormParam("diaServicio") @DefaultValue("") String diaServicio,
            @FormParam("horaInicio") @DefaultValue("") String horaInicio,
            @FormParam("horaFin") @DefaultValue("") String horaFin,
            @FormParam("token") @DefaultValue("") String token,
            @FormParam("idPersona") @DefaultValue("") String idPersona) {

        ControladorHorario cH = new ControladorHorario();

        Horario h = new Horario();
        Persona persona = new Persona(idPersona, token);

        String idH = new Timestamp(System.currentTimeMillis()).getTime() + "";
        h.setIdHorario(idH);
        h.setDiaServicio(diaServicio);
        h.setHoraInicio(horaInicio);
        h.setHoraFin(horaFin);

        String out = "";
        cH.insertarHorario(h, persona);
        out = cH.listarHorarios(persona);

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @POST
    @Path("buscarHorario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@FormParam("diaServicio") @DefaultValue("") String diaServicio,
            @FormParam("idPersona") @DefaultValue("") String idPersona,
            @FormParam("token") @DefaultValue("") String token) {
        ControladorHorario cH = new ControladorHorario();
        Persona p = new Persona(idPersona, token);
        Horario h = new Horario();
        h.setDiaServicio(diaServicio);

        String out = cH.buscarHorario(h, p);
        return Response.status(Response.Status.OK).entity(out).build();
    }

}
