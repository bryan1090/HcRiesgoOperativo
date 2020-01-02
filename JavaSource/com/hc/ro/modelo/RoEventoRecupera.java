package com.hc.ro.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the ro_evento_recupera database table.
 * 
 */
@Entity
@Table(name = "ro_evento_recupera")
@NamedQuery(name = "RoEventoRecupera.findAll", query = "SELECT r FROM RoEventoRecupera r")
public class RoEventoRecupera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_EVRE")
	private int codigoEvre;

	@Column(name = "VALOR_EVRE")
	private float valorEvre;

	@Column(name = "COSTO_EVRE")
	private float costoEvre;

	@Column(name = "TOTAL_EVRE")
	private float totalEvre;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_EVRE")
	private Date fechaEvre;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoDetalleEvento
	@ManyToOne
	@JoinColumn(name = "CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;

	// bi-directional many-to-one association to RoTipoRecupera
	@ManyToOne
	@JoinColumn(name = "CODIGO_TREC")
	private RoTipoRecupera roTipoRecupera;

	public RoEventoRecupera() {
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public RoDetalleEvento getRoDetalleEvento() {
		return this.roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

	public RoTipoRecupera getRoTipoRecupera() {
		return this.roTipoRecupera;
	}

	public void setRoTipoRecupera(RoTipoRecupera roTipoRecupera) {
		this.roTipoRecupera = roTipoRecupera;
	}

	public float getValorEvre() {
		return valorEvre;
	}

	public void setValorEvre(float valorEvre) {
		this.valorEvre = valorEvre;
	}

	public float getCostoEvre() {
		return costoEvre;
	}

	public void setCostoEvre(float costoEvre) {
		this.costoEvre = costoEvre;
	}

	public float getTotalEvre() {
		return totalEvre;
	}

	public void setTotalEvre(float totalEvre) {
		this.totalEvre = totalEvre;
	}

	public Date getFechaEvre() {
		return fechaEvre;
	}

	public void setFechaEvre(Date fechaEvre) {
		this.fechaEvre = fechaEvre;
	}

	public void setCodigoEvre(int codigoEvre) {
		this.codigoEvre = codigoEvre;
	}

	public int getCodigoEvre() {
		return codigoEvre;
	}

}