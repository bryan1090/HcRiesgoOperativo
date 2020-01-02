package com.hc.ro.vista.admin.bean;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.metamodel.MapAttribute;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.util.Constants;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoAriesgo;
import com.hc.ro.modelo.RoCalifRiesgo;
import com.hc.ro.modelo.RoConsecuenciaImpacto;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoDeveFaro;
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoFactorRiesgo;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoProbabilidadEvento;
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
import com.hc.ro.utils.CruceDetalleEvento;
import com.hc.ro.utils.CruceMapaDeRiesgo;
import com.hc.ro.utils.Matriz;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

@ManagedBean
@ViewScoped
public class ControladorMatrizDeRiesgo {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/MatrizDeRiesgo.jsf";
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

	private int tipoFiltro;

	private List<String> factorRiesgosFiltro;
	private List<RoFactorRiesgo> factorRiesgosTodos;
	private List<String> tipoPerdidasFiltro;
	private List<RoTipoPerdida> tipoPerdidasTodos;
	private List<ParamProbabilidadRiesgo> paramProbabilidadRiesgoTodos;
	private List<ParamConsecuenciaImpacto> paramConsecuenciaImpactoTodos;
	private List<CruceMapaDeRiesgo> cruceMapaTodos;
	private List<RoDetalleEvento> roDetalleEventosTodos;
	private List<RoDetalleEvento> roDetalleEventosQuitar;
	private List<RoDetalleEvento> roDetalleEventosAuxiliar;
	private List<RoDetalleEvento> detallesEventoSeleccionados;

	private List<RoAriesgo> ariesgoAuxiliares;
	private List<RoAriesgo> ariesgoTodos;
	private List<RoCalifRiesgo> calificacionesTodos;

	private CruceMapaDeRiesgo cruceMapaVista;
	private CruceMapaDeRiesgo cruceMapaAux;
	private RoEvento roEventoControlador;
	private RoEvento roEventoVista;
	private RoEvento eventoAux;

	private int codigoEventoMaxTotalOcurrencias;
	private int codigoEventoMaxTotalSeveridad;
	private List<CruceMapaDeRiesgo> listaCrucesMaxPorEvento;
	private List<CruceMapaDeRiesgo> listaCrucesMaxPorEventoPorMes;

	private RoDetalleEvento roDetalleEventoVista;

	private int columnas;

	private Date fechaInicio;
	private Date fechaFin;

	private String tipoMapa;
	private String periodicidad;

	private boolean btodosUsuarios;
	private boolean btodosFactorRiesgo;
	private boolean btodosTipoPerdida;
	private boolean mostrarMatrices;

	private String nuloPerdida;
	private String nuloFactor;
	private String nuloAgencia;
	private String nuloEvento;
	private String nuloNegocio;
	private String nuloProceso;
	private String nuloUsuario;
	private String nuloDepartamento;

	private String nivelEventoFiltro;
	private String nivelNegocioFiltro;

	private String color;
	private String tipoValorPerdida;
	private int valorMasAltoOcurrencia;
	private int valorMasAltoSeveridad;

	private Collection<String> nivelNegocioTodos;
	private Collection<String> nivelEventoTodos;

	private int totalDetalleEventos;
	private BigDecimal totalSeveridadEventos;

	private List<Matriz> listaCrucesTodos;

	private List<RoEvento> eventosMapa;
	private boolean siEsHijo;

	private List<String> eventosConDatos;

	// private LineChartModel lineModel1;

	private List<LineChartModel> lineModelTodos;

	public ControladorMatrizDeRiesgo() {
		super();
		System.out.println("*Constructor*");
		listaCrucesTodos = new ArrayList<Matriz>();
		periodicidad = new String("mensual");
		tipoMapa = new String();
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

		eventosFinales = new ArrayList<RoEvento>();
		negociosFinales = new ArrayList<RoNegocio>();

		factorRiesgosFiltro = new ArrayList<String>();
		factorRiesgosTodos = new ArrayList<RoFactorRiesgo>();
		tipoPerdidasFiltro = new ArrayList<String>();
		tipoPerdidasTodos = new ArrayList<RoTipoPerdida>();
		paramProbabilidadRiesgoTodos = new ArrayList<ParamProbabilidadRiesgo>();
		paramConsecuenciaImpactoTodos = new ArrayList<ParamConsecuenciaImpacto>();
		cruceMapaTodos = new ArrayList<CruceMapaDeRiesgo>();
		ariesgoAuxiliares = new ArrayList<RoAriesgo>();
		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();
		roDetalleEventosQuitar = new ArrayList<RoDetalleEvento>();
		roDetalleEventosAuxiliar = new ArrayList<RoDetalleEvento>();
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		ariesgoTodos = new ArrayList<RoAriesgo>();

		cruceMapaVista = new CruceMapaDeRiesgo();
		cruceMapaAux = new CruceMapaDeRiesgo();
		roEventoControlador = new RoEvento();
		roEventoVista = new RoEvento();
		eventoAux = new RoEvento();
		fechaInicio = new Date();
		fechaFin = new Date();

		nivelNegocioTodos = new HashSet<String>();
		nivelEventoTodos = new HashSet<String>();

		roEventosFitro = new ArrayList<RoEvento>();
		roNegociosFiltro = new ArrayList<RoNegocio>();
		tipoFiltro = 1;
		tipoValorPerdida = "";

		// lineModel1 = new LineChartModel();

		lineModelTodos = new ArrayList<LineChartModel>();
		eventosMapa = new ArrayList<RoEvento>();
		eventosConDatos = new ArrayList<String>();

	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("*Post Constructor*");
		mostrarMatrices = false;
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
		factorRiesgosTodos = servicioRoFactorRiesgo.buscarTodos();
		tipoPerdidasTodos = servicioRoTipoPerdida.buscarTodos();
		llenarArbolAgencia();
		llenarArbolDepartamento();
		llenarArbolEvento();
		llenarArbolNegocio();
		llenarArbolProceso();
		columnas = 0;
		List<RoEvento> eventosAux = new ArrayList<RoEvento>();
		eventosAux = servicioRoEvento.buscarEventoPorPadre("Eventos");
		buscarNivelEvento(eventosAux, 0);
		List<RoNegocio> negociosAux = new ArrayList<RoNegocio>();
		negociosAux = servicioRoNegocio.buscarNegocioPorPadre("Negocios");
		buscarNivelNegocio(negociosAux, 0);
		//
		color = "";
		valorMasAltoOcurrencia = 0;
		codigoEventoMaxTotalOcurrencias = 0;
		codigoEventoMaxTotalSeveridad = 0;
		listaCrucesMaxPorEvento = new ArrayList<CruceMapaDeRiesgo>();
		listaCrucesMaxPorEventoPorMes = new ArrayList<CruceMapaDeRiesgo>();

		// lineModel1 = createLineModels2(lineModel1);

		// lineModelTodos.add(lineModel1);

		fechaInicio.setMonth(10);
		fechaInicio.setYear(117);
		fechaFin.setMonth(11);
		fechaFin.setYear(117);

		ariesgoAuxiliares = servicioRoAriesgo
				.buscarTodos_paramConsecuenciaImpacto_paramProbabilidadRiesgo_roCalifRiesgo__nombreClrs_roCalifRiesgo__colorClrs();
		paramConsecuenciaImpactoTodos = servicioParamConsecuenciaImpacto
				.buscarTodos();
		paramProbabilidadRiesgoTodos = servicioParamProbabilidadRiesgo
				.buscarTodosDesc();

	}

	public LineChartModel createLineModels2(LineChartModel lineModel) {
		System.out.println("Creando gráficos ejemplo...");
		lineModel = DefaultInitCategoryModel2();
		lineModel.setTitle("Category Chart2");
		lineModel.setLegendPosition("e");
		lineModel.setShowPointLabels(true);
		lineModel.getAxes().put(AxisType.X, new CategoryAxis("Mes"));
		lineModel.getAxis(AxisType.X).setTickAngle(-30);
		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Valor/Porcentaje");
		yAxis.setMin(0);

		BigDecimal yMax = new BigDecimal(0);
		for (ChartSeries serie : lineModel.getSeries()) {
			Iterator it = serie.getData().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				// System.out.println("- " + pair.getValue().toString());
				if (yMax.compareTo(new BigDecimal(pair.getValue().toString())) == -1) {
					yMax = new BigDecimal((pair.getValue().toString()));

				}
			}
		}

		System.out.println("ymax:" + yMax);
		// yMax=yMax.setScale(2, BigDecimal.ROUND_HALF_UP);
		double techo = 0;
		double piso = 0;
		techo = Math.ceil(yMax.doubleValue());
		piso = Math.floor(yMax.doubleValue());
		if (yMax.doubleValue() < techo - 0.55) {
			yMax = new BigDecimal(techo - 0.55).setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			yMax = new BigDecimal(techo + 0.05).setScale(2,
					BigDecimal.ROUND_HALF_UP);
		}

		System.out.println("ymax" + yMax);
		yAxis.setMax(yMax);

		yAxis.setTickFormat("%.2f");

		// 0.07
		// double var= Math.ceil(0.02);
		// yMax=new BigDecimal();
		// System.out.println("yMax"+yMax);
		// yMax=yMax.setScale(2,BigDecimal.ROUND_HALF_UP);
		// System.out.println("yMax"+yMax);
		// yAxis.setMax(7.00+0.55);
		//

