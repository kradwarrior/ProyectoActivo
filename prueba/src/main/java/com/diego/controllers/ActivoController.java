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
		List<Activo> results = service.buscarActivos();
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activo/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Activo> verActivo(@PathVariable int id) {
		Activo results = service.buscarUno(id);
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Activo>> buscarActivos(@RequestParam String tipo, @RequestParam String fecha,
			@RequestParam String serial) {

		Date fechaDate = StringUtils.isBlank(fecha) ? null : cambiarStringADate(fecha);
		if (StringUtils.isNotBlank(fecha) && fechaDate == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		List<Activo> results = service.buscarFiltro(comprobarNulo(tipo), fechaDate, comprobarNulo(serial));

		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crearActivo(@RequestBody Activo activo) {
		
		service.guardarActivo(activo);
		String results = "Registrado correctamente";
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@RequestMapping(path = "/api/activo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizarActivo(@RequestBody Activo activo) {
		
		service.actualizarActivo(activo);
		String results = "Registrado correctamente";
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	private Date cambiarStringADate(String fechaString) {
		Date fechaDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		try {
			fechaDate = formatter.parse(fechaString);
		} catch (Exception e) {
		}
		return fechaDate;
	}

	private String comprobarNulo(String valor) {
		return StringUtils.isBlank(valor) ? null : valor;
	}

}
