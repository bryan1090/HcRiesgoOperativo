package com.hc.ro.modelo;

import java.io.Serializable;
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
@Table(name = "ro_detalle_accion")
@NamedQuery(name = "RoDetalleAccion.findAll", query = "SELECT r FROM RoDetalleAccion r")
public class RoDetalleAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_DEAC")
	private int codigoDeac;

	@ManyToOne
	@JoinColumn(name = "CODIGO_ACCI")
	private RoAccion roAccion;

	@ManyToOne
	@JoinColumn(name = "CODIGO_RESP")
	private RoResponsable roResponsable;

	@ManyToOne
	@JoinColumn(name = "CODIGO_SUPE")
	private RoResponsable roResponsableSupervisor;

	@ManyToOne
	@JoinColumn(name = "CODIGO_CUMP")
	private RoCumplimiento roCumplimiento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO_DEAC")
	private Date fechaInicioDeac;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN_DEAC")
	private Date fechaFinDeac;

	@Column(name = "RECURSOS_DEAC")
	private String recursosDeac;

	@Column(name = "ENTREGABLE_DEAC")
	private String entregableDeac;

	@Column(name = "NOMBRE_DEAC")
	private String nombreDeac;

	@Column(name = "FIN_DEAC")
	private int finDeac;

	@Column(name = "APROB_DEAC")
	private int aprobDeac;
	
	@Column(name = "GEST_ACCI")
	private int gestAcci;
	
	
	@Transient
	private String observaciones;

	public RoAccion getRoAccion() {
		return roAccion;
	}

	public void setRoAccion(RoAccion roAccion) {
		this.roAccion = roAccion;
	}

	public RoResponsable getRoResponsable() {
		return roResponsable;
	}

	public void setRoResponsable(RoResponsable roResponsable) {
		this.roResponsable = roResponsable;
	}

	public RoResponsable getRoResponsableSupervisor() {
		return roResponsableSupervisor;
	}

	public void setRoResponsableSupervisor(RoResponsable roResponsableSupervisor) {
		this.roResponsableSupervisor = roResponsableSupervisor;
	}

	public RoCumplimiento getRoCumplimiento() {
		return roCumplimiento;
	}

	public void setRoCumplimiento(RoCumplimiento roCumplimiento) {
		this.roCumplimiento = roCumplimiento;
	}

	public Date getFechaInicioDeac() {
		return fechaInicioDeac;
	}

	public void setFechaInicioDeac(Date fechaInicioDeac) {
		this.fechaInicioDeac = fechaInicioDeac;
	}

	public Date getFechaFinDeac() {
		return fechaFinDeac;
	}

	public void setFechaFinDeac(Date fechaFinDeac) {
		this.fechaFinDeac = fechaFinDeac;
	}

	public String getRecursosDeac() {
		return recursosDeac;
	}

	public void setRecursosDeac(String recursosDeac) {
		this.recursosDeac = recursosDeac;
	}

	public String getEntregableDeac() {
		return entregableDeac;
	}

	public void setEntregableDeac(String entregableDeac) {
		this.entregableDeac = entregableDeac;
	}

	public String getNombreDeac() {
		return nombreDeac;
	}

	public void setNombreDeac(String nombreDeac) {
		this.nombreDeac = nombreDeac;
	}

	public int getCodigoDeac() {
		return codigoDeac;
	}

	public void setCodigoDeac(int codigoDeac) {
		this.codigoDeac = codigoDeac;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getFinDeac() {
		return finDeac;
	}

	public void setFinDeac(int finDeac) {
		this.finDeac = finDeac;
	}

	public int getAprobDeac() {
		return aprobDeac;
	}

	public void setAprobDeac(int aprobDeac) {
		this.aprobDeac = aprobDeac;
	}

	public int getGestAcci() {
		return gestAcci;
	}

	public void setGestAcci(int gestAcci) {
		this.gestAcci = gestAcci;
	}



}
