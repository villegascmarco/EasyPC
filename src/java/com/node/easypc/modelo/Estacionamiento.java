/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.modelo;

import org.bson.types.ObjectId;


/**
 *
 * @author Esau
 */
public class Estacionamiento
{
    private ObjectId _id;
    private int estatus;
    private double latitud;
    private double longitud;
    private String nombre;
    private String foto;
    private String domicilio;
    private int capacidad;
    private double costo;
    private Administrador administrador;

    public Estacionamiento()
    {
    }

    public ObjectId getId()
    {
        return _id;
    }

    public void setId(ObjectId _id)
    {
        this._id = _id;
    }

    public int getEstatus()
    {
        return estatus;
    }

    public void setEstatus(int estatus)
    {
        this.estatus = estatus;
    }

    public double getLatitud()
    {
        return latitud;
    }

    public void setLatitud(double latitud)
    {
        this.latitud = latitud;
    }

    public double getLongitud()
    {
        return longitud;
    }

    public void setLongitud(double longitud)
    {
        this.longitud = longitud;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getFoto()
    {
        return foto;
    }

    public void setFoto(String foto)
    {
        this.foto = foto;
    }

    public String getDomicilio()
    {
        return domicilio;
    }

    public void setDomicilio(String domicilio)
    {
        this.domicilio = domicilio;
    }

    public int getCapacidad()
    {
        return capacidad;
    }

    public void setCapacidad(int capacidad)
    {
        this.capacidad = capacidad;
    }

    public double getCosto()
    {
        return costo;
    }

    public void setCosto(double costo)
    {
        this.costo = costo;
    }

    public Administrador getAdministrador()
    {
        return administrador;
    }

    public void setAdministrador(Administrador administrador)
    {
        this.administrador = administrador;
    }

    
    
    @Override
    public String toString()
    {
        return "Estacionamiento{" + "_id=" + _id + ", estatus=" + estatus + ", latitud=" + latitud + ", longitud=" + longitud + ", nombre=" + nombre + ", foto=" + foto + ", domicilio=" + domicilio + ", capacidad=" + capacidad + ", costo=" + costo + ", administrador=" + administrador + '}';
    }
    
    
}
