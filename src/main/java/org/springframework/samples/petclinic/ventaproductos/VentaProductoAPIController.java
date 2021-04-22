package org.springframework.samples.petclinic.ventaproductos;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Controller
class VentaProductoAPIController {

    private final VentaRepository ventas;
    private final VentaDetalleRepository ventaDetalleRepository;

    @Autowired
    private OwnerRepository owners;


    public VentaProductoAPIController(VentaRepository ventas, VentaDetalleRepository ventaDetalleRepository){
        this.ventas = ventas;
        this.ventaDetalleRepository = ventaDetalleRepository;
    }

    @GetMapping({ "/API/compras/{idowner}" })
    public @ResponseBody VentaProductoJSON showResourcesVentasListJSON( @PathVariable("idowner") int idowner) {
        VentaProductoJSON misCompras = new VentaProductoJSON();
        Owner owner_temp = this.owners.findOwnerByUserId(idowner);
        System.out.println(owner_temp);
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

    @GetMapping({ "/API/compraDetalle/{idowner}" })
    public @ResponseBody
    CompraDetalleJSON showResourcesCompraDetalleJSON(@PathVariable("idowner") int idCompra) {
        CompraDetalleJSON miCompra = new CompraDetalleJSON();
        Venta venta_temp = this.ventas.findById(idCompra);
        System.out.println(venta_temp);
        if(venta_temp != null){
            Collection<VentaDetalle> detallesCompra = venta_temp.getVentadetalle();
            Iterator iterator = detallesCompra.iterator();
            while(iterator.hasNext()) {
                VentaDetalle venta = (VentaDetalle) iterator.next();
                miCompra.getVentasList().add( new CompraDetalle(venta.getId(), venta.getProduct().getName(), venta.getCantidad(), venta.getPrecio(), venta.getSubtotal()) );
            }
            return miCompra;
        }else
            return null;
    }

}
