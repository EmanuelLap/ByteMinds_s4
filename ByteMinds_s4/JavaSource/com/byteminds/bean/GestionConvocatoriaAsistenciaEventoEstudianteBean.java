package com.byteminds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.byteminds.negocio.ConvocatoriaAsistenciaEventoEstudianteDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.EventoDTO;
import com.byteminds.negocio.GestionConvocatoriaAsistenciaEventoEstudianteService;
import com.byteminds.negocio.UsuarioDTO;

import tecnofenix.entidades.RegistroAsistencia;

@Named(value="gestionConvocatoriaAsistenciaEventoEstudianteBean")		//JEE8
@SessionScoped	
public class GestionConvocatoriaAsistenciaEventoEstudianteBean implements Serializable{

	GestionConvocatoriaAsistenciaEventoEstudianteService gestionConvocatoria;
	
	@Inject
	GestionUsuariosBean gestionUsuariosBean;
	
	@Inject
	GestionEventoBean gestionEventoBean;
	
	private List<ConvocatoriaAsistenciaEventoEstudianteDTO> listaDeConvocadosDTO = new ArrayList<ConvocatoriaAsistenciaEventoEstudianteDTO>();
	private EventoDTO eventoSeleccionado;
	
	private List<RegistroAsistencia> registroAsistenciaValues;
	private List<Integer> calificaciones = new ArrayList<Integer>();
	
	public GestionConvocatoriaAsistenciaEventoEstudianteBean() {
		gestionConvocatoria = new GestionConvocatoriaAsistenciaEventoEstudianteService();
	}
  @PostConstruct
    public void init() {
        // Inicializacion
        
        registroAsistenciaValues = Arrays.asList(RegistroAsistencia.values());
        calificaciones = Arrays.asList(0, 1, 2, 3, 4, 5);
    }
	
	
	public void agregarConvocados() {
		if(gestionUsuariosBean.getUsuariosSelecConvocadosEvento()==null ||gestionUsuariosBean.getUsuariosSelecConvocadosEvento().isEmpty()) {
			System.out.println("La lista de convocados esta vacia nada que agregar");
		}else {
			List<EstudianteDTO> listaEstConvTemp = new ArrayList<EstudianteDTO>();
			for(UsuarioDTO estConv :gestionUsuariosBean.getUsuariosSelecConvocadosEvento()) {
				listaEstConvTemp.add((EstudianteDTO)estConv) ;
			}
			
		}
		
		
	}
	public void setearEvento(EventoDTO evento) {
		
			this.eventoSeleccionado=evento;
			System.out.println("La selecciono el evento y se agrego para la convocatoria");
			listaDeConvocadosDTO.clear();
			listaDeConvocadosDTO=gestionConvocatoria.listarConvocatoriaEventEstuPorEvento(this.eventoSeleccionado);
			if(listaDeConvocadosDTO==null)listaDeConvocadosDTO= new ArrayList<ConvocatoriaAsistenciaEventoEstudianteDTO>();
			System.out.println(listaDeConvocadosDTO.toString());
			List<UsuarioDTO> listaTraidaDeDB = new ArrayList<UsuarioDTO>();
			listaTraidaDeDB=gestionConvocatoria.listarEstudiantesConvocadosEvento(evento.getId());
			gestionUsuariosBean.setUsuariosSelecConvocadosEvento(listaTraidaDeDB);
	}
	
