package com.hc.ro.vista.admin.bean;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
import org.primefaces.util.Constants;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoAriesgo;
import com.hc.ro.modelo.RoCalifRiesgo;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoDeveFaro;
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
import com.hc.ro.negocio.ServicioRoDepartamento;
import com.hc.ro.negocio.ServicioRoDetalleEvento;
import com.hc.ro.negocio.ServicioRoDeveFaro;
import com.hc.ro.negocio.ServicioRoEvento;
import com.hc.ro.negocio.ServicioRoFactorRiesgo;
import com.hc.ro.negocio.ServicioRoNegocio;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoTipoPerdida;
import com.hc.ro.negocio.ServicioSisUsuario;
import com.hc.ro.utils.CruceMapaDeRiesgo;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@ManagedBean
@ViewScoped
public class ControladorMapasDeRiesgo {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/MapasDeRiesgo.jsf";

	@EJB
	ServicioRoDeveFaro servicioRoDeveFaro;
	@EJB
	ServicioRoDetalleEvento servicioRoDetalleEvento;
	@EJB
	ServicioRoEvento servicioRoEvento;
	@EJB
	ServicioRoAgencia servicioRoAgencia;
	@EJB
	ServicioRoDepartamento servicioRoDepartamento;
	@EJB
	ServicioRoProceso servicioRoProceso;
	@EJB
	ServicioRoTipoPerdida servicioRoTipoPerdida;
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
	ServicioSisUsuario servicioSisUsuario;

	// LISTAS
	private List<String> agenciasFiltro;
	private TreeNode agenciasVista;
	private TreeNode[] agenciasSeleccionadas;
	private ArrayList<TreeNode> nodosTodosAgencias;

	private List<String> departamentosFiltro;
	private TreeNode departamentosVista;
	private TreeNode[] departamentosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosDepartamentos;

	private List<String> procesosFiltro;
	private TreeNode procesosVista;
	private TreeNode[] procesosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosProcesos;

	private List<String> usuariosFiltro;
	private List<SisUsuario> usuariosTodos;

	private List<String> negociosFiltro;
	private TreeNode negociosVista;
	private TreeNode[] negociosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosNegocios;

	private List<String> eventosFiltro;
	private TreeNode eventosVista;
	private TreeNode[] eventosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosEventos;

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

	
	private List<RoDetalleEvento> roDetalleEventosTodosSinRepeticiones; //repeticiones por num de ocurrencia del detalle evento

	
	private List<RoAriesgo> ariesgoAuxiliares;
	private List<RoAriesgo> ariesgoTodos;
	private List<RoCalifRiesgo> calificacionesTodos;

	private CruceMapaDeRiesgo cruceMapaVista;
	private CruceMapaDeRiesgo cruceMapaAux;
	private RoEvento roEventoControlador;
	private RoEvento roEventoVista;
	private RoEvento eventoAux;
	private RoDetalleEvento roDetalleEventoVista;

	private int columnas;
	private int tipoFiltro;

	private Date fechaInicio;
	private Date fechaFin;

	private String tipoMapa;

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
	
	
	private List<String> agenciasConDatos;
	private List<String> departamentosConDatos;
	private List<String> procesosConDatos;
	private List<String> usuariosConDatos;
	private List<String> negociosConDatos;
	private List<String> eventosConDatos;
	private List<String> factoresRiesgoConDatos;
	private List<String> tiposPerdidaConDatos;
	

