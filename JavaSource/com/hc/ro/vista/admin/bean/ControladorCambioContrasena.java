package com.hc.ro.vista.admin.bean;

//import java.sql.Date;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioSisUsuario;
import com.hc.ro.utils.Password;

@ManagedBean
@ViewScoped
public class ControladorCambioContrasena {

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	@EJB
	ServicioSisUsuario servicioSisUsuarios;

	// private SisUsuario usuario;
	String nuevaContrasena;
	String contrasenaRepetida;
	private Password password;

	// CONSTRUCTOR
	public ControladorCambioContrasena() {
		super();
		// usuario = new SisUsuario();
		password = new Password();

	}

	@PostConstruct
	public void postC() {
	}

	// METODOS

	public void guardarContrasena() {

		try {
			if (contrasenaRepetida.equals(nuevaContrasena)) {
				SisUsuario usuario = new SisUsuario();
				usuario = dataManagerSesion.getUsuarioSesion();
				usuario.setContraseniaUsua(password.encriptar(nuevaContrasena));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MONTH, 1);
				usuario.setFechaCaducaUsua(calendar.getTime());
				servicioSisUsuarios.actualizar(usuario);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/hcRiesgoOperativo/paginas/Login.jsf");
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Contraseñas no Coinciden",
						"Las Contraseñas no coinciden"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * GETTERS Y SETTERS
	 */

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public String getNuevaContrasena() {
		return nuevaContrasena;
	}

	public void setNuevaContrasena(String nuevaContrasena) {
		this.nuevaContrasena = nuevaContrasena;
	}

	public String getContrasenaRepetida() {
		return contrasenaRepetida;
	}

	public void setContrasenaRepetida(String contrasenaRepetida) {
		this.contrasenaRepetida = contrasenaRepetida;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

}
