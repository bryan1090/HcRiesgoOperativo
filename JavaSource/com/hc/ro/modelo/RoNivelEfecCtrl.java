package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;


/**
 * The persistent class for the ro_nivel_efec_ctrl database table.
 * 
 */
@Entity
@Table(name="ro_nivel_efec_ctrl")
@NamedQuery(name="RoNivelEfecCtrl.findAll", query="SELECT r FROM RoNivelEfecCtrl r")
public class RoNivelEfecCtrl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_nect")
	private int codigoNect;

	@Column(name="codigo_empr")
	private int codigoEmpr;

	@Column(name="codigo_sucu")
	private int codigoSucu;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="nombre_nect")
	private String nombreNect;

	@Column(name="observacion_nect")
	private String observacionNect;

	//bi-directional many-to-one association to RoDetalleControl
	@OneToMany(mappedBy="roNivelEfecCtrl")
	private List<RoDetalleControl> roDetalleControls;

	public RoNivelEfecCtrl() {
	}

	public int getCodigoNect() {
		return this.codigoNect;
	}

	public void setCodigoNect(int codigoNect) {
		this.codigoNect = codigoNect;
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

	public String getNombreNect() {
		return this.nombreNect;
	}

	public void setNombreNect(String nombreNect) {
		this.nombreNect = nombreNect;
	}

	public String getObservacionNect() {
		return this.observacionNect;
	}

	public void setObservacionNect(String observacionNect) {
		this.observacionNect = observacionNect;
	}

	public List<RoDetalleControl> getRoDetalleControls() {
		return this.roDetalleControls;
	}

	public void setRoDetalleControls(List<RoDetalleControl> roDetalleControls) {
		this.roDetalleControls = roDetalleControls;
	}

	public RoDetalleControl addRoDetalleControl(RoDetalleControl roDetalleControl) {
		getRoDetalleControls().add(roDetalleControl);
		roDetalleControl.setRoNivelEfecCtrl(this);

		return roDetalleControl;
	}

	public RoDetalleControl removeRoDetalleControl(RoDetalleControl roDetalleControl) {
		getRoDetalleControls().remove(roDetalleControl);
		roDetalleControl.setRoNivelEfecCtrl(null);

		return roDetalleControl;
	}

}