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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.GenEstado;
import com.hc.ro.modelo.GenNivelArbol;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoRespAgencia;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.RoTipoAgencia;
import com.hc.ro.modelo.RoTipoResp;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioGenNivelArbol;
import com.hc.ro.negocio.ServicioGenUbicGeo;
import com.hc.ro.negocio.ServicioRoAgencia;
import com.hc.ro.negocio.ServicioRoRespAgencia;
import com.hc.ro.negocio.ServicioRoResponsable;
import com.hc.ro.negocio.ServicioRoTipoAgencia;
import com.hc.ro.negocio.ServicioRoTipoResp;

@ManagedBean
@ViewScoped
public class ControladorCrudRoAgencia {
	// NEGOCIO
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@EJB
	ServicioGenUbicGeo servicioGenUbicGeo;
	@EJB
	ServicioRoAgencia servicioRoAgencia;
	@EJB
	ServicioGenEstado servicioGenEstado;
	@EJB
	ServicioRoTipoAgencia servicioRoTipoAgencia;
	@EJB
	ServicioGenNivelArbol servicioGenNivelArbol;
	@EJB
	ServicioRoResponsable servicioRoResponsable;
	@EJB
	ServicioRoRespAgencia servicioRoRespAgencia;
	@EJB
	ServicioRoTipoResp servicioRoTipoResp;

	// VARIABLES
	public final static String nombrePagina = "/paginas/CrudAgencia.jsf";
	private RoAgencia roAgenciaVista;
	private RoAgencia roAgenciaControlador;
	private List<RoAgencia> agenciasTodos;
	private List<RoAgencia> subAgenciasTodos;
	private List<GenNivelArbol> nivelesArbolTodos;
	private List<GenEstado> estadosTodos;
	private List<RoTipoAgencia> tipoAgenciaTodos;
	private List<RoResponsable> responsablesTodos;
	private List<String> responsablesSeleccionados;
	private List<RoRespAgencia> responsablesAgencia;
	private List<RoTipoResp> tipoRespTodos;
	private int idUbicGeoSeleccionado;
	private int idAgenciaSeleccionado;
	private int idEstadoSeleccionado;
	private int idTipoAgenciaSeleccionado;
	private int idNivelArbolSeleccionado;
	private int idTipoResponsabilidadSeleccionada;
	private boolean tipoGuardar;
	private TreeNode arbolAgenciaVista;
	private TreeNode arbolAgenciaSeleccionado;
	private boolean estaExpandido;
	private String nombreSeleccionado;
	private List<TreeNode> nodosTodos;
	// private String numeroPadre;
	// private String numeroHijo;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlAgencia;
	private boolean txtNumero;
	private SisUsuario sisUsuario;

	//
	public ControladorCrudRoAgencia() {
		super();
		responsablesSeleccionados = new ArrayList<String>();
		responsablesTodos = new ArrayList<RoResponsable>();
		responsablesAgencia = new ArrayList<RoRespAgencia>();
		roAgenciaControlador = new RoAgencia();
		roAgenciaVista = new RoAgencia();
		agenciasTodos = new ArrayList<RoAgencia>();
		subAgenciasTodos = new ArrayList<RoAgencia>();
		nivelesArbolTodos = new ArrayList<GenNivelArbol>();
		tipoAgenciaTodos = new ArrayList<RoTipoAgencia>();
		estadosTodos = new ArrayList<GenEstado>();
		arbolAgenciaVista = new DefaultTreeNode("Root", null);
		arbolAgenciaSeleccionado = new DefaultTreeNode();
		nombreSeleccionado = new String();
		// numeroPadre = new String();
		tipoRespTodos = new ArrayList<RoTipoResp>();
		sisUsuario = new SisUsuario();
	}

