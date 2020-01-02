package com.hc.ro.vista.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.GenEstado;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoRespAgencia;
import com.hc.ro.modelo.RoRespDepa;
import com.hc.ro.modelo.RoRespPro;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.RoTipoResp;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioRoAgencia;
import com.hc.ro.negocio.ServicioRoDepartamento;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoRespAgencia;
import com.hc.ro.negocio.ServicioRoRespDepa;
import com.hc.ro.negocio.ServicioRoRespPro;
import com.hc.ro.negocio.ServicioRoResponsable;
import com.hc.ro.negocio.ServicioRoTipoResp;
import com.hc.ro.negocio.ServicioSisUsuario;

@ManagedBean
@ViewScoped
public class ControladorCrudRoResponsable {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudResponsable.jsf";
	// Responsable
	@EJB
	ServicioRoResponsable servicioRoResponsable;
	@EJB
	ServicioGenEstado servicioGenEstado;
	@EJB
	ServicioSisUsuario servicioSisUsuario;
	@EJB
	ServicioRoTipoResp servicioRoTipoResp;
	@EJB
	ServicioRoRespPro servicioRoRespPro;
	@EJB
	ServicioRoProceso servicioRoProceso;
	@EJB
	ServicioRoRespDepa servicioRoRespDepa;

	@EJB
	ServicioRoDepartamento servicioRoDepartamento;
	@EJB
	ServicioRoRespAgencia servicioRoRespAgencia;
	@EJB
	ServicioRoAgencia servicioRoAgencia;

	// VARIABLES
	private RoResponsable roResponsableVista;
	private RoResponsable roResponsableControlador;
	private List<RoResponsable> roResponsablesTodos;
	private List<GenEstado> estadosTodos;
	private List<SisUsuario> usuariosTodos;
	private List<SisUsuario> usuariosTodosAux;
	private List<RoTipoResp> tipoRespTodos;

	private List<RoRespPro> listaRoRespProAsignadosAlResponsableVista;
	private List<Integer> listaCodigoProcesoTodos;
	private List<Integer> listaCodigoProcesoResponsableVista;
	private List<Integer> listaCodigoProcesoPorAsignarResponsableVista;
	private List<RoProceso> listaRoProcesoTodos;

	private List<RoRespDepa> listaRoRespDeptAsignadosAlResponsableVista;
	private List<Integer> listaCodigoDepartamentoTodos;
	private List<Integer> listaCodigoDepartamentoResponsableVista;
	private List<Integer> listaCodigoDepartamentoPorAsignarResponsableVista;
	private List<RoDepartamento> listaRoDepartamentoTodos;

	private List<RoRespAgencia> listaRoRespAgiaAsignadosAlResponsableVista;
	private List<Integer> listaCodigoAgenciaTodos;
	private List<Integer> listaCodigoAgenciaResponsableVista;
	private List<Integer> listaCodigoAgenciaPorAsignarResponsableVista;
	private List<RoAgencia> listaRoAgenciaTodos;

	private int idResponsableSeleccionado;
	private int idEstadoSeleccionado;
	private int idUsuarioSeleccionado;
	private int idResponsable;
	private int idTipoResponsable;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlResponsable;

	private boolean btnAgregarTodosProceso;
	private boolean btnAgregarTodosDepartamento;
	private boolean btnAgregarTodosAgencia;
	private boolean btnPermisosSU;

	private boolean mostrarBtnSUProcesos;
	private boolean mostrarBtnSUDepartamentos;
	private boolean mostrarBtnSUAgencias;

