package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ro_control_proc database table.
 * 
 */
@Entity
@Table(name="ro_control_proc")
@NamedQuery(name="RoControlProc.findAll", query="SELECT r FROM RoControlProc r")
public class RoControlProc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_ctrp")
	private int codigoCtrp;

	@Column(name="nombre_ctrp")
	private String nombreCtrp;

	@Column(name="observacion_ctrp")
	private String observacionCtrp;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="codigo_empr")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="codigo_sucu")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoTipoControl
	@ManyToOne
	@JoinColumn(name="codigo_tctrl")
	private RoTipoControl roTipoControl;

	//bi-directional many-to-one association to RoDetalleControl
	@OneToMany(mappedBy="roControlProc")
	private List<RoDetalleControl> roDetalleControls;

	public RoControlProc() {
	}

	public int getCodigoCtrp() {
		return this.codigoCtrp;
	}

	public void setCodigoCtrp(int codigoCtrp) {
		this.codigoCtrp = codigoCtrp;
	}

	public String getNombreCtrp() {
		return this.nombreCtrp;
	}

	public void setNombreCtrp(String nombreCtrp) {
		this.nombreCtrp = nombreCtrp;
	}

	public String getObservacionCtrp() {
		return this.observacionCtrp;
	}

	public void setObservacionCtrp(String observacionCtrp) {
		this.observacionCtrp = observacionCtrp;
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

	public RoTipoControl getRoTipoControl() {
		return this.roTipoControl;
	}

	public void setRoTipoControl(RoTipoControl roTipoControl) {
		this.roTipoControl = roTipoControl;
	}

	public List<RoDetalleControl> getRoDetalleControls() {
		return this.roDetalleControls;
	}

	public void setRoDetalleControls(List<RoDetalleControl> roDetalleControls) {
		this.roDetalleControls = roDetalleControls;
	}

	public RoDetalleControl addRoDetalleControl(RoDetalleControl roDetalleControl) {
		getRoDetalleControls().add(roDetalleControl);
		roDetalleControl.setRoControlProc(this);

		return roDetalleControl;
	}

	public RoDetalleControl removeRoDetalleControl(RoDetalleControl roDetalleControl) {
		getRoDetalleControls().remove(roDetalleControl);
		roDetalleControl.setRoControlProc(null);

		return roDetalleControl;
	}

}