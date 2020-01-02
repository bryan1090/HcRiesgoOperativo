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
import com.hc.ro.modelo.RoCatObjetivo;
import com.hc.ro.negocio.ServicioRoCatObjetivo;

@ManagedBean
@ViewScoped
public class ControladorCrudRoCatObjetivo {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudCatObjetivo.jsf";
	// CatObjetivo
	@EJB
	ServicioRoCatObjetivo servicioRoCatObjetivo;

	// VARIABLES
	private RoCatObjetivo roCatObjetivoVista;
	private RoCatObjetivo roCatObjetivoControlador;
	private List<RoCatObjetivo> califObjetivosTodos;
	private List<GenEstado> estadosTodos;
	private int idCatObjetivoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlCatObjetivo;

	//
	public ControladorCrudRoCatObjetivo() {
		super();
		roCatObjetivoControlador = new RoCatObjetivo();
		roCatObjetivoVista = new RoCatObjetivo();
		califObjetivosTodos = new ArrayList<RoCatObjetivo>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoCatObjetivo() {
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
		califObjetivosTodos = servicioRoCatObjetivo.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlCatObjetivo = false;
	}

	// METODOS
	public void guardarCatObjetivo() {
		if (tipoGuardar) {
			if (servicioRoCatObjetivo
					.existeCatObjetivoPorNombre(roCatObjetivoVista
							.getNombreCobj())) {
				servicioRoCatObjetivo.insertar(roCatObjetivoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Categoría de Objetivo Añadido",
						"La Categoría de Objetivo sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de Categoría de Objetivo no se debe repetir"));
			}
		} else {

			if (servicioRoCatObjetivo.existeCatObjetivoPorNombreEx(
					roCatObjetivoVista.getNombreCobj(),
					roCatObjetivoVista.getCodigoCobj())) {

				servicioRoCatObjetivo.actualizar(roCatObjetivoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage("Categoría de Objetivo Actualizado",
								"La Categoría de Objetivo ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de la Categoría de Objetivo no se debe repetir"));
				roCatObjetivoVista.setNombreCobj(roCatObjetivoControlador
						.getNombreCobj());
			}
		}
	}

	public void exitoGuardar() {

		roCatObjetivoVista = new RoCatObjetivo();
		califObjetivosTodos = servicioRoCatObjetivo.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlCatObjetivo = false;
	}

	public void eliminarCatObjetivo() {

		try {
			servicioRoCatObjetivo.eliminar(roCatObjetivoVista);
			roCatObjetivoVista = new RoCatObjetivo();
			califObjetivosTodos = servicioRoCatObjetivo.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlCatObjetivo = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Categoría de Objetivo Eliminado",
					"La Categoría de Objetivo ha sido Eliminada con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Categoria de Objetivo en uso"));
		}
	}

	public void nuevoCatObjetivoVista() {
		tipoGuardar = true;
		roCatObjetivoVista = new RoCatObjetivo();
		btnGuardar = false;
		btnCancelar = false;
		pnlCatObjetivo = true;
	}

	public void cancelar() {
		nuevoCatObjetivoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlCatObjetivo = false;
		RequestContext.getCurrentInstance().update("formCatObjetivo");
	}

	public void editarCatObjetivoVista() {
		roCatObjetivoControlador.setNombreCobj(roCatObjetivoVista
				.getNombreCobj());
		tipoGuardar = false;

		btnGuardar = false;
		btnCancelar = false;
		pnlCatObjetivo = true;
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

	public RoCatObjetivo getRoCatObjetivoVista() {
		return roCatObjetivoVista;
	}

	public void setRoCatObjetivoVista(RoCatObjetivo roCatObjetivoVista) {
		this.roCatObjetivoVista = roCatObjetivoVista;
	}

	public RoCatObjetivo getRoCatObjetivoControlador() {
		return roCatObjetivoControlador;
	}

	public void setRoCatObjetivoControlador(
			RoCatObjetivo roCatObjetivoControlador) {
		this.roCatObjetivoControlador = roCatObjetivoControlador;
	}

	public List<RoCatObjetivo> getCatObjetivosTodos() {
		return califObjetivosTodos;
	}

	public void setCatObjetivosTodos(List<RoCatObjetivo> califObjetivosTodos) {
		this.califObjetivosTodos = califObjetivosTodos;
	}

	public int getIdCatObjetivoSeleccionado() {
		return idCatObjetivoSeleccionado;
	}

	public void setIdCatObjetivoSeleccionado(int idCatObjetivoSeleccionado) {
		this.idCatObjetivoSeleccionado = idCatObjetivoSeleccionado;
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

	public boolean isPnlCatObjetivo() {
		return pnlCatObjetivo;
	}

	public void setPnlCatObjetivo(boolean pnlCatObjetivo) {
		this.pnlCatObjetivo = pnlCatObjetivo;
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

	public List<RoCatObjetivo> getCalifObjetivosTodos() {
		return califObjetivosTodos;
	}

	public void setCalifObjetivosTodos(List<RoCatObjetivo> califObjetivosTodos) {
		this.califObjetivosTodos = califObjetivosTodos;
	}

}
