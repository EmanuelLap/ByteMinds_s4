package com.byteminds.ws.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.bean.GestionUsuarioBean;
import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.UsuarioDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioService {
	
	    GestionUsuarioService gestionUsuarioService = new GestionUsuarioService();

	    @POST
	    @Path("/agregarJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearUsuario(UsuarioDTO usuario) {
	    	System.out.println("Ejecutando servicio rest agregarJson!");
	        if (usuario.getNombres() == null || usuario.getApellidos() == null
	        		|| usuario.getDocumento() == null || usuario.getUsuario() == null
	        		 || usuario.getContrasenia() == null  || usuario.getUsuario() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Campos obligatorios requeridos para continuar").build();
	        }
	        
	        try {
				gestionUsuarioService.agregarUsuario(usuario);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(usuario).build();
	    }
	    
	    
	    @POST
	    @Path("/modificarUsuarioJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response modificarUsuario(UsuarioDTO usuario) {
	    	System.out.println("Ejecutando servicio rest modificar Usuario!");
	        if (usuario.getNombres() == null || usuario.getApellidos() == null
	        		|| usuario.getDocumento() == null || usuario.getUsuario() == null
	        		 || usuario.getContrasenia() == null  || usuario.getUsuario() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Campos obligatorios requeridos para continuar").build();
	        }
	        
	        try {
				gestionUsuarioService.actualizarUsuario(usuario);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(usuario).build();
	    }

	    
	    @POST
	    @Path("/eliminarUsuarioJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response eliminarUsuario(UsuarioDTO usuario) {
	    	System.out.println("Ejecutando servicio rest eliminar Usuario!");
	        if (usuario.getNombres() == null || usuario.getApellidos() == null
	        		|| usuario.getDocumento() == null || usuario.getUsuario() == null
	        		 || usuario.getContrasenia() == null  || usuario.getUsuario() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Campos obligatorios requeridos para continuar").build();
	        }
	        try {
	        	usuario.setActivo(false);
				gestionUsuarioService.actualizarUsuario(usuario);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(usuario).build();
	    }


	    @GET
	    @Path("/listar")
	    public String listUsuarios() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de usuarios");
	    	List<UsuarioDTO> listaDeUsuarios = null;
			try {
				listaDeUsuarios = gestionUsuarioService.seleccionarUsuarios();
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(listaDeUsuarios == null || listaDeUsuarios.isEmpty()) {
	    		System.out.println("Lista de usuarios vacia");
	    		listaDeUsuarios = new ArrayList<UsuarioDTO>();
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeUsuarios);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
	    
	    @GET
	    @Path("/listarTutoresActivos")
	    public String listUsuariosTutores() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de usuarios TUTORES");
	    	List<TutorDTO> listaDeUsuariosTutores = null;
	    	List<UsuarioDTO> listaDeUsuarios = null;
			try {
				listaDeUsuariosTutores = gestionUsuarioService.listadoDeTutoresActivos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(listaDeUsuariosTutores == null || listaDeUsuariosTutores.isEmpty()) {
	    		System.out.println("Lista de usuarios tutores vacia");
	    		listaDeUsuariosTutores = new ArrayList<TutorDTO>();
	    	}else {
	    		listaDeUsuarios = new ArrayList<UsuarioDTO>();
	    		listaDeUsuarios.addAll(listaDeUsuariosTutores);
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeUsuarios);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
	    
	    @GET
	    @Path("/obtenerUsuarioById")
	    public String obtenerUsuarioById(@QueryParam("id") int id) {
			GestionEventoService gES = new GestionEventoService();
			GestionUsuarioService gUS = new GestionUsuarioService();
			System.out.println("Ingresando al servicio rest obtenerUsuarioById");
			System.out.println("Integer :" +id);
			UsuarioDTO usuario ;
			// Configura los valores del usuario como desees
			
			usuario=gestionUsuarioService.buscarUsuario(id);
			
			
			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(usuario);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(json);
	        return json;
	    }
	    
	    
	    @GET
	    @Path("/obtenerEjemploJson")
	    public String obtenerEjemplo() {
			GestionEventoService gES = new GestionEventoService();
			GestionUsuarioService gUS = new GestionUsuarioService();
			UsuarioDTO usuario ;
			// Configura los valores del usuario como desees
			
			usuario=gestionUsuarioService.buscarUsuario(1);
			
			
			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(usuario);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(json);
	        return json;
	    }
	    
	    
	    
	    @POST
	    @Path("/agregarTutorJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearUsuarioTutor(TutorDTO usuario) {
	    	System.out.println("Ejecutando servicio rest agregarJson!");
	        if (usuario.getNombres() == null || usuario.getApellidos() == null
	        		|| usuario.getDocumento() == null || usuario.getUsuario() == null
	        		 || usuario.getContrasenia() == null  || usuario.getUsuario() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Campos obligatorios requeridos para continuar").build();
	        }
	        
	        try {
				gestionUsuarioService.agregarUsuario(usuario);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(usuario).build();
	    }
	    
	    
	    @POST
	    @Path("/agregarEstudianteJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearUsuarioEstudiante(EstudianteDTO usuario) {
	    	System.out.println("Ejecutando servicio rest agregarJson!");
	        if (usuario.getNombres() == null || usuario.getApellidos() == null
	        		|| usuario.getDocumento() == null || usuario.getUsuario() == null
	        		 || usuario.getContrasenia() == null  || usuario.getUsuario() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Campos obligatorios requeridos para continuar").build();
	        }
	        
	        try {
				gestionUsuarioService.agregarUsuario(usuario);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(usuario).build();
	    }
	    
	    
	    @POST
	    @Path("/agregarAnalistaJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearUsuarioAnalista(AnalistaDTO usuario) {
	    	System.out.println("Ejecutando servicio rest agregarJson!");
	        if (usuario.getNombres() == null || usuario.getApellidos() == null
	        		|| usuario.getDocumento() == null || usuario.getUsuario() == null
	        		 || usuario.getContrasenia() == null  || usuario.getUsuario() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Campos obligatorios requeridos para continuar").build();
	        }
	        
	        try {
				gestionUsuarioService.agregarUsuario(usuario);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(usuario).build();
	    }
	    
	    
	    
	}

