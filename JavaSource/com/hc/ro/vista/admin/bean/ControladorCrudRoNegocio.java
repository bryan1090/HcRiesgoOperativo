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

import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.GenEstado;
import com.hc.ro.modelo.GenNivelArbol;
import com.hc.ro.modelo.RoNegoPro;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoTipoNegocio;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioGenNivelArbol;
import com.hc.ro.negocio.ServicioRoNegoPros;
import com.hc.ro.negocio.ServicioRoNegocio;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoTipoNegocio;

@ManagedBean
@ViewScoped
public class ControladorCrudRoNegocio {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudNegocio.jsf";
	// NEGOCIO
	@EJB
	ServicioRoNegocio servicioRoNegocio;
	@EJB
	ServicioGenEstado servicioGenEstado;
	@EJB
	ServicioGenNivelArbol servicioGenNivelArbol;
	@EJB
	ServicioRoTipoNegocio servicioRoTipoNegocio;
	@EJB
	ServicioRoProceso servicioRoProceso;
	@EJB
	ServicioRoNegoPros servicioRoNegoPros;

	// VARIABLES
	private RoNegocio roNegocioVista;
	private RoNegocio roNegocioControlador;
	private List<RoNegocio> NegociosTodos;
	private List<RoNegocio> subNegociosTodos;
	private List<GenNivelArbol> nivelesArbolTodos;
	private List<RoTipoNegocio> tipoNegociosTodos;
	private List<GenEstado> estadosTodos;
	private List<RoNegoPro> procesosNegocio;
	private int idNegocioSeleccionado;
	private int idEstadoSeleccionado;
	private int idTipoNegocioSeleccionado;
	private int idNivelArbolSeleccionado;
	private boolean tipoGuardar;
	private TreeNode arbolNegocioVista;
	private TreeNode arbolNegocioSeleccionado;
	private boolean estaExpandido;
	private String nombreSeleccionado;
	private List<TreeNode> nodosTodos;
	// private String numeroPadre;
	// private String numeroHijo;
	// arbom procesos
	private TreeNode arbolProcesoVista;
	private TreeNode[] arbolProcesosSeleccionados;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlNegocio;
	private boolean txtNumero;
	private ArrayList<TreeNode> nodosTodosProc;

	//
	public ControladorCrudRoNegocio() {
		super();

		arbolProcesoVista = new DefaultTreeNode();
		roNegocioControlador = new RoNegocio();
		roNegocioVista = new RoNegocio();
		NegociosTodos = new ArrayList<RoNegocio>();
		subNegociosTodos = new ArrayList<RoNegocio>();
		nivelesArbolTodos = new ArrayList<GenNivelArbol>();
		tipoNegociosTodos = new ArrayList<RoTipoNegocio>();
		estadosTodos = new ArrayList<GenEstado>();
		arbolNegocioVista = new DefaultTreeNode("Root", null);
		arbolNegocioSeleccionado = new DefaultTreeNode();
		nombreSeleccionado = new String();
		// numeroPadre = new String();

	}

	@PostConstruct
	public void PostControladorCrudRoNegocio() {
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
		NegociosTodos = servicioRoNegocio.buscarTodos();
		nivelesArbolTodos = servicioGenNivelArbol.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		tipoNegociosTodos = servicioRoTipoNegocio.buscarTodos();
		estaExpandido = false;
		btnAnadir = true;
		btnCancelar = true;
		btnGuardar = true;
		pnlNegocio = false;
		txtNumero = true;
		llenarArbol();
		llenarArbolProceso();

	}

	// METODOS

	/**
	 * Llena el arbol de procesos para poder seleccionarlos con respecto a cada
	 * negocio
	 */

