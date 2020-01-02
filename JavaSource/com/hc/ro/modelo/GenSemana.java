package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the gen_semana database table.
 * 
 */
@Entity
@Table(name="gen_semana")
@NamedQuery(name="GenSemana.findAll", query="SELECT g FROM GenSemana g")
public class GenSemana implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_GSEM")
	private int codigoGsem;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_FINAL_GSEM")
	private Date fechaFinalGsem;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INICIO_GSEM")
	private Date fechaInicioGsem;

	@Column(name="NOMBRE_GSEM")
	private String nombreGsem;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public GenSemana() {
	}

	public int getCodigoGsem() {
		return this.codigoGsem;
	}

	public void setCodigoGsem(int codigoGsem) {
		this.codigoGsem = codigoGsem;
	}

	public Date getFechaFinalGsem() {
		return this.fechaFinalGsem;
	}

	public void setFechaFinalGsem(Date fechaFinalGsem) {
		this.fechaFinalGsem = fechaFinalGsem;
	}

	public Date getFechaInicioGsem() {
		return this.fechaInicioGsem;
	}

	public void setFechaInicioGsem(Date fechaInicioGsem) {
		this.fechaInicioGsem = fechaInicioGsem;
	}

	public String getNombreGsem() {
		return this.nombreGsem;
	}

	public void setNombreGsem(String nombreGsem) {
		this.nombreGsem = nombreGsem;
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