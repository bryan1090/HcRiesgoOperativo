package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ro_evento_objetivos database table.
 * 
 */
@Entity
@Table(name="ro_evento_objetivos")
@NamedQuery(name="RoEventoObjetivo.findAll", query="SELECT r FROM RoEventoObjetivo r")
public class RoEventoObjetivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_EVOB")
	private int codigoEvob;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoDetalleEvento
	@ManyToOne
	@JoinColumn(name="CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;

	//bi-directional many-to-one association to RoCatObjetivo
	@ManyToOne
	@JoinColumn(name="CODIGO_COBJ")
	private RoCatObjetivo roCatObjetivo;

	public RoEventoObjetivo() {
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

	public RoCatObjetivo getRoCatObjetivo() {
		return this.roCatObjetivo;
	}

	public void setRoCatObjetivo(RoCatObjetivo roCatObjetivo) {
		this.roCatObjetivo = roCatObjetivo;
	}


	public int getCodigoEvob() {
		return codigoEvob;
	}


	public void setCodigoEvob(int codigoEvob) {
		this.codigoEvob = codigoEvob;
	}

}