package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the gen_estado database table.
 * 
 */
@Entity
@Table(name = "gen_estado")
@NamedQuery(name = "GenEstado.findAll", query = "SELECT g FROM GenEstado g")
public class GenEstado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_ESTA")
	private int codigoEsta;

	@Column(name = "NOMBRE_ESTA")
	private String nombreEsta;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to RoAgencia
	@OneToMany(mappedBy = "genEstado")
	private List<RoAgencia> roAgencias;

	// bi-directional many-to-one association to RoCatObjetivo
	@OneToMany(mappedBy = "genEstado")
	private List<RoCatObjetivo> roCatObjetivos;

	// bi-directional many-to-one association to RoDepartamento
	@OneToMany(mappedBy = "genEstado")
	private List<RoDepartamento> roDepartamentos;

	// bi-directional many-to-one association to RoEvento
	@OneToMany(mappedBy = "genEstado")
	private List<RoEvento> roEventos;

	// bi-directional many-to-one association to RoFactorRiesgo
	@OneToMany(mappedBy = "genEstado")
	private List<RoFactorRiesgo> roFactorRiesgos;

	// bi-directional many-to-one association to RoIndicaRsClave
	@OneToMany(mappedBy = "genEstado")
	private List<RoIndicaRsClave> roIndicaRsClaves;

	// bi-directional many-to-one association to RoNegocio
	@OneToMany(mappedBy = "genEstado")
	private List<RoNegocio> roNegocios;

	// bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy = "genEstado")
	private List<RoProceso> roProcesos;

	// bi-directional many-to-one association to RoResponsable
	@OneToMany(mappedBy = "genEstado")
	private List<RoResponsable> roResponsables;

	// bi-directional many-to-one association to RoTipoAfecta
	@OneToMany(mappedBy = "genEstado")
	private List<RoTipoAfecta> roTipoAfectas;

	// bi-directional many-to-one association to RoTipoCosto
	@OneToMany(mappedBy = "genEstado")
	private List<RoTipoCosto> roTipoCostos;

	// bi-directional many-to-one association to RoTipoCosto
	@OneToMany(mappedBy = "genEstado")
	private List<RoTipoPerdida> roTipoPerdidas;

	// bi-directional many-to-one association to RoTipoRecupera
	@OneToMany(mappedBy = "genEstado")
	private List<RoTipoRecupera> roTipoRecuperas;

	public GenEstado() {
	}

	public int getCodigoEsta() {
		return this.codigoEsta;
	}

	public void setCodigoEsta(int codigoEsta) {
		this.codigoEsta = codigoEsta;
	}

	public String getNombreEsta() {
		return this.nombreEsta;
	}

	public void setNombreEsta(String nombreEsta) {
		this.nombreEsta = nombreEsta;
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

	public List<RoAgencia> getRoAgencias() {
		return this.roAgencias;
	}

	public void setRoAgencias(List<RoAgencia> roAgencias) {
		this.roAgencias = roAgencias;
	}

	public RoAgencia addRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().add(roAgencia);
		roAgencia.setGenEstado(this);

		return roAgencia;
	}

	public RoAgencia removeRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().remove(roAgencia);
		roAgencia.setGenEstado(null);

		return roAgencia;
	}

	public List<RoCatObjetivo> getRoCatObjetivos() {
		return this.roCatObjetivos;
	}

	public void setRoCatObjetivos(List<RoCatObjetivo> roCatObjetivos) {
		this.roCatObjetivos = roCatObjetivos;
	}

	public RoCatObjetivo addRoCatObjetivo(RoCatObjetivo roCatObjetivo) {
		getRoCatObjetivos().add(roCatObjetivo);
		roCatObjetivo.setGenEstado(this);

		return roCatObjetivo;
	}

	public RoCatObjetivo removeRoCatObjetivo(RoCatObjetivo roCatObjetivo) {
		getRoCatObjetivos().remove(roCatObjetivo);
		roCatObjetivo.setGenEstado(null);

		return roCatObjetivo;
	}

	public List<RoDepartamento> getRoDepartamentos() {
		return this.roDepartamentos;
	}

	public void setRoDepartamentos(List<RoDepartamento> roDepartamentos) {
		this.roDepartamentos = roDepartamentos;
	}

	public RoDepartamento addRoDepartamento(RoDepartamento roDepartamento) {
		getRoDepartamentos().add(roDepartamento);
		roDepartamento.setGenEstado(this);

		return roDepartamento;
	}

	public RoDepartamento removeRoDepartamento(RoDepartamento roDepartamento) {
		getRoDepartamentos().remove(roDepartamento);
		roDepartamento.setGenEstado(null);

		return roDepartamento;
	}

	public List<RoEvento> getRoEventos() {
		return this.roEventos;
	}

	public void setRoEventos(List<RoEvento> roEventos) {
		this.roEventos = roEventos;
	}

	public RoEvento addRoEvento(RoEvento roEvento) {
		getRoEventos().add(roEvento);
		roEvento.setGenEstado(this);

		return roEvento;
	}

	public RoEvento removeRoEvento(RoEvento roEvento) {
		getRoEventos().remove(roEvento);
		roEvento.setGenEstado(null);

		return roEvento;
	}

	public List<RoFactorRiesgo> getRoFactorRiesgos() {
		return this.roFactorRiesgos;
	}

	public void setRoFactorRiesgos(List<RoFactorRiesgo> roFactorRiesgos) {
		this.roFactorRiesgos = roFactorRiesgos;
	}

	public RoFactorRiesgo addRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		getRoFactorRiesgos().add(roFactorRiesgo);
		roFactorRiesgo.setGenEstado(this);

		return roFactorRiesgo;
	}

	public RoFactorRiesgo removeRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		getRoFactorRiesgos().remove(roFactorRiesgo);
		roFactorRiesgo.setGenEstado(null);

		return roFactorRiesgo;
	}

	public List<RoIndicaRsClave> getRoIndicaRsClaves() {
		return this.roIndicaRsClaves;
	}

	public void setRoIndicaRsClaves(List<RoIndicaRsClave> roIndicaRsClaves) {
		this.roIndicaRsClaves = roIndicaRsClaves;
	}

	public RoIndicaRsClave addRoIndicaRsClave(RoIndicaRsClave roIndicaRsClave) {
		getRoIndicaRsClaves().add(roIndicaRsClave);
		roIndicaRsClave.setGenEstado(this);

		return roIndicaRsClave;
	}

	public RoIndicaRsClave removeRoIndicaRsClave(RoIndicaRsClave roIndicaRsClave) {
		getRoIndicaRsClaves().remove(roIndicaRsClave);
		roIndicaRsClave.setGenEstado(null);

		return roIndicaRsClave;
	}

	public List<RoNegocio> getRoNegocios() {
		return this.roNegocios;
	}

	public void setRoNegocios(List<RoNegocio> roNegocios) {
		this.roNegocios = roNegocios;
	}

	public RoNegocio addRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().add(roNegocio);
		roNegocio.setGenEstado(this);

		return roNegocio;
	}

	public RoNegocio removeRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().remove(roNegocio);
		roNegocio.setGenEstado(null);

		return roNegocio;
	}

	public List<RoProceso> getRoProcesos() {
		return this.roProcesos;
	}

	public void setRoProcesos(List<RoProceso> roProcesos) {
		this.roProcesos = roProcesos;
	}

	public RoProceso addRoProceso(RoProceso roProceso) {
		getRoProcesos().add(roProceso);
		roProceso.setGenEstado(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setGenEstado(null);

		return roProceso;
	}

	public List<RoResponsable> getRoResponsables() {
		return this.roResponsables;
	}

	public void setRoResponsables(List<RoResponsable> roResponsables) {
		this.roResponsables = roResponsables;
	}

	public RoResponsable addRoResponsable(RoResponsable roResponsable) {
		getRoResponsables().add(roResponsable);
		roResponsable.setGenEstado(this);

		return roResponsable;
	}

	public RoResponsable removeRoResponsable(RoResponsable roResponsable) {
		getRoResponsables().remove(roResponsable);
		roResponsable.setGenEstado(null);

		return roResponsable;
	}

	public List<RoTipoAfecta> getRoTipoAfectas() {
		return this.roTipoAfectas;
	}

	public void setRoTipoAfectas(List<RoTipoAfecta> roTipoAfectas) {
		this.roTipoAfectas = roTipoAfectas;
	}

	public RoTipoAfecta addRoTipoAfecta(RoTipoAfecta roTipoAfecta) {
		getRoTipoAfectas().add(roTipoAfecta);
		roTipoAfecta.setGenEstado(this);

		return roTipoAfecta;
	}

	public RoTipoAfecta removeRoTipoAfecta(RoTipoAfecta roTipoAfecta) {
		getRoTipoAfectas().remove(roTipoAfecta);
		roTipoAfecta.setGenEstado(null);

		return roTipoAfecta;
	}

	public List<RoTipoCosto> getRoTipoCostos() {
		return this.roTipoCostos;
	}

	public void setRoTipoCostos(List<RoTipoCosto> roTipoCostos) {
		this.roTipoCostos = roTipoCostos;
	}

	public RoTipoCosto addRoTipoCosto(RoTipoCosto roTipoCosto) {
		getRoTipoCostos().add(roTipoCosto);
		roTipoCosto.setGenEstado(this);

		return roTipoCosto;
	}

	public RoTipoCosto removeRoTipoCosto(RoTipoCosto roTipoCosto) {
		getRoTipoCostos().remove(roTipoCosto);
		roTipoCosto.setGenEstado(null);

		return roTipoCosto;
	}

	public List<RoTipoRecupera> getRoTipoRecuperas() {
		return this.roTipoRecuperas;
	}

	public void setRoTipoRecuperas(List<RoTipoRecupera> roTipoRecuperas) {
		this.roTipoRecuperas = roTipoRecuperas;
	}

	public RoTipoRecupera addRoTipoRecupera(RoTipoRecupera roTipoRecupera) {
		getRoTipoRecuperas().add(roTipoRecupera);
		roTipoRecupera.setGenEstado(this);

		return roTipoRecupera;
	}

	public RoTipoRecupera removeRoTipoRecupera(RoTipoRecupera roTipoRecupera) {
		getRoTipoRecuperas().remove(roTipoRecupera);
		roTipoRecupera.setGenEstado(null);

		return roTipoRecupera;
	}

}