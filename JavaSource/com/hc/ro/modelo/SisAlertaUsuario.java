package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the sis_alerta_usuario database table.
 * 
 */
@Entity
@Table(name="sis_alerta_usuario")
@NamedQuery(name="SisAlertaUsuario.findAll", query="SELECT s FROM SisAlertaUsuario s")
public class SisAlertaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_ALER")
	private int codigoAler;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ALER")
	private Date fechaAler;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_DESP_ALER")
	private Date fechaDespAler;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_REV_ALER")
	private Date fechaRevAler;

	@Column(name="HORA_DESP_ALER")
	private String horaDespAler;

	@Column(name="HORA_REV_ALER")
	private String horaRevAler;

	@Column(name="MENSAJE_ALER")
	private String mensajeAler;

	@Column(name="REVISADO_ALER")
	private BigDecimal revisadoAler;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisUsuario
	@ManyToOne
	@JoinColumn(name="SIS_CODIGO_USUA")
	private SisUsuario sisUsuario1;

	//bi-directional many-to-one association to SisModulo
	@ManyToOne
	@JoinColumn(name="CODIGO_MODU")
	private SisModulo sisModulo;

	//bi-directional many-to-one association to SisUsuario
	@ManyToOne
	@JoinColumn(name="CODIGO_USUA")
	private SisUsuario sisUsuario2;

	public SisAlertaUsuario() {
	}

	public int getCodigoAler() {
		return this.codigoAler;
	}

	public void setCodigoAler(int codigoAler) {
		this.codigoAler = codigoAler;
	}

	public Date getFechaAler() {
		return this.fechaAler;
	}

	public void setFechaAler(Date fechaAler) {
		this.fechaAler = fechaAler;
	}

	public Date getFechaDespAler() {
		return this.fechaDespAler;
	}

	public void setFechaDespAler(Date fechaDespAler) {
		this.fechaDespAler = fechaDespAler;
	}

	public Date getFechaRevAler() {
		return this.fechaRevAler;
	}

	public void setFechaRevAler(Date fechaRevAler) {
		this.fechaRevAler = fechaRevAler;
	}

	public String getHoraDespAler() {
		return this.horaDespAler;
	}

	public void setHoraDespAler(String horaDespAler) {
		this.horaDespAler = horaDespAler;
	}

	public String getHoraRevAler() {
		return this.horaRevAler;
	}

	public void setHoraRevAler(String horaRevAler) {
		this.horaRevAler = horaRevAler;
	}

	public String getMensajeAler() {
		return this.mensajeAler;
	}

	public void setMensajeAler(String mensajeAler) {
		this.mensajeAler = mensajeAler;
	}

	public BigDecimal getRevisadoAler() {
		return this.revisadoAler;
	}

	public void setRevisadoAler(BigDecimal revisadoAler) {
		this.revisadoAler = revisadoAler;
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

	public SisUsuario getSisUsuario1() {
		return this.sisUsuario1;
	}

	public void setSisUsuario1(SisUsuario sisUsuario1) {
		this.sisUsuario1 = sisUsuario1;
	}

	public SisModulo getSisModulo() {
		return this.sisModulo;
	}

	public void setSisModulo(SisModulo sisModulo) {
		this.sisModulo = sisModulo;
	}

	public SisUsuario getSisUsuario2() {
		return this.sisUsuario2;
	}

	public void setSisUsuario2(SisUsuario sisUsuario2) {
		this.sisUsuario2 = sisUsuario2;
	}

}