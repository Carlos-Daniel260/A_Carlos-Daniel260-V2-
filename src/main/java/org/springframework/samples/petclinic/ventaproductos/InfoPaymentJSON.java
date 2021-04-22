/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.ArrayList;

/**
 *
 * @author Hugo Ruiz
 */
public class InfoPaymentJSON {
    private double total;
    private int cantidad;
    private boolean isExistente;

    public InfoPaymentJSON(double total, int cantidad, boolean isExistente) {
        this.total = total;
        this.cantidad = cantidad;
        this.isExistente = isExistente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isIsExistente() {
        return isExistente;
    }

    public void setIsExistente(boolean isExistente) {
        this.isExistente = isExistente;
    }
    
    
}
