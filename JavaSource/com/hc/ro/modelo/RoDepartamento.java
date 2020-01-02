package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_departamento database table.
 * 
 */
@Entity
@Table(name = "ro_departamento")
@NamedQuery(name = "RoDepartamento.findAll", query = "SELECT r FROM RoDepartamento r")
public class RoDepartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_DEPT")
	private int codigoDept;

	@Column(name = "ANCESTRO_DEPT")
	private String ancestroDept;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_DEPT")
	private String nombreDept;

	@Pattern(message = "El campo Número solo debe contener letras, números y no espacios en blanco", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ]*$")
	@Column(name = "NUMERO_DEPT")
	private String numeroDept;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	// bi-directional many-to-one association to GenNivelArbol
	@ManyToOne
	@JoinColumn(name = "CODIGO_GNIV")
	private GenNivelArbol genNivelArbol;

	// bi-directional many-to-one association to RoDeptAgencia
	@OneToMany(mappedBy = "roDepartamento")
	private List<RoDeptAgencia> roDeptAgencias;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roDepartamento")
	private List<RoDetalleEvento> roDetalleEventos;

	// bi-directional many-to-one association to RoDetCriticProc
	@OneToMany(mappedBy = "roDepartamento")
	private List<RoDetCriticProc> roDetCriticProcs;

	// bi-directional many-to-one association to RoFiltroProceso
	@OneToMany(mappedBy = "roDepartamento")
	private List<RoFiltroProceso> roFiltroProcesos;

	public RoDepartamento() {
	}

	public RoDepartamento(String nombreDept) {
		super();
		this.nombreDept = nombreDept;
	}

	public RoDepartamento(int codigoDept, String nombreDept,
			String ancestroDept) {
		super();
		this.codigoDept = codigoDept;
		this.ancestroDept = ancestroDept;
		this.nombreDept = nombreDept;
	}

	public int getCodigoDept() {
		return this.codigoDept;
	}

	public void setCodigoDept(int codigoDept) {
		this.codigoDept = codigoDept;
	}

	public String getAncestroDept() {
		return this.ancestroDept;
	}

	public void setAncestroDept(String ancestroDept) {
		this.ancestroDept = ancestroDept;
	}

	public String getNombreDept() {
		return this.nombreDept;
	}

	public void setNombreDept(String nombreDept) {
		this.nombreDept = nombreDept;
	}

	public String getNumeroDept() {
		return this.numeroDept;
	}

	public void setNumeroDept(String numeroDept) {
		this.numeroDept = numeroDept;
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

	public GenNivelArbol getGenNivelArbol() {
		return this.genNivelArbol;
	}

	public void setGenNivelArbol(GenNivelArbol genNivelArbol) {
		this.genNivelArbol = genNivelArbol;
	}

	public List<RoDeptAgencia> getRoDeptAgencias() {
		return this.roDeptAgencias;
	}

	public void setRoDeptAgencias(List<RoDeptAgencia> roDeptAgencias) {
		this.roDeptAgencias = roDeptAgencias;
	}

	public RoDeptAgencia addRoDeptAgencia(RoDeptAgencia roDeptAgencia) {
		getRoDeptAgencias().add(roDeptAgencia);
		roDeptAgencia.setRoDepartamento(this);

		return roDeptAgencia;
	}

	public RoDeptAgencia removeRoDeptAgencia(RoDeptAgencia roDeptAgencia) {
		getRoDeptAgencias().remove(roDeptAgencia);
		roDeptAgencia.setRoDepartamento(null);

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
		roDetalleEvento.setRoDepartamento(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setRoDepartamento(null);

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
		roFiltroProceso.setRoDepartamento(this);

		return roFiltroProceso;
	}

	public RoFiltroProceso removeRoFiltroProceso(RoFiltroProceso roFiltroProceso) {
		getRoFiltroProcesos().remove(roFiltroProceso);
		roFiltroProceso.setRoDepartamento(null);

		return roFiltroProceso;
	}

}