package com.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;	//JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa2LogicaNegocio.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;


//@ManagedBean(name="usuario")

@Named(value="gestionUsuario")		//JEE8
@SessionScoped				        //JEE8
public class GestionUsuarioBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Inject
	//PersistenciaBean persistenciaBean;
	@EJB
	GestionUsuarioService gestionUsuarioService;

	private Long id;
	private String modalidad;
	
	private Usuario usuarioSeleccionado;
	

	private boolean modoEdicion=false;
	public GestionUsuarioBean() {
		super();
	
		
	}
	
	@PostConstruct
	public void init() {
		//empleadoSeleccionado=new Empleado();
	}
	
	

	//se ejecuta antes de desplegar la vista
	public void preRenderViewListener() {
		
		if (id!=null) {
			usuarioSeleccionado=gestionUsuarioService.buscarUsuario(id);
		} else {
			usuarioSeleccionado=new Usuario();
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion=false;
		}else if (modalidad.contentEquals("update")) {
			modoEdicion=true;
		}else if (modalidad.contentEquals("insert")) {
			modoEdicion=true;
		}else if (modalidad.contentEquals("edit")) {
			modoEdicion=true;
		}else {
			
			modoEdicion=false;
			modalidad="view";
	
		}
	}
	//acciones
	public String cambiarModalidadUpdate() throws CloneNotSupportedException {
		//this.modalidad="update";
		return "DatosUsuario?faces-redirect=true&includeViewParams=true";
		
	}
	//Pasar a modo 
	public String salvarCambios() {
		
		if (usuarioSeleccionado.getId()==null) {
			usuarioSeleccionado.setActivo(true);
			
			Usuario usuarioNuevo;
			try {
				usuarioNuevo = (Usuario) gestionUsuarioService.agregarUsuario(usuarioSeleccionado);
				this.id=usuarioNuevo.getId();
			
				//mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo usuario", "");
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
				gestionUsuarioService.actualizarUsuario(usuarioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el usuario.",""));
				
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
	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}
	public void setUsuarioSeleccionado(Usuario empleadoSeleccionado) {
		this.usuarioSeleccionado = empleadoSeleccionado;
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
		
	public GestionUsuarioService getGestionUsuarioService() {
		return gestionUsuarioService;
	}
	public void setGestionUsuarioService(GestionUsuarioService gestionUsuarioService) {
		this.gestionUsuarioService = gestionUsuarioService;
	}
	
	
}
