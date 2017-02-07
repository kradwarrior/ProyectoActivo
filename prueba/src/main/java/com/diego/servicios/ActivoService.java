package com.diego.servicios;

import java.util.Date;
import java.util.List;

import com.diego.modelos.Activo;

public interface ActivoService {

	public static String ERROR_FECHAMAYOR = "EFM";
	public static String ERROR_FECHAINVALIDA = "EFI";
	public static String ERROR_NODATOS = "END";

	public List<Activo> buscarActivos() throws Exception;

	public Activo buscarPorId(Integer ind) throws Exception;

	public List<Activo> buscarFiltroEspecifico(String tipo, Date fechaCompra, String serial) throws Exception;

	public void guardarActivo(Activo activo) throws Exception;

	public void actualizarActivo(Activo activo) throws Exception;
}
