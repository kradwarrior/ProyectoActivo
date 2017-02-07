package com.diego.servicios;

import java.util.Date;
import java.util.List;

import com.diego.modelos.Activo;

public interface ActivoService {
	
	public List<Activo> buscarActivos();
	
	public Activo buscarUno(int ind);

	public List<Activo> buscarFiltro(String tipo, Date fechaCompra, String serial);
	
	public void guardarActivo(Activo activo);
	
	public void actualizarActivo(Activo activo);
}
