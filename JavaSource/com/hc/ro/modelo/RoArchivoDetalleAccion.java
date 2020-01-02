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
@Table(name = "ro_archivo_detalle_accion")
@NamedQuery(name = "RoArchivoDetalleAccion.findAll", query = "SELECT r FROM RoArchivoDetalleAccion r")
public class RoArchivoDetalleAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_ARDE")
	private int codigoArde;

	@ManyToOne
	@JoinColumn(name = "CODIGO_DEAC")
	private RoDetalleAccion roDetalleAccion;

	@Column(name = "NOMBRE_ARDE")
	private String nombreArde;

	@Column(name = "PATH_ARDE")
	private String pathArde;

	@Column(name = "DESCRIPCION_ARDEDEAC")
	private String descripcionArde;

	public int getCodigoArde() {
		return codigoArde;
	}

	public void setCodigoArde(int codigoArde) {
		this.codigoArde = codigoArde;
	}

	public RoDetalleAccion getRoDetalleAccion() {
		return roDetalleAccion;
	}

	public void setRoDetalleAccion(RoDetalleAccion roDetalleAccion) {
		this.roDetalleAccion = roDetalleAccion;
	}

	public String getDescripcionArde() {
		return descripcionArde;
	}

	public void setDescripcionArde(String descripcionArde) {
		this.descripcionArde = descripcionArde;
	}

	public String getNombreArde() {
		return nombreArde;
	}

	public void setNombreArde(String nombreArde) {
		this.nombreArde = nombreArde;
	}

	public String getPathArde() {
		return pathArde;
	}

	public void setPathArde(String pathArde) {
		this.pathArde = pathArde;
	}

}
