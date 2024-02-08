package com.byteminds.negocio;

import java.util.Date;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class AccionReclamoDTO {


    private Integer id;

    @NotNull
    @Size(min = 1, max = 250)
    private String detalle;
  
    @NotNull
    private Date fecha;
    
    @JsonIgnore
    private ReclamoDTO reclamoId;
    

    private AnalistaDTO analistaId;

    private Boolean activo;
    
    public AccionReclamoDTO() {
		
	}

	public AccionReclamoDTO(Integer id, @NotNull @Size(min = 1, max = 45) String detalle, @NotNull Date fecha,
			ReclamoDTO reclamoId, AnalistaDTO analistaId) {
		super();
		this.id = id;
		this.detalle = detalle;
		this.fecha = fecha;
		this.reclamoId = reclamoId;
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


	public ReclamoDTO getReclamoId() {
		return reclamoId;
	}


	public void setReclamoId(ReclamoDTO reclamoId) {
		this.reclamoId = reclamoId;
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
