package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ro_detalle_proceso database table.
 * 
 */
@Entity
@Table(name = "ro_detalle_proceso")
@NamedQuery(name = "RoDetalleProceso.findAll", query = "SELECT r FROM RoDetalleProceso r")
public class RoDetalleProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_DPRO")
	private int codigoDpro;

	@Column(name = "VALOR_DPRO")
	private String valorDpro;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoProceso
	@ManyToOne
	@JoinColumn(name = "CODIGO_PROS")
	private RoProceso roProceso;

	// bi-directional many-to-one association to RoTipoDetalle
	@ManyToOne
	@JoinColumn(name = "CODIGO_TDRO")
	private RoTipoDetalle roTipoDetalle;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	public RoDetalleProceso() {
	}

	public String getValorDpro() {
		return this.valorDpro;
	}

	public void setValorDpro(String valorDpro) {
		this.valorDpro = valorDpro;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public RoProceso getRoProceso() {
		return this.roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
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

	public int getCodigoDpro() {
		return codigoDpro;
	}

	public void setCodigoDpro(int codigoDpro) {
		this.codigoDpro = codigoDpro;
	}

}