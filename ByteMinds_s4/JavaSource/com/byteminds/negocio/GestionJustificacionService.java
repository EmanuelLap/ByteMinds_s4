package com.byteminds.negocio;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.exception.PersistenciaException;
//import com.byteminds.negocio.mobile.JustificacionDTOMobile;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Justificacion;


@Stateless
@LocalBean
public class GestionJustificacionService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private EJBUsuarioRemoto ejbRemoto;
	private GestionUsuarioService gUS;
	private GestionAccionJustificacionService gAR;
	private GestionEventoService gEvento;
	private GestionTipoEstadoJustificacionService gTERS;
	
	public GestionJustificacionService() {
		ejbRemoto = new EJBUsuarioRemoto();
		gUS= new GestionUsuarioService();

		gEvento = new GestionEventoService();
		gTERS= new GestionTipoEstadoJustificacionService();
	}

	
	public JustificacionDTO fromJustificacion(Justificacion justificacionEntidad) {
		JustificacionDTO justificacionDTO = new JustificacionDTO();
		
//		ArrayList<AccionJustificacionDTO> accionJustificacionCollectionDTO = new ArrayList<AccionJustificacionDTO>();
//		
//		for (AccionJustificacion ac: reclamoEntidad.getAccionJustificacionCollection()) {
//			accionJustificacionCollectionDTO.add((AccionJustificacionDTO)gAR.fromAccionJustificacionEntidad(ac));
//		}
//		
//		reclamoDTO.setAccionJustificacionDTOCollection(accionJustificacionCollectionDTO);
		justificacionDTO.setDetalle(justificacionEntidad.getDetalle());
		justificacionDTO.setEstudianteId(((EstudianteDTO)gUS.fromUsuario(justificacionEntidad.getEstudianteId())));
		justificacionDTO.setEventoId(gEvento.fromEvento(justificacionEntidad.getEventoId()));
		justificacionDTO.setFecha(justificacionEntidad.getFecha());
		justificacionDTO.setId(justificacionEntidad.getId());
		justificacionDTO.setDetalle(justificacionEntidad.getDetalle());

		justificacionDTO.setActivo(justificacionEntidad.getActivo());
		if(justificacionEntidad.getEstadoJustificacionId() !=null) {
		justificacionDTO.setEstadoJustificacionId(gTERS.fromTipoEstadoJustificacion(justificacionEntidad.getEstadoJustificacionId()));
		}
		if(justificacionEntidad.getFechaEstadoJustificacion()!=null) {
		justificacionDTO.setFechaEstadoJustificacion(justificacionEntidad.getFechaEstadoJustificacion());
		}
		return justificacionDTO;
	}
	
	
	public Justificacion toJustificacionEntidad(JustificacionDTO justificacionDTO) {
		Justificacion justificacionEntidad = new Justificacion();
		
//		ArrayList<AccionJustificacion> accionJustificacionCollection = new ArrayList<AccionJustificacion>();
//		
//		for (AccionJustificacionDTO acDTO: reclamoDTO.getAccionJustificacionDTOCollection()) {
//			accionJustificacionCollection.add((AccionJustificacion)gAR.toAccionJustificacionEntidad(acDTO));
//		}
//		
//		reclamoEntidad.setAccionJustificacionCollection(accionJustificacionCollection);

		justificacionEntidad.setDetalle(justificacionDTO.getDetalle());
		justificacionEntidad.setEstudianteId(((Estudiante)gUS.toUsuario(justificacionDTO.getEstudianteId())));
		justificacionEntidad.setEventoId(gEvento.toEventoEntidad(justificacionDTO.getEventoId()));
		justificacionEntidad.setFecha(justificacionDTO.getFecha());
		justificacionEntidad.setId(justificacionDTO.getId());
		justificacionEntidad.setActivo(justificacionDTO.getActivo());
		if(justificacionDTO.getEstadoJustificacionId()!=null) {
			justificacionEntidad.setEstadoJustificacionId(gTERS.toTipoEstadoJustificacion(justificacionDTO.getEstadoJustificacionId()));
		}
		if(justificacionDTO.getFechaEstadoJustificacion()!=null) {
			justificacionEntidad.setFechaEstadoJustificacion(justificacionDTO.getFechaEstadoJustificacion());
		}
		return justificacionEntidad;
	}
	
	public JustificacionDTO agregarJustificacion(JustificacionDTO justificacionDTO)throws PersistenciaException {
		Justificacion justificacion =ejbRemoto.crearJustificacion(toJustificacionEntidad(justificacionDTO));
		return fromJustificacion(justificacion);
	}

	public JustificacionDTO actualizarJustificacion(JustificacionDTO justificacionDTO)throws PersistenciaException {
		System.out.println("public JustificacionDTO actualizarJustificacion(JustificacionDTO justificacionDTO)throws PersistenciaException ");
		Justificacion justificacion =ejbRemoto.modificarJustificacion(toJustificacionEntidad(justificacionDTO));
		return fromJustificacion(justificacion);
	}
	
	public JustificacionDTO buscarJustificacion(Integer id) {
		Justificacion justificacion = ejbRemoto.buscarJustificacionPorId(id);
		return fromJustificacion(justificacion);
	}
	public List<JustificacionDTO> buscarJustificacionsEstudiante(Integer estudianteId) {

		List<Justificacion> justificacionList = ejbRemoto.buscarJustificacionEstudiante(estudianteId);
		List<JustificacionDTO> justificacionDTOlist = new ArrayList<JustificacionDTO>();
		
		for(Justificacion rec: justificacionList) {
			justificacionDTOlist.add(this.fromJustificacion(rec));
		}
		
		return justificacionDTOlist;
	}
	
	public List<JustificacionDTO> listarJustificacions() {
		List<Justificacion> justificacionList = ejbRemoto.listarJustificacion();
		List<JustificacionDTO> justificacionDTOlist = new ArrayList<JustificacionDTO>();
		
		for(Justificacion rec: justificacionList) {
			justificacionDTOlist.add(this.fromJustificacion(rec));
		}
		
		return justificacionDTOlist;
	}
	
