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
import com.hc.ro.modelo.RoFactorRiesgo;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioRoFactorRiesgo;

@ManagedBean
@ViewScoped
public class ControladorCrudRoFactorRiesgo {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudFactorRiesgo.jsf";

	// FactorRiesgo
	@EJB
	ServicioRoFactorRiesgo servicioRoFactorRiesgo;
	@EJB
	ServicioGenEstado servicioGenEstado;

	// VARIABLES
	private RoFactorRiesgo roFactorRiesgoVista;
	private RoFactorRiesgo roFactorRiesgoControlador;
	private List<RoFactorRiesgo> tipoAfectasTodos;
	private List<GenEstado> estadosTodos;
	private int idFactorRiesgoSeleccionado;
	private int idEstadoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlFactorRiesgo;

	//
	public ControladorCrudRoFactorRiesgo() {
		super();
		roFactorRiesgoControlador = new RoFactorRiesgo();
		roFactorRiesgoVista = new RoFactorRiesgo();
		tipoAfectasTodos = new ArrayList<RoFactorRiesgo>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoFactorRiesgo() {
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
		tipoAfectasTodos = servicioRoFactorRiesgo.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlFactorRiesgo = false;
	}

	// METODOS

	public void guardarFactorRiesgo() {
		roFactorRiesgoVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		if (tipoGuardar) {
			if (servicioRoFactorRiesgo
					.existeFactorRiesgoPorNombre(roFactorRiesgoVista
							.getNombreFaro())) {
				servicioRoFactorRiesgo.insertar(roFactorRiesgoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage("Factor de Riesgo Operativo Añadido",
								"El Factor de Riesgo Operativo ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de Factor de Riesgo Operativo no se deben repetir"));
			}
		} else {

			if (servicioRoFactorRiesgo.existeFactorRiesgoPorNombreEx(
					roFactorRiesgoVista.getNombreFaro(),
					roFactorRiesgoVista.getCodigoFaro())) {

				servicioRoFactorRiesgo.actualizar(roFactorRiesgoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(
								"Factor de Riesgo Operativo Actualizado",
								"El Factor de Riesgo Operativo ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de Factor de Riesgo Operativo no se deben repetir"));
				roFactorRiesgoVista.setNombreFaro(roFactorRiesgoControlador
						.getNombreFaro());
			}
		}
	}

	public void exitoGuardar() {

		roFactorRiesgoVista = new RoFactorRiesgo();
		idEstadoSeleccionado = 1;
		tipoAfectasTodos = servicioRoFactorRiesgo.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlFactorRiesgo = false;
	}

	public void eliminarFactorRiesgo() {

		try {
			servicioRoFactorRiesgo.eliminar(roFactorRiesgoVista);
			roFactorRiesgoVista = new RoFactorRiesgo();
			tipoAfectasTodos = servicioRoFactorRiesgo.buscarTodos();
			idEstadoSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlFactorRiesgo = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage("Factor de Riesgo Operativo Eliminado",
							"El Factor de Riesgo Operativo ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Factor de Riesgo en Uso"));
		}
	}

	public void nuevoFactorRiesgoVista() {
		tipoGuardar = true;
		roFactorRiesgoVista = new RoFactorRiesgo();
		idEstadoSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlFactorRiesgo = true;
	}

	public void cancelar() {
		nuevoFactorRiesgoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlFactorRiesgo = false;
		RequestContext.getCurrentInstance().update("formFactorRiesgo");
	}

	public void editarFactorRiesgoVista() {
		roFactorRiesgoControlador.setNombreFaro(roFactorRiesgoVista
				.getNombreFaro());
		tipoGuardar = false;
		idEstadoSeleccionado = (int) roFactorRiesgoVista.getGenEstado()
				.getCodigoEsta();

		btnGuardar = false;
		btnCancelar = false;
		pnlFactorRiesgo = true;
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

	public RoFactorRiesgo getRoFactorRiesgoVista() {
		return roFactorRiesgoVista;
	}

	public void setRoFactorRiesgoVista(RoFactorRiesgo roFactorRiesgoVista) {
		this.roFactorRiesgoVista = roFactorRiesgoVista;
	}

	public RoFactorRiesgo getRoFactorRiesgoControlador() {
		return roFactorRiesgoControlador;
	}

	public void setRoFactorRiesgoControlador(
			RoFactorRiesgo roFactorRiesgoControlador) {
		this.roFactorRiesgoControlador = roFactorRiesgoControlador;
	}

	public List<RoFactorRiesgo> getFactorRiesgosTodos() {
		return tipoAfectasTodos;
	}

	public void setFactorRiesgosTodos(List<RoFactorRiesgo> FactorRiesgosTodos) {
		this.tipoAfectasTodos = FactorRiesgosTodos;
	}

	public int getIdFactorRiesgoSeleccionado() {
		return idFactorRiesgoSeleccionado;
	}

	public void setIdFactorRiesgoSeleccionado(int idFactorRiesgoSeleccionado) {
		this.idFactorRiesgoSeleccionado = idFactorRiesgoSeleccionado;
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

	public boolean isPnlFactorRiesgo() {
		return pnlFactorRiesgo;
	}

	public void setPnlFactorRiesgo(boolean pnlFactorRiesgo) {
		this.pnlFactorRiesgo = pnlFactorRiesgo;
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

	public List<RoFactorRiesgo> getTipoAfectasTodos() {
		return tipoAfectasTodos;
	}

	public void setTipoAfectasTodos(List<RoFactorRiesgo> tipoAfectasTodos) {
		this.tipoAfectasTodos = tipoAfectasTodos;
	}

}
