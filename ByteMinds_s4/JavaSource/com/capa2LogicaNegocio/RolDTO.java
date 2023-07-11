package com.capa2LogicaNegocio;

import java.util.ArrayList;
import java.util.List;


public class RolDTO {

	private Integer id;

	private String nombre;

	private String descripcion;

	private Boolean activo;

	private List<FuncionalidadDTO> funcionalidades = new ArrayList<FuncionalidadDTO>();
	
	public RolDTO() {
		// TODO Auto-generated constructor stub
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<FuncionalidadDTO> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<FuncionalidadDTO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	
	
	
	
}
