package com.byteminds.negocio;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.AccionReclamo;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Reclamo;
import tecnofenix.entidades.Usuario;

@Stateless
@LocalBean
public class GestionReclamoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private EJBUsuarioRemoto ejbRemoto;
	private GestionUsuarioService gUS;
	private GestionAccionReclamoService gAR;
	
	
	public GestionReclamoService() {
		ejbRemoto = new EJBUsuarioRemoto();
		gUS= new GestionUsuarioService();
		gAR = new GestionAccionReclamoService();
	}

	
	public ReclamoDTO fromReclamo(Reclamo reclamoEntidad) {
		ReclamoDTO reclamoDTO = new ReclamoDTO();
		
		ArrayList<AccionReclamoDTO> accionReclamoCollectionDTO = new ArrayList<AccionReclamoDTO>();
		
		for (AccionReclamo ac: reclamoEntidad.getAccionReclamoCollection()) {
			accionReclamoCollectionDTO.add((AccionReclamoDTO)gAR.fromAccionReclamoEntidad(ac));
		}
		
		reclamoDTO.setAccionReclamoDTOCollection(accionReclamoCollectionDTO);
		reclamoDTO.setDetalle(reclamoEntidad.getDetalle());
		reclamoDTO.setEstudianteId(((EstudianteDTO)gUS.fromUsuario(reclamoEntidad.getEstudianteId())));
		reclamoDTO.setEventoId(reclamoEntidad.getEventoId());
		reclamoDTO.setFecha(reclamoEntidad.getFecha());
		reclamoDTO.setId(reclamoEntidad.getId());
		reclamoDTO.setDetalle(reclamoEntidad.getDetalle());
		
		
		return reclamoDTO;
	}
	
	
	public Reclamo toReclamoEntidad(ReclamoDTO reclamoDTO) {
		Reclamo reclamoEntidad = new Reclamo();
		
		ArrayList<AccionReclamo> accionReclamoCollection = new ArrayList<AccionReclamo>();
		
		for (AccionReclamoDTO acDTO: reclamoDTO.getAccionReclamoDTOCollection()) {
			accionReclamoCollection.add((AccionReclamo)gAR.toAccionReclamoEntidad(acDTO));
		}
		
		reclamoEntidad.setAccionReclamoCollection(accionReclamoCollection);
		reclamoEntidad.setDetalle(reclamoDTO.getDetalle());
		reclamoEntidad.setEstudianteId(((Estudiante)gUS.toUsuario(reclamoDTO.getEstudianteId())));
		reclamoEntidad.setEventoId(reclamoDTO.getEventoId());
		reclamoEntidad.setFecha(reclamoDTO.getFecha());
		reclamoEntidad.setId(reclamoDTO.getId());
		reclamoEntidad.setDetalle(reclamoDTO.getDetalle());
		
		
		return reclamoEntidad;
	}
	
	public ReclamoDTO agregarReclamo(ReclamoDTO reclamoDTO)throws PersistenciaException {
		Reclamo reclamo =ejbRemoto.crearReclamo(toReclamoEntidad(reclamoDTO));
		return fromReclamo(reclamo);
	}

	public ReclamoDTO actualizarReclamo(ReclamoDTO reclamoDTO)throws PersistenciaException {
		Reclamo reclamo =ejbRemoto.modificarReclamo(toReclamoEntidad(reclamoDTO));
		return fromReclamo(reclamo);
	}
//	public ReclamoDTO buscarReclamo(Integer id) {
//		Reclamo e = ejbRemoto.
//		return fromUsuario(e);
//	}

}
