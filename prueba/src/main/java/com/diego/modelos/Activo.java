package com.diego.modelos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Activo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private String nombre;

	@Column
	private String descripcion;

	@Column
	private String tipo;

	@Column
	private String serial;

	@Column(name = "NUMEROINVENTARIO")
	private Integer numeroInventario;

	@Column
	private Integer peso;

	@Column
	private Integer alto;

	@Column
	private Integer ancho;

	@Column
	private Integer largo;

	@Column(name = "VALORCOMPRA")
	private Double valorCompra;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHACOMPRA")
	private Date fechaCompra;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHABAJA")
	private Date fechaBaja;

	@Column
	private String estado;

	@Column
	private String color;

	public Activo() {

	}

	public Activo(Integer id, String nombre, String descripcion, String tipo, String serial, Integer numeroInventario,
			Integer peso, Integer alto, Integer ancho, Integer largo, double valorCompra, Date fechaCompra,
			Date fechaBaja, String estado, String color) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.serial = serial;
		this.numeroInventario = numeroInventario;
		this.peso = peso;
		this.alto = alto;
		this.ancho = ancho;
		this.largo = largo;
		this.valorCompra = valorCompra;
		this.fechaCompra = fechaCompra;
		this.fechaBaja = fechaBaja;
		this.estado = estado;
		this.color = color;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getNumeroInventario() {
		return numeroInventario;
	}

	public void setNumeroInventario(Integer numeroInventario) {
		this.numeroInventario = numeroInventario;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getAlto() {
		return alto;
	}

	public void setAlto(Integer alto) {
		this.alto = alto;
	}

	public Integer getLargo() {
		return largo;
	}

	public void setLargo(Integer largo) {
		this.largo = largo;
	}

	public Double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(Double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getAncho() {
		return ancho;
	}

	public void setAncho(Integer ancho) {
		this.ancho = ancho;
	}

}
