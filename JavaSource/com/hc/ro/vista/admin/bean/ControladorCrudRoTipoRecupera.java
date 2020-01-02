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
import com.hc.ro.modelo.RoTipoRecupera;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioRoTipoRecupera;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoRecupera {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoRecupera.jsf";
	// TipoRecupera
	@EJB
	ServicioRoTipoRecupera servicioRoTipoRecupera;
	@EJB
	ServicioGenEstado servicioGenEstado;

	// VARIABLES
	private RoTipoRecupera roTipoRecuperaVista;
	private RoTipoRecupera roTipoRecuperaControlador;
	private List<RoTipoRecupera> tipoRecuperasTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoRecuperaSeleccionado;
	private int idEstadoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoRecupera;

	//
	public ControladorCrudRoTipoRecupera() {
		super();
		roTipoRecuperaControlador = new RoTipoRecupera();
		roTipoRecuperaVista = new RoTipoRecupera();
		tipoRecuperasTodos = new ArrayList<RoTipoRecupera>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoRecupera() {
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
		tipoRecuperasTodos = servicioRoTipoRecupera.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoRecupera = false;
	}

	// METODOS

	public void guardarTipoRecupera() {
		roTipoRecuperaVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		if (tipoGuardar) {
			if (servicioRoTipoRecupera
					.existeTipoRecuperaPorNombre(roTipoRecuperaVista
							.getNombreTrec())) {
				servicioRoTipoRecupera.insertar(roTipoRecuperaVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Recuperación Añadido",
						"El Tipo de Recuperación ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Recuperación no se debe repetir"));
			}
		} else {

			if (servicioRoTipoRecupera.existeTipoRecuperaPorNombreEx(
					roTipoRecuperaVista.getNombreTrec(),
					roTipoRecuperaVista.getCodigoTrec())) {

				servicioRoTipoRecupera.actualizar(roTipoRecuperaVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage("Tipo de Recuperación Actualizado",
								"El Tipo de Recuperación ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Recuperación no se debe repetir"));
				roTipoRecuperaVista.setNombreTrec(roTipoRecuperaControlador
						.getNombreTrec());
			}
		}
	}

	public void exitoGuardar() {

		roTipoRecuperaVista = new RoTipoRecupera();
		idEstadoSeleccionado = 1;
		tipoRecuperasTodos = servicioRoTipoRecupera.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoRecupera = false;
	}

	public void eliminarTipoRecupera() {

		try {
			servicioRoTipoRecupera.eliminar(roTipoRecuperaVista);
			roTipoRecuperaVista = new RoTipoRecupera();
			tipoRecuperasTodos = servicioRoTipoRecupera.buscarTodos();
			idEstadoSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoRecupera = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Recuperación Eliminado",
					"El Tipo de Recuperación ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de Recuperación en uso"));
		}
	}

	public void nuevoTipoRecuperaVista() {
		tipoGuardar = true;
		roTipoRecuperaVista = new RoTipoRecupera();
		idEstadoSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoRecupera = true;
	}

	public void cancelar() {
		nuevoTipoRecuperaVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoRecupera = false;
		RequestContext.getCurrentInstance().update("formTipoRecupera");
	}

	public void editarTipoRecuperaVista() {
		roTipoRecuperaControlador.setNombreTrec(roTipoRecuperaVista
				.getNombreTrec());
		tipoGuardar = false;
		idEstadoSeleccionado = (int) roTipoRecuperaVista.getGenEstado()
				.getCodigoEsta();

		btnGuardar = false;
		btnCancelar = false;
		pnlTipoRecupera = true;
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

	public RoTipoRecupera getRoTipoRecuperaVista() {
		return roTipoRecuperaVista;
	}

	public void setRoTipoRecuperaVista(RoTipoRecupera roTipoRecuperaVista) {
		this.roTipoRecuperaVista = roTipoRecuperaVista;
	}

	public RoTipoRecupera getRoTipoRecuperaControlador() {
		return roTipoRecuperaControlador;
	}

	public void setRoTipoRecuperaControlador(
			RoTipoRecupera roTipoRecuperaControlador) {
		this.roTipoRecuperaControlador = roTipoRecuperaControlador;
	}

	public List<RoTipoRecupera> getTipoRecuperasTodos() {
		return tipoRecuperasTodos;
	}

	public void setTipoRecuperasTodos(List<RoTipoRecupera> TipoRecuperasTodos) {
		this.tipoRecuperasTodos = TipoRecuperasTodos;
	}

	public int getIdTipoRecuperaSeleccionado() {
		return idTipoRecuperaSeleccionado;
	}

	public void setIdTipoRecuperaSeleccionado(int idTipoRecuperaSeleccionado) {
		this.idTipoRecuperaSeleccionado = idTipoRecuperaSeleccionado;
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

	public boolean isPnlTipoRecupera() {
		return pnlTipoRecupera;
	}

	public void setPnlTipoRecupera(boolean pnlTipoRecupera) {
		this.pnlTipoRecupera = pnlTipoRecupera;
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
