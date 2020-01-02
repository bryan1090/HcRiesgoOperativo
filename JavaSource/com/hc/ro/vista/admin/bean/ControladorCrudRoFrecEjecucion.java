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
import com.hc.ro.modelo.RoFrecEjecucion;
import com.hc.ro.negocio.ServicioRoFrecEjecucion;

@ManagedBean
@ViewScoped
public class ControladorCrudRoFrecEjecucion {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudFrecEjecucion.jsf";

	// FrecEjecucion
	@EJB
	ServicioRoFrecEjecucion servicioRoFrecEjecucion;

	// VARIABLES
	private RoFrecEjecucion roFrecEjecucionVista;
	private RoFrecEjecucion roFrecEjecucionControlador;
	private List<RoFrecEjecucion> tipoEjecucionsTodos;
	private List<GenEstado> estadosTodos;
	private int idFrecEjecucionSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlFrecEjecucion;

	//
	public ControladorCrudRoFrecEjecucion() {
		super();
		roFrecEjecucionControlador = new RoFrecEjecucion();
		roFrecEjecucionVista = new RoFrecEjecucion();
		tipoEjecucionsTodos = new ArrayList<RoFrecEjecucion>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoFrecEjecucion() {
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
		tipoEjecucionsTodos = servicioRoFrecEjecucion.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlFrecEjecucion = false;
	}

	// METODOS

	public void guardarFrecEjecucion() {
		if (tipoGuardar) {
			if (servicioRoFrecEjecucion
					.existeFrecEjecucionPorNombre(roFrecEjecucionVista
							.getNombreFrej())) {
				servicioRoFrecEjecucion.insertar(roFrecEjecucionVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Ejecucion Añadido",
						"El Tipo de Ejecucion ha sido añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Ejecucion no se debe repetir"));
			}
		} else {

			if (servicioRoFrecEjecucion.existeFrecEjecucionPorNombreEx(
					roFrecEjecucionVista.getNombreFrej(),
					roFrecEjecucionVista.getCodigoFrej())) {

				servicioRoFrecEjecucion.actualizar(roFrecEjecucionVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Ejecucion Actualizado",
						"El Tipo de Ejecucion ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Ejecucion no se debe repetir"));
				roFrecEjecucionVista.setNombreFrej(roFrecEjecucionControlador
						.getNombreFrej());
			}
		}
	}

	public void exitoGuardar() {

		roFrecEjecucionVista = new RoFrecEjecucion();
		tipoEjecucionsTodos = servicioRoFrecEjecucion.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlFrecEjecucion = false;
	}

	public void eliminarFrecEjecucion() {

		try {
			servicioRoFrecEjecucion.eliminar(roFrecEjecucionVista);
			roFrecEjecucionVista = new RoFrecEjecucion();
			tipoEjecucionsTodos = servicioRoFrecEjecucion.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlFrecEjecucion = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"FrecEjecucion Eliminado",
					"El Tipo de Ejecucion ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de ejecución en uso"));
		}
	}

	public void nuevoFrecEjecucionVista() {
		tipoGuardar = true;
		roFrecEjecucionVista = new RoFrecEjecucion();
		btnGuardar = false;
		btnCancelar = false;
		pnlFrecEjecucion = true;
	}

	public void cancelar() {
		nuevoFrecEjecucionVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlFrecEjecucion = false;
		RequestContext.getCurrentInstance().update("formFrecEjecucion");
	}

	public void editarFrecEjecucionVista() {
		roFrecEjecucionControlador.setNombreFrej(roFrecEjecucionVista
				.getNombreFrej());
		tipoGuardar = false;

		btnGuardar = false;
		btnCancelar = false;
		pnlFrecEjecucion = true;
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

	public RoFrecEjecucion getRoFrecEjecucionVista() {
		return roFrecEjecucionVista;
	}

	public void setRoFrecEjecucionVista(RoFrecEjecucion roFrecEjecucionVista) {
		this.roFrecEjecucionVista = roFrecEjecucionVista;
	}

	public RoFrecEjecucion getRoFrecEjecucionControlador() {
		return roFrecEjecucionControlador;
	}

	public void setRoFrecEjecucionControlador(
			RoFrecEjecucion roFrecEjecucionControlador) {
		this.roFrecEjecucionControlador = roFrecEjecucionControlador;
	}

	public List<RoFrecEjecucion> getFrecEjecucionsTodos() {
		return tipoEjecucionsTodos;
	}

	public void setFrecEjecucionsTodos(List<RoFrecEjecucion> FrecEjecucionsTodos) {
		this.tipoEjecucionsTodos = FrecEjecucionsTodos;
	}

	public int getIdFrecEjecucionSeleccionado() {
		return idFrecEjecucionSeleccionado;
	}

	public void setIdFrecEjecucionSeleccionado(int idFrecEjecucionSeleccionado) {
		this.idFrecEjecucionSeleccionado = idFrecEjecucionSeleccionado;
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

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlFrecEjecucion() {
		return pnlFrecEjecucion;
	}

	public void setPnlFrecEjecucion(boolean pnlFrecEjecucion) {
		this.pnlFrecEjecucion = pnlFrecEjecucion;
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

	public List<RoFrecEjecucion> getTipoEjecucionsTodos() {
		return tipoEjecucionsTodos;
	}

	public void setTipoEjecucionsTodos(List<RoFrecEjecucion> tipoEjecucionsTodos) {
		this.tipoEjecucionsTodos = tipoEjecucionsTodos;
	}

}
