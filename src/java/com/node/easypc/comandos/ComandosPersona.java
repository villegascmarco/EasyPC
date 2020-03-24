/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.comandos;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.node.easypc.baseDatos.BaseDatos;
import com.node.easypc.commons.Commons;
import com.node.easypc.modelo.Persona;

/**
 *
 * @author marco
 */
public class ComandosPersona {

    private BaseDatos baseDatos = new BaseDatos();

    /**
     *
     * @param persona Objeto de la persona que quiere iniciar sesion
     *
     * @param coleccion Coleccion en donde se quiere buscar a la persona
     * @return
     */
    public String iniciarSesion(Persona persona, String coleccion) {
        String respuesta = null;

        DBCollection dBCollection = baseDatos.obtenerColeccion(coleccion);

        //persona.parametro porque estamos buscando desde otra coleccion diferente
        //de persona
        DBObject query = BasicDBObjectBuilder.start()
                .add("persona.correo", persona.getCorreo())
                .add("persona.contrasenia", persona.getContrasenia())
                .add("persona.estatus", 1)
                .add("persona.token", "")
                .get();

        DBCursor dBCursor = dBCollection.find(query);

        if (dBCursor.hasNext()) {
            respuesta = dBCursor.next().toString();
        }

        baseDatos.cerrarConexion();
        return respuesta;
    }

    public boolean validarToken(Persona persona) {

        boolean respuesta = false;

        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);

        DBObject query = BasicDBObjectBuilder.start()
                .add("idPersona", persona.getIdPersona())
                .add("token", persona.getToken())
                .get();

        DBCursor dBCursor = dBCollection.find(query);

        if (dBCursor.hasNext()) {
            respuesta = true;
        }

        baseDatos.cerrarConexion();
        return respuesta;
    }

    public boolean crearPersona(Persona persona) {
        boolean respuesta = true;

        try {

            DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);

            baseDatos.insertarDocumento(dBCollection, persona.toString());

        } catch (Exception e) {
            respuesta = false;
        }
        baseDatos.cerrarConexion();
        return respuesta;

    }

    public String modificarDatos(Persona persona) {
        String respuesta = null;
        try {

            DBCollection dBCollectionPersona = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);

            DBObject queryPersona = BasicDBObjectBuilder.start()
                    .add("idPersona", persona.getIdPersona())
                    .get();

            dBCollectionPersona.update(queryPersona, persona.generarBObject());

//            clientSession.abortTransaction();
            respuesta = persona.toString();
        } catch (Exception e) {
            respuesta = null;
        }
        baseDatos.cerrarConexion();
        return respuesta;

    }

    /**
     * Este metodo sirve para validar el correo al querer crear una nueva
     * persona
     *
     * @param persona
     * @return
     */
    public boolean validarAutenticidadCorreo(Persona persona) {
        boolean respuesta = true;

        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);

        DBObject query = BasicDBObjectBuilder.start()
                .add("correo", persona.getCorreo())
                .get();

        DBCursor dBCursor = dBCollection.find(query);

        if (dBCursor.hasNext()) {
            //Si ya hay alguien con ese correo, no se le permite el cambio
            respuesta = false;

        }
        baseDatos.cerrarConexion();
        return respuesta;

    }

    /**
     * Este metodo sirve para validar el correo al querer modificarlo
     *
     * @param persona
     * @return
     */
    public boolean validarActualizacion(Persona persona) {
        boolean respuesta = false;

        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_ADMINISTRADOR);

        DBObject query = BasicDBObjectBuilder.start()
                .add("persona.correo", persona.getCorreo())
                .add("persona.idPersona", new BasicDBObject("$ne", persona.getIdPersona()))
                .get();

        DBCursor dBCursor = dBCollection.find(query);

        if (dBCursor.hasNext()) {
            respuesta = true;

        }
        baseDatos.cerrarConexion();
        return respuesta;
    }

    public boolean modificarEstatus(Persona persona) {
        boolean respuesta = true;
        String correo = persona.getCorreo();
        int estatus = persona.getEstatus();
        try {

            DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);

            DBObject query = BasicDBObjectBuilder.start()
                    .add("correo", correo)
                    .get();

            BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("estatus", estatus));

            dBCollection.update(query, set);

        } catch (Exception e) {
            respuesta = false;
        }

        baseDatos.cerrarConexion();
        return respuesta;

    }

    public boolean cerrarSesion(Persona persona) {
        boolean respuesta = true;
        String correo = persona.getCorreo();

        try {

            DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_PERSONA);

            DBObject query = BasicDBObjectBuilder.start()
                    .add("correo", correo)
                    .get();

            BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("token", ""));

            dBCollection.update(query, set);

        } catch (Exception e) {
            respuesta = false;
        }

        baseDatos.cerrarConexion();
        return respuesta;

    }
}
