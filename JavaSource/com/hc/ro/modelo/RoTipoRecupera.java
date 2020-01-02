package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_tipo_recupera database table.
 * 
 */
@Entity
@Table(name = "ro_tipo_recupera")
@NamedQuery(name = "RoTipoRecupera.findAll", query = "SELECT r FROM RoTipoRecupera r")
public class RoTipoRecupera implements Serializable {
	private static final long serialVersionUID = 1L;

	
	
	public RoTipoRecupera(int codigoTrec, String nombreTrec) {
		super();
		this.codigoTrec = codigoTrec;
		this.nombreTrec = nombreTrec;
	}

	@Id
	@Column(name = "CODIGO_TREC")
	private int codigoTrec;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_TREC")
	private String nombreTrec;

	@Column(name = "VALOR_TREC")
	private Float valorTrec;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roTipoRecupera")
	private List<RoDetalleEvento> roDetalleEventos;

	// bi-directional many-to-one association to RoEventoRecupera
	@OneToMany(mappedBy = "roTipoRecupera")
	private List<RoEventoRecupera> roEventoRecuperas;

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

	public RoTipoRecupera() {
	}

	public RoTipoRecupera(int codigoTrec, String nombreTrec, Float valorTrec) {
		super();
		this.codigoTrec = codigoTrec;
		this.nombreTrec = nombreTrec;
		this.valorTrec = valorTrec;
	}

	public int getCodigoTrec() {
		return this.codigoTrec;
	}

	public void setCodigoTrec(int codigoTrec) {
		this.codigoTrec = codigoTrec;
	}

	public String getNombreTrec() {
		return this.nombreTrec;
	}

	public void setNombreTrec(String nombreTrec) {
		this.nombreTrec = nombreTrec;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return this.roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public RoDetalleEvento addRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().add(roDetalleEvento);
		roDetalleEvento.setRoTipoRecupera(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setRoTipoRecupera(null);

		return roDetalleEvento;
	}

	public List<RoEventoRecupera> getRoEventoRecuperas() {
		return this.roEventoRecuperas;
	}

	public void setRoEventoRecuperas(List<RoEventoRecupera> roEventoRecuperas) {
		this.roEventoRecuperas = roEventoRecuperas;
	}

	public RoEventoRecupera addRoEventoRecupera(
			RoEventoRecupera roEventoRecupera) {
		getRoEventoRecuperas().add(roEventoRecupera);
		roEventoRecupera.setRoTipoRecupera(this);

		return roEventoRecupera;
	}

	public RoEventoRecupera removeRoEventoRecupera(
			RoEventoRecupera roEventoRecupera) {
		getRoEventoRecuperas().remove(roEventoRecupera);
		roEventoRecupera.setRoTipoRecupera(null);

		return roEventoRecupera;
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

	public Float getValorTrec() {
		return valorTrec;
	}

	public void setValorTrec(Float valorTrec) {
		this.valorTrec = valorTrec;
	}

}