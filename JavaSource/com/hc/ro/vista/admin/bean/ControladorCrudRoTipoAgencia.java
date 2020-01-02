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
import com.hc.ro.modelo.RoTipoAgencia;
import com.hc.ro.negocio.ServicioRoTipoAgencia;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoAgencia {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoAgencia.jsf";
	// TipoAgencia
	@EJB
	ServicioRoTipoAgencia servicioRoTipoAgencia;

	// VARIABLES
	private RoTipoAgencia roTipoAgenciaVista;
	private RoTipoAgencia roTipoAgenciaControlador;
	private List<RoTipoAgencia> tipoAgenciasTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoAgenciaSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoAgencia;

	//
	public ControladorCrudRoTipoAgencia() {
		super();
		roTipoAgenciaControlador = new RoTipoAgencia();
		roTipoAgenciaVista = new RoTipoAgencia();
		tipoAgenciasTodos = new ArrayList<RoTipoAgencia>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoAgencia() {
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
		tipoAgenciasTodos = servicioRoTipoAgencia.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoAgencia = false;
	}

	// METODOS

	public void guardarTipoAgencia() {
		if (tipoGuardar) {
			if (servicioRoTipoAgencia
					.existeTipoAgenciaPorNombre(roTipoAgenciaVista
							.getNombreTiag())) {
				servicioRoTipoAgencia.insertar(roTipoAgenciaVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Agencia Añadido",
						"El Tipo de Agencia ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Agencia no se debe repetir"));
			}
		} else {

			if (servicioRoTipoAgencia.existeTipoAgenciaPorNombreEx(
					roTipoAgenciaVista.getNombreTiag(),
					roTipoAgenciaVista.getCodigoTiag())) {

				servicioRoTipoAgencia.actualizar(roTipoAgenciaVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Agencia Actualizado",
						"El TipoAgencia ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Agencia no se debe repetir"));
				roTipoAgenciaVista.setNombreTiag(roTipoAgenciaControlador
						.getNombreTiag());
			}
		}
	}

	public void exitoGuardar() {

		roTipoAgenciaVista = new RoTipoAgencia();
		tipoAgenciasTodos = servicioRoTipoAgencia.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoAgencia = false;
	}

	public void eliminarTipoAgencia() {

		try {
			servicioRoTipoAgencia.eliminar(roTipoAgenciaVista);
			roTipoAgenciaVista = new RoTipoAgencia();
			tipoAgenciasTodos = servicioRoTipoAgencia.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoAgencia = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Agencia Eliminado",
					"El Tipo de Agencia ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de Agencia en uso"));
		}
	}

	public void nuevoTipoAgenciaVista() {
		tipoGuardar = true;
		roTipoAgenciaVista = new RoTipoAgencia();
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoAgencia = true;
	}

	public void cancelar() {
		nuevoTipoAgenciaVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoAgencia = false;
		RequestContext.getCurrentInstance().update("formTipoAgencia");
	}

	public void editarTipoAgenciaVista() {
		roTipoAgenciaControlador.setNombreTiag(roTipoAgenciaVista
				.getNombreTiag());
		tipoGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoAgencia = true;
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

	public RoTipoAgencia getRoTipoAgenciaVista() {
		return roTipoAgenciaVista;
	}

	public void setRoTipoAgenciaVista(RoTipoAgencia roTipoAgenciaVista) {
		this.roTipoAgenciaVista = roTipoAgenciaVista;
	}

	public RoTipoAgencia getRoTipoAgenciaControlador() {
		return roTipoAgenciaControlador;
	}

	public void setRoTipoAgenciaControlador(
			RoTipoAgencia roTipoAgenciaControlador) {
		this.roTipoAgenciaControlador = roTipoAgenciaControlador;
	}

	public List<RoTipoAgencia> getTipoAgenciasTodos() {
		return tipoAgenciasTodos;
	}

	public void setTipoAgenciasTodos(List<RoTipoAgencia> TipoAgenciasTodos) {
		this.tipoAgenciasTodos = TipoAgenciasTodos;
	}

	public int getIdTipoAgenciaSeleccionado() {
		return idTipoAgenciaSeleccionado;
	}

	public void setIdTipoAgenciaSeleccionado(int idTipoAgenciaSeleccionado) {
		this.idTipoAgenciaSeleccionado = idTipoAgenciaSeleccionado;
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

	public boolean isPnlTipoAgencia() {
		return pnlTipoAgencia;
	}

	public void setPnlTipoAgencia(boolean pnlTipoAgencia) {
		this.pnlTipoAgencia = pnlTipoAgencia;
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
