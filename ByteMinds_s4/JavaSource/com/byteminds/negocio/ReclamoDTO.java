package com.byteminds.negocio;

import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tecnofenix.entidades.AccionReclamo;
import tecnofenix.entidades.Estudiante;
import tecnofenix.entidades.Evento;

public class ReclamoDTO {
	
		@NotNull
	    private Integer id;
	    
	    @NotNull
	    private String detalle;
	    
	    private Date fecha;
	    
	    @JsonIgnore
	    private Collection<AccionReclamoDTO> accionReclamoDTOCollection;
	    
	    private Evento eventoId;
	    
	    private EstudianteDTO estudianteId;

		public ReclamoDTO() {
			super();
		}

		public ReclamoDTO(@NotNull Integer id, @NotNull String detalle, Date fecha,
				Collection<AccionReclamoDTO> accionReclamoDTOCollection, Evento eventoId, EstudianteDTO estudianteId) {
			super();
			this.id = id;
			this.detalle = detalle;
			this.fecha = fecha;
			this.accionReclamoDTOCollection = accionReclamoDTOCollection;
			this.eventoId = eventoId;
			this.estudianteId = estudianteId;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
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

		public Collection<AccionReclamoDTO> getAccionReclamoDTOCollection() {
			return accionReclamoDTOCollection;
		}

		public void setAccionReclamoDTOCollection(Collection<AccionReclamoDTO> accionReclamoDTOCollection) {
			this.accionReclamoDTOCollection = accionReclamoDTOCollection;
		}

		public Evento getEventoId() {
			return eventoId;
		}

		public void setEventoId(Evento eventoId) {
			this.eventoId = eventoId;
		}

		public EstudianteDTO getEstudianteId() {
			return estudianteId;
		}

		public void setEstudianteId(EstudianteDTO estudianteId) {
			this.estudianteId = estudianteId;
		}
}
