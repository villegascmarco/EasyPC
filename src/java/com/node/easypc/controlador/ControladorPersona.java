/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.controlador;

import com.node.easypc.comandos.ComandosPersona;
import com.node.easypc.modelo.Persona;

/**
 *
 * @author marco
 */
public class ControladorPersona {

    private ComandosPersona cmdPersona = new ComandosPersona();

    public String iniciarSesion(Persona persona, String coleccion) {

        if (validarDatosInicio(persona)) {
            return cmdPersona.iniciarSesion(persona, coleccion);
        }
        return null;

    }

    /**
     * Validamos que los datos necesarios para iniciar sesión se hayan ingresado
     * correctamente
     *
     * @param persona
     * @return
     */
    public boolean validarDatosInicio(Persona persona) {
        return persona.getCorreo() != null
                || persona.getContrasenia() != null
                || !persona.getCorreo().trim().isEmpty()
                || !persona.getContrasenia().trim().isEmpty();
    }

    public boolean crearPersona(Persona persona) {
        return cmdPersona.crearPersona(persona);
    }

    /**
     * Validamos que haya un token
     *
     * @param persona
     * @return
     */
    public boolean validarToken(Persona persona) {
        if (persona.getToken() == null || persona.getToken().trim().isEmpty()) {
            return false;
        }
        return cmdPersona.validarToken(persona);
    }

    /**
     * Validamos que el correo sea unico en el sistema
     * 
     * @param persona
     * @return 
     */
    public boolean validarAutenticidadCorreo(Persona persona) {

        return cmdPersona.validarAutenticidadCorreo(persona);
    }

    public boolean validarActualizacion(Persona persona) {

        return cmdPersona.validarActualizacion(persona);

    }

    public boolean validarEstatus(Persona persona) {
        int estatus = persona.getEstatus();

        return estatus == 0 || estatus == 1;
    }

    public boolean modificarDatos(Persona persona) {
        return cmdPersona.modificarDatos(persona) != null;
    }

    public boolean modificarEstatus(Persona persona) {
        return cmdPersona.modificarEstatus(persona);

    }

    public boolean cerrarSesion(Persona persona) {
        return cmdPersona.cerrarSesion(persona);
    }
}
