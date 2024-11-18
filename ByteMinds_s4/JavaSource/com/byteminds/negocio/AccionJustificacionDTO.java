package com.byteminds.negocio;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccionJustificacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotNull
	@Size(min = 1, max = 250)
	private String detalle;

	@NotNull
	private Date fecha;

	@JsonIgnore
	private JustificacionDTO justificacionId;

	private AnalistaDTO analistaId;

	private Boolean activo;

	public AccionJustificacionDTO() {

	}

	public AccionJustificacionDTO(Integer id, @NotNull @Size(min = 1, max = 45) String detalle, @NotNull Date fecha,
			JustificacionDTO justificacionId, AnalistaDTO analistaId) {
		super();
		this.id = id;
		this.detalle = detalle;
		this.fecha = fecha;
		this.justificacionId = justificacionId;
		this.analistaId = analistaId;
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

	public JustificacionDTO getJustificacionId() {
		return justificacionId;
	}

	public void setJustificacionId(JustificacionDTO justificacionId) {
		this.justificacionId = justificacionId;
	}

	public AnalistaDTO getAnalistaId() {
		return analistaId;
	}

	public void setAnalistaId(AnalistaDTO analistaId) {
		this.analistaId = analistaId;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
