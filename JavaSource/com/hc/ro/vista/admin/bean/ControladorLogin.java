package com.hc.ro.vista.admin.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioSisTiempoSesion;
import com.hc.ro.negocio.ServicioSisUsuario;
import com.hc.ro.negocio.ServicioSisParametro;
import com.hc.ro.utils.Password;
import com.sun.jndi.ldap.LdapCtxFactory;

@ManagedBean
@ViewScoped
public class ControladorLogin {
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@EJB
	private ServicioSisUsuario servicioSisUsuarios;
	@EJB
	private ServicioSisParametro servicioSisParametro;
	@EJB
	private ServicioSisTiempoSesion servicioSisTiempoSesion;
	private SisUsuario usuarioSesion;
	private SisUsuario usuario;
	private List<SisUsuario> usuarios;
	private String nomUsuario;
	private Password password;
	private String tiempoSesion;

	// CONSTRUCTOR
	public ControladorLogin() {
		super();
		// dataManagerSesion = new DataManagerSesion();
		// controladorMenuPrincipal=new ControladorMenuPrincipal();
		usuarioSesion = new SisUsuario();
		usuario = new SisUsuario();
		usuarios = new ArrayList<SisUsuario>();
		password = new Password();
	}

	@PostConstruct
	public void postC() {

		
		active();
	}

	// METODOS
	
