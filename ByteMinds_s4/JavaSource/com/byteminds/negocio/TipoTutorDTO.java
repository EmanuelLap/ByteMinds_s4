package com.byteminds.negocio;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TipoTutorDTO {

    @NotNull
    private Integer id;


    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;


    @NotNull
    private Boolean bajaLogica;
    
    public TipoTutorDTO() {
		
	}
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getBajaLogica() {
		return bajaLogica;
	}

	public void setBajaLogica(Boolean bajaLogica) {
		this.bajaLogica = bajaLogica;
	}
    
    
}
