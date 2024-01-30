package com.byteminds.ws.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.bean.GestionEventoBean;
import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.ModalidadEventoDTO;
import com.byteminds.negocio.TipoEstadoEventoDTO;
import com.byteminds.negocio.TipoEventoDTO;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.TutorResponsableEventoDTO;
import com.byteminds.negocio.mobile.EventoDTOMobile;
import com.byteminds.negocio.EventoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/eventos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventoService {
	
	    GestionEventoService gestionEventoService = new GestionEventoService();

	    @POST
	    @Path("/agregarJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearEvento(EventoDTO evento) {
	    	System.out.println("Ejecutando servicio rest agregar evento Json!");
	        if (evento.getTitulo() == null || evento.getTipoEvento() == null|| evento.getModalidadEvento() == null|| evento.getInicio() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Título, TipoEvento,ModalidadEvento y inicioEvento son campos obligatorios").build();
	        }
	        
	        try {
				gestionEventoService.agregarEvento(evento);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(evento).build();
	    }
	    
	    @POST
	    @Path("/agregarJsonMobile")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crearEventoMobile(EventoDTOMobile evento) {
	    	System.out.println("Ejecutando servicio rest agregar evento Json!");
	        if (evento.getTitulo() == null || evento.getTipoEvento() == null|| evento.getModalidadEvento() == null|| evento.getInicio() == null) {
	            return Response.status(Response.Status.BAD_REQUEST).entity("Título, TipoEvento,ModalidadEvento y inicioEvento son campos obligatorios").build();
	        }
	        
	        try {
				gestionEventoService.agregarEventoMobile(evento);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(evento).build();
	    }
	    
	    @POST
	    @Path("/modificarEventoJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response modificarEvento(EventoDTO evento) {
	    	System.out.println("Ejecutando servicio rest modificar Evento!");
	    	if (evento.getTitulo() == null || evento.getTipoEvento() == null|| evento.getModalidadEvento() == null|| evento.getInicio() == null) {
		          return Response.status(Response.Status.BAD_REQUEST).entity("Título, TipoEvento,ModalidadEvento y inicioEvento son campos obligatorios").build();
	        }
	        
	        try {
				gestionEventoService.actualizarEvento(evento);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(evento).build();
	    }
	    
	    @POST
	    @Path("/modificarEventoJsonMobile")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response modificarEventoMobile(EventoDTOMobile evento) {
	    	System.out.println("Ejecutando servicio rest modificar Evento!");
	    	if (evento.getTitulo() == null || evento.getTipoEvento() == null|| evento.getModalidadEvento() == null|| evento.getInicio() == null) {
		          return Response.status(Response.Status.BAD_REQUEST).entity("Título, TipoEvento,ModalidadEvento y inicioEvento son campos obligatorios").build();
	        }
	        
	        try {
				gestionEventoService.actualizarEventoMobile(evento);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(evento).build();
	    }
	    
	    @POST
	    @Path("/eliminarEventoJson")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response eliminarEvento(EventoDTO evento) {
	    	System.out.println("Ejecutando servicio rest eliminar Evento!");
	    	if (evento.getTitulo() == null || evento.getTipoEvento() == null|| evento.getModalidadEvento() == null|| evento.getInicio() == null) {
		          return Response.status(Response.Status.BAD_REQUEST).entity("Título, TipoEvento,ModalidadEvento y inicioEvento son campos obligatorios").build();
	        }
	        
	        try {
	        	evento.setBajaLogica(true);//true es 1 es el estado esta dado de baja
				gestionEventoService.actualizarEvento(evento);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(evento).build();
	    }
	    
	    @POST
	    @Path("/eliminarEventoJsonMobile")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response eliminarEvento(EventoDTOMobile evento) {
	    	System.out.println("Ejecutando servicio rest eliminar Evento!");
	    	if (evento.getId() == null ||evento.getTitulo() == null || evento.getTipoEvento() == null|| evento.getModalidadEvento() == null|| evento.getInicio() == null) {
		          return Response.status(Response.Status.BAD_REQUEST).entity("Título, TipoEvento,ModalidadEvento y inicioEvento son campos obligatorios").build();
	        }
	        
	        try {
	        	evento.setBajaLogica(true);//true es 1 es el estado esta dado de baja
				gestionEventoService.actualizarEventoMobile(evento);
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return Response.status(Response.Status.CREATED).entity(evento).build();
	    }

	    @GET
	    @Path("/listar")
	    public String listEventos() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de eventos");
	    	List<EventoDTO> listaDeEventos =gestionEventoService.listarEventosDTO();
	    	if(listaDeEventos == null || listaDeEventos.isEmpty()) {
	    		System.out.println("Lista de eventos vacia");
	    		listaDeEventos = new ArrayList<EventoDTO>();
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeEventos);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
	    
	    @GET
	    @Path("/listarMobile")//TODO: cambiar metodo para pasar la clase para mobile
	    public String listEventosMobile() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de eventos");
	    	List<EventoDTOMobile> listaDeEventos =gestionEventoService.listarEventosDTOMobile();
	    	if(listaDeEventos == null || listaDeEventos.isEmpty()) {
	    		System.out.println("Lista de eventos vacia");
	    		listaDeEventos = new ArrayList<EventoDTOMobile>();
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeEventos);
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
			EventoDTO evento = new EventoDTO();
			// Configura los valores del evento como desees
			evento.setId(1);
			evento.setTitulo("Ejemplo1");
			evento.setBajaLogica(false);
			evento.setFin(new Date(System.currentTimeMillis()));
			evento.setInicio(new Date(System.currentTimeMillis()));
			ItrDTO itr= new ItrDTO();
			itr.setActivo(true);
			itr.setDepartamento("ARTIGAS");
			itr.setId(2);
			itr.setNombre("DURAZNO");
			evento.setItrDTO(itr);
			evento.setLocalizacion("El patio de casa");
			TipoEstadoEventoDTO tee = new TipoEstadoEventoDTO(1, "Finalizado", true);
			evento.setTipoEstadoEventoDTO(tee);
			TipoEventoDTO te= new TipoEventoDTO();
			te.setId(1);
			te.setActivo(true);
			te.setNombre("Eventoooo");
			evento.setTipoEvento(te);
			List<TutorResponsableEventoDTO> listaTutorRE = new ArrayList<TutorResponsableEventoDTO>();
			TutorDTO tdto = new TutorDTO();
			TutorResponsableEventoDTO tre = new TutorResponsableEventoDTO();
			tre.setEventoId(1);
			tre.setId(1);
			tre.setTutorId(tdto);
			listaTutorRE.add(tre);
			listaTutorRE.add(tre);
			evento.setTutorResponsableEventoDTOCollection(listaTutorRE);
			evento.setModalidadEvento(new ModalidadEventoDTO(1, "Modalidad", true));
			
			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(evento);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(json);
	        return json;
	    }
	}