	public String guardarConvocatoria() {
		if(eventoSeleccionado !=null) {
			if(eventoSeleccionado.getInicio().before(new Date(System.currentTimeMillis()))) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Evento culminado", "Ya no puede ingresar estudiantes a este Evento!");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;
			}
			if(this.listaDeConvocadosDTO.isEmpty()) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Evento", "No hay ningun estudiante para este evento, seleccione alguno de la lista de estudiantes");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;
			}else {
				for(ConvocatoriaAsistenciaEventoEstudianteDTO convTemp: this.listaDeConvocadosDTO) {
					convTemp.setEventoDTO(eventoSeleccionado);
					gestionConvocatoria.agregarEstudianteAEvento(convTemp);
					System.out.println("Se agrego el estudiante: " +convTemp.getEstudianteDTO().getNombres() +" "+convTemp.getEstudianteDTO().getApellidos()+
							" al Evento: " + convTemp.getEventoDTO().getTitulo());
				}
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado satisfactorio ", "Se guardo la convocatoria satisfactoriamente!");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				
			}
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Evento invalido", "Primero seleccione un evento de la lista de eventos");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;
		}
		
		return "";
	
	}
	
	public String guardarAsistemciaConvocatoria() {
		if(eventoSeleccionado !=null) {
			if(eventoSeleccionado.getInicio().after(new Date(System.currentTimeMillis()))) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Evento no iniciado", "Este evento no termino no puede ingresar calificaciones ni asistencias!");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;
			}
			if(this.listaDeConvocadosDTO.isEmpty()) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Evento", "No hay ningun estudiante para este evento, seleccione el modulo correspondiente para agregar estudiantes a eventos");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;
			}else {
				for(ConvocatoriaAsistenciaEventoEstudianteDTO convTemp: this.listaDeConvocadosDTO) {
					convTemp.setEventoDTO(eventoSeleccionado);
					gestionConvocatoria.agregarEstudianteAEvento(convTemp);
					System.out.println("Se agrego el estudiante: " +convTemp.getEstudianteDTO().getNombres() +" "+convTemp.getEstudianteDTO().getApellidos()+
							" al Evento: " + convTemp.getEventoDTO().getTitulo());
				}
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado satisfactorio ", "Se guardo la convocatoria satisfactoriamente!");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				
			}
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Evento invalido", "Primero seleccione un evento de la lista de eventos");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;
		}
		
		return "";
	
	}
	public String agregarAEvento(EstudianteDTO estudiante) {
		if(eventoSeleccionado !=null) {
			if(eventoSeleccionado.getInicio().before(new Date(System.currentTimeMillis()))) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento culminado", "Ya no puede ingresar estudiantes a este Evento!");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;
			}
			
			ConvocatoriaAsistenciaEventoEstudianteDTO nuevoEstudianteConvocado = new ConvocatoriaAsistenciaEventoEstudianteDTO(eventoSeleccionado, estudiante);
			Boolean encontreEstudianteEnLista=false;
			for(ConvocatoriaAsistenciaEventoEstudianteDTO convTemp: this.listaDeConvocadosDTO) {
			if(convTemp.getEstudianteDTO().getDocumento().equals(estudiante.getDocumento())) {
				System.out.println("El estudiante ya se encuentra en la lista de convocados!");
				encontreEstudianteEnLista = true;
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estudiante convocado!", "El estudiante seleccionado ya esta en la lista de convocados");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			
			}
		}
		//Agregamos el estudiante seleccionado a la lista de convocados
		if(!encontreEstudianteEnLista)this.listaDeConvocadosDTO.add(nuevoEstudianteConvocado);
		
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Evento invalido", "Primero seleccione un evento de la lista de eventos");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;
		}
		return "";
	}
	
	
	public String quitarEstudiante(EstudianteDTO estudiante) {
		if(eventoSeleccionado !=null) {
			ConvocatoriaAsistenciaEventoEstudianteDTO aEliminar=null;
		for(ConvocatoriaAsistenciaEventoEstudianteDTO convTemp: this.listaDeConvocadosDTO) {
			if(convTemp.getEstudianteDTO().getDocumento().equals(estudiante.getDocumento())) {
			aEliminar=convTemp;
			}
		}
		//Agregamos el estudiante seleccionado a la lista de convocados
		if(aEliminar!=null)this.listaDeConvocadosDTO.remove(aEliminar);
		System.out.println("ELIMINANDOOO ESTUDIANTE DE LISTA HABILITADOS");
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Evento invalido", "Primero seleccione un evento de la lista de eventos");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;
		}
		return "";
	}
	
	
	
	
	
	public EventoDTO getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(EventoDTO eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}

	public List<ConvocatoriaAsistenciaEventoEstudianteDTO> getListaDeConvocadosDTO() {
		return listaDeConvocadosDTO;
	}

	public void setListaDeConvocadosDTO(List<ConvocatoriaAsistenciaEventoEstudianteDTO> listaDeConvocadosDTO) {
		this.listaDeConvocadosDTO = listaDeConvocadosDTO;
	}

	public List<RegistroAsistencia> getRegistroAsistenciaValues() {
		return registroAsistenciaValues;
	}

	public void setRegistroAsistenciaValues(List<RegistroAsistencia> registroAsistenciaValues) {
		this.registroAsistenciaValues = registroAsistenciaValues;
	}

	public List<Integer> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Integer> calificaciones) {
		this.calificaciones = calificaciones;
	}
}
