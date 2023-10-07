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
		
		tipoEstadoEventoDTO.setId(tipoEstadoEventoEntidad.getId());
		tipoEstadoEventoDTO.setNombre(tipoEstadoEventoEntidad.getNombre());
		tipoEstadoEventoDTO.setActivo(tipoEstadoEventoEntidad.getActivo());

		
		return tipoEstadoEventoDTO;
	}
	
	public TipoEstadoEvento toTipoEstadoEvento (TipoEstadoEventoDTO tipoEstadoEventoDTO) {
		TipoEstadoEvento tipoEstadoEvento = new TipoEstadoEvento();
		
		tipoEstadoEvento.setId(tipoEstadoEventoDTO.getId());
		tipoEstadoEvento.setNombre(tipoEstadoEventoDTO.getNombre());
		tipoEstadoEvento.setActivo(tipoEstadoEventoDTO.getActivo());
		
		return tipoEstadoEvento;
	}	
	
	public TipoEstadoEventoDTO obtenerTipoEstadoEventoDTO(Integer id) {
		
		TipoEstadoEventoDTO tee= new TipoEstadoEventoDTO();
		
		tee=fromTipoEstadoEvento(ejbRemoto.buscarTipoEstadoEventoPor(String.valueOf(id), null).get(0));
		
		return tee;
	}
	

}
