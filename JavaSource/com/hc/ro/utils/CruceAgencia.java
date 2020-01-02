package com.hc.ro.utils;

import java.util.List;

import com.hc.ro.modelo.RoAgencia;

public class CruceAgencia {

	private List<CruceMapaDeRiesgo> crucesPorAgencia;
	
	private RoAgencia agencia;

	public CruceAgencia() {
		super();
	}

	public List<CruceMapaDeRiesgo> getCrucesPorAgencia() {
		return crucesPorAgencia;
	}

	public void setCrucesPorAgencia(List<CruceMapaDeRiesgo> crucesPorAgencia) {
		this.crucesPorAgencia = crucesPorAgencia;
	}

	public RoAgencia getAgencia() {
		return agencia;
	}

	public void setAgencia(RoAgencia agencia) {
		this.agencia = agencia;
	}
	
}
