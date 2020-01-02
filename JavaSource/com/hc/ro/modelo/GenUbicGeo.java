package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gen_ubic_geo database table.
 * 
 */
@Entity
@Table(name="gen_ubic_geo")
@NamedQuery(name="GenUbicGeo.findAll", query="SELECT g FROM GenUbicGeo g")
public class GenUbicGeo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_UBGE")
	private int codigoUbge;

	@Column(name="ANCESTRO_UBGE")
	private String ancestroUbge;

	@Column(name="NOMBRE_UBGE")
	private String nombreUbge;

	@Column(name="NUMERO_UBGE")
	private String numeroUbge;

	@Column(name="VALOR1_UBGE")
	private String valor1Ubge;

	@Column(name="VALOR2_UBGE")
	private String valor2Ubge;

	@Column(name="VALOR3_UBGE")
	private String valor3Ubge;

	@Column(name="VALOR4_UBGE")
	private String valor4Ubge;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to GenTipoUbiGeog
	@ManyToOne
	@JoinColumn(name="CODIGO_TIUG")
	private GenTipoUbiGeog genTipoUbiGeog;

	//bi-directional many-to-one association to GenNivelArbol
	@ManyToOne
	@JoinColumn(name="CODIGO_GNIV")
	private GenNivelArbol genNivelArbol;

	//bi-directional many-to-one association to SisEmpresa
	@OneToMany(mappedBy="genUbicGeo")
	private List<SisEmpresa> sisEmpresas;

	//bi-directional many-to-one association to SisSucursal
	@OneToMany(mappedBy="genUbicGeo")
	private List<SisSucursal> sisSucursals;

	public GenUbicGeo() {
	}

	public int getCodigoUbge() {
		return this.codigoUbge;
	}

	public void setCodigoUbge(int codigoUbge) {
		this.codigoUbge = codigoUbge;
	}

	public String getAncestroUbge() {
		return this.ancestroUbge;
	}

	public void setAncestroUbge(String ancestroUbge) {
		this.ancestroUbge = ancestroUbge;
	}

	public String getNombreUbge() {
		return this.nombreUbge;
	}

	public void setNombreUbge(String nombreUbge) {
		this.nombreUbge = nombreUbge;
	}

	public String getNumeroUbge() {
		return this.numeroUbge;
	}

	public void setNumeroUbge(String numeroUbge) {
		this.numeroUbge = numeroUbge;
	}

	public String getValor1Ubge() {
		return this.valor1Ubge;
	}

	public void setValor1Ubge(String valor1Ubge) {
		this.valor1Ubge = valor1Ubge;
	}

	public String getValor2Ubge() {
		return this.valor2Ubge;
	}

	public void setValor2Ubge(String valor2Ubge) {
		this.valor2Ubge = valor2Ubge;
	}

	public String getValor3Ubge() {
		return this.valor3Ubge;
	}

	public void setValor3Ubge(String valor3Ubge) {
		this.valor3Ubge = valor3Ubge;
	}

	public String getValor4Ubge() {
		return this.valor4Ubge;
	}

	public void setValor4Ubge(String valor4Ubge) {
		this.valor4Ubge = valor4Ubge;
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

	public GenTipoUbiGeog getGenTipoUbiGeog() {
		return this.genTipoUbiGeog;
	}

	public void setGenTipoUbiGeog(GenTipoUbiGeog genTipoUbiGeog) {
		this.genTipoUbiGeog = genTipoUbiGeog;
	}

	public GenNivelArbol getGenNivelArbol() {
		return this.genNivelArbol;
	}

	public void setGenNivelArbol(GenNivelArbol genNivelArbol) {
		this.genNivelArbol = genNivelArbol;
	}

	public List<SisEmpresa> getSisEmpresas() {
		return this.sisEmpresas;
	}

	public void setSisEmpresas(List<SisEmpresa> sisEmpresas) {
		this.sisEmpresas = sisEmpresas;
	}

	public SisEmpresa addSisEmpresa(SisEmpresa sisEmpresa) {
		getSisEmpresas().add(sisEmpresa);
		sisEmpresa.setGenUbicGeo(this);

		return sisEmpresa;
	}

	public SisEmpresa removeSisEmpresa(SisEmpresa sisEmpresa) {
		getSisEmpresas().remove(sisEmpresa);
		sisEmpresa.setGenUbicGeo(null);

		return sisEmpresa;
	}

	public List<SisSucursal> getSisSucursals() {
		return this.sisSucursals;
	}

	public void setSisSucursals(List<SisSucursal> sisSucursals) {
		this.sisSucursals = sisSucursals;
	}

	public SisSucursal addSisSucursal(SisSucursal sisSucursal) {
		getSisSucursals().add(sisSucursal);
		sisSucursal.setGenUbicGeo(this);

		return sisSucursal;
	}

	public SisSucursal removeSisSucursal(SisSucursal sisSucursal) {
		getSisSucursals().remove(sisSucursal);
		sisSucursal.setGenUbicGeo(null);

		return sisSucursal;
	}

}