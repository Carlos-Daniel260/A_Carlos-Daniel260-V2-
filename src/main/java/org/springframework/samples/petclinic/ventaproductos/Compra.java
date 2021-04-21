package org.springframework.samples.petclinic.ventaproductos;

import java.util.Date;

class Compra{
    private Integer id;
    private Integer id_owner;
    private String metodopago;
    private Double total;
    private Date fecha;
    public Compra(Integer id,Integer id_owner, String metodopago, Double total, Date fecha) {
        this.id = id;
        this.id_owner = id_owner;
        this.metodopago = metodopago;
        this.total = total;
        this.fecha = fecha;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_owner() {
        return id_owner;
    }

    public void setId_owner(Integer id_owner) {
        this.id_owner = id_owner;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }
}
