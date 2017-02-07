package com.diego.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.diego.modelos.Activo;

public interface RepositorioActivos extends CrudRepository<Activo, Integer>, Repository<Activo, Integer> {

	public List<Activo> findAll();

	@Query("select a from Activo a where a.tipo=:tipo and (:fechaCompra is null or a.fechaCompra=:fechaCompra) and (:serial is null or a.serial=:serial)")
	public List<Activo> findAllFiltro(@Param("tipo") String tipo, @Param("fechaCompra") Date fechaCompra,
			@Param("serial") String serial);

	@Transactional
	@Modifying
	@Query("update Activo a set a.serial=:serial, a.fechaBaja=:fechaBaja where a.id=:id")
	public void actualizarActivo(@Param("id") Integer id, @Param("serial") String serial,
			@Param("fechaBaja") Date fechaBaja);

}
