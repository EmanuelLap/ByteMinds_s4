package com.byteminds.negocio;

import java.io.Serializable;
import java.util.Date;

public class AnalistaDTO extends UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnalistaDTO() {
		// TODO Auto-generated constructor stub
		this.setValidado(false);
		this.setActivo(true);
	}

	public AnalistaDTO(Integer documento, String usuario, String contrasenia, String apellidos, String nombres,
			Date fechaNacimiento, String departamento, String genero, String localidad, String mail,
			String mailPersonal, String telefono, ItrDTO itr, RolDTO rol, boolean activo, boolean validado) {
		super(documento, usuario, contrasenia, apellidos, nombres, fechaNacimiento, departamento, genero, localidad,
				mail, mailPersonal, telefono, itr, rol, activo, validado);
		// TODO Auto-generated constructor stub
	}

}
