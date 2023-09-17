package com.byteminds.negocio;

import java.util.Date;

public class AnalistaDTO extends UsuarioDTO{

	
	public AnalistaDTO() {
		// TODO Auto-generated constructor stub
	}

	public AnalistaDTO(Integer documento, String usuario, String contrasenia, String apellidos, String nombres,
			Date fechaNacimiento, String departamento, String genero, String localidad, String mail,
			String mailPersonal, String telefono, ItrDTO itr, RolDTO rol, boolean activo,
			boolean validado) {
		super(documento, usuario, contrasenia, apellidos, nombres, fechaNacimiento, departamento, genero, localidad, mail,
				mailPersonal, telefono, itr, rol, activo, validado);
		// TODO Auto-generated constructor stub
	}
	
}
