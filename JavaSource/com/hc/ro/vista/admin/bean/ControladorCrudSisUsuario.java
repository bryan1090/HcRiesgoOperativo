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
import com.hc.ro.modelo.RoTipoCargo;
import com.hc.ro.modelo.SisPerfil;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioRoTipoCargo;
import com.hc.ro.negocio.ServicioSisPerfil;
import com.hc.ro.negocio.ServicioSisUsuario;
import com.hc.ro.utils.Password;

@ManagedBean
@ViewScoped
public class ControladorCrudSisUsuario {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudUsuario.jsf";
	// SisUsuario
	@EJB
	ServicioSisUsuario servicioSisUsuario;
	@EJB
	ServicioSisPerfil servicioSisPerfil;
	@EJB
	ServicioRoTipoCargo servicioRoTipoCargo;

	// VARIABLES
	private SisUsuario sisUsuarioVista;
	private SisUsuario sisUsuarioControlador;
	private List<SisUsuario> sisUsuariosTodos;
	private List<SisPerfil> sisPerfilessTodos;
	// Lista de Cargos
	private List<RoTipoCargo> roTipoCargoTodos;
	private int idSisUsuarioSeleccionado;
	private int idPerfilSeleccionado;
	// Cargo
	private int idCargoSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	private String contraseniaRepetir;
	// botones,cajas,dialogos
	private boolean btnAuditable;
	private boolean btnBloqueado;
	private boolean btnSuper;
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnCambiarContrasenia;
	private boolean btnGuardar;
	private boolean pnlSisUsuario;
	private boolean setContraseniaUsuario;
	private Password password;
	private double desbloquear;

	private Boolean btnClaveReseteada;

	// private String claveReseteada;
	//
	public ControladorCrudSisUsuario() {
		super();
		sisUsuarioControlador = new SisUsuario();
		sisUsuarioVista = new SisUsuario();
		sisUsuariosTodos = new ArrayList<SisUsuario>();
		sisPerfilessTodos = new ArrayList<SisPerfil>();
		roTipoCargoTodos = new ArrayList<RoTipoCargo>();
		nombreSeleccionado = new String();
		password = new Password();
	}

	@PostConstruct
	public void PostControladorCrudSisUsuario() {
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
		sisUsuariosTodos = servicioSisUsuario.buscarTodos();
		sisPerfilessTodos = servicioSisPerfil.buscarTodos();
		roTipoCargoTodos = servicioRoTipoCargo.buscarTodos();
		btnAnadir = false;
		btnCambiarContrasenia = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlSisUsuario = false;
		btnClaveReseteada = false;

	}

	// METODOS

