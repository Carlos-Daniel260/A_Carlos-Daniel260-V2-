/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.product.Product;

/**
 *
 * @author Hugo Ruiz
 */
@Entity
@Table(name="ventadetalle")
public class VentaDetalle extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "venta_id")
    public Venta venta;
    
    @OneToOne(fetch = FetchType.EAGER)
    private Product product;
    
    @Column(name = "cantidad")
    private int cantidad;
    
    @Column(name = "precio")
    private Double precio;
    
    @Column(name = "subtotal")
    private Double subtotal;

    public VentaDetalle(){}
    
    public VentaDetalle(Venta venta, Product product, int cantidad, Double precio, Double subtotal) {
        this.venta = venta;
        this.product = product;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
    }  
    
    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    } 
}
