package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sis_detalle_reporte1 database table.
 * 
 */
@Entity
@Table(name="sis_detalle_reporte1")
@NamedQuery(name="SisDetalleReporte1.findAll", query="SELECT s FROM SisDetalleReporte1 s")
public class SisDetalleReporte1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_DER1")
	private int codigoDer1;

	//bi-directional many-to-one association to SisReporte
	@ManyToOne
	@JoinColumn(name="CODIGO_REPO")
	private SisReporte sisReporte;

	//bi-directional many-to-one association to SisMenu
	@ManyToOne
	@JoinColumn(name="CODIGO_MENU")
	private SisMenu sisMenu;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	public SisDetalleReporte1() {
	}

	public int getCodigoDer1() {
		return this.codigoDer1;
	}

	public void setCodigoDer1(int codigoDer1) {
		this.codigoDer1 = codigoDer1;
	}

	public SisReporte getSisReporte() {
		return this.sisReporte;
	}

	public void setSisReporte(SisReporte sisReporte) {
		this.sisReporte = sisReporte;
	}

	public SisMenu getSisMenu() {
		return this.sisMenu;
	}

	public void setSisMenu(SisMenu sisMenu) {
		this.sisMenu = sisMenu;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

}