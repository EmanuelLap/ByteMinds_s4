package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Evento;
import tecnofenix.entidades.Itr;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorTipo;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.TutorResponsableEvento;
import tecnofenix.entidades.Usuario;

@Stateless
@LocalBean
public class GestionTutorResponsableEventoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GestionUsuarioService gUS;
	EJBUsuarioRemoto ejbRemoto;
	public GestionTutorResponsableEventoService() {
	gUS = new GestionUsuarioService();
	ejbRemoto = new EJBUsuarioRemoto();
	}

	public TutorResponsableEventoDTO fromTutorResponsableEvento(TutorResponsableEvento tutorRespEventEntidad) {
		TutorResponsableEventoDTO tutorResponsableEventoDTO = new TutorResponsableEventoDTO();
		
		tutorResponsableEventoDTO.setId(tutorRespEventEntidad.getId());
		tutorResponsableEventoDTO.setEventoId(tutorRespEventEntidad.getEventoId().getId());
		tutorResponsableEventoDTO.setTutorId((TutorDTO)gUS.fromUsuario(tutorRespEventEntidad.getTutorId()));
		
		return tutorResponsableEventoDTO;
	}

	public TutorResponsableEvento toTutorResponsableEvento(TutorResponsableEventoDTO tutorResponsableEventoDTO) {
		TutorResponsableEvento tutorResponsableEvento = new TutorResponsableEvento();
		
		tutorResponsableEvento.setId(tutorResponsableEventoDTO.getId());
//		Evento evento = gES.obtenerEvento(tutorResponsableEventoDTO.getId());
		Evento evento =ejbRemoto.obtenerEvento(tutorResponsableEventoDTO.getEventoId());
		tutorResponsableEvento.setEventoId(evento);
		tutorResponsableEvento.setTutorId((Tutor)gUS.toUsuario(tutorResponsableEventoDTO.getTutorId()));
		
		return tutorResponsableEvento;
	}


	public List<TutorResponsableEventoDTO> allTutorRespEventoDTO(Integer eventoId) {
		List<TutorResponsableEventoDTO> listTutorREDTO = new ArrayList<TutorResponsableEventoDTO>();
		List<TutorResponsableEvento> listTutorRE = new ArrayList<TutorResponsableEvento>();
		
		listTutorRE= ejbRemoto.obtenerTutoresDeEvento(eventoId);
		for(TutorResponsableEvento tut:listTutorRE) {
			listTutorREDTO.add(fromTutorResponsableEvento(tut));
		}
		return listTutorREDTO;
	}
	
	public TutorResponsableEventoDTO agregarTutorRespEvento(TutorResponsableEventoDTO tutorRE) {
		TutorResponsableEvento tut = new TutorResponsableEvento();
		
		tut= ejbRemoto.asignarTutorAEvento(toTutorResponsableEvento(tutorRE));
		
		return fromTutorResponsableEvento(tut);
	}
	
	public void borrarTutorResponsableEvento(TutorResponsableEventoDTO tutorRE) {
		if(tutorRE != null && tutorRE.getId()!=null)
		ejbRemoto.borrarTutorResponsableEvento(toTutorResponsableEvento(tutorRE));
		
	}	

}
