package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ro_dept_agencia database table.
 * 
 */
@Entity
@Table(name="ro_dept_agencia")
@NamedQuery(name="RoDeptAgencia.findAll", query="SELECT r FROM RoDeptAgencia r")
public class RoDeptAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_DEAG")
	private int codigoDeag;

	//bi-directional many-to-one association to RoDepartamento
	@ManyToOne
	@JoinColumn(name="CODIGO_DEPT")
	private RoDepartamento roDepartamento;

	//bi-directional many-to-one association to RoAgencia
	@ManyToOne
	@JoinColumn(name="CODIGO_AGIA")
	private RoAgencia roAgencia;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public RoDeptAgencia() {
	}

	public int getCodigoDeag() {
		return this.codigoDeag;
	}

	public void setCodigoDeag(int codigoDeag) {
		this.codigoDeag = codigoDeag;
	}

	public RoDepartamento getRoDepartamento() {
		return this.roDepartamento;
	}

	public void setRoDepartamento(RoDepartamento roDepartamento) {
		this.roDepartamento = roDepartamento;
	}

	public RoAgencia getRoAgencia() {
		return this.roAgencia;
	}

	public void setRoAgencia(RoAgencia roAgencia) {
		this.roAgencia = roAgencia;
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