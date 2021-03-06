/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.modelo;

import com.google.gson.Gson;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import java.sql.Timestamp;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author marco
 */
public class Persona {

    private String idPersona;
    private String nombre;
    private String apellido;
    private int estatus;
    private String correo;
    private String contrasenia;
    private String foto;
    private String token;

    public Persona() {
    }

    public Persona(String idPersona, String token){
        this.idPersona = idPersona;
        this.token = token;
    }
    
    public Persona(String idPersona, String nombre, String apellido, int estatus, String correo, String contrasenia, String foto, String token) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estatus = estatus;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.foto = foto;
        this.token = token;
    }

    public Persona(int estatus, String correo) {
        this.estatus = estatus;
        this.correo = correo;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getToken() {
        return token;
    }

    public void clearToken() {
        this.token = "";
    }

    public void setToken() {

        Timestamp key;
        String usuario;
        String contra;

        key = new Timestamp(System.currentTimeMillis());

        this.token = (DigestUtils.sha256Hex(
                correo + ";" + contrasenia + ";" + key));
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        Persona persona = new Persona(idPersona, nombre, apellido, estatus, correo, contrasenia, foto, token);

        return gson.toJson(persona);
    }

    public DBObject generarBObject() {

        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        docBuilder.append("idPersona", idPersona);
        docBuilder.append("nombre", nombre);
        docBuilder.append("apellido", apellido);
        docBuilder.append("estatus", estatus);
        docBuilder.append("correo", correo);
        docBuilder.append("contrasenia", contrasenia);
        docBuilder.append("foto", foto);
        docBuilder.append("token", token);

        return docBuilder.get();
    }
}
