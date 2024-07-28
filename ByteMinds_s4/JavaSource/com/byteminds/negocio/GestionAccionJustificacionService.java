package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.AccionJustificacion;
//import tecnofenix.entidades.AccionJustificacion;
import tecnofenix.entidades.Analista;



@Stateless
@LocalBean
public class GestionAccionJustificacionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EJBUsuarioRemoto ejbRemoto;
	GestionJustificacionService gJus;
	GestionUsuarioService gUS;
	
	
	
	public GestionAccionJustificacionService() {
		ejbRemoto = new EJBUsuarioRemoto();
		gJus = new GestionJustificacionService();
		gUS = new GestionUsuarioService();
	}
	
	public AccionJustificacionDTO fromAccionJustificacionEntidad(AccionJustificacion accionJustificacionEntidad) {
		AccionJustificacionDTO accionJustificacionDTO = new AccionJustificacionDTO();
		

		accionJustificacionDTO.setId(accionJustificacionEntidad.getId());

		accionJustificacionDTO.setDetalle(accionJustificacionEntidad.getDetalle());
		accionJustificacionDTO.setFecha(accionJustificacionEntidad.getFecha());
		
		accionJustificacionDTO.setJustificacionId(gJus.fromJustificacion(accionJustificacionEntidad.getJustificacionId()));
		
		accionJustificacionDTO.setAnalistaId(((AnalistaDTO)gUS.fromUsuario(accionJustificacionEntidad.getAnalistaId())));
		accionJustificacionDTO.setActivo(accionJustificacionEntidad.getActivo());
		
		
		return accionJustificacionDTO;
	}

	public AccionJustificacion toAccionJustificacionEntidad(AccionJustificacionDTO accionJustificacionDTO) {
		AccionJustificacion accionJustificacionEntidad = new AccionJustificacion();
		
		
		accionJustificacionEntidad.setId(accionJustificacionDTO.getId());
		accionJustificacionEntidad.setDetalle(accionJustificacionDTO.getDetalle());
		accionJustificacionEntidad.setFecha(accionJustificacionDTO.getFecha());		
		
		accionJustificacionEntidad.setJustificacionId(gJus.toJustificacionEntidad(accionJustificacionDTO.getJustificacionId()));
		accionJustificacionEntidad.setAnalistaId(((Analista)gUS.toUsuario(accionJustificacionDTO.getAnalistaId())));
		accionJustificacionEntidad.setActivo(accionJustificacionDTO.getActivo());
		return accionJustificacionEntidad;
	}
	
	
	public AccionJustificacionDTO agregarAccionAJustificacionDTO(AccionJustificacionDTO acRec) {
		AccionJustificacion accionJustificacion = ejbRemoto.crearAccionJustificacion(toAccionJustificacionEntidad(acRec));
		
		return fromAccionJustificacionEntidad(accionJustificacion);
	}
	public AccionJustificacionDTO modificarAccionAJustificacionDTO(AccionJustificacionDTO acRec) {
		AccionJustificacion accionJustificacion = ejbRemoto.modificarAccionJustificacion(toAccionJustificacionEntidad(acRec));
		
		return fromAccionJustificacionEntidad(accionJustificacion);
	}
	
	public List<AccionJustificacionDTO> listarAccionAJustificacionDTO(Integer reclamoID) {
		List<AccionJustificacion> list = ejbRemoto.listAllAccionJustificacionByJustificacionID(reclamoID);
		List<AccionJustificacionDTO> listaDTO = new ArrayList<AccionJustificacionDTO>();
		for(AccionJustificacion acEntity: list) {
			listaDTO.add(fromAccionJustificacionEntidad(acEntity));
		}
		
		return listaDTO;
	}
}
