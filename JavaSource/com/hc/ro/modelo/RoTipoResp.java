package com.hc.ro.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


/**
 * The persistent class for the ro_tipo_resp database table.
 * 
 */
@Entity
@Table(name="ro_tipo_resp")
@NamedQuery(name="RoTipoResp.findAll", query="SELECT r FROM RoTipoResp r")
public class RoTipoResp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TRES")
	private int codigoTres;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="NOMBRE_TRES")
	private String nombreTres;

	//bi-directional many-to-one association to SisDetalleMenu
	@ManyToOne
	@JoinColumn(name="CODIGO_DEME_TRES")
	private SisDetalleMenu sisDetalleMenu;
	
	@OneToMany(mappedBy = "roTipoResp")
	private List<RoResponsable> roResponsables;

	public RoTipoResp() {
	}

	
	
	public int getCodigoTres() {
		return this.codigoTres;
	}

	public void setCodigoTres(int codigoTres) {
		this.codigoTres = codigoTres;
	}

	public String getNombreTres() {
		return this.nombreTres;
	}

	public void setNombreTres(String nombreTres) {
		this.nombreTres = nombreTres;
	}

	public SisDetalleMenu getSisDetalleMenu() {
		return this.sisDetalleMenu;
	}

	public void setSisDetalleMenu(SisDetalleMenu sisDetalleMenu) {
		this.sisDetalleMenu = sisDetalleMenu;
	}

}