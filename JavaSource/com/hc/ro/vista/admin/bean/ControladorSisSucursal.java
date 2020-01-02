package com.hc.ro.vista.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.hc.ro.modelo.GenUbicGeo;
import com.hc.ro.modelo.SisSucursal;
import com.hc.ro.negocio.ServicioGenUbicGeo;
import com.hc.ro.negocio.ServicioSisSucursal;

@ManagedBean
@ViewScoped
public class ControladorSisSucursal {

	// NEGOCIO
	@EJB
	ServicioGenUbicGeo servicioGenUbicGeo;
	@EJB
	ServicioSisSucursal servicioSisSucursal;

	// VARIABLES
	private SisSucursal sisSucursalVista;
	private SisSucursal sisSucursalControlador;
	private List<SisSucursal> sucursalesTodos;
	private List<GenUbicGeo> ubicGeoTodos;
	private int idUbicGeoSeleccionado;
	private int idSucursalSeleccionado;
	private boolean tipoGuardar;

	// CONSTRUCTOR
	/**
	 * INICIALIZA LAS VARIABLES Y LLENA LA LISTA PARA LOS COMBOS Y LA TABLA
	 */
	public ControladorSisSucursal() {
		super();
		sisSucursalVista = new SisSucursal();
		sisSucursalControlador = new SisSucursal();
		sucursalesTodos = new ArrayList<SisSucursal>();
		ubicGeoTodos = new ArrayList<GenUbicGeo>();
	}

	@PostConstruct
	public void PostControladorSisSucursal() {
		setTipoGuardar(true);
		sucursalesTodos = servicioSisSucursal.buscarTodos();
		ubicGeoTodos = servicioGenUbicGeo.buscarTodos();
	}

	// METODOS

	// GETTERS Y SETTERS
	public SisSucursal getSisSucursalVista() {
		return sisSucursalVista;
	}

	public void setSisSucursalVista(SisSucursal sisSucursalVista) {
		this.sisSucursalVista = sisSucursalVista;
	}

	public SisSucursal getSisSucursalControlador() {
		return sisSucursalControlador;
	}

	public void setSisSucursalControlador(SisSucursal sisSucursalControlador) {
		this.sisSucursalControlador = sisSucursalControlador;
	}

	public List<SisSucursal> getSucursalesTodos() {
		return sucursalesTodos;
	}

	public void setSucursalesTodos(List<SisSucursal> sucursalesTodos) {
		this.sucursalesTodos = sucursalesTodos;
	}

	public List<GenUbicGeo> getUbicGeoTodos() {
		return ubicGeoTodos;
	}

	public void setUbicGeoTodos(List<GenUbicGeo> ubicGeoTodos) {
		this.ubicGeoTodos = ubicGeoTodos;
	}

	public int getIdUbicGeoSeleccionado() {
		return idUbicGeoSeleccionado;
	}

	public void setIdUbicGeoSeleccionado(int idUbicGeoSeleccionado) {
		this.idUbicGeoSeleccionado = idUbicGeoSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public int getIdSucursalSeleccionado() {
		return idSucursalSeleccionado;
	}

	public void setIdSucursalSeleccionado(int idSucursalSeleccionado) {
		this.idSucursalSeleccionado = idSucursalSeleccionado;
	}

}
