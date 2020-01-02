package com.hc.ro.converters;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.hc.ro.modelo.RoProceso;
import com.hc.ro.negocio.ServicioRoProceso;

@FacesConverter(value = "roProcesoConvertidor")
public class RoProcesoConvertidor implements Converter {
	
	@EJB
	ServicioRoProceso servicioRoProceso;
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {
		List<RoProceso> procesos=servicioRoProceso.buscarTodos();
		
		if (submittedValue.trim().equals("")) {
			return null;
		} else {
			try {
				int number = Integer.parseInt(submittedValue);

				for (RoProceso m : procesos) {
					if (m.getCodigoPros() == number) {
						return m;
					}
				}

			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error",
						"No es un Proceso Válido"));
			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((RoProceso) value).getCodigoPros());
		}
	}

}