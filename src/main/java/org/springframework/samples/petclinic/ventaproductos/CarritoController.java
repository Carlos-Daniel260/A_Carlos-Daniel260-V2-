/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Hugo Ruiz
 */
@Controller
public class CarritoController {

    private final ProductRepository productRepository;
    private final CarritoCompraRepository carritoRepository;

    private static final String VIEWS_OWNER_PRODUCTS_SELECTED = "ventaproducto/productosagregados";

    @Autowired
    private OwnerRepository owners;

    @Autowired
    private UserRepository userRepository;

    public CarritoController(ProductRepository product, CarritoCompraRepository carritoRepository) {
        this.productRepository = product;
        this.carritoRepository = carritoRepository;
    }
    
    @RequestMapping("owner/seleccionarProducto/producto")
    public String ejecutarCompra(@Valid CarritoCompra carrito, BindingResult result) {
        CarritoCompra carritoCompra = new CarritoCompra();
        Owner owner = getCurrentOwner();
        Product product = new Product();
        product = productRepository.findById(carrito.getProduct().getId());

        Collection<CarritoCompra> carritos = this.carritoRepository.validateNewCarrito(product);
        if (!carritos.isEmpty()) {
            CarritoCompra carritoExistente = (CarritoCompra)carritos.toArray()[0];
            carritoExistente.setCantidad(carritoExistente.getCantidad() + carrito.getCantidad());
            product.setExistence(product.getExistence() - carrito.getCantidad());
            this.productRepository.save(product);
            this.carritoRepository.save(carritoExistente);
        } else {
            carritoCompra.setCantidad(carrito.getCantidad());
            carritoCompra.setOwner(owner);
            carritoCompra.setProduct(product);
            product.setExistence(product.getExistence() - carrito.getCantidad());
            this.productRepository.save(product);
            this.carritoRepository.save(carritoCompra);
        }
        return "redirect:/owner/listaproductos";
    }

    @GetMapping("owner/agregados")
    public String productosSeleccionados(Map<String, Object> model) {
        Owner owner = getCurrentOwner();
        Collection<CarritoCompra> productosSeleccionados = this.carritoRepository.productosOwner(owner);
        double totalCompra = 0;
        for (CarritoCompra carritoCompra : productosSeleccionados) {
            totalCompra += carritoCompra.getProduct().getPrice() * carritoCompra.getCantidad();
        }
        boolean hayProductos = false;
        if(productosSeleccionados.size()>0){
            hayProductos = true;
        }
        model.put("productosSeleccionados", productosSeleccionados);
        model.put("totalCompra", totalCompra);
        model.put("numeroProductos", productosSeleccionados.size());
        model.put("hayProductos", hayProductos);
        Venta venta = new Venta();
        model.put("venta", venta);
        return VIEWS_OWNER_PRODUCTS_SELECTED;
    }

    @GetMapping("owner/quitarProductos/{carritoId}")
    public String quitarProductoCarrito(@PathVariable("carritoId") int carritoId) {
        CarritoCompra carrito = this.carritoRepository.findById(carritoId);
        this.carritoRepository.delete(carrito);
        Product product = carrito.getProduct();
        product.setExistence(product.getExistence() + carrito.getCantidad());
        this.productRepository.save(product);
        return "redirect:/owner/agregados";
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
}
