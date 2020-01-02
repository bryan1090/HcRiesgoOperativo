package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the sis_usuario database table.
 * 
 */
@Entity
@Table(name = "sis_usuario")
@NamedQuery(name = "SisUsuario.findAll", query = "SELECT s FROM SisUsuario s")
public class SisUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_USUA")
	private int codigoUsua;

	@Column(name = "NOMBRE_USUA")
	private String nombreUsua;

	@Column(name = "AUDITABLE_USUA")
	private Double auditableUsua;

	@Column(name = "BLOQUEADO_USUA")
	private Double bloqueadoUsua;

	@Column(name = "PERMISO_SU_DEVE")
	private Double permisoSuDeve;

	@Column(name = "CONTRASENIA_USUA")
	private String contraseniaUsua;
	
	@Column(name = "CLAVE_RESETEADA")
	private int claveReseteada;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CADUCA_USUA")
	private Date fechaCaducaUsua;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_LOG_USUA")
	private Date fechaLogUsua;

	@Column(name = "HORA_LOG_USUA")
	private String horaLogUsua;

	@Column(name = "IP_LOG_USUA")
	private String ipLogUsua;

	@Pattern(message = "El campo Nombre Completo solo debe contener letras", regexp = "^[A-Za-zÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_COMPLETO_USUA")
	private String nombreCompletoUsua;

	// bi-directional many-to-one association to RoResponsable
	@OneToMany(mappedBy = "sisUsuario")
	private List<RoResponsable> roResponsables;

	// bi-directional many-to-one association to SisAlertaUsuario
	@OneToMany(mappedBy = "sisUsuario1")
	private List<SisAlertaUsuario> sisAlertaUsuarios1;

	@OneToMany(mappedBy = "sisUsuario")
	private List<RoDetalleEvento> roDetalleEventos;

	// bi-directional many-to-one association to SisAlertaUsuario
	@OneToMany(mappedBy = "sisUsuario2")
	private List<SisAlertaUsuario> sisAlertaUsuarios2;

	// bi-directional many-to-one association to SisAuditoria
	@OneToMany(mappedBy = "sisUsuario")
	private List<SisAuditoria> sisAuditorias;

	// bi-directional many-to-one association to SisControlProceso
	@OneToMany(mappedBy = "sisUsuario")
	private List<SisControlProceso> sisControlProcesos;

	// bi-directional many-to-one association to SisDetalleProceso
	@OneToMany(mappedBy = "sisUsuario")
	private List<SisDetalleProceso> sisDetalleProcesos;

	// bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy = "sisUsuario")
	private List<SisPermiso> sisPermisos;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to SisPerfil
	@ManyToOne
	@JoinColumn(name = "CODIGO_PERF")
	private SisPerfil sisPerfil;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to RoTipoCargo
	@ManyToOne
	@JoinColumn(name = "CODIGO_CARGO")
	private RoTipoCargo roTipoCargo;

	public SisUsuario() {
	}

	public SisUsuario(int codigoUsua, String nombreUsua) {
		this.codigoUsua = codigoUsua;
		this.nombreUsua = nombreUsua;
	}

	public SisUsuario(int codigoUsua, String nombreUsua,
			String nombreCompletoUsua) {
		super();
		this.codigoUsua = codigoUsua;
		this.nombreUsua = nombreUsua;
		this.nombreCompletoUsua = nombreCompletoUsua;
	}

	public int getCodigoUsua() {
		return this.codigoUsua;
	}

	public void setCodigoUsua(int codigoUsua) {
		this.codigoUsua = codigoUsua;
	}

	public Double getAuditableUsua() {
		return this.auditableUsua;
	}

	public void setAuditableUsua(Double auditableUsua) {
		this.auditableUsua = auditableUsua;
	}

	public Double getBloqueadoUsua() {
		return this.bloqueadoUsua;
	}

	public void setBloqueadoUsua(Double bloqueadoUsua) {
		this.bloqueadoUsua = bloqueadoUsua;
	}

	public String getContraseniaUsua() {
		return this.contraseniaUsua;
	}

	public void setContraseniaUsua(String contraseniaUsua) {
		this.contraseniaUsua = contraseniaUsua;
	}

	public Date getFechaCaducaUsua() {
		return this.fechaCaducaUsua;
	}

	public void setFechaCaducaUsua(Date fechaCaducaUsua) {
		this.fechaCaducaUsua = fechaCaducaUsua;
	}

	public Date getFechaLogUsua() {
		return this.fechaLogUsua;
	}

	public void setFechaLogUsua(Date fechaLogUsua) {
		this.fechaLogUsua = fechaLogUsua;
	}

	public String getHoraLogUsua() {
		return this.horaLogUsua;
	}

	public void setHoraLogUsua(String horaLogUsua) {
		this.horaLogUsua = horaLogUsua;
	}

	public String getIpLogUsua() {
		return this.ipLogUsua;
	}

	public void setIpLogUsua(String ipLogUsua) {
		this.ipLogUsua = ipLogUsua;
	}

	public String getNombreCompletoUsua() {
		return this.nombreCompletoUsua;
	}

	public void setNombreCompletoUsua(String nombreCompletoUsua) {
		this.nombreCompletoUsua = nombreCompletoUsua;
	}

	public String getNombreUsua() {
		return this.nombreUsua;
	}

	public void setNombreUsua(String nombreUsua) {
		this.nombreUsua = nombreUsua;
	}

	public List<RoResponsable> getRoResponsables() {
		return this.roResponsables;
	}

	public void setRoResponsables(List<RoResponsable> roResponsables) {
		this.roResponsables = roResponsables;
	}

	public RoResponsable addRoResponsable(RoResponsable roResponsable) {
		getRoResponsables().add(roResponsable);
		roResponsable.setSisUsuario(this);

		return roResponsable;
	}

	public RoResponsable removeRoResponsable(RoResponsable roResponsable) {
		getRoResponsables().remove(roResponsable);
		roResponsable.setSisUsuario(null);

		return roResponsable;
	}

	public List<SisAlertaUsuario> getSisAlertaUsuarios1() {
		return this.sisAlertaUsuarios1;
	}

	public void setSisAlertaUsuarios1(List<SisAlertaUsuario> sisAlertaUsuarios1) {
		this.sisAlertaUsuarios1 = sisAlertaUsuarios1;
	}

	public SisAlertaUsuario addSisAlertaUsuarios1(
			SisAlertaUsuario sisAlertaUsuarios1) {
		getSisAlertaUsuarios1().add(sisAlertaUsuarios1);
		sisAlertaUsuarios1.setSisUsuario1(this);

		return sisAlertaUsuarios1;
	}

	public SisAlertaUsuario removeSisAlertaUsuarios1(
			SisAlertaUsuario sisAlertaUsuarios1) {
		getSisAlertaUsuarios1().remove(sisAlertaUsuarios1);
		sisAlertaUsuarios1.setSisUsuario1(null);

		return sisAlertaUsuarios1;
	}

	public List<SisAlertaUsuario> getSisAlertaUsuarios2() {
		return this.sisAlertaUsuarios2;
	}

	public void setSisAlertaUsuarios2(List<SisAlertaUsuario> sisAlertaUsuarios2) {
		this.sisAlertaUsuarios2 = sisAlertaUsuarios2;
	}

	public SisAlertaUsuario addSisAlertaUsuarios2(
			SisAlertaUsuario sisAlertaUsuarios2) {
		getSisAlertaUsuarios2().add(sisAlertaUsuarios2);
		sisAlertaUsuarios2.setSisUsuario2(this);

		return sisAlertaUsuarios2;
	}

	public SisAlertaUsuario removeSisAlertaUsuarios2(
			SisAlertaUsuario sisAlertaUsuarios2) {
		getSisAlertaUsuarios2().remove(sisAlertaUsuarios2);
		sisAlertaUsuarios2.setSisUsuario2(null);

		return sisAlertaUsuarios2;
	}

	public List<SisAuditoria> getSisAuditorias() {
		return this.sisAuditorias;
	}

	public void setSisAuditorias(List<SisAuditoria> sisAuditorias) {
		this.sisAuditorias = sisAuditorias;
	}

	public SisAuditoria addSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().add(sisAuditoria);
		sisAuditoria.setSisUsuario(this);

		return sisAuditoria;
	}

	public SisAuditoria removeSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().remove(sisAuditoria);
		sisAuditoria.setSisUsuario(null);

		return sisAuditoria;
	}

	public List<SisControlProceso> getSisControlProcesos() {
		return this.sisControlProcesos;
	}

	public void setSisControlProcesos(List<SisControlProceso> sisControlProcesos) {
		this.sisControlProcesos = sisControlProcesos;
	}

	public SisControlProceso addSisControlProceso(
			SisControlProceso sisControlProceso) {
		getSisControlProcesos().add(sisControlProceso);
		sisControlProceso.setSisUsuario(this);

		return sisControlProceso;
	}

	public SisControlProceso removeSisControlProceso(
			SisControlProceso sisControlProceso) {
		getSisControlProcesos().remove(sisControlProceso);
		sisControlProceso.setSisUsuario(null);

		return sisControlProceso;
	}

	public List<SisDetalleProceso> getSisDetalleProcesos() {
		return this.sisDetalleProcesos;
	}

	public void setSisDetalleProcesos(List<SisDetalleProceso> sisDetalleProcesos) {
		this.sisDetalleProcesos = sisDetalleProcesos;
	}

	public SisDetalleProceso addSisDetalleProceso(
			SisDetalleProceso sisDetalleProceso) {
		getSisDetalleProcesos().add(sisDetalleProceso);
		sisDetalleProceso.setSisUsuario(this);

		return sisDetalleProceso;
	}

	public SisDetalleProceso removeSisDetalleProceso(
			SisDetalleProceso sisDetalleProceso) {
		getSisDetalleProcesos().remove(sisDetalleProceso);
		sisDetalleProceso.setSisUsuario(null);

		return sisDetalleProceso;
	}

	public List<SisPermiso> getSisPermisos() {
		return this.sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public SisPermiso addSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().add(sisPermiso);
		sisPermiso.setSisUsuario(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisUsuario(null);

		return sisPermiso;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public SisPerfil getSisPerfil() {
		return this.sisPerfil;
	}

	public void setSisPerfil(SisPerfil sisPerfil) {
		this.sisPerfil = sisPerfil;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public Double getPermisoSuDeve() {
		return permisoSuDeve;
	}

	public void setPermisoSuDeve(Double permisoSuDeve) {
		this.permisoSuDeve = permisoSuDeve;
	}

	public RoTipoCargo getRoTipoCargo() {
		return roTipoCargo;
	}

	public void setRoTipoCargo(RoTipoCargo roTipoCargo) {
		this.roTipoCargo = roTipoCargo;
	}

	public int getClaveReseteada() {
		return claveReseteada;
	}

	public void setClaveReseteada(int claveReseteada) {
		this.claveReseteada = claveReseteada;
	}

}