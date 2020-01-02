package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_caract_evento database table.
 * 
 */
@Entity
@Table(name = "ro_caract_evento")
@NamedQuery(name = "RoCaractEvento.findAll", query = "SELECT r FROM RoCaractEvento r")
public class RoCaractEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo_caev")
	private int codigoCaev;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "nombre_caev")
	private String nombreCaev;
	
	@Column(name = "observacion_caev")
	private String observacionCaev;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "codigo_empr")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "codigo_sucu")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoEvento
	@ManyToOne
	@JoinColumn(name = "codigo_even")
	private RoEvento roEvento;

	// bi-directional many-to-one association to RoDetCarctEvent
	@OneToMany(mappedBy = "roCaractEvento")
	private List<RoDetCarctEvent> roDetCarctEvents;

	public RoCaractEvento() {
	}

	public int getCodigoCaev() {
		return this.codigoCaev;
	}

	public void setCodigoCaev(int codigoCaev) {
		this.codigoCaev = codigoCaev;
	}

	public String getNombreCaev() {
		return this.nombreCaev;
	}

	public void setNombreCaev(String nombreCaev) {
		this.nombreCaev = nombreCaev;
	}

	public String getObservacionCaev() {
		return this.observacionCaev;
	}

	public void setObservacionCaev(String observacionCaev) {
		this.observacionCaev = observacionCaev;
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

	public RoEvento getRoEvento() {
		return this.roEvento;
	}

	public void setRoEvento(RoEvento roEvento) {
		this.roEvento = roEvento;
	}

	public List<RoDetCarctEvent> getRoDetCarctEvents() {
		return this.roDetCarctEvents;
	}

	public void setRoDetCarctEvents(List<RoDetCarctEvent> roDetCarctEvents) {
		this.roDetCarctEvents = roDetCarctEvents;
	}

	public RoDetCarctEvent addRoDetCarctEvent(RoDetCarctEvent roDetCarctEvent) {
		getRoDetCarctEvents().add(roDetCarctEvent);
		roDetCarctEvent.setRoCaractEvento(this);

		return roDetCarctEvent;
	}

	public RoDetCarctEvent removeRoDetCarctEvent(RoDetCarctEvent roDetCarctEvent) {
		getRoDetCarctEvents().remove(roDetCarctEvent);
		roDetCarctEvent.setRoCaractEvento(null);

		return roDetCarctEvent;
	}

}