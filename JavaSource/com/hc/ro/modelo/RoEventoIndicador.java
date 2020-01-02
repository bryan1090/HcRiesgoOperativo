package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RO_EVENTO_INDICADOR database table.
 * 
 */
@Entity
@Table(name="RO_EVENTO_INDICADOR")
@NamedQuery(name="RoEventoIndicador.findAll", query="SELECT r FROM RoEventoIndicador r")
public class RoEventoIndicador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CODIGO_EVIN")
	private int codigoEvin;

	@ManyToOne
	@JoinColumn(name="CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa SisEmpresa;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	@ManyToOne
	@JoinColumn(name="CODIGO_TINRI")
	private RoTipoIndicadorRiesgo roTipoIndicadorRiesgo;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_EVIN")
	private Date fechaEvin;

	@Column(name="VALOR_EVIN")
	private double valorEvin;

	public RoEventoIndicador() {
	}

	public int getCodigoEvin() {
		return codigoEvin;
	}

	public void setCodigoEvin(int codigoEvin) {
		this.codigoEvin = codigoEvin;
	}

	


	public Date getFechaEvin() {
		return fechaEvin;
	}

	public void setFechaEvin(Date fechaEvin) {
		this.fechaEvin = fechaEvin;
	}

	public double getValorEvin() {
		return valorEvin;
	}

	public void setValorEvin(double valorEvin) {
		this.valorEvin = valorEvin;
	}

	public RoTipoIndicadorRiesgo getRoTipoIndicadorRiesgo() {
		return roTipoIndicadorRiesgo;
	}

	public void setRoTipoIndicadorRiesgo(RoTipoIndicadorRiesgo roTipoIndicadorRiesgo) {
		this.roTipoIndicadorRiesgo = roTipoIndicadorRiesgo;
	}

	public RoDetalleEvento getRoDetalleEvento() {
		return roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

	public SisEmpresa getSisEmpresa() {
		return SisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		SisEmpresa = sisEmpresa;
	}

	public SisSucursal getSisSucursal() {
		return sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	

}