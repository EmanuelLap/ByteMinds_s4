package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa3Persistencia.dao.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.UsuarioEmpresa;
import com.capa3Persistencia.exception.PersistenciaException;



@Stateless
@LocalBean

public class GestionUsuarioService implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	UsuariosEmpresaDAO usuariosPersistenciaDAO;
	
	

	public Usuario fromUsuarioEmpresa(UsuarioEmpresa e) {
		Usuario usuario=new Usuario();
		usuario.setId(e.getId().longValue());
		usuario.setActivo(e.getActivo());
		usuario.setDepartamento(e.getDepartamento());
		usuario.setEdad(e.getEdad());
		usuario.setNombre(e.getNombre());
		usuario.setSalario(e.getSalario());
		return usuario;
	}
	public UsuarioEmpresa toUsuarioEmpresa(Usuario e) {
		UsuarioEmpresa usuarioEmpresa=new UsuarioEmpresa();
		usuarioEmpresa.setId(e.getId()!=null?e.getId().longValue():null);
		usuarioEmpresa.setActivo(e.getActivo());
		usuarioEmpresa.setDepartamento(e.getDepartamento());
		usuarioEmpresa.setEdad(e.getEdad());
		usuarioEmpresa.setNombre(e.getNombre());
		usuarioEmpresa.setSalario(e.getSalario());
		return usuarioEmpresa;
	}


	
	// servicios para capa de Presentacion

	

	

	public List<Usuario> seleccionarUsuarios() throws PersistenciaException {
		//buscamos todos los  objetos EmpleadoEmpresa
		List<UsuarioEmpresa> listaUsuariosEmpresa = usuariosPersistenciaDAO.buscarUsuarios();
		
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (UsuarioEmpresa usuarioEmpresa : listaUsuariosEmpresa) {
			listaUsuarios.add(fromUsuarioEmpresa(usuarioEmpresa));
		}
		return listaUsuarios;
	}


	public List<Usuario> seleccionarUsuarios(String criterioNombre,String criterioDepartamento,Boolean criterioActivo) throws PersistenciaException {
		//buscamos empleados segun criterio indicado
		List<UsuarioEmpresa> listaUsuariosEmpresa = usuariosPersistenciaDAO.seleccionarUsuarios(criterioNombre,criterioDepartamento,criterioActivo);
		//lista para devolver la seleccion de empleados
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		//recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la conversion requerida)
		for (UsuarioEmpresa usuarioEmpresa : listaUsuariosEmpresa) {
			listaUsuarios.add(fromUsuarioEmpresa(usuarioEmpresa));
		}
		return listaUsuarios;
		
	}
	
	
	public Usuario buscarUsuarioEmpresa(Long id) {
		UsuarioEmpresa e = usuariosPersistenciaDAO.buscarUsuario(id);
		return fromUsuarioEmpresa(e);
	}

	public Usuario buscarUsuario(Long i) {
		UsuarioEmpresa e = usuariosPersistenciaDAO.buscarUsuario(i);
		return fromUsuarioEmpresa(e);
	}
	
	public Usuario agregarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException   {
		UsuarioEmpresa e = usuariosPersistenciaDAO.agregarUsuario(toUsuarioEmpresa(usuarioSeleccionado));
		return fromUsuarioEmpresa(e);
	}

	public void actualizarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException   {
		UsuarioEmpresa e = usuariosPersistenciaDAO.modificarUsuario(toUsuarioEmpresa(usuarioSeleccionado));
	}
	
	
}
