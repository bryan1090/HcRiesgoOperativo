package com.hc.ro.utils;

import java.util.ArrayList;
import java.util.List;

public class Matriz {
	public List<CruceMapaDeRiesgo> cruces;
	public String periodo;

	public List<CruceMapaDeRiesgo> getCruces() {
		return cruces;
	}

	public void setCruces(List<CruceMapaDeRiesgo> cruces) {
		this.cruces = cruces;
	}

	public Matriz() {
		super();
		cruces = new ArrayList<CruceMapaDeRiesgo>();
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

}