	@PostConstruct
	public void PostControladorCrudRoAgencia() {
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
		tipoRespTodos = servicioRoTipoResp.buscarTodos();
		responsablesTodos = servicioRoResponsable.buscarTodos();

		agenciasTodos = servicioRoAgencia.buscarTodos();
		nivelesArbolTodos = servicioGenNivelArbol.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		tipoAgenciaTodos = servicioRoTipoAgencia.buscarTodos();
		estaExpandido = false;
		btnAnadir = true;
		btnCancelar = true;
		btnGuardar = true;
		pnlAgencia = false;
		txtNumero = true;
		llenarArbol();
	}

	// METODOS
	/**
	 * Guarda el proceso, en caso de que sea uno nuevo lo inserta de lo
	 * contrario lo actualiza.
	 */

	public void recursivaArbol(List<RoAgencia> agencias, TreeNode padre) {
		if (agencias.isEmpty() == false) {
			RoAgencia agencia = new RoAgencia();
			for (int i = 0; i < agencias.size(); i++) {
				agencia = agencias.get(i);
				nodosTodos.add(new DefaultTreeNode(agencia.getNombreAgia(),
						padre));
				List<RoAgencia> agenciasAux = new ArrayList<RoAgencia>();
				agenciasAux = servicioRoAgencia.buscarAgenciaPorPadre(agencia
						.getNombreAgia());
				if (agenciasAux != null) {
					if (nodosTodos.get(nodosTodos.size() - 1).getData()
							.equals(nombreSeleccionado)) {
						nodosTodos.get(nodosTodos.size() - 1).setSelected(true);
						arbolAgenciaSeleccionado = nodosTodos.get(nodosTodos
								.size() - 1);

						nodosTodos.get(nodosTodos.size() - 1).setExpanded(
								estaExpandido);
					}
					recursivaArbol(agenciasAux,
							nodosTodos.get(nodosTodos.size() - 1));
				}
			}
		}

	}

	public void seleccionarAgencia() {

		roAgenciaVista = new RoAgencia();

		/*
		 * roAgenciaVista = servicioRoAgencia
		 * .buscarAgenciaPorNombre(arbolAgenciaSeleccionado.getData()
		 * .toString()); if(roAgenciaVista.equals(null)){ roAgenciaVista=new
		 * RoAgencia(); }
		 */

		subAgenciasTodos = servicioRoAgencia
				.buscarAgenciaPorPadre(arbolAgenciaSeleccionado.getData()
						.toString());

		nombreSeleccionado = arbolAgenciaSeleccionado.getData().toString();

		// if (nombreSeleccionado.equals("Agencias")) {
		// numeroPadre = "";
		// } else {
		// numeroPadre = servicioRoAgencia.buscarAgenciaPorNombre(
		// nombreSeleccionado).getNumeroAgia();
		// }
		btnAnadir = false;
		pnlAgencia = false;

	}

