package com.bean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.io.IOException;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String token ="token";

    // Use the same key you used in AuthService
    private static final String secretKeyString = "EstaEsUnaClaveSecretaDeAlMenos32Caracteres";
    private SecretKey key = Keys.hmacShaKeyFor(secretKeyString.getBytes());

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

	// This method should be modified to validate login against your database
    public String login() {
        boolean valid = validateUsernamePassword();
        
        if (valid) {
            String jws = Jwts.builder().setSubject(username).signWith(key).compact();
            // Save the JWT token to session or somewhere else
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jwt", jws);
            System.out.println("TOKEN: "+jws);
            this.token = jws;
//            try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
//            } catch (IOException e) {
//                // Maneja la excepción
//                e.printStackTrace();
//            }
            return "index?faces-redirect=true";
        } else {
            // login failed
            return "/pages/failLogin?faces-redirect=true";
        }
    }

    private boolean validateUsernamePassword() {
        // Dummy check: replace with your database check
        return "admin".equals(username) && "admin".equals(password);
    }

}
