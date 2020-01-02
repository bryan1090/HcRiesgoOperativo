package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;


/**
 * The persistent class for the ro_tipo_afecta database table.
 * 
 */
@Entity
@Table(name="ro_tipo_afecta")
@NamedQuery(name="RoTipoAfecta.findAll", query="SELECT r FROM RoTipoAfecta r")
public class RoTipoAfecta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TAFC")
	private int codigoTafc;
	
	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="NOMBRE_TAFC")
	private String nombreTafc;

	//bi-directional many-to-one association to RoEventoAfecta
	@OneToMany(mappedBy="roTipoAfecta")
	private List<RoEventoAfecta> roEventoAfectas;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name="CODIGO_ESTA")
	private GenEstado genEstado;

	public RoTipoAfecta() {
	}

	public int getCodigoTafc() {
		return this.codigoTafc;
	}

	public void setCodigoTafc(int codigoTafc) {
		this.codigoTafc = codigoTafc;
	}

	public String getNombreTafc() {
		return this.nombreTafc;
	}

	public void setNombreTafc(String nombreTafc) {
		this.nombreTafc = nombreTafc;
	}

	public List<RoEventoAfecta> getRoEventoAfectas() {
		return this.roEventoAfectas;
	}

	public void setRoEventoAfectas(List<RoEventoAfecta> roEventoAfectas) {
		this.roEventoAfectas = roEventoAfectas;
	}

	public RoEventoAfecta addRoEventoAfecta(RoEventoAfecta roEventoAfecta) {
		getRoEventoAfectas().add(roEventoAfecta);
		roEventoAfecta.setRoTipoAfecta(this);

		return roEventoAfecta;
	}

	public RoEventoAfecta removeRoEventoAfecta(RoEventoAfecta roEventoAfecta) {
		getRoEventoAfectas().remove(roEventoAfecta);
		roEventoAfecta.setRoTipoAfecta(null);

		return roEventoAfecta;
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

	public GenEstado getGenEstado() {
		return this.genEstado;
	}

	public void setGenEstado(GenEstado genEstado) {
		this.genEstado = genEstado;
	}

}