		return lineModel;
	}

	private LineChartModel DefaultInitCategoryModel2() {
		LineChartModel model = new LineChartModel();

		ChartSeries prueba = new ChartSeries();

		prueba.setLabel("Indicador");

		prueba.set("Enero",
				new BigDecimal(0.001).setScale(2, BigDecimal.ROUND_HALF_UP));
		prueba.set("Febrero",
				new BigDecimal(0.002).setScale(2, BigDecimal.ROUND_HALF_UP));
		prueba.set("Marzo",
				new BigDecimal(0.003).setScale(2, BigDecimal.ROUND_HALF_UP));
		prueba.set("Abril",
				new BigDecimal(0.002).setScale(2, BigDecimal.ROUND_HALF_UP));
		BigDecimal asd = new BigDecimal(0.00000209638 * 100).setScale(2,
				BigDecimal.ROUND_HALF_UP);

		System.out.println("" + asd);
		prueba.set("Mayo", asd);
		prueba.set("Junio", 0);
		prueba.set("Julio", 0);
		prueba.set("Agosto", 0);
		prueba.set("Septiembre", 0);
		prueba.set("Octubre", 0);
		prueba.set("Noviembre", 0);
		prueba.set("Diciembre", 0);

		model.addSeries(prueba);
		// model.setExtender("extLegend2");
		return model;
	}

	public void buscarNivelEvento(List<RoEvento> listaEventos, int nivel) {
		if (listaEventos.size() > 0) {
			for (RoEvento roEvento : listaEventos) {
				roEvento.setNivel(nivel + 1);
				eventosTodos.add(roEvento);
				nivelEventoTodos.add(Integer.toString(nivel + 1));
				List<RoEvento> eventosAux2 = new ArrayList<RoEvento>();
				eventosAux2 = servicioRoEvento.buscarEventoPorPadre(roEvento
						.getNombreEven());
				buscarNivelEvento(eventosAux2, nivel + 1);
			}
		}
	}

	public void buscarNivelNegocio(List<RoNegocio> listaNegocios, int nivel) {
		if (listaNegocios.size() > 0) {
			for (RoNegocio roNegocio : listaNegocios) {
				roNegocio.setNivel(nivel + 1);
				negociosTodos.add(roNegocio);
				nivelNegocioTodos.add(Integer.toString(nivel + 1));
				List<RoNegocio> eventosAux2 = new ArrayList<RoNegocio>();
				eventosAux2 = servicioRoNegocio.buscarNegocioPorPadre(roNegocio
						.getNombreNego());
				buscarNivelNegocio(eventosAux2, nivel + 1);
			}
		}
	}

	// primefaces response
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

	// private Document mapaDeRiesgo;
	// private DocumentException documentException;
	// private PdfWriter escritorPDF;

	@SuppressWarnings("deprecation")
	public void crearReporteMapa(ActionEvent actionEvent) {
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
			Paragraph parrafoTitulo = new Paragraph("Matriz de Riesgo",
					FontFactory.getFont("arial", 20, Font.BOLD));

			// insetamos la primera celda el titulo del reporte
			// tabla.addCell(parrafoTitulo); // color
			// seteamos la ubicacion de la imagen que queremos en el pdf para la
			// cabecera
			String logoURL = servletContext.getRealPath("") + File.separator
					+ "utils" + File.separator + "images" + File.separator
					+ "logoCliente.png";
			Image logo = Image.getInstance(logoURL);
			logo.scaleToFit(100, 24);
			logo.setAlignment(Chunk.ALIGN_RIGHT);

			// insertamos el logo a la celda
			// tabla.addCell(logo);
			parrafoTitulo.add(logo);
			// insertamos la tabla al documento
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

			parrafo = new Paragraph();
			frase = new Phrase("Tipo de Mapa: ", FontFactory.getFont("arial",
					8, Font.BOLD));
			parrafo.add(frase);

			frase = new Phrase(tipoMapa + ", ", FontFactory.getFont("arial", 8));
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
			/**
			 * MAPA
			 */
			parrafo = new Paragraph("\n\n");
			// creamos una tabla para el mapa
			PdfPTable tablaMapa = new PdfPTable(2);
			float[] medidaCeldas = { 0.55f, 2.25f };
			tablaMapa.setWidths(medidaCeldas);
			PdfPTable tablaProba = new PdfPTable(1);
			// quitamos el borde de la tabla mapa
			tablaMapa.getDefaultCell().setBorder(0);

			// probabilidades
			for (ParamProbabilidadRiesgo proba : paramProbabilidadRiesgoTodos) {
				tablaProba.addCell(proba.getLetraPprr() + " - "
						+ proba.getNombrePprr());
			}
			tablaMapa.addCell(tablaProba);

			// celdas del mapa
			tablaProba = new PdfPTable(columnas);
			for (CruceMapaDeRiesgo cruce : cruceMapaTodos) {
				Color myColor = WebColors.getRGBColor("#"
						+ cruce.getRoAriesgo().getRoCalifRiesgo()
								.getColorClrs());
				PdfPCell cell = new PdfPCell(new Phrase(Integer.toString(cruce
						.getRoEventos().size())));
				cell.setBackgroundColor(myColor);
				tablaProba.addCell(cell);
			}
			tablaMapa.addCell(tablaProba);
			// consecuencias
			tablaMapa.addCell("");
			tablaProba = new PdfPTable(columnas);
			for (ParamConsecuenciaImpacto consec : paramConsecuenciaImpactoTodos) {

				tablaProba.addCell(consec.getNumeroPconi() + " - "
						+ consec.getNombrePconi());
			}
			tablaMapa.addCell(tablaProba);

			parrafo.add(tablaMapa);

			// añadimos el parrafo al documento
			document.add(parrafo);

			/**
			 * hoja de detalles del mapa de riesgo
			 */

			document.close();

			writePDFToResponse(externalContext, baos,
					"ReporteDetalladoDinámico");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void exportarPDF(ActionEvent actionEvent) throws JRException,
			IOException {
		Map<String, Object> parametros = new HashMap<String, Object>();

		File jasper = new File(FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/reportes/report1.jasper"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), parametros, new JRBeanCollectionDataSource(
						cruceMapaTodos));

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
				.getExternalContext().getRealPath("/reportes/report1.jasper"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), parametros, new JRBeanCollectionDataSource(
						cruceMapaTodos));

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
				.getExternalContext().getRealPath("/reportes/report1.jasper"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), parametros, new JRBeanCollectionDataSource(
						cruceMapaTodos));

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
//PARAMETROS:LISTA DE HIJOS TIPO ROEVENTO, NODO PADRE
	public void recursivaArbolEvento(List<RoEvento> Eventos, TreeNode padre) {
		//SI TIENE HIJOS PARA ESE PADRE("Eventos" EN LA PRIMERA CORRIDA)
		if (!Eventos.isEmpty()) {
			RoEvento Evento = new RoEvento();
			for (int i = 0; i < Eventos.size(); i++) {
				
				Evento = Eventos.get(i);
				nodosTodosEventos.add(new DefaultTreeNode(Evento
						.getNombreEven(), padre));
				nodosTodosEventos.get(nodosTodosEventos.size() - 1)
						.setExpanded(true);
				//OBTENGO LOS EVENTOS HIJOS POR CADA EVENTO HIJO DE LA LISTAHIJOS(PARAMETRO) DEL PADRE(PARAMETRO)
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
//OBTENGO EL ELEMENTO ANTERIOR EN LA LISTA
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

	//SIRVE PARA INICIALIZAR EL ARBOL Y LLAMAR A LA FUNCION RECURSIVA QUE VA A LLENARLO
	public void llenarArbolEvento() {
		eventosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Eventos", eventosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosEventos = new ArrayList<TreeNode>();
		//SE ENVIA LA LISTA DE HIJOS Y EL PADRE RESPECTIVO
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

	private List<RoNegocio> roNegociosFiltro;
	private List<RoEvento> roEventosFitro;

	private List<RoEvento> eventosFinales;

	private List<RoNegocio> negociosFinales;

	public void pasarListaNegocios() {
		roNegociosFiltro = new ArrayList<RoNegocio>();
		negociosFiltro = new ArrayList<String>();
		for (TreeNode nodo : negociosSeleccionadas) {
			for (RoNegocio nego : negociosTodos) {
				if (nego.getNombreNego().equals(nodo.getData().toString())
						&& nego.getNivel() >= Integer
								.parseInt(nivelNegocioFiltro)) {
					negociosFiltro.add(nodo.getData().toString());
					roNegociosFiltro.add(nego);
				}
			}
		}
		try {
			negociosFiltro.remove("Negocios");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void pasarListaEventos() {
		roEventosFitro = new ArrayList<RoEvento>();
		eventosFiltro = new ArrayList<String>();
		for (TreeNode nodo : eventosSeleccionadas) {
			for (RoEvento even : eventosTodos) {
				if (even.getNombreEven().equals(nodo.getData().toString())
						&& even.getNivel() >= Integer
								.parseInt(nivelEventoFiltro)) {
					eventosFiltro.add(nodo.getData().toString());
					roEventosFitro.add(even);
				}
			}
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

	// Verifica si "evento" es hijo de "padre", subiendo por niveles de evento,
	// comparando con el ancestro
	public boolean esHijoEvento(RoEvento evento, String padre) {
		if (!evento.getAncestroEven().equals("Eventos")) {
			if (evento.getAncestroEven().equals(padre)) {
				return true;

			} else {
				return esHijoEvento(
						servicioRoEvento.buscarEventoPorNombre(evento
								.getAncestroEven()), padre);
			}
		} else {
			return false;
		}
	}

	public boolean esHijoNegocio(RoNegocio negocio, String padre) {
		if (!negocio.getAncestroNego().equals("Negocios")) {
			if (negocio.getAncestroNego().equals(padre)) {
				return true;

			} else {
				return esHijoNegocio(
						servicioRoNegocio.buscarNegocioPorNombre(negocio
								.getAncestroNego()), padre);
			}
		} else {
			return false;
		}
	}

	public void calcularEventosFiltro() {

	}

	public void generarMapa() {
		listaCrucesTodos = new ArrayList<Matriz>();
		totalDetalleEventos = 0;
		int anioInicio = fechaInicio.getYear();
		int anioFin = fechaFin.getYear();

		totalSeveridadEventos = new BigDecimal("0");
		tipoMapa = "Cualitativo Inherente";
		cruceMapaVista = new CruceMapaDeRiesgo();

		lineModelTodos = new ArrayList<LineChartModel>();
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		// eventosConDatos = new ArrayList<String>();

		pasarListaAgencias();
		pasarListaDepartamentos();
		pasarListaEventos();
		pasarListaNegocios();
		pasarListaProcesos();
		valorMasAltoOcurrencia = 0;
		valorMasAltoSeveridad = 0;

		// ariesgoAuxiliares = servicioRoAriesgo.buscarTodos();
		ariesgoAuxiliares = servicioRoAriesgo
				.buscarTodos_paramConsecuenciaImpacto_paramProbabilidadRiesgo_roCalifRiesgo__nombreClrs_roCalifRiesgo__colorClrs();
		cruceMapaTodos = new ArrayList<CruceMapaDeRiesgo>();
		cruceMapaAux = new CruceMapaDeRiesgo();
		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();
		roDetalleEventosAuxiliar = servicioRoDetalleEvento
				.buscarEventosTodosFiltro(fechaInicio, fechaFin, tipoFiltro);
		List<Integer> roDeveFarosAux = new ArrayList<Integer>();
		System.out.println("Empiezo a filtrar detalles...");

		// *SE APLICA LOS FILTROS SELECCIONADOS EN LOS DETALLES EVENTO
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

																											roDetalleEventosTodos
																													.add(deve);

																											totalDetalleEventos += deve
																													.getNumOcur();
																											// System.out
																											// .println("TIPOVALORPERDIDA: "+tipoValorPerdida);
																											// *SE
																											// ESCOGE
																											// EL
																											// TIPO
																											// DE
																											// PERDIDA
																											switch (Integer
																													.parseInt(tipoValorPerdida)) {
																											case 1:

																												totalSeveridadEventos = totalSeveridadEventos
																														.add(new BigDecimal(
																																deve.getValorDeve()))
																														.setScale(
																																2,
																																BigDecimal.ROUND_HALF_UP);
																												break;
																											case 2:
																												totalSeveridadEventos = totalSeveridadEventos
																														.add(new BigDecimal(
																																deve.getPerdidaResidualDeve()))
																														.setScale(
																																2,
																																BigDecimal.ROUND_HALF_UP);
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
		System.out.println("Termino de  filtrar detalles");

		// totalDetalleEventos = roDetalleEventosTodos.size();

		eventosFinales = new ArrayList<RoEvento>();
		negociosFinales = new ArrayList<RoNegocio>();
		System.out.println("Lleno eventos finales...");

		// >>>> codigo original (muestra eventos con valores 0)

		// for (RoEvento evento : roEventosFitro) {
		// if (evento.getNivel() == Integer.parseInt(nivelEventoFiltro)) {
		// eventosFinales.add(evento);
		// evento.setTotalOcurrencias(0);
		// evento.setTotalSeveridad(new BigDecimal("0"));
		// evento.setPorcentajeOcurrencias(new BigDecimal("0"));
		// evento.setPorcentajeSeveridad(new BigDecimal("0"));
		// }
		// }

		// <<<<<

		// // >>>> codigo nuevo
		//
		// *LLENO LISTA "eventosFinales"
//		System.out.println("test: "+Integer.parseInt(nivelEventoFiltro));

		for (RoEvento evento : roEventosFitro) {
			if (evento.getNivel() == Integer.parseInt(nivelEventoFiltro)) {
				
				boolean detener = false;
				for (RoDetalleEvento detalle : roDetalleEventosTodos) {
					if (detalle.getRoEvento().getCodigoEven() == evento
							.getCodigoEven()
							|| esHijoEvento(detalle.getRoEvento(),
									evento.getNombreEven())) {
						if (!eventosFinales.contains(evento)) {
							evento.setTotalOcurrencias(0);
							evento.setTotalSeveridad(new BigDecimal("0"));
							evento.setPorcentajeOcurrencias(new BigDecimal("0"));
							evento.setPorcentajeSeveridad(new BigDecimal("0"));
							eventosFinales.add(evento);
							detener = true;
						}
					}
					if (detener) {
						break;
					}
				}
			}
		}

		// // <<<<<

		//

		System.out.println("eventos finales size " + eventosFinales.size());

		columnas = 0;
		System.out.println("Termino de llenar eventos finales");

		// *LLENO LA LISTA "negociosFinales"
		for (RoNegocio negocio : roNegociosFiltro) {
			if (negocio.getNivel() == Integer.parseInt(nivelNegocioFiltro)) {
				columnas++;
				negociosFinales.add(negocio);
				negocio.setTotalOcurrencias(0);
				negocio.setTotalSeveridad(new BigDecimal("0"));
				negocio.setPorcentajeOcurrencias(new BigDecimal("0"));
				negocio.setPorcentajeSeveridad(new BigDecimal("0"));
			}
		}

		System.out.println("Haciendo cruces...");

		for (RoEvento evento : eventosFinales) {
//			System.out.println(" -1");
			// System.out.println(" Evento: " + evento.getNombreEven());
			for (RoNegocio negocio : negociosFinales) {
//				System.out.println(" -2");
				// System.out.println(" Negocio: " + negocio.getNombreNego());
				// System.out.println("Inicializando cruce...");
				cruceMapaAux = new CruceMapaDeRiesgo();
				cruceMapaAux
						.setDetalleEventos(new ArrayList<RoDetalleEvento>());
				cruceMapaAux.setRoEvento(evento);
				cruceMapaAux.setRoNegocio(negocio);
				cruceMapaAux.setPorcentajeCruce(new BigDecimal("0"));
				cruceMapaAux.setPorcentajeSeveridadCruce(new BigDecimal("0"));
				cruceMapaAux.setSeveridadCruce(new BigDecimal("0"));

				cruceMapaAux
						.setParamProbabilidadRiesgo(new ParamProbabilidadRiesgo());
				cruceMapaAux
						.setParamConsecuenciaImpacto(new ParamConsecuenciaImpacto());
				cruceMapaAux.setRoAriesgo(new RoAriesgo());

				// *CALCULO EL NUM DE OCURRENCIAS Y VALOR DE PERDIDA
				// *(ACUMULATIVOS) DEL CRUCE
				for (RoDetalleEvento detalle : roDetalleEventosTodos) {
					// BUSCO DETALLES QUE COINCIDAN CON EL EVENTO Y EL PROCESO
					if (detalle.getRoEvento().getCodigoEven() == evento
							.getCodigoEven()
							&& detalle.getRoNegocio1().getCodigoNego() == negocio
									.getCodigoNego()
							|| esHijoEvento(detalle.getRoEvento(),
									evento.getNombreEven())
							&& esHijoNegocio(detalle.getRoNegocio1(),
									negocio.getNombreNego())
							|| detalle.getRoEvento().getCodigoEven() == evento
									.getCodigoEven()
							&& esHijoNegocio(detalle.getRoNegocio1(),
									negocio.getNombreNego())
							|| esHijoEvento(detalle.getRoEvento(),
									evento.getNombreEven())
							&& detalle.getRoNegocio1().getCodigoNego() == negocio
									.getCodigoNego()) {
//						System.out.println(" -3");

						cruceMapaAux.getDetalleEventos().add(detalle);
						cruceMapaAux.setNumeroOcurrencias(cruceMapaAux
								.getNumeroOcurrencias() + detalle.getNumOcur());
						// System.out.println(" num ocur: "
						// + cruceMapaAux.getNumeroOcurrencias());
						switch (Integer.parseInt(tipoValorPerdida)) {

						case 1:
							cruceMapaAux
									.setSeveridadCruce(cruceMapaAux
											.getSeveridadCruce()
											.add(new BigDecimal(detalle
													.getValorDeve()))
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));

							negocio.setTotalSeveridad(negocio
									.getTotalSeveridad()
									.add(new BigDecimal(detalle.getValorDeve()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							evento.setTotalSeveridad(evento
									.getTotalSeveridad()
									.add(new BigDecimal(detalle.getValorDeve()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							break;
						case 2:
							cruceMapaAux.setSeveridadCruce(cruceMapaAux
									.getSeveridadCruce()
									.add(new BigDecimal(detalle
											.getPerdidaResidualDeve()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));

							negocio.setTotalSeveridad(negocio
									.getTotalSeveridad()
									.add(new BigDecimal(detalle
											.getPerdidaResidualDeve()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							evento.setTotalSeveridad(evento
									.getTotalSeveridad()
									.add(new BigDecimal(detalle
											.getPerdidaResidualDeve()))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							break;
						}

					}

				}

				// cruceMapaAux.setNumeroOcurrencias(numOcurPorDetalleAcum);
				if (cruceMapaAux.getNumeroOcurrencias() != 0) {
//					System.out.println(" -4");

					// *PONGO TOTAL OCURRENCIAS PARA EL EVENTO Y EL NEGOCIO
					try {

						negocio.setTotalOcurrencias(negocio
								.getTotalOcurrencias()
								+ cruceMapaAux.getNumeroOcurrencias());
						evento.setTotalOcurrencias(evento.getTotalOcurrencias()
								+ cruceMapaAux.getNumeroOcurrencias());

						// System.out.println(" nego ocur: "
						// + evento.getTotalOcurrencias());
						// System.out.println(" even ocur: "
						// + evento.getTotalOcurrencias());

					} catch (Exception e) {
						// TODO: handle exception
					}

					// *CALCULO LOS PORCENTAJES DEL CRUCE Y EL NEGOCIO
					try {

						cruceMapaAux.setPorcentajeCruce(new BigDecimal(
								(cruceMapaAux.getNumeroOcurrencias() * 100.00)
										/ totalDetalleEventos).setScale(2,
								BigDecimal.ROUND_HALF_UP));

						evento.setPorcentajeOcurrencias(evento
								.getPorcentajeOcurrencias().add(
										cruceMapaAux.getPorcentajeCruce()));
						negocio.setPorcentajeOcurrencias(negocio
								.getPorcentajeOcurrencias().add(
										cruceMapaAux.getPorcentajeCruce()));
						// System.out.println(" even porcentaje ocur: "
						// + evento.getPorcentajeOcurrencias());
						// System.out.println(" nego porcentaje ocur: "
						// + negocio.getPorcentajeOcurrencias());

					} catch (Exception e) {
						cruceMapaAux.setPorcentajeCruce(new BigDecimal("0"));
					}
					try {
						cruceMapaAux
								.setPorcentajeSeveridadCruce((cruceMapaAux
										.getSeveridadCruce().multiply(
												new BigDecimal("100.00"))
										.divide(totalSeveridadEventos, 2,
												RoundingMode.HALF_UP))
										.setScale(2, BigDecimal.ROUND_HALF_UP));
						evento.setPorcentajeSeveridad((evento
								.getPorcentajeSeveridad().add(cruceMapaAux
								.getPorcentajeSeveridadCruce())));
						negocio.setPorcentajeSeveridad((negocio
								.getPorcentajeSeveridad().add(cruceMapaAux
								.getPorcentajeSeveridadCruce())));

						// System.out.println(" even porcentaje sever: "
						// + evento.getPorcentajeSeveridad());
						// System.out.println(" nego porcentaje sever: "
						// + negocio.getPorcentajeSeveridad());

					} catch (Exception e) {
						cruceMapaAux
								.setPorcentajeSeveridadCruce(new BigDecimal("0"));
					}
					// *AÑADO NUMERO DE PROBABILIDAD, CONSECUENCIA Y VAR ARIESGO
					int numPprob = 0;
					int numPconsec = 0;

					try {

						numPprob = servicioRoProbabilidadEvento
								.buscarProbabilidadEventoPorNegocioFrecuencia(
										cruceMapaAux.getRoNegocio()
												.getNombreNego(),
										cruceMapaAux.getNumeroOcurrencias())
								.getCodigoPprr();
						numPconsec = servicioRoConsecuenciaImpacto
								.buscarConsecuenciaImpactoPorNegocioValor(
										cruceMapaAux.getRoNegocio()
												.getNombreNego(),
										cruceMapaAux.getSeveridadCruce()
												.doubleValue())
								.getCodigoPconi();

						for (RoAriesgo varAriesgo : ariesgoAuxiliares) {
							if (numPconsec == varAriesgo
									.getParamConsecuenciaImpacto()
									.getNumeroPconi()
									&& numPprob == varAriesgo
											.getParamProbabilidadRiesgo()
											.getNumeroPprr()) {

								cruceMapaAux.setRoAriesgo(varAriesgo);
//								System.out.println("cruce numConsec: "+cruceMapaAux.getRoAriesgo().getParamConsecuenciaImpacto().getNumeroPconi());
//								 System.out.println("cruce numProba: "+cruceMapaAux.getRoAriesgo().getParamProbabilidadRiesgo().getNumeroPprr());
							}
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				 System.out.println("cruce evento: "+cruceMapaAux.getRoEvento().getNombreEven());
				 System.out.println("cruce negocio: "+cruceMapaAux.getRoNegocio().getNombreNego());
				 System.out.println("cruce perdida: "+cruceMapaAux.getSeveridadCruce());
				 System.out.println("cruce num ocurr: "+cruceMapaAux.getNumeroOcurrencias());
				 

				// System.out.println("Agregando cruce...");
				cruceMapaTodos.add(cruceMapaAux);
			}
		}

		System.out.println("Termino de hacer cruces...");
		mostrarMatrices = true;
		// identifico el evento con el maximo total de ocurrencias y de
		// severidad
		codigoEventoMaxTotalOcurrencias = maxTotalOcurrencias();
		codigoEventoMaxTotalSeveridad = maxTotalSeveridad();

	}

	public void esHijo(RoEvento evento, String padre) {
		if (!evento.getAncestroEven().equals("Eventos")) {
			// SI SU PADRE ES EL ANCESTRO
			if (evento.getAncestroEven().equals(padre)) {
				siEsHijo = true;

			} else {
				// * BUSCA SI EL ANCESTRO ES HIJO DE "PADRE", RECURSIVAMENTE.
				esHijo(servicioRoEvento.buscarEventoPorNombre_codigoEven_nombreEven_ancestroEven(evento
						.getAncestroEven()), padre);
			}
		} else {
			siEsHijo = false;
		}
	}

	public void obtenerPadre(RoEvento evento) {
		if (evento.getAncestroEven().equals("Eventos")) {
			eventoAux = evento;
		} else {
			obtenerPadre(servicioRoEvento.buscarEventoPorNombre(evento
					.getAncestroEven()));
		}
	}

	public void seleccionarCruce() {
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		detallesEventoSeleccionados = cruceMapaVista.getDetalleEventos();
	}

	public void seleccionarEvento() {
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		roEventoVista = roEventoControlador;
		for (RoDetalleEvento detalleEvento : cruceMapaVista.getDetalleEventos()) {
			eventoAux = new RoEvento();
			obtenerPadre(detalleEvento.getRoEvento());
			if (eventoAux.getCodigoEven() == roEventoVista.getCodigoEven()) {
				detallesEventoSeleccionados.add(detalleEvento);
			}

		}
		roEventoVista.getNumeroOcurrencias();
		dataManagerSesion.setNumeroDeOcurrencias(roEventoVista
				.getNumeroOcurrencias());
	}

	public void planDeAccion() {

		dataManagerSesion.setNumeroDeOcurrencias(0);

		// System.out.println("codigodeve"+roDetalleEventoVista.getCodigoDeve());
		// System.out.println("codigo"+roDetalleEventoVista
		// .getParamConsecuenciaImpactoAntes().getCodigoPconi());
		// System.out.println("nombre"+roDetalleEventoVista
		// .getParamConsecuenciaImpactoAntes().getNombrePconi());
		// System.out.println("numero"+roDetalleEventoVista
		// .getParamConsecuenciaImpactoAntes().getNumeroPconi());
		// System.out.println("roarriesgo tamaño"+roDetalleEventoVista
		// .getParamConsecuenciaImpactoAntes().getRoAriesgos().size());
		//
		//

		// AQUI EMPIEZA EL ERROR

		RoConsecuenciaImpacto roConsImp = new RoConsecuenciaImpacto();

		ParamConsecuenciaImpacto paramConsecImpacAntes = new ParamConsecuenciaImpacto();
		ParamConsecuenciaImpacto paramConsecImpacDespues = new ParamConsecuenciaImpacto();
		ParamProbabilidadRiesgo paramProbRiesAntes = new ParamProbabilidadRiesgo();
		ParamProbabilidadRiesgo paramProbRiesDespues = new ParamProbabilidadRiesgo();
		roDetalleEventoVista
				.setParamConsecuenciaImpactoAntes(paramConsecImpacAntes);
		roDetalleEventoVista
				.setParamConsecuenciaImpactoDespues(paramConsecImpacDespues);

		roDetalleEventoVista
				.setParamProbabilidadRiesgoAntes(paramProbRiesAntes);
		roDetalleEventoVista
				.setParamProbabilidadRiesgoDespues(paramProbRiesDespues);

		dataManagerSesion.setConsecInherente(roDetalleEventoVista
				.getParamConsecuenciaImpactoAntes().getNombrePconi());
		dataManagerSesion.setConsecResidual(roDetalleEventoVista
				.getParamConsecuenciaImpactoDespues().getNombrePconi());
		dataManagerSesion.setFechaRegistro(roDetalleEventoVista
				.getFechaRegisDeve());
		dataManagerSesion.setIdDetalleSeleccionado(roDetalleEventoVista
				.getCodigoDeve());
		dataManagerSesion.setNumConsecInherente(roDetalleEventoVista
				.getParamConsecuenciaImpactoAntes().getNumeroPconi());
		dataManagerSesion.setNumConsecResidual(roDetalleEventoVista
				.getParamConsecuenciaImpactoDespues().getNumeroPconi());

		dataManagerSesion.setNumProbaInherente(roDetalleEventoVista
				.getParamProbabilidadRiesgoAntes().getLetraPprr());
		dataManagerSesion.setNumProbaResidual(roDetalleEventoVista
				.getParamProbabilidadRiesgoDespues().getLetraPprr());
		dataManagerSesion.setProbaInherente(roDetalleEventoVista
				.getParamProbabilidadRiesgoAntes().getNombrePprr());
		dataManagerSesion.setProbaResidual(roDetalleEventoVista
				.getParamProbabilidadRiesgoDespues().getNombrePprr());
		dataManagerSesion.setRiesgoInherente(roDetalleEventoVista
				.getValorDeve());
		dataManagerSesion.setRiesgoResidual(roDetalleEventoVista
				.getPerdidaResidualDeve());

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

	public void generarHistorial() {
		System.out.println("Inicia Metodo Historial...");

		listaCrucesTodos = new ArrayList<Matriz>();
		lineModelTodos = new ArrayList<LineChartModel>();

		int anioInicio = fechaInicio.getYear();
		// System.out.println("fechaInicio.getYear()" + fechaInicio.getYear());
		// System.out.println("fechaFin.getYear()" + fechaFin.getYear());

		int anioFin = fechaFin.getYear();
		int mesInicio = fechaInicio.getMonth();
		int mesFin = fechaFin.getMonth();
		Matriz matriz;
		if (periodicidad.equals("anual")) {
			for (int i = anioInicio; i < anioFin + 1; i++) {
				matriz = new Matriz();
				matriz.setPeriodo("Año: " + Integer.toString(i + 1900));
				List<RoDetalleEvento> detalleEventosAuxHist = new ArrayList<RoDetalleEvento>();

				for (RoDetalleEvento detalle : roDetalleEventosTodos) {

					switch (tipoFiltro) {
					case 1:
						if (detalle.getFechaRegisDeve().getYear() == i) {
							detalleEventosAuxHist.add(detalle);
						}
						break;
					case 2:
						if (detalle.getFechaOcurDeve().getYear() == i) {
							detalleEventosAuxHist.add(detalle);
						}
						break;
					case 3:
						if (detalle.getFechaDescDeve().getYear() == i) {
							detalleEventosAuxHist.add(detalle);
						}
						break;

					default:
						break;
					}
				}

				for (RoEvento evento : eventosFinales) {

					for (RoNegocio negocio : negociosFinales) {
						cruceMapaAux = new CruceMapaDeRiesgo();
						cruceMapaAux
								.setDetalleEventos(new ArrayList<RoDetalleEvento>());
						cruceMapaAux.setRoEvento(evento);
						cruceMapaAux.setRoNegocio(negocio);

						cruceMapaAux.setPorcentajeCruce(new BigDecimal("0"));
						cruceMapaAux
								.setPorcentajeSeveridadCruce(new BigDecimal("0"));
						cruceMapaAux.setSeveridadCruce(new BigDecimal("0"));
						int j = 0;
						int numOcurPorDetalle = 0;
						for (RoDetalleEvento detalle : detalleEventosAuxHist) {
							if (detalle.getRoEvento().getCodigoEven() == evento
									.getCodigoEven()
									&& detalle.getRoNegocio1().getCodigoNego() == negocio
											.getCodigoNego()
									|| esHijoEvento(detalle.getRoEvento(),
											evento.getNombreEven())
									&& esHijoNegocio(detalle.getRoNegocio1(),
											negocio.getNombreNego())
									|| detalle.getRoEvento().getCodigoEven() == evento
											.getCodigoEven()
									&& esHijoNegocio(detalle.getRoNegocio1(),
											negocio.getNombreNego())
									|| esHijoEvento(detalle.getRoEvento(),
											evento.getNombreEven())
									&& detalle.getRoNegocio1().getCodigoNego() == negocio
											.getCodigoNego()) {
								//
								// System.out.println(""+i);
								// System.out.println("nombre"+cruceMapaAux.getDetalleEventos().get(i).getRoEvento().getNombreEven());
								// System.out.println("nombre"+cruceMapaAux.getDetalleEventos().get(i).getRoNegocio1().getNombreNego());
								// //System.out.println("nombre"+cruceMapaAux.getDetalleEventos().get(i).getRoNegocio2().getNombreNego());
								// System.out.println("numocur"+cruceMapaAux.getDetalleEventos().get(i).getNumOcur());
								// System.out.println("numocur por detalle "+i+" "+cruceMapaAux.getDetalleEventos().get(i).getNumOcur());
								// System.out.println("numocur++"+numOcurPorDetalle);
								// cruceMapaAux.setNumeroOcurrencias(detalle.getNumOcur());

								cruceMapaAux.getDetalleEventos().add(detalle);

								numOcurPorDetalle += cruceMapaAux
										.getDetalleEventos().get(j)
										.getNumOcur();

								cruceMapaAux
										.setNumeroOcurrencias(numOcurPorDetalle);

								j++;

								switch (Integer.parseInt(tipoValorPerdida)) {

								case 1:
									cruceMapaAux.setSeveridadCruce(cruceMapaAux
											.getSeveridadCruce()
											.add(new BigDecimal(detalle
													.getValorDeve()))
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));
									break;
								case 2:
									cruceMapaAux.setSeveridadCruce(cruceMapaAux
											.getSeveridadCruce()
											.add(new BigDecimal(detalle
													.getPerdidaResidualDeve()))
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));
									break;

								}

								// negocio.setTotalOcurrencias(negocio
								// // .getTotalOcurrencias() + 1);
								// negocio.setTotalSeveridad(negocio.getTotalSeveridad()
								// .add(new
								// BigDecimal(detalle.getValorDeve()*detalle.getNumOcur()))
								// .setScale(2, BigDecimal.ROUND_HALF_UP));
								// evento.setTotalSeveridad(evento.getTotalSeveridad()
								// .add(new
								// BigDecimal(detalle.getValorDeve()*detalle.getNumOcur()))
								// .setScale(2, BigDecimal.ROUND_HALF_UP));
								// System.out.println("evento"+evento.getNombreEven()+"total ocur"+evento.getTotalOcurrencias());
							}
						}

						// negocio.setTotalOcurrencias(negocio
						// .getTotalOcurrencias() + numOcurPorDetalle);
						// evento.setTotalOcurrencias(evento.getTotalOcurrencias()
						// +numOcurPorDetalle );
						try {
							cruceMapaAux
									.setPorcentajeCruce(new BigDecimal(
											(cruceMapaAux
													.getNumeroOcurrencias() * 100.00)
													/ totalDetalleEventos)
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));

							// System.out.println(cruceMapaAux.getNumeroOcurrencias()+"*100/"+totalDetalleEventos+"="+cruceMapaAux.getPorcentajeCruce());
							// evento.setPorcentajeOcurrencias(evento
							// .getPorcentajeOcurrencias().add(
							// cruceMapaAux.getPorcentajeCruce()));
							// negocio.setPorcentajeOcurrencias(negocio
							// .getPorcentajeOcurrencias().add(
							// cruceMapaAux.getPorcentajeCruce()));

						} catch (Exception e) {
							cruceMapaAux
									.setPorcentajeCruce(new BigDecimal("0"));
						}
						try {
							cruceMapaAux
									.setPorcentajeSeveridadCruce((cruceMapaAux
											.getSeveridadCruce().multiply(
													new BigDecimal("100.00"))
											.divide(totalSeveridadEventos, 2,
													RoundingMode.HALF_UP))
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));
							// evento.setPorcentajeSeveridad((evento
							// .getPorcentajeSeveridad().add(cruceMapaAux
							// .getPorcentajeSeveridadCruce())));
							// negocio.setPorcentajeSeveridad((negocio
							// .getPorcentajeSeveridad().add(cruceMapaAux
							// .getPorcentajeSeveridadCruce())));

						} catch (Exception e) {
							cruceMapaAux
									.setPorcentajeSeveridadCruce(new BigDecimal(
											"0"));
						}

						matriz.getCruces().add(cruceMapaAux);
					}
				}
				listaCrucesTodos.add(matriz);
			}
		} else {
			String[] mesesString = { "Enero", "Febrero", "Marzo", "Abril",
					"Mayo", "Junio", "Julio", "Agosto", "Septiembre",
					"Octubre", "Noviembre", "Diciembre" };

			for (int i = anioInicio; i < anioFin + 1; i++) {
				int k = (i == anioFin) ? mesFin : 11;
				for (int j = (i == anioInicio) ? mesInicio : 0; j < (k + 1); j++) {

					matriz = new Matriz();
					matriz.setPeriodo("Año: " + Integer.toString(i + 1900)
							+ "Mes:" + mesesString[j]);
					List<RoDetalleEvento> detalleEventosAuxHist = new ArrayList<RoDetalleEvento>();
					for (RoDetalleEvento detalle : roDetalleEventosTodos) {

						switch (tipoFiltro) {
						case 1:
							if (detalle.getFechaRegisDeve().getYear() == i
									&& detalle.getFechaRegisDeve().getMonth() == j) {
								detalleEventosAuxHist.add(detalle);
							}
							break;
						case 2:
							if (detalle.getFechaOcurDeve().getYear() == i
									&& detalle.getFechaOcurDeve().getMonth() == j) {
								detalleEventosAuxHist.add(detalle);

							}
							break;
						case 3:
							if (detalle.getFechaDescDeve().getYear() == i
									&& detalle.getFechaDescDeve().getMonth() == j) {
								detalleEventosAuxHist.add(detalle);

							}
							break;

						default:
							break;
						}
					}

					for (RoEvento evento : eventosFinales) {
						for (RoNegocio negocio : negociosFinales) {
							// esta fechaaux se añadira al cruce
							Date fechaaux = new Date();
							fechaaux.setYear(i + 1900);
							fechaaux.setMonth(j);

							// System.out.println("añadiendo año: "
							// + fechaaux.getYear());
							// System.out.println("añadiendo mes: " + j);
							cruceMapaAux = new CruceMapaDeRiesgo();
							cruceMapaAux
									.setDetalleEventos(new ArrayList<RoDetalleEvento>());
							cruceMapaAux.setRoEvento(evento);
							cruceMapaAux.setRoNegocio(negocio);
							cruceMapaAux
									.setPorcentajeCruce(new BigDecimal("0"));
							cruceMapaAux
									.setPorcentajeSeveridadCruce(new BigDecimal(
											"0"));
							cruceMapaAux.setSeveridadCruce(new BigDecimal("0"));
							// cruceMapaAux.setFecha(fechaaux);
							int l = 0;
							int numOcurPorDetalle = 0;
							for (RoDetalleEvento detalle : detalleEventosAuxHist) {
								if (detalle.getRoEvento().getCodigoEven() == evento
										.getCodigoEven()
										&& detalle.getRoNegocio1()
												.getCodigoNego() == negocio
												.getCodigoNego()
										|| esHijoEvento(detalle.getRoEvento(),
												evento.getNombreEven())
										&& esHijoNegocio(
												detalle.getRoNegocio1(),
												negocio.getNombreNego())
										|| detalle.getRoEvento()
												.getCodigoEven() == evento
												.getCodigoEven()
										&& esHijoNegocio(
												detalle.getRoNegocio1(),
												negocio.getNombreNego())
										|| esHijoEvento(detalle.getRoEvento(),
												evento.getNombreEven())
										&& detalle.getRoNegocio1()
												.getCodigoNego() == negocio
												.getCodigoNego()) {
									cruceMapaAux.getDetalleEventos().add(
											detalle);
									numOcurPorDetalle += cruceMapaAux
											.getDetalleEventos().get(l)
											.getNumOcur();
									cruceMapaAux
											.setNumeroOcurrencias(numOcurPorDetalle);
									l++;

									switch (Integer.parseInt(tipoValorPerdida)) {
									case 1:
										cruceMapaAux
												.setSeveridadCruce(cruceMapaAux
														.getSeveridadCruce()
														.add(new BigDecimal(
																detalle.getValorDeve()))
														.setScale(
																2,
																BigDecimal.ROUND_HALF_UP));
										break;
									case 2:
										cruceMapaAux
												.setSeveridadCruce(cruceMapaAux
														.getSeveridadCruce()
														.add(new BigDecimal(
																detalle.getPerdidaResidualDeve()))
														.setScale(
																2,
																BigDecimal.ROUND_HALF_UP));
										break;

									}

									// negocio.setTotalOcurrencias(negocio
									// .getTotalOcurrencias() + 1);
									// negocio.setTotalSeveridad(negocio
									// .getTotalSeveridad()
									// .add(new BigDecimal(detalle
									// .getValorDeve()))
									// .setScale(2,
									// BigDecimal.ROUND_HALF_UP));
									// evento.setTotalSeveridad(evento
									// .getTotalSeveridad()
									// .add(new BigDecimal(detalle
									// .getValorDeve()))
									// .setScale(2,
									// BigDecimal.ROUND_HALF_UP));
									// evento.setTotalOcurrencias(evento
									// .getTotalOcurrencias() + 1);
								}
							}

							// agrego fecha al cruce
							if (cruceMapaAux.getFecha() == null) {
								cruceMapaAux.setFecha(new Date());
								cruceMapaAux.setFecha(fechaaux);
							}

							// para imprimir todos los cruces dentro del
							// historial
							// System.out.println("fechaaux:" + fechaaux);
							// System.out.println("fechaaux año:"
							// + fechaaux.getYear());
							// System.out.println("cruce evento"
							// + cruceMapaAux.getRoEvento()
							// .getNombreEven());
							// System.out.println("cruce negocio"
							// + cruceMapaAux.getRoNegocio()
							// .getNombreNego());
							// System.out.println("cruce numocur"
							// + cruceMapaAux.getNumeroOcurrencias());
							// System.out.println("cruce fecha"
							// + cruceMapaAux.getFecha());

							try {
								cruceMapaAux
										.setPorcentajeCruce(new BigDecimal(
												(cruceMapaAux
														.getNumeroOcurrencias() * 100.00)
														/ totalDetalleEventos)
												.setScale(
														2,
														BigDecimal.ROUND_HALF_UP));
								// evento.setPorcentajeOcurrencias(evento
								// .getPorcentajeOcurrencias().add(
								// cruceMapaAux
								// .getPorcentajeCruce()));
								// negocio.setPorcentajeOcurrencias(negocio
								// .getPorcentajeOcurrencias().add(
								// cruceMapaAux
								// .getPorcentajeCruce()));

							} catch (Exception e) {
								cruceMapaAux.setPorcentajeCruce(new BigDecimal(
										"0"));
							}
							try {
								cruceMapaAux
										.setPorcentajeSeveridadCruce((cruceMapaAux
												.getSeveridadCruce()
												.multiply(
														new BigDecimal("100.00"))
												.divide(totalSeveridadEventos,
														2, RoundingMode.HALF_UP))
												.setScale(
														2,
														BigDecimal.ROUND_HALF_UP));
								// evento.setPorcentajeSeveridad((evento
								// .getPorcentajeSeveridad()
								// .add(cruceMapaAux
								// .getPorcentajeSeveridadCruce())));
								// negocio.setPorcentajeSeveridad((negocio
								// .getPorcentajeSeveridad()
								// .add(cruceMapaAux
								// .getPorcentajeSeveridadCruce())));

							} catch (Exception e) {
								cruceMapaAux
										.setPorcentajeSeveridadCruce(new BigDecimal(
												"0"));
							}

							negocio.getTotalOcurrencias();

							matriz.getCruces().add(cruceMapaAux);

						}
					}
					listaCrucesTodos.add(matriz);
				}
			}
		}

		listaCrucesMaxPorEvento = maxCruceHistorial();

		graficar();

		System.out.println("Termina el metodo historial");
		// imprimirTodosCrucesHistorial();
	}

	public void graficar() {
		lineModelTodos = new ArrayList<LineChartModel>();
		System.out.println(" Inicia método graficar");

		List<String> listaMeses = new ArrayList<String>();
		listaMeses.add("Enero");// 0
		listaMeses.add("Febrero");// 1
		listaMeses.add("Marzo");// 2
		listaMeses.add("Abril");// 3
		listaMeses.add("Mayo");// 4
		listaMeses.add("Junio");// 5
		listaMeses.add("Julio");// 6
		listaMeses.add("Agosto");// 7
		listaMeses.add("Septiembre");// 8
		listaMeses.add("Octubre");// 9
		listaMeses.add("Noviembre");// 10
		listaMeses.add("Diciembre");// 11

		// si periodicidad es anual
		if (periodicidad.equals("anual")) {
			System.out.println(" Gráficos por Año");
			lineModelTodos = new ArrayList<LineChartModel>();

			for (RoEvento even : eventosFinales) {
				// System.out.println("-evento: " + even.getNombreEven());
				LineChartModel lineModel1 = new LineChartModel();
				LineChartModel lineModel2 = new LineChartModel();
				lineModel1 = new LineChartModel();
				lineModel2 = new LineChartModel();

				lineModel1.setTitle(even.getNombreEven() + " [Frecuencia]");
				lineModel1.setLegendPosition("e");
				lineModel1.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
				lineModel1.setShowPointLabels(true);
				lineModel1
						.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
				lineModel1.getAxes().put(AxisType.X, new CategoryAxis(""));
				lineModel1.getAxis(AxisType.X).setTickAngle(-30);
				Axis yAxis = lineModel1.getAxis(AxisType.Y);
				yAxis.setLabel("# de Ocurrencias");
				yAxis.setMin(0);

				lineModel2.setTitle(even.getNombreEven() + " [Severidad]");
				lineModel2.setLegendPosition("e");
				lineModel2.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
				lineModel2.setShowPointLabels(true);
				lineModel2.getAxes().put(AxisType.X, new CategoryAxis(""));
				lineModel2.getAxis(AxisType.X).setTickAngle(-30);
				lineModel2
						.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
				Axis yAxis2 = lineModel2.getAxis(AxisType.Y);
				yAxis2.setLabel("Valor de Pérdida");
				yAxis2.setMin(0);
				// yAxis2.setTickFormat("%.2f");

				for (RoNegocio nego : negociosFinales) {
					LineChartSeries series1 = new LineChartSeries();
					LineChartSeries series2 = new LineChartSeries();
					series1.setLabel(nego.getNombreNego());
					series2.setLabel(nego.getNombreNego());
					// System.out.println("-negocio: " + nego.getNombreNego());
					for (Matriz matriz : listaCrucesTodos) {
						Integer anioMatriz = Integer.parseInt((matriz
								.getPeriodo().substring(5, 9)));// Ej:"2017"
						// System.out.println("---Matriz "
						// + listaCrucesTodos.indexOf(matriz) + " ---");
						// System.out.println("Periodo: " +
						// matriz.getPeriodo());
						// System.out.println("año matriz: " + anioMatriz);
						// System.out.println("mes matriz: "
						// + matriz.getPeriodo().substring(13));
						// System.out.println("año fechainicio: "
						// + (fechaInicio.getYear() + 1900));
						// System.out.println("mes fechainicio: "
						// + listaMeses.get(fechaInicio.getMonth()));
						// System.out.println("año fechafin: "
						// + (fechaFin.getYear() + 1900));
						// System.out.println("mes fechafin: "
						// + listaMeses.get(fechaFin.getMonth()));
						for (CruceMapaDeRiesgo cruce : matriz.getCruces()) {
							if (cruce.getRoEvento().getCodigoEven() == even
									.getCodigoEven()
									&& cruce.getRoNegocio().getCodigoNego() == nego
											.getCodigoNego()) {

								// System.out.println("--cruce--");
								// System.out.println("año: "
								// + cruce.getFecha().getYear());
								// System.out.println("mes: "
								// + listaMeses.get(cruce.getFecha()
								// .getMonth()));
								// System.out.println("evento: "
								// + cruce.getRoEvento().getNombreEven());
								// System.out.println("negocio: "
								// + cruce.getRoNegocio().getNombreNego());

								series1.set(anioMatriz,
										cruce.getNumeroOcurrencias());
								series2.set(anioMatriz,
										cruce.getSeveridadCruce());
							}
						}
					}

					lineModel1.addSeries(series1);
					lineModel2.addSeries(series2);
				}
				lineModelTodos.add(lineModel1);
				lineModelTodos.add(lineModel2);
			}

		} else {
			lineModelTodos = new ArrayList<LineChartModel>();
			System.out.println(" Gráficos por Mes");
			for (RoEvento even : eventosFinales) {
				// System.out.println("-evento: " + even.getNombreEven());
				LineChartModel lineModel1 = new LineChartModel();
				LineChartModel lineModel2 = new LineChartModel();
				lineModel1 = new LineChartModel();
				lineModel2 = new LineChartModel();
				lineModel1.setTitle(even.getNombreEven() + " [Frecuencia]");
				lineModel1.setLegendPosition("e");
				lineModel1.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
				lineModel1.setShowPointLabels(true);
				lineModel1
						.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
				lineModel1.getAxes().put(AxisType.X, new CategoryAxis(""));
				lineModel1.getAxis(AxisType.X).setTickAngle(-30);
				Axis yAxis = lineModel1.getAxis(AxisType.Y);
				yAxis.setLabel("# de Ocurrencias");
				yAxis.setMin(0);

				lineModel2.setTitle(even.getNombreEven() + " [Severidad]");
				lineModel2.setLegendPosition("e");
				lineModel2.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
				lineModel2
						.setSeriesColors("e6194b,3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080");
				lineModel2.setShowPointLabels(true);
				lineModel2.getAxes().put(AxisType.X, new CategoryAxis(""));
				lineModel2.getAxis(AxisType.X).setTickAngle(-30);
				Axis yAxis2 = lineModel2.getAxis(AxisType.Y);
				yAxis2.setLabel("Valor de Pérdida");
				yAxis2.setMin(0);
				// yAxis2.setTickFormat("%.2f");

				for (RoNegocio nego : negociosFinales) {
					LineChartSeries series1 = new LineChartSeries();
					LineChartSeries series2 = new LineChartSeries();
					series1.setLabel(nego.getNombreNego());
					series2.setLabel(nego.getNombreNego());
					// System.out.println("-negocio: " + nego.getNombreNego());
					for (Matriz matriz : listaCrucesTodos) {
						Integer anioMatriz = Integer.parseInt((matriz
								.getPeriodo().substring(5, 9)));// Ej:"2017"
						// System.out.println("---Matriz "
						// + listaCrucesTodos.indexOf(matriz) + " ---");
						// System.out.println("Periodo: " +
						// matriz.getPeriodo());
						// System.out.println("año matriz: " + anioMatriz);
						// System.out.println("mes matriz: "
						// + matriz.getPeriodo().substring(13));
						// System.out.println("año fechainicio: "
						// + (fechaInicio.getYear() + 1900));
						// System.out.println("mes fechainicio: "
						// + listaMeses.get(fechaInicio.getMonth()));
						// System.out.println("año fechafin: "
						// + (fechaFin.getYear() + 1900));
						// System.out.println("mes fechafin: "
						// + listaMeses.get(fechaFin.getMonth()));
						for (CruceMapaDeRiesgo cruce : matriz.getCruces()) {
							if (cruce.getRoEvento().getCodigoEven() == even
									.getCodigoEven()
									&& cruce.getRoNegocio().getCodigoNego() == nego
											.getCodigoNego()) {

								// System.out.println("--cruce--");
								// System.out.println("año: "
								// + cruce.getFecha().getYear());
								// System.out.println("mes: "
								// + listaMeses.get(cruce.getFecha()
								// .getMonth()));
								// System.out.println("evento: "
								// + cruce.getRoEvento().getNombreEven());
								// System.out.println("negocio: "
								// + cruce.getRoNegocio().getNombreNego());

								series1.set(
										listaMeses.get(cruce.getFecha()
												.getMonth())
												+ " "
												+ cruce.getFecha().getYear(),
										cruce.getNumeroOcurrencias());
								series2.set(
										listaMeses.get(cruce.getFecha()
												.getMonth())
												+ " "
												+ cruce.getFecha().getYear(),
										cruce.getSeveridadCruce());
							}
						}
					}

					lineModel1.addSeries(series1);
					lineModel2.addSeries(series2);
				}
				lineModelTodos.add(lineModel1);
				lineModelTodos.add(lineModel2);
			}

		}

		System.out.println(" Finaliza método graficar");
	}

	// public boolean serieIsNotEmpty(LineChartSeries serie) {
	// boolean notEmpty = false;
	// Iterator it = serie.getData().entrySet().iterator();
	// while (it.hasNext()) {
	// Map.Entry pair = (Map.Entry) it.next();
	// if (Integer.parseInt(pair.getValue().toString())> 0) {
	// notEmpty = true;
	// }
	// if (notEmpty)
	// break;
	// }
	// return notEmpty;
	// }

	public void imprimirTodosCrucesHistorial() {
		System.out.println("inicio imprimirTodosCruces");
		for (Matriz matriz1 : listaCrucesTodos) {
			System.out.println("periodo:" + matriz1.getPeriodo());
			for (CruceMapaDeRiesgo cruce : matriz1.getCruces()) {
				System.out.println("Todos cruces historial");
				System.out.println("-evento: "
						+ cruce.getRoEvento().getNombreEven());
				System.out.println("-negocio: "
						+ cruce.getRoNegocio().getNombreNego());
				System.out.println("-numOcur: " + cruce.getNumeroOcurrencias());
				System.out.println(" -severidad(double) "
						+ cruce.getSeveridadCruce().doubleValue());
				System.out.println(" -severidad(double) "
						+ cruce.getSeveridadCruce().floatValue());
				System.out.println("-FECHA: " + cruce.getFecha());

			}
		}
		System.out.println("fin imprimir");
	}

	public String resaltarValoresNoVacíos(int num) {
		if (num != 0) {
			// return "font-weight: bold,font-heigh;text-shadow: 2px 2px black";

			// return
			// "background-color: rgb(245, 245, 239);color:black;font-weight: bold; font-size:12px";
			return "font-weight: bold;font-size:12px;";
			// return "background-color: rgb(46,46, 31)";
		} else {
			return null;
		}

	}

	public int maxTotalOcurrencias() {

		String s = "";
		for (RoEvento evento : eventosFinales) {

			if (evento.getTotalOcurrencias() > 0
					&& evento.getTotalOcurrencias() > valorMasAltoOcurrencia) {
				valorMasAltoOcurrencia = evento.getTotalOcurrencias();
				codigoEventoMaxTotalOcurrencias = evento.getCodigoEven();
				s = evento.getNombreEven();

			}
		}
		// System.out.println("el valor mas alto es");
		// System.out.println("evento: " + s);
		// System.out.println("codigo" + codigoEventoMaxTotalOcurrencias);
		System.out.println("valorMasAltoOcurrencia: " + valorMasAltoOcurrencia);

		return codigoEventoMaxTotalOcurrencias;
	}

	public int maxTotalSeveridad() {

		for (RoEvento evento : eventosFinales) {
			if (evento.getTotalSeveridad().intValue() > 0
					&& evento.getTotalSeveridad().intValue() > valorMasAltoSeveridad) {
				valorMasAltoSeveridad = evento.getTotalSeveridad().intValue();
				codigoEventoMaxTotalSeveridad = evento.getCodigoEven();

			}
		}
		// System.out.println("el valor mas alto severidad es");
		// System.out.println("codigo" + codigoEventoMaxTotalSeveridad);
		System.out.println("valorMasAltoSeveridad: " + valorMasAltoSeveridad);
		return codigoEventoMaxTotalSeveridad;
	}

	public String resaltarTotalEventoMasAlto(String tipo, RoEvento event) {

		if (tipo.equals("frecuencia")) {

			if (event.getTotalOcurrencias() > 0) {
				// comparamos si el parámetro equivale al valor mas alto
				if (event.getCodigoEven() == codigoEventoMaxTotalOcurrencias) {

					return "background-color:rgb(204, 0, 0);color:white;text-shadow: 2px 2px black;font-weight: bold; font-size:12px";
				} else {
					return "background-color:rgb(226, 226, 208);color:black;font-weight: bold; font-size:12px";

				}
			} else {
				return null;

			}

		} else if (tipo.equals("severidad")) {

			// comparamos si el parámetro equivale al valor mas alto
			if (event.getTotalSeveridad().intValue() > 0) {
				// "font-weight: bold,font-heigh;text-shadow: 2px 2px black";
				if (event.getCodigoEven() == codigoEventoMaxTotalSeveridad) {
					// rgb(255, 204, 204)
					return "background-color:rgb(204, 0, 0);color:white;text-shadow: 2px 2px black;font-weight: bold; font-size:12px";

				} else {
					return "background-color:rgb(226, 226, 208);color:black;font-weight: bold; font-size:12px";

				}

			} else {
				return null;
			}

		}

		return null;

	}

	public List<CruceMapaDeRiesgo> maxCruceHistorial() {
		int maxPorEventoOcurrencia = 0;
		List<CruceMapaDeRiesgo> listaCrucesMaxPorEvento = new ArrayList<CruceMapaDeRiesgo>();

		for (RoEvento evento : eventosFinales) {
			for (RoNegocio negocio : negociosFinales) {
				maxPorEventoOcurrencia = 0;
				CruceMapaDeRiesgo cruceMax = new CruceMapaDeRiesgo();
				cruceMax.setRoEvento(new RoEvento());
				cruceMax.setRoNegocio(new RoNegocio());
				cruceMax.setNumeroOcurrencias(0);
				for (Matriz matriz : listaCrucesTodos) {

					for (CruceMapaDeRiesgo cruce : matriz.getCruces()) {

						if (cruce.getRoEvento().getCodigoEven() == evento
								.getCodigoEven()
								&& cruce.getRoNegocio().getCodigoNego() == negocio
										.getCodigoNego()) {

							if (cruce.getNumeroOcurrencias() > maxPorEventoOcurrencia
									&& cruce.getNumeroOcurrencias() > 0) {

								maxPorEventoOcurrencia = cruce
										.getNumeroOcurrencias();

								cruceMax.setRoNegocio(cruce.getRoNegocio());
								cruceMax.setNumeroOcurrencias(cruce
										.getNumeroOcurrencias());
								cruceMax.setRoEvento(cruce.getRoEvento());

							}
						}
					}
				}
				if (cruceMax.getNumeroOcurrencias() > 0) {
					listaCrucesMaxPorEvento.add(cruceMax);

				}

			}

		}

		return listaCrucesMaxPorEvento;

	}

	// *metodo calcula el máximo cruce por evento de todos los meses*
	// public List<CruceMapaDeRiesgo> maxPorMesEnHistorial(int i) {
	// if (i + 1 < listaCrucesTodos.size()) {
	// int maxPorEventoOcurrencia = 0;
	//
	// List<CruceMapaDeRiesgo> listaCrucesMaxPorEvento = new
	// ArrayList<CruceMapaDeRiesgo>();
	//
	// for (RoEvento evento : eventosFinales) {
	// for (RoNegocio negocio : negociosFinales) {
	// maxPorEventoOcurrencia = 0;
	// CruceMapaDeRiesgo cruceMax = new CruceMapaDeRiesgo();
	// cruceMax.setRoEvento(new RoEvento());
	// cruceMax.setRoNegocio(new RoNegocio());
	// cruceMax.setNumeroOcurrencias(0);
	//
	// System.out.println("Periodo:"
	// + listaCrucesTodos.get(i).getPeriodo());
	// System.out.println(""
	// + listaCrucesTodos.get(i).getPeriodo()
	// .substring(13) + "/");
	// for (CruceMapaDeRiesgo cruce : listaCrucesTodos.get(i)
	// .getCruces()) {
	//
	// if (cruce.getRoEvento().getCodigoEven() == evento
	// .getCodigoEven()
	// && cruce.getRoNegocio().getCodigoNego() == negocio
	// .getCodigoNego()) {
	//
	// if (cruce.getNumeroOcurrencias() > maxPorEventoOcurrencia
	// && cruce.getNumeroOcurrencias() > 0) {
	//
	// maxPorEventoOcurrencia = cruce
	// .getNumeroOcurrencias();
	//
	// cruceMax.setRoNegocio(cruce.getRoNegocio());
	// cruceMax.setNumeroOcurrencias(cruce
	// .getNumeroOcurrencias());
	// cruceMax.setRoEvento(cruce.getRoEvento());
	// // cruceMax.setFecha(listaCrucesTodos.get(i).getPeriodo());
	//
	// }
	// }
	// }
	//
	// System.out.println("i: " + i);
	// System.out.println("listacrucestodos: "
	// + listaCrucesTodos.size());
	// System.out.println("Periodo:"
	// + listaCrucesTodos.get(i + 1).getPeriodo());
	// for (CruceMapaDeRiesgo cruce : listaCrucesTodos.get(i + 1)
	// .getCruces()) {
	//
	// if (cruce.getRoEvento().getCodigoEven() == evento
	// .getCodigoEven()
	// && cruce.getRoNegocio().getCodigoNego() == negocio
	// .getCodigoNego()) {
	//
	// if (cruce.getNumeroOcurrencias() > maxPorEventoOcurrencia
	// && cruce.getNumeroOcurrencias() > 0) {
	//
	// maxPorEventoOcurrencia = cruce
	// .getNumeroOcurrencias();
	//
	// cruceMax.setRoNegocio(cruce.getRoNegocio());
	// cruceMax.setNumeroOcurrencias(cruce
	// .getNumeroOcurrencias());
	// cruceMax.setRoEvento(cruce.getRoEvento());
	//
	// }
	// }
	// }
	//
	// if (cruceMax.getNumeroOcurrencias() > 0) {
	// listaCrucesMaxPorEventoPorMes.add(cruceMax);
	//
	// }
	//
	// }
	// }
	// maxPorMesEnHistorial(i + 1);
	//
	// }
	//
	// return listaCrucesMaxPorEventoPorMes;
	//
	// }

	public String resaltarCruceHistorialPorMes(String tipo,
			CruceMapaDeRiesgo cruceMapa) {

		List<String> listaMeses = new ArrayList<String>();
		listaMeses.add("Enero");// 0
		listaMeses.add("Febrero");// 1
		listaMeses.add("Marzo");// 2
		listaMeses.add("Abril");// 3
		listaMeses.add("Mayo");// 4
		listaMeses.add("Junio");// 5
		listaMeses.add("Julio");// 6
		listaMeses.add("Agosto");// 7
		listaMeses.add("Septiembre");// 8
		listaMeses.add("Octubre");// 9
		listaMeses.add("Noviembre");// 10
		listaMeses.add("Diciembre");// 11
		// pintamos matriz de frecuencia
		if (tipo.equals("frecuencia")) {
			// si periodicidad es anual
			if (periodicidad.equals("anual")) {
				// ----**----***---FALTA CODIGO

				// if (cruceMapa.getFecha() != null) {
				//
				// System.out.println("Cruceparam año: "
				// + cruceMapa.getFecha().getYear());
				// int añoAnterior = 0;
				// for (int i = 0; i < listaCrucesTodos.size(); i++) {
				// añoAnterior = cruceMapa.getFecha().getYear() + 1899;
				// System.out.println("Año Antertior:" + añoAnterior);
				//
				// System.out.println("periodo:"
				// + listaCrucesTodos.get(i).getPeriodo()
				// .substring(5));
				//
				// if (listaCrucesTodos.get(i).getPeriodo().substring(5)
				// .equals(añoAnterior)) {
				// System.out.println("cumple la condicion");
				//
				// }
				//
				// }
				// }

				// si periodicidad es mensual
			} else {
				if (cruceMapa.getFecha() != null) {
					// System.out.println("Fecha de cruce(parametro) no nula");
					// System.out.println("Cruceparam date: "
					// + cruceMapa.getFecha());
					// System.out.println("Cruceparam mes: "
					// + cruceMapa.getFecha().getMonth());

					int mesAnterior = 0;
					int añoCruce = 0;
					int añoAnterior = 0;
					for (int i = 0; i < listaCrucesTodos.size(); i++) {
						mesAnterior = cruceMapa.getFecha().getMonth() - 1;
						añoCruce = cruceMapa.getFecha().getYear();
						añoAnterior = cruceMapa.getFecha().getYear() - 1;
						Integer anioMatriz = Integer.parseInt((listaCrucesTodos
								.get(i).getPeriodo().substring(5, 9)));
						if (mesAnterior < 0) {
							mesAnterior = 11;
							añoCruce--;
						} else if (mesAnterior > 11) {
							mesAnterior = 0;
						}

						// System.out.println("año matriz: " + anioMatriz);
						// System.out.println("mes de matriz"
						// + listaCrucesTodos.get(i).getPeriodo()
						// .substring(13));
						// System.out.println("año cruce: "
						// + (cruceMapa.getFecha().getYear()));
						// System.out.println("mes cruce: "+listaMeses.get(cruceMapa.getFecha().getMonth()));
						//
						// System.out.println(" mes anterior: "
						// + listaMeses.get(mesAnterior));
						//
						// System.out.println("validando mismo año de matriz y cruce; y mes de matriz es el anterior al cruce");

						if ((anioMatriz.equals(cruceMapa.getFecha().getYear()) || anioMatriz == (cruceMapa
								.getFecha().getYear() - 1))
								&& listaCrucesTodos.get(i).getPeriodo()
										.substring(13)
										.equals(listaMeses.get(mesAnterior))) {
							// System.out.println("mismo año y mes anterior");
							// System.out.println("matriz periodo: "
							// + listaCrucesTodos.get(i).getPeriodo());
							// System.out.println("indice de la matriz: " + i);

							for (CruceMapaDeRiesgo cruce : listaCrucesTodos
									.get(i).getCruces()) {
								if (cruceMapa.getRoEvento().getCodigoEven() == cruce
										.getRoEvento().getCodigoEven()
										&& cruceMapa.getRoNegocio()
												.getCodigoNego() == cruce
												.getRoNegocio().getCodigoNego()) {
									// System.out
									// .println("mismo evento y negocio");
									//
									// System.out
									// .println(">>crucemapa (parametro)");
									// System.out.println(cruceMapa.getFecha());
									// System.out.println(cruceMapa.getRoEvento()
									// .getNombreEven());
									// System.out.println(cruceMapa.getRoNegocio()
									// .getNombreNego());
									// System.out.println(cruceMapa
									// .getNumeroOcurrencias());
									//
									// System.out.println(">>cruce  (matriz)");
									// System.out.println(cruce.getFecha());
									// System.out.println(cruce.getRoEvento()
									// .getNombreEven());
									// System.out.println(cruce.getRoNegocio()
									// .getNombreNego());
									// System.out.println(cruce
									// .getNumeroOcurrencias());

									if (cruceMapa.getNumeroOcurrencias() > cruce
											.getNumeroOcurrencias()) {
										// System.out
										// .println("Pintando frecuencia...");
										// System.out
										// .println("mayor que el anterior");
										return "background-color:rgb(255, 179, 179);color:black;";
									} else if (cruceMapa.getNumeroOcurrencias() < cruce
											.getNumeroOcurrencias()) {
										// System.out
										// .println("Pintando frecuencia...");
										//
										// System.out
										// .println("menor que el anterior");
										return "background-color:rgb(179, 255, 179);color:black;";
									} else if (cruceMapa.getNumeroOcurrencias() != 0
											&& cruceMapa.getNumeroOcurrencias() == cruce
													.getNumeroOcurrencias()) {
										// System.out
										// .println("Pintando frecuencia...");
										//
										// System.out
										// .println("igual que el anterior");
										return "background-color:rgb(226, 226, 208);color:black;";
									}

								}
							}

						}
						mesAnterior++;
					}
				}
			}
			// pintamos matriz de severidad
		} else if (tipo.equals("severidad")) {

			// si periodicidad es anual
			if (periodicidad.equals("anual")) {
				// ----**----***---FALTA CODIGO

				// si periodicidad es mensual

			} else {
				if (cruceMapa.getFecha() != null) {

					// System.out.println("Cruceparam date: "
					// + cruceMapa.getFecha());
					// System.out.println("Cruceparam mes: "
					// + cruceMapa.getFecha().getMonth());

					int mesAnterior = 0;

					for (int i = 0; i < listaCrucesTodos.size(); i++) {

						mesAnterior = cruceMapa.getFecha().getMonth() - 1;
						Integer anioMatriz = Integer.parseInt((listaCrucesTodos
								.get(i).getPeriodo().substring(5, 9)));

						if (mesAnterior < 0) {
							mesAnterior = 11;
						}

						// System.out.println("mes de matriz"
						// + listaCrucesTodos.get(i).getPeriodo()
						// .substring(13));
						// System.out.println("mes anterior: "
						// + listaMeses.get(mesAnterior));

						// System.out.println("validando año y mes");

						if ((anioMatriz.equals(cruceMapa.getFecha().getYear()) || anioMatriz == (cruceMapa
								.getFecha().getYear() - 1))
								&& listaCrucesTodos.get(i).getPeriodo()
										.substring(13)
										.equals(listaMeses.get(mesAnterior))) {
							// System.out.println("mismo año y mes anterior");

							// System.out.println("matriz periodo: "
							// + listaCrucesTodos.get(i).getPeriodo());

							for (CruceMapaDeRiesgo cruce : listaCrucesTodos
									.get(i).getCruces()) {
								if (cruceMapa.getRoEvento().getCodigoEven() == cruce
										.getRoEvento().getCodigoEven()
										&& cruceMapa.getRoNegocio()
												.getCodigoNego() == cruce
												.getRoNegocio().getCodigoNego()) {

									// System.out
									// .println("mismo evento y negocio");

									// System.out
									// .println("**CruceMapa (parámetro)");
									// System.out.println("indice: "
									// + listaCrucesTodos
									// .indexOf(cruceMapa));
									// System.out.println(" - Negocio: "
									// + cruceMapa.getRoNegocio()
									// .getNombreNego());
									// System.out.println(" - Evento: "
									// + cruceMapa.getRoEvento()
									// .getNombreEven());
									// System.out.println(" - Fecha: "
									// + cruceMapa.getFecha().toString());
									// System.out.println(" - Año: "
									// + cruceMapa.getFecha().getYear());
									// System.out.println(" - Mes: "
									// + cruceMapa.getFecha().getMonth());
									// System.out.println(" - NumOcur: "
									// + cruceMapa.getNumeroOcurrencias());
									// System.out.println(" - Severidad: "
									// + cruceMapa.getSeveridadCruce());
									//
									// System.out.println("**Cruce (matriz)");
									// System.out.println("indice: "
									// + listaCrucesTodos.indexOf(cruce));
									// System.out.println(" - Negocio: "
									// + cruce.getRoNegocio()
									// .getNombreNego());
									// System.out.println(" - Evento: "
									// + cruce.getRoEvento()
									// .getNombreEven());
									// System.out.println(" - Fecha: "
									// + cruce.getFecha().toString());
									// System.out.println(" - Año: "
									// + cruce.getFecha().getYear());
									// System.out.println(" - Mes: "
									// + cruce.getFecha().getMonth());
									// System.out.println(" - NumOcur: "
									// + cruce.getNumeroOcurrencias());
									// System.out.println(" - Severidad: "
									// + cruce.getSeveridadCruce());

									if (cruceMapa.getSeveridadCruce()
											.doubleValue() > cruce
											.getSeveridadCruce().doubleValue()) {
										// System.out
										// .println("Pintando severidad...");

										// System.out
										// .println("mayor que el anterior");
										return "background-color:rgb(255, 179, 179);color:black;";
									} else if (cruceMapa.getSeveridadCruce()
											.doubleValue() < cruce
											.getSeveridadCruce().doubleValue()) {
										// System.out
										// .println("Pintando severidad...");

										// System.out
										// .println("menor que el anterior");
										// return
										// "background-color:rgb(77, 255, 77);color:black;font-weight: bold; font-size:12px";

										return "background-color:rgb(179, 255, 179);color:black;";
									} else if (cruceMapa.getSeveridadCruce()
											.intValue() != 0
											&& cruceMapa.getSeveridadCruce()
													.doubleValue() == cruce
													.getSeveridadCruce()
													.doubleValue()) {
										// System.out
										// .println("Pintando severidad...");

										// System.out
										// .println("igual que el anterior");
										return "background-color:rgb(226, 226, 208);color:black;";
									}

								}
							}

						}
						mesAnterior++;
					}
				}
			}

		}

		return null;

	}

	public String resaltarHistorialCruceValorMasAlto(CruceMapaDeRiesgo cruceMapa) {
		boolean match = false;

		for (CruceMapaDeRiesgo cruce : listaCrucesMaxPorEvento) {
			if (cruceMapa.getRoEvento().getCodigoEven() == cruce.getRoEvento()
					.getCodigoEven()
					&& cruceMapa.getRoNegocio().getCodigoNego() == cruce
							.getRoNegocio().getCodigoNego()
					&& cruceMapa.getNumeroOcurrencias() == cruce
							.getNumeroOcurrencias()) {
				// System.out.println("match!!");
				match = true;
			}

		}

		if (match) {
			return "background-color:rgb(204, 0, 0);color:white;text-shadow: 2px 2px black;font-weight: bold; font-size:12px";
		} else {
			return "background-color: rgb(245, 245, 239);color:black;font-weight: bold; font-size:12px";
		}
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

	public List<ParamProbabilidadRiesgo> getParamProbabilidadRiesgoTodos() {
		return paramProbabilidadRiesgoTodos;
	}

	public void setParamProbabilidadRiesgoTodos(
			List<ParamProbabilidadRiesgo> paramProbabilidadRiesgoTodos) {
		this.paramProbabilidadRiesgoTodos = paramProbabilidadRiesgoTodos;
	}

	public List<ParamConsecuenciaImpacto> getParamConsecuenciaImpactoTodos() {
		return paramConsecuenciaImpactoTodos;
	}

	public void setParamConsecuenciaImpactoTodos(
			List<ParamConsecuenciaImpacto> paramConsecuenciaImpactoTodos) {
		this.paramConsecuenciaImpactoTodos = paramConsecuenciaImpactoTodos;
	}

	public List<CruceMapaDeRiesgo> getCruceMapaTodos() {
		return cruceMapaTodos;
	}

	public void setCruceMapaTodos(List<CruceMapaDeRiesgo> cruceMapaTodos) {
		this.cruceMapaTodos = cruceMapaTodos;
	}

	public CruceMapaDeRiesgo getCruceMapaVista() {
		return cruceMapaVista;
	}

	public void setCruceMapaVista(CruceMapaDeRiesgo cruceMapaVista) {
		this.cruceMapaVista = cruceMapaVista;
	}

	public RoEvento getRoEventoControlador() {
		return roEventoControlador;
	}

	public void setRoEventoControlador(RoEvento roEventoControlador) {
		this.roEventoControlador = roEventoControlador;
	}

	public RoEvento getRoEventoVista() {
		return roEventoVista;
	}

	public void setRoEventoVista(RoEvento roEventoVista) {
		this.roEventoVista = roEventoVista;
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

	public List<RoAriesgo> getAriesgoAuxiliares() {
		return ariesgoAuxiliares;
	}

	public void setAriesgoAuxiliares(List<RoAriesgo> ariesgoAuxiliares) {
		this.ariesgoAuxiliares = ariesgoAuxiliares;
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

	public CruceMapaDeRiesgo getCruceMapaAux() {
		return cruceMapaAux;
	}

	public void setCruceMapaAux(CruceMapaDeRiesgo cruceMapaAux) {
		this.cruceMapaAux = cruceMapaAux;
	}

	public List<RoDetalleEvento> getRoDetalleEventosTodos() {
		return roDetalleEventosTodos;
	}

	public void setRoDetalleEventosTodos(
			List<RoDetalleEvento> roDetalleEventosTodos) {
		this.roDetalleEventosTodos = roDetalleEventosTodos;
	}

	public List<RoDetalleEvento> getRoDetalleEventosQuitar() {
		return roDetalleEventosQuitar;
	}

	public void setRoDetalleEventosQuitar(
			List<RoDetalleEvento> roDetalleEventosQuitar) {
		this.roDetalleEventosQuitar = roDetalleEventosQuitar;
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

	public RoEvento getEventoAux() {
		return eventoAux;
	}

	public void setEventoAux(RoEvento eventoAux) {
		this.eventoAux = eventoAux;
	}

	public RoDetalleEvento getRoDetalleEventoVista() {
		return roDetalleEventoVista;
	}

	public void setRoDetalleEventoVista(RoDetalleEvento roDetalleEventoVista) {
		this.roDetalleEventoVista = roDetalleEventoVista;
	}

	public String getTipoMapa() {
		return tipoMapa;
	}

	public void setTipoMapa(String tipoMapa) {
		this.tipoMapa = tipoMapa;
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

	public int getTotalDetalleEventos() {
		return totalDetalleEventos;
	}

	public void setTotalDetalleEventos(int totalDetalleEventos) {
		this.totalDetalleEventos = totalDetalleEventos;
	}

	public BigDecimal getTotalSeveridadEventos() {
		return totalSeveridadEventos;
	}

	public void setTotalSeveridadEventos(BigDecimal totalSeveridadEventos) {
		this.totalSeveridadEventos = totalSeveridadEventos;
	}

	public String getNivelEventoFiltro() {
		return nivelEventoFiltro;
	}

	public void setNivelEventoFiltro(String nivelEventoFiltro) {
		this.nivelEventoFiltro = nivelEventoFiltro;
	}

	public String getNivelNegocioFiltro() {
		return nivelNegocioFiltro;
	}

	public void setNivelNegocioFiltro(String nivelNegocioFiltro) {
		this.nivelNegocioFiltro = nivelNegocioFiltro;
	}

	public void setNivelNegocioTodos(List<String> nivelNegocioTodos) {
		this.nivelNegocioTodos = nivelNegocioTodos;
	}

	public void setNivelEventoTodos(List<String> nivelEventoTodos) {
		this.nivelEventoTodos = nivelEventoTodos;
	}

	public Collection<String> getNivelNegocioTodos() {
		return nivelNegocioTodos;
	}

	public void setNivelNegocioTodos(Collection<String> nivelNegocioTodos) {
		this.nivelNegocioTodos = nivelNegocioTodos;
	}

	public Collection<String> getNivelEventoTodos() {
		return nivelEventoTodos;
	}

	public void setNivelEventoTodos(Collection<String> nivelEventoTodos) {
		this.nivelEventoTodos = nivelEventoTodos;
	}

	public List<RoNegocio> getRoNegociosFiltro() {
		return roNegociosFiltro;
	}

	public void setRoNegociosFiltro(List<RoNegocio> roNegociosFiltro) {
		this.roNegociosFiltro = roNegociosFiltro;
	}

	public List<RoEvento> getRoEventosFitro() {
		return roEventosFitro;
	}

	public void setRoEventosFitro(List<RoEvento> roEventosFitro) {
		this.roEventosFitro = roEventosFitro;
	}

	public boolean isMostrarMatrices() {
		return mostrarMatrices;
	}

	public void setMostrarMatrices(boolean mostrarMatrices) {
		this.mostrarMatrices = mostrarMatrices;
	}

	public List<RoEvento> getEventosFinales() {
		return eventosFinales;
	}

	public void setEventosFinales(List<RoEvento> eventosFinales) {
		this.eventosFinales = eventosFinales;
	}

	public List<RoNegocio> getNegociosFinales() {
		return negociosFinales;
	}

	public void setNegociosFinales(List<RoNegocio> negociosFinales) {
		this.negociosFinales = negociosFinales;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public List<Matriz> getListaCrucesTodos() {
		return listaCrucesTodos;
	}

	public void setListaCrucesTodos(List<Matriz> listaCrucesTodos) {
		this.listaCrucesTodos = listaCrucesTodos;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTipoValorPerdida() {
		return tipoValorPerdida;
	}

	public void setTipoValorPerdida(String tipoValorPerdida) {
		this.tipoValorPerdida = tipoValorPerdida;
	}

	public List<LineChartModel> getLineModelTodos() {
		return lineModelTodos;
	}

	public void setLineModelTodos(List<LineChartModel> lineModelTodos) {
		this.lineModelTodos = lineModelTodos;
	}

	public List<RoEvento> getEventosMapa() {
		return eventosMapa;
	}

	public void setEventosMapa(List<RoEvento> eventosMapa) {
		this.eventosMapa = eventosMapa;
	}

	// public LineChartModel getLineModel1() {
	// return lineModel1;
	// }
	//
	// public void setLineModel1(LineChartModel lineModel1) {
	// this.lineModel1 = lineModel1;
	// }

}
