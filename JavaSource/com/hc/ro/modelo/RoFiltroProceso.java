package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ro_filtro_proceso database table.
 * 
 */
@Entity
@Table(name="ro_filtro_proceso")
@NamedQuery(name="RoFiltroProceso.findAll", query="SELECT r FROM RoFiltroProceso r")
public class RoFiltroProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_FLPR")
	private int codigoFlpr;

	//bi-directional many-to-one association to RoProceso
	@ManyToOne
	@JoinColumn(name="CODIGO_PROS")
	private RoProceso roProceso;

	//bi-directional many-to-one association to RoAgencia
	@ManyToOne
	@JoinColumn(name="CODIGO_AGIA")
	private RoAgencia roAgencia;

	//bi-directional many-to-one association to RoDepartamento
	@ManyToOne
	@JoinColumn(name="CODIGO_DEPT")
	private RoDepartamento roDepartamento;

	//bi-directional many-to-one association to RoNegocio
	@ManyToOne
	@JoinColumn(name="CODIGO_NEGO")
	private RoNegocio roNegocio;

	public RoFiltroProceso() {
	}

	

	public RoProceso getRoProceso() {
		return this.roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

	public RoAgencia getRoAgencia() {
		return this.roAgencia;
	}

	public void setRoAgencia(RoAgencia roAgencia) {
		this.roAgencia = roAgencia;
	}

	public RoDepartamento getRoDepartamento() {
		return this.roDepartamento;
	}

	public void setRoDepartamento(RoDepartamento roDepartamento) {
		this.roDepartamento = roDepartamento;
	}

	public RoNegocio getRoNegocio() {
		return this.roNegocio;
	}

	public void setRoNegocio(RoNegocio roNegocio) {
		this.roNegocio = roNegocio;
	}



	public int getCodigoFlpr() {
		return codigoFlpr;
	}



	public void setCodigoFlpr(int codigoFlpr) {
		this.codigoFlpr = codigoFlpr;
	}

}