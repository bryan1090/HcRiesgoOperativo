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
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoCaractEvento;
import com.hc.ro.modelo.SisParametro;
import com.hc.ro.negocio.ServicioRoEvento;
import com.hc.ro.negocio.ServicioRoCaractEvento;
import com.hc.ro.negocio.ServicioSisParametro;

@ManagedBean
@ViewScoped
public class ControladorCrudSisParametro {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudParametro.jsf";
	//
	@EJB
	ServicioSisParametro servicioSisParametro;
	// VARIABLES
	private List<SisParametro> parametrosTodos;
	private SisParametro parametroVista;
	private int idParametroSeleccionado;
	private boolean tipoGuardar;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlCaractEvento;

	//
	public ControladorCrudSisParametro() {
		super();
		parametrosTodos = new ArrayList<SisParametro>();
		parametroVista = new SisParametro();
	}

	@PostConstruct
	public void PostControladorCrudRoCaractEvento() {
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

		servicioSisParametro.verificarParametrosSistema();
		parametrosTodos = servicioSisParametro.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlCaractEvento = false;
	}

	// METODOS

	public void guardarCaractEvento() {
		if (tipoGuardar) {
			if (servicioSisParametro.existePorNombre(parametroVista
					.getNombrePara())) {
				servicioSisParametro.insertar(parametroVista);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Parámetro Añadido",
						"El Parámetro ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre del Parámetro no se debe repetir"));
			}
		} else {

			if (servicioSisParametro.existePorNombreEx(
					parametroVista.getNombrePara(),
					parametroVista.getCodigoPara())) {

				servicioSisParametro.actualizar(parametroVista);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Parámetro Actualizado",
						"El Parámetro ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre de la Característica del Evento no se debe repetir"));
			}
			cancelar();
		}
	}

	public void exitoGuardar() {

		parametroVista = new SisParametro();
		parametrosTodos = servicioSisParametro.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlCaractEvento = false;
	}

	public void eliminarCaractEvento() {

		try {
			servicioSisParametro.eliminar(parametroVista);
			parametroVista = new SisParametro();
			parametrosTodos = servicioSisParametro.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlCaractEvento = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Parametro Eliminado",
					"El Parametro ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:", e.getMessage()));
		}
	}

	public void nuevoCaractEventoVista() {
		tipoGuardar = true;
		parametroVista = new SisParametro();
		btnGuardar = false;
		btnCancelar = false;
		pnlCaractEvento = true;
	}

	public void cancelar() {
		nuevoCaractEventoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlCaractEvento = false;
		RequestContext.getCurrentInstance().update("formParametro");
	}

	public void editarCaractEventoVista() {
		tipoGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlCaractEvento = true;
		btnAnadir = true;

	}

	// GETTERS Y SETTERS

	public List<SisParametro> getParametrosTodos() {
		return parametrosTodos;
	}

	public void setParametrosTodos(List<SisParametro> parametrosTodos) {
		this.parametrosTodos = parametrosTodos;
	}

	public SisParametro getParametroVista() {
		return parametroVista;
	}

	public void setParametroVista(SisParametro parametroVista) {
		this.parametroVista = parametroVista;
	}

	public int getIdParametroSeleccionado() {
		return idParametroSeleccionado;
	}

	public void setIdParametroSeleccionado(int idParametroSeleccionado) {
		this.idParametroSeleccionado = idParametroSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

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

	public boolean isPnlCaractEvento() {
		return pnlCaractEvento;
	}

	public void setPnlCaractEvento(boolean pnlCaractEvento) {
		this.pnlCaractEvento = pnlCaractEvento;
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