	public ControladorMapasDeRiesgo() {

		super();
		System.out.println("empezamos constructor");
		tipoMapa = new String();
		agenciasFiltro = new ArrayList<String>();
		agenciasVista = new DefaultTreeNode();
		System.out.println("empezamos constructor1");
		departamentosFiltro = new ArrayList<String>();
		departamentosVista = new DefaultTreeNode();
		System.out.println("empezamos constructor2");
		procesosFiltro = new ArrayList<String>();
		procesosVista = new DefaultTreeNode();
		System.out.println("empezamos constructor3");
		usuariosFiltro = new ArrayList<String>();
		usuariosTodos = new ArrayList<SisUsuario>();

		negociosFiltro = new ArrayList<String>();
		negociosVista = new DefaultTreeNode();

		eventosFiltro = new ArrayList<String>();
		eventosVista = new DefaultTreeNode();

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
		tipoFiltro = 1;
		System.out.println("acabamos constructor");

		//PARA EXPORTAR FILTROS
		agenciasConDatos=new ArrayList<String>();
		departamentosConDatos=new ArrayList<String>();
		procesosConDatos=new ArrayList<String>();
		usuariosConDatos=new ArrayList<String>();
		negociosConDatos=new ArrayList<String>();
		eventosConDatos=new ArrayList<String>();
		factoresRiesgoConDatos=new ArrayList<String>();
		tiposPerdidaConDatos=new ArrayList<String>();
		
		
	}

