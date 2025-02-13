package com.byteminds.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.UsuarioDTO;


@Named(value = "restablecerController") // JEE8
@SessionScoped // JEE8
public class RestablecerController  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean mostrarContrasenia = false;
    private String nuevaContrasenia;
    private String token;
    private GestionUsuarioService gUS;
    
    
    @PostConstruct
   	public void init() {
   		gUS = new GestionUsuarioService();
   	}
    
    // Getters y Setters
    public String getNuevaContrasenia() {
        return nuevaContrasenia;
    }

    public void setNuevaContrasenia(String nuevaContrasenia) {
        this.nuevaContrasenia = nuevaContrasenia;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getMostrarContrasenia() {
		return mostrarContrasenia;
	}
    public void setMostrarContrasenia(Boolean mostrarContrasenia) {
		this.mostrarContrasenia = mostrarContrasenia;
	}
    public GestionUsuarioService getgUS() {
		return gUS;
	}
	public void setgUS(GestionUsuarioService gUS) {
		this.gUS = gUS;
	}
    
    public void restablecerContrasenia() {
		if (nuevaContrasenia != null || nuevaContrasenia != "") {
			if (validarContrasenia()) {

				// Validar token y actualizar contraseña
				TokenInfo tokenInfo = RecuperacionController.getTokenInfo(token);

				if (tokenInfo != null && !tokenExpirado(tokenInfo.getFechaExpiracion())) {
					System.out.println(tokenInfo.toString());
					// Actualizar contraseña en la base de datos
					actualizarContrasenia(tokenInfo.getUsuario(), nuevaContrasenia);

					// Eliminar el token después de usarlo
					RecuperacionController.removeToken(token);

					// Mostrar mensaje de éxito
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Información","Contraseña restablecida exitosamente."));
					
					 // Limpiar campos
			        nuevaContrasenia = null;

				} else {
					// Mostrar mensaje de error
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información","Token inválido o expirado."));
				}
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingreso de contraseña vacío","Debe ingresar una nueva contraseña."));
		}
    }

    private boolean tokenExpirado(Date fechaExpiracion) {
        return new Date().after(fechaExpiracion);
    }


    private void actualizarContrasenia(String usuario, String nuevaContrasenia) {
 
    	List<UsuarioDTO> usuarioExistente;
		try {
			usuarioExistente = gUS.seleccionarUsuarios("", "", "", "", "", "", "", usuario, "", "", null, null, true, "", "", null, null);
			usuarioExistente.get(0).setContrasenia(nuevaContrasenia);
			gUS.actualizarUsuario(usuarioExistente.get(0));
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información","Error al restablecer la contraseña"));
			e.printStackTrace();
		}
		
    	
    	System.out.println("Actualizando contrasenia de "+usuario+" a "+nuevaContrasenia);
    }
    
    
    public Boolean validarContrasenia() {

			String password = nuevaContrasenia;
			// Validación de la contraseña con una expresión regular.
			String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

			if (!password.matches(passwordPattern)) {
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN,"Información",
						"La contraseña no cumple con los requisitos: debe tener entre 8 y 20 caracteres, incluir números, mayúsculas, minúsculas y caracteres especiales.");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				return false;
			}
			
			return true;
		}
}