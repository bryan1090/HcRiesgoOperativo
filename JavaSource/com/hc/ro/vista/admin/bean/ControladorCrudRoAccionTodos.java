package com.hc.ro.vista.admin.bean;

import com.hc.ro.commons.ManejoFTP;
import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoAccion;
import com.hc.ro.modelo.RoArchivoAccion;
import com.hc.ro.modelo.RoArchivoDetalleAccion;
import com.hc.ro.modelo.RoCumplimiento;
import com.hc.ro.modelo.RoDetalleAccion;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.SisParametro;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioRoAccion;
import com.hc.ro.negocio.ServicioRoArchivoAccion;
import com.hc.ro.negocio.ServicioRoArchivoDetalleAccion;
import com.hc.ro.negocio.ServicioRoCumplimiento;
import com.hc.ro.negocio.ServicioRoDetalleAccion;
import com.hc.ro.negocio.ServicioRoDetalleEvento;
import com.hc.ro.negocio.ServicioRoEvento;
import com.hc.ro.negocio.ServicioRoNegocio;
import com.hc.ro.negocio.ServicioRoResponsable;
import com.hc.ro.negocio.ServicioSisParametro;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.extensions.model.timeline.TimelineEvent;
import org.primefaces.extensions.model.timeline.TimelineGroup;
import org.primefaces.extensions.model.timeline.TimelineModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class ControladorCrudRoAccionTodos {
	@EJB
	ServicioRoDetalleEvento servicioRoDetalleEvento;
	@EJB
	ServicioRoEvento servicioRoEvento;
	@EJB
	ServicioRoNegocio servicioRoNegocio;
	@EJB
	ServicioRoResponsable servicioRoResponsable;
	@EJB
	ServicioRoAccion servicioRoAccion;
	@EJB
	ServicioRoCumplimiento servicioRoCumplimiento;
	@EJB
	ServicioRoDetalleAccion servicioRoDetalleAccion;
	@EJB
	ServicioRoArchivoAccion servicioRoArchivoAccion;
	@EJB
	ServicioRoArchivoDetalleAccion servicioRoArchivoDetalleAccion;
	@EJB
	ServicioSisParametro servicioSisParametro;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	private SisUsuario sisUsuario;
	public static final String nombrePagina = "/paginas/CrudAccionTodos.jsf";
	private List<RoDetalleEvento> detalleEventoSeleccionadoLista;
	private List<RoResponsable> roResponsablesTodos;
	private List<RoAccion> accionesTodos;
	private List<RoCumplimiento> cumplimientosTodos;
	private List<RoDetalleAccion> roDetalleAccionTodos;
	private List<RoArchivoAccion> archivosPlanDeAccion;
	private List<RoArchivoDetalleAccion> archivosPlanDetalleAccion;
	private List<String> padresEvento;
	private List<String> padresNegocio;
	private String path;
	private String extenFile;
	private boolean pnlAccion;
	private boolean pnlEditarDetalleAccion;
	private boolean mostrarPnlDetalleAccion;
	private boolean pnlEditarArchivos;
	private boolean pnlEditarDetalleArchivos;
	private boolean btnAnadirDetalleAccion;
	private boolean btnAnadirAccion;
	private boolean disableResponsable;
	private String negocioNivel1;
	private String negocioNivel2;
	private String eventoNivel1;
	private String eventoNivel2;
	private RoDetalleEvento roDetalleEventoVista;
	private RoDetalleEvento roDetalleEventoControlador;
	private RoAccion roAccionVista;
	private RoAccion roAccionControlador;
	private RoDetalleAccion roDetalleAccionVista;
	private RoArchivoAccion roArchivoAccionVista;
	private RoArchivoDetalleAccion roArchivoDetalleAccionVista;
	private int idResponsableSeleccionado;
	private int idResponsableEjecucionSeleccionado;
	private int idSupervisorEjecucionSeleccionado;
	private int idCumplimiento;
	private int numPlanesAccion;
	private boolean tipoGurdar;
	private boolean tipoGuardarDetalle;
	private boolean tipoGuardarArchivoAccion;
	private boolean actualizado;
	private boolean actualizadoArchivoAccion;
	private boolean pnlContador;
	private ManejoFTP manejoFTP;
	private UploadedFile archivoRequerimiento;
	private File archivoTemporal;
	private StreamedContent fileDescarga;
	private File archivoTemporalDescarga;
	private boolean actualizadoArchivoDetalleAccion;
	private boolean tipoGuardarArchivoDetalleAccion;
	private Date fechaInicio;
	private Date fechaFin;
	private TimelineModel model;
	private TimelineModel modelS;
	private TimelineModel modelR;

	public ControladorCrudRoAccionTodos() {
		this.sisUsuario = new SisUsuario();
		this.detalleEventoSeleccionadoLista = new ArrayList<RoDetalleEvento>();
		this.padresEvento = new ArrayList<String>();
		this.padresNegocio = new ArrayList<String>();
		this.roResponsablesTodos = new ArrayList<RoResponsable>();
		this.roDetalleEventoVista = new RoDetalleEvento();
		this.accionesTodos = new ArrayList<RoAccion>();
		this.roArchivoDetalleAccionVista = new RoArchivoDetalleAccion();
		this.manejoFTP = new ManejoFTP();
		this.cumplimientosTodos = new ArrayList<RoCumplimiento>();
		fechaFin = new Date();
		fechaInicio = new Date();
		model = new TimelineModel();
		modelR = new TimelineModel();
		modelS = new TimelineModel();
		
		this.numPlanesAccion=0;
		pnlContador=false;
//		System.out.println("El código de usuario es: " + this.dataManagerSesion.getUsuarioSesion().getCodigoUsua());
	}
	

	@PostConstruct
	public void PostControladorCrudRoAccion() {
//		System.out.println("El código de usuario es: " + this.dataManagerSesion.getUsuarioSesion().getCodigoUsua());
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
		servicioSisParametro.verificarParametrosSistema();

		manejoFTP = new ManejoFTP(servicioSisParametro.buscarPorNombre("ipFTP")
				.getValorPara(), servicioSisParametro
				.buscarPorNombre("userFTP").getValorPara(),
				servicioSisParametro.buscarPorNombre("passFTP").getValorPara());
		try {
			sisUsuario = dataManagerSesion.getUsuarioSesion();
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.pnlAccion = false;
		this.mostrarPnlDetalleAccion = false;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = true;
		this.btnAnadirDetalleAccion = true;
		this.path = "sinArchivo";
		try {
//			if (sisUsuario.getPermisoSuDeve() >= 1.0) {
//				this.detalleEventoSeleccionadoLista = servicioRoDetalleEvento.buscarTodosAux();
//				
//			}else{
				this.detalleEventoSeleccionadoLista = this.servicioRoDetalleEvento
						.buscarEventosPlanDeAccion(this.dataManagerSesion
								.getUsuarioSesion().getCodigoUsua());
//				this.detalleEventoSeleccionadoLista = this.servicioRoDetalleEvento
//						.buscarEventosPlanDeAccion(sisUsuario);
//			}
			
		} catch (Exception localException3) {
		}
		servicioSisParametro.verificarParametrosSistema();		
	}

	public void generarModelo() {
		model = new TimelineModel();
		String contextPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestContextPath();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		for (RoAccion item : accionesTodos) {
			cal.set(item.getFechaInicioAcci().getYear() + 1900, item
					.getFechaInicioAcci().getMonth(), item.getFechaInicioAcci()
					.getDate());
			Date startReport = cal.getTime();
			cal.set(item.getFechaFinAcci().getYear() + 1900, item
					.getFechaFinAcci().getMonth(), item.getFechaFinAcci()
					.getDate());
			Date endReport = cal.getTime();
			model.add(new TimelineEvent(item.getNombreAcci(), startReport,
					endReport, false, null, "readonly"));
		}
	}

	public void agruparPlanesPorSupervisor() {
		// create timeline model
		modelS = new TimelineModel();

		List<RoResponsable> responsablesAux = new ArrayList<RoResponsable>();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		for (RoDetalleAccion roDeac : roDetalleAccionTodos) {
			boolean interruptor = true;
			for (RoResponsable roResponsable : responsablesAux) {
				if (roDeac.getRoResponsableSupervisor().getCodigoResp() == roResponsable
						.getCodigoResp()) {
					interruptor = false;
					break;
				}
			}
			if (interruptor) {
				responsablesAux.add(roDeac.getRoResponsableSupervisor());
				TimelineGroup group = new TimelineGroup(Integer.toString(roDeac
						.getRoResponsableSupervisor().getCodigoResp()),
						roDeac.getRoResponsableSupervisor());
				modelS.addGroup(group);
			}
			cal.set(roDeac.getFechaInicioDeac().getYear() + 1900, roDeac
					.getFechaInicioDeac().getMonth(), roDeac
					.getFechaInicioDeac().getDate());
			Date startReport = cal.getTime();
			cal.set(roDeac.getFechaFinDeac().getYear() + 1900, roDeac
					.getFechaFinDeac().getMonth(), roDeac.getFechaFinDeac()
					.getDate());
			Date endReport = cal.getTime();
			modelS.add(new TimelineEvent(roDeac.getNombreDeac(), startReport,
					endReport, true, Integer.toString(roDeac
							.getRoResponsableSupervisor().getCodigoResp())));

		}
	}

	public void agruparPlanesPorResponsable() {
		// create timeline model
		modelR = new TimelineModel();

		List<RoResponsable> responsablesAux = new ArrayList<RoResponsable>();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		for (RoDetalleAccion roDeac : roDetalleAccionTodos) {
			boolean interruptor = true;
			for (RoResponsable roResponsable : responsablesAux) {
				if (roDeac.getRoResponsable().getCodigoResp() == roResponsable
						.getCodigoResp()) {
					interruptor = false;
					break;
				}
			}
			if (interruptor) {
				responsablesAux.add(roDeac.getRoResponsable());
				TimelineGroup group = new TimelineGroup(Integer.toString(roDeac
						.getRoResponsable().getCodigoResp()),
						roDeac.getRoResponsable());
				modelR.addGroup(group);
			}
			cal.set(roDeac.getFechaInicioDeac().getYear() + 1900, roDeac
					.getFechaInicioDeac().getMonth(), roDeac
					.getFechaInicioDeac().getDate());
			Date startReport = cal.getTime();
			cal.set(roDeac.getFechaFinDeac().getYear() + 1900, roDeac
					.getFechaFinDeac().getMonth(), roDeac.getFechaFinDeac()
					.getDate());
			Date endReport = cal.getTime();
			modelR.add(new TimelineEvent(roDeac.getNombreDeac(), startReport,
					endReport, true, Integer.toString(roDeac.getRoResponsable()
							.getCodigoResp())));

		}
	}

	public void seleccionarDetalleEvento() {
		this.roDetalleEventoVista = this.roDetalleEventoControlador;
		this.pnlAccion = false;
		this.mostrarPnlDetalleAccion = false;
		this.pnlEditarDetalleAccion = false;
		this.btnAnadirAccion = false;
		this.btnAnadirDetalleAccion = true;
		this.disableResponsable = false;
		this.roResponsablesTodos = this.servicioRoResponsable.buscarTodos();
		this.path = "sinArchivo";
		this.accionesTodos = new ArrayList<RoAccion>();
		
		//pnl contador
		numPlanesAccion= (int) servicioRoAccion.buscarRoAccionPorCodigoEvento(roDetalleEventoVista.getCodigoDeve());
		pnlContador=true;
		FacesContext context=FacesContext.getCurrentInstance();
		
			context.addMessage("mensaje", new FacesMessage(FacesMessage.SEVERITY_INFO, "Número de Planes de Acción", "= "+numPlanesAccion));
		
	}
	


	public void nuevoAccion() {
		this.tipoGurdar = true;
		this.pnlAccion = true;
		this.mostrarPnlDetalleAccion = false;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = true;
		this.btnAnadirDetalleAccion = true;

		this.roAccionVista = new RoAccion();

		this.padresEvento = new ArrayList<String>();
		obtenerPadresEvento(this.roDetalleEventoVista.getRoEvento());
		if (this.padresEvento.size() >= 2) {
			this.eventoNivel1 = ((String) this.padresEvento
					.get(this.padresEvento.size() - 1));
			this.eventoNivel2 = ((String) this.padresEvento
					.get(this.padresEvento.size() - 2));
		} else if (this.padresEvento.size() == 1) {
			this.eventoNivel1 = ((String) this.padresEvento
					.get(this.padresEvento.size() - 1));
		}
		this.padresNegocio = new ArrayList<String>();
		obtenerPadresNegocio(((RoDetalleEvento) this.detalleEventoSeleccionadoLista
				.get(0)).getRoNegocio1());
		if (this.padresNegocio.size() >= 2) {
			this.negocioNivel1 = ((String) this.padresNegocio
					.get(this.padresNegocio.size() - 1));
			try {
				this.negocioNivel2 = ((String) this.padresNegocio
						.get(this.padresNegocio.size() - 2));
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else if (this.padresNegocio.size() == 1) {
			this.negocioNivel1 = ((String) this.padresNegocio
					.get(this.padresNegocio.size() - 1));
		}
		this.roAccionVista.setRoDetalleEvento(this.roDetalleEventoVista);
		this.roAccionVista.setFechaRegistroAcci(new Date());
		this.roAccionVista.setFechaFinAcci(new Date());
		this.roAccionVista.setFechaInicioAcci(new Date());
		this.roAccionVista.setMontoPerdida(new BigDecimal(Float
				.toString(this.roDetalleEventoVista.getValorDeve())).setScale(
				2, 4));
		this.roAccionVista.setRiesgoResidual(new BigDecimal(Float
				.toString(this.roDetalleEventoVista.getPerdidaResidualDeve()))
				.setScale(2, 4));
		this.roAccionVista.setNumeroOcurrenciasAcci(0);
		this.roAccionVista.setRiesgoInherente(new BigDecimal(Float
				.toString(this.roDetalleEventoVista.getValorDeve())).setScale(
				2, 4));
		if (this.roDetalleEventoVista.getTipoCalifDeve().equals("Cualitativo")) {
			try {
				this.roAccionVista.setProbaInherente(this.roDetalleEventoVista
						.getParamProbabilidadRiesgoAntes().getNombrePprr());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.roAccionVista.setConsecInherente(this.roDetalleEventoVista
						.getParamConsecuenciaImpactoAntes().getNombrePconi());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.roAccionVista
						.setNumProbaInherente(this.roDetalleEventoVista
								.getParamProbabilidadRiesgoAntes()
								.getNombrePprr());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.roAccionVista.setNumConsecInherente(Integer
						.toString(this.roDetalleEventoVista
								.getParamConsecuenciaImpactoAntes()
								.getNumeroPconi()));
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.roAccionVista.setProbaResidual(this.roDetalleEventoVista
						.getParamProbabilidadRiesgoDespues().getNombrePprr());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.roAccionVista.setConsecResidual(this.roDetalleEventoVista
						.getParamConsecuenciaImpactoDespues().getNombrePconi());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.roAccionVista
						.setNumProbaResidual(this.roDetalleEventoVista
								.getParamProbabilidadRiesgoDespues()
								.getLetraPprr());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				this.roAccionVista.setNumConsecResidual(Integer
						.toString(this.roDetalleEventoVista
								.getParamConsecuenciaImpactoDespues()
								.getNumeroPconi()));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	public void obtenerPadresEvento(RoEvento evento) {
		try {
			if (evento.getAncestroEven().equals("Eventos")) {
				this.padresEvento.add(evento.getNombreEven());
			} else {
				this.padresEvento.add(evento.getNombreEven());
				obtenerPadresEvento(this.servicioRoEvento
						.buscarEventoPorNombre(evento.getAncestroEven()));
			}
		} catch (Exception localException) {
		}
	}

	public void obtenerPadresNegocio(RoNegocio negocio) {
		try {
			if (negocio.getAncestroNego().equals("Negocios")) {
				this.padresNegocio.add(negocio.getNombreNego());
			} else {
				this.padresNegocio.add(negocio.getNombreNego());
				obtenerPadresNegocio(this.servicioRoNegocio
						.buscarNegocioPorNombre(negocio.getAncestroNego()));
			}
		} catch (Exception localException) {
		}
	}

	public void guardarAccion() {
		if (this.tipoGurdar) {
			try {
				this.roAccionVista
						.setRoResponsable((RoResponsable) this.servicioRoResponsable.buscarPorId(Integer
								.valueOf(this.idResponsableSeleccionado)));
			} catch (Exception localException) {
			}
			if (this.servicioRoAccion.existeAccionPorNombre(this.roAccionVista
					.getNombreAcci(),
					(RoDetalleEvento) this.detalleEventoSeleccionadoLista
							.get(0))) {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage("growl1", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre del Plan de Acción no se debe repetir"));
			} else {
				this.servicioRoAccion.insertar(this.roAccionVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage("growl1", new FacesMessage(
						"Plan de Acción Añadido",
						"El Plan de Acción ha sido Añadido con Exito"));
				finalizarGuardado();
			}
		} else if (this.servicioRoAccion.existeAccionPorNombreEx(
				this.roAccionVista.getNombreAcci(),
				this.roAccionVista.getCodigoAcci(),
				(RoDetalleEvento) this.detalleEventoSeleccionadoLista.get(0))) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage("growl1", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al guardar:",
					"El nombre del plan de acción no se debe repetir"));
		} else {
			this.servicioRoAccion.actualizar(this.roAccionVista);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage("growl1", new FacesMessage("Plan de Acción Añadido",
					"El Plan de Acción ha sido Actualizado con Exito"));
			finalizarGuardado();
		}
	}

	public void finalizarGuardado() {
		this.accionesTodos = this.servicioRoAccion
				.buscarRoAccionPorEventoResponsableFecha(
						this.roDetalleEventoVista.getCodigoDeve(),
						this.dataManagerSesion.getUsuarioSesion()
								.getCodigoUsua(), fechaInicio, fechaFin);
		System.out.println("El código de usuario es: " + this.dataManagerSesion.getUsuarioSesion().getCodigoUsua());
		cargarObservacionesAccion();
		generarModelo();
		cancelar();
	}

	public void cancelar() {
		this.roAccionVista = new RoAccion();

		this.pnlAccion = false;
		this.mostrarPnlDetalleAccion = false;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = false;
		this.btnAnadirDetalleAccion = true;
		cargarObservacionesAccion();
	}

	public void seleccionar() {
		this.roAccionVista = this.roAccionControlador;

		this.pnlAccion = false;
		this.mostrarPnlDetalleAccion = true;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = false;
		this.btnAnadirDetalleAccion = false;
		this.roDetalleAccionTodos = this.servicioRoDetalleAccion
				.buscarRoDetalleAccionPorAccion(this.roAccionVista
						.getCodigoAcci());
		
		
		for(int i=0; i<this.roDetalleAccionTodos.size(); i++){
			System.out.println(this.roDetalleAccionTodos.get(i).getRoAccion().getCodigoAcci());
		}
		
		cargarObservacionesDetalleAccion();
		agruparPlanesPorResponsable();
		agruparPlanesPorSupervisor();
	}

	public void editarAccionVista() {
		this.pnlAccion = false;
		this.mostrarPnlDetalleAccion = false;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = false;
		this.btnAnadirDetalleAccion = true;
		this.pnlAccion = false;
		this.mostrarPnlDetalleAccion = true;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = false;
		this.btnAnadirDetalleAccion = false;
		this.roDetalleAccionTodos = this.servicioRoDetalleAccion
				.buscarRoDetalleAccionPorAccion(this.roAccionVista
						.getCodigoAcci());

		this.tipoGurdar = false;

		this.pnlAccion = true;
		this.mostrarPnlDetalleAccion = false;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = true;
		this.btnAnadirDetalleAccion = true;

		this.padresEvento = new ArrayList<String>();
		obtenerPadresEvento(this.roDetalleEventoVista.getRoEvento());

		if (this.padresEvento.size() >= 2) {
			this.eventoNivel1 = ((String) this.padresEvento
					.get(this.padresEvento.size() - 1));
			this.eventoNivel2 = ((String) this.padresEvento
					.get(this.padresEvento.size() - 2));
		} else if (this.padresEvento.size() == 1) {
			this.eventoNivel1 = ((String) this.padresEvento
					.get(this.padresEvento.size() - 1));
		}
		this.padresNegocio = new ArrayList<String>();
		obtenerPadresNegocio(((RoDetalleEvento) this.detalleEventoSeleccionadoLista
				.get(0)).getRoNegocio1());
		if (this.padresNegocio.size() >= 2) {
			this.negocioNivel1 = ((String) this.padresNegocio
					.get(this.padresNegocio.size() - 1));
			this.negocioNivel2 = ((String) this.padresNegocio
					.get(this.padresNegocio.size() - 2));
		} else if (this.padresNegocio.size() == 1) {
			this.negocioNivel1 = ((String) this.padresNegocio
					.get(this.padresNegocio.size() - 1));
		}
		try {
			this.idResponsableSeleccionado = this.roAccionVista
					.getRoResponsable().getCodigoResp();
		} catch (Exception localException) {
		}
		cargarObservacionesAccion();
	}

	public void mostrarArchivosAccionVista() {
		this.archivosPlanDeAccion = this.servicioRoArchivoAccion
				.buscarPorIdAccion(this.roAccionVista.getCodigoAcci());
	}

	public void mostrarArchivosDetalleAccionVista() {
		this.archivosPlanDetalleAccion = this.servicioRoArchivoDetalleAccion
				.buscarPorIdDetalleAccion(this.roDetalleAccionVista
						.getCodigoDeac());
	}

	public void eliminarAccion() {
		try {
			this.servicioRoAccion.eliminar(this.roAccionVista);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage("growl1", new FacesMessage(
					"Plan de Acción Eliminado",
					"El Plan de Acción ha sido Eliminado con Exito"));
			cancelarDetalleAccion();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					"growl1",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error",
							"Se ha producido un error al eliminar, el Plan de Acción contiene archivos y/o actividades, por esta razón no se puede eliminar, si el error persiste consulte con el proveedor"));
		}
	}

	public void cargarObservacionesAccion() {
		String obsAux = "";
		for (RoAccion varAccion : this.accionesTodos) {
			obsAux = "";
			try {
				if (this.servicioRoArchivoAccion.buscarPorIdAccion(
						varAccion.getCodigoAcci()).size() > 0) {
					obsAux = obsAux.concat("- Tiene Archivos Cargados\n");
				}
			} catch (Exception localException) {
			}
			if (varAccion.getFechaFinAcci().before(new Date())) {
				obsAux = obsAux.concat("- Caducado\n");
			} else {
				obsAux = obsAux.concat("- Vigente\n");
			}
			Iterator<RoDetalleAccion> localIterator2 = this.servicioRoDetalleAccion
					.buscarRoDetalleAccionPorAccion(varAccion.getCodigoAcci())
					.iterator();
			while (localIterator2.hasNext()) {
				RoDetalleAccion varActividad = (RoDetalleAccion) localIterator2
						.next();
				try {
					if (this.servicioRoArchivoDetalleAccion
							.buscarPorIdDetalleAccion(
									varActividad.getCodigoDeac()).size() > 0) {
						obsAux = obsAux
								.concat("- Tiene Actividades con archivos cargados\n");
					}
				} catch (Exception localException1) {
				}
			}
			localIterator2 = this.servicioRoDetalleAccion
					.buscarRoDetalleAccionPorAccion(varAccion.getCodigoAcci())
					.iterator();
			while (localIterator2.hasNext()) {
				RoDetalleAccion varActividad = (RoDetalleAccion) localIterator2
						.next();
				if (varActividad.getFechaFinDeac().before(new Date())) {
					obsAux = obsAux.concat("- Tiene Actividades caducadas\n");
					break;
				}
			}
			varAccion.setObservaciones(obsAux);
		}
	}

	public void cargarObservacionesDetalleAccion() {
		String obsAux = "";
		for (RoDetalleAccion varActividad : this.roDetalleAccionTodos) {
			obsAux = "";
			try {
				if (this.servicioRoArchivoDetalleAccion
						.buscarPorIdDetalleAccion(varActividad.getCodigoDeac())
						.size() > 0) {
					obsAux = obsAux.concat("- Tiene Archivos Cargados\n");
				}
			} catch (Exception localException) {
			}
			if (varActividad.getFechaFinDeac().before(new Date())) {
				obsAux = obsAux.concat("- Caducado\n");
			}
			varActividad.setObservaciones(obsAux);
		}
	}

	public void nuevoDetalleAccion() {
		this.cumplimientosTodos = this.servicioRoCumplimiento.buscarTodos();

		this.pnlEditarDetalleAccion = true;
		this.roDetalleAccionVista = new RoDetalleAccion();
		this.roDetalleAccionVista.setFechaFinDeac(new Date());
		this.roDetalleAccionVista.setFechaInicioDeac(new Date());
	}

	public void guardarDetalleAccion() {
		this.roDetalleAccionVista.setRoAccion(this.roAccionVista);
		this.roDetalleAccionVista
				.setRoCumplimiento((RoCumplimiento) this.servicioRoCumplimiento
						.buscarPorId(Integer.valueOf(this.idCumplimiento)));
		this.roDetalleAccionVista
				.setRoResponsable((RoResponsable) this.servicioRoResponsable.buscarPorId(Integer
						.valueOf(this.idResponsableEjecucionSeleccionado)));
		this.roDetalleAccionVista
				.setRoResponsableSupervisor((RoResponsable) this.servicioRoResponsable.buscarPorId(Integer
						.valueOf(this.idSupervisorEjecucionSeleccionado)));
		if (this.tipoGuardarDetalle) {
			this.servicioRoDetalleAccion.insertar(this.roDetalleAccionVista);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage("growl1", new FacesMessage(
					"Detalle de Plan de Acción Añadido",
					"El Detalle de Plan de Acción ha sido Añadido con Exito"));
			finalizarGuardado();
			finalizarGuardadoDetalle();
		} else {
			this.servicioRoDetalleAccion.actualizar(this.roDetalleAccionVista);
			finalizarGuardadoDetalle();
		}
	}

	public void cancelarDetalleAccion() {
		this.roDetalleAccionVista = new RoDetalleAccion();

		this.pnlAccion = false;
		this.mostrarPnlDetalleAccion = true;
		this.pnlEditarDetalleAccion = false;

		this.btnAnadirAccion = false;
		this.btnAnadirDetalleAccion = false;
		this.roDetalleAccionTodos = this.servicioRoDetalleAccion
				.buscarRoDetalleAccionPorAccion(this.roAccionVista
						.getCodigoAcci());
		cargarObservacionesDetalleAccion();
		cargarObservacionesAccion();
	}

	public void editarDetalleAccion() {
		
		try {
			System.out.println(this.roAccionVista.getCodigoAcci());
		} catch (NullPointerException e) {
			System.out.println(e);
		}
		
		this.cumplimientosTodos = this.servicioRoCumplimiento.buscarTodos();
		this.btnAnadirDetalleAccion = true;
		this.pnlEditarDetalleAccion = true;
		
		try {
			this.idResponsableEjecucionSeleccionado = this.roDetalleAccionVista
					.getRoResponsable().getCodigoResp();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					"growl1",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"No existe un responsable de ejecución para este detalle de plan de acción"));
		}
		try {
			this.idSupervisorEjecucionSeleccionado = this.roDetalleAccionVista
					.getRoResponsableSupervisor().getCodigoResp();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					"growl1",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"No existe un supervisor para este detalle de plan de acción"));
		}
		try {
			this.idCumplimiento = this.roDetalleAccionVista.getRoCumplimiento()
					.getCodigoCump();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("growl1", new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Alerta",
					"No se ha escogido un Nivel de Cumplimiento"));
		}
	}

	public void eliminarDetalleAccion() {
		try {
			this.servicioRoDetalleAccion.eliminar(this.roDetalleAccionVista);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage("growl1", new FacesMessage(
					"Detalle de Plan de Acción Eliminado",
					"El Detalle de Plan de Acción ha sido Eliminado con Exito"));
			cancelarDetalleAccion();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					"growl1",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error",
							"Se ha producido un error al eliminar, la actividad contiene archivos por esta razón no se puede eliminar, si el error persiste consulte con el proveedor"));
		}
	}

	public void finalizarGuardadoDetalle() {
		this.roDetalleAccionTodos = this.servicioRoDetalleAccion
				.buscarRoDetalleAccionPorAccion(this.roAccionVista
						.getCodigoAcci());

		cancelarDetalleAccion();
	}

	public void seleccionarParaEditarArchivoAccion() {
		this.actualizadoArchivoAccion = false;
		this.tipoGuardarArchivoAccion = false;
		this.pnlEditarArchivos = true;
	}

	public void descargar() {
		try {
			this.manejoFTP.conectarServidorFtp();
			this.archivoTemporalDescarga = this.manejoFTP.descargarArchivo(
					this.roArchivoAccionVista.getPathArac(),
					this.roArchivoAccionVista.getPathArac());
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ roArchivoAccionVista.getNombreArac()
							+ "."
							+ FilenameUtils
									.getExtension(archivoTemporalDescarga
											.getAbsolutePath()));
			response.setContentLength((int) archivoTemporalDescarga.length());
			ServletOutputStream out = null;
			try {
				FileInputStream input = new FileInputStream(
						archivoTemporalDescarga);
				byte[] buffer = new byte[1024];
				out = response.getOutputStream();
				int i = 0;
				while ((i = input.read(buffer)) != -1) {
					out.write(buffer);
					out.flush();
				}
				FacesContext.getCurrentInstance().getResponseComplete();
			} catch (IOException err) {
				err.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Archivo Descargado",
					"El archivo se ha descargado exitosamente");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} catch (Exception e) {
			FacesMessage mensaje = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error al Descargar",
					"No existe un archivo para este requerimiento o la conexión con el servidor ha fallado");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
			e.printStackTrace();

			this.roArchivoAccionVista = new RoArchivoAccion();
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			this.roArchivoAccionVista = new RoArchivoAccion();
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void editarArchivoAccion() {
		if (this.actualizadoArchivoAccion) {
			if (!this.roArchivoAccionVista.getPathArac().equals("sinArchivo")) {
				try {
					this.manejoFTP.conectarServidorFtp();
					this.manejoFTP.eliminarArchivo(this.roArchivoAccionVista
							.getPathArac());
					this.manejoFTP.desconectarServidorFtp();
					guardarArchivoFileAccion();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				guardarArchivoFileAccion();
			}
		}
		try {
			String nuevoPath = new String();
			this.manejoFTP.conectarServidorFtp();
			StringTokenizer tokens = new StringTokenizer(
					this.roArchivoAccionVista.getPathArac(), ".");
			tokens.nextToken();
			try {
				nuevoPath = "archivos/"
						+ this.roArchivoAccionVista.getNombreArac() + "."
						+ tokens.nextToken();
			} catch (Exception localException1) {
			}
			try {
				this.manejoFTP.renombrarArchivoDirectorio(
						this.roArchivoAccionVista.getPathArac(), nuevoPath);
				this.roArchivoAccionVista.setPathArac(nuevoPath);
			} catch (Exception localException2) {
			}
			this.servicioRoArchivoAccion.actualizar(this.roArchivoAccionVista);
			cancelarArchivoAccion();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Información Actualizada", "El recurso ha sido actualizado");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} catch (Exception localException3) {
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void eliminarArchivoAccion() {
		try {
			this.servicioRoArchivoAccion.eliminar(this.roArchivoAccionVista);
			this.manejoFTP.conectarServidorFtp();
			this.manejoFTP.eliminarArchivo(this.roArchivoAccionVista
					.getPathArac());
			cancelarArchivoAccion();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Archivo Eliminado",
					"El archivo ha sido eliminado exitosamente");
			cargarObservacionesDetalleAccion();
			cargarObservacionesAccion();
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					"growl1",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error",
							"Se ha producido un error al eliminar, si el error persiste consulte con el proveedor"));

			cancelarArchivoAccion();
			e.printStackTrace();
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void prepararArchivo(FileUploadEvent event) {
		this.archivoRequerimiento = event.getFile();
		this.actualizadoArchivoAccion = true;
		FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Archivo Cargado", "El archivo "
						+ event.getFile().getFileName()
						+ " se ha cargado exitosamente");
		FacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void agregarRequerimiento() {
	}

	public void guardarArchivoFileAccion() {
		try {
			StringTokenizer tokens = new StringTokenizer(
					this.archivoRequerimiento.getFileName(), ".");
			tokens.nextToken();
			this.extenFile = tokens.nextToken();
			this.archivoTemporal = File.createTempFile("ArchivoAccionTemp", "."
					+ this.extenFile);
			BufferedInputStream bufferedInput = new BufferedInputStream(
					this.archivoRequerimiento.getInputstream());
			FileOutputStream fileOutput = new FileOutputStream(
					this.archivoTemporal);
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(
					fileOutput);

			byte[] array = new byte[1024];
			int leidos = bufferedInput.read(array);
			while (leidos > 0) {
				bufferedOutput.write(array, 0, leidos);
				leidos = bufferedInput.read(array);
			}
			bufferedInput.close();
			bufferedOutput.flush();
			bufferedOutput.close();

			this.manejoFTP.conectarServidorFtp();
			this.manejoFTP.cargarRequerimiento(this.archivoTemporal);
			this.path = ("/archivos/"
					+ this.roArchivoAccionVista.getNombreArac() + "." + this.extenFile);
			this.manejoFTP.renombrarArchivoDirectorio("/archivos/"
					+ this.archivoTemporal.getName(), this.path);
			this.manejoFTP.desconectarServidorFtp();
			this.roArchivoAccionVista.setPathArac(this.path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarArchivoAccion() {
		if (this.tipoGuardarArchivoAccion) {
			this.roArchivoAccionVista.setRoAccion(this.roAccionVista);
			if (this.actualizadoArchivoAccion) {
				guardarArchivoFileAccion();
			} else {
				this.roArchivoAccionVista.setPathArac(this.path);
			}
			try {
				this.servicioRoArchivoAccion
						.insertar(this.roArchivoAccionVista);
				cancelarArchivoAccion();
				FacesMessage mensaje = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Información Ingresada",
						"El recurso ha sido registrado");
				cargarObservacionesAccion();
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
			} catch (Exception e) {
				FacesMessage mensaje = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Información NO Ingresada",
						"El recurso no ha sido registrado");
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
				e.printStackTrace();
			}
		} else {
			editarArchivoAccion();
		}
	}

	public void cancelarArchivoAccion() {
		this.archivosPlanDeAccion = this.servicioRoArchivoAccion
				.buscarPorIdAccion(this.roAccionVista.getCodigoAcci());
		this.roArchivoAccionVista = new RoArchivoAccion();
		this.actualizadoArchivoAccion = false;
		this.path = "sinArchivo";
		this.pnlEditarArchivos = false;
	}

	public void nuevoArchivoAccionVista() {
		this.pnlEditarArchivos = true;
		this.tipoGuardarArchivoAccion = true;
		this.roArchivoAccionVista = new RoArchivoAccion();
	}

	public void seleccionarParaEditarArchivoDetalleAccion() {
		this.actualizadoArchivoDetalleAccion = false;
		this.tipoGuardarArchivoDetalleAccion = false;
		this.pnlEditarDetalleArchivos = true;
	}

	public void descargarDetalleAccion() {
		try {
			this.manejoFTP.conectarServidorFtp();
			this.archivoTemporalDescarga = this.manejoFTP.descargarArchivo(
					this.roArchivoDetalleAccionVista.getPathArde(),
					this.roArchivoDetalleAccionVista.getPathArde());
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ roArchivoDetalleAccionVista.getNombreArde()
							+ "."
							+ FilenameUtils
									.getExtension(archivoTemporalDescarga
											.getAbsolutePath()));
			response.setContentLength((int) archivoTemporalDescarga.length());
			ServletOutputStream out = null;
			try {
				FileInputStream input = new FileInputStream(
						archivoTemporalDescarga);
				byte[] buffer = new byte[1024];
				out = response.getOutputStream();
				int i = 0;
				while ((i = input.read(buffer)) != -1) {
					out.write(buffer);
					out.flush();
				}
				FacesContext.getCurrentInstance().getResponseComplete();
			} catch (IOException err) {
				err.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException err) {
					err.printStackTrace();
				}
			}
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Archivo Descargado",
					"El archivo se ha descargado exitosamente");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} catch (Exception e) {
			FacesMessage mensaje = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error al Descargar",
					"No existe un archivo para este requerimiento o la conexión con el servidor ha fallado");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
			e.printStackTrace();

			this.roArchivoDetalleAccionVista = new RoArchivoDetalleAccion();
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			this.roArchivoDetalleAccionVista = new RoArchivoDetalleAccion();
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void editarArchivoDetalleAccion() {
		if (this.actualizadoArchivoDetalleAccion) {
			if (!this.roArchivoDetalleAccionVista.getPathArde().equals(
					"sinArchivo")) {
				try {
					this.manejoFTP.conectarServidorFtp();
					this.manejoFTP
							.eliminarArchivo(this.roArchivoDetalleAccionVista
									.getPathArde());
					this.manejoFTP.desconectarServidorFtp();
					guardarArchivoFileDetalleAccion();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				guardarArchivoFileDetalleAccion();
			}
		}
		try {
			String nuevoPath = new String();
			this.manejoFTP.conectarServidorFtp();
			StringTokenizer tokens = new StringTokenizer(
					this.roArchivoDetalleAccionVista.getPathArde(), ".");
			tokens.nextToken();
			try {
				nuevoPath =

				"archivosdetalle/"
						+ this.roArchivoDetalleAccionVista.getNombreArde()
						+ "." + tokens.nextToken();
			} catch (Exception localException1) {
			}
			try {
				this.manejoFTP.renombrarArchivoDirectorio(
						this.roArchivoDetalleAccionVista.getPathArde(),
						nuevoPath);
				this.roArchivoDetalleAccionVista.setPathArde(nuevoPath);
			} catch (Exception localException2) {
			}
			this.servicioRoArchivoDetalleAccion
					.actualizar(this.roArchivoDetalleAccionVista);
			cancelarArchivoDetalleAccion();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Información Actualizada", "El recurso ha sido actualizado");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} catch (Exception localException3) {
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void eliminarArchivoDetalleAccion() {
		try {
			this.servicioRoArchivoDetalleAccion
					.eliminar(this.roArchivoDetalleAccionVista);
			this.manejoFTP.conectarServidorFtp();
			this.manejoFTP.eliminarArchivo(this.roArchivoDetalleAccionVista
					.getPathArde());
			cancelarArchivoDetalleAccion();
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Archivo Eliminado",
					"El archivo ha sido eliminado exitosamente");
			cargarObservacionesAccion();
			cargarObservacionesDetalleAccion();
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					"growl1",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error",
							"Se ha producido un error al eliminar, si el error persiste consulte con el proveedor"));

			cancelarArchivoDetalleAccion();
			e.printStackTrace();
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				this.manejoFTP.desconectarServidorFtp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void prepararArchivoDetalleAccion(FileUploadEvent event) {
		this.archivoRequerimiento = event.getFile();
		this.actualizadoArchivoDetalleAccion = true;
		FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Archivo Cargado", "El archivo "
						+ event.getFile().getFileName()
						+ " se ha cargado exitosamente");
		FacesContext.getCurrentInstance().addMessage(null, mensaje);
	}

	public void guardarArchivoFileDetalleAccion() {
		try {
			StringTokenizer tokens = new StringTokenizer(
					this.archivoRequerimiento.getFileName(), ".");
			tokens.nextToken();
			this.extenFile = tokens.nextToken();
			this.archivoTemporal = File.createTempFile(
					"ArchivoDetalleAccionTemp", "." + this.extenFile);
			BufferedInputStream bufferedInput = new BufferedInputStream(
					this.archivoRequerimiento.getInputstream());
			FileOutputStream fileOutput = new FileOutputStream(
					this.archivoTemporal);
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(
					fileOutput);
			byte[] array = new byte[1024];
			int leidos = bufferedInput.read(array);
			while (leidos > 0) {
				bufferedOutput.write(array, 0, leidos);
				leidos = bufferedInput.read(array);
			}
			bufferedInput.close();
			bufferedOutput.flush();
			bufferedOutput.close();

			this.manejoFTP.conectarServidorFtp();
			this.manejoFTP.cargarRequerimiento2(this.archivoTemporal);
			this.path =

			("/archivosdetalle/"
					+ this.roArchivoDetalleAccionVista.getNombreArde() + "." + this.extenFile);
			this.manejoFTP.renombrarArchivoDirectorio("/archivosdetalle/"
					+ this.archivoTemporal.getName(), this.path);
			this.manejoFTP.desconectarServidorFtp();
			this.roArchivoDetalleAccionVista.setPathArde(this.path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarArchivoDetalleAccion() {
		if (this.tipoGuardarArchivoDetalleAccion) {
			this.roArchivoDetalleAccionVista
					.setRoDetalleAccion(this.roDetalleAccionVista);
			if (this.actualizadoArchivoDetalleAccion) {
				guardarArchivoFileDetalleAccion();
			} else {
				this.roArchivoDetalleAccionVista.setPathArde(this.path);
			}
			try {
				this.servicioRoArchivoDetalleAccion
						.insertar(this.roArchivoDetalleAccionVista);
				cancelarArchivoDetalleAccion();
				FacesMessage mensaje = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Información Ingresada",
						"El recurso ha sido registrado");
				cargarObservacionesAccion();
				cargarObservacionesDetalleAccion();
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
			} catch (Exception e) {
				FacesMessage mensaje = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Información Ingresada",
						"El recurso ha sido registrado");
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
				e.printStackTrace();
			}
		} else {
			editarArchivoDetalleAccion();
		}
	}

	public void cancelarArchivoDetalleAccion() {
		this.archivosPlanDetalleAccion = this.servicioRoArchivoDetalleAccion
				.buscarPorIdDetalleAccion(this.roDetalleAccionVista
						.getCodigoDeac());
		this.roArchivoDetalleAccionVista = new RoArchivoDetalleAccion();
		this.actualizadoArchivoDetalleAccion = false;
		this.path = "sinArchivo";
		this.pnlEditarDetalleArchivos = false;
	}

	public void nuevoArchivoDetalleAccionVista() {
		this.pnlEditarDetalleArchivos = true;
		this.tipoGuardarArchivoDetalleAccion = true;
		this.roArchivoDetalleAccionVista = new RoArchivoDetalleAccion();
	}

	public SisUsuario getSisUsuario() {
		return this.sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

	public List<RoDetalleEvento> getDetalleEventoSeleccionadoLista() {
		return this.detalleEventoSeleccionadoLista;
	}

	public void setDetalleEventoSeleccionadoLista(
			List<RoDetalleEvento> detalleEventoSeleccionadoLista) {
		this.detalleEventoSeleccionadoLista = detalleEventoSeleccionadoLista;
	}

	public List<RoResponsable> getRoResponsablesTodos() {
		return this.roResponsablesTodos;
	}

	public void setRoResponsablesTodos(List<RoResponsable> roResponsablesTodos) {
		this.roResponsablesTodos = roResponsablesTodos;
	}

	public List<RoAccion> getAccionesTodos() {
		return this.accionesTodos;
	}

	public void setAccionesTodos(List<RoAccion> accionesTodos) {
		this.accionesTodos = accionesTodos;
	}

	public boolean isPnlAccion() {
		return this.pnlAccion;
	}

	public void setPnlAccion(boolean pnlAccion) {
		this.pnlAccion = pnlAccion;
	}

	public boolean isPnlEditarDetalleAccion() {
		return this.pnlEditarDetalleAccion;
	}

	public void setPnlEditarDetalleAccion(boolean pnlEditarDetalleAccion) {
		this.pnlEditarDetalleAccion = pnlEditarDetalleAccion;
	}

	public boolean isBtnAnadirDetalleAccion() {
		return this.btnAnadirDetalleAccion;
	}

	public void setBtnAnadirDetalleAccion(boolean btnAnadirDetalleAccion) {
		this.btnAnadirDetalleAccion = btnAnadirDetalleAccion;
	}

	public String getNegocioNivel1() {
		return this.negocioNivel1;
	}

	public void setNegocioNivel1(String negocioNivel1) {
		this.negocioNivel1 = negocioNivel1;
	}

	public String getNegocioNivel2() {
		return this.negocioNivel2;
	}

	public void setNegocioNivel2(String negocioNivel2) {
		this.negocioNivel2 = negocioNivel2;
	}

	public String getEventoNivel1() {
		return this.eventoNivel1;
	}

	public void setEventoNivel1(String eventoNivel1) {
		this.eventoNivel1 = eventoNivel1;
	}

	public String getEventoNivel2() {
		return this.eventoNivel2;
	}

	public void setEventoNivel2(String eventoNivel2) {
		this.eventoNivel2 = eventoNivel2;
	}

	public RoDetalleEvento getRoDetalleEventoVista() {
		return this.roDetalleEventoVista;
	}

	public void setRoDetalleEventoVista(RoDetalleEvento roDetalleEventoVista) {
		this.roDetalleEventoVista = roDetalleEventoVista;
	}

	public RoAccion getRoAccionVista() {
		return this.roAccionVista;
	}

	public void setRoAccionVista(RoAccion roAccionVista) {
		this.roAccionVista = roAccionVista;
	}

	public RoAccion getRoAccionControlador() {
		return this.roAccionControlador;
	}

	public void setRoAccionControlador(RoAccion roAccionControlador) {
		this.roAccionControlador = roAccionControlador;
	}

	public RoDetalleAccion getRoDetalleAccionVista() {
		return this.roDetalleAccionVista;
	}

	public void setRoDetalleAccionVista(RoDetalleAccion roDetalleAccionVista) {
		this.roDetalleAccionVista = roDetalleAccionVista;
	}

	public int getIdResponsableSeleccionado() {
		return this.idResponsableSeleccionado;
	}

	public void setIdResponsableSeleccionado(int idResponsableSeleccionado) {
		this.idResponsableSeleccionado = idResponsableSeleccionado;
	}

	public boolean isMostrarPnlDetalleAccion() {
		return this.mostrarPnlDetalleAccion;
	}

	public void setMostrarPnlDetalleAccion(boolean mostrarPnlDetalleAccion) {
		this.mostrarPnlDetalleAccion = mostrarPnlDetalleAccion;
	}

	public int getIdSupervisorEjecucionSeleccionado() {
		return this.idSupervisorEjecucionSeleccionado;
	}

	public void setIdSupervisorEjecucionSeleccionado(
			int idSupervisorEjecucionSeleccionado) {
		this.idSupervisorEjecucionSeleccionado = idSupervisorEjecucionSeleccionado;
	}

	public List<RoCumplimiento> getCumplimientosTodos() {
		return this.cumplimientosTodos;
	}

	public void setCumplimientosTodos(List<RoCumplimiento> cumplimientosTodos) {
		this.cumplimientosTodos = cumplimientosTodos;
	}

	public int getIdCumplimiento() {
		return this.idCumplimiento;
	}

	public void setIdCumplimiento(int idCumplimiento) {
		this.idCumplimiento = idCumplimiento;
	}

	public List<RoDetalleAccion> getRoDetalleAccionTodos() {
		return this.roDetalleAccionTodos;
	}

	public void setRoDetalleAccionTodos(
			List<RoDetalleAccion> roDetalleAccionTodos) {
		this.roDetalleAccionTodos = roDetalleAccionTodos;
	}

	public boolean isBtnAnadirAccion() {
		return this.btnAnadirAccion;
	}

	public void setBtnAnadirAccion(boolean btnAnadirAccion) {
		this.btnAnadirAccion = btnAnadirAccion;
	}

	public DataManagerSesion getDataManagerSesion() {
		return this.dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return this.controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}

	public List<String> getPadresEvento() {
		return this.padresEvento;
	}

	public void setPadresEvento(List<String> padresEvento) {
		this.padresEvento = padresEvento;
	}

	public List<String> getPadresNegocio() {
		return this.padresNegocio;
	}

	public void setPadresNegocio(List<String> padresNegocio) {
		this.padresNegocio = padresNegocio;
	}

	public boolean isTipoGurdar() {
		return this.tipoGurdar;
	}

	public void setTipoGurdar(boolean tipoGurdar) {
		this.tipoGurdar = tipoGurdar;
	}

	public boolean isTipoGuardarDetalle() {
		return this.tipoGuardarDetalle;
	}

	public void setTipoGuardarDetalle(boolean tipoGuardarDetalle) {
		this.tipoGuardarDetalle = tipoGuardarDetalle;
	}

	public int getIdResponsableEjecucionSeleccionado() {
		return this.idResponsableEjecucionSeleccionado;
	}

	public void setIdResponsableEjecucionSeleccionado(
			int idResponsableEjecucionSeleccionado) {
		this.idResponsableEjecucionSeleccionado = idResponsableEjecucionSeleccionado;
	}

	public List<RoArchivoAccion> getArchivosPlanDeAccion() {
		return this.archivosPlanDeAccion;
	}

	public void setArchivosPlanDeAccion(
			List<RoArchivoAccion> archivosPlanDeAccion) {
		this.archivosPlanDeAccion = archivosPlanDeAccion;
	}

	public RoArchivoAccion getRoArchivoAccionVista() {
		return this.roArchivoAccionVista;
	}

	public void setRoArchivoAccionVista(RoArchivoAccion roArchivoAccionVista) {
		this.roArchivoAccionVista = roArchivoAccionVista;
	}

	public boolean isPnlEditarArchivos() {
		return this.pnlEditarArchivos;
	}

	public void setPnlEditarArchivos(boolean pnlEditarArchivos) {
		this.pnlEditarArchivos = pnlEditarArchivos;
	}

	public boolean isActualizado() {
		return this.actualizado;
	}

	public void setActualizado(boolean actualizado) {
		this.actualizado = actualizado;
	}

	public ManejoFTP getManejoFTP() {
		return this.manejoFTP;
	}

	public void setManejoFTP(ManejoFTP manejoFTP) {
		this.manejoFTP = manejoFTP;
	}

	public UploadedFile getArchivoRequerimiento() {
		return this.archivoRequerimiento;
	}

	public void setArchivoRequerimiento(UploadedFile archivoRequerimiento) {
		this.archivoRequerimiento = archivoRequerimiento;
	}

	public File getArchivoTemporal() {
		return this.archivoTemporal;
	}

	public void setArchivoTemporal(File archivoTemporal) {
		this.archivoTemporal = archivoTemporal;
	}

	public StreamedContent getFileDescarga() {
		return this.fileDescarga;
	}

	public void setFileDescarga(StreamedContent fileDescarga) {
		this.fileDescarga = fileDescarga;
	}

	public File getArchivoTemporalDescarga() {
		return this.archivoTemporalDescarga;
	}

	public void setArchivoTemporalDescarga(File archivoTemporalDescarga) {
		this.archivoTemporalDescarga = archivoTemporalDescarga;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtenFile() {
		return this.extenFile;
	}

	public void setExtenFile(String extenFile) {
		this.extenFile = extenFile;
	}

	public boolean isTipoGuardarArchivoAccion() {
		return this.tipoGuardarArchivoAccion;
	}

	public void setTipoGuardarArchivoAccion(boolean tipoGuardarArchivoAccion) {
		this.tipoGuardarArchivoAccion = tipoGuardarArchivoAccion;
	}

	public boolean isActualizadoArchivoAccion() {
		return this.actualizadoArchivoAccion;
	}

	public void setActualizadoArchivoAccion(boolean actualizadoArchivoAccion) {
		this.actualizadoArchivoAccion = actualizadoArchivoAccion;
	}

	public List<RoArchivoDetalleAccion> getArchivosPlanDetalleAccion() {
		return this.archivosPlanDetalleAccion;
	}

	public void setArchivosPlanDetalleAccion(
			List<RoArchivoDetalleAccion> archivosPlanDetalleAccion) {
		this.archivosPlanDetalleAccion = archivosPlanDetalleAccion;
	}

	public boolean isActualizadoArchivoDetalleAccion() {
		return this.actualizadoArchivoDetalleAccion;
	}

	public void setActualizadoArchivoDetalleAccion(
			boolean actualizadoArchivoDetalleAccion) {
		this.actualizadoArchivoDetalleAccion = actualizadoArchivoDetalleAccion;
	}

	public boolean isTipoGuardarArchivoDetalleAccion() {
		return this.tipoGuardarArchivoDetalleAccion;
	}

	public void setTipoGuardarArchivoDetalleAccion(
			boolean tipoGuardarArchivoDetalleAccion) {
		this.tipoGuardarArchivoDetalleAccion = tipoGuardarArchivoDetalleAccion;
	}

	public boolean isPnlEditarDetalleArchivos() {
		return this.pnlEditarDetalleArchivos;
	}

	public void setPnlEditarDetalleArchivos(boolean pnlEditarDetalleArchivos) {
		this.pnlEditarDetalleArchivos = pnlEditarDetalleArchivos;
	}

	public RoArchivoDetalleAccion getRoArchivoDetalleAccionVista() {
		return this.roArchivoDetalleAccionVista;
	}

	public void setRoArchivoDetalleAccionVista(
			RoArchivoDetalleAccion roArchivoDetalleAccionVista) {
		this.roArchivoDetalleAccionVista = roArchivoDetalleAccionVista;
	}

	public RoDetalleEvento getRoDetalleEventoControlador() {
		return this.roDetalleEventoControlador;
	}

	public void setRoDetalleEventoControlador(
			RoDetalleEvento roDetalleEventoControlador) {
		this.roDetalleEventoControlador = roDetalleEventoControlador;
	}

	public boolean isDisableResponsable() {
		return this.disableResponsable;
	}

	public void setDisableResponsable(boolean disableResponsable) {
		this.disableResponsable = disableResponsable;
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

	public TimelineModel getModel() {
		return model;
	}

	public void setModel(TimelineModel model) {
		this.model = model;
	}

	public TimelineModel getModelS() {
		return modelS;
	}

	public void setModelS(TimelineModel modelS) {
		this.modelS = modelS;
	}

	public TimelineModel getModelR() {
		return modelR;
	}

	public void setModelR(TimelineModel modelR) {
		this.modelR = modelR;
	}


	public int getNumPlanesAccion() {
		return numPlanesAccion;
	}


	public void setNumPlanesAccion(int numPlanesAccion) {
		this.numPlanesAccion = numPlanesAccion;
	}


	public boolean isPnlContador() {
		return pnlContador;
	}


	public void setPnlContador(boolean pnlContador) {
		this.pnlContador = pnlContador;
	}
}
