package com.byteminds.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
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
import javax.validation.ConstraintViolationException;

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
	GestionItrService gestionItrService;
	GestionRolService gestionRolService;
	GestionTipoTutorService gestionTipoTutorService;
	GestionTipoAreaService gestionTipoAreaService;

	private Integer id;
	private String modalidad;
	private String u_tipo;

	private String contrasenia;
	private String nuevaContrasenia;
	private String nuevaContraseniaConfirmar;

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

	public GestionUsuarioBean() {
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
	public void preRenderViewListener() {
	    if (FacesContext.getCurrentInstance().isPostback()) {
	        return;
	    }
		if (id != null) {
			usuarioSeleccionado = gestionUsuarioService.buscarUsuario(id);
			this.itrDTOSeleccionado = usuarioSeleccionado.getItr();
			System.out.println("preRenderViewListener itrDTOSeleccionado: " + itrDTOSeleccionado.getNombre());
			this.rolDTOSeleccionadoId = usuarioSeleccionado.getRol().getId();
			if (esTutor()) {
				this.tipoAreaDTOSeleccionadoId = ((TutorDTO) (usuarioSeleccionado)).getTipoDTO().getId();
				this.tipoTutorDTOSeleccionadoId = ((TutorDTO) (usuarioSeleccionado)).getAreaDTO().getId();

			}
		} else {
			if (u_tipo.contentEquals("ESTUDIANTE") && usuarioSeleccionado == null) {
				usuarioSeleccionado = new EstudianteDTO();
			}
			if (u_tipo.contentEquals("ANALISTA") && usuarioSeleccionado == null) {
				usuarioSeleccionado = new AnalistaDTO();
			}
			if (u_tipo.contentEquals("TUTOR") && usuarioSeleccionado == null) {
				usuarioSeleccionado = new TutorDTO();
			}
			usuarioSeleccionado.setUTipo(u_tipo);
			// Solo cargar cuando es nuevo
			if (usuarioSeleccionado == null) {
				this.itrDTOSeleccionadoId = 0;
				this.rolDTOSeleccionadoId = 0;
				this.tipoAreaDTOSeleccionadoId = 0;
				this.tipoTutorDTOSeleccionadoId = 0;
			}
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
		if (validarDatos()) {
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
		if (usuarioSeleccionado.getId() == null) {
			usuarioSeleccionado.setActivo(true);
			usuarioSeleccionado.setValidado(false);
//				usuarioSeleccionado.setRol(rolDTOSeleccionado);
//				usuarioSeleccionado.setItr(itrDTOSeleccionado);
			

				try {
					
					UsuarioDTO usuarioNuevo;
					usuarioNuevo = (UsuarioDTO) gestionUsuarioService.agregarUsuario(usuarioSeleccionado);
					usuarioSeleccionado = null;
					this.id = usuarioNuevo.getId();

					// mensaje de actualizacion correcta
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se ha agregado un nuevo usuario", "");
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);

					this.modalidad = "view";

//			} catch (PersistenciaException e) {
//
//				Throwable rootException = ExceptionsTools.getCause(e);
//				String msg1 = e.getMessage();
//				String msg2 = ExceptionsTools.formatedMsg(rootException);
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "update";
//
//				e.printStackTrace();
//			}
				} catch (Exception e) {
					Throwable rootException = ExceptionsTools.getCause(e);
					System.out.println("Root Exception Class: " + rootException.getClass().getName());
					System.out.println("Root Exception Message: " + rootException.getMessage());

					String msg1 = "Error inesperado.";
					String msg2 = ExceptionsTools.formatedMsg(rootException);

					if (rootException instanceof ConstraintViolationException
							|| rootException instanceof SQLIntegrityConstraintViolationException) {
						msg1 = "Error: El dato que quiere ingresar ya existe.";
						msg2 = "Por favor, verifique la información ingresada.";
					}

					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);

					this.modalidad = "update";

					e.printStackTrace();
				}
			
			return null;
		} else if (modalidad.equals("update") || modalidad.equals("edit")) {

			try {
				gestionUsuarioService.actualizarUsuario(usuarioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el usuario.", ""));

//			} catch (PersistenciaException e) {

//				Throwable rootException = ExceptionsTools.getCause(e);
//				String msg1 = e.getMessage();
//				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "update";
//
//				e.printStackTrace();
//			}
			} catch (Exception e) {
				Throwable rootException = ExceptionsTools.getCause(e);
				System.out.println("Root Exception Class: " + rootException.getClass().getName());
				System.out.println("Root Exception Message: " + rootException.getMessage());

				String msg1 = "Error inesperado.";
				String msg2 = ExceptionsTools.formatedMsg(rootException);

				if (rootException instanceof ConstraintViolationException
						|| rootException instanceof SQLIntegrityConstraintViolationException) {
					msg1 = "Error: El dato que quiere ingresar ya existe.";
					msg2 = "Por favor, verifique la información ingresada.";
				}

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}
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
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha desactivado el usuario.", ""));

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

	public String activarUsuario() {

		if (usuarioSeleccionado.getId() != null) {
			usuarioSeleccionado.setActivo(true);
			try {
				gestionUsuarioService.actualizarUsuario(usuarioSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha activado el usuario.", ""));

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

				String password = nuevaContrasenia;
				// Validación de la contraseña con una expresión regular que incluye los
				// requisitos mencionados.
				String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

				if (!password.matches(passwordPattern)) {
					message = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"La contraseña no cumple con los requisitos: debe tener entre 8 y 20 caracteres, incluir números, mayúsculas, minúsculas y caracteres especiales.",
							"");
					FacesContext.getCurrentInstance().addMessage(null, message);
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

				} else {
					usuarioSeleccionado.setContrasenia(nuevaContrasenia);
					this.salvarCambios();
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se cambio la contraseña ", "");
					FacesContext.getCurrentInstance().addMessage(null, message);
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				}
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Las contraseñas no coinciden", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			}
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La contraseña proporcionada no es la correcta! ",
					"");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		}
	}

//	public void actualizarITRSeleccionado(AjaxBehaviorEvent event) {
//		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
//		if (nuevoValor == -1) {
//			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar un ITR valido", "");
//			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//		} else {
//			itrDTOSeleccionado = gestionItrService.obtenerITRSeleccionado(nuevoValor);
//			this.usuarioSeleccionado.setItr(itrDTOSeleccionado);
////	    System.out.println("ITR SETEADO = "+this.usuarioSeleccionado.getItr().getNombre());
//		}
//	}
	public void actualizarITRSeleccionado(AjaxBehaviorEvent event) {
		itrDTOSeleccionado = (ItrDTO) ((UIOutput) event.getSource()).getValue();
		if (itrDTOSeleccionado == null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar un ITR valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {
			
			this.usuarioSeleccionado.setItr(itrDTOSeleccionado);
	    System.out.println("ITR SETEADO = "+this.usuarioSeleccionado.getItr().getNombre());
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
		Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
		if (nuevoValor == -1) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe seleccionar tipo de area para tutor valido", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		} else {
			tipoAreaDTOSeleccionado = gestionTipoAreaService.obtenerTipoAreaPorId(nuevoValor);
			((TutorDTO) this.usuarioSeleccionado).setAreaDTO(tipoAreaDTOSeleccionado);
//	    System.out.println("ROL SETEADO = "+this.usuarioSeleccionado.getRol().getNombre());
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

		if (this.usuarioSeleccionado.getDocumento() == null || 
			    !(this.usuarioSeleccionado.getDocumento() >= 1_000_000 && 
			      this.usuarioSeleccionado.getDocumento() <= 99_999_999)) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Debe completar documento de identidad con un formato valido ", "");
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
			// Validación de la contraseña con expresión regular
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

	public String irACambiarContrasena() {
		return "/pages/CambiarContrasena.xhtml?id=" + usuarioSeleccionado.getId()
				+ "&modalidad=edit&faces-redirect=true";
	}

	public String irPerfil() {
		return "/pages/VerPerfil.xhtml?faces-redirect=true";
	}

//	
//	public void validarMailInstitucional(String patron) {
//		if (txtEmailInstitucional.getText() != null && !txtEmailInstitucional.getText().isEmpty()) {
//			if (!txtEmailInstitucional.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + patron + "$")) {
//				lblEmailValido.setVisible(true);
//				lblEmailValido.setText("Invalido!");
//				lblEmailValido.setForeground(Color.RED);
//				emailInstValido = false;
//			} else {
//				lblEmailValido.setVisible(true);
//				lblEmailValido.setText("Valido!");
//				lblEmailValido.setForeground(Color.GREEN);
//				String email = txtEmailInstitucional.getText();
//				email = email.replaceAll("@" + patron, "");
//				txtUsuario.setText(email);
//				emailInstValido = true;
//
//			}
//		}
//	}
//	public void validarContrasenia() {
//		if (!txtPass.getText().matches(PASSWORD_PATTERN)) {
//			lblpassValido.setVisible(true);
//			lblpassValido.setText("Invalido!");
//			lblpassValido.setForeground(Color.RED);
//			passValido = false;
//		} else {
//			lblpassValido.setVisible(true);
//			lblpassValido.setText("Valido!");
//			lblpassValido.setForeground(Color.GREEN);
//			passValido = true;
//
//		}
//	}
//	
//	public void validarContraseniasIguales() {
//		
//		if(!txtRepetirPass.getText().equals(txtPass.getText())) {
//			lblpassCoincide.setVisible(true);
//			lblpassCoincide.setText("No coinciden!");
//			lblpassCoincide.setForeground(Color.RED);
//			passCoincideValido = false;
//		} else {
//			lblpassCoincide.setVisible(true);
//			lblpassCoincide.setText("Coinciden!");
//			lblpassCoincide.setForeground(Color.GREEN);
//			passCoincideValido = true;
//
//		}
//	}
//	
//	public void validarMail() {
//		if (!txtEmail.getText().matches(EMAIL_VALIDO)) {
//			lblEmailPersonalValido.setVisible(true);
//			lblEmailPersonalValido.setText("Invalido!");
//			lblEmailPersonalValido.setForeground(Color.RED);
//			emailValido = false;
//		} else {
//			lblEmailPersonalValido.setVisible(true);
//			lblEmailPersonalValido.setText("Valido!");
//			lblEmailPersonalValido.setForeground(Color.GREEN);
//			emailValido = true;
//
//		}
//	}
//	
//	
//	public void validarEmailPerIguales() {
//
//		if(!txtEmail.getText().equals(txtRepetirEmail.getText())) {
//			lblemailPersonalCoincide.setVisible(true);
//			lblemailPersonalCoincide.setText("No coinciden!");
//			lblemailPersonalCoincide.setForeground(Color.RED);
//			emailPerCoincideValido = false;
//		} else {
//			lblemailPersonalCoincide.setVisible(true);
//			lblemailPersonalCoincide.setText("Coinciden!");
//			lblemailPersonalCoincide.setForeground(Color.GREEN);
//			emailPerCoincideValido = true;
//
//		}
//	}

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
}
