package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_reporte database table.
 * 
 */
@Entity
@Table(name="sis_reporte")
@NamedQuery(name="SisReporte.findAll", query="SELECT s FROM SisReporte s")
public class SisReporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_REPO")
	private int codigoRepo;

	@Column(name="DATAOBJECT_REPO")
	private String dataobjectRepo;

	@Column(name="FUNCION_REPO")
	private String funcionRepo;

	@Column(name="NOMBRE_ALTERNO_REPO")
	private String nombreAlternoRepo;

	@Column(name="NOMBRE_REPO")
	private String nombreRepo;

	//bi-directional many-to-one association to SisDetalleReporte1
	@OneToMany(mappedBy="sisReporte")
	private List<SisDetalleReporte1> sisDetalleReporte1s;

	//bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy="sisReporte")
	private List<SisPermiso> sisPermisos;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public SisReporte() {
	}

	public int getCodigoRepo() {
		return this.codigoRepo;
	}

	public void setCodigoRepo(int codigoRepo) {
		this.codigoRepo = codigoRepo;
	}

	public String getDataobjectRepo() {
		return this.dataobjectRepo;
	}

	public void setDataobjectRepo(String dataobjectRepo) {
		this.dataobjectRepo = dataobjectRepo;
	}

	public String getFuncionRepo() {
		return this.funcionRepo;
	}

	public void setFuncionRepo(String funcionRepo) {
		this.funcionRepo = funcionRepo;
	}

	public String getNombreAlternoRepo() {
		return this.nombreAlternoRepo;
	}

	public void setNombreAlternoRepo(String nombreAlternoRepo) {
		this.nombreAlternoRepo = nombreAlternoRepo;
	}

	public String getNombreRepo() {
		return this.nombreRepo;
	}

	public void setNombreRepo(String nombreRepo) {
		this.nombreRepo = nombreRepo;
	}

	public List<SisDetalleReporte1> getSisDetalleReporte1s() {
		return this.sisDetalleReporte1s;
	}

	public void setSisDetalleReporte1s(List<SisDetalleReporte1> sisDetalleReporte1s) {
		this.sisDetalleReporte1s = sisDetalleReporte1s;
	}

	public SisDetalleReporte1 addSisDetalleReporte1(SisDetalleReporte1 sisDetalleReporte1) {
		getSisDetalleReporte1s().add(sisDetalleReporte1);
		sisDetalleReporte1.setSisReporte(this);

		return sisDetalleReporte1;
	}

	public SisDetalleReporte1 removeSisDetalleReporte1(SisDetalleReporte1 sisDetalleReporte1) {
		getSisDetalleReporte1s().remove(sisDetalleReporte1);
		sisDetalleReporte1.setSisReporte(null);

		return sisDetalleReporte1;
	}

	public List<SisPermiso> getSisPermisos() {
		return this.sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public SisPermiso addSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().add(sisPermiso);
		sisPermiso.setSisReporte(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisReporte(null);

		return sisPermiso;
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