package com.hc.ro.vista.admin.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.type.CalculationEnum;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.util.Constants;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoAriesgo;
import com.hc.ro.modelo.RoCalifRiesgo;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoFactorRiesgo;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoTipoPerdida;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioParamConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioParamProbabilidadRiesgo;
import com.hc.ro.negocio.ServicioRoAgencia;
import com.hc.ro.negocio.ServicioRoAriesgo;
import com.hc.ro.negocio.ServicioRoCalifRiesgo;
import com.hc.ro.negocio.ServicioRoConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioRoDepartamento;
import com.hc.ro.negocio.ServicioRoDetalleEvento;
import com.hc.ro.negocio.ServicioRoDeveFaro;
import com.hc.ro.negocio.ServicioRoEvento;
import com.hc.ro.negocio.ServicioRoFactorRiesgo;
import com.hc.ro.negocio.ServicioRoNegocio;
import com.hc.ro.negocio.ServicioRoProbabilidadEvento;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoResponsable;
import com.hc.ro.negocio.ServicioRoTipoPerdida;
import com.hc.ro.negocio.ServicioSisUsuario;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.media.sound.ModelWavetable;

@ManagedBean
@ViewScoped
public class ControladorReporteDetalladoDinamico {
	@EJB
	ServicioRoDeveFaro servicioRoDeveFaro;
	@EJB
	ServicioRoDetalleEvento servicioRoDetalleEvento;
	@EJB
	ServicioRoEvento servicioRoEvento;
	@EJB
	ServicioRoProbabilidadEvento servicioRoProbabilidadEvento;
	@EJB
	ServicioRoConsecuenciaImpacto servicioRoConsecuenciaImpacto;
	@EJB
	ServicioRoAgencia servicioRoAgencia;
	@EJB
	ServicioRoDepartamento servicioRoDepartamento;
	@EJB
	ServicioRoProceso servicioRoProceso;
	@EJB
	ServicioRoTipoPerdida servicioRoTipoPerdida;
	@EJB
	ServicioRoResponsable servicioRoResponsable;
	@EJB
	ServicioRoNegocio servicioRoNegocio;
	@EJB
	ServicioRoFactorRiesgo servicioRoFactorRiesgo;
	@EJB
	ServicioParamConsecuenciaImpacto servicioParamConsecuenciaImpacto;
	@EJB
	ServicioParamProbabilidadRiesgo servicioParamProbabilidadRiesgo;
	@EJB
	ServicioRoAriesgo servicioRoAriesgo;
	@EJB
	ServicioRoCalifRiesgo servicioRoCalifRiesgo;
	@EJB
	ServicioSisUsuario servicioSisUsuario;

	// manejadores
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	public final static String nombrePagina = "/paginas/ReporteDetalladoDinamico.jsf";

	// FILTROS
	private String[] seleccionFiltros;
	private List<String> selectFiltros;

	// LISTAS
	private List<String> agenciasFiltro;
	private List<RoAgencia> agenciasTodos;
	private TreeNode agenciasVista;
	private TreeNode[] agenciasSeleccionadas;
	private ArrayList<TreeNode> nodosTodosAgencias;

	private List<String> departamentosFiltro;
	private List<RoDepartamento> departamentosTodos;
	private TreeNode departamentosVista;
	private TreeNode[] departamentosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosDepartamentos;

	private List<String> procesosFiltro;
	private List<RoProceso> procesosTodos;
	private TreeNode procesosVista;
	private TreeNode[] procesosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosProcesos;

	private List<String> camposReporteSeleccionados;

	private Boolean colTipo;
	private Boolean colCodigo;
	private Boolean colAgencia;
	private Boolean colEvento;
	private Boolean colProceso;
	private Boolean colNegocio;
	private Boolean colDepartamento;
	private Boolean colFactorDeRiesgo;
	private Boolean colTipoDePerdida;
	private Boolean colPuntoDeControl;
	private Boolean colFechaDeOcurrencia;
	private Boolean colFechaDeDescubrimiento;
	private Boolean colFechaDeRegistro;
	private Boolean colPerdida;
	private Boolean colMontoRecuperado;
	private Boolean colCostoDeRecuperacion;
	private Boolean colRecuperacionReal;
	private Boolean colPerdidaResidual;
	private Boolean colUsuario;
	private Boolean colCausaProbable;
	private Boolean colDescripcion;
	private Boolean colDescripcionDetallada;
	private Boolean colNumeroOcurrencias;

	private Boolean bseleccionarTodosCamposReporte;

	private List<String> usuariosFiltro;
	private List<SisUsuario> usuariosTodos;

	private List<String> negociosFiltro;
	private List<RoNegocio> negociosTodos;
	private TreeNode negociosVista;
	private TreeNode[] negociosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosNegocios;

	private List<String> eventosFiltro;
	private List<RoEvento> eventosTodos;
	private TreeNode eventosVista;
	private TreeNode[] eventosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosEventos;

	private List<String> factorRiesgosFiltro;

	private List<String> tipoRegistroFiltros;

	private String nivelEventoFiltro;
	private Collection<String> nivelEventoTodos;
	private boolean mostrarBtnMapaInherente;
	private boolean mostrarBtnMapaResidual;

	private List<RoFactorRiesgo> factorRiesgosTodos;
	private List<String> tipoPerdidasFiltro;
	private List<RoTipoPerdida> tipoPerdidasTodos;
	private List<RoDetalleEvento> roDetalleEventosTodos;
	private List<RoDetalleEvento> roDetalleEventosAuxiliar;
	private List<RoDetalleEvento> detallesEventoSeleccionados;

	private List<RoAriesgo> ariesgoTodos;
	private List<RoCalifRiesgo> calificacionesTodos;

	private int columnas;
	private int tipoFiltro;

	private Date fechaInicio;
	private Date fechaFin;
	private String variableGraficar;
	private String nombreVariableGraficar;
	private String variableAgrupacion;
	private String variableGraficar2;
	private String nombreVariableGraficar2;
	private boolean mostrarBarra;
	private boolean mostrarColumnas;
	private boolean mostrarAgencia;
	private PieChartModel modelPie;
	private PieChartModel modelPie2;
	private BarChartModel modelBar;
	private HorizontalBarChartModel modelBarHorizontal;

	private boolean btodosUsuarios;
	private boolean btodosFactorRiesgo;
	private boolean btodosTipoPerdida;

	private String nuloPerdida;
	private String nuloFactor;
	private String nuloAgencia;
	private String nuloEvento;
	private String nuloNegocio;
	private String nuloProceso;
	private String nuloUsuario;
	private String nuloDepartamento;

	private String[] selectedColumnas;

	private Integer numMax;

	public ControladorReporteDetalladoDinamico() {
		super();

		tipoPerdidasFiltro = new ArrayList<String>();
		agenciasFiltro = new ArrayList<String>();
		agenciasTodos = new ArrayList<RoAgencia>();
		agenciasVista = new DefaultTreeNode();

		departamentosFiltro = new ArrayList<String>();
		departamentosTodos = new ArrayList<RoDepartamento>();
		departamentosVista = new DefaultTreeNode();

		procesosFiltro = new ArrayList<String>();
		procesosTodos = new ArrayList<RoProceso>();
		procesosVista = new DefaultTreeNode();

		usuariosFiltro = new ArrayList<String>();
		usuariosTodos = new ArrayList<SisUsuario>();

		negociosFiltro = new ArrayList<String>();
		negociosTodos = new ArrayList<RoNegocio>();
		negociosVista = new DefaultTreeNode();

		eventosFiltro = new ArrayList<String>();
		eventosTodos = new ArrayList<RoEvento>();
		eventosVista = new DefaultTreeNode();

		factorRiesgosFiltro = new ArrayList<String>();
		factorRiesgosTodos = new ArrayList<RoFactorRiesgo>();

		tipoPerdidasFiltro = new ArrayList<String>();
		tipoPerdidasTodos = new ArrayList<RoTipoPerdida>();

		nivelEventoTodos = new HashSet<String>();

		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();

		roDetalleEventosAuxiliar = new ArrayList<RoDetalleEvento>();
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		ariesgoTodos = new ArrayList<RoAriesgo>();

		fechaInicio = new Date();
		fechaFin = new Date();

		modelPie = new PieChartModel();
		modelPie2 = new PieChartModel();

		tipoFiltro = 1;

		// lista con los campos de las columnas que se seleccionaron para
		// mostrar en el detalle
		// camposReporteTodos = new ArrayList<String>();
	}

