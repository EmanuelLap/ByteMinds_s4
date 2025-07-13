package com.byteminds.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.byteminds.negocio.GestionReclamoService;
import com.byteminds.negocio.GestionTipoEstadoReclamoService;
import com.byteminds.negocio.ReclamoDTO;
import com.byteminds.negocio.TipoEstadoReclamoDTO;



@Named(value="gestionEstadoReclamoBean")		//JEE8
@SessionScoped	
public class GestionEstadoReclamoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	CombosBean combosBean;
	
	GestionTipoEstadoReclamoService gTERS = new GestionTipoEstadoReclamoService();
	GestionReclamoService gestionReclamoService; 
	
	private List<TipoEstadoReclamoDTO> listEstadosReclamo;
	private TipoEstadoReclamoDTO estadosReclamoSeleccionado;
	
	
	public GestionEstadoReclamoBean() {
		listEstadosReclamo = new ArrayList<TipoEstadoReclamoDTO>();
		estadosReclamoSeleccionado = new TipoEstadoReclamoDTO();
		gestionReclamoService = new GestionReclamoService();
	}


	public String inicializar() {
		System.out.println("INICIALIZANDO GestionEstadoReclamoBean");
		listEstadosReclamo.clear();
		listEstadosReclamo=gTERS.listarTipoEstadoReclamo();
		estadosReclamoSeleccionado = new TipoEstadoReclamoDTO();
		return "/pages/reclamos/gestionestadoreclamo.xhtml?faces-redirect=true";
	}
	
	
	public String editarEstadoReclamo(TipoEstadoReclamoDTO tERDTO) {
		System.out.println("GestionEstadoReclamoBean editarEstadoReclamo");
		estadosReclamoSeleccionado= tERDTO;
		
		return "";
	}
	
	
	public String guardar() {
		System.out.println("GestionEstadoReclamoBean guardar");
		System.out.println(estadosReclamoSeleccionado.getId());
		System.out.println(estadosReclamoSeleccionado.getNombre());
		System.out.println(estadosReclamoSeleccionado.getActivo());
		System.out.println("-*--*-*-*-*--*--*-*-*-*--*--*-*-*-*--*--*-*-*-*-");
		if(estadosReclamoSeleccionado.getNombre()==""||estadosReclamoSeleccionado.getNombre().equals("")) {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe ingresar valores para un nuevo estado",	"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}else {
			if(estadosReclamoSeleccionado.getId()==null) {
				//Si existe el nombre del estado no guardamos y mandamos mensaje de error
				if(!validarExisteNombreEstado(estadosReclamoSeleccionado)) {
					gTERS.guardar(estadosReclamoSeleccionado);
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ingreso/modifico el estado de los reclamos",	"");
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					inicializar();
				}else {
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "El estado ingresado ya existe",	"");
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
				}

			}else {
				if(validarEstadoReclamoAsociadoAReclamo(estadosReclamoSeleccionado)) {
					
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No es posible modificar/eliminar un estado ya asignado a un reclamo",	"");
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					inicializar();
				}else {
					gTERS.guardar(estadosReclamoSeleccionado);
					FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ingreso/modifico el estado de los reclamos",	"");
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);
					inicializar();
				}
			}
			

		}
		//Mandamos a refrescar los combos para que tomen los cambios
		combosBean.cargarTipoEstadoReclamo();
		
		return "";
	}
	
	
	public Boolean validarEstadoReclamoAsociadoAReclamo(TipoEstadoReclamoDTO tERDTO) {
		String tipoEstadoReclamoDTOId = String.valueOf(tERDTO.getId());
		List<ReclamoDTO> listReclamos = new ArrayList<ReclamoDTO>();
		//Buscamos que existan reclamos activos con el TipoEstadoReclamoDTO seleccionado
		listReclamos=gestionReclamoService.buscarReclamosPor(null, null, null, null, null, null, null, tipoEstadoReclamoDTOId, null);
		if(listReclamos.size()>0) {
			for(ReclamoDTO rDTO:listReclamos) {
				if(rDTO.getActivo()) {
					return true;
				}
			}
			return false;
		}else {
			return false;
		}
	}
	
	public Boolean validarExisteNombreEstado(TipoEstadoReclamoDTO tERDTO) {
		List<SelectItem> listEstadosReclamoSelectItem = combosBean.getComboTipoEstadoReclamoSelectItem(); 
		for(SelectItem tERDTO2:listEstadosReclamoSelectItem) {
			if(tERDTO2.getLabel().equals(tERDTO.getNombre())) {
				return true;
			}
		}
		return false;
	}
	
	public List<TipoEstadoReclamoDTO> getListEstadosReclamo() {
		return listEstadosReclamo;
	}


	public void setListEstadosReclamo(List<TipoEstadoReclamoDTO> listEstadosReclamo) {
		this.listEstadosReclamo = listEstadosReclamo;
	}


	public TipoEstadoReclamoDTO getEstadosReclamoSeleccionado() {
		return estadosReclamoSeleccionado;
	}


	public void setEstadosReclamoSeleccionado(TipoEstadoReclamoDTO estadosReclamoSeleccionado) {
		this.estadosReclamoSeleccionado = estadosReclamoSeleccionado;
	}
	
	
	
	
}