//	public List<JustificacionDTOMobile> listarJustificacionsMobile() {
//		List<Justificacion> reclamosList = ejbRemoto.listarJustificacions();
//		List<JustificacionDTOMobile> reclamosDTOlist = new ArrayList<JustificacionDTOMobile>();
//		
//		for(Justificacion rec: reclamosList) {
//			reclamosDTOlist.add(toMobile(this.fromJustificacion(rec)));
//		}
//		
//		return reclamosDTOlist;
//	}

	
	
	
//	public JustificacionDTO agregarJustificacionMobile(JustificacionDTOMobile reclamoDTOMobile)throws PersistenciaException {
//		
//		JustificacionDTO reclamoDeMobile = new JustificacionDTO();
//		EventoDTO eventoDTO = new EventoDTO();
//		
//		eventoDTO=gEvento.obtenerEvento(reclamoDTOMobile.getEventoId());
//		reclamoDeMobile.setEventoId(eventoDTO);
//		
//		EstudianteDTO estudianteDTO = new EstudianteDTO();
//		
//		estudianteDTO = (EstudianteDTO)gUS.buscarUsuario(reclamoDTOMobile.getEstudianteId());
//		reclamoDeMobile.setEstudianteId(estudianteDTO);
//		
//		if (reclamoDTOMobile.getCreditos() != null) {
//		    reclamoDeMobile.setCreditos(reclamoDTOMobile.getCreditos());
//		}
//
//		if (reclamoDTOMobile.getSemestre() != null) {
//		    reclamoDeMobile.setSemestre(reclamoDTOMobile.getSemestre());
//		}
//
//		if (reclamoDTOMobile.getTitulo() != null) {
//		    reclamoDeMobile.setTitulo(reclamoDTOMobile.getTitulo());
//		}
//
//		if (reclamoDTOMobile.getDetalle() != null) {
//		    reclamoDeMobile.setDetalle(reclamoDTOMobile.getDetalle());
//		}
//
//		if (reclamoDTOMobile.getFecha() != null) {
//		    reclamoDeMobile.setFecha(reclamoDTOMobile.getFecha());
//		}
//		reclamoDeMobile.setActivo(reclamoDTOMobile.getActivo());
//
//		
//		Justificacion reclamo =ejbRemoto.crearJustificacion(toJustificacionEntidad(reclamoDeMobile));
//		return fromJustificacion(reclamo);
//	}
	
