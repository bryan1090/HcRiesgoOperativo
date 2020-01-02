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
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioGenNivelArbol;
import com.hc.ro.negocio.ServicioRoEvento;

@ManagedBean
@ViewScoped
public class ControladorCrudRoEvento {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudEvento.jsf";

	// Evento
	@EJB
	ServicioRoEvento servicioRoEvento;
	@EJB
	ServicioGenEstado servicioGenEstado;
	@EJB
	ServicioGenNivelArbol servicioGenNivelArbol;

	// VARIABLES
	private RoEvento roEventoVista;
	private RoEvento roEventoControlador;
	private List<RoEvento> EventosTodos;
	private List<RoEvento> subEventosTodos;
	private List<GenNivelArbol> nivelesArbolTodos;
	private List<GenEstado> estadosTodos;
	private int idEventoSeleccionado;
	private int idEstadoSeleccionado;
	private int idTipoEventoSeleccionado;
	private int idNivelArbolSeleccionado;
	private boolean tipoGuardar;
	private TreeNode arbolEventoVista;
	private TreeNode arbolEventoSeleccionado;
	private boolean estaExpandido;
	private String nombreSeleccionado;
	private List<TreeNode> nodosTodos;
	// private String numeroPadre;
	// private String numeroHijo;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlEvento;
	private boolean txtNumero;

	//
	public ControladorCrudRoEvento() {
		super();
		roEventoControlador = new RoEvento();
		roEventoVista = new RoEvento();
		EventosTodos = new ArrayList<RoEvento>();
		subEventosTodos = new ArrayList<RoEvento>();
		nivelesArbolTodos = new ArrayList<GenNivelArbol>();
		estadosTodos = new ArrayList<GenEstado>();
		arbolEventoVista = new DefaultTreeNode("Root", null);
		arbolEventoSeleccionado = new DefaultTreeNode();
		nombreSeleccionado = new String();
		// numeroPadre = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoEvento() {
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
		EventosTodos = servicioRoEvento.buscarTodos();
		nivelesArbolTodos = servicioGenNivelArbol.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		estaExpandido = false;
		btnAnadir = true;
		btnCancelar = true;
		btnGuardar = true;
		pnlEvento = false;
		txtNumero = true;
		llenarArbol();
	}

	// METODOS
	/**
	 * Guarda el Evento, en caso de que sea uno nuevo lo inserta de lo contrario
	 * lo actualiza.
	 */

	public void recursivaArbol(List<RoEvento> Eventos, TreeNode padre) {
		if (Eventos.isEmpty() == false) {
			RoEvento Evento = new RoEvento();
			for (int i = 0; i < Eventos.size(); i++) {
				Evento = Eventos.get(i);
				nodosTodos.add(new DefaultTreeNode(Evento.getNombreEven(),
						padre));
				List<RoEvento> EventosAux = new ArrayList<RoEvento>();
				EventosAux = servicioRoEvento.buscarEventoPorPadre(Evento
						.getNombreEven());
				if (EventosAux != null) {
					if (nodosTodos.get(nodosTodos.size() - 1).getData()
							.equals(nombreSeleccionado)) {
						nodosTodos.get(nodosTodos.size() - 1).setSelected(true);
						arbolEventoSeleccionado = nodosTodos.get(nodosTodos
								.size() - 1);

						nodosTodos.get(nodosTodos.size() - 1).setExpanded(
								estaExpandido);
					}
					recursivaArbol(EventosAux,
							nodosTodos.get(nodosTodos.size() - 1));
				}
			}
		}

	}

