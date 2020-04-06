/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.comandos;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.node.easypc.baseDatos.BaseDatos;
import com.node.easypc.commons.Commons;
import com.node.easypc.modelo.Horario;
import java.util.List;

/**
 *
 * @author pollo
 */
public class ComandosHorario {
private BaseDatos baseDatos = new BaseDatos();

    public String listarHorarios(String idE) {
        
        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_HORARIO);
        baseDatos.obtenerColeccion(Commons.COLECCION_HORARIO);
                
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("estatus", 1);
        whereQuery.put("idEstacionamiento", idE);
        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);

        DBCursor cursor = dBCollection.find(whereQuery, fields);
        String res = "";
        System.out.println("cursor: " + cursor);
        String replaceAll = "";

        List<DBObject> cursor3 = cursor.toArray();

        String j = cursor3.toString();
        while (cursor.hasNext()) {
            res += cursor.next().toString();
            replaceAll += res.replaceAll(" ", "");

        }

        System.out.println(cursor3.size());
        Gson gson = new Gson();
        String json = gson.toJson(replaceAll);
        return j;
    }

    public String actualizarHorario(Horario h) {
        DBCollection dbCollection = baseDatos.obtenerColeccion(Commons.COLECCION_HORARIO);
        
        BasicDBObject query = new BasicDBObject();

        query.put("idHorario", h.getIdHorario());

        BasicDBObject newDocument = new BasicDBObject();

        newDocument.put("diaServicio", h.getDiaServicio());
        newDocument.put("horaInicio", h.getHoraInicio());
        newDocument.put("horaFin", h.getHoraFin());

        BasicDBObject updateObj = new BasicDBObject();
                
        updateObj.put("$set", newDocument);
        dbCollection.update(query, updateObj);
        return "Actualizado!";
    }

    public String eliminarHorario(Horario h) {
        DBCollection dbCollection = baseDatos.obtenerColeccion(Commons.COLECCION_HORARIO);
        
        BasicDBObject query = new BasicDBObject();

        query.put("idHorario", h.getIdHorario());

        BasicDBObject newDocument = new BasicDBObject();

        newDocument.put("diaServicio", h.getDiaServicio());
        newDocument.put("horaInicio", h.getHoraInicio());
        newDocument.put("horaFin", h.getHoraFin());
        newDocument.put("estatus", 0);

        BasicDBObject updateObj = new BasicDBObject();

        updateObj.put("$set", newDocument);
        dbCollection.update(query, updateObj);
        return "Eliminado!";
    }

    public String insertarHorario(Horario h) {
        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_HORARIO);
        
        BasicDBObject document = new BasicDBObject();
        document.put("idHorario", h.getIdHorario());
        document.put("diaServicio", h.getDiaServicio());
        document.put("horaInicio", h.getHoraInicio());
        document.put("horaFin", h.getHoraFin());
        document.put("estatus", 1);
        document.put("idEstacionamiento", h.getIdEstacionamiento());
        dBCollection.insert(document);
        return "Insertado!";
    }

    public String buscarHorario(Horario h) {
        DBCollection dBCollection = baseDatos.obtenerColeccion(Commons.COLECCION_HORARIO);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("diaServicio", h.getDiaServicio());
        whereQuery.put("estatus", 1);
        BasicDBObject fields = new BasicDBObject();
        fields.put("_id", 0);

        DBCursor cursor = dBCollection.find(whereQuery, fields);
        String res = "";
        System.out.println("cursor: " + cursor);
        String replaceAll = "";

        List<DBObject> cursor3 = cursor.toArray();
        String j = cursor3.toString();
        while (cursor.hasNext()) {
            res += cursor.next().toString();
            replaceAll += res.replaceAll(" ", "");

        }

        System.out.println(cursor3.size());
        Gson gson = new Gson();
        String json = gson.toJson(replaceAll);
        System.out.println(j);
        return j;
    }
}
