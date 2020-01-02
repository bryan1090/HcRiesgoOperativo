package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RO_CONSTANTE_CONTROL database table.
 * 
 */
@Entity
@Table(name="RO_CONSTANTE_CONTROL")
@NamedQuery(name="RoConstanteControl.findAll", query="SELECT r FROM RoConstanteControl r")
public class RoConstanteControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_CTE")
	private long codigoCte;

	@Column(name="NOMBRE_CTE")
	private String nombreCte;

	@Column(name="VALOR_CTE")
	private BigDecimal valorCte;

	public RoConstanteControl() {
	}

	public long getCodigoCte() {
		return this.codigoCte;
	}

	public void setCodigoCte(long codigoCte) {
		this.codigoCte = codigoCte;
	}

	public String getNombreCte() {
		return this.nombreCte;
	}

	public void setNombreCte(String nombreCte) {
		this.nombreCte = nombreCte;
	}

	public BigDecimal getValorCte() {
		return this.valorCte;
	}

	public void setValorCte(BigDecimal valorCte) {
		this.valorCte = valorCte;
	}

}