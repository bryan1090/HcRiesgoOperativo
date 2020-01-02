package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the sis_permiso database table.
 * 
 */
@Entity
@Table(name = "sis_permiso")
@NamedQuery(name = "SisPermiso.findAll", query = "SELECT s FROM SisPermiso s")
public class SisPermiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_PERM")
	private int codigoPerm;

	@Column(name = "TIPO_PERM")
	private int tipoPerm;

	@Column(name = "VALOR_PERM")
	private String valorPerm;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisReporte
	@ManyToOne
	@JoinColumn(name = "CODIGO_REPO")
	private SisReporte sisReporte;

	// bi-directional many-to-one association to SisReporte
	@ManyToOne
	@JoinColumn(name = "CODIGO_DEME")
	private SisDetalleMenu sisDetalleMenu;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to SisUsuario
	@ManyToOne
	@JoinColumn(name = "CODIGO_USUA")
	private SisUsuario sisUsuario;

	// bi-directional many-to-one association to SisObjeto
	@ManyToOne
	@JoinColumn(name = "CODIGO_OBJE")
	private SisObjeto sisObjeto;

	// bi-directional many-to-one association to SisProcedimiento
	@ManyToOne
	@JoinColumn(name = "CODIGO_PROC")
	private SisProcedimiento sisProcedimiento;

	// bi-directional many-to-one association to SisMenu
	@ManyToOne
	@JoinColumn(name = "CODIGO_MENU")
	private SisMenu sisMenu;

	// bi-directional many-to-one association to SisPerfil
	@ManyToOne
	@JoinColumn(name = "CODIGO_PERF")
	private SisPerfil sisPerfil;

	// bi-directional many-to-one association to SisPermisoValor
	@OneToMany(mappedBy = "sisPermiso")
	private List<SisPermisoValor> sisPermisoValors;

	public SisPermiso() {
	}

	public int getCodigoPerm() {
		return this.codigoPerm;
	}

	public void setCodigoPerm(int codigoPerm) {
		this.codigoPerm = codigoPerm;
	}

	public int getTipoPerm() {
		return this.tipoPerm;
	}

	public void setTipoPerm(int tipoPerm) {
		this.tipoPerm = tipoPerm;
	}

	public String getValorPerm() {
		return this.valorPerm;
	}

	public void setValorPerm(String valorPerm) {
		this.valorPerm = valorPerm;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public SisReporte getSisReporte() {
		return this.sisReporte;
	}

	public void setSisReporte(SisReporte sisReporte) {
		this.sisReporte = sisReporte;
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

	public SisObjeto getSisObjeto() {
		return this.sisObjeto;
	}

	public void setSisObjeto(SisObjeto sisObjeto) {
		this.sisObjeto = sisObjeto;
	}

	public SisProcedimiento getSisProcedimiento() {
		return this.sisProcedimiento;
	}

	public void setSisProcedimiento(SisProcedimiento sisProcedimiento) {
		this.sisProcedimiento = sisProcedimiento;
	}

	public SisMenu getSisMenu() {
		return this.sisMenu;
	}

	public void setSisMenu(SisMenu sisMenu) {
		this.sisMenu = sisMenu;
	}

	public SisPerfil getSisPerfil() {
		return this.sisPerfil;
	}

	public void setSisPerfil(SisPerfil sisPerfil) {
		this.sisPerfil = sisPerfil;
	}

	public List<SisPermisoValor> getSisPermisoValors() {
		return this.sisPermisoValors;
	}

	public void setSisPermisoValors(List<SisPermisoValor> sisPermisoValors) {
		this.sisPermisoValors = sisPermisoValors;
	}

	
	public SisPermisoValor addSisPermisoValor(SisPermisoValor sisPermisoValor) {
		getSisPermisoValors().add(sisPermisoValor);
		sisPermisoValor.setSisPermiso(this);

		return sisPermisoValor;
	}

	public SisPermisoValor removeSisPermisoValor(SisPermisoValor sisPermisoValor) {
		getSisPermisoValors().remove(sisPermisoValor);
		sisPermisoValor.setSisPermiso(null);

		return sisPermisoValor;
	}

	public SisDetalleMenu getSisDetalleMenu() {
		return sisDetalleMenu;
	}

	public void setSisDetalleMenu(SisDetalleMenu sisDetalleMenu) {
		this.sisDetalleMenu = sisDetalleMenu;
	}

	

}