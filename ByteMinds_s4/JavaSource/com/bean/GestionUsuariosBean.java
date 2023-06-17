package com.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;



import com.capa2LogicaNegocio.Usuario;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.exception.PersistenciaException;

import javax.enterprise.context.SessionScoped;	//JEE8

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
	private Boolean criterioActivo;
	
	private List<Usuario> usuariosSeleccionados;
	private Usuario usuarioSeleccionado;
	public GestionUsuariosBean() {
		super();
	}
	
	// ********Acciones****************************
	public String seleccionarUsuarios() throws PersistenciaException {
		usuariosSeleccionados=gestionUsuarioService.seleccionarUsuarios(criterioNombre, criterioApellido, criterioDocumento,criterioActivo);
		return "";
	}
	
	
	public String verDatosUsuario() {
		//Navegamos a datos empleado
		return "DatosUsuario";
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

	public Boolean getCriterioActivo() {
		return criterioActivo;
	}
	public void setCriterioActivo(Boolean criterioActivo) {
		this.criterioActivo = criterioActivo;
	}
	public List<Usuario> getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}
	public void setUsuariosSeleccionados(List<Usuario> usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}
	
	
	
	
}
