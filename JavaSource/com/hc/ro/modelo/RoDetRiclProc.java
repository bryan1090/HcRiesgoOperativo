package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the ro_det_ricl_proc database table.
 * 
 */
@Entity
@Table(name = "ro_det_ricl_proc")
@NamedQuery(name = "RoDetRiclProc.findAll", query = "SELECT r FROM RoDetRiclProc r")
public class RoDetRiclProc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo_drcl")
	private int codigoDrcl;

	@Column(name = "codigo_pros")
	private int codigoPros;


	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "codigo_empr")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "codigo_sucu")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoRiesgoClave
	@ManyToOne
	@JoinColumn(name = "codigo_ricl")
	private RoRiesgoClave roRiesgoClave;

	public RoDetRiclProc() {
	}

	public int getCodigoDrcl() {
		return this.codigoDrcl;
	}

	public void setCodigoDrcl(int codigoDrcl) {
		this.codigoDrcl = codigoDrcl;
	}

	public int getCodigoPros() {
		return this.codigoPros;
	}

	public void setCodigoPros(int codigoPros) {
		this.codigoPros = codigoPros;
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

	public RoRiesgoClave getRoRiesgoClave() {
		return roRiesgoClave;
	}

	public void setRoRiesgoClave(RoRiesgoClave roRiesgoClave) {
		this.roRiesgoClave = roRiesgoClave;
	}

	
}