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
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.AccionReclamo;
import tecnofenix.entidades.Analista;


@Stateless
@LocalBean
public class GestionAccionReclamoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EJBUsuarioRemoto ejbRemoto;
	GestionReclamoService gRS;
	GestionUsuarioService gUS;
	
	
	
	public GestionAccionReclamoService() {
		ejbRemoto = new EJBUsuarioRemoto();
		gRS = new GestionReclamoService();
		gUS = new GestionUsuarioService();
		
	}
	
	public AccionReclamoDTO fromAccionReclamoEntidad(AccionReclamo accionReclamoEntidad) {
		AccionReclamoDTO accionReclamoDTO = new AccionReclamoDTO();
		

		accionReclamoDTO.setId(accionReclamoEntidad.getId());

		accionReclamoDTO.setDetalle(accionReclamoEntidad.getDetalle());
		accionReclamoDTO.setFecha(accionReclamoEntidad.getFecha());
		
		accionReclamoDTO.setReclamoId(gRS.fromReclamo(accionReclamoEntidad.getReclamoId()));
		accionReclamoDTO.setAnalistaId(((AnalistaDTO)gUS.fromUsuario(accionReclamoEntidad.getAnalistaId())));
		
		
		
		return accionReclamoDTO;
	}

	public AccionReclamo toAccionReclamoEntidad(AccionReclamoDTO accionReclamoDTO) {
		AccionReclamo accionReclamoEntidad = new AccionReclamo();
		
		
		accionReclamoEntidad.setId(accionReclamoDTO.getId());

		accionReclamoEntidad.setDetalle(accionReclamoDTO.getDetalle());
		accionReclamoEntidad.setFecha(accionReclamoDTO.getFecha());
		
	
		accionReclamoEntidad.setReclamoId(gRS.toReclamoEntidad(accionReclamoDTO.getReclamoId()));
		accionReclamoEntidad.setAnalistaId(((Analista)gUS.toUsuario(accionReclamoDTO.getAnalistaId())));
	
		
		
		return accionReclamoEntidad;
	}

	// servicios para capa de Presentacion



}
