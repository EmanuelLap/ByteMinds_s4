package com.ws.restapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capa3Persistencia.dao.UsuariosEmpresaDAO;
import com.capa3Persistencia.entities.UsuarioEmpresa;

/**
 * Servlet implementation class CargarDatos
 */
@WebServlet("/CargarDatos")
public class CargarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UsuariosEmpresaDAO usuariosEmpresaDAO;
	
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
			UsuarioEmpresa e=new UsuarioEmpresa("Gerardo", "ventas",33,15000);
			e.setActivo(true);
			UsuarioEmpresa usuarioCreado = usuariosEmpresaDAO.agregarUsuario(e);
			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombre());
			
			e=new UsuarioEmpresa("Daniel", "ventas",33,15000);
			e.setActivo(true);
			usuarioCreado = usuariosEmpresaDAO.agregarUsuario(e);
			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombre());
			
			e=new UsuarioEmpresa("Maria", "ventas",33,15000);
			e.setActivo(true);
			usuarioCreado = usuariosEmpresaDAO.agregarUsuario(e);
			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombre());
			
			
			
			out.println("Se creo el usuario:"+ usuarioCreado.getId());
			
		}catch(Exception e) {
			out.println("No se creo el usuario:"+ e.getClass().getSimpleName()+"-"+e.getMessage());
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
