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
import com.hc.ro.modelo.RoCriticidad;
import com.hc.ro.negocio.ServicioRoCriticidad;

@ManagedBean
@ViewScoped
public class ControladorCrudRoCriticidad {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudCriticidad.jsf";
	
	// Criticidad
	@EJB
	ServicioRoCriticidad servicioRoCriticidad;
	

	// VARIABLES
	private RoCriticidad roCriticidadVista;
	private RoCriticidad roCriticidadControlador;
	private List<RoCriticidad> tipoAgenciasTodos;
	private List<GenEstado> estadosTodos;
	private int idCriticidadSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlCriticidad;

	//
	public ControladorCrudRoCriticidad() {
		super();
		roCriticidadControlador = new RoCriticidad();
		roCriticidadVista = new RoCriticidad();
		tipoAgenciasTodos = new ArrayList<RoCriticidad>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoCriticidad() {
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
		tipoAgenciasTodos = servicioRoCriticidad.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlCriticidad = false;
	}

	// METODOS
		
	public void guardarCriticidad() {
		if (tipoGuardar) {
			if (servicioRoCriticidad.existeCriticidadPorNombre(roCriticidadVista
					.getNombreCrit())) {
				servicioRoCriticidad.insertar(roCriticidadVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Criticidad Añadida",
						"La Criticidad ha sido Añadida con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Criticidad no se debe repetir"));
			}
		} else {

			if (servicioRoCriticidad.existeCriticidadPorNombreEx(
					roCriticidadVista.getNombreCrit(),
					roCriticidadVista.getCodigoCrit())) {

				servicioRoCriticidad.actualizar(roCriticidadVista);
				
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Criticidad Actualizada",
						"La Criticidad ha sido Actualizada con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Criticidad no se debe repetir"));
				roCriticidadVista.setNombreCrit(roCriticidadControlador
						.getNombreCrit());
			}
		}
	}

	public void exitoGuardar() {
		
		roCriticidadVista = new RoCriticidad();
		tipoAgenciasTodos = servicioRoCriticidad.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlCriticidad = false;
	}

	public void eliminarCriticidad() {
			
			try {
				servicioRoCriticidad.eliminar(roCriticidadVista);
				roCriticidadVista = new RoCriticidad();
				tipoAgenciasTodos = servicioRoCriticidad.buscarTodos();
				btnGuardar = true;
				btnCancelar = true;
				pnlCriticidad = false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Criticidad Eliminada",
						"La Criticidad ha sido Eliminada con éxito"));
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
						"Criticidad en uso"));
			}		
	}

	public void nuevoCriticidadVista() {
		tipoGuardar = true;
		roCriticidadVista = new RoCriticidad();
		btnGuardar = false;
		btnCancelar = false;
		pnlCriticidad = true;
	}

	public void cancelar() {
		nuevoCriticidadVista();
		btnGuardar = true;
		btnAnadir=false;
		btnCancelar = true;
		pnlCriticidad = false;
		RequestContext.getCurrentInstance().update("formCriticidad");
	}

	public void editarCriticidadVista() {
		roCriticidadControlador.setNombreCrit(roCriticidadVista.getNombreCrit());
		tipoGuardar = false;
		
		btnGuardar = false;
		btnCancelar = false;
		pnlCriticidad = true;
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

	public RoCriticidad getRoCriticidadVista() {
		return roCriticidadVista;
	}

	public void setRoCriticidadVista(RoCriticidad roCriticidadVista) {
		this.roCriticidadVista = roCriticidadVista;
	}

	public RoCriticidad getRoCriticidadControlador() {
		return roCriticidadControlador;
	}

	public void setRoCriticidadControlador(RoCriticidad roCriticidadControlador) {
		this.roCriticidadControlador = roCriticidadControlador;
	}

	public List<RoCriticidad> getCriticidadsTodos() {
		return tipoAgenciasTodos;
	}

	public void setCriticidadsTodos(List<RoCriticidad> CriticidadsTodos) {
		this.tipoAgenciasTodos = CriticidadsTodos;
	}

	public int getIdCriticidadSeleccionado() {
		return idCriticidadSeleccionado;
	}

	public void setIdCriticidadSeleccionado(int idCriticidadSeleccionado) {
		this.idCriticidadSeleccionado = idCriticidadSeleccionado;
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

	public boolean isPnlCriticidad() {
		return pnlCriticidad;
	}

	public void setPnlCriticidad(boolean pnlCriticidad) {
		this.pnlCriticidad = pnlCriticidad;
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

	public List<RoCriticidad> getTipoAgenciasTodos() {
		return tipoAgenciasTodos;
	}

	public void setTipoAgenciasTodos(List<RoCriticidad> tipoAgenciasTodos) {
		this.tipoAgenciasTodos = tipoAgenciasTodos;
	}

}
