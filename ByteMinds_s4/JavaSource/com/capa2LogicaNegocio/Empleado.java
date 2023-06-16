package com.capa2LogicaNegocio;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Empleado {

	@NotNull
	private Long id;
	@NotNull
	@Length(min=7,	max=50)
	private String nombre;
	@NotNull
	@Length(min=5,	max=50)
	private String departamento;
	@NotNull
	@Min(12)
	@Max(120)
	private Integer edad;
	@NotNull
	@Max(500000)
	private Integer salario;
	@NotNull
	private boolean activo;
	

	public Empleado() {
		super();
	}
	
	public Empleado(Long id, String nombre, String departamento, Integer edad, Integer salario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.departamento = departamento;
		this.edad = edad;
		this.salario = salario;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Integer getSalario() {
		return salario;
	}

	public void setSalario(Integer salario) {
		this.salario = salario;
	}


	public boolean getActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
