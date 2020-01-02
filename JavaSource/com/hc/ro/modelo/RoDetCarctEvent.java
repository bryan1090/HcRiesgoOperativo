package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ro_det_carct_event database table.
 * 
 */
@Entity
@Table(name = "ro_det_carct_event")
@NamedQuery(name = "RoDetCarctEvent.findAll", query = "SELECT r FROM RoDetCarctEvent r")
public class RoDetCarctEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo_dcev")
	private int codigoDcev;

	@Column(name = "observacion_dcev")
	private String observacionDcev;

	@Column(name = "valor_dcev")
	private String valorDcev;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "codigo_empr")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "codigo_sucu")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoCaractEvento
	@ManyToOne
	@JoinColumn(name = "codigo_caev")
	private RoCaractEvento roCaractEvento;

	// bi-directional many-to-one association to RoEvento
	@ManyToOne
	@JoinColumn(name = "codigo_even")
	private RoEvento roEvento;

	// bi-directional many-to-one association to RoDetalleEvento
	@ManyToOne
	@JoinColumn(name = "codigo_deve")
	private RoDetalleEvento roDetalleEvento;

	// bi-directional many-to-one association to RoFactorRiesgo
	@ManyToOne
	@JoinColumn(name = "codigo_faro")
	private RoFactorRiesgo roFactorRiesgo;

	// bi-directional many-to-one association to RoCaractEvento
	@ManyToOne
	@JoinColumn(name = "codigo_attr")
	private RoAttrbAdicionale roAttrbAdicionale;
	
	@ManyToOne
	@JoinColumn(name = "codigo_val_attrb")
	private RoValAttrb roValAttrb;

	public RoDetCarctEvent() {
	}

	public String getObservacionDcev() {
		return this.observacionDcev;
	}

	public void setObservacionDcev(String observacionDcev) {
		this.observacionDcev = observacionDcev;
	}

	public String getValorDcev() {
		return this.valorDcev;
	}

	public void setValorDcev(String valorDcev) {
		this.valorDcev = valorDcev;
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

	public RoCaractEvento getRoCaractEvento() {
		return this.roCaractEvento;
	}

	public void setRoCaractEvento(RoCaractEvento roCaractEvento) {
		this.roCaractEvento = roCaractEvento;
	}

	public RoEvento getRoEvento() {
		return this.roEvento;
	}

	public void setRoEvento(RoEvento roEvento) {
		this.roEvento = roEvento;
	}

	public RoDetalleEvento getRoDetalleEvento() {
		return this.roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

	public RoFactorRiesgo getRoFactorRiesgo() {
		return this.roFactorRiesgo;
	}

	public void setRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		this.roFactorRiesgo = roFactorRiesgo;
	}

	public RoAttrbAdicionale getRoAttrbAdicionale() {
		return roAttrbAdicionale;
	}

	public void setRoAttrbAdicionale(RoAttrbAdicionale roAttrbAdicionale) {
		this.roAttrbAdicionale = roAttrbAdicionale;
	}

	public RoValAttrb getRoValAttrb() {
		return roValAttrb;
	}

	public void setRoValAttrb(RoValAttrb roValAttrb) {
		this.roValAttrb = roValAttrb;
	}

	public int getCodigoDcev() {
		return codigoDcev;
	}

	public void setCodigoDcev(int codigoDcev) {
		this.codigoDcev = codigoDcev;
	}

}