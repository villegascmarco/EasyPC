package com.node.easypc.rest;

import com.node.easypc.controlador.ControladorUsuario;
import com.node.easypc.modelo.Usuario;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author logic
 */
@Path("usuario")
public class RestUsuario extends Application {

    private ControladorUsuario cu;

    private String json;

    @POST
    @Path("ingresar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresar(@FormParam("usuario") String usuario) {
        cu = new ControladorUsuario();

        try {
            json = cu.iniciarSesion(new Usuario(usuario));
        } catch (Exception e) {
            e.printStackTrace();
            json = null;
        } finally {
            if (json == null) {
                return Response.status(Response.Status.NO_CONTENT).entity(json).build();
            }
        }
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(@FormParam("usuario") String usuario) {
        cu = new ControladorUsuario();
        try {
            json = cu.registrarUsuario(new Usuario(usuario));
        } catch (Exception e) {
            e.printStackTrace();
            json = null;
        } finally {
            if (json == null) {
                return Response.status(Response.Status.NO_CONTENT).entity(json).build();
            }
        }
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @POST
    @Path("modificar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificar(@FormParam("usuario") String usuario) {
        cu = new ControladorUsuario();

        try {
            json = cu.modificarUsuario(new Usuario(usuario), false);
        } catch (Exception e) {
            e.printStackTrace();
            json = null;
        } finally {
            if (json == null) {
                return Response.status(Response.Status.NO_CONTENT).entity(json).build();
            }
        }
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @POST
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@FormParam("usuario") String usuario,
            @FormParam("usuarioBuscado") String usuarioBuscado) {
        cu = new ControladorUsuario();
        try {
            json = cu.buscarUsuarios(new Usuario(usuario), new Usuario(usuarioBuscado));
        } catch (Exception e) {
            e.printStackTrace();
            json = null;
        } finally {
            if (json == null) {
                return Response.status(Response.Status.NO_CONTENT).entity(json).build();
            }
        }
        return Response.status(Response.Status.OK).entity(json).build();
    }
}
