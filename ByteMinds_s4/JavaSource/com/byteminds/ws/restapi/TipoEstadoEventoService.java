package com.byteminds.ws.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.negocio.GestionTipoAreaService;
import com.byteminds.negocio.GestionTipoEstadoEventoService;
import com.byteminds.negocio.GestionTipoTutorService;
import com.byteminds.negocio.TipoAreaDTO;
import com.byteminds.negocio.TipoEstadoEventoDTO;
import com.byteminds.negocio.TipoTutorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/tipoestadoevento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoEstadoEventoService {

	

	GestionTipoEstadoEventoService gTEE = new GestionTipoEstadoEventoService();

	    @GET
	    @Path("/listar")
	    public String listTipoTutor() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de TipoEstadoEventoDTO");
	    	List<TipoEstadoEventoDTO> listaDeTT =gTEE.listarTipoEstadoEvento();
	    	if(listaDeTT == null || listaDeTT.isEmpty()) {
	    		System.out.println("Lista de TipoEstadoEventoDTO vacia");
	    		listaDeTT = new ArrayList<TipoEstadoEventoDTO>();
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeTT);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
}
