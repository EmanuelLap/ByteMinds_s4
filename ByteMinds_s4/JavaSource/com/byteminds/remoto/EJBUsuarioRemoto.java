package com.byteminds.remoto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;


import tecnofenix.entidades.*;
import tecnofenix.exception.*;
import tecnofenix.interfaces.*;



public class EJBUsuarioRemoto {

	private static String CONEXION_CLIENTE_EJB = "ejb:/TecnoFenixEJB/ConexionClienteJNDI!tecnofenix.interfaces.ConexionClienteJNDIRemote";
	private static String RUTA_USUARIO_EJB = "ejb:/TecnoFenixEJB/UsuarioBean!tecnofenix.interfaces.UsuarioBeanRemote";
	private static String RUTA_USUARIO_ESTUDIANTE_EJB = "ejb:/TecnoFenixEJB/EstudianteBean!tecnofenix.interfaces.EstudianteBeanRemote";
	private static String RUTA_USUARIO_TUTOR_EJB = "ejb:/TecnoFenixEJB/TutorBean!tecnofenix.interfaces.TutorBeanRemote";
	private static String RUTA_USUARIO_ANALISTA_EJB = "ejb:/TecnoFenixEJB/AnalistaBean!tecnofenix.interfaces.AnalistaBeanRemote";
	private static String RUTA_ITR_EJB = "ejb:/TecnoFenixEJB/ItrBean!tecnofenix.interfaces.ItrBeanRemote";
	private static String RUTA_ACCION_CONSTANCIA_EJB = "ejb:/TecnoFenixEJB/AccionConstanciaBean!tecnofenix.interfaces.AccionConstanciaBeanRemote";
	private static String RUTA_ACCION_JUSTIFICACION_EJB = "ejb:/TecnoFenixEJB/AccionJustificacionBean!tecnofenix.interfaces.AccionJustificacionBeanRemote";
	private static String RUTA_ACCION_RECLAMO_EJB = "ejb:/TecnoFenixEJB/AccionReclamoBean!tecnofenix.interfaces.AccionReclamoBeanRemote";
	private static String RUTA_CONSTANCIA_EJB = "ejb:/TecnoFenixEJB/ConstanciaBean!tecnofenix.interfaces.ConstanciaBeanRemote";
	private static String RUTA_CONVOC_ASIST_EVEN_ESTUDIANTE_EJB = "ejb:/TecnoFenixEJB/ConvocatoriaAsistenciaEventoEstudianteBean!tecnofenix.interfaces.ConvocatoriaAsistenciaEventoEstudianteBeanRemote";
	private static String RUTA_EVENTO_EJB = "ejb:/TecnoFenixEJB/EventoBean!tecnofenix.interfaces.EventoBeanRemote";
	private static String RUTA_GESTION_EVENTO_ANALISTA_EJB = "ejb:/TecnoFenixEJB/GestionEventoAnalistaBean!tecnofenix.interfaces.GestionEventoAnalistaBeanRemote";
	private static String RUTA_JUSTIFICACION_EJB = "ejb:/TecnoFenixEJB/JustificacionBean!tecnofenix.interfaces.JustificacionBeanRemote";
	private static String RUTA_RECLAMO_EJB = "ejb:/TecnoFenixEJB/ReclamoBean!tecnofenix.interfaces.ReclamoBeanRemote";
	private static String RUTA_TUTOR_RESPONSABLE_EVENTO_EJB = "ejb:/TecnoFenixEJB/TutorResponsableEventoBean!tecnofenix.interfaces.TutorResponsableEventoBeanRemote";
	private static String RUTA_ROL_EJB = "ejb:/TecnoFenixEJB/RolBean!tecnofenix.interfaces.RolBeanRemote";
	private static String RUTA_FUNCIONALIDAD_EJB = "ejb:/TecnoFenixEJB/FuncionalidadBean!tecnofenix.interfaces.FuncionalidadBeanRemote";

	
	