	@PostConstruct
	public void postConstruct() {

		//
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
		agenciasTodos = servicioRoAgencia.buscarTodos();
		departamentosTodos = servicioRoDepartamento.buscarTodos();
		procesosTodos = servicioRoProceso.buscarTodos();
		usuariosTodos = servicioSisUsuario.buscarTodos();
		negociosTodos = servicioRoNegocio.buscarTodos();
		factorRiesgosTodos = servicioRoFactorRiesgo.buscarTodos();
		tipoPerdidasTodos = servicioRoTipoPerdida.buscarTodos();
		llenarArbolAgencia();
		llenarArbolDepartamento();
		llenarArbolEvento();
		llenarArbolNegocio();
		llenarArbolProceso();
		mostrarBtnMapaInherente = false;
		mostrarBtnMapaResidual = false;
		mostrarColumnas = false;
		mostrarAgencia = false;

		// dataman
		dataManagerSesion.setConsecInherente(new String());
		dataManagerSesion.setConsecResidual(new String());
		dataManagerSesion.setFechaRegistro(null);
		dataManagerSesion.setIdDetalleSeleccionado(0);
		dataManagerSesion.setNumConsecInherente(0);
		dataManagerSesion.setNumConsecResidual(0);
		dataManagerSesion.setNumeroDeOcurrencias(0);
		dataManagerSesion.setNumProbaInherente(new String());
		dataManagerSesion.setNumProbaResidual(new String());
		dataManagerSesion.setProbaInherente(new String());
		dataManagerSesion.setProbaResidual(new String());
		dataManagerSesion.setRiesgoInherente(0);
		dataManagerSesion.setRiesgoResidual(0);
		// modelPie.set("Unifinsa", 100);

		fechaInicio.setMonth(8);
		fechaInicio.setYear(117);
		fechaFin.setMonth(9);
		fechaFin.setYear(118);

		// codigo para mostrar/ocultar columnas en la primera tabla
		//(campos reporte)


		selectFiltros = new ArrayList<String>();
		selectFiltros.add("Agencias");
		selectFiltros.add("Departamentos");
		selectFiltros.add("Procesos");
		selectFiltros.add("Negocios");
		selectFiltros.add("Usuarios");
		selectFiltros.add("Eventos");
		selectFiltros.add("Factor de Riesgos");
		selectFiltros.add("Tipo de Pérdida");
		selectFiltros.add("Tipo de Registro");
		selectFiltros.add("Ejemplo");

		// columnas de la primera tabla
		colTipo = false;
		colCodigo = false;
		colAgencia = false;
		colEvento = false;
		colProceso = false;
		colNegocio = false;
		colDepartamento = false;
		colFactorDeRiesgo = false;
		colTipoDePerdida = false;
		colPuntoDeControl = false;
		colFechaDeOcurrencia = false;
		colFechaDeDescubrimiento = false;
		colFechaDeRegistro = false;
		colPerdida = false;
		colMontoRecuperado = false;
		colCostoDeRecuperacion = false;
		colRecuperacionReal = false;
		colPerdidaResidual = false;
		colUsuario = false;
		colCausaProbable = false;
		colDescripcion = false;
		colDescripcionDetallada = false;
		colNumeroOcurrencias = false;
		bseleccionarTodosCamposReporte = false;
		camposReporteSeleccionados = new ArrayList<String>();

	}

	public String mostrarColum() {
		mostrarColumnas = true;
		return null;
	}

	public void mostrarAgen() {
		mostrarAgencia = true;
	}

	public void filtroVisualizarCamposReporte() {
		// for (int i = 0; i < camposReporteSeleccionados.size(); i++) {
		// System.out.println(""+camposReporteSeleccionados.get(i));
		// }

		for (String s : camposReporteSeleccionados) {

			if (s.equals("Tipo")) {
				colTipo = true;
			}
			if (s.equals("Codigo")) {
				colCodigo = true;
			}
			if (s.equals("Agencia")) {
				colAgencia = true;
			}
			if (s.equals("Evento")) {
				colEvento = true;
			}
			if (s.equals("Proceso")) {
				colProceso = true;
			}
			if (s.equals("Negocio")) {
				colNegocio = true;
			}
			if (s.equals("Departamento")) {
				colDepartamento = true;
			}
			if (s.equals("Factor de Riesgo")) {
				colFactorDeRiesgo = true;
			}
			if (s.equals("Punto de Control")) {
				colPuntoDeControl = true;
			}
			if (s.equals("Fecha de Ocurrencia")) {
				colFechaDeOcurrencia = true;
			}
			if (s.equals("Fecha de Descubrimiento")) {
				colFechaDeDescubrimiento = true;
			}
			if (s.equals("Fecha de Registro")) {
				colFechaDeRegistro = true;
			}
			if (s.equals("Perdida")) {
				colPerdida = true;
			}
			if (s.equals("Monto Recuperado")) {
				colMontoRecuperado = true;
			}
			if (s.equals("Costo de Recuperacion")) {
				colCostoDeRecuperacion = true;
			}
			if (s.equals("Recuperacion Real")) {
				colRecuperacionReal = true;
			}
			if (s.equals("Perdida Residual")) {
				colPerdidaResidual = true;
			}
			if (s.equals("Usuario")) {
				colUsuario = true;
			}
			if (s.equals("Causa Probable")) {
				colCausaProbable = true;
			}
			if (s.equals("Descripcion")) {
				colDescripcion = true;
			}
			if (s.equals("Descripcion Detallada")) {
				colDescripcionDetallada = true;
			}
			if (s.equals("Numero de Ocurrencias")) {
				colNumeroOcurrencias = true;
			}
		}

	}

	public void seleccionarTodosCamposReporte() {
		if (bseleccionarTodosCamposReporte) {

			camposReporteSeleccionados.add("Tipo");
			camposReporteSeleccionados.add("Codigo");
			camposReporteSeleccionados.add("Agencia");
			camposReporteSeleccionados.add("Evento");
			camposReporteSeleccionados.add("Proceso");
			camposReporteSeleccionados.add("Negocio");
			camposReporteSeleccionados.add("Departamento");
			camposReporteSeleccionados.add("Factor de Riesgo");
			camposReporteSeleccionados.add("Tipo de Perdida");
			camposReporteSeleccionados.add("Punto de Control");
			camposReporteSeleccionados.add("Fecha de Ocurrencia");
			camposReporteSeleccionados.add("Fecha de Descubrimiento");
			camposReporteSeleccionados.add("Fecha de Registro");
			camposReporteSeleccionados.add("Perdida");
			camposReporteSeleccionados.add("Monto Recuperado");
			camposReporteSeleccionados.add("Costo de Recuperacion");
			camposReporteSeleccionados.add("Recuperacion Real");
			camposReporteSeleccionados.add("Perdida Residual");
			camposReporteSeleccionados.add("Usuario");
			camposReporteSeleccionados.add("Causa Probable");
			camposReporteSeleccionados.add("Descripcion Probable");
			camposReporteSeleccionados.add("Causa Probable");
			camposReporteSeleccionados.add("Descripcion");
			camposReporteSeleccionados.add("Descripcion Detallada");
			camposReporteSeleccionados.add("Numero de Ocurrencias");

			colTipo = true;
			colCodigo = true;
			colAgencia = true;
			colEvento = true;
			colProceso = true;
			colNegocio = true;
			colDepartamento = true;
			colFactorDeRiesgo = true;
			colTipoDePerdida = true;
			colPuntoDeControl = true;
			colFechaDeOcurrencia = true;
			colFechaDeDescubrimiento = true;
			colFechaDeRegistro = true;
			colPerdida = true;
			colMontoRecuperado = true;
			colCostoDeRecuperacion = true;
			colRecuperacionReal = true;
			colPerdidaResidual = true;
			colUsuario = true;
			colCausaProbable = true;
			colDescripcion = true;
			colDescripcionDetallada = true;
			colNumeroOcurrencias = true;
		} else {

			camposReporteSeleccionados.clear(); // limpiamos la selecciontodos
												// en la vista
			encerarCamposReporte(); // ponemos todas las columnas del reporte
									// false
		}

	}

	public void recursivaArbolAgencia(List<RoAgencia> Agencias, TreeNode padre) {
		if (!Agencias.isEmpty()) {
			RoAgencia Agencia = new RoAgencia();
			for (int i = 0; i < Agencias.size(); i++) {
				Agencia = Agencias.get(i);
				nodosTodosAgencias.add(new DefaultTreeNode(Agencia
						.getNombreAgia(), padre));
				nodosTodosAgencias.get(nodosTodosAgencias.size() - 1)
						.setExpanded(true);
				List<RoAgencia> AgenciasAux = new ArrayList<RoAgencia>();
				AgenciasAux = servicioRoAgencia.buscarAgenciaPorPadre(Agencia
						.getNombreAgia());
				try {
					nodosTodosAgencias.get(nodosTodosAgencias.size() - 1)
							.setSelected(false);

				} catch (Exception e) {
					// TODO: handle exception
				}

				if (AgenciasAux != null) {

					recursivaArbolAgencia(
							AgenciasAux,
							nodosTodosAgencias.get(nodosTodosAgencias.size() - 1));
				}
			}
		}
	}

	public void recursivaArbolDepartamento(List<RoDepartamento> Departamentos,
			TreeNode padre) {
		if (!Departamentos.isEmpty()) {
			RoDepartamento Departamento = new RoDepartamento();
			for (int i = 0; i < Departamentos.size(); i++) {
				Departamento = Departamentos.get(i);
				nodosTodosDepartamentos.add(new DefaultTreeNode(Departamento
						.getNombreDept(), padre));
				nodosTodosDepartamentos.get(nodosTodosDepartamentos.size() - 1)
						.setExpanded(true);
				List<RoDepartamento> DepartamentosAux = new ArrayList<RoDepartamento>();
				DepartamentosAux = servicioRoDepartamento
						.buscarDepartamentoPorPadre(Departamento
								.getNombreDept());
				try {
					nodosTodosDepartamentos.get(
							nodosTodosDepartamentos.size() - 1).setSelected(
							false);

				} catch (Exception e) {
					// TODO: handle exception
				}

				if (DepartamentosAux != null) {

					recursivaArbolDepartamento(DepartamentosAux,
							nodosTodosDepartamentos.get(nodosTodosDepartamentos
									.size() - 1));
				}
			}
		}
	}

