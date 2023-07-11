package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa3Persistencia.exception.PersistenciaException;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Itr;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorTipo;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.Usuario;

@Stateless
@LocalBean
public class GestionItrService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ItrDTO fromITR(Itr itr) {
		ItrDTO itrDTO = new ItrDTO();
		
		itrDTO.setId(itr.getId());
		itrDTO.setDepartamento(itr.getDepartamento());
		itrDTO.setNombre(itr.getNombre());
		itrDTO.setActivo(itr.getActivo());
		
		return itrDTO;
	}

	public Itr toITR(ItrDTO itrDTO) {
		Itr itr = new Itr();
		itr.setId(itrDTO.getId());
		itr.setDepartamento(itrDTO.getDepartamento());
		itr.setNombre(itrDTO.getNombre());
		itr.setActivo(itrDTO.getActivo());
		
		return itr;
	}


}
