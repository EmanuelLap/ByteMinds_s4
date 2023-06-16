package com.capa3Persistencia.dao;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.capa3Persistencia.entities.EmpleadoEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean


public class EmpleadosEmpresaDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public EmpleadosEmpresaDAO() {
		super();
	}

	
	public EmpleadoEmpresa agregarEmpleado(EmpleadoEmpresa empleado) throws PersistenciaException {

		try {
			EmpleadoEmpresa empleadoEmpresa = em.merge(empleado);
			em.flush();
			return empleadoEmpresa;
		} 
		catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar el empleado." + e.getMessage(), e);
		}
		finally {
			
		}
	}

	public EmpleadoEmpresa borrarEmpleado(EmpleadoEmpresa empleado) throws PersistenciaException {

		EmpleadoEmpresa empleadoEmpresa = em.find(EmpleadoEmpresa.class, empleado.getId());
		if (empleadoEmpresa == null) {
			throw new PersistenciaException("No existe el empleado a borrar. Id=" + empleado.getId());
		}
		try {
		empleadoEmpresa = em.merge(empleado);
		em.flush();
		return empleadoEmpresa;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el empleado. Id=" + empleado.getId());
		}
	}
	
	public EmpleadoEmpresa modificarEmpleado(EmpleadoEmpresa empleado) throws PersistenciaException {

		try {
			EmpleadoEmpresa empleadoEmpresa = em.merge(empleado);
			em.flush();
			return empleadoEmpresa;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el empleado." + e.getMessage(), e);
		}
	}

	public EmpleadoEmpresa buscarEmpleado(Long id) {
		EmpleadoEmpresa empleadoEmpresa = em.find(EmpleadoEmpresa.class, id);
		return empleadoEmpresa;
	}

	public List<EmpleadoEmpresa> buscarEmpleados() throws PersistenciaException {
		try {
		
		String query= 	"Select e from EmpleadoEmpresa e";
		List<EmpleadoEmpresa> resultList = (List<EmpleadoEmpresa>) em.createQuery(query,EmpleadoEmpresa.class)
							 .getResultList();
		return  resultList;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
		}
		
	}


	public List<EmpleadoEmpresa> seleccionarEmpleados(String criterioNombre,
			String criterioDepartamento, Boolean criterioActivo) throws PersistenciaException {
		try {
			
			String query= 	"Select e from EmpleadoEmpresa e  ";
			String queryCriterio="";
			if (criterioNombre!=null && ! criterioNombre.contentEquals("")) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+ " e.nombre like '%"+criterioNombre +"%' ";
			} 
			if (criterioDepartamento!=null && ! criterioDepartamento.equals("")) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.departamento='"+criterioDepartamento+"'  " ;
			}
			/*
			if (criterioActivo!=null) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.activo  " ;
			}*/
			if (!queryCriterio.contentEquals("")) {
				query+=" where "+queryCriterio;
			}
			List<EmpleadoEmpresa> resultList = (List<EmpleadoEmpresa>) em.createQuery(query,EmpleadoEmpresa.class)
								 .getResultList();
			return  resultList;
			}catch(PersistenceException e) {
				throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
			}
	}

	
	
}
