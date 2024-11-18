package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.TipoEstadoJustificacion;

@Stateless
@LocalBean
public class GestionTipoEstadoJustificacionService implements Serializable {

	private static final long serialVersionUID = 1L;

	private EJBUsuarioRemoto ejbRemoto;

	public GestionTipoEstadoJustificacionService() {
		ejbRemoto = new EJBUsuarioRemoto();
	}

	public TipoEstadoJustificacionDTO fromTipoEstadoJustificacion(
			TipoEstadoJustificacion tipoEstadoJustificacionEntidad) {
		TipoEstadoJustificacionDTO tipoEstadoJustificacionDTO = new TipoEstadoJustificacionDTO();

		tipoEstadoJustificacionDTO.setId(tipoEstadoJustificacionEntidad.getId());
		tipoEstadoJustificacionDTO.setNombre(tipoEstadoJustificacionEntidad.getNombre());
		tipoEstadoJustificacionDTO.setActivo(tipoEstadoJustificacionEntidad.getActivo());

		return tipoEstadoJustificacionDTO;
	}

	public TipoEstadoJustificacion toTipoEstadoJustificacion(TipoEstadoJustificacionDTO tipoEstadoJustificacionDTO) {
		TipoEstadoJustificacion tipoEstadoJustificacion = new TipoEstadoJustificacion();

		tipoEstadoJustificacion.setId(tipoEstadoJustificacionDTO.getId());
		tipoEstadoJustificacion.setNombre(tipoEstadoJustificacionDTO.getNombre());
		tipoEstadoJustificacion.setActivo(tipoEstadoJustificacionDTO.getActivo());

		return tipoEstadoJustificacion;
	}

	public TipoEstadoJustificacionDTO obtenerTipoEstadoJustificacionDTO(Integer id) {

		TipoEstadoJustificacionDTO tee = new TipoEstadoJustificacionDTO();

		tee = fromTipoEstadoJustificacion(ejbRemoto.buscarTipoEstadoJustificacionPor(String.valueOf(id), null).get(0));

		return tee;
	}

	public List<TipoEstadoJustificacionDTO> listarTipoEstadoJustificacion() {
		List<TipoEstadoJustificacion> listTEE = ejbRemoto.listarTipoEstadoJustificacion();
		List<TipoEstadoJustificacionDTO> listTEEDTO = new ArrayList<TipoEstadoJustificacionDTO>();

		for (TipoEstadoJustificacion tEE : listTEE) {
			listTEEDTO.add(fromTipoEstadoJustificacion(tEE));
		}
		return listTEEDTO;
	}

	public TipoEstadoJustificacionDTO guardar(TipoEstadoJustificacionDTO terDTO) {
		TipoEstadoJustificacion terTemp = new TipoEstadoJustificacion();
		if (terDTO.getId() == null) {
			terTemp = ejbRemoto.crearTipoEstadoJustificacion(toTipoEstadoJustificacion(terDTO));
		} else {
			terTemp = ejbRemoto.editarTipoEstadoJustificacion(toTipoEstadoJustificacion(terDTO));
		}

		return fromTipoEstadoJustificacion(terTemp);
	}

//	public TipoEstadoJustificacionDTO obtenerTipoEstadoJustificacionDTO(String id) {
//		TipoEstadoJustificacion terTemp = new TipoEstadoJustificacion();
//		List<TipoEstadoJustificacion> list = new ArrayList<TipoEstadoJustificacion>();
//		if(id!=null || id!="") {	
//			list=ejbRemoto.buscarTipoEstadoJustificacionPor(id, "");
//			if(!list.isEmpty()) {
//				terTemp=list.get(0);	
//			}
//			
//		}
//	
//		return fromTipoEstadoJustificacion(terTemp);
//	}
}
