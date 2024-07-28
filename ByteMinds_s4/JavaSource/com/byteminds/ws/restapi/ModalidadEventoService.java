package com.byteminds.ws.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.negocio.GestionModalidadEventoService;
import com.byteminds.negocio.GestionTipoAreaService;
import com.byteminds.negocio.GestionTipoTutorService;
import com.byteminds.negocio.ModalidadEventoDTO;
import com.byteminds.negocio.TipoAreaDTO;
import com.byteminds.negocio.TipoTutorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/modalidadevento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModalidadEventoService {

	

	GestionModalidadEventoService gME = new GestionModalidadEventoService();

	    @GET
	    @Path("/listar")
	    public String listTipoTutor() {
	    	System.out.println("Ingresando al servicio rest a mandar lista de ModalidadEventoDTO");
	    	List<ModalidadEventoDTO> listaDeTT =gME.listarModalidaEventos();
	    	if(listaDeTT == null || listaDeTT.isEmpty()) {
	    		System.out.println("Lista de ModalidadEventoDTO vacia");
	    		listaDeTT = new ArrayList<ModalidadEventoDTO>();
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
