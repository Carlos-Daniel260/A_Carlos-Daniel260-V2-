/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.product.Product;
import org.springframework.samples.petclinic.product.ProductRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Hugo Ruiz
 */
@Controller
public class VentaProductoController {

    private final ProductRepository productRepository;
    private final CarritoCompraRepository carritoRepository;
    private final VentaRepository ventaRepository;
    private final VentaDetalleRepository ventaDetalleRepo;

    private static final String VIEWS_OWNER_BUY_PRODUCTS = "ventaproducto/listaproductos";
    private static final String VIEWS_OWNER_BUYS = "ventaproducto/listaventa";
    private static final String VIEWS_OWNER_BUY_DETAILS = "ventaproducto/listaventadetalle";

    @Autowired
    private OwnerRepository owners;

    @Autowired
    private UserRepository userRepository;

    public VentaProductoController(ProductRepository product, CarritoCompraRepository carritoRepository,
            VentaRepository ventaRepo, VentaDetalleRepository ventaDetalleRepo) {
        this.productRepository = product;
        this.carritoRepository = carritoRepository;
        this.ventaRepository = ventaRepo;
        this.ventaDetalleRepo = ventaDetalleRepo;
    }

    @GetMapping("owner/listaproductos")
    public String report(Map<String, Object> model) {
        Collection<Product> allProducts = this.productRepository.productsExistentes();
        CarritoCompra carrito = new CarritoCompra();
        model.put("allProducts", allProducts);
        model.put("carrito", carrito);
        return VIEWS_OWNER_BUY_PRODUCTS;
    }

    @RequestMapping("owner/agregados/pago")
    public String ejecutarCompra(@Valid Venta venta, BindingResult result) {
        java.util.Date fecha = new Date();
        Owner owner = getCurrentOwner();

        venta.setOwner(owner);
        venta.setFecha(fecha);
        ventaRepository.save(venta);
        
        addProductsVentaDetalle(owner, venta);
        return "redirect:/owner/compras";
    }
    
    public void addProductsVentaDetalle(Owner owner, Venta venta){
        Collection<CarritoCompra> allCompras = this.carritoRepository.productosOwner(owner);
        VentaDetalle ventaDetalle;
        for (CarritoCompra compra : allCompras) {
            ventaDetalle = new VentaDetalle(venta, compra.getProduct(), compra.getCantidad(), 
                compra.getProduct().getPrice(), compra.getCantidad()*compra.getProduct().getPrice());
            ventaDetalleRepo.save(ventaDetalle);
            carritoRepository.delete(compra);
        }
    }

    public Owner getCurrentOwner() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User temp = userRepository.findByEmail(username);
        Owner owner_temp = this.owners.findByUserId(temp.getId());
        return owner_temp;
    }
    
    @GetMapping("owner/compras")
    public String compras(Map<String, Object> model) {
        Owner owner = getCurrentOwner();
        Collection<Venta> allCompras = this.ventaRepository.ventasOwner(owner);
        model.put("allCompras", allCompras);
        return VIEWS_OWNER_BUYS;
    }
    
    @GetMapping("owner/compras/{ventaId}")
    public String compraDetalle(@PathVariable("ventaId") int ventaId, Map<String, Object> model) {
        Venta venta = ventaRepository.findById(ventaId);
        Collection<VentaDetalle> allVentaDetalles = this.ventaDetalleRepo.detallesVenta(venta);
        model.put("allVentaDetalles", allVentaDetalles);
        return VIEWS_OWNER_BUY_DETAILS;
    }
    
}
