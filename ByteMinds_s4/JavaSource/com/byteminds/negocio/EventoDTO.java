package com.byteminds.negocio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class EventoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotBlank(message = "El titulo no puede ser vacio")
	private String titulo;

//    @Enumerated(EnumType.STRING)
	@NotNull(message = "Seleccione el tipo de evento")
	private TipoEventoDTO tipoEvento;
//	SIN_SELECCIONAR(""),JORNADA_PRESENCIAL("Jornada presencial"), PRUEBA_FINAL("Prueba final"), EXAMEN("Examen"), DEFENSA_PROYECTO("Defensa de Proyecto");

//    @Enumerated(EnumType.STRING)
	@NotNull(message = "Seleccione la modalidad")
	private ModalidadEventoDTO modalidadEvento;
//	SIN_SELECCIONAR(""),VIRTUAL("Virtual"), PRESENCIAL("Presencial"), SEMI_PRESENCIAL("Semipresencial");
//    @Basic(optional = false)
	@NotNull(message = "Seleccione la fecha de inicio del evento")
	private Date inicio;

	private Date fin;

	private String localizacion;

	private Boolean bajaLogica;

	private ItrDTO itrDTO;

//    finalizado, corriente, futuro
	private TipoEstadoEventoDTO tipoEstadoEventoDTO;

//	private Collection<Justificacion> justificacionCollection;
//    
//    private Collection<ConvocatoriaAsistenciaEventoEstudiante> convocatoriaAsistenciaEventoEstudianteCollection;
//    
//    private Collection<Reclamo> reclamoCollection;
//    
//    private Collection<Constancia> constanciaCollection;
//    
	@JsonManagedReference
	private Collection<TutorResponsableEventoDTO> tutorResponsableEventoDTOCollection;
//    
//    private Collection<GestionEventoAnalista> gestionEventoAnalistaCollection;

	public EventoDTO() {
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

	public EventoDTO(Integer id, @NotNull String titulo, @NotNull TipoEventoDTO tipoEvento,
			@NotNull ModalidadEventoDTO modalidadEvento, @NotNull Date inicio, Date fin, String localizacion,
			Boolean bajaLogica, ItrDTO itr, TipoEstadoEventoDTO tipoEstadoEvento) {
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

	public TipoEventoDTO getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEventoDTO tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public ModalidadEventoDTO getModalidadEvento() {
		return modalidadEvento;
	}

	public void setModalidadEvento(ModalidadEventoDTO modalidadEvento) {
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

	public ItrDTO getItrDTO() {
		return itrDTO;
	}

	public void setItrDTO(ItrDTO itrDTO) {
		this.itrDTO = itrDTO;
	}

	public TipoEstadoEventoDTO getTipoEstadoEvento() {
		return tipoEstadoEventoDTO;
	}

	public void setTipoEstadoEventoDTO(TipoEstadoEventoDTO tipoEstadoEventoDTO) {
		this.tipoEstadoEventoDTO = tipoEstadoEventoDTO;
	}

	public Collection<TutorResponsableEventoDTO> getTutorResponsableEventoDTOCollection() {
		return tutorResponsableEventoDTOCollection;
	}

	public void setTutorResponsableEventoDTOCollection(
			Collection<TutorResponsableEventoDTO> tutorResponsableEventoDTOCollection) {
		this.tutorResponsableEventoDTOCollection = tutorResponsableEventoDTOCollection;
	}

	@Override
	public String toString() {
		return "EventoDTO [id=" + id + ", título=" + titulo + ", inicio=" + inicio + ", localización=" + localizacion
				+ "]";
	}
	
	public String toStringCombo() {
		String formatoDeseado = "dd/MM/yyyy";
        SimpleDateFormat formateador = new SimpleDateFormat(formatoDeseado);
        String fechaFormateada = formateador.format(inicio);
        
		return " " + id + ", Título:" + titulo + ", Inicio:" + fechaFormateada + ", Localización:" + localizacion ;
	}

}
