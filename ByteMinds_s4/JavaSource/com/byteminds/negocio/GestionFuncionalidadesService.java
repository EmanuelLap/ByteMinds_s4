package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Funcionalidad;
import tecnofenix.entidades.Rol;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorTipo;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.Usuario;

@Stateless
@LocalBean
public class GestionFuncionalidadesService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FuncionalidadDTO fromFuncionalidad(Funcionalidad funcionalidad) {
		FuncionalidadDTO funcionalidadDTO = new FuncionalidadDTO();

		funcionalidadDTO.setId(funcionalidad.getId());
		funcionalidadDTO.setNombre(funcionalidad.getNombre());
		funcionalidadDTO.setDescripcion(funcionalidad.getDescripcion());
		funcionalidadDTO.setActivo(funcionalidad.getActivo());

		return funcionalidadDTO;
	}

	public Funcionalidad toFuncionalidad(FuncionalidadDTO funcionalidadDTO) {
		Funcionalidad funcionalidad = new Funcionalidad();

		funcionalidad.setId(funcionalidadDTO.getId());
		funcionalidad.setNombre(funcionalidadDTO.getNombre());
		funcionalidad.setDescripcion(funcionalidadDTO.getDescripcion());
		funcionalidad.setActivo(funcionalidadDTO.getActivo());

		return funcionalidad;
	}

	// servicios para capa de Presentacion

}
