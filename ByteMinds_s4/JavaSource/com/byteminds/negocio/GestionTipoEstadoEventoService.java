package com.byteminds.negocio;

import java.io.Serializable;

import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.TipoEstadoEvento;

public class GestionTipoEstadoEventoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EJBUsuarioRemoto ejbRemoto;
	
	public GestionTipoEstadoEventoService() {
	ejbRemoto = new EJBUsuarioRemoto();
	}
	
	
	public TipoEstadoEventoDTO fromTipoEstadoEvento (TipoEstadoEvento tipoEstadoEventoEntidad) {
		TipoEstadoEventoDTO tipoEstadoEventoDTO = new TipoEstadoEventoDTO();
		
		
		
		return tipoEstadoEventoDTO;
	}
	
	public TipoEstadoEvento toTipoEstadoEvento (TipoEstadoEventoDTO tipoEstadoEventoDTO) {
		TipoEstadoEvento tipoEstadoEvento = new TipoEstadoEvento();
		
		
		
		return tipoEstadoEvento;
	}	
	

}
