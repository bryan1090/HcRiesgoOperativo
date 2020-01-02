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
import com.hc.ro.modelo.RoControl;
import com.hc.ro.modelo.RoControlParamImpRep;
import com.hc.ro.modelo.RoControlValor;
import com.hc.ro.negocio.ServicioRoControl;
import com.hc.ro.negocio.ServicioRoControlParamImpRep;
import com.hc.ro.negocio.ServicioRoControlValor;

@ManagedBean
@ViewScoped
public class ControladorCrudRoControl {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudControles.jsf";
	// Control
	@EJB
	ServicioRoControl servicioRoControl;
	@EJB
	ServicioRoControlValor servicioRoControlValor;

	// PARAM IMPACTO REPUTACION
	@EJB
	ServicioRoControlParamImpRep servicioRoControlParamImpRep;

	// VARIABLES
	private RoControlValor roControlValorVista;
	private RoControl roControlVista;
	private RoControl roControlControlador;
	private List<RoControlValor> roControlValorsTodos;
	private List<RoControl> tipoAfectasTodos;
	private boolean tipoGuardar;
	private boolean tipoGuardarValor;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlControl;
	private boolean btnAnadirControlValor;
	private boolean pnlEditarControlValor;
	private boolean mostrarPnlControlValor;
	//

	// PARAM IMPACTO REPUTACION
	private boolean pnlParamImpRep;
	private RoControlParamImpRep roControlParamImpRepVista;
	private boolean nuevoParamImpRep;
	private List<RoControlParamImpRep> paramImpRepTodos;

	public ControladorCrudRoControl() {
		super();
		roControlValorsTodos = new ArrayList<RoControlValor>();
		roControlValorVista = new RoControlValor();
		roControlControlador = new RoControl();
		roControlVista = new RoControl();
		tipoAfectasTodos = new ArrayList<RoControl>();
		nombreSeleccionado = new String();

		// PARAM IMPACTO REPUTACION
		roControlParamImpRepVista = new RoControlParamImpRep();
		paramImpRepTodos = new ArrayList<RoControlParamImpRep>();
	}

	@PostConstruct
	public void PostControladorCrudRoControl() {
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
		tipoAfectasTodos = servicioRoControl.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlControl = false;

		btnAnadirControlValor = true;
		pnlEditarControlValor = false;

		// PARAM IMPACTO REPUTACION
		pnlParamImpRep = false;
		nuevoParamImpRep = false;
		paramImpRepTodos = servicioRoControlParamImpRep.buscarTodos();
	}

	public void guardarControl() {
		if (tipoGuardar) {
			if (!(servicioRoControl.existeControlPorNombre(roControlVista
					.getNombreControl()))) {

				try {
					servicioRoControl.insertar(roControlVista);

					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(
							"Control Añadido",
							"El registro ha sido Añadido con éxito"));
					exitoGuardar();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre del Atributo no se debe repetir para la misma clase"));
			}

		} else {
			if (!(servicioRoControl.existeControlPorNombreEx(
					roControlVista.getNombreControl(),
					roControlVista.getCodigoCtrl()))) {

				try {
					servicioRoControl.actualizar(roControlVista);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(
							"Control Actualizado",
							"El registro ha sido Actualizado con éxito"));
					exitoGuardar();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al actualizar:",
									"Hubo un problema al actualizar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				}

			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre del Atributo no se debe repetir para la misma clase"));
			}

		}

	}

	public void exitoGuardar() {
		roControlVista = new RoControl();
		tipoAfectasTodos = servicioRoControl.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlControl = false;
		btnAnadirControlValor = true;
		pnlEditarControlValor = false;
		
		
	}

	public void seleccionar() {
		roControlVista = roControlControlador;
		roControlValorsTodos = servicioRoControlValor
				.buscarEventoPorControl(roControlVista);

		tipoGuardar = false;
		mostrarPnlControlValor = true;
		// botones deshabilitados????
		pnlControl = false;
		btnAnadir = false;
		btnAnadirControlValor = false;
	}

