package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the sis_sucursal database table.
 * 
 */
@Entity
@Table(name = "sis_sucursal")
@NamedQuery(name = "SisSucursal.findAll", query = "SELECT s FROM SisSucursal s")
public class SisSucursal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_SUCU")
	private int codigoSucu;

	@Column(name = "DIRECCION_SUCU")
	private String direccionSucu;

	@Column(name = "MAPEOSUCU_SUCU")
	private BigDecimal mapeosucuSucu;

	@Column(name = "NOMBRE_SUCU")
	private String nombreSucu;

	@Column(name = "TELEFONO_SUCU")
	private String telefonoSucu;

	// bi-directional many-to-one association to GenDetallePeriodo
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenDetallePeriodo> genDetallePeriodos;

	// bi-directional many-to-one association to GenEstado
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenEstado> genEstados;

	// bi-directional many-to-one association to GenMoneda
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenMoneda> genMonedas;

	// bi-directional many-to-one association to GenNivelArbol
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenNivelArbol> genNivelArbols;

	// bi-directional many-to-one association to GenPeriodo
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenPeriodo> genPeriodos;

	// bi-directional many-to-one association to GenSemana
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenSemana> genSemanas;

	// bi-directional many-to-one association to GenTipoIdentific
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenTipoIdentific> genTipoIdentifics;

	// bi-directional many-to-one association to GenTipoUbiGeog
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenTipoUbiGeog> genTipoUbiGeogs;

	// bi-directional many-to-one association to GenUbicGeo
	@OneToMany(mappedBy = "sisSucursal")
	private List<GenUbicGeo> genUbicGeos;

	// bi-directional many-to-one association to RoAgencia
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoAgencia> roAgencias;

	// bi-directional many-to-one association to RoControle
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoControles> roControles;

	// bi-directional many-to-one association to RoCalifRiesgo
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoCalifRiesgo> roCalifRiesgos;

	// bi-directional many-to-one association to RoCatFrecuencia
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoCatFrecuencia> roCatFrecuencias;

	// bi-directional many-to-one association to RoCatObjetivo
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoCatObjetivo> roCatObjetivos;

	// bi-directional many-to-one association to RoCatSeveridad
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoCatSeveridad> roCatSeveridads;

	// bi-directional many-to-one association to RoCriticidad
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoCriticidad> roCriticidads;

	// bi-directional many-to-one association to RoDepartamento
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoDepartamento> roDepartamentos;

	// bi-directional many-to-one association to RoDeptAgencia
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoDeptAgencia> roDeptAgencias;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoDetalleEvento> roDetalleEventos;

	// bi-directional many-to-one association to RoDetalleNegocio
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoDetalleNegocio> roDetalleNegocios;

	// bi-directional many-to-one association to RoDetalleProceso
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoDetalleProceso> roDetalleProcesos;

	// bi-directional many-to-one association to RoEvento
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoEvento> roEventos;

	// bi-directional many-to-one association to RoEventoAfecta
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoEventoAfecta> roEventoAfectas;

	// bi-directional many-to-one association to RoEventoCosto
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoEventoCosto> roEventoCostos;

	// bi-directional many-to-one association to RoEventoObjetivo
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoEventoObjetivo> roEventoObjetivos;

	// bi-directional many-to-one association to RoEventoRecupera
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoEventoRecupera> roEventoRecuperas;

	// bi-directional many-to-one association to RoFactorRiesgo
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoFactorRiesgo> roFactorRiesgos;

	// bi-directional many-to-one association to RoFrecEjecucion
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoFrecEjecucion> roFrecEjecucions;

	// bi-directional many-to-one association to RoIndicaRsClave
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoIndicaRsClave> roIndicaRsClaves;

	// bi-directional many-to-one association to RoNegocio
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoNegocio> roNegocios;

	// bi-directional many-to-one association to RoNivelRiesgo
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoNivelRiesgo> roNivelRiesgos;

	// bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoProceso> roProcesos;

	// bi-directional many-to-one association to RoResponsable
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoResponsable> roResponsables;

	// bi-directional many-to-one association to RoTipoAfecta
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoAfecta> roTipoAfectas;

	// bi-directional many-to-one association to RoTipoAgencia
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoAgencia> roTipoAgencias;

	// bi-directional many-to-one association to RoTipoCosto
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoCosto> roTipoCostos;

	// bi-directional many-to-one association to RoTipoDetalle
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoDetalle> roTipoDetalles;

	// bi-directional many-to-one association to RoTipoEjecucion
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoEjecucion> roTipoEjecucions;

	// bi-directional many-to-one association to RoTipoNegocio
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoNegocio> roTipoNegocios;

	// bi-directional many-to-one association to RoTipoProceso
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoProceso> roTipoProcesos;

	// bi-directional many-to-one association to RoTipoRecupera
	@OneToMany(mappedBy = "sisSucursal")
	private List<RoTipoRecupera> roTipoRecuperas;

	// bi-directional many-to-one association to SisAlertaUsuario
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisAlertaUsuario> sisAlertaUsuarios;

	// bi-directional many-to-one association to SisAuditoria
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisAuditoria> sisAuditorias;

	// bi-directional many-to-one association to SisControlProceso
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisControlProceso> sisControlProcesos;

	// bi-directional many-to-one association to SisDetalleMenu
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisDetalleMenu> sisDetalleMenus;

	// bi-directional many-to-one association to SisDetalleProceso
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisDetalleProceso> sisDetalleProcesos;

	// bi-directional many-to-one association to SisDetalleReporte1
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisDetalleReporte1> sisDetalleReporte1s;

	// bi-directional many-to-one association to SisMensaje
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisMensaje> sisMensajes;

	// bi-directional many-to-one association to SisMenu
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisMenu> sisMenus;

	// bi-directional many-to-one association to SisModulo
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisModulo> sisModulos;

	// bi-directional many-to-one association to SisObjeto
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisObjeto> sisObjetos;

	// bi-directional many-to-one association to SisParametro
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisParametro> sisParametros;

	// bi-directional many-to-one association to SisPerfil
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisPerfil> sisPerfils;

	// bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisPermiso> sisPermisos;

	// bi-directional many-to-one association to SisPermisoValor
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisPermisoValor> sisPermisoValors;

	// bi-directional many-to-one association to SisProcedimiento
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisProcedimiento> sisProcedimientos;

	// bi-directional many-to-one association to SisReporte
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisReporte> sisReportes;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to GenUbicGeo
	@ManyToOne
	@JoinColumn(name = "CODIGO_UBGE")
	private GenUbicGeo genUbicGeo;

	// bi-directional many-to-one association to SisTipoObjeto
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisTipoObjeto> sisTipoObjetos;

	// bi-directional many-to-one association to SisUsuario
	@OneToMany(mappedBy = "sisSucursal")
	private List<SisUsuario> sisUsuarios;

	public SisSucursal() {
	}

	public int getCodigoSucu() {
		return this.codigoSucu;
	}

	public void setCodigoSucu(int codigoSucu) {
		this.codigoSucu = codigoSucu;
	}

	public String getDireccionSucu() {
		return this.direccionSucu;
	}

	public void setDireccionSucu(String direccionSucu) {
		this.direccionSucu = direccionSucu;
	}

	public BigDecimal getMapeosucuSucu() {
		return this.mapeosucuSucu;
	}

	public void setMapeosucuSucu(BigDecimal mapeosucuSucu) {
		this.mapeosucuSucu = mapeosucuSucu;
	}

	public String getNombreSucu() {
		return this.nombreSucu;
	}

	public void setNombreSucu(String nombreSucu) {
		this.nombreSucu = nombreSucu;
	}

	public String getTelefonoSucu() {
		return this.telefonoSucu;
	}

	public void setTelefonoSucu(String telefonoSucu) {
		this.telefonoSucu = telefonoSucu;
	}

	public List<GenDetallePeriodo> getGenDetallePeriodos() {
		return this.genDetallePeriodos;
	}

	public void setGenDetallePeriodos(List<GenDetallePeriodo> genDetallePeriodos) {
		this.genDetallePeriodos = genDetallePeriodos;
	}

	public GenDetallePeriodo addGenDetallePeriodo(
			GenDetallePeriodo genDetallePeriodo) {
		getGenDetallePeriodos().add(genDetallePeriodo);
		genDetallePeriodo.setSisSucursal(this);

		return genDetallePeriodo;
	}

	public GenDetallePeriodo removeGenDetallePeriodo(
			GenDetallePeriodo genDetallePeriodo) {
		getGenDetallePeriodos().remove(genDetallePeriodo);
		genDetallePeriodo.setSisSucursal(null);

		return genDetallePeriodo;
	}

	public List<GenEstado> getGenEstados() {
		return this.genEstados;
	}

	public void setGenEstados(List<GenEstado> genEstados) {
		this.genEstados = genEstados;
	}

	public GenEstado addGenEstado(GenEstado genEstado) {
		getGenEstados().add(genEstado);
		genEstado.setSisSucursal(this);

		return genEstado;
	}

	public GenEstado removeGenEstado(GenEstado genEstado) {
		getGenEstados().remove(genEstado);
		genEstado.setSisSucursal(null);

		return genEstado;
	}

	public List<GenMoneda> getGenMonedas() {
		return this.genMonedas;
	}

	public void setGenMonedas(List<GenMoneda> genMonedas) {
		this.genMonedas = genMonedas;
	}

	public GenMoneda addGenMoneda(GenMoneda genMoneda) {
		getGenMonedas().add(genMoneda);
		genMoneda.setSisSucursal(this);

		return genMoneda;
	}

	public GenMoneda removeGenMoneda(GenMoneda genMoneda) {
		getGenMonedas().remove(genMoneda);
		genMoneda.setSisSucursal(null);

		return genMoneda;
	}

	public List<GenNivelArbol> getGenNivelArbols() {
		return this.genNivelArbols;
	}

	public void setGenNivelArbols(List<GenNivelArbol> genNivelArbols) {
		this.genNivelArbols = genNivelArbols;
	}

	public GenNivelArbol addGenNivelArbol(GenNivelArbol genNivelArbol) {
		getGenNivelArbols().add(genNivelArbol);
		genNivelArbol.setSisSucursal(this);

		return genNivelArbol;
	}

	public GenNivelArbol removeGenNivelArbol(GenNivelArbol genNivelArbol) {
		getGenNivelArbols().remove(genNivelArbol);
		genNivelArbol.setSisSucursal(null);

		return genNivelArbol;
	}

	public List<GenPeriodo> getGenPeriodos() {
		return this.genPeriodos;
	}

	public void setGenPeriodos(List<GenPeriodo> genPeriodos) {
		this.genPeriodos = genPeriodos;
	}

	public GenPeriodo addGenPeriodo(GenPeriodo genPeriodo) {
		getGenPeriodos().add(genPeriodo);
		genPeriodo.setSisSucursal(this);

		return genPeriodo;
	}

	public GenPeriodo removeGenPeriodo(GenPeriodo genPeriodo) {
		getGenPeriodos().remove(genPeriodo);
		genPeriodo.setSisSucursal(null);

		return genPeriodo;
	}

	public List<GenSemana> getGenSemanas() {
		return this.genSemanas;
	}

	public void setGenSemanas(List<GenSemana> genSemanas) {
		this.genSemanas = genSemanas;
	}

	public GenSemana addGenSemana(GenSemana genSemana) {
		getGenSemanas().add(genSemana);
		genSemana.setSisSucursal(this);

		return genSemana;
	}

	public GenSemana removeGenSemana(GenSemana genSemana) {
		getGenSemanas().remove(genSemana);
		genSemana.setSisSucursal(null);

		return genSemana;
	}

	public List<GenTipoIdentific> getGenTipoIdentifics() {
		return this.genTipoIdentifics;
	}

	public void setGenTipoIdentifics(List<GenTipoIdentific> genTipoIdentifics) {
		this.genTipoIdentifics = genTipoIdentifics;
	}

	public GenTipoIdentific addGenTipoIdentific(
			GenTipoIdentific genTipoIdentific) {
		getGenTipoIdentifics().add(genTipoIdentific);
		genTipoIdentific.setSisSucursal(this);

		return genTipoIdentific;
	}

	public GenTipoIdentific removeGenTipoIdentific(
			GenTipoIdentific genTipoIdentific) {
		getGenTipoIdentifics().remove(genTipoIdentific);
		genTipoIdentific.setSisSucursal(null);

		return genTipoIdentific;
	}

	public List<GenTipoUbiGeog> getGenTipoUbiGeogs() {
		return this.genTipoUbiGeogs;
	}

	public void setGenTipoUbiGeogs(List<GenTipoUbiGeog> genTipoUbiGeogs) {
		this.genTipoUbiGeogs = genTipoUbiGeogs;
	}

	public GenTipoUbiGeog addGenTipoUbiGeog(GenTipoUbiGeog genTipoUbiGeog) {
		getGenTipoUbiGeogs().add(genTipoUbiGeog);
		genTipoUbiGeog.setSisSucursal(this);

		return genTipoUbiGeog;
	}

	public GenTipoUbiGeog removeGenTipoUbiGeog(GenTipoUbiGeog genTipoUbiGeog) {
		getGenTipoUbiGeogs().remove(genTipoUbiGeog);
		genTipoUbiGeog.setSisSucursal(null);

		return genTipoUbiGeog;
	}

	public List<GenUbicGeo> getGenUbicGeos() {
		return this.genUbicGeos;
	}

	public void setGenUbicGeos(List<GenUbicGeo> genUbicGeos) {
		this.genUbicGeos = genUbicGeos;
	}

	public GenUbicGeo addGenUbicGeo(GenUbicGeo genUbicGeo) {
		getGenUbicGeos().add(genUbicGeo);
		genUbicGeo.setSisSucursal(this);

		return genUbicGeo;
	}

	public GenUbicGeo removeGenUbicGeo(GenUbicGeo genUbicGeo) {
		getGenUbicGeos().remove(genUbicGeo);
		genUbicGeo.setSisSucursal(null);

		return genUbicGeo;
	}

	public List<RoAgencia> getRoAgencias() {
		return this.roAgencias;
	}

	public void setRoAgencias(List<RoAgencia> roAgencias) {
		this.roAgencias = roAgencias;
	}

	public RoAgencia addRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().add(roAgencia);
		roAgencia.setSisSucursal(this);

		return roAgencia;
	}

	public RoAgencia removeRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().remove(roAgencia);
		roAgencia.setSisSucursal(null);

		return roAgencia;
	}

	public List<RoCalifRiesgo> getRoCalifRiesgos() {
		return this.roCalifRiesgos;
	}

	public void setRoCalifRiesgos(List<RoCalifRiesgo> roCalifRiesgos) {
		this.roCalifRiesgos = roCalifRiesgos;
	}

	public RoCalifRiesgo addRoCalifRiesgo(RoCalifRiesgo roCalifRiesgo) {
		getRoCalifRiesgos().add(roCalifRiesgo);
		roCalifRiesgo.setSisSucursal(this);

		return roCalifRiesgo;
	}

	public RoCalifRiesgo removeRoCalifRiesgo(RoCalifRiesgo roCalifRiesgo) {
		getRoCalifRiesgos().remove(roCalifRiesgo);
		roCalifRiesgo.setSisSucursal(null);

		return roCalifRiesgo;
	}

	public List<RoCatFrecuencia> getRoCatFrecuencias() {
		return this.roCatFrecuencias;
	}

	public void setRoCatFrecuencias(List<RoCatFrecuencia> roCatFrecuencias) {
		this.roCatFrecuencias = roCatFrecuencias;
	}

	public RoCatFrecuencia addRoCatFrecuencia(RoCatFrecuencia roCatFrecuencia) {
		getRoCatFrecuencias().add(roCatFrecuencia);
		roCatFrecuencia.setSisSucursal(this);

		return roCatFrecuencia;
	}

	public RoCatFrecuencia removeRoCatFrecuencia(RoCatFrecuencia roCatFrecuencia) {
		getRoCatFrecuencias().remove(roCatFrecuencia);
		roCatFrecuencia.setSisSucursal(null);

		return roCatFrecuencia;
	}

	public List<RoCatObjetivo> getRoCatObjetivos() {
		return this.roCatObjetivos;
	}

	public void setRoCatObjetivos(List<RoCatObjetivo> roCatObjetivos) {
		this.roCatObjetivos = roCatObjetivos;
	}

	public RoCatObjetivo addRoCatObjetivo(RoCatObjetivo roCatObjetivo) {
		getRoCatObjetivos().add(roCatObjetivo);
		roCatObjetivo.setSisSucursal(this);

		return roCatObjetivo;
	}

	public RoCatObjetivo removeRoCatObjetivo(RoCatObjetivo roCatObjetivo) {
		getRoCatObjetivos().remove(roCatObjetivo);
		roCatObjetivo.setSisSucursal(null);

		return roCatObjetivo;
	}

	public List<RoCatSeveridad> getRoCatSeveridads() {
		return this.roCatSeveridads;
	}

	public void setRoCatSeveridads(List<RoCatSeveridad> roCatSeveridads) {
		this.roCatSeveridads = roCatSeveridads;
	}

	public RoCatSeveridad addRoCatSeveridad(RoCatSeveridad roCatSeveridad) {
		getRoCatSeveridads().add(roCatSeveridad);
		roCatSeveridad.setSisSucursal(this);

		return roCatSeveridad;
	}

	public RoCatSeveridad removeRoCatSeveridad(RoCatSeveridad roCatSeveridad) {
		getRoCatSeveridads().remove(roCatSeveridad);
		roCatSeveridad.setSisSucursal(null);

		return roCatSeveridad;
	}

	public List<RoCriticidad> getRoCriticidads() {
		return this.roCriticidads;
	}

	public void setRoCriticidads(List<RoCriticidad> roCriticidads) {
		this.roCriticidads = roCriticidads;
	}

	public RoCriticidad addRoCriticidad(RoCriticidad roCriticidad) {
		getRoCriticidads().add(roCriticidad);
		roCriticidad.setSisSucursal(this);

		return roCriticidad;
	}

	public RoCriticidad removeRoCriticidad(RoCriticidad roCriticidad) {
		getRoCriticidads().remove(roCriticidad);
		roCriticidad.setSisSucursal(null);

		return roCriticidad;
	}

	public List<RoDepartamento> getRoDepartamentos() {
		return this.roDepartamentos;
	}

	public void setRoDepartamentos(List<RoDepartamento> roDepartamentos) {
		this.roDepartamentos = roDepartamentos;
	}

	public RoDepartamento addRoDepartamento(RoDepartamento roDepartamento) {
		getRoDepartamentos().add(roDepartamento);
		roDepartamento.setSisSucursal(this);

		return roDepartamento;
	}

	public RoDepartamento removeRoDepartamento(RoDepartamento roDepartamento) {
		getRoDepartamentos().remove(roDepartamento);
		roDepartamento.setSisSucursal(null);

		return roDepartamento;
	}

	public List<RoDeptAgencia> getRoDeptAgencias() {
		return this.roDeptAgencias;
	}

	public void setRoDeptAgencias(List<RoDeptAgencia> roDeptAgencias) {
		this.roDeptAgencias = roDeptAgencias;
	}

	public RoDeptAgencia addRoDeptAgencia(RoDeptAgencia roDeptAgencia) {
		getRoDeptAgencias().add(roDeptAgencia);
		roDeptAgencia.setSisSucursal(this);

		return roDeptAgencia;
	}

	public RoDeptAgencia removeRoDeptAgencia(RoDeptAgencia roDeptAgencia) {
		getRoDeptAgencias().remove(roDeptAgencia);
		roDeptAgencia.setSisSucursal(null);

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
		roDetalleEvento.setSisSucursal(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setSisSucursal(null);

		return roDetalleEvento;
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
		roDetalleNegocio.setSisSucursal(this);

		return roDetalleNegocio;
	}

	public RoDetalleNegocio removeRoDetalleNegocio(
			RoDetalleNegocio roDetalleNegocio) {
		getRoDetalleNegocios().remove(roDetalleNegocio);
		roDetalleNegocio.setSisSucursal(null);

		return roDetalleNegocio;
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
		roDetalleProceso.setSisSucursal(this);

		return roDetalleProceso;
	}

	public RoDetalleProceso removeRoDetalleProceso(
			RoDetalleProceso roDetalleProceso) {
		getRoDetalleProcesos().remove(roDetalleProceso);
		roDetalleProceso.setSisSucursal(null);

		return roDetalleProceso;
	}

	public List<RoEvento> getRoEventos() {
		return this.roEventos;
	}

	public void setRoEventos(List<RoEvento> roEventos) {
		this.roEventos = roEventos;
	}

	public RoEvento addRoEvento(RoEvento roEvento) {
		getRoEventos().add(roEvento);
		roEvento.setSisSucursal(this);

		return roEvento;
	}

	public RoEvento removeRoEvento(RoEvento roEvento) {
		getRoEventos().remove(roEvento);
		roEvento.setSisSucursal(null);

		return roEvento;
	}

	public List<RoEventoAfecta> getRoEventoAfectas() {
		return this.roEventoAfectas;
	}

	public void setRoEventoAfectas(List<RoEventoAfecta> roEventoAfectas) {
		this.roEventoAfectas = roEventoAfectas;
	}

	public RoEventoAfecta addRoEventoAfecta(RoEventoAfecta roEventoAfecta) {
		getRoEventoAfectas().add(roEventoAfecta);
		roEventoAfecta.setSisSucursal(this);

		return roEventoAfecta;
	}

	public RoEventoAfecta removeRoEventoAfecta(RoEventoAfecta roEventoAfecta) {
		getRoEventoAfectas().remove(roEventoAfecta);
		roEventoAfecta.setSisSucursal(null);

		return roEventoAfecta;
	}

	public List<RoEventoCosto> getRoEventoCostos() {
		return this.roEventoCostos;
	}

	public void setRoEventoCostos(List<RoEventoCosto> roEventoCostos) {
		this.roEventoCostos = roEventoCostos;
	}

	public RoEventoCosto addRoEventoCosto(RoEventoCosto roEventoCosto) {
		getRoEventoCostos().add(roEventoCosto);
		roEventoCosto.setSisSucursal(this);

		return roEventoCosto;
	}

	public RoEventoCosto removeRoEventoCosto(RoEventoCosto roEventoCosto) {
		getRoEventoCostos().remove(roEventoCosto);
		roEventoCosto.setSisSucursal(null);

		return roEventoCosto;
	}

	public List<RoEventoObjetivo> getRoEventoObjetivos() {
		return this.roEventoObjetivos;
	}

	public void setRoEventoObjetivos(List<RoEventoObjetivo> roEventoObjetivos) {
		this.roEventoObjetivos = roEventoObjetivos;
	}

	public RoEventoObjetivo addRoEventoObjetivo(
			RoEventoObjetivo roEventoObjetivo) {
		getRoEventoObjetivos().add(roEventoObjetivo);
		roEventoObjetivo.setSisSucursal(this);

		return roEventoObjetivo;
	}

	public RoEventoObjetivo removeRoEventoObjetivo(
			RoEventoObjetivo roEventoObjetivo) {
		getRoEventoObjetivos().remove(roEventoObjetivo);
		roEventoObjetivo.setSisSucursal(null);

		return roEventoObjetivo;
	}

	public List<RoEventoRecupera> getRoEventoRecuperas() {
		return this.roEventoRecuperas;
	}

	public void setRoEventoRecuperas(List<RoEventoRecupera> roEventoRecuperas) {
		this.roEventoRecuperas = roEventoRecuperas;
	}

	public RoEventoRecupera addRoEventoRecupera(
			RoEventoRecupera roEventoRecupera) {
		getRoEventoRecuperas().add(roEventoRecupera);
		roEventoRecupera.setSisSucursal(this);

		return roEventoRecupera;
	}

	public RoEventoRecupera removeRoEventoRecupera(
			RoEventoRecupera roEventoRecupera) {
		getRoEventoRecuperas().remove(roEventoRecupera);
		roEventoRecupera.setSisSucursal(null);

		return roEventoRecupera;
	}

	public List<RoFactorRiesgo> getRoFactorRiesgos() {
		return this.roFactorRiesgos;
	}

	public void setRoFactorRiesgos(List<RoFactorRiesgo> roFactorRiesgos) {
		this.roFactorRiesgos = roFactorRiesgos;
	}

	public RoFactorRiesgo addRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		getRoFactorRiesgos().add(roFactorRiesgo);
		roFactorRiesgo.setSisSucursal(this);

		return roFactorRiesgo;
	}

	public RoFactorRiesgo removeRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		getRoFactorRiesgos().remove(roFactorRiesgo);
		roFactorRiesgo.setSisSucursal(null);

		return roFactorRiesgo;
	}

	public List<RoFrecEjecucion> getRoFrecEjecucions() {
		return this.roFrecEjecucions;
	}

	public void setRoFrecEjecucions(List<RoFrecEjecucion> roFrecEjecucions) {
		this.roFrecEjecucions = roFrecEjecucions;
	}

	public RoFrecEjecucion addRoFrecEjecucion(RoFrecEjecucion roFrecEjecucion) {
		getRoFrecEjecucions().add(roFrecEjecucion);
		roFrecEjecucion.setSisSucursal(this);

		return roFrecEjecucion;
	}

	public RoFrecEjecucion removeRoFrecEjecucion(RoFrecEjecucion roFrecEjecucion) {
		getRoFrecEjecucions().remove(roFrecEjecucion);
		roFrecEjecucion.setSisSucursal(null);

		return roFrecEjecucion;
	}

	public List<RoIndicaRsClave> getRoIndicaRsClaves() {
		return this.roIndicaRsClaves;
	}

	public void setRoIndicaRsClaves(List<RoIndicaRsClave> roIndicaRsClaves) {
		this.roIndicaRsClaves = roIndicaRsClaves;
	}

	public RoIndicaRsClave addRoIndicaRsClave(RoIndicaRsClave roIndicaRsClave) {
		getRoIndicaRsClaves().add(roIndicaRsClave);
		roIndicaRsClave.setSisSucursal(this);

		return roIndicaRsClave;
	}

	public RoIndicaRsClave removeRoIndicaRsClave(RoIndicaRsClave roIndicaRsClave) {
		getRoIndicaRsClaves().remove(roIndicaRsClave);
		roIndicaRsClave.setSisSucursal(null);

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
		roNegocio.setSisSucursal(this);

		return roNegocio;
	}

	public RoNegocio removeRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().remove(roNegocio);
		roNegocio.setSisSucursal(null);

		return roNegocio;
	}

	public List<RoNivelRiesgo> getRoNivelRiesgos() {
		return this.roNivelRiesgos;
	}

	public void setRoNivelRiesgos(List<RoNivelRiesgo> roNivelRiesgos) {
		this.roNivelRiesgos = roNivelRiesgos;
	}

	public RoNivelRiesgo addRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().add(roNivelRiesgo);
		roNivelRiesgo.setSisSucursal(this);

		return roNivelRiesgo;
	}

	public RoNivelRiesgo removeRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().remove(roNivelRiesgo);
		roNivelRiesgo.setSisSucursal(null);

		return roNivelRiesgo;
	}

	public List<RoProceso> getRoProcesos() {
		return this.roProcesos;
	}

	public void setRoProcesos(List<RoProceso> roProcesos) {
		this.roProcesos = roProcesos;
	}

	public RoProceso addRoProceso(RoProceso roProceso) {
		getRoProcesos().add(roProceso);
		roProceso.setSisSucursal(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setSisSucursal(null);

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
		roResponsable.setSisSucursal(this);

		return roResponsable;
	}

	public RoResponsable removeRoResponsable(RoResponsable roResponsable) {
		getRoResponsables().remove(roResponsable);
		roResponsable.setSisSucursal(null);

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
		roTipoAfecta.setSisSucursal(this);

		return roTipoAfecta;
	}

	public RoTipoAfecta removeRoTipoAfecta(RoTipoAfecta roTipoAfecta) {
		getRoTipoAfectas().remove(roTipoAfecta);
		roTipoAfecta.setSisSucursal(null);

		return roTipoAfecta;
	}

	public List<RoTipoAgencia> getRoTipoAgencias() {
		return this.roTipoAgencias;
	}

	public void setRoTipoAgencias(List<RoTipoAgencia> roTipoAgencias) {
		this.roTipoAgencias = roTipoAgencias;
	}

	public RoTipoAgencia addRoTipoAgencia(RoTipoAgencia roTipoAgencia) {
		getRoTipoAgencias().add(roTipoAgencia);
		roTipoAgencia.setSisSucursal(this);

		return roTipoAgencia;
	}

	public RoTipoAgencia removeRoTipoAgencia(RoTipoAgencia roTipoAgencia) {
		getRoTipoAgencias().remove(roTipoAgencia);
		roTipoAgencia.setSisSucursal(null);

		return roTipoAgencia;
	}

	public List<RoTipoCosto> getRoTipoCostos() {
		return this.roTipoCostos;
	}

	public void setRoTipoCostos(List<RoTipoCosto> roTipoCostos) {
		this.roTipoCostos = roTipoCostos;
	}

	public RoTipoCosto addRoTipoCosto(RoTipoCosto roTipoCosto) {
		getRoTipoCostos().add(roTipoCosto);
		roTipoCosto.setSisSucursal(this);

		return roTipoCosto;
	}

	public RoTipoCosto removeRoTipoCosto(RoTipoCosto roTipoCosto) {
		getRoTipoCostos().remove(roTipoCosto);
		roTipoCosto.setSisSucursal(null);

		return roTipoCosto;
	}

	public List<RoTipoDetalle> getRoTipoDetalles() {
		return this.roTipoDetalles;
	}

	public void setRoTipoDetalles(List<RoTipoDetalle> roTipoDetalles) {
		this.roTipoDetalles = roTipoDetalles;
	}

	public RoTipoDetalle addRoTipoDetalle(RoTipoDetalle roTipoDetalle) {
		getRoTipoDetalles().add(roTipoDetalle);
		roTipoDetalle.setSisSucursal(this);

		return roTipoDetalle;
	}

	public RoTipoDetalle removeRoTipoDetalle(RoTipoDetalle roTipoDetalle) {
		getRoTipoDetalles().remove(roTipoDetalle);
		roTipoDetalle.setSisSucursal(null);

		return roTipoDetalle;
	}

	public List<RoTipoEjecucion> getRoTipoEjecucions() {
		return this.roTipoEjecucions;
	}

	public void setRoTipoEjecucions(List<RoTipoEjecucion> roTipoEjecucions) {
		this.roTipoEjecucions = roTipoEjecucions;
	}

	public RoTipoEjecucion addRoTipoEjecucion(RoTipoEjecucion roTipoEjecucion) {
		getRoTipoEjecucions().add(roTipoEjecucion);
		roTipoEjecucion.setSisSucursal(this);

		return roTipoEjecucion;
	}

	public RoTipoEjecucion removeRoTipoEjecucion(RoTipoEjecucion roTipoEjecucion) {
		getRoTipoEjecucions().remove(roTipoEjecucion);
		roTipoEjecucion.setSisSucursal(null);

		return roTipoEjecucion;
	}

	public List<RoTipoNegocio> getRoTipoNegocios() {
		return this.roTipoNegocios;
	}

	public void setRoTipoNegocios(List<RoTipoNegocio> roTipoNegocios) {
		this.roTipoNegocios = roTipoNegocios;
	}

	public RoTipoNegocio addRoTipoNegocio(RoTipoNegocio roTipoNegocio) {
		getRoTipoNegocios().add(roTipoNegocio);
		roTipoNegocio.setSisSucursal(this);

		return roTipoNegocio;
	}

	public RoTipoNegocio removeRoTipoNegocio(RoTipoNegocio roTipoNegocio) {
		getRoTipoNegocios().remove(roTipoNegocio);
		roTipoNegocio.setSisSucursal(null);

		return roTipoNegocio;
	}

	public List<RoTipoProceso> getRoTipoProcesos() {
		return this.roTipoProcesos;
	}

	public void setRoTipoProcesos(List<RoTipoProceso> roTipoProcesos) {
		this.roTipoProcesos = roTipoProcesos;
	}

	public RoTipoProceso addRoTipoProceso(RoTipoProceso roTipoProceso) {
		getRoTipoProcesos().add(roTipoProceso);
		roTipoProceso.setSisSucursal(this);

		return roTipoProceso;
	}

	public RoTipoProceso removeRoTipoProceso(RoTipoProceso roTipoProceso) {
		getRoTipoProcesos().remove(roTipoProceso);
		roTipoProceso.setSisSucursal(null);

		return roTipoProceso;
	}

	public List<RoTipoRecupera> getRoTipoRecuperas() {
		return this.roTipoRecuperas;
	}

	public void setRoTipoRecuperas(List<RoTipoRecupera> roTipoRecuperas) {
		this.roTipoRecuperas = roTipoRecuperas;
	}

	public RoTipoRecupera addRoTipoRecupera(RoTipoRecupera roTipoRecupera) {
		getRoTipoRecuperas().add(roTipoRecupera);
		roTipoRecupera.setSisSucursal(this);

		return roTipoRecupera;
	}

	public RoTipoRecupera removeRoTipoRecupera(RoTipoRecupera roTipoRecupera) {
		getRoTipoRecuperas().remove(roTipoRecupera);
		roTipoRecupera.setSisSucursal(null);

		return roTipoRecupera;
	}

	public List<SisAlertaUsuario> getSisAlertaUsuarios() {
		return this.sisAlertaUsuarios;
	}

	public void setSisAlertaUsuarios(List<SisAlertaUsuario> sisAlertaUsuarios) {
		this.sisAlertaUsuarios = sisAlertaUsuarios;
	}

	public SisAlertaUsuario addSisAlertaUsuario(
			SisAlertaUsuario sisAlertaUsuario) {
		getSisAlertaUsuarios().add(sisAlertaUsuario);
		sisAlertaUsuario.setSisSucursal(this);

		return sisAlertaUsuario;
	}

	public SisAlertaUsuario removeSisAlertaUsuario(
			SisAlertaUsuario sisAlertaUsuario) {
		getSisAlertaUsuarios().remove(sisAlertaUsuario);
		sisAlertaUsuario.setSisSucursal(null);

		return sisAlertaUsuario;
	}

	public List<SisAuditoria> getSisAuditorias() {
		return this.sisAuditorias;
	}

	public void setSisAuditorias(List<SisAuditoria> sisAuditorias) {
		this.sisAuditorias = sisAuditorias;
	}

	public SisAuditoria addSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().add(sisAuditoria);
		sisAuditoria.setSisSucursal(this);

		return sisAuditoria;
	}

	public SisAuditoria removeSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().remove(sisAuditoria);
		sisAuditoria.setSisSucursal(null);

		return sisAuditoria;
	}

	public List<SisControlProceso> getSisControlProcesos() {
		return this.sisControlProcesos;
	}

	public void setSisControlProcesos(List<SisControlProceso> sisControlProcesos) {
		this.sisControlProcesos = sisControlProcesos;
	}

	public SisControlProceso addSisControlProceso(
			SisControlProceso sisControlProceso) {
		getSisControlProcesos().add(sisControlProceso);
		sisControlProceso.setSisSucursal(this);

		return sisControlProceso;
	}

	public SisControlProceso removeSisControlProceso(
			SisControlProceso sisControlProceso) {
		getSisControlProcesos().remove(sisControlProceso);
		sisControlProceso.setSisSucursal(null);

		return sisControlProceso;
	}

	public List<SisDetalleMenu> getSisDetalleMenus() {
		return this.sisDetalleMenus;
	}

	public void setSisDetalleMenus(List<SisDetalleMenu> sisDetalleMenus) {
		this.sisDetalleMenus = sisDetalleMenus;
	}

	public SisDetalleMenu addSisDetalleMenus(SisDetalleMenu sisDetalleMenus) {
		getSisDetalleMenus().add(sisDetalleMenus);
		sisDetalleMenus.setSisSucursal(this);

		return sisDetalleMenus;
	}

	public SisDetalleMenu removeSisDetalleMenus(SisDetalleMenu sisDetalleMenus) {
		getSisDetalleMenus().remove(sisDetalleMenus);
		sisDetalleMenus.setSisSucursal(null);

		return sisDetalleMenus;
	}

	public List<SisDetalleProceso> getSisDetalleProcesos() {
		return this.sisDetalleProcesos;
	}

	public void setSisDetalleProcesos(List<SisDetalleProceso> sisDetalleProcesos) {
		this.sisDetalleProcesos = sisDetalleProcesos;
	}

	public SisDetalleProceso addSisDetalleProceso(
			SisDetalleProceso sisDetalleProceso) {
		getSisDetalleProcesos().add(sisDetalleProceso);
		sisDetalleProceso.setSisSucursal(this);

		return sisDetalleProceso;
	}

	public SisDetalleProceso removeSisDetalleProceso(
			SisDetalleProceso sisDetalleProceso) {
		getSisDetalleProcesos().remove(sisDetalleProceso);
		sisDetalleProceso.setSisSucursal(null);

		return sisDetalleProceso;
	}

	public List<SisDetalleReporte1> getSisDetalleReporte1s() {
		return this.sisDetalleReporte1s;
	}

	public void setSisDetalleReporte1s(
			List<SisDetalleReporte1> sisDetalleReporte1s) {
		this.sisDetalleReporte1s = sisDetalleReporte1s;
	}

	public SisDetalleReporte1 addSisDetalleReporte1(
			SisDetalleReporte1 sisDetalleReporte1) {
		getSisDetalleReporte1s().add(sisDetalleReporte1);
		sisDetalleReporte1.setSisSucursal(this);

		return sisDetalleReporte1;
	}

	public SisDetalleReporte1 removeSisDetalleReporte1(
			SisDetalleReporte1 sisDetalleReporte1) {
		getSisDetalleReporte1s().remove(sisDetalleReporte1);
		sisDetalleReporte1.setSisSucursal(null);

		return sisDetalleReporte1;
	}

	public List<SisMensaje> getSisMensajes() {
		return this.sisMensajes;
	}

	public void setSisMensajes(List<SisMensaje> sisMensajes) {
		this.sisMensajes = sisMensajes;
	}

	public SisMensaje addSisMensaje(SisMensaje sisMensaje) {
		getSisMensajes().add(sisMensaje);
		sisMensaje.setSisSucursal(this);

		return sisMensaje;
	}

	public SisMensaje removeSisMensaje(SisMensaje sisMensaje) {
		getSisMensajes().remove(sisMensaje);
		sisMensaje.setSisSucursal(null);

		return sisMensaje;
	}

	public List<SisMenu> getSisMenus() {
		return this.sisMenus;
	}

	public void setSisMenus(List<SisMenu> sisMenus) {
		this.sisMenus = sisMenus;
	}

	public SisMenu addSisMenus(SisMenu sisMenus) {
		getSisMenus().add(sisMenus);
		sisMenus.setSisSucursal(this);

		return sisMenus;
	}

	public SisMenu removeSisMenus(SisMenu sisMenus) {
		getSisMenus().remove(sisMenus);
		sisMenus.setSisSucursal(null);

		return sisMenus;
	}

	public List<SisModulo> getSisModulos() {
		return this.sisModulos;
	}

	public void setSisModulos(List<SisModulo> sisModulos) {
		this.sisModulos = sisModulos;
	}

	public SisModulo addSisModulo(SisModulo sisModulo) {
		getSisModulos().add(sisModulo);
		sisModulo.setSisSucursal(this);

		return sisModulo;
	}

	public SisModulo removeSisModulo(SisModulo sisModulo) {
		getSisModulos().remove(sisModulo);
		sisModulo.setSisSucursal(null);

		return sisModulo;
	}

	public List<SisObjeto> getSisObjetos() {
		return this.sisObjetos;
	}

	public void setSisObjetos(List<SisObjeto> sisObjetos) {
		this.sisObjetos = sisObjetos;
	}

	public SisObjeto addSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().add(sisObjeto);
		sisObjeto.setSisSucursal(this);

		return sisObjeto;
	}

	public SisObjeto removeSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().remove(sisObjeto);
		sisObjeto.setSisSucursal(null);

		return sisObjeto;
	}

	public List<SisParametro> getSisParametros() {
		return this.sisParametros;
	}

	public void setSisParametros(List<SisParametro> sisParametros) {
		this.sisParametros = sisParametros;
	}

	public SisParametro addSisParametro(SisParametro sisParametro) {
		getSisParametros().add(sisParametro);
		sisParametro.setSisSucursal(this);

		return sisParametro;
	}

	public SisParametro removeSisParametro(SisParametro sisParametro) {
		getSisParametros().remove(sisParametro);
		sisParametro.setSisSucursal(null);

		return sisParametro;
	}

	public List<SisPerfil> getSisPerfils() {
		return this.sisPerfils;
	}

	public void setSisPerfils(List<SisPerfil> sisPerfils) {
		this.sisPerfils = sisPerfils;
	}

	public SisPerfil addSisPerfil(SisPerfil sisPerfil) {
		getSisPerfils().add(sisPerfil);
		sisPerfil.setSisSucursal(this);

		return sisPerfil;
	}

	public SisPerfil removeSisPerfil(SisPerfil sisPerfil) {
		getSisPerfils().remove(sisPerfil);
		sisPerfil.setSisSucursal(null);

		return sisPerfil;
	}

	public List<SisPermiso> getSisPermisos() {
		return this.sisPermisos;
	}

	public void setSisPermisos(List<SisPermiso> sisPermisos) {
		this.sisPermisos = sisPermisos;
	}

	public SisPermiso addSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().add(sisPermiso);
		sisPermiso.setSisSucursal(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisSucursal(null);

		return sisPermiso;
	}

	public List<SisPermisoValor> getSisPermisoValors() {
		return this.sisPermisoValors;
	}

	public void setSisPermisoValors(List<SisPermisoValor> sisPermisoValors) {
		this.sisPermisoValors = sisPermisoValors;
	}

	public SisPermisoValor addSisPermisoValor(SisPermisoValor sisPermisoValor) {
		getSisPermisoValors().add(sisPermisoValor);
		sisPermisoValor.setSisSucursal(this);

		return sisPermisoValor;
	}

	public SisPermisoValor removeSisPermisoValor(SisPermisoValor sisPermisoValor) {
		getSisPermisoValors().remove(sisPermisoValor);
		sisPermisoValor.setSisSucursal(null);

		return sisPermisoValor;
	}

	public List<SisProcedimiento> getSisProcedimientos() {
		return this.sisProcedimientos;
	}

	public void setSisProcedimientos(List<SisProcedimiento> sisProcedimientos) {
		this.sisProcedimientos = sisProcedimientos;
	}

	public SisProcedimiento addSisProcedimiento(
			SisProcedimiento sisProcedimiento) {
		getSisProcedimientos().add(sisProcedimiento);
		sisProcedimiento.setSisSucursal(this);

		return sisProcedimiento;
	}

	public SisProcedimiento removeSisProcedimiento(
			SisProcedimiento sisProcedimiento) {
		getSisProcedimientos().remove(sisProcedimiento);
		sisProcedimiento.setSisSucursal(null);

		return sisProcedimiento;
	}

	public List<SisReporte> getSisReportes() {
		return this.sisReportes;
	}

	public void setSisReportes(List<SisReporte> sisReportes) {
		this.sisReportes = sisReportes;
	}

	public SisReporte addSisReporte(SisReporte sisReporte) {
		getSisReportes().add(sisReporte);
		sisReporte.setSisSucursal(this);

		return sisReporte;
	}

	public SisReporte removeSisReporte(SisReporte sisReporte) {
		getSisReportes().remove(sisReporte);
		sisReporte.setSisSucursal(null);

		return sisReporte;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public GenUbicGeo getGenUbicGeo() {
		return this.genUbicGeo;
	}

	public void setGenUbicGeo(GenUbicGeo genUbicGeo) {
		this.genUbicGeo = genUbicGeo;
	}

	public List<SisTipoObjeto> getSisTipoObjetos() {
		return this.sisTipoObjetos;
	}

	public void setSisTipoObjetos(List<SisTipoObjeto> sisTipoObjetos) {
		this.sisTipoObjetos = sisTipoObjetos;
	}

	public SisTipoObjeto addSisTipoObjeto(SisTipoObjeto sisTipoObjeto) {
		getSisTipoObjetos().add(sisTipoObjeto);
		sisTipoObjeto.setSisSucursal(this);

		return sisTipoObjeto;
	}

	public SisTipoObjeto removeSisTipoObjeto(SisTipoObjeto sisTipoObjeto) {
		getSisTipoObjetos().remove(sisTipoObjeto);
		sisTipoObjeto.setSisSucursal(null);

		return sisTipoObjeto;
	}

	public List<SisUsuario> getSisUsuarios() {
		return this.sisUsuarios;
	}

	public void setSisUsuarios(List<SisUsuario> sisUsuarios) {
		this.sisUsuarios = sisUsuarios;
	}

	public SisUsuario addSisUsuario(SisUsuario sisUsuario) {
		getSisUsuarios().add(sisUsuario);
		sisUsuario.setSisSucursal(this);

		return sisUsuario;
	}

	public SisUsuario removeSisUsuario(SisUsuario sisUsuario) {
		getSisUsuarios().remove(sisUsuario);
		sisUsuario.setSisSucursal(null);

		return sisUsuario;
	}

	public List<RoControles> getRoControles() {
		return roControles;
	}

	public void setRoControles(List<RoControles> roControles) {
		this.roControles = roControles;
	}

}