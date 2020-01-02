package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_criticidad database table.
 * 
 */
@Entity
@Table(name = "ro_criticidad")
@NamedQuery(name = "RoCriticidad.findAll", query = "SELECT r FROM RoCriticidad r")
public class RoCriticidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_CRIT")
	private int codigoCrit;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_CRIT")
	private String nombreCrit;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy = "roCriticidad")
	private List<RoProceso> roProcesos;

	// bi-directional many-to-one association to RoDetCriticProc
	@OneToMany(mappedBy = "roCriticidad")
	private List<RoDetCriticProc> roDetCriticProcs;

	public RoCriticidad() {
	}

	public int getCodigoCrit() {
		return this.codigoCrit;
	}

	public void setCodigoCrit(int codigoCrit) {
		this.codigoCrit = codigoCrit;
	}

	public String getNombreCrit() {
		return this.nombreCrit;
	}

	public void setNombreCrit(String nombreCrit) {
		this.nombreCrit = nombreCrit;
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

	public List<RoProceso> getRoProcesos() {
		return this.roProcesos;
	}

	public void setRoProcesos(List<RoProceso> roProcesos) {
		this.roProcesos = roProcesos;
	}

	public RoProceso addRoProceso(RoProceso roProceso) {
		getRoProcesos().add(roProceso);
		roProceso.setRoCriticidad(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setRoCriticidad(null);

		return roProceso;
	}

}