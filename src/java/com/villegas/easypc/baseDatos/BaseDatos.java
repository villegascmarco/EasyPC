/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.villegas.easypc.baseDatos;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.villegas.easypc.commons.Commons;
import com.villegas.easypc.modelo.Administrador;
import com.villegas.easypc.modelo.Persona;

/**
 *
 * @author marco
 */
public class BaseDatos {

    //Por defecto se conecta al localHost y al puerto 27017
    private MongoClient mongoClient = new MongoClient();

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public DBCollection obtenerColeccion(String collection) {

        DB db = mongoClient.getDB(Commons.DB);

        DBCollection dBCollection = db.getCollection(collection);

        return dBCollection;
    }

    public void cerrarConexion(MongoClient mongoClient) {
        mongoClient.close();
    }

    public String insertarDocumento(DBCollection dBCollection, String document) {
        String respuesta = null;
        try {

            DBObject dBObject = (DBObject) JSON.parse(document);

            dBCollection.insert(dBObject);

            respuesta = document;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excepcion controlada " + e);
        } finally {
            return respuesta;
        }

    }

}