	public void recursivaArbolNegocio(List<RoNegocio> Negocios, TreeNode padre) {
		if (!Negocios.isEmpty()) {
			RoNegocio Negocio = new RoNegocio();
			for (int i = 0; i < Negocios.size(); i++) {
				Negocio = Negocios.get(i);
				nodosTodosNegocios.add(new DefaultTreeNode(Negocio
						.getNombreNego(), padre));
				nodosTodosNegocios.get(nodosTodosNegocios.size() - 1)
						.setExpanded(true);
				List<RoNegocio> NegociosAux = new ArrayList<RoNegocio>();
				NegociosAux = servicioRoNegocio.buscarNegocioPorPadre(Negocio
						.getNombreNego());
				try {
					nodosTodosNegocios.get(nodosTodosNegocios.size() - 1)
							.setSelected(false);

				} catch (Exception e) {
					// TODO: handle exception
				}

				if (NegociosAux != null) {

					recursivaArbolNegocio(
							NegociosAux,
							nodosTodosNegocios.get(nodosTodosNegocios.size() - 1));
				}
			}
		}
	}

	public void recursivaArbolProceso(List<RoProceso> Procesos, TreeNode padre) {
		if (!Procesos.isEmpty()) {
			RoProceso Proceso = new RoProceso();
			for (int i = 0; i < Procesos.size(); i++) {
				Proceso = Procesos.get(i);
				nodosTodosProcesos.add(new DefaultTreeNode(Proceso
						.getNombrePros(), padre));
				nodosTodosProcesos.get(nodosTodosProcesos.size() - 1)
						.setExpanded(true);
				List<RoProceso> ProcesosAux = new ArrayList<RoProceso>();
				ProcesosAux = servicioRoProceso.buscarProcesoPorPadre(Proceso
						.getNombrePros());
				try {
					nodosTodosProcesos.get(nodosTodosProcesos.size() - 1)
							.setSelected(false);

				} catch (Exception e) {
					// TODO: handle exception
				}

				if (ProcesosAux != null) {

					recursivaArbolProceso(
							ProcesosAux,
							nodosTodosProcesos.get(nodosTodosProcesos.size() - 1));
				}
			}
		}
	}

	public void recursivaArbolEvento(List<RoEvento> Eventos, TreeNode padre) {
		if (!Eventos.isEmpty()) {
			RoEvento Evento = new RoEvento();
			for (int i = 0; i < Eventos.size(); i++) {
				Evento = Eventos.get(i);
				nodosTodosEventos.add(new DefaultTreeNode(Evento
						.getNombreEven(), padre));
				nodosTodosEventos.get(nodosTodosEventos.size() - 1)
						.setExpanded(true);
				List<RoEvento> EventosAux = new ArrayList<RoEvento>();
				EventosAux = servicioRoEvento.buscarEventoPorPadre(Evento
						.getNombreEven());
				try {
					nodosTodosEventos.get(nodosTodosEventos.size() - 1)
							.setSelected(false);

				} catch (Exception e) {
					// TODO: handle exception
				}

				if (EventosAux != null) {

					recursivaArbolEvento(EventosAux,
							nodosTodosEventos.get(nodosTodosEventos.size() - 1));
				}
			}
		}
	}

	public void llenarArbolAgencia() {
		agenciasVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Agencias", agenciasVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosAgencias = new ArrayList<TreeNode>();
		recursivaArbolAgencia(
				servicioRoAgencia.buscarAgenciaPorPadre("Agencias"),
				arbolVirtual);
	}

	public void llenarArbolDepartamento() {
		departamentosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Departamentos",
				departamentosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosDepartamentos = new ArrayList<TreeNode>();
		recursivaArbolDepartamento(
				servicioRoDepartamento
						.buscarDepartamentoPorPadre("Departamentos"),
				arbolVirtual);
	}

	public void llenarArbolProceso() {
		procesosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Procesos", procesosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosProcesos = new ArrayList<TreeNode>();
		recursivaArbolProceso(
				servicioRoProceso.buscarProcesoPorPadre("Procesos"),
				arbolVirtual);
	}

	public void llenarArbolEvento() {
		eventosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Eventos", eventosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosEventos = new ArrayList<TreeNode>();
		recursivaArbolEvento(servicioRoEvento.buscarEventoPorPadre("Eventos"),
				arbolVirtual);
	}

	public void llenarArbolNegocio() {
		negociosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Negocios", negociosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosNegocios = new ArrayList<TreeNode>();
		recursivaArbolNegocio(
				servicioRoNegocio.buscarNegocioPorPadre("Negocios"),
				arbolVirtual);
	}

