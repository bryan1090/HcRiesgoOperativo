package com.hc.ro.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

/**
 * The persistent class for the ro_probabilidad_evento database table.
 * 
 */
@Entity
@Table(name = "ro_probabilidad_evento")
@NamedQuery(name = "RoProbabilidadEvento.findAll", query = "SELECT r FROM RoProbabilidadEvento r")
public class RoProbabilidadEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_prob")
	private int CODIGO_prob;

	@Column(name = "DESDE_PROB")
	private double desdeProb;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_PROB")
	private Date fechaProb;

	@Column(name = "HASTA_PROB")
	private double hastaProb;

	@Column(name = "CALCULO_PROB")
	private int calculoProb;

	@Transient
	private String nombrePprr;

	@Transient
	private String letraPprr;

	@Pattern(message = "El campo Observación solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "OBSRV_PBO")
	private String obsrvPbo;

	// bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name = "CODIGO_NEGO")
	private RoNegocio roNegocio;

	// bi-directional many-to-one association to RoControle
	@OneToMany(mappedBy = "roProbabilidadEvento")
	private List<RoControles> roControles;

	@Column(name = "CODIGO_PPRR")
	private int codigoPprr;

	public RoNegocio getRoNegocio() {
		return roNegocio;
	}

	public void setRoNegocio(RoNegocio roNegocio) {
		this.roNegocio = roNegocio;
	}

	public RoProbabilidadEvento() {
	}

	public int getCODIGO_prob() {
		return this.CODIGO_prob;
	}

	public void setCODIGO_prob(int CODIGO_prob) {
		this.CODIGO_prob = CODIGO_prob;
	}

	public Date getFechaProb() {
		return this.fechaProb;
	}

	public void setFechaProb(Date fechaProb) {
		this.fechaProb = fechaProb;
	}

	public double getDesdeProb() {
		return desdeProb;
	}

	public void setDesdeProb(double desdeProb) {
		this.desdeProb = desdeProb;
	}

	public double getHastaProb() {
		return hastaProb;
	}

	public void setHastaProb(double hastaProb) {
		this.hastaProb = hastaProb;
	}

	public String getObsrvPbo() {
		return this.obsrvPbo;
	}

	public void setObsrvPbo(String obsrvPbo) {
		this.obsrvPbo = obsrvPbo;
	}

	public String getNombrePprr() {
		return nombrePprr;
	}

	public void setNombrePprr(String nombrePprr) {
		this.nombrePprr = nombrePprr;
	}

	public int getCodigoPprr() {
		return codigoPprr;
	}

	public void setCodigoPprr(int codigoPprr) {
		this.codigoPprr = codigoPprr;
	}

	public String getLetraPprr() {
		return letraPprr;
	}

	public void setLetraPprr(String letraPprr) {
		this.letraPprr = letraPprr;
	}

	public int getCalculoProb() {
		return calculoProb;
	}

	public void setCalculoProb(int calculoProb) {
		this.calculoProb = calculoProb;
	}

	public List<RoControles> getRoControles() {
		return roControles;
	}

	public void setRoControles(List<RoControles> roControles) {
		this.roControles = roControles;
	}

}