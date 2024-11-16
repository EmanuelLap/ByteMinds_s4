package com.byteminds.bean;

import javax.ejb.EJB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.byteminds.negocio.AccionJustificacionDTO;
import com.byteminds.negocio.AccionJustificacionDTO;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.GestionAccionJustificacionService;
import com.byteminds.negocio.GestionAccionJustificacionService;
import com.byteminds.negocio.JustificacionDTO;
import com.byteminds.negocio.JustificacionDTO;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



@Named(value="gestionAccionJustificacionBean")		//JEE8
@SessionScoped				        //JEE8
public class GestionAccionJustificacionBean implements Serializable{

//	@ManagedProperty(value="#{loginBean}")
	@Inject
    LoginBean loginBean = new LoginBean();

	
	
	GestionAccionJustificacionService gAJ= new GestionAccionJustificacionService();
	
	private static final long serialVersionUID = 1L;
	
	private JustificacionDTO justificacionSeleccionado;
	private List<AccionJustificacionDTO> accionJustificacionList;
	private AccionJustificacionDTO accionJustificacionSeleccionado;
	private String detalle;
	private AccionJustificacionDTO actionJustificacionToEdit;
	
	public GestionAccionJustificacionBean() {
		justificacionSeleccionado = new JustificacionDTO();
		accionJustificacionList = new ArrayList<AccionJustificacionDTO>();
		accionJustificacionSeleccionado = new AccionJustificacionDTO();
	}
	

	public String inicializarAccionJustificacion(JustificacionDTO justificacion) {
		System.out.println("Justificacion accion para TODO");
		this.justificacionSeleccionado = justificacion;
		this.detalle ="";
		accionJustificacionList.clear();
		accionJustificacionSeleccionado = new AccionJustificacionDTO();
		accionJustificacionSeleccionado.setFecha(new Date(System.currentTimeMillis()));
		accionJustificacionSeleccionado.setJustificacionId(justificacionSeleccionado);
		System.out.println(loginBean.getUserioLogeado().getNombres() +" " +loginBean.getUserioLogeado().getApellidos() );
		accionJustificacionSeleccionado.setAnalistaId((AnalistaDTO)loginBean.getUserioLogeado());
		
		//traer lista de acciones sobre este reclamo mismo
		
		accionJustificacionList.addAll(gAJ.listarAccionAJustificacionDTO(justificacion.getId()));
		
		
		return "/pages/eventos/justificaciones/registroaccionjustificacion?faces-redirect=true";
	}
	
	
	public String inicializarAccionJustificacionEstudiante(JustificacionDTO justificacion) {
		System.out.println("Justificacion accion para TODO");
		this.justificacionSeleccionado = justificacion;
		accionJustificacionList.clear();
		//traer lista de acciones sobre este reclamo mismo
		accionJustificacionList.addAll(gAJ.listarAccionAJustificacionDTO(justificacion.getId()));
		
		
		return "/pages/eventos/justificaciones/registroaccionrjustificacionestudiante?faces-redirect=true";
	}
	
	public String guardarAccionJustificacion() {
		System.out.println("TODO: guardarAccionJustificacion");
		System.out.println("this.detalle  " +this.detalle );
		if(this.detalle != null && this.detalle != "" ) {
			accionJustificacionSeleccionado.setDetalle(detalle);
			accionJustificacionSeleccionado.setActivo(true);
			AccionJustificacionDTO acRec= gAJ.agregarAccionAJustificacionDTO(accionJustificacionSeleccionado);
			accionJustificacionList.add(acRec);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo accion sobre justificacion",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}else {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se pudo agregar la accion sobre justificacion",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		this.detalle ="";
		return "";
	}
	
	public String volver() {
		System.out.println("TODO: volver");
		return "/pages/eventos/justificaciones/listadoJustificaciones.xhtml";//pages/reclamos/listadoJustificacions.xhtml
	}
	
	public String volverEstudiante() {
		System.out.println("TODO: volver");
		return "/pages/eventos/justificaciones/listadoJustificacionesEstudiante.xhtml";//pages/reclamos/listadoJustificacions.xhtml
	}
	public String editarAccionJustificacion() {
		System.out.println("TODO: editarAccionJustificacion");
		
		gAJ.modificarAccionAJustificacionDTO(actionJustificacionToEdit);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el detalle de accion justificacion",	"");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		accionJustificacionList.clear();
		accionJustificacionList.addAll(gAJ.listarAccionAJustificacionDTO(actionJustificacionToEdit.getJustificacionId().getId()));
		return "";
	}
	public String eliminarAccionJustificacion(AccionJustificacionDTO aRDTO) {
		System.out.println("TODO: eliminarAccionJustificacion");
		
		return "";
	}
	public void setJustificacionSeleccionado(JustificacionDTO justificacionSeleccionado) {
		this.justificacionSeleccionado = justificacionSeleccionado;
	}

	public JustificacionDTO getJustificacionSeleccionado() {
		return justificacionSeleccionado;
	}


	public List<AccionJustificacionDTO> getAccionJustificacionList() {
		return accionJustificacionList;
	}


	public void setAccionJustificacionList(List<AccionJustificacionDTO> accionJustificacionList) {
		this.accionJustificacionList = accionJustificacionList;
	}


	public AccionJustificacionDTO getAccionJustificacionSeleccionado() {
		return accionJustificacionSeleccionado;
	}


	public void setAccionJustificacionSeleccionado(AccionJustificacionDTO accionJustificacionSeleccionado) {
		this.accionJustificacionSeleccionado = accionJustificacionSeleccionado;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}


	public AccionJustificacionDTO getActionJustificacionToEdit() {
		return actionJustificacionToEdit;
	}


	public void setActionJustificacionToEdit(AccionJustificacionDTO actionJustificacionToEdit) {
		this.actionJustificacionToEdit = actionJustificacionToEdit;
	}
	
}
