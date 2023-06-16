package com.capa3Persistencia.dao;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.capa3Persistencia.entities.UsuarioEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean


public class UsuariosEmpresaDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public UsuariosEmpresaDAO() {
		super();
	}

	
	public UsuarioEmpresa agregarUsuario(UsuarioEmpresa usuario) throws PersistenciaException {

		try {
			UsuarioEmpresa usuarioEmpresa = em.merge(usuario);
			em.flush();
			return usuarioEmpresa;
		} 
		catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar el usuario." + e.getMessage(), e);
		}
		finally {
			
		}
	}

	public UsuarioEmpresa borrarUsuario(UsuarioEmpresa usuario) throws PersistenciaException {

		UsuarioEmpresa usuarioEmpresa = em.find(UsuarioEmpresa.class, usuario.getId());
		if (usuarioEmpresa == null) {
			throw new PersistenciaException("No existe el usuario a borrar. Id=" + usuario.getId());
		}
		try {
		usuarioEmpresa = em.merge(usuario);
		em.flush();
		return usuarioEmpresa;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el usuario. Id=" + usuario.getId());
		}
	}
	
	public UsuarioEmpresa modificarUsuario(UsuarioEmpresa usuario) throws PersistenciaException {

		try {
			UsuarioEmpresa usuarioEmpresa = em.merge(usuario);
			em.flush();
			return usuarioEmpresa;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el usuario." + e.getMessage(), e);
		}
	}

	public UsuarioEmpresa buscarUsuario(Long id) {
		UsuarioEmpresa usuarioEmpresa = em.find(UsuarioEmpresa.class, id);
		return usuarioEmpresa;
	}

	public List<UsuarioEmpresa> buscarUsuarios() throws PersistenciaException {
		try {
		
		String query= 	"Select e from UsuarioEmpresa e";
		List<UsuarioEmpresa> resultList = (List<UsuarioEmpresa>) em.createQuery(query,UsuarioEmpresa.class)
							 .getResultList();
		return  resultList;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
		}
		
	}


	public List<UsuarioEmpresa> seleccionarUsuarios(String criterioNombre,
			String criterioDepartamento, Boolean criterioActivo) throws PersistenciaException {
		try {
			
			String query= 	"Select e from UsuarioEmpresa e  ";
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
			List<UsuarioEmpresa> resultList = (List<UsuarioEmpresa>) em.createQuery(query,UsuarioEmpresa.class)
								 .getResultList();
			return  resultList;
			}catch(PersistenceException e) {
				throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
			}
	}

	
	
}
