package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * The persistent class for the ro_ariesgo database table.
 * 
 */
@Entity
@Table(name = "ro_ariesgo")
@NamedQuery(name = "RoAriesgo.findAll", query = "SELECT r FROM RoAriesgo r")
public class RoAriesgo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "COD_ARIESGO")
	private int codAriesgo;

	@Pattern(message = "El campo Descripción solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "DESC_ARIESGO")
	private String descAriesgo;

	@Pattern(message = "El campo Observación solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "OBSV_ARIESGO")
	private String obsvAriesgo;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_PPRR")
	private ParamProbabilidadRiesgo paramProbabilidadRiesgo;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_PCONI")
	private ParamConsecuenciaImpacto paramConsecuenciaImpacto;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "COD_clrs")
	private RoCalifRiesgo roCalifRiesgo;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_NEGO")
	private RoNegocio roNegocio;
	

	public RoAriesgo() {
	}

	public RoAriesgo(ParamProbabilidadRiesgo paramProbabilidadRiesgo,
			ParamConsecuenciaImpacto paramConsecuenciaImpacto,
			String nombreClrs, String colorClrs) {
		super();
		this.paramProbabilidadRiesgo = paramProbabilidadRiesgo;
		this.paramConsecuenciaImpacto = paramConsecuenciaImpacto;
		this.roCalifRiesgo = new RoCalifRiesgo(colorClrs, nombreClrs);
	}

	public String getDescAriesgo() {
		return this.descAriesgo;
	}

	public void setDescAriesgo(String descAriesgo) {
		this.descAriesgo = descAriesgo;
	}

	public String getObsvAriesgo() {
		return this.obsvAriesgo;
	}

	public void setObsvAriesgo(String obsvAriesgo) {
		this.obsvAriesgo = obsvAriesgo;
	}

	public RoCalifRiesgo getRoCalifRiesgo() {
		return roCalifRiesgo;
	}

	public void setRoCalifRiesgo(RoCalifRiesgo roCalifRiesgo) {
		this.roCalifRiesgo = roCalifRiesgo;
	}

	public RoNegocio getRoNegocio() {
		return roNegocio;
	}

	public void setRoNegocio(RoNegocio roNegocio) {
		this.roNegocio = roNegocio;
	}

	public void setCodAriesgo(int codAriesgo) {
		this.codAriesgo = codAriesgo;
	}

	public int getCodAriesgo() {
		return codAriesgo;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgo() {
		return paramProbabilidadRiesgo;
	}

	public void setParamProbabilidadRiesgo(
			ParamProbabilidadRiesgo paramProbabilidadRiesgo) {
		this.paramProbabilidadRiesgo = paramProbabilidadRiesgo;
	}

	public ParamConsecuenciaImpacto getParamConsecuenciaImpacto() {
		return paramConsecuenciaImpacto;
	}

	public void setParamConsecuenciaImpacto(
			ParamConsecuenciaImpacto paramConsecuenciaImpacto) {
		this.paramConsecuenciaImpacto = paramConsecuenciaImpacto;
	}

	

}