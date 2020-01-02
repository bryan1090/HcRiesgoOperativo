package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gen_tipo_identific database table.
 * 
 */
@Entity
@Table(name="gen_tipo_identific")
@NamedQuery(name="GenTipoIdentific.findAll", query="SELECT g FROM GenTipoIdentific g")
public class GenTipoIdentific implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TIID")
	private int codigoTiid;

	@Column(name="NOMBRE_TIID")
	private String nombreTiid;

	@Column(name="VALOR1_TIID")
	private String valor1Tiid;

	@Column(name="VALOR2_TIID")
	private String valor2Tiid;

	@Column(name="VALOR3_TIID")
	private String valor3Tiid;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public GenTipoIdentific() {
	}

	public int getCodigoTiid() {
		return this.codigoTiid;
	}

	public void setCodigoTiid(int codigoTiid) {
		this.codigoTiid = codigoTiid;
	}

	public String getNombreTiid() {
		return this.nombreTiid;
	}

	public void setNombreTiid(String nombreTiid) {
		this.nombreTiid = nombreTiid;
	}

	public String getValor1Tiid() {
		return this.valor1Tiid;
	}

	public void setValor1Tiid(String valor1Tiid) {
		this.valor1Tiid = valor1Tiid;
	}

	public String getValor2Tiid() {
		return this.valor2Tiid;
	}

	public void setValor2Tiid(String valor2Tiid) {
		this.valor2Tiid = valor2Tiid;
	}

	public String getValor3Tiid() {
		return this.valor3Tiid;
	}

	public void setValor3Tiid(String valor3Tiid) {
		this.valor3Tiid = valor3Tiid;
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