package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ro_deve_faro")
@NamedQuery(name = "RoDeveFaro.findAll", query = "SELECT r FROM RoDeveFaro r")
public class RoDeveFaro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_DEFA")
	private int codigoDefa;

	@Column(name = "ORDEN_DEFA")
	private int ordenDefa;

	@ManyToOne
	@JoinColumn(name = "CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;

	@ManyToOne
	@JoinColumn(name = "CODIGO_FARO")
	private RoFactorRiesgo roFactorRiesgo;

	public int getCodigoDefa() {
		return codigoDefa;
	}

	public void setCodigoDefa(int codigoDefa) {
		this.codigoDefa = codigoDefa;
	}

	public int getOrdenDefa() {
		return ordenDefa;
	}

	public void setOrdenDefa(int ordenDefa) {
		this.ordenDefa = ordenDefa;
	}

	public RoDetalleEvento getRoDetalleEvento() {
		return roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

	public RoFactorRiesgo getRoFactorRiesgo() {
		return roFactorRiesgo;
	}

	public void setRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		this.roFactorRiesgo = roFactorRiesgo;
	}

}
