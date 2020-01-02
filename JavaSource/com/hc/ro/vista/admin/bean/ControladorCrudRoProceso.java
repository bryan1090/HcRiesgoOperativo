package com.hc.ro.vista.admin.bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.hc.ro.dataManager.CriticidadDataManager;
import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.GenEstado;
import com.hc.ro.modelo.GenNivelArbol;
import com.hc.ro.modelo.GenNivelProceso;
import com.hc.ro.modelo.RoCriticidad;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoDetCriticProc;
import com.hc.ro.modelo.RoFrecEjecucion;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoRespPro;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.RoTipoProceso;
import com.hc.ro.modelo.RoTipoEjecucion;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioGenNivelArbol;
import com.hc.ro.negocio.ServicioGenNivelProceso;
import com.hc.ro.negocio.ServicioRoCriticidad;
import com.hc.ro.negocio.ServicioRoDepartamento;
import com.hc.ro.negocio.ServicioRoDetCriticProc;
import com.hc.ro.negocio.ServicioRoFrecEjecucion;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoRespPro;
import com.hc.ro.negocio.ServicioRoResponsable;
import com.hc.ro.negocio.ServicioRoTipoEjecucion;
import com.hc.ro.negocio.ServicioRoTipoProceso;

@ManagedBean
@ViewScoped
public class ControladorCrudRoProceso {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	@ManagedProperty("#{criticidadDataManager}")
	CriticidadDataManager criticidadDataManager;
	public final static String nombrePagina = "/paginas/CrudProceso.jsf";

	// NEGOCIO
	@EJB
	ServicioRoProceso servicioRoProceso;
	@EJB
	ServicioGenEstado servicioGenEstado;
	@EJB
	ServicioGenNivelArbol servicioGenNivelArbol;
	@EJB
	ServicioGenNivelProceso servicioGenNivelProceso;
	@EJB
	ServicioRoTipoEjecucion servicioRoTipoEjecucion;
	@EJB
	ServicioRoFrecEjecucion servicioRoFrecEjecucion;
	@EJB
	ServicioRoTipoProceso servicioRoTipoProceso;
	@EJB
	ServicioRoCriticidad servicioRoCriticidad;
	@EJB
	ServicioRoDepartamento servicioRoDepartamento;
	@EJB
	ServicioRoDetCriticProc servicioRoDetCriticProc;
	@EJB
	ServicioRoRespPro servicioRoRespPro;
	@EJB
	ServicioRoResponsable servicioRoResponsable;

	// VARIABLES
	private RoProceso roProcesoVista;
	private RoProceso roProcesoControlador;
	private List<RoProceso> subProcesosTodos;
	private List<RoProceso> listaTodosProcesos;
	private List<GenNivelArbol> nivelesArbolTodos;
	private List<GenNivelProceso> nivelesProcesoTodos;
	private List<RoDetCriticProc> detCriticsProceso;
	private List<RoTipoEjecucion> tiposEjecucionTodos;
	private List<RoTipoProceso> tipoProcesosTodos;
	private List<RoCriticidad> criticidadTodos;
	private List<String> criticidadesSeleccionadas;
	private List<RoCriticidad> criticidades;
	private List<RoDepartamento> departamentosTodos;
	private List<RoFrecEjecucion> frecuenciasEjecucionTodos;
	private List<GenEstado> estadosTodos;
	private List<RoResponsable> responsablesTodos;
	private int idProcesoSeleccionado;
	private int idEstadoSeleccionado;
	private int idTipoProcesoSeleccionado;
	private int idTipoEjecucionSeleccionado;
	private int idFrecuenciaEjecucionSeleccionado;
	private int idNivelArbolSeleccionado;
	private int idNivelProcesoSeleccionado;
	private int idDepartamentoSeleccionadoPros;
	private int idDepartamentoSeleccionadoAntPros;
	private boolean tipoGuardar;
	private TreeNode arbolProcesoVista;
	private TreeNode arbolProcesoSeleccionado;
	private boolean estaExpandido;
	private String nombreSeleccionado;
	private List<TreeNode> nodosTodos;
	// private String numeroPadre;
	// private String numeroHijo;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlProceso;
	private boolean txtNumero;
	private SisUsuario usuario;

	private List<RoRespPro> responsablesProceso;

	private List<String> responsablesSeleccionados;

