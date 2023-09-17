package com.byteminds.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.utils.ExceptionsTools;

import io.jsonwebtoken.Jwts;

@Named(value = "gestionUsuario") // JEE8
@SessionScoped // JEE8
public class GestionUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Inject
	// PersistenciaBean persistenciaBean;
	@EJB
	GestionUsuarioService gestionUsuarioService;

	private Integer id;
	private String modalidad;

	private String contrasenia;
	private String nuevaContrasenia;
	private String nuevaContraseniaConfirmar;

	private UsuarioDTO usuarioSeleccionado;

	private boolean modoEdicion = false;

	public GestionUsuarioBean() {
		super();

	}

	@PostConstruct
	public void init() {
		// empleadoSeleccionado=new Empleado();
	}

	// se ejecuta antes de desplegar la vista
	public void preRenderViewListener() {

		if (id != null) {
			usuarioSeleccionado = gestionUsuarioService.buscarUsuario(id);
		} else {
			usuarioSeleccionado = new UsuarioDTO();
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion = false;
		} else if (modalidad.contentEquals("update")) {
			modoEdicion = true;
		} else if (modalidad.contentEquals("insert")) {
			modoEdicion = true;
		} else if (modalidad.contentEquals("edit")) {
			modoEdicion = true;
		} else {

			modoEdicion = false;
			modalidad = "view";

		}
	}

	// acciones
	public String cambiarModalidadUpdate() throws CloneNotSupportedException {
		// this.modalidad="update";
		return "DatosUsuario?faces-redirect=true&includeViewParams=true";

	}

	// Pasar a modo
	public String salvarCambios() {

		if (usuarioSeleccionado.getId() == null) {
			usuarioSeleccionado.setActivo(true);

			UsuarioDTO usuarioNuevo;
			try {
				usuarioNuevo = (UsuarioDTO) gestionUsuarioService.agregarUsuario(usuarioSeleccionado);
				this.id = usuarioNuevo.getId();

				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo usuario",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "view";

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(rootException);
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}

		} else if (modalidad.equals("update")) {

			try {
				gestionUsuarioService.actualizarUsuario(usuarioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el usuario.", ""));

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}
		}
		return "";
	}

	public String bajaLogicaUsuario() {

		if (usuarioSeleccionado.getId() != null) {
			usuarioSeleccionado.setActivo(false);

			try {
				gestionUsuarioService.actualizarUsuario(usuarioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha eliminado el usuario.", ""));

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}
		}
		return "";
	}

	public String print() {

		System.out.println("Apretaste el boton!");
		return "";
	}

	public void cambiarContrasenia() {
		FacesMessage message = null;

		if (contrasenia.equals(usuarioSeleccionado.getContrasenia())) {

			if (nuevaContrasenia.equals(nuevaContraseniaConfirmar) && !nuevaContrasenia.equals("")) {

				this.salvarCambios();
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se cambio la contraseña ", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Las contraseñas no coinciden", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			}
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña proporcionada no es la correcta! ","");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}
	}

	public boolean esTutor() {
		return this.usuarioSeleccionado instanceof TutorDTO;
	}

	public boolean esEstudiante() {
		return this.usuarioSeleccionado instanceof EstudianteDTO;
	}

	public boolean esAnalista() {
		return this.usuarioSeleccionado instanceof AnalistaDTO;
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

	public UsuarioDTO getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
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

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getNuevaContrasenia() {
		return nuevaContrasenia;
	}

	public void setNuevaContrasenia(String nuevaContrasenia) {
		this.nuevaContrasenia = nuevaContrasenia;
	}

	public String getNuevaContraseniaConfirmar() {
		return nuevaContraseniaConfirmar;
	}

	public void setNuevaContraseniaConfirmar(String nuevaContraseniaConfirmar) {
		this.nuevaContraseniaConfirmar = nuevaContraseniaConfirmar;
	}

}
