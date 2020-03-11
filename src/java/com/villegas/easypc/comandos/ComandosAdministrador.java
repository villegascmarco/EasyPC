/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.villegas.easypc.comandos;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.model.Updates;
import com.villegas.easypc.baseDatos.BaseDatos;
import com.villegas.easypc.commons.Commons;
import com.villegas.easypc.modelo.Administrador;
import com.villegas.easypc.modelo.Persona;
import java.util.LinkedList;

/**
 *
 * @author marco
 */
public class ComandosAdministrador {
    
    public String iniciarSesion(Persona persona) {
        String respuesta = null;
        
        Administrador administrador = null;
        
        BaseDatos baseDatos = new BaseDatos();
        
        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);
        
        DBObject query = BasicDBObjectBuilder.start()
                .add("persona.correo", persona.getCorreo())
                .add("persona.contrasenia", persona.getContrasenia())
                .add("persona.estatus", 1)
                .get();
        
        DBCursor dBCursor = dBCollection.find(query);
        
        if (dBCursor.hasNext()) {
            String kk = dBCursor.next().toString();
            administrador = new Administrador(kk);
        }
        
        return administrador.toString();
    }
    
    public boolean validarAutenticidadCorreo(Persona persona) {
        boolean respuesta = false;
        
        BaseDatos baseDatos = new BaseDatos();
        
        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);
        
        DBObject query = BasicDBObjectBuilder.start()
                .add("persona.correo", persona.getCorreo())
                .get();
        
        DBCursor dBCursor = dBCollection.find(query);
        
        if (dBCursor.hasNext()) {
            respuesta = true;
            
        }
        
        return respuesta;
        
    }
    
    public boolean crearAdministrador(Administrador administrador) {
        boolean respuesta = true;
        
        BaseDatos baseDatos = new BaseDatos();
        try {
            
            DBCollection dBCollectionAdministrador = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);
            
            baseDatos.insertarDocumento(dBCollectionAdministrador, administrador.toString());
            
            DBCollection dBCollectionPersona = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);
            
            baseDatos.insertarDocumento(dBCollectionPersona, administrador.getPersona().toString());
            
        } catch (Exception e) {
            respuesta = false;
        }
        return respuesta;
        
    }
    
    public boolean validarActualizacion(Persona persona) {
        boolean respuesta = false;
        
        BaseDatos baseDatos = new BaseDatos();
        
        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);
        
        DBObject query = BasicDBObjectBuilder.start()
                .add("persona.correo", persona.getCorreo())
                .add("persona.idPersona", new BasicDBObject("$ne", persona.getIdPersona()))
                .get();
        
        DBCursor dBCursor = dBCollection.find(query);
        
        if (dBCursor.hasNext()) {
            respuesta = true;
            
        }
        
        return respuesta;
    }
    
    public boolean actualizar(Administrador administrador) {
        boolean respuesta = true;
        try {
            
            BaseDatos baseDatos = new BaseDatos();
            
            DBCollection dBCollectionAdministrador = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);
            DBCollection dBCollectionPersona = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);
            
            DBObject queryAdministrador = BasicDBObjectBuilder.start()
                    .add("persona.idPersona", administrador.getPersona().getIdPersona())
                    .get();
            DBObject queryPersona = BasicDBObjectBuilder.start()
                    .add("idPersona", administrador.getPersona().getIdPersona())
                    .get();
            
            dBCollectionAdministrador.update(queryAdministrador, administrador.generarBObject());
            dBCollectionPersona.update(queryPersona, administrador.getPersona().generarBObject());
        } catch (Exception e) {
            respuesta = false;
        } finally {
            return respuesta;
        }
        
    }
    
    public boolean modificarEstatus(String correo, int estatus) {
        boolean respuesta = true;
        try {
            
            BaseDatos baseDatos = new BaseDatos();
            
            DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);
            
            DBObject query = BasicDBObjectBuilder.start()
                    .add("persona.correo", correo)
                    .get();
            
            BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("persona.estatus", estatus));
            
            dBCollection.update(query, set);
            
            dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);
            
            query = BasicDBObjectBuilder.start()
                    .add("correo", correo)
                    .get();
            
            set = new BasicDBObject("$set", new BasicDBObject("estatus", estatus));
            
            dBCollection.update(query, set);
            
        } catch (Exception e) {
            respuesta = false;
        } finally {
            return respuesta;
        }
        
    }
    
    public String listado() {
        Administrador administrador;
        Gson gson = new Gson();
        
        LinkedList<Administrador> administradores = new LinkedList<>();
        
        BaseDatos baseDatos = new BaseDatos();
        
        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);
        
         DBObject query = BasicDBObjectBuilder.start()
                    .add("persona.estatus", 1)
                    .get();
        
        DBCursor dBCursor = dBCollection.find(query);
        
        while (dBCursor.hasNext()) {
            administrador = new Administrador(dBCursor.next().toString());
            administradores.add(administrador);
        }
        
        return gson.toJson(administradores);
    }
}