	ConexionClienteJNDIRemote claseRemota;
	
	UsuarioBeanRemote usuarioRemote;
	
	EstudianteBeanRemote estudianteRemote;
	
	ItrBeanRemote itrRemote;
	
	AccionConstanciaBeanRemote accionConstanciaBeanRemote;
	
	AccionJustificacionBeanRemote accionJustificacionBeanRemote;
	
	AccionReclamoBeanRemote accionReclamoBeanRemote;
	
	AnalistaBeanRemote analistaBeanRemote;
	
	ConstanciaBeanRemote constanciaBeanRemote;
	
	ConvocatoriaAsistenciaEventoEstudianteBeanRemote convocatoriaAsistenciaEventoEstudianteBeanRemote;
	
	EventoBeanRemote eventoBeanRemote;
	
	GestionEventoAnalistaBeanRemote gestionEventoAnalistaBeanRemote;
	
	JustificacionBeanRemote justificacionBeanRemote;
	
	ReclamoBeanRemote reclamoBeanRemote;
	
	TutorBeanRemote tutorBeanRemote;
	
	TutorResponsableEventoBeanRemote tutorResponsableEventoBeanRemote;
	
	RolBeanRemote rolBeanRemote;
	
	FuncionalidadBeanRemote funcionalidadBeanRemote;
	
