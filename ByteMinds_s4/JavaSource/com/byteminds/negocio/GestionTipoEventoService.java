package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoEvento;



@Stateless
@LocalBean
public class GestionTipoEventoService implements Serializable {

	private static final long serialVersionUID = 1L;
	private EJBUsuarioRemoto ejbRemoto;

	public GestionTipoEventoService() {
		ejbRemoto = new EJBUsuarioRemoto();
	}
	
	public TipoEventoDTO fromTipoEvento(TipoEvento tipoEvento) {
		TipoEventoDTO tipoEventoDTO = new TipoEventoDTO();
		

		tipoEventoDTO.setId(tipoEvento.getId());
		tipoEventoDTO.setNombre(tipoEvento.getNombre());
		tipoEventoDTO.setActivo(tipoEvento.getActivo());
		
		
		return tipoEventoDTO;
	}

	public TipoEvento toTipoEvento(TipoEventoDTO tipoEventoDTO) {
		TipoEvento tipoEvento = new TipoEvento();
		
		tipoEvento.setId(tipoEventoDTO.getId());
		tipoEvento.setNombre(tipoEventoDTO.getNombre());
		tipoEvento.setActivo(tipoEventoDTO.getActivo());
		
		return tipoEvento;
	}

	// servicios para capa de Presentacion

	public List<TipoEventoDTO> listarTipoEventos(){
		List<TipoEventoDTO> listTEDTO = new ArrayList<TipoEventoDTO>();
		List<TipoEvento> listTE = new ArrayList<TipoEvento>();
			listTE.addAll(ejbRemoto.listarTipoEvento());
		
			for(TipoEvento te:listTE) {
				listTEDTO.add(fromTipoEvento(te));
			}
			
			
		return listTEDTO;
	}
	
	public TipoEventoDTO obtenerTipoEvento(Integer id) {
		List<TipoEventoDTO> listTEDTO = new ArrayList<TipoEventoDTO>();
//		TipoEventoDTO te = new TipoEventoDTO();
		listTEDTO =listarTipoEventos();
		
		for (TipoEventoDTO te :listTEDTO) {
			if(te.getId().equals(id))return te;
		}
		
		
		return null;
	}
	
}
