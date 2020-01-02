package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the ro_resp_depa database table.
 * 
 */
@Entity
@Table(name = "ro_resp_depa")
@NamedQuery(name = "RoRespDepa.findAll", query = "SELECT r FROM RoRespDepa r")
public class RoRespDepa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_resp_depa")
	private int CODIGO_resp_depa;

	// bi-directional many-to-one association to Responsable
	@ManyToOne
	@JoinColumn(name = "CODIGO_resp")
	private RoResponsable roResponsable;

	// bi-directional many-to-one association to Departamento
	@ManyToOne
	@JoinColumn(name = "CODIGO_dept")
	private RoDepartamento roDepartamento;

	public RoResponsable getRoResponsable() {
		return roResponsable;
	}

	public void setRoResponsable(RoResponsable roResponsable) {
		this.roResponsable = roResponsable;
	}

	public RoDepartamento getRoDepartamento() {
		return roDepartamento;
	}

	public void setRoDepartamento(RoDepartamento roDepartamento) {
		this.roDepartamento = roDepartamento;
	}

	public RoRespDepa() {
	}

	public RoRespDepa(Integer integer, RoDepartamento roDepartamento) {
		this.CODIGO_resp_depa = integer;
		this.roDepartamento = roDepartamento;
	}

	public int getCODIGO_resp_depa() {
		return this.CODIGO_resp_depa;
	}

	public void setCODIGO_resp_depa(int CODIGO_resp_depa) {
		this.CODIGO_resp_depa = CODIGO_resp_depa;
	}

}