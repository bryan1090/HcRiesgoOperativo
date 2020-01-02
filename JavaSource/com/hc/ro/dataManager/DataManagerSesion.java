package com.hc.ro.dataManager;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioSisPermiso;
import com.hc.ro.negocio.ServicioSisTiempoSesion;

@ManagedBean(name="dataManagerSesion")
@SessionScoped
public class DataManagerSesion {

	@EJB
	ServicioSisPermiso servicioSisPermiso;
	@EJB
	ServicioSisTiempoSesion servicioSisTiempoSesion;

	private int idDetalleSeleccionado;
	private SisUsuario usuarioSesion;
	private int numeroDeOcurrencias;
	private double riesgoResidual;
	private int numConsecResidual;
	private String consecResidual;
	private String numProbaResidual;
	private String probaResidual;

	private double riesgoInherente;
	private int numConsecInherente;
	private String consecInherente;
	private String numProbaInherente;
	private String probaInherente;
	private Date fechaRegistro;

	public DataManagerSesion() {
		super();
		System.out.println("*DataManagerSesion cargado!*");
		usuarioSesion = new SisUsuario();
		usuarioSesion = null;

	}

	public void logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(false);
		if (session != null) {
			try {

				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/hcRiesgoOperativo/paginas/Login.jsf");
				session.invalidate(); // Cierre de sesion
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}
	
	public int tiemp(){		
		int tiempoSesion=0;
		try {
			tiempoSesion=Integer.parseInt(servicioSisTiempoSesion.timeSesion()) * 1000;
		} catch (Exception e) {
			tiempoSesion=380;
			// TODO: handle exception
		}
		
//		return Integer.parseInt(servicioSisTiempoSesion.timeSesion()) * 1000;
		return tiempoSesion;
	}
	
	public void control() {
		//System.out.println(">>>>>>>>>>>>>>>>> Entro a verificar");
	}
	
	//Si el usuario da clic en el logo de HC consultores del templatepro, le redireccionamos a la pantalla de bienvenida.
	public void clicLogoHc()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/hcRiesgoOperativo/paginas/Bienvenido.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Revisamos si el perfil del usuario tiene restriccion a la página que está intentado ingresar en caso de que tenga restriccion
	 * le envia a la página Bienvenida, caso contrario le deja ingresar a la página que está solicitando
	 * @param nombrePagina
	 * true SI tiene restriccion
	 * false Si no tiene restriccion
	 */
	public void controlarAcceso(String nombrePagina) {
		System.out.println("DataManager ->controlarAcceso("+nombrePagina+")");
		boolean boolAux = false;
		try {
			//Comprueba este método si tiene restriccion
			boolAux = servicioSisPermiso.tienePermiso(nombrePagina,
					usuarioSesion.getSisPerfil());
			System.out.println("usuario logeado:"+usuarioSesion.getNombreUsua());
			System.out.println("tiene prohibido ver la pagina?: "+boolAux);
		} catch (Exception e) {
			
			boolAux = true;
			System.out.println("prohibiendo pagina: "+boolAux);
		}
		if (boolAux) {
			try {
				System.out.println("Redireccionando a la pantalla de bienvenida...");
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/hcRiesgoOperativo/paginas/Bienvenido.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public SisUsuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(SisUsuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	public int getIdDetalleSeleccionado() {
		return idDetalleSeleccionado;
	}

	public void setIdDetalleSeleccionado(int idDetalleSeleccionado) {
		this.idDetalleSeleccionado = idDetalleSeleccionado;
	}

	public int getNumeroDeOcurrencias() {
		return numeroDeOcurrencias;
	}

	public void setNumeroDeOcurrencias(int numeroDeOcurrencias) {
		this.numeroDeOcurrencias = numeroDeOcurrencias;
	}

	public double getRiesgoResidual() {
		return riesgoResidual;
	}

	public void setRiesgoResidual(double riesgoResidual) {
		this.riesgoResidual = riesgoResidual;
	}

	public int getNumConsecResidual() {
		return numConsecResidual;
	}

	public void setNumConsecResidual(int numConsecResidual) {
		this.numConsecResidual = numConsecResidual;
	}

	public String getConsecResidual() {
		return consecResidual;
	}

	public void setConsecResidual(String consecResidual) {
		this.consecResidual = consecResidual;
	}

	public String getProbaResidual() {
		return probaResidual;
	}

	public void setProbaResidual(String probaResidual) {
		this.probaResidual = probaResidual;
	}

	public double getRiesgoInherente() {
		return riesgoInherente;
	}

	public void setRiesgoInherente(double riesgoInherente) {
		this.riesgoInherente = riesgoInherente;
	}

	public int getNumConsecInherente() {
		return numConsecInherente;
	}

	public void setNumConsecInherente(int numConsecInherente) {
		this.numConsecInherente = numConsecInherente;
	}

	public String getConsecInherente() {
		return consecInherente;
	}

	public void setConsecInherente(String consecInherente) {
		this.consecInherente = consecInherente;
	}

	public String getProbaInherente() {
		return probaInherente;
	}

	public void setProbaInherente(String probaInherente) {
		this.probaInherente = probaInherente;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNumProbaInherente() {
		return numProbaInherente;
	}

	public void setNumProbaInherente(String numProbaInherente) {
		this.numProbaInherente = numProbaInherente;
	}

	public String getNumProbaResidual() {
		return numProbaResidual;
	}

	public void setNumProbaResidual(String numProbaResidual) {
		this.numProbaResidual = numProbaResidual;
	}

}
