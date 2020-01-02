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
import com.hc.ro.modelo.RoTipoDetalle;
import com.hc.ro.negocio.ServicioRoTipoDetalle;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoDetalle {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoDetalle.jsf";
	// TipoDetalle
	@EJB
	ServicioRoTipoDetalle servicioRoTipoDetalle;

	// VARIABLES
	private RoTipoDetalle roTipoDetalleVista;
	private RoTipoDetalle roTipoDetalleControlador;
	private List<RoTipoDetalle> tipoDetallesTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoDetalleSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoDetalle;

	//
	public ControladorCrudRoTipoDetalle() {
		super();
		roTipoDetalleControlador = new RoTipoDetalle();
		roTipoDetalleVista = new RoTipoDetalle();
		tipoDetallesTodos = new ArrayList<RoTipoDetalle>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoDetalle() {
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
		tipoDetallesTodos = servicioRoTipoDetalle.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoDetalle = false;
	}

	// METODOS

	public void guardarTipoDetalle() {
		if (tipoGuardar) {
			if (servicioRoTipoDetalle
					.existeTipoDetallePorNombre(roTipoDetalleVista
							.getNombreTdro())) {
				servicioRoTipoDetalle.insertar(roTipoDetalleVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Detalle Añadido",
						"El Tipo de Detalle ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Detalle no se debe repetir"));
			}
		} else {

			if (servicioRoTipoDetalle.existeTipoDetallePorNombreEx(
					roTipoDetalleVista.getNombreTdro(),
					roTipoDetalleVista.getCodigoTdro())) {

				servicioRoTipoDetalle.actualizar(roTipoDetalleVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Detalle Actualizado",
						"El Tipo de Detalle ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Detalle no se debe repetir"));
				roTipoDetalleVista.setNombreTdro(roTipoDetalleControlador
						.getNombreTdro());
			}
		}
	}

	public void exitoGuardar() {

		roTipoDetalleVista = new RoTipoDetalle();
		tipoDetallesTodos = servicioRoTipoDetalle.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoDetalle = false;
	}

	public void eliminarTipoDetalle() {

		try {
			servicioRoTipoDetalle.eliminar(roTipoDetalleVista);
			roTipoDetalleVista = new RoTipoDetalle();
			tipoDetallesTodos = servicioRoTipoDetalle.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoDetalle = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Detalle Eliminado",
					"El Tipo de Detalle ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:", e.getMessage()));
		}
	}

	public void nuevoTipoDetalleVista() {
		tipoGuardar = true;
		roTipoDetalleVista = new RoTipoDetalle();
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoDetalle = true;
	}

	public void cancelar() {
		nuevoTipoDetalleVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoDetalle = false;
		RequestContext.getCurrentInstance().update("formTipoDetalle");
	}

	public void editarTipoDetalleVista() {
		roTipoDetalleControlador.setNombreTdro(roTipoDetalleVista
				.getNombreTdro());
		tipoGuardar = false;

		btnGuardar = false;
		btnCancelar = false;
		pnlTipoDetalle = true;
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

	public RoTipoDetalle getRoTipoDetalleVista() {
		return roTipoDetalleVista;
	}

	public void setRoTipoDetalleVista(RoTipoDetalle roTipoDetalleVista) {
		this.roTipoDetalleVista = roTipoDetalleVista;
	}

	public RoTipoDetalle getRoTipoDetalleControlador() {
		return roTipoDetalleControlador;
	}

	public void setRoTipoDetalleControlador(
			RoTipoDetalle roTipoDetalleControlador) {
		this.roTipoDetalleControlador = roTipoDetalleControlador;
	}

	public List<RoTipoDetalle> getTipoDetallesTodos() {
		return tipoDetallesTodos;
	}

	public void setTipoDetallesTodos(List<RoTipoDetalle> TipoDetallesTodos) {
		this.tipoDetallesTodos = TipoDetallesTodos;
	}

	public int getIdTipoDetalleSeleccionado() {
		return idTipoDetalleSeleccionado;
	}

	public void setIdTipoDetalleSeleccionado(int idTipoDetalleSeleccionado) {
		this.idTipoDetalleSeleccionado = idTipoDetalleSeleccionado;
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

	public boolean isPnlTipoDetalle() {
		return pnlTipoDetalle;
	}

	public void setPnlTipoDetalle(boolean pnlTipoDetalle) {
		this.pnlTipoDetalle = pnlTipoDetalle;
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
