package com.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;



import com.capa2LogicaNegocio.Empleado;
import com.capa2LogicaNegocio.GestionEmpleadoService;
import com.capa3Persistencia.exception.PersistenciaException;

import sun.misc.Perf.GetPerfAction;

import javax.enterprise.context.SessionScoped;	//JEE8

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;


//@ManagedBean(name="usuario")

@Named(value="gestionEmpleados")		//JEE8
@SessionScoped				        //JEE8
public class GestionEmpleadosBean implements Serializable{
	
	@EJB
	GestionEmpleadoService gestionEmpleadoService;

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
	private String criterioDepartamento;
	private Boolean criterioActivo;
	
	private List<Empleado> empleadosSeleccionados;
	private Empleado empleadoSeleccionado;
	public GestionEmpleadosBean() {
		super();
	}
	
	// ********Acciones****************************
	public String seleccionarEmpleados() throws PersistenciaException {
		empleadosSeleccionados=gestionEmpleadoService.seleccionarEmpleados(criterioNombre, criterioDepartamento, criterioActivo);
		return "";
	}
	
	
	public String verDatosEmpleado() {
		//Navegamos a datos empleado
		return "DatosEmpleado";
	}
	
	// ******** Getters & Setters****************************
	
	public String getCriterioNombre() {
		return criterioNombre;
	}
	public void setCriterioNombre(String criterioNombre) {
		this.criterioNombre = criterioNombre;
	}
	public String getCriterioDepartamento() {
		return criterioDepartamento;
	}
	public void setCriterioDepartamento(String criterioDepartamento) {
		this.criterioDepartamento = criterioDepartamento;
	}
	public Boolean getCriterioActivo() {
		return criterioActivo;
	}
	public void setCriterioActivo(Boolean criterioActivo) {
		this.criterioActivo = criterioActivo;
	}
	public List<Empleado> getEmpleadosSeleccionados() {
		return empleadosSeleccionados;
	}
	public void setEmpleadosSeleccionados(List<Empleado> empleadosSeleccionados) {
		this.empleadosSeleccionados = empleadosSeleccionados;
	}

	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}
	
	
	
	
}
