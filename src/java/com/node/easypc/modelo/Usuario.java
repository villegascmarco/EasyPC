package com.node.easypc.modelo;

import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

/**
 *
 * @author logic
 */
public class Usuario {
    
    private int idUsuario;
    private Persona persona;
    
    public Usuario() {
    }
    
    public Usuario (int idUsuario, Persona persona) {
        this.idUsuario = idUsuario;
        this.persona = persona;
    }
    
    public Usuario (String JSONUsuario) {
        Usuario aux = new Gson().fromJson(JSONUsuario, getClass());
        this.idUsuario = aux.getIdUsuario();
        this.persona = aux.getPersona();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public DBObject toBObject() {
        
        BasicDBObjectBuilder personaDOC = BasicDBObjectBuilder.start();
        personaDOC.append("idPersona", persona.getIdPersona());
        personaDOC.append("nombre", persona.getNombre());
        personaDOC.append("apellido", persona.getApellido());
        personaDOC.append("estatus", persona.getEstatus());
        personaDOC.append("correo", persona.getCorreo());
        personaDOC.append("contrasenia", persona.getContrasenia());
        personaDOC.append("foto", persona.getFoto());
        personaDOC.append("token", persona.getToken());
        
        BasicDBObjectBuilder usuarioDOC = BasicDBObjectBuilder.start();
        usuarioDOC.append("idUsuario", getIdUsuario());
        usuarioDOC.append("persona", personaDOC.get());
        
        return usuarioDOC.get();
        
    }
    
}
