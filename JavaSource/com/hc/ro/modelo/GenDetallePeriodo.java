package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the gen_detalle_periodo database table.
 * 
 */
@Entity
@Table(name="gen_detalle_periodo")
@NamedQuery(name="GenDetallePeriodo.findAll", query="SELECT g FROM GenDetallePeriodo g")
public class GenDetallePeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_DPER")
	private int codigoDper;

	@Column(name="DIA_FIN_DPER")
	private BigDecimal diaFinDper;

	@Column(name="DIA_INICIO_DPER")
	private BigDecimal diaInicioDper;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_FIN_DPER")
	private Date fechaFinDper;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INICIO_DPER")
	private Date fechaInicioDper;

	@Column(name="NOMBRE_DPER")
	private String nombreDper;

	//bi-directional many-to-one association to GenPeriodo
	@ManyToOne
	@JoinColumn(name="CODIGO_PERI")
	private GenPeriodo genPeriodo;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public GenDetallePeriodo() {
	}

	public int getCodigoDper() {
		return this.codigoDper;
	}

	public void setCodigoDper(int codigoDper) {
		this.codigoDper = codigoDper;
	}

	public BigDecimal getDiaFinDper() {
		return this.diaFinDper;
	}

	public void setDiaFinDper(BigDecimal diaFinDper) {
		this.diaFinDper = diaFinDper;
	}

	public BigDecimal getDiaInicioDper() {
		return this.diaInicioDper;
	}

	public void setDiaInicioDper(BigDecimal diaInicioDper) {
		this.diaInicioDper = diaInicioDper;
	}

	public Date getFechaFinDper() {
		return this.fechaFinDper;
	}

	public void setFechaFinDper(Date fechaFinDper) {
		this.fechaFinDper = fechaFinDper;
	}

	public Date getFechaInicioDper() {
		return this.fechaInicioDper;
	}

	public void setFechaInicioDper(Date fechaInicioDper) {
		this.fechaInicioDper = fechaInicioDper;
	}

	public String getNombreDper() {
		return this.nombreDper;
	}

	public void setNombreDper(String nombreDper) {
		this.nombreDper = nombreDper;
	}

	public GenPeriodo getGenPeriodo() {
		return this.genPeriodo;
	}

	public void setGenPeriodo(GenPeriodo genPeriodo) {
		this.genPeriodo = genPeriodo;
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