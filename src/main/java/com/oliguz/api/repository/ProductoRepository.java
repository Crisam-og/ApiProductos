package com.oliguz.api.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oliguz.api.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Integer>{
	List<Producto> findByNombreContaining(String nombre, Pageable page);
	Producto findByNombre(String nombre);
}
