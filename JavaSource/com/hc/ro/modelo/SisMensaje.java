package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sis_mensaje database table.
 * 
 */
@Entity
@Table(name="sis_mensaje")
@NamedQuery(name="SisMensaje.findAll", query="SELECT s FROM SisMensaje s")
public class SisMensaje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_MENS")
	private int codigoMens;

	@Column(name="CODIGO_ERROR_MENS")
	private String codigoErrorMens;

	@Column(name="DESCRIPCION_MENS")
	private String descripcionMens;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public SisMensaje() {
	}

	public int getCodigoMens() {
		return this.codigoMens;
	}

	public void setCodigoMens(int codigoMens) {
		this.codigoMens = codigoMens;
	}

	public String getCodigoErrorMens() {
		return this.codigoErrorMens;
	}

	public void setCodigoErrorMens(String codigoErrorMens) {
		this.codigoErrorMens = codigoErrorMens;
	}

	public String getDescripcionMens() {
		return this.descripcionMens;
	}

	public void setDescripcionMens(String descripcionMens) {
		this.descripcionMens = descripcionMens;
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