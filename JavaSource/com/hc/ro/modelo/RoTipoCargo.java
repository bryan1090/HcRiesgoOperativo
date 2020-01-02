package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the RO_TIPO_CARGO database table.
 * 
 */
@Entity
@Table(name="RO_TIPO_CARGO")
@NamedQuery(name="RoTipoCargo.findAll", query="SELECT r FROM RoTipoCargo r")
public class RoTipoCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_CARGO")
	private int codigoCargo;

	@Column(name="NOMBRE_CARGO")
	private String nombreCargo;

	//bi-directional many-to-one association to SisUsuario
	@OneToMany(mappedBy="roTipoCargo")
	private List<SisUsuario> sisUsuarios;

	public RoTipoCargo() {
	}

	public int getCodigoCargo() {
		return this.codigoCargo;
	}

	public void setCodigoCargo(int codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	public String getNombreCargo() {
		return this.nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public List<SisUsuario> getSisUsuarios() {
		return this.sisUsuarios;
	}

	public void setSisUsuarios(List<SisUsuario> sisUsuarios) {
		this.sisUsuarios = sisUsuarios;
	}

	public SisUsuario addSisUsuario(SisUsuario sisUsuario) {
		getSisUsuarios().add(sisUsuario);
		sisUsuario.setRoTipoCargo(this);

		return sisUsuario;
	}

	public SisUsuario removeSisUsuario(SisUsuario sisUsuario) {
		getSisUsuarios().remove(sisUsuario);
		sisUsuario.setRoTipoCargo(null);

		return sisUsuario;
	}

}