/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.Collection;
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

    private static final String VIEWS_OWNER_BUY_PRODUCTS = "ventaproducto/listaproductos";
    private static final String VIEWS_OWNER_PRODUCTS_SELECTED = "ventaproducto/productosagregados";

    @Autowired
    private OwnerRepository owners;

    @Autowired
    private UserRepository userRepository;

    public VentaProductoController(ProductRepository product, CarritoCompraRepository carritoRepository) {
        this.productRepository = product;
        this.carritoRepository = carritoRepository;
    }

    @GetMapping("owner/listaproductos")
    public String report(Map<String, Object> model) {
        Collection<Product> allProducts = this.productRepository.getAllProducts();
        model.put("allProducts", allProducts);
        return VIEWS_OWNER_BUY_PRODUCTS;
    }

    @GetMapping("owner/seleccionarProducto/{productId}")
    public String seleccionarProducto(@PathVariable("productId") int productId) {
        CarritoCompra carritoCompra = new CarritoCompra();

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User temp = userRepository.findByEmail(username);
        Owner owner_temp = this.owners.findByUserId(temp.getId());
        //System.out.println("usuario: " + temp);
        //System.out.println("numero jaja: " + temp.getId());
        //System.out.println("owner encontrado: " + owner_temp);
        //System.out.println("id del owner encontrado: " + owner_temp.getId());
        //System.out.println("Producto: " + productId);
        Product product = new Product();
        product = productRepository.findById(productId);
        carritoCompra.setCantidad(1);
        carritoCompra.setOwner(owner_temp);
        carritoCompra.setProduct(product);
        this.carritoRepository.save(carritoCompra);
        return "redirect:/owner/listaproductos";
    }

    @GetMapping("owner/agregados")
    public String productosSeleccionados(Map<String, Object> model) {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User temp = userRepository.findByEmail(username);
        Owner owner_temp = this.owners.findByUserId(temp.getId());
        System.out.println("ID OWNER: "+owner_temp.getId());
        //Collection<CarritoCompra> productosSeleccionados = this.carritoRepository.productosOwner(owner_temp);
        //model.put("productosSeleccionados", productosSeleccionados);
        return VIEWS_OWNER_PRODUCTS_SELECTED;
    }
}
