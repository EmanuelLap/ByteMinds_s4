package com.capa2LogicaNegocio;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class ItrDTO {
	
	@NotNull
	private Integer id;

	@NotNull
	@Length(min = 1, max = 45)
	private String departamento;

	@NotNull
	private String nombre;

	@NotNull
	private Boolean activo;
	
	public ItrDTO() {
		// TODO Auto-generated constructor stub
	}
 

	
	public ItrDTO(@NotNull Integer id, @NotNull @Length(min = 1, max = 45) String departamento, @NotNull String nombre,
			@NotNull Boolean activo) {
		super();
		this.id = id;
		this.departamento = departamento;
		this.nombre = nombre;
		this.activo = activo;
	}






	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
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
	
	
	
}
