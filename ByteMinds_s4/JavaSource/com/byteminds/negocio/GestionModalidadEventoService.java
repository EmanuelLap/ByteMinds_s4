package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.ModalidadEvento;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoEvento;



@Stateless
@LocalBean
public class GestionModalidadEventoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EJBUsuarioRemoto ejbRemoto;

	public GestionModalidadEventoService() {
		ejbRemoto = new EJBUsuarioRemoto();
	}

	public ModalidadEventoDTO fromModalidadEvento(ModalidadEvento modalidadEvento) {
		ModalidadEventoDTO modalidadEventoDTO = new ModalidadEventoDTO();
		

		modalidadEventoDTO.setId(modalidadEvento.getId());
		modalidadEventoDTO.setNombre(modalidadEvento.getNombre());
		modalidadEventoDTO.setActivo(modalidadEvento.getActivo());
		
		
		return modalidadEventoDTO;
	}

	public ModalidadEvento toModalidadEvento(ModalidadEventoDTO tipoEventoDTO) {
		ModalidadEvento tipoEvento = new ModalidadEvento();
		
		tipoEvento.setId(tipoEventoDTO.getId());
		tipoEvento.setNombre(tipoEventoDTO.getNombre());
		tipoEvento.setActivo(tipoEventoDTO.getActivo());
		
		return tipoEvento;
	}

	
	// servicios para capa de Presentacion

	public List<ModalidadEventoDTO> listarModalidaEventos(){
		List<ModalidadEventoDTO> listMEDTO = new ArrayList<ModalidadEventoDTO>();
		List<ModalidadEvento> listME = new ArrayList<ModalidadEvento>();
		listME.addAll(ejbRemoto.listarModalidadEvento());
		
			for(ModalidadEvento me:listME) {
				listMEDTO.add(fromModalidadEvento(me));
			}
			
			
		return listMEDTO;
	}
	
	public ModalidadEventoDTO obtenerModalidadEvento(Integer id) {
		List<ModalidadEventoDTO> listMEDTO = new ArrayList<ModalidadEventoDTO>();
		listMEDTO =listarModalidaEventos();
		
		for (ModalidadEventoDTO me :listMEDTO) {
			if(me.getId().equals(id))return me;
		}
		
		
		return null;
	}


}
