package com.byteminds.bean;

import javax.annotation.PostConstruct;
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
import com.byteminds.negocio.GestionTipoEstadoReclamoService;
import com.byteminds.negocio.ReclamoDTO;
import com.byteminds.negocio.TipoEstadoReclamoDTO;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



@Named(value="gestionEstadoReclamoBean")		//JEE8
@SessionScoped	
public class GestionEstadoReclamoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	GestionTipoEstadoReclamoService gTERS = new GestionTipoEstadoReclamoService();
	
	private List<TipoEstadoReclamoDTO> listEstadosReclamo;
	private TipoEstadoReclamoDTO estadosReclamoSeleccionado;
	
	
	public GestionEstadoReclamoBean() {
		listEstadosReclamo = new ArrayList<TipoEstadoReclamoDTO>();
		estadosReclamoSeleccionado = new TipoEstadoReclamoDTO();
	}


	public String inicializar() {
		System.out.println("INICIALIZANDO GestionEstadoReclamoBean");
		listEstadosReclamo.clear();
		listEstadosReclamo=gTERS.listarTipoEstadoReclamo();
		estadosReclamoSeleccionado = new TipoEstadoReclamoDTO();
		return "/pages/reclamos/gestionestadoreclamo.xhtml?faces-redirect=true";
	}
	
	
	public String editarEstadoReclamo(TipoEstadoReclamoDTO tERDTO) {
		System.out.println("GestionEstadoReclamoBean editarEstadoReclamo");
		estadosReclamoSeleccionado= tERDTO;
		
		return "";
	}
	
	
	public String guardar() {
		System.out.println("GestionEstadoReclamoBean guardar");
		System.out.println(estadosReclamoSeleccionado.getId());
		System.out.println(estadosReclamoSeleccionado.getNombre());
		System.out.println(estadosReclamoSeleccionado.getActivo());
		System.out.println("-*--*-*-*-*--*--*-*-*-*--*--*-*-*-*--*--*-*-*-*-");
		if(estadosReclamoSeleccionado.getNombre()==""||estadosReclamoSeleccionado.getNombre().equals("")) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar valores para un nuevo estado",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}else {
			gTERS.guardar(estadosReclamoSeleccionado);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ingreso/modifico el estado de los reclamos",	"");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		inicializar();
		}
		return "";
	}
	
	
	public List<TipoEstadoReclamoDTO> getListEstadosReclamo() {
		return listEstadosReclamo;
	}


	public void setListEstadosReclamo(List<TipoEstadoReclamoDTO> listEstadosReclamo) {
		this.listEstadosReclamo = listEstadosReclamo;
	}


	public TipoEstadoReclamoDTO getEstadosReclamoSeleccionado() {
		return estadosReclamoSeleccionado;
	}


	public void setEstadosReclamoSeleccionado(TipoEstadoReclamoDTO estadosReclamoSeleccionado) {
		this.estadosReclamoSeleccionado = estadosReclamoSeleccionado;
	}
	
	
	
	
}
