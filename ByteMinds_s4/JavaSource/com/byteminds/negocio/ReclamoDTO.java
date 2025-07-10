package com.byteminds.negocio;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

public class ReclamoDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@NotNull
	private Integer id;

	@NotBlank (message = "El titulo del reclamo es obligatorio")
	@Length(min = 1, max = 40 , message = "El titulo del reclamo debe tener entre 1 y 40 caracteres")
	private String titulo;

	@NotBlank (message = "El detalle del reclamo es obligatorio")
	@Size(min = 1, max = 45, message = "El detalle del reclamo debe tener entre menos de 200 caracteres")
	private String detalle;

	private Date fecha;

//	    @JsonIgnore
//	    private Collection<AccionReclamoDTO> accionReclamoDTOCollection;

	private EventoDTO eventoId;

	private EstudianteDTO estudianteId;

	@NotNull (message = "Los creditos son obligatorios")
	private Integer creditos;

	@NotNull (message = "El semestre es obligatorio")
	private Integer semestre;

	private Boolean activo;

	private TipoEstadoReclamoDTO estadoReclamoId;

	private Date fechaEstadoReclamo;

	public ReclamoDTO() {
		super();

	}

	public ReclamoDTO(@NotNull Integer id, @NotNull String detalle, Date fecha,
			/* Collection<AccionReclamoDTO> accionReclamoDTOCollection, */ EventoDTO eventoId,
			EstudianteDTO estudianteId) {
		super();
		this.id = id;
		this.detalle = detalle;
		this.fecha = fecha;
//			this.accionReclamoDTOCollection = accionReclamoDTOCollection;
		this.eventoId = eventoId;
		this.estudianteId = estudianteId;
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

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}
//		public Collection<AccionReclamoDTO> getAccionReclamoDTOCollection() {
//			return accionReclamoDTOCollection;
//		}
//
//		public void setAccionReclamoDTOCollection(Collection<AccionReclamoDTO> accionReclamoDTOCollection) {
//			this.accionReclamoDTOCollection = accionReclamoDTOCollection;
//		}

	public EventoDTO getEventoId() {
		return eventoId;
	}

	public void setEventoId(EventoDTO eventoId) {
		this.eventoId = eventoId;
	}

	public EstudianteDTO getEstudianteId() {
		return estudianteId;
	}

	public void setEstudianteId(EstudianteDTO estudianteId) {
		this.estudianteId = estudianteId;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public TipoEstadoReclamoDTO getEstadoReclamoId() {
		return estadoReclamoId;
	}

	public void setEstadoReclamoId(TipoEstadoReclamoDTO estadoReclamoId) {
		this.estadoReclamoId = estadoReclamoId;
	}

	public Date getFechaEstadoReclamo() {
		return fechaEstadoReclamo;
	}

	public void setFechaEstadoReclamo(Date fechaEstadoReclamo) {
		this.fechaEstadoReclamo = fechaEstadoReclamo;
	}

}
