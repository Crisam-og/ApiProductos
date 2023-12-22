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

import com.oliguz.api.entity.Producto;
import com.oliguz.api.converter.ProductoConverter;
import com.oliguz.api.dto.ProductoDTO;
//import com.oliguz.api.entity.Categoria;
import com.oliguz.api.service.ProductoService;
import com.oliguz.api.utils.WrapperResponse;
//import com.oliguz.api.service.CategoriaService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService service;
    
	//@Autowired
	//private CategoriaService categoriaService;

	@Autowired
	private ProductoConverter converter;
	
    @GetMapping()
    public ResponseEntity<List<ProductoDTO>> findAll(
            @RequestParam(value = "nombre", required = false, defaultValue = "") String nombre,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ){
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Producto> productos;
        
        if(nombre == null) {
            productos = service.findAll(page);
        } else {
            productos = service.findByNombre(nombre, page);
        }
        /*
        if(productos.isEmpty()) {
        	return ResponseEntity.noContent().build();
        }*/
        List<ProductoDTO> productosDTO = converter.fromEntity(productos);
        return new WrapperResponse(true,"success", productosDTO).createResponse(HttpStatus.OK);
    }
    @GetMapping(value="/{id}")
	public ResponseEntity<WrapperResponse<ProductoDTO>> findById(@PathVariable("id") int id){
    	Producto productos = service.findById(id);
		if (productos ==null) {
			return ResponseEntity.notFound().build();
		}
		ProductoDTO productosDTO=converter.fromEntity(productos);
		return new WrapperResponse<ProductoDTO>(true,"success", productosDTO).createResponse(HttpStatus.OK);
	}
    
    @PostMapping
	public ResponseEntity<ProductoDTO> create(@RequestBody ProductoDTO productoDTO){
		Producto productos = service.create(converter.fromDTO(productoDTO));
		ProductoDTO productosDTO = converter.fromEntity(productos);
		return new WrapperResponse(true,"success", productosDTO).createResponse(HttpStatus.CREATED);
	}
    
    @PutMapping(value="/{id}")
	public ResponseEntity<ProductoDTO> update(@PathVariable("id") int id, @RequestBody ProductoDTO productoDTO){
		Producto productos = service.update(converter.fromDTO(productoDTO));
		if(productos == null) {
			return ResponseEntity.notFound().build();
		}
		ProductoDTO productosDTO = converter.fromEntity(productos);
		return new WrapperResponse(true,"success", productosDTO).createResponse(HttpStatus.OK);		
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<ProductoDTO> delete(@PathVariable("id") int id){
		service.delete(id);
		return new WrapperResponse(true,"success", null).createResponse(HttpStatus.OK);
	}
	/*@GetMapping("/categorias")
	public List<Categoria> getCategorias(Pageable page){
	    return categoriaService.findAll(page);
	}*/
}
 