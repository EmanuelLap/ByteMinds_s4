package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.mobile.EventoDTOMobile;
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
	
	
	public List<EventoDTOMobile> listarEventosDTOMobile() {
		List<EventoDTOMobile> listadoEventosDTO = new ArrayList<EventoDTOMobile>();
		List<Evento> listadoEventosEntity = new ArrayList<Evento>();
		listadoEventosEntity.addAll(ejbRemoto.listarEventos());
		
		for(Evento ev : listadoEventosEntity) {
			EventoDTOMobile eventoDTO = new EventoDTOMobile();
			eventoDTO.setId(ev.getId());
			eventoDTO.setTitulo(ev.getTitulo());
			
			eventoDTO.setBajaLogica(ev.getBajaLogica());
			eventoDTO.setInicio(ev.getInicio());
			eventoDTO.setFin(ev.getFin());
			
			eventoDTO.setTutorResponsableEventoDTOCollection(new ArrayList<Integer>());
			try {
				List<TutorResponsableEvento>treList= ejbRemoto.obtenerTutoresDeEvento(ev.getId());
//				ev.setTutorResponsableEventoCollection(ejbRemoto.obtenerTutoresDeEvento(ev.getId()));
				
			if(treList != null || !treList.isEmpty()) {
				for(TutorResponsableEvento tre: treList) {
					eventoDTO.getTutorResponsableEventoDTOCollection().add(tre.getTutorId().getId());
				}
			}
			} catch ( Exception e) {
				System.out.println("Error al intentar traer la lista de tutores del evento!");
				e.printStackTrace();
			}
			
			eventoDTO.setItrDTO(ev.getItr().getId());
			eventoDTO.setLocalizacion(ev.getLocalizacion());
			
			eventoDTO.setModalidadEvento(ev.getModalidad().getId());
			eventoDTO.setTipoEstadoEventoDTO(ev.getTipoEstadoEvento().getId());
			eventoDTO.setTipoEvento(ev.getTipo().getId());
			
			listadoEventosDTO.add(eventoDTO);
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
	
	public EventoDTO fromEventoDTOMobileToEventoDTO(EventoDTOMobile eventoDTOMobile) {
		EventoDTO eventoDTO = new EventoDTO();
		eventoDTO.setId(eventoDTOMobile.getId());
		eventoDTO.setTitulo(eventoDTOMobile.getTitulo());
		eventoDTO.setBajaLogica(eventoDTOMobile.getBajaLogica());
		eventoDTO.setInicio(eventoDTOMobile.getInicio());
		eventoDTO.setFin(eventoDTOMobile.getFin());
		eventoDTO.setItrDTO(gITR.obtenerITRSeleccionado(eventoDTOMobile.getItrDTO()));
		eventoDTO.setLocalizacion(eventoDTOMobile.getLocalizacion());
		eventoDTO.setModalidadEvento(gME.obtenerModalidadEvento(eventoDTOMobile.getModalidadEvento()));
		eventoDTO.setTipoEstadoEventoDTO(gTEE.obtenerTipoEstadoEventoDTO(eventoDTOMobile.getTipoEstadoEvento()));
		eventoDTO.setTipoEvento(gTE.obtenerTipoEvento(eventoDTOMobile.getTipoEvento()));

		eventoDTO.setTutorResponsableEventoDTOCollection(gTRE.allTutorRespEventoDTO(eventoDTO.getId()));
		return eventoDTO;	
	}
	public EventoDTOMobile fromEventoDTOToEventoDTOMobile(EventoDTO eventoDTO) {
		EventoDTOMobile eventoDTOMobile = new EventoDTOMobile();
		eventoDTOMobile.setId(eventoDTO.getId());
		eventoDTOMobile.setTitulo(eventoDTO.getTitulo());
		eventoDTOMobile.setBajaLogica(eventoDTO.getBajaLogica());
		eventoDTOMobile.setInicio(eventoDTO.getInicio());
		eventoDTOMobile.setFin(eventoDTO.getFin());
		eventoDTOMobile.setItrDTO(eventoDTO.getItrDTO().getId());
		eventoDTOMobile.setLocalizacion(eventoDTO.getLocalizacion());
		eventoDTOMobile.setModalidadEvento(eventoDTO.getModalidadEvento().getId());
		eventoDTOMobile.setTipoEstadoEventoDTO(eventoDTO.getTipoEstadoEvento().getId());
		eventoDTOMobile.setTipoEvento(eventoDTO.getTipoEvento().getId());
		
		ArrayList<Integer> tutorResponsableEventoDTOCollection = new ArrayList<Integer>();
		for(TutorResponsableEventoDTO treDTO: eventoDTO.getTutorResponsableEventoDTOCollection()) {
			tutorResponsableEventoDTOCollection.add(treDTO.getTutorId().getId());
		}
		
		eventoDTOMobile.setTutorResponsableEventoDTOCollection(tutorResponsableEventoDTOCollection);
		return eventoDTOMobile;	
	}

	
	public EventoDTOMobile agregarEventoMobile(EventoDTOMobile eventoSeleccionado)throws PersistenciaException {
		System.out.println("ENTRANDO A AGREGAR EVENTO MOBILE");
		EventoDTO eventoDTO = new EventoDTO();
		
		eventoDTO.setId(eventoSeleccionado.getId());
		eventoDTO.setTitulo(eventoSeleccionado.getTitulo());
		
		eventoDTO.setBajaLogica(eventoSeleccionado.getBajaLogica());
		eventoDTO.setInicio(eventoSeleccionado.getInicio());
		eventoDTO.setFin(eventoSeleccionado.getFin());
	
		eventoDTO.setItrDTO(gITR.obtenerITRSeleccionado(eventoSeleccionado.getItrDTO()));
	
		eventoDTO.setLocalizacion(eventoSeleccionado.getLocalizacion());

		
		eventoDTO.setModalidadEvento(gME.obtenerModalidadEvento(eventoSeleccionado.getModalidadEvento()) );
		eventoDTO.setTipoEstadoEventoDTO(gTEE.obtenerTipoEstadoEventoDTO(eventoSeleccionado.getTipoEstadoEvento()));
		eventoDTO.setTipoEvento(gTE.obtenerTipoEvento(eventoSeleccionado.getTipoEvento()));
		
		
		List<TutorResponsableEventoDTO> listaDeTutoresResponsablesDTO = new ArrayList<TutorResponsableEventoDTO>();
		if(eventoSeleccionado.getTutorResponsableEventoDTOCollection() != null || !eventoSeleccionado.getTutorResponsableEventoDTOCollection().isEmpty()) {
			for(Integer treDTO: eventoSeleccionado.getTutorResponsableEventoDTOCollection()) {
				TutorResponsableEventoDTO treNew = new TutorResponsableEventoDTO();
				treNew.setEventoId(eventoSeleccionado.getId());
				treNew.setTutorId((TutorDTO)gUS.buscarUsuario(treDTO));
				listaDeTutoresResponsablesDTO.add(treNew);
			}
		}
		
		eventoDTO.setTutorResponsableEventoDTOCollection(listaDeTutoresResponsablesDTO);
		Evento eventoResult = ejbRemoto.crearEvento(toEventoEntidad(eventoDTO));
		EventoDTO eventDTOResult= fromEvento(eventoResult);
		
		return fromEventoDTOToEventoDTOMobile(eventDTOResult);
	}
	
	
	public EventoDTOMobile actualizarEventoMobile(EventoDTOMobile eventoSeleccionado)throws PersistenciaException  {
		
//	Evento evento = new Evento();
//		
//		evento.setId(eventoSeleccionado.getId());
//		evento.setTitulo(eventoSeleccionado.getTitulo());
//		
//		evento.setBajaLogica(eventoSeleccionado.getBajaLogica());
//		evento.setInicio(eventoSeleccionado.getInicio());
//		evento.setFin(eventoSeleccionado.getFin());
//	
//		evento.setItr(gITR.toITR(gITR.obtenerITRSeleccionado(eventoSeleccionado.getItrDTO())));
//	
//		evento.setLocalizacion(eventoSeleccionado.getLocalizacion());
//
//		
//		evento.setModalidad(gME.toModalidadEvento(gME.obtenerModalidadEvento(eventoSeleccionado.getModalidadEvento()) ));
//		evento.setTipoEstadoEvento(gTEE.toTipoEstadoEvento(gTEE.obtenerTipoEstadoEventoDTO(eventoSeleccionado.getTipoEstadoEvento())));
//		evento.setTipo(gTE.toTipoEvento(gTE.obtenerTipoEvento(eventoSeleccionado.getTipoEvento())));
//		
////		TODO:
////		obtener tutores responsables
////		recorrerlos y buscar si estan los que vienen
////		si al terminar tengo alguno que no esta en la lista que vino, lo elimino de la coleccion,
////		si al terminar hay alguno mas agregarlo
////		mandar a actualizar evento
//		
//		List<TutorResponsableEventoDTO > listaDeTutoresREDTO = new ArrayList<TutorResponsableEventoDTO>();
//		listaDeTutoresREDTO=gTRE.allTutorRespEventoDTO(eventoSeleccionado.getId());
//		
//		List<TutorDTO > listaDeTutoresDTOMemoria = new ArrayList<TutorDTO>();
//		for(Integer tutDTO: eventoSeleccionado.getTutorResponsableEventoDTOCollection()) {
//			TutorDTO tutorDTOTmp = new TutorDTO();
//			
//			tutorDTOTmp =(TutorDTO)gUS.buscarUsuario(tutDTO);
//		
//			listaDeTutoresDTOMemoria.add(tutorDTOTmp);
//		}
//		
//		sincronizarListas(listaDeTutoresREDTO, listaDeTutoresDTOMemoria);
//
//		List<TutorResponsableEvento> listaDeTutores = new ArrayList<TutorResponsableEvento>();
//		if(listaDeTutoresREDTO != null || !listaDeTutoresREDTO.isEmpty()) {
//			for(TutorResponsableEventoDTO treDTO: listaDeTutoresREDTO) {
//				listaDeTutores.add(gTRE.toTutorResponsableEvento(treDTO));
//			}
//		}
//		
//		evento.setTutorResponsableEventoCollection(listaDeTutores);
//
////		Evento evento=ejbRemoto.modificarEvento(toEventoEntidad(eventoSeleccionado));
////		return fromEvento(evento);
//		return null;
		
		System.out.println("ENTRANDO A AGREGAR EVENTO MOBILE");
		
		
		 System.out.println(eventoSeleccionado.toString());
		EventoDTO eventoDTO = new EventoDTO();
		
		eventoDTO.setId(eventoSeleccionado.getId());
		eventoDTO.setTitulo(eventoSeleccionado.getTitulo());
		
		eventoDTO.setBajaLogica(eventoSeleccionado.getBajaLogica());
		eventoDTO.setInicio(eventoSeleccionado.getInicio());
		eventoDTO.setFin(eventoSeleccionado.getFin());
	
		eventoDTO.setItrDTO(gITR.obtenerITRSeleccionado(eventoSeleccionado.getItrDTO()));
	
		eventoDTO.setLocalizacion(eventoSeleccionado.getLocalizacion());

		
		eventoDTO.setModalidadEvento(gME.obtenerModalidadEvento(eventoSeleccionado.getModalidadEvento()) );
		eventoDTO.setTipoEstadoEventoDTO(gTEE.obtenerTipoEstadoEventoDTO(eventoSeleccionado.getTipoEstadoEvento()));
		eventoDTO.setTipoEvento(gTE.obtenerTipoEvento(eventoSeleccionado.getTipoEvento()));
		
		
		
//		List<TutorResponsableEventoDTO> listaDeTutoresResponsablesDTO = new ArrayList<TutorResponsableEventoDTO>();
//		if(eventoSeleccionado.getTutorResponsableEventoDTOCollection() != null || !eventoSeleccionado.getTutorResponsableEventoDTOCollection().isEmpty()) {
//			for(Integer treDTO: eventoSeleccionado.getTutorResponsableEventoDTOCollection()) {
//				TutorResponsableEventoDTO treNew = new TutorResponsableEventoDTO();
//				treNew.setEventoId(eventoSeleccionado.getId());
//				System.out.println("treNew.setTutorId((TutorDTO)gUS.buscarUsuario(treDTO));" +treDTO);
//				TutorDTO tDTO = (TutorDTO)gUS.buscarUsuario(treDTO);
//				System.out.println(tDTO.toString());
//				treNew.setTutorId(tDTO);
//				listaDeTutoresResponsablesDTO.add(treNew);
//			}
//		}
		
//		eventoDTO.setTutorResponsableEventoDTOCollection(listaDeTutoresResponsablesDTO);
		eventoDTO.setTutorResponsableEventoDTOCollection(gTRE.allTutorRespEventoDTO(eventoSeleccionado.getId()));
		Evento eventoResult = ejbRemoto.modificarEvento(toEventoEntidad(eventoDTO));
		EventoDTO eventDTOResult= fromEvento(eventoResult);
		
		return fromEventoDTOToEventoDTOMobile(eventDTOResult);
	}
	
	
    public static void sincronizarListas(List<TutorResponsableEventoDTO> listaPersistida, List<TutorDTO> listaMemoria) {
        // Verificar y agregar nuevos tutores a la lista persistida
        for (TutorDTO tutorMemoria : listaMemoria) {
            if (!existeTutorEnListaPersistida(tutorMemoria, listaPersistida)) {
                // El tutor de la lista de memoria no está en la lista persistida, agregarlo
                TutorResponsableEventoDTO nuevoTutorDTO = new TutorResponsableEventoDTO();
                nuevoTutorDTO.setTutorId(tutorMemoria);
                listaPersistida.add(nuevoTutorDTO);
            }
        }

        // Eliminar tutores de la lista persistida que no están en la lista de memoria
        Iterator<TutorResponsableEventoDTO> iterator = listaPersistida.iterator();
        while (iterator.hasNext()) {
            TutorResponsableEventoDTO tutorPersistido = iterator.next();
            TutorDTO tutorDTO = tutorPersistido.getTutorId();
            if (!existeTutorEnListaMemoria(tutorDTO, listaMemoria)) {
                // El tutor de la lista persistida no está en la lista de memoria, eliminarlo
                iterator.remove();
            }
        }
    }

    private static boolean existeTutorEnListaPersistida(TutorDTO tutor, List<TutorResponsableEventoDTO> listaPersistida) {
        for (TutorResponsableEventoDTO tutorPersistido : listaPersistida) {
            if (tutorPersistido.getTutorId().equals(tutor)) {
                return true;
            }
        }
        return false;
    }

    private static boolean existeTutorEnListaMemoria(TutorDTO tutor, List<TutorDTO> listaMemoria) {
        for (TutorDTO tutorMemoria : listaMemoria) {
            if (tutorMemoria.equals(tutor)) {
                return true;
            }
        }
        return false;
    }
	
	
}
