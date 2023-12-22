package com.oliguz.api.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oliguz.api.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository <Categoria, Integer>{
	List<Categoria> findByNombreContaining(String nombre, Pageable page);
	Categoria findByNombre(String nombre);
	
}