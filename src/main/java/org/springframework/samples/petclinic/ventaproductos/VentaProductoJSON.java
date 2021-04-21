package org.springframework.samples.petclinic.ventaproductos;

import java.util.ArrayList;
import java.util.List;

public class VentaProductoJSON {
    private List<Compra> ventas;

    public List<Compra> getVentasList(){
        if(ventas == null){
            ventas = new ArrayList<>();
        }
        return ventas;
    }
}
