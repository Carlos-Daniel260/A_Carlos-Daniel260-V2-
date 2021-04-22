/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
public class CarritoAPIController {

    private final ProductRepository productRepository;
    private final CarritoCompraRepository carritoRepository;

    @Autowired
    private OwnerRepository owners;

    @Autowired
    private UserRepository userRepository;

    public CarritoAPIController(ProductRepository product, CarritoCompraRepository carritoRepository) {
        this.productRepository = product;
        this.carritoRepository = carritoRepository;
    }

    @PostMapping(value = {"/API/addProduct"},
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String addProduct(@RequestBody ProductSelectedJSON carrito) {
        CarritoCompra carritoCompra = new CarritoCompra();
        Owner owner = getCurrentOwner(carrito.getUser());
        Product product = new Product();
        product = productRepository.findById(carrito.getId());
        Collection<CarritoCompra> carritos = this.carritoRepository.validateNewCarrito(product);
        if (!carritos.isEmpty()) {
            CarritoCompra carritoExistente = (CarritoCompra) carritos.toArray()[0];
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
        System.out.println("Producto agregado");
        return "Producto agregado";
    }

    @GetMapping({"/API/deleteProduct/{idProduct}"})
    public @ResponseBody
    String quitarProductoCarrito(@PathVariable("idProduct") int carritoId) {
        CarritoCompra carrito = this.carritoRepository.findById(carritoId);
        this.carritoRepository.delete(carrito);
        Product product = carrito.getProduct();
        product.setExistence(product.getExistence() + carrito.getCantidad());
        this.productRepository.save(product);
        System.out.println("Producto quitado");
        return "Producto quitado";
    }

    @GetMapping({"/API/selecteds/{userName}"})
    public @ResponseBody
    ArrayList selectedsJSON(@PathVariable("userName") String username) {
        Owner owner = getCurrentOwner(username);
        Collection<CarritoCompra> productosSeleccionados = this.carritoRepository.productosOwner(owner);
        double totalCompra = 0;
        for (CarritoCompra carritoCompra : productosSeleccionados) {
            totalCompra += carritoCompra.getProduct().getPrice() * carritoCompra.getCantidad();
        }
        boolean hayProductos = false;
        if (productosSeleccionados.size() > 0) {
            hayProductos = true;
        }
        ArrayList selecteds = new ArrayList();
        for (CarritoCompra carrito : productosSeleccionados) {
            selecteds.add(new CarritoCompraJSON(carrito.getId(),carrito.getProduct().getName(), carrito.getCantidad(), carrito.getCantidad() * carrito.getProduct().getPrice()));
        }
        int cantidad = productosSeleccionados.size();
        //model.put("productosSeleccionados", productosSeleccionados);
        //model.put("totalCompra", totalCompra);
        //model.put("numeroProductos", productosSeleccionados.size());
        //model.put("hayProductos", hayProductos);
        //Venta venta = new Venta();
        //model.put("venta", venta);

        return selecteds;
    }

    @GetMapping({"/API/infoPayment/{userName}"})
    public @ResponseBody
    InfoPaymentJSON infoPayment(@PathVariable("userName") String username) {
        Owner owner = getCurrentOwner(username);
        Collection<CarritoCompra> productosSeleccionados = this.carritoRepository.productosOwner(owner);
        double totalCompra = 0;
        for (CarritoCompra carritoCompra : productosSeleccionados) {
            totalCompra += carritoCompra.getProduct().getPrice() * carritoCompra.getCantidad();
        }
        boolean hayProductos = false;
        if (productosSeleccionados.size() > 0) {
            hayProductos = true;
        }

        int cantidad = productosSeleccionados.size();

        return new InfoPaymentJSON(totalCompra, cantidad, hayProductos);
    }

    public Owner getCurrentOwner(String username) {
        User temp = userRepository.findByEmail(username);
        Owner owner_temp = this.owners.findByUserId(temp.getId());
        return owner_temp;
    }
}
