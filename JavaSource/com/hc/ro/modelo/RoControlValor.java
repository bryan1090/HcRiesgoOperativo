package com.hc.ro.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * The persistent class for the ro_ariesgo database table.
 * 
 */
@Entity
@Table(name = "ro_control_valor")
@NamedQuery(name = "RoControlValor.findAll", query = "SELECT r FROM RoControlValor r")
public class RoControlValor implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODIGO_CTVA")
	private int codigoCtva;

	@Pattern(message = "El campo Tipo solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "TIPO_CTVA")
	private String tipoCtva;

	@Column(name = "CONTROL_CTVA")
	private String controlCtva;

	@ManyToOne
	@JoinColumn(name = "CODIGO_CTRL")
	private RoControl roControl;

	@OneToMany(mappedBy = "roControlValor")
	private List<RoControlEvento> roControlEventos;

	public RoControlValor() {
	}

	public RoControlValor(int codigoCtva, String tipoCtva) {
		super();
		this.codigoCtva = codigoCtva;
		this.tipoCtva = tipoCtva;
	}

	public int getCodigoCtva() {
		return codigoCtva;
	}

	public void setCodigoCtva(int codigoCtva) {
		this.codigoCtva = codigoCtva;
	}

	public String getTipoCtva() {
		return tipoCtva;
	}

	public void setTipoCtva(String tipoCtva) {
		this.tipoCtva = tipoCtva;
	}

	public String getControlCtva() {
		return controlCtva;
	}

	public void setControlCtva(String controlCtva) {
		this.controlCtva = controlCtva;
	}

	public RoControl getRoControl() {
		return roControl;
	}

	public void setRoControl(RoControl roControl) {
		this.roControl = roControl;
	}

	public List<RoControlEvento> getRoControlEventos() {
		return roControlEventos;
	}

	public void setRoControlEventos(List<RoControlEvento> roControlEventos) {
		this.roControlEventos = roControlEventos;
	}

}