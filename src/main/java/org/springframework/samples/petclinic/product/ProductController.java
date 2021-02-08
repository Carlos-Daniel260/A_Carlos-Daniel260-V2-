package org.springframework.samples.petclinic.product;

import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    @Autowired
    private FileStorageService fileStorageService;
    private static final String VIEW_PRODUCTO_HOME = "producto/product";
    private static final String VIEW_PRODUCTO_CREATE = "producto/product-create";
    private static final String VIEW_PRODUCTO_EDIT = "producto/product-edit";
    private static final String VIEW_PRODUCTO_REPORT = "producto/product-report";


    
    public ProductController(ProductRepository product) {
        this.productRepository = product;
    }

    @GetMapping("/products")
    public String initFindForm(Map<String, Object> model) {
        Collection<Product> allProducts = this.productRepository.getAllProducts();
        model.put("allProducts", allProducts);
        return VIEW_PRODUCTO_HOME;
    }
    
    @GetMapping("/products/report")
    public String report(Map<String, Object> model) {
        Collection<Product> allProducts = this.productRepository.getAllProducts();
        model.put("allProducts", allProducts);
        return VIEW_PRODUCTO_REPORT;
    }

    @GetMapping("/product/create-product")
    public String initCreationForm(Map<String, Object> model) {
        Product product = new Product();
        model.put("product", product);
        return VIEW_PRODUCTO_CREATE;
    }

    @PostMapping("/product/create-product")
    public String processCreationForm(@Valid Product product, BindingResult result, @RequestParam("image") MultipartFile image) {
        Collection<Product> productList = this.productRepository.validateNewProduct(product.getName());

        if (!image.isEmpty()) {
            product.setPhoto("/resources/images/" + fileStorageService.storeFile(image));
        } else if (product.getPhoto().isEmpty()) {
            product.setPhoto("/resources/images/default-image.jpg");
        }

        if (result.hasErrors()) {
            return VIEW_PRODUCTO_CREATE;
        } else {
            if (!productList.isEmpty()) {
                result.rejectValue("name", "ProductoDuplicado");
                return VIEW_PRODUCTO_CREATE;
            }

            this.productRepository.save(product);
            return "redirect:/products";
        }

    }

    @GetMapping("/product/edit/{productId}")
    public ModelAndView showOwner(@PathVariable("productId") int productId) {
        ModelAndView mav = new ModelAndView("producto/product-edit");
        mav.addObject(this.productRepository.findById(productId));
        return mav;
    }

    @PostMapping("/product/edit/{productId}")
    public String processUpdateOwnerForm(@Valid Product product, BindingResult result, @PathVariable("productId") int productId, @RequestParam("image") MultipartFile image) {
        Collection<Product> productList = this.productRepository.validateEditProduct(productId, product.getName());

        if (!image.isEmpty()) {
            product.setPhoto("/resources/images/" + fileStorageService.storeFile(image));
        }

        if (result.hasErrors()) {
            return VIEW_PRODUCTO_EDIT;
        } else {
            if (!productList.isEmpty()) {
                result.rejectValue("name", "ProductoDuplicado");
                return VIEW_PRODUCTO_CREATE;
            }

            product.setId(productId);
            this.productRepository.save(product);
            return "redirect:/products";
        }
    }

    @GetMapping("/product/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") int productId) {
        Product product = this.productRepository.findById(productId);
        System.out.println("PRODUCT "+product);
        this.productRepository.delete(product);
        return "redirect:/products";
    }

}
