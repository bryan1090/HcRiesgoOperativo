package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ro_tipo_control database table.
 * 
 */
@Entity
@Table(name="ro_tipo_control")
@NamedQuery(name="RoTipoControl.findAll", query="SELECT r FROM RoTipoControl r")
public class RoTipoControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_tctrl")
	private int codigoTctrl;

	@Column(name="codigo_empr")
	private int codigoEmpr;

	@Column(name="codigo_sucu")
	private int codigoSucu;

	@Column(name="nombre_tctrl")
	private String nombreTctrl;

	@Column(name="observacion_tctrl")
	private String observacionTctrl;

	//bi-directional many-to-one association to RoControlProc
	@OneToMany(mappedBy="roTipoControl")
	private List<RoControlProc> roControlProcs;

	public RoTipoControl() {
	}

	public int getCodigoTctrl() {
		return this.codigoTctrl;
	}

	public void setCodigoTctrl(int codigoTctrl) {
		this.codigoTctrl = codigoTctrl;
	}

	public int getCodigoEmpr() {
		return this.codigoEmpr;
	}

	public void setCodigoEmpr(int codigoEmpr) {
		this.codigoEmpr = codigoEmpr;
	}

	public int getCodigoSucu() {
		return this.codigoSucu;
	}

	public void setCodigoSucu(int codigoSucu) {
		this.codigoSucu = codigoSucu;
	}

	public String getNombreTctrl() {
		return this.nombreTctrl;
	}

	public void setNombreTctrl(String nombreTctrl) {
		this.nombreTctrl = nombreTctrl;
	}

	public String getObservacionTctrl() {
		return this.observacionTctrl;
	}

	public void setObservacionTctrl(String observacionTctrl) {
		this.observacionTctrl = observacionTctrl;
	}

	public List<RoControlProc> getRoControlProcs() {
		return this.roControlProcs;
	}

	public void setRoControlProcs(List<RoControlProc> roControlProcs) {
		this.roControlProcs = roControlProcs;
	}

	public RoControlProc addRoControlProc(RoControlProc roControlProc) {
		getRoControlProcs().add(roControlProc);
		roControlProc.setRoTipoControl(this);

		return roControlProc;
	}

	public RoControlProc removeRoControlProc(RoControlProc roControlProc) {
		getRoControlProcs().remove(roControlProc);
		roControlProc.setRoTipoControl(null);

		return roControlProc;
	}

}