/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.ventaproductos;

/**
 *
 * @author Hugo Ruiz
 */

public class ProductJSON{
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer existence;    
    private String photo;

    public ProductJSON(int id, String name, String description, Double price, Integer existence, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.existence = existence;
        this.photo = photo;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getExistence() {
        return existence;
    }

    public void setExistence(Integer existence) {
        this.existence = existence;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
}
