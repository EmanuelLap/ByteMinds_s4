package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa3Persistencia.dao.UsuariosDAO;
import com.capa3Persistencia.entities.UsuarioEntity;
import com.capa3Persistencia.exception.PersistenciaException;



@Stateless
@LocalBean
public class GestionUsuarioService implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	UsuariosDAO usuariosPersistenciaDAO;
	
	

	public Usuario fromUsuarioEntity(UsuarioEntity e) {
		Usuario usuario=new Usuario();
		usuario.setId(e.getId().longValue());
		usuario.setDocumento(e.getDocumento());
		usuario.setApellidos(e.getApellidos());
		usuario.setNombres(e.getNombres());
		usuario.setUsuario(e.getUsuario());
		usuario.setContrasenia(e.getContrasenia());
		usuario.setDepartamento(e.getDepartamento());
		usuario.setFechaNacimiento(e.getFechaNacimiento());
		usuario.setGenero(e.getGenero());
		usuario.setItr(e.getItr());
		usuario.setLocalidad(e.getLocalidad());
		usuario.setMail(e.getMail());
		usuario.setMailPersonal(e.getMailPersonal());
		usuario.setRol(e.getRol());
		usuario.setTelefono(e.getTelefono());
		usuario.setActivo(e.getActivo());
		usuario.setEliminado(e.getEliminado());
		return usuario;
	}
	public UsuarioEntity toUsuarioEntity(Usuario e) {
		UsuarioEntity usuarioEntity=new UsuarioEntity();
		usuarioEntity.setId(e.getId()!=null?e.getId().longValue():null);
		usuarioEntity.setDocumento(e.getDocumento());
		usuarioEntity.setApellidos(e.getApellidos());
		usuarioEntity.setNombres(e.getNombres());
		usuarioEntity.setUsuario(e.getUsuario());
		usuarioEntity.setContrasenia(e.getContrasenia());
		usuarioEntity.setDepartamento(e.getDepartamento());
		usuarioEntity.setFechaNacimiento(e.getFechaNacimiento());
		usuarioEntity.setGenero(e.getGenero());
		usuarioEntity.setItr(e.getItr());
		usuarioEntity.setLocalidad(e.getLocalidad());
		usuarioEntity.setMail(e.getMail());
		usuarioEntity.setMailPersonal(e.getMailPersonal());
		usuarioEntity.setRol(e.getRol());
		usuarioEntity.setTelefono(e.getTelefono());
		usuarioEntity.setActivo(e.getActivo());
		usuarioEntity.setEliminado(e.getEliminado());
		return usuarioEntity;
	}



	
	// servicios para capa de Presentacion

	

	

	public List<Usuario> seleccionarUsuarios() throws PersistenciaException {
		//buscamos todos los  objetos EmpleadoEmpresa
		List<UsuarioEntity> listaUsuariosEmpresa = usuariosPersistenciaDAO.buscarUsuarios();
		
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (UsuarioEntity usuarioEmpresa : listaUsuariosEmpresa) {
			listaUsuarios.add(fromUsuarioEntity(usuarioEmpresa));
		}
		return listaUsuarios;
	}


	public List<Usuario> seleccionarUsuarios(String criterioNombre,String criterioApellido,Integer criterioDocumento,Boolean criterioActivo) throws PersistenciaException {
		//buscamos empleados segun criterio indicado
		List<UsuarioEntity> listaUsuariosEmpresa = usuariosPersistenciaDAO.seleccionarUsuarios(criterioNombre,criterioApellido,criterioDocumento,criterioActivo);
		//lista para devolver la seleccion de empleados
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (UsuarioEntity usuarioEmpresa : listaUsuariosEmpresa) {
			listaUsuarios.add(fromUsuarioEntity(usuarioEmpresa));
		}
		return listaUsuarios;
		
	}
	
	
	public Usuario buscarUsuarioEntity(Long id) {
		UsuarioEntity e = usuariosPersistenciaDAO.buscarUsuario(id);
		return fromUsuarioEntity(e);
	}

	public Usuario buscarUsuario(Long i) {
		UsuarioEntity e = usuariosPersistenciaDAO.buscarUsuario(i);
		return fromUsuarioEntity(e);
	}
	
	public Usuario agregarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException   {
		UsuarioEntity e = usuariosPersistenciaDAO.agregarUsuario(toUsuarioEntity(usuarioSeleccionado));
		return fromUsuarioEntity(e);
	}

	public void actualizarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException   {
		UsuarioEntity e = usuariosPersistenciaDAO.modificarUsuario(toUsuarioEntity(usuarioSeleccionado));
	}
	
	
}
