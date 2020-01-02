package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the sis_control_proceso database table.
 * 
 */
@Entity
@Table(name="sis_control_proceso")
@NamedQuery(name="SisControlProceso.findAll", query="SELECT s FROM SisControlProceso s")
public class SisControlProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_SCLP")
	private String codigoSclp;

	@Column(name="AVANCE_SCLP")
	private BigDecimal avanceSclp;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_FIN_SCLP")
	private Date fechaFinSclp;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INICIO_SCLP")
	private Date fechaInicioSclp;

	@Column(name="FUNCION_SCLP")
	private String funcionSclp;

	@Column(name="HORA_FIN_SCLP")
	private String horaFinSclp;

	@Column(name="HORA_INICIO_SCLP")
	private String horaInicioSclp;

	@Column(name="MENSAJE1_SCLP")
	private String mensaje1Sclp;

	@Column(name="MENSAJE2_SCLP")
	private String mensaje2Sclp;

	@Column(name="TIEMPO_EJEC_SCLP")
	private String tiempoEjecSclp;

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
	@JoinColumn(name="CODIGO_USUA")
	private SisUsuario sisUsuario;

	public SisControlProceso() {
	}

	public String getCodigoSclp() {
		return this.codigoSclp;
	}

	public void setCodigoSclp(String codigoSclp) {
		this.codigoSclp = codigoSclp;
	}

	public BigDecimal getAvanceSclp() {
		return this.avanceSclp;
	}

	public void setAvanceSclp(BigDecimal avanceSclp) {
		this.avanceSclp = avanceSclp;
	}

	public Date getFechaFinSclp() {
		return this.fechaFinSclp;
	}

	public void setFechaFinSclp(Date fechaFinSclp) {
		this.fechaFinSclp = fechaFinSclp;
	}

	public Date getFechaInicioSclp() {
		return this.fechaInicioSclp;
	}

	public void setFechaInicioSclp(Date fechaInicioSclp) {
		this.fechaInicioSclp = fechaInicioSclp;
	}

	public String getFuncionSclp() {
		return this.funcionSclp;
	}

	public void setFuncionSclp(String funcionSclp) {
		this.funcionSclp = funcionSclp;
	}

	public String getHoraFinSclp() {
		return this.horaFinSclp;
	}

	public void setHoraFinSclp(String horaFinSclp) {
		this.horaFinSclp = horaFinSclp;
	}

	public String getHoraInicioSclp() {
		return this.horaInicioSclp;
	}

	public void setHoraInicioSclp(String horaInicioSclp) {
		this.horaInicioSclp = horaInicioSclp;
	}

	public String getMensaje1Sclp() {
		return this.mensaje1Sclp;
	}

	public void setMensaje1Sclp(String mensaje1Sclp) {
		this.mensaje1Sclp = mensaje1Sclp;
	}

	public String getMensaje2Sclp() {
		return this.mensaje2Sclp;
	}

	public void setMensaje2Sclp(String mensaje2Sclp) {
		this.mensaje2Sclp = mensaje2Sclp;
	}

	public String getTiempoEjecSclp() {
		return this.tiempoEjecSclp;
	}

	public void setTiempoEjecSclp(String tiempoEjecSclp) {
		this.tiempoEjecSclp = tiempoEjecSclp;
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

	public SisUsuario getSisUsuario() {
		return this.sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

}