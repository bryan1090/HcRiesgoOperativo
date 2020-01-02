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
import com.hc.ro.modelo.GenNivelProceso;
import com.hc.ro.negocio.ServicioGenNivelProceso;

@ManagedBean
@ViewScoped
public class ControladorCrudGenNivelProceso {
	// NivelProceso

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@EJB
	ServicioGenNivelProceso servicioGenNivelProceso;

	// VARIABLES
	public final static String nombrePagina = "/paginas/CrudNivelProceso.jsf";
	private GenNivelProceso roNivelProcesoVista;
	private GenNivelProceso roNivelProcesoControlador;
	private List<GenNivelProceso> nivelProcesosTodos;
	private List<GenEstado> estadosTodos;
	private int idNivelProcesoSeleccionado;
	private boolean nivelGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlNivelProceso;

	//
	public ControladorCrudGenNivelProceso() {
		super();
		roNivelProcesoControlador = new GenNivelProceso();
		roNivelProcesoVista = new GenNivelProceso();
		nivelProcesosTodos = new ArrayList<GenNivelProceso>();
		estadosTodos = new ArrayList<GenEstado>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudGenNivelProceso() {
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
		
		nivelProcesosTodos = servicioGenNivelProceso.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlNivelProceso = false;
	}

	// METODOS

	public void guardarNivelProceso() {
		if (nivelGuardar) {
			if (servicioGenNivelProceso
					.existeNivelProcesoPorNombre(roNivelProcesoVista
							.getNombreGnip())) {
				servicioGenNivelProceso.insertar(roNivelProcesoVista);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Nivel de Proceso Añadido",
						"El Nivel de Proceso ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Nivel de Proceso no se debe repetir"));
			}
		} else {

			if (servicioGenNivelProceso.existeNivelProcesoPorNombreEx(
					roNivelProcesoVista.getNombreGnip(),
					roNivelProcesoVista.getCodigoGnip())) {

				servicioGenNivelProceso.actualizar(roNivelProcesoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Nivel de Proceso Actualizado",
						"El NivelProceso ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Nivel de Proceso no se debe repetir"));
				roNivelProcesoVista.setNombreGnip(roNivelProcesoControlador
						.getNombreGnip());
			}
		}
	}

	public void exitoGuardar() {

		roNivelProcesoVista = new GenNivelProceso();
		nivelProcesosTodos = servicioGenNivelProceso.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlNivelProceso = false;
	}

	public void eliminarNivelProceso() {

		try {
			servicioGenNivelProceso.eliminar(roNivelProcesoVista);
			roNivelProcesoVista = new GenNivelProceso();
			nivelProcesosTodos = servicioGenNivelProceso.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlNivelProceso = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Nivel de Proceso Eliminado",
					"El Nivel de Proceso ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Nivel de Proceso en uso"));
		}
	}

	public void nuevoNivelProcesoVista() {
		nivelGuardar = true;
		roNivelProcesoVista = new GenNivelProceso();
		btnGuardar = false;
		btnCancelar = false;
		pnlNivelProceso = true;
	}

	public void cancelar() {
		nuevoNivelProcesoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlNivelProceso = false;
		RequestContext.getCurrentInstance().update("formNivelProceso");
	}

	public void editarNivelProcesoVista() {
		roNivelProcesoControlador.setNombreGnip(roNivelProcesoVista
				.getNombreGnip());
		nivelGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlNivelProceso = true;
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

	public GenNivelProceso getGenNivelProcesoVista() {
		return roNivelProcesoVista;
	}

	public void setGenNivelProcesoVista(GenNivelProceso roNivelProcesoVista) {
		this.roNivelProcesoVista = roNivelProcesoVista;
	}

	public GenNivelProceso getGenNivelProcesoControlador() {
		return roNivelProcesoControlador;
	}

	public void setGenNivelProcesoControlador(
			GenNivelProceso roNivelProcesoControlador) {
		this.roNivelProcesoControlador = roNivelProcesoControlador;
	}

	public List<GenNivelProceso> getNivelProcesosTodos() {
		return nivelProcesosTodos;
	}

	public void setNivelProcesosTodos(List<GenNivelProceso> NivelProcesosTodos) {
		this.nivelProcesosTodos = NivelProcesosTodos;
	}

	public int getIdNivelProcesoSeleccionado() {
		return idNivelProcesoSeleccionado;
	}

	public void setIdNivelProcesoSeleccionado(int idNivelProcesoSeleccionado) {
		this.idNivelProcesoSeleccionado = idNivelProcesoSeleccionado;
	}

	public boolean isNivelGuardar() {
		return nivelGuardar;
	}

	public void setNivelGuardar(boolean nivelGuardar) {
		this.nivelGuardar = nivelGuardar;
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

	public boolean isPnlNivelProceso() {
		return pnlNivelProceso;
	}

	public void setPnlNivelProceso(boolean pnlNivelProceso) {
		this.pnlNivelProceso = pnlNivelProceso;
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

	public GenNivelProceso getRoNivelProcesoVista() {
		return roNivelProcesoVista;
	}

	public void setRoNivelProcesoVista(GenNivelProceso roNivelProcesoVista) {
		this.roNivelProcesoVista = roNivelProcesoVista;
	}

	public GenNivelProceso getRoNivelProcesoControlador() {
		return roNivelProcesoControlador;
	}

	public void setRoNivelProcesoControlador(
			GenNivelProceso roNivelProcesoControlador) {
		this.roNivelProcesoControlador = roNivelProcesoControlador;
	}

}
