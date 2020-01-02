package com.hc.ro.vista.admin.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;
import javax.validation.constraints.Size;

import org.primefaces.context.RequestContext;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoAccion;
import com.hc.ro.modelo.RoDetalleAccion;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioRoAccion;
import com.hc.ro.negocio.ServicioRoDetalleAccion;
import com.hc.ro.negocio.ServicioSisUsuario;
import com.hc.ro.utils.Password;

@ManagedBean
@ViewScoped
public class ControladorBienvenida {

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@Size(min = 5, message = "El campo Contraseña debe contener por lo menos 5 caracteres")
	private String contrasenia;
	private String contraseniaAnterior;
	private String contrasenia2;
	private Password password;
	private SisUsuario sisUsuario;
	private RoDetalleEvento roDetalleEventoVista;
	public final static String nombrePagina = "/paginas/Bienvenido.jsf";
	private Date ahora;
	private SimpleDateFormat formateador;
	private boolean pnlEventos;
	private List<RoAccion> listaEventos;
	private List<RoDetalleAccion> listaRoDetalleAccion;
	
	private RoAccion roAccionVista;
	@EJB
	ServicioSisUsuario servicioCrearUsuario;
	@EJB
	ServicioRoAccion servicioRoAccion;
	@EJB
	ServicioRoDetalleAccion servicioRoDetalleAccion;

	/**
	 * Contructor vacio
	 */

	public ControladorBienvenida() {
		// Instancio el método password
		pnlEventos = false;
		password = new Password();
		this.roDetalleEventoVista = new RoDetalleEvento();
		ahora = new Date();
		formateador = new SimpleDateFormat("dd/MM/yyyy");
		listaEventos = new ArrayList<RoAccion>();
		listaRoDetalleAccion = new ArrayList<RoDetalleAccion>();
		roAccionVista = new RoAccion();
	}

	/**
	 * 
	 */
	@PostConstruct
	public void ejecutar() {		
		try {
			// Controla que un usuario esté logeado y no esté caducado la sesion
			controladorMenuPrincipal.controlarAcceso();

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			// Toma el nombre de usuario a la vista, a la sesion
			sisUsuario = dataManagerSesion.getUsuarioSesion();

		} catch (Exception e) {
			// TODO: handle exception
		}
		// System.out.println("El código de usuario es nuevo: " +
		// this.dataManagerSesion.getUsuarioSesion().getCodigoUsua());
		try {
			// Número total de acciones por eventos por usuario
			if (numeroTotalRegistros() > 0 || numeroTotalRegistrosRoDetalle() > 0){
				pnlEventos = true;
			}else{
				pnlEventos = false;
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listaEventos = servicioRoAccion.totalRegistrosPorResponsable(this.dataManagerSesion.getUsuarioSesion().getCodigoUsua(), ahora);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			//listaEventos = servicioRoAccion.totalRegistrosPorResponsable(this.dataManagerSesion.getUsuarioSesion().getCodigoUsua(), ahora);
			listaRoDetalleAccion = servicioRoDetalleAccion.listaTotalRegistrosRoDetalle(this.dataManagerSesion.getUsuarioSesion().getCodigoUsua(), ahora);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	// Numero de registros por usuario

	public long numeroTotalRegistros() {
		long totalRegistros = this.servicioRoAccion.numeroTotalRegistros(
				this.dataManagerSesion.getUsuarioSesion().getCodigoUsua(),
				ahora);		
		System.out.println("El número total de planes de acción es: "
				+ this.servicioRoAccion.numeroTotalRegistros(
						this.dataManagerSesion.getUsuarioSesion()
								.getCodigoUsua(), ahora));
		return totalRegistros;
	}
	
	//Número de registro por RoDetalleAccion
	
	public long numeroTotalRegistrosRoDetalle() {		
		long totalRegistros = this.servicioRoDetalleAccion.numeroTotalRegistrosRoDetalle
				(this.dataManagerSesion.getUsuarioSesion().getCodigoUsua(),
						ahora);
		return totalRegistros;
	}

	/**
	 * Limpa los campos y encero la variables
	 */
	public void abrirCambiarContrasenia() {
		contrasenia = "";
		contrasenia2 = "";
		contraseniaAnterior = "";
	}

	/**
	 * Primero: Encripta la contraseña anterior que va a escribir el usuario, y
	 * verifica si la contraseña de la sesion encriptada es igual. Segundo:
	 * Verifica si la contraseña ingresa es igual a la contraseña que se vuelve
	 * a ingresar. Entonces le setea o cambia la contraseña con la contraseña
	 * encriptada.
	 */
	public void cambiarContrasenia() {
		try {
			if (password.encriptar(contraseniaAnterior).equals(
					dataManagerSesion.getUsuarioSesion().getContraseniaUsua())) {
				if (contrasenia.equals(contrasenia2)) {
					dataManagerSesion.getUsuarioSesion().setContraseniaUsua(
							password.encriptar(contrasenia));
					// Actualizo en la base de datos
					servicioCrearUsuario.actualizar(dataManagerSesion
							.getUsuarioSesion());
					FacesMessage mensaje = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Usuario Actualizado - La contraseña se ha cambiado exitosamente",
							"");
					FacesContext.getCurrentInstance().addMessage(null, mensaje);
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("PF('dlgCambiarContrasena').hide();");
					abrirCambiarContrasenia();
				} else {
					FacesMessage mensaje = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al ingresar - Las contraseñas no coinciden",
							"");
					FacesContext.getCurrentInstance().addMessage(null, mensaje);
				}
			} else {
				FacesMessage mensaje = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Error al ingresar - Contraseña actual incorrecta", "");
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
			}
		} catch (Exception e) {
			FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error al ingresar - Intentelo Nuevamente", "");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgCambiarContrasena').hide();");
			abrirCambiarContrasenia();
		}
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getContraseniaAnterior() {
		return contraseniaAnterior;
	}

	public void setContraseniaAnterior(String contraseniaAnterior) {
		this.contraseniaAnterior = contraseniaAnterior;
	}

	public String getContrasenia2() {
		return contrasenia2;
	}

	public void setContrasenia2(String contrasenia2) {
		this.contrasenia2 = contrasenia2;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}

	public SisUsuario getSisUsuario() {
		return sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

	public RoDetalleEvento getRoDetalleEventoVista() {
		return roDetalleEventoVista;
	}

	public void setRoDetalleEventoVista(RoDetalleEvento roDetalleEventoVista) {
		this.roDetalleEventoVista = roDetalleEventoVista;
	}

	public Date getAhora() {
		return ahora;
	}

	public void setAhora(Date ahora) {
		this.ahora = ahora;
	}

	public SimpleDateFormat getFormateador() {
		return formateador;
	}

	public void setFormateador(SimpleDateFormat formateador) {
		this.formateador = formateador;
	}

	public boolean isPnlEventos() {
		return pnlEventos;
	}

	public void setPnlEventos(boolean pnlEventos) {
		this.pnlEventos = pnlEventos;
	}

	public List<RoAccion> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(List<RoAccion> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public RoAccion getRoAccionVista() {
		return roAccionVista;
	}

	public void setRoAccionVista(RoAccion roAccionVista) {
		this.roAccionVista = roAccionVista;
	}

	public List<RoDetalleAccion> getListaRoDetalleAccion() {
		return listaRoDetalleAccion;
	}

	public void setListaRoDetalleAccion(List<RoDetalleAccion> listaRoDetalleAccion) {
		this.listaRoDetalleAccion = listaRoDetalleAccion;
	}
}
