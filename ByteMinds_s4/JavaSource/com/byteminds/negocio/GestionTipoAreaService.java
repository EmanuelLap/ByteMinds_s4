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
public class GestionTipoAreaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public TipoAreaDTO fromTipoArea(TipoArea tipoArea) {
		TipoAreaDTO tipoAreaDTO = new TipoAreaDTO();
		

		tipoAreaDTO.setId(tipoArea.getId());
		tipoAreaDTO.setNombre(tipoArea.getNombre());
		tipoAreaDTO.setBajaLogica(tipoArea.getBajaLogica());
		
		
		return tipoAreaDTO;
	}

	public TipoArea toTipoArea(TipoAreaDTO tipoAreaDTO) {
		TipoArea tipoArea = new TipoArea();
		
		tipoArea.setId(tipoAreaDTO.getId());
		tipoArea.setNombre(tipoAreaDTO.getNombre());
		tipoArea.setBajaLogica(tipoAreaDTO.getBajaLogica());
		
	
		
		
		return tipoArea;
	}

	// servicios para capa de Presentacion

public TipoAreaDTO obtenerTipoAreaPorId(Integer id) {

	
	return null;
}



}
