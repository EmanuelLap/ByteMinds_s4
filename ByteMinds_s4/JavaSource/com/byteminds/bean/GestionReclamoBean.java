package com.byteminds.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.GestionReclamoService;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ReclamoDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.ExceptionsTools;

@Named(value = "gestionReclamo") // JEE8
@SessionScoped // JEE8
public class GestionReclamoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	GestionReclamoService gestionReclamoService;
	private EJBUsuarioRemoto ejbReclamoRemoto;

	private Integer id;
	private String modalidad;
	private ReclamoDTO reclamoSeleccionado;
	private boolean modoEdicion = false;

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public void preRenderViewListener() {

		if (id != null) {
			reclamoSeleccionado = gestionReclamoService.fromReclamo(ejbReclamoRemoto.buscarReclamoPorId(id));
		} else {
			reclamoSeleccionado = new ReclamoDTO();
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion = false;
		} else if (modalidad.contentEquals("update")) {
			modoEdicion = true;
		} else if (modalidad.contentEquals("insert")) {
			modoEdicion = true;
		} else if (modalidad.contentEquals("edit")) {
			modoEdicion = true;
		} else {

			modoEdicion = false;
			modalidad = "view";

		}
	}
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public String salvarCambios() {

		if (reclamoSeleccionado.getId() == null) {
			

			ReclamoDTO nuevoReclamoDTO;
			try {
				nuevoReclamoDTO = gestionReclamoService.agregarReclamo(reclamoSeleccionado);
				this.id = nuevoReclamoDTO.getId();

				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo reclamo",	"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "view";

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(rootException);
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}

		} else if (modalidad.equals("update")) {

			try {
				gestionReclamoService.agregarReclamo(reclamoSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el reclamo.", ""));

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}
		}
		return "";
	}
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	

}
