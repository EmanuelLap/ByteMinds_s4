package com.byteminds.ws.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.bean.GestionReclamoBean;
import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionReclamoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ReclamoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/reclamos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReclamoService {
	
	    GestionReclamoService gestionReclamoService = new GestionReclamoService();

	    @POST
	    @Path("/agregarJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearReclamo(ReclamoDTO reclamo) {
	    	System.out.println("Ejecutando servicio rest agregarJson!");
	        if (reclamo.getTitulo() == null || reclamo.getDetalle() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Título y detalle son campos obligatorios").build();
	        }
	        
	        try {
				gestionReclamoService.agregarReclamo(reclamo);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(reclamo).build();
	    }
	    
	    
	    @POST
	    @Path("/modificarReclamoJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response modificarReclamo(ReclamoDTO reclamo) {
	    	System.out.println("Ejecutando servicio rest modificar Reclamo!");
	        if (reclamo.getTitulo() == null || reclamo.getDetalle() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Título y detalle son campos obligatorios").build();
	        }
	        
	        try {
				gestionReclamoService.actualizarReclamo(reclamo);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(reclamo).build();
	    }

	    
	    @POST
	    @Path("/eliminarReclamoJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response eliminarReclamo(ReclamoDTO reclamo) {
	    	System.out.println("Ejecutando servicio rest eliminar Reclamo!");
	        if (reclamo.getTitulo() == null || reclamo.getDetalle() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Título y detalle son campos obligatorios").build();
	        }
	        
	        try {
	        	reclamo.setActivo(false);
				gestionReclamoService.actualizarReclamo(reclamo);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(reclamo).build();
	    }
//	    @POST
//	    @Path("/agregarForm")
//	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	    public Response addReclamoForm(@FormParam("titulo") String titulo, @FormParam("detalle") String detalle) {
//	        if (titulo == null || detalle == null) {
//	            return Response.status(Response.Status.BAD_REQUEST).entity("Título y detalle son campos obligatorios").build();
//	        }
//	        
//	        ReclamoDTO reclamo = new ReclamoDTO();
//	        reclamo.setTitulo(titulo);
//	        reclamo.setDetalle(detalle);
//	        reclamo.setFecha(new Date(System.currentTimeMillis()));
//	        
//	        try {
//				gestionReclamoService.agregarReclamo(reclamo);
//			} catch (PersistenciaException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	        return Response.status(Response.Status.CREATED).entity(reclamo).build();
//	    }

	    @GET
	    @Path("/listar")
	    public String listReclamos() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de reclamos");
	    	List<ReclamoDTO> listaDeReclamos =gestionReclamoService.listarReclamos();
	    	if(listaDeReclamos == null || listaDeReclamos.isEmpty()) {
	    		System.out.println("Lista de reclamos vacia");
	    		listaDeReclamos = new ArrayList<ReclamoDTO>();
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeReclamos);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
	    
	    @GET
	    @Path("/obtenerEjemploJson")
	    public String obtenerEjemplo() {
			GestionEventoService gES = new GestionEventoService();
			GestionUsuarioService gUS = new GestionUsuarioService();
			ReclamoDTO reclamo = new ReclamoDTO();
			// Configura los valores del reclamo como desees
			reclamo.setId(1);
			reclamo.setTitulo("Ejemplo1");
			reclamo.setDetalle("Detalle1");
			reclamo.setEventoId(gES.obtenerEvento(2));
			reclamo.setEstudianteId((EstudianteDTO)gUS.buscarUsuario(117));
			reclamo.setCreditos(10);
			reclamo.setFecha(new Date(System.currentTimeMillis()));
			reclamo.setActivo(true);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(reclamo);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(json);
	        return json;
	    }
	}

