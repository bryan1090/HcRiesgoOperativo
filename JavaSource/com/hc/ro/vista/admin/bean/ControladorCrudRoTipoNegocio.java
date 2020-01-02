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
import com.hc.ro.modelo.RoTipoNegocio;
import com.hc.ro.negocio.ServicioRoTipoNegocio;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoNegocio {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoNegocio.jsf";
	// TipoNegocio
	@EJB
	ServicioRoTipoNegocio servicioRoTipoNegocio;

	// VARIABLES
	private RoTipoNegocio roTipoNegocioVista;
	private RoTipoNegocio roTipoNegocioControlador;
	private List<RoTipoNegocio> tipoNegociosTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoNegocioSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoNegocio;

	//
	public ControladorCrudRoTipoNegocio() {
		super();
		roTipoNegocioControlador = new RoTipoNegocio();
		roTipoNegocioVista = new RoTipoNegocio();
		tipoNegociosTodos = new ArrayList<RoTipoNegocio>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoNegocio() {
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
		tipoNegociosTodos = servicioRoTipoNegocio.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoNegocio = false;
	}

	// METODOS

	public void guardarTipoNegocio() {
		if (tipoGuardar) {
			if (servicioRoTipoNegocio
					.existeTipoNegocioPorNombre(roTipoNegocioVista
							.getNombreTneg())) {
				servicioRoTipoNegocio.insertar(roTipoNegocioVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Negocio Añadido",
						"El Tipo de Negocio ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Negocio no se debe repetir"));
			}
		} else {

			if (servicioRoTipoNegocio.existeTipoNegocioPorNombreEx(
					roTipoNegocioVista.getNombreTneg(),
					roTipoNegocioVista.getCodigoTneg())) {

				servicioRoTipoNegocio.actualizar(roTipoNegocioVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Negocio Actualizado",
						"El Tipo de Negocio ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Negocio no se debe repetir"));
				roTipoNegocioVista.setNombreTneg(roTipoNegocioControlador
						.getNombreTneg());
			}
		}
	}

	public void exitoGuardar() {

		roTipoNegocioVista = new RoTipoNegocio();
		tipoNegociosTodos = servicioRoTipoNegocio.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoNegocio = false;
	}

	public void eliminarTipoNegocio() {

		try {
			servicioRoTipoNegocio.eliminar(roTipoNegocioVista);
			roTipoNegocioVista = new RoTipoNegocio();
			tipoNegociosTodos = servicioRoTipoNegocio.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoNegocio = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Negocio Eliminado",
					"El Tipo de Negocio ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de Negocio en Uso"));
		}
	}

	public void nuevoTipoNegocioVista() {
		tipoGuardar = true;
		roTipoNegocioVista = new RoTipoNegocio();
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoNegocio = true;
	}

	public void cancelar() {
		nuevoTipoNegocioVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoNegocio = false;
		RequestContext.getCurrentInstance().update("formTipoNegocio");
	}

	public void editarTipoNegocioVista() {
		roTipoNegocioControlador.setNombreTneg(roTipoNegocioVista
				.getNombreTneg());
		tipoGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoNegocio = true;
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

	public RoTipoNegocio getRoTipoNegocioVista() {
		return roTipoNegocioVista;
	}

	public void setRoTipoNegocioVista(RoTipoNegocio roTipoNegocioVista) {
		this.roTipoNegocioVista = roTipoNegocioVista;
	}

	public RoTipoNegocio getRoTipoNegocioControlador() {
		return roTipoNegocioControlador;
	}

	public void setRoTipoNegocioControlador(
			RoTipoNegocio roTipoNegocioControlador) {
		this.roTipoNegocioControlador = roTipoNegocioControlador;
	}

	public List<RoTipoNegocio> getTipoNegociosTodos() {
		return tipoNegociosTodos;
	}

	public void setTipoNegociosTodos(List<RoTipoNegocio> TipoNegociosTodos) {
		this.tipoNegociosTodos = TipoNegociosTodos;
	}

	public int getIdTipoNegocioSeleccionado() {
		return idTipoNegocioSeleccionado;
	}

	public void setIdTipoNegocioSeleccionado(int idTipoNegocioSeleccionado) {
		this.idTipoNegocioSeleccionado = idTipoNegocioSeleccionado;
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

	public boolean isPnlTipoNegocio() {
		return pnlTipoNegocio;
	}

	public void setPnlTipoNegocio(boolean pnlTipoNegocio) {
		this.pnlTipoNegocio = pnlTipoNegocio;
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
