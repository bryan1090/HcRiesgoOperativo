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
import com.hc.ro.modelo.RoNivelEfecCtrl;
import com.hc.ro.negocio.ServicioRoNivelEfecCtrl;

@ManagedBean
@ViewScoped
public class ControladorCrudRoNivelEfecCtrl {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudNivelEfecCtrl.jsf";

	// NivelEfecCtrl
	@EJB
	ServicioRoNivelEfecCtrl servicioRoNivelEfecCtrl;

	// VARIABLES
	private RoNivelEfecCtrl roNivelEfecCtrlVista;
	private RoNivelEfecCtrl roNivelEfecCtrlControlador;
	private List<RoNivelEfecCtrl> tipoAfectasTodos;
	private int idNivelEfecCtrlSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlNivelEfecCtrl;

	//
	public ControladorCrudRoNivelEfecCtrl() {
		super();
		roNivelEfecCtrlControlador = new RoNivelEfecCtrl();
		roNivelEfecCtrlVista = new RoNivelEfecCtrl();
		tipoAfectasTodos = new ArrayList<RoNivelEfecCtrl>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoNivelEfecCtrl() {
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
		tipoAfectasTodos = servicioRoNivelEfecCtrl.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlNivelEfecCtrl = false;
	}

	// METODOS

	public void guardarNivelEfecCtrl() {
		if (tipoGuardar) {
			if (servicioRoNivelEfecCtrl
					.existeNivelEfecCtrlPorNombre(roNivelEfecCtrlVista
							.getNombreNect())) {
				servicioRoNivelEfecCtrl.insertar(roNivelEfecCtrlVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(
								"Nivel de Efectividad de Control Añadido",
								"El Nivel de Efectividad de Control ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de la Nivel de Efectividad de Control no se debe repetir"));
			}
		} else {

			if (servicioRoNivelEfecCtrl.existeNivelEfecCtrlPorNombreEx(
					roNivelEfecCtrlVista.getNombreNect(),
					roNivelEfecCtrlVista.getCodigoNect())) {

				servicioRoNivelEfecCtrl.actualizar(roNivelEfecCtrlVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(
								"Nivel de Efectividad de Control Actualizada",
								"El Nivel de Efectividad de Control ha sido Actualizada con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de la Nivel de Efectividad de Control no se debe repetir"));
				roNivelEfecCtrlVista.setNombreNect(roNivelEfecCtrlControlador
						.getNombreNect());
			}
		}
	}

	public void exitoGuardar() {

		roNivelEfecCtrlVista = new RoNivelEfecCtrl();
		tipoAfectasTodos = servicioRoNivelEfecCtrl.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlNivelEfecCtrl = false;
	}

	public void eliminarNivelEfecCtrl() {

		try {
			servicioRoNivelEfecCtrl.eliminar(roNivelEfecCtrlVista);
			roNivelEfecCtrlVista = new RoNivelEfecCtrl();
			tipoAfectasTodos = servicioRoNivelEfecCtrl.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlNivelEfecCtrl = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							"Nivel de Efectividad de Control Eliminado",
							"El Nivel de Efectividad de Control ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:", e.getMessage()));
		}
	}

	public void nuevoNivelEfecCtrlVista() {
		tipoGuardar = true;
		roNivelEfecCtrlVista = new RoNivelEfecCtrl();
		btnGuardar = false;
		btnCancelar = false;
		pnlNivelEfecCtrl = true;
	}

	public void cancelar() {
		nuevoNivelEfecCtrlVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlNivelEfecCtrl = false;
		RequestContext.getCurrentInstance().update("formNivelEfecCtrl");
	}

	public void editarNivelEfecCtrlVista() {
		roNivelEfecCtrlControlador.setNombreNect(roNivelEfecCtrlVista
				.getNombreNect());
		tipoGuardar = false;

		btnGuardar = false;
		btnCancelar = false;
		pnlNivelEfecCtrl = true;
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

	public RoNivelEfecCtrl getRoNivelEfecCtrlVista() {
		return roNivelEfecCtrlVista;
	}

	public void setRoNivelEfecCtrlVista(RoNivelEfecCtrl roNivelEfecCtrlVista) {
		this.roNivelEfecCtrlVista = roNivelEfecCtrlVista;
	}

	public RoNivelEfecCtrl getRoNivelEfecCtrlControlador() {
		return roNivelEfecCtrlControlador;
	}

	public void setRoNivelEfecCtrlControlador(
			RoNivelEfecCtrl roNivelEfecCtrlControlador) {
		this.roNivelEfecCtrlControlador = roNivelEfecCtrlControlador;
	}

	public List<RoNivelEfecCtrl> getNivelEfecCtrlsTodos() {
		return tipoAfectasTodos;
	}

	public void setNivelEfecCtrlsTodos(List<RoNivelEfecCtrl> NivelEfecCtrlsTodos) {
		this.tipoAfectasTodos = NivelEfecCtrlsTodos;
	}

	public int getIdNivelEfecCtrlSeleccionado() {
		return idNivelEfecCtrlSeleccionado;
	}

	public void setIdNivelEfecCtrlSeleccionado(int idNivelEfecCtrlSeleccionado) {
		this.idNivelEfecCtrlSeleccionado = idNivelEfecCtrlSeleccionado;
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

	public boolean isPnlNivelEfecCtrl() {
		return pnlNivelEfecCtrl;
	}

	public void setPnlNivelEfecCtrl(boolean pnlNivelEfecCtrl) {
		this.pnlNivelEfecCtrl = pnlNivelEfecCtrl;
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

	public List<RoNivelEfecCtrl> getTipoAfectasTodos() {
		return tipoAfectasTodos;
	}

	public void setTipoAfectasTodos(List<RoNivelEfecCtrl> tipoAfectasTodos) {
		this.tipoAfectasTodos = tipoAfectasTodos;
	}

}
