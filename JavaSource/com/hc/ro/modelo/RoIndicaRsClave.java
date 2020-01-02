package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ro_indica_rs_clave database table.
 * 
 */
@Entity
@Table(name="ro_indica_rs_clave")
@NamedQuery(name="RoIndicaRsClave.findAll", query="SELECT r FROM RoIndicaRsClave r")
public class RoIndicaRsClave implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_IRCL")
	private int codigoIrcl;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_FIN_IRCL")
	private Date fechaFinIrcl;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INICIO_IRCL")
	private Date fechaInicioIrcl;

	private BigDecimal limite;

	@Column(name="NOMBRE_IRCL")
	private String nombreIrcl;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name="CODIGO_ESTA")
	private GenEstado genEstado;

	public RoIndicaRsClave() {
	}

	public int getCodigoIrcl() {
		return this.codigoIrcl;
	}

	public void setCodigoIrcl(int codigoIrcl) {
		this.codigoIrcl = codigoIrcl;
	}

	public Date getFechaFinIrcl() {
		return this.fechaFinIrcl;
	}

	public void setFechaFinIrcl(Date fechaFinIrcl) {
		this.fechaFinIrcl = fechaFinIrcl;
	}

	public Date getFechaInicioIrcl() {
		return this.fechaInicioIrcl;
	}

	public void setFechaInicioIrcl(Date fechaInicioIrcl) {
		this.fechaInicioIrcl = fechaInicioIrcl;
	}

	public BigDecimal getLimite() {
		return this.limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public String getNombreIrcl() {
		return this.nombreIrcl;
	}

	public void setNombreIrcl(String nombreIrcl) {
		this.nombreIrcl = nombreIrcl;
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

	public GenEstado getGenEstado() {
		return this.genEstado;
	}

	public void setGenEstado(GenEstado genEstado) {
		this.genEstado = genEstado;
	}

}