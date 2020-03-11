/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.villegas.easypc.controlador;

import com.villegas.easypc.baseDatos.BaseDatos;
import com.villegas.easypc.comandos.ComandosAdministrador;
import com.villegas.easypc.modelo.Administrador;
import com.villegas.easypc.modelo.Persona;

/**
 *
 * @author marco
 */
public class ControladorAdministrador {

    public boolean validarDatosInicio(Persona persona) {
        return persona.getCorreo() != null
                && persona.getContrasenia() != null
                && !persona.getCorreo().trim().isEmpty()
                && !persona.getContrasenia().trim().isEmpty();
    }

    public boolean validarAutenticidadCorreo(Persona persona) {
        ComandosAdministrador cmdAdministrador = new ComandosAdministrador();

        return cmdAdministrador.validarAutenticidadCorreo(persona);
    }

    public boolean validarActualizacion(Persona persona) {
        ComandosAdministrador cmdAdministrador = new ComandosAdministrador();

        return cmdAdministrador.validarActualizacion(persona);

    }

    public String buscarAdministrador(Administrador administrador) {
        ComandosAdministrador cmdAdministrador = new ComandosAdministrador();

        if (!validarDatosInicio(administrador.getPersona())) {
            return null;
        }

        return cmdAdministrador.iniciarSesion(administrador.getPersona());
        //return baseDatos.conectar();
    }

    public String crearAdministrador(Administrador administrador, Administrador viejo) {
        ComandosAdministrador cmdAdministrador = new ComandosAdministrador();

        if (validarAutenticidadCorreo(administrador.getPersona())) {
            return "Correo no disponible";
        }

        if (viejo.getNumero().trim() == null) {
            return "No tiene permisos para agregar a un administrador";
        }

        if (cmdAdministrador.crearAdministrador(administrador)) {
            return administrador.toString();
        }
        return "Ocurrió un problema";
    }

    public String modificarDatos(Administrador administrador) {
        ComandosAdministrador cmdAdministrador = new ComandosAdministrador();

        if (validarActualizacion(administrador.getPersona())) {
            return "Correo no disponible";
        }

        if (cmdAdministrador.actualizar(administrador)) {
            return administrador.toString();
        }

        return "Ocurrió un problema";
    }

    public String modificarEstatus(String correo, int estatus) {
        ComandosAdministrador cmdAdministrador = new ComandosAdministrador();
        if (estatus < 0 || estatus > 1) {
            return "Ingrese un estatus válido";
        }
        if (cmdAdministrador.modificarEstatus(correo, estatus)) {
            return "Exito";
        }
        return "No se pudo concretar";

    }

    public String listado(String correo) {
        ComandosAdministrador cmdAdministrador = new ComandosAdministrador();

        return cmdAdministrador.listado();
    }
}
