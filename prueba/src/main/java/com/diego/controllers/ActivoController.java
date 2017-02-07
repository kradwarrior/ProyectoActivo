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

/**
 * Esta es clase Controller la cual contiene recibe todas las peticiones,
 * procesa y/o devuelve una respuesta dependiendo de su method
 * 
 * @author DIEGO BERMUDEZ
 * @version 1.0
 *
 */

@RestController
public class ActivoController {

	@Autowired
	private ActivoService service;

	/**
	 * Recibe una peticion GET y retorna una lista de la entidad Activo
	 * @return lista de Activo en formato JSON
	 * @exception excepciones controladas
	 */
	@RequestMapping(path = "/api/activo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Activo>> verActivos() {
		List<Activo> results = null;
		try {
			results = service.buscarActivos();
		} catch (Exception e) {
			return controladorExcepcion(results, e);
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	/**
	 * Recibe una peticion GET con el parametro id para obtener una entidad Activo
	 * @param id filtro llave primaria de Activo
	 * @return Activo
	 * @exception excepciones controladas
	 */
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

	/**
	 * Recibe una peticion GET con los paramtros para filtrar y devolver una lista de entidad Activo
	 * @param tipo filtro de entidad Activo.tipo
	 * @param fecha filtro de entidad Activo.tipo
	 * @param serial filtro de entidad Activo.tipo
	 * @return Lista de entidades Activo
	 * @exception excepciones controladas
	 */
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

	/**
	 * Recibe una peticion POST para crear un nuevo Activo
	 * @param Activo con los valores cargados
	 * @return String sin ninguna valor
	 * @exception excepciones controladas
	 */
	@RequestMapping(path = "/api/activo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crearActivo(@RequestBody Activo activo) {
		
		try {
			validarActivo(activo, true);
			service.guardarActivo(activo);
		} catch (Exception e) {
			return controladorExcepcion(null, e);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	/**
	 * Recibe una peticion PUT para actualizar los valores serial y fecha de baja
	 * @param Activo con los valores y id de registro a actualizar
	 * @return String sin ninguna valor
	 * @exception excepciones controladas
	 */
	@RequestMapping(path = "/api/activo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> actualizarActivo(@RequestBody Activo activo) {

		try {
			
			validarActivo(activo, false);
			service.actualizarActivo(activo);
			
		} catch (Exception e) {
			return controladorExcepcion(null, e);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	/**
	 * Recibe un dato tipo String y lo retorna tipo Date
	 * @param fechaString valor String
	 * @return Date
	 * @throws Exception si se presenta un erro al cambiar el formato String
	 */
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

	/**
	 * Obtiene un String, retorna un nulo en caso de tener algun valor vacio o nulo
	 * @param valor dato a validar
	 * @return null en caso de tener una valor vacio o nulo
	 */
	private String comprobarNulo(String valor) {
		return StringUtils.isBlank(valor) ? null : valor;
	}

	/**
	 * Controla e identifica las excepciones generadas por el servicio, para asi devolver
	 * la respuesta que corresponda
	 * @param results resultado a devolver
	 * @param e excepcion generada por algun servicio
	 * @return respuesta según la excepcion generada
	 */
	private ResponseEntity controladorExcepcion(Object results, Exception e) {
		
		if (e != null && ActivoService.ERROR_FECHAMAYOR.equals(e.getMessage())
				|| ActivoService.ERROR_FECHAINVALIDA.equals(e.getMessage())
				|| ActivoService.ERROR_DATOSINCOMPLETOS.equals(e.getMessage())) {
			return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
		} else if (ActivoService.ERROR_NODATOS.equals(e.getMessage())) {
			return new ResponseEntity<>(results, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(results, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Valida un Activo cuando se esta actualizando o eliminando
	 * @param activo entidad a validar
	 * @param nuevo true o false
	 * @throws Exception si existe alguna valor nulo o vacio
	 */
	private void validarActivo(Activo activo, boolean nuevo) throws Exception {
		
		boolean valido = true;

		valido &= StringUtils.isNotBlank(activo.getSerial());
		if(nuevo){
			valido &= StringUtils.isNotBlank(activo.getNombre());
			valido &= StringUtils.isNotBlank(activo.getDescripcion());
			valido &= StringUtils.isNotBlank(activo.getTipo());
			valido &= activo.getNumeroInventario() != null;
			valido &= activo.getPeso() != null;
			valido &= activo.getAlto() != null;
			valido &= activo.getAncho() != null;
			valido &= activo.getLargo() != null;
			valido &= activo.getValorCompra() != null;
			valido &= activo.getFechaCompra() != null;
			valido &= StringUtils.isNotBlank(activo.getEstado());
			valido &= StringUtils.isNotBlank(activo.getColor());
		}else {
			valido &= activo.getId() == null;
			valido &= activo.getFechaBaja() != null;
		}
		
		if(!valido){
			throw new Exception(ActivoService.ERROR_DATOSINCOMPLETOS);
		}
	}

}
