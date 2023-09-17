package com.byteminds.negocio;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class EstudianteDTO extends UsuarioDTO {

	@NotNull
	private Integer generacion;

	public EstudianteDTO() {
		// TODO Auto-generated constructor stub
	}

	public EstudianteDTO(Integer documento,
			 String usuario, 
			 String contrasenia, 
			 String apellidos,
			 String nombres, 
			 Date fechaNacimiento,
			 String departamento, 
			 String genero,
			 String localidad,
			 String mail,
			 String mailPersonal,
			 String telefono,
			 ItrDTO itr,
			 RolDTO rol,
			 boolean activo,
			 boolean validado
			 ,Integer generacion ) {
	        super(documento, usuario, contrasenia, apellidos, nombres, fechaNacimiento, departamento, genero, localidad, mail, mailPersonal, telefono, itr, rol, activo, validado);
	        this.generacion = generacion;
	    }

	public Integer getGeneracion() {
		return generacion;
	}

	public void setGeneracion(Integer generacion) {
		this.generacion = generacion;
	}

}