	//
	public ControladorCrudRoResponsable() {
		super();
		roResponsableControlador = new RoResponsable();
		roResponsableVista = new RoResponsable();
		roResponsablesTodos = new ArrayList<RoResponsable>();
		estadosTodos = new ArrayList<GenEstado>();
		usuariosTodos = new ArrayList<SisUsuario>();
		usuariosTodosAux = new ArrayList<SisUsuario>();
		nombreSeleccionado = new String();
		tipoRespTodos = new ArrayList<RoTipoResp>();

		// lista con los codigos de todos los procesos
		listaCodigoProcesoTodos = new ArrayList<Integer>();
		// lista con los codigos de los procesos asignados al
		// responsablevista
		listaCodigoProcesoResponsableVista = new ArrayList<Integer>();
		// lista con los codigos de los procesos nuevos por asignar
		listaCodigoProcesoPorAsignarResponsableVista = new ArrayList<Integer>();
		// lista con todos los objetos roprocesos
		listaRoProcesoTodos = new ArrayList<RoProceso>();
		// lista con todos los procesos asignados al responsable vista
		listaRoRespProAsignadosAlResponsableVista = new ArrayList<RoRespPro>();

		// listas para uso asignacion todo departamentos
		listaRoRespDeptAsignadosAlResponsableVista = new ArrayList<RoRespDepa>();
		listaCodigoDepartamentoTodos = new ArrayList<Integer>();
		listaCodigoDepartamentoResponsableVista = new ArrayList<Integer>();
		listaCodigoDepartamentoPorAsignarResponsableVista = new ArrayList<Integer>();
		listaRoDepartamentoTodos = new ArrayList<RoDepartamento>();

		// listas para uso asignacion todo agencias
		listaRoRespAgiaAsignadosAlResponsableVista = new ArrayList<RoRespAgencia>();
		listaCodigoAgenciaTodos = new ArrayList<Integer>();
		listaCodigoAgenciaResponsableVista = new ArrayList<Integer>();
		listaCodigoAgenciaPorAsignarResponsableVista = new ArrayList<Integer>();
		listaRoAgenciaTodos = new ArrayList<RoAgencia>();

	}

	@PostConstruct
	public void PostControladorCrudRoResponsable() {
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
		roResponsablesTodos = servicioRoResponsable.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		usuariosTodos = servicioSisUsuario.buscarTodos();

		tipoRespTodos = servicioRoTipoResp.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlResponsable = false;

		btnAgregarTodosProceso = false;
		btnAgregarTodosDepartamento = false;
		btnAgregarTodosAgencia = false;
		btnPermisosSU = false;

		mostrarBtnSUAgencias = false;
		mostrarBtnSUDepartamentos = false;
		mostrarBtnSUProcesos = false;

		// Obtengo datos para listas generales
		// procesos
		listaRoProcesoTodos = servicioRoProceso.buscarTodos();
		listaCodigoProcesoTodos = servicioRoProceso.buscarCodigosProcesoTodos();
		// departamentos
		listaRoDepartamentoTodos = servicioRoDepartamento.buscarTodos();
		listaCodigoDepartamentoTodos = servicioRoDepartamento
				.buscarCodigosDepartamentoTodos();
		// agencias
		listaRoAgenciaTodos = servicioRoAgencia.buscarTodos();
		listaCodigoAgenciaTodos = servicioRoAgencia.buscarCodigosAgenciaTodos();

	}

	// METODOS

