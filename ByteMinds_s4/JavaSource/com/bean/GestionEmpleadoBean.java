package com.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;


import com.capa2LogicaNegocio.Empleado;
import com.capa2LogicaNegocio.GestionEmpleadoService;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;	//JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;


//@ManagedBean(name="usuario")

@Named(value="gestionEmpleado")		//JEE8
@SessionScoped				        //JEE8
public class GestionEmpleadoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Inject
	//PersistenciaBean persistenciaBean;
	@EJB
	GestionEmpleadoService gestionEmpleadoService;

	private Long id;
	private String modalidad;
	
	private Empleado empleadoSeleccionado;
	private boolean modoEdicion=false;
	public GestionEmpleadoBean() {
		super();
	}
	@PostConstruct
	public void init() {
		//empleadoSeleccionado=new Empleado();
	}
	
	

	//se ejecuta antes de desplegar la vista
	public void preRenderViewListener() {
		
		if (id!=null) {
			empleadoSeleccionado=gestionEmpleadoService.buscarEmpleado(id);
		} else {
			empleadoSeleccionado=new Empleado();
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion=false;
		}else if (modalidad.contentEquals("update")) {
			modoEdicion=true;
		}else if (modalidad.contentEquals("insert")) {
			modoEdicion=true;
		}else {
			modoEdicion=false;
			modalidad="view";
	
		}
	}
	//acciones
	public String cambiarModalidadUpdate() throws CloneNotSupportedException {
		//this.modalidad="update";
		return "DatosEmpleado?faces-redirect=true&includeViewParams=true";
		
	}
	//Pasar a modo 
	public String salvarCambios() {
		
		if (empleadoSeleccionado.getId()==null) {
			empleadoSeleccionado.setActivo(true);
			
			Empleado empleadoNuevo;
			try {
				empleadoNuevo = (Empleado) gestionEmpleadoService.agregarEmpleado(empleadoSeleccionado);
				this.id=empleadoNuevo.getId();
			
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo Empleado", "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				
				this.modalidad="view";
			
				
			} catch (PersistenciaException e) {
				
				Throwable rootException=ExceptionsTools.getCause(e);
				String msg1=e.getMessage();
				String msg2=ExceptionsTools.formatedMsg(rootException);
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				this.modalidad="update";
				
				e.printStackTrace();
			}
			
			
			 
		} else if (modalidad.equals("update")) {
			
			try {
				gestionEmpleadoService.actualizarEmpleado(empleadoSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado Empleado.",""));
				
			} catch (PersistenciaException e) {

				Throwable rootException=ExceptionsTools.getCause(e);
				String msg1=e.getMessage();
				String msg2=ExceptionsTools.formatedMsg(e.getCause());
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			
				this.modalidad="update";
			
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}
	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}
	public boolean getModoEdicion() {
		return modoEdicion;
	}
	public boolean isModoEdicion() {
		return modoEdicion;
	}
	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}
		
	
	
	
}
