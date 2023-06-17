package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name = "USUARIO")
public class UsuarioEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer documento;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String contrasenia;

    private String apellidos;
    private String nombres;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    private String departamento;
    private String genero;
    private String localidad;

    @Column(nullable = false, unique = true)
    private String mail;

    private String mailPersonal;
    private String telefono;
    private String itr;
    private String rol;

    @Column(nullable = false)
    private boolean activo;

    public UsuarioEntity() {
     
    }

    public UsuarioEntity(Integer documento, String usuario, String contrasenia, String apellidos, 
            String nombres, Date fechaNacimiento, String departamento, String genero, String localidad, 
            String mail, String mailPersonal, String telefono, String itr, String rol) {
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" + "id=" + id + ", documento=" + documento + ", usuario=" + usuario + ", contrasenia=" + contrasenia 
                + ", apellidos=" + apellidos + ", nombres=" + nombres + ", fechaNacimiento=" + fechaNacimiento
                + ", departamento=" + departamento + ", genero=" + genero + ", localidad=" + localidad + ", mail=" + mail 
                + ", mailPersonal=" + mailPersonal + ", telefono=" + telefono + ", itr=" + itr + ", rol=" + rol + ", activo=" + activo + '}';
    }

}