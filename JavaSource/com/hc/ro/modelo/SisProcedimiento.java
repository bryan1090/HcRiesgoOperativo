package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_procedimiento database table.
 * 
 */
@Entity
@Table(name="sis_procedimiento")
@NamedQuery(name="SisProcedimiento.findAll", query="SELECT s FROM SisProcedimiento s")
public class SisProcedimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_PROC")
	private int codigoProc;

	@Column(name="FUNCION_PROC")
	private String funcionProc;

	@Column(name="LAPSO_DISPARO_PROC")
	private String lapsoDisparoProc;

	@Column(name="NOMBRE_PROC")
	private String nombreProc;

	@Column(name="OBSERVACION_PROC")
	private String observacionProc;

	//bi-directional many-to-one association to SisAuditoria
	@OneToMany(mappedBy="sisProcedimiento")
	private List<SisAuditoria> sisAuditorias;

	//bi-directional many-to-one association to SisDetalleProceso
	@OneToMany(mappedBy="sisProcedimiento")
	private List<SisDetalleProceso> sisDetalleProcesos;

	//bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy="sisProcedimiento")
	private List<SisPermiso> sisPermisos;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public SisProcedimiento() {
	}

	public int getCodigoProc() {
		return this.codigoProc;
	}

	public void setCodigoProc(int codigoProc) {
		this.codigoProc = codigoProc;
	}

	public String getFuncionProc() {
		return this.funcionProc;
	}

	public void setFuncionProc(String funcionProc) {
		this.funcionProc = funcionProc;
	}

	public String getLapsoDisparoProc() {
		return this.lapsoDisparoProc;
	}

	public void setLapsoDisparoProc(String lapsoDisparoProc) {
		this.lapsoDisparoProc = lapsoDisparoProc;
	}

	public String getNombreProc() {
		return this.nombreProc;
	}

	public void setNombreProc(String nombreProc) {
		this.nombreProc = nombreProc;
	}

	public String getObservacionProc() {
		return this.observacionProc;
	}

	public void setObservacionProc(String observacionProc) {
		this.observacionProc = observacionProc;
	}

	public List<SisAuditoria> getSisAuditorias() {
		return this.sisAuditorias;
	}

	public void setSisAuditorias(List<SisAuditoria> sisAuditorias) {
		this.sisAuditorias = sisAuditorias;
	}

	public SisAuditoria addSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().add(sisAuditoria);
		sisAuditoria.setSisProcedimiento(this);

		return sisAuditoria;
	}

	public SisAuditoria removeSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().remove(sisAuditoria);
		sisAuditoria.setSisProcedimiento(null);

		return sisAuditoria;
	}

	public List<SisDetalleProceso> getSisDetalleProcesos() {
		return this.sisDetalleProcesos;
	}

	public void setSisDetalleProcesos(List<SisDetalleProceso> sisDetalleProcesos) {
		this.sisDetalleProcesos = sisDetalleProcesos;
	}

	public SisDetalleProceso addSisDetalleProceso(SisDetalleProceso sisDetalleProceso) {
		getSisDetalleProcesos().add(sisDetalleProceso);
		sisDetalleProceso.setSisProcedimiento(this);

		return sisDetalleProceso;
	}

	public SisDetalleProceso removeSisDetalleProceso(SisDetalleProceso sisDetalleProceso) {
		getSisDetalleProcesos().remove(sisDetalleProceso);
		sisDetalleProceso.setSisProcedimiento(null);

		return sisDetalleProceso;
	}

	public List<SisPermiso> getSisPermisos() {
		return this.sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public SisPermiso addSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().add(sisPermiso);
		sisPermiso.setSisProcedimiento(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisProcedimiento(null);

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