	public void guardarSisUsuario() {
		System.out.println("Ingresó a guardar Usuario1");
		sisUsuarioVista.setSisPerfil(servicioSisPerfil
				.buscarPorId(idPerfilSeleccionado));
		sisUsuarioVista.setRoTipoCargo(servicioRoTipoCargo
				.buscarPorId(idCargoSeleccionado));
		if (btnSuper) {
			sisUsuarioVista.setPermisoSuDeve(1.0);
		} else {
			sisUsuarioVista.setPermisoSuDeve(0.0);
		}
		// aqui iba el bloqueo del usuario
		if (btnAuditable) {
			sisUsuarioVista.setAuditableUsua(1.0);
		} else {
			sisUsuarioVista.setAuditableUsua(0.0);
		}

		if (contraseniaRepetir.equals(sisUsuarioVista.getContraseniaUsua())) {
			if (tipoGuardar) {
				if (servicioSisUsuario.existeUsuarioPorNombre(sisUsuarioVista
						.getNombreUsua())) {
					sisUsuarioVista.setContraseniaUsua(password
							.encriptar(sisUsuarioVista.getContraseniaUsua()));
					servicioSisUsuario.insertar(sisUsuarioVista);
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(null, new FacesMessage(
							"Usuario Añadido",
							"El Usuario ha sido Añadido con éxito"));
					exitoGuardar();
				} else {
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Error al guardar:",
							"El nombre de Usuario no se debe repetir"));
				}
			} else {

				if (servicioSisUsuario.existeUsuarioPorNombreEx(
						sisUsuarioVista.getNombreUsua(),
						sisUsuarioVista.getCodigoUsua())) {

					FacesContext context = FacesContext.getCurrentInstance();

					sisUsuarioVista.setContraseniaUsua(password
							.encriptar(sisUsuarioVista.getContraseniaUsua()));
					if (setContraseniaUsuario) {
						if(sisUsuarioVista.getCodigoUsua()!=dataManagerSesion.getUsuarioSesion().getCodigoUsua())
						{
							sisUsuarioVista.setClaveReseteada(1);
							context.addMessage(null, new FacesMessage(
									"Clave reseteada", ""));
						}
						
					}
					servicioSisUsuario.actualizar(sisUsuarioVista);

					context.addMessage(null, new FacesMessage(
							"Usuario Actualizado",
							"El Usuario ha sido Actualizado con éxito"));

					exitoGuardar();
				} else {
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Error al guardar:",
							"El nombre de Usuario no se debe repetir"));
					sisUsuarioVista.setNombreUsua(sisUsuarioControlador
							.getNombreUsua());
				}
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al guardar:",
					"Las contraseñas deben ser iguales"));
		}

		System.out.println("-contraseña" + setContraseniaUsuario);

	}

	public void exitoGuardar() {

		sisUsuarioVista = new SisUsuario();
		sisUsuariosTodos = servicioSisUsuario.buscarTodos();
		btnGuardar = true;
		btnCancelar = true;
		pnlSisUsuario = false;
		btnAnadir = false;
	}

	public void eliminarSisUsuario() {

		try {
			servicioSisUsuario.eliminar(sisUsuarioVista);
			sisUsuarioVista = new SisUsuario();
			sisUsuariosTodos = servicioSisUsuario.buscarTodos();
			// idEstadoSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlSisUsuario = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Usuario Eliminado",
					"El Usuario ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"El Usuario que desea eliminar es de la lista de Responsbles, no se puede eliminar un Usuario-Responsable ya que tiene asociados a el varios registros"));
		}
	}

	public void nuevoSisUsuarioVista() {
		setContraseniaUsuario = true;
		tipoGuardar = true;
		sisUsuarioVista = new SisUsuario();
		btnBloqueado = false;
		btnSuper = false;
		btnAuditable = true;
		idPerfilSeleccionado = sisPerfilessTodos.get(0).getCodigoPerf();
		idCargoSeleccionado = roTipoCargoTodos.get(0).getCodigoCargo();
		btnGuardar = false;
		btnCancelar = false;
		pnlSisUsuario = true;
		btnCambiarContrasenia = false;
	}

	public void cancelar() {
		nuevoSisUsuarioVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlSisUsuario = false;
		RequestContext.getCurrentInstance().update("formSisUsuario");
	}

	public void editarSisUsuario() {
		setContraseniaUsuario = false;
		System.out.println("contrasenia: "+password
				.desencriptar(sisUsuarioVista.getContraseniaUsua()));
		sisUsuarioVista.setContraseniaUsua(password
				.desencriptar(sisUsuarioVista.getContraseniaUsua()));
		contraseniaRepetir = sisUsuarioVista.getContraseniaUsua();
		sisUsuarioControlador.setNombreUsua(sisUsuarioVista.getNombreUsua());
		idPerfilSeleccionado = sisUsuarioVista.getSisPerfil().getCodigoPerf();
		idCargoSeleccionado = sisUsuarioVista.getRoTipoCargo().getCodigoCargo();
		tipoGuardar = false;
		if (sisUsuarioVista.getAuditableUsua() >= 1.0) {
			btnAuditable = true;
		} else {
			btnAuditable = false;
		}
		if (sisUsuarioVista.getBloqueadoUsua() <= 0.0) {
			btnBloqueado = true;
		} else {
			btnBloqueado = false;
		}
		if (sisUsuarioVista.getPermisoSuDeve() >= 1.0) {
			btnSuper = true;
		} else {
			btnSuper = false;
		}
		// idEstadoSeleccionado =
		// SisUsuarioVista.getGenEstado().getCodigoEsta();

		btnGuardar = false;
		btnCancelar = false;
		pnlSisUsuario = true;
		btnAnadir = true;
		btnCambiarContrasenia = true;

	}

	public void cambiarContrasenia() {
		btnCambiarContrasenia = false;
		setContraseniaUsuario = true;
	}

	public boolean transformarBool(String strng) {
		if (strng.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean transformarBool(int entero) {
		if (entero == 1) {
			return true;
		} else {
			return false;
		}
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

	public SisUsuario getSisUsuarioVista() {
		return sisUsuarioVista;
	}

	public void setSisUsuarioVista(SisUsuario sisUsuarioVista) {
		this.sisUsuarioVista = sisUsuarioVista;
	}

	public SisUsuario getSisUsuarioControlador() {
		return sisUsuarioControlador;
	}

	public void setSisUsuarioControlador(SisUsuario sisUsuarioControlador) {
		this.sisUsuarioControlador = sisUsuarioControlador;
	}

	public List<SisUsuario> getSisUsuariosTodos() {
		return sisUsuariosTodos;
	}

	public void setSisUsuariosTodos(List<SisUsuario> sisUsuariosTodos) {
		this.sisUsuariosTodos = sisUsuariosTodos;
	}

	public int getIdSisUsuarioSeleccionado() {
		return idSisUsuarioSeleccionado;
	}

	public void setIdSisUsuarioSeleccionado(int idSisUsuarioSeleccionado) {
		this.idSisUsuarioSeleccionado = idSisUsuarioSeleccionado;
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

	public boolean isPnlSisUsuario() {
		return pnlSisUsuario;
	}

	public void setPnlSisUsuario(boolean pnlSisUsuario) {
		this.pnlSisUsuario = pnlSisUsuario;
	}

	public int getIdPerfilSeleccionado() {
		return idPerfilSeleccionado;
	}

	public void setIdPerfilSeleccionado(int idPerfilSeleccionado) {
		this.idPerfilSeleccionado = idPerfilSeleccionado;
	}

	public String getContraseniaRepetir() {
		return contraseniaRepetir;
	}

	public void setContraseniaRepetir(String contraseniaRepetir) {
		this.contraseniaRepetir = contraseniaRepetir;
	}

	public boolean isBtnBloqueado() {
		return btnBloqueado;
	}

	public void setBtnBloqueado(boolean btnBloqueado) {
		this.btnBloqueado = btnBloqueado;
	}

	public boolean isBtnAuditable() {
		return btnAuditable;
	}

	public void setBtnAuditable(boolean btnAuditable) {
		this.btnAuditable = btnAuditable;
	}

	public List<SisPerfil> getSisPerfilessTodos() {
		return sisPerfilessTodos;
	}

	public void setSisPerfilessTodos(List<SisPerfil> sisPerfilessTodos) {
		this.sisPerfilessTodos = sisPerfilessTodos;
	}

	public boolean isSetContraseniaUsuario() {
		return setContraseniaUsuario;
	}

	public void setSetContraseniaUsuario(boolean setContraseniaUsuario) {
		this.setContraseniaUsuario = setContraseniaUsuario;
	}

	public boolean isBtnCambiarContrasenia() {
		return btnCambiarContrasenia;
	}

	public void setBtnCambiarContrasenia(boolean btnCambiarContrasenia) {
		this.btnCambiarContrasenia = btnCambiarContrasenia;
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

	public boolean getBtnSuper() {
		return btnSuper;
	}

	public void setBtnSuper(boolean btnSuper) {
		this.btnSuper = btnSuper;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public double getDesbloquear() {
		return desbloquear;
	}

	public void setDesbloquear(double desbloquear) {
		this.desbloquear = desbloquear;
	}

	public int getIdCargoSeleccionado() {
		return idCargoSeleccionado;
	}

	public void setIdCargoSeleccionado(int idCargoSeleccionado) {
		this.idCargoSeleccionado = idCargoSeleccionado;
	}

	public List<RoTipoCargo> getRoTipoCargoTodos() {
		return roTipoCargoTodos;
	}

	public void setRoTipoCargoTodos(List<RoTipoCargo> roTipoCargoTodos) {
		this.roTipoCargoTodos = roTipoCargoTodos;
	}

	public boolean isBtnClaveReseteada() {
		return btnClaveReseteada;
	}

	public void setBtnClaveReseteada(boolean btnClaveReseteada) {
		this.btnClaveReseteada = btnClaveReseteada;
	}
	//
	// public String getClaveReseteada() {
	// return claveReseteada;
	// }
	//
	// public void setClaveReseteada(String claveReseteada) {
	// this.claveReseteada = claveReseteada;
	// }

}
