package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa3Persistencia.exception.PersistenciaException;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Funcionalidad;
import tecnofenix.entidades.Rol;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorTipo;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.Usuario;

@Stateless
@LocalBean
public class GestionRolService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public RolDTO fromRol(Rol rol) {
		RolDTO rolDTO = new RolDTO();
		
		GestionFuncionalidadesService funcionalidad = new GestionFuncionalidadesService();
		
		rolDTO.setId(rol.getId());
		rolDTO.setNombre(rol.getNombre());
		rolDTO.setDescripcion(rol.getDescripcion());
		
		List<FuncionalidadDTO> listafuncionalidades = new ArrayList<FuncionalidadDTO>();
		for(Funcionalidad fun: rol.getFuncionalidades()) {
			listafuncionalidades.add(funcionalidad.fromFuncionalidad(fun));
		}
		
		rolDTO.setFuncionalidades(listafuncionalidades);
		rolDTO.setActivo(rol.getActivo());
		
		return rolDTO;
	}

	public Rol toRol(RolDTO rolDTO) {
		Rol rol = new Rol();
		GestionFuncionalidadesService funcionalidad = new GestionFuncionalidadesService();
		
		rol.setId(rolDTO.getId());
		rol.setNombre(rolDTO.getNombre());
		rol.setDescripcion(rolDTO.getDescripcion());
		
		List<Funcionalidad> listafuncionalidades = new ArrayList<Funcionalidad>();
		for(FuncionalidadDTO fun: rolDTO.getFuncionalidades()) {
			listafuncionalidades.add(funcionalidad.toFuncionalidad(fun));
		}
		
		rol.setFuncionalidades(listafuncionalidades);
		rol.setActivo(rolDTO.getActivo());
		
		
		return rol;
	}

	// servicios para capa de Presentacion



}