	public void llenarArbol() {
		agenciasTodos = servicioRoAgencia.buscarTodos();
		arbolAgenciaVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Agencias",
				arbolAgenciaVista);
		if (nombreSeleccionado.equals("Agencias")) {
			arbolVirtual.setExpanded(estaExpandido);
			arbolVirtual.setSelected(true);
		}
		nodosTodos = new ArrayList<TreeNode>();
		recursivaArbol(servicioRoAgencia.buscarAgenciaPorPadre("Agencias"),
				arbolVirtual);

	}

	public void guardarResponsablesAgencia() {
		roAgenciaVista = servicioRoAgencia
				.buscarAgenciaPorNombre(roAgenciaVista.getNombreAgia());
		List<RoRespAgencia> iterable = servicioRoRespAgencia
				.buscarRespAgenciasPorAgencias(roAgenciaVista);
		for (RoRespAgencia item : iterable) {
			servicioRoRespAgencia.eliminar(item);
		}

		responsablesAgencia = new ArrayList<RoRespAgencia>();
		for (String item : responsablesSeleccionados) {

			RoRespAgencia respAgen = new RoRespAgencia();
			respAgen.setRoAgencia(roAgenciaVista);
			respAgen.setRoResponsable(servicioRoResponsable.buscarPorId(Integer
					.parseInt(item)));
			responsablesAgencia.add(respAgen);
		}

		for (RoRespAgencia item1 : responsablesAgencia) {
			servicioRoRespAgencia.insertar(item1);
		}
		responsablesAgencia = new ArrayList<RoRespAgencia>();
	}

	public void guardarAgencia() {
		// roAgenciaVista.setNumeroAgia(numeroPadre.concat(numeroHijo));
		roAgenciaVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		roAgenciaVista.setRoTipoAgencia(servicioRoTipoAgencia
				.buscarPorId(idTipoAgenciaSeleccionado));
		roAgenciaVista.setGenNivelArbol(servicioGenNivelArbol
				.buscarPorId(idNivelArbolSeleccionado));
		roAgenciaVista.setAncestroAgia(arbolAgenciaSeleccionado.getData()
				.toString());
		if (tipoGuardar) {
			if (servicioRoAgencia.existeAgenciaPorNombre(roAgenciaVista
					.getNombreAgia())) {
				servicioRoAgencia.insertar(roAgenciaVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Agencia Añadida",
						"La Agencia ha sido Añadida con Exito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y código de Agencia no se deben repetir"));
			}
		} else {

			if (servicioRoAgencia.existeAgenciaPorNombreEx(
					roAgenciaVista.getNombreAgia(),
					roAgenciaVista.getCodigoAgia())) {

				servicioRoAgencia.actualizar(roAgenciaVista);
				List<RoAgencia> listaAgenciasHijos = servicioRoAgencia
						.buscarAgenciaPorPadre(roAgenciaControlador
								.getNombreAgia());
				if (listaAgenciasHijos.size() != 0) {

					for (RoAgencia hijo : listaAgenciasHijos) {
						hijo.setAncestroAgia(roAgenciaVista.getNombreAgia());
						servicioRoAgencia.actualizar(hijo);
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Agencia Actualizada",
						"La Agencia ha sido Actualizada con �xito"));

				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y número de Agencia no se deben repetir"));
				roAgenciaVista.setNombreAgia(roAgenciaControlador
						.getNombreAgia());
				// roAgenciaVista.setNumeroAgia(roAgenciaControlador
				// .getNumeroAgia());
				// numeroHijo = roAgenciaVista.getNombreAgia().substring(
				// numeroPadre.length());
			}

		}

	}

	public void exitoGuardar() {
		try {
			guardarResponsablesAgencia();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al guardar:",
							"Agencia Guardada con errores en Responsables (Vuelva a intentarlo)"));
		}

		estaExpandido = arbolAgenciaSeleccionado.isExpanded();
		llenarArbol();
		roAgenciaVista = new RoAgencia();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idTipoAgenciaSeleccionado = 1;
		TreeNode arbolAux;
		arbolAux = arbolAgenciaSeleccionado;
		subAgenciasTodos = servicioRoAgencia
				.buscarAgenciaPorPadre(arbolAgenciaSeleccionado.getData()
						.toString());
		while (!arbolAux.getData().toString().equals("Agencias")) {
			arbolAux = arbolAux.getParent();
			arbolAux.setExpanded(true);
		}
		btnGuardar = true;
		btnCancelar = true;
		btnAnadir = false;
		pnlAgencia = false;
	}

	public void eliminarAgencia() {
		if (servicioRoAgencia.buscarAgenciaPorPadre(
				roAgenciaVista.getNombreAgia()).size() == 0) {

			responsablesAgencia = new ArrayList<RoRespAgencia>();
			responsablesAgencia = servicioRoRespAgencia
					.buscarRespAgenciasPorAgencias(roAgenciaVista);
			for (RoRespAgencia item : responsablesAgencia) {
				servicioRoRespAgencia.eliminar(item);
			}
			roAgenciaVista = servicioRoAgencia.buscarPorId(roAgenciaVista
					.getCodigoAgia());
			try {
				servicioRoAgencia.eliminar(roAgenciaVista);
				roAgenciaVista = new RoAgencia();
				subAgenciasTodos = servicioRoAgencia
						.buscarAgenciaPorPadre(arbolAgenciaSeleccionado
								.getData().toString());
				estaExpandido = arbolAgenciaSeleccionado.isExpanded();
				llenarArbol();
				idEstadoSeleccionado = 1;
				idEstadoSeleccionado = 1;
				idNivelArbolSeleccionado = 1;
				idTipoAgenciaSeleccionado = 1;
				TreeNode arbolAux;
				arbolAux = arbolAgenciaSeleccionado;

				while (!arbolAux.getData().toString().equals("Agencias")) {
					arbolAux = arbolAux.getParent();
					arbolAux.setExpanded(true);
				}
				btnGuardar = true;
				btnCancelar = true;
				pnlAgencia = false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Agencia Eliminada",
						"La Agencia ha sido Eliminada con �xito"));
			} catch (Exception e) {
				for (RoRespAgencia item : responsablesAgencia) {
					servicioRoRespAgencia.insertar(item);
				}

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
						"No se puede eliminar una agencia en uso"));
			}

		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"No se puede eliminar una agencia que tenga sub-agencias"));
		}
	}

	public void nuevoAgenciaVista() {
		tipoGuardar = true;
		responsablesSeleccionados = new ArrayList<String>();
		roAgenciaVista = new RoAgencia();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idTipoAgenciaSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlAgencia = true;
		txtNumero = false;
	}

	public void cancelar() {
		nuevoAgenciaVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlAgencia = false;
		RequestContext.getCurrentInstance().update("formAgencia");
	}

	public void editarAgenciaVista() {
		responsablesSeleccionados = new ArrayList<String>();
		// numeroHijo = roAgenciaVista.getNumeroAgia().substring(
		// numeroPadre.length());
		roAgenciaControlador.setNombreAgia(roAgenciaVista.getNombreAgia());
		// roAgenciaControlador.setNumeroAgia(roAgenciaVista.getNumeroAgia());

		tipoGuardar = false;

		responsablesAgencia = servicioRoRespAgencia
				.buscarRespAgenciasPorAgencias(roAgenciaVista);

		for (RoRespAgencia item : responsablesAgencia) {
			responsablesSeleccionados.add(Integer.toString(item
					.getRoResponsable().getCodigoResp()));
		}

		idEstadoSeleccionado = (int) roAgenciaVista.getGenEstado().getCodigoEsta();
		idNivelArbolSeleccionado = roAgenciaVista.getGenNivelArbol()
				.getCodigoGniv();
		idTipoAgenciaSeleccionado = roAgenciaVista.getRoTipoAgencia()
				.getCodigoTiag();
		btnGuardar = false;
		btnCancelar = false;
		pnlAgencia = true;
		btnAnadir = true;
		if (servicioRoAgencia.buscarAgenciaPorPadre(
				roAgenciaVista.getNombreAgia()).size() == 0) {
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

	public RoAgencia getRoAgenciaVista() {
		return roAgenciaVista;
	}

	public void setRoAgenciaVista(RoAgencia roAgenciaVista) {
		this.roAgenciaVista = roAgenciaVista;
	}

	public RoAgencia getRoAgenciaControlador() {
		return roAgenciaControlador;
	}

	public void setRoAgenciaControlador(RoAgencia roAgenciaControlador) {
		this.roAgenciaControlador = roAgenciaControlador;
	}

	public List<RoAgencia> getAgenciasTodos() {
		return agenciasTodos;
	}

	public void setAgenciasTodos(List<RoAgencia> agenciasTodos) {
		this.agenciasTodos = agenciasTodos;
	}

	public int getIdUbicGeoSeleccionado() {
		return idUbicGeoSeleccionado;
	}

	public void setIdUbicGeoSeleccionado(int idUbicGeoSeleccionado) {
		this.idUbicGeoSeleccionado = idUbicGeoSeleccionado;
	}

	public int getIdAgenciaSeleccionado() {
		return idAgenciaSeleccionado;
	}

	public void setIdAgenciaSeleccionado(int idAgenciaSeleccionado) {
		this.idAgenciaSeleccionado = idAgenciaSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public TreeNode getArbolAgenciaVista() {
		return arbolAgenciaVista;
	}

	public void setArbolAgenciaVista(TreeNode arbolAgenciaVista) {
		this.arbolAgenciaVista = arbolAgenciaVista;
	}

	public TreeNode getArbolAgenciaSeleccionado() {
		return arbolAgenciaSeleccionado;
	}

	public void setArbolAgenciaSeleccionado(TreeNode arbolAgenciaSeleccionado) {
		this.arbolAgenciaSeleccionado = arbolAgenciaSeleccionado;
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

	public int getIdTipoAgenciaSeleccionado() {
		return idTipoAgenciaSeleccionado;
	}

	public void setIdTipoAgenciaSeleccionado(int idTipoAgenciaSeleccionado) {
		this.idTipoAgenciaSeleccionado = idTipoAgenciaSeleccionado;
	}

	public int getIdNivelArbolSeleccionado() {
		return idNivelArbolSeleccionado;
	}

	public void setIdNivelArbolSeleccionado(int idNivelArbolSeleccionado) {
		this.idNivelArbolSeleccionado = idNivelArbolSeleccionado;
	}

	public List<RoTipoAgencia> getTipoAgenciaTodos() {
		return tipoAgenciaTodos;
	}

	public void setTipoAgenciaTodos(List<RoTipoAgencia> tipoAgenciaTodos) {
		this.tipoAgenciaTodos = tipoAgenciaTodos;
	}

	public List<TreeNode> getNodosTodos() {
		return nodosTodos;
	}

	public void setNodosTodos(List<TreeNode> nodosTodos) {
		this.nodosTodos = nodosTodos;
	}

	public List<RoAgencia> getSubAgenciasTodos() {
		return subAgenciasTodos;
	}

	public void setSubAgenciasTodos(List<RoAgencia> subAgenciasTodos) {
		this.subAgenciasTodos = subAgenciasTodos;
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

	public boolean isPnlAgencia() {
		return pnlAgencia;
	}

	public void setPnlAgencia(boolean pnlAgencia) {
		this.pnlAgencia = pnlAgencia;
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

	public List<RoResponsable> getResponsablesTodos() {
		return responsablesTodos;
	}

	public void setResponsablesTodos(List<RoResponsable> responsablesTodos) {
		this.responsablesTodos = responsablesTodos;
	}

	public List<String> getResponsablesSeleccionados() {
		return responsablesSeleccionados;
	}

	public void setResponsablesSeleccionados(
			List<String> responsablesSeleccionados) {
		this.responsablesSeleccionados = responsablesSeleccionados;
	}

	public List<RoRespAgencia> getResponsablesAgencia() {
		return responsablesAgencia;
	}

	public void setResponsablesAgencia(List<RoRespAgencia> responsablesAgencia) {
		this.responsablesAgencia = responsablesAgencia;
	}

	public int getIdTipoResponsabilidadSeleccionada() {
		return idTipoResponsabilidadSeleccionada;
	}

	public void setIdTipoResponsabilidadSeleccionada(
			int idTipoResponsabilidadSeleccionada) {
		this.idTipoResponsabilidadSeleccionada = idTipoResponsabilidadSeleccionada;
	}

	public List<RoTipoResp> getTipoRespTodos() {
		return tipoRespTodos;
	}

	public void setTipoRespTodos(List<RoTipoResp> tipoRespTodos) {
		this.tipoRespTodos = tipoRespTodos;
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

	public SisUsuario getSisUsuario() {
		return sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

}
