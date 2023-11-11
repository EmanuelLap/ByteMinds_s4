package com.byteminds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.EventoDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionItrService;
import com.byteminds.negocio.GestionModalidadEventoService;
import com.byteminds.negocio.GestionTipoEventoService;
import com.byteminds.negocio.GestionTutorResponsableEventoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.TutorResponsableEventoDTO;
import com.byteminds.utils.ExceptionsTools;

import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.TutorResponsableEvento;

@Named(value = "gestionEventoBean") // JEE8
@SessionScoped// JEE8
public class GestionEventoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionEventoService gestionEventoService;
	@EJB
	GestionTutorResponsableEventoService gTRES;
	@EJB
	GestionItrService gestionItrService;
	@EJB
	GestionTipoEventoService gTE;
	@EJB
	GestionModalidadEventoService gME;
	@EJB
	GestionUsuarioService gestionUsuarioService;

	private Integer id;
	private String modalidad;
	private boolean modoEdicion = false;

	private EventoDTO eventoDTOseleccionado;
	private Integer tipoEventoSeleccionadoId;
	private Integer modalidadEventoSeleccionadoId;

	private ItrDTO itrDTOSeleccionado;
	private Integer itrDTOSeleccionadoId;

	private List<TutorDTO> listaDeTutoresDisponibles;
	private List<TutorDTO> listaDeTutoresAsignados;
	
	private List<TutorDTO> listaTutoresSeleccionados;
	
	private List<TutorDTO> listaDeTutoresFiltrados;
	
	private boolean editarTutores= false;


	private List<TutorDTO> listaDeTutoresAEliminar;
