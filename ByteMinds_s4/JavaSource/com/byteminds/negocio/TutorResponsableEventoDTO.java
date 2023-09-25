package com.byteminds.negocio;

import javax.validation.constraints.NotNull;


public class TutorResponsableEventoDTO {

	private Integer id;

	@NotNull
	private TutorDTO tutorId;

	@NotNull
	private Integer eventoId;
	
	
	public TutorResponsableEventoDTO() {
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public TutorDTO getTutorId() {
		return tutorId;
	}


	public void setTutorId(TutorDTO tutorId) {
		this.tutorId = tutorId;
	}


	public Integer getEventoId() {
		return eventoId;
	}


	public void setEventoId(Integer eventoId) {
		this.eventoId = eventoId;
	}
	
}
