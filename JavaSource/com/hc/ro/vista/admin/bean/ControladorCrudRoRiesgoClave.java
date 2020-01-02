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
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoRiesgoClave;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoRiesgoClave;

@ManagedBean
@ViewScoped
public class ControladorCrudRoRiesgoClave {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudRiesgoClave.jsf";
	// RiesgoClave
	@EJB
	ServicioRoRiesgoClave servicioRoRiesgoClave;
	@EJB
	ServicioRoProceso servicioRoProceso;

	// VARIABLES
	private RoRiesgoClave roRiesgoClaveVista;
	private RoRiesgoClave roRiesgoClaveControlador;
	private List<RoRiesgoClave> califObjetivosTodos;
	private List<GenEstado> estadosTodos;
	private List<RoProceso> procesosTodos;
	private int idProcesoSeleccionado;
	private int idRiesgoClaveSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlRiesgoClave;

	//
	public ControladorCrudRoRiesgoClave() {
		super();
		roRiesgoClaveControlador = new RoRiesgoClave();
		roRiesgoClaveVista = new RoRiesgoClave();
		califObjetivosTodos = new ArrayList<RoRiesgoClave>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
		procesosTodos = new ArrayList<RoProceso>();

	}

	@PostConstruct
	public void PostControladorCrudRoRiesgoClave() {
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
		califObjetivosTodos = servicioRoRiesgoClave.buscarTodos();
		procesosTodos = servicioRoProceso.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlRiesgoClave = false;
	}

	// METODOS

	
	public void guardarRiesgoClave() {
		System.out.println("Entra");
		roRiesgoClaveVista.setRoProceso(servicioRoProceso
				.buscarPorId(idProcesoSeleccionado));
		if (tipoGuardar) {
			if (servicioRoRiesgoClave
					.existeRiesgoClavePorNombre(roRiesgoClaveVista
							.getNombreRicl())) {
				servicioRoRiesgoClave.insertar(roRiesgoClaveVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Riesgo Clave Añadido",
						"El Riesgo Clave sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Riesgo Clave no se debe repetir"));
			}
		} else {

			if (servicioRoRiesgoClave.existeRiesgoClavePorNombreEx(
					roRiesgoClaveVista.getNombreRicl(),
					roRiesgoClaveVista.getCodigoRicl())) {

				servicioRoRiesgoClave.actualizar(roRiesgoClaveVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Riesgo Clave Actualizado",
						"El Riesgo Clave ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Riesgo Clave se debe repetir"));
				roRiesgoClaveVista.setNombreRicl(roRiesgoClaveControlador
						.getNombreRicl());
			}
		}
	}

	public void exitoGuardar() {

		roRiesgoClaveVista = new RoRiesgoClave();
		califObjetivosTodos = servicioRoRiesgoClave.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlRiesgoClave = false;
	}

	public void eliminarRiesgoClave() {

		try {
			servicioRoRiesgoClave.eliminar(roRiesgoClaveVista);
			roRiesgoClaveVista = new RoRiesgoClave();
			califObjetivosTodos = servicioRoRiesgoClave.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlRiesgoClave = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Riesgo Clave Eliminado",
					"El Riesgo Clave ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Riesgo Clave en Uso"));
		}
	}

	public void nuevoRiesgoClaveVista() {
		System.out.println("Entra");
		tipoGuardar = true;
		roRiesgoClaveVista = new RoRiesgoClave();
		btnGuardar = false;
		btnCancelar = false;
		pnlRiesgoClave = true;
	}

	public void cancelar() {
		nuevoRiesgoClaveVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlRiesgoClave = false;
		RequestContext.getCurrentInstance().update("formRiesgoClave");
	}

	public void editarRiesgoClaveVista() {
		roRiesgoClaveControlador.setNombreRicl(roRiesgoClaveVista
				.getNombreRicl());
		tipoGuardar = false;
		idProcesoSeleccionado = roRiesgoClaveVista.getRoProceso()
				.getCodigoPros();
		btnGuardar = false;
		btnCancelar = false;
		pnlRiesgoClave = true;
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

	public RoRiesgoClave getRoRiesgoClaveVista() {
		return roRiesgoClaveVista;
	}

	public void setRoRiesgoClaveVista(RoRiesgoClave roRiesgoClaveVista) {
		this.roRiesgoClaveVista = roRiesgoClaveVista;
	}

	public RoRiesgoClave getRoRiesgoClaveControlador() {
		return roRiesgoClaveControlador;
	}

	public void setRoRiesgoClaveControlador(
			RoRiesgoClave roRiesgoClaveControlador) {
		this.roRiesgoClaveControlador = roRiesgoClaveControlador;
	}

	public List<RoRiesgoClave> getRiesgoClavesTodos() {
		return califObjetivosTodos;
	}

	public void setRiesgoClavesTodos(List<RoRiesgoClave> califObjetivosTodos) {
		this.califObjetivosTodos = califObjetivosTodos;
	}

	public int getIdRiesgoClaveSeleccionado() {
		return idRiesgoClaveSeleccionado;
	}

	public void setIdRiesgoClaveSeleccionado(int idRiesgoClaveSeleccionado) {
		this.idRiesgoClaveSeleccionado = idRiesgoClaveSeleccionado;
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

	public boolean isPnlRiesgoClave() {
		return pnlRiesgoClave;
	}

	public void setPnlRiesgoClave(boolean pnlRiesgoClave) {
		this.pnlRiesgoClave = pnlRiesgoClave;
	}

	public List<RoProceso> getProcesosTodos() {
		return procesosTodos;
	}

	public void setProcesosTodos(List<RoProceso> procesosTodos) {
		this.procesosTodos = procesosTodos;
	}

	public int getIdProcesoSeleccionado() {
		return idProcesoSeleccionado;
	}

	public void setIdProcesoSeleccionado(int idProcesoSeleccionado) {
		this.idProcesoSeleccionado = idProcesoSeleccionado;
	}

	public List<RoRiesgoClave> getCalifObjetivosTodos() {
		return califObjetivosTodos;
	}

	public void setCalifObjetivosTodos(List<RoRiesgoClave> califObjetivosTodos) {
		this.califObjetivosTodos = califObjetivosTodos;
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
