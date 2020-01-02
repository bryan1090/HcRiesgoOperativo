package com.hc.ro.vista.admin.bean;

import groovy.util.MapEntry;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.metamodel.ListAttribute;
import javax.print.attribute.standard.Severity;

import org.apache.commons.collections.DefaultMapEntry;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoAriesgo;
import com.hc.ro.modelo.RoCalifRiesgo;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoDeveFaro;
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoEventoIndicador;
import com.hc.ro.modelo.RoEventoRecupera;
import com.hc.ro.modelo.RoFactorRiesgo;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoTipoIndicadorRiesgo;
import com.hc.ro.modelo.RoTipoPerdida;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioRoAgencia;
import com.hc.ro.negocio.ServicioRoDepartamento;
import com.hc.ro.negocio.ServicioRoDetalleEvento;
import com.hc.ro.negocio.ServicioRoDeveFaro;
import com.hc.ro.negocio.ServicioRoEvento;
import com.hc.ro.negocio.ServicioRoEventoIndicador;
import com.hc.ro.negocio.ServicioRoFactorRiesgo;
import com.hc.ro.negocio.ServicioRoNegocio;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoTipoIndicadorRiesgo;
import com.hc.ro.negocio.ServicioRoTipoPerdida;
import com.hc.ro.negocio.ServicioRoTipoProceso;
import com.hc.ro.negocio.ServicioSisUsuario;

@ManagedBean
@ViewScoped
public class ControladorReporteIndicadores {

	// manejadores
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	public final static String nombrePagina = "/paginas/ReporteDetalladoDinamico.jsf";

	@EJB
	ServicioRoProceso servicioRoProceso;

	@EJB
	ServicioRoDetalleEvento servicioRoDetalleEvento;

	@EJB
	ServicioRoEventoIndicador servicioRoEventoIndicador;
	@EJB
	ServicioRoAgencia servicioRoAgencia;
	@EJB
	ServicioRoDepartamento servicioRoDepartamento;
	@EJB
	ServicioRoNegocio servicioRoNegocio;
	@EJB
	ServicioSisUsuario servicioSisUsuario;
	@EJB
	ServicioRoEvento servicioRoEvento;
	@EJB
	ServicioRoFactorRiesgo servicioRoFactorRiesgo;
	@EJB
	ServicioRoTipoPerdida servicioRoTipoPerdida;
	@EJB
	ServicioRoDeveFaro servicioRoDeveFaro;
	@EJB
	ServicioRoTipoIndicadorRiesgo servicioRoTipoIndicadorRiesgo;

	private Date fechaInicio;
	private Date fechaFin;

	private int tipoFiltro;

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
	private TreeNode procesosVista;
	private TreeNode[] procesosSeleccionados;
	private ArrayList<TreeNode> nodosTodosProcesos;

	private List<String> usuariosFiltro;
	private List<SisUsuario> usuariosTodos;
	private boolean btodosUsuarios;

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

	private List<String> tipoRegistroFiltros;

	private List<String> factorRiesgosFiltro;
	private List<RoFactorRiesgo> factorRiesgosTodos;
	private boolean btodosFactorRiesgo;

	private List<String> tipoPerdidasFiltro;
	private List<RoTipoPerdida> tipoPerdidasTodos;
	private boolean btodosTipoPerdida;

	private List<RoAriesgo> ariesgoTodos;

	private List<RoCalifRiesgo> calificacionesTodos;

	private boolean btodosTipoIndicadores;

	private List<RoDetalleEvento> roDetalleEventosTodos;
	private List<RoDetalleEvento> roDetalleEventosVisibles;
	private List<RoDetalleEvento> roDetalleEventosAuxiliar;
	private List<RoDetalleEvento> roDetalleEventosSeleccionados;
	private List<RoDetalleEvento> detallesEventoSeleccionados;
	private RoDetalleEvento roDetalleEventoVista;

	private List<RoTipoIndicadorRiesgo> tipoIndicadoresTodos;
	private List<String> tipoIndicadoresFiltro;

	private List<RoEventoIndicador> indicadoresEventoVista;
	private boolean mostrarDtlIndicadores;

