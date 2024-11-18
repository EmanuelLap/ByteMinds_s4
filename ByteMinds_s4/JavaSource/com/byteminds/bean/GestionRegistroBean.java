package com.byteminds.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionItrService;
import com.byteminds.negocio.GestionRolService;
import com.byteminds.negocio.GestionTipoAreaService;
import com.byteminds.negocio.GestionTipoTutorService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.RolDTO;
import com.byteminds.negocio.TipoAreaDTO;
import com.byteminds.negocio.TipoTutorDTO;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.utils.ExceptionsTools;

@Named(value = "gestionRegistro") // JEE8
@SessionScoped // JEE8
public class GestionRegistroBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Inject
	// PersistenciaBean persistenciaBean;
	@EJB
	GestionUsuarioService gestionUsuarioService;
	GestionItrService gestionItrService;
	GestionRolService gestionRolService;
	GestionTipoTutorService gestionTipoTutorService;
	GestionTipoAreaService gestionTipoAreaService;

	private String u_tipo;
	private Boolean mostrarContrasenia = false;

	private UsuarioDTO usuarioSeleccionado;

	private ItrDTO itrDTOSeleccionado;
	private Integer itrDTOSeleccionadoId;
	private Integer rolDTOSeleccionadoId;

	private RolDTO rolDTOSeleccionado;
	private Integer idRolSeleccionado;// marcar para borrar?

	private TipoTutorDTO tipoTutorDTOSeleccionado;
	private TipoAreaDTO tipoAreaDTOSeleccionado;
	private Integer tipoTutorDTOSeleccionadoId;
	private Integer tipoAreaDTOSeleccionadoId;

	private List<Integer> anosGeneracion;
	private Date fechaMaximaNacimiento;

	private boolean modoEdicion = false;

	public GestionRegistroBean() {
		super();
		gestionItrService = new GestionItrService();
		gestionRolService = new GestionRolService();
		gestionTipoTutorService = new GestionTipoTutorService();
		gestionTipoAreaService = new GestionTipoAreaService();

		itrDTOSeleccionado = new ItrDTO();
		rolDTOSeleccionado = new RolDTO();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -18);

		fechaMaximaNacimiento = cal.getTime();
	}

	@PostConstruct
	public void init() {
		anosGeneracion = new ArrayList<>();
		int añoActual = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 2011; i <= añoActual; i++) {
			anosGeneracion.add(i);
		}

	}

	// se ejecuta antes de desplegar la vista
	public String inicializar() {
		this.itrDTOSeleccionadoId = 0;
		this.rolDTOSeleccionadoId = 0;
		this.tipoAreaDTOSeleccionadoId = 0;
		this.tipoTutorDTOSeleccionadoId = 0;

		if (u_tipo.contentEquals("ESTUDIANTE")) {
			usuarioSeleccionado = new EstudianteDTO();
			this.rolDTOSeleccionadoId = 1;
			rolDTOSeleccionado = gestionRolService.obtenerRolSeleccionado(1);
			this.usuarioSeleccionado.setRol(rolDTOSeleccionado);
		}
		if (u_tipo.contentEquals("ANALISTA")) {
			usuarioSeleccionado = new AnalistaDTO();
			this.rolDTOSeleccionadoId = 2;
			rolDTOSeleccionado = gestionRolService.obtenerRolSeleccionado(2);
			this.usuarioSeleccionado.setRol(rolDTOSeleccionado);
		}
		if (u_tipo.contentEquals("TUTOR")) {
			usuarioSeleccionado = new TutorDTO();
			this.rolDTOSeleccionadoId = 3;
			rolDTOSeleccionado = gestionRolService.obtenerRolSeleccionado(3);
			this.usuarioSeleccionado.setRol(rolDTOSeleccionado);
		}
		usuarioSeleccionado.setUTipo(u_tipo);

//			return "registroweb/registro.xhtml";
		return "registroweb/registro.xhtml?faces-redirect=true";

	}

	public String volver() {
		return "/login.xhtml?faces-redirect=true";
	}

	// Pasar a modo
	public String salvarCambios() {

		if (usuarioSeleccionado != null) {
			usuarioSeleccionado.setActivo(true);
			usuarioSeleccionado.setValidado(false);
//				usuarioSeleccionado.setRol(rolDTOSeleccionado);
//				usuarioSeleccionado.setItr(itrDTOSeleccionado);
			cargarMailAutomaticamente();
			if (validarDatos()) {

				try {
					System.out.println(usuarioSeleccionado.toString());
					System.out.println("------------------------------------------");
					System.out.println(usuarioSeleccionado.getNombres());
					System.out.println(usuarioSeleccionado.getApellidos());
					System.out.println(usuarioSeleccionado.getDocumento());
					System.out.println(usuarioSeleccionado.getGenero());
					System.out.println(usuarioSeleccionado.getFechaNacimiento());
					System.out.println(usuarioSeleccionado.getDepartamento());
					System.out.println(usuarioSeleccionado.getLocalidad());
					System.out.println(usuarioSeleccionado.getUTipo());
					System.out.println(usuarioSeleccionado.getItr().getNombre());
					System.out.println(usuarioSeleccionado.getRol().getNombre());
					System.out.println(usuarioSeleccionado.getTelefono());
					System.out.println(usuarioSeleccionado.getActivo());
					System.out.println(usuarioSeleccionado.getUsuario());
					System.out.println(usuarioSeleccionado.getMail());
					System.out.println(usuarioSeleccionado.getMailPersonal());
					System.out.println(usuarioSeleccionado.getValidado());
					System.out.println("------------------------------------------");
					UsuarioDTO usuarioNuevo;
					usuarioNuevo = (UsuarioDTO) gestionUsuarioService.agregarUsuario(usuarioSeleccionado);
					usuarioSeleccionado = null;

					// mensaje de actualizacion correcta
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se ha agregado un nuevo usuario", "");
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				} catch (PersistenciaException e) {

					Throwable rootException = ExceptionsTools.getCause(e);
					String msg1 = e.getMessage();
					String msg2 = ExceptionsTools.formatedMsg(rootException);
					// mensaje de actualizacion correcta
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);

					e.printStackTrace();
				}

				return null;
			} else {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR AL VALIDAR USUARIO", "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}

		}
		return null;
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

				e.printStackTrace();
			}
		}
		return "";
	}

	public String print() {

		System.out.println("Apretaste el boton!");
		return "";
	}

	public String limpiar() {

		System.out.println("Apretaste el boton LIMPIAR!");
		inicializar();

		System.out.println("Apretaste el boton LIMPIAR! SE LIMPIARON?");

		return "";
	}

	public void cargarMailAutomaticamente() {
		System.out.println("MAIL AUTOMATICO SIN SETEAR: " + this.usuarioSeleccionado.getMail());
		if (this.usuarioSeleccionado.getMail() == null || this.usuarioSeleccionado.getMail().isEmpty()) {
			if (!this.usuarioSeleccionado.getApellidos().isEmpty()
					&& !this.usuarioSeleccionado.getNombres().isEmpty()) {
				String tipoPerfil = this.u_tipo.toLowerCase();
				if (tipoPerfil.equals("TUTOR")) {
					tipoPerfil = tipoPerfil + "e";
				}
				this.usuarioSeleccionado.setMail(this.usuarioSeleccionado.getNombres() + "."
						+ this.usuarioSeleccionado.getApellidos() + "@" + tipoPerfil + "s.utec.edu.uy");
			}
			System.out.println("MAIL AUTOMATICO: " + this.usuarioSeleccionado.getMail());
		}

	}

	public void actualizarITRSeleccionado(AjaxBehaviorEvent event) {
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar un ITR valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {
			itrDTOSeleccionado = gestionItrService.obtenerITRSeleccionado(nuevoValor);
			this.usuarioSeleccionado.setItr(itrDTOSeleccionado);
//	    System.out.println("ITR SETEADO = "+this.usuarioSeleccionado.getItr().getNombre());
		}
	}

	public void actualizarROLSeleccionado(AjaxBehaviorEvent event) {
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar Rol valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {
			rolDTOSeleccionado = gestionRolService.obtenerRolSeleccionado(nuevoValor);
			this.usuarioSeleccionado.setRol(rolDTOSeleccionado);
//	    System.out.println("ROL SETEADO = "+this.usuarioSeleccionado.getRol().getNombre());
		}
	}

	public void actualizarTipoTutorAreaSeleccionado(AjaxBehaviorEvent event) {
		System.out.println("AYAX actualizarTipoTutorAreaSeleccionado");
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe seleccionar tipo de area para tutor valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {
			tipoAreaDTOSeleccionado = gestionTipoAreaService.obtenerTipoAreaPorId(nuevoValor);
			((TutorDTO) this.usuarioSeleccionado).setAreaDTO(tipoAreaDTOSeleccionado);
			System.out
					.println("tipoAreaDTOSeleccionado SETEADO = " + ((TutorDTO) this.usuarioSeleccionado).getAreaDTO());
		}
	}

	public void actualizarTipoTutorSeleccionado(AjaxBehaviorEvent event) {
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe seleccionar tipo de tutor valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {
			tipoTutorDTOSeleccionado = gestionTipoTutorService.obtenerTipoTutorPorId(nuevoValor);
			((TutorDTO) this.usuarioSeleccionado).setTipoDTO(tipoTutorDTOSeleccionado);

//	    System.out.println("ROL SETEADO = "+this.usuarioSeleccionado.getRol().getNombre());
		}
	}

	public Boolean validarDatos() {

		if (this.usuarioSeleccionado.getMail() == null || this.usuarioSeleccionado.getMail().isEmpty()) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe completar nombre y apellido para que se le asigne un correo ", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
		if (this.usuarioSeleccionado.getRol() == null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar Rol ", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
		if (this.usuarioSeleccionado.getItr() == null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar Itr ", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
		if (this.usuarioSeleccionado.getContrasenia() == null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe Ingresar contraseña", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		} else {
			String password = this.usuarioSeleccionado.getContrasenia();
			// Validación de la contraseña con una expresión regular que incluye los
			// requisitos mencionados.
			String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

			if (!password.matches(passwordPattern)) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"La contraseña no cumple con los requisitos: debe tener entre 8 y 20 caracteres, incluir números, mayúsculas, minúsculas y caracteres especiales.",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				return false;
			}
		}

		if (esEstudiante()) {

			if (((EstudianteDTO) this.usuarioSeleccionado).getGeneracion() == null) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Debe seleccionar el año de la generacion", "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				return false;
			}
		}

		if (esTutor()) {
			if (((TutorDTO) this.usuarioSeleccionado).getAreaDTO() == null) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar el tipo de area",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				return false;
			}
			if (((TutorDTO) this.usuarioSeleccionado).getTipoDTO() == null) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Debe seleccionar el tipo de tutor", "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				return false;
			}
		}

		return true;
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

	public ItrDTO getItrDTOSeleccionado() {
		return itrDTOSeleccionado;
	}

	public void setItrDTOSeleccionado(ItrDTO itrDTOSeleccionado) {
		this.itrDTOSeleccionado = itrDTOSeleccionado;
	}

	public Integer getItrDTOSeleccionadoId() {
		return itrDTOSeleccionadoId;
	}

	public void setItrDTOSeleccionadoId(Integer itrDTOSeleccionadoId) {
		this.itrDTOSeleccionadoId = itrDTOSeleccionadoId;
	}

	public String getU_tipo() {
		return u_tipo;
	}

	public void setU_tipo(String u_tipo) {
		this.u_tipo = u_tipo;
	}

	public Integer getRolDTOSeleccionadoId() {
		return rolDTOSeleccionadoId;
	}

	public void setRolDTOSeleccionadoId(Integer rolDTOSeleccionadoId) {
		this.rolDTOSeleccionadoId = rolDTOSeleccionadoId;
	}

	public Integer getIdRolSeleccionado() {
		return idRolSeleccionado;
	}

	public void setIdRolSeleccionado(Integer idRolSeleccionado) {
		this.idRolSeleccionado = idRolSeleccionado;
	}

	public List<Integer> getAnosGeneracion() {
		return anosGeneracion;
	}

	public void setAnosGeneracion(List<Integer> anosGeneracion) {
		this.anosGeneracion = anosGeneracion;
	}

	public TipoTutorDTO getTipoTutorDTOSeleccionado() {
		return tipoTutorDTOSeleccionado;
	}

	public void setTipoTutorDTOSeleccionado(TipoTutorDTO tipoTutorDTOSeleccionado) {
		this.tipoTutorDTOSeleccionado = tipoTutorDTOSeleccionado;
	}

	public TipoAreaDTO getTipoAreaDTOSeleccionado() {
		return tipoAreaDTOSeleccionado;
	}

	public void setTipoAreaDTOSeleccionado(TipoAreaDTO tipoAreaDTOSeleccionado) {
		this.tipoAreaDTOSeleccionado = tipoAreaDTOSeleccionado;
	}

	public Integer getTipoTutorDTOSeleccionadoId() {
		return tipoTutorDTOSeleccionadoId;
	}

	public void setTipoTutorDTOSeleccionadoId(Integer tipoTutorDTOSeleccionadoId) {
		this.tipoTutorDTOSeleccionadoId = tipoTutorDTOSeleccionadoId;
	}

	public Integer getTipoAreaDTOSeleccionadoId() {
		return tipoAreaDTOSeleccionadoId;
	}

	public void setTipoAreaDTOSeleccionadoId(Integer tipoAreaDTOSeleccionadoId) {
		this.tipoAreaDTOSeleccionadoId = tipoAreaDTOSeleccionadoId;
	}

	public Date getFechaMaximaNacimiento() {
		return fechaMaximaNacimiento;
	}

	public void setFechaMaximaNacimiento(Date fechaMaximaNacimiento) {
		this.fechaMaximaNacimiento = fechaMaximaNacimiento;
	}

	public Boolean getMostrarContrasenia() {
		return mostrarContrasenia;
	}

	public void setMostrarContrasenia(Boolean mostrarContrasenia) {
		this.mostrarContrasenia = mostrarContrasenia;
	}

}
