package com.byteminds.negocio;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.jboss.marshalling.SerializabilityChecker;


public class ItrDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer id;

	@NotBlank (message = "El departamento es obligatorio")
	@Length(min = 1, max = 45)
	private String departamento;

	@NotBlank (message = "El nombre es obligatorio")
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
	
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    ItrDTO other = (ItrDTO) obj;
	    return id != null && id.equals(other.id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}
}
