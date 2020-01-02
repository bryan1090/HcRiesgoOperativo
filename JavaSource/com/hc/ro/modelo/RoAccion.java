package com.hc.ro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "ro_accion")
@NamedQuery(name = "RoAccion.findAll", query = "SELECT r FROM RoAccion r")
public class RoAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_ACCI")
	private int codigoAcci;

	@ManyToOne
	@JoinColumn(name = "CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;

	@ManyToOne
	@JoinColumn(name = "CODIGO_RESP")
	private RoResponsable roResponsable;

	@Column(name = "NOMBRE_ACCI")
	private String nombreAcci;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO_ACCI")
	private Date fechaInicioAcci;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN_ACCI")
	private Date fechaFinAcci;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_REGISTRO_ACCI")
	private Date fechaRegistroAcci;

	@Column(name = "NUMERO_OCURRENCIAS_ACCI")
	private int numeroOcurrenciasAcci;

	@Column(name = "RIESGO_INHERENTE")
	private BigDecimal riesgoInherente;

	@Column(name = "RIESGO_RESIDUAL")
	private BigDecimal riesgoResidual;

	@Column(name = "MONTO_PERDIDA")
	private BigDecimal montoPerdida;

	@Column(name = "NUM_PROBA_INHERENTE")
	private String numProbaInherente;

	@Column(name = "PROBA_INHERENTE")
	private String probaInherente;

	@Column(name = "NUM_PROBA_RESIDUAL")
	private String numProbaResidual;

	@Column(name = "PROBA_RESIDUAL")
	private String probaResidual;

	@Column(name = "NUM_CONSEC_INHERENTE")
	private String numConsecInherente;

	@Column(name = "CONSEC_INHERENTE")
	private String consecInherente;

	@Column(name = "NUM_CONSEC_RESIDUAL")
	private String numConsecResidual;

	@Column(name = "CONSEC_RESIDUAL")
	private String consecResidual;

	@Column(name = "RESUMEN_ACCI")
	private String resumenAcci;

	@Column(name = "FIN_ACCI")
	private int finAcci;

	@Column(name = "APROB_ACCI")
	private int aprobAcci;

	@Transient
	private String observaciones;

	public int getCodigoAcci() {
		return codigoAcci;
	}

	public void setCodigoAcci(int codigoAcci) {
		this.codigoAcci = codigoAcci;
	}

	public RoDetalleEvento getRoDetalleEvento() {
		return roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

	public RoResponsable getRoResponsable() {
		return roResponsable;
	}

	public void setRoResponsable(RoResponsable roResponsable) {
		this.roResponsable = roResponsable;
	}

	public Date getFechaInicioAcci() {
		return fechaInicioAcci;
	}

	public void setFechaInicioAcci(Date fechaInicioAcci) {
		this.fechaInicioAcci = fechaInicioAcci;
	}

	public Date getFechaFinAcci() {
		return fechaFinAcci;
	}

	public void setFechaFinAcci(Date fechaFinAcci) {
		this.fechaFinAcci = fechaFinAcci;
	}

	public Date getFechaRegistroAcci() {
		return fechaRegistroAcci;
	}

	public void setFechaRegistroAcci(Date fechaRegistroAcci) {
		this.fechaRegistroAcci = fechaRegistroAcci;
	}

	public int getNumeroOcurrenciasAcci() {
		return numeroOcurrenciasAcci;
	}

	public void setNumeroOcurrenciasAcci(int numeroOcurrenciasAcci) {
		this.numeroOcurrenciasAcci = numeroOcurrenciasAcci;
	}

	public String getNumProbaInherente() {
		return numProbaInherente;
	}

	public void setNumProbaInherente(String numProbaInherente) {
		this.numProbaInherente = numProbaInherente;
	}

	public String getProbaInherente() {
		return probaInherente;
	}

	public void setProbaInherente(String probaInherente) {
		this.probaInherente = probaInherente;
	}

	public String getNumProbaResidual() {
		return numProbaResidual;
	}

	public void setNumProbaResidual(String numProbaResidual) {
		this.numProbaResidual = numProbaResidual;
	}

	public String getProbaResidual() {
		return probaResidual;
	}

	public void setProbaResidual(String probaResidual) {
		this.probaResidual = probaResidual;
	}

	public String getNumConsecInherente() {
		return numConsecInherente;
	}

	public void setNumConsecInherente(String numConsecInherente) {
		this.numConsecInherente = numConsecInherente;
	}

	public String getConsecInherente() {
		return consecInherente;
	}

	public void setConsecInherente(String consecInherente) {
		this.consecInherente = consecInherente;
	}

	public String getNumConsecResidual() {
		return numConsecResidual;
	}

	public void setNumConsecResidual(String numConsecResidual) {
		this.numConsecResidual = numConsecResidual;
	}

	public String getConsecResidual() {
		return consecResidual;
	}

	public void setConsecResidual(String consecResidual) {
		this.consecResidual = consecResidual;
	}

	public String getResumenAcci() {
		return resumenAcci;
	}

	public void setResumenAcci(String resumenAcci) {
		this.resumenAcci = resumenAcci;
	}

	public String getNombreAcci() {
		return nombreAcci;
	}

	public void setNombreAcci(String nombreAcci) {
		this.nombreAcci = nombreAcci;
	}

	public BigDecimal getRiesgoInherente() {
		return riesgoInherente;
	}

	public void setRiesgoInherente(BigDecimal riesgoInherente) {
		this.riesgoInherente = riesgoInherente;
	}

	public BigDecimal getRiesgoResidual() {
		return riesgoResidual;
	}

	public void setRiesgoResidual(BigDecimal riesgoResidual) {
		this.riesgoResidual = riesgoResidual;
	}

	public BigDecimal getMontoPerdida() {
		return montoPerdida;
	}

	public void setMontoPerdida(BigDecimal montoPerdida) {
		this.montoPerdida = montoPerdida;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getFinAcci() {
		return finAcci;
	}

	public void setFinAcci(int finAcci) {
		this.finAcci = finAcci;
	}

	public int getAprobAcci() {
		return aprobAcci;
	}

	public void setAprobAcci(int aprobAcci) {
		this.aprobAcci = aprobAcci;
	}

}
