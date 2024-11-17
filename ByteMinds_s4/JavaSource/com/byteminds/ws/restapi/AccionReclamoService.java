package com.byteminds.ws.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.negocio.GestionReclamoService;
import com.byteminds.negocio.GestionAccionReclamoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.AccionReclamoDTO;
import com.byteminds.negocio.AnalistaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/accion_reclamos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AccionReclamoService {
	
	    GestionAccionReclamoService gestionAccionReclamoService = new GestionAccionReclamoService();

	    @POST
	    @Path("/agregarAccionAReclamoJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearAccionAReclamo(AccionReclamoDTO acAreclamo) {
	    	System.out.println("Ejecutando servicio rest agregarJson!");
	        if (acAreclamo.getDetalle() == null || acAreclamo.getFecha() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Fecha y/o detalle son campos obligatorios").build();
	        }
	        
	        try {
				gestionAccionReclamoService.agregarAccionAReclamoDTO(acAreclamo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(acAreclamo).build();
	    }
	    
	    
	    
	    @POST
	    @Path("/modificarAccionAReclamoJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response modificarAccionReclamo(AccionReclamoDTO acAreclamo) {
	    	System.out.println("Ejecutando servicio rest modificar AccionReclamo!");
	    	if (acAreclamo.getDetalle() == null || acAreclamo.getFecha() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Fecha y/o detalle son campos obligatorios").build();
	        }
	        
	        try {
				gestionAccionReclamoService.modificarAccionAReclamoDTO(acAreclamo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(acAreclamo).build();
	    }
	    
	    

	    @GET
	    @Path("/listarAccionesAReclamo")
	    public String listAccionReclamos(@QueryParam("id") int id) {
	    	System.out.println("Ingresando al servicio rest a mandar lista de acciones sobre reclamos");
	    	List<AccionReclamoDTO> listaDeAccionReclamos =gestionAccionReclamoService.listarAccionAReclamoDTO(id);
	    	if(listaDeAccionReclamos == null || listaDeAccionReclamos.isEmpty()) {
	    		System.out.println("Lista de acciones a reclamos vacia");
	    		listaDeAccionReclamos = new ArrayList<AccionReclamoDTO>();
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeAccionReclamos);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
	    
	    
	    
	    @GET
	    @Path("/obtenerEjemploJson")
	    public String obtenerEjemplo() {
			GestionReclamoService gRS = new GestionReclamoService();
			GestionUsuarioService gUS = new GestionUsuarioService();
			AccionReclamoDTO accionreclamo = new AccionReclamoDTO();
			// Configura los valores del reclamo como desees
			accionreclamo.setId(1);
			accionreclamo.setActivo(true);
			accionreclamo.setDetalle("Detalle de accion sobre reclamo");
			accionreclamo.setAnalistaId((AnalistaDTO)gUS.buscarUsuario(16));
			accionreclamo.setFecha(new Date(System.currentTimeMillis()));
			accionreclamo.setReclamoId(gRS.buscarReclamo(1));
			
			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(accionreclamo);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(json);
	        return json;
	    }
	    
	}