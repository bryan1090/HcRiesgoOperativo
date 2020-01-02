package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_tipo_ejecucion database table.
 * 
 */
@Entity
@Table(name = "ro_tipo_ejecucion")
@NamedQuery(name = "RoTipoEjecucion.findAll", query = "SELECT r FROM RoTipoEjecucion r")
public class RoTipoEjecucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_TIEJ")
	private int codigoTiej;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_TIEJ")
	private String nombreTiej;

	// bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy = "roTipoEjecucion")
	private List<RoProceso> roProcesos;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public RoTipoEjecucion() {
	}

	public int getCodigoTiej() {
		return this.codigoTiej;
	}

	public void setCodigoTiej(int codigoTiej) {
		this.codigoTiej = codigoTiej;
	}

	public String getNombreTiej() {
		return this.nombreTiej;
	}

	public void setNombreTiej(String nombreTiej) {
		this.nombreTiej = nombreTiej;
	}

	public List<RoProceso> getRoProcesos() {
		return this.roProcesos;
	}

	public void setRoProcesos(List<RoProceso> roProcesos) {
		this.roProcesos = roProcesos;
	}

	public RoProceso addRoProceso(RoProceso roProceso) {
		getRoProcesos().add(roProceso);
		roProceso.setRoTipoEjecucion(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setRoTipoEjecucion(null);

		return roProceso;
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