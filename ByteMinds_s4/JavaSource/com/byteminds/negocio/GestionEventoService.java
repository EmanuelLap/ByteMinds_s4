package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Evento;
import tecnofenix.entidades.ModalidadEvento;
import tecnofenix.entidades.TipoEvento;
import tecnofenix.entidades.TutorResponsableEvento;

public class GestionEventoService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private EJBUsuarioRemoto ejbRemoto;
	private GestionItrService gITR;
	private GestionTipoEstadoEventoService gTEE;
	private GestionTutorResponsableEventoService gTRE;
	public GestionEventoService() {
		ejbRemoto = new EJBUsuarioRemoto();
		gITR = new GestionItrService();
		gTEE = new GestionTipoEstadoEventoService();
		gTRE = new GestionTutorResponsableEventoService();
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
		
		eventoDTO.setModalidadEvento(evento.getModalidad().getModalidad());
		eventoDTO.setTipoEstadoEventoDTO(gTEE.fromTipoEstadoEvento(evento.getTipoEstadoEvento()));
		eventoDTO.setTipoEvento(evento.getTipo().getTipo());
		
		
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

		evento.setModalidad(ModalidadEvento.fromString(eventoDTO.getModalidadEvento()));
		evento.setTipoEstadoEvento(gTEE.toTipoEstadoEvento(eventoDTO.getTipoEstadoEvento()));
		evento.setTipo(TipoEvento.fromString(eventoDTO.getTipoEvento()));
		
		

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
	
	public TutorResponsableEventoDTO obtenerTutorResponsableEventoDTO() {
		return new TutorResponsableEventoDTO();
	}
}
