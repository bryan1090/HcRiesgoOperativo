package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the RO_CONTROLES database table.
 * 
 */
@Entity
@Table(name = "RO_CONTROLES")
@NamedQuery(name = "RoControles.findAll", query = "SELECT r FROM RoControles r")
public class RoControles implements Serializable {

	
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODIGO_CONTROLES")
	private BigDecimal codigoControles;

	@Column(name = "CALC_EFECT")
	private BigDecimal calcEfect;

	@Column(name = "CALC_ESTA_DOCU")
	private BigDecimal calcEstaDocu;

	@Column(name = "CALC_ESTA_FORMAL")
	private BigDecimal calcEstaFormal;

	@Column(name = "CALC_FORM")
	private BigDecimal calcForm;

	@Column(name = "CALC_IMPACT")
	private BigDecimal calcImpact;

	@Column(name = "CALC_PERIO")
	private BigDecimal calcPerio;

	@Column(name = "CALC_SE_APLICA")
	private BigDecimal calcSeAplica;

	@Column(name = "CALC_TIPO")
	private BigDecimal calcTipo;

	private String categorizacion;

	@Column(name = "CLASIFI_FINAL")
	private String clasifiFinal;

	@Column(name = "CONTROL_ACT")
	private String controlAct;

	@Column(name = "DENOM_CONTROL")
	private String denomControl;

	@Column(name = "ESTA_DOC")
	private String estaDoc;

	@Column(name = "ESTA_FOR")
	private String estaFor;

	@Column(name = "EFECTIVIDAD")
	private String efectividad;

	private String forma;

	private BigDecimal impactoeco;

	@Column(name = "IMPACT_ECON")
	private BigDecimal impactEcon;

	private String periodicidad;

	private BigDecimal producto;

	@Column(name = "PROMEDIO_CONTROL")
	private BigDecimal promedioControl;


	
	@Column(name = "CAL_REP")
	private BigDecimal calRep;

	@Column(name = "CAL_BCP")
	private BigDecimal calBcp;

	@Column(name = "CAL_LEG")
	private BigDecimal calLeg;

	// private String rep;
	
	// bi-directional many-to-one association to RoResponsable
	@ManyToOne
	@JoinColumn(name = "REP")
	private RoControlParamImpRep paramImpRep;

	private String bcp;

	private String leg;

	@Column(name = "RIESGO_RESI")
	private BigDecimal riesgoResi;
	
	@Column(name = "RIESGO_INHE")
	private BigDecimal riesgoInhe;

	@Column(name = "SE_APLICA")
	private String seAplica;

	private String tipo;

	@Column(name = "VALOR_CONTROL")
	private BigDecimal valorControl;

	@Column(name = "VALOR_EFIC")
	private BigDecimal valorEfic;

	// bi-directional many-to-one association to RoConsecuenciaImpacto
	@ManyToOne
	@JoinColumn(name = "CODIGO_CONS")
	private RoConsecuenciaImpacto roConsecuenciaImpacto;

	// bi-directional many-to-one association to RoDetalleEvento
	@ManyToOne
	@JoinColumn(name = "CODIGO_DEVE")
	private RoDetalleEvento roDetalleEvento;

	// bi-directional many-to-one association to ParamProbabilidadRiesgo
	@ManyToOne
	@JoinColumn(name = "CODIGO_PPRR")
	private ParamProbabilidadRiesgo paramProbabilidadRiesgo;

	// bi-directional many-to-one association to RoProbabilidadEvento
	@ManyToOne
	@JoinColumn(name = "CODIGO_PROB")
	private RoProbabilidadEvento roProbabilidadEvento;

	// bi-directional many-to-one association to RoResponsable
	@ManyToOne
	@JoinColumn(name = "CODIGO_RESP")
	private RoResponsable roResponsable;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public RoControles() {
	}

	public BigDecimal getCalcEfect() {
		return this.calcEfect;
	}

	public void setCalcEfect(BigDecimal calcEfect) {
		this.calcEfect = calcEfect;
	}

	public BigDecimal getCalcEstaDocu() {
		return this.calcEstaDocu;
	}

	public void setCalcEstaDocu(BigDecimal calcEstaDocu) {
		this.calcEstaDocu = calcEstaDocu;
	}

	public BigDecimal getCalcEstaFormal() {
		return this.calcEstaFormal;
	}

	public void setCalcEstaFormal(BigDecimal calcEstaFormal) {
		this.calcEstaFormal = calcEstaFormal;
	}

	public BigDecimal getCalcForm() {
		return this.calcForm;
	}

	public void setCalcForm(BigDecimal calcForm) {
		this.calcForm = calcForm;
	}

	public BigDecimal getCalcImpact() {
		return this.calcImpact;
	}

	public void setCalcImpact(BigDecimal calcImpact) {
		this.calcImpact = calcImpact;
	}

	public BigDecimal getCalcPerio() {
		return this.calcPerio;
	}

	public void setCalcPerio(BigDecimal calcPerio) {
		this.calcPerio = calcPerio;
	}

	public BigDecimal getCalcSeAplica() {
		return this.calcSeAplica;
	}

	public void setCalcSeAplica(BigDecimal calcSeAplica) {
		this.calcSeAplica = calcSeAplica;
	}

	public BigDecimal getCalcTipo() {
		return this.calcTipo;
	}

	public void setCalcTipo(BigDecimal calcTipo) {
		this.calcTipo = calcTipo;
	}