//	private DualListModel<TutorDTO> tutores ;
	
	
	private String filtroNombre;
	private String filtroApellido;
	private String filtroItr;
	private String filtroArea;

	
	public GestionEventoBean() {
		System.out.println("INICIALIZANDO GestionEventoBean");

	}

	public String inicializar() {
		System.out.println("INICIALIZANDO GestionEventoBean preRenderViewListener");
		itrDTOSeleccionado = new ItrDTO();
		listaDeTutoresDisponibles = new ArrayList<TutorDTO>();
		listaDeTutoresAsignados = new ArrayList<TutorDTO>();
		listaDeTutoresFiltrados= new ArrayList<TutorDTO>();
		listaTutoresSeleccionados=new ArrayList<TutorDTO>();
		eventoDTOseleccionado = new EventoDTO();
		this.editarTutores=false;	
//		tutores =new DualListModel<TutorDTO>(listaDeTutoresDisponibles,listaDeTutoresAsignados);
		
//		 if (!FacesContext.getCurrentInstance().isPostback()) {
		if (id != null) {
			eventoDTOseleccionado = gestionEventoService.obtenerEvento(id);
			this.itrDTOSeleccionadoId = eventoDTOseleccionado.getItrDTO().getId();
			this.tipoEventoSeleccionadoId = eventoDTOseleccionado.getTipoEvento().getId();
			this.modalidadEventoSeleccionadoId = eventoDTOseleccionado.getModalidadEvento().getId();
			
			cargarListaDeTutoresDisponibles();
			
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
//		}
		return "datosEvento.xhtml";
	}

	public void cargarTutores() {
//		this.listaDeTutoresAsignados.clear();
		if (!eventoDTOseleccionado.getTutorResponsableEventoDTOCollection().isEmpty()) {
			for (TutorResponsableEventoDTO tutor : eventoDTOseleccionado.getTutorResponsableEventoDTOCollection()) {
				this.listaDeTutoresAsignados.add(tutor.getTutorId());	
			}
			
//			listaDeTutoresDisponibles.removeAll(listaDeTutoresAsignados);
			
		}
//		setTutores(new DualListModel<TutorDTO>(listaDeTutoresDisponibles, listaDeTutoresAsignados));
	}

	public void cargarListaDeTutoresDisponibles() {
		System.out.println("APRETANDO BOTON cargarListaDeTutoresDisponibles");
		listaDeTutoresAsignados = new ArrayList<TutorDTO>();
		listaDeTutoresAsignados.clear();
		listaDeTutoresDisponibles = new ArrayList<TutorDTO>();
		listaDeTutoresDisponibles.clear();
		listaTutoresSeleccionados =  new ArrayList<TutorDTO>();
		listaTutoresSeleccionados.clear();
		listaDeTutoresAEliminar = new ArrayList<TutorDTO>();
		listaDeTutoresAEliminar.clear();
		listaDeTutoresDisponibles = gestionUsuarioService.listadoDeTutoresActivos();
		cargarTutores();
		
	}

	public String salvarCambios() {

		if (eventoDTOseleccionado.getId() == null) {

			EventoDTO nuevoEventoDTO;
			try {

				nuevoEventoDTO = gestionEventoService.agregarEvento(eventoDTOseleccionado);
				this.id = nuevoEventoDTO.getId();

				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo evento",
						"");
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

				gestionEventoService.actualizarEvento(eventoDTOseleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el evento.", ""));

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

//		FacesContext.getCurrentInstance().addMessage(null,
//				new FacesMessage(FacesMessage.SEVERITY_INFO, "click en gestion evento sin implementar.", ""));
		return "";
	}

	public void actualizarITRSeleccionado(AjaxBehaviorEvent event) {
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar un ITR valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {
			itrDTOSeleccionado = gestionItrService.obtenerITRSeleccionado(nuevoValor);
			this.eventoDTOseleccionado.setItrDTO(itrDTOSeleccionado);

		}
	}

	public void actualizarTipoEventoSeleccionado(AjaxBehaviorEvent event) {
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe seleccionar un Tipo evento valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {

			this.eventoDTOseleccionado.setTipoEvento(gTE.obtenerTipoEvento(nuevoValor));

		}
	}

	public void actualizarModalidadEventoSeleccionado(AjaxBehaviorEvent event) {
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe seleccionar un Tipo evento valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {

			this.eventoDTOseleccionado.setModalidadEvento(gME.obtenerModalidadEvento(nuevoValor));

		}
	}

	public void eliminarDeLista(TutorResponsableEventoDTO tRE) {
		if (eventoDTOseleccionado.getTutorResponsableEventoDTOCollection().contains(tRE)) {
			eventoDTOseleccionado.getTutorResponsableEventoDTOCollection().remove(tRE);
		}
	}
	public void confirmarSeleccion(){
		this.editarTutores=false;
		if(!this.listaDeTutoresFiltrados.isEmpty()) {
			for (TutorDTO tut : this.listaDeTutoresFiltrados) {
				Boolean agregar = true;
				for (TutorResponsableEventoDTO tutRE : eventoDTOseleccionado.getTutorResponsableEventoDTOCollection()) {
					if (tutRE.getTutorId().getId()== tut.getId()) {
						agregar = false;
						System.out.println("EL tutor" + tut.getNombres() + " " + tut.getApellidos()
								+ "  ya se encuentra en la lista no se agregara");
					}
				}
				if (agregar) {
					TutorResponsableEventoDTO tutResEvent = new TutorResponsableEventoDTO();
					tutResEvent.setTutorId(tut);
					tutResEvent.setEventoId(this.id);
					eventoDTOseleccionado.getTutorResponsableEventoDTOCollection().add(tutResEvent);
//					gTRES.agregarTutorRespEvento(tutResEvent);
				}
			}	
		}
	}
//	public void asignarTutores() {
//		System.out.println("APRETANDO BOTON asignarTutores");
//		listaDeTutoresAsignados.clear();
//		listaDeTutoresAsignados.addAll(this.tutores.getTarget());
//
//	}
	

	 public void onTransfer(TransferEvent event) {
	        // event.getItems() te da los elementos transferidos
	        for (Object item : event.getItems()) {
	            // Realizar cast del objeto
	            TutorDTO tutorTransferido = (TutorDTO) item;

	            if (event.isRemove()) { // Verificar si el elemento fue eliminado del 'target'
	                // Manejar la eliminación
	            	System.out.println("SE ELIMINO DE LA LISTA TARGET");
	            	listaDeTutoresAEliminar.add(tutorTransferido);
	            	
	            } else if (event.isAdd()) {
	                // Manejar la adición si es necesario
	            	System.out.println("SE AGREGO UN NUEVO TUTOR A la lista DEL EVENTO");
	            }
	        }
	        
	    }
		public String actualizarTabla() {
			System.out.println("actualizarTablaINICIO");
			PrimeFaces.current().ajax().update("tablaTUTORESASIGNADOS");
			System.out.println("actualizarTablaFIN");
			return "";
		}
		public String editarTutorex() {
			System.out.println("editarTutorex "+this.editarTutores);
			this.editarTutores=true;
			System.out.println("editarTutorexFIN "+this.editarTutores);
			return "";
		}
	
		public String seleccionTutor(Object tutor) {
			System.out.println("editarTutorex222222222222");
			this.listaTutoresSeleccionados.add((TutorDTO)tutor);
			System.out.println("editarTutorex22222222222244444444444");
			return "";
		}
		
		
		
	public List<TutorDTO> getListaTutoresSeleccionados() {
			return listaTutoresSeleccionados;
		}

		public void setListaTutoresSeleccionados(List<TutorDTO> listaTutoresSeleccionados) {
			this.listaTutoresSeleccionados = listaTutoresSeleccionados;
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

//	public DualListModel<TutorDTO> getTutores() {
//		return tutores;
//	}
//
//	public void setTutores(DualListModel<TutorDTO> tutores) {
//		this.tutores = tutores;
//	}

	public List<TutorDTO> getListaDeTutoresAsignados() {
		return listaDeTutoresAsignados;
	}

	public void setListaDeTutoresAsignados(List<TutorDTO> listaDeTutoresAsignados) {
		this.listaDeTutoresAsignados = listaDeTutoresAsignados;
	}

	public Integer getTipoEventoSeleccionadoId() {
		return tipoEventoSeleccionadoId;
	}

	public void setTipoEventoSeleccionadoId(Integer tipoEventoSeleccionadoId) {
		this.tipoEventoSeleccionadoId = tipoEventoSeleccionadoId;
	}

	public Integer getModalidadEventoSeleccionadoId() {
		return modalidadEventoSeleccionadoId;
	}

	public void setModalidadEventoSeleccionadoId(Integer modalidadEventoSeleccionadoId) {
		this.modalidadEventoSeleccionadoId = modalidadEventoSeleccionadoId;
	}

	public List<TutorDTO> getListaDeTutoresAEliminar() {
		return listaDeTutoresAEliminar;
	}

	public void setListaDeTutoresAEliminar(List<TutorDTO> listaDeTutoresAEliminar) {
		this.listaDeTutoresAEliminar = listaDeTutoresAEliminar;
	}
	public List<TutorDTO> getListaDeTutoresFiltrados() {
		return listaDeTutoresFiltrados;
	}

	public void setListaDeTutoresFiltrados(List<TutorDTO> listaDeTutoresFiltrados) {
		this.listaDeTutoresFiltrados = listaDeTutoresFiltrados;
	}

	
	public String getFiltroNombre() {
		return filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) {
		this.filtroNombre = filtroNombre;
	}

	public String getFiltroApellido() {
		return filtroApellido;
	}

	public void setFiltroApellido(String filtroApellido) {
		this.filtroApellido = filtroApellido;
	}

	public String getFiltroItr() {
		return filtroItr;
	}

	public void setFiltroItr(String filtroItr) {
		this.filtroItr = filtroItr;
	}

	public String getFiltroArea() {
		return filtroArea;
	}

	public void setFiltroArea(String filtroArea) {
		this.filtroArea = filtroArea;
	}
	public boolean isEditarTutores() {
		return editarTutores;
	}

	public void setEditarTutores(boolean editarTutores) {
		this.editarTutores = editarTutores;
	}
}
