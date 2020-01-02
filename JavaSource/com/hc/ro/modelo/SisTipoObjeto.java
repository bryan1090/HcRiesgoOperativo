package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_tipo_objeto database table.
 * 
 */
@Entity
@Table(name="sis_tipo_objeto")
@NamedQuery(name="SisTipoObjeto.findAll", query="SELECT s FROM SisTipoObjeto s")
public class SisTipoObjeto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TIOB")
	private int codigoTiob;

	@Column(name="NOMBRE_TIOB")
	private String nombreTiob;

	//bi-directional many-to-one association to SisObjeto
	@OneToMany(mappedBy="sisTipoObjeto")
	private List<SisObjeto> sisObjetos;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	public SisTipoObjeto() {
	}

	public int getCodigoTiob() {
		return this.codigoTiob;
	}

	public void setCodigoTiob(int codigoTiob) {
		this.codigoTiob = codigoTiob;
	}

	public String getNombreTiob() {
		return this.nombreTiob;
	}

	public void setNombreTiob(String nombreTiob) {
		this.nombreTiob = nombreTiob;
	}

	public List<SisObjeto> getSisObjetos() {
		return this.sisObjetos;
	}

	public void setSisObjetos(List<SisObjeto> sisObjetos) {
		this.sisObjetos = sisObjetos;
	}

	public SisObjeto addSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().add(sisObjeto);
		sisObjeto.setSisTipoObjeto(this);

		return sisObjeto;
	}

	public SisObjeto removeSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().remove(sisObjeto);
		sisObjeto.setSisTipoObjeto(null);

		return sisObjeto;
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

}