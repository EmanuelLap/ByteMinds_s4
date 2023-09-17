package com.byteminds.ws.restapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CargarDatos
 */
@WebServlet("/CargarDatos")
public class CargarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//	@EJB
//	UsuariosDAO usuariosEntityDAO;
//	
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public CargarDatos() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath()+"\n");
//		PrintWriter out = response.getWriter();
//		
//		try {
//			UsuarioEntity e=new UsuarioEntity(12345678,"usuario1","usuario1","Apellido1","nombre1",new Date(System.currentTimeMillis()),
//					"departamento1","Genero1","localidad1","mail1@mail.com","mailpersonal1@mail.com","099999999","ITR","ESTUDIANTE",false);
//			e.setActivo(true);
//			UsuarioEntity usuarioCreado = usuariosEntityDAO.agregarUsuario(e);
//			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombres());
//			
//			UsuarioEntity e1=new UsuarioEntity(22345678,"usuario2","usuario2","Apellido2","nombre2",new Date(System.currentTimeMillis()),
//					"departamento2","Genero2","localidad2","mail2@mail.com","mailpersonal2@mail.com","099999999","ITR","ESTUDIANTE",false);
//			e1.setActivo(true);
//			usuarioCreado = usuariosEntityDAO.agregarUsuario(e1);
//			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombres());
//			
//			UsuarioEntity e2=new UsuarioEntity(32345678,"usuario3","usuario3","Apellido3","nombre3",new Date(System.currentTimeMillis()),
//					"departamento3","Genero3","localidad3","mail3@mail.com","mailpersonal3@mail.com","099999999","ITR","ESTUDIANTE",false);
//			e2.setActivo(true);
//			usuarioCreado = usuariosEntityDAO.agregarUsuario(e2);
//			out.println("Se creo el usuario:"+ usuarioCreado.getId()+" Nombre"+usuarioCreado.getNombres());
//			
//						
//		}catch(Exception e) {
//			out.println("No se creo el usuario:"+ e.getClass().getSimpleName()+"-"+e.getMessage());
//		}
//		
//		
//		
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
