package com.byteminds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import com.byteminds.negocio.EventoDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionItrService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.TutorDTO;




@Named(value = "gestionEventoBean") // JEE8
@SessionScoped // JEE8
public class GestionEventoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionEventoService gestionEventoService;
	GestionItrService gestionItrService;
	GestionUsuarioService gestionUsuarioService;
	
	private Integer id;
	private String modalidad;
	private boolean modoEdicion = false;
	
	private EventoDTO eventoDTOseleccionado;
	
	private ItrDTO itrDTOSeleccionado;
	private Integer itrDTOSeleccionadoId;
 
	private List<TutorDTO> listaDeTutoresDisponibles;
	private List<TutorDTO> listaDeTutoresAsignados;
	private DualListModel<TutorDTO> tutores;
	 
	public GestionEventoBean() {
		System.out.println("INICIALIZANDO GestionEventoBean");
		itrDTOSeleccionado = new ItrDTO();
		gestionItrService= new GestionItrService();
		gestionUsuarioService = new GestionUsuarioService();
		eventoDTOseleccionado = new EventoDTO();
		//Cargando listado de tutores disponibles
		listaDeTutoresAsignados = new ArrayList<TutorDTO>();
		listaDeTutoresDisponibles = new ArrayList<TutorDTO>();
		listaDeTutoresDisponibles= gestionUsuarioService.listadoDeTutoresActivos();
		
        List<TutorDTO> tutoresTarget = new ArrayList<TutorDTO>();
        setTutores(new DualListModel<TutorDTO>(listaDeTutoresDisponibles, tutoresTarget));
    
		
	}

	public void preRenderViewListener() {
		System.out.println("INICIALIZANDO GestionEventoBean preRenderViewListener");
		if (id != null) {
			eventoDTOseleccionado = gestionEventoService.obtenerEvento(id);
		} else {
			eventoDTOseleccionado = new EventoDTO();

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
	}

	public String salvarCambios() {

//		if (reclamoSeleccionado.getId() == null) {
//
//
//			ReclamoDTO nuevoReclamoDTO;
//			try {
//				nuevoReclamoDTO = gestionReclamoService.agregarReclamo(reclamoSeleccionado);
//				this.id = nuevoReclamoDTO.getId();
//
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo reclamo",	"");
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "view";
//
//			} catch (PersistenciaException e) {
//
//				Throwable rootException = ExceptionsTools.getCause(e);
//				String msg1 = e.getMessage();
//				String msg2 = ExceptionsTools.formatedMsg(rootException);
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "update";
//
//				e.printStackTrace();
//			}
//
//		} else if (modalidad.equals("update")) {
//
//			try {
//				gestionReclamoService.agregarReclamo(reclamoSeleccionado);
//
//				FacesContext.getCurrentInstance().addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el reclamo.", ""));
//
//			} catch (PersistenciaException e) {
//
//				Throwable rootException = ExceptionsTools.getCause(e);
//				String msg1 = e.getMessage();
//				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "update";
//
//				e.printStackTrace();
//			}
//		}
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "click en gestion evento sin implementar.", ""));
		return "";
	}

	
	public void actualizarITRSeleccionado(AjaxBehaviorEvent event) {
	    Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
	    if(nuevoValor == -1) {
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar un ITR valido",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    }else {
	    itrDTOSeleccionado = gestionItrService.obtenerITRSeleccionado(nuevoValor);
	    this.eventoDTOseleccionado.setItrDTO(itrDTOSeleccionado);

	    }
	}

	public void asignarTutores() {
		listaDeTutoresAsignados = new ArrayList<TutorDTO>();
		listaDeTutoresAsignados.addAll(this.tutores.getTarget());
		
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

	public EventoDTO getEventoDTOseleccionado() {
		return eventoDTOseleccionado;
	}

	public void setEventoDTOseleccionado(EventoDTO eventoDTOseleccionado) {
		this.eventoDTOseleccionado = eventoDTOseleccionado;
	}

	public Integer getItrDTOSeleccionadoId() {
		return itrDTOSeleccionadoId;
	}

	public void setItrDTOSeleccionadoId(Integer itrDTOSeleccionadoId) {
		this.itrDTOSeleccionadoId = itrDTOSeleccionadoId;
	}


	public ItrDTO getItrDTOSeleccionado() {
		return itrDTOSeleccionado;
	}

	public void setItrDTOSeleccionado(ItrDTO itrDTOSeleccionado) {
		this.itrDTOSeleccionado = itrDTOSeleccionado;
	}

	public List<TutorDTO> getListaDeTutoresDisponibles() {
		return listaDeTutoresDisponibles;
	}

	public void setListaDeTutoresDisponibles(List<TutorDTO> listaDeTutoresDisponibles) {
		this.listaDeTutoresDisponibles = listaDeTutoresDisponibles;
	}

	public DualListModel<TutorDTO> getTutores() {
		return tutores;
	}

	public void setTutores(DualListModel<TutorDTO> tutores) {
		this.tutores = tutores;
	}

	public List<TutorDTO> getListaDeTutoresAsignados() {
		return listaDeTutoresAsignados;
	}

	public void setListaDeTutoresAsignados(List<TutorDTO> listaDeTutoresAsignados) {
		this.listaDeTutoresAsignados = listaDeTutoresAsignados;
	}
}
