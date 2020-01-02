package com.hc.ro.vista.admin.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.SisDetalleMenu;
import com.hc.ro.modelo.SisMenu;
import com.hc.ro.modelo.SisPermiso;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioSisDetalleMenu;
import com.hc.ro.negocio.ServicioSisMenu;
import com.hc.ro.negocio.ServicioSisPermiso;

@ManagedBean(name = "controladorMenuPrincipal")
@SessionScoped
public class ControladorMenuPrincipal {

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	// NEGOCIO
	@EJB
	ServicioSisMenu servicioMenu;
	@EJB
	ServicioSisDetalleMenu servicioDetalleMenu;
	@EJB
	ServicioSisPermiso servicioSisPermiso;

	// VARIABLES
	private SisMenu sisMenuVista;
	private SisDetalleMenu sisDetalleMenuVista;
	private SisMenu sisMenuControlador;
	private SisDetalleMenu sisDetalleMenuControlador;
	private List<SisMenu> menuTodos;
	private List<SisMenu> subMenuTodos;
	private MenuModel menuPrincipal;
	private List<SisPermiso> permisos;
	private List<SisDetalleMenu> detallesMenusAux;

	// CONSTRUCTOR
	public ControladorMenuPrincipal() {
		super();
		System.out.println("*Controlador MenuPrincipal cargado!*");
		sisDetalleMenuControlador = new SisDetalleMenu();
		sisMenuControlador = new SisMenu();
		sisDetalleMenuVista = new SisDetalleMenu();
		sisMenuVista = new SisMenu();
		permisos = new ArrayList<SisPermiso>();
		subMenuTodos = new ArrayList<SisMenu>();
		menuTodos = new ArrayList<SisMenu>();
		detallesMenusAux = new ArrayList<SisDetalleMenu>();
	}

	@PostConstruct
	public void PostControladorMenuPrincipal() {
		controlarAcceso();
		llenarMenu();
	}

	// metodos

