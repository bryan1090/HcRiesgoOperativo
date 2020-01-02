package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ro_cat_frecuencia database table.
 * 
 */
@Entity
@Table(name="ro_cat_frecuencia")
@NamedQuery(name="RoCatFrecuencia.findAll", query="SELECT r FROM RoCatFrecuencia r")
public class RoCatFrecuencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_CTFR")
	private int codigoCtfr;

	@Column(name="DESDE_CTFR")
	private BigDecimal desdeCtfr;

	@Column(name="HASTA_CTFR")
	private BigDecimal hastaCtfr;

	@Column(name="NOMBRE_CTFR")
	private String nombreCtfr;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoNivelRiesgo
	@OneToMany(mappedBy="roCatFrecuencia")
	private List<RoNivelRiesgo> roNivelRiesgos;

	public RoCatFrecuencia() {
	}

	public int getCodigoCtfr() {
		return this.codigoCtfr;
	}

	public void setCodigoCtfr(int codigoCtfr) {
		this.codigoCtfr = codigoCtfr;
	}

	public BigDecimal getDesdeCtfr() {
		return this.desdeCtfr;
	}

	public void setDesdeCtfr(BigDecimal desdeCtfr) {
		this.desdeCtfr = desdeCtfr;
	}

	public BigDecimal getHastaCtfr() {
		return this.hastaCtfr;
	}

	public void setHastaCtfr(BigDecimal hastaCtfr) {
		this.hastaCtfr = hastaCtfr;
	}

	public String getNombreCtfr() {
		return this.nombreCtfr;
	}

	public void setNombreCtfr(String nombreCtfr) {
		this.nombreCtfr = nombreCtfr;
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

	public List<RoNivelRiesgo> getRoNivelRiesgos() {
		return this.roNivelRiesgos;
	}

	public void setRoNivelRiesgos(List<RoNivelRiesgo> roNivelRiesgos) {
		this.roNivelRiesgos = roNivelRiesgos;
	}

	public RoNivelRiesgo addRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().add(roNivelRiesgo);
		roNivelRiesgo.setRoCatFrecuencia(this);

		return roNivelRiesgo;
	}

	public RoNivelRiesgo removeRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().remove(roNivelRiesgo);
		roNivelRiesgo.setRoCatFrecuencia(null);

		return roNivelRiesgo;
	}

}