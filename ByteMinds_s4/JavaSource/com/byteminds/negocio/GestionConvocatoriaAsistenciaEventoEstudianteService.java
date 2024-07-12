package com.byteminds.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.byteminds.exception.PersistenciaException;
import com.byteminds.remoto.EJBUsuarioRemoto;

import tecnofenix.entidades.Analista;
import tecnofenix.entidades.ConvocatoriaAsistenciaEventoEstudiante;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Evento;
import tecnofenix.entidades.Itr;
import tecnofenix.entidades.TipoArea;
import tecnofenix.entidades.TipoTutorTipo;
import tecnofenix.entidades.Tutor;
import tecnofenix.entidades.TutorResponsableEvento;
import tecnofenix.entidades.Usuario;
import tecnofenix.exception.ServiciosException;

@Stateless
@LocalBean
public class GestionConvocatoriaAsistenciaEventoEstudianteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GestionEventoService gES;
	GestionUsuarioService gUS;
	EJBUsuarioRemoto ejbRemoto;
	
	
	public GestionConvocatoriaAsistenciaEventoEstudianteService() {
		gES = new GestionEventoService();
		gUS =new GestionUsuarioService();
		ejbRemoto = new EJBUsuarioRemoto();
	}

	public ConvocatoriaAsistenciaEventoEstudianteDTO fromConvocatoriaAsistenciaEventoEstudiante(ConvocatoriaAsistenciaEventoEstudiante convocatoriaAsistenciaEventoEstudiante) {
		
		ConvocatoriaAsistenciaEventoEstudianteDTO convocatoriaAsistenciaEventoEstudianteDTO = new ConvocatoriaAsistenciaEventoEstudianteDTO();
		
		convocatoriaAsistenciaEventoEstudianteDTO.setId(convocatoriaAsistenciaEventoEstudiante.getId());
		convocatoriaAsistenciaEventoEstudianteDTO.setEventoDTO(gES.fromEvento(convocatoriaAsistenciaEventoEstudiante.getEventoId()));
		convocatoriaAsistenciaEventoEstudianteDTO.setEstudianteDTO((EstudianteDTO)gUS.fromUsuario(convocatoriaAsistenciaEventoEstudiante.getEstudianteId()));
		convocatoriaAsistenciaEventoEstudianteDTO.setRegistroAsistencia(convocatoriaAsistenciaEventoEstudiante.getRegistroAsistencia());
		convocatoriaAsistenciaEventoEstudianteDTO.setAsistencia(convocatoriaAsistenciaEventoEstudiante.getAsistencia());
		convocatoriaAsistenciaEventoEstudianteDTO.setCalificacion(convocatoriaAsistenciaEventoEstudiante.getCalificacion());
		
		return convocatoriaAsistenciaEventoEstudianteDTO;
	}

	public ConvocatoriaAsistenciaEventoEstudiante toConvocatoriaAsistenciaEventoEstudiante(ConvocatoriaAsistenciaEventoEstudianteDTO convocatoriaAsistenciaEventoEstudianteDTO) {
		
		ConvocatoriaAsistenciaEventoEstudiante convocatoriaAsistenciaEventoEstudiante = new ConvocatoriaAsistenciaEventoEstudiante();
		
		convocatoriaAsistenciaEventoEstudiante.setId(convocatoriaAsistenciaEventoEstudianteDTO.getId());
		convocatoriaAsistenciaEventoEstudiante.setEventoId(gES.toEventoEntidad(convocatoriaAsistenciaEventoEstudianteDTO.getEventoDTO()));
		convocatoriaAsistenciaEventoEstudiante.setEstudianteId((Estudiante)gUS.toUsuario(convocatoriaAsistenciaEventoEstudianteDTO.getEstudianteDTO()));
		convocatoriaAsistenciaEventoEstudiante.setRegistroAsistencia(convocatoriaAsistenciaEventoEstudianteDTO.getRegistroAsistencia());
		convocatoriaAsistenciaEventoEstudiante.setAsistencia(convocatoriaAsistenciaEventoEstudianteDTO.getAsistencia());
		convocatoriaAsistenciaEventoEstudiante.setCalificacion(convocatoriaAsistenciaEventoEstudianteDTO.getCalificacion());
		
		return convocatoriaAsistenciaEventoEstudiante;
	}
	


	public List<ConvocatoriaAsistenciaEventoEstudianteDTO> listarAllConvocAsistenciaEventEstu() {
		List<ConvocatoriaAsistenciaEventoEstudianteDTO> listaDTO = new ArrayList<ConvocatoriaAsistenciaEventoEstudianteDTO>();
		List<ConvocatoriaAsistenciaEventoEstudiante> lista = new ArrayList<ConvocatoriaAsistenciaEventoEstudiante>();
		try {
			lista = ejbRemoto.listarAllConvocAsistenciaEventEstu() ;
			
			for (ConvocatoriaAsistenciaEventoEstudiante listTemp: lista) {
				listaDTO.add(this.fromConvocatoriaAsistenciaEventoEstudiante(listTemp));
	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaDTO;

	}
	public List<ConvocatoriaAsistenciaEventoEstudianteDTO> listarConvocatoriaEventEstuPorEvento(EventoDTO eventoId) {
		List<ConvocatoriaAsistenciaEventoEstudianteDTO> listaDTO = new ArrayList<ConvocatoriaAsistenciaEventoEstudianteDTO>();
		List<ConvocatoriaAsistenciaEventoEstudiante> lista = new ArrayList<ConvocatoriaAsistenciaEventoEstudiante>();
		try {
			
			lista = ejbRemoto.listarConvocatoriaEventEstuPorEvento(gES.toEventoEntidad(eventoId));
			
			for (ConvocatoriaAsistenciaEventoEstudiante listTemp: lista) {
				listaDTO.add(this.fromConvocatoriaAsistenciaEventoEstudiante(listTemp));
	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaDTO;

	}
	
	
	public List<ConvocatoriaAsistenciaEventoEstudianteDTO> filtrarAsistEstuAEventosPor(String id, String tituloEvento,String nombre,String apellido ,String documento, String valorLogico,String calificacion,String registroAsistencia){
		List<ConvocatoriaAsistenciaEventoEstudianteDTO> listaDTO = new ArrayList<ConvocatoriaAsistenciaEventoEstudianteDTO>();
		List<ConvocatoriaAsistenciaEventoEstudiante> lista = new ArrayList<ConvocatoriaAsistenciaEventoEstudiante>();
		try {
			lista = ejbRemoto.filtrarAsistEstuAEventosPor(id, tituloEvento, nombre, apellido,documento,valorLogico,calificacion, registroAsistencia);
			
			for (ConvocatoriaAsistenciaEventoEstudiante listTemp: lista) {
				listaDTO.add(this.fromConvocatoriaAsistenciaEventoEstudiante(listTemp));
	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaDTO;
	
	}	
	
	public List<UsuarioDTO> listarEstudiantesConvocadosEvento(Integer eventoID) {
		List<UsuarioDTO> listEstudiantesDTO = new ArrayList<UsuarioDTO>();
		List<Estudiante> listEstudiantes = new ArrayList<Estudiante>();
		
		listEstudiantes=ejbRemoto.listarEstudiantesConvocadosEvento(eventoID);
		
		for(Estudiante estTemp: listEstudiantes) {
			
			listEstudiantesDTO.add(gUS.fromUsuario(estTemp));
		}
		return listEstudiantesDTO;
	}

	public ConvocatoriaAsistenciaEventoEstudianteDTO agregarEstudianteAEvento(ConvocatoriaAsistenciaEventoEstudianteDTO convAsisEventEstuDTO) {
		ConvocatoriaAsistenciaEventoEstudiante conv = new ConvocatoriaAsistenciaEventoEstudiante();	
		conv = ejbRemoto.agregarEstudianteAEvento(toConvocatoriaAsistenciaEventoEstudiante(convAsisEventEstuDTO));
		return fromConvocatoriaAsistenciaEventoEstudiante(conv);
	}
		
	public ConvocatoriaAsistenciaEventoEstudianteDTO obtenerDatosConvPorId(Integer id) {
		
		return fromConvocatoriaAsistenciaEventoEstudiante(ejbRemoto.obtenerDatosConvPorId(id));
	}
	
	
}
