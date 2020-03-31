/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.easypc.modelo;

/**
 *
 * @author pollo
 */
public class Horario {

    private String idHorario;
    private String diaServicio;
    private String horaInicio;
    private String horaFin;
    private int estatus;

    public Horario() {
    }

    public Horario(String idHorario, String diaServicio, String horaInicio, String horaFin, int estatus) {
        this.idHorario = idHorario;
        this.diaServicio = diaServicio;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estatus = estatus;
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public String getDiaServicio() {
        return diaServicio;
    }

    public void setDiaServicio(String diaServicio) {
        this.diaServicio = diaServicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

}
