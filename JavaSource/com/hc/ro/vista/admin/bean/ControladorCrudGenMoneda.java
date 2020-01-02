package com.hc.ro.vista.admin.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.GenMoneda;
import com.hc.ro.negocio.ServicioGenMoneda;

@ManagedBean
@ViewScoped
public class ControladorCrudGenMoneda {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudMoneda.jsf";

	@EJB
	ServicioGenMoneda servicioGenMoneda;
	private List<GenMoneda> genMonedas;
	private boolean btnAnadir;

	public ControladorCrudGenMoneda() {
		super();
	}

	@PostConstruct
	public void postControladorCrudGenMoneda() {
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
	}

	public void nuevoTipoMoneda() {

	}

	// Getters y setters

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

	public List<GenMoneda> getGenMonedas() {
		return genMonedas;
	}

	public void setGenMonedas(List<GenMoneda> genMonedas) {
		this.genMonedas = genMonedas;
	}

	public boolean isBtnAnadir() {
		return btnAnadir;
	}

	public void setBtnAnadir(boolean btnAnadir) {
		this.btnAnadir = btnAnadir;
	}

}
