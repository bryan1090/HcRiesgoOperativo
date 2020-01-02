package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_agencia database table.
 * 
 */
@Entity
@Table(name = "ro_agencia")
@NamedQuery(name = "RoAgencia.findAll", query = "SELECT r FROM RoAgencia r")
public class RoAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_AGIA")
	private int codigoAgia;

	@Column(name = "ANCESTRO_AGIA")
	private String ancestroAgia;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_AGIA")
	private String nombreAgia;

	@Pattern(message = "El campo Número solo debe contener letras, números y no espacios en blanco", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ]*$")
	@Column(name = "NUMERO_AGIA")
	private String numeroAgia;

	@Column(name = "OBSERVACION_AGIA")
	private String observacionAgia;

	// bi-directional many-to-one association to RoTipoAgencia
	@ManyToOne
	@JoinColumn(name = "CODIGO_TIAG")
	private RoTipoAgencia roTipoAgencia;

	// bi-directional many-to-one association to GenNivelArbol
	@ManyToOne
	@JoinColumn(name = "CODIGO_GNIV")
	private GenNivelArbol genNivelArbol;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoDeptAgencia
	@OneToMany(mappedBy = "roAgencia")
	private List<RoDeptAgencia> roDeptAgencias;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roAgencia")
	private List<RoDetalleEvento> roDetalleEventos;

	// bi-directional many-to-one association to RoFiltroProceso
	@OneToMany(mappedBy = "roAgencia")
	private List<RoFiltroProceso> roFiltroProcesos;

	// bi-directional many-to-one association to RoRespAgencia
	@OneToMany(mappedBy = "roAgencia")
	private List<RoRespAgencia> roRespAgencias;

	public RoAgencia() {
	}

	public RoAgencia(int codigoAgia, String nombreAgia, String ancestroAgia) {
		super();
		this.codigoAgia = codigoAgia;
		this.ancestroAgia = ancestroAgia;
		this.nombreAgia = nombreAgia;
	}

	public RoAgencia(String nombreAgia) {
		super();
		this.nombreAgia = nombreAgia;
	}

	public int getCodigoAgia() {
		return this.codigoAgia;
	}

	public void setCodigoAgia(int codigoAgia) {
		this.codigoAgia = codigoAgia;
	}

	public String getAncestroAgia() {
		return this.ancestroAgia;
	}

	public void setAncestroAgia(String ancestroAgia) {
		this.ancestroAgia = ancestroAgia;
	}

	public String getNombreAgia() {
		return this.nombreAgia;
	}

	public void setNombreAgia(String nombreAgia) {
		this.nombreAgia = nombreAgia;
	}

	public String getNumeroAgia() {
		return this.numeroAgia;
	}

	public void setNumeroAgia(String numeroAgia) {
		this.numeroAgia = numeroAgia;
	}

	public String getObservacionAgia() {
		return this.observacionAgia;
	}

	public void setObservacionAgia(String observacionAgia) {
		this.observacionAgia = observacionAgia;
	}

	public RoTipoAgencia getRoTipoAgencia() {
		return this.roTipoAgencia;
	}

	public void setRoTipoAgencia(RoTipoAgencia roTipoAgencia) {
		this.roTipoAgencia = roTipoAgencia;
	}

	public GenNivelArbol getGenNivelArbol() {
		return this.genNivelArbol;
	}

	public void setGenNivelArbol(GenNivelArbol genNivelArbol) {
		this.genNivelArbol = genNivelArbol;
	}

	public GenEstado getGenEstado() {
		return this.genEstado;
	}

	public void setGenEstado(GenEstado genEstado) {
		this.genEstado = genEstado;
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

	public List<RoDeptAgencia> getRoDeptAgencias() {
		return this.roDeptAgencias;
	}

	public void setRoDeptAgencias(List<RoDeptAgencia> roDeptAgencias) {
		this.roDeptAgencias = roDeptAgencias;
	}

	public RoDeptAgencia addRoDeptAgencia(RoDeptAgencia roDeptAgencia) {
		getRoDeptAgencias().add(roDeptAgencia);
		roDeptAgencia.setRoAgencia(this);

		return roDeptAgencia;
	}

	public RoDeptAgencia removeRoDeptAgencia(RoDeptAgencia roDeptAgencia) {
		getRoDeptAgencias().remove(roDeptAgencia);
		roDeptAgencia.setRoAgencia(null);

		return roDeptAgencia;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return this.roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public RoDetalleEvento addRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().add(roDetalleEvento);
		roDetalleEvento.setRoAgencia(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setRoAgencia(null);

		return roDetalleEvento;
	}

	public List<RoFiltroProceso> getRoFiltroProcesos() {
		return this.roFiltroProcesos;
	}

	public void setRoFiltroProcesos(List<RoFiltroProceso> roFiltroProcesos) {
		this.roFiltroProcesos = roFiltroProcesos;
	}

	public RoFiltroProceso addRoFiltroProceso(RoFiltroProceso roFiltroProceso) {
		getRoFiltroProcesos().add(roFiltroProceso);
		roFiltroProceso.setRoAgencia(this);

		return roFiltroProceso;
	}

	public RoFiltroProceso removeRoFiltroProceso(RoFiltroProceso roFiltroProceso) {
		getRoFiltroProcesos().remove(roFiltroProceso);
		roFiltroProceso.setRoAgencia(null);

		return roFiltroProceso;
	}

	public List<RoRespAgencia> getRoRespAgencias() {
		return this.roRespAgencias;
	}

	public void setRoRespAgencias(List<RoRespAgencia> roRespAgencias) {
		this.roRespAgencias = roRespAgencias;
	}

	public RoRespAgencia addRoRespAgencia(RoRespAgencia roRespAgencia) {
		getRoRespAgencias().add(roRespAgencia);
		roRespAgencia.setRoAgencia(this);

		return roRespAgencia;
	}

	public RoRespAgencia removeRoRespAgencia(RoRespAgencia roRespAgencia) {
		getRoRespAgencias().remove(roRespAgencia);
		roRespAgencia.setRoAgencia(null);

		return roRespAgencia;
	}

}