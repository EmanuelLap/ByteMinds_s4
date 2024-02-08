package com.byteminds.bean;

import javax.ejb.EJB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.byteminds.negocio.AccionReclamoDTO;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.GestionAccionReclamoService;
import com.byteminds.negocio.ReclamoDTO;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



@Named(value="gestionAccionReclamoBean")		//JEE8
@SessionScoped				        //JEE8
public class GestionAccionReclamoBean implements Serializable{

//	@ManagedProperty(value="#{loginBean}")
	@Inject
    LoginBean loginBean = new LoginBean();

	
	
	GestionAccionReclamoService gAR= new GestionAccionReclamoService();
	
	private static final long serialVersionUID = 1L;
	
	private ReclamoDTO reclamoSeleccionado;
	private List<AccionReclamoDTO> accionReclamoList;
	private AccionReclamoDTO accionReclamoSeleccionado;
	
	
	
	public GestionAccionReclamoBean() {
		reclamoSeleccionado = new ReclamoDTO();
		accionReclamoList = new ArrayList<AccionReclamoDTO>();
		accionReclamoSeleccionado = new AccionReclamoDTO();
	}
	

	public String inicializarAccionReclamo(ReclamoDTO reclamo) {
		System.out.println("Reclamo accion para TODO");
		this.reclamoSeleccionado = reclamo;
		
		accionReclamoList.clear();
		accionReclamoSeleccionado = new AccionReclamoDTO();
		accionReclamoSeleccionado.setFecha(new Date(System.currentTimeMillis()));
		accionReclamoSeleccionado.setReclamoId(reclamoSeleccionado);
		System.out.println(loginBean.getUserioLogeado().getNombres() +" " +loginBean.getUserioLogeado().getApellidos() );
		accionReclamoSeleccionado.setAnalistaId((AnalistaDTO)loginBean.getUserioLogeado());
		
		//traer lista de acciones sobre este reclamo mismo
		
		accionReclamoList.addAll(gAR.listarAccionAReclamoDTO(reclamo.getId()));
		
		
		return "/pages/reclamos/registroaccionreclamo?faces-redirect=true";
	}
	
	public String guardarAccionReclamo() {
		System.out.println("TODO: guardarAccionReclamo");
		System.out.println("accionReclamoSeleccionado.getDetalle() " +accionReclamoSeleccionado.getDetalle());
		if(accionReclamoSeleccionado.getDetalle()!= null && accionReclamoSeleccionado.getDetalle()!= "" ) {
			accionReclamoSeleccionado.setActivo(true);
			AccionReclamoDTO acRec= gAR.agregarAccionAReclamoDTO(accionReclamoSeleccionado);
			accionReclamoList.add(acRec);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo accion sobre reclamo",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}else {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se pudo agregar la accion sobre reclamo",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		
		return "";
	}
	
	public String validarAccionReclamo() {
		System.out.println("TODO: validarAccionReclamo");
		return "";
	}
	public String editarAccionReclamo(AccionReclamoDTO aRDTO) {
		System.out.println("TODO: editarAccionReclamo");
		return "";
	}
	public void setReclamoSeleccionado(ReclamoDTO reclamoSeleccionado) {
		this.reclamoSeleccionado = reclamoSeleccionado;
	}

	public ReclamoDTO getReclamoSeleccionado() {
		return reclamoSeleccionado;
	}


	public List<AccionReclamoDTO> getAccionReclamoList() {
		return accionReclamoList;
	}


	public void setAccionReclamoList(List<AccionReclamoDTO> accionReclamoList) {
		this.accionReclamoList = accionReclamoList;
	}


	public AccionReclamoDTO getAccionReclamoSeleccionado() {
		return accionReclamoSeleccionado;
	}


	public void setAccionReclamoSeleccionado(AccionReclamoDTO accionReclamoSeleccionado) {
		this.accionReclamoSeleccionado = accionReclamoSeleccionado;
	}
	
}
