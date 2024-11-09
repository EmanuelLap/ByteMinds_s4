package com.byteminds.negocio;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import tecnofenix.entidades.RegistroAsistencia;

public class ConvocatoriaAsistenciaEventoEstudianteDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer calificacion;

	private Boolean asistencia;

	@Enumerated(EnumType.STRING)
	private RegistroAsistencia registroAsistencia;

	private EventoDTO eventoDTO;

	private EstudianteDTO estudianteDTO;

	
	
	public ConvocatoriaAsistenciaEventoEstudianteDTO() {

	}
	
	public ConvocatoriaAsistenciaEventoEstudianteDTO( EventoDTO eventoDTO, EstudianteDTO estudianteDTO) {
		super();
		this.id = null;
		this.calificacion = null;
		this.asistencia = null;
		this.registroAsistencia = registroAsistencia.SIN_SELECCIONAR;
		this.eventoDTO = eventoDTO;
		this.estudianteDTO = estudianteDTO;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	public Boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}

	public RegistroAsistencia getRegistroAsistencia() {
		return registroAsistencia;
	}

	public void setRegistroAsistencia(RegistroAsistencia registroAsistencia) {
		this.registroAsistencia = registroAsistencia;
	}

	public EventoDTO getEventoDTO() {
		return eventoDTO;
	}

	public void setEventoDTO(EventoDTO eventoDTO) {
		this.eventoDTO = eventoDTO;
	}

	public EstudianteDTO getEstudianteDTO() {
		return estudianteDTO;
	}

	public void setEstudianteDTO(EstudianteDTO estudianteDTO) {
		this.estudianteDTO = estudianteDTO;
	}

	@Override
	public String toString() {
		return "ConvocatoriaAsistenciaEventoDTO [id=" + id + ", calificacion=" + calificacion + ", asistencia="
				+ asistencia + ", registroAsistencia=" + registroAsistencia + ", eventoDTO=" + eventoDTO
				+ ", estudianteDTO=" + estudianteDTO + "]";
	}

	
	
	
	
}
