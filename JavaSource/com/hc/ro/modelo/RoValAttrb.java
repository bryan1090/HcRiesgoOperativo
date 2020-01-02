package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ro_val_attrb")
@NamedQuery(name = "RoValorAttrb.findAll", query = "SELECT r FROM RoValAttrb r")
public class RoValAttrb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_val_attr")
	private int codigoValorAttr;

	@Column(name = "VARIABLE_rol_attr")
	private String variableValAttr;

	@ManyToOne
	@JoinColumn(name = "COD_attr")
	private RoAttrbAdicionale roAttrbAdicionales;

	public RoValAttrb() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoValAttrb(int codigoValorAttr, String variableValAttr) {
		super();
		this.codigoValorAttr = codigoValorAttr;
		this.variableValAttr = variableValAttr;
	}

	public int getCodigoValorAttr() {
		return codigoValorAttr;
	}

	public void setCodigoValorAttr(int codigoValorAttr) {
		this.codigoValorAttr = codigoValorAttr;
	}

	public String getVariableValAttr() {
		return variableValAttr;
	}

	public void setVariableValAttr(String variableValAttr) {
		this.variableValAttr = variableValAttr;
	}

	public RoAttrbAdicionale getRoAttrbAdicionale() {
		return roAttrbAdicionales;
	}

	public void setRoAttrbAdicionale(RoAttrbAdicionale roAttrbAdicionales) {
		this.roAttrbAdicionales = roAttrbAdicionales;
	}

	public RoAttrbAdicionale getRoAttrbAdicionales() {
		return roAttrbAdicionales;
	}

	public void setRoAttrbAdicionales(RoAttrbAdicionale roAttrbAdicionales) {
		this.roAttrbAdicionales = roAttrbAdicionales;
	}

}