	public EJBUsuarioRemoto() {
		try {
			InitialContext ctx = new InitialContext();
			// Instanciamos las interfaces remotas con el lookup
			claseRemota = (ConexionClienteJNDIRemote) ctx.lookup(CONEXION_CLIENTE_EJB);
			usuarioRemote = (UsuarioBeanRemote) ctx.lookup(RUTA_USUARIO_EJB);
			estudianteRemote = (EstudianteBeanRemote) ctx.lookup(RUTA_USUARIO_ESTUDIANTE_EJB);
			tutorBeanRemote = (TutorBeanRemote) ctx.lookup(RUTA_USUARIO_TUTOR_EJB);
			analistaBeanRemote = (AnalistaBeanRemote) ctx.lookup(RUTA_USUARIO_ANALISTA_EJB);
			itrRemote = (ItrBeanRemote) ctx.lookup(RUTA_ITR_EJB);
			accionConstanciaBeanRemote = (AccionConstanciaBeanRemote) ctx.lookup(RUTA_ACCION_CONSTANCIA_EJB);
			accionJustificacionBeanRemote = (AccionJustificacionBeanRemote) ctx.lookup(RUTA_ACCION_JUSTIFICACION_EJB);
			accionReclamoBeanRemote = (AccionReclamoBeanRemote) ctx.lookup(RUTA_ACCION_RECLAMO_EJB);
			constanciaBeanRemote = (ConstanciaBeanRemote) ctx.lookup(RUTA_CONSTANCIA_EJB);
			convocatoriaAsistenciaEventoEstudianteBeanRemote = (ConvocatoriaAsistenciaEventoEstudianteBeanRemote) ctx.lookup(RUTA_CONVOC_ASIST_EVEN_ESTUDIANTE_EJB);
			eventoBeanRemote = (EventoBeanRemote) ctx.lookup(RUTA_EVENTO_EJB);
			gestionEventoAnalistaBeanRemote = (GestionEventoAnalistaBeanRemote) ctx.lookup(RUTA_GESTION_EVENTO_ANALISTA_EJB);
			justificacionBeanRemote = (JustificacionBeanRemote) ctx.lookup(RUTA_JUSTIFICACION_EJB);
			reclamoBeanRemote = (ReclamoBeanRemote) ctx.lookup(RUTA_RECLAMO_EJB);
			tutorResponsableEventoBeanRemote = (TutorResponsableEventoBeanRemote) ctx.lookup(RUTA_TUTOR_RESPONSABLE_EVENTO_EJB);
			rolBeanRemote = (RolBeanRemote) ctx.lookup(RUTA_ROL_EJB);
			funcionalidadBeanRemote = (FuncionalidadBeanRemote) ctx.lookup(RUTA_FUNCIONALIDAD_EJB);
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ejecutarMetodo() {
		System.out.println(claseRemota.levantando());

	}
	public Boolean ping() {
		Boolean status = false;
		try {
			status=claseRemota.ping();
		} catch (Exception e) {
			System.out.println("Server sin conexion...");
		}
		
		if(status) {
			System.out.println("Server status [ON]");
		}else {
			System.out.println("Server status [OFF]");
		}
		return status;
	}
	
	public Usuario login(String usu, String pass) {
		Usuario logeado = null;
		System.out.println("Verificando Login");
		try {
			logeado =  usuarioRemote.login(usu, pass);
		} catch (Exception e) {
			System.err.println(e);
		}

		System.out.println("Usuario RETORNADO");
		return logeado;
	}

	public Usuario login(Usuario usuario) {

		System.out.println("Verificando Login");
		try {
//			usuario=usuarioRemote.login(usuario);
		} catch (Exception e) {
			System.err.println(e);
		}

		System.out.println("Usuario RETORNADO");
		return usuario;
	}

	public Usuario crearUsuario(Usuario usuario) {

		try {
			System.out.println("LEVANTANDOOO USUARIO");
			usuario = usuarioRemote.crearUsuario(usuario);

		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Usuario RETORNADO");
		return usuario;
	}
	public Usuario modificarUsuario(Usuario usuario) {

		try {
			System.out.println("modificarUsuario");
			usuario = usuarioRemote.modificarUsuario(usuario);

		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Usuario RETORNADO");
		return usuario;
	}
	
	public Usuario bajaLogicaUsuario(Usuario usuario) {

		try {
			System.out.println("Borrando Usuario borrado Logico");
			usuario = usuarioRemote.borrarUsuario(usuario);

		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Usuario borrado");
		return usuario;
	}
	
	
	
	
	public List<Usuario> buscarUsuarioPor(String tipo, String id ,String depto,String doc,String nombre,String apellido
			,String mail,String usuario,String itrNombre,String generacion, Boolean validado ,Boolean activo,Boolean todos,String localidad,String telefono,Boolean noValidados ,Boolean noActivos){
		List<Usuario> lista = new ArrayList<Usuario>();
		lista = usuarioRemote.buscarUsuarioPor(tipo, id, depto, doc, nombre, apellido, mail, usuario, itrNombre, generacion,validado,activo,todos,localidad,telefono,noValidados,noActivos);
		return lista;
	}
	
	/*
	 * METODOS ESTUDIANTES REMOTOS
	 */
	public Estudiante crearEstudiante(Estudiante estudiante) {

		try {
			estudiante = estudianteRemote.crearEstudiante(null);
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return estudiante;

	}
	public Estudiante buscarEstudiantePorId(Integer id) {
		Estudiante estudiante = new Estudiante();
		try {
			estudiante = estudianteRemote.buscarEstudiantePorId(id);
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return estudiante;

	}
	public List<Estudiante> listarEstudiantes() {
		List<Estudiante> lista = new ArrayList<Estudiante>();
		try {
			lista = estudianteRemote.listarEstudiantes();
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	/*
	 * METODOS ESTUDIANTES REMOTOS FIN
	 */
	/*
	 * METODOS ITR REMOTOS
	 */

	public Itr crearITR(Itr itr) {
		Itr itrDevueto = new Itr();
		try {
			itrDevueto = itrRemote.crearItr(itr);
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itrDevueto;

	}
	public Itr editarITR(Itr itr) {
		Itr itrDevueto = new Itr();
		try {
			itrDevueto = itrRemote.modificarItr(itr);
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itrDevueto;

	}
	
	public Itr borrarItr(Itr itr) {
		Itr itrDevueto = new Itr();
		try {
			itrDevueto = itrRemote.borrarItr(itr);
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itrDevueto;

	}

	public List<Itr> listarITR() {
		List<Itr> lista = new ArrayList<Itr>();
		try {
			lista = itrRemote.listarItr();
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	public List<Itr> buscarItrPor(String id, String nombre ,String depto) {
		List<Itr> lista = new ArrayList<Itr>();
		try {
			lista = itrRemote.buscarPor(id,nombre,depto);
		} catch (Exception e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
		return lista;
		
	}
	
	public Itr obtenerITRporId(Integer id) {
		Itr itr = new Itr();
		try {
			itr = itrRemote.findById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
		return itr;
		
	}
	
	/*
	 * METODOS ITR REMOTOS FIN
	 */

	public Estudiante modificarEstudiantePropio(Estudiante estudiante) throws ServiciosException, UsuarioNoEncontradoException {
		return estudianteRemote.modificarEstudiantePropio(estudiante);
	}

	public Analista modificarAnalistaPropio(Analista analista) throws ServiciosException, UsuarioNoEncontradoException {
		return analistaBeanRemote.modificarAnalistaPropio(analista);
	}

	public Tutor modificarTutorPropio(Tutor tutor) throws ServiciosException, UsuarioNoEncontradoException {
		return tutorBeanRemote.modificarTutorPropio(tutor);
	}

	public Usuario encontrarUsuario(int id) throws UsuarioNoEncontradoException {
		return usuarioRemote.encontrarUsuario(id);
	}
	
	/*
	 * METODOS ANALISTA REMOTOS
	 */
	public List<Usuario> listarUsuarios() {
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			lista = usuarioRemote.listarUsuariosGeneral();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	
	public List<Estudiante> listarEstudiantesConvocadosEvento(Integer eventoID) {
		try {
			Evento evento = eventoBeanRemote.obtenerEvento(eventoID);
			System.out.println("Evento: " + evento);
			return eventoBeanRemote.obtenerEstudiantesConvocados(evento);
		} catch (Exception e) {
			System.out.println("Error en listar estudiantes convocados: " + e.getMessage());
			e.printStackTrace();
		}
		
		return new ArrayList<Estudiante>();
	}
	
	public ConvocatoriaAsistenciaEventoEstudiante agregarEstudianteAEvento(ConvocatoriaAsistenciaEventoEstudiante convAsisEventEstu) {
		ConvocatoriaAsistenciaEventoEstudiante conv = new ConvocatoriaAsistenciaEventoEstudiante();
		try {
			conv=convocatoriaAsistenciaEventoEstudianteBeanRemote.agregarEstudianteAEvento(convAsisEventEstu);
		} catch (Exception e) {
			System.out.println("Error en agregar convocatoria estudiante evento: " + e.getMessage());
			e.printStackTrace();
		}
		return conv;
	}
	
	public ConvocatoriaAsistenciaEventoEstudiante obtenerDatosConvPorId(Integer id) {
		ConvocatoriaAsistenciaEventoEstudiante conv = new ConvocatoriaAsistenciaEventoEstudiante();
		try {
			conv=convocatoriaAsistenciaEventoEstudianteBeanRemote.obtenerDatosConvPorId(id);
		} catch (Exception e) {
			System.out.println("Error en agregar convocatoria estudiante evento: " + e.getMessage());
			e.printStackTrace();
		}
		return conv;
		
		
		
	}
	
	

	
	public Evento obtenerEvento(Integer id) {
		try {
			return eventoBeanRemote.obtenerEvento(id);
		} catch (Exception e) {
			System.out.println("Error en obtener eventos: " + e.getMessage());
			e.printStackTrace();
		}
		
		return new Evento();
	}	
	public List<Evento> listarEventos() {
		List<Evento> lista = new ArrayList<Evento>();
		try {
			lista= eventoBeanRemote.listarEventos();
		} catch (Exception e) {
			System.out.println("Error en obtener eventos: " + e.getMessage());
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public List<Evento> listarEventosTutor(Usuario tutor) {
		List<Evento> lista = new ArrayList<Evento>();
		try {
			lista= eventoBeanRemote.listarEventosTutor(tutor);
		} catch (Exception e) {
			System.out.println("Error en obtener eventos: " + e.getMessage());
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public Evento crearEvento(Evento evento) {
		Evento eventoNew = new Evento();
		try {
			
			eventoNew=eventoBeanRemote.crearEvento(evento);
		} catch (ServiciosException e) {
			System.out.println("Error al crear evento: " + e.getMessage());
			e.printStackTrace();
			eventoNew = null;
		}
		return eventoNew;
	}
	
	public Evento modificarEvento(Evento evento) {
		Evento eventoNew = new Evento();
		try {
			
			eventoNew=eventoBeanRemote.modificarEvento(evento);
		} catch (ServiciosException e) {
			System.out.println("Error al crear evento: " + e.getMessage());
			e.printStackTrace();
			eventoNew = null;
		}
		return eventoNew;
	}
	
	public List<TipoEvento> listarTipoEvento() {
		List<TipoEvento> list = new ArrayList<TipoEvento>();
		
		list= eventoBeanRemote.listarTipoEvento();
		
		
		return list;
	}
	
	public List<ModalidadEvento> listarModalidadEvento() {
		List<ModalidadEvento> list = new ArrayList<ModalidadEvento>();
		
		list= eventoBeanRemote.listarModalidadEvento();
		
		
		return list;
	}

	/*
	 * METODOS ANALISTA REMOTOS FIN
	 */
	
	

	/*
	 * METODOS ROL REMOTOS INICIO
	 */
	public Rol crearRol(Rol rol) {

		try {
			rol = rolBeanRemote.crearRol(rol);
		} catch (ServiciosException e) {

			e.printStackTrace();
		}
		return rol;
	}
	
	public Rol modificarRol(Rol rol) {

		try {
			rol = rolBeanRemote.modificarRol(rol);
		} catch (ServiciosException e) {

			e.printStackTrace();
		}
		return rol;
	}
	
	public Rol obtenerRolPorId(Integer rolId) {
		Rol rol = new Rol();
		try {
			rol = rolBeanRemote.findById(rolId);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return rol;
	}

	public List<Rol> listarRoles(){
		List<Rol> listado = null;
		try {
			listado = rolBeanRemote.listarRoles();
		} catch (ServiciosException e) {
			
			e.printStackTrace();
		}
		return listado;
	}
	public Rol borrarRol(Rol rol) {

		try {
			rol = rolBeanRemote.borrarRol(rol);
		} catch (ServiciosException e) {

			e.printStackTrace();
		}
		return rol;
	}
	

	
	/*
	 * METODOS ROL REMOTOS FIN
	 */
	
	/*
	 * METODOS FUNCIONALIDAD REMOTOS INICIO
	 */
	
	public List<Funcionalidad> listarFuncionalidades(){
		List<Funcionalidad> listado = null;
		try {
			listado = funcionalidadBeanRemote.listarFuncionalidad();
		} catch (ServiciosException e) {
			
			e.printStackTrace();
		}
		return listado;
	}
	
	public Funcionalidad borrarFuncionalidad(Funcionalidad funcionalidad){
		
		try {
			funcionalidad = funcionalidadBeanRemote.borrarFuncionalidad(funcionalidad);
		} catch (ServiciosException e) {
			
			e.printStackTrace();
		}
		return funcionalidad;
	}
	
	public Funcionalidad crearFuncionalidad(Funcionalidad funcionalidad){
		
		try {
			funcionalidad = funcionalidadBeanRemote.crearFuncionalidad(funcionalidad);
		} catch (ServiciosException e) {
			
			e.printStackTrace();
		}
		return funcionalidad;
	}
	
	public Funcionalidad obtenerFuncionalidadPorId(Integer idFuncionalidad){
		Funcionalidad funcionalidad = new Funcionalidad();
		try {
			funcionalidad = funcionalidadBeanRemote.findById(idFuncionalidad);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return funcionalidad;
	}

	
	/*
	 * METODOS FUNCIONALIDAD REMOTOS FIN
	 */
	
	/*
	 * METODOS TUTORES REMOTOS
	 */
	public List<Tutor> listarTutores() {
		List<Tutor> lista = new ArrayList<Tutor>();
		try {
			lista = tutorBeanRemote.listarTutores();
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	
	public List<Tutor> listarTutoresActivos() {
		List<Tutor> lista = new ArrayList<Tutor>();
		try {
			lista = tutorBeanRemote.listarTutoresActivos();
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	
	public List<Tutor> buscarTutorPor(String id,String ci, String nombre ,String apellido,String tipo ,String area) {
		List<Tutor> lista = new ArrayList<Tutor>();
		try {
			lista = tutorBeanRemote.listarTutores();
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	public Tutor obtenerTutorPorId(Integer tutorId) {
		Tutor tutor = new Tutor();
		try {
			tutor = tutorBeanRemote.obtenerTutorPorId(tutorId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tutor;

	}
	
	/*
	 * METODOS TUTORES REMOTOS FIN
	 */
	
	/*
	 * METODOS TUTORESRESPONSABLEEVENTO REMOTOS
	 */
		public TutorResponsableEvento asignarTutorAEvento(TutorResponsableEvento tutResEvent) {
			TutorResponsableEvento tre = new TutorResponsableEvento();
			try {
				tre = tutorResponsableEventoBeanRemote.modificarTutorResponsableEvento(tutResEvent);
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return tre;
		}
	
		public List<TutorResponsableEvento> obtenerTutoresDeEvento(Integer eventoId) {
			List<TutorResponsableEvento> tRECollection = new ArrayList<TutorResponsableEvento>();
			try {
				tRECollection = tutorResponsableEventoBeanRemote.obtenerTutorResponsableEventoPorId(eventoId);
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return tRECollection;
		}
	/*
	 * METODOS TUTORESRESPONSABLEEVENTO REMOTOS FIN
	 */
		
	/*
	 * METODOS ConvocatoriaAsistenciaEventoEstudiante REMOTOS
	 */
		
		public List<ConvocatoriaAsistenciaEventoEstudiante> listarAllConvocAsistenciaEventEstu() {
			List<ConvocatoriaAsistenciaEventoEstudiante> lista = new ArrayList<ConvocatoriaAsistenciaEventoEstudiante>();
			try {
				lista = convocatoriaAsistenciaEventoEstudianteBeanRemote.obtenerTodos();
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lista;

		}
		public List<ConvocatoriaAsistenciaEventoEstudiante> listarConvocatoriaEventEstuPorEvento(Evento eventoId) {
			List<ConvocatoriaAsistenciaEventoEstudiante> lista = new ArrayList<ConvocatoriaAsistenciaEventoEstudiante>();
			try {
				lista = convocatoriaAsistenciaEventoEstudianteBeanRemote.listarConvocatoriaEventEstuPorEvento(eventoId);
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lista;

		}
		
		
		public List<ConvocatoriaAsistenciaEventoEstudiante> filtrarAsistEstuAEventosPor(String id, String tituloEvento,String nombre,String apellido ,String documento, String valorLogico,String calificacion,String registroAsistencia){
			List<ConvocatoriaAsistenciaEventoEstudiante> lista = new ArrayList<ConvocatoriaAsistenciaEventoEstudiante>();
			try {
				lista = convocatoriaAsistenciaEventoEstudianteBeanRemote.filtrarAsistEstuAEventosPor(id, tituloEvento, nombre, apellido,documento,valorLogico,calificacion, registroAsistencia);
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lista;
			
			
		}
		
		public List<Evento> buscarEventosPor(String id,String titulo){
			 List<Evento> list = new ArrayList<Evento>();	
			try {
				list= eventoBeanRemote.buscarEventosPor(id,titulo);
			} catch (Exception e) {
				System.out.println("Error en obtener eventos: " + e.getMessage());
				e.printStackTrace();
			}
			
			return list;
		}
		
		public List<Evento> buscarEventosPor(String id, String titulo,String localizacion,String modalidad,String tipoEvento,String itrNombre,Date inicioInicio, Date finInicio,Date inicioFin, Date finFin,Boolean activo) {
			
			 List<Evento> list = new ArrayList<Evento>();	
			try {
				list= eventoBeanRemote.buscarEventosPor(id,titulo,localizacion,modalidad, tipoEvento, itrNombre, inicioInicio,  finInicio, inicioFin,  finFin, activo);
			} catch (Exception e) {
				System.out.println("Error en obtener eventos: " + e.getMessage());
				e.printStackTrace();
			}
			
			return list;
		}
			
		
		public List<Estudiante>  buscarEstudiantePor(String ci, String nombre ,String apellido,String generacion ,String itr){
			List<Estudiante> list = new ArrayList<Estudiante>();	
			try {
				List<Usuario> listUsu = new ArrayList<Usuario>();
				System.out.println("Buscando usuario estudiante");
				listUsu = usuarioRemote.buscarUsuarioPor("ESTUDIANTE", null, null, ci, nombre, apellido, null, null, itr, generacion,true,true,true,null,null,false,false);
//				(String tipo, String id, String depto, String doc, String nombre,
//						String apellido, String mail, String usuario, String itrNombre, String generacion, Boolean validado,
//						Boolean activo, Boolean todos,String localidad,String telefono, Boolean noValidados ,Boolean noActivos) throws UsuarioNoEncontradoException {

				if(listUsu!=null && !listUsu.isEmpty()) {
					
					for(Usuario u : listUsu) {
						list.add((Estudiante)u);
	
					}
				}
				
			} catch (Exception e) {
				System.out.println("Error al obtener estudiantes: " + e.getMessage());
				e.printStackTrace();
			}
			
			return list;	
		}
		
		/*
		 * METODOS ConvocatoriaAsistenciaEventoEstudiante REMOTOS FIN
		 */

		
		
		/*
		 * METODOS TipoEstadoEvento REMOTOS 
		 */
		public TipoEstadoEvento crearTipoEstadoEvento(TipoEstadoEvento tEE) {
			TipoEstadoEvento tEEDevueto = new TipoEstadoEvento();
			try {
				tEEDevueto = eventoBeanRemote.crearTipoEstadoEvento(tEE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tEEDevueto;

		}
		public TipoEstadoEvento editarTipoEstadoEvento(TipoEstadoEvento tEE) {
			TipoEstadoEvento tEEDevueto = new TipoEstadoEvento();
			try {
				tEEDevueto = eventoBeanRemote.editarTipoEstadoEvento(tEE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tEEDevueto;

		}
		
	

		public List<TipoEstadoEvento> listarTipoEstadoEvento() {
			List<TipoEstadoEvento> lista = new ArrayList<TipoEstadoEvento>();
			try {
				lista = eventoBeanRemote.listarTipoEstadoEvento();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lista;

		}
		public List<TipoEstadoEvento> buscarTipoEstadoEventoPor(String id, String nombre) {
			List<TipoEstadoEvento> lista = new ArrayList<TipoEstadoEvento>();
			try {
				lista = eventoBeanRemote.buscarTipoEstadoEventoPor(id,nombre);
			} catch (Exception e) {
				// TODO Auto-generated catch blocks
				e.printStackTrace();
			}
			return lista;
			
		}
		
		/*
		 * METODOS TipoEstadoEvento REMOTOS  FIN
		 */
		/*
		 * METODOS TipoTutorTipo REMOTOS
		 */
		


		
				public TipoTutorTipo crearTipoTutorTipo(TipoTutorTipo ttt) {
					TipoTutorTipo tttDevueto = new TipoTutorTipo();
					try {
						tttDevueto = tutorBeanRemote.crearTipoTutorTipo(ttt);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return tttDevueto;

				}
				public TipoTutorTipo editarTipoTutorTipo(TipoTutorTipo ttt) {
					TipoTutorTipo tttDevueto = new TipoTutorTipo();
					try {
						tttDevueto = tutorBeanRemote.modificarTipoTutorTipo(ttt);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return tttDevueto;

				}
				
			

				public List<TipoTutorTipo> listarTipoTutorTipo() {
					List<TipoTutorTipo> lista = new ArrayList<TipoTutorTipo>();
					try {
						lista = tutorBeanRemote.listadoTipoTutorTipo();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return lista;

				}
				public List<TipoTutorTipo> buscarTipoTutorTipoPor(String id, String nombre) {
					List<TipoTutorTipo> lista = new ArrayList<TipoTutorTipo>();
					try {
						lista = tutorBeanRemote.buscarTipoTutorTipoPor(id,nombre);
					} catch (Exception e) {
						// TODO Auto-generated catch blocks
						e.printStackTrace();
					}
					return lista;
					
				}
		
		/*
		 * METODOS TipoTutorTipo REMOTOS  FIN
		 */
		
		/*
		 * METODOS TipoArea REMOTOS
		 */
		
				
	
		public TipoArea crearTipoArea(TipoArea ta) {
			TipoArea taDevueto = new TipoArea();
			try {
				taDevueto = tutorBeanRemote.crearTipoArea(ta);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return taDevueto;
	
		}
		public TipoArea editarTipoArea(TipoArea ta) {
			TipoArea taDevueto = new TipoArea();
			try {
				taDevueto = tutorBeanRemote.modificarTipoArea(ta);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return taDevueto;
	
		}
		
	
	
		public List<TipoArea> listarTipoArea() {
			List<TipoArea> lista = new ArrayList<TipoArea>();
			try {
				lista = tutorBeanRemote.listadoTipoArea();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lista;
	
		}
		public List<TipoArea> buscarTipoAreaPor(String id, String nombre) {
			List<TipoArea> lista = new ArrayList<TipoArea>();
			try {
				lista = tutorBeanRemote.buscarTipoAreaPor(id,nombre);
			} catch (Exception e) {
				// TODO Auto-generated catch blocks
				e.printStackTrace();
			}
			return lista;
			
		}
		
		
		/*
		 * METODOS TipoArea REMOTOS  FIN
		 */
		
		/*
		 * METODOS RECLAMOS REMOTOS
		 */
		
		public Reclamo buscarReclamo(Reclamo id) throws UsuarioNoEncontradoException {
			
			return (Reclamo) reclamoBeanRemote.obtenerReclamoPorAtributo(id);
		}
		
		public Reclamo buscarReclamoPorId(Integer id) throws UsuarioNoEncontradoException {
			
			return (Reclamo) reclamoBeanRemote.buscarReclamoPorId(id);
		}
		
		public Reclamo crearReclamo(Reclamo reclamo) {
			Reclamo reclamoNew = new Reclamo();
			try {
				
				reclamoNew=reclamoBeanRemote.crearReclamo(reclamo);
			} catch (ServiciosException e) {
				System.out.println("Error al crear el reclamo: " + e.getMessage());
				e.printStackTrace();
				reclamoNew = null;
			}
			return reclamoNew;
		}
		
		public Reclamo modificarReclamo(Reclamo reclamo) {
			Reclamo reclamoNew = new Reclamo();
			try {
				
				reclamoNew=reclamoBeanRemote.modificarReclamo(reclamo);
			} catch (ServiciosException e) {
				System.out.println("Error al modificar el reclamo: " + e.getMessage());
				e.printStackTrace();
				reclamoNew = null;
			}
			return reclamoNew;
		}
		
		public List<Reclamo> listarReclamos() {
			List<Reclamo> listReclamo = new ArrayList<Reclamo>();
			listReclamo=reclamoBeanRemote.listarReclamos();
			return listReclamo;
		}
		
		/*
		 * METODOS RECLAMOS REMOTOS FIN
		 */
}