	//
	public ControladorCrudRoProceso() {
		super();
		detCriticsProceso = new ArrayList<RoDetCriticProc>();
		responsablesProceso = new ArrayList<RoRespPro>();
		responsablesSeleccionados = new ArrayList<String>();
		responsablesTodos = new ArrayList<RoResponsable>();
		roProcesoControlador = new RoProceso();
		roProcesoVista = new RoProceso();
		subProcesosTodos = new ArrayList<RoProceso>();
		listaTodosProcesos = new ArrayList<RoProceso>();
		nivelesArbolTodos = new ArrayList<GenNivelArbol>();
		nivelesProcesoTodos = new ArrayList<GenNivelProceso>();
		tipoProcesosTodos = new ArrayList<RoTipoProceso>();
		tiposEjecucionTodos = new ArrayList<RoTipoEjecucion>();
		frecuenciasEjecucionTodos = new ArrayList<RoFrecEjecucion>();
		criticidadTodos = new ArrayList<RoCriticidad>();
		departamentosTodos = new ArrayList<RoDepartamento>();
		criticidadesSeleccionadas = new ArrayList<String>();
		criticidades = new ArrayList<RoCriticidad>();
		estadosTodos = new ArrayList<GenEstado>();
		arbolProcesoVista = new DefaultTreeNode("Root", null);
		arbolProcesoSeleccionado = new DefaultTreeNode();
		nombreSeleccionado = new String();
		// numeroPadre = new String();
		usuario = new SisUsuario();
		// listaProcesos();
	}

	@PostConstruct
	public void PostControladorCrudRoProceso() {
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
		responsablesTodos = servicioRoResponsable.buscarTodos();
		nivelesArbolTodos = servicioGenNivelArbol.buscarTodos();
		nivelesProcesoTodos = servicioGenNivelProceso.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		tiposEjecucionTodos = servicioRoTipoEjecucion.buscarTodos();
		tipoProcesosTodos = servicioRoTipoProceso.buscarTodos();
		frecuenciasEjecucionTodos = servicioRoFrecEjecucion.buscarTodos();
		criticidadTodos = servicioRoCriticidad.buscarTodos();
		estaExpandido = false;
		btnAnadir = true;
		btnCancelar = true;
		btnGuardar = true;
		pnlProceso = false;
		txtNumero = true;
		// usuario=dataManagerSesion.getUsuarioSesion();
		llenarArbol();
	}

	// METODOS
	/**
	 * Guarda el proceso, en caso de que sea uno nuevo lo inserta de lo
	 * contrario lo actualiza.
	 */

	public void listaProcesos() {
		listaTodosProcesos = servicioRoProceso.buscarTodos();
		for (RoProceso procesos : listaTodosProcesos) {
			System.out.println(procesos.getAncestroPros());
		}
	}

	

	public void recursivaArbol(List<RoProceso> Procesos, TreeNode padre) {
		if (Procesos.isEmpty() == false) {
			RoProceso Proceso = new RoProceso();
			for (int i = 0; i < Procesos.size(); i++) {
				Proceso = Procesos.get(i);
				nodosTodos.add(new DefaultTreeNode(Proceso.getNombrePros(),
						padre));
				List<RoProceso> ProcesosAux = new ArrayList<RoProceso>();
				ProcesosAux = servicioRoProceso.buscarProcesoPorPadre(Proceso
						.getNombrePros());
				if (ProcesosAux != null) {
					if (nodosTodos.get(nodosTodos.size() - 1).getData()
							.equals(nombreSeleccionado)) {
						nodosTodos.get(nodosTodos.size() - 1).setSelected(true);
						arbolProcesoSeleccionado = nodosTodos.get(nodosTodos
								.size() - 1);
						nodosTodos.get(nodosTodos.size() - 1).setExpanded(
								estaExpandido);
					}
					recursivaArbol(ProcesosAux,
							nodosTodos.get(nodosTodos.size() - 1));
				}
			}
		}
	}

