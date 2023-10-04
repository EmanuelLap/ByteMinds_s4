package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.byteminds.remoto.EJBUsuarioRemoto;
import tecnofenix.entidades.Funcionalidad;
import tecnofenix.entidades.Rol;

@Stateless
@LocalBean
public class GestionRolService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EJBUsuarioRemoto ejbRemoto;

	public GestionRolService() {
	ejbRemoto = new EJBUsuarioRemoto();
	}
	
	
	
	public RolDTO fromRol(Rol rol) {
		RolDTO rolDTO = new RolDTO();

		GestionFuncionalidadesService funcionalidad = new GestionFuncionalidadesService();

//		System.out.println("Transformando informacion de ROl");
//		System.out.println("Rol id" + rol.getId());
//		System.out.println("Rol nombre" + rol.getNombre());
//		System.out.println("Rol Descripcion" + rol.getDescripcion());
		rolDTO.setId(rol.getId());
		rolDTO.setNombre(rol.getNombre());
		rolDTO.setDescripcion(rol.getDescripcion());

		List<FuncionalidadDTO> listafuncionalidades = new ArrayList<FuncionalidadDTO>();
		for (Funcionalidad fun : rol.getFuncionalidades()) {
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
		for (FuncionalidadDTO fun : rolDTO.getFuncionalidades()) {
			listafuncionalidades.add(funcionalidad.toFuncionalidad(fun));
		}

		rol.setFuncionalidades(listafuncionalidades);
		rol.setActivo(rolDTO.getActivo());

		return rol;
	}

	// servicios para capa de Presentacion

	public RolDTO obtenerRolSeleccionado(Integer idRol) {
		Rol rol = new Rol();
		
		rol=ejbRemoto.obtenerRolPorId(idRol);
		
		return fromRol(rol);
	}

}
