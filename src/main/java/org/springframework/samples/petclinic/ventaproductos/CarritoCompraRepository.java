/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.ArrayList;
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
public interface CarritoCompraRepository extends Repository<CarritoCompra, Integer>{
    @Query("SELECT carritocompra FROM CarritoCompra carritocompra WHERE carritocompra.id =:id")
    @Transactional(readOnly = true)
    CarritoCompra findById(@Param("id") Integer id);
    
    void save(CarritoCompra carritocompra);
    
    @Query("SELECT carritocompra FROM CarritoCompra carritocompra ")
    @Transactional(readOnly = true)
    Collection<CarritoCompra> All();
    
    @Query("SELECT carritocompra FROM CarritoCompra carritocompra WHERE carritocompra.owner=:owner")
    @Transactional(readOnly = true)
    Collection<CarritoCompra> productosOwner(@Param("owner") Owner owner);
}
