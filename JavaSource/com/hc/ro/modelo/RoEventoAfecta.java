package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ro_evento_afecta database table.
 * 
 */
@Entity
@Table(name="ro_evento_afecta")
@NamedQuery(name="RoEventoAfecta.findAll", query="SELECT r FROM RoEventoAfecta r")
public class RoEventoAfecta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_EVAF")
	private int codigoEvaf;

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

	//bi-directional many-to-one association to RoTipoAfecta
	@ManyToOne
	@JoinColumn(name="CODIGO_TAFC")
	private RoTipoAfecta roTipoAfecta;

	public RoEventoAfecta() {
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

	public RoTipoAfecta getRoTipoAfecta() {
		return this.roTipoAfecta;
	}

	public void setRoTipoAfecta(RoTipoAfecta roTipoAfecta) {
		this.roTipoAfecta = roTipoAfecta;
	}


	public int getCodigoEvaf() {
		return codigoEvaf;
	}


	public void setCodigoEvaf(int codigoEvaf) {
		this.codigoEvaf = codigoEvaf;
	}

}