	/*
	 * public void marcarCriticidadesPorDepartamento() {
	 * criticidadesSeleccionadas = new ArrayList<String>(); for (RoDetCriticProc
	 * itemDCP : detCriticsProceso) { .println(idDepartamentoSeleccionadoPros);
	 * .println(itemDCP.getRoDepartamento().getCodigoDept()); if
	 * (idDepartamentoSeleccionadoPros == itemDCP.getRoDepartamento()
	 * .getCodigoDept()) {
	 * criticidadesSeleccionadas.add(Integer.toString(itemDCP
	 * .getRoCriticidad().getCodigoCrit())); } }
	 * idDepartamentoSeleccionadoAntPros = idDepartamentoSeleccionadoPros; }
	 * 
	 * public void desmarcarCriticidadesPorDepartamento() { int a =
	 * detCriticsProceso.size(); List<RoDetCriticProc> listaBorrar=new
	 * ArrayList<RoDetCriticProc>(); for (int i = 0; i < a; i++) { if
	 * (idDepartamentoSeleccionadoAntPros == detCriticsProceso.get(i)
	 * .getRoDepartamento().getCodigoDept()) {
	 * listaBorrar.add(detCriticsProceso.get(i)); } }
	 * 
	 * for (RoDetCriticProc item : listaBorrar) {
	 * detCriticsProceso.remove(item); } RoDepartamento auxDept =
	 * servicioRoDepartamento .buscarPorId((short)
	 * (idDepartamentoSeleccionadoAntPros));
	 * 
	 * for (String item : criticidadesSeleccionadas) { RoDetCriticProc detCritic
	 * = new RoDetCriticProc(); detCritic.setRoDepartamento(auxDept);
	 * detCritic.setRoProceso(roProcesoVista);
	 * detCritic.setRoCriticidad(servicioRoCriticidad
	 * .buscarPorId(Integer.parseInt(item))); detCriticsProceso.add(detCritic);
	 * } }
	 * 
	 * public void desMarcar() { desmarcarCriticidadesPorDepartamento();
	 * marcarCriticidadesPorDepartamentdesmarcarCriticidadesPorDepartamento
	 * ();o(); }
	 */

	public void seleccionarProceso() {

		roProcesoVista = new RoProceso();
		// roProcesoControlador = new RoProceso();
		subProcesosTodos = servicioRoProceso
				.buscarProcesoPorPadre(arbolProcesoSeleccionado.getData()
						.toString());

		nombreSeleccionado = arbolProcesoSeleccionado.getData().toString();

		// if (nombreSeleccionado.equals("Procesos")) {
		// numeroPadre = "";
		// } else {
		// numeroPadre = servicioRoProceso.buscarProcesoPorNombre(
		// nombreSeleccionado).getNumeroPros();
		// }

		btnAnadir = false;
		pnlProceso = false;
		// listaProcesos();
	}

