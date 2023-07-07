package com.capa3Persistencia.dao;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.capa3Persistencia.entities.UsuarioEntity;
import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean


public class UsuariosDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public UsuariosDAO() {
		super();
	}

	
	public UsuarioEntity agregarUsuario(UsuarioEntity usuario) throws PersistenciaException {

		try {
			UsuarioEntity usuarioEntity = em.merge(usuario);
			em.flush();
			return usuarioEntity;
		} 
		catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar el usuario." + e.getMessage(), e);
		}
		finally {
			
		}
	}

	public UsuarioEntity borrarUsuario(UsuarioEntity usuario) throws PersistenciaException {

		UsuarioEntity usuarioEntity = em.find(UsuarioEntity.class, usuario.getId());
		if (usuarioEntity == null) {
			throw new PersistenciaException("No existe el usuario a borrar. Id=" + usuario.getId());
		}
		try {
			usuarioEntity = em.merge(usuario);
		em.flush();
		return usuarioEntity;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el usuario. Id=" + usuario.getId());
		}
	}
	
	public UsuarioEntity modificarUsuario(UsuarioEntity usuario) throws PersistenciaException {

		try {
			UsuarioEntity usuarioEntity = em.merge(usuario);
			em.flush();
			return usuarioEntity;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el usuario." + e.getMessage(), e);
		}
	}

	public UsuarioEntity buscarUsuario(Long id) {
		UsuarioEntity usuarioEntity = em.find(UsuarioEntity.class, id);
		return usuarioEntity;
	}

	public List<UsuarioEntity> buscarUsuarios() throws PersistenciaException {
		try {
		
		String query= 	"Select e from UsuarioEntity e";
		List<UsuarioEntity> resultList = (List<UsuarioEntity>) em.createQuery(query,UsuarioEntity.class)
							 .getResultList();
		return  resultList;
		}catch(PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
		}
		
	}


	public List<UsuarioEntity> seleccionarUsuarios(String criterioNombre,
			String criterioApellido,Integer criterioDocumento, Boolean criterioActivo) throws PersistenciaException {
		try {
			
			String query= 	"Select e from UsuarioEntity e  ";
			String queryCriterio="";
			if (criterioNombre!=null && ! criterioNombre.contentEquals("")) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+ " e.nombres like '%"+criterioNombre +"%' ";
			} 
			if (criterioApellido!=null && ! criterioApellido.equals("")) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.apellidos='"+criterioApellido+"'  " ;
			}
			if (criterioDocumento!=null && criterioDocumento >=0 ) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.documento='"+criterioDocumento+"'  " ;
			}
			if (criterioActivo!=null) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.activo  " ;
			}
			
			queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.eliminado =false  " ;
			
			if (!queryCriterio.contentEquals("")) {
				query+=" where "+queryCriterio;
			}
			List<UsuarioEntity> resultList = (List<UsuarioEntity>) em.createQuery(query,UsuarioEntity.class)
								 .getResultList();
			return  resultList;
			}catch(PersistenceException e) {
				throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(),e);
			}
	}

	public UsuarioEntity loginUsuarios(String criterioUsuario, String criterioPasswd) throws PersistenciaException {
		try {
			
			String query= 	"Select e from UsuarioEntity e  ";
			String queryCriterio="";
			if (criterioUsuario!=null && !criterioUsuario.contentEquals("")) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+ " e.usuario = '"+criterioUsuario +"' ";
			} 
			if (criterioPasswd!=null && !criterioPasswd.equals("")) {
				queryCriterio+=(!queryCriterio.isEmpty()?" and ":"")+" e.contrasenia = '"+criterioPasswd+"'  " ;
			}
			if (!queryCriterio.contentEquals("")) {
				query+=" where "+queryCriterio;
			}
			System.out.println("**************************************");
			System.out.println("QUERY "+query);
			System.out.println("**************************************");
			UsuarioEntity result = (UsuarioEntity) em.createQuery(query,UsuarioEntity.class).getSingleResult();
			
		
			return  result;
			
			}catch(PersistenceException e) {
				return new UsuarioEntity();
			}
	}
	
}
