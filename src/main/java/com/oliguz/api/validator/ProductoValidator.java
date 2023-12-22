package com.oliguz.api.validator;

import com.oliguz.api.entity.Producto;
import com.oliguz.api.exceptions.ValidateServiceException;

public class ProductoValidator {
    public static void save(Producto producto){
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new ValidateServiceException("El nombre es requerido"); 
        }
        if (producto.getNombre().length() > 100) {
            throw new ValidateServiceException("El nombre es muy largo"); 
        }
        /*if (Double.compare(producto.getPrecio(), 0.0) == 0) {
            throw new RuntimeException("El precio es requerido");
        }
        if (producto.getPrecio() < 0) {
            throw new RuntimeException("El precio no debe ser negativo"); 
        }*/
        if (producto.getStock() < 0) {
            throw new ValidateServiceException("El stock no debe ser negativo"); 
        }
    }
}
