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
@Table(name = "ro_archivo_accion")
@NamedQuery(name = "RoArchivoAccion.findAll", query = "SELECT r FROM RoArchivoAccion r")
public class RoArchivoAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_ARAC")
	private int codigoArac;

	@ManyToOne
	@JoinColumn(name = "CODIGO_ACCI")
	private RoAccion roAccion;

	@Column(name = "NOMBRE_ARAC")
	private String nombreArac;

	@Column(name = "PATH_ARAC")
	private String pathArac;

	@Column(name = "DESCRIPCION_ARAC")
	private String descripcionArac;

	public int getCodigoArac() {
		return codigoArac;
	}

	public void setCodigoArac(int codigoArac) {
		this.codigoArac = codigoArac;
	}

	public RoAccion getRoAccion() {
		return roAccion;
	}

	public void setRoAccion(RoAccion roAccion) {
		this.roAccion = roAccion;
	}

	

	public String getDescripcionArac() {
		return descripcionArac;
	}

	public void setDescripcionArac(String descripcionArac) {
		this.descripcionArac = descripcionArac;
	}

	public String getNombreArac() {
		return nombreArac;
	}

	public void setNombreArac(String nombreArac) {
		this.nombreArac = nombreArac;
	}

	public String getPathArac() {
		return pathArac;
	}

	public void setPathArac(String pathArac) {
		this.pathArac = pathArac;
	}

}
