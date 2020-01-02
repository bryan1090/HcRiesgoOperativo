package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sis_permiso_valor database table.
 * 
 */
@Entity
@Table(name="sis_permiso_valor")
@NamedQuery(name="SisPermisoValor.findAll", query="SELECT s FROM SisPermisoValor s")
public class SisPermisoValor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_PEVA")
	private int codigoPeva;

	@Column(name="VALOR_PEVA")
	private String valorPeva;

	//bi-directional many-to-one association to SisPermiso
	@ManyToOne
	@JoinColumn(name="CODIGO_PERM")
	private SisPermiso sisPermiso;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	public SisPermisoValor() {
	}

	public int getCodigoPeva() {
		return this.codigoPeva;
	}

	public void setCodigoPeva(int codigoPeva) {
		this.codigoPeva = codigoPeva;
	}

	public String getValorPeva() {
		return this.valorPeva;
	}

	public void setValorPeva(String valorPeva) {
		this.valorPeva = valorPeva;
	}

	public SisPermiso getSisPermiso() {
		return this.sisPermiso;
	}

	public void setSisPermiso(SisPermiso sisPermiso) {
		this.sisPermiso = sisPermiso;
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