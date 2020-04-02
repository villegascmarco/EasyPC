/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.comandos;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.node.easypc.modelo.Administrador;
import com.node.easypc.modelo.Estacionamiento;
import java.util.ArrayList;
import java.util.List;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

/**
 *
 * @author Esau
 */
public class ComandosEstacionamiento
{

    MongoClient mongoClient = (MongoClient) MongoClients.create();

    private CodecRegistry pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(
                    PojoCodecProvider.builder().automatic(true).build()));

    private MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();

    public ComandosEstacionamiento()
    {

    }

    public List<Estacionamiento> getAll(boolean isActive,
                                        String db)
    {
        Gson gson = new Gson();

        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection<Estacionamiento> collection = database.getCollection(
                "estacionamiento", Estacionamiento.class);

        // FindIterable<Estacionamiento> find = collection.find();
        List<Estacionamiento> estacionamientos = new ArrayList<Estacionamiento>();

        FindIterable<Estacionamiento> iterable;

        if (isActive)
            iterable = collection.find(eq("estatus", 1));
        else
            iterable = collection.find(eq("estatus", 0));

        for (Estacionamiento estacionamiento : iterable)
            // Estacionamiento e = gson.fromJson(doc.toJson(), Estacionamiento.class);
            estacionamientos.add(estacionamiento);

        return estacionamientos;
    }

    public void insert(Estacionamiento estacionamiento,
                       String db)
    {
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection<Estacionamiento> collection = database.getCollection(
                "estacionamiento", Estacionamiento.class);

        collection.insertOne(estacionamiento);
    }

    public void update(Estacionamiento estacionamiento,
                       String db)
    {
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection<Estacionamiento> collection = database.getCollection(
                "estacionamiento", Estacionamiento.class);

        collection.replaceOne(eq("_id", estacionamiento.getId()),
                estacionamiento);
    }

    public void delete(Estacionamiento estacionamiento,
                       String db)
    {
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection<Estacionamiento> collection = database.getCollection(
                "estacionamiento", Estacionamiento.class);

        estacionamiento.setEstatus(0);

        collection.replaceOne(eq("_id", estacionamiento.getId()),
                estacionamiento);
    }

    public void unDelete(Estacionamiento estacionamiento,
                         String db)
    {
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection<Estacionamiento> collection = database.getCollection(
                "estacionamiento", Estacionamiento.class);

        estacionamiento.setEstatus(1);

        collection.replaceOne(eq("_id", estacionamiento.getId()),
                estacionamiento);
    }

    public List getAllAdmins()
    {
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase("EasyPC");
        MongoCollection<Administrador> collection = database.getCollection(
                "administrador", Administrador.class);

        // FindIterable<Estacionamiento> find = collection.find();
        List<Administrador> administradores = new ArrayList<>();

        for (Administrador administrador : collection.find())
            // Estacionamiento e = gson.fromJson(doc.toJson(), Estacionamiento.class);
            administradores.add(administrador);

        return administradores;
    }
}
