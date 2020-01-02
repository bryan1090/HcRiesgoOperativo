package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;


/**
 * The persistent class for the ro_cat_objetivos database table.
 * 
 */
@Entity
@Table(name="ro_cat_objetivos")
@NamedQuery(name="RoCatObjetivo.findAll", query="SELECT r FROM RoCatObjetivo r")
public class RoCatObjetivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_COBJ")
	private int codigoCobj;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="NOMBRE_COBJ")
	private String nombreCobj;

	@Column(name="OBSERVACION_COBJ")
	private String observacionCobj;

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

	//bi-directional many-to-one association to RoEventoObjetivo
	@OneToMany(mappedBy="roCatObjetivo")
	private List<RoEventoObjetivo> roEventoObjetivos;

	public RoCatObjetivo() {
	}

	

	public String getNombreCobj() {
		return this.nombreCobj;
	}

	public void setNombreCobj(String nombreCobj) {
		this.nombreCobj = nombreCobj;
	}

	public String getObservacionCobj() {
		return this.observacionCobj;
	}

	public void setObservacionCobj(String observacionCobj) {
		this.observacionCobj = observacionCobj;
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

	public List<RoEventoObjetivo> getRoEventoObjetivos() {
		return this.roEventoObjetivos;
	}

	public void setRoEventoObjetivos(List<RoEventoObjetivo> roEventoObjetivos) {
		this.roEventoObjetivos = roEventoObjetivos;
	}

	public RoEventoObjetivo addRoEventoObjetivo(RoEventoObjetivo roEventoObjetivo) {
		getRoEventoObjetivos().add(roEventoObjetivo);
		roEventoObjetivo.setRoCatObjetivo(this);

		return roEventoObjetivo;
	}

	public RoEventoObjetivo removeRoEventoObjetivo(RoEventoObjetivo roEventoObjetivo) {
		getRoEventoObjetivos().remove(roEventoObjetivo);
		roEventoObjetivo.setRoCatObjetivo(null);

		return roEventoObjetivo;
	}



	public int getCodigoCobj() {
		return codigoCobj;
	}



	public void setCodigoCobj(int codigoCobj) {
		this.codigoCobj = codigoCobj;
	}

}