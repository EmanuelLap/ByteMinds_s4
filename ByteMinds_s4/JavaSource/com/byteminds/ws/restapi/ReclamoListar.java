package com.byteminds.ws.restapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ReclamoDTO;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ReclamoListar
 */
@WebServlet("/AgregarListar")
public class ReclamoListar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
//	GestionUsuarioService gUS;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReclamoListar() {
        super();
//        gUS = new GestionUsuarioService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath()+"\n");
		PrintWriter out = response.getWriter();
		
		try {
		
//			out.println("Ejecutando metodo rest:");
			
			GestionEventoService gES = new GestionEventoService();
			GestionUsuarioService gUS = new GestionUsuarioService();
			ReclamoDTO reclamo = new ReclamoDTO();
			// Configura los valores del reclamo como desees
			reclamo.setId(1);
			reclamo.setTitulo("Ejemplo1");
			reclamo.setDetalle("Detalle1");
			reclamo.setEventoId(gES.obtenerEvento(2));
			reclamo.setEstudianteId((EstudianteDTO)gUS.buscarUsuario(117));
			reclamo.setCreditos(10);
			reclamo.setFecha(new Date(System.currentTimeMillis()));
			
			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(reclamo);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(json);
			out.println(json);
			
			
			
		}catch(Exception e) {
			out.println("Error al ejecutar:"+ e.getClass().getSimpleName()+"-"+e.getMessage());
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
