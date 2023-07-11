package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.remoto.EJBUsuarioRemoto;
import com.capa3Persistencia.exception.PersistenciaException;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorTipo;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.Usuario;

@Stateless
@LocalBean
public class GestionUsuarioService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EJBUsuarioRemoto ejbRemoto;
	
	public GestionUsuarioService() {
		ejbRemoto = new EJBUsuarioRemoto();
	}

	public UsuarioDTO fromUsuario(Usuario e) {
		UsuarioDTO usuario = new UsuarioDTO();
		GestionItrService gItr= new GestionItrService();
		GestionRolService gRol= new GestionRolService();
		
		usuario.setId(e.getId());
		usuario.setDocumento(e.getDocumento());
		usuario.setApellidos(e.getApellidos());
		usuario.setNombres(e.getNombres());
		usuario.setUsuario(e.getUsuario());
		usuario.setContrasenia(e.getContrasenia());
		usuario.setDepartamento(e.getDepartamento());
		usuario.setFechaNacimiento(e.getFechaNacimiento());
		usuario.setGenero(e.getGenero());
		usuario.setLocalidad(e.getLocalidad());
		usuario.setMail(e.getMail());
		usuario.setMailPersonal(e.getMailPersonal());
		
		usuario.setItr(gItr.fromITR(e.getItr()));
		usuario.setRol(gRol.fromRol(e.getRol()));
		
		usuario.setTelefono(e.getTelefono());
		usuario.setActivo(e.getActivo());
//		usuario.setEliminado(e.getEliminado());
		
		
//		 aca hacer los instance off de los otros dto
		return usuario;
	}

	public Usuario toUsuario(UsuarioDTO userDTO) {
		Usuario usuario= null;
		GestionItrService gItr= new GestionItrService();
		GestionRolService gRol= new GestionRolService();
		
		if (userDTO.getUTipo().equals("ANALISTA")) {
			usuario = new Analista();
		}
		if (userDTO.getUTipo().equals("TUTOR")) {
			usuario = new Tutor();
		}
		if (userDTO.getUTipo().equals("ESTUDIANTE")) {
			usuario = new Estudiante();
		}
	
	        
	        
		usuario.setId(userDTO.getId() != null ? userDTO.getId() : null);
		usuario.setDocumento(userDTO.getDocumento());
		usuario.setApellidos(userDTO.getApellidos());
		usuario.setNombres(userDTO.getNombres());
		usuario.setUsuario(userDTO.getUsuario());
		usuario.setContrasenia(userDTO.getContrasenia());
		usuario.setDepartamento(userDTO.getDepartamento());
		usuario.setFechaNacimiento(userDTO.getFechaNacimiento());
		usuario.setGenero(userDTO.getGenero());
		usuario.setLocalidad(userDTO.getLocalidad());
		usuario.setMail(userDTO.getMail());
		usuario.setMailPersonal(userDTO.getMailPersonal());
		
		usuario.setItr( gItr.toITR( userDTO.getItr()) );
		usuario.setRol( gRol.toRol(userDTO.getRol())  );
		
		usuario.setTelefono(userDTO.getTelefono());
		usuario.setActivo(userDTO.getActivo());

		
		return usuario;
	}

	// servicios para capa de Presentacion

	public List<UsuarioDTO> seleccionarUsuarios() throws PersistenciaException {
		// buscamos todos los objetos EmpleadoEmpresa
		List<Usuario> listaUsuarios = ejbRemoto.listarUsuarios();

		List<UsuarioDTO> listaUsuariosDTO = new ArrayList<UsuarioDTO>();
		// recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la
		// conversion requerida)
		for (Usuario usuario : listaUsuarios) {
			listaUsuariosDTO.add(fromUsuario(usuario));
		}
		return listaUsuariosDTO;
	}

	public List<UsuarioDTO> seleccionarUsuarios(String criterioNombre, String criterioApellido,
			Integer criterioDocumento, Boolean criterioActivo) throws PersistenciaException {
		// buscamos empleados segun criterio indicado
		List<Usuario> listaUsuariosEmpresa = 
				ejbRemoto.buscarUsuarioPor("", "",
				"", String.valueOf(criterioDocumento), 
				criterioNombre, criterioApellido, 
				"", "", 
				"", "", true, 
				true, true, "",
				"", true, true);
//		String tipo, String id ,String depto,String doc,String nombre,String apellido
//		,String mail,String usuario,String itrNombre,String generacion, Boolean validado ,Boolean activo,Boolean todos,String localidad
//		,String telefono,Boolean noValidados ,Boolean noActivos){
//		
		// lista para devolver la seleccion de empleados
		List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
		// recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la
		// conversion requerida)
		for (Usuario usuarioEmpresa : listaUsuariosEmpresa) {
			listaUsuarios.add(fromUsuario(usuarioEmpresa));
		}
		return listaUsuarios;

	}

	public UsuarioDTO buscarUsuario(Integer id) {
		Usuario e = ejbRemoto.encontrarUsuario(id);
		return fromUsuario(e);
	}

//	public UsuarioDTO buscarUsuario(Integer i) {
//		Usuario e = ejbRemoto.encontrarUsuario(i);
//		return fromUsuario(e);
//	}

	public UsuarioDTO agregarUsuario(UsuarioDTO usuarioSeleccionado) throws PersistenciaException {
		Usuario e = ejbRemoto.crearUsuario(toUsuario(usuarioSeleccionado));
		return fromUsuario(e);
	}

	public void actualizarUsuario(UsuarioDTO usuarioSeleccionado) throws PersistenciaException {
		Usuario e = ejbRemoto.modificarUsuario(toUsuario(usuarioSeleccionado));
	}

}
