package com.diego.servicios.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.modelos.Activo;
import com.diego.repositorios.RepositorioActivos;
import com.diego.servicios.ActivoService;

@Service
public class ActivoServiceImpl implements ActivoService {
	
	@Autowired
	private RepositorioActivos repositorio;

	@Override
	public List<Activo> buscarActivos() {
		return repositorio.findAll();
	}
	
	@Override
	public Activo buscarUno(int id) {
		return repositorio.findOne(id);
	}
	
	@Override
	public List<Activo> buscarFiltro(String tipo, Date fechaCompra, String serial) {
		return (List<Activo>) repositorio.findAllFiltro(tipo, fechaCompra, serial);
	}
	
	@Override
	public void guardarActivo(Activo activo) {
		repositorio.save(activo);
	}
	
	@Override
	public void actualizarActivo(Activo activo) {
		repositorio.actualizarActivo(activo.getId(), activo.getSerial(), activo.getFechaBaja());
	}

}
