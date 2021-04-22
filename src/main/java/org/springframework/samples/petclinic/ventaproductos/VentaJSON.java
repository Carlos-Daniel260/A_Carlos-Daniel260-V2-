/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

/**
 *
 * @author Hugo Ruiz
 */
public class VentaJSON {
    private String username;
    private String metodoPago;
    private double total;
    
    public VentaJSON(){
        
    }

    public VentaJSON(String username, String metodoPago, double total) {
        this.username = username;
        this.metodoPago = metodoPago;
        this.total = total;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
