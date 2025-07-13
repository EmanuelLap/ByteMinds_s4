package com.byteminds.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.byteminds.bean.CombosBean;
import com.byteminds.negocio.EventoDTO;


@FacesConverter(value = "eventoConverter", forClass = EventoDTO.class)
public class EventoConverter implements Converter<EventoDTO> {

    @Override
    public EventoDTO getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty() || value.equals("-1")) {
            return null;
        }

        CombosBean combosBean = context.getApplication()
            .evaluateExpressionGet(context, "#{combosBean}", CombosBean.class);

        for (EventoDTO itr : combosBean.getListaEventos()) {
            if (itr.getId() != null && itr.getId().toString().equals(value)) {
                return itr;
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, EventoDTO value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : "-1";
    }
}