	public void guardarResponsable() {

		roResponsableVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		roResponsableVista.setSisUsuario(servicioSisUsuario
				.buscarPorId(idUsuarioSeleccionado));
		roResponsableVista.setRoTipoResp(servicioRoTipoResp
				.buscarPorId(idTipoResponsable));

		FacesContext context = FacesContext.getCurrentInstance();

		roResponsableControlador = roResponsableVista;

		if (tipoGuardar) {
			System.out.println("Responsable nuevo");
			// if (servicioRoResponsable
			// .existeResponsablePorNombre(roResponsableVista
			// .getNombreResp())) {

			servicioRoResponsable.insertar(roResponsableVista);

			context.addMessage(null, new FacesMessage("Responsable Añadido",
					"El Responsable ha sido Añadido con éxito"));

			// } else {
			// FacesContext context = FacesContext.getCurrentInstance();
			//
			// context.addMessage(null, new FacesMessage(
			// FacesMessage.SEVERITY_ERROR, "Error al guardar:",
			// "El nombre de Responsable no se debe repetir"));
			// }
		} else {
			System.out.println("Responsable ya existente");

			// if (servicioRoResponsable.existeResponsablePorNombreEx(
			// roResponsableVista.getNombreResp(),
			// roResponsableVista.getCodigoResp())) {

			servicioRoResponsable.actualizar(roResponsableVista);

			context.addMessage(null, new FacesMessage(
					"Responsable Actualizado",
					"El Responsable ha sido Actualizado con éxito"));

			// } else {
			// FacesContext context = FacesContext.getCurrentInstance();
			//
			// context.addMessage(null, new FacesMessage(
			// FacesMessage.SEVERITY_ERROR, "Error al guardar:",
			// "El nombre de Responsable no se debe repetir"));
			// roResponsableVista.setNombreResp(roResponsableControlador
			// .getNombreResp());
			// }
		}

		// Obtengo datos para listas especificas al responsable(controlador)

		// Procesos
		listaRoRespProAsignadosAlResponsableVista = servicioRoRespPro
				.buscarRespProcesoPorResponsable(roResponsableControlador);
		listaCodigoProcesoResponsableVista = servicioRoRespPro
				.buscarCodigosProcesoPorResponsable(roResponsableControlador);

		// Departamento dept
		listaRoRespDeptAsignadosAlResponsableVista = servicioRoRespDepa
				.buscarRespDepartamentoPorResponsable(roResponsableControlador);
		listaCodigoDepartamentoResponsableVista = servicioRoRespDepa
				.buscarCodigosDepartamentoPorResponsable(roResponsableControlador);

		// Agencias

		listaRoRespAgiaAsignadosAlResponsableVista = servicioRoRespAgencia
				.buscarRespAgenciaPorResponsable(roResponsableControlador);
		listaCodigoAgenciaResponsableVista = servicioRoRespAgencia
				.buscarCodigosAgenciaPorResponsable(roResponsableControlador);

		// responsable
		roResponsableControlador = servicioRoResponsable
				.buscarResponsablePorNombreApellido(
						roResponsableVista.getNombreResp(),
						roResponsableVista.getApellidoResp());

		if (btnPermisosSU) {
			System.out.println("permisosu" + btnPermisosSU);
			System.out.println("procesos" + btnAgregarTodosProceso);

			if (btnAgregarTodosProceso) {
				asignarPermisoSuperUsuarioProcesos();
				System.out.println("agregando");
			} else {
				System.out.println("quitando");
				quitarPermisoSuperUsuarioProcesos();
			}

			if (btnAgregarTodosDepartamento) {
				System.out.println("V");
				asignarPermisoSuperUsuarioDepartamentos();

			} else {
				System.out.println("V");
				quitarPermisoSuperUsuarioDepartamentos();
			}
			if (btnAgregarTodosAgencia) {

				asignarPermisoSuperUsuarioAgencia();

			} else {

				quitarPermisoSuperUsuarioAgencia();
			}

		}

		// System.out.println("su" +
		// roResponsableControlador.getPermisoSuPros());
		if (btnAgregarTodosAgencia && btnAgregarTodosDepartamento
				&& btnAgregarTodosProceso) {
			btnPermisosSU = false;
		}

		servicioRoResponsable.actualizar(roResponsableControlador);

		exitoGuardar();

		// context.addMessage(null, new FacesMessage("Permisos",
		// "El Responsable ha sido Actualizado con éxito"));

	}

