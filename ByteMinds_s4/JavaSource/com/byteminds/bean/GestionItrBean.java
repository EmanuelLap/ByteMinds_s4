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
import com.byteminds.negocio.GestionItrService;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.ExceptionsTools;


@Named(value = "gestionItrBean") // JEE8
@SessionScoped // JEE8
public class GestionItrBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	GestionItrService gestionItrService;

	private Integer id;
	private String modalidad;
	
//	private GestionItrService gITRService;
	@Inject
	GestionUsuarioBean gestionUsuarioBean;
	

	private List<ItrDTO> listITRDTO = new ArrayList<ItrDTO>();
	private ItrDTO itrDTOSeleccionado = new ItrDTO();

	private Integer idItrSeleccionado;


	private boolean modoEdicion = false;


	public GestionItrBean() {
		System.out.println("INICIALIZANDO GestionITRBean");
		gestionItrService = new GestionItrService();
		idItrSeleccionado=0;
		id=null;
		inicializar();
	}

	public String inicializar() {
		System.out.println("INICIALIZANDO GestionITRBean preRenderViewListener");
		listITRDTO.addAll(gestionItrService.listarITRs());
		
		if (id != null) {
						itrDTOSeleccionado = gestionItrService.obtenerITRSeleccionado(id);
		} else {
			itrDTOSeleccionado = new ItrDTO();

		}
		if(modalidad!=null) {
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
		} else {
			
			modoEdicion = false;
			modalidad = "view";

		}
		
		return "/pages/itr/gestionITR?faces-redirect=true";
	}

	public String salvarCambios() {
		if(validarITR()) {

		if (itrDTOSeleccionado.getId() == null) {


			ItrDTO nuevoITRDTO;
			try {
				nuevoITRDTO = gestionItrService.agregarITR(itrDTOSeleccionado);
				this.id = nuevoITRDTO.getId();

				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo ITR",	"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "view";

			} catch (Exception e) {

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
				gestionItrService.modificarITR(itrDTOSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el reclamo.", ""));

			} catch (Exception e) {

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
		}
		return "";
	}

	
	public void actualizarITRSeleccionado(ValueChangeEvent event) {
		System.out.println(event.getNewValue()); 
		System.out.println("idITRSeleccionado ValueChangeEvent "+idItrSeleccionado);
		idItrSeleccionado=(Integer)event.getNewValue();
		itrDTOSeleccionado= gestionItrService.obtenerITRSeleccionado((Integer)event.getNewValue());
		System.out.println(itrDTOSeleccionado.toString());
		gestionUsuarioBean.getUsuarioSeleccionado().setItr(itrDTOSeleccionado);
		System.out.println("ITR SETEADO = "+gestionUsuarioBean.getUsuarioSeleccionado().getItr().getNombre());
	}

	public void actualizarITRSeleccionado(AjaxBehaviorEvent event) {
	    Integer nuevoValor = (Integer) ((UIOutput) event.getSource()).getValue();
	    
	    System.out.println(nuevoValor); 
	    System.out.println("idITRSeleccionado AjaxBehaviorEvent " + idItrSeleccionado);
	    idItrSeleccionado = nuevoValor;
	    itrDTOSeleccionado = gestionItrService.obtenerITRSeleccionado(nuevoValor);
	    System.out.println(itrDTOSeleccionado.toString());
	    gestionUsuarioBean.getUsuarioSeleccionado().setItr(itrDTOSeleccionado);
	    System.out.println("ITR SETEADO = "+gestionUsuarioBean.getUsuarioSeleccionado().getItr().getNombre());
	}

	
	public boolean validarITR() {
		if(this.itrDTOSeleccionado==null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El ITR no puede ser null ",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
		if(this.itrDTOSeleccionado.getNombre()==null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar un nombre para el ITR ",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
		
		if(this.itrDTOSeleccionado.getDepartamento()==null) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar un departamento para el ITR ",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return false;
		}
		
		return true;
	}
	
	
	
	public void seleccionarITR(ItrDTO itrDto) {
		itrDTOSeleccionado = itrDto;
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
	public GestionItrService getGestionItrService() {
		return gestionItrService;
	}

	public void setGestionItrService(GestionItrService gestionItrService) {
		this.gestionItrService = gestionItrService;
	}

	public List<ItrDTO> getListITRDTO() {
		return listITRDTO;
	}

	public void setListITRDTO(List<ItrDTO> listITRDTO) {
		this.listITRDTO = listITRDTO;
	}

	public ItrDTO getItrDTOSeleccionado() {
		return itrDTOSeleccionado;
	}

	public void setItrDTOSeleccionado(ItrDTO itrDTOSeleccionado) {
		this.itrDTOSeleccionado = itrDTOSeleccionado;
	}

	public Integer getIdItrSeleccionado() {
		return idItrSeleccionado;
	}

	public void setIdItrSeleccionado(Integer idItrSeleccionado) {
		this.idItrSeleccionado = idItrSeleccionado;
	}

}
