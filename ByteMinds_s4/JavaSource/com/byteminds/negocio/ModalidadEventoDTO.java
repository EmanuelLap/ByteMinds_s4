package com.byteminds.negocio;

import javax.validation.constraints.NotNull;

public class ModalidadEventoDTO {

	private Integer id;

    @NotNull
    private String nombre;

    private Boolean activo;
    
    
    public ModalidadEventoDTO() {
//		this.id=1;
//		this.nombre="Evento inicial new";
//		this.activo=true;
	}
    
    

	public ModalidadEventoDTO(Integer id, @NotNull String nombre, Boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
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


	public Boolean getActivo() {
		return activo;
	}


	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
    
    
}
