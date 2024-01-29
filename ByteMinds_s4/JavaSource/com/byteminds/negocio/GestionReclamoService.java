package com.byteminds.negocio;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.mobile.ReclamoDTOMobile;
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
	private GestionEventoService gEvento;
	
	
	public GestionReclamoService() {
		ejbRemoto = new EJBUsuarioRemoto();
		gUS= new GestionUsuarioService();
//		gAR = new GestionAccionReclamoService();
		gEvento = new GestionEventoService();
	}

	
	public ReclamoDTO fromReclamo(Reclamo reclamoEntidad) {
		ReclamoDTO reclamoDTO = new ReclamoDTO();
		
//		ArrayList<AccionReclamoDTO> accionReclamoCollectionDTO = new ArrayList<AccionReclamoDTO>();
//		
//		for (AccionReclamo ac: reclamoEntidad.getAccionReclamoCollection()) {
//			accionReclamoCollectionDTO.add((AccionReclamoDTO)gAR.fromAccionReclamoEntidad(ac));
//		}
//		
//		reclamoDTO.setAccionReclamoDTOCollection(accionReclamoCollectionDTO);
		reclamoDTO.setTitulo(reclamoEntidad.getTitulo());
		reclamoDTO.setDetalle(reclamoEntidad.getDetalle());
		reclamoDTO.setEstudianteId(((EstudianteDTO)gUS.fromUsuario(reclamoEntidad.getEstudianteId())));
		reclamoDTO.setEventoId(gEvento.fromEvento(reclamoEntidad.getEventoId()));
		reclamoDTO.setFecha(reclamoEntidad.getFecha());
		reclamoDTO.setId(reclamoEntidad.getId());
		reclamoDTO.setDetalle(reclamoEntidad.getDetalle());
		reclamoDTO.setCreditos(reclamoEntidad.getCreditos());
		reclamoDTO.setSemestre(reclamoEntidad.getSemestre());
		reclamoDTO.setActivo(reclamoEntidad.getActivo());
		
		return reclamoDTO;
	}
	
	
	public Reclamo toReclamoEntidad(ReclamoDTO reclamoDTO) {
		Reclamo reclamoEntidad = new Reclamo();
		
//		ArrayList<AccionReclamo> accionReclamoCollection = new ArrayList<AccionReclamo>();
//		
//		for (AccionReclamoDTO acDTO: reclamoDTO.getAccionReclamoDTOCollection()) {
//			accionReclamoCollection.add((AccionReclamo)gAR.toAccionReclamoEntidad(acDTO));
//		}
//		
//		reclamoEntidad.setAccionReclamoCollection(accionReclamoCollection);
		reclamoEntidad.setCreditos(reclamoDTO.getCreditos());
		reclamoEntidad.setSemestre(reclamoDTO.getSemestre());
		reclamoEntidad.setTitulo(reclamoDTO.getTitulo());
		reclamoEntidad.setDetalle(reclamoDTO.getDetalle());
		reclamoEntidad.setEstudianteId(((Estudiante)gUS.toUsuario(reclamoDTO.getEstudianteId())));
		reclamoEntidad.setEventoId(gEvento.toEventoEntidad(reclamoDTO.getEventoId()));
		reclamoEntidad.setFecha(reclamoDTO.getFecha());
		reclamoEntidad.setId(reclamoDTO.getId());
		reclamoEntidad.setActivo(reclamoDTO.getActivo());
		
		
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
	
	public ReclamoDTO buscarReclamo(Integer id) {
		Reclamo e = ejbRemoto.buscarReclamoPorId(id);
		return fromReclamo(e);
	}
	
	
	public List<ReclamoDTO> listarReclamos() {
		List<Reclamo> reclamosList = ejbRemoto.listarReclamos();
		List<ReclamoDTO> reclamosDTOlist = new ArrayList<ReclamoDTO>();
		
		for(Reclamo rec: reclamosList) {
			reclamosDTOlist.add(this.fromReclamo(rec));
		}
		
		return reclamosDTOlist;
	}
	
	public List<ReclamoDTOMobile> listarReclamosMobile() {
		List<Reclamo> reclamosList = ejbRemoto.listarReclamos();
		List<ReclamoDTOMobile> reclamosDTOlist = new ArrayList<ReclamoDTOMobile>();
		
		for(Reclamo rec: reclamosList) {
			reclamosDTOlist.add(toMobile(this.fromReclamo(rec)));
		}
		
		return reclamosDTOlist;
	}

	
	
	
	public ReclamoDTO agregarReclamoMobile(ReclamoDTOMobile reclamoDTOMobile)throws PersistenciaException {
		
		ReclamoDTO reclamoDeMobile = new ReclamoDTO();
		EventoDTO eventoDTO = new EventoDTO();
		
		eventoDTO=gEvento.obtenerEvento(reclamoDTOMobile.getEventoId());
		reclamoDeMobile.setEventoId(eventoDTO);
		
		EstudianteDTO estudianteDTO = new EstudianteDTO();
		
		estudianteDTO = (EstudianteDTO)gUS.buscarUsuario(reclamoDTOMobile.getEstudianteId());
		reclamoDeMobile.setEstudianteId(estudianteDTO);
		
		if (reclamoDTOMobile.getCreditos() != null) {
		    reclamoDeMobile.setCreditos(reclamoDTOMobile.getCreditos());
		}

		if (reclamoDTOMobile.getSemestre() != null) {
		    reclamoDeMobile.setSemestre(reclamoDTOMobile.getSemestre());
		}

		if (reclamoDTOMobile.getTitulo() != null) {
		    reclamoDeMobile.setTitulo(reclamoDTOMobile.getTitulo());
		}

		if (reclamoDTOMobile.getDetalle() != null) {
		    reclamoDeMobile.setDetalle(reclamoDTOMobile.getDetalle());
		}

		if (reclamoDTOMobile.getFecha() != null) {
		    reclamoDeMobile.setFecha(reclamoDTOMobile.getFecha());
		}
		reclamoDeMobile.setActivo(reclamoDTOMobile.getActivo());

		
		Reclamo reclamo =ejbRemoto.crearReclamo(toReclamoEntidad(reclamoDeMobile));
		return fromReclamo(reclamo);
	}
	
	public ReclamoDTO actualizarReclamoMobile(ReclamoDTOMobile reclamoDTOMobile)throws PersistenciaException {
		
		if(reclamoDTOMobile.getId() !=null) {
		ReclamoDTO reclamoDeMobile = buscarReclamo(reclamoDTOMobile.getId());
		EventoDTO eventoDTO = new EventoDTO();
		
		eventoDTO=gEvento.obtenerEvento(reclamoDTOMobile.getEventoId());
		reclamoDeMobile.setEventoId(eventoDTO);
		
		EstudianteDTO estudianteDTO = new EstudianteDTO();
		
		estudianteDTO = (EstudianteDTO)gUS.buscarUsuario(reclamoDTOMobile.getEstudianteId());
		reclamoDeMobile.setEstudianteId(estudianteDTO);

		if (reclamoDTOMobile.getCreditos() != null) {
		    reclamoDeMobile.setCreditos(reclamoDTOMobile.getCreditos());
		}

		if (reclamoDTOMobile.getSemestre() != null) {
		    reclamoDeMobile.setSemestre(reclamoDTOMobile.getSemestre());
		}

		if (reclamoDTOMobile.getTitulo() != null) {
		    reclamoDeMobile.setTitulo(reclamoDTOMobile.getTitulo());
		}

		if (reclamoDTOMobile.getDetalle() != null) {
		    reclamoDeMobile.setDetalle(reclamoDTOMobile.getDetalle());
		}

		if (reclamoDTOMobile.getFecha() != null) {
		    reclamoDeMobile.setFecha(reclamoDTOMobile.getFecha());
		}
		reclamoDeMobile.setActivo(reclamoDTOMobile.getActivo());
		
		
		Reclamo reclamo =ejbRemoto.modificarReclamo(toReclamoEntidad(reclamoDeMobile));
		
		return fromReclamo(reclamo);
		
		}else {
			return null;
			}
	}
	
	public ReclamoDTOMobile toMobile(ReclamoDTO reclamoDTO) {
		ReclamoDTOMobile reclamoDTOMobile = new ReclamoDTOMobile();
			
		reclamoDTOMobile.setEventoId(reclamoDTO.getEventoId().getId());

		reclamoDTOMobile.setEstudianteId(reclamoDTO.getEstudianteId().getId());
		
		if (reclamoDTO.getCreditos() != null) {
			reclamoDTOMobile.setCreditos(reclamoDTO.getCreditos());
		}

		if (reclamoDTO.getSemestre() != null) {
			reclamoDTOMobile.setSemestre(reclamoDTO.getSemestre());
		}

		if (reclamoDTO.getTitulo() != null) {
			reclamoDTOMobile.setTitulo(reclamoDTO.getTitulo());
		}

		if (reclamoDTO.getDetalle() != null) {
			reclamoDTOMobile.setDetalle(reclamoDTO.getDetalle());
		}

		if (reclamoDTO.getFecha() != null) {
			reclamoDTOMobile.setFecha(reclamoDTO.getFecha());
		}
		reclamoDTOMobile.setActivo(reclamoDTO.getActivo());
		
		
		
		return reclamoDTOMobile;
	}
	}
