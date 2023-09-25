package com.byteminds.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.byteminds.negocio.GestionEventoService;


@SuppressWarnings("rawtypes")
@FacesConverter("eventoConverter")
public class EventoConverter implements Converter {
	

	private GestionEventoService gES;
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Aquí conviertes la cadena de texto a un objeto Evento
        // Implementa la lógica necesaria para convertir el valor a un objeto Evento
        // Por ejemplo, puedes buscar el Evento en una base de datos o en tu lista de eventos.
    	
    	gES = new GestionEventoService();

        return gES.obtenerEvento(Integer.valueOf(value)); // Debes adaptar esta parte según tu lógica de negocio.
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Aquí conviertes el objeto Evento de vuelta a una cadena de texto
        // Implementa la lógica necesaria para obtener la representación de cadena del Evento.
        // En este caso, simplemente se usa el método toString.
        return value.toString(); // Debes adaptar esta parte según tu lógica de negocio.
    }
}
