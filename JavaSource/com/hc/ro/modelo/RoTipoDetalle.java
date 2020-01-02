package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;


/**
 * The persistent class for the ro_tipo_detalle database table.
 * 
 */
@Entity
@Table(name="ro_tipo_detalle")
@NamedQuery(name="RoTipoDetalle.findAll", query="SELECT r FROM RoTipoDetalle r")
public class RoTipoDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TDRO")
	private short codigoTdro;
	
	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="NOMBRE_TDRO")
	private String nombreTdro;

	//bi-directional many-to-one association to RoDetalleNegocio
	@OneToMany(mappedBy="roTipoDetalle")
	private List<RoDetalleNegocio> roDetalleNegocios;

	//bi-directional many-to-one association to RoDetalleProceso
	@OneToMany(mappedBy="roTipoDetalle")
	private List<RoDetalleProceso> roDetalleProcesos;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public RoTipoDetalle() {
	}

	public short getCodigoTdro() {
		return this.codigoTdro;
	}

	public void setCodigoTdro(short codigoTdro) {
		this.codigoTdro = codigoTdro;
	}

	public String getNombreTdro() {
		return this.nombreTdro;
	}

	public void setNombreTdro(String nombreTdro) {
		this.nombreTdro = nombreTdro;
	}

	public List<RoDetalleNegocio> getRoDetalleNegocios() {
		return this.roDetalleNegocios;
	}

	public void setRoDetalleNegocios(List<RoDetalleNegocio> roDetalleNegocios) {
		this.roDetalleNegocios = roDetalleNegocios;
	}

	public RoDetalleNegocio addRoDetalleNegocio(RoDetalleNegocio roDetalleNegocio) {
		getRoDetalleNegocios().add(roDetalleNegocio);
		roDetalleNegocio.setRoTipoDetalle(this);

		return roDetalleNegocio;
	}

	public RoDetalleNegocio removeRoDetalleNegocio(RoDetalleNegocio roDetalleNegocio) {
		getRoDetalleNegocios().remove(roDetalleNegocio);
		roDetalleNegocio.setRoTipoDetalle(null);

		return roDetalleNegocio;
	}

	public List<RoDetalleProceso> getRoDetalleProcesos() {
		return this.roDetalleProcesos;
	}

	public void setRoDetalleProcesos(List<RoDetalleProceso> roDetalleProcesos) {
		this.roDetalleProcesos = roDetalleProcesos;
	}

	public RoDetalleProceso addRoDetalleProceso(RoDetalleProceso roDetalleProceso) {
		getRoDetalleProcesos().add(roDetalleProceso);
		roDetalleProceso.setRoTipoDetalle(this);

		return roDetalleProceso;
	}

	public RoDetalleProceso removeRoDetalleProceso(RoDetalleProceso roDetalleProceso) {
		getRoDetalleProcesos().remove(roDetalleProceso);
		roDetalleProceso.setRoTipoDetalle(null);

		return roDetalleProceso;
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

}