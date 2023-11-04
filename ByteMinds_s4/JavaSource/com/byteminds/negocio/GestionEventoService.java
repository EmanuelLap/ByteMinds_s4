package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Evento;
import tecnofenix.entidades.ModalidadEvento;
import tecnofenix.entidades.TipoEvento;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.TutorResponsableEvento;

@Stateless
@LocalBean
public class GestionEventoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private EJBUsuarioRemoto ejbRemoto;
	private GestionItrService gITR;
	private GestionTipoEstadoEventoService gTEE;
	private GestionTutorResponsableEventoService gTRE;
	private GestionUsuarioService gUS;
	private GestionTipoEventoService gTE;
	private GestionModalidadEventoService gME;

	public GestionEventoService() {
		ejbRemoto = new EJBUsuarioRemoto();
		gITR = new GestionItrService();
		gTEE = new GestionTipoEstadoEventoService();
		gTRE = new GestionTutorResponsableEventoService();
		gUS = new GestionUsuarioService();
		gTE = new GestionTipoEventoService();
		gME = new GestionModalidadEventoService();
	}

	public EventoDTO fromEvento (Evento evento) {
		EventoDTO eventoDTO = new EventoDTO();
		
		eventoDTO.setId(evento.getId());
		eventoDTO.setTitulo(evento.getTitulo());
		
		eventoDTO.setBajaLogica(evento.getBajaLogica());
		eventoDTO.setInicio(evento.getInicio());
		eventoDTO.setFin(evento.getFin());
		
		List<TutorResponsableEventoDTO> listaDeTutoresDTO = new ArrayList<TutorResponsableEventoDTO>();
		try {

			evento.setTutorResponsableEventoCollection(ejbRemoto.obtenerTutoresDeEvento(evento.getId()));
		if(evento.getTutorResponsableEventoCollection() != null || !evento.getTutorResponsableEventoCollection().isEmpty()) {
			for(TutorResponsableEvento tre: evento.getTutorResponsableEventoCollection()) {
				listaDeTutoresDTO.add(gTRE.fromTutorResponsableEvento(tre));
			}
		}
		} catch ( Exception e) {
			System.out.println("Error al intentar traer la lista de tutores del evento!");
		}
		eventoDTO.setTutorResponsableEventoDTOCollection(listaDeTutoresDTO);
		
		eventoDTO.setItrDTO(gITR.fromITR(evento.getItr()));
		eventoDTO.setLocalizacion(evento.getLocalizacion());
		
		eventoDTO.setModalidadEvento(gME.fromModalidadEvento(evento.getModalidad()));
		eventoDTO.setTipoEstadoEventoDTO(gTEE.fromTipoEstadoEvento(evento.getTipoEstadoEvento()));
		eventoDTO.setTipoEvento(gTE.fromTipoEvento(evento.getTipo()));
		
		
		return eventoDTO;
	}
	public Evento toEventoEntidad (EventoDTO eventoDTO) {
		Evento evento = new Evento();
		
		evento.setId(eventoDTO.getId());
		evento.setTitulo(eventoDTO.getTitulo());
		
		evento.setBajaLogica(eventoDTO.getBajaLogica());
		evento.setInicio(eventoDTO.getInicio());
		evento.setFin(eventoDTO.getFin());
		
		evento.setItr(gITR.toITR(eventoDTO.getItrDTO()));
		evento.setLocalizacion(eventoDTO.getLocalizacion());

		evento.setModalidad(gME.toModalidadEvento(eventoDTO.getModalidadEvento()));
		evento.setTipoEstadoEvento(gTEE.toTipoEstadoEvento(eventoDTO.getTipoEstadoEvento()));
		evento.setTipo(gTE.toTipoEvento(eventoDTO.getTipoEvento()));
		
		

		List<TutorResponsableEvento> listaDeTutores = new ArrayList<TutorResponsableEvento>();
		if(eventoDTO.getTutorResponsableEventoDTOCollection() != null || !eventoDTO.getTutorResponsableEventoDTOCollection().isEmpty()) {
			for(TutorResponsableEventoDTO treDTO: eventoDTO.getTutorResponsableEventoDTOCollection()) {
				listaDeTutores.add(gTRE.toTutorResponsableEvento(treDTO));
			}
		}
		
		evento.setTutorResponsableEventoCollection(listaDeTutores);
		
		return evento;
	}
	
	public EventoDTO obtenerEvento(Integer id) {
		return fromEvento(ejbRemoto.obtenerEvento(id)) ;
		
		
	}
	
	public List<EventoDTO> listarEventosTutor(UsuarioDTO tutor) {
		
		List<EventoDTO> listadoEventosDTO = new ArrayList<EventoDTO>();
		List<Evento> listadoEventosEntity = new ArrayList<Evento>();
		
		listadoEventosEntity=ejbRemoto.listarEventosTutor(gUS.toUsuario(tutor));
		
		for(Evento ev : listadoEventosEntity) {
			listadoEventosDTO.add(fromEvento(ev));
		}
		
		 return listadoEventosDTO;

	}
	
	public List<EventoDTO> listarEventosDTO() {
		List<EventoDTO> listadoEventosDTO = new ArrayList<EventoDTO>();
		List<Evento> listadoEventosEntity = new ArrayList<Evento>();
		listadoEventosEntity.addAll(ejbRemoto.listarEventos());
		
		for(Evento ev : listadoEventosEntity) {
			listadoEventosDTO.add(fromEvento(ev));
		}
		
		 return listadoEventosDTO;

	}
	
	public List<EventoDTO> buscarEventosPor(String id, String titulo,String localizacion,
			String modalidad,String tipoEvento,String itrNombre,
			Date inicioInicio, Date finInicio,
			Date inicioFin, Date finFin,
			Boolean activo) {
		List<EventoDTO> listadoEventosDTO = new ArrayList<EventoDTO>();
		List<Evento> listadoEventosEntity = new ArrayList<Evento>();
		listadoEventosEntity.addAll(ejbRemoto.buscarEventosPor(id, titulo, localizacion, modalidad, tipoEvento, itrNombre, inicioInicio, finInicio, inicioFin, finFin, activo));
		for(Evento ev : listadoEventosEntity) {
			listadoEventosDTO.add(fromEvento(ev));
		}
		
		 return listadoEventosDTO;

	}
	
	public EventoDTO agregarEvento(EventoDTO eventoSeleccionado)throws PersistenciaException {
		Evento evento = ejbRemoto.crearEvento(toEventoEntidad(eventoSeleccionado));
		
		return fromEvento(evento);
	}
	public EventoDTO actualizarEvento(EventoDTO eventoSeleccionado)throws PersistenciaException  {
		Evento evento=ejbRemoto.modificarEvento(toEventoEntidad(eventoSeleccionado));
		return fromEvento(evento);
	}
	
}
