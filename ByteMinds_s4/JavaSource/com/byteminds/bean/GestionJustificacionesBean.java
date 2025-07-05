package com.byteminds.bean;

import javax.ejb.EJB;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;

import com.byteminds.negocio.GestionJustificacionService;

import com.byteminds.negocio.JustificacionDTO;

import javax.enterprise.context.SessionScoped;

@Named(value = "gestionJustificacionesBean") // JEE8
@SessionScoped // JEE8
public class GestionJustificacionesBean implements Serializable {

	@EJB
	GestionJustificacionService gestionJustificacionService;

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

	private String criterioDescripcion;
	private Date criterioFecha;
	private String criterioEventoActividad;
	private Date criterioFechaEvento;
	private String criterioDocente;

	private String criterioTipoEstado;

	private List<JustificacionDTO> listadoDeJustificacionFiltrados;

	public GestionJustificacionesBean() {
		super();
		// TODO: INICIALIZAMOS Los filtros de eventos para que esten en vacios
		listadoDeJustificacionFiltrados = new ArrayList<>();
	}

	// ********Acciones****************************
	public String seleccionarEventos() throws PersistenciaException {
		listadoDeJustificacionFiltrados.clear();
		// Verificar si los criterios son nulos y asignar valores predeterminados si es
		// necesario

		if (criterioDescripcion == null) {
			criterioDescripcion = "";
		}
		if (criterioEventoActividad == null) {
			criterioEventoActividad = "";
		}
		if (criterioDocente == null) {
			criterioDocente = "";
		}

		if (criterioFecha == null) {
			// Manejar el caso en el que criterioFinInicio sea nulo
		}
		if (criterioFechaEvento == null) {
			// Manejar el caso en el que criterioFinFin sea nulo
		}
		if (criterioTipoEstado == null) {
			criterioTipoEstado = "";
		}
		if(loginBean.esEstudiante()){
			Integer idEstudiante = loginBean.getUsuarioLogeado().getId();
			listadoDeJustificacionFiltrados = gestionJustificacionService.buscarJustificacionsEstudiante(idEstudiante);
		}else {
			listadoDeJustificacionFiltrados = gestionJustificacionService.listarJustificacions();
		}
		return "";
	}

	public String seleccionarEventosEstudiante() throws PersistenciaException {
		listadoDeJustificacionFiltrados.clear();
		listadoDeJustificacionFiltrados = gestionJustificacionService
				.buscarJustificacionsEstudiante(loginBean.getUsuarioLogeado().getId());

		return "";

	}

	public String verDatosJustificacion() {
		// Navegamos a datos reclamo
		return "DatosJustificacion";
	}

	// ******** Getters & Setters****************************

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

	public List<JustificacionDTO> getListadoDeJustificacionFiltrados() {
		return listadoDeJustificacionFiltrados;
	}

	public void setListadoDeJustificacionFiltrados(List<JustificacionDTO> listadoDeJustificacionFiltrados) {
		this.listadoDeJustificacionFiltrados = listadoDeJustificacionFiltrados;
	}

	public String getCriterioTipoEstado() {
		return criterioTipoEstado;
	}

	public void setCriterioTipoEstado(String criterioTipoEstado) {
		this.criterioTipoEstado = criterioTipoEstado;
	}

}
