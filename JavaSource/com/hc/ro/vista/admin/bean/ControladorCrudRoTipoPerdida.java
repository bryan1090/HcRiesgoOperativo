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
import com.hc.ro.modelo.RoTipoPerdida;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioRoTipoPerdida;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoPerdida {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoPerdida.jsf";
	// TipoPerdida
	@EJB
	ServicioRoTipoPerdida servicioRoTipoPerdida;
	@EJB
	ServicioGenEstado servicioGenEstado;

	// VARIABLES
	private RoTipoPerdida roTipoPerdidaVista;
	private RoTipoPerdida roTipoPerdidaControlador;
	private List<RoTipoPerdida> tipoPerdidasTodos;
	private List<GenEstado> estadosTodos;
	private int idTipoPerdidaSeleccionado;
	private int idEstadoSeleccionado;
	private int idTipoRegistroSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoPerdida;

	//
	public ControladorCrudRoTipoPerdida() {
		super();
		roTipoPerdidaControlador = new RoTipoPerdida();
		roTipoPerdidaVista = new RoTipoPerdida();
		tipoPerdidasTodos = new ArrayList<RoTipoPerdida>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoPerdida() {
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
		tipoPerdidasTodos = servicioRoTipoPerdida.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoPerdida = false;
	}

	// METODOS

	public void guardarTipoPerdida() {
		roTipoPerdidaVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		if (tipoGuardar) {
			if (servicioRoTipoPerdida
					.existeTipoPerdidaPorNombre(roTipoPerdidaVista
							.getNombreTipe())) {
				servicioRoTipoPerdida.insertar(roTipoPerdidaVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Pérdida Añadido",
						"El Tipo de Pérdida ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Pérdida no se deben repetir"));
			}
		} else {

			if (servicioRoTipoPerdida.existeTipoPerdidaPorNombreEx(
					roTipoPerdidaVista.getNombreTipe(),
					roTipoPerdidaVista.getCodigoTipe())) {

				servicioRoTipoPerdida.actualizar(roTipoPerdidaVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Pérdida Actualizado",
						"El Tipo de Pérdida ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Pérdida no se deben repetir"));
				roTipoPerdidaVista.setNombreTipe(roTipoPerdidaControlador
						.getNombreTipe());
			}
		}
	}

	public void exitoGuardar() {

		roTipoPerdidaVista = new RoTipoPerdida();
		idEstadoSeleccionado = 1;
		tipoPerdidasTodos = servicioRoTipoPerdida.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoPerdida = false;
	}

	public void eliminarTipoPerdida() {

		try {
			servicioRoTipoPerdida.eliminar(roTipoPerdidaVista);
			roTipoPerdidaVista = new RoTipoPerdida();
			tipoPerdidasTodos = servicioRoTipoPerdida.buscarTodos();
			idEstadoSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoPerdida = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Pérdida Eliminado",
					"El Tipo de Pérdida ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de Pérdida en uso"));
		}
	}

	public void nuevoTipoPerdidaVista() {
		roTipoPerdidaVista.setTipoRegistro(1);
		tipoGuardar = true;
		roTipoPerdidaVista = new RoTipoPerdida();
		idEstadoSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoPerdida = true;
	}

	public void cancelar() {
		nuevoTipoPerdidaVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoPerdida = false;
		RequestContext.getCurrentInstance().update("formTipoPerdida");
	}

	public void editarTipoPerdidaVista() {
		roTipoPerdidaControlador.setNombreTipe(roTipoPerdidaVista
				.getNombreTipe());
		tipoGuardar = false;
		idEstadoSeleccionado = (int) roTipoPerdidaVista.getGenEstado()
				.getCodigoEsta();

		btnGuardar = false;
		btnCancelar = false;
		pnlTipoPerdida = true;
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

	public RoTipoPerdida getRoTipoPerdidaVista() {
		return roTipoPerdidaVista;
	}

	public void setRoTipoPerdidaVista(RoTipoPerdida roTipoPerdidaVista) {
		this.roTipoPerdidaVista = roTipoPerdidaVista;
	}

	public RoTipoPerdida getRoTipoPerdidaControlador() {
		return roTipoPerdidaControlador;
	}

	public void setRoTipoPerdidaControlador(
			RoTipoPerdida roTipoPerdidaControlador) {
		this.roTipoPerdidaControlador = roTipoPerdidaControlador;
	}

	public List<RoTipoPerdida> getTipoPerdidasTodos() {
		return tipoPerdidasTodos;
	}

	public void setTipoPerdidasTodos(List<RoTipoPerdida> TipoPerdidasTodos) {
		this.tipoPerdidasTodos = TipoPerdidasTodos;
	}

	public int getIdTipoPerdidaSeleccionado() {
		return idTipoPerdidaSeleccionado;
	}

	public void setIdTipoPerdidaSeleccionado(int idTipoPerdidaSeleccionado) {
		this.idTipoPerdidaSeleccionado = idTipoPerdidaSeleccionado;
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

	public boolean isPnlTipoPerdida() {
		return pnlTipoPerdida;
	}

	public void setPnlTipoPerdida(boolean pnlTipoPerdida) {
		this.pnlTipoPerdida = pnlTipoPerdida;
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

	public int getIdTipoRegistroSeleccionado() {
		return idTipoRegistroSeleccionado;
	}

	public void setIdTipoRegistroSeleccionado(int idTipoRegistroSeleccionado) {
		this.idTipoRegistroSeleccionado = idTipoRegistroSeleccionado;
	}

}