//	public JustificacionDTO actualizarJustificacionMobile(JustificacionDTOMobile reclamoDTOMobile)throws PersistenciaException {
//		
//		if(reclamoDTOMobile.getId() !=null) {
//		JustificacionDTO reclamoDeMobile = buscarJustificacion(reclamoDTOMobile.getId());
//		EventoDTO eventoDTO = new EventoDTO();
//		
//		eventoDTO=gEvento.obtenerEvento(reclamoDTOMobile.getEventoId());
//		reclamoDeMobile.setEventoId(eventoDTO);
//		
//		EstudianteDTO estudianteDTO = new EstudianteDTO();
//		
//		estudianteDTO = (EstudianteDTO)gUS.buscarUsuario(reclamoDTOMobile.getEstudianteId());
//		reclamoDeMobile.setEstudianteId(estudianteDTO);
//
//		if (reclamoDTOMobile.getCreditos() != null) {
//		    reclamoDeMobile.setCreditos(reclamoDTOMobile.getCreditos());
//		}
//
//		if (reclamoDTOMobile.getSemestre() != null) {
//		    reclamoDeMobile.setSemestre(reclamoDTOMobile.getSemestre());
//		}
//
//		if (reclamoDTOMobile.getTitulo() != null) {
//		    reclamoDeMobile.setTitulo(reclamoDTOMobile.getTitulo());
//		}
//
//		if (reclamoDTOMobile.getDetalle() != null) {
//		    reclamoDeMobile.setDetalle(reclamoDTOMobile.getDetalle());
//		}
//
//		if (reclamoDTOMobile.getFecha() != null) {
//		    reclamoDeMobile.setFecha(reclamoDTOMobile.getFecha());
//		}
//		reclamoDeMobile.setActivo(reclamoDTOMobile.getActivo());
//		
//		
//		Justificacion reclamo =ejbRemoto.modificarJustificacion(toJustificacionEntidad(reclamoDeMobile));
//		
//		return fromJustificacion(reclamo);
//		
//		}else {
//			return null;
//			}
//	}
	
//	public JustificacionDTOMobile toMobile(JustificacionDTO reclamoDTO) {
//		JustificacionDTOMobile reclamoDTOMobile = new JustificacionDTOMobile();
//			
//		reclamoDTOMobile.setEventoId(reclamoDTO.getEventoId().getId());
//
//		reclamoDTOMobile.setEstudianteId(reclamoDTO.getEstudianteId().getId());
//		
//		if (reclamoDTO.getId() != null) {
//			reclamoDTOMobile.setId(reclamoDTO.getId());
//		}
//		
//		
//		if (reclamoDTO.getCreditos() != null) {
//			reclamoDTOMobile.setCreditos(reclamoDTO.getCreditos());
//		}
//
//		if (reclamoDTO.getSemestre() != null) {
//			reclamoDTOMobile.setSemestre(reclamoDTO.getSemestre());
//		}
//
//		if (reclamoDTO.getTitulo() != null) {
//			reclamoDTOMobile.setTitulo(reclamoDTO.getTitulo());
//		}
//
//		if (reclamoDTO.getDetalle() != null) {
//			reclamoDTOMobile.setDetalle(reclamoDTO.getDetalle());
//		}
//
//		if (reclamoDTO.getFecha() != null) {
//			reclamoDTOMobile.setFecha(reclamoDTO.getFecha());
//		}
//		reclamoDTOMobile.setActivo(reclamoDTO.getActivo());
//		
//		
//		
//		return reclamoDTOMobile;
//	}
	}
