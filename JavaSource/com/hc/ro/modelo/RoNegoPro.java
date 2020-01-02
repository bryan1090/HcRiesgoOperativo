package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the ro_nego_pros database table.
 * 
 */
@Entity
@Table(name = "ro_nego_pros")
@NamedQuery(name = "RoNegoPro.findAll", query = "SELECT r FROM RoNegoPro r")
public class RoNegoPro implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int CODIGO_nego_pros;

	// bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name = "CODIGO_NEGO")
	private RoNegocio roNegocio;

	// bi-directional many-to-one association to Proceso
	@ManyToOne
	@JoinColumn(name = "CODIGO_PROS")
	private RoProceso roProceso;

	public RoNegoPro() {
	}

	public RoNegoPro(Integer integer, RoNegocio roNegocio2) {
		this.CODIGO_nego_pros = integer;
		this.roNegocio = roNegocio2;
	}

	public int getCODIGO_nego_pros() {
		return this.CODIGO_nego_pros;
	}

	public void setCODIGO_nego_pros(int CODIGO_nego_pros) {
		this.CODIGO_nego_pros = CODIGO_nego_pros;
	}

	public RoNegocio getRoNegocio() {
		return roNegocio;
	}

	public void setRoNegocio(RoNegocio roNegocio) {
		this.roNegocio = roNegocio;
	}

	public RoProceso getRoProceso() {
		return roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

}