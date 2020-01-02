package com.hc.ro.modelo;

import java.io.Serializable;
//import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the ro_detalle_evento database table.
 * 
 */
@Entity
@Table(name = "ro_detalle_evento")
@NamedQuery(name = "RoDetalleEvento.findAll", query = "SELECT r FROM RoDetalleEvento r")
public class RoDetalleEvento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_DEVE")
	private int codigoDeve;

	@Column(name = "COSTO_RECUP_DEVE")
	private float costoRecupDeve;

	@Column(name = "CUENTA_AFEC_DEVE")
	private String cuentaAfecDeve;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_OCUR_DEVE")
	private Date fechaOcurDeve;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_DESC_DEVE")
	private Date fechaDescDeve;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_REGIS_DEVE")
	private Date fechaRegisDeve;

	@Column(name = "CAUSA_DEVE")
	private String causaDeve;

	@Column(name = "DESCRIPCION_DEVE")
	private String descripcionDeve;

	@Column(name = "TIPO_CALIF_DEVE")
	private String tipoCalifDeve;

	@Column(name = "DESCRIPCION_DETALLADA_DEVE")
	private String descripcionDetalladaDeve;

	@Column(name = "MONTO_RECUP_DEVE")
	private float montoRecupDeve;

	@Column(name = "OTROS_GASTOS_DEVE")
	private float otrosGastosDeve;

	@Column(name = "PTO_CTRL_PROC_DEVE")
	private String ptoCtrlProcDeve;

	@Column(name = "REAL_RECUP_DEVE")
	private float realRecupDeve;

	@Column(name = "TIPO_COSTO")
	private int tipoCostoDeve;

	@Column(name = "PERDIDA_RESIDUAL_DEVE")
	private float perdidaResidualDeve;

	@Column(name = "VALOR_CAMBIO_DEVE")
	private float valorCambioDeve;

	@Column(name = "VALOR_DEVE")
	private float valorDeve;
	
	@Column(name = "VALOR_DEVE_DESPUES")
	private float valorDeveDespues;

	@Column(name = "NUM_OCUR")
	private int numOcur;
	
	@Column(name = "NUM_OCUR_DESPUES")
	private int numOcurDespues;
	
	private String denominacion;
	
	private float promedio;
	
	@Column(name="RIESGO_RESIDUAL")
	private float riesgoResidual;
	
	@Column(name="RIESGO_INHERENTE")
	private float riesgoInherente;
	
	private String clasificacion;
	
	@Column(name = "BLOQUEO")
	private int bloqueo;
	
	
	
	// bi-directional many-to-one association to RoControle
	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoControles> roControles;

	// bi-directional many-to-one association to RoDetCarctEvent
	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoDetCarctEvent> roDetCarctEvents;

	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoDeveFaro> roDeveFaros;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	
	@ManyToOne
	@JoinColumn(name = "CODIGO_TIPE")
	private RoTipoPerdida roTipoPerdida;

	@ManyToOne
	@JoinColumn(name = "CODIGO_USUA")
	private SisUsuario sisUsuario;

	// bi-directional many-to-one association to RoEvento
	@ManyToOne
	@JoinColumn(name = "CODIGO_EVEN")
	private RoEvento roEvento;

	// bi-directional many-to-one association to RoDepartamento
	@ManyToOne
	@JoinColumn(name = "CODIGO_DEPT")
	private RoDepartamento roDepartamento;

	// bi-directional many-to-one association to RoNegocio
	@ManyToOne
	@JoinColumn(name = "CODIGO_NEGO")
	private RoNegocio roNegocio1;

	// bi-directional many-to-one association to RoNegocio
	@ManyToOne
	@JoinColumn(name = "RO__CODIGO_NEGO")
	private RoNegocio roNegocio2;

	// bi-directional many-to-one association to RoProceso
	@ManyToOne
	@JoinColumn(name = "CODIGO_PROS")
	private RoProceso roProceso;

	// bi-directional many-to-one association to RoAgencia
	@ManyToOne
	@JoinColumn(name = "CODIGO_AGIA")
	private RoAgencia roAgencia;

	// bi-directional many-to-one association to RoFactorRiesgo
	@ManyToOne
	@JoinColumn(name = "CODIGO_FARO")
	private RoFactorRiesgo roFactorRiesgo;

	// bi-directional many-to-one association to RoTipoRecupera
	@ManyToOne
	@JoinColumn(name = "CODIGO_TREC")
	private RoTipoRecupera roTipoRecupera;

	// bi-directional many-to-one association to GenMoneda
	@ManyToOne
	@JoinColumn(name = "CODIGO_GMON")
	private GenMoneda genMoneda;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	@ManyToOne
	@JoinColumn(name = "CODIGO_PCONI_ANTES")
	private ParamConsecuenciaImpacto paramConsecuenciaImpactoAntes;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_PCONI_DESPUES")
	private ParamConsecuenciaImpacto paramConsecuenciaImpactoDespues;

	@ManyToOne
	@JoinColumn(name = "CODIGO_PPRR_ANTES")
	private ParamProbabilidadRiesgo paramProbabilidadRiesgoAntes;

	@ManyToOne
	@JoinColumn(name = "CODIGO_PPRR_DESPUES")
	private ParamProbabilidadRiesgo paramProbabilidadRiesgoDespues;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_ARIES_ANTES")
	private RoAriesgo roAriesgoAntes;

	@ManyToOne
	@JoinColumn(name = "CODIGO_ARIES_DESPUES")
	private RoAriesgo roAriesgoDespues;
	
	// bi-directional many-to-one association to RoEventoAfecta
	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoEventoAfecta> roEventoAfectas;

	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoControlEvento> roControlEventos;

	// bi-directional many-to-one association to RoEventoCosto
	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoEventoCosto> roEventoCostos;

	// bi-directional many-to-one association to RoEventoObjetivo
	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoEventoObjetivo> roEventoObjetivos;

	// bi-directional many-to-one association to RoEventoRecupera
	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoEventoRecupera> roEventoRecuperas;
	
	// bi-directional many-to-one association to RoEventoRecupera
	@OneToMany(mappedBy = "roDetalleEvento")
	private List<RoEventoIndicador> roEventoIndicadores;
	
	@Transient
	private String padre;
	
	

	public RoDetalleEvento() {
	}

	public RoDetalleEvento(int codigoDeve, float costoRecupDeve,
			String cuentaAfecDeve, Date fechaOcurDeve, Date fechaDescDeve,
			Date fechaRegisDeve, String causaDeve, String descripcionDeve,
			String tipoCalifDeve, String descripcionDetalladaDeve,
			float montoRecupDeve, float otrosGastosDeve,
			String ptoCtrlProcDeve, float realRecupDeve, int tipoCostoDeve,
			float perdidaResidualDeve, float valorCambioDeve, float valorDeve, int numOcur,
			float promedio, String denominacion, float riesgoResidual, String clasificacion, 
			int bloqueo, float riesgoInherente,
			
			RoTipoPerdida roTipoPerdida, SisUsuario sisUsuario,
			RoEvento roEvento, RoDepartamento roDepartamento,
			RoNegocio roNegocio1, RoProceso roProceso, RoAgencia roAgencia,
			RoFactorRiesgo roFactorRiesgo,
			ParamConsecuenciaImpacto paramConsecuenciaImpactoAntes,
			ParamConsecuenciaImpacto paramConsecuenciaImpactoDespues,
			ParamProbabilidadRiesgo paramProbabilidadRiesgoAntes,
			ParamProbabilidadRiesgo paramProbabilidadRiesgoDespues) {
		super();
		//System.out.println("Entro al constructor");
		this.codigoDeve = codigoDeve;
		this.costoRecupDeve = costoRecupDeve;
		this.cuentaAfecDeve = cuentaAfecDeve;
		this.fechaOcurDeve = fechaOcurDeve;
		this.fechaDescDeve = fechaDescDeve;
		this.fechaRegisDeve = fechaRegisDeve;
		this.causaDeve = causaDeve;
		this.descripcionDeve = descripcionDeve;
		this.tipoCalifDeve = tipoCalifDeve;
		this.descripcionDetalladaDeve = descripcionDetalladaDeve;
		this.montoRecupDeve = montoRecupDeve;
		this.otrosGastosDeve = otrosGastosDeve;
		this.ptoCtrlProcDeve = ptoCtrlProcDeve;
		this.realRecupDeve = realRecupDeve;
		this.tipoCostoDeve = tipoCostoDeve;
		this.perdidaResidualDeve = perdidaResidualDeve;
		this.valorCambioDeve = valorCambioDeve;
		this.valorDeve = valorDeve;
		this.numOcur=numOcur;
		this.paramConsecuenciaImpactoAntes = paramConsecuenciaImpactoAntes;
		this.roTipoPerdida = roTipoPerdida;
		this.sisUsuario = sisUsuario;
		this.roEvento = roEvento;
		this.roDepartamento = roDepartamento;
		this.roNegocio1 = roNegocio1;
		this.roProceso = roProceso;
		this.roAgencia = roAgencia;
		this.roFactorRiesgo = roFactorRiesgo;
		this.paramConsecuenciaImpactoDespues = paramConsecuenciaImpactoDespues;
		this.paramProbabilidadRiesgoAntes = paramProbabilidadRiesgoAntes;
		this.paramProbabilidadRiesgoDespues = paramProbabilidadRiesgoDespues;
		this.promedio = promedio;
		this.denominacion = denominacion;
		this.riesgoResidual = riesgoResidual;
		this.clasificacion = clasificacion;
		this.bloqueo = bloqueo;
		this.riesgoInherente=riesgoInherente;
		
	}

	public String getCuentaAfecDeve() {
		return this.cuentaAfecDeve;
	}

	public void setCuentaAfecDeve(String cuentaAfecDeve) {
		this.cuentaAfecDeve = cuentaAfecDeve;
	}

	public String getPtoCtrlProcDeve() {
		return this.ptoCtrlProcDeve;
	}

	public void setPtoCtrlProcDeve(String ptoCtrlProcDeve) {
		this.ptoCtrlProcDeve = ptoCtrlProcDeve;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public RoEvento getRoEvento() {
		return this.roEvento;
	}

	public void setRoEvento(RoEvento roEvento) {
		this.roEvento = roEvento;
	}

	public RoDepartamento getRoDepartamento() {
		return this.roDepartamento;
	}

	public void setRoDepartamento(RoDepartamento roDepartamento) {
		this.roDepartamento = roDepartamento;
	}

	public RoNegocio getRoNegocio1() {
		return this.roNegocio1;
	}

	public void setRoNegocio1(RoNegocio roNegocio1) {
		this.roNegocio1 = roNegocio1;
	}

	public RoNegocio getRoNegocio2() {
		return this.roNegocio2;
	}

	public void setRoNegocio2(RoNegocio roNegocio2) {
		this.roNegocio2 = roNegocio2;
	}

	public RoProceso getRoProceso() {
		return this.roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

	public RoAgencia getRoAgencia() {
		return this.roAgencia;
	}

	public void setRoAgencia(RoAgencia roAgencia) {
		this.roAgencia = roAgencia;
	}

	public RoFactorRiesgo getRoFactorRiesgo() {
		return this.roFactorRiesgo;
	}

	public void setRoFactorRiesgo(RoFactorRiesgo roFactorRiesgo) {
		this.roFactorRiesgo = roFactorRiesgo;
	}

	public RoTipoRecupera getRoTipoRecupera() {
		return this.roTipoRecupera;
	}

	public void setRoTipoRecupera(RoTipoRecupera roTipoRecupera) {
		this.roTipoRecupera = roTipoRecupera;
	}

	public GenMoneda getGenMoneda() {
		return this.genMoneda;
	}

	public void setGenMoneda(GenMoneda genMoneda) {
		this.genMoneda = genMoneda;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public List<RoEventoAfecta> getRoEventoAfectas() {
		return this.roEventoAfectas;
	}

	public void setRoEventoAfectas(List<RoEventoAfecta> roEventoAfectas) {
		this.roEventoAfectas = roEventoAfectas;
	}

	public RoEventoAfecta addRoEventoAfecta(RoEventoAfecta roEventoAfecta) {
		getRoEventoAfectas().add(roEventoAfecta);
		roEventoAfecta.setRoDetalleEvento(this);

		return roEventoAfecta;
	}

	public RoEventoAfecta removeRoEventoAfecta(RoEventoAfecta roEventoAfecta) {
		getRoEventoAfectas().remove(roEventoAfecta);
		roEventoAfecta.setRoDetalleEvento(null);

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
		roEventoCosto.setRoDetalleEvento(this);

		return roEventoCosto;
	}

	public RoEventoCosto removeRoEventoCosto(RoEventoCosto roEventoCosto) {
		getRoEventoCostos().remove(roEventoCosto);
		roEventoCosto.setRoDetalleEvento(null);

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
		roEventoObjetivo.setRoDetalleEvento(this);

		return roEventoObjetivo;
	}

	public RoEventoObjetivo removeRoEventoObjetivo(
			RoEventoObjetivo roEventoObjetivo) {
		getRoEventoObjetivos().remove(roEventoObjetivo);
		roEventoObjetivo.setRoDetalleEvento(null);

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
		roEventoRecupera.setRoDetalleEvento(this);

		return roEventoRecupera;
	}

	public RoEventoRecupera removeRoEventoRecupera(
			RoEventoRecupera roEventoRecupera) {
		getRoEventoRecuperas().remove(roEventoRecupera);
		roEventoRecupera.setRoDetalleEvento(null);

		return roEventoRecupera;
	}

	public List<RoDetCarctEvent> getRoDetCarctEvents() {
		return roDetCarctEvents;
	}

	public void setRoDetCarctEvents(List<RoDetCarctEvent> roDetCarctEvents) {
		this.roDetCarctEvents = roDetCarctEvents;
	}

	public SisUsuario getSisUsuario() {
		return sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

	public int getCodigoDeve() {
		return codigoDeve;
	}

	public void setCodigoDeve(int codigoDeve) {
		this.codigoDeve = codigoDeve;
	}

	public float getPerdidaResidualDeve() {
		return perdidaResidualDeve;
	}

	public void setPerdidaResidualDeve(float perdidaResidualDeve) {
		this.perdidaResidualDeve = perdidaResidualDeve;
	}

	public void setMontoRecupDeve(float montoRecupDeve) {
		this.montoRecupDeve = montoRecupDeve;
	}

	public void setRealRecupDeve(float realRecupDeve) {
		this.realRecupDeve = realRecupDeve;
	}

	public void setValorCambioDeve(float valorCambioDeve) {
		this.valorCambioDeve = valorCambioDeve;
	}

	public float getMontoRecupDeve() {
		return montoRecupDeve;
	}

	public float getOtrosGastosDeve() {
		return otrosGastosDeve;
	}

	public float getRealRecupDeve() {
		return realRecupDeve;
	}

	public float getValorCambioDeve() {
		return valorCambioDeve;
	}

	public float getCostoRecupDeve() {
		return costoRecupDeve;
	}

	public void setCostoRecupDeve(float costoRecupDeve) {
		this.costoRecupDeve = costoRecupDeve;
	}

	public int getTipoCostoDeve() {
		return tipoCostoDeve;
	}

	public void setTipoCostoDeve(int tipoCostoDeve) {
		this.tipoCostoDeve = tipoCostoDeve;
	}

	public float getValorDeve() {
		return valorDeve;
	}

	public void setValorDeve(float valorDeve) {
		this.valorDeve = valorDeve;
	}

	public Date getFechaOcurDeve() {
		return fechaOcurDeve;
	}

	public void setFechaOcurDeve(Date fechaOcurDeve) {
		this.fechaOcurDeve = fechaOcurDeve;
	}

	public Date getFechaDescDeve() {
		return fechaDescDeve;
	}

	public void setFechaDescDeve(Date fechaDescDeve) {
		this.fechaDescDeve = fechaDescDeve;
	}

	public Date getFechaRegisDeve() {
		return fechaRegisDeve;
	}

	public void setFechaRegisDeve(Date fechaRegisDeve) {
		this.fechaRegisDeve = fechaRegisDeve;
	}

	public String getCausaDeve() {
		return causaDeve;
	}

	public void setCausaDeve(String causaDeve) {
		this.causaDeve = causaDeve;
	}

	public String getDescripcionDeve() {
		return descripcionDeve;
	}

	public void setDescripcionDeve(String descripcionDeve) {
		this.descripcionDeve = descripcionDeve;
	}


	public void setDescripcionDetalladaDeve(String descripcionDetalladaDeve) {
		this.descripcionDetalladaDeve = descripcionDetalladaDeve;
	}

	public String getDescripcionDetalladaDeve() {
		return descripcionDetalladaDeve;
	}
	
	public void setOtrosGastosDeve(float otrosGastosDeve) {
		this.otrosGastosDeve = otrosGastosDeve;
	}

	public RoTipoPerdida getRoTipoPerdida() {
		return roTipoPerdida;
	}

	public void setRoTipoPerdida(RoTipoPerdida roTipoPerdida) {
		this.roTipoPerdida = roTipoPerdida;
	}

	public List<RoControlEvento> getRoControlEventos() {
		return roControlEventos;
	}

	public void setRoControlEventos(List<RoControlEvento> roControlEventos) {
		this.roControlEventos = roControlEventos;
	}

	public ParamConsecuenciaImpacto getParamConsecuenciaImpactoAntes() {
		return paramConsecuenciaImpactoAntes;
	}

	public void setParamConsecuenciaImpactoAntes(
			ParamConsecuenciaImpacto paramConsecuenciaImpactoAntes) {
		this.paramConsecuenciaImpactoAntes = paramConsecuenciaImpactoAntes;
	}

	public ParamConsecuenciaImpacto getParamConsecuenciaImpactoDespues() {
		return paramConsecuenciaImpactoDespues;
	}

	public void setParamConsecuenciaImpactoDespues(
			ParamConsecuenciaImpacto paramConsecuenciaImpactoDespues) {
		this.paramConsecuenciaImpactoDespues = paramConsecuenciaImpactoDespues;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoAntes() {
		return paramProbabilidadRiesgoAntes;
	}

	public void setParamProbabilidadRiesgoAntes(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoAntes) {
		this.paramProbabilidadRiesgoAntes = paramProbabilidadRiesgoAntes;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoDespues() {
		return paramProbabilidadRiesgoDespues;
	}

	public void setParamProbabilidadRiesgoDespues(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoDespues) {
		this.paramProbabilidadRiesgoDespues = paramProbabilidadRiesgoDespues;
	}

	public String getTipoCalifDeve() {
		return tipoCalifDeve;
	}

	public void setTipoCalifDeve(String tipoCalifDeve) {
		this.tipoCalifDeve = tipoCalifDeve;
	}

	public List<RoDeveFaro> getRoDeveFaros() {
		return roDeveFaros;
	}

	public void setRoDeveFaros(List<RoDeveFaro> roDeveFaros) {
		this.roDeveFaros = roDeveFaros;
	}

	public List<RoControles> getRoControles() {
		return roControles;
	}

	public void setRoControles(List<RoControles> roControles) {
		this.roControles = roControles;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public float getPromedio() {
		return promedio;
	}

	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}

	public float getRiesgoResidual() {
		return riesgoResidual;
	}

	public void setRiesgoResidual(float riesgoResidual) {
		this.riesgoResidual = riesgoResidual;
	}

	public int getNumOcur() {
		return (int) numOcur;
	}

	public void setNumOcur(int numOcur) {
		this.numOcur = numOcur;
	}

	public int getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(int bloqueo) {
		this.bloqueo = bloqueo;
	}

	public List<RoEventoIndicador> getRoEventoIndicadores() {
		return roEventoIndicadores;
	}

	public void setRoEventoIndicadores(List<RoEventoIndicador> roEventoIndicadores) {
		this.roEventoIndicadores = roEventoIndicadores;
	}

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}

	public float getRiesgoInherente() {
		return riesgoInherente;
	}

	public void setRiesgoInherente(float riesgoInherente) {
		this.riesgoInherente = riesgoInherente;
	}



	public float getValorDeveDespues() {
		return valorDeveDespues;
	}

	public void setValorDeveDespues(float valorDeveDespues) {
		this.valorDeveDespues = valorDeveDespues;
	}

	public int getNumOcurDespues() {
		return numOcurDespues;
	}

	public void setNumOcurDespues(int numOcurDespues) {
		this.numOcurDespues = numOcurDespues;
	}

	public RoAriesgo getRoAriesgoAntes() {
		return roAriesgoAntes;
	}

	public void setRoAriesgoAntes(RoAriesgo roAriesgoAntes) {
		this.roAriesgoAntes = roAriesgoAntes;
	}

	public RoAriesgo getRoAriesgoDespues() {
		return roAriesgoDespues;
	}

	public void setRoAriesgoDespues(RoAriesgo roAriesgoDespues) {
		this.roAriesgoDespues = roAriesgoDespues;
	}





	

}