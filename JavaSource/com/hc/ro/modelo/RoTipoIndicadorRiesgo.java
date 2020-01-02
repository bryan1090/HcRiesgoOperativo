package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RO_INDICADOR_RIESGO database table.
 * 
 */
@Entity
@Table(name="RO_TIPO_INDICADOR_RIESGO")
@NamedQuery(name="RoTipoIndicadorRiesgo.findAll", query="SELECT r FROM RoTipoIndicadorRiesgo r")
public class RoTipoIndicadorRiesgo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_TINRI")
	private int codigoTinri;

	@Column(name="CODIGO_DEVE")
	private BigDecimal codigoDeve;

	@Column(name="CODIGO_ESTA")
	private BigDecimal codigoEsta;

	@Column(name="LIMITE")
	private BigDecimal limite;

	@Column(name="ALERTA")
	private BigDecimal alerta;

//	@ManyToOne
//	@JoinColumn(name="CODIGO_RESP")
//	private RoResponsable roResponsable;
	
	@ManyToOne
	@JoinColumn(name="CODIGO_PROS")
	private RoProceso roProceso;
	
	@Column(name="NOMBRE_TINRI")
	private String nombreTinri;
	
	@Column(name="UNIDAD")
	private String unidad;
	
	@Column(name="OBSERVACION_INRI")
	private String observacionInri;
	
	@Column(name="TIPO_VALOR")
	private String tipoValor;
	
	public RoTipoIndicadorRiesgo() {
	}

	public int getcodigoTinri() {
		return this.codigoTinri;
	}

	public void setcodigoTinri(int codigoTinri) {
		this.codigoTinri = codigoTinri;
	}

	public BigDecimal getCodigoDeve() {
		return this.codigoDeve;
	}

	public void setCodigoDeve(BigDecimal codigoDeve) {
		this.codigoDeve = codigoDeve;
	}

	public BigDecimal getCodigoEsta() {
		return this.codigoEsta;
	}

	public void setCodigoEsta(BigDecimal codigoEsta) {
		this.codigoEsta = codigoEsta;
	}

	public BigDecimal getLimite() {
		return this.limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	

//	public RoResponsable getRoResponsable() {
//		return roResponsable;
//	}
//
//	public void setRoResponsable(RoResponsable roResponsable) {
//		this.roResponsable = roResponsable;
//	}

	public String getnombreTinri() {
		return nombreTinri;
	}

	public void setnombreTinri(String nombreTinri) {
		this.nombreTinri = nombreTinri;
	}


	public RoProceso getRoProceso() {
		return roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getObservacionInri() {
		return observacionInri;
	}

	public void setObservacionInri(String observacionInri) {
		this.observacionInri = observacionInri;
	}

	public BigDecimal getAlerta() {
		return alerta;
	}

	public void setAlerta(BigDecimal alerta) {
		this.alerta = alerta;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	


}