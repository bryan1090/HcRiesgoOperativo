package com.hc.ro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.Date;

/**
 * The persistent class for the ro_consecuencia_impacto database table.
 * 
 */
@Entity
@Table(name = "ro_consecuencia_impacto")
@NamedQuery(name = "RoConsecuenciaImpacto.findAll", query = "SELECT r FROM RoConsecuenciaImpacto r")
public class RoConsecuenciaImpacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int CODIGO_cons;

	@Transient
	private String nombrePconi;

	private double DESDE_cons;

	@Temporal(TemporalType.DATE)
	private Date FECHA_cons;

	private double HASTA_cons;

	@Pattern(message = "El campo Observación solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	private String OBSERV_cons;

	// bi-directional many-to-one association to Negocio
	@ManyToOne
	@JoinColumn(name = "CODIGO_nego")
	private RoNegocio roNegocio;

	@Column(name = "CODIGO_PCONI")
	private int codigoPconi;
	
	@Column(name="IMPACT_ECON")
	private BigDecimal impactEcon;
	
	public RoNegocio getRoNegocio() {
		return roNegocio;
	}

	public BigDecimal getImpactEcon() {
		return impactEcon;
	}

	public void setImpactEcon(BigDecimal impactEcon) {
		this.impactEcon = impactEcon;
	}

	public void setRoNegocio(RoNegocio roNegocio) {
		this.roNegocio = roNegocio;
	}

	public RoConsecuenciaImpacto() {
	}

	public int getCODIGO_cons() {
		return this.CODIGO_cons;
	}

	public void setCODIGO_cons(int CODIGO_cons) {
		this.CODIGO_cons = CODIGO_cons;
	}

	public void setFECHA_cons(Date FECHA_cons) {
		this.FECHA_cons = FECHA_cons;
	}

	public double getDESDE_cons() {
		return DESDE_cons;
	}

	public void setDESDE_cons(double dESDE_cons) {
		DESDE_cons = dESDE_cons;
	}

	public Date getFECHA_cons() {
		return FECHA_cons;
	}

	public double getHASTA_cons() {
		return HASTA_cons;
	}

	public void setHASTA_cons(double hASTA_cons) {
		HASTA_cons = hASTA_cons;
	}

	public String getOBSERV_cons() {
		return this.OBSERV_cons;
	}

	public void setOBSERV_cons(String OBSERV_cons) {
		this.OBSERV_cons = OBSERV_cons;
	}

	public int getCodigoPconi() {
		return codigoPconi;
	}

	public void setCodigoPconi(int codigoPconi) {
		this.codigoPconi = codigoPconi;
	}

	public String getNombrePconi() {
		return nombrePconi;
	}

	public void setNombrePconi(String nombrePconi) {
		this.nombrePconi = nombrePconi;
	}

}