	public void seleccionarEvento() {

		roEventoVista = new RoEvento();

		/*
		 * roEventoVista = servicioRoEvento
		 * .buscarEventoPorNombre(arbolEventoSeleccionado.getData()
		 * .toString()); if(roEventoVista.equals(null)){ roEventoVista=new
		 * RoEvento(); }
		 */

		subEventosTodos = servicioRoEvento
				.buscarEventoPorPadre(arbolEventoSeleccionado.getData()
						.toString());

		nombreSeleccionado = arbolEventoSeleccionado.getData().toString();

		// if (nombreSeleccionado.equals("Eventos")) {
		// numeroPadre = "";
		// } else {
		// numeroPadre = servicioRoEvento.buscarEventoPorNombre(
		// nombreSeleccionado).getNumeroEven();
		// }
		btnAnadir = false;
		pnlEvento = false;

	}

	public void llenarArbol() {
		EventosTodos = servicioRoEvento.buscarTodos();
		arbolEventoVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Eventos", arbolEventoVista);
		if (nombreSeleccionado.equals("Eventos")) {
			arbolVirtual.setExpanded(estaExpandido);
			arbolVirtual.setSelected(true);
		}
		nodosTodos = new ArrayList<TreeNode>();
		recursivaArbol(servicioRoEvento.buscarEventoPorPadre("Eventos"),
				arbolVirtual);

	}

