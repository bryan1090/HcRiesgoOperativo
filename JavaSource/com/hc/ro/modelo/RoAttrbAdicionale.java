package com.hc.ro.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * The persistent class for the ro_attrb_adicionales database table.
 * 
 */
@Entity
@Table(name = "ro_attrb_adicionales")
@NamedQuery(name = "RoAttrbAdicionale.findAll", query = "SELECT r FROM RoAttrbAdicionale r")
public class RoAttrbAdicionale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COD_attr")
	private int codigoAttr;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_attr")
	private String nombreAttr;

	@Column(name = "DESCRIPCION_attr")
	private String descripcionAttr;

	@Column(name = "CLASE_attr")
	private String claseAttr;

	@OneToMany(mappedBy = "roAttrbAdicionales")
	private List<RoValAttrb> roValAttrbs;

	public RoAttrbAdicionale(int codigoAttr, String nombreAttr) {
		super();
		this.codigoAttr = codigoAttr;
		this.nombreAttr = nombreAttr;
	}

	public RoAttrbAdicionale() {
		// TODO Auto-generated constructor stub
	}

	public Integer getCodigoAttr() {
		return codigoAttr;
	}

	public void setCodigoAttr(Integer codigoAttr) {
		this.codigoAttr = codigoAttr;
	}

	public String getNombreAttr() {
		return nombreAttr;
	}

	public void setNombreAttr(String nombreAttr) {
		this.nombreAttr = nombreAttr;
	}

	public String getDescripcionAttr() {
		return descripcionAttr;
	}

	public void setDescripcionAttr(String descripcionAttr) {
		this.descripcionAttr = descripcionAttr;
	}

	public String getClaseAttr() {
		return claseAttr;
	}

	public void setClaseAttr(String claseAttr) {
		this.claseAttr = claseAttr;
	}

}