	private Date fechaInicioInri;
	private Date fechaFinInri;

	private LineChartModel lineModel1;

	private List<LineChartModel> lineModelTodos;

	
	public ControladorReporteIndicadores() {
		super();
		// TODO Auto-generated constructor stub
		fechaInicio = new Date();
		fechaFin = new Date();
		fechaInicioInri = new Date();
		fechaFinInri = new Date();

		fechaInicio.setMonth(11);
		fechaInicio.setYear(116);
		fechaFin.setMonth(0);
		fechaFin.setYear(117);
		fechaInicioInri.setMonth(11);
		fechaInicioInri.setYear(116);
		fechaFinInri.setMonth(0);
		fechaFinInri.setYear(117);

		tipoFiltro = 1;

		procesosVista = new DefaultTreeNode();
		procesosFiltro = new ArrayList<String>();

		usuariosFiltro = new ArrayList<String>();
		usuariosTodos = new ArrayList<SisUsuario>();

		factorRiesgosFiltro = new ArrayList<String>();
		factorRiesgosTodos = new ArrayList<RoFactorRiesgo>();

		roDetalleEventosVisibles = new ArrayList<RoDetalleEvento>();
		roDetalleEventosAuxiliar = new ArrayList<RoDetalleEvento>();
		roDetalleEventosSeleccionados = new ArrayList<RoDetalleEvento>();
		roDetalleEventoVista = new RoDetalleEvento();

		tipoIndicadoresTodos = new ArrayList<RoTipoIndicadorRiesgo>();
		tipoIndicadoresFiltro = new ArrayList<String>();

		indicadoresEventoVista = new ArrayList<RoEventoIndicador>();

		lineModel1 = new LineChartModel();

		lineModelTodos = new ArrayList<LineChartModel>();

	}

	@PostConstruct
	public void postConstruct() {
		procesosVista = new DefaultTreeNode();

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

		usuariosTodos = servicioSisUsuario.buscarTodos();
		factorRiesgosTodos = servicioRoFactorRiesgo.buscarTodos();
		tipoPerdidasTodos = servicioRoTipoPerdida.buscarTodos();
//		tipoIndicadoresTodos = servicioRoTipoIndicadorRiesgo.buscarTodos();

		llenarArbolAgencia();
		llenarArbolDepartamento();
		llenarArbolProceso();
		llenarArbolNegocio();
		llenarArbolEvento();

		lineModel1 = createLineModels2(lineModel1);

		lineModelTodos.add(lineModel1);

	}

	public LineChartModel createLineModels(LineChartModel lineModel) {
		lineModel = DefaultInitCategoryModel();
		lineModel.setTitle("Category Chart");
		lineModel.setLegendPosition("e");
		lineModel.setShowPointLabels(true);
		lineModel.getAxes().put(AxisType.X, new CategoryAxis("Mes"));
		Axis yAxis = lineModel.getAxis(AxisType.Y);
		yAxis.setLabel("Valor/Porcentaje");
		yAxis.setMin(0);
		yAxis.setMax(50);

		return lineModel;
	}

