package com.byteminds.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.byteminds.negocio.GestionItrService;
import com.byteminds.negocio.GestionModalidadEventoService;
import com.byteminds.negocio.GestionRolService;
import com.byteminds.negocio.GestionTipoAreaService;
import com.byteminds.negocio.GestionTipoEventoService;
import com.byteminds.negocio.GestionTipoTutorService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.ModalidadEventoDTO;
import com.byteminds.negocio.RolDTO;
import com.byteminds.negocio.TipoEventoDTO;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Itr;
import tecnofenix.entidades.Rol;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorArea;
import tecnofenix.entidades.TipoTutorEncargado;
import tecnofenix.entidades.TipoTutorTipo;

import java.io.Serializable;


@Named(value="combosBean")		//JEE8
@SessionScoped	
public class CombosBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EJBUsuarioRemoto ejbRemoto;

	private List<String> comboItr;
	private List<SelectItem> comboItrSelectItem;
	private List<SelectItem> comboROLSelectItem;
	private List<SelectItem> comboTipoTutorSelectItem;
	private List<SelectItem> comboTipoAreaSelectItem;
	private List<String> comboRol;
	
	private List<String> comboModalidad;
	private List<SelectItem> comboModalidadSelectItem;

	private List<String> comboTipoEvento;
	private List<SelectItem> comboTipoEventoSelectItem;
	
	private GestionItrService gITRS;
	private GestionRolService gROLS;
	private GestionTipoAreaService gTAS;
	private GestionTipoTutorService gTTT;
	private GestionTipoEventoService gTE;
	private GestionModalidadEventoService gME;
	
	
	
	public CombosBean() {
		gITRS = new GestionItrService();
		gROLS = new GestionRolService();
		gTAS = new GestionTipoAreaService();
		gTTT = new GestionTipoTutorService();
		gTE = new GestionTipoEventoService();
		gME = new GestionModalidadEventoService();
		
		ejbRemoto = new EJBUsuarioRemoto();
		comboItr = new ArrayList<String>();
		comboRol = new ArrayList<String>();
		comboModalidadSelectItem = new ArrayList<SelectItem>();
		comboModalidad= new ArrayList<String>();
		comboTipoEvento = new ArrayList<String>();
		comboTipoEventoSelectItem= new ArrayList<SelectItem>();
		comboItrSelectItem= new ArrayList<SelectItem>();
		comboROLSelectItem= new ArrayList<SelectItem>();
		comboTipoTutorSelectItem= new ArrayList<SelectItem>();
		comboTipoAreaSelectItem= new ArrayList<SelectItem>();
		cargarITRCombos();
		cargarROLCombos();
		cargarTipoTutorCombo();
		cargarTipoAreaCombo();
		cargarModalidad();
		cargarTipoEvento();
	}

	private void cargarTipoEvento() {
		
		for (TipoEventoDTO te: gTE.listarTipoEventos()) {
			comboTipoEvento.add(te.getNombre());
			comboTipoEventoSelectItem.add(new SelectItem(te.getId(),te.getNombre()));
			System.out.println("cargarTipoEvento cargarTipoEvento cargarTipoEvento "+te.getId()+" "+te.getNombre());
		}
			
	}
	private void cargarModalidad() {
		
		for (ModalidadEventoDTO me: gME.listarModalidaEventos()) {
			comboModalidad.add(me.getNombre());
			comboModalidadSelectItem.add(new SelectItem(me.getId(),me.getNombre()));
			System.out.println("cargarModalidad cargarModalidad cargarModalidad "+me.getId()+" "+me.getNombre());
		}
//		
//		comboModalidad.add(new SelectItem("", ""));
//		comboModalidad.add(new SelectItem("VIRTUAL", "Virtual"));
//		comboModalidad.add(new SelectItem("PRESENCIAL", "Presencial"));
//		comboModalidad.add(new SelectItem("SEMI_PRESENCIAL", "Semipresencial"));
	}
	
	private void cargarITRCombos() {
		List<Itr> listItr = ejbRemoto.listarITR();

		for (Itr itrItem : listItr) {
			this.comboItr.add(gITRS.fromITR(itrItem).getNombre());
			comboItrSelectItem.add(new SelectItem(gITRS.fromITR(itrItem).getId(),gITRS.fromITR(itrItem).getNombre()));
//			comboItrSelectItem.add(new SelectItem(gITRS.fromITR(itrItem),gITRS.fromITR(itrItem).getNombre()));
		}

	}
	private void cargarTipoTutorCombo() {
		List<TipoTutorTipo> listTTT = ejbRemoto.listarTipoTutorTipo();

		for (TipoTutorTipo ttt : listTTT) {
//			this.comboItr.add(gTTT.fromTipoTutorTipo(ttt).getNombre());
			comboTipoTutorSelectItem.add(new SelectItem(gTTT.fromTipoTutorTipo(ttt).getId(),gTTT.fromTipoTutorTipo(ttt).getNombre()));

		}

	}
	private void cargarTipoAreaCombo() {
		List<TipoArea> listTA = ejbRemoto.listarTipoArea();

		for (TipoArea ta : listTA) {
//			this.comboItr.add(gTAS.fromTipoArea(ta).getNombre());
			comboTipoAreaSelectItem.add(new SelectItem(gTAS.fromTipoArea(ta).getId(),gTAS.fromTipoArea(ta).getNombre()));

		}

	}
	

	private void cargarROLCombos() {
		List<Rol> listROL = ejbRemoto.listarRoles();

		for (Rol rolItem : listROL) {
//			this.comboRol.add(gROLS.fromRol(rolItem).getNombre());
			comboROLSelectItem.add(new SelectItem(gROLS.fromRol(rolItem).getId(),gROLS.fromRol(rolItem).getNombre()));
		}

	}

	public List<String> getComboItr() {
		return comboItr;
	}

	public void setComboItr(List<String> comboItr) {
		this.comboItr = comboItr;
	}

	public List<String> getComboRol() {
		return comboRol;
	}

	public void setComboRol(List<String> comboRol) {
		this.comboRol = comboRol;
	}

	public List<SelectItem> getComboROLSelectItem() {
		return comboROLSelectItem;
	}

	public void setComboROLSelectItem(List<SelectItem> comboROLSelectItem) {
		this.comboROLSelectItem = comboROLSelectItem;
	}

	public List<SelectItem> getComboTipoAreaSelectItem() {
		return comboTipoAreaSelectItem;
	}

	public void setComboTipoAreaSelectItem(List<SelectItem> comboTipoAreaSelectItem) {
		this.comboTipoAreaSelectItem = comboTipoAreaSelectItem;
	}

	public List<SelectItem> getComboTipoTutorSelectItem() {
		return comboTipoTutorSelectItem;
	}

	public void setComboTipoTutorSelectItem(List<SelectItem> comboTipoTutorSelectItem) {
		this.comboTipoTutorSelectItem = comboTipoTutorSelectItem;
	}

	public List<SelectItem> getComboItrSelectItem() {
		return comboItrSelectItem;
	}

	public void setComboItrSelectItem(List<SelectItem> comboItrSelectItem) {
		this.comboItrSelectItem = comboItrSelectItem;
	}

	public List<String> getComboModalidad() {
		return comboModalidad;
	}

	public void setComboModalidad(List<String> comboModalidad) {
		this.comboModalidad = comboModalidad;
	}

	public List<String> getComboTipoEvento() {
		return comboTipoEvento;
	}

	public void setComboTipoEvento(List<String> comboTipoEvento) {
		this.comboTipoEvento = comboTipoEvento;
	}

	public List<SelectItem> getComboTipoEventoSelectItem() {
		return comboTipoEventoSelectItem;
	}

	public void setComboTipoEventoSelectItem(List<SelectItem> comboTipoEventoSelectItem) {
		this.comboTipoEventoSelectItem = comboTipoEventoSelectItem;
	}
	public List<SelectItem> getComboModalidadSelectItem() {
		return comboModalidadSelectItem;
	}

	public void setComboModalidadSelectItem(List<SelectItem> comboModalidadSelectItem) {
		this.comboModalidadSelectItem = comboModalidadSelectItem;
	}
}
