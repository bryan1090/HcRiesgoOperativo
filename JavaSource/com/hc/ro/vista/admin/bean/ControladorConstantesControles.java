package com.hc.ro.vista.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoConstanteControl;
import com.hc.ro.modelo.RoTipoIndicadorRiesgo;
import com.hc.ro.negocio.ServicioRoConstanteControl;

@ManagedBean
@ViewScoped
public class ControladorConstantesControles {
	public final static String nombrePagina = "/paginas/ConstanteControl.jsf";

	@ManagedProperty(value = "#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@ManagedProperty(value = "#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@EJB
	ServicioRoConstanteControl servicioRoConstanteControl;

	private List<RoConstanteControl> constantesControlesTodas;
	private RoConstanteControl roConstanteControlVista;
	private long idRoConstanteControl;

	private Boolean mostrarPnlEditar;
	private boolean nuevo;

	public ControladorConstantesControles() {
		super();
		constantesControlesTodas = new ArrayList<RoConstanteControl>();
		roConstanteControlVista = new RoConstanteControl();
		mostrarPnlEditar = new Boolean(false);
		nuevo=false;
	}

	@PostConstruct
	public void postConstruct() {
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

		// dataman
		dataManagerSesion.setConsecInherente(new String());
		dataManagerSesion.setConsecResidual(new String());
		dataManagerSesion.setFechaRegistro(null);
		dataManagerSesion.setIdDetalleSeleccionado(0);
		dataManagerSesion.setNumConsecInherente(0);
		dataManagerSesion.setNumConsecResidual(0);
		dataManagerSesion.setNumeroDeOcurrencias(0);
		dataManagerSesion.setNumProbaInherente(new String());
		dataManagerSesion.setNumProbaResidual(new String());
		dataManagerSesion.setProbaInherente(new String());
		dataManagerSesion.setProbaResidual(new String());
		dataManagerSesion.setRiesgoInherente(0);
		dataManagerSesion.setRiesgoResidual(0);

		constantesControlesTodas = servicioRoConstanteControl.buscarTodos();
	}

	public void nuevo() {

		roConstanteControlVista = new RoConstanteControl();
		mostrarPnlEditar = true;
		
		nuevo=true;
	}



	public void editar() {
		
		System.out.println("nombre "+roConstanteControlVista.getNombreCte());
		mostrarPnlEditar = true;

	}

	public void eliminar() {
		servicioRoConstanteControl.eliminar(roConstanteControlVista);
		constantesControlesTodas = servicioRoConstanteControl.buscarTodos();
		mostrarPnlEditar=false;

	}

	public void guardar() {
//		RoConstanteControl roConstanteControlAux = new RoConstanteControl();
//		roConstanteControlAux = servicioRoConstanteControl
//				.buscarPorId(idRoConstanteControl);
//		roConstanteControl.setCodigoCte(idRoConstanteControl);
//		roConstanteControl.setNombreCte(roConstanteControlAux.getNombreCte());
//		servicioRoConstanteControl.actualizar(roConstanteControl);
		try {
			if(nuevo){
				servicioRoConstanteControl.insertar(roConstanteControlVista);
			}
			else{
				servicioRoConstanteControl.actualizar(roConstanteControlVista);
			}
			mostrarPnlEditar=false;
			roConstanteControlVista=new RoConstanteControl();
			constantesControlesTodas = servicioRoConstanteControl.buscarTodos();
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		


	}

	public void cancelar() {
		mostrarPnlEditar = false;
		roConstanteControlVista = new RoConstanteControl();
	}

	public DataManagerSesion getDataManagerSesion() {
		return dataManagerSesion;
	}

	public void setDataManagerSesion(DataManagerSesion dataManagerSesion) {
		this.dataManagerSesion = dataManagerSesion;
	}

	public ControladorMenuPrincipal getControladorMenuPrincipal() {
		return controladorMenuPrincipal;
	}

	public void setControladorMenuPrincipal(
			ControladorMenuPrincipal controladorMenuPrincipal) {
		this.controladorMenuPrincipal = controladorMenuPrincipal;
	}


	public List<RoConstanteControl> getConstantesControlesTodas() {
		return constantesControlesTodas;
	}

	public void setConstantesControlesTodas(
			List<RoConstanteControl> constantesControlesTodas) {
		this.constantesControlesTodas = constantesControlesTodas;
	}

	public RoConstanteControl getRoConstanteControlVista() {
		return roConstanteControlVista;
	}

	public void setRoConstanteControlVista(
			RoConstanteControl roConstanteControlVista) {
		this.roConstanteControlVista = roConstanteControlVista;
	}

	public long getIdRoConstanteControl() {
		return idRoConstanteControl;
	}

	public void setIdRoConstanteControl(long idRoConstanteControl) {
		this.idRoConstanteControl = idRoConstanteControl;
	}

	public Boolean getMostrarPnlEditar() {
		return mostrarPnlEditar;
	}

	public void setMostrarPnlEditar(Boolean mostrarPnlEditar) {
		this.mostrarPnlEditar = mostrarPnlEditar;
	}

}
