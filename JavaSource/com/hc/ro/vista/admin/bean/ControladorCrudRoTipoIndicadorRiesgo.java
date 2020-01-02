package com.hc.ro.vista.admin.bean;

import java.math.BigDecimal;
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
import com.hc.ro.modelo.RoTipoIndicadorRiesgo;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoRespPro;
//import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.SisUsuario;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioRoTipoIndicadorRiesgo;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioRoRespPro;
import com.hc.ro.negocio.ServicioRoResponsable;

@ManagedBean
@ViewScoped
public class ControladorCrudRoTipoIndicadorRiesgo {

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	public final static String nombrePagina = "/paginas/CrudTipoIndicadorRiesgo.jsf";
	//

	@EJB
	ServicioRoTipoIndicadorRiesgo servicioRoTipoIndicadorRiesgo;
	@EJB
	ServicioGenEstado servicioGenEstado;
	@EJB
	ServicioRoRespPro servicioRoRespPro;
	@EJB
	ServicioRoResponsable servicioRoResponsable;
	@EJB
	ServicioRoProceso servicioRoProceso;

	// private BigDecimal limite;
	// private BigDecimal numeroOcurrencias;
	private boolean pnlEditarIndicadorRiesgo;
	private List<RoTipoIndicadorRiesgo> listaRoTipoIndicadorRiesgo;
	private List<RoProceso> procesosUsuario;
	RoTipoIndicadorRiesgo roTipoIndicadorRiesgoVista;
	RoTipoIndicadorRiesgo roTipoIndicadorRiesgoControlador;
	private SisUsuario sisUsuario;
//	private List<RoResponsable> responsablesTodos;
	private int idProcesoSeleccionado;
	private int idResponsableSeleccionado;
//	private RoResponsable roResponsableVista;
	private RoProceso roProcesoVista;
	private boolean nuevo;
	private String tipoValor;

	public ControladorCrudRoTipoIndicadorRiesgo() {

		listaRoTipoIndicadorRiesgo = new ArrayList<RoTipoIndicadorRiesgo>();
		roTipoIndicadorRiesgoVista = new RoTipoIndicadorRiesgo();
		roTipoIndicadorRiesgoVista.setRoProceso(new RoProceso());
//		roTipoIndicadorRiesgoVista.setRoResponsable(new RoResponsable());
		roTipoIndicadorRiesgoControlador=new RoTipoIndicadorRiesgo();
		procesosUsuario = new ArrayList<RoProceso>();
		sisUsuario = new SisUsuario();
//		roResponsableVista = new RoResponsable();
		roProcesoVista = new RoProceso();

	}

