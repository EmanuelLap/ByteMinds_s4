package com.byteminds.ws.restapi;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.UsuarioDTO;


@Path("usuarios")
public class RestApiService {
	
	

	@EJB
	GestionUsuarioService gestionUsuarioService;
	
//	
//	@GET
//	@Path("obtenerUsuario/{id}")
//	@Produces("application/json")
//	public UsuarioDTO obtenerEmpleado(@PathParam("id") Long id){
//		try {
//			 UsuarioDTO empleado = gestionUsuarioService.buscarUsuario(id);
//			 if (empleado==null) {
//				 return new UsuarioDTO();
//			 }
//			 return empleado;
//		}catch(Exception e) {
//			e.printStackTrace();
//			return new UsuarioDTO(); 
//		}
//	
//		
//	}
	
	@GET
	@Path("listarUsuarios")
	@Produces("application/json")
	public List<UsuarioDTO> listarUsuarios(){
		

		try {
			 List<UsuarioDTO> listaEmpleados = gestionUsuarioService.seleccionarUsuarios();
			 return listaEmpleados;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return  new ArrayList<UsuarioDTO>(); 
		}
		
	}
}
