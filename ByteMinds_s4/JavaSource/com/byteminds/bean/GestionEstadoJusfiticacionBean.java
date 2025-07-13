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

import com.byteminds.negocio.AccionJustificacionDTO;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.GestionAccionJustificacionService;
import com.byteminds.negocio.GestionTipoEstadoJustificacionService;
import com.byteminds.negocio.JustificacionDTO;
import com.byteminds.negocio.TipoEstadoJustificacionDTO;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



@Named(value="gestionEstadoJustificacionBean")		//JEE8
@SessionScoped	
public class GestionEstadoJusfiticacionBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	CombosBean combosBean;
	
	GestionTipoEstadoJustificacionService gTERS = new GestionTipoEstadoJustificacionService();
	
	private List<TipoEstadoJustificacionDTO> listEstadosJustificacion;
	private TipoEstadoJustificacionDTO estadosJustificacionSeleccionado;
	
	
	public GestionEstadoJusfiticacionBean() {
		listEstadosJustificacion = new ArrayList<TipoEstadoJustificacionDTO>();
		estadosJustificacionSeleccionado = new TipoEstadoJustificacionDTO();
	}


	public String inicializar() {
		System.out.println("INICIALIZANDO GestionEstadoJustificacionBean");
		listEstadosJustificacion.clear();
		listEstadosJustificacion=gTERS.listarTipoEstadoJustificacion();
		estadosJustificacionSeleccionado = new TipoEstadoJustificacionDTO();
		return "/pages/eventos/justificaciones/gestionestadojustificacion.xhtml";
	}
	
	
	public String editarEstadoJustificacion(TipoEstadoJustificacionDTO tERDTO) {
		System.out.println("GestionEstadoJustificacionBean editarEstadoJustificacion");
		estadosJustificacionSeleccionado= tERDTO;
		
		return "";
	}
	
	
	public String guardar() {
		System.out.println("GestionEstadoJustificacionBean guardar");
		System.out.println(estadosJustificacionSeleccionado.getId());
		System.out.println(estadosJustificacionSeleccionado.getNombre());
		System.out.println(estadosJustificacionSeleccionado.getActivo());
		System.out.println("-*--*-*-*-*--*--*-*-*-*--*--*-*-*-*--*--*-*-*-*-");
		if(estadosJustificacionSeleccionado.getNombre()==""||estadosJustificacionSeleccionado.getNombre().equals("")) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar valores para un nuevo estado",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}else {
			gTERS.guardar(estadosJustificacionSeleccionado);
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ingreso/modifico el estado de los justificaciones",	"");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		inicializar();
		}
		//Mandamos a refrescar los combos para que tomen los cambios
		combosBean.cargarTipoEstadoJustificacion();
		
		
		return "";
	}
	
	
	public List<TipoEstadoJustificacionDTO> getListEstadosJustificacion() {
		return listEstadosJustificacion;
	}


	public void setListEstadosJustificacion(List<TipoEstadoJustificacionDTO> listEstadosJustificacion) {
		this.listEstadosJustificacion = listEstadosJustificacion;
	}


	public TipoEstadoJustificacionDTO getEstadosJustificacionSeleccionado() {
		return estadosJustificacionSeleccionado;
	}


	public void setEstadosJustificacionSeleccionado(TipoEstadoJustificacionDTO estadosJustificacionSeleccionado) {
		this.estadosJustificacionSeleccionado = estadosJustificacionSeleccionado;
	}
	
	
	
	
}
