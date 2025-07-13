package com.byteminds.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.byteminds.bean.CombosBean;
import com.byteminds.negocio.ItrDTO;

@FacesConverter(value = "itrConverter", forClass = ItrDTO.class)
public class ItrConverter implements Converter<ItrDTO> {

    @Override
    public ItrDTO getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty() || value.equals("-1")) {
            return null;
        }

        CombosBean combosBean = context.getApplication()
            .evaluateExpressionGet(context, "#{combosBean}", CombosBean.class);

        for (ItrDTO itr : combosBean.getListaItr()) {
            if (itr.getId() != null && itr.getId().toString().equals(value)) {
                return itr;
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ItrDTO value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : "-1";
    }
}
