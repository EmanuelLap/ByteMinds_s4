package com.byteminds.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.byteminds.negocio.GestionItrService;
import com.byteminds.negocio.GestionRolService;
import com.byteminds.negocio.GestionTipoAreaService;
import com.byteminds.negocio.GestionTipoTutorService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.RolDTO;
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
	private GestionItrService gITRS;
	private GestionRolService gROLS;
	private GestionTipoAreaService gTAS;
	private GestionTipoTutorService gTTT;
	
	
	
	
	public CombosBean() {
		gITRS = new GestionItrService();
		gROLS = new GestionRolService();
		gTAS = new GestionTipoAreaService();
		gTTT = new GestionTipoTutorService();
		
		ejbRemoto = new EJBUsuarioRemoto();
		comboItr = new ArrayList<String>();
		comboRol = new ArrayList<String>();
		comboItrSelectItem= new ArrayList<SelectItem>();
		comboROLSelectItem= new ArrayList<SelectItem>();
		comboTipoTutorSelectItem= new ArrayList<SelectItem>();
		comboTipoAreaSelectItem= new ArrayList<SelectItem>();
		cargarITRCombos();
		cargarROLCombos();
		cargarTipoTutorCombo();
		cargarTipoAreaCombo();
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
			this.comboItr.add(gTTT.fromTipoTutorTipo(ttt).getNombre());
			comboTipoTutorSelectItem.add(new SelectItem(gTTT.fromTipoTutorTipo(ttt).getId(),gTTT.fromTipoTutorTipo(ttt).getNombre()));

		}

	}
	private void cargarTipoAreaCombo() {
		List<TipoArea> listTA = ejbRemoto.listarTipoArea();

		for (TipoArea ta : listTA) {
			this.comboItr.add(gTAS.fromTipoArea(ta).getNombre());
			comboTipoAreaSelectItem.add(new SelectItem(gTAS.fromTipoArea(ta).getId(),gTAS.fromTipoArea(ta).getNombre()));

		}

	}
	

	private void cargarROLCombos() {
		List<Rol> listROL = ejbRemoto.listarRoles();

		for (Rol rolItem : listROL) {
			this.comboRol.add(gROLS.fromRol(rolItem).getNombre());
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
	
}
