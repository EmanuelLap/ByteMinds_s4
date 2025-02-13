package com.byteminds.utils;

import com.byteminds.negocio.UsuarioDTO;
/**
 * 
 * @author jasuaga
 * Esta clase es la respuesta de la autenticación por REST para mobile
 */
public class AuthResponse {
	
	private String token;
	private UsuarioDTO user;

	public AuthResponse(String token, UsuarioDTO user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UsuarioDTO getUser() {
		return user;
	}

	public void setUser(UsuarioDTO user) {
		this.user = user;
	}

}
