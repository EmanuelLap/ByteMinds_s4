package com.byteminds.ws.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.negocio.GestionItrService;
import com.byteminds.negocio.ItrDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/itrs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItrService {

	GestionItrService gestionItrService = new GestionItrService();

	@GET
	@Path("/listar")
	public String listItrs() {
		System.out.println("Ingresando al servicio rest a mandar lista de Itrs");
		List<ItrDTO> listaDeITRS = gestionItrService.listarITRs();
		if (listaDeITRS == null || listaDeITRS.isEmpty()) {
			System.out.println("Lista de ITRS vacia");
			listaDeITRS = new ArrayList<ItrDTO>();
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(listaDeITRS);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

}
