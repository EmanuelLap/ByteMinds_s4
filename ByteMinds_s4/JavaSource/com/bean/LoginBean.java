package com.bean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
//import tecnofenix.entidades.Usuario;
import tecnofenix.entidades.Usuario;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.byteminds.remoto.EJBUsuarioRemoto;

import java.io.IOException;
import java.io.Serializable;

@Named(value="loginBean")		//JEE8
@SessionScoped	
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String token ="token";
    private EJBUsuarioRemoto ejbRemoto;
    
    
    private static final String secretKeyString = "EstaEsUnaClaveSecretaDeAlMenos32Caracteres";
    private SecretKey key = Keys.hmacShaKeyFor(secretKeyString.getBytes());

	@PostConstruct
	public void init() {
		

	}
 

	// This method should be modified to validate login against your database
    public String login() {
    	Usuario user= validateUsernamePassword();
    
        FacesMessage message = null;
        
        if (user != null) {
            String jws = Jwts.builder().setSubject(user.getApellidos()).signWith(key).compact();
            // Save the JWT token to session or somewhere else
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jwt", jws);
            System.out.println("TOKEN: "+jws);
            this.token = jws;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido "+username, username);
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            return "index?faces-redirect=true";
        } else {
        	 message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Credenciales invalidas");
        	 FacesContext.getCurrentInstance().addMessage(null, message);
        	 FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return null;
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }
    
    private Usuario validateUsernamePassword() {
		Usuario user =null;
		try {
			ejbRemoto = new EJBUsuarioRemoto();
			user=ejbRemoto.login(username, password);
			ejbRemoto.ping();

			ejbRemoto.ejecutarMetodo();
			
		} catch (Exception e) {
			System.out.println(e);
		}
    	
        return user;
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
}