	public LineChartModel createLineModels2(LineChartModel lineModel) {
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

	private LineChartModel DefaultInitCategoryModel() {
		LineChartModel model = new LineChartModel();

		ChartSeries prueba = new ChartSeries();

		prueba.setLabel("Indicador");

		prueba.set("Enero", 0);
		prueba.set("Febrero", 0);
		prueba.set("Marzo", 0);
		prueba.set("Abril", 0);
		prueba.set("Mayo", 0);
		prueba.set("Junio", 0);
		prueba.set("Julio", 0);
		prueba.set("Agosto", 0);
		prueba.set("Septiembre", 0);
		prueba.set("Octubre", 0);
		prueba.set("Noviembre", 0);
		prueba.set("Diciembre", 0);

		model.addSeries(prueba);
		model.setExtender("extLegend");

		return model;
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

		prueba.set("Mayo", asd);
		prueba.set("Junio", 0);
		prueba.set("Julio", 0);
		prueba.set("Agosto", 0);
		prueba.set("Septiembre", 0);
		prueba.set("Octubre", 0);
		prueba.set("Noviembre", 0);
		prueba.set("Diciembre", 0);

		model.addSeries(prueba);
		model.setExtender("extLegend2");
		return model;
	}

	public void defaultFechaInicioInri() {
		fechaInicioInri = fechaInicio;

	}

	public void defaultFechaFinInri() {
		fechaFinInri = fechaFin;
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

	public void llenarArbolEvento() {
		eventosVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Eventos", eventosVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(true);
		nodosTodosEventos = new ArrayList<TreeNode>();
		recursivaArbolEvento(servicioRoEvento.buscarEventoPorPadre("Eventos"),
				arbolVirtual);
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

	public void pasarListaProcesos() {
		procesosFiltro = new ArrayList<String>();
		// System.out.println("procesostreesize: " +
		// procesosSeleccionados.length);
		for (TreeNode nodo : procesosSeleccionados) {

			procesosFiltro.add(nodo.getData().toString());
		}
		try {
			procesosFiltro.remove("Procesos");
		} catch (Exception e) {
			// TODO: handle exception
		}
		// System.out.println("******************procesosfiltrosize:"
		// + procesosFiltro.size());
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

	public void seleccionarTodosTipoIndicadores() {
		if (btodosTipoIndicadores) {
			tipoIndicadoresFiltro = new ArrayList<String>();
			for (RoTipoIndicadorRiesgo tipoIndicador : tipoIndicadoresTodos) {
				tipoIndicadoresFiltro.add(Integer.toString(tipoIndicador
						.getcodigoTinri()));
			}
		} else {
			tipoIndicadoresFiltro = new ArrayList<String>();
		}

	}

	public void filtrar() {
		System.out.println("Inicio Metodo Filtrar");
		roDetalleEventosVisibles = new ArrayList<RoDetalleEvento>();
		roDetalleEventoVista = new RoDetalleEvento();
		indicadoresEventoVista = new ArrayList<RoEventoIndicador>();
		List<Integer> roDeveFarosAux = new ArrayList<Integer>();

		pasarListaProcesos();
		pasarListaAgencias();
		pasarListaDepartamentos();
		pasarListaNegocios();
		pasarListaEventos();

		roDetalleEventosAuxiliar = servicioRoDetalleEvento
				.buscarEventosTodosFiltro(fechaInicio, fechaFin, tipoFiltro);

		FacesContext context = FacesContext.getCurrentInstance();

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
																														roDetalleEventosVisibles
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

		// --
		// System.out.println("**IMPRESION ANTES ");
		// for (RoDetalleEvento deve : roDetalleEventosVisibles) {
		//
		// List<RoEventoIndicador> deveIndicadores = new
		// ArrayList<RoEventoIndicador>();
		// deveIndicadores = servicioRoEventoIndicador
		// .buscarTodosPorCodigoDetalleEvento(deve.getCodigoDeve());
		// System.out.println("" + deve.getCodigoDeve());
		// deve.setRoEventoIndicadores(deveIndicadores);
		// if (!deveIndicadores.isEmpty()) {
		// for (RoEventoIndicador evenInri : deveIndicadores) {
		//
		// System.out.println(""
		// + evenInri.getRoTipoIndicadorRiesgo()
		// .getnombreTinri());
		// }
		// }
		//
		// }
		// --

		roDetalleEventosAuxiliar = new ArrayList<RoDetalleEvento>();
		for (RoDetalleEvento deve : roDetalleEventosVisibles) {
			List<RoEventoIndicador> deveIndicadores = new ArrayList<RoEventoIndicador>();
			boolean tipoIndicador;
			tipoIndicador = false;
			try {
				deveIndicadores = servicioRoEventoIndicador
						.buscarPorCodigoDetalleEventoFiltroFecha(
								deve.getCodigoDeve(), fechaInicioInri,
								fechaFinInri);
			} catch (Exception e) {
				// TODO: handle exception

				context.addMessage("mensaje", new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Error en Indicadores",
						"Valores incorrectos o nulos ingresados. Revisar parametrización"));
			}

			if (!deveIndicadores.isEmpty()) {

				for (RoEventoIndicador evenInri : deveIndicadores) {

					for (String tipoInri : tipoIndicadoresFiltro) {

						if (evenInri.getRoTipoIndicadorRiesgo()
								.getcodigoTinri() == Integer.parseInt(tipoInri
								.toString())) {
							tipoIndicador = true;
							break;
						}
					}
					if (tipoIndicador)
						break;
				}

				if (tipoIndicador)
					roDetalleEventosAuxiliar.add(deve);
			}
		}
		roDetalleEventosVisibles = roDetalleEventosAuxiliar;

		// --
		// System.out.println("**IMPRESION DESPUES");
		// for (RoDetalleEvento deve : roDetalleEventosVisibles) {
		//
		// List<RoEventoIndicador> deveIndicadores = new
		// ArrayList<RoEventoIndicador>();
		// deveIndicadores = deve.getRoEventoIndicadores();
		//
		// System.out.println("" + deve.getCodigoDeve());
		// if (!deveIndicadores.isEmpty()) {
		// for (RoEventoIndicador evenInri : deveIndicadores) {
		//
		// System.out.println(""
		// + evenInri.getRoTipoIndicadorRiesgo()
		// .getnombreTinri());
		// }
		// }
		// }
		// --

		graficarLineModelsIndividuales();
		System.out.println("# graficos line: "+lineModelTodos.size());

		System.out.println("Fin Metodo Filtrar");
	}

	private void graficarLineModelsIndividuales() {

		System.out.println("Inicio metodo graficar");
		boolean impreso = false;
		List<RoEventoIndicador> deveIndicadores;
		List<RoTipoIndicadorRiesgo> procesoTiposInri;

		List<String> data = new ArrayList<String>();

		String[] mesesString = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
				"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre" };

		String labelExt = "";
		boolean crearLineChart = false;
		lineModelTodos.clear();
		// System.out.println("tam procesos filtro: " + procesosFiltro.size());
		for (String nombrePros : procesosFiltro) {

			procesoTiposInri = new ArrayList<RoTipoIndicadorRiesgo>();
			procesoTiposInri = servicioRoTipoIndicadorRiesgo
					.buscarRoTipoIndicadorRiesgoPorNombreProceso(nombrePros);
			for (RoTipoIndicadorRiesgo tipoInri : procesoTiposInri) {
				if (tipoIndicadoresFiltro.contains(Integer.toString(tipoInri
						.getcodigoTinri()))) {
					crearLineChart = false;

					for (RoDetalleEvento event : roDetalleEventosVisibles) {
						if (event.getRoProceso().getNombrePros()
								.equals(nombrePros)) {

							deveIndicadores = new ArrayList<RoEventoIndicador>();
							deveIndicadores = servicioRoEventoIndicador
									.buscarPorCodigoDetalleEventoFiltroFecha(
											event.getCodigoDeve(),
											fechaInicioInri, fechaFinInri);
							for (RoEventoIndicador evenInri : deveIndicadores) {

								if (evenInri.getFechaEvin() != null) {
									if (evenInri.getFechaEvin().getMonth() <= fechaFinInri
											.getMonth()
											|| evenInri.getFechaEvin()
													.getMonth() >= fechaInicioInri
													.getMonth()) {
										if (evenInri.getRoTipoIndicadorRiesgo()
												.getcodigoTinri() == tipoInri
												.getcodigoTinri()) {

											// System.out.println("-evin: "
											// + evenInri.getCodigoEvin()
											// + "valor: "
											// + evenInri.getValorEvin());

											if (evenInri.getValorEvin() != 0) {
												crearLineChart = true;
												if (crearLineChart)
													break;
											}
											if (crearLineChart)
												break;
										}
										if (crearLineChart)
											break;
									}
									if (crearLineChart)
										break;
								}
								if (crearLineChart)
									break;
							}
							if (crearLineChart)
								break;
						}
						if (crearLineChart)
							break;
					}

					// System.out.println("crearline??" + crearLineChart);
					if (crearLineChart) {
						System.out.println("*Generando grafico individual...*");
						// lineModel2 = DefaultInitCategoryModel2();
						lineModel1 = initCategoryModelIndividual(tipoInri,
								nombrePros);

						lineModel1.setTitle(tipoInri.getnombreTinri() + "["
								+ nombrePros + "] ");
						lineModel1.setLegendPosition("e");
						lineModel1.setShowPointLabels(true);
						lineModel1.getAxes().put(AxisType.X,
								new CategoryAxis("Mes"));
						lineModel1.getAxis(AxisType.X).setTickAngle(-30);

						Axis yAxis = lineModel1.getAxis(AxisType.Y);
						yAxis.setLabel("Valor/Porcentaje");
						yAxis.setMin(0);
						yAxis.setTickFormat("%.2f");
						float yMax = (float) 0.00;
						for (ChartSeries serie : lineModel1.getSeries()) {
							Iterator it = serie.getData().entrySet().iterator();
							while (it.hasNext()) {
								Map.Entry pair = (Map.Entry) it.next();
								if (yMax < Float.parseFloat(pair.getValue()
										.toString())) {
									yMax = Float.parseFloat(pair.getValue()
											.toString());
								}
							}
						}
						// System.out.println("yMax: "+yMax);
						double techo = 0;
						double piso = 0;
						techo = Math.ceil(yMax);
						// System.out.println("techo: "+techo);
						// System.out.println("piso: "+piso);
						piso = Math.floor(yMax);

						double i = 0.50;
						int j = 5;

						i = valorASumarYmax(j, techo, i);
						// System.out.println(">i: "+i);

						if (yMax < techo - i) {
							// yMax = (float) (techo - i);
							// System.out.println("Debajo de la mitad del techo");
							// System.out.println("techo-i: "+(techo - i));
							yMax = (float) (techo - i);

						} else {
							// System.out.println("Encima de la mitad del techo");
							// System.out.println("techo+i: "+(techo + i));
							yMax = (float) (techo + i);
						}

						// yMax=(float)(yMax+0.5);
						// System.out.println("Poniendo yMax: " + (yMax));
						yAxis.setMax(yMax);
					}

					if (crearLineChart)
						lineModelTodos.add(lineModel1);

				}
			}
		}
	}

	private double valorASumarYmax(int j, double techo, double i) {

		if (techo < j) {
			return i;
		} else {
			return valorASumarYmax(j * 2, techo, i * 2);
		}
	}

	private LineChartModel initCategoryModelIndividual(
			RoTipoIndicadorRiesgo tipoInri, String nombrePros) {

		System.out.println("Creando grafico para el proceso: " + nombrePros
				+ "\n" + "tipoInri: " + tipoInri.getnombreTinri());
		
			List<RoEventoIndicador> deveIndicadores;

			List<String> data = new ArrayList<String>();

			String[] mesesString = { "Enero", "Febrero", "Marzo", "Abril",
					"Mayo", "Junio", "Julio", "Agosto", "Septiembre",
					"Octubre", "Noviembre", "Diciembre" };

			LineChartModel model = new LineChartModel();
			model.setSeriesColors("3cb44b,ffe119,0082c8,f58231,911eb4,46f0f0,f032e6,d2f53c,fabebe,008080,e6beff,aa6e28,fffac8,800000,aaffc3,808000,ffd8b1,000080,808080,e6194b");
			model.setExtender("extLegend2");
			String labelExt = "";

			ChartSeries var = new ChartSeries();
			BigDecimal acumValorPorTipoIndicator = new BigDecimal(0);

			labelExt = "";
			int añoInicio = fechaInicioInri.getYear() + 1900;
			System.out.println("año inicio" + añoInicio);
			int añoFin = fechaFinInri.getYear() + 1900;
			System.out.println("año fin" + añoFin);
			for (int año = añoInicio; año <= añoFin; año++) {

				int mesInicio=0;
				int mesFin = 11;
				if(añoFin>año)
				{
					mesInicio=fechaInicioInri.getMonth();
					mesFin=11;
				}
				if(añoFin==año)
				{
					mesInicio=0;
					mesFin=fechaFinInri.getMonth();
				}
				
			
				for (int mes = mesInicio; mes <= mesFin; mes++) {
					System.out.println("**mes: "+mes);
					acumValorPorTipoIndicator = new BigDecimal(0);
					for (RoDetalleEvento event : roDetalleEventosVisibles) {
						if (event.getRoProceso().getNombrePros()
								.equals(nombrePros)) {
							System.out.println("Evento: "+event.getCodigoDeve());

							deveIndicadores = new ArrayList<RoEventoIndicador>();
							deveIndicadores = servicioRoEventoIndicador
									.buscarPorCodigoDetalleEventoFiltroFecha(
											event.getCodigoDeve(),
											fechaInicioInri, fechaFinInri);
//							System.out.println("---------");
//							for (RoEventoIndicador evenInri : deveIndicadores) {
//								System.out.println("---------");
//								System.out.println("evenInri: "+evenInri.getCodigoEvin());
//								System.out.println("fecha: "+evenInri.getFechaEvin());
//								System.out.println("tipoInri"+evenInri.getRoTipoIndicadorRiesgo().getcodigoTinri());
//								System.out.println("valor: "+evenInri.getValorEvin());
//							}
//							System.out.println("---------");

							for (RoEventoIndicador evenInri : deveIndicadores) {

								if (evenInri.getFechaEvin() != null) {
									if (evenInri.getFechaEvin().getMonth() == mes
											&& (evenInri.getFechaEvin()
													.getYear() + 1900) == año) {
										if (evenInri.getRoTipoIndicadorRiesgo()
												.getcodigoTinri() == tipoInri
												.getcodigoTinri()) {

											if (evenInri.getValorEvin() != 0) {

												// acumValorPorTipoIndicator =
												// acumValorPorTipoIndicator
												// .add(new BigDecimal(evenInri
												// .getValorEvin())
												// .setScale(
												// 5,
												// BigDecimal.ROUND_HALF_UP));
												//
//												 data.add("evenInri:"
//												 + evenInri.getCodigoEvin()
//												 + " - "
//												 + evenInri.getValorEvin());
												if (evenInri
														.getRoTipoIndicadorRiesgo()
														.getnombreTinri()
														.startsWith("%")||evenInri.getRoTipoIndicadorRiesgo().getTipoValor().equals("porcentaje!")) {
													System.out
															.println("Ingresando porcentaje");
													// System.out.println("valor:"
													// +
													// evenInri.getValorEvin());

													acumValorPorTipoIndicator = acumValorPorTipoIndicator
															.add(new BigDecimal(
																	evenInri.getValorEvin())
																	.setScale(
																			2,
																			BigDecimal.ROUND_HALF_UP));
													// System.out.println("porcentaje:"
													// +
													// acumValorPorTipoIndicator
													// .floatValue());
													data.add("evenInri:"
															 +
															 evenInri.getCodigoEvin()+" - "+evenInri.getFechaEvin()
															 + " - "
															 +
															 evenInri.getValorEvin());

												} else {
													System.out
															.println("Ingresando numero");
													acumValorPorTipoIndicator = acumValorPorTipoIndicator
															.add(new BigDecimal(
																	evenInri.getValorEvin())
																	.setScale(
																			2,
																			BigDecimal.ROUND_HALF_UP));

													 data.add("evenInri:"
													 +
													 evenInri.getCodigoEvin()+" - "+evenInri.getFechaEvin()
													 + " - "
													 +
													 evenInri.getValorEvin());
												}
											}
										}
									}
								}
							}
						}
					}

					// GRÁFICO LINE
					// acumValorPorTipoIndicator=acumValorPorTipoIndicator.setScale(3,BigDecimal.ROUND_HALF_UP);
					// System.out.println("valor: " +
					// acumValorPorTipoIndicator);
					// System.out.println("valor float: "
					// + acumValorPorTipoIndicator.floatValue());
					if (acumValorPorTipoIndicator.floatValue() >= 0) {
						// solo añade el valor que en el mes es superior a 0
						data.add(tipoInri.getnombreTinri() + " - "
								+ acumValorPorTipoIndicator);
						var.setLabel(tipoInri.getnombreTinri());

						var.set(mesesString[mes]+" "+año,
								acumValorPorTipoIndicator);

					}
					labelExt += acumValorPorTipoIndicator + "/";

				}
				if (var.getData() != null) {

				}
			}
			model.addSeries(var);


//			 imprimirData(data);

			return model;
		

	}

	public void imprimirData(List<String> data) {
		System.out.println("Imprimiendo Data");
		
		for (String s : data) {
			System.out.println("-" + s);
		}
	}

	

	
	public void seleccionar() {

		// if (roDetalleEventoVista.getRoEventoIndicadores() != null) {
		// indicadoresEventoVista = roDetalleEventoVista
		// .getRoEventoIndicadores();
		indicadoresEventoVista = new ArrayList<RoEventoIndicador>();
		List<RoEventoIndicador> deveIndicadores = new ArrayList<RoEventoIndicador>();
		deveIndicadores = servicioRoEventoIndicador
				.buscarPorCodigoDetalleEventoFiltroFecha(
						roDetalleEventoVista.getCodigoDeve(), fechaInicioInri,
						fechaFinInri, tipoIndicadoresFiltro);
		indicadoresEventoVista = deveIndicadores;
		System.out.println(">#indicadores: " + indicadoresEventoVista.size());
		// for (RoEventoIndicador evenInri : deveIndicadores) {
		// System.out.println(">evenInri tipo:"+evenInri.getRoTipoIndicadorRiesgo().getcodigoTinri());
		// if (tipoIndicadoresFiltro.contains(Integer.toString(evenInri
		// .getRoTipoIndicadorRiesgo().getcodigoTinri()))) {
		// indicadoresEventoVista.add(evenInri);
		// }
		// }

		// }

	}
	
	public void cargarIndicadoresPorProcesosSeleccionados(){
		System.out.println("Cargando Tipos de Indicadores...");
//		System.out.println("tamaño: "+tipoIndicadoresTodos.size());
		
		List<RoTipoIndicadorRiesgo> aux;
		aux=new ArrayList<RoTipoIndicadorRiesgo>();
//		aux=tipoIndicadoresTodos;
		aux=servicioRoTipoIndicadorRiesgo.buscarTodos();
		
		
	pasarListaProcesos();
		
		System.out.println("Procesos filtro: " +procesosFiltro.size() );
		System.out.println("Procesos Seleccionados: " +procesosSeleccionados.length);
		
//		for(String codigoPros:procesosFiltro)
//			{
//			System.out.println(codigoPros+", ");
//			}
//		for (TreeNode nodo : procesosSeleccionados) {
//			System.out.println(nodo.getData().toString());
//		}
	
		
		
//		
//		for (TreeNode nodo : procesosSeleccionados) {
//			RoProceso pros=new RoProceso();
//			pros=servicioRoProceso.buscarProcesoPorNombre(nodo.getData().toString());
//			System.out.println("nombre: "+pros.getNombrePros());
//
//
//			procesosFiltro.add(nodo.getData().toString());
//		}
//		try {
//			procesosFiltro.remove("Procesos");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
		
		
		
		
		
		
		
		
		tipoIndicadoresTodos=new ArrayList<RoTipoIndicadorRiesgo>();
		
		for(RoTipoIndicadorRiesgo tipoInri:aux)
		{
			if(procesosFiltro.contains((tipoInri.getRoProceso().getNombrePros())))
			{
				tipoIndicadoresTodos.add(tipoInri);
			}
		}
		
//		for(String codigoPros:procesosFiltro)
//		{
//			for(RoTipoIndicadorRiesgo tipoInri:aux)
//			{
//				if(tipoInri.getRoProceso().getCodigoPros()==Integer.parseInt(codigoPros))
//				{
//					tipoIndicadoresTodos.add(tipoInri);
//				}
//			}
//		}
		
		
//		System.out.println("tamaño: "+tipoIndicadoresTodos.size());
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

	public TreeNode getProcesosVista() {
		return procesosVista;
	}

	public void setProcesosVista(TreeNode procesosVista) {
		this.procesosVista = procesosVista;
	}

	public ArrayList<TreeNode> getNodosTodosProcesos() {
		return nodosTodosProcesos;
	}

	public void setNodosTodosProcesos(ArrayList<TreeNode> nodosTodosProcesos) {
		this.nodosTodosProcesos = nodosTodosProcesos;
	}

	public TreeNode[] getProcesosSeleccionados() {
		return procesosSeleccionados;
	}

	public void setProcesosSeleccionados(TreeNode[] procesosSeleccionados) {
		this.procesosSeleccionados = procesosSeleccionados;
	}

	public List<String> getProcesosFiltro() {
		return procesosFiltro;
	}

	public void setProcesosFiltro(List<String> procesosFiltro) {
		this.procesosFiltro = procesosFiltro;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
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

	public boolean isBtodosUsuarios() {
		return btodosUsuarios;
	}

	public void setBtodosUsuarios(boolean btodosUsuarios) {
		this.btodosUsuarios = btodosUsuarios;
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

	public boolean isBtodosFactorRiesgo() {
		return btodosFactorRiesgo;
	}

	public void setBtodosFactorRiesgo(boolean btodosFactorRiesgo) {
		this.btodosFactorRiesgo = btodosFactorRiesgo;
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

	public boolean isBtodosTipoPerdida() {
		return btodosTipoPerdida;
	}

	public void setBtodosTipoPerdida(boolean btodosTipoPerdida) {
		this.btodosTipoPerdida = btodosTipoPerdida;
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

	public List<String> getTipoRegistroFiltros() {
		return tipoRegistroFiltros;
	}

	public void setTipoRegistroFiltros(List<String> tipoRegistroFiltros) {
		this.tipoRegistroFiltros = tipoRegistroFiltros;
	}

	public boolean isBtodosTipoIndicadores() {
		return btodosTipoIndicadores;
	}

	public void setBtodosTipoIndicadores(boolean btodosTipoIndicadores) {
		this.btodosTipoIndicadores = btodosTipoIndicadores;
	}

	public List<RoTipoIndicadorRiesgo> getTipoIndicadoresTodos() {
		return tipoIndicadoresTodos;
	}

	public void setTipoIndicadoresTodos(
			List<RoTipoIndicadorRiesgo> tipoIndicadoresTodos) {
		this.tipoIndicadoresTodos = tipoIndicadoresTodos;
	}

	public List<String> getTipoIndicadoresFiltro() {
		return tipoIndicadoresFiltro;
	}

	public void setTipoIndicadoresFiltro(List<String> tipoIndicadoresFiltro) {
		this.tipoIndicadoresFiltro = tipoIndicadoresFiltro;
	}

	public RoDetalleEvento getRoDetalleEventoVista() {
		return roDetalleEventoVista;
	}

	public void setRoDetalleEventoVista(RoDetalleEvento roDetalleEventoVista) {
		this.roDetalleEventoVista = roDetalleEventoVista;
	}

	public List<RoEventoIndicador> getIndicadoresEventoVista() {
		return indicadoresEventoVista;
	}

	public void setIndicadoresEventoVista(
			List<RoEventoIndicador> indicadoresEventoVista) {
		this.indicadoresEventoVista = indicadoresEventoVista;
	}

	public List<RoDetalleEvento> getRoDetalleEventosVisibles() {
		return roDetalleEventosVisibles;
	}

	public void setRoDetalleEventosVisibles(
			List<RoDetalleEvento> roDetalleEventosVisibles) {
		this.roDetalleEventosVisibles = roDetalleEventosVisibles;
	}

	public Date getFechaInicioInri() {
		return fechaInicioInri;
	}

	public void setFechaInicioInri(Date fechaInicioInri) {
		this.fechaInicioInri = fechaInicioInri;
	}

	public Date getFechaFinInri() {
		return fechaFinInri;
	}

	public void setFechaFinInri(Date fechaFinInri) {
		this.fechaFinInri = fechaFinInri;
	}

	public List<LineChartModel> getLineModelTodos() {
		return lineModelTodos;
	}

	public void setLineModelTodos(List<LineChartModel> lineModelTodos) {
		this.lineModelTodos = lineModelTodos;
	}

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	public void setLineModel1(LineChartModel lineModel1) {
		this.lineModel1 = lineModel1;
	}



}
