/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.villegas.easypc.modelo;

import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

/**
 *
 * @author marco
 */
public class Administrador {

    private String idAdministrador;
    private String telefono;
    private String colonia;
    private String calles;
    private String numero;
    private int cp;
    private Persona persona;

    public Administrador() {
    }

    public Administrador(String json) {
        Gson gson = new Gson();
        Administrador administrador = gson.fromJson(json, getClass());
        idAdministrador = administrador.getIdAdministrador();
        cp = administrador.getCp();
        telefono = administrador.getTelefono();
        numero = administrador.getNumero();
        calles = administrador.getCalles();
        colonia = administrador.getColonia();
        persona = administrador.getPersona();
    }

    public Administrador(String idAdministrador, String telefono, String colonia, String calles, String numero, int cp, Persona persona) {
        this.idAdministrador = idAdministrador;
        this.telefono = telefono;
        this.colonia = colonia;
        this.calles = calles;
        this.numero = numero;
        this.cp = cp;
        this.persona = persona;
    }

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalles() {
        return calles;
    }

    public void setCalles(String calles) {
        this.calles = calles;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        Administrador administrador = new Administrador(idAdministrador, telefono, colonia, calles, numero, cp, persona);

        return gson.toJson(administrador);
    }

    public DBObject generarBObject() {

        BasicDBObjectBuilder doc = BasicDBObjectBuilder.start();
        doc.append("idPersona", persona.getIdPersona());
        doc.append("nombre", persona.getNombre());
        doc.append("apellido", persona.getApellido());
        doc.append("estatus", persona.getEstatus());
        doc.append("correo", persona.getCorreo());
        doc.append("contrasenia", persona.getContrasenia());
        doc.append("foto", persona.getFoto());

        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("idAdministrador", idAdministrador);
        docBuilder.append("telefono", telefono);
        docBuilder.append("colonia", colonia);
        docBuilder.append("calles", calles);
        docBuilder.append("numero", numero);
        docBuilder.append("cp", cp);
        docBuilder.append("persona", doc.get());

        return docBuilder.get();
    }
}
