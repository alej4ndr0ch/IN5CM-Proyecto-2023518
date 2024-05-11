/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alejandrocuxun.models;

/**
 *
 * @author aleja
 */
public class Distribuidores {
    private int distribuidorId;
    private String nombreDistribuidor;
    private String descripcionDistribuidor;
    private int nitDistribuidor;
    private int Telefono;
    private String web;

    public Distribuidores() {
    }

    public Distribuidores(int distribuidorId, String nombreDistribuidor, String descripcionDistribuidor, int nitDistribuidor, int Telefono, String web) {
        this.distribuidorId = distribuidorId;
        this.nombreDistribuidor = nombreDistribuidor;
        this.descripcionDistribuidor = descripcionDistribuidor;
        this.nitDistribuidor = nitDistribuidor;
        this.Telefono = Telefono;
        this.web = web;
    }

    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public String getNombreDistribuidor() {
        return nombreDistribuidor;
    }

    public void setNombreDistribuidor(String nombreDistribuidor) {
        this.nombreDistribuidor = nombreDistribuidor;
    }

    public String getDescripcionDistribuidor() {
        return descripcionDistribuidor;
    }

    public void setDescripcionDistribuidor(String descripcionDistribuidor) {
        this.descripcionDistribuidor = descripcionDistribuidor;
    }

    public int getNitDistribuidor() {
        return nitDistribuidor;
    }

    public void setNitDistribuidor(int nitDistribuidor) {
        this.nitDistribuidor = nitDistribuidor;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "Distribuidores{" + "distribuidorId=" + distribuidorId + ", nombreDistribuidor=" + nombreDistribuidor + ", descripcionDistribuidor=" + descripcionDistribuidor + ", nitDistribuidor=" + nitDistribuidor + ", Telefono=" + Telefono + ", web=" + web + '}';
    }
}
