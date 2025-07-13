package com.byteminds.bean;


import javax.ejb.EJB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.EventoDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionReclamoService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.ReclamoDTO;
import com.byteminds.negocio.TipoEstadoEventoDTO;
import com.byteminds.negocio.TutorResponsableEventoDTO;
import com.byteminds.negocio.UsuarioDTO;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;


@Named(value="gestionReclamosBean")		//JEE8
@SessionScoped				        //JEE8
public class GestionReclamosBean implements Serializable{
	
	@EJB
	GestionReclamoService gestionReclamoService;
	
	@Inject
    LoginBean loginBean = new LoginBean();
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
	private String criterioDescripcion;
	private Date criterioFecha;
	private EventoDTO criterioEventoActividad;
	private List<EventoDTO> listadoDeEventos;
	private Date criterioFechaEvento;
	private String criterioDocente;
	private String criterioSemestre;
	private String criterioCreditos;
	private String criterioTipoEstado;
	private Boolean criterioEliminados;
	private List<TutorResponsableEventoDTO> docentesDelEventoSeleccionado;
	
	private List<ReclamoDTO> listadoDeReclamosFiltrados;
	private List<ReclamoDTO> masterList; 
	
	public GestionReclamosBean() {
		super();
		//TODO: INICIALIZAMOS Los filtros de eventos para que esten en vacios
		listadoDeReclamosFiltrados= new ArrayList<>();
		masterList = new ArrayList<>();
		criterioEliminados=false;
	}
	
	// ********Acciones****************************
	public String seleccionarEventos() throws PersistenciaException {
		listadoDeReclamosFiltrados.clear();
		String criterioEventoActividadId;
		 // Verificar si los criterios son nulos y asignar valores predeterminados si es necesario
	    if (criterioTitulo == null) {
	        criterioTitulo = "";
	    }
	    if (criterioDescripcion == null) {
	    	criterioDescripcion = "";
	    }
	    if (criterioEventoActividad == null) {
	    	criterioEventoActividadId=null;
	    }else {
	    	criterioEventoActividadId=String.valueOf(criterioEventoActividad.getId());
	    	
	    }

	    if (criterioSemestre == null ) {
	    	criterioSemestre ="";
	    	
	    }
	    if (criterioCreditos == null) {
	    	criterioCreditos="";
	    }
	    if (criterioFecha == null) {
	        // Manejar el caso en el que criterioFinInicio sea nulo
	    }
	    if (criterioFechaEvento == null) {
	        // Manejar el caso en el que criterioFinFin sea nulo
	    }
	    if(criterioTipoEstado==null) {
	    	criterioTipoEstado="";
	    }
		//String id, String detalle, String fechaReclamo,
//		String accionReclamo, String eventoId, String estudianteId)
//	    listadoDeReclamosFiltrados=gestionReclamoService.buscarReclamosPor(null, criterioDescripcion, null, null, String.valueOf(criterioEventoActividad.getId()), null);
//	    listadoDeReclamosFiltrados=gestionReclamoService.buscarReclamosPor(criterioTitulo, criterioDescripcion, criterioFecha, String.valueOf(criterioEventoActividad.getId()), criterioFechaEvento, criterioSemestre, criterioCreditos, criterioTipoEstado, null);
	    
	    masterList=gestionReclamoService.buscarReclamosPor(
	    		criterioTitulo, 
	    		criterioDescripcion,
	    		criterioFecha,
	    		criterioEventoActividadId,
	    		criterioFechaEvento,
	    		criterioSemestre,
	    		criterioCreditos,
	    		criterioTipoEstado,
	    		null);
	    
	    
	    	
	    	aplicarFiltro();
	
		return "";
	}
	
