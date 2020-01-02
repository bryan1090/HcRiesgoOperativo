package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gen_tipo_ubi_geog database table.
 * 
 */
@Entity
@Table(name="gen_tipo_ubi_geog")
@NamedQuery(name="GenTipoUbiGeog.findAll", query="SELECT g FROM GenTipoUbiGeog g")
public class GenTipoUbiGeog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TIUG")
	private int codigoTiug;

	@Column(name="NOMBRE_TIUG")
	private String nombreTiug;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to GenUbicGeo
	@OneToMany(mappedBy="genTipoUbiGeog")
	private List<GenUbicGeo> genUbicGeos;

	public GenTipoUbiGeog() {
	}

	public int getCodigoTiug() {
		return this.codigoTiug;
	}

	public void setCodigoTiug(int codigoTiug) {
		this.codigoTiug = codigoTiug;
	}

	public String getNombreTiug() {
		return this.nombreTiug;
	}

	public void setNombreTiug(String nombreTiug) {
		this.nombreTiug = nombreTiug;
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

	public List<GenUbicGeo> getGenUbicGeos() {
		return this.genUbicGeos;
	}

	public void setGenUbicGeos(List<GenUbicGeo> genUbicGeos) {
		this.genUbicGeos = genUbicGeos;
	}

	public GenUbicGeo addGenUbicGeo(GenUbicGeo genUbicGeo) {
		getGenUbicGeos().add(genUbicGeo);
		genUbicGeo.setGenTipoUbiGeog(this);

		return genUbicGeo;
	}

	public GenUbicGeo removeGenUbicGeo(GenUbicGeo genUbicGeo) {
		getGenUbicGeos().remove(genUbicGeo);
		genUbicGeo.setGenTipoUbiGeog(null);

		return genUbicGeo;
	}

}