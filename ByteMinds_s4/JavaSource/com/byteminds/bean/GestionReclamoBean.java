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

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.GestionReclamoService;
import com.byteminds.negocio.GestionTipoEstadoReclamoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ReclamoDTO;
import com.byteminds.negocio.TipoEstadoReclamoDTO;
import com.byteminds.negocio.TutorResponsableEventoDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.EventoDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.ExceptionsTools;

import tecnofenix.entidades.Evento;

@Named(value = "gestionReclamo") // JEE8
@SessionScoped // JEE8
public class GestionReclamoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionReclamoService gestionReclamoService;
	@EJB
	GestionUsuarioService gestionUsuarioService;
	@EJB
	GestionTipoEstadoReclamoService gestionTipoEstadoReclamoService;
	@Inject
    LoginBean loginBean = new LoginBean();
	
	private EJBUsuarioRemoto ejbReclamoRemoto;

	private GestionEventoService gestEventService;
	private Integer id;
	private String modalidad;
	
	private EstudianteDTO estudianteQueReclamaDTO;
	private Integer idEstudianteDTO;
	

	private ReclamoDTO reclamoSeleccionado;
	
	private ReclamoDTO reclamoToEditEstado;
	private Integer idTipoEstado;
	
	private List<EventoDTO> listEventosDTO = new ArrayList<EventoDTO>();
	private EventoDTO eventoSeleccionado = new EventoDTO();

	private List<SelectItem> listaDeEventosDTO;

	private Integer idEventoSeleccionado;

	private UsuarioDTO usuarioLogeado;
	private boolean modoEdicion = false;


	public GestionReclamoBean() {
		System.out.println("INICIALIZANDO GestionReclamoBean");
		ejbReclamoRemoto = new EJBUsuarioRemoto();
		gestEventService = new GestionEventoService();
		estudianteQueReclamaDTO = new EstudianteDTO();
		gestionUsuarioService = new GestionUsuarioService();
		cargarComboEventosDisponibles();
		idEventoSeleccionado=0;
		idTipoEstado=0;
		reclamoSeleccionado = new ReclamoDTO();
		reclamoSeleccionado.setEventoId(new EventoDTO());
		reclamoSeleccionado.setFecha(new Date(System.currentTimeMillis()));
		reclamoToEditEstado = new ReclamoDTO();
	}

	public String inicializar() {
		System.out.println("INICIALIZANDO GestionReclamoBean inicializar");
		ejbReclamoRemoto = new EJBUsuarioRemoto();
		gestEventService = new GestionEventoService();
		estudianteQueReclamaDTO = new EstudianteDTO();
		gestionUsuarioService = new GestionUsuarioService();
		cargarComboEventosDisponibles();
		idEventoSeleccionado=0;
		reclamoSeleccionado = new ReclamoDTO();
		reclamoSeleccionado.setEventoId(new EventoDTO());
		reclamoSeleccionado.setFecha(new Date(System.currentTimeMillis()));
		if(idEstudianteDTO!= null) {
			
//			estudianteQueReclamaDTO =(EstudianteDTO) gestionUsuarioService.fromUsuario(ejbReclamoRemoto.buscarUsuarioPor("ESTUDIANTE", "123", "", "", "", "", "", "", "", "", null, null, null, "", "", null, null).get(0));
			if(loginBean.getUserioLogeado().getUTipo().equals("ESTUDIANTE")) {
				estudianteQueReclamaDTO=(EstudianteDTO)loginBean.getUserioLogeado();
			}else {
				estudianteQueReclamaDTO =(EstudianteDTO) gestionUsuarioService.buscarUsuario(123);//TODO: cambiar por idEstudianteDTO
			}
		}
		if (id != null ) {
						reclamoSeleccionado = gestionReclamoService.fromReclamo(ejbReclamoRemoto.buscarReclamoPorId(id));
						this.idEventoSeleccionado = reclamoSeleccionado.getEventoId().getId();
						
		} else {
//			reclamoSeleccionado = new ReclamoDTO();
//			reclamoSeleccionado.setEventoId(new EventoDTO());
//			reclamoSeleccionado.setFecha(new Date(System.currentTimeMillis()));
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
		
		return "/pages/reclamos/altaReclamo.xhtml";
	}

	public String salvarCambios() {
		if(validarDatos()) {
		if (reclamoSeleccionado.getId() == null) {


			ReclamoDTO nuevoReclamoDTO;
			try {
				reclamoSeleccionado.setEstudianteId(estudianteQueReclamaDTO);
				nuevoReclamoDTO = gestionReclamoService.agregarReclamo(reclamoSeleccionado);
				this.id = nuevoReclamoDTO.getId();

				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo reclamo",	"");
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
				gestionReclamoService.actualizarReclamo(reclamoSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el reclamo.", ""));

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
		if(this.reclamoSeleccionado.getTitulo()=="") return false;
		if(this.reclamoSeleccionado.getDetalle()=="") return false;
				
		return true;
	}
	
	public String guardarEstadoReclamo() {
		try {
			System.out.println("INGRESANDO A guardarEstadoReclamo");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-");
			if(reclamoToEditEstado != null || idTipoEstado > 0) {
			TipoEstadoReclamoDTO tERDTO = null;
			tERDTO=gestionTipoEstadoReclamoService.obtenerTipoEstadoReclamoDTO(idTipoEstado);
			System.out.println("tERDTO"+tERDTO.toString());
			reclamoToEditEstado.setEstadoReclamoId(tERDTO);
			reclamoToEditEstado.setFechaEstadoReclamo(new Date(System.currentTimeMillis()));
			gestionReclamoService.actualizarReclamo(reclamoToEditEstado);
			
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el estado del reclamo.", ""));
			reclamoToEditEstado= null;
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
		listEventos = ejbReclamoRemoto.listarEventos();
		listaDeEventosDTO = new ArrayList<>();

		for(Evento e :listEventos) {
			listEventosDTO.add(gestEventService.fromEvento(e));

		}
		for(EventoDTO e :listEventosDTO) {
			listaDeEventosDTO.add(new SelectItem(e.getId(), e.toString()));	
		}
	}
	
	public void actualizarEventoSeleccionado(ValueChangeEvent event) {
		System.out.println(event.getNewValue()); 
		System.out.println("idEventoSeleccionado"+idEventoSeleccionado);
		idEventoSeleccionado=(Integer)event.getNewValue();
		eventoSeleccionado= gestEventService.obtenerEvento((Integer)event.getNewValue());
		System.out.println(eventoSeleccionado.toString());
		reclamoSeleccionado.setEventoId(eventoSeleccionado);
		
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
	public ReclamoDTO getReclamoSeleccionado() {
		return reclamoSeleccionado;
	}

	public void setReclamoSeleccionado(ReclamoDTO reclamoSeleccionado) {
		this.reclamoSeleccionado = reclamoSeleccionado;
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

	public ReclamoDTO getReclamoToEditEstado() {
		return reclamoToEditEstado;
	}

	public void setReclamoToEditEstado(ReclamoDTO reclamoToEditEstado) {
		this.reclamoToEditEstado = reclamoToEditEstado;
	}

	public Integer getIdTipoEstado() {
		return idTipoEstado;
	}

	public void setIdTipoEstado(Integer idTipoEstado) {
		this.idTipoEstado = idTipoEstado;
	}
	
}
