package com.hc.ro.converters;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.hc.ro.dataManager.CriticidadDataManager;
import com.hc.ro.modelo.RoCriticidad;
import com.hc.ro.negocio.ServicioRoCriticidad;

@FacesConverter(value = "roCriticidadConvertidor")
public class RoCriticidadConvertidor implements Converter {
	
	@EJB
	ServicioRoCriticidad servicioRoCriticidad;
	
	private CriticidadDataManager criticidadDataManager;
	
	public RoCriticidadConvertidor() {
		criticidadDataManager = (CriticidadDataManager) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("criticidadDataManager");
	}

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) {
		List <RoCriticidad> criticidades=criticidadDataManager.getCriticidades();
		System.out.println(">>>>>>Entrando al metodo getAsObject<<<<<<<");
		if (submittedValue.trim().equals("")) {
			return null;
		} else {
			try {
				int number = Integer.parseInt(submittedValue);

				for (RoCriticidad m : criticidades) {
					if (m.getCodigoCrit() == number) {
						return m;
					}
				}

			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error",
						"No es una Criticidad Válida"));
			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		System.out.println(">>>>>>Entrando al metodo getAsString<<<<<<<");
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((RoCriticidad) value).getCodigoCrit());
		}
	}
}