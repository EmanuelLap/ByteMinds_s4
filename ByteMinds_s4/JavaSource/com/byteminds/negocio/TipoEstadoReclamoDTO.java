package com.byteminds.negocio;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class TipoEstadoReclamoDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull
	private String nombre;

	private Boolean activo;

	public TipoEstadoReclamoDTO() {
//			this.id=1;
//			this.nombre="Reclamo inicial new";
		this.activo = true;
	}

	public TipoEstadoReclamoDTO(Integer id, @NotNull String nombre, Boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "TipoEstadoReclamoDTO [id=" + id + ", nombre=" + nombre + ", activo=" + activo + "]";
	}

}