	public void guardarEvento() {
		// roEventoVista.setNumeroEven(numeroPadre.concat(numeroHijo));
		roEventoVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		roEventoVista.setGenNivelArbol(servicioGenNivelArbol
				.buscarPorId(idNivelArbolSeleccionado));
		roEventoVista.setAncestroEven(arbolEventoSeleccionado.getData()
				.toString());
		if (tipoGuardar) {
			if (servicioRoEvento.existeEventoPorNombre(roEventoVista
					.getNombreEven())) {
				servicioRoEvento.insertar(roEventoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Evento Añadido",
						"El Evento ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y número de Evento no se deben repetir"));
			}
		} else {

			if (servicioRoEvento.existeEventoPorNombreEx(
					roEventoVista.getNombreEven(),
					roEventoVista.getCodigoEven())) {

				servicioRoEvento.actualizar(roEventoVista);
				List<RoEvento> listaEventosHijos = servicioRoEvento
						.buscarEventoPorPadre(roEventoControlador
								.getNombreEven());
				if (listaEventosHijos.size() != 0) {

					for (RoEvento hijo : listaEventosHijos) {
						hijo.setAncestroEven(roEventoVista.getNombreEven());
						servicioRoEvento.actualizar(hijo);
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Evento Actualizado",
						"El Evento ha sido Actualizado con éxito"));

				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre y número de Evento no se deben repetir"));
				roEventoVista
						.setNombreEven(roEventoControlador.getNombreEven());
				// roEventoVista
				// .setNumeroEven(roEventoControlador.getNumeroEven());
				// numeroHijo = roEventoVista.getNombreEven().substring(
				// numeroPadre.length());
			}

		}

	}

	public void exitoGuardar() {
		estaExpandido = arbolEventoSeleccionado.isExpanded();
		llenarArbol();
		roEventoVista = new RoEvento();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idTipoEventoSeleccionado = 1;
		TreeNode arbolAux;
		arbolAux = arbolEventoSeleccionado;
		subEventosTodos = servicioRoEvento
				.buscarEventoPorPadre(arbolEventoSeleccionado.getData()
						.toString());
		while (!arbolAux.getData().toString().equals("Eventos")) {
			arbolAux = arbolAux.getParent();
			arbolAux.setExpanded(true);
		}
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlEvento = false;
	}

	public void eliminarEvento() {

		if (servicioRoEvento
				.buscarEventoPorPadre(roEventoVista.getNombreEven()).size() == 0) {
			try {
				servicioRoEvento.eliminar(roEventoVista);
				roEventoVista = new RoEvento();
				subEventosTodos = servicioRoEvento
						.buscarEventoPorPadre(arbolEventoSeleccionado.getData()
								.toString());
				estaExpandido = arbolEventoSeleccionado.isExpanded();
				llenarArbol();
				idEstadoSeleccionado = 1;
				idEstadoSeleccionado = 1;
				idNivelArbolSeleccionado = 1;
				idTipoEventoSeleccionado = 1;
				TreeNode arbolAux;
				arbolAux = arbolEventoSeleccionado;

				while (!arbolAux.getData().toString().equals("Eventos")) {
					arbolAux = arbolAux.getParent();
					arbolAux.setExpanded(true);
				}
				btnGuardar = true;
				btnCancelar = true;
				pnlEvento = false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Evento Eliminado",
						"El Evento ha sido Eliminado con éxito"));
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
						"No se puede eliminar un Evento en uso"));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"No se puede eliminar un Evento que tenga sub-Eventos"));
		}
	}

	public void nuevoEventoVista() {
		tipoGuardar = true;
		roEventoVista = new RoEvento();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idTipoEventoSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlEvento = true;
		txtNumero = false;
	}

	public void cancelar() {
		nuevoEventoVista();
		btnGuardar = true;
		btnCancelar = true;
		pnlEvento = false;
		btnAnadir = false;
		RequestContext.getCurrentInstance().update("formEvento");
	}

	public void editarEventoVista() {
		// numeroHijo = roEventoVista.getNumeroEven().substring(
		// numeroPadre.length());
		roEventoControlador.setNombreEven(roEventoVista.getNombreEven());
		// roEventoControlador.setNumeroEven(roEventoVista.getNumeroEven());

		tipoGuardar = false;
		idEstadoSeleccionado = (int) roEventoVista.getGenEstado().getCodigoEsta();
		idNivelArbolSeleccionado = roEventoVista.getGenNivelArbol()
				.getCodigoGniv();

		btnGuardar = false;
		btnCancelar = false;
		pnlEvento = true;
		btnAnadir = true;
		if (servicioRoEvento
				.buscarEventoPorPadre(roEventoVista.getNombreEven()).size() == 0) {
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

	public RoEvento getRoEventoVista() {
		return roEventoVista;
	}

	public void setRoEventoVista(RoEvento roEventoVista) {
		this.roEventoVista = roEventoVista;
	}

	public RoEvento getRoEventoControlador() {
		return roEventoControlador;
	}

	public void setRoEventoControlador(RoEvento roEventoControlador) {
		this.roEventoControlador = roEventoControlador;
	}

	public List<RoEvento> getEventosTodos() {
		return EventosTodos;
	}

	public void setEventosTodos(List<RoEvento> EventosTodos) {
		this.EventosTodos = EventosTodos;
	}

	public int getIdEventoSeleccionado() {
		return idEventoSeleccionado;
	}

	public void setIdEventoSeleccionado(int idEventoSeleccionado) {
		this.idEventoSeleccionado = idEventoSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public TreeNode getArbolEventoVista() {
		return arbolEventoVista;
	}

	public void setArbolEventoVista(TreeNode arbolEventoVista) {
		this.arbolEventoVista = arbolEventoVista;
	}

	public TreeNode getArbolEventoSeleccionado() {
		return arbolEventoSeleccionado;
	}

	public void setArbolEventoSeleccionado(TreeNode arbolEventoSeleccionado) {
		this.arbolEventoSeleccionado = arbolEventoSeleccionado;
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

	public int getIdTipoEventoSeleccionado() {
		return idTipoEventoSeleccionado;
	}

	public void setIdTipoEventoSeleccionado(int idTipoEventoSeleccionado) {
		this.idTipoEventoSeleccionado = idTipoEventoSeleccionado;
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

	public List<RoEvento> getSubEventosTodos() {
		return subEventosTodos;
	}

	public void setSubEventosTodos(List<RoEvento> subEventosTodos) {
		this.subEventosTodos = subEventosTodos;
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

	public boolean isPnlEvento() {
		return pnlEvento;
	}

	public void setPnlEvento(boolean pnlEvento) {
		this.pnlEvento = pnlEvento;
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
