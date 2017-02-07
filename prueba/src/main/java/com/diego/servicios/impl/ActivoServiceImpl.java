package com.diego.servicios.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.modelos.Activo;
import com.diego.repositorios.RepositorioActivos;
import com.diego.servicios.ActivoService;

/**
 * Esta clase contiene la implementacion de los servicios correspondientes a la
 * entidad Activo
 * 
 * @author DIEGO BERMUDEZ
 * @version 1.0
 *
 */

@Service
public class ActivoServiceImpl implements ActivoService {

	@Autowired
	private RepositorioActivos repositorio;

	@Override
	public List<Activo> buscarActivos() throws Exception {
		return repositorio.findAll();
	}

	@Override
	public Activo buscarPorId(Integer id) throws Exception {
		Activo activo = (Activo) repositorio.findOne(id);
		if (activo == null) {
			throw new Exception(ERROR_NODATOS);
		}
		return activo;
	}

	@Override
	public List<Activo> buscarFiltroEspecifico(String tipo, Date fechaCompra, String serial) throws Exception {
		List<Activo> listaActivos = (List<Activo>) repositorio.findAllFiltro(tipo, fechaCompra, serial);
		if (listaActivos != null && listaActivos.isEmpty()) {
			throw new Exception(ERROR_NODATOS);
		}
		return listaActivos;
	}

	@Override
	public void guardarActivo(Activo activo) throws Exception {
		// Si fecha de compra del activo es mayor a fecha de baja, genera una
		// excepcion.
		if (activo.getFechaCompra() != null && activo.getFechaBaja() != null && activo.getFechaCompra().compareTo(activo.getFechaBaja()) > 0) {
			throw new Exception(ERROR_FECHAMAYOR);
		}

		repositorio.save(activo);
	}

	@Override
	public void actualizarActivo(Activo activo) throws Exception {
		Activo activoValida = buscarPorId(activo.getId());
		// Si fecha de compra del activo es mayor a fecha de baja a actualizar,
		// genera una excepcion.
		if (activoValida.getFechaCompra() != null && activo.getFechaBaja() != null && activoValida.getFechaCompra().compareTo(activo.getFechaBaja()) > 0) {
			throw new Exception(ERROR_FECHAMAYOR);
		}
		repositorio.actualizarActivo(activo.getId(), activo.getSerial(), activo.getFechaBaja());
	}

}
