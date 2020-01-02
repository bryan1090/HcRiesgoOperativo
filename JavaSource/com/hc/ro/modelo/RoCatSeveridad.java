package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ro_cat_severidad database table.
 * 
 */
@Entity
@Table(name="ro_cat_severidad")
@NamedQuery(name="RoCatSeveridad.findAll", query="SELECT r FROM RoCatSeveridad r")
public class RoCatSeveridad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_CTSV")
	private int codigoCtsv;

	@Column(name="DESDE_CTSV")
	private BigDecimal desdeCtsv;

	@Column(name="HASTA_CTSV")
	private BigDecimal hastaCtsv;

	@Column(name="NOMBRE_CTSV")
	private String nombreCtsv;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoNivelRiesgo
	@OneToMany(mappedBy="roCatSeveridad")
	private List<RoNivelRiesgo> roNivelRiesgos;

	public RoCatSeveridad() {
	}

	public int getCodigoCtsv() {
		return this.codigoCtsv;
	}

	public void setCodigoCtsv(int codigoCtsv) {
		this.codigoCtsv = codigoCtsv;
	}

	public BigDecimal getDesdeCtsv() {
		return this.desdeCtsv;
	}

	public void setDesdeCtsv(BigDecimal desdeCtsv) {
		this.desdeCtsv = desdeCtsv;
	}

	public BigDecimal getHastaCtsv() {
		return this.hastaCtsv;
	}

	public void setHastaCtsv(BigDecimal hastaCtsv) {
		this.hastaCtsv = hastaCtsv;
	}

	public String getNombreCtsv() {
		return this.nombreCtsv;
	}

	public void setNombreCtsv(String nombreCtsv) {
		this.nombreCtsv = nombreCtsv;
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
		roNivelRiesgo.setRoCatSeveridad(this);

		return roNivelRiesgo;
	}

	public RoNivelRiesgo removeRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().remove(roNivelRiesgo);
		roNivelRiesgo.setRoCatSeveridad(null);

		return roNivelRiesgo;
	}

}