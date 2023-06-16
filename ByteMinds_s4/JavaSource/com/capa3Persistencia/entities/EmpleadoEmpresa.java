package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name =  "EMPLEADO")
public class EmpleadoEmpresa implements Serializable {	   

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(unique=true)
	private String nombre;
	private String departamento;
	private Integer edad;
	private Integer salario;
	private boolean activo;

	public EmpleadoEmpresa() {
		super();
	}
	
	public EmpleadoEmpresa(String nombre, String departamento, Integer edad, Integer salario) {
		super();
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
		
}
