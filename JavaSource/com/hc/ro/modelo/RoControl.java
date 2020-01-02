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
@Table(name = "ro_control")
@NamedQuery(name = "RoControl.findAll", query = "SELECT r FROM RoControl r")
public class RoControl implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODIGO_CTRL")
	private int codigoCtrl;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_CTRL")
	private String nombreControl;

	@Pattern(message = "El campo Observación solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "OBSERVACION_CTRL")
	private String observacionControl;

	@OneToMany(mappedBy = "roControl")
	private List<RoControlValor> roControlValors;
	
	@OneToMany(mappedBy = "roControl")
	private List<RoControlEvento> roControlEventos;

	public RoControl() {
	}
	
	public RoControl(int codigoCtrl, String nombreControl) {
		super();
		this.codigoCtrl = codigoCtrl;
		this.nombreControl = nombreControl;
	}

	public int getCodigoCtrl() {
		return codigoCtrl;
	}

	public void setCodigoCtrl(int codigoCtrl) {
		this.codigoCtrl = codigoCtrl;
	}

	public String getNombreControl() {
		return nombreControl;
	}

	public void setNombreControl(String nombreControl) {
		this.nombreControl = nombreControl;
	}

	public String getObservacionControl() {
		return observacionControl;
	}

	public void setObservacionControl(String observacionControl) {
		this.observacionControl = observacionControl;
	}

	public List<RoControlValor> getRoControlValors() {
		return roControlValors;
	}

	public void setRoControlValors(List<RoControlValor> roControlValors) {
		this.roControlValors = roControlValors;
	}

	public List<RoControlEvento> getRoControlEventos() {
		return roControlEventos;
	}

	public void setRoControlEventos(List<RoControlEvento> roControlEventos) {
		this.roControlEventos = roControlEventos;
	}

}