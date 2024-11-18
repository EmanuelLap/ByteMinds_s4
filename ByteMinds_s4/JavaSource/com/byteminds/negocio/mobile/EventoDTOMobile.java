package com.byteminds.negocio.mobile;

import java.util.ArrayList;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class EventoDTOMobile {


	private Integer id;

	@NotNull(message = "El titulo no puede ser vacio")
	private String titulo;

//    @Enumerated(EnumType.STRING)
	@NotNull(message = "Seleccione el tipo de evento")
	private Integer tipoEvento;
//	SIN_SELECCIONAR(""),JORNADA_PRESENCIAL("Jornada presencial"), PRUEBA_FINAL("Prueba final"), EXAMEN("Examen"), DEFENSA_PROYECTO("Defensa de Proyecto");
	
//    @Enumerated(EnumType.STRING)
	@NotNull(message = "Seleccione la modalidad")
	private Integer modalidadEvento;
//	SIN_SELECCIONAR(""),VIRTUAL("Virtual"), PRESENCIAL("Presencial"), SEMI_PRESENCIAL("Semipresencial");
//    @Basic(optional = false)
	@NotNull (message = "Seleccione la fecha de inicio del evento")
	private Date inicio;

	private Date fin;

	private String localizacion;

	private Boolean bajaLogica;

	private Integer itrDTO;

//    finalizado, corriente, futuro
	private Integer tipoEstadoEventoDTO;

//	private Collection<Justificacion> justificacionCollection;
//    
//    private Collection<ConvocatoriaAsistenciaEventoEstudiante> convocatoriaAsistenciaEventoEstudianteCollection;
//    
//    private Collection<Reclamo> reclamoCollection;
//    
//    private Collection<Constancia> constanciaCollection;
//    
    private ArrayList<Integer> tutorResponsableEventoDTOCollection;//Lista Solo de IDS de Tutores
//    
//    private Collection<GestionEventoAnalista> gestionEventoAnalistaCollection;

	

	public EventoDTOMobile() {
//		this.id = 0;
//		this.titulo = "";
//		this.tipoEvento = "";
//		this.modalidadEvento = "";
//		this.inicio = new Date(System.currentTimeMillis());
//		this.fin = new Date(System.currentTimeMillis());
//		this.localizacion = "";
//		this.bajaLogica = false;
//		this.itrDTO = new ItrDTO();
//		this.tipoEstadoEventoDTO = new TipoEstadoEventoDTO();
	}

public EventoDTOMobile(Integer id, @NotNull String titulo, @NotNull Integer tipoEvento, @NotNull Integer modalidadEvento,
		@NotNull Date inicio, Date fin, String localizacion, Boolean bajaLogica, Integer itr,
		Integer tipoEstadoEvento) {
	super();
	this.id = id;
	this.titulo = titulo;
	this.tipoEvento = tipoEvento;
	this.modalidadEvento = modalidadEvento;
	this.inicio = inicio;
	this.fin = fin;
	this.localizacion = localizacion;
	this.bajaLogica = bajaLogica;
	this.itrDTO = itr;
	this.tipoEstadoEventoDTO = tipoEstadoEvento;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getTitulo() {
	return titulo;
}

public void setTitulo(String titulo) {
	this.titulo = titulo;
}

public Integer getTipoEvento() {
	return tipoEvento;
}

public void setTipoEvento(Integer tipoEvento) {
	this.tipoEvento = tipoEvento;
}

public Integer getModalidadEvento() {
	return modalidadEvento;
}

public void setModalidadEvento(Integer modalidadEvento) {
	this.modalidadEvento = modalidadEvento;
}

public Date getInicio() {
	return inicio;
}

public void setInicio(Date inicio) {
	this.inicio = inicio;
}

public Date getFin() {
	return fin;
}

public void setFin(Date fin) {
	this.fin = fin;
}

public String getLocalizacion() {
	return localizacion;
}

public void setLocalizacion(String localizacion) {
	this.localizacion = localizacion;
}

public Boolean getBajaLogica() {
	return bajaLogica;
}

public void setBajaLogica(Boolean bajaLogica) {
	this.bajaLogica = bajaLogica;
}

public Integer getItrDTO() {
	return itrDTO;
}

public void setItrDTO(Integer itrDTO) {
	this.itrDTO = itrDTO;
}

public Integer getTipoEstadoEvento() {
	return tipoEstadoEventoDTO;
}

public void setTipoEstadoEventoDTO(Integer tipoEstadoEventoDTO) {
	this.tipoEstadoEventoDTO = tipoEstadoEventoDTO;
}
public ArrayList<Integer> getTutorResponsableEventoDTOCollection() {
	return tutorResponsableEventoDTOCollection;
}

public void setTutorResponsableEventoDTOCollection(
		ArrayList<Integer> tutorResponsableEventoDTOCollection) {
	this.tutorResponsableEventoDTOCollection = tutorResponsableEventoDTOCollection;
}

@Override
public String toString() {
	return "EventoDTO [id=" + id + ", titulo=" + titulo + ", inicio=" + inicio + ", localizacion=" + localizacion + ", TUTORES="+tutorResponsableEventoDTOCollection.toString()+" ]";
}


	
	
	
}
