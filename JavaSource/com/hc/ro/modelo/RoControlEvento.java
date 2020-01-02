package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * The persistent class for the ro_ariesgo database table.
 * 
 */
@Entity
@Table(name = "ro_control_evento")
@NamedQuery(name = "RoControlEvento.findAll", query = "SELECT r FROM RoControlEvento r")
public class RoControlEvento implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODIGO_CTEV")
	private int codigoCtev;

	@Pattern(message = "El campo Tipo solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "TIPO_CTEV")
	private String tipoCtev;

	@Column(name = "CONTROL_CTEV")
	private String controlCtev;

	@ManyToOne
	@JoinColumn(name = "CODIGO_CTRL")
	private RoControl roControl;

	@ManyToOne
	@JoinColumn(name = "CODIGO_CTVA")
	private RoControlValor roControlValor;

	@ManyToOne
	@JoinColumn(name = "CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;

	public RoControlEvento() {
	}

	public int getCodigoCtev() {
		return codigoCtev;
	}

	public void setCodigoCtev(int codigoCtev) {
		this.codigoCtev = codigoCtev;
	}

	public String getTipoCtev() {
		return tipoCtev;
	}

	public void setTipoCtev(String tipoCtev) {
		this.tipoCtev = tipoCtev;
	}

	public String getControlCtev() {
		return controlCtev;
	}

	public void setControlCtev(String controlCtev) {
		this.controlCtev = controlCtev;
	}

	public RoControl getRoControl() {
		return roControl;
	}

	public void setRoControl(RoControl roControl) {
		this.roControl = roControl;
	}

	public RoControlValor getRoControlValor() {
		return roControlValor;
	}

	public void setRoControlValor(RoControlValor roControlValor) {
		this.roControlValor = roControlValor;
	}

	public RoDetalleEvento getRoDetalleEvento() {
		return roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

}