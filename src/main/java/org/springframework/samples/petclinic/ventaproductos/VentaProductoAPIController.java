package org.springframework.samples.petclinic.ventaproductos;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Controller
class VentaProductoAPIController {

    private final VentaRepository ventas;

    @Autowired
    private OwnerRepository owners;


    public VentaProductoAPIController(VentaRepository ventas){
        this.ventas = ventas;
    }

    @GetMapping({ "/API/comprasJSON/{idowner}" })
    public @ResponseBody VentaProductoJSON showResourcesVetListJSON( @PathVariable("idowner") int idowner) {
        VentaProductoJSON misCompras = new VentaProductoJSON();
        Owner owner_temp = this.owners.findOwnerByUserId(idowner);

        if(owner_temp != null){
            Collection<Venta> allCompras = this.ventas.ventasOwner(owner_temp);
            Iterator iterator = allCompras.iterator();
            while(iterator.hasNext()) {
                Venta venta = (Venta) iterator.next();
                misCompras.getVentasList().add( new Compra(venta.getId(), venta.getOwner().getId(), venta.getMetodopago(), venta.getTotal(), venta.getFecha()) );
            }
            return misCompras;
        }else
            return null;

    }

}
