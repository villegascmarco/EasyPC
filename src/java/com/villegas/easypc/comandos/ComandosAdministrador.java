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
import com.mongodb.client.ClientSession;
import com.villegas.easypc.baseDatos.BaseDatos;
import com.villegas.easypc.commons.Commons;
import com.villegas.easypc.modelo.Administrador;
import java.util.LinkedList;

/**
 *
 * @author marco
 */
public class ComandosAdministrador {

    private BaseDatos baseDatos = new BaseDatos();

    public boolean crearAdministrador(Administrador administrador) {
        boolean respuesta = true;

        try {

            DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);

            baseDatos.insertarDocumento(dBCollection, administrador.toString());

        } catch (Exception e) {
            respuesta = false;
        }
        return respuesta;

    }

    public String modificarDatos(Administrador administrador) {
        String respuesta = null;
        try {

            DBCollection dBCollectionAdministrador = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);

            DBObject queryAdministrador = BasicDBObjectBuilder.start()
                    .add("persona.idPersona", administrador.getPersona().getIdPersona())
                    .get();

            dBCollectionAdministrador.update(queryAdministrador, administrador.generarBObject());

//            clientSession.abortTransaction();
            respuesta = administrador.toString();
        } catch (Exception e) {
            respuesta = null;
        }
        return respuesta;

    }

    public boolean modificarEstatus(Administrador administrador) {
        boolean respuesta = true;
        String correo = administrador.getPersona().getCorreo();
        int estatus = administrador.getPersona().getEstatus();
        try {

            DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);

            DBObject query = BasicDBObjectBuilder.start()
                    .add("persona.correo", correo)
                    .get();

            BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("persona.estatus", estatus));

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

    public boolean cerrarSesion(Administrador administrador) {
        boolean respuesta = true;
        String correo = administrador.getPersona().getCorreo();

        try {

            DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);

            DBObject query = BasicDBObjectBuilder.start()
                    .add("persona.correo", correo)
                    .get();

            BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("persona.token", ""));

            dBCollection.update(query, set);

        } catch (Exception e) {
            respuesta = false;
        }
        return respuesta;

    }
}
