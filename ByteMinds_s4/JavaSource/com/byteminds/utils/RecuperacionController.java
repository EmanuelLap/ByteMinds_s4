package com.byteminds.utils;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

import javax.mail.*;
import javax.mail.internet.*;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.UsuarioDTO;

import java.util.Properties;
import java.util.UUID;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named(value = "recuperacionController") // JEE8
@SessionScoped // JEE8
public class RecuperacionController  implements Serializable {

	private static final long serialVersionUID = 1L;
	private GestionUsuarioService gUS;
	
    private String usuario;
    private String email;

    // Hacer el mapa de tokens público y estático para que sea accesible
    public static Map<String, TokenInfo> tokens = new HashMap<>();

    
    @PostConstruct
	public void init() {
		gUS = new GestionUsuarioService();
	}

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void enviarCorreoRecuperacion() {
    	
        // Verificar que el usuario y el correo correspondan
        if (validarUsuarioYCorreo(usuario, email)) {
        	 // Verificar si ya existe un token activo para este usuario y correo
            TokenInfo tokenActivo = buscarTokenActivo(usuario, email);

            if (tokenActivo != null) {
                // Mostrar mensaje de que ya existe un token activo
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo de recuperación","Ya existe una solicitud de recuperación activa. Revisa tu correo o espera 15 minutos."));
            } else {
            // Generar token
            String token = UUID.randomUUID().toString();

            // Guardar token en memoria con fecha de expiración (15 minutos)
            Date fechaExpiracion = new Date(System.currentTimeMillis() + 15 * 60 * 1000); // 15 minutos
            tokens.put(token, new TokenInfo(usuario, email, fechaExpiracion));

            // Enviar correo electrónico
            enviarCorreo(email,usuario , token);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo de recuperación", "El correo de recuperación ha sido enviado. Verifique su correo para recuperar su contraseña. Dispone de 15 minutos para recuperar su contraseña."));
            }
        } else {
            // Mostrar mensaje de error
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales","Usuario y correo no coinciden."));
        }
    }

    private boolean validarUsuarioYCorreo(String usuario, String email) {
        // Implementa la lógica para verificar que el usuario y el correo correspondan
    	if(usuario==null||email==null||usuario.equals("")||email.equals("")) {
    		return false;
    	}else {
    		 try {
				List<UsuarioDTO> usuarioExistente = gUS.seleccionarUsuarios("", "", "", "", "", "", email, usuario, "", "", null, null, true, "", "", null, null);
				for (UsuarioDTO u : usuarioExistente) {
					System.out.println(u.toStringRestablecer());
				}
				if (usuarioExistente.isEmpty()) {
					return false;
				}else {
					return true;
				}
    		 } catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    private TokenInfo buscarTokenActivo(String usuario, String email) {
        // Recorrer todos los tokens para buscar uno activo para el usuario y correo
        for (Map.Entry<String, TokenInfo> entry : tokens.entrySet()) {
            TokenInfo tokenInfo = entry.getValue();
            if (tokenInfo.getUsuario().equals(usuario) && tokenInfo.getEmail().equals(email) && !tokenExpirado(tokenInfo.getFechaExpiracion())) {
                return tokenInfo; // Devuelve el token activo
            }
        }
        return null; // No hay tokens activos
    }
    
    private void enviarCorreo(String email, String usuario, String token) {
        final String username = "mailejemplo";
        final String password = "XXXX XXXX XXXX XXXX";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreplybyteminds@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jasuaga@gmail.com"));//mauro.diaz@estudiantes.utec.edu.uy  //jasuaga@gmail.com
            message.setSubject("Recuperación de Contraseña");
            message.setText("Para restablecer la contraseña del usuario " + usuario + ", haz clic en el siguiente enlace: "
                + "http://localhost:8080/ByteMinds_s4/restablecer.xhtml?token=" + token );//+"?usuario="+usuario

            Transport.send(message);

            System.out.println("Correo de recuperacion enviado exitosamente.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean tokenExpirado(Date fechaExpiracion) {
        return new Date().after(fechaExpiracion);
    }
    // Método para obtener el tokenInfo desde otra clase
    public static TokenInfo getTokenInfo(String token) {
        return tokens.get(token);
    }

    // Método para eliminar un token
    public static void removeToken(String token) {
        tokens.remove(token);
    }
	public GestionUsuarioService getgUS() {
		return gUS;
	}

	public void setgUS(GestionUsuarioService gUS) {
		this.gUS = gUS;
	}
}