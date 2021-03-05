/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hugo Ruiz
 */
public interface VentaDetalleRepository extends Repository<VentaDetalle, Integer> {

    void save(VentaDetalle ventaDetalle);

    @Query("SELECT ventadetalle FROM VentaDetalle ventadetalle WHERE ventadetalle.venta=:venta")
    @Transactional(readOnly = true)
    Collection<VentaDetalle> detallesVenta(@Param("venta") Venta venta);
}
