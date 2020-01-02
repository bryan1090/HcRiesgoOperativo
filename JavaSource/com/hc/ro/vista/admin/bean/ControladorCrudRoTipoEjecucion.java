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
import com.hc.ro.modelo.RoTipoEjecucion;
import com.hc.ro.negocio.ServicioRoTipoEjecucion;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoEjecucion {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoEjecucion.jsf";
	// TipoEjecucion
	@EJB
	ServicioRoTipoEjecucion servicioRoTipoEjecucion;

	// VARIABLES
	private RoTipoEjecucion roTipoEjecucionVista;
	private RoTipoEjecucion roTipoEjecucionControlador;
	private List<RoTipoEjecucion> tipoEjecucionsTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoEjecucionSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoEjecucion;

	//
	public ControladorCrudRoTipoEjecucion() {
		super();
		roTipoEjecucionControlador = new RoTipoEjecucion();
		roTipoEjecucionVista = new RoTipoEjecucion();
		tipoEjecucionsTodos = new ArrayList<RoTipoEjecucion>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoEjecucion() {
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
		tipoEjecucionsTodos = servicioRoTipoEjecucion.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoEjecucion = false;
	}

	// METODOS

	public void guardarTipoEjecucion() {
		if (tipoGuardar) {
			if (servicioRoTipoEjecucion
					.existeTipoEjecucionPorNombre(roTipoEjecucionVista
							.getNombreTiej())) {
				servicioRoTipoEjecucion.insertar(roTipoEjecucionVista);
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

			if (servicioRoTipoEjecucion.existeTipoEjecucionPorNombreEx(
					roTipoEjecucionVista.getNombreTiej(),
					roTipoEjecucionVista.getCodigoTiej())) {

				servicioRoTipoEjecucion.actualizar(roTipoEjecucionVista);

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
				roTipoEjecucionVista.setNombreTiej(roTipoEjecucionControlador
						.getNombreTiej());
			}
		}
	}

	public void exitoGuardar() {

		roTipoEjecucionVista = new RoTipoEjecucion();
		tipoEjecucionsTodos = servicioRoTipoEjecucion.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoEjecucion = false;
	}

	public void eliminarTipoEjecucion() {

		try {
			servicioRoTipoEjecucion.eliminar(roTipoEjecucionVista);
			roTipoEjecucionVista = new RoTipoEjecucion();
			tipoEjecucionsTodos = servicioRoTipoEjecucion.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoEjecucion = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"TipoEjecucion Eliminado",
					"El Tipo de Ejecucion ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de ejecución en uso"));
		}
	}

	public void nuevoTipoEjecucionVista() {
		tipoGuardar = true;
		roTipoEjecucionVista = new RoTipoEjecucion();
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoEjecucion = true;
	}

	public void cancelar() {
		nuevoTipoEjecucionVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoEjecucion = false;
		RequestContext.getCurrentInstance().update("formTipoEjecucion");
	}

	public void editarTipoEjecucionVista() {
		roTipoEjecucionControlador.setNombreTiej(roTipoEjecucionVista
				.getNombreTiej());
		tipoGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoEjecucion = true;
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

	public RoTipoEjecucion getRoTipoEjecucionVista() {
		return roTipoEjecucionVista;
	}

	public void setRoTipoEjecucionVista(RoTipoEjecucion roTipoEjecucionVista) {
		this.roTipoEjecucionVista = roTipoEjecucionVista;
	}

	public RoTipoEjecucion getRoTipoEjecucionControlador() {
		return roTipoEjecucionControlador;
	}

	public void setRoTipoEjecucionControlador(
			RoTipoEjecucion roTipoEjecucionControlador) {
		this.roTipoEjecucionControlador = roTipoEjecucionControlador;
	}

	public List<RoTipoEjecucion> getTipoEjecucionsTodos() {
		return tipoEjecucionsTodos;
	}

	public void setTipoEjecucionsTodos(List<RoTipoEjecucion> TipoEjecucionsTodos) {
		this.tipoEjecucionsTodos = TipoEjecucionsTodos;
	}

	public int getIdTipoEjecucionSeleccionado() {
		return idTipoEjecucionSeleccionado;
	}

	public void setIdTipoEjecucionSeleccionado(int idTipoEjecucionSeleccionado) {
		this.idTipoEjecucionSeleccionado = idTipoEjecucionSeleccionado;
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

	public boolean isPnlTipoEjecucion() {
		return pnlTipoEjecucion;
	}

	public void setPnlTipoEjecucion(boolean pnlTipoEjecucion) {
		this.pnlTipoEjecucion = pnlTipoEjecucion;
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
