package com.capa2LogicaNegocio;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

public class Usuario {

	@NotNull
	private Long id;
	
	@NotNull
	private Integer documento;

	@NotNull
	@Length(min=4, max=50)
	private String usuario;

	@NotNull
	@Length(min=8, max=16)
	private String contrasenia;

	@NotNull
	@Length(min=2, max=50)
	private String apellidos;

	@NotNull
	@Length(min=2, max=50)
	private String nombres;

	private Date fechaNacimiento;

	private String departamento;
	private String genero;
	private String localidad;

	@NotNull
	@Email
	private String mail;

	private String mailPersonal;
	private String telefono;
	private String itr;
	private String rol;

	@NotNull
	private boolean activo;

	public Usuario() {
		super();
	}

	public Usuario(Long id, Integer documento, String usuario, String contrasenia, String apellidos, String nombres, Date fechaNacimiento, String departamento, String genero, String localidad, String mail, String mailPersonal, String telefono, String itr, String rol, boolean activo) {
		this.id = id;
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
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getItr() {
        return itr;
    }

    public void setItr(String itr) {
        this.itr = itr;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
	
        
        
        
}