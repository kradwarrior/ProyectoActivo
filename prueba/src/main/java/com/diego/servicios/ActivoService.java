package com.diego.servicios;

import java.util.Date;
import java.util.List;

import com.diego.modelos.Activo;

/**
 * Esta clase contiene contiene los servicios correspondientes a la entidad
 * Activo
 * 
 * @author DIEGO BERMUDEZ
 * @version 1.0
 *
 */

public interface ActivoService {

	public static String ERROR_FECHAMAYOR = "EFM";
	public static String ERROR_FECHAINVALIDA = "EFI";
	public static String ERROR_NODATOS = "END";

	/**
	 * Permite retornar una lista de Activos sin ningun filtro
	 * @return lista de activos
	 * @throws Exception si se presenta un error controlado o una inconsitencia en base de datos
	 */
	public List<Activo> buscarActivos() throws Exception;

	/**
	 * Retorna un Activo segun el filtro id
	 * @param id filtro llave primaria de Activo
	 * @return Activo
	 * @throws Exception si se presenta un error controlado o una inconsitencia en base de datos
	 */
	public Activo buscarPorId(Integer id) throws Exception;

	/**
	 * Busca una lista de Activos segun los filtros tipo, fechaCompra, serial
	 * @param tipo filtro de Activo
	 * @param fechaCompra filtro de Activo
	 * @param serial filtro de Activo
	 * @return Lista de Activos
	 * @throws Exception si se presenta un error controlado o una inconsitencia en base de datos
	 */
	public List<Activo> buscarFiltroEspecifico(String tipo, Date fechaCompra, String serial) throws Exception;

	/**
	 * Guarda un activo
	 * @param activo entidad con valores a guardar
	 * @throws Exception si se presenta un error controlado o una inconsitencia en base de datos
	 */
	public void guardarActivo(Activo activo) throws Exception;

	/**
	 * Actualiza el serial y fechaBaja de un activo
	 * @param activo entidad a actualizar por id
	 * @throws Exception si se presenta un error controlado o una inconsitencia en base de datos
	 */
	public void actualizarActivo(Activo activo) throws Exception;
}
