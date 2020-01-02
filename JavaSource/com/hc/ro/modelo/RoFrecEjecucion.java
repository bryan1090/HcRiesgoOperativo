package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ro_frec_ejecucion database table.
 * 
 */
@Entity
@Table(name="ro_frec_ejecucion")
@NamedQuery(name="RoFrecEjecucion.findAll", query="SELECT r FROM RoFrecEjecucion r")
public class RoFrecEjecucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_FREJ")
	private int codigoFrej;

	@Column(name="NOMBRE_FREJ")
	private String nombreFrej;

	@Column(name="VALOR_FREJ")
	private BigDecimal valorFrej;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy="roFrecEjecucion")
	private List<RoProceso> roProcesos;

	public RoFrecEjecucion() {
	}

	public int getCodigoFrej() {
		return this.codigoFrej;
	}

	public void setCodigoFrej(int codigoFrej) {
		this.codigoFrej = codigoFrej;
	}

	public String getNombreFrej() {
		return this.nombreFrej;
	}

	public void setNombreFrej(String nombreFrej) {
		this.nombreFrej = nombreFrej;
	}

	public BigDecimal getValorFrej() {
		return this.valorFrej;
	}

	public void setValorFrej(BigDecimal valorFrej) {
		this.valorFrej = valorFrej;
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
		roProceso.setRoFrecEjecucion(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setRoFrecEjecucion(null);

		return roProceso;
	}

}