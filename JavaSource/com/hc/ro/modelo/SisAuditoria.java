package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sis_auditoria database table.
 * 
 */
@Entity
@Table(name="sis_auditoria")
@NamedQuery(name="SisAuditoria.findAll", query="SELECT s FROM SisAuditoria s")
public class SisAuditoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_AUDI")
	private String codigoAudi;

	@Column(name="ACCION_AUDI")
	private String accionAudi;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_AUDI")
	private Date fechaAudi;

	@Column(name="HORA_AUDI")
	private String horaAudi;

	@Column(name="SQL_AUDI")
	private String sqlAudi;

	@Column(name="TABLA_AUDI")
	private String tablaAudi;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisMenu
	@ManyToOne
	@JoinColumn(name="CODIGO_MENU")
	private SisMenu sisMenu;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisUsuario
	@ManyToOne
	@JoinColumn(name="CODIGO_USUA")
	private SisUsuario sisUsuario;

	//bi-directional many-to-one association to SisProcedimiento
	@ManyToOne
	@JoinColumn(name="CODIGO_PROC")
	private SisProcedimiento sisProcedimiento;

	public SisAuditoria() {
	}

	public String getCodigoAudi() {
		return this.codigoAudi;
	}

	public void setCodigoAudi(String codigoAudi) {
		this.codigoAudi = codigoAudi;
	}

	public String getAccionAudi() {
		return this.accionAudi;
	}

	public void setAccionAudi(String accionAudi) {
		this.accionAudi = accionAudi;
	}

	public Date getFechaAudi() {
		return this.fechaAudi;
	}

	public void setFechaAudi(Date fechaAudi) {
		this.fechaAudi = fechaAudi;
	}

	public String getHoraAudi() {
		return this.horaAudi;
	}

	public void setHoraAudi(String horaAudi) {
		this.horaAudi = horaAudi;
	}

	public String getSqlAudi() {
		return this.sqlAudi;
	}

	public void setSqlAudi(String sqlAudi) {
		this.sqlAudi = sqlAudi;
	}

	public String getTablaAudi() {
		return this.tablaAudi;
	}

	public void setTablaAudi(String tablaAudi) {
		this.tablaAudi = tablaAudi;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public SisMenu getSisMenu() {
		return this.sisMenu;
	}

	public void setSisMenu(SisMenu sisMenu) {
		this.sisMenu = sisMenu;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public SisUsuario getSisUsuario() {
		return this.sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

	public SisProcedimiento getSisProcedimiento() {
		return this.sisProcedimiento;
	}

	public void setSisProcedimiento(SisProcedimiento sisProcedimiento) {
		this.sisProcedimiento = sisProcedimiento;
	}

}