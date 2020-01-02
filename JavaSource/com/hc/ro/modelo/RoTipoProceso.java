package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_tipo_proceso database table.
 * 
 */
@Entity
@Table(name = "ro_tipo_proceso")
@NamedQuery(name = "RoTipoProceso.findAll", query = "SELECT r FROM RoTipoProceso r")
public class RoTipoProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_TIPR")
	private int codigoTipr;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_TIPR")
	private String nombreTipr;

	// bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy = "roTipoProceso")
	private List<RoProceso> roProcesos;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public RoTipoProceso() {
	}

	public int getCodigoTipr() {
		return this.codigoTipr;
	}

	public void setCodigoTipr(int codigoTipr) {
		this.codigoTipr = codigoTipr;
	}

	public String getNombreTipr() {
		return this.nombreTipr;
	}

	public void setNombreTipr(String nombreTipr) {
		this.nombreTipr = nombreTipr;
	}

	public List<RoProceso> getRoProcesos() {
		return this.roProcesos;
	}

	public void setRoProcesos(List<RoProceso> roProcesos) {
		this.roProcesos = roProcesos;
	}

	public RoProceso addRoProceso(RoProceso roProceso) {
		getRoProcesos().add(roProceso);
		roProceso.setRoTipoProceso(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setRoTipoProceso(null);

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