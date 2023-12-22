package com.oliguz.api.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.oliguz.api.entity.Producto;

public interface ProductoService {
	public List<Producto> findAll(Pageable page);
	public Producto findById(int id);
	public List<Producto> findByNombre(String nombre, Pageable page);
    public Producto create(Producto obj);
    public Producto update(Producto obj);
    public void delete(int id);
}
