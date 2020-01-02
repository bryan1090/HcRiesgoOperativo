package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sis_modulo database table.
 * 
 */
@Entity
@Table(name="sis_modulo")
@NamedQuery(name="SisModulo.findAll", query="SELECT s FROM SisModulo s")
public class SisModulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_MODU")
	private int codigoModu;

	@Column(name="NOMBRE_MODU")
	private String nombreModu;

	//bi-directional many-to-one association to SisAlertaUsuario
	@OneToMany(mappedBy="sisModulo")
	private List<SisAlertaUsuario> sisAlertaUsuarios;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisObjeto
	@OneToMany(mappedBy="sisModulo")
	private List<SisObjeto> sisObjetos;

	//bi-directional many-to-one association to SisParametro
	@OneToMany(mappedBy="sisModulo")
	private List<SisParametro> sisParametros;

	public SisModulo() {
	}

	public int getCodigoModu() {
		return this.codigoModu;
	}

	public void setCodigoModu(int codigoModu) {
		this.codigoModu = codigoModu;
	}

	public String getNombreModu() {
		return this.nombreModu;
	}

	public void setNombreModu(String nombreModu) {
		this.nombreModu = nombreModu;
	}

	public List<SisAlertaUsuario> getSisAlertaUsuarios() {
		return this.sisAlertaUsuarios;
	}

	public void setSisAlertaUsuarios(List<SisAlertaUsuario> sisAlertaUsuarios) {
		this.sisAlertaUsuarios = sisAlertaUsuarios;
	}

	public SisAlertaUsuario addSisAlertaUsuario(SisAlertaUsuario sisAlertaUsuario) {
		getSisAlertaUsuarios().add(sisAlertaUsuario);
		sisAlertaUsuario.setSisModulo(this);

		return sisAlertaUsuario;
	}

	public SisAlertaUsuario removeSisAlertaUsuario(SisAlertaUsuario sisAlertaUsuario) {
		getSisAlertaUsuarios().remove(sisAlertaUsuario);
		sisAlertaUsuario.setSisModulo(null);

		return sisAlertaUsuario;
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
		sisObjeto.setSisModulo(this);

		return sisObjeto;
	}

	public SisObjeto removeSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().remove(sisObjeto);
		sisObjeto.setSisModulo(null);

		return sisObjeto;
	}

	public List<SisParametro> getSisParametros() {
		return this.sisParametros;
	}

	public void setSisParametros(List<SisParametro> sisParametros) {
		this.sisParametros = sisParametros;
	}

	public SisParametro addSisParametro(SisParametro sisParametro) {
		getSisParametros().add(sisParametro);
		sisParametro.setSisModulo(this);

		return sisParametro;
	}

	public SisParametro removeSisParametro(SisParametro sisParametro) {
		getSisParametros().remove(sisParametro);
		sisParametro.setSisModulo(null);

		return sisParametro;
	}

}