	public void caduco() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(false);
		if (session != null) {
			try {
				session.invalidate(); // Cierre de sesion
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/hcRiesgoOperativo/paginas/Login.jsf");
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

//	public void recursivaMenu(List<SisMenu> menus, DefaultSubMenu padre) {
//		if (menus.isEmpty() == false) {
//			System.out.println(menus.isEmpty());
//			for (SisMenu itemSisMenu : menus) {
//				DefaultSubMenu subMenuPrime = new DefaultSubMenu(
//						itemSisMenu.getNombreMenu());
//				recursivaMenu(servicioMenu.buscarMenuPorPadre(itemSisMenu
//						.getNombreMenu()), subMenuPrime);
//				padre.addElement(subMenuPrime);
//				detallesMenusAux = servicioDetalleMenu
//						.buscarDetalleMenuPorMenu(itemSisMenu.getCodigoMenu());
//				for (SisDetalleMenu itemSisDeme : detallesMenusAux) {
//					DefaultMenuItem item = new DefaultMenuItem(
//							itemSisDeme.getNombreDeme());
//					item.setUrl(itemSisDeme.getAccionDeme());
//					item.setIcon(itemSisDeme.getIconoDeme());
//					subMenuPrime.addElement(item);
//				}
//			}
//		}
//	}

	/**
	 * Si la fecha actual está antes de la fecha que se va a caducar, entonces
	 * la la contraseña no caduca, caso contrario, caduca la contraseña
	 * 
	 * @param usuario
	 * @return false Si la contraseña no caducó true Si la fecha caducó
	 * 
	 */
	public boolean contrasenaCaduca(SisUsuario usuario) {
		Date fechaActual = new Date();
		boolean caduca = true;
		if (fechaActual.before(usuario.getFechaCaducaUsua())) {
			caduca = false;
		}
		return caduca;
	}

	public void llenarMenu() {
		menuTodos = new ArrayList<SisMenu>();
		menuPrincipal = new DefaultMenuModel();
		try {
			permisos = servicioSisPermiso
					.buscarPermisoPorPerfil(dataManagerSesion
							.getUsuarioSesion().getSisPerfil().getNombrePerf());
		} catch (Exception e) {

		}

		for (SisPermiso element : permisos) {

			if (element.getSisMenu() != null) {
				menuTodos.add(element.getSisMenu());

			}
		}

		List<SisMenu> menuPrimerNivelTodos = new ArrayList<SisMenu>();
		// los menus de primer nivel deben tener como ancestro la palabra menu
		for (SisMenu sisMenu : menuTodos) {
			try {
				if (sisMenu.getAncestroMenu().equals("menu")) {
					menuPrimerNivelTodos.add(sisMenu);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		// menuPrimerNivelTodos = servicioMenu.buscarMenuPorPadre("menu");

		for (SisMenu itemSisMenu : menuPrimerNivelTodos) {
			DefaultSubMenu subMenuPrime = new DefaultSubMenu(
					itemSisMenu.getNombreMenu());			
//			recursivaMenu(servicioMenu.buscarMenuPorPadre(itemSisMenu
//					.getNombreMenu()), subMenuPrime);
			menuPrincipal.addElement(subMenuPrime);
			detallesMenusAux = servicioDetalleMenu
					.buscarDetalleMenuPorMenu(itemSisMenu.getCodigoMenu());			
			for (SisDetalleMenu itemSisDeme : detallesMenusAux) {
				if (!servicioSisPermiso.existeDenegacion(itemSisDeme,
						dataManagerSesion.getUsuarioSesion().getSisPerfil())) {
					DefaultMenuItem item = new DefaultMenuItem(
							itemSisDeme.getNombreDeme());
					item.setUrl(itemSisDeme.getAccionDeme());
					subMenuPrime.addElement(item);
				}

			}
		}

	}

	/**
	 * Verifica si hay un usuario en sesion, o si la contraseña de usuario está
	 * caducada. Si no tiene sesion o la contraseña está caducada, me va a
	 * enviar a la página de login
	 */
	public void controlarAcceso() {
		if (dataManagerSesion.getUsuarioSesion() == null
				|| contrasenaCaduca(dataManagerSesion.getUsuarioSesion())) {

			try {
				
				System.out.println("Redireccionando al login...");
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/hcRiesgoOperativo/paginas/Login.jsf");
			} catch (IOException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			try {
				System.out.println("usario logeado: "
						+ dataManagerSesion.getUsuarioSesion().getNombreUsua());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	// getter setters
	public List<SisPermiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<SisPermiso> permisos) {
		this.permisos = permisos;
	}

	public List<SisDetalleMenu> getDetallesMenusAux() {
		return detallesMenusAux;
	}

	public void setDetallesMenusAux(List<SisDetalleMenu> detallesMenusAux) {
		this.detallesMenusAux = detallesMenusAux;
	}

	public SisMenu getSisMenuVista() {
		return sisMenuVista;
	}

	public void setSisMenuVista(SisMenu sisMenuVista) {
		this.sisMenuVista = sisMenuVista;
	}

	public SisDetalleMenu getSisDetalleMenuVista() {
		return sisDetalleMenuVista;
	}

	public void setSisDetalleMenuVista(SisDetalleMenu sisDetalleMenuVista) {
		this.sisDetalleMenuVista = sisDetalleMenuVista;
	}

	public SisMenu getSisMenuControlador() {
		return sisMenuControlador;
	}

	public void setSisMenuControlador(SisMenu sisMenuControlador) {
		this.sisMenuControlador = sisMenuControlador;
	}

	public SisDetalleMenu getSisDetalleMenuControlador() {
		return sisDetalleMenuControlador;
	}

	public void setSisDetalleMenuControlador(
			SisDetalleMenu sisDetalleMenuControlador) {
		this.sisDetalleMenuControlador = sisDetalleMenuControlador;
	}

	public List<SisMenu> getMenuTodos() {
		return menuTodos;
	}

	public void setMenuTodos(List<SisMenu> menuTodos) {
		this.menuTodos = menuTodos;
	}

	public List<SisMenu> getSubMenuTodos() {
		return subMenuTodos;
	}

	public void setSubMenuTodos(List<SisMenu> subMenuTodos) {
		this.subMenuTodos = subMenuTodos;
	}

	public MenuModel getMenuPrincipal() {
		return menuPrincipal;
	}

	public void setMenuPrincipal(MenuModel menuPrincipal) {
		this.menuPrincipal = menuPrincipal;
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

}
