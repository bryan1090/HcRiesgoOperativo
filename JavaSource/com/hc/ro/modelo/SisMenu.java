package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the sis_menu database table.
 * 
 */
@Entity
@Table(name = "sis_menu")
@NamedQuery(name = "SisMenu.findAll", query = "SELECT s FROM SisMenu s")
public class SisMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_MENU")
	private int codigoMenu;

	@Column(name = "ANCESTRO_MENU")
	private String ancestroMenu;

	@Column(name = "NOMBRE_MENU")
	private String nombreMenu;

	@Column(name = "NUMERO_MENU")
	private String numeroMenu;

	@Column(name = "OPCION_MENU")
	private String opcionMenu;

	// bi-directional many-to-one association to SisAuditoria
	@OneToMany(mappedBy = "sisMenu")
	private List<SisAuditoria> sisAuditorias;

	// bi-directional many-to-one association to SisDetalleMenu

	@OneToMany(mappedBy = "sisMenu")
	private List<SisDetalleMenu> sisDetalleMenus;

	// bi-directional many-to-one association to SisDetalleReporte1
	@OneToMany(mappedBy = "sisMenu")
	private List<SisDetalleReporte1> sisDetalleReporte1s;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisObjeto
	@OneToMany(mappedBy = "sisMenu")
	private List<SisObjeto> sisObjetos;

	// bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy = "sisMenu")
	private List<SisPermiso> sisPermisos;

	public SisMenu() {
	}

	public int getCodigoMenu() {
		return this.codigoMenu;
	}

	public void setCodigoMenu(int codigoMenu) {
		this.codigoMenu = codigoMenu;
	}

	public String getAncestroMenu() {
		return this.ancestroMenu;
	}

	public void setAncestroMenu(String ancestroMenu) {
		this.ancestroMenu = ancestroMenu;
	}

	public String getNombreMenu() {
		return this.nombreMenu;
	}

	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}

	public String getNumeroMenu() {
		return this.numeroMenu;
	}

	public void setNumeroMenu(String numeroMenu) {
		this.numeroMenu = numeroMenu;
	}

	public String getOpcionMenu() {
		return this.opcionMenu;
	}

	public void setOpcionMenu(String opcionMenu) {
		this.opcionMenu = opcionMenu;
	}

	public List<SisAuditoria> getSisAuditorias() {
		return this.sisAuditorias;
	}

	public void setSisAuditorias(List<SisAuditoria> sisAuditorias) {
		this.sisAuditorias = sisAuditorias;
	}

	public SisAuditoria addSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().add(sisAuditoria);
		sisAuditoria.setSisMenu(this);

		return sisAuditoria;
	}

	public SisAuditoria removeSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().remove(sisAuditoria);
		sisAuditoria.setSisMenu(null);

		return sisAuditoria;
	}

	public List<SisDetalleMenu> getSisDetalleMenus() {
		return this.sisDetalleMenus;
	}

	public void setSisDetalleMenus(List<SisDetalleMenu> sisDetalleMenus) {
		this.sisDetalleMenus = sisDetalleMenus;
	}

	public SisDetalleMenu addSisDetalleMenus(SisDetalleMenu sisDetalleMenus) {
		getSisDetalleMenus().add(sisDetalleMenus);
		sisDetalleMenus.setSisMenu(this);

		return sisDetalleMenus;
	}

	public SisDetalleMenu removeSisDetalleMenus(SisDetalleMenu sisDetalleMenus) {
		getSisDetalleMenus().remove(sisDetalleMenus);
		sisDetalleMenus.setSisMenu(null);

		return sisDetalleMenus;
	}

	public List<SisDetalleReporte1> getSisDetalleReporte1s() {
		return this.sisDetalleReporte1s;
	}

	public void setSisDetalleReporte1s(
			List<SisDetalleReporte1> sisDetalleReporte1s) {
		this.sisDetalleReporte1s = sisDetalleReporte1s;
	}

	public SisDetalleReporte1 addSisDetalleReporte1(
			SisDetalleReporte1 sisDetalleReporte1) {
		getSisDetalleReporte1s().add(sisDetalleReporte1);
		sisDetalleReporte1.setSisMenu(this);

		return sisDetalleReporte1;
	}

	public SisDetalleReporte1 removeSisDetalleReporte1(
			SisDetalleReporte1 sisDetalleReporte1) {
		getSisDetalleReporte1s().remove(sisDetalleReporte1);
		sisDetalleReporte1.setSisMenu(null);

		return sisDetalleReporte1;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public List<SisObjeto> getSisObjetos() {
		return this.sisObjetos;
	}

	public void setSisObjetos(List<SisObjeto> sisObjetos) {
		this.sisObjetos = sisObjetos;
	}

	public SisObjeto addSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().add(sisObjeto);
		sisObjeto.setSisMenu(this);

		return sisObjeto;
	}

	public SisObjeto removeSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().remove(sisObjeto);
		sisObjeto.setSisMenu(null);

		return sisObjeto;
	}

	public List<SisPermiso> getSisPermisos() {
		return this.sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public SisPermiso addSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().add(sisPermiso);
		sisPermiso.setSisMenu(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisMenu(null);

		return sisPermiso;
	}

}