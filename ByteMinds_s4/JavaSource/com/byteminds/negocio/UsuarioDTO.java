package com.byteminds.negocio;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = TutorDTO.class, name = "TUTOR"),
		@JsonSubTypes.Type(value = AnalistaDTO.class, name = "ANALISTA"),
		@JsonSubTypes.Type(value = EstudianteDTO.class, name = "ESTUDIANTE") })
public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer id;

	@NotNull(message = "El documento de identidad es obligatorio")
//	@Size(min = 6, max = 8, message = "El documento de identidad debe tener entre 6 y 8 caracteres")
	@Min(value = 99999, message = "El documento de identidad debe tener entre 6 y 8 caracteres")
	@Max(value = 99999999, message = "El documento de identidad debe tener entre 6 y 8 caracteres")
	private Integer documento;

	@NotNull(message = "Ingrese un nombre de usuario minimo 4 caracteres")
	@Size(min = 4, max = 45, message = "El nombre de usuario debe tener entre 4 y 50 caracteres")
	private String usuario;

	@NotNull(message = "La contraseña es obligatoria")
	@Size(min = 8, max = 16, message = "La contraseña debe tener entre 8 y 16 caracteres")
	private String contrasenia;

	@NotNull (message = "El apellido es obligatorio")
	@Size(min = 2, max = 45, message = "El apellido debe tener entre 2 y 45 caracteres")
	private String apellidos;

	@NotNull (message = "El nombre es obligatorio")
	@Size(min = 2, max = 45, message = "El nombre debe tener entre 2 y 45 caracteres")
	private String nombres;

	@NotNull (message = "La fecha de nacimiento es obligatoria")
	private Date fechaNacimiento;

	private String departamento;
	private String genero;
	private String localidad;

	@NotNull(message = "El correo electronico es obligatorio")
	@Email
	private String mail;

	private String mailPersonal;

	@NotNull (message = "El telefono es obligatorio")
	@Size(min = 4, max = 20, message = "El telefono debe tener entre 4 y 20 caracteres")
	private String telefono;
	private ItrDTO itr;
	private RolDTO rol;

	@NotNull
	private boolean activo;

	@NotNull
	private boolean validado;

	private String uTipo;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Integer documento, String usuario, String contrasenia, String apellidos, String nombres,
			Date fechaNacimiento, String departamento, String genero, String localidad, String mail,
			String mailPersonal, String telefono, ItrDTO itr, RolDTO rol, boolean activo, boolean validado) {

		this.documento = documento;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.apellidos = apellidos;
		this.nombres = nombres;
		this.fechaNacimiento = fechaNacimiento;
		this.departamento = departamento;
		this.genero = genero;
		this.localidad = localidad;
		this.mail = mail;
		this.mailPersonal = mailPersonal;
		this.telefono = telefono;
		this.itr = itr;
		this.rol = rol;
		this.activo = activo;
		this.validado = validado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDocumento() {
		return documento;
	}

	public void setDocumento(Integer documento) {
		this.documento = documento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailPersonal() {
		return mailPersonal;
	}

	public void setMailPersonal(String mailPersonal) {
		this.mailPersonal = mailPersonal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public ItrDTO getItr() {
		return itr;
	}

	public void setItr(ItrDTO itr) {
		this.itr = itr;
	}

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public String getUTipo() {
		return uTipo;
	}

	public void setUTipo(String uTipo) {
		this.uTipo = uTipo;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean getValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}
	
	public String toStringRestablecer() {
		return this.usuario + "," + this.mail;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    UsuarioDTO other = (UsuarioDTO) obj;
	    return this.id != null && this.id.equals(other.id);
	}


}