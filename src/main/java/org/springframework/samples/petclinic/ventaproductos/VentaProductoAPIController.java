/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.product.Product;
import org.springframework.samples.petclinic.product.ProductRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Hugo Ruiz
 */
@Controller
public class VentaProductoAPIController {

    private final ProductRepository productRepository;
    private final CarritoCompraRepository carritoRepository;
    private final VentaRepository ventaRepository;
    private final VentaDetalleRepository ventaDetalleRepo;

    @Autowired
    private OwnerRepository owners;

    @Autowired
    private UserRepository userRepository;

    public VentaProductoAPIController(ProductRepository product, CarritoCompraRepository carritoRepository,
            VentaRepository ventaRepo, VentaDetalleRepository ventaDetalleRepo) {
        this.productRepository = product;
        this.carritoRepository = carritoRepository;
        this.ventaRepository = ventaRepo;
        this.ventaDetalleRepo = ventaDetalleRepo;
    }

    @GetMapping("/API/products")
    public @ResponseBody
    Collection productsJSON() {
        ArrayList products = new ArrayList();
        Collection<Product> allProducts = this.productRepository.productsExistentes();
        /*for (Product p : allProducts) {
            try {
                products.add(new ProductJSON(p.getId(), p.getName(), p.getDescription(), 
                        p.getPrice(), p.getExistence(), encodeImageToBase64(p.getPhoto())));
            } catch (IOException ex) {
                Logger.getLogger(VentaProductoAPIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        return allProducts;
    }

    private String encodeImageToBase64(String photo) throws FileNotFoundException, IOException {
        String path = "classpath:static"+photo;
        File f = ResourceUtils.getFile(path);	//change path of image according to you
        FileInputStream fis = new FileInputStream(f);
        byte byteArray[] = new byte[(int) f.length()];
        fis.read(byteArray);
        String imageString = Base64.encodeBase64String(byteArray);
        fis.close();
        return imageString;
    }

    @PostMapping(value = {"/API/pay"},
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String pay(@RequestBody VentaJSON ventaJSON) {
        java.util.Date fecha = new Date();
        Owner owner = getCurrentOwner(ventaJSON.getUsername());
        Venta venta = new Venta();
        venta.setMetodopago(ventaJSON.getMetodoPago());
        venta.setTotal(ventaJSON.getTotal());
        venta.setOwner(owner);
        venta.setFecha(fecha);
        ventaRepository.save(venta);
        addProductsVentaDetalle(owner, venta);
        System.out.println("Pago realizado");
        return "Pago realizado";
    }

    public void addProductsVentaDetalle(Owner owner, Venta venta) {
        Collection<CarritoCompra> allCompras = this.carritoRepository.productosOwner(owner);
        VentaDetalle ventaDetalle;
        for (CarritoCompra compra : allCompras) {
            ventaDetalle = new VentaDetalle(venta, compra.getProduct(), compra.getCantidad(),
                    compra.getProduct().getPrice(), compra.getCantidad() * compra.getProduct().getPrice());
            ventaDetalleRepo.save(ventaDetalle);
            carritoRepository.delete(compra);
        }
    }
    
    
    @GetMapping({ "/API/compras/{idowner}" })
    public @ResponseBody ArrayList showResourcesVentasListJSON( @PathVariable("idowner") int idowner) {
        ArrayList misCompras = new ArrayList();
        Owner owner_temp = this.owners.findOwnerByUserId(idowner);
        System.out.println(owner_temp);
        if(owner_temp != null){
            Collection<Venta> allCompras = this.ventaRepository.ventasOwner(owner_temp);
            Iterator iterator = allCompras.iterator();
            while(iterator.hasNext()) {
                Venta venta = (Venta) iterator.next();
                misCompras.add( new Compra(venta.getId(), venta.getOwner().getId(), venta.getMetodopago(), venta.getTotal(), venta.getFecha()) );
            }
            return misCompras;
        }else
            return null;
    }

    @GetMapping({ "/API/compraDetalle/{idowner}" })
    public @ResponseBody
    ArrayList showResourcesCompraDetalleJSON(@PathVariable("idowner") int idCompra) {
        ArrayList miCompra = new ArrayList();
        Venta venta_temp = this.ventaRepository.findById(idCompra);
        System.out.println(venta_temp);
        if(venta_temp != null){
            Collection<VentaDetalle> detallesCompra = venta_temp.getVentadetalle();
            Iterator iterator = detallesCompra.iterator();
            while(iterator.hasNext()) {
                VentaDetalle venta = (VentaDetalle) iterator.next();
                miCompra.add( new CompraDetalle(venta.getId(), venta.getProduct().getName(), venta.getCantidad(), venta.getPrecio(), venta.getSubtotal()) );
            }
            return miCompra;
        }else
            return null;
    }

    public Owner getCurrentOwner(String username) {
        User temp = userRepository.findByEmail(username);
        Owner owner_temp = this.owners.findByUserId(temp.getId());
        return owner_temp;
    }
}
