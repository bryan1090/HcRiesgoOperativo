package com.hc.ro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

/**
 * The persistent class for the ro_negocio database table.
 * 
 */
@Entity
@Table(name = "ro_negocio")
@NamedQuery(name = "RoNegocio.findAll", query = "SELECT r FROM RoNegocio r")
public class RoNegocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_NEGO")
	private int codigoNego;

	@Column(name = "ANCESTRO_NEGO")
	private String ancestroNego;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_NEGO")
	private String nombreNego;

	@Pattern(message = "El campo Número solo debe contener letras, números y no espacios en blanco", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ]*$")
	@Column(name = "NUMERO_NEGO")
	private String numeroNego;

	@Column(name = "OBSERVACION_NEGO")
	private String observacionNego;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roNegocio1")
	private List<RoDetalleEvento> roDetalleEventos1;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roNegocio2")
	private List<RoDetalleEvento> roDetalleEventos2;

	// bi-directional many-to-one association to RoConsecuenciaImpacto
	@OneToMany(mappedBy = "roNegocio")
	private List<RoConsecuenciaImpacto> roConsecuenciaImpacto;

	// bi-directional many-to-one association to RoAriesgo
	@OneToMany(mappedBy = "roNegocio")
	private List<RoAriesgo> roAriesgo;

	// bi-directional many-to-one association to RoProbabilidadEvento
	@OneToMany(mappedBy = "roNegocio")
	private List<RoProbabilidadEvento> roProbabilidadEvento;

	// bi-directional many-to-one association to RoDetalleNegocio
	@OneToMany(mappedBy = "roNegocio")
	private List<RoDetalleNegocio> roDetalleNegocios;

	// bi-directional many-to-one association to RoFiltroProceso
	@OneToMany(mappedBy = "roNegocio")
	private List<RoFiltroProceso> roFiltroProcesos;

	// bi-directional many-to-one association to RoNegoPro
	@OneToMany(mappedBy = "roNegocio")
	private List<RoNegoPro> roNegoPro;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to GenNivelArbol
	@ManyToOne
	@JoinColumn(name = "CODIGO_GNIV")
	private GenNivelArbol genNivelArbol;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	@Transient
	private int nivel;

	@Transient
	private int totalOcurrencias;
	@Transient
	private BigDecimal totalSeveridad;

	@Transient
	private BigDecimal porcentajeOcurrencias;
	@Transient
	private BigDecimal porcentajeSeveridad;

	public List<RoAriesgo> getRoAriesgo() {
		return roAriesgo;
	}

	public void setRoAriesgo(List<RoAriesgo> roAriesgo) {
		this.roAriesgo = roAriesgo;
	}

	// bi-directional many-to-one association to RoTipoNegocio
	@ManyToOne
	@JoinColumn(name = "CODIGO_TNEG")
	private RoTipoNegocio roTipoNegocio;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	public RoNegocio() {
	}

	public RoNegocio(int codigoNego, String nombreNego, String ancestroNego) {
		super();
		this.codigoNego = codigoNego;
		this.ancestroNego = ancestroNego;
		this.nombreNego = nombreNego;
	}

	public int getCodigoNego() {
		return this.codigoNego;
	}

	public void setCodigoNego(int codigoNego) {
		this.codigoNego = codigoNego;
	}

	public String getAncestroNego() {
		return this.ancestroNego;
	}

	public void setAncestroNego(String ancestroNego) {
		this.ancestroNego = ancestroNego;
	}

	public String getNombreNego() {
		return this.nombreNego;
	}

	public void setNombreNego(String nombreNego) {
		this.nombreNego = nombreNego;
	}

	public String getNumeroNego() {
		return this.numeroNego;
	}

	public void setNumeroNego(String numeroNego) {
		this.numeroNego = numeroNego;
	}

	public String getObservacionNego() {
		return this.observacionNego;
	}

	public void setObservacionNego(String observacionNego) {
		this.observacionNego = observacionNego;
	}

	public List<RoDetalleEvento> getRoDetalleEventos1() {
		return this.roDetalleEventos1;
	}

	public void setRoDetalleEventos1(List<RoDetalleEvento> roDetalleEventos1) {
		this.roDetalleEventos1 = roDetalleEventos1;
	}

	public RoDetalleEvento addRoDetalleEventos1(
			RoDetalleEvento roDetalleEventos1) {
		getRoDetalleEventos1().add(roDetalleEventos1);
		roDetalleEventos1.setRoNegocio1(this);

		return roDetalleEventos1;
	}

	public RoDetalleEvento removeRoDetalleEventos1(
			RoDetalleEvento roDetalleEventos1) {
		getRoDetalleEventos1().remove(roDetalleEventos1);
		roDetalleEventos1.setRoNegocio1(null);

		return roDetalleEventos1;
	}

	public List<RoDetalleEvento> getRoDetalleEventos2() {
		return this.roDetalleEventos2;
	}

	public void setRoDetalleEventos2(List<RoDetalleEvento> roDetalleEventos2) {
		this.roDetalleEventos2 = roDetalleEventos2;
	}

	public RoDetalleEvento addRoDetalleEventos2(
			RoDetalleEvento roDetalleEventos2) {
		getRoDetalleEventos2().add(roDetalleEventos2);
		roDetalleEventos2.setRoNegocio2(this);

		return roDetalleEventos2;
	}

	public RoDetalleEvento removeRoDetalleEventos2(
			RoDetalleEvento roDetalleEventos2) {
		getRoDetalleEventos2().remove(roDetalleEventos2);
		roDetalleEventos2.setRoNegocio2(null);

		return roDetalleEventos2;
	}

	public List<RoDetalleNegocio> getRoDetalleNegocios() {
		return this.roDetalleNegocios;
	}

	public void setRoDetalleNegocios(List<RoDetalleNegocio> roDetalleNegocios) {
		this.roDetalleNegocios = roDetalleNegocios;
	}

	public RoDetalleNegocio addRoDetalleNegocio(
			RoDetalleNegocio roDetalleNegocio) {
		getRoDetalleNegocios().add(roDetalleNegocio);
		roDetalleNegocio.setRoNegocio(this);

		return roDetalleNegocio;
	}

	public RoDetalleNegocio removeRoDetalleNegocio(
			RoDetalleNegocio roDetalleNegocio) {
		getRoDetalleNegocios().remove(roDetalleNegocio);
		roDetalleNegocio.setRoNegocio(null);

		return roDetalleNegocio;
	}

	public List<RoFiltroProceso> getRoFiltroProcesos() {
		return this.roFiltroProcesos;
	}

	public void setRoFiltroProcesos(List<RoFiltroProceso> roFiltroProcesos) {
		this.roFiltroProcesos = roFiltroProcesos;
	}

	public RoFiltroProceso addRoFiltroProceso(RoFiltroProceso roFiltroProceso) {
		getRoFiltroProcesos().add(roFiltroProceso);
		roFiltroProceso.setRoNegocio(this);

		return roFiltroProceso;
	}

	public RoFiltroProceso removeRoFiltroProceso(RoFiltroProceso roFiltroProceso) {
		getRoFiltroProcesos().remove(roFiltroProceso);
		roFiltroProceso.setRoNegocio(null);

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

	public RoTipoNegocio getRoTipoNegocio() {
		return this.roTipoNegocio;
	}

	public void setRoTipoNegocio(RoTipoNegocio roTipoNegocio) {
		this.roTipoNegocio = roTipoNegocio;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public List<RoConsecuenciaImpacto> getRoConsecuenciaImpacto() {
		return roConsecuenciaImpacto;
	}

	public void setRoConsecuenciaImpacto(
			List<RoConsecuenciaImpacto> roConsecuenciaImpacto) {
		this.roConsecuenciaImpacto = roConsecuenciaImpacto;
	}

	public List<RoProbabilidadEvento> getRoProbabilidadEvento() {
		return roProbabilidadEvento;
	}

	public void setRoProbabilidadEvento(
			List<RoProbabilidadEvento> roProbabilidadEvento) {
		this.roProbabilidadEvento = roProbabilidadEvento;
	}

	public List<RoNegoPro> getRoNegoPro() {
		return roNegoPro;
	}

	public void setRoNegoPro(List<RoNegoPro> roNegoPro) {
		this.roNegoPro = roNegoPro;
	}

	public int getTotalOcurrencias() {
		return totalOcurrencias;
	}

	public void setTotalOcurrencias(int totalOcurrencias) {
		this.totalOcurrencias = totalOcurrencias;
	}

	public BigDecimal getTotalSeveridad() {
		return totalSeveridad;
	}

	public void setTotalSeveridad(BigDecimal totalSeveridad) {
		this.totalSeveridad = totalSeveridad;
	}

	public BigDecimal getPorcentajeSeveridad() {
		return porcentajeSeveridad;
	}

	public void setPorcentajeSeveridad(BigDecimal porcentajeSeveridad) {
		this.porcentajeSeveridad = porcentajeSeveridad;
	}

	public BigDecimal getPorcentajeOcurrencias() {
		return porcentajeOcurrencias;
	}

	public void setPorcentajeOcurrencias(BigDecimal porcentajeOcurrencias) {
		this.porcentajeOcurrencias = porcentajeOcurrencias;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public RoNegocio(String nombreNego) {
		super();
		this.nombreNego = nombreNego;
	}

}