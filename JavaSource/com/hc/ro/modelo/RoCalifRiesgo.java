package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;


/**
 * The persistent class for the ro_calif_riesgo database table.
 * 
 */
@Entity
@Table(name="ro_calif_riesgo")
@NamedQuery(name="RoCalifRiesgo.findAll", query="SELECT r FROM RoCalifRiesgo r")
public class RoCalifRiesgo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_CLRS")
	private int codigoClrs;

	@Column(name="COLOR_CLRS")
	private String colorClrs;
	
	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="NOMBRE_CLRS")
	private String nombreClrs;

	@Column(name="OBSERVACION_CLRS")
	private String observacionClrs;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoNivelRiesgo
	@OneToMany(mappedBy="roCalifRiesgo")
	private List<RoNivelRiesgo> roNivelRiesgos;

	@Transient
	private int total;

	
	public RoCalifRiesgo() {
	}
	
	

	public RoCalifRiesgo(String colorClrs, String nombreClrs) {
		super();
		this.colorClrs = colorClrs;
		this.nombreClrs = nombreClrs;
		
	}



	public int getCodigoClrs() {
		return this.codigoClrs;
	}

	public void setCodigoClrs(int codigoClrs) {
		this.codigoClrs = codigoClrs;
	}

	

	public String getNombreClrs() {
		return this.nombreClrs;
	}

	public void setNombreClrs(String nombreClrs) {
		this.nombreClrs = nombreClrs;
	}

	public String getObservacionClrs() {
		return this.observacionClrs;
	}

	public void setObservacionClrs(String observacionClrs) {
		this.observacionClrs = observacionClrs;
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

	public List<RoNivelRiesgo> getRoNivelRiesgos() {
		return this.roNivelRiesgos;
	}

	public void setRoNivelRiesgos(List<RoNivelRiesgo> roNivelRiesgos) {
		this.roNivelRiesgos = roNivelRiesgos;
	}

	public RoNivelRiesgo addRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().add(roNivelRiesgo);
		roNivelRiesgo.setRoCalifRiesgo(this);

		return roNivelRiesgo;
	}

	public RoNivelRiesgo removeRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().remove(roNivelRiesgo);
		roNivelRiesgo.setRoCalifRiesgo(null);

		return roNivelRiesgo;
	}

	public String getColorClrs() {
		return colorClrs;
	}

	public void setColorClrs(String colorClrs) {
		this.colorClrs = colorClrs;
	}



	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}




}