	public void eliminarControl() {
		try {
			servicioRoControl.eliminar(roControlVista);
			roControlVista = new RoControl();
			tipoAfectasTodos = servicioRoControl.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlControl = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Control Eliminado",
					"El registro ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Control en uso"));
		}
	}

	public void nuevoControlVista() {
		cancelarControlValor();
		tipoGuardar = true;
		roControlVista = new RoControl();
		roControlControlador = new RoControl();
		btnGuardar = false;
		btnCancelar = false;
		pnlControl = true;
		pnlEditarControlValor = false;
		mostrarPnlControlValor = false;

	}

	public void cancelar() {
		nuevoControlVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlControl = false;
		btnAnadirControlValor = true;
		pnlEditarControlValor = false;
//		RequestContext.getCurrentInstance().update("formControl");

	}

	public void editarControlVista() {
		tipoGuardar = false;
		btnGuardar = false;
		btnCancelar = false;
		pnlControl = true;
		btnAnadir = true;
		btnAnadirControlValor = false;
		pnlEditarControlValor = true;
		mostrarPnlControlValor = false;
		roControlValorsTodos = servicioRoControlValor
				.buscarEventoPorControl(roControlVista);
	}

	public void editarControlValor() {
		tipoGuardarValor = false;
		pnlControl = true;
		btnAnadir = true;
		pnlEditarControlValor = true;

	}

	public void seleccionarControlVista() {
		btnAnadirControlValor = false;
		pnlEditarControlValor = true;
		roControlValorsTodos = servicioRoControlValor
				.buscarEventoPorControl(roControlVista);
	}

	public void nuevoControlValor() {
		tipoGuardarValor = false;
		roControlValorVista = new RoControlValor();
		btnAnadirControlValor = false;
		pnlEditarControlValor = true;
	}

	public void guardarControlValor() {
		roControlValorVista.setRoControl(roControlVista);
		if (tipoGuardarValor) {
			if (!servicioRoControlValor.existeControlValorPorNombreControl(
					roControlValorVista.getTipoCtva(), roControlVista)) {
				try {
					servicioRoControlValor.insertar(roControlValorVista);
					cancelarControlValor();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				} finally {
					roControlValorsTodos = servicioRoControlValor
							.buscarEventoPorControl(roControlVista);
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Nombre del Control no se puede repetir"));
				roControlValorsTodos = servicioRoControlValor
						.buscarEventoPorControl(roControlVista);
			}

		} else {
			if (!servicioRoControlValor.existeControlValorPorNombreControlEx(
					roControlValorVista.getTipoCtva(),
					roControlValorVista.getCodigoCtva(), roControlVista)) {
				try {
					servicioRoControlValor.actualizar(roControlValorVista);
					cancelarControlValor();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al actualizar:",
									"Hubo un problema al actualizar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				} finally {
					roControlValorsTodos = servicioRoControlValor
							.buscarEventoPorControl(roControlVista);
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Nombre del Control no se puede repetir"));
				roControlValorsTodos = servicioRoControlValor
						.buscarEventoPorControl(roControlVista);
			}
		}
	}

	public void cancelarControlValor() {
		roControlValorVista = new RoControlValor();
		btnAnadirControlValor = false;
		pnlEditarControlValor = false;
	}

	public void eliminarControlValor() {
		try {
			servicioRoControlValor.eliminar(roControlValorVista);
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al guardar:",
					"Hubo un problema al guardar, registro en uso"));

		} finally {
			roControlValorsTodos = servicioRoControlValor
					.buscarEventoPorControl(roControlVista);
		}
	}

	// PARAM IMPACTO REPUTACION

	public void nuevoParamImpRepVista() {
		nuevoParamImpRep=true;
		pnlParamImpRep = true;
		roControlParamImpRepVista = new RoControlParamImpRep();
	}
	
	public void editarParamImpactoReputacion() {
		pnlParamImpRep=true;
		
		
	}

	public void eliminarParamImpactoReputacion() {
		
		try {
			servicioRoControlParamImpRep.eliminar(roControlParamImpRepVista);	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		paramImpRepTodos=servicioRoControlParamImpRep.buscarTodos();
		
	}
	
	public void guardarParamImpactoReputacion() {

		if (nuevoParamImpRep) {
			try {
				servicioRoControlParamImpRep
						.insertar(roControlParamImpRepVista);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		else {
			try {
				servicioRoControlParamImpRep.actualizar(roControlParamImpRepVista);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		
		paramImpRepTodos=servicioRoControlParamImpRep.buscarTodos();
		pnlParamImpRep=false;

	}



	public void cancelarParamImpactoReputacion() {
		pnlParamImpRep=false;
		nuevoParamImpRep = false;
		roControlParamImpRepVista = new RoControlParamImpRep();
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

	public RoControl getRoControlVista() {
		return roControlVista;
	}

	public void setRoControlVista(RoControl roControlVista) {
		this.roControlVista = roControlVista;
	}

	public RoControl getRoControlControlador() {
		return roControlControlador;
	}

	public void setRoControlControlador(RoControl roControlControlador) {
		this.roControlControlador = roControlControlador;
	}

	public List<RoControl> getControlsTodos() {
		return tipoAfectasTodos;
	}

	public void setControlsTodos(List<RoControl> ControlsTodos) {
		this.tipoAfectasTodos = ControlsTodos;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlControl() {
		return pnlControl;
	}

	public void setPnlControl(boolean pnlControl) {
		this.pnlControl = pnlControl;
	}

	public List<RoControl> getTipoAfectasTodos() {
		return tipoAfectasTodos;
	}

	public void setTipoAfectasTodos(List<RoControl> tipoAfectasTodos) {
		this.tipoAfectasTodos = tipoAfectasTodos;
	}

	public List<RoControlValor> getRoControlValorsTodos() {
		return roControlValorsTodos;
	}

	public void setRoControlValorsTodos(
			List<RoControlValor> roControlValorsTodos) {
		this.roControlValorsTodos = roControlValorsTodos;
	}

	public RoControlValor getRoControlValorVista() {
		return roControlValorVista;
	}

	public void setRoControlValorVista(RoControlValor roControlValorVista) {
		this.roControlValorVista = roControlValorVista;
	}

	public boolean isBtnAnadirControlValor() {
		return btnAnadirControlValor;
	}

	public void setBtnAnadirControlValor(boolean btnAnadirControlValor) {
		this.btnAnadirControlValor = btnAnadirControlValor;
	}

	public boolean isPnlEditarControlValor() {
		return pnlEditarControlValor;
	}

	public void setPnlEditarControlValor(boolean pnlEditarControlValor) {
		this.pnlEditarControlValor = pnlEditarControlValor;
	}

	public boolean isMostrarPnlControlValor() {
		return mostrarPnlControlValor;
	}

	public void setMostrarPnlControlValor(boolean mostrarPnlControlValor) {
		this.mostrarPnlControlValor = mostrarPnlControlValor;
	}

	public boolean isTipoGuardarValor() {
		return tipoGuardarValor;
	}

	public void setTipoGuardarValor(boolean tipoGuardarValor) {
		this.tipoGuardarValor = tipoGuardarValor;
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

	public boolean isPnlParamImpRep() {
		return pnlParamImpRep;
	}

	public void setPnlParamImpRep(boolean pnlParamImpRep) {
		this.pnlParamImpRep = pnlParamImpRep;
	}

	public RoControlParamImpRep getRoControlParamImpRepVista() {
		return roControlParamImpRepVista;
	}

	public void setRoControlParamImpRepVista(
			RoControlParamImpRep roControlParamImpRepVista) {
		this.roControlParamImpRepVista = roControlParamImpRepVista;
	}

	public List<RoControlParamImpRep> getParamImpRepTodos() {
		return paramImpRepTodos;
	}

	public void setParamImpRepTodos(List<RoControlParamImpRep> paramImpRepTodos) {
		this.paramImpRepTodos = paramImpRepTodos;
	}

	public boolean isNuevoParamImpRep() {
		return nuevoParamImpRep;
	}

	public void setNuevoParamImpRep(boolean nuevoParamImpRep) {
		this.nuevoParamImpRep = nuevoParamImpRep;
	}

}
