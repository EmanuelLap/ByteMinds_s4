package com.byteminds.ws.restapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.utils.AuthResponse;
import com.byteminds.utils.AuthService;
import com.byteminds.utils.Credenciales;


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

		UsuarioDTO usuario = gUS.login(credenciales.getUsername(), credenciales.getPassword());

		if (usuario != null) {
			// Devuelve el token y los datos básicos del usuario
			this.token = auth.createJWT(String.valueOf(usuario.getId()), "ByteMindsApp",
					usuario.getApellidos() + usuario.getNombres(), 3600000);//El token dura 1 hora
			return Response.ok(new AuthResponse(this.token, usuario)).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales invalidas").build();
		}
	}

}
