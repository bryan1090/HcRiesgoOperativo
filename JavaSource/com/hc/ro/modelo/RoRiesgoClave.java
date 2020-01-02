package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ro_riesgo_clave database table.
 * 
 */
@Entity
@Table(name = "ro_riesgo_clave")
@NamedQuery(name = "RoRiesgoClave.findAll", query = "SELECT r FROM RoRiesgoClave r")
public class RoRiesgoClave implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo_ricl")
	private int codigoRicl;

	@Column(name = "nombre_ricl")
	private String nombreRicl;

	@Column(name = "observacion_ricl")
	private String observacionRicl;

	// bi-directional many-to-one association to RoDetRiclProc
	@OneToMany(mappedBy = "roRiesgoClave")
	private List<RoDetRiclProc> roDetRiclProcs;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "codigo_empr")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "codigo_pros")
	private RoProceso roProceso;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "codigo_sucu")
	private SisSucursal sisSucursal;

	public RoRiesgoClave() {
	}

	public int getCodigoRicl() {
		return this.codigoRicl;
	}

	public void setCodigoRicl(int codigoRicl) {
		this.codigoRicl = codigoRicl;
	}

	public String getNombreRicl() {
		return this.nombreRicl;
	}

	public void setNombreRicl(String nombreRicl) {
		this.nombreRicl = nombreRicl;
	}

	public String getObservacionRicl() {
		return this.observacionRicl;
	}

	public void setObservacionRicl(String observacionRicl) {
		this.observacionRicl = observacionRicl;
	}

	public List<RoDetRiclProc> getRoDetRiclProcs() {
		return this.roDetRiclProcs;
	}

	public void setRoDetRiclProcs(List<RoDetRiclProc> roDetRiclProcs) {
		this.roDetRiclProcs = roDetRiclProcs;
	}

	public RoDetRiclProc addRoDetRiclProc(RoDetRiclProc roDetRiclProc) {
		getRoDetRiclProcs().add(roDetRiclProc);
		roDetRiclProc.setRoRiesgoClave(this);
		return roDetRiclProc;
	}

	public RoDetRiclProc removeRoDetRiclProc(RoDetRiclProc roDetRiclProc) {
		getRoDetRiclProcs().remove(roDetRiclProc);
		roDetRiclProc.setRoRiesgoClave(null);

		return roDetRiclProc;
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

	public RoProceso getRoProceso() {
		return roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

}