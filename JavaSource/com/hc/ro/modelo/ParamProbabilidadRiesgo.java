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
@Table(name = "param_probabilidad_riesgo")
@NamedQuery(name = "ParamProbabilidadRiesgo.findAll", query = "SELECT r FROM ParamProbabilidadRiesgo r")
public class ParamProbabilidadRiesgo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_PPRR")
	private Integer codigoPprr;

	@Pattern(message = "El campo Nombre solo debe contener letras, espacios y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_PPRR")
	private String nombrePprr;

	@Column(name = "NUMERO_PPRR")
	private int numeroPprr;

	@Column(name = "LETRA_PPRR")
	private String letraPprr;

	public ParamProbabilidadRiesgo(Integer codigoPprr, String nombrePprr,
			int numeroPprr, String letraPprr) {
		super();
		this.codigoPprr = codigoPprr;
		this.nombrePprr = nombrePprr;
		this.numeroPprr = numeroPprr;
		this.letraPprr = letraPprr;
	}

	public ParamProbabilidadRiesgo(Integer codigoPprr, String nombrePprr) {
		super();
		this.codigoPprr = codigoPprr;
		this.nombrePprr = nombrePprr;
	}

	@OneToMany(mappedBy = "paramProbabilidadRiesgo")
	private List<RoAriesgo> roAriesgos;

	// bi-directional many-to-one association to RoControle
	@OneToMany(mappedBy = "paramProbabilidadRiesgo")
	private List<RoControles> roControles;

	public int getCodigoPprr() {
		return codigoPprr;
	}

	public void setCodigoPprr(int codigoPprr) {
		this.codigoPprr = codigoPprr;
	}

	public String getNombrePprr() {
		return nombrePprr;
	}

	public void setNombrePprr(String nombrePprr) {
		this.nombrePprr = nombrePprr;
	}

	public int getNumeroPprr() {
		return numeroPprr;
	}

	public void setNumeroPprr(int numeroPprr) {
		this.numeroPprr = numeroPprr;
	}

	public String getLetraPprr() {
		return letraPprr;
	}

	public void setLetraPprr(String letraPprr) {
		this.letraPprr = letraPprr;
	}

	public ParamProbabilidadRiesgo() {
	}

	public List<RoAriesgo> getRoAriesgos() {
		return roAriesgos;
	}

	public void setRoAriesgos(List<RoAriesgo> roAriesgos) {
		this.roAriesgos = roAriesgos;
	}

	public List<RoControles> getRoControles() {
		return roControles;
	}

	public void setRoControles(List<RoControles> roControles) {
		this.roControles = roControles;
	}
}