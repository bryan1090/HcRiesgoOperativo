package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ro_cumplimiento")
@NamedQuery(name = "RoCumplimiento.findAll", query = "SELECT r FROM RoCumplimiento r")
public class RoCumplimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_CUMP")
	private int codigoCump;

	@Column(name = "NOMBRE_CUMP")
	private String nombreCump;

	@Column(name = "DESCRIPCION_CUMP")
	private String descripcionCump;

	public int getCodigoCump() {
		return codigoCump;
	}

	public void setCodigoCump(int codigoCump) {
		this.codigoCump = codigoCump;
	}

	public String getDescripcionCump() {
		return descripcionCump;
	}

	public void setDescripcionCump(String descripcionCump) {
		this.descripcionCump = descripcionCump;
	}

	public String getNombreCump() {
		return nombreCump;
	}

	public void setNombreCump(String nombreCump) {
		this.nombreCump = nombreCump;
	}

}
