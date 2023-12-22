package com.oliguz.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oliguz.api.entity.Producto;
import com.oliguz.api.exceptions.GeneralServiceException;
import com.oliguz.api.exceptions.NoDataFoundException;
import com.oliguz.api.exceptions.ValidateServiceException;
import com.oliguz.api.repository.ProductoRepository;
import com.oliguz.api.service.ProductoService;
import com.oliguz.api.validator.ProductoValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoRepository repos;
	
	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll(Pageable page) {
		try {
			return repos.findAll(page).toList();
		} catch (NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}


	@Override
	@Transactional(readOnly=true)
	public Producto findById(int id) {
		try {
			Producto producto =  repos.findById(id).orElseThrow(()->new NoDataFoundException("No exist el registro con ese ID"));
			return producto;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findByNombre(String nombre, Pageable page) {
		try {
			return repos.findByNombreContaining(nombre, page);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public Producto create(Producto obj) {
		try {
			ProductoValidator.save(obj);
			if(repos.findByNombre(obj.getNombre())!=null) {
				throw new ValidateServiceException("Ya existe un registro con ese nombre: " + obj.getNombre());
			}
			obj.setActivo(true);
			Producto producto = repos.save(obj);
			return producto;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public Producto update(Producto obj) {
		try {
			ProductoValidator.save(obj);
			Producto producto = repos.findById(obj.getId()).orElseThrow(()->new NoDataFoundException("No existe el registro con ese ID"));
			Producto productoD = repos.findByNombre(obj.getNombre());
			if(productoD !=null && productoD.getId() != producto.getId()) {
				throw new ValidateServiceException("Ya existe un registro con ese nombre"+obj.getNombre());
			}
			producto.setNombre(obj.getNombre());
			producto.setPrecio(obj.getPrecio());
			producto.setStock(obj.getStock());
			producto.setCategorias(obj.getCategorias());
			producto.setActivo(obj.isActivo());
			repos.save(producto);
			
			return producto;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		try {
			Producto producto = repos.findById(id).orElseThrow(()->new NoDataFoundException("No exist el registro con ese ID"));
			repos.delete(producto);
					
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(),e);
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
