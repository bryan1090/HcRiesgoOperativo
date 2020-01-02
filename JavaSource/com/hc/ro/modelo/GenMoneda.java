package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the gen_moneda database table.
 * 
 */
@Entity
@Table(name="gen_moneda")
@NamedQuery(name="GenMoneda.findAll", query="SELECT g FROM GenMoneda g")
public class GenMoneda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_GMON")
	private int codigoGmon;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_GMON")
	private Date fechaGmon;

	@Column(name="NOMBRE_GMON")
	private String nombreGmon;

	@Column(name="VALOR1_GMON")
	private BigDecimal valor1Gmon;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy="genMoneda")
	private List<RoDetalleEvento> roDetalleEventos;

	public GenMoneda() {
	}

	public int getCodigoGmon() {
		return this.codigoGmon;
	}

	public void setCodigoGmon(int codigoGmon) {
		this.codigoGmon = codigoGmon;
	}

	public Date getFechaGmon() {
		return this.fechaGmon;
	}

	public void setFechaGmon(Date fechaGmon) {
		this.fechaGmon = fechaGmon;
	}

	public String getNombreGmon() {
		return this.nombreGmon;
	}

	public void setNombreGmon(String nombreGmon) {
		this.nombreGmon = nombreGmon;
	}

	public BigDecimal getValor1Gmon() {
		return this.valor1Gmon;
	}

	public void setValor1Gmon(BigDecimal valor1Gmon) {
		this.valor1Gmon = valor1Gmon;
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

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return this.roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public RoDetalleEvento addRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().add(roDetalleEvento);
		roDetalleEvento.setGenMoneda(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setGenMoneda(null);

		return roDetalleEvento;
	}

}