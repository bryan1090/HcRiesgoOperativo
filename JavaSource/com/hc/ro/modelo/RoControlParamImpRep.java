package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RO_CONTROL_PARAM_IMP_REP database table.
 * 
 */
@Entity
@Table(name="RO_CONTROL_PARAM_IMP_REP")
@NamedQuery(name="RoControlParamImpRep.findAll", query="SELECT r FROM RoControlParamImpRep r")
public class RoControlParamImpRep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_REP")
	private long codigoRep;

	@Column(name="NOMBRE_REP")
	private String nombreRep;

	@Column(name="VALOR_REP")
	private BigDecimal valorRep;

	public RoControlParamImpRep() {
	}

	public long getCodigoRep() {
		return this.codigoRep;
	}

	public void setCodigoRep(long codigoRep) {
		this.codigoRep = codigoRep;
	}

	public String getNombreRep() {
		return this.nombreRep;
	}

	public void setNombreRep(String nombreRep) {
		this.nombreRep = nombreRep;
	}

	public BigDecimal getValorRep() {
		return this.valorRep;
	}

	public void setValorRep(BigDecimal valorRep) {
		this.valorRep = valorRep;
	}

}