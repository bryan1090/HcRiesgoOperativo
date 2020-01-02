package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sis_parametro database table.
 * 
 */
@Entity
@Table(name="sis_parametro")
@NamedQuery(name="SisParametro.findAll", query="SELECT s FROM SisParametro s")
public class SisParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_PARA")
	private int codigoPara;

	@Column(name="NOMBRE_PARA")
	private String nombrePara;

	@Column(name="UTILIZACION_PARA")
	private String utilizacionPara;

	@Column(name="VALOR_PARA")
	private String valorPara;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisModulo
	@ManyToOne
	@JoinColumn(name="CODIGO_MODU")
	private SisModulo sisModulo;

	public SisParametro() {
	}

	public int getCodigoPara() {
		return this.codigoPara;
	}

	public void setCodigoPara(int codigoPara) {
		this.codigoPara = codigoPara;
	}

	public String getNombrePara() {
		return this.nombrePara;
	}

	public void setNombrePara(String nombrePara) {
		this.nombrePara = nombrePara;
	}

	public String getUtilizacionPara() {
		return this.utilizacionPara;
	}

	public void setUtilizacionPara(String utilizacionPara) {
		this.utilizacionPara = utilizacionPara;
	}

	public String getValorPara() {
		return this.valorPara;
	}

	public void setValorPara(String valorPara) {
		this.valorPara = valorPara;
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

	public SisModulo getSisModulo() {
		return this.sisModulo;
	}

	public void setSisModulo(SisModulo sisModulo) {
		this.sisModulo = sisModulo;
	}

}