package com.byteminds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.exception.PersistenciaException;
import com.byteminds.negocio.ConvocatoriaAsistenciaEventoEstudianteDTO;
import com.byteminds.negocio.GestionConvocatoriaAsistenciaEventoEstudianteService;
import com.byteminds.negocio.GestionItrService;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.ExceptionsTools;

import tecnofenix.entidades.ConvocatoriaAsistenciaEventoEstudiante;

@Named(value = "gestionEscolaridadBean") // JEE8
@SessionScoped // JEE8
public class GestionEscolaridadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionConvocatoriaAsistenciaEventoEstudianteService gCAEES;

	@Inject
	LoginBean gestionLoginBean;

	List<ConvocatoriaAsistenciaEventoEstudianteDTO> listEscolaridad;

	private double promedioCalificacion;

	public GestionEscolaridadBean() {
		System.out.println("INICIALIZANDO GestionEscolaridadBean");
		gCAEES = new GestionConvocatoriaAsistenciaEventoEstudianteService();
		listEscolaridad = new ArrayList<ConvocatoriaAsistenciaEventoEstudianteDTO>();

	}

	public String inicializar() {
		System.out.println("INICIALIZANDO GestionEscolaridadBean ");
		listEscolaridad.clear();
		listEscolaridad = gCAEES.filtrarAsistEstuAEventosPor(null, null, null, null,
				String.valueOf(gestionLoginBean.getUserioLogeado().getDocumento()), null, null, null);

		calcularPromedioCalificacion();

		return "/pages/escolaridad/escolaridad.xhtml";
	}

	public void calcularPromedioCalificacion() {
		promedioCalificacion = 0;
		if (listEscolaridad != null && !listEscolaridad.isEmpty()) {
			double sum = 0;
			for (ConvocatoriaAsistenciaEventoEstudianteDTO escolaridad : listEscolaridad) {
				sum += escolaridad.getCalificacion();
			}
			promedioCalificacion = sum / listEscolaridad.size();
		} else {
			promedioCalificacion = 0;
		}
	}

	public List<ConvocatoriaAsistenciaEventoEstudianteDTO> getListEscolaridad() {
		return listEscolaridad;
	}

	public void setListEscolaridad(List<ConvocatoriaAsistenciaEventoEstudianteDTO> listEscolaridad) {
		this.listEscolaridad = listEscolaridad;
	}

	public double getPromedioCalificacion() {
		return promedioCalificacion;
	}

	public void setPromedioCalificacion(double promedioCalificacion) {
		this.promedioCalificacion = promedioCalificacion;
	}

}