	@PostConstruct
	public void PostControladorCrudRoIndicadorRiesgo() {

		listaRoTipoIndicadorRiesgo = servicioRoTipoIndicadorRiesgo.buscarTodos();
		pnlEditarIndicadorRiesgo = false;
		nuevo = false;
		
		try {
			controladorMenuPrincipal.controlarAcceso();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		try {
			dataManagerSesion.controlarAcceso(nombrePagina);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		try {
			sisUsuario = dataManagerSesion.getUsuarioSesion();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		try {
//			cargarComboResponsables();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		try {
			cargarComboProcesos();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		System.out.println("roinrivista" +roTipoIndicadorRiesgoVista.getRoProceso().getNombrePros());
		tipoValor="numero";
		
	}

//	public void cargarComboResponsables()
//	{
//		responsablesTodos = servicioRoResponsable.buscarTodos();
//		System.out.println("-Combobox responsables cargado correctamente");
//	}
	
	
	public void cargarComboProcesos() {
		System.out.println(">>>nombre" + sisUsuario.getNombreCompletoUsua());
		List<RoRespPro> respProAux = new ArrayList<RoRespPro>();
		respProAux = servicioRoRespPro
				.buscarRespProcesoPorUsuarioEvaluacion(sisUsuario);
		procesosUsuario = new ArrayList<RoProceso>();
		if (!respProAux.isEmpty()) {
			for (RoRespPro respPro : respProAux) {
				procesosUsuario.add(respPro.getRoProceso());
			}
			System.out.println("-Combobox procesos cargado correctamente");
		} else {
			System.out.println("-ERROR al cargar combobox procesos");
		}

	}

	public void nuevoTipoIndicadorRiesgo() {

		
		//encerando seleccion de los combo box
		idProcesoSeleccionado=0;
		idResponsableSeleccionado=0;
		nuevo = true;
		roTipoIndicadorRiesgoVista = new RoTipoIndicadorRiesgo();
		
//		cargarComboProcesos();
//		cargarComboResponsables();
		pnlEditarIndicadorRiesgo = true;
		
		
	}
	
	public void modificarIndicadorRiesgo() {
		System.out.println("vista"+roTipoIndicadorRiesgoVista.getcodigoTinri());
		System.out.println("seleccionado"+roTipoIndicadorRiesgoControlador.getcodigoTinri());
		roTipoIndicadorRiesgoVista=new RoTipoIndicadorRiesgo();
		roTipoIndicadorRiesgoVista=roTipoIndicadorRiesgoControlador;
		pnlEditarIndicadorRiesgo = false;
		pnlEditarIndicadorRiesgo = true;
		nuevo = false;
		idProcesoSeleccionado=roTipoIndicadorRiesgoVista.getRoProceso().getCodigoPros();
//		idResponsableSeleccionado=roTipoIndicadorRiesgoVista.getRoResponsable().getCodigoResp();
		tipoValor=roTipoIndicadorRiesgoVista.getTipoValor();
		System.out.println("vista"+roTipoIndicadorRiesgoVista.getcodigoTinri());
		System.out.println("seleccionado"+roTipoIndicadorRiesgoControlador.getcodigoTinri());
	}
	
	public void eliminarIndicadorRiesgo() {
		//pnlEditarIndicadorRiesgo = false;
		servicioRoTipoIndicadorRiesgo.eliminar(roTipoIndicadorRiesgoVista);
		listaRoTipoIndicadorRiesgo = servicioRoTipoIndicadorRiesgo.buscarTodos();
	}

	

	// metodo para guardar elemento unico
	// public void guardarIndicadorRiesgo() {
	// if (servicioRoTipoIndicadorRiesgo.buscarTodos() != null) {
	// roIndicadorRiesgoVista.setcodigoTinri(servicioRoTipoIndicadorRiesgo
	// .getPrimero().getcodigoTinri());
	//
	// servicioRoTipoIndicadorRiesgo.actualizar(roIndicadorRiesgoVista);
	// listaRoIndicadorRiesgo = servicioRoTipoIndicadorRiesgo.buscarTodos();
	// pnlEditarIndicadorRiesgo = false;
	//
	// } else {
	// servicioRoTipoIndicadorRiesgo.insertar(roIndicadorRiesgoVista);
	// pnlEditarIndicadorRiesgo = false;
	// listaRoIndicadorRiesgo = servicioRoTipoIndicadorRiesgo.buscarTodos();
	// }
	//
	// }

	public void guardarIndicadorRiesgo() {
//		roResponsableVista = servicioRoResponsable
//				.buscarPorId(idResponsableSeleccionado);
		
		roProcesoVista = servicioRoProceso.buscarPorId(idProcesoSeleccionado);
//		roTipoIndicadorRiesgoVista.setRoResponsable(roResponsableVista);
		roTipoIndicadorRiesgoVista.setRoProceso(roProcesoVista);
//		roTipoIndicadorRiesgoVista.setnombreTinri(roTipoIndicadorRiesgoVista
//				.getnombreTinri());
//		roTipoIndicadorRiesgoVista.setUnidad(roTipoIndicadorRiesgoVista.getUnidad());
//		roTipoIndicadorRiesgoVista.setAlerta(roTipoIndicadorRiesgoVista.getAlerta());
//		roTipoIndicadorRiesgoVista.setLimite(roTipoIndicadorRiesgoVista.getLimite());
//		roTipoIndicadorRiesgoVista.setObservacionInri(roTipoIndicadorRiesgoVista
//				.getObservacionInri());

		if (nuevo) {
FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo indicador ¡Agregado!"));
			
			System.out.println("Insertando Nuevo");
			servicioRoTipoIndicadorRiesgo.insertar(roTipoIndicadorRiesgoVista);
			pnlEditarIndicadorRiesgo = false;
			listaRoTipoIndicadorRiesgo = servicioRoTipoIndicadorRiesgo.buscarTodos();

		} else {

			System.out.println("Actualizando");
			servicioRoTipoIndicadorRiesgo.actualizar(roTipoIndicadorRiesgoVista);
			pnlEditarIndicadorRiesgo = false;
			listaRoTipoIndicadorRiesgo = servicioRoTipoIndicadorRiesgo.buscarTodos();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo indicador ¡Actualizado!"));
		}

	}

	public void cancelar() {
		
		roTipoIndicadorRiesgoVista=new RoTipoIndicadorRiesgo();
		pnlEditarIndicadorRiesgo = false;
		

	}

	public boolean isPnlEditarIndicadorRiesgo() {
		return pnlEditarIndicadorRiesgo;
	}

	public void setPnlEditarIndicadorRiesgo(boolean pnlEditarIndicadorRiesgo) {
		this.pnlEditarIndicadorRiesgo = pnlEditarIndicadorRiesgo;
	}

	public List<RoTipoIndicadorRiesgo> getListaRoTipoIndicadorRiesgo() {
		return listaRoTipoIndicadorRiesgo;
	}

	public void setListaRoTipoIndicadorRiesgo(
			List<RoTipoIndicadorRiesgo> listaRoTipoIndicadorRiesgo) {
		this.listaRoTipoIndicadorRiesgo = listaRoTipoIndicadorRiesgo;
	}

	public List<RoProceso> getProcesosUsuario() {
		return procesosUsuario;
	}

	public void setProcesosUsuario(List<RoProceso> procesosUsuario) {
		this.procesosUsuario = procesosUsuario;
	}

//	public List<RoResponsable> getResponsablesTodos() {
//		return responsablesTodos;
//	}
//
//	public void setResponsablesTodos(List<RoResponsable> responsablesTodos) {
//		this.responsablesTodos = responsablesTodos;
//	}

	public int getIdProcesoSeleccionado() {
		return idProcesoSeleccionado;
	}

	public void setIdProcesoSeleccionado(int idProcesoSeleccionado) {
		this.idProcesoSeleccionado = idProcesoSeleccionado;
	}

	public int getIdResponsableSeleccionado() {
		return idResponsableSeleccionado;
	}

	public void setIdResponsableSeleccionado(int idResponsableSeleccionado) {
		this.idResponsableSeleccionado = idResponsableSeleccionado;
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

	public RoTipoIndicadorRiesgo getRoTipoIndicadorRiesgoVista() {
		return roTipoIndicadorRiesgoVista;
	}

	public void setRoTipoIndicadorRiesgoVista(
			RoTipoIndicadorRiesgo roTipoIndicadorRiesgoVista) {
		this.roTipoIndicadorRiesgoVista = roTipoIndicadorRiesgoVista;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	public RoTipoIndicadorRiesgo getRoTipoIndicadorRiesgoSeleccionado() {
		return roTipoIndicadorRiesgoControlador;
	}

	public void setRoTipoIndicadorRiesgoSeleccionado(
			RoTipoIndicadorRiesgo roTipoIndicadorRiesgoSeleccionado) {
		this.roTipoIndicadorRiesgoControlador = roTipoIndicadorRiesgoSeleccionado;
	}

	
	// GETTERS Y SETTERS

}
