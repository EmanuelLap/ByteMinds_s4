package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Rol;
import tecnofenix.entidades.TipoTutorTipo;


@Stateless
@LocalBean
public class GestionTipoTutorService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EJBUsuarioRemoto ejbRemoto;

	
	public GestionTipoTutorService() {
		ejbRemoto = new EJBUsuarioRemoto();
	}
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


	public TipoTutorDTO obtenerTipoTutorPorId(Integer id) {
		List<TipoTutorTipo> listTTT=new ArrayList<TipoTutorTipo>();
		
		listTTT=ejbRemoto.buscarTipoTutorTipoPor(String.valueOf(id),null);
		
		return fromTipoTutorTipo(listTTT.get(0));
	}
	
	public List<TipoTutorDTO> listarTipoTutorDTO() {
		List<TipoTutorTipo> listTTT=new ArrayList<TipoTutorTipo>();
		List<TipoTutorDTO> listTTTDTO=new ArrayList<TipoTutorDTO>();
		
		listTTT=ejbRemoto.listarTipoTutorTipo();
		for(TipoTutorTipo ttt: listTTT) {
			listTTTDTO.add(fromTipoTutorTipo(ttt));
		}
		
		return listTTTDTO;
	}

}
