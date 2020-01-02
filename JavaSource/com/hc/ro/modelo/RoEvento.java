package com.hc.ro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_evento database table.
 * 
 */
@Entity
@Table(name = "ro_evento")
@NamedQuery(name = "RoEvento.findAll", query = "SELECT r FROM RoEvento r")
public class RoEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_EVEN")
	private int codigoEven;

	public RoEvento(String nombreEven) {
		super();
		this.nombreEven = nombreEven;
	}

	public RoEvento(int codigoEven, String nombreEven, String ancestroEven) {
		super();
		this.codigoEven = codigoEven;
		this.ancestroEven = ancestroEven;
		this.nombreEven = nombreEven;
	}

	@Column(name = "ANCESTRO_EVEN")
	private String ancestroEven;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_EVEN")
	private String nombreEven;

	@Pattern(message = "El campo Número solo debe contener letras, números y no espacios en blanco", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ]*$")
	@Column(name = "NUMERO_EVEN")
	private String numeroEven;

	@Column(name = "OBSERVACION_EVEN")
	private String observacionEven;

	// bi-directional many-to-one association to RoCaractEvento
	@OneToMany(mappedBy = "roEvento")
	private List<RoCaractEvento> roCaractEventos;

	// bi-directional many-to-one association to RoDetCarctEvent
	@OneToMany(mappedBy = "roEvento")
	private List<RoDetCarctEvent> roDetCarctEvents;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "roEvento")
	private List<RoDetalleEvento> roDetalleEventos;

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

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	@Transient
	private int numeroOcurrencias;

	@Transient
	private double valorPerdidaEvento;

	@Transient
	private int nivel;

	@Transient
	private List<RoDetalleEvento> detallesEvento;

	@Transient
	private List<RoEvento> eventosHijo;	
	
	@Transient
	private int totalOcurrencias;
	@Transient
	private BigDecimal totalSeveridad;

	@Transient
	private BigDecimal porcentajeOcurrencias;
	@Transient
	private BigDecimal porcentajeSeveridad;

	public RoEvento() {
	}	

	public RoEvento(int codigoEven, String nombreEven) {
		super();
		this.codigoEven = codigoEven;
		this.nombreEven = nombreEven;
	}

	@Transient
	private int numConsecuencia;

	@Transient
	private int numProbabilidad;

	@Transient
	private String negocio;

	public int getCodigoEven() {
		return this.codigoEven;
	}

	public void setCodigoEven(int codigoEven) {
		this.codigoEven = codigoEven;
	}

	public String getAncestroEven() {
		return this.ancestroEven;
	}

	public void setAncestroEven(String ancestroEven) {
		this.ancestroEven = ancestroEven;
	}

	public String getNombreEven() {
		return this.nombreEven;
	}

	public void setNombreEven(String nombreEven) {
		this.nombreEven = nombreEven;
	}

	public String getNumeroEven() {
		return this.numeroEven;
	}

	public void setNumeroEven(String numeroEven) {
		this.numeroEven = numeroEven;
	}

	public String getObservacionEven() {
		return this.observacionEven;
	}

	public void setObservacionEven(String observacionEven) {
		this.observacionEven = observacionEven;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return this.roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public RoDetalleEvento addRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().add(roDetalleEvento);
		roDetalleEvento.setRoEvento(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setRoEvento(null);

		return roDetalleEvento;
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

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public List<RoCaractEvento> getRoCaractEventos() {
		return roCaractEventos;
	}

	public void setRoCaractEventos(List<RoCaractEvento> roCaractEventos) {
		this.roCaractEventos = roCaractEventos;
	}

	public List<RoDetCarctEvent> getRoDetCarctEvents() {
		return roDetCarctEvents;
	}

	public void setRoDetCarctEvents(List<RoDetCarctEvent> roDetCarctEvents) {
		this.roDetCarctEvents = roDetCarctEvents;
	}

	public int getNumeroOcurrencias() {
		return numeroOcurrencias;
	}

	public void setNumeroOcurrencias(int numeroOcurrencias) {
		this.numeroOcurrencias = numeroOcurrencias;
	}

	public double getValorPerdidaEvento() {
		return valorPerdidaEvento;
	}

	public void setValorPerdidaEvento(double valorPerdidaEvento) {
		this.valorPerdidaEvento = valorPerdidaEvento;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public List<RoDetalleEvento> getDetallesEvento() {
		return detallesEvento;
	}

	public void setDetallesEvento(List<RoDetalleEvento> detallesEvento) {
		this.detallesEvento = detallesEvento;
	}

	public int getNumConsecuencia() {
		return numConsecuencia;
	}

	public void setNumConsecuencia(int numConsecuencia) {
		this.numConsecuencia = numConsecuencia;
	}

	public int getNumProbabilidad() {
		return numProbabilidad;
	}

	public void setNumProbabilidad(int numProbabilidad) {
		this.numProbabilidad = numProbabilidad;
	}

	public String getNegocio() {
		return negocio;
	}

	public void setNegocio(String negocio) {
		this.negocio = negocio;
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

	public List<RoEvento> getEventosHijo() {
		return eventosHijo;
	}

	public void setEventosHijo(List<RoEvento> eventosHijo) {
		this.eventosHijo = eventosHijo;
	}

}