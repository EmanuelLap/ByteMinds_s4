package com.ws.restapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capa3Persistencia.dao.EmpleadosEmpresaDAO;
import com.capa3Persistencia.entities.EmpleadoEmpresa;

/**
 * Servlet implementation class CargarDatos
 */
@WebServlet("/CargarDatos")
public class CargarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	EmpleadosEmpresaDAO empleadosEmpresaDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarDatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()+"\n");
		PrintWriter out = response.getWriter();
		
		try {
			EmpleadoEmpresa e=new EmpleadoEmpresa("Gerardo", "ventas",33,15000);
			e.setActivo(true);
			EmpleadoEmpresa empleadoCreado = empleadosEmpresaDAO.agregarEmpleado(e);
			out.println("Se creo el empleado:"+ empleadoCreado.getId()+" Nombre"+empleadoCreado.getNombre());
			
			e=new EmpleadoEmpresa("Daniel", "ventas",33,15000);
			e.setActivo(true);
			empleadoCreado = empleadosEmpresaDAO.agregarEmpleado(e);
			out.println("Se creo el empleado:"+ empleadoCreado.getId()+" Nombre"+empleadoCreado.getNombre());
			
			e=new EmpleadoEmpresa("Maria", "ventas",33,15000);
			e.setActivo(true);
			empleadoCreado = empleadosEmpresaDAO.agregarEmpleado(e);
			out.println("Se creo el empleado:"+ empleadoCreado.getId()+" Nombre"+empleadoCreado.getNombre());
			
			
			
			out.println("Se creo el empleado:"+ empleadoCreado.getId());
			
		}catch(Exception e) {
			out.println("No se creo el empleado:"+ e.getClass().getSimpleName()+"-"+e.getMessage());
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
