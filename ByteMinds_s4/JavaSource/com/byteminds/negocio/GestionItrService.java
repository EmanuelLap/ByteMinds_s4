package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Evento;
import tecnofenix.entidades.Itr;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorTipo;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.Usuario;

@Stateless
@LocalBean
public class GestionItrService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EJBUsuarioRemoto ejbRemoto;

	public GestionItrService() {
	ejbRemoto = new EJBUsuarioRemoto();
	}
	
	public ItrDTO fromITR(Itr itr) {
		ItrDTO itrDTO = new ItrDTO();
		
		itrDTO.setId(itr.getId());
		itrDTO.setDepartamento(itr.getDepartamento());
		itrDTO.setNombre(itr.getNombre());
		itrDTO.setActivo(itr.getActivo());
		
		return itrDTO;
	}

	public Itr toITR(ItrDTO itrDTO) {
		Itr itr = new Itr();
		itr.setId(itrDTO.getId());
		itr.setDepartamento(itrDTO.getDepartamento());
		itr.setNombre(itrDTO.getNombre());
		itr.setActivo(itrDTO.getActivo());
		
		return itr;
	}


	public ItrDTO obtenerITRSeleccionado(Integer id) {
		ItrDTO itrDTO = new ItrDTO();
		Itr itr = new Itr();
		itr=ejbRemoto.obtenerITRporId(id);
		System.out.println("ITR DE LA BASE ="+itr.toString());
		itrDTO= fromITR(itr);
		System.out.println("ITRDTO DEVUELTO ="+itrDTO.toString());
		return itrDTO;
	}
	public List<ItrDTO> listarITRs() {
		List<Itr> listIRT = new ArrayList<Itr>();
		List<ItrDTO> listIRTDTO =new ArrayList<ItrDTO>();
		listIRT=ejbRemoto.listarITR();
		
		for(Itr itr : listIRT) {			
			listIRTDTO.add(fromITR(itr));	
		}
	
		return listIRTDTO;
	}

	
}
