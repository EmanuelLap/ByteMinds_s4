package com.byteminds.bean;


import javax.ejb.EJB;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.GestionUsuarioService;

import com.byteminds.negocio.UsuarioDTO;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;



//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;


//@ManagedBean(name="usuario")

@Named(value="gestionUsuarios")		//JEE8
@SessionScoped				        //JEE8
public class GestionUsuariosBean implements Serializable{
	
	@EJB
	GestionUsuarioService gestionUsuarioService;

//	@Inject
//	PersistenciaBean persistenciaBean;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String criterioNombre;
	private String criterioApellido;
	private Integer criterioDocumento;

	private String criterioROL;
	private String criterioITR;
	
	private Boolean criterioTodos;
	private Boolean criterioActivo;
	private Boolean criterioNoActivo;
	private Boolean criterioValidado;
	private Boolean criterioNoValidado;


	

	private List<UsuarioDTO> usuariosSeleccionados;
	private UsuarioDTO usuarioSeleccionado;
	public GestionUsuariosBean() {
		super();
		criterioNombre="";
		criterioApellido="";
		criterioROL="";
		criterioITR="";
		
		criterioTodos = true;
		criterioActivo = false;
		criterioNoActivo= false;
		criterioValidado= false;
		criterioNoValidado= false;
	}
	
	// ********Acciones****************************
	public String seleccionarUsuarios() throws PersistenciaException {
		usuariosSeleccionados=gestionUsuarioService.seleccionarUsuarios("", "", "", String.valueOf(criterioDocumento), criterioNombre,criterioApellido,
				"", "", criterioITR, "", criterioValidado, criterioActivo, criterioTodos, "", 
				"", criterioNoValidado, criterioNoActivo);
//		String tipo, String id ,String depto,String doc,String nombre,String apellido
//		,String mail,String usuario,String itrNombre,String generacion, Boolean validado ,Boolean activo,Boolean todos,String localidad
//		,String telefono,Boolean noValidados ,Boolean noActivos){
		return "";
	}
	
	
	public String verDatosUsuario() {
		//Navegamos a datos empleado
		return "DatosUsuario";
	}
	
	
	public void cambiarChecks() {
		if(criterioTodos ==true) {
			criterioActivo = false;
			criterioNoActivo= false;
			criterioValidado= false;
			criterioNoValidado= false;
		}
		
	}
	
	
	// ******** Getters & Setters****************************
	
	public String getCriterioNombre() {
		return criterioNombre;
	}
	public void setCriterioNombre(String criterioNombre) {
		this.criterioNombre = criterioNombre;
	}

	public String getCriterioApellido() {
		return criterioApellido;
	}

	public void setCriterioApellido(String criterioApellido) {
		this.criterioApellido = criterioApellido;
	}

	public Integer getCriterioDocumento() {
		return criterioDocumento;
	}

	public void setCriterioDocumento(Integer criterioDocumento) {
		this.criterioDocumento = criterioDocumento;
	}

	
	public String getCriterioROL() {
		return criterioROL;
	}

	public void setCriterioROL(String criterioROL) {
		this.criterioROL = criterioROL;
	}

	public String getCriterioITR() {
		return criterioITR;
	}

	public void setCriterioITR(String criterioITR) {
		this.criterioITR = criterioITR;
	}
	public Boolean getCriterioActivo() {
		return criterioActivo;
	}
	public void setCriterioActivo(Boolean criterioActivo) {
		this.criterioActivo = criterioActivo;
	}
	
	
	
	public Boolean getCriterioNoActivo() {
		return criterioNoActivo;
	}

	public void setCriterioNoActivo(Boolean criterioNoActivo) {
		this.criterioNoActivo = criterioNoActivo;
	}

	public Boolean getCriterioNoValidado() {
		return criterioNoValidado;
	}

	public void setCriterioNoValidado(Boolean criterioNoValidado) {
		this.criterioNoValidado = criterioNoValidado;
	}

	public Boolean getCriterioTodos() {
		return criterioTodos;
	}

	public void setCriterioTodos(Boolean criterioTodos) {
		this.criterioTodos = criterioTodos;
	}
	
	
	public Boolean getCriterioValidado() {
		return criterioValidado;
	}

	public void setCriterioValidado(Boolean criterioValidado) {
		this.criterioValidado = criterioValidado;
	}

	public List<UsuarioDTO> getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}
	public void setUsuariosSeleccionados(List<UsuarioDTO> usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}

	public UsuarioDTO getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}
	
	
	
	
}