	public String getCategorizacion() {
		return this.categorizacion;
	}

	public void setCategorizacion(String categorizacion) {
		this.categorizacion = categorizacion;
	}

	public String getClasifiFinal() {
		return this.clasifiFinal;
	}

	public void setClasifiFinal(String clasifiFinal) {
		this.clasifiFinal = clasifiFinal;
	}

	public BigDecimal getCodigoControles() {
		return this.codigoControles;
	}

	public void setCodigoControles(BigDecimal codigoControles) {
		this.codigoControles = codigoControles;
	}

	public String getControlAct() {
		return this.controlAct;
	}

	public void setControlAct(String controlAct) {
		this.controlAct = controlAct;
	}

	public String getDenomControl() {
		return this.denomControl;
	}

	public void setDenomControl(String denomControl) {
		this.denomControl = denomControl;
	}

	public String getEstaDoc() {
		return this.estaDoc;
	}

	public void setEstaDoc(String estaDoc) {
		this.estaDoc = estaDoc;
	}

	public String getEstaFor() {
		return this.estaFor;
	}

	public void setEstaFor(String estaFor) {
		this.estaFor = estaFor;
	}

	public String getForma() {
		return this.forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public BigDecimal getImpactEcon() {
		return this.impactEcon;
	}

	public void setImpactEcon(BigDecimal impactEcon) {
		this.impactEcon = impactEcon;
	}

	public String getPeriodicidad() {
		return this.periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public BigDecimal getProducto() {
		return this.producto;
	}

	public void setProducto(BigDecimal producto) {
		this.producto = producto;
	}

	public BigDecimal getPromedioControl() {
		return this.promedioControl;
	}

	public void setPromedioControl(BigDecimal promedioControl) {
		this.promedioControl = promedioControl;
	}

	public String getSeAplica() {
		return this.seAplica;
	}

	public void setSeAplica(String seAplica) {
		this.seAplica = seAplica;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValorControl() {
		return this.valorControl;
	}

	public void setValorControl(BigDecimal valorControl) {
		this.valorControl = valorControl;
	}

	public BigDecimal getValorEfic() {
		return this.valorEfic;
	}

	public void setValorEfic(BigDecimal valorEfic) {
		this.valorEfic = valorEfic;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgo() {
		return this.paramProbabilidadRiesgo;
	}

	public void setParamProbabilidadRiesgo(
			ParamProbabilidadRiesgo paramProbabilidadRiesgo) {
		this.paramProbabilidadRiesgo = paramProbabilidadRiesgo;
	}

	public RoProbabilidadEvento getRoProbabilidadEvento() {
		return this.roProbabilidadEvento;
	}

	public void setRoProbabilidadEvento(
			RoProbabilidadEvento roProbabilidadEvento) {
		this.roProbabilidadEvento = roProbabilidadEvento;
	}

	public RoResponsable getRoResponsable() {
		return this.roResponsable;
	}

	public void setRoResponsable(RoResponsable roResponsable) {
		this.roResponsable = roResponsable;
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

	public String getEfectividad() {
		return efectividad;
	}

	public void setEfectividad(String efectividad) {
		this.efectividad = efectividad;
	}

	public BigDecimal getRiesgoResi() {
		return riesgoResi;
	}

	public void setRiesgoResi(BigDecimal riesgoResi) {
		this.riesgoResi = riesgoResi;
	}

	public RoDetalleEvento getRoDetalleEvento() {
		return roDetalleEvento;
	}

	public void setRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		this.roDetalleEvento = roDetalleEvento;
	}

	public BigDecimal getCalRep() {
		return calRep;
	}

	public void setCalRep(BigDecimal calRep) {
		this.calRep = calRep;
	}

	public BigDecimal getCalBcp() {
		return calBcp;
	}

	public void setCalBcp(BigDecimal calBcp) {
		this.calBcp = calBcp;
	}

	public BigDecimal getCalLeg() {
		return calLeg;
	}

	public void setCalLeg(BigDecimal calLeg) {
		this.calLeg = calLeg;
	}

//	public String getRep() {
//		return rep;
//	}
//
//	public void setRep(String rep) {
//		this.rep = rep;
//	}

	public String getBcp() {
		return bcp;
	}

	public void setBcp(String bcp) {
		this.bcp = bcp;
	}

	public String getLeg() {
		return leg;
	}

	public void setLeg(String leg) {
		this.leg = leg;
	}

	public RoConsecuenciaImpacto getRoConsecuenciaImpacto() {
		return roConsecuenciaImpacto;
	}

	public void setRoConsecuenciaImpacto(
			RoConsecuenciaImpacto roConsecuenciaImpacto) {
		this.roConsecuenciaImpacto = roConsecuenciaImpacto;
	}

	public BigDecimal getImpactoeco() {
		return impactoeco;
	}

	public void setImpactoeco(BigDecimal impactoeco) {
		this.impactoeco = impactoeco;
	}

	public RoControlParamImpRep getParamImpRep() {
		return paramImpRep;
	}

	public void setParamImpRep(RoControlParamImpRep paramImpRep) {
		this.paramImpRep = paramImpRep;
	}

	public BigDecimal getRiesgoInhe() {
		return riesgoInhe;
	}

	public void setRiesgoInhe(BigDecimal riesgoInhe) {
		this.riesgoInhe = riesgoInhe;
	}

	



	
}