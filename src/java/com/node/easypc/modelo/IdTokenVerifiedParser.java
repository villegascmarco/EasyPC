/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.modelo;

/**
 *
 * @author marco
 */
public class IdTokenVerifiedParser {

    private String email;
    private String given_name;
    private String family_name;
    private String picture;

    public IdTokenVerifiedParser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Usuario convertirAUsuario() {
        Usuario usuario = new Usuario();
        
        Persona persona= new Persona();
        
        persona.setCorreo(email);
        persona.setFoto(picture);
        persona.setApellido(family_name);
        persona.setNombre(given_name);
        
        usuario.setPersona(persona);

        return usuario;
    }

}
