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
public class Productos {
    String nombreProducto;
    String descripcionProducto;
    int cantidadProducto;
    int precioVentaUnitaria;
    int precioVentaMayor;
    int precioCompra;
    int distribuidorId;
    int categoriaProductoId;

    public Productos() {
    }

    public Productos(String nombreProducto, String descripcionProducto, int cantidadProducto, int precioVentaUnitaria, int precioVentaMayor, int precioCompra, int distribuidorId, int categoriaProductoId) {
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioVentaUnitaria = precioVentaUnitaria;
        this.precioVentaMayor = precioVentaMayor;
        this.precioCompra = precioCompra;
        this.distribuidorId = distribuidorId;
        this.categoriaProductoId = categoriaProductoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public int getPrecioVentaUnitaria() {
        return precioVentaUnitaria;
    }

    public void setPrecioVentaUnitaria(int precioVentaUnitaria) {
        this.precioVentaUnitaria = precioVentaUnitaria;
    }

    public int getPrecioVentaMayor() {
        return precioVentaMayor;
    }

    public void setPrecioVentaMayor(int precioVentaMayor) {
        this.precioVentaMayor = precioVentaMayor;
    }

    public int getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(int precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public int getCategoriaProductoId() {
        return categoriaProductoId;
    }

    public void setCategoriaProductoId(int categoriaProductoId) {
        this.categoriaProductoId = categoriaProductoId;
    }

    @Override
    public String toString() {
        return "Productos{" + "nombreProducto=" + nombreProducto + ", descripcionProducto=" + descripcionProducto + ", cantidadProducto=" + cantidadProducto + ", precioVentaUnitaria=" + precioVentaUnitaria + ", precioVentaMayor=" + precioVentaMayor + ", precioCompra=" + precioCompra + ", distribuidorId=" + distribuidorId + ", categoriaProductoId=" + categoriaProductoId + '}';
    }
    
    
}
