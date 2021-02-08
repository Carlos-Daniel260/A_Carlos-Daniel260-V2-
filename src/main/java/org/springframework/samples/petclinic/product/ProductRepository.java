package org.springframework.samples.petclinic.product;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends Repository<Product, Integer> {

    @Query("SELECT product FROM Product product")
    @Transactional(readOnly = true)
    Collection<Product> getAllProducts();

    
    
    @Query("SELECT product FROM Product product WHERE product.id =:id")
    @Transactional(readOnly = true)
    Product findById(@Param("id") Integer id);
    
    
    @Query("SELECT product FROM Product product WHERE product.name =:name")
    @Transactional(readOnly = true)
    Collection<Product> validateNewProduct(@Param("name") String name);
    
    
    
    @Query("SELECT product FROM Product product WHERE product.id!=:id AND product.name =:name")
    @Transactional(readOnly = true)
    Collection<Product> validateEditProduct(@Param("id") Integer id, @Param("name") String name);
    
    
    
    void save(Product product);

    void delete(Product product);

}
