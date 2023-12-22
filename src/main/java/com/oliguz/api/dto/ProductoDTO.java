package com.oliguz.api.dto;


import com.oliguz.api.entity.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductoDTO {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private boolean activo;
    private Categoria categoria;
}
