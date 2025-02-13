package com.byteminds.utils;

import java.util.Date;

public class TokenInfo {
    private String usuario;
    private String email;
    private Date fechaExpiracion;

    public TokenInfo(String usuario, String email, Date fechaExpiracion) {
        this.usuario = usuario;
        this.email = email;
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }
    
    public String toString() {
		return "TokenInfo [usuario=" + usuario + ", email=" + email + ", fechaExpiracion=" + fechaExpiracion + "]";
	}
}