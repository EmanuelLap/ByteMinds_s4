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
public class GestionTipoTutorService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public TipoTutorDTO fromTipoTutorTipo(TipoTutorTipo ttt) {
		TipoTutorDTO tipoTutorDTO = new TipoTutorDTO();
		

		tipoTutorDTO.setId(ttt.getId());
		tipoTutorDTO.setNombre(ttt.getNombre());
		tipoTutorDTO.setBajaLogica(ttt.getBajaLogica());
		
		
		return tipoTutorDTO;
	}

	public TipoTutorTipo toTipoTutorTipo(TipoTutorDTO tipoTutorDTO) {
		TipoTutorTipo ttt = new TipoTutorTipo();
		
		ttt.setId(tipoTutorDTO.getId());
		ttt.setNombre(tipoTutorDTO.getNombre());
		ttt.setBajaLogica(tipoTutorDTO.getBajaLogica());
		
	
		
		
		return ttt;
	}

	// servicios para capa de Presentacion



}
