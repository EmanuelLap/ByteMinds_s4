package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa3Persistencia.dao.EmpleadosEmpresaDAO;
import com.capa3Persistencia.entities.EmpleadoEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;



@Stateless
@LocalBean

public class GestionEmpleadoService implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	EmpleadosEmpresaDAO empleadosPersistenciaDAO;
	
	

	public Empleado fromEmpleadoEmpresa(EmpleadoEmpresa e) {
		Empleado empleado=new Empleado();
		empleado.setId(e.getId().longValue());
		empleado.setActivo(e.getActivo());
		empleado.setDepartamento(e.getDepartamento());
		empleado.setEdad(e.getEdad());
		empleado.setNombre(e.getNombre());
		empleado.setSalario(e.getSalario());
		return empleado;
	}
	public EmpleadoEmpresa toEmpleadoEmpresa(Empleado e) {
		EmpleadoEmpresa empleadoEmpresa=new EmpleadoEmpresa();
		empleadoEmpresa.setId(e.getId()!=null?e.getId().longValue():null);
		empleadoEmpresa.setActivo(e.getActivo());
		empleadoEmpresa.setDepartamento(e.getDepartamento());
		empleadoEmpresa.setEdad(e.getEdad());
		empleadoEmpresa.setNombre(e.getNombre());
		empleadoEmpresa.setSalario(e.getSalario());
		return empleadoEmpresa;
	}


	
	// servicios para capa de Presentacion

	

	

	public List<Empleado> seleccionarEmpleados() throws PersistenciaException {
		//buscamos todos los  objetos EmpleadoEmpresa
		List<EmpleadoEmpresa> listaEmpleadosEmpresa = empleadosPersistenciaDAO.buscarEmpleados();
		
		List<Empleado> listaEmpleados=new ArrayList<Empleado>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (EmpleadoEmpresa empleadoEmpresa : listaEmpleadosEmpresa) {
			listaEmpleados.add(fromEmpleadoEmpresa(empleadoEmpresa));
		}
		return listaEmpleados;
	}


	public List<Empleado> seleccionarEmpleados(String criterioNombre,String criterioDepartamento,Boolean criterioActivo) throws PersistenciaException {
		//buscamos empleados segun criterio indicado
		List<EmpleadoEmpresa> listaEmpleadosEmpresa = empleadosPersistenciaDAO.seleccionarEmpleados(criterioNombre,criterioDepartamento,criterioActivo);
		//lista para devolver la seleccion de empleados
		List<Empleado> listaEmpleados=new ArrayList<Empleado>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (EmpleadoEmpresa empleadoEmpresa : listaEmpleadosEmpresa) {
			listaEmpleados.add(fromEmpleadoEmpresa(empleadoEmpresa));
		}
		return listaEmpleados;
		
	}
	
	
	public Empleado buscarEmpleadoEmpresa(Long id) {
		EmpleadoEmpresa e = empleadosPersistenciaDAO.buscarEmpleado(id);
		return fromEmpleadoEmpresa(e);
	}

	public Empleado buscarEmpleado(Long i) {
		EmpleadoEmpresa e = empleadosPersistenciaDAO.buscarEmpleado(i);
		return fromEmpleadoEmpresa(e);
	}
	
	public Empleado agregarEmpleado(Empleado empleadoSeleccionado) throws PersistenciaException   {
		EmpleadoEmpresa e = empleadosPersistenciaDAO.agregarEmpleado(toEmpleadoEmpresa(empleadoSeleccionado));
		return fromEmpleadoEmpresa(e);
	}

	public void actualizarEmpleado(Empleado empleadoSeleccionado) throws PersistenciaException   {
		EmpleadoEmpresa e = empleadosPersistenciaDAO.modificarEmpleado(toEmpleadoEmpresa(empleadoSeleccionado));
	}
	
	
}
