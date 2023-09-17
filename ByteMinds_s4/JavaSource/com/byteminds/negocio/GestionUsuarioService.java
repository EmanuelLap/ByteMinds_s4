package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.remoto.EJBUsuarioRemoto;

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
		UsuarioDTO usuarioDTO = null;// = new UsuarioDTO();
		GestionItrService gItr = new GestionItrService();
		GestionRolService gRol = new GestionRolService();
		GestionTipoAreaService gTa = new GestionTipoAreaService();
		GestionTipoTutorService gTT = new GestionTipoTutorService();
		
		if (e instanceof Analista) {
			usuarioDTO = new AnalistaDTO();
		}
		if (e instanceof Estudiante) {
//			
			usuarioDTO = new EstudianteDTO();
			((EstudianteDTO)usuarioDTO).setGeneracion(((Estudiante)e).getGeneracion());
		}
		if (e instanceof Tutor) {
			usuarioDTO = new TutorDTO();
			((TutorDTO)usuarioDTO).setTipoDTO(gTT.fromTipoTutorTipo(((Tutor)e).getTipo()));
			((TutorDTO)usuarioDTO).setAreaDTO(gTa.fromTipoArea(((Tutor)e).getArea()));
		}



		usuarioDTO.setId(e.getId());
		usuarioDTO.setDocumento(e.getDocumento());
		usuarioDTO.setApellidos(e.getApellidos());
		usuarioDTO.setNombres(e.getNombres());
		usuarioDTO.setUsuario(e.getUsuario());
		usuarioDTO.setContrasenia(e.getContrasenia());
		usuarioDTO.setDepartamento(e.getDepartamento());
		usuarioDTO.setFechaNacimiento(e.getFechaNacimiento());
		usuarioDTO.setGenero(e.getGenero());
		usuarioDTO.setLocalidad(e.getLocalidad());
		usuarioDTO.setMail(e.getMail());
		usuarioDTO.setMailPersonal(e.getMailPersonal());

		usuarioDTO.setItr(gItr.fromITR(e.getItr()));
		usuarioDTO.setRol(gRol.fromRol(e.getRol()));

		usuarioDTO.setTelefono(e.getTelefono());
		usuarioDTO.setActivo(e.getActivo());
		usuarioDTO.setValidado(e.getValidado());
		usuarioDTO.setUTipo(e.getUTtipo());


		return usuarioDTO;
	}

	public Usuario toUsuario(UsuarioDTO userDTO) {
		Usuario usuario = null;
		GestionItrService gItr = new GestionItrService();
		GestionRolService gRol = new GestionRolService();

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

		usuario.setItr(gItr.toITR(userDTO.getItr()));
		usuario.setRol(gRol.toRol(userDTO.getRol()));

		usuario.setTelefono(userDTO.getTelefono());
		usuario.setActivo(userDTO.getActivo());
		usuario.setValidado(userDTO.getValidado());
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

	public List<UsuarioDTO> seleccionarUsuarios(String tipo, String id ,String depto,String criterioDocumento,String criterioNombre,String criterioApellido
			,String mail,String usuario,String itrNombre,String generacion, Boolean validado ,Boolean activo,Boolean todos,String localidad
			,String telefono,Boolean noValidados ,Boolean noActivos) throws PersistenciaException {

		String documento = "";
		System.out.println(String.valueOf(criterioDocumento));
		if (String.valueOf(criterioDocumento).equals("null")) {

		} else {
			documento = String.valueOf(criterioDocumento);
		}

		List<Usuario> listaUsuariosEmpresa = ejbRemoto.buscarUsuarioPor(tipo, id, depto, documento, criterioNombre,criterioApellido,
				mail, usuario, itrNombre, generacion, validado, activo, todos, localidad,
				telefono, noValidados, noActivos);
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
