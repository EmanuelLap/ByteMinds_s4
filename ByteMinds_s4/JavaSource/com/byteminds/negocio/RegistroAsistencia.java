package com.byteminds.negocio;


public enum RegistroAsistencia {

	SIN_SELECCIONAR(""),
	ASISTENCIA("Asistencia"), 
	MEDIA_ASISTENCIA("Media asistencia"),
	AUSENCIA("Ausencia"),
	AUSENCIA_JUSTIFICADA("Ausencia justificada");
	
	private String asistencia;
	
	private RegistroAsistencia(String asistencia) {
		this.setAsistencia(asistencia);
	}

	public String getAsistencia() {
		return asistencia;
		
	}

	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
		
	}
	
	public static RegistroAsistencia fromString(String text) {
		for (RegistroAsistencia as :RegistroAsistencia.values()) {
			if (as.asistencia.equalsIgnoreCase(text)) {
				return as;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.asistencia;
	}
}
