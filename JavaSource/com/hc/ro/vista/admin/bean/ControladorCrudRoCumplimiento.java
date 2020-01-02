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
import com.hc.ro.modelo.RoCumplimiento;
import com.hc.ro.negocio.ServicioRoCumplimiento;

@ManagedBean
@ViewScoped
public class ControladorCrudRoCumplimiento {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudCumplimiento.jsf";

	// Cumplimiento
	@EJB
	ServicioRoCumplimiento servicioRoCumplimiento;

	// VARIABLES
	private RoCumplimiento roCumplimientoVista;
	private RoCumplimiento roCumplimientoControlador;
	private List<RoCumplimiento> tipoAfectasTodos;
	private int idCumplimientoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlCumplimiento;

	//
	public ControladorCrudRoCumplimiento() {
		super();
		roCumplimientoControlador = new RoCumplimiento();
		roCumplimientoVista = new RoCumplimiento();
		tipoAfectasTodos = new ArrayList<RoCumplimiento>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoCumplimiento() {
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

		tipoAfectasTodos = servicioRoCumplimiento.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlCumplimiento = false;
	}

	// METODOS

	public void guardarCumplimiento() {
		if (tipoGuardar) {
			if (servicioRoCumplimiento
					.existeCumplimientoPorNombre(roCumplimientoVista
							.getNombreCump())) {
				servicioRoCumplimiento.insertar(roCumplimientoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Nivel de Cumplimiento Añadido",
						"El Nivel de Cumplimiento ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de el Nivel de Cumplimiento no se debe repetir"));
			}
		} else {

			if (servicioRoCumplimiento.existeCumplimientoPorNombreEx(
					roCumplimientoVista.getNombreCump(),
					roCumplimientoVista.getCodigoCump())) {

				servicioRoCumplimiento.actualizar(roCumplimientoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage("Nivel de Cumplimiento Actualizada",
								"El Nivel de Cumplimiento ha sido Actualizada con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de el Nivel de Cumplimiento no se debe repetir"));
				roCumplimientoVista.setNombreCump(roCumplimientoControlador
						.getNombreCump());
			}
		}
	}

	public void exitoGuardar() {

		roCumplimientoVista = new RoCumplimiento();
		tipoAfectasTodos = servicioRoCumplimiento.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlCumplimiento = false;
	}

	public void eliminarCumplimiento() {

		try {
			servicioRoCumplimiento.eliminar(roCumplimientoVista);
			roCumplimientoVista = new RoCumplimiento();
			tipoAfectasTodos = servicioRoCumplimiento.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlCumplimiento = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Nivel de Cumplimiento Eliminado",
					"El Nivel de Cumplimiento ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:", e.getMessage()));
		}
	}

	public void nuevoCumplimientoVista() {
		tipoGuardar = true;
		roCumplimientoVista = new RoCumplimiento();
		btnGuardar = false;
		btnCancelar = false;
		pnlCumplimiento = true;
	}

	public void cancelar() {
		nuevoCumplimientoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlCumplimiento = false;
		RequestContext.getCurrentInstance().update("formCumplimiento");
	}

	public void editarCumplimientoVista() {
		roCumplimientoControlador.setNombreCump(roCumplimientoVista
				.getNombreCump());
		tipoGuardar = false;

		btnGuardar = false;
		btnCancelar = false;
		pnlCumplimiento = true;
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

	public RoCumplimiento getRoCumplimientoVista() {
		return roCumplimientoVista;
	}

	public void setRoCumplimientoVista(RoCumplimiento roCumplimientoVista) {
		this.roCumplimientoVista = roCumplimientoVista;
	}

	public RoCumplimiento getRoCumplimientoControlador() {
		return roCumplimientoControlador;
	}

	public void setRoCumplimientoControlador(
			RoCumplimiento roCumplimientoControlador) {
		this.roCumplimientoControlador = roCumplimientoControlador;
	}

	public List<RoCumplimiento> getCumplimientosTodos() {
		return tipoAfectasTodos;
	}

	public void setCumplimientosTodos(List<RoCumplimiento> CumplimientosTodos) {
		this.tipoAfectasTodos = CumplimientosTodos;
	}

	public int getIdCumplimientoSeleccionado() {
		return idCumplimientoSeleccionado;
	}

	public void setIdCumplimientoSeleccionado(int idCumplimientoSeleccionado) {
		this.idCumplimientoSeleccionado = idCumplimientoSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlCumplimiento() {
		return pnlCumplimiento;
	}

	public void setPnlCumplimiento(boolean pnlCumplimiento) {
		this.pnlCumplimiento = pnlCumplimiento;
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

	public List<RoCumplimiento> getTipoAfectasTodos() {
		return tipoAfectasTodos;
	}

	public void setTipoAfectasTodos(List<RoCumplimiento> tipoAfectasTodos) {
		this.tipoAfectasTodos = tipoAfectasTodos;
	}

}