	public void active()
	{
		try
	    {
			
//			 Hashtable<String, String> props = new Hashtable<String, String>();
//	            String principalName = "bryan" + "@" + "MYDOMAIN";
//	            props.put(Context.SECURITY_PRINCIPAL, principalName);
//	            props.put(Context.SECURITY_CREDENTIALS, password.toString());
//	            DirContext context = null;
//	            context = LdapCtxFactory.getLdapCtxInstance("ldap://ad.mydomain:389", props);
//	            System.out.println("User login successful: " );
			
			
			
//			// Set up the environment for creating the initial context
//			Hashtable<String, Object> env = new Hashtable<String, Object>();
//			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//			env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");
//
//			// Authenticate as S. User and password "mysecret"
//			env.put(Context.SECURITY_AUTHENTICATION, "simple");
//			env.put(Context.SECURITY_PRINCIPAL, 
//			        "cn=S. Bryan, ou=NewHires, o=JNDITutorial");
//			env.put(Context.SECURITY_CREDENTIALS, "213546");
//
//			// Create the initial context
//			DirContext ctx = new InitialDirContext(env);

	    }
	    catch (Exception e)
	    {
	      System.out.println(" Search error: " + e);
	      e.printStackTrace();
	      System.exit(-1);
	    }
	  }
	
	
	/**
	 * El Metodo realiza el logeo de un usuario con los permisos necesarios al
	 * sistema, actualizando los datos y manteniendo en vista los datos
	 * 
	 */
	public void logearUsuario() {
		try {
			// Buscamos el nombre de usuario en la base de datos
			usuario = servicioSisUsuarios.buscarUsuarioPorNombre(nomUsuario.toLowerCase());
			if (usuario.getBloqueadoUsua() == null) {
				usuario.setBloqueadoUsua(usuario.getBloqueadoUsua());
				servicioSisUsuarios.actualizar(usuario);
			}
			if (usuario.getBloqueadoUsua() <= 0.0) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(
						null,
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"Usuario Bloqueado",
								"Usted ha excedido el número de intentos permitidos. Comuniquese con el Administrador"));

			} else {
				// Creamos el usuario sesion, encriptamos la contraseña de la
				// vista y comparamos con el pass de la base
				if (usuario.getContraseniaUsua().equals(
						password.encriptar(usuarioSesion.getContraseniaUsua()))) {
					if (!contrasenaCaduca(usuario)) {// si la contraseña no
														// caducó
						if (usuario.getClaveReseteada() != 0) {
							// si la contraseña fue previamente
							// reseteada por
							// un usuario de
							// alto
							// privilegio(crudUsuario.jsf)

							// Hacemos que el usuario ponga una contraseña
							// propia
							FacesContext
									.getCurrentInstance()
									.getExternalContext()
									.redirect(
											"/hcRiesgoOperativo/paginas/CambioContrasena.jsf");
							usuario.setBloqueadoUsua(usuario.getBloqueadoUsua());
							usuario.setIpLogUsua(((HttpServletRequest) FacesContext
									.getCurrentInstance().getExternalContext()
									.getRequest()).getRemoteAddr());
								usuario.setClaveReseteada(0);
							servicioSisUsuarios.actualizar(usuario);
							dataManagerSesion.setUsuarioSesion(usuario);
						} else {// si no fue reseteada previamente
							usuario.setBloqueadoUsua(usuario.getBloqueadoUsua());
							usuario.setIpLogUsua(((HttpServletRequest) FacesContext
									.getCurrentInstance().getExternalContext()
									.getRequest()).getRemoteAddr());

							servicioSisUsuarios.actualizar(usuario);
							// Mantenemos los datos de usuario en la vista y
							// llenamos al usuario los menus que sea asignado
							dataManagerSesion.setUsuarioSesion(usuario);
							controladorMenuPrincipal.llenarMenu();
							// Nos dirigimos a la página Bienvenido
							FacesContext
									.getCurrentInstance()
									.getExternalContext()
									.redirect(
											"/hcRiesgoOperativo/paginas/Bienvenido.jsf");

						}
					} else {
						// Vamos a cambiar la contraseña, cuando ha expirado la
						// contraseña en 1 mes
						FacesContext
								.getCurrentInstance()
								.getExternalContext()
								.redirect(
										"/hcRiesgoOperativo/paginas/CambioContrasena.jsf");
						usuario.setBloqueadoUsua(usuario.getBloqueadoUsua());
						usuario.setIpLogUsua(((HttpServletRequest) FacesContext
								.getCurrentInstance().getExternalContext()
								.getRequest()).getRemoteAddr());

						servicioSisUsuarios.actualizar(usuario);
						dataManagerSesion.setUsuarioSesion(usuario);

					}

					tiempoSesion = servicioSisTiempoSesion.timeSesion();
					HttpSession session = (HttpSession) FacesContext
							.getCurrentInstance().getExternalContext()
							.getSession(false);
					session.setMaxInactiveInterval(Integer
							.parseInt(tiempoSesion));
					System.out.println("El tiempo de sesion es: "
							+ this.tiempoSesion + " segundos");
					session.setAttribute("username",
							usuario.getNombreCompletoUsua());

				} else {
					// Cuando ha ocurrido un error de intento de ingresar al
					// sistema, se suma el numero de intentos
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error al Ingresar",
									"El nombre de usuario o contraseña son incorrectos"));
					usuario.setBloqueadoUsua(usuario.getBloqueadoUsua() - 1.0);
					servicioSisUsuarios.actualizar(usuario);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Ha ocurrido una excepcion al intentar ingresar al sistema, cuando
			// recorrio algun pto del try y salio algun error
			dataManagerSesion.setUsuarioSesion(null);
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al Ingresar",
					"El nombre de usuario o contraseña son incorrectos"));
		}
	}

	public boolean contrasenaCaduca(SisUsuario usuario) {
		Date fechaActual = new Date();
		boolean caduca = true;
		if (fechaActual.before(usuario.getFechaCaducaUsua())) {
			caduca = false;
		}
		return caduca;
	}

	// metodos temporales

	/**
	 * GETTERS Y SETTERS
	 */
	public SisUsuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(SisUsuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public List<SisUsuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<SisUsuario> usuarios) {
		this.usuarios = usuarios;
	}

	public ServicioSisUsuario getServicioSisUsuarios() {
		return servicioSisUsuarios;
	}

	public void setServicioSisUsuarios(ServicioSisUsuario servicioSisUsuarios) {
		this.servicioSisUsuarios = servicioSisUsuarios;
	}

	public SisUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(SisUsuario usuario) {
		this.usuario = usuario;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public String getTiempoSesion() {
		return tiempoSesion;
	}

	public void setTiempoSesion(String tiempoSesion) {
		this.tiempoSesion = tiempoSesion;
	}

}
