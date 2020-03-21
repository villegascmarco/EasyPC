/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.villegas.easypc.controlador;

import com.google.gson.Gson;
import com.villegas.easypc.comandos.ComandosAdministrador;
import com.villegas.easypc.commons.Commons;
import com.villegas.easypc.modelo.Administrador;
import java.sql.Timestamp;

/**
 *
 * @author marco
 */
public class ControladorAdministrador {

    private final ControladorPersona ctrlPersona = new ControladorPersona();
    private final ComandosAdministrador cmdAdministrador = new ComandosAdministrador();

    /**
     *
     * @param administrador Objeto del admin que quiere iniciar sesion
     *
     * @return
     */
    public String iniciarSesion(Administrador administrador) {
        String respuesta = ctrlPersona.iniciarSesion(administrador.getPersona(), Commons.COLECCION_ADMINISTRADOR);

        administrador = new Administrador(respuesta);

        if (administrador.getIdAdministrador() != null) {
            administrador.getPersona().setToken();
            modificarDatos(administrador, true);
        }

        return administrador.toString();

    }

    /**
     *
     * @param administrador Objeto del nuevo administrador que se va a crear (Un
     * administrador sólo puede ser dado de alta por otro)
     *
     * @param viejo Objeto del admin que quiere dar de alta a otro
     *
     * @return
     */
    public String crearAdministrador(Administrador administrador, Administrador viejo) {

        if (!ctrlPersona.validarToken(viejo.getPersona())) {
            return "No tiene permisos para agregar a un administrador";
        }

        if (!ctrlPersona.validarAutenticidadCorreo(administrador.getPersona())) {
            return "Correo no disponible";
        }

        String id = new Timestamp(System.currentTimeMillis()).getTime() + "";

        administrador.setIdAdministrador(id);
        administrador.getPersona().setIdPersona(id);
        administrador.getPersona().setEstatus(1);

        administrador.getPersona().clearToken();

        if (ctrlPersona.crearPersona(administrador.getPersona())) {

            if (cmdAdministrador.crearAdministrador(administrador)) {
                return administrador.toString();
            }

        }

        return "Ocurrió un problema";
    }

    /**
     * @param administrador Recibe el objeto de la sesión que va a modificar
     * (información personal, un admin no puede modificar la info de otro a
     * excepcion del estatus)
     *
     * @param actualizarToken Se necesita para cuando se inicia sesion poder
     * actualizar el token en la bd sin que haya problemas
     *
     * @return
     */
    public String modificarDatos(Administrador administrador, boolean actualizarToken) {

        if (ctrlPersona.validarActualizacion(administrador.getPersona())) {
            return "Correo no disponible";
        }
        if (!actualizarToken) {
            if (!ctrlPersona.validarToken(administrador.getPersona())) {
                return "No tiene permisos para modifcar datos";
            }
        }

        if (ctrlPersona.modificarDatos(administrador.getPersona())) {

            return cmdAdministrador.modificarDatos(administrador);
        }
        return null;
    }

    /**
     *
     * @param administrador El objeto del admin al que se le cambiará el estaus
     *
     * @param viejo El objeto del admin que está modificando el estatus, por lo
     * que es a este al que validamos su token
     *
     * @return
     */
    public String modificarEstatus(Administrador administrador, Administrador viejo) {

        if (!ctrlPersona.validarToken(viejo.getPersona())) {
            return "No tiene permisos para modifcar datos";
        }

        if (!ctrlPersona.validarEstatus(administrador.getPersona())) {
            return "Ingrese un estatus válido";
        }

        if (ctrlPersona.modificarEstatus(administrador.getPersona())) {

            if (cmdAdministrador.modificarEstatus(administrador)) {
                return "Exito";
            }
        }
        return "No se pudo concretar";

    }

    /**
     *
     * @param administrador Objeto de la persona que quiere cerrar su sesión
     *
     * @return
     */
    public String cerrarSesion(Administrador administrador) {
        Gson gson = new Gson();

        if (!ctrlPersona.validarToken(administrador.getPersona())) {
            return null;
        }

        if (ctrlPersona.cerrarSesion(administrador.getPersona())) {

            if (cmdAdministrador.cerrarSesion(administrador)) {
                return gson.toJson("Éxito");
            }
        }
        return null;

    }

    /**
     *
     * @param administrador Objeto de la persona que solicita el listado
     *
     * @return
     */
    public String listado(Administrador administrador) {
        if (!ctrlPersona.validarToken(administrador.getPersona())) {
            return "Token inválido";
        }
        return cmdAdministrador.listado();
    }

}
