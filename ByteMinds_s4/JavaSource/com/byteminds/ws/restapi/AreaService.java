package com.byteminds.ws.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.negocio.GestionTipoAreaService;
import com.byteminds.negocio.TipoAreaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/area")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AreaService {

	
	GestionTipoAreaService gTa = new GestionTipoAreaService();

	    @GET
	    @Path("/listar")
	    public String listAreas() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de Areas");
	    	List<TipoAreaDTO> listaDeTipoAreas =gTa.listarTipoArea();
	    	if(listaDeTipoAreas == null || listaDeTipoAreas.isEmpty()) {
	    		System.out.println("Lista de TipoAreaDTO vacia");
	    		listaDeTipoAreas = new ArrayList<TipoAreaDTO>();
	    	}
	    	ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(listaDeTipoAreas);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return json;
	    }
	    
	  
	}