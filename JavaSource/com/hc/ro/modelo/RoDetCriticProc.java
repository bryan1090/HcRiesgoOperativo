package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ro_det_critic_proc database table.
 * 
 */
@Entity
@Table(name="ro_det_critic_proc")
@NamedQuery(name="RoDetCriticProc.findAll", query="SELECT r FROM RoDetCriticProc r")
public class RoDetCriticProc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_dcpr")
	private int codigoDcpr;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="codigo_empr")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="codigo_sucu")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoProceso
	@ManyToOne
	@JoinColumn(name="codigo_pros")
	private RoProceso roProceso;

	//bi-directional many-to-one association to RoDepartamento
	@ManyToOne
	@JoinColumn(name="codigo_dept")
	private RoDepartamento roDepartamento;

	//bi-directional many-to-one association to RoCriticidad
	@ManyToOne
	@JoinColumn(name="codigo_crit")
	private RoCriticidad roCriticidad;

	public RoDetCriticProc() {
	}

	public int getCodigoDcpr() {
		return this.codigoDcpr;
	}

	public void setCodigoDcpr(int codigoDcpr) {
		this.codigoDcpr = codigoDcpr;
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
		return this.roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

	public RoDepartamento getRoDepartamento() {
		return this.roDepartamento;
	}

	public void setRoDepartamento(RoDepartamento roDepartamento) {
		this.roDepartamento = roDepartamento;
	}

	public RoCriticidad getRoCriticidad() {
		return this.roCriticidad;
	}

	public void setRoCriticidad(RoCriticidad roCriticidad) {
		this.roCriticidad = roCriticidad;
	}

}