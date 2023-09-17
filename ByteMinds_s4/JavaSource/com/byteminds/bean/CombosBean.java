package com.byteminds.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.negocio.GestionItrService;
import com.byteminds.negocio.GestionRolService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.RolDTO;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Itr;
import tecnofenix.entidades.Rol;
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
	private List<String> comboRol;
	private GestionItrService gITRS;
	private GestionRolService gROLS;

	public CombosBean() {
		gITRS = new GestionItrService();
		gROLS = new GestionRolService();
		ejbRemoto = new EJBUsuarioRemoto();
		comboItr = new ArrayList<String>();
		comboRol = new ArrayList<String>();
		cargarITRCombos();
		cargarROLCombos();
	}

	private void cargarITRCombos() {
		List<Itr> listItr = ejbRemoto.listarITR();

		for (Itr itrItem : listItr) {
			this.comboItr.add(gITRS.fromITR(itrItem).getNombre());
		}

	}

	private void cargarROLCombos() {
		List<Rol> listROL = ejbRemoto.listarRoles();

		for (Rol rolItem : listROL) {
			this.comboRol.add(gROLS.fromRol(rolItem).getNombre());
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

	
	
}
