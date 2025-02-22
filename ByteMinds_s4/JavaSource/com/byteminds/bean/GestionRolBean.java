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

import com.byteminds.negocio.GestionRolService;
import com.byteminds.negocio.RolDTO;



@Named(value = "gestionRolBean") // JEE8
@SessionScoped // JEE8
public class GestionRolBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionRolService gestionRolService;

	
	private Integer id;
	private String modalidad;
	
	@Inject
	GestionUsuarioBean gestionUsuarioBean;
	

	private List<RolDTO> listROLDTO = new ArrayList<RolDTO>();
	private RolDTO rolDTOSeleccionado = new RolDTO();

	private Integer idRolSeleccionado;


	private boolean modoEdicion = false;


	public GestionRolBean() {
		System.out.println("INICIALIZANDO GestionROLBean");
		
//		gestionItrService = new GestionItrService();
		
		idRolSeleccionado=0;
	}

	public void preRenderViewListener() {
		System.out.println("INICIALIZANDO GestionROLBean preRenderViewListener");
		if (id != null) {
			//			reclamoSeleccionado = gestionReclamoService.fromReclamo(ejbReclamoRemoto.buscarReclamoPorId(id));
		} else {
//			reclamoSeleccionado = new ReclamoDTO();
//			reclamoSeleccionado.setEventoId(new EventoDTO());
//			reclamoSeleccionado.setFecha(new Date(System.currentTimeMillis()));
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

	public String salvarCambios() {

//		if (reclamoSeleccionado.getId() == null) {
//
//
//			ReclamoDTO nuevoReclamoDTO;
//			try {
//				nuevoReclamoDTO = gestionReclamoService.agregarReclamo(reclamoSeleccionado);
//				this.id = nuevoReclamoDTO.getId();
//
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo reclamo",	"");
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "view";
//
//			} catch (PersistenciaException e) {
//
//				Throwable rootException = ExceptionsTools.getCause(e);
//				String msg1 = e.getMessage();
//				String msg2 = ExceptionsTools.formatedMsg(rootException);
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "update";
//
//				e.printStackTrace();
//			}
//
//		} else if (modalidad.equals("update")) {
//
//			try {
//				gestionReclamoService.agregarReclamo(reclamoSeleccionado);
//
//				FacesContext.getCurrentInstance().addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el reclamo.", ""));
//
//			} catch (PersistenciaException e) {
//
//				Throwable rootException = ExceptionsTools.getCause(e);
//				String msg1 = e.getMessage();
//				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
//				// mensaje de actualizacion correcta
//				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
//				FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//
//				this.modalidad = "update";
//
//				e.printStackTrace();
//			}
//		}
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "click en gestion rol.", ""));
		return "";
	}

	
//	public void actualizarROLSeleccionado(ValueChangeEvent event) {
//		System.out.println(event.getNewValue()); 
//		System.out.println("idROLSeleccionado ValueChangeEvent "+idRolSeleccionado);
//		idRolSeleccionado=(Integer)event.getNewValue();
//		rolDTOSeleccionado= gestionRolService.obtenerRolSeleccionado((Integer)event.getNewValue());
//		System.out.println(rolDTOSeleccionado.toString());
//		gestionUsuarioBean.getUsuarioSeleccionado().setRol(rolDTOSeleccionado);
//		System.out.println("ROL SETEADO = "+gestionUsuarioBean.getUsuarioSeleccionado().getRol().getNombre());
//	}

	public void actualizarROLSeleccionado(AjaxBehaviorEvent event) {
	    Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
	    
	    System.out.println(nuevoValor); 
	    System.out.println("idROLSeleccionado AjaxBehaviorEvent " + idRolSeleccionado);
	    idRolSeleccionado = nuevoValor;
	    rolDTOSeleccionado = gestionRolService.obtenerRolSeleccionado(nuevoValor);
	    System.out.println(rolDTOSeleccionado.toString());
	    gestionUsuarioBean.getUsuarioSeleccionado().setRol(rolDTOSeleccionado);
	    System.out.println("ROL SETEADO = "+gestionUsuarioBean.getUsuarioSeleccionado().getRol().getNombre());
	}

	public void chuchu() {
		System.out.println("HICISTE CLICK EN CHUCHU");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public boolean getModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}


	public List<RolDTO> getListROLDTO() {
		return listROLDTO;
	}

	public void setListROLDTO(List<RolDTO> listROLDTO) {
		this.listROLDTO = listROLDTO;
	}

	public RolDTO getRolDTOSeleccionado() {
		return rolDTOSeleccionado;
	}

	public void setRolDTOSeleccionado(RolDTO rolDTOSeleccionado) {
		this.rolDTOSeleccionado = rolDTOSeleccionado;
	}

	public Integer getIdItrSeleccionado() {
		return idRolSeleccionado;
	}

	public void setIdItrSeleccionado(Integer idRolSeleccionado) {
		this.idRolSeleccionado = idRolSeleccionado;
	}


}
