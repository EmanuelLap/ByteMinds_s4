package com.byteminds.utils;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.TutorDTO;


@FacesConverter(value = "tutorConverter")
public class TutorConverter implements Converter {

    @EJB
    private GestionUsuarioService gestionUsuarioService = new GestionUsuarioService(); // Suponiendo que tienes un servicio EJB para obtener tutores por ID

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return (TutorDTO)gestionUsuarioService.buscarUsuario(Integer.valueOf(value)); // Obtener el tutor por ID
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || !(value instanceof TutorDTO)) {
            return "";
        }
        return String.valueOf(((TutorDTO) value).getId()); // Retornar el ID del tutor como String
    }
}
