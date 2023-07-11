package com.capa2LogicaNegocio;

import java.util.Date;

public class TutorDTO extends UsuarioDTO {

	private TipoTutorTipoDTO tipoDTO;

	private TipoAreaDTO areaDTO;

	public TutorDTO() {
		// TODO Auto-generated constructor stub
	}


	public TutorDTO(Integer documento, String usuario, String contrasenia, String apellidos, String nombres,
			Date fechaNacimiento, String departamento, String genero, String localidad, String mail,
			String mailPersonal, String telefono, ItrDTO itr, RolDTO rol, boolean activo, 
			boolean validado, TipoTutorTipoDTO tipoDTO, TipoAreaDTO areaDTO) {
		super(documento, usuario, contrasenia, apellidos, nombres, fechaNacimiento, departamento, genero, localidad,
				mail, mailPersonal, telefono, itr, rol, activo, validado);
		this.tipoDTO = tipoDTO;
		this.areaDTO = areaDTO;
	}



	public TipoTutorTipoDTO getTipoDTO() {
		return tipoDTO;
	}

	public void setTipoDTO(TipoTutorTipoDTO tipoDTO) {
		this.tipoDTO = tipoDTO;
	}

	public TipoAreaDTO getAreaDTO() {
		return areaDTO;
	}

	public void setAreaDTO(TipoAreaDTO areaDTO) {
		this.areaDTO = areaDTO;
	}

}
