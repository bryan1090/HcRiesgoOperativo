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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.SisTiempoSesion;
import com.hc.ro.negocio.ServicioSisTiempoSesion;

@ManagedBean
@ViewScoped
@XmlRootElement(name="controladorSisTiempoSesion")
public class ControladorSisTiempoSesion {
	
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;	
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/TiempoDeSesion.jsf";
	
	@EJB
	ServicioSisTiempoSesion servicioSisTiempoSesion;
	
	private List<SisTiempoSesion> listaSisTiempoSesion;
	private SisTiempoSesion sisTiempoSesion;
	private SisTiempoSesion sisTiempoSesionTipoControlador;
	private SisTiempoSesion sisTiempoSesionActual;
	@XmlElement(name="tiempoSes")
	private int tiempoSes;
	private boolean tipoGuardar;
	private boolean pnlTiempoSesion;
	private boolean pnlSesion;
	
	
	public ControladorSisTiempoSesion() {
		super();
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
		sisTiempoSesion = new SisTiempoSesion();
		sisTiempoSesionTipoControlador = new SisTiempoSesion();	
		sisTiempoSesionActual = new SisTiempoSesion();
		listaSisTiempoSesion = new ArrayList<SisTiempoSesion>();		
	}

	@PostConstruct
	public void PostControladorSisTiempoSesion(){
//		try {
//			controladorMenuPrincipal.controlarAcceso();
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}		
//		try {
//			dataManagerSesion.controlarAcceso(nombrePagina);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		listaSisTiempoSesion = servicioSisTiempoSesion.buscarTodos();
		pnlTiempoSesion = false;
		pnlSesion = false;
	}
	
	
	public void modificarTiempo(){		
		
		sisTiempoSesionTipoControlador.setTiempoSesion(sisTiempoSesion.getTiempoSesion());		
		pnlTiempoSesion = true;	
		
	}
	
	public void exitoGuardar(){
		pnlTiempoSesion = false;	
	}
	
	public void guardarTiempoDeSesion(){
		if (tipoGuardar) {
			if (servicioSisTiempoSesion.existeTiempoSesion(sisTiempoSesion.getTiempoSesion())) {
				servicioSisTiempoSesion.insertar(sisTiempoSesion);
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Tiempo de Sesion Añadido",
						"El Tiempo de Sesion ha sido Añadido con Exito"));
				//exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Tiempo de Sesion no se debe repetir"));
			}
		} else {

			if (servicioSisTiempoSesion.existeTiempoSesionEx(
					sisTiempoSesion.getTiempoSesion(), sisTiempoSesion.getCodigoSesion())) {

				servicioSisTiempoSesion.actualizar(sisTiempoSesion);

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Tiempo de Sesion Actualizado",
						"El Tiempo de Sesion ha sido Actualizado con Exito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Tiempo de Sesion no se debe repetir"));
				sisTiempoSesion.setTiempoSesion(sisTiempoSesionTipoControlador.getTiempoSesion());
			}
		}
	}	
	
	public List<SisTiempoSesion> getListaSisTiempoSesion() {
		return listaSisTiempoSesion;
	}
	public void setListaSisTiempoSesion(List<SisTiempoSesion> listaSisTiempoSesion) {
		this.listaSisTiempoSesion = listaSisTiempoSesion;
	}
	public SisTiempoSesion getSisTiempoSesion() {
		return sisTiempoSesion;
	}
	public void setSisTiempoSesion(SisTiempoSesion sisTiempoSesion) {
		this.sisTiempoSesion = sisTiempoSesion;
	}

	public SisTiempoSesion getSisTiempoSesionTipoControlador() {
		return sisTiempoSesionTipoControlador;
	}

	public void setSisTiempoSesionTipoControlador(
			SisTiempoSesion sisTiempoSesionTipoControlador) {
		this.sisTiempoSesionTipoControlador = sisTiempoSesionTipoControlador;
	}

	public boolean isPnlTiempoSesion() {
		return pnlTiempoSesion;
	}

	public void setPnlTiempoSesion(boolean pnlTiempoSesion) {
		this.pnlTiempoSesion = pnlTiempoSesion;
	}

	public SisTiempoSesion getSisTiempoSesionActual() {
		return sisTiempoSesionActual;
	}

	public void setSisTiempoSesionActual(SisTiempoSesion sisTiempoSesionActual) {
		this.sisTiempoSesionActual = sisTiempoSesionActual;
	}

	public boolean isPnlSesion() {
		return pnlSesion;
	}

	public void setPnlSesion(boolean pnlSesion) {
		this.pnlSesion = pnlSesion;
	}

	public int getTiempoSes() {
		return tiempoSes;
	}

	public void setTiempoSes(int tiempoSes) {
		this.tiempoSes = tiempoSes;
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
