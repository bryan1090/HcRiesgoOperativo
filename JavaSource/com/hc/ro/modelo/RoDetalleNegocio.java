package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ro_detalle_negocio database table.
 * 
 */
@Entity
@Table(name = "ro_detalle_negocio")
@NamedQuery(name = "RoDetalleNegocio.findAll", query = "SELECT r FROM RoDetalleNegocio r")
public class RoDetalleNegocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_DNEG")
	private int codigoDneg;

	@Column(name = "VALOR_DNEG")
	private String valorDneg;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoNegocio
	@ManyToOne
	@JoinColumn(name = "CODIGO_NEGO")
	private RoNegocio roNegocio;

	// bi-directional many-to-one association to RoTipoDetalle
	@ManyToOne
	@JoinColumn(name = "CODIGO_TDRO")
	private RoTipoDetalle roTipoDetalle;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	public RoDetalleNegocio() {
	}

	public int getCodigoDneg() {
		return this.codigoDneg;
	}

	public void setCodigoDneg(int codigoDneg) {
		this.codigoDneg = codigoDneg;
	}

	public String getValorDneg() {
		return this.valorDneg;
	}

	public void setValorDneg(String valorDneg) {
		this.valorDneg = valorDneg;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public RoNegocio getRoNegocio() {
		return this.roNegocio;
	}

	public void setRoNegocio(RoNegocio roNegocio) {
		this.roNegocio = roNegocio;
	}

	public RoTipoDetalle getRoTipoDetalle() {
		return this.roTipoDetalle;
	}

	public void setRoTipoDetalle(RoTipoDetalle roTipoDetalle) {
		this.roTipoDetalle = roTipoDetalle;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

}