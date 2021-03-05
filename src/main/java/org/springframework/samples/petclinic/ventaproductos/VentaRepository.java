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
public interface VentaRepository extends Repository<Venta, Integer> {

    void save(Venta venta);

    @Query("SELECT venta FROM Venta venta WHERE venta.owner=:owner")
    @Transactional(readOnly = true)
    Collection<Venta> ventasOwner(@Param("owner") Owner owner);
    
    @Query("SELECT venta FROM Venta venta WHERE venta.id =:id")
    @Transactional(readOnly = true)
    Venta findById(@Param("id") Integer id);
}
