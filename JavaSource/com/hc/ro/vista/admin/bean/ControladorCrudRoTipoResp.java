package com.hc.ro.vista.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoTipoResp;
import com.hc.ro.modelo.SisDetalleMenu;
import com.hc.ro.negocio.ServicioRoTipoResp;
import com.hc.ro.negocio.ServicioSisDetalleMenu;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoResp {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudTipoResp.jsf";
	// TipoAfecta
	@EJB
	ServicioRoTipoResp servicioRoTipoResp;
	@EJB
	ServicioSisDetalleMenu servicioSisDetalleMenu;

	// VARIABLES
	private RoTipoResp roTipoRespVista;
	private RoTipoResp roTipoRespControlador;
	private List<RoTipoResp> tipoRespTodos;
	private List<SisDetalleMenu> detalleMenuTodos;
	private int idTipoRespSeleccionado;
	private int idDetalleMenuSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlTipoResp;

	//
	public ControladorCrudRoTipoResp() {
		super();
		roTipoRespControlador = new RoTipoResp();
		roTipoRespVista = new RoTipoResp();
		tipoRespTodos = new ArrayList<RoTipoResp>();
		detalleMenuTodos = new ArrayList<SisDetalleMenu>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoAfecta() {
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
		tipoRespTodos = servicioRoTipoResp.buscarTodos();
		detalleMenuTodos = servicioSisDetalleMenu.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlTipoResp = false;
	}

	// METODOS

	public void guardarTipoResp() {
		roTipoRespVista.setSisDetalleMenu(servicioSisDetalleMenu
				.buscarPorId(idDetalleMenuSeleccionado));
		if (tipoGuardar) {
			// preguntar if
			if (servicioRoTipoResp.existeTipoRespPorNombre(roTipoRespVista
					.getNombreTres())) {
				servicioRoTipoResp.insertar(roTipoRespVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Responsabilidad Añadido",
						"El tipo de Responsabilidad ha sido Añadido con Exito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre del tipo de Responsabilidad no se debe repetir"));
			}
		} else {
			// preguntar if
			if (servicioRoTipoResp.existePorNombreEx(
					roTipoRespVista.getNombreTres(),
					roTipoRespVista.getCodigoTres())) {

				servicioRoTipoResp.actualizar(roTipoRespVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Responsabilidad Actualizado",
						"El tipo de responsable ha sido Actualizado con Exito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Tipo de Afectación no se deben repetir"));
				roTipoRespVista.setNombreTres(roTipoRespControlador
						.getNombreTres());
			}
		}
	}

	public void exitoGuardar() {

		roTipoRespVista = new RoTipoResp();
		idDetalleMenuSeleccionado = 431;
		tipoRespTodos = servicioRoTipoResp.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlTipoResp = false;
	}

	public void eliminarTipoResp() {

		try {
			servicioRoTipoResp.eliminar(roTipoRespVista);
			roTipoRespVista = new RoTipoResp();
			tipoRespTodos = servicioRoTipoResp.buscarTodos();
			idDetalleMenuSeleccionado = 431;
			btnGuardar = true;
			btnCancelar = true;
			pnlTipoResp = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Responsabilidad Eliminado",
					"El tipo de responsabilidad ha sido Eliminado con Exito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Tipo de Responsabilidad en uso"));
		}
	}

	public void nuevoTipoRespVista() {
		tipoGuardar = true;
		roTipoRespVista = new RoTipoResp();
		idDetalleMenuSeleccionado = 431;
		btnGuardar = false;
		btnCancelar = false;
		pnlTipoResp = true;
	}

	public void cancelar() {
		nuevoTipoRespVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlTipoResp = false;
		RequestContext.getCurrentInstance().update("formTipoAfecta");
	}

	public void editarTipoRespVista() {
		roTipoRespControlador.setNombreTres(roTipoRespVista.getNombreTres());
		tipoGuardar = false;
		idDetalleMenuSeleccionado = roTipoRespVista.getSisDetalleMenu()
				.getCodigoDeme();

		btnGuardar = false;
		btnCancelar = false;
		pnlTipoResp = true;
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

	public int getIdTipoAfectaSeleccionado() {
		return idTipoRespSeleccionado;
	}

	public void setIdTipoAfectaSeleccionado(int idTipoAfectaSeleccionado) {
		this.idTipoRespSeleccionado = idTipoAfectaSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public RoTipoResp getRoTipoRespVista() {
		return roTipoRespVista;
	}

	public void setRoTipoRespVista(RoTipoResp roTipoRespVista) {
		this.roTipoRespVista = roTipoRespVista;
	}

	public RoTipoResp getRoTipoRespControlador() {
		return roTipoRespControlador;
	}

	public void setRoTipoRespControlador(RoTipoResp roTipoRespControlador) {
		this.roTipoRespControlador = roTipoRespControlador;
	}

	public List<RoTipoResp> getTipoRespTodos() {
		return tipoRespTodos;
	}

	public void setTipoRespTodos(List<RoTipoResp> tipoRespTodos) {
		this.tipoRespTodos = tipoRespTodos;
	}

	public List<SisDetalleMenu> getDetalleMenuTodos() {
		return detalleMenuTodos;
	}

	public void setDetalleMenuTodos(List<SisDetalleMenu> detalleMenuTodos) {
		this.detalleMenuTodos = detalleMenuTodos;
	}

	public int getIdTipoRespSeleccionado() {
		return idTipoRespSeleccionado;
	}

	public void setIdTipoRespSeleccionado(int idTipoRespSeleccionado) {
		this.idTipoRespSeleccionado = idTipoRespSeleccionado;
	}

	public int getIdDetalleMenuSeleccionado() {
		return idDetalleMenuSeleccionado;
	}

	public void setIdDetalleMenuSeleccionado(int idDetalleMenuSeleccionado) {
		this.idDetalleMenuSeleccionado = idDetalleMenuSeleccionado;
	}

	public boolean isPnlTipoResp() {
		return pnlTipoResp;
	}

	public void setPnlTipoResp(boolean pnlTipoResp) {
		this.pnlTipoResp = pnlTipoResp;
	}

	public int getIdConsecuenciaSeleccionado() {
		return idTipoRespSeleccionado;
	}

	public void setIdConsecuenciaSeleccionado(int idConsecuenciaSeleccionado) {
		this.idTipoRespSeleccionado = idConsecuenciaSeleccionado;
	}

	public int getIdNegocioSeleccionado() {
		return idDetalleMenuSeleccionado;
	}

	public void setIdNegocioSeleccionado(int idNegocioSeleccionado) {
		this.idDetalleMenuSeleccionado = idNegocioSeleccionado;
	}

	public void setIdEstadoSeleccionado(int idEstadoSeleccionado) {
		this.idDetalleMenuSeleccionado = idEstadoSeleccionado;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlTipoAfecta() {
		return pnlTipoResp;
	}

	public void setPnlTipoAfecta(boolean pnlTipoAfecta) {
		this.pnlTipoResp = pnlTipoAfecta;
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
