package com.ws.restapi;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa2LogicaNegocio.Usuario;


@Path("usuarios")
public class RestApiService {
	
	

	@EJB
	GestionUsuarioService gestionUsuarioService;
	
	
	@GET
	@Path("obtenerUsuario/{id}")
	@Produces("application/json")
	public Usuario obtenerEmpleado(@PathParam("id") Long id){
		try {
			 Usuario empleado = gestionUsuarioService.buscarUsuario(id);
			 if (empleado==null) {
				 return new Usuario();
			 }
			 return empleado;
		}catch(Exception e) {
			e.printStackTrace();
			return new Usuario(); 
		}
	
		
	}
	
	@GET
	@Path("listarUsuarios")
	@Produces("application/json")
	public List<Usuario> listarUsuarios(){
		

		try {
			 List<Usuario> listaEmpleados = gestionUsuarioService.seleccionarUsuarios();
			 return listaEmpleados;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return  new ArrayList<Usuario>(); 
		}
		
	}
}
