package com.byteminds.bean;


import javax.ejb.EJB;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.EventoDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.TipoEstadoEventoDTO;
import com.byteminds.negocio.TutorResponsableEventoDTO;
import com.byteminds.negocio.UsuarioDTO;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;


@Named(value="gestionEnventosBean")		//JEE8
@SessionScoped				        //JEE8
public class GestionEnventosBean implements Serializable{
	
	@EJB
	GestionEventoService gestionEventoService;

//	@Inject
//	PersistenciaBean persistenciaBean;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String criterioTitulo;

	//	SIN_SELECCIONAR(""),JORNADA_PRESENCIAL("Jornada presencial"), PRUEBA_FINAL("Prueba final"), EXAMEN("Examen"), DEFENSA_PROYECTO("Defensa de Proyecto");
	private String criterioTipoEvento;
	
	//	SIN_SELECCIONAR(""),VIRTUAL("Virtual"), PRESENCIAL("Presencial"), SEMI_PRESENCIAL("Semipresencial");
	private String criterioModalidadEvento;

	private Date criterioInicioInicio;
	private Date criterioFinInicio;
	private Date criterioInicioFin;
	private Date criterioFinFin;
	
	private String criterioLocalizacion;
	
	private Boolean criterioActivo =false;
	
	private String criterioItrDTO;
	
//  finalizado, corriente, futuro
	private TipoEstadoEventoDTO tipoEstadoEventoDTO;
 
    private Collection<TutorResponsableEventoDTO> tutorResponsableEventoDTOCollection;

	

	private List<EventoDTO> eventosSeleccionados;
	private EventoDTO eventoSeleccionado;
	
	public GestionEnventosBean() {
		super();
		//TODO: INICIALIZAMOS Los filtros de eventos para que esten en vacios
		gestionEventoService = new GestionEventoService();
	}
	
	// ********Acciones****************************
	public String seleccionarEventos() throws PersistenciaException {
		
		 // Verificar si los criterios son nulos y asignar valores predeterminados si es necesario
	    if (criterioTitulo == null) {
	        criterioTitulo = "";
	    }
	    if (criterioLocalizacion == null) {
	        criterioLocalizacion = "";
	    }
	    if (criterioModalidadEvento == null) {
	        criterioModalidadEvento = "";
	    }
	    if (criterioTipoEvento == null) {
	        criterioTipoEvento = "";
	    }
	    if (criterioItrDTO == null ) {
	        // Manejar el caso en el que criterioItrDTO o su propiedad nombre sean nulos
	        // Puedes asignar un valor predeterminado o lanzar una excepción según tu lógica
	        // Por ejemplo:
	        // throw new IllegalArgumentException("El criterioItrDTO o su propiedad nombre no pueden ser nulos");
	    	criterioItrDTO ="";
	    	
	    }
	    if (criterioInicioInicio == null) {
	        // Manejar el caso en el que criterioInicioInicio sea nulo
	    }
	    if (criterioFinInicio == null) {
	        // Manejar el caso en el que criterioFinInicio sea nulo
	    }
	    if (criterioFinFin == null) {
	        // Manejar el caso en el que criterioFinFin sea nulo
	    }
	    if (criterioActivo == null) {
	        // Manejar el caso en el que criterioBajaLogica sea nulo
	    	
	    }
		
		eventosSeleccionados=gestionEventoService.buscarEventosPor("",criterioTitulo,criterioLocalizacion,
				criterioModalidadEvento,criterioTipoEvento,criterioItrDTO
				,criterioInicioInicio,criterioFinInicio,criterioFinInicio,criterioFinFin,criterioActivo);

		return "";
	}
	
	
	public String verDatosEvento() {
		//Navegamos a datos evento
		return "DatosEvento";
	}

	
	
//	public void cambiarChecks() {
//		if(criterioTodos ==true) {
//			criterioActivo = false;
//			criterioNoActivo= false;
//			criterioValidado= false;
//			criterioNoValidado= false;
//		}
//		
//	}
	
	
	// ******** Getters & Setters****************************
	

	public String getCriterioTitulo() {
		return criterioTitulo;
	}

	public void setCriterioTitulo(String criterioTitulo) {
		this.criterioTitulo = criterioTitulo;
	}

	public String getCriterioTipoEvento() {
		return criterioTipoEvento;
	}

	public void setCriterioTipoEvento(String criterioTipoEvento) {
		this.criterioTipoEvento = criterioTipoEvento;
	}

	public String getCriterioModalidadEvento() {
		return criterioModalidadEvento;
	}

	public void setCriterioModalidadEvento(String criterioModalidadEvento) {
		this.criterioModalidadEvento = criterioModalidadEvento;
	}

	public Date getCriterioInicioInicio() {
		return criterioInicioInicio;
	}

	public void setCriterioInicioInicio(Date criterioInicioInicio) {
		this.criterioInicioInicio = criterioInicioInicio;
	}

	public Date getCriterioFinInicio() {
		return criterioFinInicio;
	}

	public void setCriterioFinInicio(Date criterioFinInicio) {
		this.criterioFinInicio = criterioFinInicio;
	}

	public Date getCriterioInicioFin() {
		return criterioInicioFin;
	}

	public void setCriterioInicioFin(Date criterioInicioFin) {
		this.criterioInicioFin = criterioInicioFin;
	}

	public Date getCriterioFinFin() {
		return criterioFinFin;
	}

	public void setCriterioFinFin(Date criterioFinFin) {
		this.criterioFinFin = criterioFinFin;
	}

	public String getCriterioLocalizacion() {
		return criterioLocalizacion;
	}

	public void setCriterioLocalizacion(String criterioLocalizacion) {
		this.criterioLocalizacion = criterioLocalizacion;
	}

	public Boolean getCriterioActivo() {
		return criterioActivo;
	}

	public void setCriterioActivo(Boolean criterioBajaLogica) {
		this.criterioActivo = criterioBajaLogica;
	}

	public String getCriterioItrDTO() {
		return criterioItrDTO;
	}

	public void setCriterioItrDTO(String criterioItrDTO) {
		this.criterioItrDTO = criterioItrDTO;
	}

	public TipoEstadoEventoDTO getTipoEstadoEventoDTO() {
		return tipoEstadoEventoDTO;
	}

	public void setTipoEstadoEventoDTO(TipoEstadoEventoDTO tipoEstadoEventoDTO) {
		this.tipoEstadoEventoDTO = tipoEstadoEventoDTO;
	}

	public Collection<TutorResponsableEventoDTO> getTutorResponsableEventoDTOCollection() {
		return tutorResponsableEventoDTOCollection;
	}

	public void setTutorResponsableEventoDTOCollection(
			Collection<TutorResponsableEventoDTO> tutorResponsableEventoDTOCollection) {
		this.tutorResponsableEventoDTOCollection = tutorResponsableEventoDTOCollection;
	}

	public List<EventoDTO> getEventosSeleccionados() {
		return eventosSeleccionados;
	}

	public void setEventosSeleccionados(List<EventoDTO> eventosSeleccionados) {
		this.eventosSeleccionados = eventosSeleccionados;
	}

	public EventoDTO getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(EventoDTO eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}
	
	
}
