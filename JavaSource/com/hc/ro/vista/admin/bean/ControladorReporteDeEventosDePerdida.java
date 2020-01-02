package com.hc.ro.vista.admin.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;

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
import com.hc.ro.utils.CruceAgencia;
import com.hc.ro.utils.CruceMapaDeRiesgo;

@ManagedBean
@ViewScoped
public class ControladorReporteDeEventosDePerdida {

	public final static String nombrePagina = "/paginas/ReporteDeEventosDePerdida.jsf";

	@ManagedProperty(value = "#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@ManagedProperty(value = "#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

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

	// Fechas
	private Date fechaInicio;
	private Date fechaFin;
	private int tipoFiltro;

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
	private List<RoNegocio> negociosTodos;
	private TreeNode negociosVista;
	private TreeNode[] negociosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosNegocios;

	private List<String> eventosFiltro;
	private List<RoEvento> eventosTodos;
	private TreeNode eventosVista;
	private TreeNode[] eventosSeleccionadas;
	private ArrayList<TreeNode> nodosTodosEventos;

	private List<RoAgencia> agenciasTodas;

	private List<String> factorRiesgosFiltro;

	private List<String> tipoRegistroFiltros;

	private String nivelEventoFiltro;
	private Collection<String> nivelEventoTodos;

	private List<RoFactorRiesgo> factorRiesgosTodos;
	private List<String> tipoPerdidasFiltro;
	private List<RoTipoPerdida> tipoPerdidasTodos;
	private List<RoDetalleEvento> roDetalleEventosTodos;
	private List<RoDetalleEvento> roDetalleEventosAuxiliar;
	private List<RoDetalleEvento> detallesEventoSeleccionados;
	private List<RoAriesgo> ariesgoAuxiliares;
	private List<RoAriesgo> ariesgoTodos;
	private List<RoCalifRiesgo> calificacionesTodos;
	private List<CruceMapaDeRiesgo> crucesTodos;
	private List<CruceAgencia> crucesPorAgenciaTodos;

	private List<RoEvento> roEventosFiltro;
	private List<RoAgencia> roAgenciasFiltro;

	private List<RoDetalleEvento> detallesEventoItemSeleccionado;

	// botones "Seleccionar Todos"
	private boolean btodosUsuarios;
	private boolean btodosFactorRiesgo;
	private boolean btodosTipoPerdida;

	// Entidades
	private RoEvento roEventoControlador;
	private RoEvento roEventoVista;
	private RoEvento eventoAux;
	private RoDetalleEvento roDetalleEventoVista;

	// Grafico
	private BarChartModel modelBar;

	public ControladorReporteDeEventosDePerdida() {

		super();
		System.out.println("Constructor");
		agenciasFiltro = new ArrayList<String>();
		agenciasVista = new DefaultTreeNode();

		departamentosFiltro = new ArrayList<String>();
		departamentosVista = new DefaultTreeNode();

		procesosFiltro = new ArrayList<String>();
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

		ariesgoAuxiliares = new ArrayList<RoAriesgo>();

		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();
		roDetalleEventosAuxiliar = new ArrayList<RoDetalleEvento>();
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();

		crucesTodos = new ArrayList<CruceMapaDeRiesgo>();

		ariesgoTodos = new ArrayList<RoAriesgo>();

		calificacionesTodos = new ArrayList<RoCalifRiesgo>();

		roEventoControlador = new RoEvento();
		roEventoVista = new RoEvento();
		eventoAux = new RoEvento();

		fechaInicio = new Date();
		fechaFin = new Date();
		tipoFiltro = 1;

		crucesPorAgenciaTodos = new ArrayList<CruceAgencia>();

		roEventosFiltro = new ArrayList<RoEvento>();
		roAgenciasFiltro = new ArrayList<RoAgencia>();

		modelBar = new BarChartModel();

		agenciasTodas = new ArrayList<RoAgencia>();
		detallesEventoItemSeleccionado = new ArrayList<RoDetalleEvento>();

	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("Post-Constructor");

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
		System.out.println("empezamos pc 9");
		tipoPerdidasTodos = servicioRoTipoPerdida
				.buscarTodos_codigoTipe_nombreTipe();
		calificacionesTodos = servicioRoCalifRiesgo.buscarTodos();
		llenarArbolAgencia();
		llenarArbolDepartamento();
		llenarArbolEvento();
		llenarArbolNegocio();
		llenarArbolProceso();
		List<RoEvento> eventosAux = new ArrayList<RoEvento>();
		eventosAux = servicioRoEvento
				.buscarEventoPorPadre_nombreEven("Eventos");

		buscarNivel(eventosAux, 0);

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

		fechaInicio.setYear(117);
		fechaInicio.setMonth(10);
		fechaFin.setYear(117);
		fechaFin.setMonth(11);

		defaultGrafico();

	}

	public void buscarNivel(List<RoEvento> listaEventos, int nivel) {
		// la primera llamada usa los parametros: lista de eventos con
		// ancestro="Evento" y nivel=0(revisar postconstruct)
		if (listaEventos.size() > 0) {
			for (RoEvento roEvento : listaEventos) {
				roEvento.setNivel(nivel + 1);
				eventosTodos.add(roEvento);
				nivelEventoTodos.add(Integer.toString(nivel + 1));
				List<RoEvento> eventosAux2 = new ArrayList<RoEvento>();
				eventosAux2 = servicioRoEvento
						.buscarEventoPorPadre_nombreEven(roEvento
								.getNombreEven());// obtiene lista de hijos
				buscarNivel(eventosAux2, nivel + 1);
			}
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

	public void generaReporte() {
		System.out.println("Ejecutando filtros...");
		roEventoVista = new RoEvento();
		detallesEventoSeleccionados = new ArrayList<RoDetalleEvento>();
		pasarListaAgencias();
		pasarListaDepartamentos();
		pasarListaEventos();
		pasarListaNegocios();
		pasarListaProcesos();
		ariesgoAuxiliares = servicioRoAriesgo
				.buscarTodos_paramConsecuenciaImpacto_paramProbabilidadRiesgo_roCalifRiesgo__nombreClrs_roCalifRiesgo__colorClrs();

		roDetalleEventosTodos = new ArrayList<RoDetalleEvento>();
		roDetalleEventosAuxiliar = servicioRoDetalleEvento
				.buscarEventosFiltroRegistro(fechaInicio, fechaFin, tipoFiltro,
						"Cuantitativo");
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
																		// for
																		// (RoEvento
																		// eventoNivel
																		// :
																		// eventosTodos)
																		// {

																		for (String evento : eventosFiltro) {
																			try {
																				// if
																				// (eventoNivel
																				// .getNombreEven()
																				// .equals(evento)
																				// &&
																				// eventoNivel
																				// .getNivel()
																				// >=
																				// Integer
																				// .parseInt(nivelEventoFiltro))
																				// {
																				if (deve.getRoEvento()
																						.getNombreEven()
																						.equals(evento)) {
																					for (String faro : factorRiesgosFiltro) {
																						try {
																							boolean interruptor;
																							interruptor = false;
																							for (Integer deveFaro : roDeveFarosAux) {
																								if (deveFaro == Integer
																										.parseInt(faro)) {
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
																											roDetalleEventosTodos
																													.add(deve);

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
																				// }
																			} catch (Exception e) {
																				// //
																				// TODO:
																				// //
																				// handle
																				// //
																				// exception
																			}

																		}
																		// }
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

		System.out.println("size detallesEventoTodos: "
				+ roDetalleEventosTodos.size());
		// System.out.println("padre: "+(buscarPadre(deve.getRoEvento())).getNombreEven());
		System.out.println("Llenando eventos todos");
		crucesTodos = new ArrayList<CruceMapaDeRiesgo>();
		agenciasTodas = new ArrayList<RoAgencia>();
		crucesPorAgenciaTodos = new ArrayList<CruceAgencia>();
		roEventosFiltro = new ArrayList<RoEvento>();
		roAgenciasFiltro = new ArrayList<RoAgencia>();

		agenciasTodas = servicioRoAgencia.buscarTodos();

		System.out.println("agenciasFiltro: " + agenciasFiltro.size());
		System.out.println("agenciasTodas: " + agenciasTodas.size());

		System.out.println("crucesPorAgencia size antes: "
				+ crucesPorAgenciaTodos.size());

		// CORREMOS FILTROS EN LISTAS: TODOS AGENCIAS Y TODOS EVENTOS
		for (RoEvento even : eventosTodos) {
			for (String nombreEventoFiltro : eventosFiltro) {
				if (even.getNombreEven().equals(nombreEventoFiltro)) {
					roEventosFiltro.add(even);
				}
			}
		}

		for (RoAgencia agia : agenciasTodas) {
			for (String nombreAgenciaFiltro : agenciasFiltro) {
				if (agia.getNombreAgia().equals(nombreAgenciaFiltro)) {
					roAgenciasFiltro.add(agia);
				}
			}
		}

		System.out.println("roeventosfiltro: " + roEventosFiltro.size());
		System.out.println("roAgenciasFiltro: " + roAgenciasFiltro.size());

		for (RoAgencia agencia : roAgenciasFiltro) {

			// SOLO AGENCIAS DE NIVEL 1
			if (agencia.getAncestroAgia().equals("Agencias")) {
				// POR CADA AGENCIA CREO UN CRUCEAGENCIA
				CruceAgencia cruceAgencia = new CruceAgencia();
				cruceAgencia
						.setCrucesPorAgencia(new ArrayList<CruceMapaDeRiesgo>());

				for (RoEvento even : roEventosFiltro) {

					// SI EL EVENTO DE NIVEL 1
					if (even.getNivel() == 1) {
						// POR CADA EVENTO CREO UN CRUCEMAPA
						CruceMapaDeRiesgo cruce = new CruceMapaDeRiesgo();
						cruce.setDetalleEventos(new ArrayList<RoDetalleEvento>());
						cruce.setEventosHijo(new ArrayList<RoEvento>());
						cruce.setRoEvento(even);
						for (RoDetalleEvento deve : roDetalleEventosTodos) {
							// SOLO DETALLES DE ESA AGENCIA
							if (deve.getRoAgencia().getNombreAgia()
									.equals(agencia.getNombreAgia())) {
								// ENCUENTRO EL PADRE DEL DETALLE
								RoEvento eventoPadre = new RoEvento();
								eventoPadre = buscarPadre(deve.getRoEvento());
								// SI EL DETALLE PERTENECE AL EVENTO
								// DE NIVEL 1, AÑADIMOS EL DETALLE
								// AL
								// CRUCEMAPA()
								if (even.getCodigoEven() == eventoPadre
										.getCodigoEven()) {
									cruce.getDetalleEventos().add(deve);
									// AÑADIMOS EVENTOS DE NIVEL4 AL
									// CRUCEMAPA
									for (RoEvento evento : eventosTodos) {
										// SI ES EL EVENTO(NIVEL 4)
										// ESTÁ
										// ASOCIADO
										// AL
										// DETALLE
										if (evento.getCodigoEven() == deve
												.getRoEvento().getCodigoEven()) {
											if (cruce.getEventosHijo()
													.isEmpty()) {
												cruce.getEventosHijo().add(
														evento);
											} else {
												if (!cruce.getEventosHijo()
														.contains(evento)) {
													cruce.getEventosHijo().add(
															evento);
												}
											}
										}
									}
								}
							}
						}
						// AÑADIMOS LOS CRUCESMAPA AL CRUCEAGENCIA
						if (!cruce.getDetalleEventos().isEmpty()) {
							// crucesTodos.add(cruce);
							cruceAgencia.setAgencia(agencia);
							cruceAgencia.getCrucesPorAgencia().add(cruce);
							// System.out
							// .println("cruceAgencia size: "
							// + cruceAgencia
							// .getCrucesPorAgencia()
							// .size());
						}

					}

				}
				// AÑADIMOS TODOS LOS CRUCESAGENCIA A UNA LISTA
				if (!cruceAgencia.getCrucesPorAgencia().isEmpty()) {
					crucesPorAgenciaTodos.add(cruceAgencia);
				}
			}
		}

		System.out.println("crucesPorAgencia size: "
				+ crucesPorAgenciaTodos.size());
		for (CruceAgencia cruceAgencia : crucesPorAgenciaTodos) {

			for (CruceMapaDeRiesgo cruce : cruceAgencia.getCrucesPorAgencia()) {
				// System.out.println("Evento(1): "+even.getNombreEven());
				cruce.setPerdida(new BigDecimal(0));
				for (RoEvento evenNivel4 : cruce.getEventosHijo()) {
					// System.out.println("Evento(4): "
					// + evenNivel4.getNombreEven());
					evenNivel4.setNumeroOcurrencias(0);
					evenNivel4.setTotalSeveridad(new BigDecimal(0));
					for (RoDetalleEvento deve : roDetalleEventosTodos) {
						if (deve.getRoEvento().getCodigoEven() == evenNivel4
								.getCodigoEven()
								&& deve.getRoAgencia().getCodigoAgia() == cruceAgencia
										.getAgencia().getCodigoAgia()) {
							evenNivel4
									.setNumeroOcurrencias(evenNivel4
											.getNumeroOcurrencias()
											+ deve.getNumOcur());
							evenNivel4.setTotalSeveridad(new BigDecimal(
									evenNivel4.getTotalSeveridad().floatValue()
											+ deve.getValorDeve()).setScale(2,
									BigDecimal.ROUND_HALF_UP));
						}
					}
					// System.out.println(" total perdida: "
					// + evenNivel4.getTotalSeveridad());
					cruce.setPerdida((new BigDecimal(cruce.getPerdida()
							.floatValue()
							+ evenNivel4.getTotalSeveridad().floatValue()))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
					cruce.setNumeroOcurrencias(cruce.getNumeroOcurrencias()
							+ evenNivel4.getNumeroOcurrencias());
				}
				// System.out.println(cruce.getPerdida());
			}
		}
		
		PrimeFaces.current().scrollTo("formReporteDeEventosDePerdida:pnlCrucesAgencia");
		generarGrafico();

	}

	public void generarGrafico() {
		detallesEventoItemSeleccionado=new ArrayList<RoDetalleEvento>();
		modelBar = new BarChartModel();
		modelBar = llenarGraficoBarras();
		modelBar.setTitle("Pérdida por Evento");
		modelBar.setLegendPosition("ne");
		modelBar.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		modelBar.setZoom(true);
		modelBar.setShowPointLabels(true);
		modelBar.getAxis(AxisType.X).setTickAngle(-15);
		// modelBar.setExtender("extender");

		Axis xAxis = modelBar.getAxis(AxisType.X);
		xAxis.setLabel("Evento");

		Axis yAxis = modelBar.getAxis(AxisType.Y);
		yAxis.setLabel("Pérdida");

		for (ChartSeries serie : modelBar.getSeries()) {
			
			if (serie.getData().entrySet().size()>0) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						"msjGrafico",
						new FacesMessage(
								"Tabla detalles Evento: ",
								"Seleccione un barra del gráfico para mostrar los detalles en la tabla"));
			}
			
		}
		

		// yAxis.setMin(0);
		// yAxis.setMax(200);

	}

	public void defaultGrafico() {
		BarChartModel model = new BarChartModel();
		ChartSeries boys = new ChartSeries();
		boys.setLabel("Perdida (Total: 439)");
		boys.set("Evento1(27%)", 120);
		boys.set("Evento2(22%)", 100);
		boys.set("Evento3(10%)", 44);
		boys.set("Evento4(34%)", 150);
		boys.set("Evento5(5%)", 25);
		// total:439
		model.addSeries(boys);

		modelBar = model;
		modelBar.setTitle("Gráfico de barras (EJEMPLO)");
		modelBar.setLegendPosition("ne");
		modelBar.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		modelBar.setZoom(true);
		modelBar.getAxis(AxisType.X).setTickAngle(-15);

		modelBar.setShowPointLabels(true);
		// modelBar.setExtender("extender");

		Axis xAxis = modelBar.getAxis(AxisType.X);
		xAxis.setLabel("Evento");

		Axis yAxis = modelBar.getAxis(AxisType.Y);
		yAxis.setLabel("Pérdida ");

	}

	private BarChartModel llenarGraficoBarras() {
		BarChartModel model = new BarChartModel();
		model.setShowPointLabels(true);

		// for (RoEvento evento : roEventosFiltro) {
		// if (evento.getNivel() == 1) {
		// ChartSeries serieEvento = new ChartSeries();
		// serieEvento.setLabel(evento.getNombreEven());
		// Float perdida = new Float(0);
		// for (CruceAgencia cruceAgencia : crucesPorAgenciaTodos) {
		// for (CruceMapaDeRiesgo cruceMapa : cruceAgencia
		// .getCrucesPorAgencia()) {
		// // Si el cruce pertenece a ese evento
		// if (evento.getCodigoEven() == cruceMapa.getRoEvento()
		// .getCodigoEven()) {
		// for (RoDetalleEvento deve : cruceMapa
		// .getDetalleEventos()) {
		// perdida += deve.getValorDeve();
		// }
		// serieEvento.set("asd", perdida);
		// }
		// }
		// }
		// if(perdida>0)
		// {
		// model.addSeries(serieEvento);
		// }
		// }
		// }

		// SE USA UNA SOLA SERIE LLAMADA "PERDIDA"
		ChartSeries seriePerdida = new ChartSeries();
		seriePerdida.setLabel("Pérdida");
		BigDecimal total = new BigDecimal(0);
		try {
			for (RoEvento evento : roEventosFiltro) {
				if (evento.getNivel() == 1) {

					for (CruceAgencia cruceAgencia : crucesPorAgenciaTodos) {
						for (CruceMapaDeRiesgo cruceMapa : cruceAgencia
								.getCrucesPorAgencia()) {
							// Si el cruce pertenece a ese evento
							if (evento.getCodigoEven() == cruceMapa
									.getRoEvento().getCodigoEven()) {
								for (RoDetalleEvento deve : cruceMapa
										.getDetalleEventos()) {
									total = new BigDecimal(total.floatValue()
											+ deve.getValorDeve());
								}
							}
						}
					}
				}
			}

			System.out.println("total: " + total.floatValue());
			for (RoEvento evento : roEventosFiltro) {
				if (evento.getNivel() == 1) {

					BigDecimal perdida = new BigDecimal(0);
					for (CruceAgencia cruceAgencia : crucesPorAgenciaTodos) {
						for (CruceMapaDeRiesgo cruceMapa : cruceAgencia
								.getCrucesPorAgencia()) {
							// Si el cruce pertenece a ese evento
							if (evento.getCodigoEven() == cruceMapa
									.getRoEvento().getCodigoEven()) {
								for (RoDetalleEvento deve : cruceMapa
										.getDetalleEventos()) {
									// perdida += deve.getValorDeve();
									perdida = new BigDecimal(
											perdida.floatValue()
													+ deve.getValorDeve());
								}
								// // sobre escribe el valor de perdida en cada
								// evento
								// seriePerdida.set(evento.getNombreEven(),
								// perdida);
							}
						}
					}
					if (total.floatValue() > 0) {
						seriePerdida
								.set(evento.getNombreEven()
										+ " ("
										+ new BigDecimal(perdida.floatValue()
												/ total.floatValue() * 100)
												.setScale(
														0,
														BigDecimal.ROUND_HALF_UP)
										+ "%)", perdida);
					}
				}

			}

			seriePerdida.setLabel(seriePerdida.getLabel()
					+ " (Total: "
					+ new BigDecimal(total.floatValue()).setScale(0,
							BigDecimal.ROUND_HALF_UP) + ")");
			model.addSeries(seriePerdida);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return model;
	}

	@SuppressWarnings("rawtypes")
	public void seleccionarItem(ItemSelectEvent event) {
		detallesEventoItemSeleccionado = new ArrayList<RoDetalleEvento>();
		FacesContext context = FacesContext.getCurrentInstance();

		for (ChartSeries serie : modelBar.getSeries()) {
			int contador = 0;

			for (Map.Entry entry : serie.getData().entrySet()) {
				String nombreEven = "";
				if (contador == event.getItemIndex()) {
					// context.addMessage(null, new FacesMessage("nombre: ",
					// entry
					// .getKey().toString()));
					//
					// context.addMessage(null, new FacesMessage("valor: ",
					// entry
					// .getValue().toString()));

					String[] textos = entry.getKey().toString()
							.split("\\ \\(|\\(");
					nombreEven = textos[0];
					System.out.println("evento: " + nombreEven);

					for (CruceAgencia agencia : crucesPorAgenciaTodos) {
						for (CruceMapaDeRiesgo cruceEvento : agencia
								.getCrucesPorAgencia()) {
							// System.out.println("nombre evento del cruce: "+cruceEvento.getRoEvento().getNombreEven());
							// System.out.println("nombre evento del item: "+nombreEven);
							if (cruceEvento.getRoEvento().getNombreEven()
									.equals(nombreEven)) {
								for (RoDetalleEvento deve : cruceEvento
										.getDetalleEventos()) {
									detallesEventoItemSeleccionado.add(deve);
								}

							}
						}
					}
					context.addMessage(
							"notificacion",
							new FacesMessage(
									"¡Tabla detalles evento actualizada! ",
									"Mostrando "
											+ detallesEventoItemSeleccionado
													.size()
											+ " detalles, pertencientes al evento '"
											+ nombreEven + "'"));
				}

				contador++;
			}

			System.out.println("#detalles eventos todos: "
					+ roDetalleEventosTodos.size());
			
		PrimeFaces.current().scrollTo("formReporteDeEventosDePerdida:dtlDetalles");
			
			// Otra forma de iterar (con un Iterator)
			// Iterator it = serie.getData().entrySet().iterator();
			// contador = 0;
			// while (it.hasNext()) {
			//
			// Map.Entry pair = (Map.Entry) it.next();
			// pair.getValue();
			//
			// if (contador == event.getItemIndex()) {
			// context.addMessage(null,
			// new FacesMessage("key: " + pair.getKey()));
			// context.addMessage(null,
			// new FacesMessage("valor: " + pair.getValue()));
			// }
			// contador++;
			// }

		}
	}

	// private void imprimir() {
	// for (RoEvento even : eventosTodos) {
	// if (even.getNivel() == 1) {
	//
	// System.out.println("- " + even.getNombreEven());
	// String detalles;
	// detalles = "";
	// System.out.println(" " + even.getDetallesEvento().size()
	// + " detalles");
	// for (RoDetalleEvento deve : even.getDetallesEvento()) {
	// detalles.concat(deve.getCodigoDeve() + ", ");
	// }
	// System.out.println(detalles);
	// }
	// }
	// }

	private RoEvento buscarPadre(RoEvento evento) {

		RoEvento padre = new RoEvento();

		for (RoEvento even : eventosTodos) {
			if (even.getNombreEven().equals(evento.getAncestroEven())) {
				// padre.setNivel(even.getNivel());
				padre = even;
			}
		}
		if (padre.getNivel() == 1) {
			// System.out.println("su padre es: "+padre.getNombreEven());
			return padre;
		} else {
			padre = servicioRoEvento.buscarEventoPorNombre(evento
					.getAncestroEven());
			return buscarPadre(padre);
		}
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

	public RoEvento getEventoAux() {
		return eventoAux;
	}

	public void setEventoAux(RoEvento eventoAux) {
		this.eventoAux = eventoAux;
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

	public RoDetalleEvento getRoDetalleEventoVista() {
		return roDetalleEventoVista;
	}

	public void setRoDetalleEventoVista(RoDetalleEvento roDetalleEventoVista) {
		this.roDetalleEventoVista = roDetalleEventoVista;
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

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public List<String> getTipoRegistroFiltros() {
		return tipoRegistroFiltros;
	}

	public void setTipoRegistroFiltros(List<String> tipoRegistroFiltros) {
		this.tipoRegistroFiltros = tipoRegistroFiltros;
	}

	public List<CruceMapaDeRiesgo> getCrucesTodos() {
		return crucesTodos;
	}

	public void setCrucesTodos(List<CruceMapaDeRiesgo> crucesTodos) {
		this.crucesTodos = crucesTodos;
	}

	public List<CruceAgencia> getCrucesPorAgenciaTodos() {
		return crucesPorAgenciaTodos;
	}

	public void setCrucesPorAgenciaTodos(
			List<CruceAgencia> crucesPorAgenciaTodos) {
		this.crucesPorAgenciaTodos = crucesPorAgenciaTodos;
	}

	public List<RoEvento> getRoEventosFiltro() {
		return roEventosFiltro;
	}

	public void setRoEventosFiltro(List<RoEvento> roEventosFiltro) {
		this.roEventosFiltro = roEventosFiltro;
	}

	public BarChartModel getModelBar() {
		return modelBar;
	}

	public void setModelBar(BarChartModel modelBar) {
		this.modelBar = modelBar;
	}

	public List<RoDetalleEvento> getDetallesEventoItemSeleccionado() {
		return detallesEventoItemSeleccionado;
	}

	public void setDetallesEventoItemSeleccionado(
			List<RoDetalleEvento> detallesEventoItemSeleccionado) {
		this.detallesEventoItemSeleccionado = detallesEventoItemSeleccionado;
	}

}
