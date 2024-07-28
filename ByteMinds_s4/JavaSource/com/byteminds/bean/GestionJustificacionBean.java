package com.byteminds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.GestionJustificacionService;
import com.byteminds.negocio.GestionTipoEstadoJustificacionService;
import com.byteminds.negocio.GestionTipoEstadoJustificacionService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.JustificacionDTO;
import com.byteminds.negocio.JustificacionDTO;
import com.byteminds.negocio.TipoEstadoJustificacionDTO;
import com.byteminds.negocio.TipoEstadoJustificacionDTO;
import com.byteminds.negocio.TutorResponsableEventoDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.EventoDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.ExceptionsTools;

import tecnofenix.entidades.Evento;

@Named(value = "gestionJustificacion") // JEE8
@SessionScoped // JEE8
public class GestionJustificacionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionJustificacionService gestionJustificacionService;
	@EJB
	GestionUsuarioService gestionUsuarioService;
	@EJB
	GestionTipoEstadoJustificacionService gestionTipoEstadoJustificacionService;
	@Inject
    LoginBean loginBean = new LoginBean();
	
	private EJBUsuarioRemoto ejbJustificacionRemoto;

	private GestionEventoService gestEventService;
	private Integer id;
	private String modalidad;
	
	private EstudianteDTO estudianteQueReclamaDTO;
	private Integer idEstudianteDTO;
	

	private JustificacionDTO justificacionSeleccionado;
	
	private JustificacionDTO justificacionToEditEstado;
	private Integer idTipoEstado;
	
	private List<EventoDTO> listEventosDTO = new ArrayList<EventoDTO>();
	private EventoDTO eventoSeleccionado = new EventoDTO();

	private List<SelectItem> listaDeEventosDTO;

	private Integer idEventoSeleccionado;

	private UsuarioDTO usuarioLogeado;
	private boolean modoEdicion = false;


	public GestionJustificacionBean() {
		System.out.println("INICIALIZANDO GestionJustificacionBean");
		ejbJustificacionRemoto = new EJBUsuarioRemoto();
		gestEventService = new GestionEventoService();
		estudianteQueReclamaDTO = new EstudianteDTO();
		gestionUsuarioService = new GestionUsuarioService();
		cargarComboEventosDisponibles();
		idEventoSeleccionado=0;
		idTipoEstado=0;
		justificacionSeleccionado = new JustificacionDTO();
		justificacionSeleccionado.setEventoId(new EventoDTO());
		justificacionSeleccionado.setFecha(new Date(System.currentTimeMillis()));
		justificacionToEditEstado = new JustificacionDTO();
	}

	public String inicializar() {
		System.out.println("INICIALIZANDO GestionJustificacionBean inicializar");
		ejbJustificacionRemoto = new EJBUsuarioRemoto();
		gestEventService = new GestionEventoService();
		estudianteQueReclamaDTO = new EstudianteDTO();
		gestionUsuarioService = new GestionUsuarioService();
		cargarComboEventosDisponibles();
		idEventoSeleccionado=0;
		justificacionSeleccionado = new JustificacionDTO();
		justificacionSeleccionado.setEventoId(new EventoDTO());
		justificacionSeleccionado.setFecha(new Date(System.currentTimeMillis()));
//		if(idEstudianteDTO!= null) {
			
//			estudianteQueReclamaDTO =(EstudianteDTO) gestionUsuarioService.fromUsuario(ejbJustificacionRemoto.buscarUsuarioPor("ESTUDIANTE", "123", "", "", "", "", "", "", "", "", null, null, null, "", "", null, null).get(0));
			if(loginBean.getUserioLogeado().getUTipo().equals("ESTUDIANTE")) {
				estudianteQueReclamaDTO=(EstudianteDTO)loginBean.getUserioLogeado();
//			}else {
//				estudianteQueReclamaDTO =(EstudianteDTO) gestionUsuarioService.buscarUsuario(idEstudianteDTO);//TODO: cambiar por idEstudianteDTO
//			}
		}
			if(loginBean.getUserioLogeado().getUTipo().equals("ANALISTA")) {
				
			}
		if (id != null ) {
						justificacionSeleccionado = gestionJustificacionService.fromJustificacion(ejbJustificacionRemoto.buscarJustificacionPorId(id));
						this.idEventoSeleccionado = justificacionSeleccionado.getEventoId().getId();
						estudianteQueReclamaDTO = justificacionSeleccionado.getEstudianteId();
						
		} else {
//			justificacionSeleccionado = new JustificacionDTO();
//			justificacionSeleccionado.setEventoId(new EventoDTO());
//			justificacionSeleccionado.setFecha(new Date(System.currentTimeMillis()));
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion = false;
		} else if (modalidad.contentEquals("update")) {
			modoEdicion = true;
		} else if (modalidad.contentEquals("insert")) {
			modoEdicion = true;
		} else if (modalidad.contentEquals("edit")) {
			modoEdicion = true;
		} else {

			modoEdicion = false;
			modalidad = "view";

		}
		 
		return "/pages/eventos/justificaciones/altaJustificacion.xhtml";
	}

	public String salvarCambios() {
		if(validarDatos()) {
		if (justificacionSeleccionado.getId() == null) {


			JustificacionDTO nuevoJustificacionDTO;
			try {
				justificacionSeleccionado.setEstudianteId(estudianteQueReclamaDTO);
				nuevoJustificacionDTO = gestionJustificacionService.agregarJustificacion(justificacionSeleccionado);
				this.id = nuevoJustificacionDTO.getId();

				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo justificacion",	"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "view";

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(rootException);
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}

		} else if (modalidad.equals("update")) {

			try {
				gestionJustificacionService.actualizarJustificacion(justificacionSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el justificacion.", ""));

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}
		}
		}
		return "";
	}

	
	
	public Boolean validarDatos() {
//		if (this.justificacionSeleccionado.getTitulo() == "") {
//			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos no validos", "Debe ingresar el titulo");
//			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//			return false;
//		}
		if (this.justificacionSeleccionado.getDetalle() == ""||this.justificacionSeleccionado.getDetalle().length() > 45) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos no validos", "Debe ingresar el detalle ");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
//		if (this.justificacionSeleccionado.getCreditos() == null ) {
//			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos no validos", "Debe ingresar los creditos");
//			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//			return false;
//		}
//		if (this.justificacionSeleccionado.getSemestre() == null ) {
//			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos no validos", "Debe ingresar el semestre");
//			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//			return false;
//		}
		if (this.estudianteQueReclamaDTO != null && this.estudianteQueReclamaDTO.getId() == null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos no validos", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
		
		
		return true;
	}
	
	public String guardarEstadoJustificacion() {
		try {
			System.out.println("INGRESANDO A guardarEstadoJustificacion");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			if(justificacionToEditEstado != null || idTipoEstado > 0) {
			TipoEstadoJustificacionDTO tEJDTO = null;
			tEJDTO=gestionTipoEstadoJustificacionService.obtenerTipoEstadoJustificacionDTO(idTipoEstado);
			System.out.println("tERDTO"+tEJDTO.toString());
			justificacionToEditEstado.setEstadoJustificacionId(tEJDTO);
			justificacionToEditEstado.setFechaEstadoJustificacion(new Date(System.currentTimeMillis()));
			gestionJustificacionService.actualizarJustificacion(justificacionToEditEstado);
			
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el estado del justificacion.", ""));
			justificacionToEditEstado= null;
			idTipoEstado=0;
			}
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private void cargarComboEventosDisponibles(){
		List<Evento> listEventos = new ArrayList<Evento>();
		listEventos = ejbJustificacionRemoto.listarEventos();
		if(listaDeEventosDTO != null && listaDeEventosDTO.size() ==listEventos.size()) {
			//el tamaño es el mismo, no hay que hacer nada
			PrimeFaces.current().ajax().update("idDatosJustificacions");
			System.out.println("Actualizando WEB-cargarComboEventosDisponibles");
		}else {
			listaDeEventosDTO = new ArrayList<>();
	
			for(Evento e :listEventos) {
				listEventosDTO.add(gestEventService.fromEvento(e));
	
			}
			for(EventoDTO e :listEventosDTO) {
				listaDeEventosDTO.add(new SelectItem(e.getId(), e.toString()));	
			}
		}
	}
	
	public void actualizarEventoSeleccionado(ValueChangeEvent event) {
		System.out.println(event.getNewValue()); 
		System.out.println("idEventoSeleccionado"+idEventoSeleccionado);
		idEventoSeleccionado=(Integer)event.getNewValue();
		eventoSeleccionado= gestEventService.obtenerEvento((Integer)event.getNewValue());
		System.out.println(eventoSeleccionado.toString());
		justificacionSeleccionado.setEventoId(eventoSeleccionado);
		
		if(eventoSeleccionado.getTutorResponsableEventoDTOCollection() !=null) {
			for (TutorResponsableEventoDTO tre :eventoSeleccionado.getTutorResponsableEventoDTOCollection()) {
				System.out.println("Tutor "+tre.getTutorId().getNombres() + " "+tre.getTutorId().getApellidos());
				
			}
		}else {
			System.out.println("El evento no devolvio tutores");
		}
		
//		 FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("fechaEvento");
//	return"";
	}

	
	public void seleccionarUsuarioJustificacion(EstudianteDTO estudianteDTO) {
		
		estudianteQueReclamaDTO =estudianteDTO;
//		idEstudianteDTO = estudianteDTO.getId();
		
	}

	public List<EventoDTO> getListEventosDTO() {
		return listEventosDTO;
	}

	public void setListEventosDTO(List<EventoDTO> listEventosDTO) {
		this.listEventosDTO = listEventosDTO;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public boolean getModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}
	public JustificacionDTO getJustificacionSeleccionado() {
		return justificacionSeleccionado;
	}

	public void setJustificacionSeleccionado(JustificacionDTO justificacionSeleccionado) {
		this.justificacionSeleccionado = justificacionSeleccionado;
	}


	public List<SelectItem> getListaDeEventosDTO() {
		return listaDeEventosDTO;
	}

	public void setListaDeEventosDTO(List<SelectItem> listaDeEventosDTO) {
		this.listaDeEventosDTO = listaDeEventosDTO;
	}
	public EventoDTO getEventoSeleccionado() {
		return eventoSeleccionado;
	}

	public void setEventoSeleccionado(EventoDTO eventoSeleccionado) {
		this.eventoSeleccionado = eventoSeleccionado;
	}
	public Integer getIdEventoSeleccionado() {
		return idEventoSeleccionado;
	}

	public void setIdEventoSeleccionado(Integer idEventoSeleccionado) {
		this.idEventoSeleccionado = idEventoSeleccionado;
	}

	public Integer getIdEstudianteDTO() {
		return idEstudianteDTO;
	}

	public void setIdEstudianteDTO(Integer idEstudianteDTO) {
		this.idEstudianteDTO = idEstudianteDTO;
	}

	public EstudianteDTO getEstudianteQueReclamaDTO() {
		return estudianteQueReclamaDTO;
	}

	public void setEstudianteQueReclamaDTO(EstudianteDTO estudianteQueReclamaDTO) {
		this.estudianteQueReclamaDTO = estudianteQueReclamaDTO;
	}

	public UsuarioDTO getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(UsuarioDTO usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public JustificacionDTO getJustificacionToEditEstado() {
		return justificacionToEditEstado;
	}

	public void setJustificacionToEditEstado(JustificacionDTO justificacionToEditEstado) {
		this.justificacionToEditEstado = justificacionToEditEstado;
	}

	public Integer getIdTipoEstado() {
		return idTipoEstado;
	}

	public void setIdTipoEstado(Integer idTipoEstado) {
		this.idTipoEstado = idTipoEstado;
	}
	
}
