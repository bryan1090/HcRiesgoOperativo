package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;


/**
 * The persistent class for the ro_tipo_agencia database table.
 * 
 */
@Entity
@Table(name="ro_tipo_agencia")
@NamedQuery(name="RoTipoAgencia.findAll", query="SELECT r FROM RoTipoAgencia r")
public class RoTipoAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TIAG")
	private int codigoTiag;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="NOMBRE_TIAG")
	private String nombreTiag;

	//bi-directional many-to-one association to RoAgencia
	@OneToMany(mappedBy="roTipoAgencia")
	private List<RoAgencia> roAgencias;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	public RoTipoAgencia() {
	}

	public int getCodigoTiag() {
		return this.codigoTiag;
	}

	public void setCodigoTiag(int codigoTiag) {
		this.codigoTiag = codigoTiag;
	}

	public String getNombreTiag() {
		return this.nombreTiag;
	}

	public void setNombreTiag(String nombreTiag) {
		this.nombreTiag = nombreTiag;
	}

	public List<RoAgencia> getRoAgencias() {
		return this.roAgencias;
	}

	public void setRoAgencias(List<RoAgencia> roAgencias) {
		this.roAgencias = roAgencias;
	}

	public RoAgencia addRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().add(roAgencia);
		roAgencia.setRoTipoAgencia(this);

		return roAgencia;
	}

	public RoAgencia removeRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().remove(roAgencia);
		roAgencia.setRoTipoAgencia(null);

		return roAgencia;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

}