	public void llenarArbol() {
		arbolProcesoVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Procesos",
				arbolProcesoVista);
		if (nombreSeleccionado.equals("Procesos")) {
			arbolVirtual.setExpanded(estaExpandido);
			arbolVirtual.setSelected(true);
		}
		nodosTodos = new ArrayList<TreeNode>();
		recursivaArbol(servicioRoProceso.buscarProcesoPorPadre("Procesos"),
				arbolVirtual);
	}
	
	public void guardarResponsablesProceso() {
		roProcesoVista = servicioRoProceso
				.buscarProcesoPorNombre(roProcesoVista.getNombrePros());
		List<RoRespPro> iterable = servicioRoRespPro
				.buscarRespProcesoPorProcesoCodigo(roProcesoVista.getCodigoPros());
		for (RoRespPro item : iterable) {
			servicioRoRespPro.eliminar(item);
		}
		//setResponsablesProceso(new ArrayList<RoRespPro>());
		responsablesProceso = new ArrayList<RoRespPro>();
		for (String item : responsablesSeleccionados) {

			RoRespPro respPros = new RoRespPro();
			System.out.println("proceso: "+roProcesoVista.getCodigoPros());
			respPros.setRoProceso(roProcesoVista);
			respPros.setRoResponsable(servicioRoResponsable.buscarPorId(Integer
					.parseInt(item)));
			responsablesProceso.add(respPros);
		}
		for (RoRespPro item1 : responsablesProceso) {
			servicioRoRespPro.insertar(item1);
		}
		responsablesProceso = new ArrayList<RoRespPro>();
	}

	public void guardarDetCritProc() {
		List<RoDetCriticProc> iterable = servicioRoDetCriticProc
				.buscarDetCriticProcPorProcesoCodigo(roProcesoVista.getCodigoPros());
		for (RoDetCriticProc item : iterable) {
			servicioRoDetCriticProc.eliminar(item);
		}
		detCriticsProceso = new ArrayList<RoDetCriticProc>();
		for (String item : criticidadesSeleccionadas) {
			RoDetCriticProc detCritic = new RoDetCriticProc();
			detCritic.setRoProceso(roProcesoVista);
			detCritic.setRoCriticidad(servicioRoCriticidad.buscarPorId(Integer
					.parseInt(item)));
			detCriticsProceso.add(detCritic);
		}
		for (RoDetCriticProc item1 : detCriticsProceso) {
			servicioRoDetCriticProc.insertar(item1);
		}
		detCriticsProceso = new ArrayList<RoDetCriticProc>();
	}

	public void guardarProceso() {
		// roProcesoVista.setNumeroPros(numeroPadre.concat(numeroHijo));
		roProcesoVista.setRoTipoEjecucion(servicioRoTipoEjecucion
				.buscarPorId(idTipoEjecucionSeleccionado));
		roProcesoVista.setRoTipoProceso(servicioRoTipoProceso
				.buscarPorId(idTipoProcesoSeleccionado));
		roProcesoVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		roProcesoVista.setRoFrecEjecucion(servicioRoFrecEjecucion
				.buscarPorId(idFrecuenciaEjecucionSeleccionado));
		roProcesoVista.setGenNivelArbol(servicioGenNivelArbol
				.buscarPorId(idNivelArbolSeleccionado));
		roProcesoVista.setGenNivelProceso(servicioGenNivelProceso
				.buscarPorId(idNivelProcesoSeleccionado));

		roProcesoVista.setAncestroPros(arbolProcesoSeleccionado.getData()
				.toString());
		
		if (tipoGuardar) {
			if (servicioRoProceso.existeProcesoPorNombre(roProcesoVista
					.getNombrePros())) {				
				servicioRoProceso.insertar(roProcesoVista);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Proceso Añadido",
						"La Proceso ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y número de Proceso no se deben repetir"));
			}
		} else {

			if (servicioRoProceso.existeProcesoPorNombreEx(
					roProcesoVista.getNombrePros(),
					roProcesoVista.getCodigoPros())) {

				servicioRoProceso.actualizar(roProcesoVista);
				List<RoProceso> listaProcesosHijos = servicioRoProceso
						.buscarProcesoPorPadre(roProcesoControlador
								.getNombrePros());
				if (listaProcesosHijos.size() != 0) {

					for (RoProceso hijo : listaProcesosHijos) {
						hijo.setAncestroPros(roProcesoVista.getNombrePros());
						servicioRoProceso.actualizar(hijo);
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Proceso Actualizado",
						"El Proceso ha sido Actualizado con éxito"));

				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y número de Proceso no se deben repetir"));
				roProcesoVista.setNombrePros(roProcesoControlador
						.getNombrePros());
				// roProcesoVista.setNumeroPros(roProcesoControlador
				// .getNumeroPros());
				// numeroHijo = roProcesoVista.getNombrePros().substring(
				// numeroPadre.length());
			}

		}

	}

