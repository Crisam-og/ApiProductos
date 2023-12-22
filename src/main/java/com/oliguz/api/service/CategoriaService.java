package com.oliguz.api.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.oliguz.api.entity.Categoria;

public interface CategoriaService {
	public List<Categoria> findAll(Pageable page);
	public Categoria findById(int id);
	public List<Categoria> findByNombre(String nombre, Pageable page);
    public Categoria create(Categoria obj);
    public Categoria update(Categoria obj);
    public void delete(int id);
}