package com.hc.ro.vista.admin.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.GenEstado;
import com.hc.ro.modelo.GenMoneda;
import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoAriesgo;
import com.hc.ro.modelo.RoAttrbAdicionale;
import com.hc.ro.modelo.RoCalifRiesgo;
import com.hc.ro.modelo.RoConsecuenciaImpacto;
import com.hc.ro.modelo.RoConstanteControl;
import com.hc.ro.modelo.RoControl;
import com.hc.ro.modelo.RoControlEvento;
import com.hc.ro.modelo.RoControlParamImpRep;
import com.hc.ro.modelo.RoControlValor;
import com.hc.ro.modelo.RoControles;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoDetCarctEvent;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoDeveFaro;
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoEventoCosto;
import com.hc.ro.modelo.RoEventoIndicador;
import com.hc.ro.modelo.RoEventoRecupera;
import com.hc.ro.modelo.RoFactorRiesgo;
import com.hc.ro.modelo.RoIndicaRsClave;
import com.hc.ro.modelo.RoTipoIndicadorRiesgo;
import com.hc.ro.modelo.RoNegoPro;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoPermisosDetalleEvento;
import com.hc.ro.modelo.RoProbabilidadEvento;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoRespAgencia;
import com.hc.ro.modelo.RoRespDepa;
import com.hc.ro.modelo.RoRespPro;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.RoTipoCosto;
import com.hc.ro.modelo.RoTipoPerdida;
import com.hc.ro.modelo.RoTipoRecupera;
import com.hc.ro.modelo.RoValAttrb;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioParamConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioParamProbabilidadRiesgo;
import com.hc.ro.negocio.ServicioRoAgencia;
import com.hc.ro.negocio.ServicioRoAriesgo;
import com.hc.ro.negocio.ServicioRoAttrbAdicionale;
import com.hc.ro.negocio.ServicioRoCalifRiesgo;
import com.hc.ro.negocio.ServicioRoConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioRoConstanteControl;
import com.hc.ro.negocio.ServicioRoControl;
import com.hc.ro.negocio.ServicioRoControlEvento;
import com.hc.ro.negocio.ServicioRoControlParamImpRep;
import com.hc.ro.negocio.ServicioRoControlValor;
import com.hc.ro.negocio.ServicioRoControles;
import com.hc.ro.negocio.ServicioRoDepartamento;
import com.hc.ro.negocio.ServicioRoDetCarctEvent;
import com.hc.ro.negocio.ServicioRoDetalleEvento;
import com.hc.ro.negocio.ServicioRoDeveFaro;
import com.hc.ro.negocio.ServicioRoEvento;
import com.hc.ro.negocio.ServicioRoEventoCosto;
import com.hc.ro.negocio.ServicioRoEventoIndicador;
import com.hc.ro.negocio.ServicioRoEventoRecupera;
import com.hc.ro.negocio.ServicioRoFactorRiesgo;
import com.hc.ro.negocio.ServicioRoTipoIndicadorRiesgo;
import com.hc.ro.negocio.ServicioRoNegoPros;
import com.hc.ro.negocio.ServicioRoNegocio;
import com.hc.ro.negocio.ServicioRoPermisosDetalleEvento;
import com.hc.ro.negocio.ServicioRoProbabilidadEvento;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoRespAgencia;
import com.hc.ro.negocio.ServicioRoRespDepa;
import com.hc.ro.negocio.ServicioRoRespPro;
import com.hc.ro.negocio.ServicioRoResponsable;
import com.hc.ro.negocio.ServicioRoTipoCosto;
import com.hc.ro.negocio.ServicioRoTipoPerdida;
import com.hc.ro.negocio.ServicioRoTipoRecupera;
import com.hc.ro.negocio.ServicioRoValAttrb;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;

@ManagedBean
@ViewScoped
public class ControladorCrudRoDetalleEvento {

	// DetalleEvento
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@EJB
	ServicioRoDeveFaro servicioRoDeveFaro;
	@EJB
	ServicioParamConsecuenciaImpacto servicioParamConsecuenciaImpacto;
	@EJB
	ServicioParamProbabilidadRiesgo servicioParamProbabilidadRiesgo;
	@EJB
	ServicioRoControl servicioRoControl;
	@EJB
	ServicioRoControlEvento servicioRoControlEvento;
	@EJB
	ServicioRoControlValor servicioRoControlValor;
	@EJB
	ServicioRoDetalleEvento servicioRoDetalleEvento;
	@EJB
	ServicioRoNegoPros servicioRoNegoPros;
	@EJB
	ServicioRoNegocio servicioRoNegocio;
	@EJB
	ServicioRoDetCarctEvent servicioRoDetCarctEvent;
	@EJB
	ServicioRoEventoCosto servicioRoEventoCosto;
	@EJB
	ServicioRoEventoRecupera servicioRoEventoRecupera;
	@EJB
	ServicioRoAgencia servicioRoAgencia;
	@EJB
	ServicioRoRespAgencia servicioRoRespAgencia;
	@EJB
	ServicioRoEvento servicioRoEvento;
	@EJB
	ServicioRoRespPro servicioRoRespPro;
	@EJB
	ServicioRoRespDepa servicioRoRespDepa;
	@EJB
	ServicioRoFactorRiesgo servicioRoFactorRiesgo;
	@EJB
	ServicioRoDepartamento servicioRoDepartamento;
	@EJB
	ServicioRoProceso servicioRoProceso;
	@EJB
	ServicioRoTipoCosto servicioRoTipoCosto;
	@EJB
	ServicioRoTipoRecupera servicioRoTipoRecupera;
	@EJB
	ServicioRoTipoPerdida servicioRoTipoPerdida;
	@EJB
	ServicioRoValAttrb servicioRoValAttr;
	@EJB
	ServicioRoAttrbAdicionale servicioRoAttrbAdicionale;
	@EJB
	ServicioRoPermisosDetalleEvento servicioRoPermisosDetalleEvento;
	@EJB
	ServicioRoConsecuenciaImpacto servicioRoConsecuenciaImpacto;
	@EJB
	ServicioRoTipoIndicadorRiesgo servicioRoTipoIndicadorRiesgo;
	@EJB
	ServicioRoEventoIndicador servicioRoEventoIndicador;
	@EJB
	ServicioRoAriesgo servicioRoAriesgo;
	@EJB
	ServicioRoCalifRiesgo servicioRoCalifRiesgo;

	public final static String nombrePagina = "/paginas/DetalleEvento.jsf";
	private boolean colCodigo;
	private boolean colPanelCualitativo;
	private boolean colPanelCualitativo2;
	private boolean colPanelCualitativoAux;
	private boolean colAgencia;
	private boolean colEvento;
	private boolean colProceso;
	// private boolean colSubProceso;
	private boolean colNumeroOcurrencias;
	private boolean colMoneda;
	private boolean colNegocio;
	private boolean colDepartamento;
	private boolean colFactorRiesgo;
	private boolean colValor;
	private boolean colPtoControl;
	private boolean colMonto;
	private boolean colValorCambio;
	private boolean colFechaOcurrencia;
	private boolean colFechaDescubrimiento;
	private boolean colFechaRegistro;
	private boolean colCosto;
	private boolean colReal;
	private boolean colCuenta;
	private boolean colOtrosGastos;

	private boolean colNumOcur;

	private boolean colOpEdicion;
	private boolean colBtnEditar;
	private boolean colTipoPerdida;
	private boolean colCausaProbable;
	private boolean colDescripcion;
	private boolean colDescripcionDetallada;
	private boolean colPromedio;
	private boolean colBloqueo;
	private boolean colRecupReal;
	private boolean colPerdResidual;
	private boolean colUsuario;
	private boolean colPanelAdicionales;
	private boolean colPanelCostos;
	private boolean colPanelRecuperaciones;
	private boolean colPanelIndicadoresRiesgo;

	private boolean reqCodigo;
	private boolean reqAgencia;
	private boolean reqEvento;
	private boolean reqProceso;
	private boolean reqMoneda;
	private boolean reqNegocio;
	private boolean reqDepartamento;
	private boolean reqFactorRiesgo;
	private boolean reqValor;
	private boolean reqPtoControl;
	private boolean reqMonto;
	private boolean reqValorCambio;
	private boolean reqFechaOcurrencia;
	private boolean reqFechaDescubrimiento;
	private boolean reqFechaRegistro;
	private boolean reqCosto;
	private boolean reqReal;
	private boolean reqCuenta;
	private boolean reqOtrosGastos;
	private boolean reqNumOcur;

	private boolean reqOpEdicion;
	private boolean reqBtnEditar;
	private boolean reqTipoPerdida;
	private boolean reqCausaProbable;
	private boolean reqDescripcion;
	private boolean reqDescripcionDetallada;
	private boolean reqBloqueo;
	private boolean reqRecupReal;
	private boolean reqPerdResidual;
	private boolean reqUsuario;
	private boolean reqPanelAdicionales;
	private boolean reqPanelCostos;
	private boolean reqPanelRecuperaciones;
	private boolean reqPanelCualitativo;

	private boolean disPanelAdicionales;
	private boolean disPanelCostos;
	private boolean disPanelRecuperaciones;
	private boolean disPanelIndicadores;
	private boolean disFechaRegistro;
	private boolean disRecupReal;
	private boolean disPerdResidual;
	private boolean disUsuario;
	private boolean disDescripcion;
	private boolean disDescripcionDetallada;
	private boolean disBloqueo;
	private boolean disCausaProbable;
	private boolean disTipoPerdida;
	private boolean disFechaOcurrencia;
	private boolean disFechaDescubrimiento;
	private boolean disCodigo;
	private boolean disAgencia;
	private boolean disEvento;
	private boolean disProceso;
	private boolean disMoneda;
	private boolean disNegocio;
	private boolean disDepartamento;
	private boolean disFactorRiesgo;
	private boolean disValor;
	private boolean disPtoControl;
	private boolean disMonto;
	private boolean disValorCambio;
	private boolean disFecha;
	private boolean disCosto;
	private boolean disReal;
	private boolean disCuenta;
	private boolean disOtrosGastos;
	private boolean disNumOcur;

	private boolean disOpEdicion;
	private boolean disPanelCualitativo;

	private boolean disBtnEditar;

	private boolean cmbValor;
	private boolean cmbValorControl;
	private boolean txtValor;

	private boolean tipoGuardar;

	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnAnadirCarac;
	private boolean btnInsertarCarac;
	private boolean btnAnadirCosto;
	private boolean btnAnadirRecup;
	private boolean btnAnadirIndicador;
	private boolean btnEditar;
	private boolean btnSeleccionar;
	private boolean pnlRoDetalleEvento;
	private boolean pnlRoDetalleEventoEditar;
	private boolean pnlEditarCualitativo;
	private boolean btnAnadirCualitativo;
	private boolean txtValorControl;
	private boolean txtValorControl2;
	private boolean btnGuardar;
	private boolean btnGuardarEditado;
	private boolean btnCancelar;
	private boolean btnCancelarEditado;

	private boolean valBloqueado;

	private boolean esPorcentaje;

	private boolean pnlProbabilidad;

	private boolean bolDespuesDeControles;
	// VARIABLES
	private RoControlEvento roControlEventoVista;
	private RoDetalleEvento roDetalleEventoVista;
	private RoDetalleEvento roDetalleEventoControlador;
	private RoDetCarctEvent roDetCarctEventVista;
	private RoTipoCosto roTipoCostoVista;
	private RoTipoRecupera roTipoRecuperaVista;
	private RoTipoIndicadorRiesgo roTipoIndicadorRiesgoVista;

	private RoEventoCosto roEventoCosto;
	private SisUsuario sisUsuario;
	private RoEventoRecupera roEventoRecupera;
	private RoRespPro roRespProceso;
	private RoProceso roProceso;

	// Indicador de riesgo
	private BigDecimal valorEvin;
	private Date fechaEvin;
	private List<RoEventoIndicador> indicadoresDetalleEventoTodos;
	private int idTipoIndicadorRiesgo;
	// private RoTipoIndicadorRiesgo tipoIndicadorRiesgoSeleccionado;
	private RoEventoIndicador roEventoIndicadorVista;
	private double sumaValorIndicadores;
	private BigDecimal porcentajeEvin;
	private Double totalTransaccionesEvin;

	private float costoEvre;
	private float valorEvre;
	private String valorControl;
	private String control;
	private String califSeleccionada;
	private int idEventoSeleccionado;
	private int idAgenciaSeleccionada;

	private int idProcesoSeleccionado;
	private String nombreProcesoSeleccionado;
	private int idSubProcesoSeleccionado;

	private String ancestroProceso;
	private int idNegocioSeleccionado;
	private int idDepartamentoSeleccionado;
	private int idMonedaSeleccionada;
	private int idFactorSeleccionado;
	private int idTipoPerdida;
	private int idControlEvento;
	private int idValControl;
	private int idAttrAdicional;
	private int idTipoCosto;
	private int idTipoRecupera;

	private int cantidadEvco;
	private int cantidadEvre;
	private String nombreProbabilidadAntes;
	private int idProbabilidadDespues;
	private int idConsecuenciaAntes;
	private int idConsecuenciaDespues;
	private Date fechaEvre;

	private int idValAttrb;

	private int idDetalleEventoSeleccionado;
	private int indiceTabla;
	// combos detalle evento
	private List<RoAgencia> agenciasUsuario;
	private List<RoEvento> eventosTodos;
	private List<RoProceso> procesosUsuario;
	private List<RoProceso> subprocesosUsuario;
	private List<GenMoneda> monedasTodos;
	private List<RoNegocio> negociosProcesos;
	private List<RoDepartamento> departamentosTodos;
	private List<RoFactorRiesgo> factorRiesgoTodos;
	private List<RoControl> controlesTodos;
	private List<ParamProbabilidadRiesgo> probabilidadesTodos;
	private List<ParamConsecuenciaImpacto> consecuenciaTodos;
	// listas
	private List<RoControlEvento> controlesEvento;
	private List<RoControlValor> roControlValores;
	private List<RoDetalleEvento> detalleEventosVisibles;
	private List<GenEstado> estadosTodos;
	private List<RoEventoCosto> costosDetalleEvento;
	private List<RoEventoRecupera> recuperacionesDetalleEvento;
	private List<RoDetCarctEvent> atributosDetalleEvento;
	private List<RoAttrbAdicionale> atributosDetalleTodos;
	private List<RoValAttrb> roValAttrbs;
	private List<RoTipoCosto> tipoCostoTodos;
	private List<RoTipoPerdida> tipoPerdidaTodos;
	private List<RoTipoRecupera> tipoRecuperaTodos;
	private List<RoTipoIndicadorRiesgo> tipoIndicadorTodos;
	private List<RoTipoIndicadorRiesgo> tipoIndicadoresPorProcesoDeve;
	private RoTipoIndicadorRiesgo tipoIndicadorVista;
	private String tipoIndicador;
	private List<RoConstanteControl> constantesControlTodas;

	private boolean pnlEditarCostos;

	private boolean pnlEditarRecupera;
	private boolean pnlEditarIndicador;

	private String valorDetAdic;

	private String ptoCtrlProcDeve;

	private String causaProbable;

	// private String numeroOcurrencias;

	private String descDetalleEvento;

	private String descDetallada;

	private float otrosGastosDeve;
	private float otrosGastosDeveDespues;
	private int numOcur;
	private int numOcurDespues;

	private boolean pnlEditarDetalles;

	private boolean mostrarPanelControles;

	private boolean mostrarPanelAdicionales;

	private Date fechaOcurrencia;

	private Date fechaDescubrimiento;

	private RoPermisosDetalleEvento permisosDetalleEvento;

	// deve faro
	private RoDeveFaro roDeveFaro;
	private List<RoDeveFaro> deveFarosDetalleEvento;
	private boolean btnAnadirDeveFaro;
	private boolean pnlEditarDeveFaro;
	private int idFactorRiesgo;

	// private List<Ro>

	// ----------------------------------------------------------------------------------------------------------------//
	// Variables del antiguo bean Controladores

	@EJB
	ServicioRoControles servicioRoControles;
	@EJB
	ServicioRoProbabilidadEvento servicioRoProbabilidadEvento;
	@EJB
	ServicioRoResponsable servicioRoResponsable;
	@EJB
	ServicioRoConstanteControl servicioRoConstanteControl;

	@EJB
	ServicioRoControlParamImpRep servicioRoControlParamImpRep;
	private RoControles roControlesControlador;
	private RoControles roControlesVista;
	private List<RoControles> listaRoControles;

	private ParamProbabilidadRiesgo paramProbabilidadRiesgoControlador;
	private ParamProbabilidadRiesgo paramProbabilidadRiesgoVista;
	private List<ParamProbabilidadRiesgo> listaParamProbabilidadRiesgo;

	private RoProbabilidadEvento roProbabilidadEventoControlador;
	private RoProbabilidadEvento roProbabilidadEventoVista;
	private List<RoProbabilidadEvento> listaRoProbabilidadEvento;

	private RoConsecuenciaImpacto roConsecuenciaImpactoContrololador;
	private RoConsecuenciaImpacto roConsecuenciaImpactoVista;
	private List<RoConsecuenciaImpacto> listaRoConsecuenciaImpacto;

	private RoResponsable roResponsableControlador;
	private RoResponsable roResponsableVista;
	private List<RoResponsable> listaRoResponsable;

	public boolean tipoGuardarControl;
	private boolean pnlRoTipoControl;
	private boolean btnAñadirControl;
	private boolean btnGuardarControl;
	private boolean btnCancelarControl;

	private boolean disableSelectionDetalleEvento;

	private int idCalcProbSeleccionado;
	private int idImpactoEconómico;

	private int calculoProbSeleccionado;
	// private int idTipoRiesgo;
	private int idResponsable;

	private BigDecimal riesgoResidual = new BigDecimal("0.00");
	private String calculoDenomDelControl;
	private String clasificacionFinalDelRiesgo;
	private double valorPerdida = 0.00f;

	private BigDecimal promedioControl = new BigDecimal("0.00");
	private RoProbabilidadEvento roProbabilidadVista;

	// Panel Control - Variables
	private BigDecimal calREP = new BigDecimal("0.00"); // REP
	private BigDecimal calBCP = new BigDecimal("0.00"); // BCP
	private BigDecimal calLEG = new BigDecimal("0.00"); // LEG
	private double perdida = 0.00f; // impacto economico
	private BigDecimal calcIm = new BigDecimal("0.00"); // cálculo impacto
	private BigDecimal mult = new BigDecimal("0.00"); // cálculo producto
	private int efectividad = 0; // cálculo efectividad
	private BigDecimal calP = new BigDecimal("0.00");// cálculo periodicidad
	private BigDecimal calT = new BigDecimal("0.00");// cálculo tipo
	private BigDecimal calF = new BigDecimal("0.00");// cálculo forma
	private BigDecimal calD = new BigDecimal("0.00");// cálculo documentado
	private BigDecimal calEF = new BigDecimal("0.00");// cálculo formalizado
	private BigDecimal calSA = new BigDecimal("0.00");// cálculo aplica
	private BigDecimal valoracion = new BigDecimal("0.00");// valoración
															// eficiencia
	private BigDecimal valoracionDelControls = new BigDecimal("0.00"); // valoración
																		// control

	// PARÁMETROS DEL CONTROL
	private List<RoControlParamImpRep> paramImpRepTodos;

	private Long idParamImpRep;

	// ----------------------------------------------------------------------------------------------------------------//

	public ControladorCrudRoDetalleEvento() {
		super();
		System.out.println("*Constructor*");

		// tipoIndicadorRiesgoSeleccionado=new RoTipoIndicadorRiesgo();
		roDeveFaro = new RoDeveFaro();
		deveFarosDetalleEvento = new ArrayList<RoDeveFaro>();
		consecuenciaTodos = new ArrayList<ParamConsecuenciaImpacto>();
		probabilidadesTodos = new ArrayList<ParamProbabilidadRiesgo>();
		roControlValores = new ArrayList<RoControlValor>();
		roControlEventoVista = new RoControlEvento();
		permisosDetalleEvento = new RoPermisosDetalleEvento();
		roDetalleEventoControlador = new RoDetalleEvento();
		roDetalleEventoVista = new RoDetalleEvento();
		roDetCarctEventVista = new RoDetCarctEvent();
		roTipoCostoVista = new RoTipoCosto();
		roTipoRecuperaVista = new RoTipoRecupera();
		roTipoIndicadorRiesgoVista = new RoTipoIndicadorRiesgo();
		roEventoCosto = new RoEventoCosto();
		roEventoRecupera = new RoEventoRecupera();

		tipoCostoTodos = new ArrayList<RoTipoCosto>();
		tipoRecuperaTodos = new ArrayList<RoTipoRecupera>();

		roValAttrbs = new ArrayList<RoValAttrb>();
		atributosDetalleTodos = new ArrayList<RoAttrbAdicionale>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
		agenciasUsuario = new ArrayList<RoAgencia>();
		eventosTodos = new ArrayList<RoEvento>();
		controlesTodos = new ArrayList<RoControl>();
		procesosUsuario = new ArrayList<RoProceso>();
		subprocesosUsuario = new ArrayList<RoProceso>();
		monedasTodos = new ArrayList<GenMoneda>();
		negociosProcesos = new ArrayList<RoNegocio>();
		departamentosTodos = new ArrayList<RoDepartamento>();
		factorRiesgoTodos = new ArrayList<RoFactorRiesgo>();
		tipoPerdidaTodos = new ArrayList<RoTipoPerdida>();
		costosDetalleEvento = new ArrayList<RoEventoCosto>();
		recuperacionesDetalleEvento = new ArrayList<RoEventoRecupera>();
		atributosDetalleEvento = new ArrayList<RoDetCarctEvent>();
		detalleEventosVisibles = new ArrayList<RoDetalleEvento>();
		sisUsuario = new SisUsuario();

		ptoCtrlProcDeve = "";
		otrosGastosDeve = 0;
		otrosGastosDeveDespues = 0;
		numOcur = 0;
		numOcurDespues = 0;

		indicadoresDetalleEventoTodos = new ArrayList<RoEventoIndicador>();
		// indicadoresDetalleEventoTodosPorProceso= new
		// ArrayList<RoEventoIndicador>();
		roRespProceso = new RoRespPro();
		roProceso = new RoProceso();

		// Variables antiguo bean
		roControlesControlador = new RoControles();
		roControlesVista = new RoControles();
		listaRoControles = new ArrayList<RoControles>();

		paramProbabilidadRiesgoControlador = new ParamProbabilidadRiesgo();
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		listaParamProbabilidadRiesgo = new ArrayList<ParamProbabilidadRiesgo>();

		roProbabilidadEventoControlador = new RoProbabilidadEvento();
		roProbabilidadEventoVista = new RoProbabilidadEvento();
		listaRoProbabilidadEvento = new ArrayList<RoProbabilidadEvento>();

		roConsecuenciaImpactoVista = new RoConsecuenciaImpacto();
		roConsecuenciaImpactoContrololador = new RoConsecuenciaImpacto();
		listaRoConsecuenciaImpacto = new ArrayList<RoConsecuenciaImpacto>();

		roResponsableControlador = new RoResponsable();
		roResponsableVista = new RoResponsable();
		listaRoResponsable = new ArrayList<RoResponsable>();

		roProbabilidadVista = new RoProbabilidadEvento();

		roEventoIndicadorVista = new RoEventoIndicador();
		sumaValorIndicadores = 0;
		esPorcentaje = false;
		mostrarPanelControles = false;

		idTipoIndicadorRiesgo = 0;
		porcentajeEvin = new BigDecimal(0);
		totalTransaccionesEvin = new Double(0);

		constantesControlTodas = new ArrayList<RoConstanteControl>();

		disableSelectionDetalleEvento = false;

		nombreProbabilidadAntes = "";
		idConsecuenciaAntes = 0;
		idProbabilidadDespues = 0;
		idConsecuenciaDespues = 0;

		bolDespuesDeControles = false;
		// PARÁMETROS DEL CONTROL
		paramImpRepTodos = new ArrayList<RoControlParamImpRep>();
	}

