package com.byteminds.ws.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.byteminds.bean.GestionReclamoBean;
import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionEventoService;
import com.byteminds.negocio.GestionReclamoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ReclamoDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.utils.AuthResponse;
import com.byteminds.utils.AuthService;
import com.byteminds.utils.Credenciales;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/login")
public class RestApiLogin {

	private AuthService auth = new AuthService();
	GestionUsuarioService gUS = new GestionUsuarioService();
	String token;

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Credenciales credenciales) {
//		{
//			  "username": "nombreDeUsuario",
//			  "password": "contraseñaDelUsuario"
//			}
		
		
		UsuarioDTO usuario = gUS.login(credenciales.getUsername(), credenciales.getPassword());

		if (usuario != null) {
			// Devuelve el token y los datos básicos del usuario
			this.token = auth.createJWT(String.valueOf(usuario), "ByteMindsApp",
					usuario.getApellidos() + usuario.getNombres(), 3600000);
			return Response.ok(new AuthResponse(this.token, usuario)).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales invalidas").build();
		}
	}

}