	public void pasarListaAgencias() {
		agenciasFiltro = new ArrayList<String>();
		for (TreeNode nodo : agenciasSeleccionadas) {
			agenciasFiltro.add(nodo.getData().toString());
		}
		try {
			agenciasFiltro.remove("Agencias");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void pasarListaDepartamentos() {
		departamentosFiltro = new ArrayList<String>();
		for (TreeNode nodo : departamentosSeleccionadas) {
			departamentosFiltro.add(nodo.getData().toString());
		}
		try {
			departamentosFiltro.remove("Departamentos");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void pasarListaNegocios() {
		negociosFiltro = new ArrayList<String>();
		for (TreeNode nodo : negociosSeleccionadas) {
			negociosFiltro.add(nodo.getData().toString());
		}
		try {
			negociosFiltro.remove("Negocios");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void pasarListaEventos() {
		eventosFiltro = new ArrayList<String>();
		for (TreeNode nodo : eventosSeleccionadas) {
			eventosFiltro.add(nodo.getData().toString());
		}
		try {
			eventosFiltro.remove("Eventos");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void pasarListaProcesos() {
		procesosFiltro = new ArrayList<String>();
		for (TreeNode nodo : procesosSeleccionadas) {
			procesosFiltro.add(nodo.getData().toString());
		}
		try {
			procesosFiltro.remove("Procesos");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void obtenerPadre(RoEvento evento) {
		if (evento.getAncestroEven().equals("Eventos")) {

		} else {
			obtenerPadre(servicioRoEvento.buscarEventoPorNombre(evento
					.getAncestroEven()));
		}
	}

	private boolean siEsHijo;

	public void esHijo(RoEvento evento, String padre) {
		if (!evento.getAncestroEven().equals("Eventos")) {
			if (evento.getAncestroEven().equals(padre)) {
				siEsHijo = true;

			} else {
				esHijo(servicioRoEvento.buscarEventoPorNombre(evento
						.getAncestroEven()), padre);
			}
		} else {
			siEsHijo = false;
		}
	}

	public void exportarPDF(ActionEvent actionEvent) throws JRException,
			IOException {
		Map<String, Object> parametros = new HashMap<String, Object>();

		File jasper = new File(FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/reportes/report2.jasper"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), parametros, new JRBeanCollectionDataSource(
						roDetalleEventosTodos));

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition",
				"attachment; filename=ReporteDetalladoDinámico.pdf");
		ServletOutputStream stream = response.getOutputStream();

		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}

	@SuppressWarnings("deprecation")
	public void exportarXLS(ActionEvent actionEvent) throws JRException,
			IOException {

		Map<String, Object> parametros = new HashMap<String, Object>();

		File jasper = new File(FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/reportes/report2.jasper"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), parametros, new JRBeanCollectionDataSource(
						roDetalleEventosTodos));

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/excel");
		response.addHeader("Content-disposition",
				"attachment; filename=ReporteDetalladoDinámico.xlsx");
		ServletOutputStream stream = response.getOutputStream();

		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
		exporter.exportReport();

		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();

	}

	@SuppressWarnings("deprecation")
	public void exportarDOC(ActionEvent actionEvent) throws JRException,
			IOException {

		Map<String, Object> parametros = new HashMap<String, Object>();

		File jasper = new File(FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/reportes/report2.jasper"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), parametros, new JRBeanCollectionDataSource(
						roDetalleEventosTodos));

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/word");
		response.addHeader("Content-disposition",
				"attachment; filename=ReporteDetalladoDinámico.docx");
		ServletOutputStream stream = response.getOutputStream();

		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
		exporter.exportReport();

		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();

	}

	public void encerarCamposReporte() {
		colTipo = false;
		colCodigo = false;
		colAgencia = false;
		colEvento = false;
		colProceso = false;
		colNegocio = false;
		colDepartamento = false;
		colFactorDeRiesgo = false;
		colTipoDePerdida = false;
		colPuntoDeControl = false;
		colFechaDeOcurrencia = false;
		colFechaDeDescubrimiento = false;
		colFechaDeRegistro = false;
		colPerdida = false;
		colMontoRecuperado = false;
		colCostoDeRecuperacion = false;
		colRecuperacionReal = false;
		colPerdidaResidual = false;
		colUsuario = false;
		colCausaProbable = false;

		colDescripcion = false;
		colDescripcionDetallada = false;
		colNumeroOcurrencias = false;

	}

	public void filtrar() {
		// pongo todos las columnas del reporte como no visibles
		encerarCamposReporte();

		// escojo las columnas a visualizar en el reporte segun el selecmanymenu
		// id="cmbCamposAutorizados"

		filtroVisualizarCamposReporte();

		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		pasarListaAgencias();
		pasarListaDepartamentos();
		pasarListaEventos();
		pasarListaNegocios();
		pasarListaProcesos();
		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();

		roDetalleEventosAuxiliar = servicioRoDetalleEvento
				.buscarEventosTodosFiltro(fechaInicio, fechaFin, tipoFiltro);
		List<Integer> roDeveFarosAux = new ArrayList<Integer>();
		for (RoDetalleEvento deve : roDetalleEventosAuxiliar) {
			roDeveFarosAux = servicioRoDeveFaro.buscarPorDeve(deve);
			for (String agencia : agenciasFiltro) {

				try {
					if (deve.getRoAgencia().getNombreAgia().equals(agencia)) {

						for (String departamento : departamentosFiltro) {
							try {
								if (deve.getRoDepartamento().getNombreDept()
										.equals(departamento)) {

									for (String proceso : procesosFiltro) {

										try {
											if (deve.getRoProceso()
													.getNombrePros()
													.equals(proceso)) {
												for (String usuario : usuariosFiltro) {
													try {
														if (deve.getSisUsuario()
																.getCodigoUsua() == Integer
																.parseInt(usuario)) {
															for (String negocio : negociosFiltro) {
																try {
																	if (deve.getRoNegocio1()
																			.getNombreNego()
																			.equals(negocio)) {
																		for (String evento : eventosFiltro) {
																			try {
																				if (deve.getRoEvento()
																						.getNombreEven()
																						.equals(evento)) {
																					for (String faro : factorRiesgosFiltro) {
																						boolean interruptor;
																						interruptor = false;
																						for (Integer deveFaro : roDeveFarosAux) {
																							if (deveFaro == Integer
																									.parseInt(faro)) {
																								interruptor = true;
																								break;
																							}
																						}
																						try {
																							if (deve.getRoFactorRiesgo()
																									.getCodigoFaro() == Integer
																									.parseInt(faro)
																									|| interruptor) {
																								for (String tipe : tipoPerdidasFiltro) {
																									try {
																										if (deve.getRoTipoPerdida()
																												.getCodigoTipe() == Integer
																												.parseInt(tipe)) {

																											for (String tire : tipoRegistroFiltros) {
																												try {
																													if (deve.getTipoCalifDeve()
																															.equals(tire)) {

																														// esta
																														// lista
																														// contiene
																														// los
																														// detalle
																														// evento
																														// sin
																														// considerar
																														// el
																														// num
																														// de
																														// ocurrencia(repeticiones)
																														roDetalleEventosTodos
																																.add(deve);

																													}
																												} catch (Exception e) {
																													// TODO:
																													// handle
																													// exception
																												}

																											}
																										}
																									} catch (Exception e) {
																										// TODO:
																										// handle
																										// exception
																									}

																								}
																							}
																						} catch (Exception e) {
																							// TODO:
																							// handle
																							// exception
																						}

																					}
																				}

																			} catch (Exception e) {
																				// TODO:
																				// handle
																				// exception
																			}

																		}
																	}

																} catch (Exception e) {
																	// TODO:
																	// handle
																	// exception
																}

															}
														}
													} catch (Exception e) {
														// TODO: handle
														// exception
													}

												}
											}
										} catch (Exception e) {
											// TODO: handle exception
										}

									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		System.out.println("rodetalleeventostodos"
				+ roDetalleEventosTodos.size());

		mostrarBarra = true;
		Float acum = (float) 0;
		Float acum2 = (float) 0;
		String param = new String("");
		
		modelPie = new PieChartModel();
		modelPie2 = new PieChartModel();
		modelBar = new BarChartModel();
		modelBarHorizontal = new HorizontalBarChartModel();

		modelPie.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
		modelPie.setLegendPosition("ne");
		modelPie.setShowDataLabels(true);
		modelPie.setFill(false);
		
		modelPie2
				.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
		modelPie2.setLegendPosition("ne");
		modelPie2.setShowDataLabels(true);
		modelPie2.setFill(false);

		modelBar.getAxis(AxisType.Y).setMin(0);
		modelBar.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
		modelBar.getAxis(AxisType.X).setTickAngle(45);
		modelBar.setLegendPosition("ne");
		modelBar.setExtender("extender");
		modelBar.setShowPointLabels(true);
		modelBar.setZoom(true);

		modelBarHorizontal
				.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
		modelBarHorizontal.setShowPointLabels(true);
		modelBarHorizontal.setBarMargin(4);
		modelBarHorizontal.setLegendPosition("ne");
		modelBarHorizontal.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		modelBarHorizontal.getAxis(AxisType.X).setTickAngle(90);
		modelBarHorizontal.setZoom(true);

		// CREANDO LAS SERIES(DATOS A GRAFICAR)
		BarChartSeries barrasVar1 = new BarChartSeries();
		BarChartSeries barrasVar2 = new BarChartSeries();
		LineChartSeries lineaVar2 = new LineChartSeries();
		try {
			if (variableAgrupacion.equals("Agencia")) {

				for (String string : agenciasFiltro) {
					acum = (float) 0;
					acum2 = (float) 0;
					param = string;
					for (RoDetalleEvento event : roDetalleEventosTodos) {

						if (event.getRoAgencia().getNombreAgia().equals(string)) {

							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}
					if (acum != 0.0) {
						// modelPie.set(
						// param
						// + ": "
						// + Float.parseFloat((new BigDecimal(
						// Float.toString(acum)).setScale(
						// 2, BigDecimal.ROUND_HALF_UP))
						// .toString()), acum);
						// modelPie2
						// .set(param
						// + ": "
						// + Float.parseFloat((new BigDecimal(
						// Float.toString(acum2))
						// .setScale(
						// 2,
						// BigDecimal.ROUND_HALF_UP))
						// .toString()), acum2);
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);

					}
				}

				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del comboBox
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}

			if (variableAgrupacion.equals("Departamento")) {
				for (String string : departamentosFiltro) {
					acum = (float) 0;
					acum2 = (float) 0;
					param = string;
					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (event.getRoDepartamento().getNombreDept()
								.equals(string)) {
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);
					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}
			if (variableAgrupacion.equals("Proceso")) {
				for (String string : procesosFiltro) {
					acum = (float) 0;
					acum2 = (float) 0;
					param = string;
					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (event.getRoProceso().getNombrePros().equals(string)) {
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);

					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}
			if (variableAgrupacion.equals("Negocio")) {
				for (String string : negociosFiltro) {
					acum = (float) 0;
					acum2 = (float) 0;
					param = string;
					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (event.getRoNegocio1().getNombreNego()
								.equals(string)) {
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {

						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);
					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}

			if (variableAgrupacion.equals("Usuario")) {
				for (String string : usuariosFiltro) {
					string = servicioSisUsuario.buscarPorId(
							Integer.parseInt(string)).getNombreUsua();
					acum = (float) 0;
					acum2 = (float) 0;
					param = string;
					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (event.getSisUsuario().getNombreUsua()
								.equals(string)) {
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);
					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}

			if (variableAgrupacion.equals("Evento")) {
				for (String string : eventosFiltro) {

					acum = (float) 0;
					acum2 = (float) 0;
					param = string;
					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (event.getRoEvento().getNombreEven().equals(string)) {
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);

					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}

			if (variableAgrupacion.equals("Factor de Riesgos")) {
				for (String string : factorRiesgosFiltro) {

					acum = (float) 0;
					acum2 = (float) 0;

					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (Integer.toString(
								event.getRoFactorRiesgo().getCodigoFaro())
								.equals(string)) {
							param = event.getRoFactorRiesgo().getNombreFaro();
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);

					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}

			if (variableAgrupacion.equals("Tipo de Pérdida")) {
				for (String string : tipoPerdidasFiltro) {

					acum = (float) 0;
					acum2 = (float) 0;

					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (Integer.toString(
								event.getRoTipoPerdida().getCodigoTipe())
								.equals(string)) {
							param = event.getRoTipoPerdida().getNombreTipe();
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);

					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}

			if (variableAgrupacion.equals("Tipo de Registro")) {
				for (String string : tipoRegistroFiltros) {

					acum = (float) 0;
					acum2 = (float) 0;
					param = string;
					for (RoDetalleEvento event : roDetalleEventosTodos) {
						if (event.getTipoCalifDeve().equals(string)) {
							switch (Integer.parseInt(variableGraficar)) {
							case 1:
								nombreVariableGraficar = "Pérdida";
								acum += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar = "Monto Recuperado";
								acum += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar = "Costo Recuperación";
								acum += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar = "Recuperación Real";
								acum += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar = "Pérdida Residual";
								acum += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar = "Número de Ocurrencias";
								acum += event.getNumOcur();
								break;
							default:
								break;
							}

							switch (Integer.parseInt(variableGraficar2)) {
							case 1:
								nombreVariableGraficar2 = "Pérdida";
								acum2 += event.getValorDeve();
								break;
							case 2:
								nombreVariableGraficar2 = "Monto Recuperado";
								acum2 += event.getMontoRecupDeve();
								break;
							case 3:
								nombreVariableGraficar2 = "Costo Recuperación";
								acum2 += event.getCostoRecupDeve();
								break;
							case 4:
								nombreVariableGraficar2 = "Recuperación Real";
								acum2 += event.getRealRecupDeve();
								break;
							case 5:
								nombreVariableGraficar2 = "Pérdida Residual";
								acum2 += event.getPerdidaResidualDeve();
								break;
							case 6:
								nombreVariableGraficar2 = "Número de Ocurrencias";
								acum2 += event.getNumOcur();
								break;
							default:
								break;
							}
						}
					}

					if (acum != 0.0) {
						barrasVar1.set(param, acum);
						barrasVar2.set(param, acum2);
						lineaVar2.set(param, acum2);
					}

				}
				// >> GUARDANDO EN LAS SERIES SOLO LOS N(NUMMAX) MAXIMOS, SI ES
				// NO
				// SE ESCOGIÓ LA OPCION "Todos" del cmb
				if (numMax != 1000) {
					try {
						if (barrasVar1.getData().size() > numMax) {
							// serie con n maximos de var 1
							barrasVar1 = getSerieConNmayores(barrasVar1, numMax);
						}

						if (barrasVar2.getData().size() > numMax) {
							BarChartSeries auxBarrasVar2 = new BarChartSeries();
							auxBarrasVar2 = barrasVar2;
							barrasVar2 = new BarChartSeries();
							// serie con n maximos para las mismos elementos de
							// var1
							Iterator it = barrasVar1.getData().entrySet()
									.iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								barrasVar2.set(pair.getKey(), auxBarrasVar2
										.getData().get(pair.getKey()));
							}
						}
						if (lineaVar2.getData().size() > numMax) {
							// serie con n maximos de var 2
							lineaVar2 = getSerieConNmayores(lineaVar2, numMax);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// << //

			}

			barrasVar1.setLabel(nombreVariableGraficar);
			barrasVar2.setLabel(nombreVariableGraficar2);
			lineaVar2.setLabel(nombreVariableGraficar2);

			// CREO LINECHART PARA EL GRAFICO 4
			LineChartSeries linea = new LineChartSeries();
			linea.setYaxis(AxisType.Y2);
			linea.setData(barrasVar2.getData());
			linea.setLabel(barrasVar2.getLabel());

			Axis yAxis = modelBar.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setLabel(nombreVariableGraficar);

			// 2do eje Y para el grafico 4
			Axis y2Axis = new LinearAxis(nombreVariableGraficar2);
			y2Axis.setMin(0);

			modelBar.getAxes().put(AxisType.Y2, y2Axis);

			// >> AGREGANDO VALORES AL PIE
			modelPie = llenarPie(barrasVar1, modelPie);
			// << //

			// >> AGREGANDO VALORES AL PIE 2
			// modelPie2.setData(modelPie.getData());
			modelPie2 = llenarPie(lineaVar2, modelPie2);
			// << //

			modelBar.addSeries(barrasVar1);
			modelBar.addSeries(linea);

			modelBarHorizontal.addSeries(barrasVar1);
			modelBarHorizontal.addSeries(barrasVar2);

			modelPie.setTitle(nombreVariableGraficar + " por "
					+ variableAgrupacion);
			modelPie2.setTitle(nombreVariableGraficar2 + " por "
					+ variableAgrupacion);
			modelBar.setTitle(nombreVariableGraficar + " vs "
					+ nombreVariableGraficar2 + " (agrupado por: "
					+ variableAgrupacion + ")");

			modelBarHorizontal.setTitle(nombreVariableGraficar + " vs "
					+ nombreVariableGraficar2 + " (agrupado por: "
					+ variableAgrupacion + ")");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al graficar!", e.toString()));
		}

	}

	public BarChartSeries getSerieConNmayores(BarChartSeries serie,
			Integer numMax) {
		System.out
				.println("tamaño de la serie antes:" + serie.getData().size());

		BarChartSeries aux = new BarChartSeries();
		String keyMaximo = "";

		aux = serie;

		serie = new BarChartSeries();// borrando data de la
										// serie

		for (int i = 0; i < numMax; i++) {
			keyMaximo = "";
			keyMaximo = getMaximo(aux);
			System.out.println("- keyMaximo" + keyMaximo);

			serie.set(keyMaximo, aux.getData().get(keyMaximo));
			System.out.println("- auxVar1" + aux.getData().get(keyMaximo));
			aux.getData().remove(keyMaximo);

		}
		System.out.println("tamaño de la serie despues:"
				+ serie.getData().size());
		return serie;

	}

	public LineChartSeries getSerieConNmayores(LineChartSeries serie,
			Integer numMax) {
		System.out
				.println("tamaño de la serie antes:" + serie.getData().size());

		LineChartSeries aux = new LineChartSeries();
		aux = serie;// respaldo de serie
		serie = new LineChartSeries();// borrando data de la
										// serie
		String keyMaximo = "";

		for (int i = 0; i < numMax; i++) {
			keyMaximo = "";
			keyMaximo = getMaximo(aux);
			System.out.println("- keyMaximo" + keyMaximo);

			serie.set(keyMaximo, aux.getData().get(keyMaximo));
			System.out.println("- auxVar1" + aux.getData().get(keyMaximo));
			aux.getData().remove(keyMaximo);
		}
		System.out.println("tamaño de la serie despues:"
				+ serie.getData().size());

		return serie;

	}

	public PieChartModel llenarPie(LineChartSeries lineaVar2,
			PieChartModel modelPie) {
		Iterator it = lineaVar2.getData().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			modelPie.set(
					(String) pair.getKey()
							+ ": "
							+ Float.parseFloat((new BigDecimal((pair.getValue()
									.toString())).setScale(2,
									BigDecimal.ROUND_HALF_UP)).toString()),
					(Number) pair.getValue());
		}

		return modelPie;
	}

	public PieChartModel llenarPie(BarChartSeries lineaVar2,
			PieChartModel modelPie) {

		Iterator it = lineaVar2.getData().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			modelPie.set(
					(String) pair.getKey()
							+ ": "
							+ Float.parseFloat((new BigDecimal((pair.getValue()
									.toString())).setScale(2,
									BigDecimal.ROUND_HALF_UP)).toString()),
					(Number) pair.getValue());
		}
		return modelPie;
	}

	@SuppressWarnings("rawtypes")
	public String getMaximo(BarChartSeries series) {
		Iterator it = series.getData().entrySet().iterator();
		Float max = (float) 0.0;
		String key = "";
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			if (Float.parseFloat(pair.getValue().toString()) > max) {
				max = Float.parseFloat(pair.getValue().toString());
				key = pair.getKey().toString();

			}
		}

		// System.out.println(">." + key + " : " + max);

		return key;
	}

	@SuppressWarnings("rawtypes")
	public String getMaximo(LineChartSeries series) {
		Iterator it = series.getData().entrySet().iterator();
		Float max = (float) 0.0;
		String key = "";
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			if (Float.parseFloat(pair.getValue().toString()) > max) {
				max = Float.parseFloat(pair.getValue().toString());
				key = pair.getKey().toString();

			}
		}

		// System.out.println(">." + key + " : " + max);

		return key;
	}

	public void seleccionarTodosUsuarios() {
		if (btodosUsuarios) {
			usuariosFiltro = new ArrayList<String>();
			for (SisUsuario usuario : usuariosTodos) {
				usuariosFiltro.add(Integer.toString(usuario.getCodigoUsua()));
			}
		} else {
			usuariosFiltro = new ArrayList<String>();
		}
	}

	public void seleccionarTodosFactorRiesgo() {
		if (btodosFactorRiesgo) {
			factorRiesgosFiltro = new ArrayList<String>();
			for (RoFactorRiesgo factor : factorRiesgosTodos) {
				factorRiesgosFiltro
						.add(Integer.toString(factor.getCodigoFaro()));

			}
		} else {
			factorRiesgosFiltro = new ArrayList<String>();
		}
	}

	public void seleccionarTodosTipoPerdida() {
		if (btodosTipoPerdida) {
			tipoPerdidasFiltro = new ArrayList<String>();
			for (RoTipoPerdida tipo : tipoPerdidasTodos) {
				tipoPerdidasFiltro.add(Integer.toString(tipo.getCodigoTipe()));
			}
		} else {
			tipoPerdidasFiltro = new ArrayList<String>();
		}
	}

	public void crearReporteCabecera(ActionEvent actionEvent) {
		// Se crea el documento
		try {
			// response para descargar
			ExternalContext externalContext = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) externalContext
					.getResponse();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// creamos el pdf y rotamos la pagina a horizontal
			Document document = new Document(PageSize.A4.rotate());
			PdfWriter.getInstance(document, baos);

			// abrimos el pdf
			if (!document.isOpen()) {
				document.open();
			}

			// obtenemos la instancia para descargar el pdf
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			/**
			 * Cabecera
			 */

			// seteamos el titulo del reporte
			Paragraph parrafoSoftware = new Paragraph(
					"Sistema de Riesgo Operativo", FontFactory.getFont("arial",
							20, Font.BOLD));

			Paragraph parrafoTitulo = new Paragraph(
					"Reporte Detallado Dinámico - Filtros",
					FontFactory.getFont("arial", 15, Font.BOLD));

			// insertamos la primera celda el titulo del reporte
			// tabla.addCell(parrafoTitulo); // color
			// seteamos la ubicacion de la imagen que queremos en el pdf para la
			// cabecera
			String logoURL = servletContext.getRealPath("") + File.separator
					+ "utils" + File.separator + "images" + File.separator
					+ "logoJEP.png";
			Image logo = Image.getInstance(logoURL);
			logo.scaleToFit(100, 24);
			logo.setAlignment(Chunk.ALIGN_RIGHT);

			// insertamos el logo a la celda
			// tabla.addCell(logo);
			parrafoTitulo.add(logo);
			// insertamos la tabla al documento
			document.add(parrafoSoftware);
			document.add(parrafoTitulo);

			// creamos parrafo para poner los detalles del reporte
			Paragraph parrafo = new Paragraph();

			// otenemos fecha y hora
			Date fecha1 = new Date();
			Calendar cal1 = Calendar.getInstance();

			// fecha
			Phrase frase = new Phrase("Fecha: ", FontFactory.getFont("arial",
					10, Font.BOLD));

			parrafo.add(frase);

			frase = new Phrase(new java.util.Date().toLocaleString(),
					FontFactory.getFont("arial", 10));

			parrafo.add(frase);

			// usuario
			frase = new Phrase("\nUsuario: ", FontFactory.getFont("arial", 10,
					Font.BOLD));

			parrafo.add(frase);

			frase = new Phrase(dataManagerSesion.getUsuarioSesion()
					.getNombreUsua(), FontFactory.getFont("arial", 10));

			parrafo.add(frase);

			// añadimos el parrafo al documento

			document.add(parrafo);
			/*
			 * for (String agencia : agenciasFiltro) { .println("Ag: " +
			 * agencia); } pasarListaDepartamentos(); for (String agencia :
			 * departamentosFiltro) { .println("De: " + agencia); }
			 * pasarListaEventos(); for (String evento : eventosFiltro) {
			 * .println("Ev: " + evento); } pasarListaNegocios(); for (String
			 * negocio : negociosFiltro) { .println("Ne: " + negocio); }
			 * pasarListaProcesos(); for (String agencia : procesosFiltro) {
			 * .println("Pr: " + agencia); }
			 */
			parrafo = new Paragraph();
			frase = new Phrase("Tipo de Reporte: ", FontFactory.getFont(
					"arial", 8, Font.BOLD));
			parrafo.add(frase);

			frase = new Phrase("Reporte Detallado Dinámico",
					FontFactory.getFont("arial", 8));
			parrafo.add(frase);

			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Fecha de Inicio: ", FontFactory.getFont(
					"arial", 8, Font.BOLD));
			parrafo.add(frase);

			frase = new Phrase(fechaInicio.toLocaleString() + ", ",
					FontFactory.getFont("arial", 8));
			parrafo.add(frase);

			frase = new Phrase("\tFecha de Fin: ", FontFactory.getFont("arial",
					8, Font.BOLD));
			parrafo.add(frase);

			frase = new Phrase(fechaFin.toLocaleString() + ", ",
					FontFactory.getFont("arial", 8));
			parrafo.add(frase);

			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Agencias: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : agenciasFiltro) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Departamentos: ", FontFactory.getFont("arial",
					8, Font.BOLD));
			parrafo.add(frase);
			for (String agencia : departamentosFiltro) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Procesos: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : procesosFiltro) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Usuarios: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : usuariosFiltro) {
				frase = new Phrase(servicioSisUsuario.buscarPorId(
						Integer.parseInt(agencia)).getNombreUsua()
						+ ", ", FontFactory.getFont("arial", 8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Negocios: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : negociosFiltro) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Eventos: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : eventosFiltro) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Factor de Riesgos: ", FontFactory.getFont(
					"arial", 8, Font.BOLD));
			parrafo.add(frase);
			for (String agencia : factorRiesgosFiltro) {
				frase = new Phrase(servicioRoFactorRiesgo.buscarPorId(
						Integer.parseInt(agencia)).getNombreFaro()
						+ ", ", FontFactory.getFont("arial", 8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Tipo de Perdida: ", FontFactory.getFont(
					"arial", 8, Font.BOLD));
			parrafo.add(frase);
			for (String agencia : tipoPerdidasFiltro) {
				frase = new Phrase(servicioRoTipoPerdida.buscarPorId(
						Integer.parseInt(agencia)).getNombreTipe()
						+ ", ", FontFactory.getFont("arial", 8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Tipo de Registro: ", FontFactory.getFont(
					"arial", 8, Font.BOLD));
			parrafo.add(frase);
			for (String agencia : tipoRegistroFiltros) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Agrupado por: ", FontFactory.getFont("arial",
					8, Font.BOLD));
			parrafo.add(frase);

			frase = new Phrase(variableAgrupacion, FontFactory.getFont("arial",
					8));
			parrafo.add(frase);

			document.add(parrafo);

			document.close();

			writePDFToResponse(externalContext, baos,
					"ReporteDetalladoDinámico");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void writePDFToResponse(ExternalContext externalContext,
			ByteArrayOutputStream baos, String fileName) throws IOException,
			DocumentException {
		externalContext.responseReset();
		externalContext.setResponseContentType("application/pdf");
		externalContext.setResponseHeader("Expires", "0");
		externalContext.setResponseHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		externalContext.setResponseHeader("Pragma", "public");
		externalContext.setResponseHeader("Content-disposition",
				"attachment;filename=" + fileName + ".pdf");
		externalContext.setResponseContentLength(baos.size());
		externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true",
				Collections.<String, Object> emptyMap());
		OutputStream out = externalContext.getResponseOutputStream();
		baos.writeTo(out);
		externalContext.responseFlushBuffer();

	}

	public List<String> getAgenciasFiltro() {
		return agenciasFiltro;
	}

	public void setAgenciasFiltro(List<String> agenciasFiltro) {
		this.agenciasFiltro = agenciasFiltro;
	}

	public List<RoAgencia> getAgenciasTodos() {
		return agenciasTodos;
	}

	public void setAgenciasTodos(List<RoAgencia> agenciasTodos) {
		this.agenciasTodos = agenciasTodos;
	}

	public List<String> getDepartamentosFiltro() {
		return departamentosFiltro;
	}

	public void setDepartamentosFiltro(List<String> departamentosFiltro) {
		this.departamentosFiltro = departamentosFiltro;
	}

	public List<RoDepartamento> getDepartamentosTodos() {
		return departamentosTodos;
	}

	public void setDepartamentosTodos(List<RoDepartamento> departamentosTodos) {
		this.departamentosTodos = departamentosTodos;
	}

	public List<String> getProcesosFiltro() {
		return procesosFiltro;
	}

	public void setProcesosFiltro(List<String> procesosFiltro) {
		this.procesosFiltro = procesosFiltro;
	}

	public List<RoProceso> getProcesosTodos() {
		return procesosTodos;
	}

	public void setProcesosTodos(List<RoProceso> procesosTodos) {
		this.procesosTodos = procesosTodos;
	}

	public List<String> getNegociosFiltro() {
		return negociosFiltro;
	}

	public void setNegociosFiltro(List<String> negociosFiltro) {
		this.negociosFiltro = negociosFiltro;
	}

	public List<RoNegocio> getNegociosTodos() {
		return negociosTodos;
	}

	public void setNegociosTodos(List<RoNegocio> negociosTodos) {
		this.negociosTodos = negociosTodos;
	}

	public List<String> getEventosFiltro() {
		return eventosFiltro;
	}

	public void setEventosFiltro(List<String> eventosFiltro) {
		this.eventosFiltro = eventosFiltro;
	}

	public List<RoEvento> getEventosTodos() {
		return eventosTodos;
	}

	public void setEventosTodos(List<RoEvento> eventosTodos) {
		this.eventosTodos = eventosTodos;
	}

	public List<String> getFactorRiesgosFiltro() {
		return factorRiesgosFiltro;
	}

	public void setFactorRiesgosFiltro(List<String> factorRiesgosFiltro) {
		this.factorRiesgosFiltro = factorRiesgosFiltro;
	}

	public List<RoFactorRiesgo> getFactorRiesgosTodos() {
		return factorRiesgosTodos;
	}

	public void setFactorRiesgosTodos(List<RoFactorRiesgo> factorRiesgosTodos) {
		this.factorRiesgosTodos = factorRiesgosTodos;
	}

	public List<String> getTipoPerdidasFiltro() {
		return tipoPerdidasFiltro;
	}

	public void setTipoPerdidasFiltro(List<String> tipoPerdidasFiltro) {
		this.tipoPerdidasFiltro = tipoPerdidasFiltro;
	}

	public List<RoTipoPerdida> getTipoPerdidasTodos() {
		return tipoPerdidasTodos;
	}

	public void setTipoPerdidasTodos(List<RoTipoPerdida> tipoPerdidasTodos) {
		this.tipoPerdidasTodos = tipoPerdidasTodos;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<RoAriesgo> getAriesgoTodos() {
		return ariesgoTodos;
	}

	public void setAriesgoTodos(List<RoAriesgo> ariesgoTodos) {
		this.ariesgoTodos = ariesgoTodos;
	}

	public List<RoCalifRiesgo> getCalificacionesTodos() {
		return calificacionesTodos;
	}

	public void setCalificacionesTodos(List<RoCalifRiesgo> calificacionesTodos) {
		this.calificacionesTodos = calificacionesTodos;
	}

	public List<RoDetalleEvento> getRoDetalleEventosTodos() {
		return roDetalleEventosTodos;
	}

	public void setRoDetalleEventosTodos(
			List<RoDetalleEvento> roDetalleEventosTodos) {
		this.roDetalleEventosTodos = roDetalleEventosTodos;
	}

	public List<RoDetalleEvento> getDetallesEventoSeleccionados() {
		return detallesEventoSeleccionados;
	}

	public void setDetallesEventoSeleccionados(
			List<RoDetalleEvento> detallesEventoSeleccionados) {
		this.detallesEventoSeleccionados = detallesEventoSeleccionados;
	}

	public List<String> getUsuariosFiltro() {
		return usuariosFiltro;
	}

	public void setUsuariosFiltro(List<String> usuariosFiltro) {
		this.usuariosFiltro = usuariosFiltro;
	}

	public List<SisUsuario> getUsuariosTodos() {
		return usuariosTodos;
	}

	public void setUsuariosTodos(List<SisUsuario> usuariosTodos) {
		this.usuariosTodos = usuariosTodos;
	}

	public List<RoDetalleEvento> getRoDetalleEventosAuxiliar() {
		return roDetalleEventosAuxiliar;
	}

	public void setRoDetalleEventosAuxiliar(
			List<RoDetalleEvento> roDetalleEventosAuxiliar) {
		this.roDetalleEventosAuxiliar = roDetalleEventosAuxiliar;
	}

	public TreeNode getAgenciasVista() {
		return agenciasVista;
	}

	public void setAgenciasVista(TreeNode agenciasVista) {
		this.agenciasVista = agenciasVista;
	}

	public TreeNode[] getAgenciasSeleccionadas() {
		return agenciasSeleccionadas;
	}

	public void setAgenciasSeleccionadas(TreeNode[] agenciasSeleccionadas) {
		this.agenciasSeleccionadas = agenciasSeleccionadas;
	}

	public ArrayList<TreeNode> getNodosTodosAgencias() {
		return nodosTodosAgencias;
	}

	public void setNodosTodosAgencias(ArrayList<TreeNode> nodosTodosAgencias) {
		this.nodosTodosAgencias = nodosTodosAgencias;
	}

	public TreeNode getDepartamentosVista() {
		return departamentosVista;
	}

	public void setDepartamentosVista(TreeNode departamentosVista) {
		this.departamentosVista = departamentosVista;
	}

	public TreeNode[] getDepartamentosSeleccionadas() {
		return departamentosSeleccionadas;
	}

	public void setDepartamentosSeleccionadas(
			TreeNode[] departamentosSeleccionadas) {
		this.departamentosSeleccionadas = departamentosSeleccionadas;
	}

	public ArrayList<TreeNode> getNodosTodosDepartamentos() {
		return nodosTodosDepartamentos;
	}

	public void setNodosTodosDepartamentos(
			ArrayList<TreeNode> nodosTodosDepartamentos) {
		this.nodosTodosDepartamentos = nodosTodosDepartamentos;
	}

	public TreeNode getProcesosVista() {
		return procesosVista;
	}

	public void setProcesosVista(TreeNode procesosVista) {
		this.procesosVista = procesosVista;
	}

	public TreeNode[] getProcesosSeleccionadas() {
		return procesosSeleccionadas;
	}

	public void setProcesosSeleccionadas(TreeNode[] procesosSeleccionadas) {
		this.procesosSeleccionadas = procesosSeleccionadas;
	}

	public ArrayList<TreeNode> getNodosTodosProcesos() {
		return nodosTodosProcesos;
	}

	public void setNodosTodosProcesos(ArrayList<TreeNode> nodosTodosProcesos) {
		this.nodosTodosProcesos = nodosTodosProcesos;
	}

	public TreeNode getNegociosVista() {
		return negociosVista;
	}

	public void setNegociosVista(TreeNode negociosVista) {
		this.negociosVista = negociosVista;
	}

	public TreeNode[] getNegociosSeleccionadas() {
		return negociosSeleccionadas;
	}

	public void setNegociosSeleccionadas(TreeNode[] negociosSeleccionadas) {
		this.negociosSeleccionadas = negociosSeleccionadas;
	}

	public ArrayList<TreeNode> getNodosTodosNegocios() {
		return nodosTodosNegocios;
	}

	public void setNodosTodosNegocios(ArrayList<TreeNode> nodosTodosNegocios) {
		this.nodosTodosNegocios = nodosTodosNegocios;
	}

	public TreeNode getEventosVista() {
		return eventosVista;
	}

	public void setEventosVista(TreeNode eventosVista) {
		this.eventosVista = eventosVista;
	}

	public TreeNode[] getEventosSeleccionadas() {
		return eventosSeleccionadas;
	}

	public void setEventosSeleccionadas(TreeNode[] eventosSeleccionadas) {
		this.eventosSeleccionadas = eventosSeleccionadas;
	}

	public ArrayList<TreeNode> getNodosTodosEventos() {
		return nodosTodosEventos;
	}

	public void setNodosTodosEventos(ArrayList<TreeNode> nodosTodosEventos) {
		this.nodosTodosEventos = nodosTodosEventos;
	}

	public void setNivelEventoTodos(Collection<String> nivelEventoTodos) {
		this.nivelEventoTodos = nivelEventoTodos;
	}

	public Collection<String> getNivelEventoTodos() {
		return nivelEventoTodos;
	}

	public String getNivelEventoFiltro() {
		return nivelEventoFiltro;
	}

	public void setNivelEventoFiltro(String nivelEventoFiltro) {
		this.nivelEventoFiltro = nivelEventoFiltro;
	}

	public boolean isSiEsHijo() {
		return siEsHijo;
	}

	public void setSiEsHijo(boolean siEsHijo) {
		this.siEsHijo = siEsHijo;
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}

	public boolean isMostrarBtnMapaInherente() {
		return mostrarBtnMapaInherente;
	}

	public void setMostrarBtnMapaInherente(boolean mostrarBtnMapaInherente) {
		this.mostrarBtnMapaInherente = mostrarBtnMapaInherente;
	}

	public boolean isMostrarBtnMapaResidual() {
		return mostrarBtnMapaResidual;
	}

	public void setMostrarBtnMapaResidual(boolean mostrarBtnMapaResidual) {
		this.mostrarBtnMapaResidual = mostrarBtnMapaResidual;
	}

	public List<String> getTipoRegistroFiltros() {
		return tipoRegistroFiltros;
	}

	public void setTipoRegistroFiltros(List<String> tipoRegistroFiltros) {
		this.tipoRegistroFiltros = tipoRegistroFiltros;
	}

	public PieChartModel getModelPie() {
		return modelPie;
	}

	public void setModelPie(PieChartModel modelPie) {
		this.modelPie = modelPie;
	}

	public String getVariableGraficar() {
		return variableGraficar;
	}

	public void setVariableGraficar(String variableGraficar) {
		this.variableGraficar = variableGraficar;
	}

	public String getVariableAgrupacion() {
		return variableAgrupacion;
	}

	public void setVariableAgrupacion(String variableAgrupacion) {
		this.variableAgrupacion = variableAgrupacion;
	}

	public String getNombreVariableGraficar() {
		return nombreVariableGraficar;
	}

	public void setNombreVariableGraficar(String nombreVariableGraficar) {
		this.nombreVariableGraficar = nombreVariableGraficar;
	}

	public BarChartModel getModelBar() {
		return modelBar;
	}

	public void setModelBar(BarChartModel modelBar) {
		this.modelBar = modelBar;
	}

	public String getVariableGraficar2() {
		return variableGraficar2;
	}

	public void setVariableGraficar2(String variableGraficar2) {
		this.variableGraficar2 = variableGraficar2;
	}

	public String getNombreVariableGraficar2() {
		return nombreVariableGraficar2;
	}

	public void setNombreVariableGraficar2(String nombreVariableGraficar2) {
		this.nombreVariableGraficar2 = nombreVariableGraficar2;
	}

	public boolean isMostrarBarra() {
		return mostrarBarra;
	}

	public void setMostrarBarra(boolean mostrarBarra) {
		this.mostrarBarra = mostrarBarra;
	}

	public boolean isBtodosUsuarios() {
		return btodosUsuarios;
	}

	public void setBtodosUsuarios(boolean btodosUsuarios) {
		this.btodosUsuarios = btodosUsuarios;
	}

	public boolean isBtodosFactorRiesgo() {
		return btodosFactorRiesgo;
	}

	public void setBtodosFactorRiesgo(boolean btodosFactorRiesgo) {
		this.btodosFactorRiesgo = btodosFactorRiesgo;
	}

	public boolean isBtodosTipoPerdida() {
		return btodosTipoPerdida;
	}

	public void setBtodosTipoPerdida(boolean btodosTipoPerdida) {
		this.btodosTipoPerdida = btodosTipoPerdida;
	}

	public String getNuloPerdida() {
		return nuloPerdida;
	}

	public void setNuloPerdida(String nuloPerdida) {
		this.nuloPerdida = nuloPerdida;
	}

	public String getNuloFactor() {
		return nuloFactor;
	}

	public void setNuloFactor(String nuloFactor) {
		this.nuloFactor = nuloFactor;
	}

	public String getNuloAgencia() {
		return nuloAgencia;
	}

	public void setNuloAgencia(String nuloAgencia) {
		this.nuloAgencia = nuloAgencia;
	}

	public String getNuloEvento() {
		return nuloEvento;
	}

	public void setNuloEvento(String nuloEvento) {
		this.nuloEvento = nuloEvento;
	}

	public String getNuloNegocio() {
		return nuloNegocio;
	}

	public void setNuloNegocio(String nuloNegocio) {
		this.nuloNegocio = nuloNegocio;
	}

	public String getNuloProceso() {
		return nuloProceso;
	}

	public void setNuloProceso(String nuloProceso) {
		this.nuloProceso = nuloProceso;
	}

	public String getNuloUsuario() {
		return nuloUsuario;
	}

	public void setNuloUsuario(String nuloUsuario) {
		this.nuloUsuario = nuloUsuario;
	}

	public String getNuloDepartamento() {
		return nuloDepartamento;
	}

	public void setNuloDepartamento(String nuloDepartamento) {
		this.nuloDepartamento = nuloDepartamento;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String[] getSeleccionFiltros() {
		return seleccionFiltros;
	}

	public void setSeleccionFiltros(String[] seleccionFiltros) {
		this.seleccionFiltros = seleccionFiltros;
	}

	public boolean isMostrarColumnas() {
		return mostrarColumnas;
	}

	public void setMostrarColumnas(boolean mostrarColumnas) {
		this.mostrarColumnas = mostrarColumnas;
	}

	public List<String> getSelectFiltros() {
		return selectFiltros;
	}

	public void setSelectFiltros(List<String> selectFiltros) {
		this.selectFiltros = selectFiltros;
	}

	public boolean isMostrarAgencia() {
		return mostrarAgencia;
	}

	public void setMostrarAgencia(boolean mostrarAgencia) {
		this.mostrarAgencia = mostrarAgencia;
	}

	public String[] getSelectedColumnas() {
		return selectedColumnas;
	}

	public void setSelectedColumnas(String[] selectedColumnas) {
		this.selectedColumnas = selectedColumnas;
	}

	public List<String> getCamposReporteSeleccionados() {
		return camposReporteSeleccionados;
	}

	public void setCamposReporteSeleccionados(
			List<String> camposReporteSeleccionados) {
		this.camposReporteSeleccionados = camposReporteSeleccionados;
	}

	public Boolean getColTipo() {
		return colTipo;
	}

	public void setColTipo(Boolean colTipo) {
		this.colTipo = colTipo;
	}

	public Boolean getColAgencia() {
		return colAgencia;
	}

	public void setColAgencia(Boolean colAgencia) {
		this.colAgencia = colAgencia;
	}

	public Boolean getColEvento() {
		return colEvento;
	}

	public void setColEvento(Boolean colEvento) {
		this.colEvento = colEvento;
	}

	public Boolean getColProceso() {
		return colProceso;
	}

	public void setColProceso(Boolean colProceso) {
		this.colProceso = colProceso;
	}

	public Boolean getColNegocio() {
		return colNegocio;
	}

	public void setColNegocio(Boolean colNegocio) {
		this.colNegocio = colNegocio;
	}

	public Boolean getColDepartamento() {
		return colDepartamento;
	}

	public void setColDepartamento(Boolean colDepartamento) {
		this.colDepartamento = colDepartamento;
	}

	public Boolean getColFactorDeRiesgo() {
		return colFactorDeRiesgo;
	}

	public void setColFactorDeRiesgo(Boolean colFactorDeRiesgo) {
		this.colFactorDeRiesgo = colFactorDeRiesgo;
	}

	public Boolean getColTipoDePerdida() {
		return colTipoDePerdida;
	}

	public void setColTipoDePerdida(Boolean colTipoDePerdida) {
		this.colTipoDePerdida = colTipoDePerdida;
	}

	public Boolean getColPuntoDeControl() {
		return colPuntoDeControl;
	}

	public void setColPuntoDeControl(Boolean colPuntoDeControl) {
		this.colPuntoDeControl = colPuntoDeControl;
	}

	public Boolean getColFechaDeOcurrencia() {
		return colFechaDeOcurrencia;
	}

	public void setColFechaDeOcurrencia(Boolean colFechaDeOcurrencia) {
		this.colFechaDeOcurrencia = colFechaDeOcurrencia;
	}

	public Boolean getColFechaDeDescubrimiento() {
		return colFechaDeDescubrimiento;
	}

	public void setColFechaDeDescubrimiento(Boolean colFechaDeDescubrimiento) {
		this.colFechaDeDescubrimiento = colFechaDeDescubrimiento;
	}

	public Boolean getColFechaDeRegistro() {
		return colFechaDeRegistro;
	}

	public void setColFechaDeRegistro(Boolean colFechaDeRegistro) {
		this.colFechaDeRegistro = colFechaDeRegistro;
	}

	public Boolean getColPerdida() {
		return colPerdida;
	}

	public void setColPerdida(Boolean colPerdida) {
		this.colPerdida = colPerdida;
	}

	public Boolean getColMontoRecuperado() {
		return colMontoRecuperado;
	}

	public void setColMontoRecuperado(Boolean colMontoRecuperado) {
		this.colMontoRecuperado = colMontoRecuperado;
	}

	public Boolean getColCostoDeRecuperacion() {
		return colCostoDeRecuperacion;
	}

	public void setColCostoDeRecuperacion(Boolean colCostoDeRecuperacion) {
		this.colCostoDeRecuperacion = colCostoDeRecuperacion;
	}

	public Boolean getColRecuperacionReal() {
		return colRecuperacionReal;
	}

	public void setColRecuperacionReal(Boolean colRecuperacionReal) {
		this.colRecuperacionReal = colRecuperacionReal;
	}

	public Boolean getColPerdidaResidual() {
		return colPerdidaResidual;
	}

	public void setColPerdidaResidual(Boolean colPerdidaResidual) {
		this.colPerdidaResidual = colPerdidaResidual;
	}

	public Boolean getColUsuario() {
		return colUsuario;
	}

	public void setColUsuario(Boolean colUsuario) {
		this.colUsuario = colUsuario;
	}

	public Boolean getColCausaProbable() {
		return colCausaProbable;
	}

	public void setColCausaProbable(Boolean colCausaProbable) {
		this.colCausaProbable = colCausaProbable;
	}

	public Boolean getColDescripcion() {
		return colDescripcion;
	}

	public void setColDescripcion(Boolean colDescripcion) {
		this.colDescripcion = colDescripcion;
	}

	public Boolean getColDescripcionDetallada() {
		return colDescripcionDetallada;
	}

	public void setColDescripcionDetallada(Boolean colDescripcionDetallada) {
		this.colDescripcionDetallada = colDescripcionDetallada;
	}

	public Boolean getColCodigo() {
		return colCodigo;
	}

	public void setColCodigo(Boolean colCodigo) {
		this.colCodigo = colCodigo;
	}

	public Boolean getBseleccionarTodosCamposReporte() {
		return bseleccionarTodosCamposReporte;
	}

	public void setBseleccionarTodosCamposReporte(
			Boolean bseleccionarTodosCamposReporte) {
		this.bseleccionarTodosCamposReporte = bseleccionarTodosCamposReporte;
	}

	public Boolean getColNumeroOcurrencias() {
		return colNumeroOcurrencias;
	}

	public void setColNumeroOcurrencias(Boolean colNumeroOcurrencias) {
		this.colNumeroOcurrencias = colNumeroOcurrencias;
	}

	public HorizontalBarChartModel getModelBarHorizontal() {
		return modelBarHorizontal;
	}

	public void setModelBarHorizontal(HorizontalBarChartModel modelBarHorizontal) {
		this.modelBarHorizontal = modelBarHorizontal;
	}

	public PieChartModel getModelPie2() {
		return modelPie2;
	}

	public void setModelPie2(PieChartModel modelPie2) {
		this.modelPie2 = modelPie2;
	}

	public Integer getNumMax() {
		return numMax;
	}

	public void setNumMax(Integer numMax) {
		this.numMax = numMax;
	}

}

// **+---Codigo para organizar "series" de mayor a menor. ORDENA
// PERO AL
// GRAFICAR, SE PIERDE EL ORDEN.
// BarChartSeries aux = new BarChartSeries();
// int tamaño = 0;
// List<String> keysMaximos = new ArrayList<String>();
// String keyMax = "";
// tamaño = series.getData().size();
// for (int i = 0; i < tamaño; i++) {
// keyMax = escogerMaximo(series);
// aux.set(keyMax, series.getData().get(keyMax));
// series.getData().remove(keyMax);
// }
// Iterator it2 = aux.getData().entrySet().iterator();
// while (it2.hasNext()) {
// Map.Entry pair = (Map.Entry) it2.next();
// System.out.println(">>>" + pair.getKey() + ": "
// + pair.getValue());
// }
// **-----------------------------------------------------------**//