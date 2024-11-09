package com.byteminds.negocio;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class JustificacionDTO  implements Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@NotNull
	    private Integer id;
		
//		@NotNull
//	    private String titulo;
		
		@NotNull
		@Length(min = 1, max = 255)
	    private String detalle;
	    
	    private Date fecha;
	    
//	    @JsonIgnore
//	    private Collection<AccionReclamoDTO> accionReclamoDTOCollection;
	    
	    private EventoDTO eventoId;
	    
	    private EstudianteDTO estudianteId;
	        
	    private Boolean activo;

	    private TipoEstadoJustificacionDTO estadoJustificacionId;
	    
	    private Date fechaEstadoJustificacion;
	    
		public JustificacionDTO() {
			super();
					
		}

		public JustificacionDTO(@NotNull Integer id, @NotNull String detalle, Date fecha,
				/*Collection<AccionReclamoDTO> accionReclamoDTOCollection,*/ EventoDTO eventoId, EstudianteDTO estudianteId) {
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
//	    public String getTitulo() {
//			return titulo;
//		}
//
//		public void setTitulo(String titulo) {
//			this.titulo = titulo;
//		}
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

		public TipoEstadoJustificacionDTO getEstadoJustificacionId() {
			return estadoJustificacionId;
		}

		public void setEstadoJustificacionId(TipoEstadoJustificacionDTO estadoJustificacionId) {
			this.estadoJustificacionId = estadoJustificacionId;
		}

		public Date getFechaEstadoJustificacion() {
			return fechaEstadoJustificacion;
		}

		public void setFechaEstadoJustificacion(Date fechaEstadoJustificacion) {
			this.fechaEstadoJustificacion = fechaEstadoJustificacion;
		}

		
		
		
}
