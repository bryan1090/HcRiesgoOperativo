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
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoCaractEvento;
import com.hc.ro.negocio.ServicioRoEvento;
import com.hc.ro.negocio.ServicioRoCaractEvento;

@ManagedBean
@ViewScoped
public class ControladorCrudRoCaractEvento {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudCaractEvento.jsf";
	// CaractEvento
	@EJB
	ServicioRoCaractEvento servicioRoCaractEvento;
	@EJB
	ServicioRoEvento servicioRoEvento;

	// VARIABLES
	private RoCaractEvento roCaractEventoVista;
	private RoCaractEvento roCaractEventoControlador;
	private List<RoCaractEvento> tipoAfectasTodos;
	private List<RoEvento> eventosTodos;
	private int idCaractEventoSeleccionado;
	private int idEventoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlCaractEvento;

	//
	public ControladorCrudRoCaractEvento() {
		super();
		roCaractEventoControlador = new RoCaractEvento();
		roCaractEventoVista = new RoCaractEvento();
		tipoAfectasTodos = new ArrayList<RoCaractEvento>();
		eventosTodos = new ArrayList<RoEvento>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoCaractEvento() {
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
		tipoAfectasTodos = servicioRoCaractEvento.buscarTodos();
		eventosTodos = servicioRoEvento.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlCaractEvento = false;
	}

	// METODOS

	public void guardarCaractEvento() {
		roCaractEventoVista.setRoEvento(servicioRoEvento
				.buscarPorId(idEventoSeleccionado));
		if (tipoGuardar) {
			if (servicioRoCaractEvento
					.existeCaractEventoPorNombre(roCaractEventoVista
							.getNombreCaev())) {
				servicioRoCaractEvento.insertar(roCaractEventoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage("Característica del Evento Añadida",
								"La Característica del Evento ha sido Añadida con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de la Característica del Evento no se debe repetir"));
			}
		} else {

			if (servicioRoCaractEvento.existeCaractEventoPorNombreEx(
					roCaractEventoVista.getNombreCaev(),
					roCaractEventoVista.getCodigoCaev())) {

				servicioRoCaractEvento.actualizar(roCaractEventoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(
								"Característica del Evento Actualizada",
								"La Característica del Evento ha sido Actualizada con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de la Característica del Evento no se debe repetir"));
				roCaractEventoVista.setNombreCaev(roCaractEventoControlador
						.getNombreCaev());
			}
		}
	}

	public void exitoGuardar() {

		roCaractEventoVista = new RoCaractEvento();
		idEventoSeleccionado = 1;
		tipoAfectasTodos = servicioRoCaractEvento.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlCaractEvento = false;
	}

	public void eliminarCaractEvento() {

		try {
			servicioRoCaractEvento.eliminar(roCaractEventoVista);
			roCaractEventoVista = new RoCaractEvento();
			tipoAfectasTodos = servicioRoCaractEvento.buscarTodos();
			idEventoSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlCaractEvento = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Característica del Evento Eliminado",
					"La Característica del Evento ha sido Eliminada con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:", e.getMessage()));
		}
	}

	public void nuevoCaractEventoVista() {
		tipoGuardar = true;
		roCaractEventoVista = new RoCaractEvento();
		idEventoSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlCaractEvento = true;
	}

	public void cancelar() {
		nuevoCaractEventoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlCaractEvento = false;
		RequestContext.getCurrentInstance().update("formCaractEvento");
	}

	public void editarCaractEventoVista() {
		roCaractEventoControlador.setNombreCaev(roCaractEventoVista
				.getNombreCaev());
		tipoGuardar = false;
		idEventoSeleccionado = roCaractEventoVista.getRoEvento()
				.getCodigoEven();

		btnGuardar = false;
		btnCancelar = false;
		pnlCaractEvento = true;
		btnAnadir = true;

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

	public RoCaractEvento getRoCaractEventoVista() {
		return roCaractEventoVista;
	}

	public void setRoCaractEventoVista(RoCaractEvento roCaractEventoVista) {
		this.roCaractEventoVista = roCaractEventoVista;
	}

	public RoCaractEvento getRoCaractEventoControlador() {
		return roCaractEventoControlador;
	}

	public void setRoCaractEventoControlador(
			RoCaractEvento roCaractEventoControlador) {
		this.roCaractEventoControlador = roCaractEventoControlador;
	}

	public List<RoCaractEvento> getCaractEventosTodos() {
		return tipoAfectasTodos;
	}

	public void setCaractEventosTodos(List<RoCaractEvento> CaractEventosTodos) {
		this.tipoAfectasTodos = CaractEventosTodos;
	}

	public int getIdCaractEventoSeleccionado() {
		return idCaractEventoSeleccionado;
	}

	public void setIdCaractEventoSeleccionado(int idCaractEventoSeleccionado) {
		this.idCaractEventoSeleccionado = idCaractEventoSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public List<RoEvento> getEstadosTodos() {
		return eventosTodos;
	}

	public void setEstadosTodos(List<RoEvento> eventosTodos) {
		this.eventosTodos = eventosTodos;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlCaractEvento() {
		return pnlCaractEvento;
	}

	public void setPnlCaractEvento(boolean pnlCaractEvento) {
		this.pnlCaractEvento = pnlCaractEvento;
	}

	public List<RoEvento> getEventosTodos() {
		return eventosTodos;
	}

	public void setEventosTodos(List<RoEvento> eventosTodos) {
		this.eventosTodos = eventosTodos;
	}

	public int getIdEventoSeleccionado() {
		return idEventoSeleccionado;
	}

	public void setIdEventoSeleccionado(int idEventoSeleccionado) {
		this.idEventoSeleccionado = idEventoSeleccionado;
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

	public List<RoCaractEvento> getTipoAfectasTodos() {
		return tipoAfectasTodos;
	}

	public void setTipoAfectasTodos(List<RoCaractEvento> tipoAfectasTodos) {
		this.tipoAfectasTodos = tipoAfectasTodos;
	}

}
