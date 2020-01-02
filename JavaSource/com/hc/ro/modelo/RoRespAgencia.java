package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the ro_resp_agencia database table.
 * 
 */
@Entity
@Table(name = "ro_resp_agencia")
@NamedQuery(name = "RoRespAgencia.findAll", query = "SELECT r FROM RoRespAgencia r")
public class RoRespAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_REAG")
	private int codigoReag;

	// bi-directional many-to-one association to RoResponsable
	@ManyToOne
	@JoinColumn(name = "CODIGO_RESP")
	private RoResponsable roResponsable;

	// bi-directional many-to-one association to RoAgencia
	@ManyToOne
	@JoinColumn(name = "CODIGO_AGIA")
	private RoAgencia roAgencia;

	public RoRespAgencia() {
	}

	public RoRespAgencia(Integer codigo, RoAgencia agencia) {
		this.codigoReag = codigo;
		this.roAgencia = agencia;
	}

	public int getCodigoReag() {
		return this.codigoReag;
	}

	public void setCodigoReag(int codigoReag) {
		this.codigoReag = codigoReag;
	}

	public RoResponsable getRoResponsable() {
		return this.roResponsable;
	}

	public void setRoResponsable(RoResponsable roResponsable) {
		this.roResponsable = roResponsable;
	}

	public RoAgencia getRoAgencia() {
		return this.roAgencia;
	}

	public void setRoAgencia(RoAgencia roAgencia) {
		this.roAgencia = roAgencia;
	}
}