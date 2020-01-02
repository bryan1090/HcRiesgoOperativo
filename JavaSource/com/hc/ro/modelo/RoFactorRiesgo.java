package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_factor_riesgo database table.
 * 
 */
@Entity
@Table(name = "ro_factor_riesgo")
@NamedQuery(name = "RoFactorRiesgo.findAll", query = "SELECT r FROM RoFactorRiesgo r")
public class RoFactorRiesgo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_FARO")
	private int codigoFaro;

	// bi-directional many-to-one association to RoDetCarctEvent
	@OneToMany(mappedBy = "roFactorRiesgo")
	private List<RoDetCarctEvent> roDetCarctEvents;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_FARO")
	private String nombreFaro;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roFactorRiesgo")
	private List<RoDetalleEvento> roDetalleEventos;

	@OneToMany(mappedBy = "roFactorRiesgo")
	private List<RoDeveFaro> roDeveFaros;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	public RoFactorRiesgo() {
	}

	public RoFactorRiesgo(int codigoFaro, String nombreFaro) {
		super();
		this.codigoFaro = codigoFaro;
		this.nombreFaro = nombreFaro;
	}



	public int getCodigoFaro() {
		return this.codigoFaro;
	}

	public void setCodigoFaro(int codigoFaro) {
		this.codigoFaro = codigoFaro;
	}

	public String getNombreFaro() {
		return this.nombreFaro;
	}

	public void setNombreFaro(String nombreFaro) {
		this.nombreFaro = nombreFaro;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return this.roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public RoDetalleEvento addRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().add(roDetalleEvento);
		roDetalleEvento.setRoFactorRiesgo(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setRoFactorRiesgo(null);

		return roDetalleEvento;
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

	public GenEstado getGenEstado() {
		return this.genEstado;
	}

	public void setGenEstado(GenEstado genEstado) {
		this.genEstado = genEstado;
	}

	public List<RoDetCarctEvent> getRoDetCarctEvents() {
		return roDetCarctEvents;
	}

	public void setRoDetCarctEvents(List<RoDetCarctEvent> roDetCarctEvents) {
		this.roDetCarctEvents = roDetCarctEvents;
	}

}