package com.byteminds.ws.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.negocio.GestionRolService;
import com.byteminds.negocio.RolDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/rol")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RolService {

	GestionRolService gRol = new GestionRolService();

    @GET
    @Path("/listar")
    public String listRoles() {
    	System.out.println("Ingresando al servicio rest a mandar lista de Roles");
    	List<RolDTO> listaDeRoles =gRol.listarRolDTO();
    	if(listaDeRoles == null || listaDeRoles.isEmpty()) {
    		System.out.println("Lista de RolDTO vacia");
    		listaDeRoles = new ArrayList<RolDTO>();
    	}
    	ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(listaDeRoles);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return json;
    }
	
	
}
