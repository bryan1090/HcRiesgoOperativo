package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the sis_empresa database table.
 * 
 */
@Entity
@Table(name = "sis_empresa")
@NamedQuery(name = "SisEmpresa.findAll", query = "SELECT s FROM SisEmpresa s")
public class SisEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_EMPR")
	private int codigoEmpr;

	@Column(name = "DIRECCION_EMPR")
	private String direccionEmpr;

	@Column(name = "EMAIL_EMPR")
	private String emailEmpr;

	@Column(name = "FAX_EMPR")
	private String faxEmpr;

	@Column(name = "IDENTIFIC_REPRE_EMPR")
	private String identificRepreEmpr;

	@Column(name = "LOGOTIPO_EMPR")
	private String logotipoEmpr;

	@Column(name = "MAPEOEMPR_EMPR")
	private BigDecimal mapeoemprEmpr;

	@Column(name = "NOMBRE_ALTERNO1_EMPR")
	private String nombreAlterno1Empr;

	@Column(name = "NOMBRE_EMPR")
	private String nombreEmpr;

	@Column(name = "REPRESENTANTE_EMPR")
	private String representanteEmpr;

	@Column(name = "RUC_CONTADOR")
	private String rucContador;

	@Column(name = "RUC_EMPR")
	private String rucEmpr;

	@Column(name = "TELEFONO1_EMPR")
	private String telefono1Empr;

	@Column(name = "TELEFONO2_EMPR")
	private String telefono2Empr;

	@Column(name = "TELEFONO3_EMPR")
	private String telefono3Empr;

	// bi-directional many-to-one association to GenDetallePeriodo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenDetallePeriodo> genDetallePeriodos;

	// bi-directional many-to-one association to GenEstado
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenEstado> genEstados;

	// bi-directional many-to-one association to GenMoneda
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenMoneda> genMonedas;

	// bi-directional many-to-one association to GenNivelArbol
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenNivelArbol> genNivelArbols;

	// bi-directional many-to-one association to GenNivelArbol
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenNivelProceso> genNivelProcs;

	// bi-directional many-to-one association to GenPeriodo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenPeriodo> genPeriodos;

	// bi-directional many-to-one association to GenSemana
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenSemana> genSemanas;

	// bi-directional many-to-one association to GenTipoIdentific
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenTipoIdentific> genTipoIdentifics;

	// bi-directional many-to-one association to GenTipoUbiGeog
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenTipoUbiGeog> genTipoUbiGeogs;

	// bi-directional many-to-one association to GenUbicGeo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<GenUbicGeo> genUbicGeos;

	// bi-directional many-to-one association to RoAgencia
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoAgencia> roAgencias;

	// bi-directional many-to-one association to RoCalifRiesgo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoCalifRiesgo> roCalifRiesgos;

	// bi-directional many-to-one association to RoCaractEvento
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoCaractEvento> roCaractEventos;

	// bi-directional many-to-one association to RoCatFrecuencia
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoCatFrecuencia> roCatFrecuencias;

	// bi-directional many-to-one association to RoCatObjetivo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoCatObjetivo> roCatObjetivos;

	// bi-directional many-to-one association to RoCatSeveridad
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoCatSeveridad> roCatSeveridads;

	// bi-directional many-to-one association to RoControlProc
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoControlProc> roControlProcs;

	// bi-directional many-to-one association to RoCriticidad
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoCriticidad> roCriticidads;

	// bi-directional many-to-one association to RoDepartamento
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDepartamento> roDepartamentos;

	// bi-directional many-to-one association to RoDeptAgencia
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDeptAgencia> roDeptAgencias;

	// bi-directional many-to-one association to RoDetCarctEvent
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDetCarctEvent> roDetCarctEvents;

	// bi-directional many-to-one association to RoDetCriticProc
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDetCriticProc> roDetCriticProcs;

	// bi-directional many-to-one association to RoDetRiclProc
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDetRiclProc> roDetRiclProcs;

	// bi-directional many-to-one association to RoDetalleControl
	@OneToMany(mappedBy = "sisEmpresa1")
	private List<RoDetalleControl> roDetalleControls1;

	// bi-directional many-to-one association to RoDetalleControl
	@OneToMany(mappedBy = "sisEmpresa2")
	private List<RoDetalleControl> roDetalleControls2;

	// bi-directional many-to-one association to RoDetalleEvento
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDetalleEvento> roDetalleEventos;

	// bi-directional many-to-one association to RoDetalleNegocio
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDetalleNegocio> roDetalleNegocios;

	// bi-directional many-to-one association to RoDetalleProceso
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoDetalleProceso> roDetalleProcesos;

	// bi-directional many-to-one association to RoEvento
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoEvento> roEventos;

	// bi-directional many-to-one association to RoEventoAfecta
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoEventoAfecta> roEventoAfectas;

	// bi-directional many-to-one association to RoEventoCosto
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoEventoCosto> roEventoCostos;

	// bi-directional many-to-one association to RoEventoObjetivo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoEventoObjetivo> roEventoObjetivos;

	// bi-directional many-to-one association to RoEventoRecupera
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoEventoRecupera> roEventoRecuperas;

	// bi-directional many-to-one association to RoFactorRiesgo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoFactorRiesgo> roFactorRiesgos;

	// bi-directional many-to-one association to RoFrecEjecucion
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoFrecEjecucion> roFrecEjecucions;

	// bi-directional many-to-one association to RoIndicaRsClave
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoIndicaRsClave> roIndicaRsClaves;

	// bi-directional many-to-one association to RoNegocio
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoNegocio> roNegocios;

	// bi-directional many-to-one association to RoNivelRiesgo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoNivelRiesgo> roNivelRiesgos;

	// bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoProceso> roProcesos;

	// bi-directional many-to-one association to RoResponsable
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoResponsable> roResponsables;

	// bi-directional many-to-one association to RoTipoAfecta
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoAfecta> roTipoAfectas;

	// bi-directional many-to-one association to RoTipoAgencia
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoAgencia> roTipoAgencias;

	// bi-directional many-to-one association to RoTipoCosto
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoCosto> roTipoCostos;

	// bi-directional many-to-one association to RoTipoDetalle
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoDetalle> roTipoDetalles;

	// bi-directional many-to-one association to RoTipoEjecucion
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoEjecucion> roTipoEjecucions;

	// bi-directional many-to-one association to RoTipoNegocio
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoNegocio> roTipoNegocios;

	// bi-directional many-to-one association to RoTipoProceso
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoProceso> roTipoProcesos;

	// bi-directional many-to-one association to RoTipoRecupera
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoTipoRecupera> roTipoRecuperas;

	// bi-directional many-to-one association to SisAlertaUsuario
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisAlertaUsuario> sisAlertaUsuarios;

	// bi-directional many-to-one association to SisAuditoria
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisAuditoria> sisAuditorias;

	// bi-directional many-to-one association to SisControlProceso
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisControlProceso> sisControlProcesos;

	// bi-directional many-to-one association to SisDetalleMenu
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisDetalleMenu> sisDetalleMenus;

	// bi-directional many-to-one association to SisDetalleProceso
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisDetalleProceso> sisDetalleProcesos;

	// bi-directional many-to-one association to SisDetalleReporte1
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisDetalleReporte1> sisDetalleReporte1s;

	// bi-directional many-to-one association to GenUbicGeo
	@ManyToOne
	@JoinColumn(name = "CODIGO_UBGE")
	private GenUbicGeo genUbicGeo;

	// bi-directional many-to-one association to SisMensaje
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisMensaje> sisMensajes;

	// bi-directional many-to-one association to SisMenu
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisMenu> sisMenus;

	// bi-directional many-to-one association to SisModulo
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisModulo> sisModulos;

	// bi-directional many-to-one association to SisObjeto
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisObjeto> sisObjetos;

	// bi-directional many-to-one association to SisParametro
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisParametro> sisParametros;

	// bi-directional many-to-one association to SisPerfil
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisPerfil> sisPerfils;

	// bi-directional many-to-one association to SisPermiso
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisPermiso> sisPermisos;

	// bi-directional many-to-one association to SisPermisoValor
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisPermisoValor> sisPermisoValors;

	// bi-directional many-to-one association to SisProcedimiento
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisProcedimiento> sisProcedimientos;

	// bi-directional many-to-one association to SisReporte
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisReporte> sisReportes;

	// bi-directional many-to-one association to SisSucursal
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisSucursal> sisSucursals;

	// bi-directional many-to-one association to SisTipoObjeto
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisTipoObjeto> sisTipoObjetos;

	// bi-directional many-to-one association to SisUsuario
	@OneToMany(mappedBy = "sisEmpresa")
	private List<SisUsuario> sisUsuarios;

	// bi-directional many-to-one association to RoControle
	@OneToMany(mappedBy = "sisEmpresa")
	private List<RoControles> roControles;

	public SisEmpresa() {
	}

	public int getCodigoEmpr() {
		return this.codigoEmpr;
	}

	public void setCodigoEmpr(int codigoEmpr) {
		this.codigoEmpr = codigoEmpr;
	}

	public String getDireccionEmpr() {
		return this.direccionEmpr;
	}

	public void setDireccionEmpr(String direccionEmpr) {
		this.direccionEmpr = direccionEmpr;
	}

	public String getEmailEmpr() {
		return this.emailEmpr;
	}

	public void setEmailEmpr(String emailEmpr) {
		this.emailEmpr = emailEmpr;
	}

	public String getFaxEmpr() {
		return this.faxEmpr;
	}

	public void setFaxEmpr(String faxEmpr) {
		this.faxEmpr = faxEmpr;
	}

	public String getIdentificRepreEmpr() {
		return this.identificRepreEmpr;
	}

	public void setIdentificRepreEmpr(String identificRepreEmpr) {
		this.identificRepreEmpr = identificRepreEmpr;
	}

	public String getLogotipoEmpr() {
		return this.logotipoEmpr;
	}

	public void setLogotipoEmpr(String logotipoEmpr) {
		this.logotipoEmpr = logotipoEmpr;
	}

	public BigDecimal getMapeoemprEmpr() {
		return this.mapeoemprEmpr;
	}

	public void setMapeoemprEmpr(BigDecimal mapeoemprEmpr) {
		this.mapeoemprEmpr = mapeoemprEmpr;
	}

	public String getNombreAlterno1Empr() {
		return this.nombreAlterno1Empr;
	}

	public void setNombreAlterno1Empr(String nombreAlterno1Empr) {
		this.nombreAlterno1Empr = nombreAlterno1Empr;
	}

	public String getNombreEmpr() {
		return this.nombreEmpr;
	}

	public void setNombreEmpr(String nombreEmpr) {
		this.nombreEmpr = nombreEmpr;
	}

	public String getRepresentanteEmpr() {
		return this.representanteEmpr;
	}

	public void setRepresentanteEmpr(String representanteEmpr) {
		this.representanteEmpr = representanteEmpr;
	}

	public String getRucContador() {
		return this.rucContador;
	}

	public void setRucContador(String rucContador) {
		this.rucContador = rucContador;
	}

	public String getRucEmpr() {
		return this.rucEmpr;
	}

	public void setRucEmpr(String rucEmpr) {
		this.rucEmpr = rucEmpr;
	}

	public String getTelefono1Empr() {
		return this.telefono1Empr;
	}

	public void setTelefono1Empr(String telefono1Empr) {
		this.telefono1Empr = telefono1Empr;
	}

	public String getTelefono2Empr() {
		return this.telefono2Empr;
	}

	public void setTelefono2Empr(String telefono2Empr) {
		this.telefono2Empr = telefono2Empr;
	}

	public String getTelefono3Empr() {
		return this.telefono3Empr;
	}

	public void setTelefono3Empr(String telefono3Empr) {
		this.telefono3Empr = telefono3Empr;
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
		genDetallePeriodo.setSisEmpresa(this);

		return genDetallePeriodo;
	}

	public GenDetallePeriodo removeGenDetallePeriodo(
			GenDetallePeriodo genDetallePeriodo) {
		getGenDetallePeriodos().remove(genDetallePeriodo);
		genDetallePeriodo.setSisEmpresa(null);

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
		genEstado.setSisEmpresa(this);

		return genEstado;
	}

	public GenEstado removeGenEstado(GenEstado genEstado) {
		getGenEstados().remove(genEstado);
		genEstado.setSisEmpresa(null);

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
		genMoneda.setSisEmpresa(this);

		return genMoneda;
	}

	public GenMoneda removeGenMoneda(GenMoneda genMoneda) {
		getGenMonedas().remove(genMoneda);
		genMoneda.setSisEmpresa(null);

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
		genNivelArbol.setSisEmpresa(this);

		return genNivelArbol;
	}

	public GenNivelArbol removeGenNivelArbol(GenNivelArbol genNivelArbol) {
		getGenNivelArbols().remove(genNivelArbol);
		genNivelArbol.setSisEmpresa(null);

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
		genPeriodo.setSisEmpresa(this);

		return genPeriodo;
	}

	public GenPeriodo removeGenPeriodo(GenPeriodo genPeriodo) {
		getGenPeriodos().remove(genPeriodo);
		genPeriodo.setSisEmpresa(null);

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
		genSemana.setSisEmpresa(this);

		return genSemana;
	}

	public GenSemana removeGenSemana(GenSemana genSemana) {
		getGenSemanas().remove(genSemana);
		genSemana.setSisEmpresa(null);

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
		genTipoIdentific.setSisEmpresa(this);

		return genTipoIdentific;
	}

	public GenTipoIdentific removeGenTipoIdentific(
			GenTipoIdentific genTipoIdentific) {
		getGenTipoIdentifics().remove(genTipoIdentific);
		genTipoIdentific.setSisEmpresa(null);

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
		genTipoUbiGeog.setSisEmpresa(this);

		return genTipoUbiGeog;
	}

	public GenTipoUbiGeog removeGenTipoUbiGeog(GenTipoUbiGeog genTipoUbiGeog) {
		getGenTipoUbiGeogs().remove(genTipoUbiGeog);
		genTipoUbiGeog.setSisEmpresa(null);

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
		genUbicGeo.setSisEmpresa(this);

		return genUbicGeo;
	}

	public GenUbicGeo removeGenUbicGeo(GenUbicGeo genUbicGeo) {
		getGenUbicGeos().remove(genUbicGeo);
		genUbicGeo.setSisEmpresa(null);

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
		roAgencia.setSisEmpresa(this);

		return roAgencia;
	}

	public RoAgencia removeRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().remove(roAgencia);
		roAgencia.setSisEmpresa(null);

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
		roCalifRiesgo.setSisEmpresa(this);

		return roCalifRiesgo;
	}

	public RoCalifRiesgo removeRoCalifRiesgo(RoCalifRiesgo roCalifRiesgo) {
		getRoCalifRiesgos().remove(roCalifRiesgo);
		roCalifRiesgo.setSisEmpresa(null);

		return roCalifRiesgo;
	}

	public List<RoCaractEvento> getRoCaractEventos() {
		return this.roCaractEventos;
	}

	public void setRoCaractEventos(List<RoCaractEvento> roCaractEventos) {
		this.roCaractEventos = roCaractEventos;
	}

	public RoCaractEvento addRoCaractEvento(RoCaractEvento roCaractEvento) {
		getRoCaractEventos().add(roCaractEvento);
		roCaractEvento.setSisEmpresa(this);

		return roCaractEvento;
	}

	public RoCaractEvento removeRoCaractEvento(RoCaractEvento roCaractEvento) {
		getRoCaractEventos().remove(roCaractEvento);
		roCaractEvento.setSisEmpresa(null);

		return roCaractEvento;
	}

	public List<RoCatFrecuencia> getRoCatFrecuencias() {
		return this.roCatFrecuencias;
	}

	public void setRoCatFrecuencias(List<RoCatFrecuencia> roCatFrecuencias) {
		this.roCatFrecuencias = roCatFrecuencias;
	}

	public RoCatFrecuencia addRoCatFrecuencia(RoCatFrecuencia roCatFrecuencia) {
		getRoCatFrecuencias().add(roCatFrecuencia);
		roCatFrecuencia.setSisEmpresa(this);

		return roCatFrecuencia;
	}

	public RoCatFrecuencia removeRoCatFrecuencia(RoCatFrecuencia roCatFrecuencia) {
		getRoCatFrecuencias().remove(roCatFrecuencia);
		roCatFrecuencia.setSisEmpresa(null);

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
		roCatObjetivo.setSisEmpresa(this);

		return roCatObjetivo;
	}

	public RoCatObjetivo removeRoCatObjetivo(RoCatObjetivo roCatObjetivo) {
		getRoCatObjetivos().remove(roCatObjetivo);
		roCatObjetivo.setSisEmpresa(null);

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
		roCatSeveridad.setSisEmpresa(this);

		return roCatSeveridad;
	}

	public RoCatSeveridad removeRoCatSeveridad(RoCatSeveridad roCatSeveridad) {
		getRoCatSeveridads().remove(roCatSeveridad);
		roCatSeveridad.setSisEmpresa(null);

		return roCatSeveridad;
	}

	public List<RoControlProc> getRoControlProcs() {
		return this.roControlProcs;
	}

	public void setRoControlProcs(List<RoControlProc> roControlProcs) {
		this.roControlProcs = roControlProcs;
	}

	public RoControlProc addRoControlProc(RoControlProc roControlProc) {
		getRoControlProcs().add(roControlProc);
		roControlProc.setSisEmpresa(this);

		return roControlProc;
	}

	public RoControlProc removeRoControlProc(RoControlProc roControlProc) {
		getRoControlProcs().remove(roControlProc);
		roControlProc.setSisEmpresa(null);

		return roControlProc;
	}

	public List<RoCriticidad> getRoCriticidads() {
		return this.roCriticidads;
	}

	public void setRoCriticidads(List<RoCriticidad> roCriticidads) {
		this.roCriticidads = roCriticidads;
	}

	public RoCriticidad addRoCriticidad(RoCriticidad roCriticidad) {
		getRoCriticidads().add(roCriticidad);
		roCriticidad.setSisEmpresa(this);

		return roCriticidad;
	}

	public RoCriticidad removeRoCriticidad(RoCriticidad roCriticidad) {
		getRoCriticidads().remove(roCriticidad);
		roCriticidad.setSisEmpresa(null);

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
		roDepartamento.setSisEmpresa(this);

		return roDepartamento;
	}

	public RoDepartamento removeRoDepartamento(RoDepartamento roDepartamento) {
		getRoDepartamentos().remove(roDepartamento);
		roDepartamento.setSisEmpresa(null);

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
		roDeptAgencia.setSisEmpresa(this);

		return roDeptAgencia;
	}

	public RoDeptAgencia removeRoDeptAgencia(RoDeptAgencia roDeptAgencia) {
		getRoDeptAgencias().remove(roDeptAgencia);
		roDeptAgencia.setSisEmpresa(null);

		return roDeptAgencia;
	}

	public List<RoDetCarctEvent> getRoDetCarctEvents() {
		return this.roDetCarctEvents;
	}

	public void setRoDetCarctEvents(List<RoDetCarctEvent> roDetCarctEvents) {
		this.roDetCarctEvents = roDetCarctEvents;
	}

	public RoDetCarctEvent addRoDetCarctEvent(RoDetCarctEvent roDetCarctEvent) {
		getRoDetCarctEvents().add(roDetCarctEvent);
		roDetCarctEvent.setSisEmpresa(this);

		return roDetCarctEvent;
	}

	public RoDetCarctEvent removeRoDetCarctEvent(RoDetCarctEvent roDetCarctEvent) {
		getRoDetCarctEvents().remove(roDetCarctEvent);
		roDetCarctEvent.setSisEmpresa(null);

		return roDetCarctEvent;
	}

	public List<RoDetCriticProc> getRoDetCriticProcs() {
		return this.roDetCriticProcs;
	}

	public void setRoDetCriticProcs(List<RoDetCriticProc> roDetCriticProcs) {
		this.roDetCriticProcs = roDetCriticProcs;
	}

	public RoDetCriticProc addRoDetCriticProc(RoDetCriticProc roDetCriticProc) {
		getRoDetCriticProcs().add(roDetCriticProc);
		roDetCriticProc.setSisEmpresa(this);

		return roDetCriticProc;
	}

	public RoDetCriticProc removeRoDetCriticProc(RoDetCriticProc roDetCriticProc) {
		getRoDetCriticProcs().remove(roDetCriticProc);
		roDetCriticProc.setSisEmpresa(null);

		return roDetCriticProc;
	}

	public List<RoDetRiclProc> getRoDetRiclProcs() {
		return this.roDetRiclProcs;
	}

	public void setRoDetRiclProcs(List<RoDetRiclProc> roDetRiclProcs) {
		this.roDetRiclProcs = roDetRiclProcs;
	}

	public RoDetRiclProc addRoDetRiclProc(RoDetRiclProc roDetRiclProc) {
		getRoDetRiclProcs().add(roDetRiclProc);
		roDetRiclProc.setSisEmpresa(this);

		return roDetRiclProc;
	}

	public RoDetRiclProc removeRoDetRiclProc(RoDetRiclProc roDetRiclProc) {
		getRoDetRiclProcs().remove(roDetRiclProc);
		roDetRiclProc.setSisEmpresa(null);

		return roDetRiclProc;
	}

	public List<RoDetalleControl> getRoDetalleControls1() {
		return this.roDetalleControls1;
	}

	public void setRoDetalleControls1(List<RoDetalleControl> roDetalleControls1) {
		this.roDetalleControls1 = roDetalleControls1;
	}

	public RoDetalleControl addRoDetalleControls1(
			RoDetalleControl roDetalleControls1) {
		getRoDetalleControls1().add(roDetalleControls1);
		roDetalleControls1.setSisEmpresa1(this);

		return roDetalleControls1;
	}

	public RoDetalleControl removeRoDetalleControls1(
			RoDetalleControl roDetalleControls1) {
		getRoDetalleControls1().remove(roDetalleControls1);
		roDetalleControls1.setSisEmpresa1(null);

		return roDetalleControls1;
	}

	public List<RoDetalleControl> getRoDetalleControls2() {
		return this.roDetalleControls2;
	}

	public void setRoDetalleControls2(List<RoDetalleControl> roDetalleControls2) {
		this.roDetalleControls2 = roDetalleControls2;
	}

	public RoDetalleControl addRoDetalleControls2(
			RoDetalleControl roDetalleControls2) {
		getRoDetalleControls2().add(roDetalleControls2);
		roDetalleControls2.setSisEmpresa2(this);

		return roDetalleControls2;
	}

	public RoDetalleControl removeRoDetalleControls2(
			RoDetalleControl roDetalleControls2) {
		getRoDetalleControls2().remove(roDetalleControls2);
		roDetalleControls2.setSisEmpresa2(null);

		return roDetalleControls2;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return this.roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public RoDetalleEvento addRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().add(roDetalleEvento);
		roDetalleEvento.setSisEmpresa(this);

		return roDetalleEvento;
	}

	public RoDetalleEvento removeRoDetalleEvento(RoDetalleEvento roDetalleEvento) {
		getRoDetalleEventos().remove(roDetalleEvento);
		roDetalleEvento.setSisEmpresa(null);

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
		roDetalleNegocio.setSisEmpresa(this);

		return roDetalleNegocio;
	}

	public RoDetalleNegocio removeRoDetalleNegocio(
			RoDetalleNegocio roDetalleNegocio) {
		getRoDetalleNegocios().remove(roDetalleNegocio);
		roDetalleNegocio.setSisEmpresa(null);

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
		roDetalleProceso.setSisEmpresa(this);

		return roDetalleProceso;
	}

	public RoDetalleProceso removeRoDetalleProceso(
			RoDetalleProceso roDetalleProceso) {
		getRoDetalleProcesos().remove(roDetalleProceso);
		roDetalleProceso.setSisEmpresa(null);

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
		roEvento.setSisEmpresa(this);

		return roEvento;
	}

	public RoEvento removeRoEvento(RoEvento roEvento) {
		getRoEventos().remove(roEvento);
		roEvento.setSisEmpresa(null);

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
		roEventoAfecta.setSisEmpresa(this);

		return roEventoAfecta;
	}

	public RoEventoAfecta removeRoEventoAfecta(RoEventoAfecta roEventoAfecta) {
		getRoEventoAfectas().remove(roEventoAfecta);
		roEventoAfecta.setSisEmpresa(null);

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
		roEventoCosto.setSisEmpresa(this);

		return roEventoCosto;
	}

	public RoEventoCosto removeRoEventoCosto(RoEventoCosto roEventoCosto) {
		getRoEventoCostos().remove(roEventoCosto);
		roEventoCosto.setSisEmpresa(null);

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
		roEventoObjetivo.setSisEmpresa(this);

		return roEventoObjetivo;
	}

	public RoEventoObjetivo removeRoEventoObjetivo(
			RoEventoObjetivo roEventoObjetivo) {
		getRoEventoObjetivos().remove(roEventoObjetivo);
		roEventoObjetivo.setSisEmpresa(null);

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
		roEventoRecupera.setSisEmpresa(this);

		return roEventoRecupera;
	}

	public RoEventoRecupera removeRoEventoRecupera(
			RoEventoRecupera roEventoRecupera) {
		getRoEventoRecuperas().remove(roEventoRecupera);
		roEventoRecupera.setSisEmpresa(null);

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
		roFactorRiesgo.setSisEmpresa(this);

		return roFactorRiesgo;
	}

	public RoFactorRiesgo removeRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		getRoFactorRiesgos().remove(roFactorRiesgo);
		roFactorRiesgo.setSisEmpresa(null);

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
		roFrecEjecucion.setSisEmpresa(this);

		return roFrecEjecucion;
	}

	public RoFrecEjecucion removeRoFrecEjecucion(RoFrecEjecucion roFrecEjecucion) {
		getRoFrecEjecucions().remove(roFrecEjecucion);
		roFrecEjecucion.setSisEmpresa(null);

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
		roIndicaRsClave.setSisEmpresa(this);

		return roIndicaRsClave;
	}

	public RoIndicaRsClave removeRoIndicaRsClave(RoIndicaRsClave roIndicaRsClave) {
		getRoIndicaRsClaves().remove(roIndicaRsClave);
		roIndicaRsClave.setSisEmpresa(null);

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
		roNegocio.setSisEmpresa(this);

		return roNegocio;
	}

	public RoNegocio removeRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().remove(roNegocio);
		roNegocio.setSisEmpresa(null);

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
		roNivelRiesgo.setSisEmpresa(this);

		return roNivelRiesgo;
	}

	public RoNivelRiesgo removeRoNivelRiesgo(RoNivelRiesgo roNivelRiesgo) {
		getRoNivelRiesgos().remove(roNivelRiesgo);
		roNivelRiesgo.setSisEmpresa(null);

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
		roProceso.setSisEmpresa(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setSisEmpresa(null);

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
		roResponsable.setSisEmpresa(this);

		return roResponsable;
	}

	public RoResponsable removeRoResponsable(RoResponsable roResponsable) {
		getRoResponsables().remove(roResponsable);
		roResponsable.setSisEmpresa(null);

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
		roTipoAfecta.setSisEmpresa(this);

		return roTipoAfecta;
	}

	public RoTipoAfecta removeRoTipoAfecta(RoTipoAfecta roTipoAfecta) {
		getRoTipoAfectas().remove(roTipoAfecta);
		roTipoAfecta.setSisEmpresa(null);

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
		roTipoAgencia.setSisEmpresa(this);

		return roTipoAgencia;
	}

	public RoTipoAgencia removeRoTipoAgencia(RoTipoAgencia roTipoAgencia) {
		getRoTipoAgencias().remove(roTipoAgencia);
		roTipoAgencia.setSisEmpresa(null);

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
		roTipoCosto.setSisEmpresa(this);

		return roTipoCosto;
	}

	public RoTipoCosto removeRoTipoCosto(RoTipoCosto roTipoCosto) {
		getRoTipoCostos().remove(roTipoCosto);
		roTipoCosto.setSisEmpresa(null);

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
		roTipoDetalle.setSisEmpresa(this);

		return roTipoDetalle;
	}

	public RoTipoDetalle removeRoTipoDetalle(RoTipoDetalle roTipoDetalle) {
		getRoTipoDetalles().remove(roTipoDetalle);
		roTipoDetalle.setSisEmpresa(null);

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
		roTipoEjecucion.setSisEmpresa(this);

		return roTipoEjecucion;
	}

	public RoTipoEjecucion removeRoTipoEjecucion(RoTipoEjecucion roTipoEjecucion) {
		getRoTipoEjecucions().remove(roTipoEjecucion);
		roTipoEjecucion.setSisEmpresa(null);

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
		roTipoNegocio.setSisEmpresa(this);

		return roTipoNegocio;
	}

	public RoTipoNegocio removeRoTipoNegocio(RoTipoNegocio roTipoNegocio) {
		getRoTipoNegocios().remove(roTipoNegocio);
		roTipoNegocio.setSisEmpresa(null);

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
		roTipoProceso.setSisEmpresa(this);

		return roTipoProceso;
	}

	public RoTipoProceso removeRoTipoProceso(RoTipoProceso roTipoProceso) {
		getRoTipoProcesos().remove(roTipoProceso);
		roTipoProceso.setSisEmpresa(null);

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
		roTipoRecupera.setSisEmpresa(this);

		return roTipoRecupera;
	}

	public RoTipoRecupera removeRoTipoRecupera(RoTipoRecupera roTipoRecupera) {
		getRoTipoRecuperas().remove(roTipoRecupera);
		roTipoRecupera.setSisEmpresa(null);

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
		sisAlertaUsuario.setSisEmpresa(this);

		return sisAlertaUsuario;
	}

	public SisAlertaUsuario removeSisAlertaUsuario(
			SisAlertaUsuario sisAlertaUsuario) {
		getSisAlertaUsuarios().remove(sisAlertaUsuario);
		sisAlertaUsuario.setSisEmpresa(null);

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
		sisAuditoria.setSisEmpresa(this);

		return sisAuditoria;
	}

	public SisAuditoria removeSisAuditoria(SisAuditoria sisAuditoria) {
		getSisAuditorias().remove(sisAuditoria);
		sisAuditoria.setSisEmpresa(null);

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
		sisControlProceso.setSisEmpresa(this);

		return sisControlProceso;
	}

	public SisControlProceso removeSisControlProceso(
			SisControlProceso sisControlProceso) {
		getSisControlProcesos().remove(sisControlProceso);
		sisControlProceso.setSisEmpresa(null);

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
		sisDetalleMenus.setSisEmpresa(this);

		return sisDetalleMenus;
	}

	public SisDetalleMenu removeSisDetalleMenus(SisDetalleMenu sisDetalleMenus) {
		getSisDetalleMenus().remove(sisDetalleMenus);
		sisDetalleMenus.setSisEmpresa(null);

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
		sisDetalleProceso.setSisEmpresa(this);

		return sisDetalleProceso;
	}

	public SisDetalleProceso removeSisDetalleProceso(
			SisDetalleProceso sisDetalleProceso) {
		getSisDetalleProcesos().remove(sisDetalleProceso);
		sisDetalleProceso.setSisEmpresa(null);

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
		sisDetalleReporte1.setSisEmpresa(this);

		return sisDetalleReporte1;
	}

	public SisDetalleReporte1 removeSisDetalleReporte1(
			SisDetalleReporte1 sisDetalleReporte1) {
		getSisDetalleReporte1s().remove(sisDetalleReporte1);
		sisDetalleReporte1.setSisEmpresa(null);

		return sisDetalleReporte1;
	}

	public GenUbicGeo getGenUbicGeo() {
		return this.genUbicGeo;
	}

	public void setGenUbicGeo(GenUbicGeo genUbicGeo) {
		this.genUbicGeo = genUbicGeo;
	}

	public List<SisMensaje> getSisMensajes() {
		return this.sisMensajes;
	}

	public void setSisMensajes(List<SisMensaje> sisMensajes) {
		this.sisMensajes = sisMensajes;
	}

	public SisMensaje addSisMensaje(SisMensaje sisMensaje) {
		getSisMensajes().add(sisMensaje);
		sisMensaje.setSisEmpresa(this);

		return sisMensaje;
	}

	public SisMensaje removeSisMensaje(SisMensaje sisMensaje) {
		getSisMensajes().remove(sisMensaje);
		sisMensaje.setSisEmpresa(null);

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
		sisMenus.setSisEmpresa(this);

		return sisMenus;
	}

	public SisMenu removeSisMenus(SisMenu sisMenus) {
		getSisMenus().remove(sisMenus);
		sisMenus.setSisEmpresa(null);

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
		sisModulo.setSisEmpresa(this);

		return sisModulo;
	}

	public SisModulo removeSisModulo(SisModulo sisModulo) {
		getSisModulos().remove(sisModulo);
		sisModulo.setSisEmpresa(null);

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
		sisObjeto.setSisEmpresa(this);

		return sisObjeto;
	}

	public SisObjeto removeSisObjeto(SisObjeto sisObjeto) {
		getSisObjetos().remove(sisObjeto);
		sisObjeto.setSisEmpresa(null);

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
		sisParametro.setSisEmpresa(this);

		return sisParametro;
	}

	public SisParametro removeSisParametro(SisParametro sisParametro) {
		getSisParametros().remove(sisParametro);
		sisParametro.setSisEmpresa(null);

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
		sisPerfil.setSisEmpresa(this);

		return sisPerfil;
	}

	public SisPerfil removeSisPerfil(SisPerfil sisPerfil) {
		getSisPerfils().remove(sisPerfil);
		sisPerfil.setSisEmpresa(null);

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
		sisPermiso.setSisEmpresa(this);

		return sisPermiso;
	}

	public SisPermiso removeSisPermiso(SisPermiso sisPermiso) {
		getSisPermisos().remove(sisPermiso);
		sisPermiso.setSisEmpresa(null);

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
		sisPermisoValor.setSisEmpresa(this);

		return sisPermisoValor;
	}

	public SisPermisoValor removeSisPermisoValor(SisPermisoValor sisPermisoValor) {
		getSisPermisoValors().remove(sisPermisoValor);
		sisPermisoValor.setSisEmpresa(null);

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
		sisProcedimiento.setSisEmpresa(this);

		return sisProcedimiento;
	}

	public SisProcedimiento removeSisProcedimiento(
			SisProcedimiento sisProcedimiento) {
		getSisProcedimientos().remove(sisProcedimiento);
		sisProcedimiento.setSisEmpresa(null);

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
		sisReporte.setSisEmpresa(this);

		return sisReporte;
	}

	public SisReporte removeSisReporte(SisReporte sisReporte) {
		getSisReportes().remove(sisReporte);
		sisReporte.setSisEmpresa(null);

		return sisReporte;
	}

	public List<SisSucursal> getSisSucursals() {
		return this.sisSucursals;
	}

	public void setSisSucursals(List<SisSucursal> sisSucursals) {
		this.sisSucursals = sisSucursals;
	}

	public SisSucursal addSisSucursal(SisSucursal sisSucursal) {
		getSisSucursals().add(sisSucursal);
		sisSucursal.setSisEmpresa(this);

		return sisSucursal;
	}

	public SisSucursal removeSisSucursal(SisSucursal sisSucursal) {
		getSisSucursals().remove(sisSucursal);
		sisSucursal.setSisEmpresa(null);

		return sisSucursal;
	}

	public List<SisTipoObjeto> getSisTipoObjetos() {
		return this.sisTipoObjetos;
	}

	public void setSisTipoObjetos(List<SisTipoObjeto> sisTipoObjetos) {
		this.sisTipoObjetos = sisTipoObjetos;
	}

	public SisTipoObjeto addSisTipoObjeto(SisTipoObjeto sisTipoObjeto) {
		getSisTipoObjetos().add(sisTipoObjeto);
		sisTipoObjeto.setSisEmpresa(this);

		return sisTipoObjeto;
	}

	public SisTipoObjeto removeSisTipoObjeto(SisTipoObjeto sisTipoObjeto) {
		getSisTipoObjetos().remove(sisTipoObjeto);
		sisTipoObjeto.setSisEmpresa(null);

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
		sisUsuario.setSisEmpresa(this);

		return sisUsuario;
	}

	public SisUsuario removeSisUsuario(SisUsuario sisUsuario) {
		getSisUsuarios().remove(sisUsuario);
		sisUsuario.setSisEmpresa(null);

		return sisUsuario;
	}

	public List<RoControles> getRoControles() {
		return roControles;
	}

	public void setRoControles(List<RoControles> roControles) {
		this.roControles = roControles;
	}

}