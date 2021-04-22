/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.Collection;
import org.springframework.samples.petclinic.product.Product;

/**
 *
 * @author Hugo Ruiz
 */
public class CarritoCompraJSON {
    private int id;
    private String nombre;
    private int cantidad;
    private double subtotal;

    public CarritoCompraJSON(int id, String nombre, int cantidad, double subtotal) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    } 
}