	@PostConstruct
	public void postConstruct() {
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
//		usuariosTodos = servicioSisUsuario.buscarTodos_codigoUsua_nombreUsua();
		usuariosTodos = servicioSisUsuario.buscarTodos();

		factorRiesgosTodos = servicioRoFactorRiesgo
				.buscarTodos_codigoFaro_nombreFaro();
		tipoPerdidasTodos = servicioRoTipoPerdida
				.buscarTodos_codigoTipe_nombreTipe();
		llenarArbolAgencia();
		llenarArbolDepartamento();
		llenarArbolEvento();
		llenarArbolNegocio();
		llenarArbolProceso();
		pintarMapa();
		//

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
			Paragraph parrafoTitulo = new Paragraph(
					"Mapa de Riesgo Cualitativo", FontFactory.getFont("arial",
							20, Font.BOLD));

			// insetamos la primera celda el titulo del reporte
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
			for (String agencia : agenciasConDatos) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Departamentos: ", FontFactory.getFont("arial",
					8, Font.BOLD));
			parrafo.add(frase);
			for (String agencia : departamentosConDatos) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Procesos: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : procesosConDatos) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Usuarios: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String usuario : usuariosConDatos) {
				frase = new Phrase(
						servicioSisUsuario.buscarNombrePorId(usuario) + ", ",
						FontFactory.getFont("arial", 8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Negocios: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : negociosConDatos) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Eventos: ", FontFactory.getFont("arial", 8,
					Font.BOLD));
			parrafo.add(frase);
			for (String agencia : eventosConDatos) {
				frase = new Phrase(agencia + ", ", FontFactory.getFont("arial",
						8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Factor de Riesgos: ", FontFactory.getFont(
					"arial", 8, Font.BOLD));
			parrafo.add(frase);
			for (String faro : factoresRiesgoConDatos) {
				frase = new Phrase(
						servicioRoFactorRiesgo.buscarNombrePorId(faro) + ", ",
						FontFactory.getFont("arial", 8));
				parrafo.add(frase);
			}
			document.add(parrafo);

			parrafo = new Paragraph();
			frase = new Phrase("Tipo de Perdida: ", FontFactory.getFont(
					"arial", 8, Font.BOLD));
			parrafo.add(frase);
			for (String tipe : tiposPerdidaConDatos) {
				frase = new Phrase(
						servicioRoTipoPerdida.buscarNombrePorId(tipe) + ", ",
						FontFactory.getFont("arial", 8));
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
				AgenciasAux = servicioRoAgencia
						.buscarAgenciaPorPadre_nombreAgia(Agencia
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
						.buscarDepartamentoPorPadre_nombreDept(Departamento
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
				NegociosAux = servicioRoNegocio
						.buscarNegocioPorPadre_nombreNego(Negocio
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
				ProcesosAux = servicioRoProceso
						.buscarProcesoPorPadre_nombrePros(Proceso
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
				EventosAux = servicioRoEvento
						.buscarEventoPorPadre_nombreEven(Evento.getNombreEven());
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
				servicioRoAgencia.buscarAgenciaPorPadre_nombreAgia("Agencias"),
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
						.buscarDepartamentoPorPadre_nombreDept("Departamentos"),
				arbolVirtual);
	}

	public void llenarArbolProceso() {
		procesosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Procesos", procesosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosProcesos = new ArrayList<TreeNode>();
		recursivaArbolProceso(
				servicioRoProceso.buscarProcesoPorPadre_nombrePros("Procesos"),
				arbolVirtual);
	}

	public void llenarArbolEvento() {
		eventosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Eventos", eventosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosEventos = new ArrayList<TreeNode>();
		recursivaArbolEvento(
				servicioRoEvento.buscarEventoPorPadre_nombreEven("Eventos"),
				arbolVirtual);
	}

	public void llenarArbolNegocio() {
		negociosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Negocios", negociosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosNegocios = new ArrayList<TreeNode>();
		recursivaArbolNegocio(
				servicioRoNegocio.buscarNegocioPorPadre_nombreNego("Negocios"),
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

	public void pintarMapa() {
		System.out.println("pintarMapa1");
		ariesgoAuxiliares = servicioRoAriesgo
				.buscarTodos_paramConsecuenciaImpacto_paramProbabilidadRiesgo_roCalifRiesgo__nombreClrs_roCalifRiesgo__colorClrs();
		System.out.println("pintarMapa2");
		paramConsecuenciaImpactoTodos = servicioParamConsecuenciaImpacto
				.buscarTodos();
		System.out.println("pintarMapa3");
		paramProbabilidadRiesgoTodos = servicioParamProbabilidadRiesgo
				.buscarTodosDesc();
		System.out.println("pintarMapa4");
		columnas = paramConsecuenciaImpactoTodos.size();
		System.out.println("pintarMapa5");
	}

	public void generarMapa() {
		tipoMapa = "Cualitativo Inherente";
		cruceMapaVista = new CruceMapaDeRiesgo();
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		pasarListaAgencias();
		pasarListaDepartamentos();
		pasarListaEventos();
		pasarListaNegocios();
		pasarListaProcesos();
		ariesgoAuxiliares = servicioRoAriesgo
				.buscarTodos_paramConsecuenciaImpacto_paramProbabilidadRiesgo_roCalifRiesgo__nombreClrs_roCalifRiesgo__colorClrs();
		cruceMapaTodos = new ArrayList<CruceMapaDeRiesgo>();
		cruceMapaAux = new CruceMapaDeRiesgo();
		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();
		
		roDetalleEventosTodosSinRepeticiones= new ArrayList<RoDetalleEvento>();

		
		roDetalleEventosAuxiliar = servicioRoDetalleEvento
				.buscarEventosFiltroRegistro(fechaInicio, fechaFin, tipoFiltro,
						"Cualitativo");
		System.out.println("numero de elementos de la consulta: "
				+ roDetalleEventosAuxiliar.size());
		for (RoDetalleEvento deve : roDetalleEventosAuxiliar) {

			System.out.println(deve.getCodigoDeve());
		}
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
																						try {
																							boolean interruptor;
																							interruptor = false;
																							for (Integer deveFaro : roDeveFarosAux) {
																								if (deveFaro
																										.toString()
																										.equals(faro)) {
																									interruptor = true;
																									break;
																								}
																							}
																							if (deve.getRoFactorRiesgo()
																									.getCodigoFaro() == Integer
																									.parseInt(faro)
																									|| interruptor) {
																								for (String tipe : tipoPerdidasFiltro) {
																									try {
																										if (deve.getRoTipoPerdida()
																												.getCodigoTipe() == Integer
																												.parseInt(tipe)) {
																											
																											//esta lista contiene los detalle evento sin considerar el num de ocurrencia(repeticiones)
//																											roDetalleEventosTodosSinRepeticiones.add(deve);

//																											for (int i = 0; i < deve.getNumOcur(); i++) {
						
																											roDetalleEventosTodos
																													.add(deve);
//																											}
																											
																										}
																									} catch (Exception e) {
																										// TODO:
																										// handle
																										// exception
																									}

																								}
																								break;
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
		

		for (ParamProbabilidadRiesgo proba : paramProbabilidadRiesgoTodos) {
			for (ParamConsecuenciaImpacto consec : paramConsecuenciaImpactoTodos) {

//				roDetalleEventosQuitar = cruceMapaAux.getDetalleEventos();
//				try {
//					for (RoDetalleEvento deve : roDetalleEventosTodos) {
//						roDetalleEventosTodos.remove(deve);
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
				cruceMapaAux = new CruceMapaDeRiesgo();
				cruceMapaAux
						.setDetalleEventos(new ArrayList<RoDetalleEvento>());
				cruceMapaAux.setRoEventos(new ArrayList<RoEvento>());
				cruceMapaAux.setParamConsecuenciaImpacto(consec);
				cruceMapaAux.setParamProbabilidadRiesgo(proba);
				for (RoAriesgo varAriesgo : ariesgoAuxiliares) {
					try {
						if (varAriesgo.getParamConsecuenciaImpacto()
								.getNumeroPconi() == consec.getNumeroPconi()
								&& varAriesgo.getParamProbabilidadRiesgo()
										.getNumeroPprr() == proba
										.getNumeroPprr()) {
							cruceMapaAux.setRoAriesgo(varAriesgo);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				for (RoDetalleEvento detalle : roDetalleEventosTodos) {
					if (detalle.getParamConsecuenciaImpactoAntes()
							.getNumeroPconi() == consec.getNumeroPconi()
							&& detalle.getParamProbabilidadRiesgoAntes()
									.getNumeroPprr() == proba.getNumeroPprr()) {
						cruceMapaAux.getDetalleEventos().add(detalle);
						eventoAux = new RoEvento();
						obtenerPadre(detalle.getRoEvento());
						boolean auxB = false;
						for (RoEvento evento : cruceMapaAux.getRoEventos()) {
							if (eventoAux.getCodigoEven() == evento
									.getCodigoEven()) {
								auxB = auxB || true;
								break;
							}
						}
						if (!auxB) {
							cruceMapaAux.getRoEventos().add(eventoAux);
						}
					}
				}
				cruceMapaTodos.add(cruceMapaAux);
			}

		}

	}

	public void obtenerPadre(RoEvento evento) {
		if (evento.getAncestroEven().equals("Eventos")) {
			eventoAux = evento;
		} else {
			obtenerPadre(servicioRoEvento
					.buscarEventoPorNombre_codigoEven_nombreEven_ancestroEven(evento
							.getAncestroEven()));
		}
	}

	public void generarMapaResidual() {
		tipoMapa = "Cualitativo Residual";
		cruceMapaVista = new CruceMapaDeRiesgo();
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		pasarListaAgencias();
		pasarListaDepartamentos();
		pasarListaEventos();
		pasarListaNegocios();
		pasarListaProcesos();
		ariesgoAuxiliares = servicioRoAriesgo
				.buscarTodos_paramConsecuenciaImpacto_paramProbabilidadRiesgo_roCalifRiesgo__nombreClrs_roCalifRiesgo__colorClrs();
		cruceMapaTodos = new ArrayList<CruceMapaDeRiesgo>();
		cruceMapaAux = new CruceMapaDeRiesgo();
		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();
		roDetalleEventosAuxiliar = servicioRoDetalleEvento
				.buscarEventosFiltroRegistro(fechaInicio, fechaFin, tipoFiltro,
						"Cualitativo");

		for (RoDetalleEvento deve : roDetalleEventosAuxiliar) {
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
																						try {
																							if (deve.getRoFactorRiesgo()
																									.getCodigoFaro() == Integer
																									.parseInt(faro)) {
																								for (String tipe : tipoPerdidasFiltro) {
																									try {
																										if (deve.getRoTipoPerdida()
																												.getCodigoTipe() == Integer
																												.parseInt(tipe)) {
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
		roDetalleEventosQuitar = new ArrayList<RoDetalleEvento>();

		for (ParamProbabilidadRiesgo proba : paramProbabilidadRiesgoTodos) {
			for (ParamConsecuenciaImpacto consec : paramConsecuenciaImpactoTodos) {

				roDetalleEventosQuitar = cruceMapaAux.getDetalleEventos();
				try {
					for (RoDetalleEvento deve : roDetalleEventosQuitar) {
						roDetalleEventosTodos.remove(deve);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				cruceMapaAux = new CruceMapaDeRiesgo();
				cruceMapaAux
						.setDetalleEventos(new ArrayList<RoDetalleEvento>());
				cruceMapaAux.setRoEventos(new ArrayList<RoEvento>());
				cruceMapaAux.setParamConsecuenciaImpacto(consec);
				cruceMapaAux.setParamProbabilidadRiesgo(proba);
				for (RoAriesgo varAriesgo : ariesgoAuxiliares) {
					try {
						if (varAriesgo.getParamConsecuenciaImpacto()
								.getNumeroPconi() == consec.getNumeroPconi()
								&& varAriesgo.getParamProbabilidadRiesgo()
										.getNumeroPprr() == proba
										.getNumeroPprr()) {
							cruceMapaAux.setRoAriesgo(varAriesgo);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
				for (RoDetalleEvento detalle : roDetalleEventosTodos) {
					if (detalle.getParamConsecuenciaImpactoDespues()
							.getNumeroPconi() == consec.getNumeroPconi()
							&& detalle.getParamProbabilidadRiesgoDespues()
									.getNumeroPprr() == proba.getNumeroPprr()) {
						cruceMapaAux.getDetalleEventos().add(detalle);
						eventoAux = new RoEvento();

						obtenerPadre(detalle.getRoEvento());
						boolean auxB = false;
						for (RoEvento evento : cruceMapaAux.getRoEventos()) {
							if (eventoAux.getCodigoEven() == evento
									.getCodigoEven()) {
								auxB = auxB || true;
								break;
							}
						}
						if (!auxB) {
							cruceMapaAux.getRoEventos().add(eventoAux);
						}
					}
				}
				cruceMapaTodos.add(cruceMapaAux);
			}
		}
	}

	public void seleccionarCruce() {
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		for (RoEvento evento : cruceMapaVista.getRoEventos()) {
			evento.setNumeroOcurrencias(0);
			for (RoDetalleEvento detalleEvento : cruceMapaVista
					.getDetalleEventos()) {
				eventoAux = new RoEvento();
				obtenerPadre(detalleEvento.getRoEvento());
				if (eventoAux.getCodigoEven() == evento.getCodigoEven()) {
					evento.setNumeroOcurrencias(evento.getNumeroOcurrencias() + detalleEvento.getNumOcur());
				}

			}
		}
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

	public List<String> getAgenciasFiltro() {
		return agenciasFiltro;
	}

	public void setAgenciasFiltro(List<String> agenciasFiltro) {
		this.agenciasFiltro = agenciasFiltro;
	}

	public List<String> getDepartamentosFiltro() {
		return departamentosFiltro;
	}

	public void setDepartamentosFiltro(List<String> departamentosFiltro) {
		this.departamentosFiltro = departamentosFiltro;
	}

	public List<String> getProcesosFiltro() {
		return procesosFiltro;
	}

	public void setProcesosFiltro(List<String> procesosFiltro) {
		this.procesosFiltro = procesosFiltro;
	}

	public List<String> getNegociosFiltro() {
		return negociosFiltro;
	}

	public void setNegociosFiltro(List<String> negociosFiltro) {
		this.negociosFiltro = negociosFiltro;
	}

	public List<String> getEventosFiltro() {
		return eventosFiltro;
	}

	public void setEventosFiltro(List<String> eventosFiltro) {
		this.eventosFiltro = eventosFiltro;
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

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public List<RoDetalleEvento> getRoDetalleEventosTodosSinRepeticiones() {
		return roDetalleEventosTodosSinRepeticiones;
	}

	public void setRoDetalleEventosTodosSinRepeticiones(
			List<RoDetalleEvento> roDetalleEventosTodosSinRepeticiones) {
		this.roDetalleEventosTodosSinRepeticiones = roDetalleEventosTodosSinRepeticiones;
	}

}
