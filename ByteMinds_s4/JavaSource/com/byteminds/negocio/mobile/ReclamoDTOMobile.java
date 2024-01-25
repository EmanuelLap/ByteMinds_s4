package com.byteminds.negocio.mobile;

import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.NotNull;


public class ReclamoDTOMobile {
	
		@NotNull
	    private Integer id;
		
		@NotNull
	    private String titulo;
		
		@NotNull
	    private String detalle;
	    
	    private Date fecha;
	    

	    
	    private Integer eventoId;
	    
	    private Integer estudianteId;
	    
	    private Integer creditos;
	    
	    private Integer semestre;
	    
	    private Boolean activo;

		public ReclamoDTOMobile() {
			super();
					
		}

		public ReclamoDTOMobile(@NotNull Integer id, @NotNull String detalle, Date fecha,
				/*Collection<AccionReclamoDTO> accionReclamoDTOCollection,*/ Integer eventoId, Integer estudianteId) {
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

		public Integer getEventoId() {
			return eventoId;
		}

		public void setEventoId(Integer eventoId) {
			this.eventoId = eventoId;
		}

		public Integer getEstudianteId() {
			return estudianteId;
		}

		public void setEstudianteId(Integer estudianteId) {
			this.estudianteId = estudianteId;
		}

		public Boolean getActivo() {
			return activo;
		}

		public void setActivo(Boolean activo) {
			this.activo = activo;
		}
}
