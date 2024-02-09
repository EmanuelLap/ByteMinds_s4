package com.byteminds.negocio;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.TipoEstadoReclamo;

@Stateless
@LocalBean
public class GestionTipoEstadoReclamoService implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private EJBUsuarioRemoto ejbRemoto;
	
	public GestionTipoEstadoReclamoService() {
	ejbRemoto = new EJBUsuarioRemoto();
	}
	
	
	public TipoEstadoReclamoDTO fromTipoEstadoReclamo (TipoEstadoReclamo tipoEstadoReclamoEntidad) {
		TipoEstadoReclamoDTO tipoEstadoReclamoDTO = new TipoEstadoReclamoDTO();
		
		tipoEstadoReclamoDTO.setId(tipoEstadoReclamoEntidad.getId());
		tipoEstadoReclamoDTO.setNombre(tipoEstadoReclamoEntidad.getNombre());
		tipoEstadoReclamoDTO.setActivo(tipoEstadoReclamoEntidad.getActivo());

		
		return tipoEstadoReclamoDTO;
	}
	
	public TipoEstadoReclamo toTipoEstadoReclamo (TipoEstadoReclamoDTO tipoEstadoReclamoDTO) {
		TipoEstadoReclamo tipoEstadoReclamo = new TipoEstadoReclamo();
		
		tipoEstadoReclamo.setId(tipoEstadoReclamoDTO.getId());
		tipoEstadoReclamo.setNombre(tipoEstadoReclamoDTO.getNombre());
		tipoEstadoReclamo.setActivo(tipoEstadoReclamoDTO.getActivo());
		
		return tipoEstadoReclamo;
	}	
	
	public TipoEstadoReclamoDTO obtenerTipoEstadoReclamoDTO(Integer id) {
		
		TipoEstadoReclamoDTO tee= new TipoEstadoReclamoDTO();
		
		tee=fromTipoEstadoReclamo(ejbRemoto.buscarTipoEstadoReclamoPor(String.valueOf(id), null).get(0));
		
		return tee;
	}
	
	
	public List<TipoEstadoReclamoDTO> listarTipoEstadoReclamo() {
		List<TipoEstadoReclamo> listTEE = ejbRemoto.listarTipoEstadoReclamo();
		List<TipoEstadoReclamoDTO> listTEEDTO= new ArrayList<TipoEstadoReclamoDTO>();	
		
		for (TipoEstadoReclamo tEE : listTEE) {
			listTEEDTO.add(fromTipoEstadoReclamo(tEE));
		}
		return listTEEDTO;
	}
	
	public TipoEstadoReclamoDTO guardar(TipoEstadoReclamoDTO terDTO) {
		TipoEstadoReclamo terTemp = new TipoEstadoReclamo();
		if(terDTO.getId()==null) {	
			terTemp=ejbRemoto.crearTipoEstadoReclamo(toTipoEstadoReclamo(terDTO));
		}else {
			terTemp=ejbRemoto.editarTipoEstadoReclamo(toTipoEstadoReclamo(terDTO));
		}
	
		return fromTipoEstadoReclamo(terTemp);
	}
	
}
