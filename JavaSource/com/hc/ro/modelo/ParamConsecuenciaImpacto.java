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
@Table(name = "param_consecuencia_impacto")
@NamedQuery(name = "ParamConsecuenciaImpacto.findAll", query = "SELECT r FROM ParamConsecuenciaImpacto r")
public class ParamConsecuenciaImpacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_PCONI")
	private Integer codigoPconi;

	@Pattern(message = "El campo Nombre solo debe contener letras, espacios y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_PCONI")
	private String nombrePconi;

	@Column(name = "NUMERO_PCONI")
	private int numeroPconi;

	public ParamConsecuenciaImpacto(Integer codigoPconi, String nombrePconi,
			int numeroPconi) {
		super();
		this.codigoPconi = codigoPconi;
		this.nombrePconi = nombrePconi;
		this.numeroPconi = numeroPconi;
	}

	@OneToMany(mappedBy = "paramConsecuenciaImpacto")
	private List<RoAriesgo> roAriesgos;

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

	public int getNumeroPconi() {
		return numeroPconi;
	}

	public void setNumeroPconi(int numeroPconi) {
		this.numeroPconi = numeroPconi;
	}

	public ParamConsecuenciaImpacto() {
	}

	public List<RoAriesgo> getRoAriesgos() {
		return roAriesgos;
	}

	public void setRoAriesgos(List<RoAriesgo> roAriesgos) {
		this.roAriesgos = roAriesgos;
	}

}