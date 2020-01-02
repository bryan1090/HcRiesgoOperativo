package com.hc.ro.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoAriesgo;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoNegocio;

public class CruceMapaDeRiesgo {
	private List<RoEvento> roEventos;
	private List<RoDetalleEvento> detalleEventos;
	private ParamConsecuenciaImpacto paramConsecuenciaImpacto;
	private ParamProbabilidadRiesgo paramProbabilidadRiesgo;
	private RoAriesgo roAriesgo;
	private RoEvento roEvento;
	private RoNegocio roNegocio;
	private BigDecimal porcentajeCruce;
	private BigDecimal severidadCruce;
	private BigDecimal porcentajeSeveridadCruce;
	private Date fecha;
	private RoAgencia roAgencia;
	
	private BigDecimal perdida;
	
	private int numeroOcurrencias;
	private List<RoEvento> EventosHijo;
	
	public CruceMapaDeRiesgo() {
		super();
		
	}

	public ParamConsecuenciaImpacto getParamConsecuenciaImpacto() {
		return paramConsecuenciaImpacto;
	}

	public void setParamConsecuenciaImpacto(
			ParamConsecuenciaImpacto paramConsecuenciaImpacto) {
		this.paramConsecuenciaImpacto = paramConsecuenciaImpacto;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgo() {
		return paramProbabilidadRiesgo;
	}

	public void setParamProbabilidadRiesgo(
			ParamProbabilidadRiesgo paramProbabilidadRiesgo) {
		this.paramProbabilidadRiesgo = paramProbabilidadRiesgo;
	}

	public RoAriesgo getRoAriesgo() {
		return roAriesgo;
	}

	public void setRoAriesgo(RoAriesgo roAriesgo) {
		this.roAriesgo = roAriesgo;
	}

	public List<RoEvento> getRoEventos() {
		return roEventos;
	}

	public void setRoEventos(List<RoEvento> roEventos) {
		this.roEventos = roEventos;
	}

	public List<RoDetalleEvento> getDetalleEventos() {
		return detalleEventos;
	}

	public void setDetalleEventos(List<RoDetalleEvento> detalleEventos) {
		this.detalleEventos = detalleEventos;
	}

	public RoEvento getRoEvento() {
		return roEvento;
	}

	public void setRoEvento(RoEvento roEvento) {
		this.roEvento = roEvento;
	}

	public RoNegocio getRoNegocio() {
		return roNegocio;
	}

	public void setRoNegocio(RoNegocio roNegocio) {
		this.roNegocio = roNegocio;
	}

	public BigDecimal getPorcentajeCruce() {
		return porcentajeCruce;
	}

	public void setPorcentajeCruce(BigDecimal porcentajeCruce) {
		this.porcentajeCruce = porcentajeCruce;
	}

	public BigDecimal getSeveridadCruce() {
		return severidadCruce;
	}

	public void setSeveridadCruce(BigDecimal severidadCruce) {
		this.severidadCruce = severidadCruce;
	}

	public BigDecimal getPorcentajeSeveridadCruce() {
		return porcentajeSeveridadCruce;
	}

	public void setPorcentajeSeveridadCruce(BigDecimal porcentajeSeveridadCruce) {
		this.porcentajeSeveridadCruce = porcentajeSeveridadCruce;
	}

	public int getNumeroOcurrencias() {
		return numeroOcurrencias;
	}

	public void setNumeroOcurrencias(int numeroOcurrencias) {
		this.numeroOcurrencias = numeroOcurrencias;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public RoAgencia getRoAgencia() {
		return roAgencia;
	}

	public void setRoAgencia(RoAgencia roAgencia) {
		this.roAgencia = roAgencia;
	}

	public List<RoEvento> getEventosHijo() {
		return EventosHijo;
	}

	public void setEventosHijo(List<RoEvento> eventosHijo) {
		EventosHijo = eventosHijo;
	}

	public BigDecimal getPerdida() {
		return perdida;
	}

	public void setPerdida(BigDecimal perdida) {
		this.perdida = perdida;
	}



}
