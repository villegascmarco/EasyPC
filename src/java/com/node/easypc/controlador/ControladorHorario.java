/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.controlador;

import com.node.easypc.comandos.ComandosHorario;
import com.node.easypc.modelo.Horario;
import com.node.easypc.modelo.Persona;

/**
 *
 * @author pollo
 */
public class ControladorHorario {
    
    ComandosHorario cH = new ComandosHorario();
    ControladorPersona cP = new ControladorPersona();
    
    public String listarHorarios(Persona persona, String idE) {
        String response = "";
        if (cP.validarToken(persona)) {
            response = cH.listarHorarios(idE);
            return response;
        }
        return response;
    }
    
    public String actualizarHorario(Horario h, Persona persona) {
        String response = "";
        if (cP.validarToken(persona)) {
            response = cH.actualizarHorario(h);
            return response;
        }
        return response;
    }
    
    public String eliminarHorario(Horario h, Persona persona) {
        String response = "";
        if (cP.validarToken(persona)) {
            response = cH.eliminarHorario(h);
            return response;
        }
        return response;
    }
    
    public String insertarHorario(Horario h, Persona p) {
        String response = "";
        if (cP.validarToken(p)) {
            response = cH.insertarHorario(h);
            return response;
        }
        return response;
    }
    
    public String buscarHorario(Horario h, Persona p) {
        String response = "";
        if (cP.validarToken(p)) {
            response = cH.buscarHorario(h);
            return response;
        }
        return response;
    }
}
