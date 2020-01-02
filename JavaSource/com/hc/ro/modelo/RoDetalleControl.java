package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ro_detalle_control database table.
 * 
 */
@Entity
@Table(name="ro_detalle_control")
@NamedQuery(name="RoDetalleControl.findAll", query="SELECT r FROM RoDetalleControl r")
public class RoDetalleControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_dtct")
	private int codigoDtct;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_dtct")
	private Date fechaDtct;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="sis_codigo_empr")
	private SisEmpresa sisEmpresa1;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="codigo_empr")
	private SisEmpresa sisEmpresa2;

	//bi-directional many-to-one association to RoProceso
	@ManyToOne
	@JoinColumn(name="codigo_pros")
	private RoProceso roProceso;

	//bi-directional many-to-one association to RoControlProc
	@ManyToOne
	@JoinColumn(name="codigo_ctrp")
	private RoControlProc roControlProc;

	//bi-directional many-to-one association to RoNivelEfecCtrl
	@ManyToOne
	@JoinColumn(name="codigo_nect")
	private RoNivelEfecCtrl roNivelEfecCtrl;

	public RoDetalleControl() {
	}

	public int getCodigoDtct() {
		return this.codigoDtct;
	}

	public void setCodigoDtct(int codigoDtct) {
		this.codigoDtct = codigoDtct;
	}

	public Date getFechaDtct() {
		return this.fechaDtct;
	}

	public void setFechaDtct(Date fechaDtct) {
		this.fechaDtct = fechaDtct;
	}

	public SisEmpresa getSisEmpresa1() {
		return this.sisEmpresa1;
	}

	public void setSisEmpresa1(SisEmpresa sisEmpresa1) {
		this.sisEmpresa1 = sisEmpresa1;
	}

	public SisEmpresa getSisEmpresa2() {
		return this.sisEmpresa2;
	}

	public void setSisEmpresa2(SisEmpresa sisEmpresa2) {
		this.sisEmpresa2 = sisEmpresa2;
	}

	public RoProceso getRoProceso() {
		return this.roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

	public RoControlProc getRoControlProc() {
		return this.roControlProc;
	}

	public void setRoControlProc(RoControlProc roControlProc) {
		this.roControlProc = roControlProc;
	}

	public RoNivelEfecCtrl getRoNivelEfecCtrl() {
		return this.roNivelEfecCtrl;
	}

	public void setRoNivelEfecCtrl(RoNivelEfecCtrl roNivelEfecCtrl) {
		this.roNivelEfecCtrl = roNivelEfecCtrl;
	}

}