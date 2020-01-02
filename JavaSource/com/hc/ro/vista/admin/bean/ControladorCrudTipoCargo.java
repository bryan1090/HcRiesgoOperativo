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

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoTipoCargo;
import com.hc.ro.negocio.ServicioRoTipoCargo;

@ManagedBean
@ViewScoped
public class ControladorCrudTipoCargo {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	
	public final static String nombrePagina = "/paginas/CrudTipoCargo.jsf";
	@EJB
	ServicioRoTipoCargo servicioRoTipoCargo;
	private RoTipoCargo roTipoCargoControlador;
	private RoTipoCargo roTipoCargoVista;
	private List<RoTipoCargo> roTipoCargoLista;
	public boolean tipoGuardar;
	private boolean pnlRoTipoCargo;
	private boolean btnAñadir;
	private boolean btnGuardar;
	private boolean btnCancelar;
	
	public ControladorCrudTipoCargo(){
		super();
		roTipoCargoVista = new RoTipoCargo();
		roTipoCargoControlador = new RoTipoCargo();
		roTipoCargoLista = new ArrayList<RoTipoCargo>();
	}
	
	@PostConstruct
	public void postConstructControladorCrudTipoCargo(){
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
		roTipoCargoLista = servicioRoTipoCargo.buscarTodos();
		
		pnlRoTipoCargo = false;
		btnAñadir = false;
		btnGuardar = true;
		btnCancelar = true;
	}
	
	public void nuevoRoTipoCargo(){
		roTipoCargoVista = new RoTipoCargo();
		pnlRoTipoCargo = true;
		tipoGuardar = true;
		btnAñadir = true;
	}
	
	public void exitoGuardar(){
		roTipoCargoVista = new RoTipoCargo();		
		roTipoCargoLista = servicioRoTipoCargo.buscarTodos();
		pnlRoTipoCargo = false;
		tipoGuardar = false;
		btnAñadir = false;
		btnGuardar = true;
		btnCancelar = true;
	}
	
	public void guardarRoTipoCargo(){
		if (tipoGuardar) {
			if (servicioRoTipoCargo.existeRoTipoCargoPorNombre((roTipoCargoVista.getNombreCargo()))) {
				servicioRoTipoCargo.insertar(roTipoCargoVista);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Tipo de Cargo Añadido",
						"El Cargo ha sido Añadido con Exito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Cargo no se debe repetir"));
			}
		}else{
			
			if (servicioRoTipoCargo.existeRoTipoCargoPorCodigoEx(
					roTipoCargoVista.getNombreCargo(),roTipoCargoVista.getCodigoCargo())) {

				servicioRoTipoCargo.actualizar(roTipoCargoVista);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tipo de Cargo Actualizado",
						"El Tipo Cargo ha sido Actualizado con Exito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Cargo no se debe repetir"));
				roTipoCargoVista.setNombreCargo((roTipoCargoControlador.getNombreCargo()));
				
			}
		}
	}
	
	public void nuevoRoTipoCargoVista(){
		roTipoCargoVista = new RoTipoCargo();
		pnlRoTipoCargo = true;
		btnAñadir = false;
		btnGuardar = false;
		btnCancelar = false;
	}
	
	public void cancelar(){
		nuevoRoTipoCargoVista();
		pnlRoTipoCargo = false;	
		btnGuardar = true;
		roTipoCargoLista = servicioRoTipoCargo.buscarTodos();
		tipoGuardar = false;
		btnAñadir = false;
		btnCancelar = true;
		//RequestContext.getCurrentInstance().update("formTipoCliente");
	}
	
	public void borrarCargo(){
		try {
			servicioRoTipoCargo.eliminar(roTipoCargoVista);
			roTipoCargoVista = new RoTipoCargo();
			roTipoCargoLista = servicioRoTipoCargo.buscarTodos();
			tipoGuardar = true;
			btnGuardar = true;
			btnCancelar = true;
			pnlRoTipoCargo = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo Cargo Eliminado",
					"El cargo ha sido Eliminado con Exito"));
		}catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"El Cargo que desea eliminar es de la lista de Usuario, no se puede eliminar un Cargo-Usuario ya que tiene asociados a el varios registros"));
		}
	}
	
	public void editarRoTipoCargo(){
		roTipoCargoControlador.setNombreCargo(roTipoCargoVista.getNombreCargo());
		pnlRoTipoCargo = true;
		btnAñadir = true;
		btnGuardar = false;
		btnCancelar = false;
	}
	
	// GETTERS Y SETTERS
	
	public RoTipoCargo getRoTipoCargoControlador() {
		return roTipoCargoControlador;
	}

	public void setRoTipoCargoControlador(RoTipoCargo roTipoCargoControlador) {
		this.roTipoCargoControlador = roTipoCargoControlador;
	}

	public RoTipoCargo getRoTipoCargoVista() {
		return roTipoCargoVista;
	}

	public void setRoTipoCargoVista(RoTipoCargo roTipoCargoVista) {
		this.roTipoCargoVista = roTipoCargoVista;
	}

	public List<RoTipoCargo> getRoTipoCargoLista() {
		return roTipoCargoLista;
	}

	public void setRoTipoCargoLista(List<RoTipoCargo> roTipoCargoLista) {
		this.roTipoCargoLista = roTipoCargoLista;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public boolean isBtnAñadir() {
		return btnAñadir;
	}

	public void setBtnAñadir(boolean btnAñadir) {
		this.btnAñadir = btnAñadir;
	}

	public boolean isPnlRoTipoCargo() {
		return pnlRoTipoCargo;
	}

	public void setPnlRoTipoCargo(boolean pnlRoTipoCargo) {
		this.pnlRoTipoCargo = pnlRoTipoCargo;
	}

	public boolean isBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(boolean btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public boolean isBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(boolean btnCancelar) {
		this.btnCancelar = btnCancelar;
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
