package com.hc.ro.utils;

import java.util.List;

import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoEvento;

public class CruceDetalleEvento {
	private RoEvento roEvento;
	private List<RoDetalleEvento> roDetalleEventos;
	private ParamProbabilidadRiesgo paramProbabilidadRiesgo;
	private ParamConsecuenciaImpacto paramConsecuenciaImpacto;
	private float severidad;
	private float severidadResidual;

	public CruceDetalleEvento() {
		super();
	}

	public RoEvento getRoEvento() {
		return roEvento;
	}

	public void setRoEvento(RoEvento roEvento) {
		this.roEvento = roEvento;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public float getSeveridad() {
		return severidad;
	}

	public void setSeveridad(float severidad) {
		this.severidad = severidad;
	}

	public float getSeveridadResidual() {
		return severidadResidual;
	}

	public void setSeveridadResidual(float severidadResidual) {
		this.severidadResidual = severidadResidual;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgo() {
		return paramProbabilidadRiesgo;
	}

	public void setParamProbabilidadRiesgo(
			ParamProbabilidadRiesgo paramProbabilidadRiesgo) {
		this.paramProbabilidadRiesgo = paramProbabilidadRiesgo;
	}

	public ParamConsecuenciaImpacto getParamConsecuenciaImpacto() {
		return paramConsecuenciaImpacto;
	}

	public void setParamConsecuenciaImpacto(
			ParamConsecuenciaImpacto paramConsecuenciaImpacto) {
		this.paramConsecuenciaImpacto = paramConsecuenciaImpacto;
	}

}
