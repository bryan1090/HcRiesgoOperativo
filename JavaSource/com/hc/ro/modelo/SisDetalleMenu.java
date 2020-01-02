package com.hc.ro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the sis_detalle_menu database table.
 * 
 */
@Entity
@Table(name="sis_detalle_menu")
@NamedQuery(name="SisDetalleMenu.findAll", query="SELECT s FROM SisDetalleMenu s")
public class SisDetalleMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_DEME")
	private int codigoDeme;

	@Column(name="ANCESTRO_DEME")
	private String ancestroDeme;

	@Column(name="CLAVE_AUTO_DEME")
	private BigDecimal claveAutoDeme;

	@Column(name="CLAVE_PRIMARIA_DEME")
	private String clavePrimariaDeme;

	@Column(name="COMBO_DEME")
	private String comboDeme;

	@Column(name="DATAOBJECT_DEME")
	private String dataobjectDeme;

	@Column(name="ETIQUETA_DEME")
	private String etiquetaDeme;

	@Column(name="NOMBRE_DEME")
	private String nombreDeme;

	@Column(name="ORDENAMIENTO_DEME")
	private String ordenamientoDeme;

	@Column(name="RECURSIVO_DEME")
	private String recursivoDeme;

	@Column(name="RELACION_DEME")
	private String relacionDeme;

	@Column(name="SERIAL_DEME")
	private String serialDeme;

	@Column(name="TABLA_DEME")
	private String tablaDeme;
	
	@Column(name="ACCION_DEME")
	private String accionDeme;
	
	@Column(name="ICONO_DEME")
	private String iconoDeme;

	//bi-directional many-to-one association to SisMenu
	@ManyToOne
	@JoinColumn(name="CODIGO_MENU")
	private SisMenu sisMenu;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;
	
	@OneToMany(mappedBy="sisDetalleMenu")
	private List<SisPermiso> sisPermisos;
	
	@OneToMany(mappedBy="sisDetalleMenu")
	private List<RoTipoResp> roTipoResp;
	
	public SisDetalleMenu() {
	}

	public int getCodigoDeme() {
		return this.codigoDeme;
	}

	public void setCodigoDeme(int codigoDeme) {
		this.codigoDeme = codigoDeme;
	}

	public String getAncestroDeme() {
		return this.ancestroDeme;
	}

	public void setAncestroDeme(String ancestroDeme) {
		this.ancestroDeme = ancestroDeme;
	}

	public BigDecimal getClaveAutoDeme() {
		return this.claveAutoDeme;
	}

	public void setClaveAutoDeme(BigDecimal claveAutoDeme) {
		this.claveAutoDeme = claveAutoDeme;
	}

	public String getClavePrimariaDeme() {
		return this.clavePrimariaDeme;
	}

	public void setClavePrimariaDeme(String clavePrimariaDeme) {
		this.clavePrimariaDeme = clavePrimariaDeme;
	}

	public String getComboDeme() {
		return this.comboDeme;
	}

	public void setComboDeme(String comboDeme) {
		this.comboDeme = comboDeme;
	}

	public String getDataobjectDeme() {
		return this.dataobjectDeme;
	}

	public void setDataobjectDeme(String dataobjectDeme) {
		this.dataobjectDeme = dataobjectDeme;
	}

	public String getEtiquetaDeme() {
		return this.etiquetaDeme;
	}

	public void setEtiquetaDeme(String etiquetaDeme) {
		this.etiquetaDeme = etiquetaDeme;
	}

	public String getNombreDeme() {
		return this.nombreDeme;
	}

	public void setNombreDeme(String nombreDeme) {
		this.nombreDeme = nombreDeme;
	}

	public String getOrdenamientoDeme() {
		return this.ordenamientoDeme;
	}

	public void setOrdenamientoDeme(String ordenamientoDeme) {
		this.ordenamientoDeme = ordenamientoDeme;
	}

	public String getRecursivoDeme() {
		return this.recursivoDeme;
	}

	public void setRecursivoDeme(String recursivoDeme) {
		this.recursivoDeme = recursivoDeme;
	}

	public String getRelacionDeme() {
		return this.relacionDeme;
	}

	public void setRelacionDeme(String relacionDeme) {
		this.relacionDeme = relacionDeme;
	}

	public String getSerialDeme() {
		return this.serialDeme;
	}

	public void setSerialDeme(String serialDeme) {
		this.serialDeme = serialDeme;
	}

	public String getTablaDeme() {
		return this.tablaDeme;
	}

	public void setTablaDeme(String tablaDeme) {
		this.tablaDeme = tablaDeme;
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

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public String getAccionDeme() {
		return accionDeme;
	}

	public void setAccionDeme(String accionDeme) {
		this.accionDeme = accionDeme;
	}

	public String getIconoDeme() {
		return iconoDeme;
	}

	public void setIconoDeme(String iconoDeme) {
		this.iconoDeme = iconoDeme;
	}

	public List<SisPermiso> getSisPermisos() {
		return sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public List<RoTipoResp> getRoTipoResp() {
		return roTipoResp;
	}

	public void setRoTipoResp(List<RoTipoResp> roTipoResp) {
		this.roTipoResp = roTipoResp;
	}
	

}