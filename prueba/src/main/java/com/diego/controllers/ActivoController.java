package com.diego.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diego.modelos.Activo;
import com.diego.servicios.ActivoService;

@RestController
public class ActivoController {

	@Autowired
	private ActivoService service;

	@RequestMapping(path = "/api/activo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Activo>> verActivos() {
		List<Activo> results = null;
		try {
			results = service.buscarActivos();
		} catch (Exception e) {
			return controladorExcepcion(results, e);
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activo/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Activo> verActivo(@PathVariable Integer id) {

		Activo results = null;
		try {
			results = service.buscarPorId(id);
		} catch (Exception e) {
			return controladorExcepcion(results, e);
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Activo>> buscarActivos(@RequestParam String tipo, @RequestParam String fecha,
			@RequestParam String serial) {

		List<Activo> results = null;
		try {

			Date fechaDate = StringUtils.isBlank(fecha) ? null : cambiarStringADate(fecha);
			if (StringUtils.isNotBlank(fecha) && fechaDate == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			results = service.buscarFiltroEspecifico(comprobarNulo(tipo), fechaDate, comprobarNulo(serial));

		} catch (Exception e) {
			return controladorExcepcion(results, e);
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crearActivo(@RequestBody Activo activo) {
		try {
			service.guardarActivo(activo);
		} catch (Exception e) {
			return controladorExcepcion(null, e);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizarActivo(@RequestBody Activo activo) {

		try {
			service.actualizarActivo(activo);

		} catch (Exception e) {
			return controladorExcepcion(null, e);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	private Date cambiarStringADate(String fechaString) throws Exception {
		Date fechaDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		try {
			fechaDate = formatter.parse(fechaString);
		} catch (Exception e) {
			throw new Exception(ActivoService.ERROR_FECHAINVALIDA);
		}
		return fechaDate;
	}

	private String comprobarNulo(String valor) {
		return StringUtils.isBlank(valor) ? null : valor;
	}

	private ResponseEntity controladorExcepcion(Object results, Exception e) {
		if (e != null && ActivoService.ERROR_FECHAMAYOR.equals(e.getMessage())
				&& ActivoService.ERROR_FECHAINVALIDA.equals(e.getMessage())) {
			return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
		} else if (ActivoService.ERROR_NODATOS.equals(e.getMessage())) {
			return new ResponseEntity<>(results, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(results, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