	public void recursivaArbolProceso(List<RoProceso> Procesos, TreeNode padre) {
		if (!Procesos.isEmpty()) {
			RoProceso Proceso = new RoProceso();
			for (int i = 0; i < Procesos.size(); i++) {
				Proceso = Procesos.get(i);
				nodosTodosProc.add(new DefaultTreeNode(Proceso.getNombrePros(),
						padre));
				nodosTodosProc.get(nodosTodosProc.size() - 1).setExpanded(true);
				List<RoProceso> ProcesosAux = new ArrayList<RoProceso>();
				ProcesosAux = servicioRoProceso.buscarProcesoPorPadre(Proceso
						.getNombrePros());
				try {
					nodosTodosProc.get(nodosTodosProc.size() - 1).setSelected(
							false);
					for (TreeNode it : arbolProcesosSeleccionados) {

						if (nodosTodosProc.get(nodosTodosProc.size() - 1)
								.equals(it)) {
							nodosTodosProc.get(nodosTodosProc.size() - 1)
									.setSelected(true);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				if (ProcesosAux != null) {

					recursivaArbolProceso(ProcesosAux,
							nodosTodosProc.get(nodosTodosProc.size() - 1));
				}
			}
		}
	}

	public void llenarArbolProceso() {
		arbolProcesoVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Procesos",
				arbolProcesoVista);
		arbolVirtual.setExpanded(true);
		arbolVirtual.setSelectable(false);
		nodosTodosProc = new ArrayList<TreeNode>();
		recursivaArbolProceso(
				servicioRoProceso.buscarProcesoPorPadre("Procesos"),
				arbolVirtual);
	}

	public void guardarProcesosMarcados() {

		List<RoNegoPro> listaborrar = new ArrayList<RoNegoPro>();
		listaborrar = servicioRoNegoPros.buscarPorNegocio(roNegocioVista
				.getNombreNego());
		int z = listaborrar.size();
		for (int i = 0; i < z; i++) {
			servicioRoNegoPros.eliminar(listaborrar.get(i));
		}
		for (TreeNode nodo : arbolProcesosSeleccionados) {
			RoNegoPro negoPro = new RoNegoPro();
			negoPro.setRoNegocio(roNegocioVista);
			negoPro.setRoProceso(servicioRoProceso.buscarProcesoPorNombre(nodo
					.getData().toString()));
			servicioRoNegoPros.insertar(negoPro);
		}
	}

	public void cargarProcesosSeleccionados() {

		List<RoNegoPro> listaNegoPro = new ArrayList<RoNegoPro>();
		listaNegoPro = servicioRoNegoPros.buscarPorNegocio(roNegocioVista
				.getNombreNego());
		arbolProcesosSeleccionados = new TreeNode[listaNegoPro.size()];
		int count = 0;
		for (RoNegoPro item : listaNegoPro) {
			TreeNode arbolPrc = new DefaultTreeNode();
			for (TreeNode nodo : nodosTodosProc) {
				if (nodo.getData().toString()
						.equals(item.getRoProceso().getNombrePros())) {
					arbolPrc = nodo;
					break;
				}
			}
			arbolProcesosSeleccionados[count] = arbolPrc;
			count++;
		}
		llenarArbolProceso();
	}

	/**
	 * Guarda el Negocio, en caso de que sea uno nuevo lo inserta de lo
	 * contrario lo actualiza.
	 */

	public void recursivaArbol(List<RoNegocio> Negocios, TreeNode padre) {
		if (Negocios.isEmpty() == false) {
			RoNegocio Negocio = new RoNegocio();
			for (int i = 0; i < Negocios.size(); i++) {
				Negocio = Negocios.get(i);
				nodosTodos.add(new DefaultTreeNode(Negocio.getNombreNego(),
						padre));
				List<RoNegocio> NegociosAux = new ArrayList<RoNegocio>();
				NegociosAux = servicioRoNegocio.buscarNegocioPorPadre(Negocio
						.getNombreNego());
				if (NegociosAux != null) {
					if (nodosTodos.get(nodosTodos.size() - 1).getData()
							.equals(nombreSeleccionado)) {
						nodosTodos.get(nodosTodos.size() - 1).setSelected(true);
						arbolNegocioSeleccionado = nodosTodos.get(nodosTodos
								.size() - 1);

						nodosTodos.get(nodosTodos.size() - 1).setExpanded(
								estaExpandido);
					}
					recursivaArbol(NegociosAux,
							nodosTodos.get(nodosTodos.size() - 1));
				}
			}
		}

	}

	public void seleccionarNegocio() {

		roNegocioVista = new RoNegocio();

		/*
		 * roNegocioVista = servicioRoNegocio
		 * .buscarNegocioPorNombre(arbolNegocioSeleccionado.getData()
		 * .toString()); if(roNegocioVista.equals(null)){ roNegocioVista=new
		 * RoNegocio(); }
		 */

		subNegociosTodos = servicioRoNegocio
				.buscarNegocioPorPadre(arbolNegocioSeleccionado.getData()
						.toString());

		nombreSeleccionado = arbolNegocioSeleccionado.getData().toString();

		// if (nombreSeleccionado.equals("Negocios")) {
		// numeroPadre = "";
		// } else {
		// numeroPadre = servicioRoNegocio.buscarNegocioPorNombre(
		// nombreSeleccionado).getNumeroNego();
		// }
		btnAnadir = false;
		pnlNegocio = false;

		

	}

	public void llenarArbol() {
		NegociosTodos = servicioRoNegocio.buscarTodos();
		arbolNegocioVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Negocios",
				arbolNegocioVista);
		if (nombreSeleccionado.equals("Negocios")) {
			arbolVirtual.setExpanded(estaExpandido);
			arbolVirtual.setSelected(true);
		}
		nodosTodos = new ArrayList<TreeNode>();
		recursivaArbol(servicioRoNegocio.buscarNegocioPorPadre("Negocios"),
				arbolVirtual);

	}

	public void guardarNegocio() {
		// roNegocioVista.setNumeroNego(numeroPadre.concat(numeroHijo));
		roNegocioVista.setRoTipoNegocio(servicioRoTipoNegocio
				.buscarPorId(idTipoNegocioSeleccionado));
		roNegocioVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		roNegocioVista.setGenNivelArbol(servicioGenNivelArbol
				.buscarPorId(idNivelArbolSeleccionado));
		roNegocioVista.setAncestroNego(arbolNegocioSeleccionado.getData()
				.toString());
		if (tipoGuardar) {
			if (servicioRoNegocio.existeNegocioPorNombre(roNegocioVista
					.getNombreNego())) {
				servicioRoNegocio.insertar(roNegocioVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage("growl1", new FacesMessage("Negocio Añadido",
						"El Negocio ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage("growl1", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y número de Negocio no se deben repetir"));
			}
		} else {

			if (servicioRoNegocio.existeNegocioPorNombreEx(
					roNegocioVista.getNombreNego(),
					roNegocioVista.getCodigoNego())) {

				servicioRoNegocio.actualizar(roNegocioVista);
				List<RoNegocio> listaNegociosHijos = servicioRoNegocio
						.buscarNegocioPorPadre(roNegocioControlador
								.getNombreNego());
				if (listaNegociosHijos.size() != 0) {

					for (RoNegocio hijo : listaNegociosHijos) {
						hijo.setAncestroNego(roNegocioVista.getNombreNego());
						servicioRoNegocio.actualizar(hijo);
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage("growl1", new FacesMessage(
						"Negocio Actualizado",
						"La Negocio ha sido Actualizado con éxito"));

				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage("growl1", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y número de Negocio no se deben repetir"));
				roNegocioVista.setNombreNego(roNegocioControlador
						.getNombreNego());
				// roNegocioVista.setNumeroNego(roNegocioControlador
				// .getNumeroNego());
				// numeroHijo = roNegocioVista.getNombreNego().substring(
				// numeroPadre.length());
			}

		}

	}

	public void exitoGuardar() {
		
		roNegocioVista = servicioRoNegocio
				.buscarNegocioPorNombre(roNegocioVista.getNombreNego());
		guardarProcesosMarcados();
		estaExpandido = arbolNegocioSeleccionado.isExpanded();
		llenarArbol();
		roNegocioVista = new RoNegocio();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = nivelesArbolTodos.get(0).getCodigoGniv();
		idTipoNegocioSeleccionado = tipoNegociosTodos.get(0).getCodigoTneg();
		// idPerfilSeleccionado = sisPerfilessTodos.get(0).getCodigoPerf();
		TreeNode arbolAux;
		arbolAux = arbolNegocioSeleccionado;
		subNegociosTodos = servicioRoNegocio
				.buscarNegocioPorPadre(arbolNegocioSeleccionado.getData()
						.toString());
		while (!arbolAux.getData().toString().equals("Negocios")) {
			arbolAux = arbolAux.getParent();
			arbolAux.setExpanded(true);
		}
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlNegocio = false;
	}

	public void eliminarNegocio() {
		if (servicioRoNegocio.buscarNegocioPorPadre(
				roNegocioVista.getNombreNego()).size() == 0) {

			procesosNegocio = new ArrayList<RoNegoPro>();
			procesosNegocio = servicioRoNegoPros
					.buscarPorNegocio(roNegocioVista.getNombreNego());
			for (RoNegoPro item : procesosNegocio) {
				servicioRoNegoPros.eliminar(item);
			}

			roNegocioVista = servicioRoNegocio.buscarPorId(roNegocioVista
					.getCodigoNego());

			try {
				servicioRoNegocio.eliminar(roNegocioVista);
				roNegocioVista = new RoNegocio();
				subNegociosTodos = servicioRoNegocio
						.buscarNegocioPorPadre(arbolNegocioSeleccionado
								.getData().toString());
				estaExpandido = arbolNegocioSeleccionado.isExpanded();
				llenarArbol();
				idEstadoSeleccionado = 1;
				idEstadoSeleccionado = 1;
				idNivelArbolSeleccionado = 1;
				idTipoNegocioSeleccionado = 1;
				TreeNode arbolAux;
				arbolAux = arbolNegocioSeleccionado;

				while (!arbolAux.getData().toString().equals("Negocios")) {
					arbolAux = arbolAux.getParent();
					arbolAux.setExpanded(true);
				}
				btnGuardar = true;
				btnCancelar = true;
				pnlNegocio = false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("growl1", new FacesMessage("Negocio Eliminado",
						"El Negocio ha sido Eliminado con éxito"));
			} catch (Exception e) {
				for (RoNegoPro item : procesosNegocio) {
					servicioRoNegoPros.insertar(item);
				}

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage("growl1", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
						"No se puede eliminar un Negocio en uso"));
			}

		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage("growl1", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"No se puede eliminar un Negocio que tenga sub-Negocios"));
		}
	}

	public void nuevoNegocioVista() {
		FacesContext context = PrimeFacesContext.getCurrentInstance();
		context.addMessage(
				"mensaje",
				new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Seleccionar procesos",
						"Debe usar la tecla ctrl para seleccionar varios procesos en el árbol."));
		arbolProcesosSeleccionados = new TreeNode[0];
		cargarProcesosSeleccionados();
		llenarArbol();
		tipoGuardar = true;
		roNegocioVista = new RoNegocio();
		idEstadoSeleccionado = 1;
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idTipoNegocioSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlNegocio = true;
		txtNumero = false;
	}

	public void cancelar() {
		nuevoNegocioVista();
		btnGuardar = true;
		btnCancelar = true;
		btnAnadir = false;
		pnlNegocio = false;
		RequestContext.getCurrentInstance().update("formNegocio");
		
	}

	public void editarNegocioVista() {
		FacesContext context = PrimeFacesContext.getCurrentInstance();
		context.addMessage(
				"mensaje",
				new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Seleccionar procesos",
						"Debe usar la tecla ctrl para seleccionar varios procesos en el árbol."));
		cargarProcesosSeleccionados();
		// numeroHijo = roNegocioVista.getNumeroNego().substring(
		// numeroPadre.length());
		roNegocioControlador.setNombreNego(roNegocioVista.getNombreNego());
		// roNegocioControlador.setNumeroNego(roNegocioVista.getNumeroNego());

		tipoGuardar = false;
		idEstadoSeleccionado = (int) roNegocioVista.getGenEstado()
				.getCodigoEsta();
		idNivelArbolSeleccionado = roNegocioVista.getGenNivelArbol()
				.getCodigoGniv();
		idTipoNegocioSeleccionado = roNegocioVista.getRoTipoNegocio()
				.getCodigoTneg();

		btnGuardar = false;
		btnCancelar = false;
		pnlNegocio = true;
		btnAnadir = true;
		if (servicioRoNegocio.buscarNegocioPorPadre(
				roNegocioVista.getNombreNego()).size() == 0) {
			txtNumero = false;
		} else {
			txtNumero = true;
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

	public RoNegocio getRoNegocioVista() {
		return roNegocioVista;
	}

	public void setRoNegocioVista(RoNegocio roNegocioVista) {
		this.roNegocioVista = roNegocioVista;
	}

	public RoNegocio getRoNegocioControlador() {
		return roNegocioControlador;
	}

	public void setRoNegocioControlador(RoNegocio roNegocioControlador) {
		this.roNegocioControlador = roNegocioControlador;
	}

	public List<RoNegocio> getNegociosTodos() {
		return NegociosTodos;
	}

	public void setNegociosTodos(List<RoNegocio> NegociosTodos) {
		this.NegociosTodos = NegociosTodos;
	}

	public int getIdNegocioSeleccionado() {
		return idNegocioSeleccionado;
	}

	public void setIdNegocioSeleccionado(int idNegocioSeleccionado) {
		this.idNegocioSeleccionado = idNegocioSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public TreeNode getArbolNegocioVista() {
		return arbolNegocioVista;
	}

	public void setArbolNegocioVista(TreeNode arbolNegocioVista) {
		this.arbolNegocioVista = arbolNegocioVista;
	}

	public TreeNode getArbolNegocioSeleccionado() {
		return arbolNegocioSeleccionado;
	}

	public void setArbolNegocioSeleccionado(TreeNode arbolNegocioSeleccionado) {
		this.arbolNegocioSeleccionado = arbolNegocioSeleccionado;
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

	public int getIdTipoNegocioSeleccionado() {
		return idTipoNegocioSeleccionado;
	}

	public void setIdTipoNegocioSeleccionado(int idTipoNegocioSeleccionado) {
		this.idTipoNegocioSeleccionado = idTipoNegocioSeleccionado;
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

	public List<RoNegocio> getSubNegociosTodos() {
		return subNegociosTodos;
	}

	public void setSubNegociosTodos(List<RoNegocio> subNegociosTodos) {
		this.subNegociosTodos = subNegociosTodos;
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

	public boolean isPnlNegocio() {
		return pnlNegocio;
	}

	public void setPnlNegocio(boolean pnlNegocio) {
		this.pnlNegocio = pnlNegocio;
	}

	//
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

	public List<RoTipoNegocio> getTipoNegociosTodos() {
		return tipoNegociosTodos;
	}

	public void setTipoNegociosTodos(List<RoTipoNegocio> tipoNegociosTodos) {
		this.tipoNegociosTodos = tipoNegociosTodos;
	}

	public TreeNode getArbolProcesoVista() {
		return arbolProcesoVista;
	}

	public void setArbolProcesoVista(TreeNode arbolProcesoVista) {
		this.arbolProcesoVista = arbolProcesoVista;
	}

	public TreeNode[] getArbolProcesosSeleccionados() {
		return arbolProcesosSeleccionados;
	}

	public void setArbolProcesosSeleccionados(
			TreeNode[] arbolProcesosSeleccionados) {
		this.arbolProcesosSeleccionados = arbolProcesosSeleccionados;
	}

	public ArrayList<TreeNode> getNodosTodosProc() {
		return nodosTodosProc;
	}

	public void setNodosTodosProc(ArrayList<TreeNode> nodosTodosProc) {
		this.nodosTodosProc = nodosTodosProc;
	}

	public List<RoNegoPro> getProcesosNegocio() {
		return procesosNegocio;
	}

	public void setProcesosNegocio(List<RoNegoPro> procesosNegocio) {
		this.procesosNegocio = procesosNegocio;
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

}
