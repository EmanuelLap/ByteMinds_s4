package com.ws.restapi;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.capa2LogicaNegocio.Empleado;
import com.capa2LogicaNegocio.GestionEmpleadoService;
import com.capa3Persistencia.dao.EmpleadosEmpresaDAO;
import com.capa3Persistencia.entities.EmpleadoEmpresa;

@Path("empleados")
public class RestApiService {
	
	

	@EJB
	GestionEmpleadoService gestionEmpleadoService;
	
	
	@GET
	@Path("obtenerEmpleado/{id}")
	@Produces("application/json")
	public Empleado obtenerEmpleado(@PathParam("id") Long id){
		try {
			 Empleado empleado = gestionEmpleadoService.buscarEmpleado(id);
			 if (empleado==null) {
				 return new Empleado();
			 }
			 return empleado;
		}catch(Exception e) {
			e.printStackTrace();
			return new Empleado(); 
		}
	
		
	}
	
	@GET
	@Path("listarEmpleados")
	@Produces("application/json")
	public List<Empleado> listarEmpleados(){
		

		try {
			 List<Empleado> listaEmpleados = gestionEmpleadoService.seleccionarEmpleados();
			 return listaEmpleados;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return  new ArrayList<Empleado>(); 
		}
		
	}
}