	public void asignarPermisoSuperUsuarioProcesos() {
		roResponsableControlador.setPermisoSuPros(1.00);

		// Asigno el responsable de la vista al objeto a ingresar

		// si el responsable no tiene asignado ningun proceso, le asigno
		// todos.

		System.out.println("listaRoRespProAsignadosAlResponsableVista"
				+ listaRoRespProAsignadosAlResponsableVista.size());
		if (listaRoRespProAsignadosAlResponsableVista.isEmpty()) {

			for (RoProceso pros : listaRoProcesoTodos) {

				RoRespPro respPro = new RoRespPro();
				respPro.setRoProceso(pros);
				respPro.setRoResponsable(roResponsableControlador);
				servicioRoRespPro.insertar(respPro);

				// RoResponsable roResponsableAux=new RoResponsable();
				// roResponsableAux=
				// servicioRoResponsable.buscarResponsablePorNombreApellido(roResponsableControlador.getNombreResp(),
				// roResponsableControlador.getApellidoResp());

			}

		}

		// si el responsable ya tiene asignado procesos, le asigno los que
		// le faltan para tener todos

		else {
			RoRespPro respPro = new RoRespPro();
			respPro.setRoResponsable(roResponsableControlador);

			// igualo temporalemente ambas listas para luego borrar los
			// valores repetidos
			listaCodigoProcesoPorAsignarResponsableVista = listaCodigoProcesoTodos;

			for (int j = 0; j < listaCodigoProcesoResponsableVista.size(); j++) {

				// encuentro procesos no repetidos
				if (listaCodigoProcesoTodos
						.contains(listaCodigoProcesoResponsableVista.get(j))) {

					listaCodigoProcesoPorAsignarResponsableVista
							.remove(listaCodigoProcesoResponsableVista.get(j));

				}
			}

			// asigno procesos faltantes al responsable vista (SU)

			for (RoProceso pros : listaRoProcesoTodos) {

				for (int j = 0; j < listaCodigoProcesoPorAsignarResponsableVista
						.size(); j++) {
					if (pros.getCodigoPros() == listaCodigoProcesoPorAsignarResponsableVista
							.get(j)) {
						respPro.setRoProceso(pros);
						servicioRoRespPro.insertar(respPro);
					}

				}

			}

			roResponsableControlador.setPermisoSuPros(1.00);

		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Permiso SU Actualizado",
				"Agregado Permiso SU - PROCESOS"));
	}

	public void quitarPermisoSuperUsuarioProcesos() {

		// listaCodigoProcesoResponsableVista();
		System.out.println("listaRoRespProAsignadosAlResponsableVista"
				+ listaRoRespProAsignadosAlResponsableVista.size());

		for (RoRespPro respPro2 : listaRoRespProAsignadosAlResponsableVista) {
			servicioRoRespPro.eliminar(respPro2);
		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Permiso SU Actualizado",
				"Eliminado Permiso SU - PROCESOS"));

		roResponsableControlador.setPermisoSuPros(0.00);
	}

	public void asignarPermisoSuperUsuarioDepartamentos() {
		roResponsableControlador.setPermisoSuDept(1.00);

		// Asigno el responsable de la vista al objeto a ingresar

		// si el responsable no tiene asignado ningun departamento, le asigno
		// todos.

		// listaRoRespDeptAsignadosAlResponsableVista;
		System.out.println("listaRoRespDeptAsignadosAlResponsableVista"
				+ listaRoRespDeptAsignadosAlResponsableVista.size());
		if (listaRoRespDeptAsignadosAlResponsableVista.isEmpty()) {

			for (RoDepartamento dept : listaRoDepartamentoTodos) {
				RoRespDepa respDept = new RoRespDepa();
				respDept.setRoDepartamento(dept);
				respDept.setRoResponsable(roResponsableControlador);
				servicioRoRespDepa.insertar(respDept);

			}

		}

		// si el responsable ya tiene asignado departamentos, le asigno los que
		// le faltan para tener todos

		else {
			RoRespDepa respDept = new RoRespDepa();
			respDept.setRoResponsable(roResponsableControlador);

			// igualo temporalemente ambas listas para luego borrar los
			// valores repetidos

			listaCodigoDepartamentoPorAsignarResponsableVista = listaCodigoDepartamentoTodos;

			for (int j = 0; j < listaCodigoDepartamentoResponsableVista.size(); j++) {

				// encuentro departamentos no repetidos
				if (listaCodigoDepartamentoTodos
						.contains(listaCodigoDepartamentoResponsableVista
								.get(j))) {
					listaCodigoDepartamentoPorAsignarResponsableVista
							.remove(listaCodigoDepartamentoResponsableVista
									.get(j));

				}
			}

			// asigno departamentos faltantes al responsable vista (SU)

			for (RoDepartamento dept : listaRoDepartamentoTodos) {

				for (int j = 0; j < listaCodigoDepartamentoPorAsignarResponsableVista
						.size(); j++) {
					if (dept.getCodigoDept() == listaCodigoDepartamentoPorAsignarResponsableVista
							.get(j)) {
						respDept.setRoDepartamento(dept);
						servicioRoRespDepa.insertar(respDept);

					}

				}

			}

			roResponsableControlador.setPermisoSuDept(1.00);

		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Permiso SU Actualizado",
				"Agregado Permiso SU - DEPARTAMENTO"));
	}

	public void quitarPermisoSuperUsuarioDepartamentos() {

		// listaCodigodepartamentoResponsableVista();
		System.out.println("listaRoRespDeptAsignadosAlResponsableVista"
				+ listaRoRespDeptAsignadosAlResponsableVista.size());

		for (RoRespDepa respDept2 : listaRoRespDeptAsignadosAlResponsableVista) {
			servicioRoRespDepa.eliminar(respDept2);
		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Permiso SU Actualizado",
				"Eliminado Permiso SU - DEPARTAMENTOS"));

		roResponsableControlador.setPermisoSuDept(0.00);
	}

	public void asignarPermisoSuperUsuarioAgencia() {
		roResponsableControlador.setPermisoSuAgia(1.00);

		// Asigno el responsable de la vista al objeto a ingresar

		// si el responsable no tiene asignado ningun agencia, le asigno
		// todos.

		System.out.println("listaRoRespAgiaAsignadosAlResponsableVista"
				+ listaRoRespAgiaAsignadosAlResponsableVista.size());
		if (listaRoRespAgiaAsignadosAlResponsableVista.isEmpty()) {

			for (RoAgencia agia : listaRoAgenciaTodos) {
				RoRespAgencia respAgia = new RoRespAgencia();
				respAgia.setRoAgencia(agia);
				respAgia.setRoResponsable(roResponsableControlador);
				servicioRoRespAgencia.insertar(respAgia);

			}

		}

		// si el responsable ya tiene asignado agencias, le asigno los que
		// le faltan para tener todos

		else {
			RoRespAgencia respAgia = new RoRespAgencia();
			respAgia.setRoResponsable(roResponsableControlador);

			// igualo temporalemente ambas listas para luego borrar los
			// valores repetidos
			listaCodigoAgenciaPorAsignarResponsableVista = listaCodigoAgenciaTodos;

			for (int j = 0; j < listaCodigoAgenciaResponsableVista.size(); j++) {

				// encuentro agencias no repetidos
				if (listaCodigoAgenciaTodos
						.contains(listaCodigoAgenciaResponsableVista.get(j))) {
					listaCodigoAgenciaPorAsignarResponsableVista
							.remove(listaCodigoAgenciaResponsableVista.get(j));

				}
			}

			// asigno agencias faltantes al responsable vista (SU)

			for (RoAgencia agencias : listaRoAgenciaTodos) {

				for (int j = 0; j < listaCodigoAgenciaPorAsignarResponsableVista
						.size(); j++) {
					if (agencias.getCodigoAgia() == listaCodigoAgenciaPorAsignarResponsableVista
							.get(j)) {
						respAgia.setRoAgencia(agencias);
						servicioRoRespAgencia.insertar(respAgia);
					}

				}

			}

			roResponsableControlador.setPermisoSuAgia(1.00);

		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Permiso SU Actualizado",
				"Agregado Permiso SU - AGENCIAS"));
	}

	public void quitarPermisoSuperUsuarioAgencia() {

		// listaCodigoagenciaResponsableVista();
		System.out.println("listaRoRespAgiaAsignadosAlResponsableVista"
				+ listaRoRespAgiaAsignadosAlResponsableVista.size());

		for (RoRespAgencia respAgia2 : listaRoRespAgiaAsignadosAlResponsableVista) {
			servicioRoRespAgencia.eliminar(respAgia2);
		}

		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Permiso SU Actualizado",
				"Eliminado Permiso SU - AGENCIAS"));

		roResponsableControlador.setPermisoSuAgia(0.00);
	}

	public void exitoGuardar() {

		roResponsableVista = new RoResponsable();
		roResponsablesTodos = servicioRoResponsable.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlResponsable = false;
		// roResponsableControlador = new RoResponsable();

	}

	public void eliminarResponsable() {
		System.out.println("Entro a eliminar el responsable");
		// servicioRoResponsable.eliminar(roResponsableVista);

		try {
			System.out.println("Procesando eliminacion de responsable");

			servicioRoResponsable.eliminar(roResponsableVista);
			roResponsableVista = new RoResponsable();
			roResponsablesTodos = servicioRoResponsable.buscarTodos();
			// idEstadoSeleccionado = 1;
			// idUsuarioSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlResponsable = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Responsable Eliminado",
					"El Responsable ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"Este registro tiene atados a el una o mas responsabilidades, no se puede eliminar."));
		}
	}

	public void nuevoResponsableVista() {

		System.out.println("se procede a agregar un nuevo responsable");

		tipoGuardar = true;
		roResponsableVista = new RoResponsable();
		// idEstadoSeleccionado = 1;
		// idUsuarioSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlResponsable = true;

		roResponsableVista.setPermisoSuAgia(0.00);
		roResponsableVista.setPermisoSuDept(0.00);
		roResponsableVista.setPermisoSuPros(0.00);
		btnPermisosSU = false;
		btnAgregarTodosAgencia = false;
		btnAgregarTodosDepartamento = false;
		btnAgregarTodosProceso = false;

	}

	public void cancelar() {
		nuevoResponsableVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		btnPermisosSU = false;
		pnlResponsable = false;
		RequestContext.getCurrentInstance().update("formResponsable");
	}

	public void editarResponsableVista() {
		System.out.println("se procede a editar el responsable");

		ponerPrimeroSisUsuarioActual();

		roResponsableControlador.setNombreResp(roResponsableVista
				.getNombreResp());

		tipoGuardar = false;
		idEstadoSeleccionado = (int) roResponsableVista.getGenEstado()
				.getCodigoEsta();
		idTipoResponsable = roResponsableVista.getRoTipoResp().getCodigoTres();

		btnGuardar = false;
		btnCancelar = false;
		pnlResponsable = true;
		btnAnadir = true;
		

		if (servicioRoResponsable.buscarPorId(
				roResponsableVista.getCodigoResp()).getPermisoSuPros() == 1) {
			btnAgregarTodosProceso = true;
			System.out
					.println("El permiso SU procesos para el usuario es verdadero");
		} else {
			btnAgregarTodosProceso = false;
		}

		if (servicioRoResponsable.buscarPorId(
				roResponsableVista.getCodigoResp()).getPermisoSuDept() == 1) {
			btnAgregarTodosDepartamento = true;
			System.out
					.println("El permiso SU departamentos para el usuario es verdadero");
		} else {
			btnAgregarTodosDepartamento = false;
		}

		if (servicioRoResponsable.buscarPorId(
				roResponsableVista.getCodigoResp()).getPermisoSuAgia() == 1) {
			btnAgregarTodosAgencia = true;
			System.out
					.println("El permiso SU agencias para el usuario es verdadero");
		} else {
			btnAgregarTodosAgencia = false;
		}

		if (btnAgregarTodosAgencia || btnAgregarTodosDepartamento
				|| btnAgregarTodosProceso) {
			btnPermisosSU = true;
			habilitarBtnsSU();

		} else {
			btnPermisosSU = false;
			habilitarBtnsSU();

		}
		System.out.println("" + btnAgregarTodosProceso);
		System.out.println("" + btnAgregarTodosDepartamento);

		System.out.println("" + btnAgregarTodosAgencia);
		System.out.println("" + btnPermisosSU);
	}

	public void ponerPrimeroSisUsuarioActual() {

		boolean datosCorrectos = false;
		datosCorrectos = servicioSisUsuario
				.existeUsuarioPorNombreApellidoResponsable(
						roResponsableVista.getNombreResp(),
						roResponsableVista.getApellidoResp());

		if (datosCorrectos) {
			System.out.println("Reorganizando cmb usuario");
			List<SisUsuario> usuariosTodosAux = new ArrayList<SisUsuario>();
			SisUsuario sisUsuario = new SisUsuario();
			int index = 0;

			
			usuariosTodosAux = usuariosTodos;

			sisUsuario = servicioSisUsuario
					.buscarUsuarioPorNombreApellidoResponsable(
							roResponsableVista.getNombreResp(),
							roResponsableVista.getApellidoResp());

			System.out.println("usuario:"
					+ sisUsuario.getNombreCompletoUsua());

			
			for (int i = 0; i < usuariosTodos.size(); i++) {
				if (usuariosTodos.get(i).getNombreCompletoUsua()
						.equals(sisUsuario.getNombreCompletoUsua())
						&& usuariosTodos.get(i).getCodigoUsua() == sisUsuario
								.getCodigoUsua()) {

					index = i;
					System.out.println(index);


				}
			}
			// usuariosTodosAux.remove(index);
			if (!usuariosTodosAux.get(index).equals(sisUsuario)) {
				usuariosTodosAux.set(index, usuariosTodos.get(0));
				usuariosTodosAux.set(0, sisUsuario);
				usuariosTodos = usuariosTodosAux;
			}

		} else
		{
			System.out.println("Se vuelve a consultar cmb usuarios");

			usuariosTodos = servicioSisUsuario.buscarTodos();

		}

	}

	public void habilitarBtnsSU() {
		if (btnPermisosSU) {
			mostrarBtnSUAgencias = true;
			mostrarBtnSUDepartamentos = true;
			mostrarBtnSUProcesos = true;
		} else {
			mostrarBtnSUAgencias = false;
			mostrarBtnSUDepartamentos = false;
			mostrarBtnSUProcesos = false;
		}

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

	public RoResponsable getRoResponsableVista() {
		return roResponsableVista;
	}

	public void setRoResponsableVista(RoResponsable roResponsableVista) {
		this.roResponsableVista = roResponsableVista;
	}

	public RoResponsable getRoResponsableControlador() {
		return roResponsableControlador;
	}

	public void setRoResponsableControlador(
			RoResponsable roResponsableControlador) {
		this.roResponsableControlador = roResponsableControlador;
	}

	public List<RoResponsable> getResponsablesTodos() {
		return roResponsablesTodos;
	}

	public void setResponsablesTodos(List<RoResponsable> ResponsablesTodos) {
		this.roResponsablesTodos = ResponsablesTodos;
	}

	public int getIdResponsableSeleccionado() {
		return idResponsableSeleccionado;
	}

	public void setIdResponsableSeleccionado(int idResponsableSeleccionado) {
		this.idResponsableSeleccionado = idResponsableSeleccionado;
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

	public int getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	public void setIdEstadoSeleccionado(int idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlResponsable() {
		return pnlResponsable;
	}

	public void setPnlResponsable(boolean pnlResponsable) {
		this.pnlResponsable = pnlResponsable;
	}

	public List<SisUsuario> getUsuariosTodos() {
		return usuariosTodos;
	}

	public void setUsuariosTodos(List<SisUsuario> usuariosTodos) {
		this.usuariosTodos = usuariosTodos;
	}

	public int getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}

	public void setIdUsuarioSeleccionado(int idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}

	public int getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
	}

	public List<RoResponsable> getRoResponsablesTodos() {
		return roResponsablesTodos;
	}

	public void setRoResponsablesTodos(List<RoResponsable> roResponsablesTodos) {
		this.roResponsablesTodos = roResponsablesTodos;
	}

	public int getIdTipoResponsable() {
		return idTipoResponsable;
	}

	public void setIdTipoResponsable(int idTipoResponsable) {
		this.idTipoResponsable = idTipoResponsable;
	}

	public List<RoTipoResp> getTipoRespTodos() {
		return tipoRespTodos;
	}

	public void setTipoRespTodos(List<RoTipoResp> tipoRespTodos) {
		this.tipoRespTodos = tipoRespTodos;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public boolean isBtnAgregarTodosProceso() {
		return btnAgregarTodosProceso;
	}

	public void setBtnAgregarTodosProceso(boolean btnAgregarTodosProceso) {
		this.btnAgregarTodosProceso = btnAgregarTodosProceso;
	}

	public boolean isBtnAgregarTodosDepartamento() {
		return btnAgregarTodosDepartamento;
	}

	public void setBtnAgregarTodosDepartamento(
			boolean btnAgregarTodosDepartamento) {
		this.btnAgregarTodosDepartamento = btnAgregarTodosDepartamento;
	}

	public boolean isBtnAgregarTodosAgencia() {
		return btnAgregarTodosAgencia;
	}

	public void setBtnAgregarTodosAgencia(boolean btnAgregarTodosAgencia) {
		this.btnAgregarTodosAgencia = btnAgregarTodosAgencia;
	}

	public boolean isBtnPermisosSU() {
		return btnPermisosSU;
	}

	public void setBtnPermisosSU(boolean btnPermisosSU) {
		this.btnPermisosSU = btnPermisosSU;
	}

	public boolean isMostrarBtnSUProcesos() {
		return mostrarBtnSUProcesos;
	}

	public void setMostrarBtnSUProcesos(boolean mostrarBtnSUProcesos) {
		this.mostrarBtnSUProcesos = mostrarBtnSUProcesos;
	}

	public boolean isMostrarBtnSUDepartamentos() {
		return mostrarBtnSUDepartamentos;
	}

	public void setMostrarBtnSUDepartamentos(boolean mostrarBtnSUDepartamentos) {
		this.mostrarBtnSUDepartamentos = mostrarBtnSUDepartamentos;
	}

	public boolean isMostrarBtnSUAgencias() {
		return mostrarBtnSUAgencias;
	}

	public void setMostrarBtnSUAgencias(boolean mostrarBtnSUAgencias) {
		this.mostrarBtnSUAgencias = mostrarBtnSUAgencias;
	}

}
