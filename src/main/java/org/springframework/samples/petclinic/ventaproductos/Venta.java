/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;

/**
 *
 * @author Hugo Ruiz
 */
@Entity
@Table(name = "venta")
public class Venta extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private Owner owner;

    @Column(name = "metodopago")
    private String metodopago;

    @Column(name = "total")
    private Double total;

    @Column(name = "fecha")
    private String fecha;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    private Set<VentaDetalle> ventadetalle;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Set<VentaDetalle> getVentadetalle() {
        return ventadetalle;
    }

    public void setVentadetalle(Set<VentaDetalle> ventadetalle) {
        this.ventadetalle = ventadetalle;
    }

    
}
