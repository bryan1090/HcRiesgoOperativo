package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sis_detalle_proceso database table.
 * 
 */
@Entity
@Table(name="sis_detalle_proceso")
@NamedQuery(name="SisDetalleProceso.findAll", query="SELECT s FROM SisDetalleProceso s")
public class SisDetalleProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_DPRC")
	private int codigoDprc;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisUsuario
	@ManyToOne
	@JoinColumn(name="CODIGO_USUA")
	private SisUsuario sisUsuario;

	//bi-directional many-to-one association to SisProcedimiento
	@ManyToOne
	@JoinColumn(name="CODIGO_PROC")
	private SisProcedimiento sisProcedimiento;

	public SisDetalleProceso() {
	}

	public int getCodigoDprc() {
		return this.codigoDprc;
	}

	public void setCodigoDprc(int codigoDprc) {
		this.codigoDprc = codigoDprc;
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

	public SisUsuario getSisUsuario() {
		return this.sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

	public SisProcedimiento getSisProcedimiento() {
		return this.sisProcedimiento;
	}

	public void setSisProcedimiento(SisProcedimiento sisProcedimiento) {
		this.sisProcedimiento = sisProcedimiento;
	}

}