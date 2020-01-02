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
import com.hc.ro.modelo.RoTipoProceso;
import com.hc.ro.negocio.ServicioRoTipoProceso;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoProceso {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoProceso.jsf";
	// TipoProceso
	@EJB
	ServicioRoTipoProceso servicioRoTipoProceso;

	// VARIABLES
	private RoTipoProceso roTipoProcesoVista;
	private RoTipoProceso roTipoProcesoControlador;
	private List<RoTipoProceso> TipoProcesosTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoProcesoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoProceso;

	//
	public ControladorCrudRoTipoProceso() {
		super();
		roTipoProcesoControlador = new RoTipoProceso();
		roTipoProcesoVista = new RoTipoProceso();
		TipoProcesosTodos = new ArrayList<RoTipoProceso>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoProceso() {
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
		TipoProcesosTodos = servicioRoTipoProceso.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoProceso = false;
	}

	// METODOS
	public void guardarTipoProceso() {
		if (tipoGuardar) {
			if (servicioRoTipoProceso
					.existeTipoProcesoPorNombre(roTipoProcesoVista
							.getNombreTipr())) {
				servicioRoTipoProceso.insertar(roTipoProcesoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Proceso Añadido",
						"El Tipo de Proceso ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Proceso no se debe repetir"));
			}
		} else {

			if (servicioRoTipoProceso.existeTipoProcesoPorNombreEx(
					roTipoProcesoVista.getNombreTipr(),
					roTipoProcesoVista.getCodigoTipr())) {

				servicioRoTipoProceso.actualizar(roTipoProcesoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Proceso Actualizado",
						"El Tipo de Proceso ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Proceso no se deben repetir"));
				roTipoProcesoVista.setNombreTipr(roTipoProcesoControlador
						.getNombreTipr());
			}
		}
	}

	public void exitoGuardar() {

		roTipoProcesoVista = new RoTipoProceso();
		TipoProcesosTodos = servicioRoTipoProceso.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoProceso = false;
	}

	public void eliminarTipoProceso() {

		try {
			servicioRoTipoProceso.eliminar(roTipoProcesoVista);
			roTipoProcesoVista = new RoTipoProceso();
			TipoProcesosTodos = servicioRoTipoProceso.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoProceso = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Proceso Eliminado",
					"El Tipo de Proceso ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de Proceso en uso"));
		}
	}

	public void nuevoTipoProcesoVista() {
		tipoGuardar = true;
		roTipoProcesoVista = new RoTipoProceso();
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoProceso = true;
	}

	public void cancelar() {
		nuevoTipoProcesoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoProceso = false;
		RequestContext.getCurrentInstance().update("formTipoProceso");
	}

	public void editarTipoProcesoVista() {
		roTipoProcesoControlador.setNombreTipr(roTipoProcesoVista
				.getNombreTipr());
		tipoGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoProceso = true;
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

	public RoTipoProceso getRoTipoProcesoVista() {
		return roTipoProcesoVista;
	}

	public void setRoTipoProcesoVista(RoTipoProceso roTipoProcesoVista) {
		this.roTipoProcesoVista = roTipoProcesoVista;
	}

	public RoTipoProceso getRoTipoProcesoControlador() {
		return roTipoProcesoControlador;
	}

	public void setRoTipoProcesoControlador(
			RoTipoProceso roTipoProcesoControlador) {
		this.roTipoProcesoControlador = roTipoProcesoControlador;
	}

	public List<RoTipoProceso> getTipoProcesosTodos() {
		return TipoProcesosTodos;
	}

	public void setTipoProcesosTodos(List<RoTipoProceso> TipoProcesosTodos) {
		this.TipoProcesosTodos = TipoProcesosTodos;
	}

	public int getIdTipoProcesoSeleccionado() {
		return idTipoProcesoSeleccionado;
	}

	public void setIdTipoProcesoSeleccionado(int idTipoProcesoSeleccionado) {
		this.idTipoProcesoSeleccionado = idTipoProcesoSeleccionado;
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

	public boolean isPnlTipoProceso() {
		return pnlTipoProceso;
	}

	public void setPnlTipoProceso(boolean pnlTipoProceso) {
		this.pnlTipoProceso = pnlTipoProceso;
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
