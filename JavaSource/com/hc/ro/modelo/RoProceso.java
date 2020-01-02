package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the ro_proceso database table.
 * 
 */
@Entity
@Table(name = "ro_proceso")
@NamedQuery(name = "RoProceso.findAll", query = "SELECT r FROM RoProceso r")
public class RoProceso implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_PROS")
	private int codigoPros;

	@Column(name = "ANCESTRO_PROS")
	private String ancestroPros;

	@Digits(integer = 1, fraction = 2, message = "El campo Grado Automata solo debe tener 2 decimales")
	@Max(value = 1, message = "El campo Grado Automata no debe ser mayor a 1")
	@Min(value = 0, message = "El campo Grado Automata no debe ser menor a 0")
	@Column(name = "GRADO_AUTOMAT_PROS")
	private BigDecimal gradoAutomatPros;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_PROS")
	private String nombrePros;

	//@Pattern(message = "El campo Número solo debe contener letras, números y no espacios en blanco", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ]*$")
	@Column(name = "NUMERO_PROS")
	private String numeroPros;

	@Column(name = "OBSERVACION_PROS")
	private String observacionPros;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roProceso")
	private List<RoDetalleEvento> roDetalleEventos;

	// bi-directional many-to-one association to RoDetCriticProc
	@OneToMany(mappedBy = "roProceso")
	private List<RoDetCriticProc> roDetCriticProcs;

	// bi-directional many-to-one association to RoDetalleProceso
	@OneToMany(mappedBy = "roProceso")
	private List<RoDetalleProceso> roDetalleProcesos;

	// bi-directional many-to-one association to RoDetalleControl
	@OneToMany(mappedBy = "roProceso")
	private List<RoDetalleControl> roDetalleControls;

	// bi-directional many-to-one association to RoFiltroProceso
	@OneToMany(mappedBy = "roProceso")
	private List<RoFiltroProceso> roFiltroProcesos;

	// bi-directional many-to-one association to RoFiltroProceso
	@OneToMany(mappedBy = "roProceso")
	private List<RoRiesgoClave> roRiesgoClaves;

	// bi-directional many-to-one association to RoRespAgencia
	@OneToMany(mappedBy = "roProceso")
	private List<RoNegoPro> roNegoPro;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to GenNivelArbol
	@ManyToOne
	@JoinColumn(name = "CODIGO_GNIV")
	private GenNivelArbol genNivelArbol;

	@ManyToOne
	@JoinColumn(name = "CODIGO_GNIP")
	private GenNivelProceso genNivelProceso;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	// bi-directional many-to-one association to RoTipoProceso
	@ManyToOne
	@JoinColumn(name = "CODIGO_TIPR")
	private RoTipoProceso roTipoProceso;

	// bi-directional many-to-one association to RoCriticidad
	@ManyToOne
	@JoinColumn(name = "CODIGO_CRIT")
	private RoCriticidad roCriticidad;

	public GenNivelProceso getGenNivelProceso() {
		return genNivelProceso;
	}

	public void setGenNivelProceso(GenNivelProceso genNivelProceso) {
		this.genNivelProceso = genNivelProceso;
	}

	// bi-directional many-to-one association to RoTipoEjecucion
	@ManyToOne
	@JoinColumn(name = "CODIGO_TIEJ")
	private RoTipoEjecucion roTipoEjecucion;

	// bi-directional many-to-one association to RoFrecEjecucion
	@ManyToOne
	@JoinColumn(name = "CODIGO_FREJ")
	private RoFrecEjecucion roFrecEjecucion;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;
	
	public RoProceso() {
	}

	public RoProceso(String nombrePros) {
		super();
		this.nombrePros = nombrePros;
	}

	public RoProceso(int codigoPros, String nombrePros, String ancestroPros) {
		super();
		this.codigoPros = codigoPros;
		this.ancestroPros = ancestroPros;
		this.nombrePros = nombrePros;
	}

	public int getCodigoPros() {
		return this.codigoPros;
	}

	public void setCodigoPros(int codigoPros) {
		this.codigoPros = codigoPros;
	}

	public String getAncestroPros() {
		return this.ancestroPros;
	}

	public void setAncestroPros(String ancestroPros) {
		this.ancestroPros = ancestroPros;
	}

	public BigDecimal getGradoAutomatPros() {
		return this.gradoAutomatPros;
	}

	public void setGradoAutomatPros(BigDecimal gradoAutomatPros) {
		this.gradoAutomatPros = gradoAutomatPros;
	}

	public String getNombrePros() {
		return this.nombrePros;
	}

	public void setNombrePros(String nombrePros) {
		this.nombrePros = nombrePros;
	}

	public String getNumeroPros() {
		return this.numeroPros;
	}

	public void setNumeroPros(String numeroPros) {
		this.numeroPros = numeroPros;
	}

	public String getObservacionPros() {
		return this.observacionPros;
	}

	public void setObservacionPros(String observacionPros) {
		this.observacionPros = observacionPros;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return this.roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public RoDetalleEvento addRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().add(roDetalleEvento);
		roDetalleEvento.setRoProceso(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setRoProceso(null);

		return roDetalleEvento;
	}

	public List<RoDetalleProceso> getRoDetalleProcesos() {
		return this.roDetalleProcesos;
	}

	public void setRoDetalleProcesos(List<RoDetalleProceso> roDetalleProcesos) {
		this.roDetalleProcesos = roDetalleProcesos;
	}

	public RoDetalleProceso addRoDetalleProceso(
			RoDetalleProceso roDetalleProceso) {
		getRoDetalleProcesos().add(roDetalleProceso);
		roDetalleProceso.setRoProceso(this);

		return roDetalleProceso;
	}

	public RoDetalleProceso removeRoDetalleProceso(
			RoDetalleProceso roDetalleProceso) {
		getRoDetalleProcesos().remove(roDetalleProceso);
		roDetalleProceso.setRoProceso(null);

		return roDetalleProceso;
	}

	public List<RoFiltroProceso> getRoFiltroProcesos() {
		return this.roFiltroProcesos;
	}

	public void setRoFiltroProcesos(List<RoFiltroProceso> roFiltroProcesos) {
		this.roFiltroProcesos = roFiltroProcesos;
	}

	public RoFiltroProceso addRoFiltroProceso(RoFiltroProceso roFiltroProceso) {
		getRoFiltroProcesos().add(roFiltroProceso);
		roFiltroProceso.setRoProceso(this);

		return roFiltroProceso;
	}

	public RoFiltroProceso removeRoFiltroProceso(RoFiltroProceso roFiltroProceso) {
		getRoFiltroProcesos().remove(roFiltroProceso);
		roFiltroProceso.setRoProceso(null);

		return roFiltroProceso;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
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

	public RoTipoProceso getRoTipoProceso() {
		return this.roTipoProceso;
	}

	public void setRoTipoProceso(RoTipoProceso roTipoProceso) {
		this.roTipoProceso = roTipoProceso;
	}

	public RoCriticidad getRoCriticidad() {
		return this.roCriticidad;
	}

	public void setRoCriticidad(RoCriticidad roCriticidad) {
		this.roCriticidad = roCriticidad;
	}

	public RoTipoEjecucion getRoTipoEjecucion() {
		return this.roTipoEjecucion;
	}

	public void setRoTipoEjecucion(RoTipoEjecucion roTipoEjecucion) {
		this.roTipoEjecucion = roTipoEjecucion;
	}

	public RoFrecEjecucion getRoFrecEjecucion() {
		return this.roFrecEjecucion;
	}

	public void setRoFrecEjecucion(RoFrecEjecucion roFrecEjecucion) {
		this.roFrecEjecucion = roFrecEjecucion;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public List<RoDetCriticProc> getRoDetCriticProcs() {
		return roDetCriticProcs;
	}

	public void setRoDetCriticProcs(List<RoDetCriticProc> roDetCriticProcs) {
		this.roDetCriticProcs = roDetCriticProcs;
	}

	public List<RoDetalleControl> getRoDetalleControls() {
		return roDetalleControls;
	}

	public void setRoDetalleControls(List<RoDetalleControl> roDetalleControls) {
		this.roDetalleControls = roDetalleControls;
	}

	public List<RoNegoPro> getRoNegoPro() {
		return roNegoPro;
	}

	public void setRoNegoPro(List<RoNegoPro> roNegoPro) {
		this.roNegoPro = roNegoPro;
	}

	public List<RoRiesgoClave> getRoRiesgoClaves() {
		return roRiesgoClaves;
	}

	public void setRoRiesgoClaves(List<RoRiesgoClave> roRiesgoClaves) {
		this.roRiesgoClaves = roRiesgoClaves;
	}

}