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
import com.hc.ro.modelo.RoTipoCosto;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioRoTipoCosto;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoCosto {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoCosto.jsf";
	// TipoCosto
	@EJB
	ServicioRoTipoCosto servicioRoTipoCosto;
	@EJB
	ServicioGenEstado servicioGenEstado;

	// VARIABLES
	private RoTipoCosto roTipoCostoVista;
	private RoTipoCosto roTipoCostoControlador;
	private List<RoTipoCosto> tipoCostosTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoCostoSeleccionado;
	private int idEstadoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoCosto;

	//
	public ControladorCrudRoTipoCosto() {
		super();
		roTipoCostoControlador = new RoTipoCosto();
		roTipoCostoVista = new RoTipoCosto();
		tipoCostosTodos = new ArrayList<RoTipoCosto>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoCosto() {
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
		tipoCostosTodos = servicioRoTipoCosto.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoCosto = false;
	}

	// METODOS

	public void guardarTipoCosto() {
		roTipoCostoVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		if (tipoGuardar) {
			if (servicioRoTipoCosto.existeTipoCostoPorNombre(roTipoCostoVista
					.getNombreTico())) {
				servicioRoTipoCosto.insertar(roTipoCostoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Costo Añadido",
						"El Tipo de Costo ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Costo no se debe repetir"));
			}
		} else {

			if (servicioRoTipoCosto.existeTipoCostoPorNombreEx(
					roTipoCostoVista.getNombreTico(),
					roTipoCostoVista.getCodigoTico())) {

				servicioRoTipoCosto.actualizar(roTipoCostoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Costo Actualizado",
						"El Tipo de Costo ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Costo no se debe repetir"));
				roTipoCostoVista.setNombreTico(roTipoCostoControlador
						.getNombreTico());
			}
		}
	}

	public void exitoGuardar() {

		roTipoCostoVista = new RoTipoCosto();
		idEstadoSeleccionado = 1;
		tipoCostosTodos = servicioRoTipoCosto.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoCosto = false;
	}

	public void eliminarTipoCosto() {

		try {
			servicioRoTipoCosto.eliminar(roTipoCostoVista);
			roTipoCostoVista = new RoTipoCosto();
			tipoCostosTodos = servicioRoTipoCosto.buscarTodos();
			idEstadoSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoCosto = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Costo Eliminado",
					"El Tipo de Costo ha sido Eliminado con éxito"));
			exitoGuardar();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de Costo en uso"));
		}
	}

	public void nuevoTipoCostoVista() {
		tipoGuardar = true;
		roTipoCostoVista = new RoTipoCosto();
		idEstadoSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoCosto = true;
	}

	public void cancelar() {
		nuevoTipoCostoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoCosto = false;
		RequestContext.getCurrentInstance().update("formTipoCosto");
	}

	public void editarTipoCostoVista() {
		roTipoCostoControlador.setNombreTico(roTipoCostoVista.getNombreTico());
		tipoGuardar = false;
		idEstadoSeleccionado = (int) roTipoCostoVista.getGenEstado().getCodigoEsta();

		btnGuardar = false;
		btnCancelar = false;
		pnlTipoCosto = true;
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

	public RoTipoCosto getRoTipoCostoVista() {
		return roTipoCostoVista;
	}

	public void setRoTipoCostoVista(RoTipoCosto roTipoCostoVista) {
		this.roTipoCostoVista = roTipoCostoVista;
	}

	public RoTipoCosto getRoTipoCostoControlador() {
		return roTipoCostoControlador;
	}

	public void setRoTipoCostoControlador(RoTipoCosto roTipoCostoControlador) {
		this.roTipoCostoControlador = roTipoCostoControlador;
	}

	public List<RoTipoCosto> getTipoCostosTodos() {
		return tipoCostosTodos;
	}

	public void setTipoCostosTodos(List<RoTipoCosto> TipoCostosTodos) {
		this.tipoCostosTodos = TipoCostosTodos;
	}

	public int getIdTipoCostoSeleccionado() {
		return idTipoCostoSeleccionado;
	}

	public void setIdTipoCostoSeleccionado(int idTipoCostoSeleccionado) {
		this.idTipoCostoSeleccionado = idTipoCostoSeleccionado;
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

	public boolean isPnlTipoCosto() {
		return pnlTipoCosto;
	}

	public void setPnlTipoCosto(boolean pnlTipoCosto) {
		this.pnlTipoCosto = pnlTipoCosto;
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