	public void actualizarFiltroEliminados() {
        aplicarFiltro();
    }
	
	
    /** Ejecución de baja lógica y refresco de la lista en memoria */
    public void bajaLogicaReclamo(ReclamoDTO r) {
        try {
        	if(r.getEstadoReclamoId()== null || r.getEstadoReclamoId().getId()==3) {

	        	r.setActivo(false);
				gestionReclamoService.actualizarReclamo(r);
		
        	}else {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se puede eliminar un reclamo que ya se ha tomado y tiene un estado.","");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        	}
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        aplicarFiltro();
    }
    

    /** Ejecución de re-activación lógica y refresco */
    public void activarReclamo(ReclamoDTO r) {
    	r.setActivo(true);
        try {
			gestionReclamoService.actualizarReclamo(r);
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        aplicarFiltro();
    }
	private void aplicarFiltro() {
        listadoDeReclamosFiltrados = masterList.stream()
            // Si criterioEliminados==false muestro r.activo==true
            // Si criterioEliminados==true  muestro r.activo==false
            .filter(r -> r.getActivo() != criterioEliminados)
            .collect(Collectors.toList());
    }
	
	public String seleccionarEventosEstudiante() throws PersistenciaException {
		listadoDeReclamosFiltrados.clear();
		masterList.clear();
		masterList=gestionReclamoService.buscarReclamosEstudiante(loginBean.getUsuarioLogeado().getId());
		aplicarFiltro();
		return "/pages/reclamos/listadoReclamosEstudiante.xhtml?faces-redirect=true";
		
	}

	public String verDatosReclamo() {
		//Navegamos a datos reclamo
		return "DatosReclamo";
	}


	// Método para cargar docentes
	public void cargarDocentesDelEvento() {
	    if (criterioEventoActividad != null && criterioEventoActividad.getTutorResponsableEventoDTOCollection() != null) {
	        docentesDelEventoSeleccionado = (List<TutorResponsableEventoDTO>) criterioEventoActividad.getTutorResponsableEventoDTOCollection();
	    } else {
	        docentesDelEventoSeleccionado = new ArrayList<>();
	    }
	}
	
	// ******** Getters & Setters****************************
	
	
	

	public String getCriterioTitulo() {
		return criterioTitulo;
	}

	public void setCriterioTitulo(String criterioTitulo) {
		this.criterioTitulo = criterioTitulo;
	}

	public String getCriterioDescripcion() {
		return criterioDescripcion;
	}

	public void setCriterioDescripcion(String criterioDescripcion) {
		this.criterioDescripcion = criterioDescripcion;
	}

	public Date getCriterioFecha() {
		return criterioFecha;
	}

	public void setCriterioFecha(Date criterioFecha) {
		this.criterioFecha = criterioFecha;
	}

	public EventoDTO getCriterioEventoActividad() {
		return criterioEventoActividad;
	}

	public void setCriterioEventoActividad(EventoDTO criterioEventoActividad) {
		this.criterioEventoActividad = criterioEventoActividad;
	}

	public Date getCriterioFechaEvento() {
		return criterioFechaEvento;
	}

	public void setCriterioFechaEvento(Date criterioFechaEvento) {
		this.criterioFechaEvento = criterioFechaEvento;
	}

	public String getCriterioDocente() {
		return criterioDocente;
	}

	public void setCriterioDocente(String criterioDocente) {
		this.criterioDocente = criterioDocente;
	}

	public String getCriterioSemestre() {
		return criterioSemestre;
	}

	public void setCriterioSemestre(String criterioSemestre) {
		this.criterioSemestre = criterioSemestre;
	}

	public String getCriterioCreditos() {
		return criterioCreditos;
	}

	public void setCriterioCreditos(String criterioCreditos) {
		this.criterioCreditos = criterioCreditos;
	}

	public List<ReclamoDTO> getListadoDeReclamosFiltrados() {
		return listadoDeReclamosFiltrados;
	}

	public void setListadoDeReclamosFiltrados(List<ReclamoDTO> listadoDeReclamosFiltrados) {
		this.listadoDeReclamosFiltrados = listadoDeReclamosFiltrados;
	}

	public String getCriterioTipoEstado() {
		return criterioTipoEstado;
	}

	public void setCriterioTipoEstado(String criterioTipoEstado) {
		this.criterioTipoEstado = criterioTipoEstado;
	}

	public List<EventoDTO> getListadoDeEventos() {
		return listadoDeEventos;
	}

	public void setListadoDeEventos(List<EventoDTO> listadoDeEventos) {
		this.listadoDeEventos = listadoDeEventos;
	}
	
	
	public List<TutorResponsableEventoDTO> getDocentesDelEventoSeleccionado() {
	    return docentesDelEventoSeleccionado;
	}

	public void setDocentesDelEventoSeleccionado(List<TutorResponsableEventoDTO> docentesDelEventoSeleccionado) {
	    this.docentesDelEventoSeleccionado = docentesDelEventoSeleccionado;
	}

	public Boolean getCriterioEliminados() {
		return criterioEliminados;
	}

	public void setCriterioEliminados(Boolean criterioEliminados) {
		this.criterioEliminados = criterioEliminados;
	}

	public List<ReclamoDTO> getMasterList() {
		return masterList;
	}

	public void setMasterList(List<ReclamoDTO> masterList) {
		this.masterList = masterList;
	}
	
	
	
}
