package com.byteminds.bean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
//import tecnofenix.entidades.Usuario;
import tecnofenix.entidades.Usuario;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.AuthService;

import java.io.IOException;
import java.io.Serializable;

@Named(value = "loginBean") // JEE8
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String token = "token";
	private UsuarioDTO userioLogeado;
	private EJBUsuarioRemoto ejbRemoto;
	private GestionUsuarioService gUS;
	private AuthService auth;
//	private static final String secretKeyString = "EstaEsUnaClaveSecretaDeAlMenos32Caracteres";
//	private SecretKey key = Keys.hmacShaKeyFor(secretKeyString.getBytes());
//	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	@PostConstruct
	public void init() {
		auth = new AuthService();
		gUS = new GestionUsuarioService();
	}

	// This method should be modified to validate login against your database
	public String login() {
		Usuario user = validateUsernamePassword();

		FacesMessage message = null;

		if (user != null) {
			userioLogeado = gUS.fromUsuario(user);
			this.token =auth.createJWT(String.valueOf(user.getId()), "ByteMindsApp", user.getApellidos()+user.getNombres(), 3600000);
//			this.token = Jwts.builder().setSubject(user.getApellidos()).signWith(key).compact();
			// Save the JWT token to session or somewhere else
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jwt", token);
			System.out.println("TOKEN: " + token);
//			System.out.println("SecretKey key: " + key.toString());
			
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido " +" ["+ userioLogeado.getUTipo()+"] " + userioLogeado.getNombres()+" "+userioLogeado.getApellidos() , username);
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			return "index?faces-redirect=true";
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Credenciales invalidas");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;// return "login?faces-redirect=false";
		}
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	private Usuario validateUsernamePassword() {
		Usuario user = null;
		try {
			ejbRemoto = new EJBUsuarioRemoto();
			user = ejbRemoto.login(username, password);
			ejbRemoto.ping();

			ejbRemoto.ejecutarMetodo();

		} catch (Exception e) {
			System.out.println(e);
		}

		return user;
	}

	public boolean esTutor() {
		return this.userioLogeado instanceof TutorDTO;
	}

	public boolean esEstudiante() {
		return this.userioLogeado instanceof EstudianteDTO;
	}

	public boolean esAnalista() {
		return this.userioLogeado instanceof AnalistaDTO;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UsuarioDTO getUserioLogeado() {
		return userioLogeado;
	}

	public void setUserioLogeado(UsuarioDTO userioLogeado) {
		this.userioLogeado = userioLogeado;
	}

	public GestionUsuarioService getgUS() {
		return gUS;
	}

	public void setgUS(GestionUsuarioService gUS) {
		this.gUS = gUS;
	}
}
