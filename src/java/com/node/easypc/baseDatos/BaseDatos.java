/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.baseDatos;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.node.easypc.commons.Commons;

/**
 *
 * @author marco
 */
public class BaseDatos {

    private MongoClient mongoClient;

    public void iniciarMongo() {
        //Por defecto se conecta al localHost y al puerto 27017
        mongoClient = new MongoClient();
    }

    public MongoClient getMongoClient() {
        return mongoClient != null ? mongoClient : null;
    }

    public DBCollection obtenerColeccion(String collection) {
        iniciarMongo();
        DB db = mongoClient.getDB(Commons.DB);

        DBCollection dBCollection = db.getCollection(collection);

        return dBCollection;
    }

    public void cerrarConexion() {
        this.mongoClient.close();
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
