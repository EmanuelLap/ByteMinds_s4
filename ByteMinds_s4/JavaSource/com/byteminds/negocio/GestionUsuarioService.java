package com.byteminds.negocio;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.Estudiante;
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

	public UsuarioDTO fromUsuario(Usuario usuarioEntidad) {
		UsuarioDTO usuarioDTO = null;// = new UsuarioDTO();
		GestionItrService gItr = new GestionItrService();
		GestionRolService gRol = new GestionRolService();
		GestionTipoAreaService gTa = new GestionTipoAreaService();
		GestionTipoTutorService gTT = new GestionTipoTutorService();

		if (usuarioEntidad instanceof Analista) {
//			System.out.println("Es instancia de ANALISTA -->"+usuarioEntidad.getUTtipo());
			if (usuarioEntidad.getUTtipo().equals("ANALISTA")) {
				usuarioDTO = new AnalistaDTO();
			}
		}
		if (usuarioEntidad instanceof Estudiante) {
//			System.out.println("Es instancia de ESTUDIANTE -->"+usuarioEntidad.getUTtipo());
			if (usuarioEntidad.getUTtipo().equals("ESTUDIANTE")) {
				usuarioDTO = new EstudianteDTO();
				if (((Estudiante) usuarioEntidad).getGeneracion() != null) {
					((EstudianteDTO) usuarioDTO).setGeneracion(((Estudiante) usuarioEntidad).getGeneracion());
				}
			}
		}
		if (usuarioEntidad instanceof Tutor) {
//			System.out.println("Es instancia de TUTOR -->"+usuarioEntidad.getUTtipo());
			if (usuarioEntidad.getUTtipo().equals("TUTOR")) {
				usuarioDTO = new TutorDTO();
				if (((Tutor) usuarioEntidad).getTipo() != null) {
					((TutorDTO) usuarioDTO).setTipoDTO(gTT.fromTipoTutorTipo(((Tutor) usuarioEntidad).getTipo()));
				}
				if (((Tutor) usuarioEntidad).getArea() != null) {
					((TutorDTO) usuarioDTO).setAreaDTO(gTa.fromTipoArea(((Tutor) usuarioEntidad).getArea()));
				}
			}
		}

		usuarioDTO.setId(usuarioEntidad.getId());
		usuarioDTO.setDocumento(usuarioEntidad.getDocumento());
		usuarioDTO.setApellidos(usuarioEntidad.getApellidos());
		usuarioDTO.setNombres(usuarioEntidad.getNombres());
		usuarioDTO.setUsuario(usuarioEntidad.getUsuario());
		usuarioDTO.setContrasenia(usuarioEntidad.getContrasenia());
		usuarioDTO.setDepartamento(usuarioEntidad.getDepartamento());
		usuarioDTO.setFechaNacimiento(usuarioEntidad.getFechaNacimiento());
		usuarioDTO.setGenero(usuarioEntidad.getGenero());
		usuarioDTO.setLocalidad(usuarioEntidad.getLocalidad());
		usuarioDTO.setMail(usuarioEntidad.getMail());
		usuarioDTO.setMailPersonal(usuarioEntidad.getMailPersonal());

		usuarioDTO.setItr(gItr.fromITR(usuarioEntidad.getItr()));
//		System.out.println("usuarioEntidad.getRol() "+usuarioEntidad.getRol().getNombre());
		usuarioDTO.setRol(gRol.fromRol(usuarioEntidad.getRol()));

		usuarioDTO.setTelefono(usuarioEntidad.getTelefono());
		usuarioDTO.setActivo(usuarioEntidad.getActivo());
		usuarioDTO.setValidado(usuarioEntidad.getValidado());
		usuarioDTO.setUTipo(usuarioEntidad.getUTtipo());

//		printReflextion(e);

		return usuarioDTO;
	}

	public void printReflextion(Object obj) {

		// Obtener la clase de la instancia
		Class<?> clase = obj.getClass();
		System.out.println("----------------------------------------");
		System.out.println("Imprimiendo datos de la clase: " + obj.getClass());
		// Obtener todos los campos de la clase
		Field[] campos = clase.getDeclaredFields();

		// Iterar sobre los campos y mostrar sus nombres y valores
		for (Field campo : campos) {
			campo.setAccessible(true); // Esto permite acceder a campos privados
			try {

				try {
					Object valor = campo.get(obj);
					System.out.println("Nombre del campo: " + campo.getName() + ", Valor: " + valor);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			} catch (Exception eLaxy) {
				// eLaxy.printStackTrace();
			}
		}
		System.out.println("----------------------------------------\n");

	}

	public Usuario toUsuario(UsuarioDTO userDTO) {
		Usuario usuario = null;
		GestionItrService gItr = new GestionItrService();
		GestionRolService gRol = new GestionRolService();
		GestionTipoAreaService gTa = new GestionTipoAreaService();
		GestionTipoTutorService gTT = new GestionTipoTutorService();

		if (userDTO.getUTipo().equals("ANALISTA")) {
			usuario = new Analista();
		}
		if (userDTO.getUTipo().equals("TUTOR")) {
			usuario = new Tutor();
			((Tutor) usuario).setTipo(gTT.toTipoTutorTipo((((TutorDTO) userDTO).getTipoDTO())));
			((Tutor) usuario).setArea(gTa.toTipoArea((((TutorDTO) userDTO).getAreaDTO())));
		}
		if (userDTO.getUTipo().equals("ESTUDIANTE")) {
			usuario = new Estudiante();
			((Estudiante) usuario).setGeneracion((((EstudianteDTO) userDTO).getGeneracion()));

		}

		usuario.setId(userDTO.getId());
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

		usuario.setUTipo(userDTO.getUTipo());

		usuario.setItr(gItr.toITR(userDTO.getItr()));
		usuario.setRol(gRol.toRol(userDTO.getRol()));

		usuario.setTelefono(userDTO.getTelefono());
		usuario.setActivo(userDTO.getActivo());
		usuario.setValidado(userDTO.getValidado());
		return usuario;
	}

	// servicios para capa de Presentacion

	public List<UsuarioDTO> seleccionarUsuarios() throws PersistenciaException {
		// buscamos todos los objetos 
		List<Usuario> listaUsuarios = ejbRemoto.listarUsuarios();

		List<UsuarioDTO> listaUsuariosDTO = new ArrayList<UsuarioDTO>();
		// recorremos(haciendo la
		// conversion requerida)
		for (Usuario usuario : listaUsuarios) {
			listaUsuariosDTO.add(fromUsuario(usuario));
		}
		return listaUsuariosDTO;
	}

	public List<UsuarioDTO> seleccionarUsuarios(String tipo, String id, String depto, String criterioDocumento,
			String criterioNombre, String criterioApellido, String mail, String usuario, String itrNombre,
			String generacion, Boolean validado, Boolean activo, Boolean todos, String localidad, String telefono,
			Boolean noValidados, Boolean noActivos) throws PersistenciaException {

		String documento = "";
		System.out.println(String.valueOf(criterioDocumento));
		if (String.valueOf(criterioDocumento).equals("null")) {

		} else {
			documento = String.valueOf(criterioDocumento);
		}

		List<Usuario> listaUsuariosBackend = ejbRemoto.buscarUsuarioPor(tipo, id, depto, documento, criterioNombre,
				criterioApellido, mail, usuario, itrNombre, generacion, validado, activo, todos, localidad, telefono,
				noValidados, noActivos);
//		String tipo, String id ,String depto,String doc,String nombre,String apellido
//		,String mail,String usuario,String itrNombre,String generacion, Boolean validado ,Boolean activo,Boolean todos,String localidad
//		,String telefono,Boolean noValidados ,Boolean noActivos){
//		
		// lista para devolver la seleccion de empleados
		List<UsuarioDTO> listaUsuariosDTO = new ArrayList<UsuarioDTO>();
		// recorremos listaEmpleadosEmpresa y vamos populando listaEmpleado (haciendo la
		// conversion requerida)
		for (Usuario usuarioEntidad : listaUsuariosBackend) {
			listaUsuariosDTO.add(fromUsuario(usuarioEntidad));
		}
		return listaUsuariosDTO;

	}

	public UsuarioDTO buscarUsuario(Integer id) {
		Usuario e = ejbRemoto.encontrarUsuario(id);
		return fromUsuario(e);
	}

//	public UsuarioDTO buscarUsuario(Integer i) {
//		Usuario e = ejbRemoto.encontrarUsuario(i);
//		return fromUsuario(e);
//	}

	public List<TutorDTO> listadoDeTutores() {
		List<TutorDTO> listadoTutoresDTO = new ArrayList<TutorDTO>();
		List<Tutor> listadoTutores = new ArrayList<Tutor>();

		listadoTutores = ejbRemoto.listarTutores();

		for (Tutor tut : listadoTutores) {
			TutorDTO tutorcito = (TutorDTO) this.fromUsuario(tut);
			listadoTutoresDTO.add(tutorcito);
		}

		return listadoTutoresDTO;
	}

	public List<TutorDTO> listadoDeTutoresActivos() {
		List<TutorDTO> listadoTutoresDTO = new ArrayList<TutorDTO>();
		List<Tutor> listadoTutores = new ArrayList<Tutor>();

		listadoTutores = ejbRemoto.listarTutoresActivos();

		for (Tutor tut : listadoTutores) {
			TutorDTO tutorcito = (TutorDTO) this.fromUsuario(tut);
			listadoTutoresDTO.add(tutorcito);
		}

		return listadoTutoresDTO;
	}

	public UsuarioDTO agregarUsuario(UsuarioDTO usuarioSeleccionado) throws PersistenciaException {
		Usuario e = ejbRemoto.crearUsuario(toUsuario(usuarioSeleccionado));
		return fromUsuario(e);
	}

	public void actualizarUsuario(UsuarioDTO usuarioSeleccionado) throws PersistenciaException {
		Usuario e = ejbRemoto.modificarUsuario(toUsuario(usuarioSeleccionado));
	}

	public UsuarioDTO buscarUsuarioPorDocumento(String documento, String tipo) {
		UsuarioDTO usuario = null;
		try {
			if ((documento != null || documento != "") && (tipo != null || tipo != "")
					&& (tipo.equals("ESTUDIANTE") || tipo.equals("ANALISTA") || tipo.equals("TUTOR"))) {

				List<UsuarioDTO> listaUsuarios = seleccionarUsuarios(tipo, null, null, documento, null, null, null,
						null, null, null, null, null, null, null, null, null, null);
				if (!listaUsuarios.isEmpty()) {
					usuario = listaUsuarios.get(0);
				}

			}
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usuario;
	}

	public UsuarioDTO login(String username, String password) {
		Usuario user = null;
		try {

			user = ejbRemoto.login(username, password);
			if (user != null && user.getId() != null) {
				return fromUsuario(user);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
