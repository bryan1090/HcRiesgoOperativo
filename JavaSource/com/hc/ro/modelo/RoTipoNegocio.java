package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_tipo_negocio database table.
 * 
 */
@Entity
@Table(name = "ro_tipo_negocio")
@NamedQuery(name = "RoTipoNegocio.findAll", query = "SELECT r FROM RoTipoNegocio r")
public class RoTipoNegocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name = "CODIGO_TNEG")
	private int codigoTneg;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_TNEG")
	private String nombreTneg;

	// bi-directional many-to-one association to RoNegocio
	@OneToMany(mappedBy = "roTipoNegocio")
	private List<RoNegocio> roNegocios;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public RoTipoNegocio() {
	}

	public int getCodigoTneg() {
		return this.codigoTneg;
	}

	public void setCodigoTneg(int codigoTneg) {
		this.codigoTneg = codigoTneg;
	}

	public String getNombreTneg() {
		return this.nombreTneg;
	}

	public void setNombreTneg(String nombreTneg) {
		this.nombreTneg = nombreTneg;
	}

	public List<RoNegocio> getRoNegocios() {
		return this.roNegocios;
	}

	public void setRoNegocios(List<RoNegocio> roNegocios) {
		this.roNegocios = roNegocios;
	}

	public RoNegocio addRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().add(roNegocio);
		roNegocio.setRoTipoNegocio(this);

		return roNegocio;
	}

	public RoNegocio removeRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().remove(roNegocio);
		roNegocio.setRoTipoNegocio(null);

		return roNegocio;
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

}