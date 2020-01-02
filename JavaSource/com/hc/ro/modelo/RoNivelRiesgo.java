package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ro_nivel_riesgo database table.
 * 
 */
@Entity
@Table(name="ro_nivel_riesgo")
@NamedQuery(name="RoNivelRiesgo.findAll", query="SELECT r FROM RoNivelRiesgo r")
public class RoNivelRiesgo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_NIRI")
	private int codigoNiri;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoCatSeveridad
	@ManyToOne
	@JoinColumn(name="CODIGO_CTSV")
	private RoCatSeveridad roCatSeveridad;

	//bi-directional many-to-one association to RoCatFrecuencia
	@ManyToOne
	@JoinColumn(name="CODIGO_CTFR")
	private RoCatFrecuencia roCatFrecuencia;

	//bi-directional many-to-one association to RoCalifRiesgo
	@ManyToOne
	@JoinColumn(name="CODIGO_CLRS")
	private RoCalifRiesgo roCalifRiesgo;

	public RoNivelRiesgo() {
	}

	public int getCodigoNiri() {
		return this.codigoNiri;
	}

	public void setCodigoNiri(int codigoNiri) {
		this.codigoNiri = codigoNiri;
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

	public RoCatSeveridad getRoCatSeveridad() {
		return this.roCatSeveridad;
	}

	public void setRoCatSeveridad(RoCatSeveridad roCatSeveridad) {
		this.roCatSeveridad = roCatSeveridad;
	}

	public RoCatFrecuencia getRoCatFrecuencia() {
		return this.roCatFrecuencia;
	}

	public void setRoCatFrecuencia(RoCatFrecuencia roCatFrecuencia) {
		this.roCatFrecuencia = roCatFrecuencia;
	}

	public RoCalifRiesgo getRoCalifRiesgo() {
		return this.roCalifRiesgo;
	}

	public void setRoCalifRiesgo(RoCalifRiesgo roCalifRiesgo) {
		this.roCalifRiesgo = roCalifRiesgo;
	}

}