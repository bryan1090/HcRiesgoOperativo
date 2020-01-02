package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ro_evento_costo database table.
 * 
 */
@Entity
@Table(name="ro_evento_costo")
@NamedQuery(name="RoEventoCosto.findAll", query="SELECT r FROM RoEventoCosto r")
public class RoEventoCosto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_EVCO")
	private int codigoEvco;

	@Column(name="CANTIDAD_EVCO")
	private int cantidadEvco;

	@Column(name="TOTAL_EVCO")
	private float totalEvco;

	@Column(name="VALOR_EVCO")
	private float valorEvco;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoDetalleEvento
	@ManyToOne
	@JoinColumn(name="CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;

	//bi-directional many-to-one association to RoTipoCosto
	@ManyToOne
	@JoinColumn(name="CODIGO_TICO")
	private RoTipoCosto roTipoCosto;

	public RoEventoCosto() {
	}


	

	public float getTotalEvco() {
		return this.totalEvco;
	}

	public void setTotalEvco(float totalEvco) {
		this.totalEvco = totalEvco;
	}

	public float getValorEvco() {
		return this.valorEvco;
	}

	public void setValorEvco(float valorEvco) {
		this.valorEvco = valorEvco;
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

	public RoDetalleEvento getRoDetalleEvento() {
		return this.roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

	public RoTipoCosto getRoTipoCosto() {
		return this.roTipoCosto;
	}

	public void setRoTipoCosto(RoTipoCosto roTipoCosto) {
		this.roTipoCosto = roTipoCosto;
	}

	public int getCantidadEvco() {
		return cantidadEvco;
	}

	public void setCantidadEvco(int cantidadEvco) {
		this.cantidadEvco = cantidadEvco;
	}




	public int getCodigoEvco() {
		return codigoEvco;
	}




	public void setCodigoEvco(int codigoEvco) {
		this.codigoEvco = codigoEvco;
	}

}