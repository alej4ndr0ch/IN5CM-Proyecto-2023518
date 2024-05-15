/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alejandrocuxun.dto;

import org.alejandrocuxun.models.Compras;

/**
 *
 * @author informatica
 */
public class ComprasDTO {
    private static ComprasDTO instance;
    private Compras compras;
    
    private ComprasDTO(){
    
    }
    
    public static ComprasDTO getCompraDTO(){
        if(instance == null){
            instance = new ComprasDTO();
        }
        
        return instance;
    }

    public Compras getCompra() {
        return compras;
    }

    public void setCompra(Compras compras) {
        this.compras = compras;
    }
}
