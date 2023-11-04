package com.byteminds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.byteminds.negocio.GestionTutorResponsableEventoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.TutorResponsableEventoDTO;

@Named(value = "gestionTutorResponsableEventoBean") // JEE8
@SessionScoped // JEE8
public class GestionTutorResponsableEventoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionTutorResponsableEventoService gTRE;

	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	@Inject
	GestionEventoBean gestionEventoBean;
	
	private Integer id;
	private String modalidad;

	private List<TutorResponsableEventoDTO> listaTutoresResponsablesEvento;
	
	private List<TutorDTO> listaTutoresSeleccionados;
	private List<TutorDTO> listaAllTutores;

	private boolean modoEdicion = false;

	public GestionTutorResponsableEventoBean() {
		listaTutoresResponsablesEvento = new ArrayList<TutorResponsableEventoDTO>();
		listaAllTutores=gestionUsuarioService.listadoDeTutoresActivos();
	}

	public void inicializar(List<TutorResponsableEventoDTO> listaTutoresResponsablesEvento) {
		this.listaTutoresResponsablesEvento=listaTutoresResponsablesEvento;
	}


	

	public List<TutorDTO> getListaTutoresSeleccionados() {
		return listaTutoresSeleccionados;
	}

	public void setListaTutoresSeleccionados(List<TutorDTO> listaTutoresSeleccionados) {
		this.listaTutoresSeleccionados = listaTutoresSeleccionados;
	}

	public List<TutorResponsableEventoDTO> getListaTutoresResponsablesEvento() {
		return listaTutoresResponsablesEvento;
	}

	public void setListaTutoresResponsablesEvento(List<TutorResponsableEventoDTO> listaTutoresResponsablesEvento) {
		this.listaTutoresResponsablesEvento = listaTutoresResponsablesEvento;
	}

	
	public List<TutorDTO> getListaAllTutores() {
		return listaAllTutores;
	}

	public void setListaAllTutores(List<TutorDTO> listaAllTutores) {
		this.listaAllTutores = listaAllTutores;
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

}
