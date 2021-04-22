package org.springframework.samples.petclinic.ventaproductos;

import java.util.ArrayList;
import java.util.List;

public class CompraDetalleJSON {
    private List<CompraDetalle> ventas;

    public List<CompraDetalle> getVentasList(){
        if(ventas == null){
            ventas = new ArrayList<>();
        }
        return ventas;
    }
}
