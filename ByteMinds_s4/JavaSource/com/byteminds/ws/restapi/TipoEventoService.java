package com.byteminds.ws.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.negocio.GestionTipoAreaService;
import com.byteminds.negocio.GestionTipoEventoService;
import com.byteminds.negocio.GestionTipoTutorService;
import com.byteminds.negocio.TipoAreaDTO;
import com.byteminds.negocio.TipoEventoDTO;
import com.byteminds.negocio.TipoTutorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/tipoevento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoEventoService {

	

	GestionTipoEventoService gTE = new GestionTipoEventoService();

	    @GET
	    @Path("/listar")
	    public String listTipoTutor() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de TipoEventoDTO");
	    	List<TipoEventoDTO> listaDeTT =gTE.listarTipoEventos();
	    	if(listaDeTT == null || listaDeTT.isEmpty()) {
	    		System.out.println("Lista de TipoEventoDTO vacia");
	    		listaDeTT = new ArrayList<TipoEventoDTO>();
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
