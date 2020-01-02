package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;


/**
 * The persistent class for the sis_perfil database table.
 * 
 */
@Entity
@Table(name="sis_perfil")
@NamedQuery(name="SisPerfil.findAll", query="SELECT s FROM SisPerfil s")
public class SisPerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_PERF")
	private int codigoPerf;

	@Column(name="DIRECT_ACTUALIZ_PERF")
	private String directActualizPerf;
	
	@Pattern(message = "El campo Hora Final solo debe contener horas validas (HH:mm)(24 horas)", regexp = "([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]")
	@Column(name="HORA_FINAL_PERF")
	private String horaFinalPerf;
	
	@Pattern(message = "El campo Hora Final solo debe contener horas validas (HH:mm)(24 horas)", regexp = "([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]")
	@Column(name="HORA_INICIAL_PERF")
	private String horaInicialPerf;
																					
	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name="NOMBRE_PERF")
	private String nombrePerf;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy="sisPerfil")
	private List<SisPermiso> sisPermisos;
	

	//bi-directional many-to-one association to SisUsuario
	@OneToMany(mappedBy="sisPerfil")
	private List<SisUsuario> sisUsuarios;

	public SisPerfil() {
	}

	public int getCodigoPerf() {
		return this.codigoPerf;
	}

	public void setCodigoPerf(int codigoPerf) {
		this.codigoPerf = codigoPerf;
	}

	public String getDirectActualizPerf() {
		return this.directActualizPerf;
	}

	public void setDirectActualizPerf(String directActualizPerf) {
		this.directActualizPerf = directActualizPerf;
	}

	public String getHoraFinalPerf() {
		return this.horaFinalPerf;
	}

	public void setHoraFinalPerf(String horaFinalPerf) {
		this.horaFinalPerf = horaFinalPerf;
	}

	public String getHoraInicialPerf() {
		return this.horaInicialPerf;
	}

	public void setHoraInicialPerf(String horaInicialPerf) {
		this.horaInicialPerf = horaInicialPerf;
	}

	public String getNombrePerf() {
		return this.nombrePerf;
	}

	public void setNombrePerf(String nombrePerf) {
		this.nombrePerf = nombrePerf;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public List<SisPermiso> getSisPermisos() {
		return this.sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public SisPermiso addSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().add(sisPermiso);
		sisPermiso.setSisPerfil(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisPerfil(null);

		return sisPermiso;
	}

	public List<SisUsuario> getSisUsuarios() {
		return this.sisUsuarios;
	}

	public void setSisUsuarios(List<SisUsuario> sisUsuarios) {
		this.sisUsuarios = sisUsuarios;
	}

	public SisUsuario addSisUsuario(SisUsuario sisUsuario) {
		getSisUsuarios().add(sisUsuario);
		sisUsuario.setSisPerfil(this);

		return sisUsuario;
	}

	public SisUsuario removeSisUsuario(SisUsuario sisUsuario) {
		getSisUsuarios().remove(sisUsuario);
		sisUsuario.setSisPerfil(null);

		return sisUsuario;
	}

}