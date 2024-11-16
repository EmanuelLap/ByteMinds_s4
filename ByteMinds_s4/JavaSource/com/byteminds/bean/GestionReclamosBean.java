package com.byteminds.bean;


import javax.ejb.EJB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
	private String criterioEventoActividad;
	private Date criterioFechaEvento;
	private String criterioDocente;
	private String criterioSemestre;
	private String criterioCreditos;
	private String criterioTipoEstado;
	
	
	private List<ReclamoDTO> listadoDeReclamosFiltrados;

	public GestionReclamosBean() {
		super();
		//TODO: INICIALIZAMOS Los filtros de eventos para que esten en vacios
		listadoDeReclamosFiltrados= new ArrayList<>();
	}
	
	// ********Acciones****************************
	public String seleccionarEventos() throws PersistenciaException {
		listadoDeReclamosFiltrados.clear();
		 // Verificar si los criterios son nulos y asignar valores predeterminados si es necesario
	    if (criterioTitulo == null) {
	        criterioTitulo = "";
	    }
	    if (criterioDescripcion == null) {
	    	criterioDescripcion = "";
	    }
	    if (criterioEventoActividad == null) {
	    	criterioEventoActividad = "";
	    }
	    if (criterioDocente == null) {
	    	criterioDocente = "";
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
		
	    listadoDeReclamosFiltrados=gestionReclamoService.listarReclamos();

		return "";
	}
	public String seleccionarEventosEstudiante() throws PersistenciaException {
		listadoDeReclamosFiltrados.clear();
		listadoDeReclamosFiltrados=gestionReclamoService.buscarReclamosEstudiante(loginBean.getUserioLogeado().getId());

		return "/pages/reclamos/listadoReclamosEstudiante.xhtml?faces-redirect=true";
		
	}

	public String verDatosReclamo() {
		//Navegamos a datos reclamo
		return "DatosReclamo";
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

	public String getCriterioEventoActividad() {
		return criterioEventoActividad;
	}

	public void setCriterioEventoActividad(String criterioEventoActividad) {
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
	
	
}
