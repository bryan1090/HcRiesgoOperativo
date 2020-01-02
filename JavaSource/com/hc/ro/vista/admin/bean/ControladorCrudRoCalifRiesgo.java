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
import com.hc.ro.modelo.RoCalifRiesgo;
import com.hc.ro.negocio.ServicioRoCalifRiesgo;

@ManagedBean
@ViewScoped
public class ControladorCrudRoCalifRiesgo {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudCalifRiesgo.jsf";
	// CalifRiesgo
	@EJB
	ServicioRoCalifRiesgo servicioRoCalifRiesgo;

	// VARIABLES
	private RoCalifRiesgo roCalifRiesgoVista;
	private RoCalifRiesgo roCalifRiesgoControlador;
	private List<RoCalifRiesgo> califRiesgosTodos;
	private List<GenEstado> estadosTodos;
	private int idCalifRiesgoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlCalifRiesgo;

	//
	public ControladorCrudRoCalifRiesgo() {
		super();
		roCalifRiesgoControlador = new RoCalifRiesgo();
		roCalifRiesgoVista = new RoCalifRiesgo();
		califRiesgosTodos = new ArrayList<RoCalifRiesgo>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoCalifRiesgo() {
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
		califRiesgosTodos = servicioRoCalifRiesgo.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlCalifRiesgo = false;
	}

	// METODOS
	public void guardarCalifRiesgo() {
		if (tipoGuardar) {
			if (servicioRoCalifRiesgo
					.existeCalifRiesgoPorNombre(roCalifRiesgoVista
							.getNombreClrs())) {
				servicioRoCalifRiesgo.insertar(roCalifRiesgoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(
								"Tipo de Calificación de Riesgo Añadido",
								"El Tipo de Calificación de Riesgo ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de CalifRiesgo no se debe repetir"));
			}
		} else {

			if (servicioRoCalifRiesgo.existeCalifRiesgoPorNombreEx(
					roCalifRiesgoVista.getNombreClrs(),
					roCalifRiesgoVista.getCodigoClrs())) {

				servicioRoCalifRiesgo.actualizar(roCalifRiesgoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de CalifRiesgo Actualizado",
						"El Tipo de CalifRiesgo ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de CalifRiesgo no se deben repetir"));
				roCalifRiesgoVista.setNombreClrs(roCalifRiesgoControlador
						.getNombreClrs());
			}
		}
	}

	public void exitoGuardar() {

		roCalifRiesgoVista = new RoCalifRiesgo();
		califRiesgosTodos = servicioRoCalifRiesgo.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlCalifRiesgo = false;
	}

	public void eliminarCalifRiesgo() {

		try {
			servicioRoCalifRiesgo.eliminar(roCalifRiesgoVista);
			roCalifRiesgoVista = new RoCalifRiesgo();
			califRiesgosTodos = servicioRoCalifRiesgo.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlCalifRiesgo = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de CalifRiesgo Eliminado",
					"El Tipo de CalifRiesgo ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Calificación de Riesgo en Uso"));
		}
	}

	public void nuevoCalifRiesgoVista() {
		tipoGuardar = true;
		roCalifRiesgoVista = new RoCalifRiesgo();
		btnGuardar = false;
		btnCancelar = false;
		pnlCalifRiesgo = true;
	}

	public void cancelar() {
		nuevoCalifRiesgoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlCalifRiesgo = false;
		RequestContext.getCurrentInstance().update("formCalifRiesgo");
	}

	public void editarCalifRiesgoVista() {
		roCalifRiesgoControlador.setNombreClrs(roCalifRiesgoVista
				.getNombreClrs());
		tipoGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlCalifRiesgo = true;
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

	public RoCalifRiesgo getRoCalifRiesgoVista() {
		return roCalifRiesgoVista;
	}

	public void setRoCalifRiesgoVista(RoCalifRiesgo roCalifRiesgoVista) {
		this.roCalifRiesgoVista = roCalifRiesgoVista;
	}

	public RoCalifRiesgo getRoCalifRiesgoControlador() {
		return roCalifRiesgoControlador;
	}

	public void setRoCalifRiesgoControlador(
			RoCalifRiesgo roCalifRiesgoControlador) {
		this.roCalifRiesgoControlador = roCalifRiesgoControlador;
	}

	public List<RoCalifRiesgo> getCalifRiesgosTodos() {
		return califRiesgosTodos;
	}

	public void setCalifRiesgosTodos(List<RoCalifRiesgo> califRiesgosTodos) {
		this.califRiesgosTodos = califRiesgosTodos;
	}

	public int getIdCalifRiesgoSeleccionado() {
		return idCalifRiesgoSeleccionado;
	}

	public void setIdCalifRiesgoSeleccionado(int idCalifRiesgoSeleccionado) {
		this.idCalifRiesgoSeleccionado = idCalifRiesgoSeleccionado;
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

	public boolean isPnlCalifRiesgo() {
		return pnlCalifRiesgo;
	}

	public void setPnlCalifRiesgo(boolean pnlCalifRiesgo) {
		this.pnlCalifRiesgo = pnlCalifRiesgo;
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
