package com.oliguz.api.converter;

import org.springframework.stereotype.Component;

import com.oliguz.api.dto.ProductoDTO;
import com.oliguz.api.entity.Categoria;
import com.oliguz.api.entity.Producto;

@Component
public class ProductoConverter extends AbstractConverter<Producto, ProductoDTO>{

	@Override
	public ProductoDTO fromEntity(Producto entity) {
		if(entity==null) return null;
		return ProductoDTO.builder()
				.id(entity.getId())
				.nombre(entity.getNombre())
				.stock(entity.getStock())
				.activo(entity.isActivo())
				.precio(entity.getPrecio())
				.categoria(entity.getCategorias())
				.build();
	}

	@Override
	public Producto fromDTO(ProductoDTO dto) {
		if(dto == null) { 
			return null;
		}
		Categoria categoriaDTO = dto.getCategoria();
		
		if (categoriaDTO == null) {
			return null;
		}
		
		Categoria categoria = Categoria.builder()
				.id(categoriaDTO.getId())
				.nombre(categoriaDTO.getNombre())
				.build();
		
		return Producto.builder()
				.id(dto.getId())
				.nombre(dto.getNombre())
				.stock(dto.getStock())
				.activo(dto.isActivo())
				.precio(dto.getPrecio())
				.categorias(categoria)
				.build();
	}

}
