package com.oliguz.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oliguz.api.entity.Categoria;
import com.oliguz.api.repository.CategoriaRepository;
import com.oliguz.api.service.CategoriaService;


@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository repos;
	
	@Override
	@Transactional(readOnly=true)
	public List<Categoria> findAll(Pageable page) {
		try {
			return repos.findAll(page).toList();
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	@Transactional(readOnly=true)
	public Categoria findById(int id) {
		try {
			Categoria categoria =  repos.findById(id).orElseThrow();
			return categoria;
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Categoria> findByNombre(String nombre, Pageable page) {
		try {
			return repos.findByNombreContaining(nombre, page);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Categoria create(Categoria obj) {
		try {
			Categoria categoria = repos.save(obj);
			return categoria;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Categoria update(Categoria obj) {
		try {
			Categoria categoria = repos.findById(obj.getId()).orElseThrow();
			categoria.setNombre(obj.getNombre());
			repos.save(categoria);
			
			return categoria;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void delete(int id) {
		try {
			Categoria categoria = repos.findById(id).orElseThrow();
			repos.delete(categoria);
					
		} catch (Exception e) {
			
		}	
	}
	/*public int delete(int id) {
		try {
			Producto producto = repos.findById(id).orElseThrow(null);
			if(producto == null) {
				return 0;			
			}else {
				producto.setActivo(false);
				repos.delete(producto);
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
	}*/


}