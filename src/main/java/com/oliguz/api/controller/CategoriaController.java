package com.oliguz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oliguz.api.entity.Categoria;
import com.oliguz.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService service;
    
    @GetMapping()
    public ResponseEntity<List<Categoria>> findAll(
            @RequestParam(value = "nombre", required = false, defaultValue = "") String nombre,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Categoria> categoria;
        
        if(nombre == null) {
            categoria = service.findAll(page);
        } else {
            categoria = service.findByNombre(nombre, page);
        }
        if(categoria.isEmpty()) {
        	return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoria);
    }
    @GetMapping(value="/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable("id") int id){
    	Categoria categoria = service.findById(id);
		if (categoria ==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoria);
	}
    
    @PostMapping
	public ResponseEntity<Categoria> create(@RequestBody Categoria categoria){
		Categoria categoriaBD = service.create(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaBD);
	}
    
    @PutMapping(value="/{id}")
	public ResponseEntity<Categoria> update(@PathVariable("id") int id, @RequestBody Categoria categoria){
		Categoria categoriaBD = service.update(categoria);
		if(categoriaBD == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaBD);		
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Categoria> delete(@PathVariable("id") int id){
		service.delete(id);
		return ResponseEntity.ok(null);	
	}
    
}