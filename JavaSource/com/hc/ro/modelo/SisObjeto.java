package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_objeto database table.
 * 
 */
@Entity
@Table(name="sis_objeto")
@NamedQuery(name="SisObjeto.findAll", query="SELECT s FROM SisObjeto s")
public class SisObjeto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_OBJE")
	private int codigoObje;

	@Column(name="ALTERNO_OBJE")
	private String alternoObje;

	@Column(name="ANCESTRO_OBJE")
	private String ancestroObje;

	@Column(name="DESCRIPCION_OBJE")
	private String descripcionObje;

	@Column(name="MASCARA_OBJE")
	private String mascaraObje;

	@Column(name="NOMBRE_OBJE")
	private String nombreObje;

	@Column(name="NUMERO_OBJE")
	private String numeroObje;

	@Column(name="TABLA_MENU_OBJE")
	private String tablaMenuObje;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisTipoObjeto
	@ManyToOne
	@JoinColumn(name="CODIGO_TIOB")
	private SisTipoObjeto sisTipoObjeto;

	//bi-directional many-to-one association to SisModulo
	@ManyToOne
	@JoinColumn(name="CODIGO_MODU")
	private SisModulo sisModulo;

	//bi-directional many-to-one association to SisMenu
	@ManyToOne
	@JoinColumn(name="CODIGO_MENU")
	private SisMenu sisMenu;

	//bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy="sisObjeto")
	private List<SisPermiso> sisPermisos;

	public SisObjeto() {
	}

	public int getCodigoObje() {
		return this.codigoObje;
	}

	public void setCodigoObje(int codigoObje) {
		this.codigoObje = codigoObje;
	}

	public String getAlternoObje() {
		return this.alternoObje;
	}

	public void setAlternoObje(String alternoObje) {
		this.alternoObje = alternoObje;
	}

	public String getAncestroObje() {
		return this.ancestroObje;
	}

	public void setAncestroObje(String ancestroObje) {
		this.ancestroObje = ancestroObje;
	}

	public String getDescripcionObje() {
		return this.descripcionObje;
	}

	public void setDescripcionObje(String descripcionObje) {
		this.descripcionObje = descripcionObje;
	}

	public String getMascaraObje() {
		return this.mascaraObje;
	}

	public void setMascaraObje(String mascaraObje) {
		this.mascaraObje = mascaraObje;
	}

	public String getNombreObje() {
		return this.nombreObje;
	}

	public void setNombreObje(String nombreObje) {
		this.nombreObje = nombreObje;
	}

	public String getNumeroObje() {
		return this.numeroObje;
	}

	public void setNumeroObje(String numeroObje) {
		this.numeroObje = numeroObje;
	}

	public String getTablaMenuObje() {
		return this.tablaMenuObje;
	}

	public void setTablaMenuObje(String tablaMenuObje) {
		this.tablaMenuObje = tablaMenuObje;
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

	public SisTipoObjeto getSisTipoObjeto() {
		return this.sisTipoObjeto;
	}

	public void setSisTipoObjeto(SisTipoObjeto sisTipoObjeto) {
		this.sisTipoObjeto = sisTipoObjeto;
	}

	public SisModulo getSisModulo() {
		return this.sisModulo;
	}

	public void setSisModulo(SisModulo sisModulo) {
		this.sisModulo = sisModulo;
	}

	public SisMenu getSisMenu() {
		return this.sisMenu;
	}

	public void setSisMenu(SisMenu sisMenu) {
		this.sisMenu = sisMenu;
	}

	public List<SisPermiso> getSisPermisos() {
		return this.sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public SisPermiso addSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().add(sisPermiso);
		sisPermiso.setSisObjeto(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisObjeto(null);

		return sisPermiso;
	}

}