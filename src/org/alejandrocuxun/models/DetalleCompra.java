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
public class DetalleCompra {
    int detalleCompraId;
    int Cantidad;
    int productoId;
    int compraId;
    
    public void DetalleCompra(){
    
    }

    public DetalleCompra(int detalleCompraId, int Cantidad, int productoId, int compraId) {
        this.detalleCompraId = detalleCompraId;
        this.Cantidad = Cantidad;
        this.productoId = productoId;
        this.compraId = compraId;
    }

    public int getDetalleCompraId() {
        return detalleCompraId;
    }

    public void setDetalleCompraId(int detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    @Override
    public String toString() {
        return "DetalleCompra{" + "detalleCompraId=" + detalleCompraId + ", Cantidad=" + Cantidad + ", productoId=" + productoId + ", compraId=" + compraId + '}';
    }
    
    
}