//	public void guardarProceso() {
//		// roProcesoVista.setNumeroPros(numeroPadre.concat(numeroHijo));
//		roProcesoVista.setRoTipoEjecucion(servicioRoTipoEjecucion
//				.buscarPorId(idTipoEjecucionSeleccionado));
//		roProcesoVista.setRoTipoProceso(servicioRoTipoProceso
//				.buscarPorId(idTipoProcesoSeleccionado));
//		roProcesoVista.setGenEstado(servicioGenEstado
//				.buscarPorId(idEstadoSeleccionado));
//		roProcesoVista.setRoFrecEjecucion(servicioRoFrecEjecucion
//				.buscarPorId(idFrecuenciaEjecucionSeleccionado));
//		roProcesoVista.setGenNivelArbol(servicioGenNivelArbol
//				.buscarPorId(idNivelArbolSeleccionado));
//		roProcesoVista.setGenNivelProceso(servicioGenNivelProceso
//				.buscarPorId(idNivelProcesoSeleccionado));
//
//		roProcesoVista.setAncestroPros(arbolProcesoSeleccionado.getData()
//				.toString());
//
//		if (tipoGuardar) {
//			servicioRoProceso.insertar(roProcesoVista);
//			FacesContext context = FacesContext.getCurrentInstance();
//
//			context.addMessage(null, new FacesMessage("Proceso Añadido",
//					"La Proceso ha sido Añadido con éxito"));
//			exitoGuardar();
//		} else {
//
//			servicioRoProceso.actualizar(roProcesoVista);
//			List<RoProceso> listaProcesosHijos = servicioRoProceso
//					.buscarProcesoPorPadre(roProcesoControlador.getNombrePros());
//			if (listaProcesosHijos.size() != 0) {
//
//				for (RoProceso hijo : listaProcesosHijos) {
//					hijo.setAncestroPros(roProcesoVista.getNombrePros());
//					servicioRoProceso.actualizar(hijo);
//				}
//			}
//			FacesContext context = FacesContext.getCurrentInstance();
//
//			context.addMessage(null, new FacesMessage("Proceso Actualizado",
//					"El Proceso ha sido Actualizado con éxito"));
//
//			exitoGuardar();
//
//		}
//
//	}

	public void exitoGuardar() {	
		try {
			guardarResponsablesProceso();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Proceso Guardado con errores en responsables (Vuelva a intentarlo)"));
		}
		
		try {			
			guardarDetCritProc();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Proceso Guardado con errores en criticidades (Vuelva a intentarlo)"));
		}

		estaExpandido = arbolProcesoSeleccionado.isExpanded();
		llenarArbol();
		roProcesoVista = new RoProceso();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idNivelProcesoSeleccionado = 1;
		idTipoProcesoSeleccionado = 1;
		idTipoEjecucionSeleccionado = 1;

		idFrecuenciaEjecucionSeleccionado = 1;
		TreeNode arbolAux;
		arbolAux = arbolProcesoSeleccionado;
		subProcesosTodos = servicioRoProceso
				.buscarProcesoPorPadre(arbolProcesoSeleccionado.getData()
						.toString());
		while (!arbolAux.getData().toString().equals("Procesos")) {
			arbolAux = arbolAux.getParent();
			arbolAux.setExpanded(true);
		}
		btnGuardar = true;
		btnCancelar = true;
		btnAnadir = false;
		pnlProceso = false;
	}

	public void eliminarProceso() {
		for (RoDetCriticProc item : detCriticsProceso) {
			criticidadesSeleccionadas.add(Integer.toString(item
					.getRoCriticidad().getCodigoCrit()));
		}
		if (servicioRoProceso.buscarProcesoPorPadre(
				roProcesoVista.getNombrePros()).size() == 0) {
			responsablesProceso = new ArrayList<RoRespPro>();
			responsablesProceso = servicioRoRespPro
					.buscarRespProcesoPorProceso(roProcesoVista.getNombrePros());
			for (RoRespPro item : responsablesProceso) {
				servicioRoRespPro.eliminar(item);
			}

			detCriticsProceso = servicioRoDetCriticProc
					.buscarDetCriticProcPorProcesoCodigo(roProcesoVista
							.getCodigoPros());
			for (RoDetCriticProc item : detCriticsProceso) {
				servicioRoDetCriticProc.eliminar(item);
			}

			roProcesoVista = servicioRoProceso.buscarPorId(roProcesoVista
					.getCodigoPros());
			try {

				servicioRoProceso.eliminar(roProcesoVista);
				roProcesoVista = new RoProceso();
				subProcesosTodos = servicioRoProceso
						.buscarProcesoPorPadre(arbolProcesoSeleccionado
								.getData().toString());
				estaExpandido = arbolProcesoSeleccionado.isExpanded();
				llenarArbol();
				idEstadoSeleccionado = 1;
				idEstadoSeleccionado = 1;
				idNivelArbolSeleccionado = 1;
				idNivelProcesoSeleccionado = 1;
				idTipoProcesoSeleccionado = 1;
				idTipoEjecucionSeleccionado = 1;
				idFrecuenciaEjecucionSeleccionado = 1;
				TreeNode arbolAux;
				arbolAux = arbolProcesoSeleccionado;

				while (!arbolAux.getData().toString().equals("Procesos")) {
					arbolAux = arbolAux.getParent();
					arbolAux.setExpanded(true);
				}
				btnGuardar = true;
				btnCancelar = true;
				pnlProceso = false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Proceso Eliminado",
						"El Proceso ha sido Eliminado con éxito"));
			} catch (Exception e) {
				for (RoRespPro item : responsablesProceso) {
					servicioRoRespPro.insertar(item);
				}
				for (RoDetCriticProc item : detCriticsProceso) {
					servicioRoDetCriticProc.insertar(item);
				}

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
						"No se puede eliminar un Proceso en uso"));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"No se puede eliminar un Proceso que tenga sub-Procesos"));
		}
	}

	public void nuevoProcesoVista() {
		tipoGuardar = true;
		criticidadesSeleccionadas = new ArrayList<String>();
		responsablesSeleccionados = new ArrayList<String>();
		roProcesoVista = new RoProceso();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idNivelProcesoSeleccionado = 1;
		idTipoProcesoSeleccionado = 1;
		idTipoEjecucionSeleccionado = 1;
		idFrecuenciaEjecucionSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlProceso = true;
		txtNumero = false;
	}

	public void cancelar() {
		nuevoProcesoVista();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlProceso = false;
		RequestContext.getCurrentInstance().update("formProceso");

	}

	public void editarProcesoVista() {
		responsablesSeleccionados = new ArrayList<String>();
		criticidadesSeleccionadas = new ArrayList<String>();
		// numeroHijo = roProcesoVista.getNumeroPros().substring(
		// numeroPadre.length());
		System.out.println(roProcesoVista.getNombrePros());
		roProcesoControlador.setNombrePros(roProcesoVista.getNombrePros());
		// roProcesoControlador.setNumeroPros(roProcesoVista.getNumeroPros());

		tipoGuardar = false;
		responsablesProceso = servicioRoRespPro
				.buscarRespProcesoPorProcesoCodigo(roProcesoVista.getCodigoPros());

		for (RoRespPro item : responsablesProceso) {
			responsablesSeleccionados.add(Integer.toString(item
					.getRoResponsable().getCodigoResp()));
		}

		detCriticsProceso = servicioRoDetCriticProc
				.buscarDetCriticProcPorProcesoCodigo(roProcesoVista.getCodigoPros());

		for (RoDetCriticProc item : detCriticsProceso) {
			criticidadesSeleccionadas.add(Integer.toString(item
					.getRoCriticidad().getCodigoCrit()));
		}
		idEstadoSeleccionado = (int) roProcesoVista.getGenEstado()
				.getCodigoEsta();

		idNivelArbolSeleccionado = roProcesoVista.getGenNivelArbol()
				.getCodigoGniv();
		idNivelProcesoSeleccionado = roProcesoVista.getGenNivelProceso()
				.getCodigoGnip();
		idFrecuenciaEjecucionSeleccionado = roProcesoVista.getRoFrecEjecucion()
				.getCodigoFrej();
		idTipoEjecucionSeleccionado = roProcesoVista.getRoTipoEjecucion()
				.getCodigoTiej();
		idTipoProcesoSeleccionado = roProcesoVista.getRoTipoProceso()
				.getCodigoTipr();

		btnGuardar = false;
		btnCancelar = false;
		pnlProceso = true;
		btnAnadir = true;
		if (servicioRoProceso.buscarProcesoPorPadre(
				roProcesoVista.getNombrePros()).size() == 0) {
			txtNumero = false;
		} else {
			txtNumero = true;
		}
	}

	public void exportarPDF(ActionEvent actionEvent) throws JRException,
			IOException {
		Map<String, Object> parametros = new HashMap<String, Object>();

		File jasper = new File(FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/reportes/reporteProcesos.jasper"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), parametros, new JRBeanCollectionDataSource(
						subProcesosTodos));

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition",
				"attachment; filename=ReporteDeProcesos.pdf");
		ServletOutputStream stream = response.getOutputStream();

		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}

	// GETTERS Y SETTERS

	public boolean isBtnAnadir() {
		return btnAnadir;
	}

	public void setBtnAnadir(boolean btnAnadir) {
		this.btnAnadir = btnAnadir;
	}

	public boolean isBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(boolean btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public boolean isBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(boolean btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public RoProceso getRoProcesoVista() {
		return roProcesoVista;
	}

	public void setRoProcesoVista(RoProceso roProcesoVista) {
		this.roProcesoVista = roProcesoVista;
	}

	public RoProceso getRoProcesoControlador() {
		return roProcesoControlador;
	}

	public void setRoProcesoControlador(RoProceso roProcesoControlador) {
		this.roProcesoControlador = roProcesoControlador;
	}

	public int getIdProcesoSeleccionado() {
		return idProcesoSeleccionado;
	}

	public void setIdProcesoSeleccionado(int idProcesoSeleccionado) {
		this.idProcesoSeleccionado = idProcesoSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public TreeNode getArbolProcesoVista() {
		return arbolProcesoVista;
	}

	public void setArbolProcesoVista(TreeNode arbolProcesoVista) {
		this.arbolProcesoVista = arbolProcesoVista;
	}

	public TreeNode getArbolProcesoSeleccionado() {
		return arbolProcesoSeleccionado;
	}

	public void setArbolProcesoSeleccionado(TreeNode arbolProcesoSeleccionado) {
		this.arbolProcesoSeleccionado = arbolProcesoSeleccionado;
	}

	public List<GenNivelArbol> getNivelesArbolTodos() {
		return nivelesArbolTodos;
	}

	public void setNivelesArbolTodos(List<GenNivelArbol> nivelesArbolTodos) {
		this.nivelesArbolTodos = nivelesArbolTodos;
	}

	public List<GenEstado> getEstadosTodos() {
		return estadosTodos;
	}

	public void setEstadosTodos(List<GenEstado> estadosTodos) {
		this.estadosTodos = estadosTodos;
	}

	public int getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	public void setIdEstadoSeleccionado(int idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	public int getIdTipoProcesoSeleccionado() {
		return idTipoProcesoSeleccionado;
	}

	public void setIdTipoProcesoSeleccionado(int idTipoProcesoSeleccionado) {
		this.idTipoProcesoSeleccionado = idTipoProcesoSeleccionado;
	}

	public int getIdNivelArbolSeleccionado() {
		return idNivelArbolSeleccionado;
	}

	public void setIdNivelArbolSeleccionado(int idNivelArbolSeleccionado) {
		this.idNivelArbolSeleccionado = idNivelArbolSeleccionado;
	}

	public List<TreeNode> getNodosTodos() {
		return nodosTodos;
	}

	public void setNodosTodos(List<TreeNode> nodosTodos) {
		this.nodosTodos = nodosTodos;
	}

	public List<RoProceso> getSubProcesosTodos() {
		return subProcesosTodos;
	}

	public void setSubProcesosTodos(List<RoProceso> subProcesosTodos) {
		this.subProcesosTodos = subProcesosTodos;
	}

	public boolean isEstaExpandido() {
		return estaExpandido;
	}

	public void setEstaExpandido(boolean estaExpandido) {
		this.estaExpandido = estaExpandido;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlProceso() {
		return pnlProceso;
	}

	public void setPnlProceso(boolean pnlProceso) {
		this.pnlProceso = pnlProceso;
	}

	// public String getNumeroPadre() {
	// return numeroPadre;
	// }
	//
	// public void setNumeroPadre(String numeroPadre) {
	// this.numeroPadre = numeroPadre;
	// }
	//
	// public String getNumeroHijo() {
	// return numeroHijo;
	// }
	//
	// public void setNumeroHijo(String numeroHijo) {
	// this.numeroHijo = numeroHijo;
	// }

	public boolean isTxtNumero() {
		return txtNumero;
	}

	public void setTxtNumero(boolean txtNumero) {
		this.txtNumero = txtNumero;
	}

	public List<RoTipoEjecucion> getTiposEjecucionTodos() {
		return tiposEjecucionTodos;
	}

	public void setTiposEjecucionTodos(List<RoTipoEjecucion> tiposEjecucionTodos) {
		this.tiposEjecucionTodos = tiposEjecucionTodos;
	}

	public int getIdTipoEjecucionSeleccionado() {
		return idTipoEjecucionSeleccionado;
	}

	public void setIdTipoEjecucionSeleccionado(int idTipoEjecucionSeleccionado) {
		this.idTipoEjecucionSeleccionado = idTipoEjecucionSeleccionado;
	}

	public int getIdFrecuenciaEjecucionSeleccionado() {
		return idFrecuenciaEjecucionSeleccionado;
	}

	public void setIdFrecuenciaEjecucionSeleccionado(
			int idFrecuenciaEjecucionSeleccionado) {
		this.idFrecuenciaEjecucionSeleccionado = idFrecuenciaEjecucionSeleccionado;
	}

	public List<RoFrecEjecucion> getFrecuenciasEjecucionTodos() {
		return frecuenciasEjecucionTodos;
	}

	public void setFrecuenciasEjecucionTodos(
			List<RoFrecEjecucion> frecuenciasEjecucionTodos) {
		this.frecuenciasEjecucionTodos = frecuenciasEjecucionTodos;
	}

	public List<RoTipoProceso> getTipoProcesosTodos() {
		return tipoProcesosTodos;
	}

	public void setTipoProcesosTodos(List<RoTipoProceso> tipoProcesosTodos) {
		this.tipoProcesosTodos = tipoProcesosTodos;
	}

	public List<RoCriticidad> getCriticidadTodos() {
		return criticidadTodos;
	}

	public void setCriticidadTodos(List<RoCriticidad> criticidadTodos) {
		this.criticidadTodos = criticidadTodos;
	}

	public SisUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(SisUsuario usuario) {
		this.usuario = usuario;
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public List<RoCriticidad> getCriticidades() {
		return criticidades;
	}

	public void setCriticidades(List<RoCriticidad> criticidades) {
		this.criticidades = criticidades;
	}

	public CriticidadDataManager getCriticidadDataManager() {
		return criticidadDataManager;
	}

	public void setCriticidadDataManager(
			CriticidadDataManager criticidadDataManager) {
		this.criticidadDataManager = criticidadDataManager;
	}

	public List<RoDetCriticProc> getDetCriticsProceso() {
		return detCriticsProceso;
	}

	public void setDetCriticsProceso(List<RoDetCriticProc> detCriticsProceso) {
		this.detCriticsProceso = detCriticsProceso;
	}

	public int getIdDepartamentoSeleccionadoPros() {
		return idDepartamentoSeleccionadoPros;
	}

	public void setIdDepartamentoSeleccionadoPros(
			int idDepartamentoSeleccionadoPros) {
		this.idDepartamentoSeleccionadoPros = idDepartamentoSeleccionadoPros;
	}

	public List<RoDepartamento> getDepartamentosTodos() {
		return departamentosTodos;
	}

	public void setDepartamentosTodos(List<RoDepartamento> departamentosTodos) {
		this.departamentosTodos = departamentosTodos;
	}

	public int getIdDepartamentoSeleccionadoAntPros() {
		return idDepartamentoSeleccionadoAntPros;
	}

	public void setIdDepartamentoSeleccionadoAntPros(
			int idDepartamentoSeleccionadoAntPros) {
		this.idDepartamentoSeleccionadoAntPros = idDepartamentoSeleccionadoAntPros;
	}

	public List<String> getCriticidadesSeleccionadas() {
		return criticidadesSeleccionadas;
	}

	public void setCriticidadesSeleccionadas(
			List<String> criticidadesSeleccionadas) {
		this.criticidadesSeleccionadas = criticidadesSeleccionadas;
	}

	public List<RoResponsable> getResponsablesTodos() {
		return responsablesTodos;
	}

	public void setResponsablesTodos(List<RoResponsable> responsablesTodos) {
		this.responsablesTodos = responsablesTodos;
	}

	public List<RoRespPro> getResponsablesProceso() {
		return responsablesProceso;
	}

	public void setResponsablesProceso(List<RoRespPro> responsablesProceso) {
		this.responsablesProceso = responsablesProceso;
	}

	public List<String> getResponsablesSeleccionados() {
		return responsablesSeleccionados;
	}

	public void setResponsablesSeleccionados(
			List<String> responsablesSeleccionados) {
		this.responsablesSeleccionados = responsablesSeleccionados;
	}

	public List<GenNivelProceso> getNivelesProcesoTodos() {
		return nivelesProcesoTodos;
	}

	public void setNivelesProcesoTodos(List<GenNivelProceso> nivelesProcesoTodos) {
		this.nivelesProcesoTodos = nivelesProcesoTodos;
	}

	public int getIdNivelProcesoSeleccionado() {
		return idNivelProcesoSeleccionado;
	}

	public void setIdNivelProcesoSeleccionado(int idNivelProcesoSeleccionado) {
		this.idNivelProcesoSeleccionado = idNivelProcesoSeleccionado;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}

}