	@PostConstruct
	public void PostControladorCrudRoDetalleEvento() {
		System.out.println("*Post constructor*");
		tipoIndicadorVista = new RoTipoIndicadorRiesgo();
		tipoIndicador = "";
		// detalleEventosVisibles = servicioRoDetalleEvento
		// .buscarEventoPorUsuarioAux(sisUsuario);
		// // detalleEventosVisibles = servicioRoDetalleEvento.buscarTodosAux();
		try {
			controladorMenuPrincipal.controlarAcceso();

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			dataManagerSesion.controlarAcceso(nombrePagina);

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			sisUsuario = dataManagerSesion.getUsuarioSesion();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			permisosDetalleEvento = servicioRoPermisosDetalleEvento
					.buscarPermisoPorPerfil(sisUsuario.getSisPerfil()
							.getNombrePerf());

			colCodigo = transformarBool(permisosDetalleEvento.getColCodigo());
			colAgencia = transformarBool(permisosDetalleEvento.getColAgencia());
			colEvento = transformarBool(permisosDetalleEvento.getColEvento());
			colProceso = transformarBool(permisosDetalleEvento.getColProceso());
			// colSubProceso =
			// transformarBool(permisosDetalleEvento.getColSubProceso());
			colNegocio = transformarBool(permisosDetalleEvento.getColNegocio());
			colDepartamento = transformarBool(permisosDetalleEvento
					.getColDepartamento());
			colFactorRiesgo = transformarBool(permisosDetalleEvento
					.getColFactorRiesgo());
			colTipoPerdida = transformarBool(permisosDetalleEvento
					.getColTipoPerdida());
			colPtoControl = transformarBool(permisosDetalleEvento
					.getColPtoControl());
			colOtrosGastos = transformarBool(permisosDetalleEvento
					.getColOtrosGastos());

			colNumOcur = transformarBool(permisosDetalleEvento.getColNumOcur());

			colFechaOcurrencia = transformarBool(permisosDetalleEvento
					.getColFechaOcurrencia());
			colFechaDescubrimiento = transformarBool(permisosDetalleEvento
					.getColFechaDescubrimiento());
			colFechaRegistro = transformarBool(permisosDetalleEvento
					.getColFechaRegistro());
			colCausaProbable = transformarBool(permisosDetalleEvento
					.getColCausaProbable());

			colDescripcion = transformarBool(permisosDetalleEvento
					.getColDescripcion());
			colDescripcionDetallada = transformarBool(permisosDetalleEvento
					.getColDescripcionDetallada());
			colPromedio = transformarBool(permisosDetalleEvento
					.getColPromedio());
			colBloqueo = transformarBool(permisosDetalleEvento.getColBloqueo());
			// System.out.println("PostControlador:colbloqueo" + colBloqueo);
			colValor = transformarBool(permisosDetalleEvento.getColValor());
			colMonto = transformarBool(permisosDetalleEvento.getColMonto());
			colCosto = transformarBool(permisosDetalleEvento.getColCosto());
			colRecupReal = transformarBool(permisosDetalleEvento
					.getColRecupReal());
			colPerdResidual = transformarBool(permisosDetalleEvento
					.getColPerdResidual());
			colUsuario = transformarBool(permisosDetalleEvento.getColUsuario());
			colBtnEditar = transformarBool(permisosDetalleEvento
					.getColBtnEditar());
			colPanelAdicionales = transformarBool(permisosDetalleEvento
					.getColPanelAdicionales());
			colPanelCualitativo = transformarBool(permisosDetalleEvento
					.getColPanelCualitativo());
			colPanelCualitativoAux = colPanelCualitativo;
			colPanelCualitativo2 = transformarBool(permisosDetalleEvento
					.getColPanelCualitativo());
			colPanelCostos = transformarBool(permisosDetalleEvento
					.getColPanelCostos());
			colPanelRecuperaciones = transformarBool(permisosDetalleEvento
					.getColPanelRecuperaciones());
			// System.out.println("colPanelIndicadoresRiesgo"
			// + permisosDetalleEvento.getColPanelIndicadores());

			colPanelIndicadoresRiesgo = transformarBool(permisosDetalleEvento
					.getColPanelIndicadores());
			// System.out.println("colPanelIndicadoresRiesgo"
			// + colPanelIndicadoresRiesgo);
			disCodigo = transformarBool(permisosDetalleEvento.getDisCodigo());
			disAgencia = transformarBool(permisosDetalleEvento.getDisAgencia());
			disEvento = transformarBool(permisosDetalleEvento.getDisEvento());
			disProceso = transformarBool(permisosDetalleEvento.getDisProceso());
			disNegocio = transformarBool(permisosDetalleEvento.getDisNegocio());
			disDepartamento = transformarBool(permisosDetalleEvento
					.getDisDepartamento());
			disFactorRiesgo = transformarBool(permisosDetalleEvento
					.getDisFactorRiesgo());
			disTipoPerdida = transformarBool(permisosDetalleEvento
					.getDisTipoPerdida());
			disPtoControl = transformarBool(permisosDetalleEvento
					.getDisPtoControl());
			disOtrosGastos = transformarBool(permisosDetalleEvento
					.getDisOtrosGastos());
			disNumOcur = transformarBool(permisosDetalleEvento.getDisNumOcur());

			disFechaOcurrencia = transformarBool(permisosDetalleEvento
					.getDisFechaOcurrencia());
			disFechaDescubrimiento = transformarBool(permisosDetalleEvento
					.getDisFechaDescubrimiento());
			disFechaRegistro = transformarBool(permisosDetalleEvento
					.getDisFechaRegistro());
			disCausaProbable = transformarBool(permisosDetalleEvento
					.getDisCausaProbable());
			disDescripcion = transformarBool(permisosDetalleEvento
					.getDisDescripcion());
			disDescripcionDetallada = transformarBool(permisosDetalleEvento
					.getDisDescripcionDetallada());
			disBloqueo = transformarBool(permisosDetalleEvento.getDisBloqueo());
			disValor = transformarBool(permisosDetalleEvento.getDisValor());
			disMonto = transformarBool(permisosDetalleEvento.getDisMonto());
			disCosto = transformarBool(permisosDetalleEvento.getDisCosto());
			disRecupReal = transformarBool(permisosDetalleEvento
					.getDisRecupReal());
			disPerdResidual = transformarBool(permisosDetalleEvento
					.getDisPerdResidual());
			disUsuario = transformarBool(permisosDetalleEvento.getDisUsuario());
			disBtnEditar = transformarBool(permisosDetalleEvento
					.getDisBtnEditar());
			disPanelAdicionales = transformarBool(permisosDetalleEvento
					.getDisPanelAdicionales());
			disPanelCualitativo = transformarBool(permisosDetalleEvento
					.getDisPanelCualitativo());
			disPanelCostos = transformarBool(permisosDetalleEvento
					.getDisPanelCostos());
			disPanelRecuperaciones = transformarBool(permisosDetalleEvento
					.getDisPanelRecuperaciones());

			disPanelIndicadores = transformarBool(permisosDetalleEvento
					.getDisPanelIndicadores());

			reqCodigo = !transformarBool(permisosDetalleEvento.getDisCodigo());
			reqAgencia = !transformarBool(permisosDetalleEvento.getDisAgencia());
			reqEvento = !transformarBool(permisosDetalleEvento.getDisEvento());
			reqProceso = !transformarBool(permisosDetalleEvento.getDisProceso());
			reqNegocio = !transformarBool(permisosDetalleEvento.getDisNegocio());
			reqDepartamento = !transformarBool(permisosDetalleEvento
					.getDisDepartamento());
			reqFactorRiesgo = !transformarBool(permisosDetalleEvento
					.getDisFactorRiesgo());
			reqTipoPerdida = !transformarBool(permisosDetalleEvento
					.getDisTipoPerdida());
			reqPtoControl = !transformarBool(permisosDetalleEvento
					.getDisPtoControl());
			reqOtrosGastos = !transformarBool(permisosDetalleEvento
					.getDisOtrosGastos());
			// que hace la siguiente linea??
			reqNumOcur = !transformarBool(permisosDetalleEvento.getDisNumOcur());
			reqFechaOcurrencia = !transformarBool(permisosDetalleEvento
					.getDisFechaOcurrencia());
			reqFechaDescubrimiento = !transformarBool(permisosDetalleEvento
					.getDisFechaDescubrimiento());
			reqFechaRegistro = !transformarBool(permisosDetalleEvento
					.getDisFechaRegistro());
			reqCausaProbable = !transformarBool(permisosDetalleEvento
					.getDisCausaProbable());
			reqDescripcion = !transformarBool(permisosDetalleEvento
					.getDisDescripcion());
			reqDescripcionDetallada = !transformarBool(permisosDetalleEvento
					.getDisDescripcionDetallada());
			reqBloqueo = !transformarBool(permisosDetalleEvento.getDisBloqueo());
			reqValor = !transformarBool(permisosDetalleEvento.getDisValor());
			reqMonto = !transformarBool(permisosDetalleEvento.getDisMonto());
			reqCosto = !transformarBool(permisosDetalleEvento.getDisCosto());
			reqRecupReal = !transformarBool(permisosDetalleEvento
					.getDisRecupReal());
			reqPerdResidual = !transformarBool(permisosDetalleEvento
					.getDisPerdResidual());
			reqUsuario = !transformarBool(permisosDetalleEvento.getDisUsuario());
			reqBtnEditar = !transformarBool(permisosDetalleEvento
					.getDisBtnEditar());
			reqPanelAdicionales = !transformarBool(permisosDetalleEvento
					.getDisPanelAdicionales());
			reqPanelCostos = !transformarBool(permisosDetalleEvento
					.getDisPanelCostos());
			reqPanelRecuperaciones = !transformarBool(permisosDetalleEvento
					.getDisPanelRecuperaciones());
			reqPanelCualitativo = !transformarBool(permisosDetalleEvento
					.getDisPanelCualitativo());

			pnlProbabilidad = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		setCalifSeleccionada("Cuantitativo");

		// //////agregar agencias todas si es superusuario
		probabilidadesTodos = servicioParamProbabilidadRiesgo
				.buscarTodos_codigoPprr_nombrePprr();

		consecuenciaTodos = servicioParamConsecuenciaImpacto
				.buscarTodos_codigoPconi_nombrePconi_numeroPconi();
		cargarComboAgencias();
		cargarComboProcesos();
		cargarComboNegocios();
		cargarComboDepartamentos();
		// cargarComboSubProcesosPorProcesos(idProcesoSeleccionado);

		tipoCostoTodos = servicioRoTipoCosto.buscarTodosAux();
		tipoRecuperaTodos = servicioRoTipoRecupera.buscarTodosAux();
		factorRiesgoTodos = servicioRoFactorRiesgo.buscarTodosAux();
		tipoPerdidaTodos = servicioRoTipoPerdida
				.buscarPorTipoRegistroAux(califSeleccionada);
		eventosTodos = servicioRoEvento.buscarTodosNombreCodigo();

		atributosDetalleTodos = servicioRoAttrbAdicionale
				.buscarTodosNombreCodigo();
		controlesTodos = servicioRoControl.buscarTodosNombreCodigo();

		try {
			idAttrAdicional = atributosDetalleTodos.get(0).getCodigoAttr();
			roValAttrbs = servicioRoValAttr
					.buscarPorAttrbCodigoAtributo(idAttrAdicional);
			roControlValores = servicioRoControlValor
					.buscarPorCodigoControl(idControlEvento);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// ////////////// agregar eventos todos si es superusuario
		try {
			cargarDetallesEventoVisibles();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// paneles visibles??
		pnlRoDetalleEvento = false;
		pnlRoDetalleEventoEditar = false;
		pnlEditarCostos = false;
		pnlEditarRecupera = false;
		pnlEditarIndicador = false;
		btnGuardar = false;
		btnGuardarEditado = false;
		btnCancelar = false;
		btnCancelarEditado = false;

		// ////// columnas /////////////
		// colAgencia = true;
		// colCodigo = true;
		// colCosto = true;
		// colCosto = true;
		// colCuenta = true;
		// colDepartamento = true;
		// colEvento = true;
		// colFactorRiesgo = true;
		// colMoneda = true;
		// colMonto = true;
		// colNegocio = true;
		// colOpEdicion = true;
		// colOtrosGastos = true;
		// colProceso = true;
		// colPtoControl = true;
		// colReal = true;
		// colValor = true;
		// colValorCambio = true;
		// colBtnEditar = true;
		// colRecupReal = true;
		// colPerdResidual = true;
		// colUsuario = true;
		// ////// botones ///// deshabilitados? /////////
		btnEditar = false;
		btnSeleccionar = false;
		btnAnadir = false;
		btnAnadirCarac = true;
		btnAnadirCualitativo = true;
		btnAnadirCosto = true;
		btnAnadirRecup = true;
		btnAnadirIndicador = true;
		btnInsertarCarac = true;

		cmbValor = true;
		txtValor = false;
		cmbValorControl = true;
		txtValorControl = false;
		txtValorControl2 = true;

		// Antiguo controlador

		// listaRoControles = servicioRoControles.buscarTodos();
		listaRoProbabilidadEvento = servicioRoProbabilidadEvento.buscarTodos();
		listaRoConsecuenciaImpacto = servicioRoConsecuenciaImpacto
				.buscarTodos();
		listaParamProbabilidadRiesgo = servicioParamProbabilidadRiesgo
				.buscarTodos();
		listaRoResponsable = servicioRoResponsable.buscarTodos();

		pnlRoTipoControl = false;
		btnAñadirControl = false;
		btnGuardarControl = true;
		btnCancelarControl = true;

		nombrarPprr();

		valBloqueado = true; // btnbloqueado
		// System.out.println("PostControlador: btnBloqueado: " + valBloqueado);
		try {
			tipoIndicadorTodos = servicioRoTipoIndicadorRiesgo.buscarTodos();
			constantesControlTodas = servicioRoConstanteControl.buscarTodos();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// PARÁMETROS DEL CONTROL
		try {
			paramImpRepTodos = servicioRoControlParamImpRep.buscarTodos();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		idParamImpRep = (long) 0.0;

	}

	// -------------- ** MÉTODOS DEL BEAN ** -------------------//

	public void nuevoControl() {
		System.out.println("Nuevo Control...");

		// variables de la clase
		calREP = new BigDecimal("0.00"); // REP
		calBCP = new BigDecimal("0.00"); // BCP
		calLEG = new BigDecimal("0.00"); // LEG
		perdida = 0.00f; // impacto economico
		calcIm = new BigDecimal("0.00"); // cálculo impacto
		mult = new BigDecimal("0.00"); // cálculo producto
		efectividad = 0; // cálculo efectividad
		calP = new BigDecimal("0.00");// cálculo periodicidad
		calT = new BigDecimal("0.00");// cálculo tipo
		calF = new BigDecimal("0.00");// cálculo forma
		calD = new BigDecimal("0.00");// cálculo documentado
		calEF = new BigDecimal("0.00");// cálculo formalizado
		calSA = new BigDecimal("0.00");// cálculo aplica
		valoracion = new BigDecimal("0.00");// valoración eficiencia
		valoracionDelControls = new BigDecimal("0.00"); // valoración control

		System.out.println();

		roControlesVista = new RoControles();
		// roControlesVista.setRep("");

		roControlesVista.setBcp("");
		roControlesVista.setLeg("");
		roProbabilidadEventoVista = new RoProbabilidadEvento();
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		roResponsableVista = new RoResponsable();
		pnlRoTipoControl = true;
		tipoGuardarControl = true;
		btnAñadirControl = true;
		idCalcProbSeleccionado = 0;
		idImpactoEconómico = listaRoConsecuenciaImpacto.get(0).getCODIGO_cons();
		// idTipoRiesgo = listaParamProbabilidadRiesgo.get(0).getCodigoPprr();
		idResponsable = listaRoResponsable.get(0).getCodigoResp();
		// idTipoRiesgo = 0;
		idResponsable = 0;
		roProbabilidadVista = new RoProbabilidadEvento();

		idParamImpRep = (long) 0;

		calculoImpacto();

	}

	public BigDecimal buscarValorConstante(String[] nombreConstante) {
		BigDecimal valorConstante = new BigDecimal(0);
		System.out.println("-Metodo Buscar valor de Constante");
		boolean detenerFor = false;

		for (RoConstanteControl roConstante : constantesControlTodas) {
			for (String name : nombreConstante) {
				if (roConstante.getNombreCte().toLowerCase()
						.equals(name.toLowerCase())) {
					valorConstante = roConstante.getValorCte();

					detenerFor = true;
				} else if (roConstante.getNombreCte().toLowerCase()
						.contains(name.toLowerCase())) {
					valorConstante = roConstante.getValorCte();

					detenerFor = true;
				}
				if (detenerFor)
					break;
			}
			if (detenerFor)
				break;
		}
		return valorConstante;
	}

	public void nombrarPprr() {
		System.out.println("Nombrando Parametro Probabilidad...");

		try {
			for (RoProbabilidadEvento item : listaRoProbabilidadEvento) {
				item.setNombrePprr(servicioParamProbabilidadRiesgo
						.buscarProbabilidadRiesgoPorNumero(item.getCodigoPprr())
						.getNombrePprr());
				item.setLetraPprr(servicioParamProbabilidadRiesgo
						.buscarProbabilidadRiesgoPorNumero(item.getCodigoPprr())
						.getLetraPprr());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean habilitarProbabilidad() {
		System.out.println("Verificando habilitar selección de probabilidad");

		if (idParamImpRep != 0 && !roControlesVista.getBcp().isEmpty()
				&& !roControlesVista.getLeg().isEmpty()) {
			pnlProbabilidad = false;
		}
		return pnlProbabilidad;
	}

	public int seleccionarCalcProb() {
		System.out.println("Seleccionando Cálculo de Probabilidad...");

		System.out.println(roProbabilidadEventoVista.getCalculoProb());
		System.out.println(roProbabilidadEventoVista.getCODIGO_prob());
		idCalcProbSeleccionado = roProbabilidadEventoVista.getCalculoProb();
		calcProducto(roProbabilidadEventoVista.getCODIGO_prob());
		System.out.println(calcProducto(roProbabilidadEventoVista
				.getCODIGO_prob()));
		// RequestContext.getCurrentInstance().update(
		// ":formDetalleEvento:txtCalculoProducto");

		return idCalcProbSeleccionado;
	}

	public BigDecimal calcProducto(int idC) {
		System.out.println("Calculo del producto...");

		calculoProbSeleccionado = servicioRoControles.buscarCalculoProb(idC);
		BigDecimal a1 = new BigDecimal(this.calculoProbSeleccionado);
		BigDecimal aux = new BigDecimal("0.00");
		aux = a1.multiply(calcIm);
		Double dod = Math.ceil(Double.parseDouble(aux.toString()));
		mult = new BigDecimal(dod.toString());
		// RequestContext.getCurrentInstance().update(
		// ":formDetalleEvento:txtCalculoProducto");
		return mult;
	}

	public BigDecimal calcImpact() {

		System.out.println("Calculo del Impacto...");

		// BigDecimal cte = new BigDecimal("0.25");
		// BigDecimal cteA = new BigDecimal("0.15");
		// BigDecimal cteN = new BigDecimal("0.35");

		BigDecimal cteR = new BigDecimal("0");
		BigDecimal cteC = new BigDecimal("0");
		BigDecimal cteL = new BigDecimal("0");
		BigDecimal cteE = new BigDecimal("0");

		BigDecimal Bcp = new BigDecimal(this.calBCP.toString());
		BigDecimal Rep = new BigDecimal(this.calREP.toString());
		BigDecimal Leg = new BigDecimal(this.calLEG.toString());
		BigDecimal ImpactEcon = new BigDecimal(this.perdida);

		// Rep.multiply(cte);
		// Bcp.multiply(cte);
		// Leg.multiply(cteA);
		// ImpactEcon.multiply(cteN);

		// BigDecimal d1 = new BigDecimal("0.00");
		// BigDecimal d2 = new BigDecimal("0.00");
		// BigDecimal d3 = new BigDecimal("0.00");
		// BigDecimal d4 = new BigDecimal("0.00");
		// d1 = Rep.multiply(cte);
		// d2 = Bcp.multiply(cte);
		// d3 = ImpactEcon.multiply(cteN);
		// d4 = d1.add(d2);
		// calcIm =
		// Rep.multiply(cte).add(Bcp.multiply(cte)).add(Leg.multiply(cteA)).add(ImpactEcon.multiply(cteN));
		// System.out.println("Obteniendo valores de constantes...");
		String[] nombreConstante = new String[] {};
		try {
			System.out
					.println(" Obteniendo Constantes(cteR,cteC,cteL,cteE)...");
			nombreConstante = new String[] { "reputacion", "reputación" };
			cteR = buscarValorConstante(nombreConstante);
			// System.out.println(" - cteR: " + cteR.doubleValue());

			nombreConstante = new String[] { "continuidad" };
			cteC = buscarValorConstante(nombreConstante);
			// System.out.println(" - cteC: " + cteC.doubleValue());

			nombreConstante = new String[] { "legal" };
			cteL = buscarValorConstante(nombreConstante);
			// System.out.println(" - cteL: " + cteL.doubleValue());

			nombreConstante = new String[] { "económico", "economico" };
			cteE = buscarValorConstante(nombreConstante);
			// System.out.println(" - cteE: " + cteE.doubleValue());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			cteR = new BigDecimal("0.25");
			cteC = new BigDecimal("0.25");
			cteL = new BigDecimal("0.15");
			cteE = new BigDecimal("0.35");
		}

		System.out.println("cteR: " + cteR.floatValue());
		System.out.println("cteC: " + cteC.floatValue());
		System.out.println("cteL: " + cteL.floatValue());
		System.out.println("cteE: " + cteE.floatValue());

		calcIm = Rep.multiply(cteR).add(Bcp.multiply(cteC))
				.add(Leg.multiply(cteL)).add(ImpactEcon.multiply(cteE));
		calcIm = calcIm.setScale(0, BigDecimal.ROUND_UP);
		// calcIm = Rep.multiply(cte).add(Bcp.multiply(cte))
		// .add(Leg.multiply(cteA)).add(ImpactEcon.multiply(cteN));

		System.out.println("*Cálculo ipacto= " + calcIm);

		return calcIm;
	}

	public Integer calcEfectividad() {
		System.out.println("Cálculo de la efectividad...");

		if (roControlesVista.getEfectividad().equals("Ninguna")) {
			efectividad = 1;
		} else if (roControlesVista.getEfectividad().equals(
				"Mal diseñado no cumple con el fin")) {
			efectividad = 2;
		} else if (roControlesVista.getEfectividad().equals(
				"Reduce solo algún aspescto del riesgo")) {
			efectividad = 3;
		} else if (roControlesVista.getEfectividad().equals(
				"Su diseño reduce los principales efectos del riesgo")) {
			efectividad = 4;
		} else if (roControlesVista.getEfectividad().equals(
				"Su diseño elimina en su totalidad el riesgo")) {
			efectividad = 5;
		}
		return efectividad;
	}

	public BigDecimal valoracionEficiencia() {
		System.out.println("Valoración de la Eficiencia");
		BigDecimal aw = new BigDecimal(this.calP.toString());
		BigDecimal ax = new BigDecimal(this.calT.toString());
		BigDecimal ay = new BigDecimal(this.calF.toString());
		// BigDecimal a = new BigDecimal("0.30");
		// BigDecimal b = new BigDecimal("0.35");

		BigDecimal cteF = new BigDecimal(0);// cte forma
		BigDecimal cteP = new BigDecimal(0); // cte periodicidad
		BigDecimal cteT = new BigDecimal(0); // cte tipo

		String[] nombreConstante = new String[] {};
		try {
			nombreConstante = new String[] { "forma" };
			cteF = buscarValorConstante(nombreConstante);
			nombreConstante = new String[] { "periodicidad" };
			cteP = buscarValorConstante(nombreConstante);
			nombreConstante = new String[] { "tipo" };
			cteT = buscarValorConstante(nombreConstante);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			cteF = new BigDecimal(0.30);
			cteP = new BigDecimal(0.35);
			cteT = new BigDecimal(0.35);
		}
		System.out.println("cteF: " + cteF.floatValue());
		System.out.println("cteP: " + cteP.floatValue());
		System.out.println("cteT: " + cteT.floatValue());

		// valoracion = aw.multiply(b).add(ax.multiply(b)).add(ay.multiply(a));
		valoracion = aw.multiply(cteP).add(ax.multiply(cteT))
				.add(ay.multiply(cteF)).setScale(2, BigDecimal.ROUND_HALF_UP);
		return valoracion;
	}

	public BigDecimal valoracionDelControl() {
		System.out.println("Valoración del Control...");

		BigDecimal aux = new BigDecimal("0.00");

		// BigDecimal a = new BigDecimal("0.10");
		// BigDecimal b = new BigDecimal("0.20");
		// BigDecimal c = new BigDecimal("0.30");

		BigDecimal ap = new BigDecimal(this.efectividad); // efectividad
		System.out.println("efectividad" + ap);
		BigDecimal az = new BigDecimal(this.calD.toString()); // documentado
		System.out.println("documentado" + az);
		BigDecimal ba = new BigDecimal(this.calEF.toString()); // formalizado
		System.out.println("formalizado" + ba);
		BigDecimal bb = new BigDecimal(this.calSA.toString()); // Si Aplica
		System.out.println("Si Aplica" + bb);
		BigDecimal bd = new BigDecimal(this.valoracion.toString()); // valoracion
																	// eficiencia
		System.out.println("valoracion eficiencia" + bd);

		BigDecimal cteD = new BigDecimal("0"); // documentado
		BigDecimal cteF = new BigDecimal("0"); // formalizado
		BigDecimal cteSA = new BigDecimal("0"); // si aplica
		BigDecimal cteVE = new BigDecimal("0"); // valoracion eficiencia
		BigDecimal cteCE = new BigDecimal("0"); // calificacion efectividad

		String[] nombreConstante = new String[] {};
		try {

			System.out.println(" Obteniendo Constantes...");

			nombreConstante = new String[] { "documentado" };
			cteD = buscarValorConstante(nombreConstante);
			nombreConstante = new String[] { "formalizado" };
			cteF = buscarValorConstante(nombreConstante);
			nombreConstante = new String[] { "aplica" };
			cteSA = buscarValorConstante(nombreConstante);
			nombreConstante = new String[] { "eficiencia" };
			cteVE = buscarValorConstante(nombreConstante);
			nombreConstante = new String[] { "efectividad" };
			cteCE = buscarValorConstante(nombreConstante);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			cteD = new BigDecimal("0.10"); // documentado
			cteF = new BigDecimal("0.10"); // formalizado
			cteSA = new BigDecimal("0.20"); // si aplica
			cteVE = new BigDecimal("0.30"); // valoracion eficiencia
			cteCE = new BigDecimal("0.30"); // calificacion efectividad

		}

		System.out.println("cteD: " + cteD.floatValue());
		System.out.println("cteF: " + cteF.floatValue());
		System.out.println("cteSA: " + cteSA.floatValue());
		System.out.println("cteVE: " + cteVE.floatValue());
		System.out.println("cteCE: " + cteCE.floatValue());

		aux = ap.multiply(cteCE).add(az.multiply(cteD)).add(ba.multiply(cteF))
				.add(bb.multiply(cteSA)).add(bd.multiply(cteVE));

		// aux = ap.multiply(c).add(az.multiply(a)).add(ba.multiply(a))
		// .add(bb.multiply(b)).add(bd.multiply(c));
		System.out.println("El valor de la valoracion control es: " + aux);

		// Double aauxx = Math.floor(Double.parseDouble(aux.toString()));
		Double aauxx = Double.parseDouble(aux.toString());
		System.out.println("Veamos que nos sale: " + aauxx);
		// int nuevo = BigDecimal.parseInt(aux);

		valoracionDelControls = new BigDecimal(aauxx.toString()).setScale(0,
				BigDecimal.ROUND_DOWN);
		System.out.println("Que imprime al final: " + valoracionDelControls);
		return valoracionDelControls;
	}

	// PARAM IMPACTO REPUTACION

	public BigDecimal calculoREP() {
		System.out.println("Calculo de reputación...");

		try {
			roControlesVista.setParamImpRep(servicioRoControlParamImpRep
					.buscarPorId(idParamImpRep));

			calREP = roControlesVista.getParamImpRep().getValorRep();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		// if (roControlesVista.getRep().equals("No Afecta")) {
		// calREP = new BigDecimal("1.00");
		// } else if (roControlesVista.getRep().equals(
		// "Afecta Imagen ante trabajadores")) {
		// calREP = new BigDecimal("2.00");
		// } else if (roControlesVista.getRep().equals(
		// "Afecta imagen ante un cliente")) {
		// calREP = new BigDecimal("3.00");
		// } else if (roControlesVista.getRep().equals(
		// "Afecta imagen ante clientes de agencia(s)")) {
		// calREP = new BigDecimal("4.00");
		// } else if (roControlesVista.getRep().equals(
		// "Afecta imagen ante todos los clientes y socios")) {
		// calREP = new BigDecimal("5.00");
		// }
		return calREP;
	}

	public void actualizar() {

	}

	public BigDecimal calculoBCP() {
		System.out.println("Calculo BCP...");

		if (roControlesVista.getBcp().equals("NO AFECTA")) {
			calBCP = new BigDecimal("1.00");
		} else if (roControlesVista.getBcp().equals(
				"AFECTA A USUARIO INTERNO Y NO SS AL SOCIO")) {
			calBCP = new BigDecimal("2.00");
		} else if (roControlesVista.getBcp().equals(
				"AFECTA A DEP. INTERNO PERO NO A SS SOCIO")) {
			calBCP = new BigDecimal("3.00");
		} else if (roControlesVista.getBcp().equals(
				"AFECTA EN SUSPENSION DE ALGUN SERVICIO AL SOCIO")) {
			calBCP = new BigDecimal("4.00");
		} else if (roControlesVista.getBcp().equals(
				"AFECTA EN LA SUSPENSION DE TODOS LOS SERVICIOS")) {
			calBCP = new BigDecimal("5.00");
		}
		return calBCP;
	}

	public BigDecimal calculoLEG() {
		System.out.println("Cálculo de legalidad...");
		System.out.println(">>>" + roControlesVista.getLeg().toString());

		if (roControlesVista
				.getLeg()
				.equals("Genera o da lugar a Observaciones por parte de los entes de control")) {
			calLEG = new BigDecimal("1.00");
			System.out.println(">>>" + calLEG.byteValue());
		} else if (roControlesVista
				.getLeg()
				.equals("Sanciones de carácter administrativo y/o restitución de valores a socios (multas por parte del O. de control)")) {
			calLEG = new BigDecimal("2.00");
			System.out.println(">>>" + calLEG.byteValue());
		} else if (roControlesVista
				.getLeg()
				.equals("Pérdidas derivadas de la inadecuada instrumentación de operaciones (valores irrecuperables)")) {
			calLEG = new BigDecimal("3.00");
			System.out.println(">>>" + calLEG.byteValue());
		} else if (roControlesVista.getLeg().equals(
				"Pago de Indemnizaciones (daños y perjuicios)")) {
			calLEG = new BigDecimal("4.00");
			System.out.println(">>>" + calLEG.byteValue());
		} else if (roControlesVista.getLeg()
				.equals("Liquidación de la entidad")) {
			calLEG = new BigDecimal("5.00");
			System.out.println(">>>" + calLEG.byteValue());
		} else if (roControlesVista.getLeg().equals("No Aplica")) {
			calLEG = new BigDecimal("0.00");
			System.out.println(">>>" + calLEG.byteValue());

		}
		return calLEG;
	}

	public Double calculoImpacto() {
		System.out.println("Cálculo de impacto...");

		// System.out.println("El valor de la perdida es: " + valorPerdida);
		if (valorPerdida >= 0.01 && valorPerdida <= 2800) {
			perdida = 1;
		} else if (valorPerdida >= 2801 && valorPerdida <= 8600) {
			perdida = 2;
		} else if (valorPerdida >= 8601 && valorPerdida <= 29000) {
			perdida = 3;
		} else if (valorPerdida >= 29001 && valorPerdida <= 147000) {
			perdida = 4;
		} else if (valorPerdida >= 147001) {
			perdida = 5;
		}
		return perdida;
	}

	public BigDecimal calculoP() {
		System.out.println("Cálculo P...");

		if (roControlesVista.getPeriodicidad().equals("NINGUNA")) {
			calP = new BigDecimal("1.00");
		} else if (roControlesVista.getPeriodicidad().equals(
				"POR LO MENOS UNA VEZ AL AÑO")) {
			calP = new BigDecimal("2.00");
		} else if (roControlesVista.getPeriodicidad().equals(
				"OCASIONAL O CUANDO SE REQUIERA")) {
			calP = new BigDecimal("3.00");
		} else if (roControlesVista.getPeriodicidad().equals(
				"PERIODICO QUE INVOLUCRA ALGUNAS VECES EN EL AÑO")) {
			calP = new BigDecimal("4.00");
		} else if (roControlesVista.getPeriodicidad().equals("PERMANENTE")) {
			calP = new BigDecimal("5.00");
		}
		return calP;
	}

	public BigDecimal calculoT() {
		System.out.println("Cálculo T...");

		if (roControlesVista.getTipo().equals("NINGUNO")) {
			calT = new BigDecimal("1.00");
		} else if (roControlesVista.getTipo().equals("CORRECTIVO")) {
			calT = new BigDecimal("2.00");
		} else if (roControlesVista.getTipo().equals("DETECTIVO")) {
			calT = new BigDecimal("3.00");
		} else if (roControlesVista.getTipo().equals("PREVENTIVO")) {
			calT = new BigDecimal("5.00");
		}
		return calT;
	}

	public BigDecimal calculoF() {
		System.out.println("Cálculo F...");

		if (roControlesVista.getForma().equals("NINGUNO")) {
			calF = new BigDecimal("1.00");
		} else if (roControlesVista.getForma().equals("MANUAL")) {
			calF = new BigDecimal("2.00");
		} else if (roControlesVista.getForma().equals("MIXTO")) {
			calF = new BigDecimal("3.00");
		} else if (roControlesVista.getForma().equals("AUTOMÁTICO")) {
			calF = new BigDecimal("5.00");
		}
		return calF;
	}

	public BigDecimal calculoD() {
		System.out.println("Cálculo D...");

		if (roControlesVista.getEstaDoc().equals("SI")) {
			calD = new BigDecimal("5.00");
		} else if (roControlesVista.getEstaDoc().equals("NO")) {
			calD = new BigDecimal("1.00");
		}
		return calD;
	}

	public BigDecimal calculoEF() {
		System.out.println("Cálculo EF...");

		if (roControlesVista.getEstaFor().equals("SI")) {
			calEF = new BigDecimal("5.00");
		} else if (roControlesVista.getEstaFor().equals("NO")) {
			calEF = new BigDecimal("1.00");
		}
		return calEF;
	}

	public BigDecimal calculoSA() {
		System.out.println("Cálculo SA...");
		if (roControlesVista.getSeAplica().equals("SI")) {
			calSA = new BigDecimal("5.00");
		} else if (roControlesVista.getSeAplica().equals("NO")) {
			calSA = new BigDecimal("1.00");
		}
		return calSA;
	}

	public void calculoPromedio() {
		// servicioRoControles.promedioValorControles(roDetalleEventoVista)

	}

	public String calculoDenominacionDelControl(
			RoDetalleEvento roDetalleEventoVista) {
		System.out.println("Cálculo Denominación...");
		double val = servicioRoControles
				.promedioValorControles(roDetalleEventoVista);

		System.out.println("El promedio de valor de controles es: " + val);
		// double val = this.valoracionDelControl.doubleValue();
		if (val <= 0) {
			calculoDenomDelControl = "";
		} else if (val <= 1) {
			calculoDenomDelControl = "NINGUNO";
		} else if (val <= 2) {
			calculoDenomDelControl = "DEFICIENTE";
		} else if (val <= 3) {
			calculoDenomDelControl = "VULNERABLE";
		} else if (val <= 4) {
			calculoDenomDelControl = "MODERADO";
		} else if (val <= 5) {
			calculoDenomDelControl = "OPTIMO";
		}

		return calculoDenomDelControl;
	}

	public BigDecimal calculoRiesgoInherente() {
		System.out.println("*Cálculo de Riesgo Inherente...");
		BigDecimal riesgoInherente = new BigDecimal(0);
		double a = 0;
		double b = 0;

		a = idCalcProbSeleccionado;
		b = calcIm.doubleValue();

		riesgoInherente = new BigDecimal(a * b)
				.setScale(0, BigDecimal.ROUND_UP);
		System.out.println("*Riesgo Inherente: " + riesgoInherente);
		return riesgoInherente;
	}

	// public Double calculoRiesgoResidual(RoDetalleEvento roDetalleEventoVista)
	// {
	// System.out.println("Cálculo Riesgo Residual...");
	// double a = this.valoracionDelControls.doubleValue();
	// double b =
	// servicioRoControles.promedioValorControles(roDetalleEventoVista);
	// if (a <= 3) {
	// riesgoResidual = a;
	// } else {
	// riesgoResidual = (a / b);
	// }
	// return riesgoResidual;
	// }
	public BigDecimal calculoRiesgoResidual(RoDetalleEvento roDetalleEventoVista) {
		System.out.println("Cálculo Riesgo Residual...");
		Integer b = new Integer(calculoRiesgoInherente().intValue());
		double a = this.valoracionDelControls.doubleValue();
		// double b =
		// servicioRoControles.promedioValorControles(roDetalleEventoVista);
		if (a <= 3) {
			riesgoResidual = new BigDecimal(b.intValue()).setScale(0,
					BigDecimal.ROUND_UP);
		} else {
			riesgoResidual = new BigDecimal(b / a).setScale(0,
					BigDecimal.ROUND_UP);
		}

		System.out.println("--Riesgo Residual: " + riesgoResidual);
		return riesgoResidual;
	}

	public String calculoClasificacionFinalDelRiesgo() {
		System.out.println("Cálculo Clasificación Final...");

		double val = servicioRoControles
				.promedioRiesgoResidualControles(roDetalleEventoVista);
		System.out.println("El promedio de riesgo residual de controles es: "
				+ val);

		if (val <= 0) {
			clasificacionFinalDelRiesgo = "";
		} else if (val <= 3) {
			clasificacionFinalDelRiesgo = "RIESGO INUSUAL";
		} else if (val <= 6) {
			clasificacionFinalDelRiesgo = "RIESGO BAJO";
		} else if (val <= 12) {
			clasificacionFinalDelRiesgo = "RIESGO MEDIO";
		} else if (val <= 16) {
			clasificacionFinalDelRiesgo = "RIESGO ALTO";
		} else if (val <= 25) {
			clasificacionFinalDelRiesgo = "RIESGO EXTREMO";
		}

		return clasificacionFinalDelRiesgo;
	}

	public void exitoGuardarControles() {
		roControlesVista = new RoControles();
		roProbabilidadEventoVista = new RoProbabilidadEvento();
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		roResponsableVista = new RoResponsable();
		listaRoControles = servicioRoControles
				.buscarControlesPorEvento(roDetalleEventoVista);
		tipoGuardarControl = false;
		pnlRoTipoControl = false;
		btnAñadirControl = false;

		cargarDetallesEventoVisibles();

		// System.out.println(">>>>>>>"+btnAñadirControl);
	}

	public void guardarRoTipoControl() {
		System.out.println("Guardando Control...");
		roControlesVista.setRoDetalleEvento(roDetalleEventoVista);
		// System.out.println("El codigo es "
		// + roDetalleEventoVista.getCodigoDeve());
		if (tipoGuardarControl) {
			roControlesVista.setCodigoControles(new BigDecimal(0));
			// roControlesVista
			// .setRoProbabilidadEvento(servicioRoProbabilidadEvento
			// .buscarPorId(idCalcProbSeleccionado));
			roControlesVista
					.setRoProbabilidadEvento(servicioRoProbabilidadEvento
							.buscarPorId(roProbabilidadEventoVista
									.getCODIGO_prob()));
			// System.out.println("codigo: "+roProbabilidadEventoVista.getCalculoProb());
			// roControlesVista.setRoConsecuenciaImpacto(servicioRoConsecuenciaImpacto.buscarPorId(idImpactoEconómico));
			// roControlesVista.setImpactEcon(this.calcIm);
			roControlesVista.setCalcImpact(this.calcIm);
			roControlesVista.setProducto(this.mult);
			// roControlesVista
			// .setParamProbabilidadRiesgo(servicioParamProbabilidadRiesgo
			// .buscarPorId(idTipoRiesgo));

			roControlesVista
					.setParamProbabilidadRiesgo(servicioParamProbabilidadRiesgo
							.buscarProbabilidadRiesgoPorNumero(roProbabilidadEventoVista
									.getCodigoPprr()));
			System.out.println("Control vista param prob codigo pprr"
					+ roControlesVista.getParamProbabilidadRiesgo()
							.getCodigoPprr());
			roControlesVista.setRoResponsable(servicioRoResponsable
					.buscarPorId(idResponsable));
			roControlesVista.setCalcEfect(new BigDecimal(this.efectividad));
			roControlesVista.setValorEfic(this.valoracion);
			roControlesVista.setValorControl(this.valoracionDelControls);
			roControlesVista.setCalcPerio(this.calP);
			roControlesVista.setCalcTipo(this.calT);
			roControlesVista.setCalcForm(this.calF);
			roControlesVista.setCalcEstaDocu(this.calD);
			roControlesVista.setCalcEstaFormal(this.calEF);
			roControlesVista.setCalcSeAplica(this.calSA);
			roControlesVista.setCalRep(this.calREP);
			roControlesVista.setCalBcp(this.calBCP);
			roControlesVista.setCalLeg(this.calLEG);
			roControlesVista.setImpactoeco(new BigDecimal(this.perdida));
			roControlesVista
					.setRiesgoResi(calculoRiesgoResidual(roDetalleEventoVista));
			roControlesVista.setRiesgoInhe(calculoRiesgoInherente());
			// Campos que siempre se va a repetir

			servicioRoControles.insertar(roControlesVista);
			System.out.println("El promedio es: "
					+ servicioRoControles
							.promedioValorControles(roDetalleEventoVista));
			System.out.println("La denominacion es: "
					+ calculoDenominacionDelControl(roDetalleEventoVista));
			System.out.println("El Riesgo residual es: "
					+ calculoRiesgoResidual(roDetalleEventoVista));
			System.out.println("La Clasificacion es: "
					+ calculoClasificacionFinalDelRiesgo());
			try {

				servicioRoDetalleEvento.actualizarPromedio(
						new Float(servicioRoControles
								.promedioValorControles(roDetalleEventoVista)),
						roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarDenominacion(
						calculoDenominacionDelControl(roDetalleEventoVista),
						roDetalleEventoVista.getCodigoDeve());
				// servicioRoDetalleEvento.actualizarRiesgoResidual(
				// calculoRiesgoResidual(roDetalleEventoVista).floatValue(),
				// roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento
						.actualizarRiesgoResidual(
								new Float(
										servicioRoControles
												.promedioRiesgoResidualControles(roDetalleEventoVista)),
								roDetalleEventoVista.getCodigoDeve());

				servicioRoDetalleEvento.actualizarClasificacion(
						calculoClasificacionFinalDelRiesgo(),
						roDetalleEventoVista.getCodigoDeve());

				// servicioRoDetalleEvento.actualizarRiesgoInherente(
				// calculoRiesgoInherente().floatValue(),
				// roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarRiesgoInherente(
						servicioRoControles.promedioRiesgoInherenteControles(
								roDetalleEventoVista).floatValue(),
						roDetalleEventoVista.getCodigoDeve());

				System.out.println("**calculoRiesgoInherente()"
						+ calculoRiesgoInherente().floatValue());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Control Añadido",
					"El Control ha sido Añadido con Exito"));
			exitoGuardarControles();

		} else {
			// roControlesVista.setCodigoControles(new BigDecimal (0));
			roControlesVista
					.setRoProbabilidadEvento(servicioRoProbabilidadEvento
							.buscarPorId(idCalcProbSeleccionado));
			// roControlesVista.setRoConsecuenciaImpacto(servicioRoConsecuenciaImpacto.buscarPorId(idImpactoEconómico));
			// roControlesVista.setImpactEcon(this.calcIm);
			roControlesVista.setCalcImpact(this.calcIm);
			roControlesVista.setProducto(this.mult);
			// roControlesVista
			// .setParamProbabilidadRiesgo(servicioParamProbabilidadRiesgo
			// .buscarPorId(idTipoRiesgo));
			roControlesVista.setRoResponsable(servicioRoResponsable
					.buscarPorId(idResponsable));
			roControlesVista.setCalcEfect(new BigDecimal(this.efectividad));
			roControlesVista.setValorEfic(this.valoracion);
			roControlesVista.setValorControl(this.valoracionDelControls);
			roControlesVista.setCalcPerio(this.calP);
			roControlesVista.setCalcTipo(this.calT);
			roControlesVista.setCalcForm(this.calF);
			roControlesVista.setCalcEstaDocu(this.calD);
			roControlesVista.setCalcEstaFormal(this.calEF);
			roControlesVista.setCalcSeAplica(this.calSA);
			roControlesVista.setCalRep(this.calREP);
			roControlesVista.setCalBcp(this.calBCP);
			roControlesVista.setCalLeg(this.calLEG);
			roControlesVista.setImpactoeco(new BigDecimal(this.perdida));

			servicioRoControles.actualizar(roControlesVista);
			try {

				servicioRoDetalleEvento.actualizarPromedio(
						new Float(servicioRoControles
								.promedioValorControles(roDetalleEventoVista)),
						roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarDenominacion(
						calculoDenominacionDelControl(roDetalleEventoVista),
						roDetalleEventoVista.getCodigoDeve());
				// servicioRoDetalleEvento.actualizarRiesgoResidual(
				// calculoRiesgoResidual(roDetalleEventoVista).floatValue(),
				// roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento
						.actualizarRiesgoResidual(
								new Float(
										servicioRoControles
												.promedioRiesgoResidualControles(roDetalleEventoVista)),
								roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarClasificacion(
						calculoClasificacionFinalDelRiesgo(),
						roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarRiesgoInherente(
						calculoRiesgoInherente().floatValue(),
						roDetalleEventoVista.getCodigoDeve());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Control Actualizado",
					"El Control ha sido Añadido con Exito"));
			exitoGuardarControles();

		}
	}

	public void cancelarControles() {
		Double aux = servicioRoControles
				.promedioValorControles(roDetalleEventoVista);
		try {
			promedioControl = new BigDecimal(aux.toString());
			servicioRoControles.actualizarCampo(promedioControl,
					roDetalleEventoVista);
			System.out.println("El promedio de estos controles es; "
					+ promedioControl);
		} catch (Exception e) {
			// TODO: handle exception
		}
		listaRoControles = servicioRoControles
				.buscarControlesPorEvento(roDetalleEventoVista);
		tipoGuardarControl = false;
		pnlRoTipoControl = false;
		btnAñadirControl = false;
	}

	public void editarRoControles() {
		System.out.println("Editando Control...");
		pnlProbabilidad = true;
		tipoGuardarControl = false;
		pnlRoTipoControl = true;
		btnAñadirControl = true;

		// PARAM IMPACTO REPUTACION
		idParamImpRep = roControlesVista.getParamImpRep().getCodigoRep();

		// calculos
		calculoREP();
		calculoBCP();

		calculoLEG();
		calculoEF();
		calcImpact();

		calculoClasificacionFinalDelRiesgo();

		// idCalcProbSeleccionado=
		System.out
				.println("Nombre:"
						+ roControlesVista.getParamProbabilidadRiesgo()
								.getCodigoPprr());
		System.out
				.println("num:"
						+ roControlesVista.getParamProbabilidadRiesgo()
								.getNumeroPprr());
		System.out.println("letra:"
				+ roControlesVista.getParamProbabilidadRiesgo().getLetraPprr());
		System.out
				.println("nombre:"
						+ roControlesVista.getParamProbabilidadRiesgo()
								.getNombrePprr());

		// nullpointer

	}

	public void borrarControl() {

		try {
			servicioRoControles.eliminar(roControlesVista);
			try {

				System.out.println("Controlador/promedio: "
						+ new Float(servicioRoControles
								.promedioValorControles(roDetalleEventoVista)));

				servicioRoDetalleEvento.actualizarPromedio(
						new Float(servicioRoControles
								.promedioValorControles(roDetalleEventoVista)),
						roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarDenominacion(
						calculoDenominacionDelControl(roDetalleEventoVista),
						roDetalleEventoVista.getCodigoDeve());
				// servicioRoDetalleEvento.actualizarRiesgoResidual(
				// calculoRiesgoResidual(roDetalleEventoVista).floatValue(),
				// roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento
						.actualizarRiesgoResidual(
								new Float(
										servicioRoControles
												.promedioRiesgoResidualControles(roDetalleEventoVista)),
								roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarClasificacion(
						calculoClasificacionFinalDelRiesgo(),
						roDetalleEventoVista.getCodigoDeve());
				servicioRoDetalleEvento.actualizarRiesgoInherente(
						calculoRiesgoInherente().floatValue(),
						roDetalleEventoVista.getCodigoDeve());

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			pnlRoTipoControl = false;
			listaRoControles = servicioRoControles
					.buscarControlesPorEvento(roDetalleEventoVista);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Control Eliminado",
					"El registro ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"El Control está en uso"));
		}

		cargarDetallesEventoVisibles(); // se vuelve a cargar para que se
										// actualicen los campos del detalle
										// como por ej: denominacion
	}

	// -----------------------------------------------------------------------------------------------------------------//

	// /////registro cualitativo////////

	public void nuevaControlCualitativo() {
		roControlEventoVista = new RoControlEvento();
		pnlEditarCualitativo = true;
		roControlEventoVista.setTipoCtev("Lista");
		try {
			idControlEvento = controlesTodos.get(0).getCodigoCtrl();
			roControlValores = servicioRoControlValor
					.buscarPorCodigoControl(idControlEvento);
			control = servicioRoControlValor
					.buscarPorIdControlAux(idControlEvento);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void preProcessPDF(Object document) {
		Document pdf = (Document) document;
		pdf.setPageSize(PageSize.A4.rotate());
		pdf.open();
	}

	/**
	 * Se eliminan todas las dependencias de detalle eveno y luego el registro
	 * de detalle evento en sí
	 */
	public void eliminar() {
		try {
			List<RoControles> auxDetRoControles = new ArrayList<RoControles>();
			auxDetRoControles = servicioRoControles
					.buscarControlesPorEvento(roDetalleEventoVista);
			for (RoControles roControles : auxDetRoControles) {
				servicioRoControles.eliminar(roControles);
			}

			List<RoDetCarctEvent> auxDetCaractEvents = new ArrayList<RoDetCarctEvent>();
			auxDetCaractEvents = servicioRoDetCarctEvent
					.buscarPorDetalleEvento(roDetalleEventoVista);
			for (RoDetCarctEvent roDetCarctEvent : auxDetCaractEvents) {
				servicioRoDetCarctEvent.eliminar(roDetCarctEvent);
			}
			// List<RoEventoAfecta> auxEventoAfectas = new
			// ArrayList<RoEventoAfecta>();
			// auxEventoAfectas=servicioRoEv
			List<RoControlEvento> auxControlEventos = new ArrayList<RoControlEvento>();
			auxControlEventos = servicioRoControlEvento
					.buscarPorDetalleEvento(roDetalleEventoVista);
			for (RoControlEvento roControlEvento : auxControlEventos) {
				servicioRoControlEvento.eliminar(roControlEvento);
			}
			List<RoEventoCosto> auxEventoCostos = new ArrayList<RoEventoCosto>();
			auxEventoCostos = servicioRoEventoCosto
					.buscarPorDetalleEvento(roDetalleEventoVista);
			for (RoEventoCosto roEventoCosto : auxEventoCostos) {
				servicioRoEventoCosto.eliminar(roEventoCosto);
			}
			// List<RoEventoObjetivo> auxEventoObjetivos = new
			// ArrayList<RoEventoObjetivo>();
			List<RoEventoRecupera> auxEventoRecuperas = new ArrayList<RoEventoRecupera>();
			auxEventoRecuperas = servicioRoEventoRecupera
					.buscarPorDetalleEvento(roDetalleEventoVista);
			for (RoEventoRecupera roEventoRecupera : auxEventoRecuperas) {
				servicioRoEventoRecupera.eliminar(roEventoRecupera);
			}

			List<RoEventoIndicador> auxEventoIndicadores = new ArrayList<RoEventoIndicador>();
			auxEventoIndicadores = servicioRoEventoIndicador
					.buscarTodosPorCodigoDetalleEvento(roDetalleEventoVista
							.getCodigoDeve());
			for (RoEventoIndicador roEventoIndicador : auxEventoIndicadores) {
				servicioRoEventoIndicador.eliminar(roEventoIndicador);
			}

			servicioRoDetalleEvento.eliminar(roDetalleEventoVista);
			cargarDetallesEventoVisibles();
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					"Detalle de Evento Eliminado",
					"El Detalle de Evento ha sido Eliminado con éxito"));

		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"El detalle de evento no se ha eliminado de forma correcta, por favor revisar dependencias (Ej: planes de acción)"));

		}
	}

	public void cargarControlValorLista2() {
		roControlValores = servicioRoControlValor
				.buscarPorCodigoControl(idControlEvento);

		if (roControlEventoVista.getTipoCtev().equals("Lista")) {
			try {
				idValAttrb = roControlValores.get(0).getCodigoCtva();
			} catch (Exception e) {
				// TODO: handle exception
			}

			cmbValorControl = true;
			txtValorControl = false;
			txtValorControl2 = true;

			control = servicioRoControlValor.buscarPorIdAux(idValAttrb);

		} else {
			cmbValorControl = false;
			txtValorControl = true;
			txtValorControl2 = false;
			control = roControlEventoVista.getControlCtev();
		}

	}

	public void cargarControlValorLista() {

		if (roControlEventoVista.getTipoCtev().equals("Lista")) {
			try {
				idValAttrb = roControlValores.get(0).getCodigoCtva();
			} catch (Exception e) {
				// TODO: handle exception
			}

			cmbValorControl = true;
			txtValorControl = false;
			txtValorControl2 = true;
			control = servicioRoControlValor.buscarPorIdAux(idValAttrb);
		} else {
			cmbValorControl = false;
			txtValorControl = true;
			txtValorControl2 = false;
			control = roControlEventoVista.getControlCtev();
		}

	}

	public void cargarControl() {
		control = servicioRoControlValor.buscarPorIdAux(idValAttrb);
	}

	public void guardarControl() {
		roControlEventoVista.setRoControl(servicioRoControl
				.buscarPorId(idControlEvento));
		if (roControlEventoVista.getTipoCtev().equals("Lista")) {

			roControlEventoVista.setRoControlValor(servicioRoControlValor
					.buscarPorId(idValControl));
			roControlEventoVista.setControlCtev(servicioRoControlValor
					.buscarPorId(idValControl).getControlCtva());

		} else {

			roControlEventoVista.setControlCtev(control);

		}
		roControlEventoVista.setRoDetalleEvento(roDetalleEventoVista);

		try {
			servicioRoControlEvento.insertar(roControlEventoVista);
			pnlEditarCualitativo = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Control Añadido",
					"El Control ha sido Añadido con éxito"));

		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

		}

		controlesEvento = servicioRoControlEvento
				.buscarPorDetalleEvento(roDetalleEventoVista);
	}

	public void cancelarControl() {
		roControlEventoVista = new RoControlEvento();
		pnlEditarCualitativo = false;
		pnlRoTipoControl = false;
		btnAñadirControl = false;
	}

	public void eliminarControl() {
		try {
			servicioRoControlEvento.eliminar(roControlEventoVista);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Control Eliminado",
					"El Control ha sido Eliminado con éxito"));

		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"Hubo un problema al eliminar, El registro puede estar siendo usado, si el problema persiste comuniquese con el proveedor del sistema"));

		}
		controlesEvento = servicioRoControlEvento
				.buscarPorDetalleEvento(roDetalleEventoVista);
	}

	// /////////////////////////////////

	public void terminarRegistro() {

	}

	public void ocultarPaneles() {
		mostrarPanelAdicionales = false;
		btnEditar = false;
		btnSeleccionar = false;
	}

	public void cargarValorLista() {
		if (roDetCarctEventVista.getObservacionDcev().equals("Lista")) {

			cmbValor = true;
			txtValor = false;

		} else {

			cmbValor = false;
			txtValor = true;

		}
		roValAttrbs = new ArrayList<RoValAttrb>();
		try {
			roValAttrbs = servicioRoValAttr
					.buscarPorAttrbCodigoAtributo(idAttrAdicional);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void guardarAttrb() {

		roDetCarctEventVista.setRoAttrbAdicionale(servicioRoAttrbAdicionale
				.buscarPorId(idAttrAdicional));
		if (roDetCarctEventVista.getObservacionDcev().equals("Lista")) {

			roDetCarctEventVista.setRoValAttrb(servicioRoValAttr
					.buscarPorId(idValAttrb));
			roDetCarctEventVista.setValorDcev(servicioRoValAttr.buscarPorId(
					idValAttrb).getVariableValAttr());

		} else {

			roDetCarctEventVista.setValorDcev(valorDetAdic);

		}
		roDetCarctEventVista.setRoDetalleEvento(roDetalleEventoVista);

		try {
			servicioRoDetCarctEvent.insertar(roDetCarctEventVista);
			pnlEditarDetalles = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Detalle Adicional Añadido",
					"El Detalle Adicional ha sido Añadido con éxito"));

		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

		}

		atributosDetalleEvento = servicioRoDetCarctEvent
				.buscarPorDetalleEvento(roDetalleEventoVista);

	}

	public void cancelarAttrb() {
		roDetCarctEventVista = new RoDetCarctEvent();
		pnlEditarDetalles = false;
	}

	public void eliminarAttrb() {
		try {
			servicioRoDetCarctEvent.eliminar(roDetCarctEventVista);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					"Detalle Adicional Eliminado",
					"El Detalle Adicional ha sido Eliminado con éxito"));

		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"Hubo un problema al eliminar, El registro puede estar siendo usado, si el problema persiste comuniquese con el proveedor del sistema"));

		}
		atributosDetalleEvento = servicioRoDetCarctEvent
				.buscarPorDetalleEvento(roDetalleEventoVista);
	}

	// METODOS

	public boolean transformarBool(String strng) {
		if (strng.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public void cargarRecupera() {
		roTipoRecuperaVista = servicioRoTipoRecupera
				.buscarPorId(idTipoRecupera);
	}

	public void calcularGuardarCostos() {
		float sum;
		sum = 0;
		for (RoEventoCosto var : costosDetalleEvento) {
			sum += var.getTotalEvco();
		}

		roDetalleEventoVista.setValorDeve(Float
				.parseFloat((new BigDecimal(Float.toString(roDetalleEventoVista
						.getOtrosGastosDeve() + sum)).setScale(2,
						BigDecimal.ROUND_HALF_UP)).toString()));
		roDetalleEventoVista.setPerdidaResidualDeve(Float
				.parseFloat((new BigDecimal(Float.toString(roDetalleEventoVista
						.getValorDeve()
						- roDetalleEventoVista.getRealRecupDeve())).setScale(2,
						BigDecimal.ROUND_HALF_UP)).toString()));

		servicioRoDetalleEvento.actualizar(roDetalleEventoVista);
		cargarDetallesEventoVisibles();

	}

	public void calcularGuardarRecuperaciones() {
		float sum, sum1;
		sum = 0;
		sum1 = 0;
		for (RoEventoRecupera var : recuperacionesDetalleEvento) {
			sum += var.getValorEvre();
			sum1 += var.getCostoEvre();

		}
		roDetalleEventoVista.setMontoRecupDeve(Float
				.parseFloat((new BigDecimal(Float.toString(sum)).setScale(2,
						BigDecimal.ROUND_HALF_UP)).toString()));
		roDetalleEventoVista.setCostoRecupDeve(Float
				.parseFloat((new BigDecimal(Float.toString(sum1)).setScale(2,
						BigDecimal.ROUND_HALF_UP)).toString()));
		roDetalleEventoVista.setRealRecupDeve(Float.parseFloat((new BigDecimal(
				Float.toString(sum - sum1)).setScale(2,
				BigDecimal.ROUND_HALF_UP)).toString()));
		roDetalleEventoVista.setPerdidaResidualDeve(Float
				.parseFloat((new BigDecimal(Float.toString(roDetalleEventoVista
						.getValorDeve()
						- roDetalleEventoVista.getRealRecupDeve())).setScale(2,
						BigDecimal.ROUND_HALF_UP)).toString()));
		servicioRoDetalleEvento.actualizar(roDetalleEventoVista);
	}

	public void guardarTipoRecupera() {
		roEventoRecupera = new RoEventoRecupera();
		roTipoRecuperaVista = servicioRoTipoRecupera
				.buscarPorId(idTipoRecupera);
		roEventoRecupera.setFechaEvre(fechaEvre);
		roEventoRecupera.setCostoEvre(costoEvre);
		roEventoRecupera.setRoDetalleEvento(roDetalleEventoVista);
		roEventoRecupera.setRoTipoRecupera(roTipoRecuperaVista);
		roEventoRecupera.setValorEvre(valorEvre);
		roEventoRecupera.setTotalEvre(Float.parseFloat((new BigDecimal(Float
				.toString(roEventoRecupera.getValorEvre()
						- roEventoRecupera.getCostoEvre())).setScale(2,
				BigDecimal.ROUND_HALF_UP)).toString()));

		try {
			servicioRoEventoRecupera.insertar(roEventoRecupera);
			pnlEditarRecupera = false;
			recuperacionesDetalleEvento = servicioRoEventoRecupera
					.buscarPorDetalleEvento(roDetalleEventoVista);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Recuperación Añadida",
					"La Recuperación ha sido Añadida con éxito"));
			calcularGuardarRecuperaciones();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));
		}

	}

	public void cancelarTipoRecupera() {
		nuevaRecuperacion();
		pnlEditarRecupera = false;
	}

	public void eliminarTipoRecupera() {
		try {
			servicioRoEventoRecupera.eliminar(roEventoRecupera);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Recuperación Eliminada",
					"La recuperación ha sido Eliminada con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Hubo un problema al eliminar, el registro puede estar siendo usado , si el problema persiste comuniquese con el proveedor del sistema"));

		}

		recuperacionesDetalleEvento = servicioRoEventoRecupera
				.buscarPorDetalleEvento(roDetalleEventoVista);

		cancelarTipoRecupera();
		calcularGuardarRecuperaciones();
	}

	public void guardarTipoCosto() {
		roEventoCosto = new RoEventoCosto();
		roTipoCostoVista = servicioRoTipoCosto.buscarPorId((short) idTipoCosto);
		roEventoCosto.setRoDetalleEvento(roDetalleEventoVista);
		roEventoCosto.setRoTipoCosto(roTipoCostoVista);
		roEventoCosto.setCantidadEvco(cantidadEvco);
		roEventoCosto.setTotalEvco(Float.parseFloat((new BigDecimal(Float
				.toString(roTipoCostoVista.getValorTico() * cantidadEvco))
				.setScale(2, BigDecimal.ROUND_HALF_UP)).toString()));
		roEventoCosto.setValorEvco(roTipoCostoVista.getValorTico());
		try {
			servicioRoEventoCosto.insertar(roEventoCosto);
			pnlEditarCostos = false;
			FacesContext context = FacesContext.getCurrentInstance();
			costosDetalleEvento = servicioRoEventoCosto
					.buscarPorDetalleEvento(roDetalleEventoVista);
			context.addMessage(null, new FacesMessage("Costo Añadido",
					"El Costo ha sido Añadido con éxito"));
			calcularGuardarCostos();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));
		}

	}

	public void cancelarTipoCosto() {
		nuevoCosto();
		pnlEditarCostos = false;
	}

	public void eliminarTipoCosto() {
		try {
			servicioRoEventoCosto.eliminar(roEventoCosto);
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"Hubo un problema al eliminar, el registro puede estar siendo usado, si el problema persiste comuniquese con el proveedor del sistema"));
		}
		costosDetalleEvento = servicioRoEventoCosto
				.buscarPorDetalleEvento(roDetalleEventoVista);
		cancelarTipoCosto();
		calcularGuardarCostos();
	}

	public void cargarDetallesEventoVisibles() {
		if (sisUsuario.getPermisoSuDeve() >= 1.0) {
			System.out
					.println("Usuario con permiso SU. Mostrando todos los detalles.");
			// detalleEventosVisibles =
			// servicioRoDetalleEvento.buscarTodosAux();
			// detalleEventosVisibles = servicioRoDetalleEvento.buscarTodos();
			detalleEventosVisibles = servicioRoDetalleEvento.buscarTodosDesc();
		} else {

			System.out
					.println("Usuario sin privilegios. Mostrando sólo detalles asignados.");
			detalleEventosVisibles = servicioRoDetalleEvento
					.buscarEventoPorUsuarioAux(sisUsuario);
		}
		// int i=0;
		// for(RoDetalleEvento detalle:detalleEventosVisibles)
		// {
		// System.out.println("codigo: "+detalle.getCodigoDeve());
		// System.out.println(detalle.getBloqueo());
		// }
	}

	public void cargarComboAgencias() {
		List<RoRespAgencia> respAgiaAux = new ArrayList<RoRespAgencia>();
		respAgiaAux = servicioRoRespAgencia
				.buscarRespAgenciasPorUsuarioEvaluacion(sisUsuario);
		agenciasUsuario = new ArrayList<RoAgencia>();
		if (!respAgiaAux.isEmpty()) {
			for (RoRespAgencia var : respAgiaAux) {
				agenciasUsuario.add(var.getRoAgencia());
			}
		}
	}

	public void cargarComboDepartamentos() {
		List<RoRespDepa> respDepaAux = new ArrayList<RoRespDepa>();
		respDepaAux = servicioRoRespDepa
				.buscarRespDepaPorUsuarioEvaluacion(sisUsuario);
		departamentosTodos = new ArrayList<RoDepartamento>();
		if (!respDepaAux.isEmpty()) {
			for (RoRespDepa var : respDepaAux) {
				departamentosTodos.add(var.getRoDepartamento());
			}
		}
	}

	public void cargarComboProcesos() {
		System.out.println("Cargando cmb procesos...");
		List<RoRespPro> respProAux = new ArrayList<RoRespPro>();
		System.out.println("sisUsuario: " + sisUsuario.getNombreCompletoUsua());

		respProAux = servicioRoRespPro
				.buscarRespProcesoPorUsuarioEvaluacion(sisUsuario);
		System.out.println("respPro: " + respProAux.size());
		procesosUsuario = new ArrayList<RoProceso>();
		if (!respProAux.isEmpty()) {

			for (RoRespPro var : respProAux) {
				procesosUsuario.add(var.getRoProceso());
			}
		}
	}

	public void cargarComboSubProcesosPorProcesos(int idProceso) {
		try {
			System.out.println("idproceso:" + idProceso);
			int idP = idProceso;
			nombreProcesoSeleccionado = servicioRoProceso
					.buscarProcesoPorIdProceso(idP);
			List<RoRespPro> respSubProAux = new ArrayList<RoRespPro>();
			respSubProAux = servicioRoRespPro
					.buscarRepSubProcesoPorUsuarioYPorProceso(nombreProcesoSeleccionado);

			subprocesosUsuario = new ArrayList<RoProceso>();
			if (!respSubProAux.isEmpty()) {
				for (RoRespPro var : respSubProAux) {
					subprocesosUsuario.add(var.getRoProceso());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void cargarComboNegocios() {
		List<RoNegoPro> negosProAux = new ArrayList<RoNegoPro>();
		negosProAux = servicioRoNegoPros
				.buscarPorProcesoEvaluacion(idProcesoSeleccionado);
		negociosProcesos = new ArrayList<RoNegocio>();
		if (!negosProAux.isEmpty()) {
			for (RoNegoPro roNegoPro : negosProAux) {
				negociosProcesos.add(roNegoPro.getRoNegocio());
			}
		}
	}

	public void cambiarSubProceso() {
		cargarComboSubProcesosPorProcesos(idProcesoSeleccionado);
	}

	public void cambiarNegocio() {
		cargarComboNegocios();
	}

	public void seleccionar() {
		System.out.println("Detalle evento vista seleccionado...");
		valorPerdida = roDetalleEventoVista.getOtrosGastosDeve();
		atributosDetalleEvento = servicioRoDetCarctEvent
				.buscarPorDetalleEvento(roDetalleEventoVista);
		costosDetalleEvento = servicioRoEventoCosto
				.buscarPorDetalleEvento(roDetalleEventoVista);
		recuperacionesDetalleEvento = servicioRoEventoRecupera
				.buscarPorDetalleEvento(roDetalleEventoVista);
		deveFarosDetalleEvento = servicioRoDeveFaro
				.buscarPorDeveEntidad(roDetalleEventoVista);
		listaRoControles = servicioRoControles
				.buscarControlesPorEvento(roDetalleEventoVista);

		indicadoresDetalleEventoTodos = servicioRoEventoIndicador
				.buscarTodosPorCodigoDetalleEvento(roDetalleEventoVista
						.getCodigoDeve());

		tipoGuardar = false;
		pnlRoDetalleEvento = false;

		btnAnadir = false;

		btnAnadirCarac = false || disPanelAdicionales;
		btnAnadirCualitativo = false || disPanelCualitativo;
		btnAnadirCosto = false || disPanelCostos;

		btnAnadirRecup = false || disPanelRecuperaciones;

		btnAnadirIndicador = false || disPanelIndicadores;

		btnInsertarCarac = false;
		btnInsertarCarac = false;
		btnEditar = true;
		btnSeleccionar = true;
		mostrarPanelAdicionales = true;
		// Si es cualitativo, muestra el panel de controles
		if (roDetalleEventoVista.getTipoCalifDeve().toLowerCase()
				.matches("(.*)cual(.*)")) {
			System.out
					.println("Evento Cualitativo seleccionado, mostrando Panel Controles.");
			mostrarPanelControles = true;
		} else {
			System.out.println("Evento Cuantitativo seleccionado.");
			mostrarPanelControles = false;
		}

		colPanelCualitativo = transformarBool(permisosDetalleEvento
				.getColPanelCualitativo());
		colPanelCualitativo2 = transformarBool(permisosDetalleEvento
				.getColPanelCualitativo());
		if (colPanelCualitativo) {
			califSeleccionada = roDetalleEventoVista.getTipoCalifDeve();
			cambiarCalificaion();
		} else {
			colPanelCualitativo = transformarBool(permisosDetalleEvento
					.getColPanelCualitativo());
		}

		// cargando cmb tipo indicadores de riesgo
		tipoIndicadoresPorProcesoDeve = servicioRoTipoIndicadorRiesgo
				.buscarRoTipoIndicadorRiesgoPorProcesoDeve(roDetalleEventoVista
						.getRoProceso());

	}

	public void cambiarCalificaion() {
		if (califSeleccionada.equals("Cualitativo")) {
			colPanelCualitativo = true;
			colFechaOcurrencia = false;
			colFechaDescubrimiento = false;
			tipoPerdidaTodos = servicioRoTipoPerdida
					.buscarPorTipoRegistroAux("Cualitativo");
		} else {
			colPanelCualitativo = false;
			colFechaOcurrencia = true;
			colFechaDescubrimiento = true;
			tipoPerdidaTodos = servicioRoTipoPerdida
					.buscarPorTipoRegistroAux("Cuantitativo");
		}
	}

	// public void deseleccionarDetalleEvento()
	// {
	// System.out.println("Quitando selección...");
	// roDetalleEventoVista=new RoDetalleEvento();
	// }

	// NO es el boton nuevo!!
	public void nuevoDetalleEventoVista() {
		System.out.println("Metodo nuevoDetalle??");
		setCalifSeleccionada("Cuantitativo");
		tipoGuardar = true;
		// roDetalleEventoVista = new RoDetalleEvento();

		cargarComboNegocios();
		cargarDetallesEventoVisibles();
		atributosDetalleEvento = new ArrayList<RoDetCarctEvent>();
		costosDetalleEvento = new ArrayList<RoEventoCosto>();
		recuperacionesDetalleEvento = new ArrayList<RoEventoRecupera>();

		tipoGuardar = true;
		// botones deshabilitados????
		pnlRoDetalleEvento = true;
		btnAnadir = true;
		btnAnadirCarac = false || disPanelAdicionales;
		btnAnadirCualitativo = false || disPanelCualitativo;
		btnAnadirCosto = false || disPanelCostos;
		btnAnadirRecup = false || disPanelRecuperaciones;
		btnAnadirIndicador = false || disPanelIndicadores;
		btnInsertarCarac = false;
		btnEditar = true;
		btnSeleccionar = true;
		btnInsertarCarac = false;
		mostrarPanelAdicionales = false;

	}

	public void cargarComboSubProcesos() {

	}

	public void nuevaCaracteristica() {
		roDetCarctEventVista = new RoDetCarctEvent();
		pnlEditarDetalles = true;
		roDetCarctEventVista.setObservacionDcev("Lista");
		try {
			idAttrAdicional = atributosDetalleTodos.get(0).getCodigoAttr();
			roValAttrbs = servicioRoValAttr
					.buscarPorAttrbCodigoAtributo(idAttrAdicional);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void nuevoCosto() {
		/* roEventoCosto = new RoEventoCosto(); */
		roTipoCostoVista = new RoTipoCosto();
		pnlEditarCostos = true;
		try {
			roTipoCostoVista = servicioRoTipoCosto.buscarPorId(tipoCostoTodos
					.get(0).getCodigoTico());
		} catch (Exception e) {
			// TODO: handle exception
		}

		cantidadEvco = 0;
	}

	public void nuevaRecuperacion() {
		roTipoRecuperaVista = new RoTipoRecupera();
		pnlEditarRecupera = true;
		try {
			roTipoRecuperaVista = servicioRoTipoRecupera
					.buscarPorId(tipoRecuperaTodos.get(0).getCodigoTrec());
		} catch (Exception e) {
			// TODO: handle exception
		}

		cantidadEvre = 0;
		valorEvre = 0;
	}

	public void cargarCosto() {
		roTipoCostoVista = servicioRoTipoCosto.buscarPorId((short) idTipoCosto);
	}

	public void editarDetalleEvento() {
		System.out.println("Método Editar...");

		// limpio los campos de los eventos cuantitativos y cualitativos
		numOcur = 0;
		numOcurDespues = 0;
		nombreProbabilidadAntes = "";

		otrosGastosDeve = 0;
		otrosGastosDeveDespues = 0;

		//
		// idProbabilidadDespues=roDetalleEventoVista.getParamProbabilidadRiesgoDespues().getCodigoPprr();
		// System.out.println("codigo: "+roDetalleEventoVista.getCodigoDeve());

		try {
			califSeleccionada = roDetalleEventoVista.getTipoCalifDeve();
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}
		colPanelCualitativo = transformarBool(permisosDetalleEvento
				.getColPanelCualitativo());
		colPanelCualitativo2 = transformarBool(permisosDetalleEvento
				.getColPanelCualitativo());
		cambiarCalificaion();
		try {
			idAgenciaSeleccionada = roDetalleEventoVista.getRoAgencia()
					.getCodigoAgia();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			idTipoPerdida = roDetalleEventoVista.getRoTipoPerdida()
					.getCodigoTipe();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			idDepartamentoSeleccionado = roDetalleEventoVista
					.getRoDepartamento().getCodigoDept();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			idEventoSeleccionado = roDetalleEventoVista.getRoEvento()
					.getCodigoEven();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			idFactorSeleccionado = roDetalleEventoVista.getRoFactorRiesgo()
					.getCodigoFaro();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			idProcesoSeleccionado = roDetalleEventoVista.getRoProceso()
					.getCodigoPros();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			ancestroProceso = roDetalleEventoVista.getRoProceso()
					.getAncestroPros();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			cargarComboNegocios();
			idNegocioSeleccionado = roDetalleEventoVista.getRoNegocio1()
					.getCodigoNego();
		} catch (Exception e) {
			// TODO: handle exception
		}

		ptoCtrlProcDeve = roDetalleEventoVista.getPtoCtrlProcDeve();

		fechaDescubrimiento = roDetalleEventoVista.getFechaDescDeve();
		fechaOcurrencia = roDetalleEventoVista.getFechaOcurDeve();
		causaProbable = roDetalleEventoVista.getCausaDeve();
		descDetalleEvento = roDetalleEventoVista.getDescripcionDeve();
		descDetallada = roDetalleEventoVista.getDescripcionDetalladaDeve();
		numOcur = roDetalleEventoVista.getNumOcur();
		otrosGastosDeve = roDetalleEventoVista.getOtrosGastosDeve();

		// Elementos solo de un evento cualitativo

		try {

			if (colPanelCualitativo) {

				nombreProbabilidadAntes = roDetalleEventoVista
						.getParamProbabilidadRiesgoAntes().getNombrePprr();

				// roDetalleEventoVista
				// .setParamProbabilidadRiesgoAntes(servicioParamProbabilidadRiesgo
				// .buscarPorId(nombreProbabilidadAntes));
			}

		} catch (Exception e) {
			nombreProbabilidadAntes = "";
			e.printStackTrace();

			// TODO: handle exception
		}

		try {
			if (colPanelCualitativo) {

				otrosGastosDeveDespues = roDetalleEventoVista
						.getValorDeveDespues();
				numOcurDespues = roDetalleEventoVista.getNumOcurDespues();
				idProbabilidadDespues = roDetalleEventoVista
						.getParamProbabilidadRiesgoDespues().getCodigoPprr();
				idConsecuenciaDespues = roDetalleEventoVista
						.getParamConsecuenciaImpactoDespues().getCodigoPconi();
				// roDetalleEventoVista
				// .setParamProbabilidadRiesgoAntes(servicioParamProbabilidadRiesgo
				// .buscarPorId(nombreProbabilidadAntes));
			}
		} catch (Exception e) {
			otrosGastosDeveDespues = 0;
			numOcurDespues = 0;
			idProbabilidadDespues = 0;
			idConsecuenciaDespues = 0;
			// TODO: handle exception
		}

		try {

			if (colPanelCualitativo) {
				idConsecuenciaAntes = roDetalleEventoVista
						.getParamConsecuenciaImpactoAntes().getCodigoPconi();
				// roDetalleEventoVista
				// .setParamConsecuenciaImpactoAntes(servicioParamConsecuenciaImpacto
				// .buscarPorId(idConsecuenciaAntes));
			}

		} catch (Exception e) {
			idConsecuenciaAntes = 0;
			e.printStackTrace();
			// TODO: handle exception
		}

		tipoGuardar = false;
		// botones deshabilitados????
		pnlRoDetalleEvento = true;
		btnGuardarEditado = true;
		btnCancelarEditado = true;
		btnAnadir = true;
		btnAnadirCarac = false || disPanelAdicionales;
		btnAnadirCualitativo = false || disPanelCualitativo;
		btnAnadirCosto = false || disPanelCostos;
		btnAnadirRecup = false || disPanelRecuperaciones;
		btnAnadirIndicador = false || disPanelIndicadores;
		btnInsertarCarac = false;
		btnEditar = true;
		btnSeleccionar = true;
		btnInsertarCarac = false;

	}

	// public void calcularProb_Consec(float numOcur, float otrosGastosDeve) {
	//
	// System.out.println("idnego: " + idNegocioSeleccionado);
	// System.out.println("numOcur: " + numOcur);
	// System.out.println("otrosGastosDeve: " + otrosGastosDeve);
	//
	// // CONSENCUENCIA Y PROBABILIDAD DEL DETALLE EVENTO
	//
	// RoProbabilidadEvento roProbabilidadEventoDetalleVista = new
	// RoProbabilidadEvento();
	// ParamProbabilidadRiesgo paramProbabilidadRiesgoAntesDetalleVista = new
	// ParamProbabilidadRiesgo();
	//
	// RoConsecuenciaImpacto roConsecuenciaImpactoDetalleVista = new
	// RoConsecuenciaImpacto();
	// ParamConsecuenciaImpacto paramConsecuenciaImpactoAntesDetalleVista = new
	// ParamConsecuenciaImpacto();
	//
	// RoAriesgo roAriesgoDetalleVista = new RoAriesgo();
	//
	// if (numOcur > 0) {
	// System.out
	// .println("Calculando probabilidad con el valor de numero de ocurrencia ...");
	//
	// roProbabilidadEventoDetalleVista = servicioRoProbabilidadEvento
	// .buscarProbabilidadEventoPorNegocioFrecuencia(
	// servicioRoNegocio
	// .buscarPorId(idNegocioSeleccionado)
	// .getNombreNego(), numOcur);
	//
	// paramProbabilidadRiesgoAntesDetalleVista =
	// servicioParamProbabilidadRiesgo
	// .buscarProbabilidadRiesgoPorNumero(roProbabilidadEventoDetalleVista
	// .getCodigoPprr());
	// nombreProbabilidadAntes = paramProbabilidadRiesgoAntesDetalleVista
	// .getNombrePprr();
	//
	// }
	//
	// if (otrosGastosDeve > 0) {
	// System.out
	// .println("Calculando consecuencia con el valor de perdida...");
	//
	// roConsecuenciaImpactoDetalleVista = servicioRoConsecuenciaImpacto
	// .buscarConsecuenciaImpactoPorNegocioValor(
	// servicioRoNegocio
	// .buscarPorId(idNegocioSeleccionado)
	// .getNombreNego(), otrosGastosDeve);
	//
	// paramConsecuenciaImpactoAntesDetalleVista =
	// servicioParamConsecuenciaImpacto
	// .buscarConsecuenciaImpactoPorNumero(roConsecuenciaImpactoDetalleVista
	// .getCodigoPconi());
	//
	// System.out.println("*codCons"
	// + roConsecuenciaImpactoDetalleVista.getCODIGO_cons());
	// System.out.println("*codParamCons"
	// + paramConsecuenciaImpactoAntesDetalleVista
	// .getCodigoPconi());
	// idConsecuenciaAntes = paramConsecuenciaImpactoAntesDetalleVista
	// .getCodigoPconi();
	// }
	// if (numOcur > 0 && otrosGastosDeve > 0) {
	// roAriesgoDetalleVista = servicioRoAriesgo
	// .buscarPorParams_Prob_Consec(
	// paramProbabilidadRiesgoAntesDetalleVista
	// .getCodigoPprr(),
	// paramConsecuenciaImpactoAntesDetalleVista
	// .getCodigoPconi());
	//
	// // ERROR no se agrega satisfactoriamente los valores(objeto) de
	// // impacto,
	// // prob o
	// // ariesgo al objeto detalleeventovista hasta que se
	// // ejecuta el metodo guardardetalle, donde se llama otra vez a este
	// // método
	// // . Aqui solo se actualiza los indices de los combo
	// // box. En El evento guardardetalle se vuelve a crear el
	// // detallevento vista, si no se hace asi, da null exception.
	// roDetalleEventoVista
	// .setParamConsecuenciaImpactoAntes(paramConsecuenciaImpactoAntesDetalleVista);
	// roDetalleEventoVista
	// .setParamProbabilidadRiesgoAntes(paramProbabilidadRiesgoAntesDetalleVista);
	// roDetalleEventoVista.setRoAriesgoAntes(roAriesgoDetalleVista);
	//
	// roDetalleEventoVista
	// .setParamConsecuenciaImpactoAntes(paramConsecuenciaImpactoAntesDetalleVista);
	// roDetalleEventoVista
	// .setParamProbabilidadRiesgoAntes(paramProbabilidadRiesgoAntesDetalleVista);
	// roDetalleEventoVista.setRoAriesgoAntes(roAriesgoDetalleVista);
	//
	// }
	//
	// System.out.println("idProbabilidadAntes+ " + nombreProbabilidadAntes
	// + paramProbabilidadRiesgoAntesDetalleVista.getNombrePprr());
	// System.out.println("idConsecuenciaAntes+ " + idConsecuenciaAntes
	// + paramConsecuenciaImpactoAntesDetalleVista.getNombrePconi());
	//
	// }

	public void calcularProb_Consec_Antes() {
		FacesContext context = PrimeFacesContext.getCurrentInstance();

		System.out.println("idnego: " + idNegocioSeleccionado);
		System.out.println("numOcur: " + numOcur);
		System.out.println("otrosGastosDeve: " + otrosGastosDeve);

		// CONSENCUENCIA Y PROBABILIDAD DEL DETALLE EVENTO

		RoProbabilidadEvento roProbabilidadEventoDetalleVista = new RoProbabilidadEvento();
		ParamProbabilidadRiesgo paramProbabilidadRiesgoAntesDetalleVista = new ParamProbabilidadRiesgo();

		RoConsecuenciaImpacto roConsecuenciaImpactoDetalleVista = new RoConsecuenciaImpacto();
		ParamConsecuenciaImpacto paramConsecuenciaImpactoAntesDetalleVista = new ParamConsecuenciaImpacto();

		RoAriesgo roAriesgoDetalleVista = new RoAriesgo();

		if (numOcur > 0) {
			System.out
					.println("Calculando probabilidad con el valor de numero de ocurrencia ...");
			try {
				roProbabilidadEventoDetalleVista = servicioRoProbabilidadEvento
						.buscarProbabilidadEventoPorNegocioFrecuencia(
								servicioRoNegocio.buscarPorId(
										idNegocioSeleccionado).getNombreNego(),
								numOcur);

				paramProbabilidadRiesgoAntesDetalleVista = servicioParamProbabilidadRiesgo
						.buscarProbabilidadRiesgoPorNumero(roProbabilidadEventoDetalleVista
								.getCodigoPprr());
				nombreProbabilidadAntes = paramProbabilidadRiesgoAntesDetalleVista
						.getNombrePprr();
			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Error al cargar parámetros",
						"Revisar parametrización de Probabilidad-Ocurrencia"));
				e.printStackTrace();
				// TODO: handle exception
			}

		}

		if (otrosGastosDeve > 0) {
			System.out
					.println("Calculando consecuencia con el valor de perdida...");
			try {
				roConsecuenciaImpactoDetalleVista = servicioRoConsecuenciaImpacto
						.buscarConsecuenciaImpactoPorNegocioValor(
								servicioRoNegocio.buscarPorId(
										idNegocioSeleccionado).getNombreNego(),
								otrosGastosDeve);

				paramConsecuenciaImpactoAntesDetalleVista = servicioParamConsecuenciaImpacto
						.buscarConsecuenciaImpactoPorNumero(roConsecuenciaImpactoDetalleVista
								.getCodigoPconi());

				System.out.println("*codCons"
						+ roConsecuenciaImpactoDetalleVista.getCODIGO_cons());
				System.out.println("*codParamCons"
						+ paramConsecuenciaImpactoAntesDetalleVista
								.getCodigoPconi());
				idConsecuenciaAntes = paramConsecuenciaImpactoAntesDetalleVista
						.getCodigoPconi();
			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Error al cargar parámetros",
						"Revisar parametrización de Consecuencia-Impacto"));
				e.printStackTrace();
				// TODO: handle exception
			}

		}
		if (numOcur > 0 && otrosGastosDeve > 0) {
			try {
				roAriesgoDetalleVista = servicioRoAriesgo
						.buscarPorParams_Prob_Consec(
								paramProbabilidadRiesgoAntesDetalleVista
										.getCodigoPprr(),
								paramConsecuenciaImpactoAntesDetalleVista
										.getCodigoPconi());

			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Error al cargar parámetros",
						"Revisar parametrización de Asignación de Riesgo"));
				e.printStackTrace();
				// TODO: handle exception
			}
			
			// ERROR no se agrega satisfactoriamente los valores(objeto) de
			// impacto,
			// prob o
			// ariesgo al objeto detalleeventovista hasta que se
			// ejecuta el metodo guardardetalle, donde se llama otra vez a este
			// método
			// . Aqui solo se actualiza los indices de los combo
			// box. En El evento guardardetalle se vuelve a crear el
			// detallevento vista, si no se hace asi, da null exception.
			roDetalleEventoVista
					.setParamConsecuenciaImpactoAntes(paramConsecuenciaImpactoAntesDetalleVista);
			roDetalleEventoVista
					.setParamProbabilidadRiesgoAntes(paramProbabilidadRiesgoAntesDetalleVista);
			roDetalleEventoVista.setRoAriesgoAntes(roAriesgoDetalleVista);

			roDetalleEventoVista
					.setParamConsecuenciaImpactoAntes(paramConsecuenciaImpactoAntesDetalleVista);
			roDetalleEventoVista
					.setParamProbabilidadRiesgoAntes(paramProbabilidadRiesgoAntesDetalleVista);
			roDetalleEventoVista.setRoAriesgoAntes(roAriesgoDetalleVista);

		}

		System.out.println("idProbabilidadAntes+ " + nombreProbabilidadAntes
				+ paramProbabilidadRiesgoAntesDetalleVista.getNombrePprr());
		System.out.println("idConsecuenciaAntes+ " + idConsecuenciaAntes
				+ paramConsecuenciaImpactoAntesDetalleVista.getNombrePconi());

	}

	public void calcularProb_Consec_Despues() {

		System.out.println("idnego: " + idNegocioSeleccionado);
		System.out.println("numOcurDespues: " + numOcurDespues);
		System.out.println("otrosGastosDeve: " + otrosGastosDeveDespues);

		// CONSENCUENCIA Y PROBABILIDAD DEL DETALLE EVENTO

		// Despues de controles
		RoProbabilidadEvento roProbabilidadEventoDespuesDetalleVista = new RoProbabilidadEvento();
		ParamProbabilidadRiesgo paramProbabilidadRiesgoDespuesDetalleVista = new ParamProbabilidadRiesgo();

		RoConsecuenciaImpacto roConsecuenciaImpactoDespuesDetalleVista = new RoConsecuenciaImpacto();
		ParamConsecuenciaImpacto paramConsecuenciaImpactoDespuesDetalleVista = new ParamConsecuenciaImpacto();

		RoAriesgo roAriesgoDespuesDetalleVista = new RoAriesgo();

		// DEPUES
		if (numOcurDespues > 0) {
			System.out
					.println("Calculando probabilidad con el valor de numero de ocurrencia ...");

			roProbabilidadEventoDespuesDetalleVista = servicioRoProbabilidadEvento
					.buscarProbabilidadEventoPorNegocioFrecuencia(
							servicioRoNegocio
									.buscarPorId(idNegocioSeleccionado)
									.getNombreNego(), numOcurDespues);

			paramProbabilidadRiesgoDespuesDetalleVista = servicioParamProbabilidadRiesgo
					.buscarProbabilidadRiesgoPorNumero(roProbabilidadEventoDespuesDetalleVista
							.getCodigoPprr());
			idProbabilidadDespues = paramProbabilidadRiesgoDespuesDetalleVista
					.getCodigoPprr();

		}

		if (otrosGastosDeveDespues > 0) {
			System.out
					.println("Calculando consecuencia con el valor de perdida...");

			roConsecuenciaImpactoDespuesDetalleVista = servicioRoConsecuenciaImpacto
					.buscarConsecuenciaImpactoPorNegocioValor(
							servicioRoNegocio
									.buscarPorId(idNegocioSeleccionado)
									.getNombreNego(), otrosGastosDeveDespues);

			paramConsecuenciaImpactoDespuesDetalleVista = servicioParamConsecuenciaImpacto
					.buscarConsecuenciaImpactoPorNumero(roConsecuenciaImpactoDespuesDetalleVista
							.getCodigoPconi());

			idConsecuenciaDespues = paramConsecuenciaImpactoDespuesDetalleVista
					.getCodigoPconi();
		}
		if (numOcurDespues > 0 && otrosGastosDeveDespues > 0) {
			roAriesgoDespuesDetalleVista = servicioRoAriesgo
					.buscarPorParams_Prob_Consec(
							paramProbabilidadRiesgoDespuesDetalleVista
									.getCodigoPprr(),
							paramConsecuenciaImpactoDespuesDetalleVista
									.getCodigoPconi());

			// ERROR no se agrega satisfactoriamente los valores(objeto) de
			// impacto,
			// prob o
			// ariesgo al objeto detalleeventovista hasta que se
			// ejecuta el metodo guardardetalle, donde se llama otra vez a
			// este
			// método
			// . Aqui solo se actualiza los indices de los combo
			// box. En El evento guardardetalle se vuelve a crear el
			// detallevento vista, si no se hace asi, da null exception.
			roDetalleEventoVista
					.setParamConsecuenciaImpactoDespues(paramConsecuenciaImpactoDespuesDetalleVista);
			roDetalleEventoVista
					.setParamProbabilidadRiesgoDespues(paramProbabilidadRiesgoDespuesDetalleVista);
			roDetalleEventoVista
					.setRoAriesgoDespues(roAriesgoDespuesDetalleVista);

			roDetalleEventoVista
					.setParamConsecuenciaImpactoDespues(paramConsecuenciaImpactoDespuesDetalleVista);
			roDetalleEventoVista
					.setParamProbabilidadRiesgoDespues(paramProbabilidadRiesgoDespuesDetalleVista);
			roDetalleEventoVista
					.setRoAriesgoDespues(roAriesgoDespuesDetalleVista);

		}

	}

	// ES EL BOTON NUEVO
	public void nuevoDetalleEvento() {

		System.out.println("Ingresando nuevo detalle evento...");
		tipoGuardar = true;
		roDetalleEventoVista = new RoDetalleEvento();
		numOcur = 0;
		numOcurDespues = 0;
		otrosGastosDeve = 0;
		otrosGastosDeveDespues = 0;
		fechaDescubrimiento = null;
		fechaOcurrencia = null;
		bolDespuesDeControles = false;
		atributosDetalleEvento = new ArrayList<RoDetCarctEvent>();
		costosDetalleEvento = new ArrayList<RoEventoCosto>();
		recuperacionesDetalleEvento = new ArrayList<RoEventoRecupera>();

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			colPanelCualitativo = transformarBool(permisosDetalleEvento
					.getColPanelCualitativo());
			colPanelCualitativo2 = transformarBool(permisosDetalleEvento
					.getColPanelCualitativo());
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Error al cargar datos",
					"Permisos Detalle Evento"));
			// TODO: handle exception
		}

		setCalifSeleccionada("Cuantitativo");
		cambiarCalificaion();

		nombreProbabilidadAntes = "";
		idProbabilidadDespues = 0;
		idConsecuenciaAntes = 0;
		idConsecuenciaDespues = 0;
		try {
			idProcesoSeleccionado = procesosUsuario.get(0).getCodigoPros();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		System.out.println("idprocesoSelec:" + idProcesoSeleccionado);
		try {
			cargarComboSubProcesosPorProcesos(idProcesoSeleccionado);
			// nombreProcesoSeleccionado =
			// servicioRoProceso.buscarProcesoPorIdProceso(idProcesoSeleccionado);
			// System.out.println("El nombre del proceso es: " +
			// nombreProcesoSeleccionado);
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Error al cargar datos",
					"SubProcesos"));
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			ancestroProceso = procesosUsuario.get(0).getAncestroPros();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		try {
			idAgenciaSeleccionada = agenciasUsuario.get(0).getCodigoAgia();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		try {
			idEventoSeleccionado = eventosTodos.get(0).getCodigoEven();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		try {
			idNegocioSeleccionado = eventosTodos.get(0).getCodigoEven();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		try {
			idDepartamentoSeleccionado = departamentosTodos.get(0)
					.getCodigoDept();
		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}
		try {
			idFactorSeleccionado = factorRiesgoTodos.get(0).getCodigoFaro();
		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}
		try {
			idTipoPerdida = tipoPerdidaTodos.get(0).getCodigoTipe();
		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}
		try {
			// idProbabilidadAntes = probabilidadesTodos.get(0).getCodigoPprr();
		} catch (Exception e) {

			// TODO: handle exception
		}
		try {
			// idProbabilidadDespues =
			// probabilidadesTodos.get(0).getCodigoPprr();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			// idConsecuenciaAntes = consecuenciaTodos.get(0).getCodigoPconi();
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			// idConsecuenciaDespues =
			// consecuenciaTodos.get(0).getCodigoPconi();

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			cargarComboNegocios();
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Error al cargar datos",
					"Negocios"));
			// TODO: handle exception
		}

		// botones desahabilitados?
		btnEditar = true;
		btnSeleccionar = true;
		btnAnadirCarac = true;
		btnAnadirCualitativo = true;
		btnAnadirCosto = true;
		btnAnadirRecup = true;
		btnAnadirIndicador = true;
		btnInsertarCarac = true;
		pnlRoDetalleEvento = true;
		mostrarPanelAdicionales = false;
		// botones habilitiados
		btnGuardar = true;
		btnCancelar = true;
		// vaciar campos detalleevento
		otrosGastosDeve = 0;
		ptoCtrlProcDeve = "";
		causaProbable = "";
		descDetalleEvento = "";
		descDetallada = "";

	}

	public void qwe() {
		System.out.println("rodetalleeventovista: "
				+ roDetalleEventoVista.getPtoCtrlProcDeve());

	}

	public void guardarDetalleEvento() {
		System.out.println("Guardando Detalle Evento Nuevo...");
		if (tipoGuardar) {
			// System.out.println("rodetalleeventovista: "+roDetalleEventoVista.toString());
			roDetalleEventoVista = new RoDetalleEvento();
		}
		try {

			if (colAgencia) {
				roDetalleEventoVista.setRoAgencia(servicioRoAgencia
						.buscarPorId(idAgenciaSeleccionada));
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		try {

			roDetalleEventoVista.setTipoCalifDeve(califSeleccionada);

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {

			// if (colPanelCualitativo) {
			// roDetalleEventoVista
			// .setParamConsecuenciaImpactoAntes(servicioParamConsecuenciaImpacto
			// .buscarPorId(idConsecuenciaAntes));
			// }

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		try {

			// if (colPanelCualitativo) {
			// roDetalleEventoVista
			// .setParamConsecuenciaImpactoDespues(servicioParamConsecuenciaImpacto
			// .buscarPorId(idConsecuenciaDespues));
			// }

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		try {

			// if (colPanelCualitativo) {
			// roDetalleEventoVista
			// .setParamProbabilidadRiesgoAntes(servicioParamProbabilidadRiesgo
			// .buscarPorId(idProbabilidadAntes));
			// }

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		try {

			// if (colPanelCualitativo) {
			// roDetalleEventoVista
			// .setParamProbabilidadRiesgoDespues(servicioParamProbabilidadRiesgo
			// .buscarPorId(idProbabilidadDespues));
			// }

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		// System.out.println("**************");
		// System.out.println("param prob antes: "+roDetalleEventoVista.getParamProbabilidadRiesgoAntes().getNombrePprr());
		try {

			if (colTipoPerdida) {
				roDetalleEventoVista.setRoTipoPerdida(null);
				roDetalleEventoVista.setRoTipoPerdida(servicioRoTipoPerdida
						.buscarPorId(idTipoPerdida));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			roDetalleEventoVista.setSisUsuario(sisUsuario);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {

			if (colDepartamento) {
				roDetalleEventoVista.setRoDepartamento(null);
				roDetalleEventoVista.setRoDepartamento(servicioRoDepartamento
						.buscarPorId((int) idDepartamentoSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {

			if (colEvento) {
				roDetalleEventoVista.setRoEvento(servicioRoEvento
						.buscarPorId(idEventoSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if (colNegocio) {
				roDetalleEventoVista.setRoNegocio1(servicioRoNegocio
						.buscarPorId(idNegocioSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {

			if (colProceso) {
				roDetalleEventoVista.setRoProceso(servicioRoProceso
						.buscarPorId(idProcesoSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// try {
		//
		// if (colSubProceso) {
		// roDetalleEventoVista.setRoProceso(servicioRoProceso
		// .buscarPorId(idProcesoSeleccionado));
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		try {

			if (colFactorRiesgo) {
				roDetalleEventoVista.setRoFactorRiesgo(servicioRoFactorRiesgo
						.buscarPorId(idFactorSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		roDetalleEventoVista.setPtoCtrlProcDeve(ptoCtrlProcDeve);
		roDetalleEventoVista.setOtrosGastosDeve(otrosGastosDeve);
		if (califSeleccionada.equals("Cualitativo")) {
			fechaDescubrimiento = new Date();
			fechaOcurrencia = new Date();
			roDetalleEventoVista.setNumOcurDespues(numOcurDespues);
			roDetalleEventoVista.setValorDeveDespues(otrosGastosDeveDespues);
			// Se agrega el valor de perdida despues
			// if (otrosGastosDeveDespues <= otrosGastosDeve) {
			// roDetalleEventoVista
			// .setValorDeveDespues(otrosGastosDeveDespues);
			// } else {
			// FacesContext context = FacesContext.getCurrentInstance();
			// context.addMessage(
			// null,
			// new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// "Error",
			// "El valor de pérdida(después de controles) no puede ser mayor al valor de pérdida original."));
			// }
		}
		roDetalleEventoVista.setFechaDescDeve(fechaDescubrimiento);
		roDetalleEventoVista.setFechaOcurDeve(fechaOcurrencia);
		roDetalleEventoVista.setFechaRegisDeve(new Date());
		roDetalleEventoVista.setCausaDeve(causaProbable);
		roDetalleEventoVista.setDescripcionDeve(descDetalleEvento);
		roDetalleEventoVista.setDescripcionDetalladaDeve(descDetallada);
		roDetalleEventoVista.setNumOcur(numOcur);

		if (!valBloqueado) // si es falso
		{
			System.out.println("detalle evento desbloqueado");
			roDetalleEventoVista.setBloqueo(0);

		} else {
			System.out.println("detalle evento bloqueado");
			roDetalleEventoVista.setBloqueo(1);
		}

		if (tipoGuardar) {
			try {
				System.out.println("valbloqueado(btn)= " + valBloqueado);

				roDetalleEventoVista.setValorDeve(roDetalleEventoVista
						.getOtrosGastosDeve());
				roDetalleEventoVista
						.setPerdidaResidualDeve(roDetalleEventoVista
								.getOtrosGastosDeve());

				calcularProb_Consec_Antes();
				calcularProb_Consec_Despues();

				servicioRoDetalleEvento.insertar(roDetalleEventoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Detalle de Evento Añadido",
						"El Detalle de Evento ha sido Añadido con éxito"));
				exitoGuardar();
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Error al eliminar:",
								"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));
			}

		} else {

			try {
				costosDetalleEvento = servicioRoEventoCosto
						.buscarPorDetalleEvento(roDetalleEventoVista);
				calcularGuardarCostos();
				servicioRoDetalleEvento.actualizar(roDetalleEventoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Detalle de Evento Actualizado",
						"El Detalle de Evento ha sido Actualizado con éxito"));
				exitoGuardar();
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));
			}

		}

	}

	public void guardarDetalleEventoEditado() {

		System.out.println("Guardando Detalle Evento Editado...");

		if (tipoGuardar) {
			roDetalleEventoVista = new RoDetalleEvento();
		}
		try {

			if (colAgencia) {
				roDetalleEventoVista.setRoAgencia(servicioRoAgencia
						.buscarPorId(idAgenciaSeleccionada));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {

			roDetalleEventoVista.setTipoCalifDeve(califSeleccionada);

		} catch (Exception e) {
			// TODO: handle exception
		}

		try {

			if (colPanelCualitativo) {
				// con el valor de consecuenciaDespues, cambio el valor de
				// consecuenciaAntes
				// if (idConsecuenciaDespues != 0) {
				// roDetalleEventoVista
				// .setParamConsecuenciaImpactoAntes(servicioParamConsecuenciaImpacto
				// .buscarPorId(idConsecuenciaDespues));
				//
				// }

			}

		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}

		try {

			if (colPanelCualitativo) {
				// if (idProbabilidadDespues != 0) {
				// roDetalleEventoVista
				// .setParamProbabilidadRiesgoAntes(servicioParamProbabilidadRiesgo
				// .buscarPorId(idProbabilidadDespues));
				// }

			}

		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}

		try {

			if (colPanelCualitativo) {

			}

		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}

		try {

			if (colTipoPerdida) {
				roDetalleEventoVista.setRoTipoPerdida(null);
				roDetalleEventoVista.setRoTipoPerdida(servicioRoTipoPerdida
						.buscarPorId(idTipoPerdida));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// try {
		// roDetalleEventoVista.setSisUsuario(sisUsuario);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		try {

			if (colDepartamento) {
				roDetalleEventoVista.setRoDepartamento(null);
				roDetalleEventoVista.setRoDepartamento(servicioRoDepartamento
						.buscarPorId((int) idDepartamentoSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {

			if (colEvento) {
				roDetalleEventoVista.setRoEvento(servicioRoEvento
						.buscarPorId(idEventoSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if (colNegocio) {
				roDetalleEventoVista.setRoNegocio1(servicioRoNegocio
						.buscarPorId(idNegocioSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {

			if (colProceso) {
				roDetalleEventoVista.setRoProceso(servicioRoProceso
						.buscarPorId(idProcesoSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// try {
		//
		// if (colPanelIndicadoresRiesgo) {
		// roDetalleEventoVista.setRoE(servicioRoProceso
		// .buscarPorId(idProcesoSeleccionado));
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {

			if (colFactorRiesgo) {
				roDetalleEventoVista.setRoFactorRiesgo(servicioRoFactorRiesgo
						.buscarPorId(idFactorSeleccionado));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (!valBloqueado) // si es falso
		{
			System.out.println("detalle evento desbloqueado");
			roDetalleEventoVista.setBloqueo(0);

		} else {
			System.out.println("detalle evento bloqueado");
			roDetalleEventoVista.setBloqueo(1);
		}

		roDetalleEventoVista.setPtoCtrlProcDeve(ptoCtrlProcDeve);
		roDetalleEventoVista.setOtrosGastosDeve(otrosGastosDeve);
		if (califSeleccionada.equals("Cualitativo")) {
			fechaDescubrimiento = new Date();
			fechaOcurrencia = new Date();

			roDetalleEventoVista.setNumOcurDespues(numOcurDespues);
			roDetalleEventoVista.setValorDeveDespues(otrosGastosDeveDespues);
			// // Se agrega el valor de perdida despues
			// if (otrosGastosDeveDespues <= otrosGastosDeve) {
			// roDetalleEventoVista
			// .setValorDeveDespues(otrosGastosDeveDespues);
			//
			//
			// } else {
			// FacesContext context = FacesContext.getCurrentInstance();
			// context.addMessage(
			// null,
			// new FacesMessage(
			// FacesMessage.SEVERITY_ERROR,
			// "Error",
			// "El valor de pérdida(después de controles) no puede ser mayor al valor de pérdida original."));
			// }
			calcularProb_Consec_Despues();
		}
		roDetalleEventoVista.setFechaDescDeve(fechaDescubrimiento);
		roDetalleEventoVista.setFechaOcurDeve(fechaOcurrencia);
		roDetalleEventoVista.setFechaRegisDeve(new Date());
		roDetalleEventoVista.setCausaDeve(causaProbable);
		roDetalleEventoVista.setDescripcionDeve(descDetalleEvento);
		roDetalleEventoVista.setDescripcionDetalladaDeve(descDetallada);
		roDetalleEventoVista.setNumOcur(numOcur);
		if (tipoGuardar) {
			try {
				roDetalleEventoVista.setValorDeve(roDetalleEventoVista
						.getOtrosGastosDeve());
				roDetalleEventoVista
						.setPerdidaResidualDeve(roDetalleEventoVista
								.getOtrosGastosDeve());
				servicioRoDetalleEvento.insertar(roDetalleEventoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Detalle de Evento Añadido",
						"El Detalle de Evento ha sido Añadido con éxito"));
				exitoGuardar();
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Error al eliminar:",
								"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));
			}

		} else {

			try {
				costosDetalleEvento = servicioRoEventoCosto
						.buscarPorDetalleEvento(roDetalleEventoVista);
				calcularGuardarCostos();
				servicioRoDetalleEvento.actualizar(roDetalleEventoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Detalle de Evento Actualizado",
						"El Detalle de Evento ha sido Actualizado con éxito"));
				exitoGuardar();
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));
			}

		}
	}

	public void exitoGuardar() {
		System.out.println("Método Éxito Guardar...");

		roDetalleEventoVista = new RoDetalleEvento();
		cargarDetallesEventoVisibles();
		atributosDetalleEvento = new ArrayList<RoDetCarctEvent>();
		costosDetalleEvento = new ArrayList<RoEventoCosto>();
		recuperacionesDetalleEvento = new ArrayList<RoEventoRecupera>();

		pnlRoDetalleEvento = false;
		btnGuardar = false;
		btnGuardarEditado = false;
		btnCancelar = false;
		btnCancelarEditado = false;
		btnAnadir = false;
		btnAnadirCarac = true;
		btnAnadirCualitativo = true;
		btnAnadirCosto = true;
		btnAnadirRecup = true;
		btnAnadirIndicador = true;
		btnInsertarCarac = true;
		btnEditar = false;
		btnSeleccionar = false;
		btnInsertarCarac = true;
		// limpio variables de la clase del panel detalle evento
		numOcur = 0;
		numOcurDespues = 0;
		otrosGastosDeve = 0;
		otrosGastosDeveDespues = 0;
		nombreProbabilidadAntes = "";
		idConsecuenciaAntes = 0;
		idProbabilidadDespues = 0;
		idConsecuenciaDespues = 0;
	}

	public void cancelar() {
		roDetalleEventoVista = new RoDetalleEvento();

		atributosDetalleEvento = new ArrayList<RoDetCarctEvent>();
		costosDetalleEvento = new ArrayList<RoEventoCosto>();
		recuperacionesDetalleEvento = new ArrayList<RoEventoRecupera>();

		pnlRoDetalleEvento = false;
		btnAnadir = false;
		btnAnadirCarac = true;
		btnAnadirCualitativo = true;
		btnAnadirCosto = true;
		btnAnadirRecup = true;
		btnAnadirIndicador = true;
		btnInsertarCarac = true;
		btnEditar = false;
		btnSeleccionar = false;
		btnInsertarCarac = true;

		btnGuardar = false;
		btnGuardarEditado = false;
		btnCancelar = false;
		btnCancelarEditado = false;

		// limpio variables de la clase del panel detalle evento
		numOcur = 0;
		numOcurDespues = 0;
		otrosGastosDeve = 0;
		otrosGastosDeveDespues = 0;
		nombreProbabilidadAntes = "";
		idConsecuenciaAntes = 0;
		idProbabilidadDespues = 0;
		idConsecuenciaDespues = 0;

	}

	// DEVE FARO
	public void nuevoDeveFaro() {
		roDeveFaro = new RoDeveFaro();
		roDeveFaro.setRoDetalleEvento(roDetalleEventoVista);
		pnlEditarDeveFaro = true;
	}

	public void guardarDeveFaro() {
		try {
			roDeveFaro.setRoFactorRiesgo(servicioRoFactorRiesgo
					.buscarPorId(idFactorRiesgo));
			servicioRoDeveFaro.insertar(roDeveFaro);
			deveFarosDetalleEvento = servicioRoDeveFaro
					.buscarPorDeveEntidad(roDetalleEventoVista);
			cancelar();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void cancelarDeveFaro() {
		roDeveFaro = new RoDeveFaro();
		pnlEditarDeveFaro = false;
	}

	public void eliminarDeveFaro() {
		servicioRoDeveFaro.eliminar(roDeveFaro);
		deveFarosDetalleEvento = servicioRoDeveFaro
				.buscarPorDeveEntidad(roDetalleEventoVista);
		cancelar();
	}

	// METODOS INDICADOR DE RIESGO CLAVE
	public void nuevoIndicadorRiesgo() {

		roTipoIndicadorRiesgoVista = new RoTipoIndicadorRiesgo();

		pnlEditarIndicador = true;
		esPorcentaje = false;
		valorEvin = new BigDecimal(0);
		fechaEvin = null;
		totalTransaccionesEvin = new Double(0);
		idTipoIndicadorRiesgo = 0;
		porcentajeEvin = new BigDecimal(0);
		System.out.println("idTipoIndicadorRiesgo" + idTipoIndicadorRiesgo);
		System.out.println("3 valorEvin: " + valorEvin.toString());

	}

	public void seleccionarTipoIndicador() {
		System.out.println("1 valorEvin: " + valorEvin.toString());
		roTipoIndicadorRiesgoVista = servicioRoTipoIndicadorRiesgo
				.buscarPorId(idTipoIndicadorRiesgo);
		if (roTipoIndicadorRiesgoVista.getTipoValor().equals("porcentaje")) {
			esPorcentaje = true;
			valorEvin = new BigDecimal(0);
			totalTransaccionesEvin = new Double(0);
		} else {
			esPorcentaje = false;
			// valorEvin = new Double(0);
		}

		// System.out.println("tipoindicadorriesgoseleccionado "+tipoIndicadorRiesgoSeleccionado.getnombreTinri());
		System.out.println("tipoIndicadorSelec "
				+ roTipoIndicadorRiesgoVista.getnombreTinri() + "\n"
				+ roTipoIndicadorRiesgoVista.getTipoValor());
		System.out.println("2 valorEvin: " + valorEvin.toString());

	}

	public void calcularPorcentaje() {
		System.out.println("Calculando porcentaje");
		System.out.println("5 valorEvin" + valorEvin);

		// if (idTipoIndicadorRiesgo != 0) {
		// if (roTipoIndicadorRiesgoVista.getTipoValor().equals("porcentaje")) {
		if (roTipoIndicadorRiesgoVista.getTipoValor().equals("porcentaje")) {
			porcentajeEvin = new BigDecimal(
					(roDetalleEventoVista.getNumOcur() / totalTransaccionesEvin) * 100)
					.setScale(2, BigDecimal.ROUND_HALF_UP);

			valorEvin = new BigDecimal(porcentajeEvin.doubleValue()).setScale(
					2, BigDecimal.ROUND_HALF_UP);
		}
		// }

		// }
		System.out.println("6 valorEvin" + valorEvin.toString());
	}

	public void guardarDeveIndicador() {
		try {
			System.out.println("4 valorEvin: " + valorEvin.toString());

			double sumaTemp = 0;
			double limite = 0;
			FacesContext context = FacesContext.getCurrentInstance();
			calcularPorcentaje();

			// if
			// (roTipoIndicadorRiesgoVista.getTipoValor().equals("porcentaje"))
			// {
			// valorEvin=porcentajeEvin;
			// }

			System.out.println("valorevin: " + valorEvin.toString());
			exitoGuardarControles();

			System.out.println("Guardando un nuevo indicador");
			roEventoIndicadorVista = new RoEventoIndicador();
			roEventoIndicadorVista.setRoDetalleEvento(roDetalleEventoVista);

			roEventoIndicadorVista
					.setRoTipoIndicadorRiesgo(servicioRoTipoIndicadorRiesgo
							.buscarPorId(idTipoIndicadorRiesgo));

			valorEvin = new BigDecimal(valorEvin.floatValue()).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			System.out.println("añadiendo el valorEvin al eveninri de: "
					+ valorEvin.floatValue());
			roEventoIndicadorVista.setValorEvin(valorEvin.doubleValue());
			roEventoIndicadorVista.setFechaEvin(fechaEvin);

			// roEventoIndicadorVista.setRoTipoIndicadorRiesgo(tipoIndicadorRiesgoSeleccionado);
			RoTipoIndicadorRiesgo roTipoIndicadorRiesgoSeleccionado = new RoTipoIndicadorRiesgo();

			roTipoIndicadorRiesgoSeleccionado = servicioRoTipoIndicadorRiesgo
					.buscarPorId(idTipoIndicadorRiesgo);

			if (roTipoIndicadorRiesgoSeleccionado.getLimite().doubleValue() == 0) {
				limite = Double.POSITIVE_INFINITY;
			} else {
				limite = roTipoIndicadorRiesgoSeleccionado.getLimite()
						.doubleValue();
			}

			System.out.println(">>sumaTemp: " + sumaTemp);
			if (roTipoIndicadorRiesgoSeleccionado.getAlerta() == (null)
					|| roTipoIndicadorRiesgoSeleccionado.getAlerta()
							.doubleValue() == 0) {
				if (roTipoIndicadorRiesgoSeleccionado.getLimite() == (null)
						|| roTipoIndicadorRiesgoSeleccionado.getLimite()
								.doubleValue() == 0) {
					servicioRoEventoIndicador.insertar(roEventoIndicadorVista);
					context.addMessage("Ingreso", new FacesMessage(
							"Indicador ingresado correctamente."));
					context.addMessage("Ingreso", new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Advertencia",
							"No hay valor de límite, tampoco de alerta"));

				} else {
					sumaTemp = calcularSumaIndicadores();
					System.out.println(">sumaTemp: " + sumaTemp);
					sumaTemp = sumaTemp + valorEvin.floatValue();
					if (sumaTemp < roTipoIndicadorRiesgoSeleccionado
							.getLimite().doubleValue()) {
						servicioRoEventoIndicador
								.insertar(roEventoIndicadorVista);
						context.addMessage("Ingreso", new FacesMessage(
								"Indicador ingresado correctamente."));
						context.addMessage("Ingreso", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Advertencia",
								"No hay valor de Alerta"));
					} else {
						System.out
								.println(">Se ha sobrepasado el lìmite. No se ingresó el Indicador");
						context.addMessage(
								"qwe",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"ERROR",
										"No se ingresó el indicador porque se sobrepasó el límite"
												+ " ("
												+ roTipoIndicadorRiesgoSeleccionado
														.getLimite() + ")"));

					}
				}

			} else {

				sumaTemp = calcularSumaIndicadores();
				System.out.println(">sumaTemp: " + sumaTemp);
				sumaTemp = sumaTemp + valorEvin.floatValue();
				if (sumaTemp > roTipoIndicadorRiesgoSeleccionado.getAlerta()
						.doubleValue()) {

					if (sumaTemp < limite) {
						// FacesContext context =
						// FacesContext.getCurrentInstance();
						// context.addMessage(null, new FacesMessage("Permisos",
						// "El Responsable ha sido Actualizado con éxito"));
						if (limite != Double.POSITIVE_INFINITY) {
							context.addMessage("Ingreso", new FacesMessage(
									"Indicador ingresado correctamente."));
							context.addMessage("asd", new FacesMessage(
									FacesMessage.SEVERITY_WARN, "Advertencia",
									"Cerca del valor límite"));

							System.out
									.println(">Esta acercandose a valor lìmite");
							servicioRoEventoIndicador
									.insertar(roEventoIndicadorVista);
						} else {
							context.addMessage("Ingreso", new FacesMessage(
									"Indicador ingresado correctamente."));
							context.addMessage("asd", new FacesMessage(
									FacesMessage.SEVERITY_INFO, "Advertencia",
									"Límite infinito (= 0)"));

							System.out.println(">Valor limite infinito");
							servicioRoEventoIndicador
									.insertar(roEventoIndicadorVista);

						}

					} else {
						System.out
								.println(">Se ha sobrepasado el lìmite. No se ingresó el Indicador");
						context.addMessage(
								"qwe",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"ERROR",
										"No se ingresó el indicador porque se sobrepasó el límite"
												+ " ("
												+ roTipoIndicadorRiesgoSeleccionado
														.getLimite() + ")"));

					}

				} else {
					// System.out.println(">Sin observaciones");
					// servicioRoEventoIndicador.insertar(roEventoIndicadorVista);
					// context.addMessage("Ingreso", new FacesMessage(
					// "Indicador ingresado correctamente."));

					if (limite != Double.POSITIVE_INFINITY) {
						context.addMessage("Ingreso", new FacesMessage(
								"Indicador ingresado correctamente."));
						servicioRoEventoIndicador
								.insertar(roEventoIndicadorVista);
						System.out.println(">Valor limite no infinito");

					} else {
						context.addMessage("Ingreso", new FacesMessage(
								"Indicador ingresado correctamente."));
						context.addMessage("asd", new FacesMessage(
								FacesMessage.SEVERITY_INFO, "Advertencia",
								"Límite infinito (= 0)"));

						System.out.println(">Valor limite infinito");
						servicioRoEventoIndicador
								.insertar(roEventoIndicadorVista);

					}

				}

			}

			pnlEditarIndicador = false;
			indicadoresDetalleEventoTodos = servicioRoEventoIndicador
					.buscarTodosPorCodigoDetalleEvento(roDetalleEventoVista
							.getCodigoDeve());
		} catch (Exception e) {
			// TODO: handle exception
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("Error", new FacesMessage(
					FacesMessage.SEVERITY_INFO, "¡Error!", e.toString()));
			e.printStackTrace();
		}

	}

	public void cancelarIndicador() {
		nuevoIndicadorRiesgo();
		pnlEditarIndicador = false;
	}

	public double calcularSumaIndicadores() {
		// sumando el valor de los indicadores del detalle
		sumaValorIndicadores = 0;
		System.out.println("tipo ind escogido: "
				+ servicioRoTipoIndicadorRiesgo.buscarPorId(
						idTipoIndicadorRiesgo).getcodigoTinri());

		for (RoEventoIndicador indicador : indicadoresDetalleEventoTodos) {
			System.out.println("tipo ind: "
					+ indicador.getRoTipoIndicadorRiesgo().getcodigoTinri());
			if (indicador.getRoTipoIndicadorRiesgo().getcodigoTinri() == servicioRoTipoIndicadorRiesgo
					.buscarPorId(idTipoIndicadorRiesgo).getcodigoTinri())
				sumaValorIndicadores += indicador.getValorEvin();
		}
		return sumaValorIndicadores;
	}

	public void eliminarDeveIndicador() {
		servicioRoEventoIndicador.eliminar(roEventoIndicadorVista);
		indicadoresDetalleEventoTodos = servicioRoEventoIndicador
				.buscarTodosPorCodigoDetalleEvento(roDetalleEventoVista
						.getCodigoDeve());
	}

	public void cancelarDeveIndicador() {
		roEventoIndicadorVista = new RoEventoIndicador();
		pnlEditarIndicador = false;
	}

	//

	// GETTERS Y SETTERS

	public boolean isBtnAnadir() {
		return btnAnadir;
	}

	public void setBtnAnadir(boolean btnAnadir) {
		this.btnAnadir = btnAnadir;
	}

	public RoDetalleEvento getRoDetalleEventoControlador() {
		return roDetalleEventoControlador;
	}

	public void setRoDetalleEventoControlador(
			RoDetalleEvento roDetalleEventoControlador) {
		this.roDetalleEventoControlador = roDetalleEventoControlador;
	}

	public int getIdDetalleEventoSeleccionado() {
		return idDetalleEventoSeleccionado;
	}

	public void setIdDetalleEventoSeleccionado(int idDetalleEventoSeleccionado) {
		this.idDetalleEventoSeleccionado = idDetalleEventoSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public List<GenEstado> getEstadosTodos() {
		return estadosTodos;
	}

	public void setEstadosTodos(List<GenEstado> estadosTodos) {
		this.estadosTodos = estadosTodos;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public ServicioRoDetalleEvento getServicioRoDetalleEvento() {
		return servicioRoDetalleEvento;
	}

	public void setServicioRoDetalleEvento(
			ServicioRoDetalleEvento servicioRoDetalleEvento) {
		this.servicioRoDetalleEvento = servicioRoDetalleEvento;
	}

	public List<RoAgencia> getAgenciasUsuario() {
		return agenciasUsuario;
	}

	public void setAgenciasUsuario(List<RoAgencia> agenciasUsuario) {
		this.agenciasUsuario = agenciasUsuario;
	}

	public List<RoEvento> getEventosTodos() {
		return eventosTodos;
	}

	public void setEventosTodos(List<RoEvento> eventosTodos) {
		this.eventosTodos = eventosTodos;
	}

	public List<RoProceso> getProcesosUsuario() {
		return procesosUsuario;
	}

	public void setProcesosUsuario(List<RoProceso> procesosUsuario) {
		this.procesosUsuario = procesosUsuario;
	}

	public List<GenMoneda> getMonedasTodos() {
		return monedasTodos;
	}

	public void setMonedasTodos(List<GenMoneda> monedasTodos) {
		this.monedasTodos = monedasTodos;
	}

	public List<RoNegocio> getNegociosProcesos() {
		return negociosProcesos;
	}

	public void setNegociosProcesos(List<RoNegocio> negociosProcesos) {
		this.negociosProcesos = negociosProcesos;
	}

	public List<RoDepartamento> getDepartamentosTodos() {
		return departamentosTodos;
	}

	public void setDepartamentosTodos(List<RoDepartamento> departamentosTodos) {
		this.departamentosTodos = departamentosTodos;
	}

	public List<RoFactorRiesgo> getFactorRiesgoTodos() {
		return factorRiesgoTodos;
	}

	public void setFactorRiesgoTodos(List<RoFactorRiesgo> factorRiesgoTodos) {
		this.factorRiesgoTodos = factorRiesgoTodos;
	}

	public boolean isColCodigo() {
		return colCodigo;
	}

	public void setColCodigo(boolean colCodigo) {
		this.colCodigo = colCodigo;
	}

	public boolean isColAgencia() {
		return colAgencia;
	}

	public void setColAgencia(boolean colAgencia) {
		this.colAgencia = colAgencia;
	}

	public boolean isColEvento() {
		return colEvento;
	}

	public void setColEvento(boolean colEvento) {
		this.colEvento = colEvento;
	}

	public boolean isColProceso() {
		return colProceso;
	}

	public void setColProceso(boolean colProceso) {
		this.colProceso = colProceso;
	}

	public boolean isColMoneda() {
		return colMoneda;
	}

	public void setColMoneda(boolean colMoneda) {
		this.colMoneda = colMoneda;
	}

	public boolean isColNegocio() {
		return colNegocio;
	}

	public void setColNegocio(boolean colNegocio) {
		this.colNegocio = colNegocio;
	}

	public boolean isColDepartamento() {
		return colDepartamento;
	}

	public void setColDepartamento(boolean colDepartamento) {
		this.colDepartamento = colDepartamento;
	}

	public boolean isColFactorRiesgo() {
		return colFactorRiesgo;
	}

	public void setColFactorRiesgo(boolean colFactorRiesgo) {
		this.colFactorRiesgo = colFactorRiesgo;
	}

	public boolean isColValor() {
		return colValor;
	}

	public void setColValor(boolean colValor) {
		this.colValor = colValor;
	}

	public boolean isColPtoControl() {
		return colPtoControl;
	}

	public void setColPtoControl(boolean colPtoControl) {
		this.colPtoControl = colPtoControl;
	}

	public boolean isColMonto() {
		return colMonto;
	}

	public void setColMonto(boolean colMonto) {
		this.colMonto = colMonto;
	}

	public boolean isColValorCambio() {
		return colValorCambio;
	}

	public void setColValorCambio(boolean colValorCambio) {
		this.colValorCambio = colValorCambio;
	}

	public boolean isColCosto() {
		return colCosto;
	}

	public void setColCosto(boolean colCosto) {
		this.colCosto = colCosto;
	}

	public boolean isColReal() {
		return colReal;
	}

	public void setColReal(boolean colReal) {
		this.colReal = colReal;
	}

	public boolean isColCuenta() {
		return colCuenta;
	}

	public void setColCuenta(boolean colCuenta) {
		this.colCuenta = colCuenta;
	}

	public boolean isColOtrosGastos() {
		return colOtrosGastos;
	}

	public void setColOtrosGastos(boolean colOtrosGastos) {
		this.colOtrosGastos = colOtrosGastos;
	}

	public List<RoEventoCosto> getCostosDetalleEvento() {
		return costosDetalleEvento;
	}

	public void setCostosDetalleEvento(List<RoEventoCosto> costosDetalleEvento) {
		this.costosDetalleEvento = costosDetalleEvento;
	}

	public List<RoDetCarctEvent> getAtributosDetalleEvento() {
		return atributosDetalleEvento;
	}

	public void setAtributosDetalleEvento(
			List<RoDetCarctEvent> atributosDetalleEvento) {
		this.atributosDetalleEvento = atributosDetalleEvento;
	}

	public boolean isColOpEdicion() {
		return colOpEdicion;
	}

	public void setColOpEdicion(boolean colOpEdicion) {
		this.colOpEdicion = colOpEdicion;
	}

	public boolean isColBtnEditar() {
		return colBtnEditar;
	}

	public void setColBtnEditar(boolean colBtnEditar) {
		this.colBtnEditar = colBtnEditar;
	}

	public SisUsuario getSisUsuario() {
		return sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

	public RoDetCarctEvent getRoDetCarctEventVista() {
		return roDetCarctEventVista;
	}

	public void setRoDetCarctEventVista(RoDetCarctEvent roDetCarctEventVista) {
		this.roDetCarctEventVista = roDetCarctEventVista;
	}

	public List<RoEventoRecupera> getRecuperacionesDetalleEvento() {
		return recuperacionesDetalleEvento;
	}

	public void setRecuperacionesDetalleEvento(
			List<RoEventoRecupera> recuperacionesDetalleEvento) {
		this.recuperacionesDetalleEvento = recuperacionesDetalleEvento;
	}

	public List<RoDetalleEvento> getDetalleEventosVisibles() {
		return detalleEventosVisibles;
	}

	public void setDetalleEventosVisibles(
			List<RoDetalleEvento> detalleEventosVisibles) {
		this.detalleEventosVisibles = detalleEventosVisibles;
	}

	public boolean isBtnAnadirCarac() {
		return btnAnadirCarac;
	}

	public void setBtnAnadirCarac(boolean btnAnadirCarac) {
		this.btnAnadirCarac = btnAnadirCarac;
	}

	public boolean isBtnInsertarCarac() {
		return btnInsertarCarac;
	}

	public void setBtnInsertarCarac(boolean btnInsertarCarac) {
		this.btnInsertarCarac = btnInsertarCarac;
	}

	public boolean isBtnAnadirCosto() {
		return btnAnadirCosto;
	}

	public void setBtnAnadirCosto(boolean btnAnadirCosto) {
		this.btnAnadirCosto = btnAnadirCosto;
	}

	public boolean isBtnAnadirRecup() {
		return btnAnadirRecup;
	}

	public void setBtnAnadirRecup(boolean btnAnadirRecup) {
		this.btnAnadirRecup = btnAnadirRecup;
	}

	public boolean isBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(boolean btnEditar) {
		this.btnEditar = btnEditar;
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public int getIndiceTabla() {
		return indiceTabla;
	}

	public void setIndiceTabla(int indiceTabla) {
		this.indiceTabla = indiceTabla;
	}

	public boolean isPnlRoDetalleEvento() {
		return pnlRoDetalleEvento;
	}

	public void setPnlRoDetalleEvento(boolean pnlRoDetalleEvento) {
		this.pnlRoDetalleEvento = pnlRoDetalleEvento;
	}

	public int getIdAgenciaSeleccionada() {
		return idAgenciaSeleccionada;
	}

	public void setIdAgenciaSeleccionada(int idAgenciaSeleccionada) {
		this.idAgenciaSeleccionada = idAgenciaSeleccionada;
	}

	public int getIdEventoSeleccionado() {
		return idEventoSeleccionado;
	}

	public void setIdEventoSeleccionado(int idEventoSeleccionado) {
		this.idEventoSeleccionado = idEventoSeleccionado;
	}

	public int getIdProcesoSeleccionado() {
		return idProcesoSeleccionado;
	}

	public void setIdProcesoSeleccionado(int idProcesoSeleccionado) {
		this.idProcesoSeleccionado = idProcesoSeleccionado;
	}

	public int getIdNegocioSeleccionado() {
		return idNegocioSeleccionado;
	}

	public void setIdNegocioSeleccionado(int idNegocioSeleccionado) {
		this.idNegocioSeleccionado = idNegocioSeleccionado;
	}

	public int getIdDepartamentoSeleccionado() {
		return idDepartamentoSeleccionado;
	}

	public void setIdDepartamentoSeleccionado(int idDepartamentoSeleccionado) {
		this.idDepartamentoSeleccionado = idDepartamentoSeleccionado;
	}

	public int getIdMonedaSeleccionada() {
		return idMonedaSeleccionada;
	}

	public void setIdMonedaSeleccionada(int idMonedaSeleccionada) {
		this.idMonedaSeleccionada = idMonedaSeleccionada;
	}

	public int getIdFactorSeleccionado() {
		return idFactorSeleccionado;
	}

	public void setIdFactorSeleccionado(int idFactorSeleccionado) {
		this.idFactorSeleccionado = idFactorSeleccionado;
	}

	public boolean isValBloqueado() {
		return valBloqueado;
	}

	public void setValBloqueado(boolean valBloqueado) {
		this.valBloqueado = valBloqueado;
	}

	public boolean isBtnSeleccionar() {
		return btnSeleccionar;
	}

	public void setBtnSeleccionar(boolean btnSeleccionar) {
		this.btnSeleccionar = btnSeleccionar;
	}

	public boolean isColRecupReal() {
		return colRecupReal;
	}

	public void setColRecupReal(boolean colRecupReal) {
		this.colRecupReal = colRecupReal;
	}

	public boolean isColPerdResidual() {
		return colPerdResidual;
	}

	public void setColPerdResidual(boolean colPerdResidual) {
		this.colPerdResidual = colPerdResidual;
	}

	public boolean isColUsuario() {
		return colUsuario;
	}

	public void setColUsuario(boolean colUsuario) {
		this.colUsuario = colUsuario;
	}

	public int getIdAttrAdicional() {
		return idAttrAdicional;
	}

	public void setIdAttrAdicional(int idAttrAdicional) {
		this.idAttrAdicional = idAttrAdicional;
	}

	public List<RoAttrbAdicionale> getAtributosDetalleTodos() {
		return atributosDetalleTodos;
	}

	public void setAtributosDetalleTodos(
			List<RoAttrbAdicionale> atributosDetalleTodos) {
		this.atributosDetalleTodos = atributosDetalleTodos;
	}

	public int getIdValAttrb() {
		return idValAttrb;
	}

	public void setIdValAttrb(int idValAttrb) {
		this.idValAttrb = idValAttrb;
	}

	public List<RoValAttrb> getRoValAttrbs() {
		return roValAttrbs;
	}

	public void setRoValAttrbs(List<RoValAttrb> roValAttrbs) {
		this.roValAttrbs = roValAttrbs;
	}

	public int getIdTipoCosto() {
		return idTipoCosto;
	}

	public void setIdTipoCosto(int idTipoCosto) {
		this.idTipoCosto = idTipoCosto;
	}

	public List<RoTipoCosto> getTipoCostoTodos() {
		return tipoCostoTodos;
	}

	public void setTipoCostoTodos(List<RoTipoCosto> tipoCostoTodos) {
		this.tipoCostoTodos = tipoCostoTodos;
	}

	public RoTipoCosto getRoTipoCostoVista() {
		return roTipoCostoVista;
	}

	public void setRoTipoCostoVista(RoTipoCosto roTipoCostoVista) {
		this.roTipoCostoVista = roTipoCostoVista;
	}

	public RoEventoCosto getRoEventoCosto() {
		return roEventoCosto;
	}

	public void setRoEventoCosto(RoEventoCosto roEventoCosto) {
		this.roEventoCosto = roEventoCosto;
	}

	public boolean isPnlEditarCostos() {
		return pnlEditarCostos;
	}

	public void setPnlEditarCostos(boolean pnlEditarCostos) {
		this.pnlEditarCostos = pnlEditarCostos;
	}

	public int getCantidadEvco() {
		return cantidadEvco;
	}

	public void setCantidadEvco(int cantidadEvco) {
		this.cantidadEvco = cantidadEvco;
	}

	public RoTipoRecupera getRoTipoRecuperaVista() {
		return roTipoRecuperaVista;
	}

	public void setRoTipoRecuperaVista(RoTipoRecupera roTipoRecuperaVista) {
		this.roTipoRecuperaVista = roTipoRecuperaVista;
	}

	public int getIdTipoRecupera() {
		return idTipoRecupera;
	}

	public void setIdTipoRecupera(int idTipoRecupera) {
		this.idTipoRecupera = idTipoRecupera;
	}

	public int getCantidadEvre() {
		return cantidadEvre;
	}

	public void setCantidadEvre(int cantidadEvre) {
		this.cantidadEvre = cantidadEvre;
	}

	public List<RoTipoRecupera> getTipoRecuperaTodos() {
		return tipoRecuperaTodos;
	}

	public void setTipoRecuperaTodos(List<RoTipoRecupera> tipoRecuperaTodos) {
		this.tipoRecuperaTodos = tipoRecuperaTodos;
	}

	public boolean isPnlEditarRecupera() {
		return pnlEditarRecupera;
	}

	public void setPnlEditarRecupera(boolean pnlEditarRecupera) {
		this.pnlEditarRecupera = pnlEditarRecupera;
	}

	public RoEventoRecupera getRoEventoRecupera() {
		return roEventoRecupera;
	}

	public void setRoEventoRecupera(RoEventoRecupera roEventoRecupera) {
		this.roEventoRecupera = roEventoRecupera;
	}

	public float getCostoEvre() {
		return costoEvre;
	}

	public void setCostoEvre(float costoEvre) {
		this.costoEvre = costoEvre;
	}

	public boolean isCmbValor() {
		return cmbValor;
	}

	public void setCmbValor(boolean cmbValor) {
		this.cmbValor = cmbValor;
	}

	public boolean isTxtValor() {
		return txtValor;
	}

	public void setTxtValor(boolean txtValor) {
		this.txtValor = txtValor;
	}

	public String getValorDetAdic() {
		return valorDetAdic;
	}

	public void setValorDetAdic(String valorDetAdic) {
		this.valorDetAdic = valorDetAdic;
	}

	public boolean isPnlEditarDetalles() {
		return pnlEditarDetalles;
	}

	public void setPnlEditarDetalles(boolean pnlEditarDetalles) {
		this.pnlEditarDetalles = pnlEditarDetalles;
	}

	public float getValorEvre() {
		return valorEvre;
	}

	public void setValorEvre(float valorEvre) {
		this.valorEvre = valorEvre;
	}

	public boolean isMostrarPanelAdicionales() {
		return mostrarPanelAdicionales;
	}

	public void setMostrarPanelAdicionales(boolean mostrarPanelAdicionales) {
		this.mostrarPanelAdicionales = mostrarPanelAdicionales;
	}

	public Date getFechaEvre() {
		return fechaEvre;
	}

	public void setFechaEvre(Date fechaEvre) {
		this.fechaEvre = fechaEvre;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}

	public String getPtoCtrlProcDeve() {
		return ptoCtrlProcDeve;
	}

	public void setPtoCtrlProcDeve(String ptoCtrlProcDeve) {
		this.ptoCtrlProcDeve = ptoCtrlProcDeve;
	}

	public float getOtrosGastosDeve() {
		return otrosGastosDeve;
	}

	public void setOtrosGastosDeve(float otrosGastosDeve) {
		this.otrosGastosDeve = otrosGastosDeve;
	}

	public String getCausaProbable() {
		return causaProbable;
	}

	public void setCausaProbable(String causaProbable) {
		this.causaProbable = causaProbable;
	}

	public String getDescDetalleEvento() {
		return descDetalleEvento;
	}

	public void setDescDetalleEvento(String descDetalleEvento) {
		this.descDetalleEvento = descDetalleEvento;
	}

	public String getDescDetallada() {
		return descDetallada;
	}

	public void setDescDetallada(String descDetallada) {
		this.descDetallada = descDetallada;
	}

	public Date getFechaOcurrencia() {
		return fechaOcurrencia;
	}

	public void setFechaOcurrencia(Date fechaOcurrencia) {
		this.fechaOcurrencia = fechaOcurrencia;
	}

	public Date getFechaDescubrimiento() {
		return fechaDescubrimiento;
	}

	public void setFechaDescubrimiento(Date fechaDescubrimiento) {
		this.fechaDescubrimiento = fechaDescubrimiento;
	}

	public int getIdTipoPerdida() {
		return idTipoPerdida;
	}

	public void setIdTipoPerdida(int idTipoPerdida) {
		this.idTipoPerdida = idTipoPerdida;
	}

	public List<RoTipoPerdida> getTipoPerdidaTodos() {
		return tipoPerdidaTodos;
	}

	public void setTipoPerdidaTodos(List<RoTipoPerdida> tipoPerdidaTodos) {
		this.tipoPerdidaTodos = tipoPerdidaTodos;
	}

	public boolean isDisCodigo() {
		return disCodigo;
	}

	public void setDisCodigo(boolean disCodigo) {
		this.disCodigo = disCodigo;
	}

	public boolean isDisAgencia() {
		return disAgencia;
	}

	public void setDisAgencia(boolean disAgencia) {
		this.disAgencia = disAgencia;
	}

	public boolean isDisEvento() {
		return disEvento;
	}

	public void setDisEvento(boolean disEvento) {
		this.disEvento = disEvento;
	}

	public boolean isDisProceso() {
		return disProceso;
	}

	public void setDisProceso(boolean disProceso) {
		this.disProceso = disProceso;
	}

	public boolean isDisMoneda() {
		return disMoneda;
	}

	public void setDisMoneda(boolean disMoneda) {
		this.disMoneda = disMoneda;
	}

	public boolean isDisNegocio() {
		return disNegocio;
	}

	public void setDisNegocio(boolean disNegocio) {
		this.disNegocio = disNegocio;
	}

	public boolean isDisDepartamento() {
		return disDepartamento;
	}

	public void setDisDepartamento(boolean disDepartamento) {
		this.disDepartamento = disDepartamento;
	}

	public boolean isDisFactorRiesgo() {
		return disFactorRiesgo;
	}

	public void setDisFactorRiesgo(boolean disFactorRiesgo) {
		this.disFactorRiesgo = disFactorRiesgo;
	}

	public boolean isDisValor() {
		return disValor;
	}

	public void setDisValor(boolean disValor) {
		this.disValor = disValor;
	}

	public boolean isDisPtoControl() {
		return disPtoControl;
	}

	public void setDisPtoControl(boolean disPtoControl) {
		this.disPtoControl = disPtoControl;
	}

	public boolean isDisMonto() {
		return disMonto;
	}

	public void setDisMonto(boolean disMonto) {
		this.disMonto = disMonto;
	}

	public boolean isDisValorCambio() {
		return disValorCambio;
	}

	public void setDisValorCambio(boolean disValorCambio) {
		this.disValorCambio = disValorCambio;
	}

	public boolean isDisFecha() {
		return disFecha;
	}

	public void setDisFecha(boolean disFecha) {
		this.disFecha = disFecha;
	}

	public boolean isDisCosto() {
		return disCosto;
	}

	public void setDisCosto(boolean disCosto) {
		this.disCosto = disCosto;
	}

	public boolean isDisReal() {
		return disReal;
	}

	public void setDisReal(boolean disReal) {
		this.disReal = disReal;
	}

	public boolean isDisCuenta() {
		return disCuenta;
	}

	public void setDisCuenta(boolean disCuenta) {
		this.disCuenta = disCuenta;
	}

	public boolean isDisOtrosGastos() {
		return disOtrosGastos;
	}

	public void setDisOtrosGastos(boolean disOtrosGastos) {
		this.disOtrosGastos = disOtrosGastos;
	}

	public boolean isDisOpEdicion() {
		return disOpEdicion;
	}

	public void setDisOpEdicion(boolean disOpEdicion) {
		this.disOpEdicion = disOpEdicion;
	}

	public boolean isDisBtnEditar() {
		return disBtnEditar;
	}

	public void setDisBtnEditar(boolean disBtnEditar) {
		this.disBtnEditar = disBtnEditar;
	}

	public boolean isColTipoPerdida() {
		return colTipoPerdida;
	}

	public void setColTipoPerdida(boolean colTipoPerdida) {
		this.colTipoPerdida = colTipoPerdida;
	}

	public boolean isDisTipoPerdida() {
		return disTipoPerdida;
	}

	public void setDisTipoPerdida(boolean disTipoPerdida) {
		this.disTipoPerdida = disTipoPerdida;
	}

	public boolean isColFechaOcurrencia() {
		return colFechaOcurrencia;
	}

	public void setColFechaOcurrencia(boolean colFechaOcurrencia) {
		this.colFechaOcurrencia = colFechaOcurrencia;
	}

	public boolean isColFechaDescubrimiento() {
		return colFechaDescubrimiento;
	}

	public void setColFechaDescubrimiento(boolean colFechaDescubrimiento) {
		this.colFechaDescubrimiento = colFechaDescubrimiento;
	}

	public boolean isDisFechaOcurrencia() {
		return disFechaOcurrencia;
	}

	public void setDisFechaOcurrencia(boolean disFechaOcurrencia) {
		this.disFechaOcurrencia = disFechaOcurrencia;
	}

	public boolean isDisFechaDescubrimiento() {
		return disFechaDescubrimiento;
	}

	public void setDisFechaDescubrimiento(boolean disFechaDescubrimiento) {
		this.disFechaDescubrimiento = disFechaDescubrimiento;
	}

	public boolean isColCausaProbable() {
		return colCausaProbable;
	}

	public void setColCausaProbable(boolean colCausaProbable) {
		this.colCausaProbable = colCausaProbable;
	}

	public boolean isDisCausaProbable() {
		return disCausaProbable;
	}

	public void setDisCausaProbable(boolean disCausaProbable) {
		this.disCausaProbable = disCausaProbable;
	}

	public boolean isColDescripcion() {
		return colDescripcion;
	}

	public void setColDescripcion(boolean colDescripcion) {
		this.colDescripcion = colDescripcion;
	}

	public boolean isColDescripcionDetallada() {
		return colDescripcionDetallada;
	}

	public void setColDescripcionDetallada(boolean colDescripcionDetallada) {
		this.colDescripcionDetallada = colDescripcionDetallada;
	}

	public boolean isColBloqueo() {
		return colBloqueo;
	}

	public void setColBloqueo(boolean colBloqueo) {
		this.colBloqueo = colBloqueo;
	}

	public boolean isDisDescripcion() {
		return disDescripcion;
	}

	public void setDisDescripcion(boolean disDescripcion) {
		this.disDescripcion = disDescripcion;
	}

	public boolean isDisDescripcionDetallada() {
		return disDescripcionDetallada;
	}

	public void setDisDescripcionDetallada(boolean disDescripcionDetallada) {
		this.disDescripcionDetallada = disDescripcionDetallada;
	}

	public boolean isDisBloqueo() {
		return disBloqueo;
	}

	public void setDisBloqueo(boolean disBloqueo) {
		this.disBloqueo = disBloqueo;
	}

	public boolean isDisRecupReal() {
		return disRecupReal;
	}

	public void setDisRecupReal(boolean disRecupReal) {
		this.disRecupReal = disRecupReal;
	}

	public boolean isDisPerdResidual() {
		return disPerdResidual;
	}

	public void setDisPerdResidual(boolean disPerdResidual) {
		this.disPerdResidual = disPerdResidual;
	}

	public boolean isDisUsuario() {
		return disUsuario;
	}

	public void setDisUsuario(boolean disUsuario) {
		this.disUsuario = disUsuario;
	}

	public boolean isColFechaRegistro() {
		return colFechaRegistro;
	}

	public void setColFechaRegistro(boolean colFechaRegistro) {
		this.colFechaRegistro = colFechaRegistro;
	}

	public boolean isDisFechaRegistro() {
		return disFechaRegistro;
	}

	public void setDisFechaRegistro(boolean disFechaRegistro) {
		this.disFechaRegistro = disFechaRegistro;
	}

	public boolean isDisPanelAdicionales() {
		return disPanelAdicionales;
	}

	public void setDisPanelAdicionales(boolean disPanelAdicionales) {
		this.disPanelAdicionales = disPanelAdicionales;
	}

	public boolean isDisPanelCostos() {
		return disPanelCostos;
	}

	public void setDisPanelCostos(boolean disPanelCostos) {
		this.disPanelCostos = disPanelCostos;
	}

	public boolean isDisPanelRecuperaciones() {
		return disPanelRecuperaciones;
	}

	public void setDisPanelRecuperaciones(boolean disPanelRecuperaciones) {
		this.disPanelRecuperaciones = disPanelRecuperaciones;
	}

	public boolean isColPanelAdicionales() {
		return colPanelAdicionales;
	}

	public void setColPanelAdicionales(boolean colPanelAdicionales) {
		this.colPanelAdicionales = colPanelAdicionales;
	}

	public boolean isColPanelCostos() {
		return colPanelCostos;
	}

	public void setColPanelCostos(boolean colPanelCostos) {
		this.colPanelCostos = colPanelCostos;
	}

	public boolean isColPanelRecuperaciones() {
		return colPanelRecuperaciones;
	}

	public void setColPanelRecuperaciones(boolean colPanelRecuperaciones) {
		this.colPanelRecuperaciones = colPanelRecuperaciones;
	}

	public RoPermisosDetalleEvento getPermisosDetalleEvento() {
		return permisosDetalleEvento;
	}

	public void setPermisosDetalleEvento(
			RoPermisosDetalleEvento permisosDetalleEvento) {
		this.permisosDetalleEvento = permisosDetalleEvento;
	}

	public boolean isReqCodigo() {
		return reqCodigo;
	}

	public void setReqCodigo(boolean reqCodigo) {
		this.reqCodigo = reqCodigo;
	}

	public boolean isReqAgencia() {
		return reqAgencia;
	}

	public void setReqAgencia(boolean reqAgencia) {
		this.reqAgencia = reqAgencia;
	}

	public boolean isReqEvento() {
		return reqEvento;
	}

	public void setReqEvento(boolean reqEvento) {
		this.reqEvento = reqEvento;
	}

	public boolean isReqProceso() {
		return reqProceso;
	}

	public void setReqProceso(boolean reqProceso) {
		this.reqProceso = reqProceso;
	}

	public boolean isReqMoneda() {
		return reqMoneda;
	}

	public void setReqMoneda(boolean reqMoneda) {
		this.reqMoneda = reqMoneda;
	}

	public boolean isReqNegocio() {
		return reqNegocio;
	}

	public void setReqNegocio(boolean reqNegocio) {
		this.reqNegocio = reqNegocio;
	}

	public boolean isReqDepartamento() {
		return reqDepartamento;
	}

	public void setReqDepartamento(boolean reqDepartamento) {
		this.reqDepartamento = reqDepartamento;
	}

	public boolean isReqFactorRiesgo() {
		return reqFactorRiesgo;
	}

	public void setReqFactorRiesgo(boolean reqFactorRiesgo) {
		this.reqFactorRiesgo = reqFactorRiesgo;
	}

	public boolean isReqValor() {
		return reqValor;
	}

	public void setReqValor(boolean reqValor) {
		this.reqValor = reqValor;
	}

	public boolean isReqPtoControl() {
		return reqPtoControl;
	}

	public void setReqPtoControl(boolean reqPtoControl) {
		this.reqPtoControl = reqPtoControl;
	}

	public boolean isReqMonto() {
		return reqMonto;
	}

	public void setReqMonto(boolean reqMonto) {
		this.reqMonto = reqMonto;
	}

	public boolean isReqValorCambio() {
		return reqValorCambio;
	}

	public void setReqValorCambio(boolean reqValorCambio) {
		this.reqValorCambio = reqValorCambio;
	}

	public boolean isReqFechaOcurrencia() {
		return reqFechaOcurrencia;
	}

	public void setReqFechaOcurrencia(boolean reqFechaOcurrencia) {
		this.reqFechaOcurrencia = reqFechaOcurrencia;
	}

	public boolean isReqFechaDescubrimiento() {
		return reqFechaDescubrimiento;
	}

	public void setReqFechaDescubrimiento(boolean reqFechaDescubrimiento) {
		this.reqFechaDescubrimiento = reqFechaDescubrimiento;
	}

	public boolean isReqFechaRegistro() {
		return reqFechaRegistro;
	}

	public void setReqFechaRegistro(boolean reqFechaRegistro) {
		this.reqFechaRegistro = reqFechaRegistro;
	}

	public boolean isReqCosto() {
		return reqCosto;
	}

	public void setReqCosto(boolean reqCosto) {
		this.reqCosto = reqCosto;
	}

	public boolean isReqReal() {
		return reqReal;
	}

	public void setReqReal(boolean reqReal) {
		this.reqReal = reqReal;
	}

	public boolean isReqCuenta() {
		return reqCuenta;
	}

	public void setReqCuenta(boolean reqCuenta) {
		this.reqCuenta = reqCuenta;
	}

	public boolean isReqOtrosGastos() {
		return reqOtrosGastos;
	}

	public void setReqOtrosGastos(boolean reqOtrosGastos) {
		this.reqOtrosGastos = reqOtrosGastos;
	}

	public boolean isReqOpEdicion() {
		return reqOpEdicion;
	}

	public void setReqOpEdicion(boolean reqOpEdicion) {
		this.reqOpEdicion = reqOpEdicion;
	}

	public boolean isReqBtnEditar() {
		return reqBtnEditar;
	}

	public void setReqBtnEditar(boolean reqBtnEditar) {
		this.reqBtnEditar = reqBtnEditar;
	}

	public boolean isReqTipoPerdida() {
		return reqTipoPerdida;
	}

	public void setReqTipoPerdida(boolean reqTipoPerdida) {
		this.reqTipoPerdida = reqTipoPerdida;
	}

	public boolean isReqCausaProbable() {
		return reqCausaProbable;
	}

	public void setReqCausaProbable(boolean reqCausaProbable) {
		this.reqCausaProbable = reqCausaProbable;
	}

	public boolean isReqDescripcion() {
		return reqDescripcion;
	}

	public void setReqDescripcion(boolean reqDescripcion) {
		this.reqDescripcion = reqDescripcion;
	}

	public boolean isReqDescripcionDetallada() {
		return reqDescripcionDetallada;
	}

	public void setReqDescripcionDetallada(boolean reqDescripcionDetallada) {
		this.reqDescripcionDetallada = reqDescripcionDetallada;
	}

	public boolean isReqBloqueo() {
		return reqBloqueo;
	}

	public void setReqBloqueo(boolean reqBloqueo) {
		this.reqBloqueo = reqBloqueo;
	}

	public boolean isReqRecupReal() {
		return reqRecupReal;
	}

	public void setReqRecupReal(boolean reqRecupReal) {
		this.reqRecupReal = reqRecupReal;
	}

	public boolean isReqPerdResidual() {
		return reqPerdResidual;
	}

	public void setReqPerdResidual(boolean reqPerdResidual) {
		this.reqPerdResidual = reqPerdResidual;
	}

	public boolean isReqUsuario() {
		return reqUsuario;
	}

	public void setReqUsuario(boolean reqUsuario) {
		this.reqUsuario = reqUsuario;
	}

	public boolean isReqPanelAdicionales() {
		return reqPanelAdicionales;
	}

	public void setReqPanelAdicionales(boolean reqPanelAdicionales) {
		this.reqPanelAdicionales = reqPanelAdicionales;
	}

	public boolean isReqPanelCostos() {
		return reqPanelCostos;
	}

	public void setReqPanelCostos(boolean reqPanelCostos) {
		this.reqPanelCostos = reqPanelCostos;
	}

	public boolean isReqPanelRecuperaciones() {
		return reqPanelRecuperaciones;
	}

	public void setReqPanelRecuperaciones(boolean reqPanelRecuperaciones) {
		this.reqPanelRecuperaciones = reqPanelRecuperaciones;
	}

	public boolean isColPanelCualitativo() {
		return colPanelCualitativo;
	}

	public void setColPanelCualitativo(boolean colPanelCualitativo) {
		this.colPanelCualitativo = colPanelCualitativo;
	}

	public boolean isDisPanelCualitativo() {
		return disPanelCualitativo;
	}

	public void setDisPanelCualitativo(boolean disPanelCualitativo) {
		this.disPanelCualitativo = disPanelCualitativo;
	}

	public boolean isCmbValorControl() {
		return cmbValorControl;
	}

	public void setCmbValorControl(boolean cmbValorControl) {
		this.cmbValorControl = cmbValorControl;
	}

	public boolean isPnlEditarCualitativo() {
		return pnlEditarCualitativo;
	}

	public void setPnlEditarCualitativo(boolean pnlEditarCualitativo) {
		this.pnlEditarCualitativo = pnlEditarCualitativo;
	}

	public boolean isBtnAnadirCualitativo() {
		return btnAnadirCualitativo;
	}

	public void setBtnAnadirCualitativo(boolean btnAnadirCualitativo) {
		this.btnAnadirCualitativo = btnAnadirCualitativo;
	}

	public RoControlEvento getRoControlEventoVista() {
		return roControlEventoVista;
	}

	public void setRoControlEventoVista(RoControlEvento roControlEventoVista) {
		this.roControlEventoVista = roControlEventoVista;
	}

	public int getIdControlEvento() {
		return idControlEvento;
	}

	public void setIdControlEvento(int idControlEvento) {
		this.idControlEvento = idControlEvento;
	}

	public int getIdValControl() {
		return idValControl;
	}

	public void setIdValControl(int idValControl) {
		this.idValControl = idValControl;
	}

	public List<RoControl> getControlesTodos() {
		return controlesTodos;
	}

	public void setControlesTodos(List<RoControl> controlesTodos) {
		this.controlesTodos = controlesTodos;
	}

	public List<RoControlEvento> getControlesEvento() {
		return controlesEvento;
	}

	public void setControlesEvento(List<RoControlEvento> controlesEvento) {
		this.controlesEvento = controlesEvento;
	}

	public List<RoControlValor> getRoControlValores() {
		return roControlValores;
	}

	public void setRoControlValores(List<RoControlValor> roControlValores) {
		this.roControlValores = roControlValores;
	}

	public boolean isTxtValorControl() {
		return txtValorControl;
	}

	public void setTxtValorControl(boolean txtValorControl) {
		this.txtValorControl = txtValorControl;
	}

	public String getValorControl() {
		return valorControl;
	}

	public void setValorControl(String valorControl) {
		this.valorControl = valorControl;
	}

	public boolean isReqPanelCualitativo() {
		return reqPanelCualitativo;
	}

	public void setReqPanelCualitativo(boolean reqPanelCualitativo) {
		this.reqPanelCualitativo = reqPanelCualitativo;
	}

	public int getIdProbabilidadDespues() {
		return idProbabilidadDespues;
	}

	public void setIdProbabilidadDespues(int idProbabilidadDespues) {
		this.idProbabilidadDespues = idProbabilidadDespues;
	}

	public int getIdConsecuenciaAntes() {
		return idConsecuenciaAntes;
	}

	public void setIdConsecuenciaAntes(int idConsecuenciaAntes) {
		this.idConsecuenciaAntes = idConsecuenciaAntes;
	}

	public int getIdConsecuenciaDespues() {
		return idConsecuenciaDespues;
	}

	public void setIdConsecuenciaDespues(int idConsecuenciaDespues) {
		this.idConsecuenciaDespues = idConsecuenciaDespues;
	}

	public List<ParamProbabilidadRiesgo> getProbabilidadesTodos() {
		return probabilidadesTodos;
	}

	public void setProbabilidadesTodos(
			List<ParamProbabilidadRiesgo> probabilidadesTodos) {
		this.probabilidadesTodos = probabilidadesTodos;
	}

	public List<ParamConsecuenciaImpacto> getConsecuenciaTodos() {
		return consecuenciaTodos;
	}

	public void setConsecuenciaTodos(
			List<ParamConsecuenciaImpacto> consecuenciaTodos) {
		this.consecuenciaTodos = consecuenciaTodos;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public boolean isTxtValorControl2() {
		return txtValorControl2;
	}

	public void setTxtValorControl2(boolean txtValorControl2) {
		this.txtValorControl2 = txtValorControl2;
	}

	public String getCalifSeleccionada() {
		return califSeleccionada;
	}

	public void setCalifSeleccionada(String califSeleccionada) {
		this.califSeleccionada = califSeleccionada;
	}

	public boolean isColPanelCualitativoAux() {
		return colPanelCualitativoAux;
	}

	public void setColPanelCualitativoAux(boolean colPanelCualitativoAux) {
		this.colPanelCualitativoAux = colPanelCualitativoAux;
	}

	public boolean isColPanelCualitativo2() {
		return colPanelCualitativo2;
	}

	public void setColPanelCualitativo2(boolean colPanelCualitativo2) {
		this.colPanelCualitativo2 = colPanelCualitativo2;
	}

	public RoDeveFaro getRoDeveFaro() {
		return roDeveFaro;
	}

	public void setRoDeveFaro(RoDeveFaro roDeveFaro) {
		this.roDeveFaro = roDeveFaro;
	}

	public List<RoDeveFaro> getDeveFarosDetalleEvento() {
		return deveFarosDetalleEvento;
	}

	public void setDeveFarosDetalleEvento(
			List<RoDeveFaro> deveFarosDetalleEvento) {
		this.deveFarosDetalleEvento = deveFarosDetalleEvento;
	}

	public boolean isBtnAnadirDeveFaro() {
		return btnAnadirDeveFaro;
	}

	public void setBtnAnadirDeveFaro(boolean btnAnadirDeveFaro) {
		this.btnAnadirDeveFaro = btnAnadirDeveFaro;
	}

	public boolean isPnlEditarDeveFaro() {
		return pnlEditarDeveFaro;
	}

	public void setPnlEditarDeveFaro(boolean pnlEditarDeveFaro) {
		this.pnlEditarDeveFaro = pnlEditarDeveFaro;
	}

	public int getIdFactorRiesgo() {
		return idFactorRiesgo;
	}

	public void setIdFactorRiesgo(int idFactorRiesgo) {
		this.idFactorRiesgo = idFactorRiesgo;
	}

	public boolean isPnlRoDetalleEventoEditar() {
		return pnlRoDetalleEventoEditar;
	}

	public void setPnlRoDetalleEventoEditar(boolean pnlRoDetalleEventoEditar) {
		this.pnlRoDetalleEventoEditar = pnlRoDetalleEventoEditar;
	}

	public boolean isBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(boolean btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public boolean isBtnGuardarEditado() {
		return btnGuardarEditado;
	}

	public void setBtnGuardarEditado(boolean btnGuardarEditado) {
		this.btnGuardarEditado = btnGuardarEditado;
	}

	public boolean isBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(boolean btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public boolean isBtnCancelarEditado() {
		return btnCancelarEditado;
	}

	public void setBtnCancelarEditado(boolean btnCancelarEditado) {
		this.btnCancelarEditado = btnCancelarEditado;
	}

	public RoRespPro getRoRespProceso() {
		return roRespProceso;
	}

	public void setRoRespProceso(RoRespPro roRespProceso) {
		this.roRespProceso = roRespProceso;
	}

	public RoProceso getRoProceso() {
		return roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

	public List<RoProceso> getSubprocesosUsuario() {
		return subprocesosUsuario;
	}

	public void setSubprocesosUsuario(List<RoProceso> subprocesosUsuario) {
		this.subprocesosUsuario = subprocesosUsuario;
	}

	public String getAncestroProceso() {
		return ancestroProceso;
	}

	public void setAncestroProceso(String ancestroProceso) {
		this.ancestroProceso = ancestroProceso;
	}

	public int getIdSubProcesoSeleccionado() {
		return idSubProcesoSeleccionado;
	}

	public void setIdSubProcesoSeleccionado(int idSubProcesoSeleccionado) {
		this.idSubProcesoSeleccionado = idSubProcesoSeleccionado;
	}

	public String getNombreProcesoSeleccionado() {
		return nombreProcesoSeleccionado;
	}

	public void setNombreProcesoSeleccionado(String nombreProcesoSeleccionado) {
		this.nombreProcesoSeleccionado = nombreProcesoSeleccionado;
	}

	// -------------------------------------------------Get y
	// Set----------------------------------------------------------------//

	public RoControles getRoControlesControlador() {
		return roControlesControlador;
	}

	public void setRoControlesControlador(RoControles roControlesControlador) {
		this.roControlesControlador = roControlesControlador;
	}

	public RoControles getRoControlesVista() {
		return roControlesVista;
	}

	public void setRoControlesVista(RoControles roControlesVista) {
		this.roControlesVista = roControlesVista;
	}

	public List<RoControles> getListaRoControles() {
		return listaRoControles;
	}

	public void setListaRoControles(List<RoControles> listaRoControles) {
		this.listaRoControles = listaRoControles;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoControlador() {
		return paramProbabilidadRiesgoControlador;
	}

	public void setParamProbabilidadRiesgoControlador(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoControlador) {
		this.paramProbabilidadRiesgoControlador = paramProbabilidadRiesgoControlador;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoVista() {
		return paramProbabilidadRiesgoVista;
	}

	public void setParamProbabilidadRiesgoVista(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoVista) {
		this.paramProbabilidadRiesgoVista = paramProbabilidadRiesgoVista;
	}

	public List<ParamProbabilidadRiesgo> getListaParamProbabilidadRiesgo() {
		return listaParamProbabilidadRiesgo;
	}

	public void setListaParamProbabilidadRiesgo(
			List<ParamProbabilidadRiesgo> listaParamProbabilidadRiesgo) {
		this.listaParamProbabilidadRiesgo = listaParamProbabilidadRiesgo;
	}

	public RoProbabilidadEvento getRoProbabilidadEventoControlador() {
		return roProbabilidadEventoControlador;
	}

	public void setRoProbabilidadEventoControlador(
			RoProbabilidadEvento roProbabilidadEventoControlador) {
		this.roProbabilidadEventoControlador = roProbabilidadEventoControlador;
	}

	public RoProbabilidadEvento getRoProbabilidadEventoVista() {
		return roProbabilidadEventoVista;
	}

	public void setRoProbabilidadEventoVista(
			RoProbabilidadEvento roProbabilidadEventoVista) {
		this.roProbabilidadEventoVista = roProbabilidadEventoVista;
	}

	public List<RoProbabilidadEvento> getListaRoProbabilidadEvento() {
		return listaRoProbabilidadEvento;
	}

	public void setListaRoProbabilidadEvento(
			List<RoProbabilidadEvento> listaRoProbabilidadEvento) {
		this.listaRoProbabilidadEvento = listaRoProbabilidadEvento;
	}

	public RoResponsable getRoResponsableControlador() {
		return roResponsableControlador;
	}

	public void setRoResponsableControlador(
			RoResponsable roResponsableControlador) {
		this.roResponsableControlador = roResponsableControlador;
	}

	public RoResponsable getRoResponsableVista() {
		return roResponsableVista;
	}

	public void setRoResponsableVista(RoResponsable roResponsableVista) {
		this.roResponsableVista = roResponsableVista;
	}

	public List<RoResponsable> getListaRoResponsable() {
		return listaRoResponsable;
	}

	public void setListaRoResponsable(List<RoResponsable> listaRoResponsable) {
		this.listaRoResponsable = listaRoResponsable;
	}

	public boolean isPnlRoTipoControl() {
		return pnlRoTipoControl;
	}

	public void setPnlRoTipoControl(boolean pnlRoTipoControl) {
		this.pnlRoTipoControl = pnlRoTipoControl;
	}

	public boolean isBtnAñadirControl() {
		return btnAñadirControl;
	}

	public void setBtnAñadirControl(boolean btnAñadirControl) {
		this.btnAñadirControl = btnAñadirControl;
	}

	public boolean isBtnGuardarControl() {
		return btnGuardarControl;
	}

	public void setBtnGuardarControl(boolean btnGuardarControl) {
		this.btnGuardarControl = btnGuardarControl;
	}

	public boolean isBtnCancelarControl() {
		return btnCancelarControl;
	}

	public void setBtnCancelarControl(boolean btnCancelarControl) {
		this.btnCancelarControl = btnCancelarControl;
	}

	public int getIdCalcProbSeleccionado() {
		return idCalcProbSeleccionado;
	}

	public void setIdCalcProbSeleccionado(int idCalcProbSeleccionado) {
		this.idCalcProbSeleccionado = idCalcProbSeleccionado;
	}

	public BigDecimal getCalcIm() {
		return calcIm;
	}

	public void setCalcIm(BigDecimal calcIm) {
		this.calcIm = calcIm;
	}

	public BigDecimal getMult() {
		return mult;
	}

	public void setMult(BigDecimal mult) {
		this.mult = mult;
	}

	public int getCalculoProbSeleccionado() {
		return calculoProbSeleccionado;
	}

	public void setCalculoProbSeleccionado(int calculoProbSeleccionado) {
		this.calculoProbSeleccionado = calculoProbSeleccionado;
	}

	// public int getIdTipoRiesgo() {
	// return idTipoRiesgo;
	// }
	//
	// public void setIdTipoRiesgo(int idTipoRiesgo) {
	// this.idTipoRiesgo = idTipoRiesgo;
	// }

	public int getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
	}

	public BigDecimal getValoracion() {
		return valoracion;
	}

	public void setValoracion(BigDecimal valoracion) {
		this.valoracion = valoracion;
	}

	public String getCalculoDenomDelControl() {
		return calculoDenomDelControl;
	}

	public void setCalculoDenomDelControl(String calculoDenomDelControl) {
		this.calculoDenomDelControl = calculoDenomDelControl;
	}

	public String getClasificacionFinalDelRiesgo() {
		return clasificacionFinalDelRiesgo;
	}

	public void setClasificacionFinalDelRiesgo(
			String clasificacionFinalDelRiesgo) {
		this.clasificacionFinalDelRiesgo = clasificacionFinalDelRiesgo;
	}

	public BigDecimal getCalP() {
		return calP;
	}

	public void setCalP(BigDecimal calP) {
		this.calP = calP;
	}

	public BigDecimal getCalT() {
		return calT;
	}

	public void setCalT(BigDecimal calT) {
		this.calT = calT;
	}

	public BigDecimal getCalF() {
		return calF;
	}

	public void setCalF(BigDecimal calF) {
		this.calF = calF;
	}

	public BigDecimal getCalD() {
		return calD;
	}

	public void setCalD(BigDecimal calD) {
		this.calD = calD;
	}

	public BigDecimal getCalEF() {
		return calEF;
	}

	public void setCalEF(BigDecimal calEF) {
		this.calEF = calEF;
	}

	public BigDecimal getCalSA() {
		return calSA;
	}

	public void setCalSA(BigDecimal calSA) {
		this.calSA = calSA;
	}

	public boolean isTipoGuardarControl() {
		return tipoGuardarControl;
	}

	public void setTipoGuardarControl(boolean tipoGuardarControl) {
		this.tipoGuardarControl = tipoGuardarControl;
	}

	public BigDecimal getPromedioControl() {
		return promedioControl;
	}

	public void setPromedioControl(BigDecimal promedioControl) {
		this.promedioControl = promedioControl;
	}

	public BigDecimal getCalREP() {
		return calREP;
	}

	public void setCalREP(BigDecimal calREP) {
		this.calREP = calREP;
	}

	public BigDecimal getCalBCP() {
		return calBCP;
	}

	public void setCalBCP(BigDecimal calBCP) {
		this.calBCP = calBCP;
	}

	public BigDecimal getCalLEG() {
		return calLEG;
	}

	public void setCalLEG(BigDecimal calLEG) {
		this.calLEG = calLEG;
	}

	public int getIdImpactoEconómico() {
		return idImpactoEconómico;
	}

	public void setIdImpactoEconómico(int idImpactoEconómico) {
		this.idImpactoEconómico = idImpactoEconómico;
	}

	public List<RoConsecuenciaImpacto> getListaRoConsecuenciaImpacto() {
		return listaRoConsecuenciaImpacto;
	}

	public void setListaRoConsecuenciaImpacto(
			List<RoConsecuenciaImpacto> listaRoConsecuenciaImpacto) {
		this.listaRoConsecuenciaImpacto = listaRoConsecuenciaImpacto;
	}

	public RoConsecuenciaImpacto getRoConsecuenciaImpactoContrololador() {
		return roConsecuenciaImpactoContrololador;
	}

	public void setRoConsecuenciaImpactoContrololador(
			RoConsecuenciaImpacto roConsecuenciaImpactoContrololador) {
		this.roConsecuenciaImpactoContrololador = roConsecuenciaImpactoContrololador;
	}

	public RoConsecuenciaImpacto getRoConsecuenciaImpactoVista() {
		return roConsecuenciaImpactoVista;
	}

	public void setRoConsecuenciaImpactoVista(
			RoConsecuenciaImpacto roConsecuenciaImpactoVista) {
		this.roConsecuenciaImpactoVista = roConsecuenciaImpactoVista;
	}

	public double getValorPerdida() {
		return valorPerdida;
	}

	public void setValorPerdida(double valorPerdida) {
		this.valorPerdida = valorPerdida;
	}

	public double getPerdida() {
		return perdida;
	}

	public void setPerdida(double perdida) {
		this.perdida = perdida;
	}

	public boolean isColPromedio() {
		return colPromedio;
	}

	public void setColPromedio(boolean colPromedio) {
		this.colPromedio = colPromedio;
	}

	public List<RoProbabilidadEvento> getProbabilidadEventoTodos() {
		return listaRoProbabilidadEvento;
	}

	public void setProbabilidadEventoTodos(
			List<RoProbabilidadEvento> listaRoProbabilidadEvento) {
		this.listaRoProbabilidadEvento = listaRoProbabilidadEvento;
	}

	public BigDecimal getValoracionDelControls() {
		return valoracionDelControls;
	}

	public void setValoracionDelControls(BigDecimal valoracionDelControls) {
		this.valoracionDelControls = valoracionDelControls;
	}

	public boolean isColNumeroOcurrencias() {
		return colNumeroOcurrencias;
	}

	public void setColNumeroOcurrencias(boolean colNumeroOcurrencias) {
		this.colNumeroOcurrencias = colNumeroOcurrencias;
	}

	public boolean isColNumOcur() {
		return colNumOcur;
	}

	public void setColNumOcur(boolean colNumOcur) {
		this.colNumOcur = colNumOcur;
	}

	public boolean isReqNumOcur() {
		return reqNumOcur;
	}

	public void setReqNumOcur(boolean reqNumOcur) {
		this.reqNumOcur = reqNumOcur;
	}

	public boolean isDisNumOcur() {
		return disNumOcur;
	}

	public void setDisNumOcur(boolean disNumOcur) {
		this.disNumOcur = disNumOcur;
	}

	public int getNumOcur() {
		return numOcur;
	}

	public void setNumOcur(int numOcur) {
		this.numOcur = numOcur;
	}

	public boolean isColPanelIndicadoresRiesgo() {
		return colPanelIndicadoresRiesgo;
	}

	public void setColPanelIndicadoresRiesgo(boolean colPanelIndicadoresRiesgo) {
		this.colPanelIndicadoresRiesgo = colPanelIndicadoresRiesgo;
	}

	public boolean isDisPanelIndicadores() {
		return disPanelIndicadores;
	}

	public void setDisPanelIndicadores(boolean disPanelIndicadores) {
		this.disPanelIndicadores = disPanelIndicadores;
	}

	public boolean isBtnAnadirIndicador() {
		return btnAnadirIndicador;
	}

	public void setBtnAnadirIndicador(boolean btnAnadirIndicador) {
		this.btnAnadirIndicador = btnAnadirIndicador;
	}

	public boolean isPnlEditarIndicador() {
		return pnlEditarIndicador;
	}

	public void setPnlEditarIndicador(boolean pnlEditarIndicador) {
		this.pnlEditarIndicador = pnlEditarIndicador;
	}

	public RoTipoIndicadorRiesgo getroTipoIndicadorRiesgoVista() {
		return roTipoIndicadorRiesgoVista;
	}

	public void setroTipoIndicadorRiesgoVista(
			RoTipoIndicadorRiesgo roTipoIndicadorRiesgoVista) {
		this.roTipoIndicadorRiesgoVista = roTipoIndicadorRiesgoVista;
	}

	public int getIdTipoIndicadorRiesgo() {
		return idTipoIndicadorRiesgo;
	}

	public void setIdTipoIndicadorRiesgo(int idTipoIndicadorRiesgo) {
		this.idTipoIndicadorRiesgo = idTipoIndicadorRiesgo;
	}

	public List<RoTipoIndicadorRiesgo> getTipoIndicadorTodos() {
		return tipoIndicadorTodos;
	}

	public void setTipoIndicadorTodos(
			List<RoTipoIndicadorRiesgo> tipoIndicadorTodos) {
		this.tipoIndicadorTodos = tipoIndicadorTodos;
	}

	public Date getFechaEvin() {
		return fechaEvin;
	}

	public void setFechaEvin(Date fechaEvin) {
		this.fechaEvin = fechaEvin;
	}

	public List<RoEventoIndicador> getIndicadoresDetalleEventoTodos() {
		return indicadoresDetalleEventoTodos;
	}

	public void setIndicadoresDetalleEventoTodos(
			List<RoEventoIndicador> indicadoresDetalleEventoTodos) {
		this.indicadoresDetalleEventoTodos = indicadoresDetalleEventoTodos;
	}

	public RoEventoIndicador getRoEventoIndicadorVista() {
		return roEventoIndicadorVista;
	}

	public void setRoEventoIndicadorVista(
			RoEventoIndicador roEventoIndicadorVista) {
		this.roEventoIndicadorVista = roEventoIndicadorVista;
	}

	public List<RoTipoIndicadorRiesgo> gettipoIndicadoresPorProcesoDeve() {
		return tipoIndicadoresPorProcesoDeve;
	}

	public void settipoIndicadoresPorProcesoDeve(
			List<RoTipoIndicadorRiesgo> tipoIndicadoresPorProcesoDeve) {
		this.tipoIndicadoresPorProcesoDeve = tipoIndicadoresPorProcesoDeve;
	}

	public RoTipoIndicadorRiesgo getTipoIndicadorVista() {
		return tipoIndicadorVista;
	}

	public void setTipoIndicadorVista(RoTipoIndicadorRiesgo tipoIndicadorVista) {
		this.tipoIndicadorVista = tipoIndicadorVista;
	}

	public String getTipoIndicador() {
		return tipoIndicador;
	}

	public void setTipoIndicador(String tipoIndicador) {
		this.tipoIndicador = tipoIndicador;
	}

	public boolean isMostrarPorcentaje() {
		return esPorcentaje;
	}

	public void setMostrarPorcentaje(boolean mostrarPorcentaje) {
		this.esPorcentaje = mostrarPorcentaje;
	}

	public Double getTotalTransaccionesEvin() {
		return totalTransaccionesEvin;
	}

	public void setTotalTransaccionesEvin(Double totalTransaccionesEvin) {
		this.totalTransaccionesEvin = totalTransaccionesEvin;
	}

	public boolean isEsPorcentaje() {
		return esPorcentaje;
	}

	public void setEsPorcentaje(boolean esPorcentaje) {
		this.esPorcentaje = esPorcentaje;
	}

	public int getEfectividad() {
		return efectividad;
	}

	public void setEfectividad(int efectividad) {
		this.efectividad = efectividad;
	}

	public boolean isPnlProbabilidad() {
		return pnlProbabilidad;
	}

	public void setPnlProbabilidad(boolean pnlProbabilidad) {
		this.pnlProbabilidad = pnlProbabilidad;
	}

	public BigDecimal getRiesgoResidual() {
		return riesgoResidual;
	}

	public void setRiesgoResidual(BigDecimal riesgoResidual) {
		this.riesgoResidual = riesgoResidual;
	}

	public List<RoControlParamImpRep> getParamImpRepTodos() {
		return paramImpRepTodos;
	}

	public void setParamImpRepTodos(List<RoControlParamImpRep> paramImpRepTodos) {
		this.paramImpRepTodos = paramImpRepTodos;
	}

	public Long getIdParamImpRep() {
		return idParamImpRep;
	}

	public void setIdParamImpRep(Long idParamImpRep) {
		this.idParamImpRep = idParamImpRep;
	}

	public boolean isDisableSelectionDetalleEvento() {
		return disableSelectionDetalleEvento;
	}

	public void setDisableSelectionDetalleEvento(
			boolean disableSelectionDetalleEvento) {
		this.disableSelectionDetalleEvento = disableSelectionDetalleEvento;
	}

	public BigDecimal getPorcentajeEvin() {
		return porcentajeEvin;
	}

	public void setPorcentajeEvin(BigDecimal porcentajeEvin) {
		this.porcentajeEvin = porcentajeEvin;
	}

	public RoDetalleEvento getRoDetalleEventoVista() {
		return roDetalleEventoVista;
	}

	public void setRoDetalleEventoVista(RoDetalleEvento roDetalleEventoVista) {
		this.roDetalleEventoVista = roDetalleEventoVista;
	}

	public float getOtrosGastosDeveDespues() {
		return otrosGastosDeveDespues;
	}

	public void setOtrosGastosDeveDespues(float otrosGastosDeveDespues) {
		this.otrosGastosDeveDespues = otrosGastosDeveDespues;
	}

	public String getNombreProbabilidadAntes() {
		return nombreProbabilidadAntes;
	}

	public void setNombreProbabilidadAntes(String nombreProbabilidadAntes) {
		this.nombreProbabilidadAntes = nombreProbabilidadAntes;
	}

	public boolean isMostrarPanelControles() {
		return mostrarPanelControles;
	}

	public void setMostrarPanelControles(boolean mostrarPanelControles) {
		this.mostrarPanelControles = mostrarPanelControles;
	}

	public boolean isBolDespuesDeControles() {
		return bolDespuesDeControles;
	}

	public void setBolDespuesDeControles(boolean bolDespuesDeControles) {
		this.bolDespuesDeControles = bolDespuesDeControles;
	}

	public int getNumOcurDespues() {
		return numOcurDespues;
	}

	public void setNumOcurDespues(int numOcurDespues) {
		this.numOcurDespues = numOcurDespues;
	}

	public BigDecimal getValorEvin() {
		return valorEvin;
	}

	public void setValorEvin(BigDecimal valorEvin) {
		this.valorEvin = valorEvin;
	}

	// public RoTipoIndicadorRiesgo getTipoIndicadorRiesgoSeleccionado() {
	// return tipoIndicadorRiesgoSeleccionado;
	// }
	//
	// public void setTipoIndicadorRiesgoSeleccionado(
	// RoTipoIndicadorRiesgo tipoIndicadorRiesgoSeleccionado) {
	// this.tipoIndicadorRiesgoSeleccionado = tipoIndicadorRiesgoSeleccionado;
	// }

	// -----------------------------------------------------------------------------------------------------------------//
}
