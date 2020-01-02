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
import com.hc.ro.modelo.RoAttrbAdicionale;
import com.hc.ro.modelo.RoValAttrb;
import com.hc.ro.negocio.ServicioRoAttrbAdicionale;
import com.hc.ro.negocio.ServicioRoValAttrb;

@ManagedBean
@ViewScoped
public class ControladorCrudRoAttrbAdicionale {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudAttrbAdicionales.jsf";
	// AttrbAdicionale
	@EJB
	ServicioRoAttrbAdicionale servicioRoAttrbAdicionale;

	@EJB
	ServicioRoValAttrb servicioRoValAttrb;

	// VARIABLES
	private RoValAttrb roValAttrbVista;
	private RoAttrbAdicionale roAttrbAdicionaleVista;
	private RoAttrbAdicionale roAttrbAdicionaleControlador;
	
	private List<RoValAttrb> roValAttrbsTodos;
	private List<RoAttrbAdicionale> tipoAfectasTodos;
	private List<String> clasesTodos;
	private String claseSeleccionada;
	private int idAttrbAdicionaleSeleccionado;
	private int idEstadoSeleccionado;
	
	private boolean tipoGuardar;
	
	private String nombreSeleccionado;
	// botones,cajas,dialogos

	private boolean tipoGuardarValor;
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlAttrbAdicionale;
	private boolean btnAnadirValAttrb;
	private boolean pnlEditarValAttrb;
	private boolean mostrarPnlValAttrb;

	//
	public ControladorCrudRoAttrbAdicionale() {
		super();
		roValAttrbsTodos = new ArrayList<RoValAttrb>();
		roValAttrbVista = new RoValAttrb();
		roAttrbAdicionaleControlador = new RoAttrbAdicionale();
		roAttrbAdicionaleVista = new RoAttrbAdicionale();
		tipoAfectasTodos = new ArrayList<RoAttrbAdicionale>();
		clasesTodos = new ArrayList<String>();
		nombreSeleccionado = new String();
	}

	@PostConstruct
	public void PostControladorCrudRoAttrbAdicionale() {
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
		clasesTodos.add("Agencias");
		clasesTodos.add("Departamentos");
		clasesTodos.add("Procesos");
		clasesTodos.add("Responsable");
		clasesTodos.add("Detalle de Evento");
//		if(clasesTodos.get(0).equals("Agencias")){
//			//claseSeleccionada = clasesTodos.get(0);
////			tipoAfectasTodos = servicioRoAttrbAdicionale
////					.buscarEventoPorClase(claseSeleccionada);
//		}else if(clasesTodos.get(1).equals("Departamentos")){
//			//claseSeleccionada = clasesTodos.get(1);
////			tipoAfectasTodos = servicioRoAttrbAdicionale
////					.buscarEventoPorClase(claseSeleccionada);
//		}else if(clasesTodos.get(2).equals("Procesos")){
//			//claseSeleccionada = clasesTodos.get(2);
////			tipoAfectasTodos = servicioRoAttrbAdicionale
////					.buscarEventoPorClase(claseSeleccionada);
//		}else if(clasesTodos.get(3).equals("Responsable")){
//			//claseSeleccionada = clasesTodos.get(3);
////			tipoAfectasTodos = servicioRoAttrbAdicionale
////					.buscarEventoPorClase(claseSeleccionada);
//		}else if(clasesTodos.get(4).equals("Detalle de Evento")){
//			//claseSeleccionada = clasesTodos.get(4);
////			tipoAfectasTodos = servicioRoAttrbAdicionale
////					.buscarEventoPorClase(claseSeleccionada);
//		}

//		tipoAfectasTodos = servicioRoAttrbAdicionale
//				.buscarEventoPorClase(claseSeleccionada);
		tipoAfectasTodos = servicioRoAttrbAdicionale
		.buscarTodos();

		
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlAttrbAdicionale = false;

		btnAnadirValAttrb = true;
		pnlEditarValAttrb = false;
	}

	public void guardarAttrbAdicionale() {
		roAttrbAdicionaleVista.setClaseAttr(claseSeleccionada);
		if (tipoGuardar) {
			if (!(servicioRoAttrbAdicionale.existeAtributoPorNombrePorClase(roAttrbAdicionaleVista.getNombreAttr(),roAttrbAdicionaleVista.getClaseAttr()))) {

				try {
					servicioRoAttrbAdicionale.insertar(roAttrbAdicionaleVista);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(
							"Atributo Adicional Añadido",
							"El registro ha sido Añadido con éxito"));
					exitoGuardar();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre del Atributo no se debe repetir para la misma clase jejeje"));
			}

		} else {
			if (!(servicioRoAttrbAdicionale.existeAtributoPorNombreEx(roAttrbAdicionaleVista.getNombreAttr(),roAttrbAdicionaleVista.getCodigoAttr(), roAttrbAdicionaleVista.getClaseAttr()))) {

				try {
					servicioRoAttrbAdicionale.actualizar(roAttrbAdicionaleVista);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(
							"Atributo Adicional Actualizado",
							"El registro ha sido Actualizado con éxito"));
					exitoGuardar();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al actualizar:",
									"Hubo un problema al actualizar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				}

			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre del Atributo no se debe repetir para la misma clase jajajaj"));
			}

		}
	}

	public void exitoGuardar() {
		roAttrbAdicionaleVista = new RoAttrbAdicionale();
//		tipoAfectasTodos = servicioRoAttrbAdicionale
//				.buscarEventoPorClase(claseSeleccionada);
		
		tipoAfectasTodos = servicioRoAttrbAdicionale
				.buscarTodos();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		btnAnadirValAttrb = true;
		pnlEditarValAttrb = false;
		pnlAttrbAdicionale = false;
		tipoGuardar = false;
	}

	public void seleccionar() {
		roAttrbAdicionaleVista = roAttrbAdicionaleControlador;
		roValAttrbsTodos = servicioRoValAttrb
				.buscarEventoPorAttrb(roAttrbAdicionaleVista);

		tipoGuardar = false;
		mostrarPnlValAttrb = true;
		// botones deshabilitados????
		pnlAttrbAdicionale = false;
		btnAnadir = false;
		btnAnadirValAttrb = false;
	}

	public void eliminarAttrbAdicionale() {

		try {
			servicioRoAttrbAdicionale.eliminar(roAttrbAdicionaleVista);
			roAttrbAdicionaleVista = new RoAttrbAdicionale();
//			tipoAfectasTodos = servicioRoAttrbAdicionale
//					.buscarEventoPorClase(claseSeleccionada);
			tipoAfectasTodos = servicioRoAttrbAdicionale
					.buscarTodos();
			btnGuardar = true;
			btnCancelar = true;
			pnlAttrbAdicionale = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Atributo Adicional Eliminado",
					"El registro ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Atributo Adicional en uso"));
		}
	}

	public void nuevoAttrbAdicionaleVista() {
		tipoGuardar = true;
		roAttrbAdicionaleVista = new RoAttrbAdicionale();
		roAttrbAdicionaleControlador = new RoAttrbAdicionale();
		btnGuardar = false;
		btnCancelar = false;
		mostrarPnlValAttrb = false;
		pnlAttrbAdicionale = true;
		btnAnadirValAttrb = true;
		pnlEditarValAttrb = false;

	}

	public void cancelar() {
		//nuevoAttrbAdicionaleVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlAttrbAdicionale = false;
		RequestContext.getCurrentInstance().update("formAttrbAdicionale");
		btnAnadirValAttrb = true;
		pnlEditarValAttrb = false;
		tipoGuardar = false;
	}

	public void editarAttrbAdicionaleVista() {
		//tipoGuardar = false;
		claseSeleccionada = roAttrbAdicionaleVista.getClaseAttr();
		btnGuardar = false;
		btnCancelar = false;
		pnlAttrbAdicionale = true;
		btnAnadir = true;
		btnAnadirValAttrb = false;
		pnlEditarValAttrb = true;
		mostrarPnlValAttrb = false;
		//roValAttrbsTodos = servicioRoValAttrb.buscarEventoPorAttrb(roAttrbAdicionaleVista);
	}

	public void editarAttrVal() {
		tipoGuardarValor = false;
		pnlAttrbAdicionale = true;
		btnAnadir = true;
		pnlEditarValAttrb = true;

	}

	public void seleccionarAttrbAdicionaleVista() {
		btnAnadirValAttrb = false;
		pnlEditarValAttrb = true;
		roValAttrbsTodos = servicioRoValAttrb
				.buscarEventoPorAttrb(roAttrbAdicionaleVista);
		tipoGuardarValor = false;
	}

	public void nuevoValAttrb() {
		tipoGuardarValor = true;
		roValAttrbVista = new RoValAttrb();
		btnAnadirValAttrb = false;
		pnlEditarValAttrb = true;
	}

	public void guardarValAttrb() {
		roValAttrbVista.setRoAttrbAdicionale(roAttrbAdicionaleVista);
		if (tipoGuardarValor) {
			System.out.println("Entra a guardar");
			if (!servicioRoValAttrb.existeValAttrbPorNombreAttrbClase(
					roValAttrbVista.getVariableValAttr(),
					roAttrbAdicionaleVista,
					roAttrbAdicionaleVista.getClaseAttr())) {
				try {
					servicioRoValAttrb.insertar(roValAttrbVista);
					cancelarValAttrb();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"Hubo un problema al guardar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				} finally {
					roValAttrbsTodos = servicioRoValAttrb
							.buscarEventoPorAttrb(roAttrbAdicionaleVista);
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Nombre del Control no se puede repetir"));
				roValAttrbsTodos = servicioRoValAttrb
						.buscarEventoPorAttrb(roAttrbAdicionaleVista);
			}

		} else {
			if (!servicioRoValAttrb.existeValAttrbPorNombreAttrbClaseEx(
					roValAttrbVista.getVariableValAttr(),
					roAttrbAdicionaleVista,
					roAttrbAdicionaleVista.getClaseAttr(),
					roValAttrbVista.getCodigoValorAttr())) {
				System.out.println("Ingresa a actualzar");
				try {
					servicioRoValAttrb.actualizar(roValAttrbVista);
					cancelarValAttrb();
				} catch (Exception e) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al actualizar:",
									"Hubo un problema al actualizar, intentelo nuevamente, si el problema persiste comuniquese con el proveedor del sistema"));

				} finally {
					roValAttrbsTodos = servicioRoValAttrb
							.buscarEventoPorAttrb(roAttrbAdicionaleVista);
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El Nombre de la variable no se puede repetir"));
				roValAttrbsTodos = servicioRoValAttrb
						.buscarEventoPorAttrb(roAttrbAdicionaleVista);
			}
		}
	}

	public void cancelarValAttrb() {
		roValAttrbVista = new RoValAttrb();
		btnAnadirValAttrb = false;
		pnlEditarValAttrb = false;
	}

	public void eliminarValAttrb() {
		try {
			servicioRoValAttrb.eliminar(roValAttrbVista);
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al guardar:",
					"Hubo un problema al guardar, registro en uso"));
		} finally {
			roValAttrbsTodos = servicioRoValAttrb
					.buscarEventoPorAttrb(roAttrbAdicionaleVista);
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

	public RoAttrbAdicionale getRoAttrbAdicionaleVista() {
		return roAttrbAdicionaleVista;
	}

	public void setRoAttrbAdicionaleVista(
			RoAttrbAdicionale roAttrbAdicionaleVista) {
		this.roAttrbAdicionaleVista = roAttrbAdicionaleVista;
	}

	public RoAttrbAdicionale getRoAttrbAdicionaleControlador() {
		return roAttrbAdicionaleControlador;
	}

	public void setRoAttrbAdicionaleControlador(
			RoAttrbAdicionale roAttrbAdicionaleControlador) {
		this.roAttrbAdicionaleControlador = roAttrbAdicionaleControlador;
	}

	public List<RoAttrbAdicionale> getAttrbAdicionalesTodos() {
		return tipoAfectasTodos;
	}

	public void setAttrbAdicionalesTodos(
			List<RoAttrbAdicionale> AttrbAdicionalesTodos) {
		this.tipoAfectasTodos = AttrbAdicionalesTodos;
	}

	public int getIdAttrbAdicionaleSeleccionado() {
		return idAttrbAdicionaleSeleccionado;
	}

	public void setIdAttrbAdicionaleSeleccionado(
			int idAttrbAdicionaleSeleccionado) {
		this.idAttrbAdicionaleSeleccionado = idAttrbAdicionaleSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public int getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	public void setIdEstadoSeleccionado(int idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlAttrbAdicionale() {
		return pnlAttrbAdicionale;
	}

	public void setPnlAttrbAdicionale(boolean pnlAttrbAdicionale) {
		this.pnlAttrbAdicionale = pnlAttrbAdicionale;
	}

	public List<RoAttrbAdicionale> getTipoAfectasTodos() {
		return tipoAfectasTodos;
	}

	public void setTipoAfectasTodos(List<RoAttrbAdicionale> tipoAfectasTodos) {
		this.tipoAfectasTodos = tipoAfectasTodos;
	}

	public List<String> getClasesTodos() {
		return clasesTodos;
	}

	public void setClasesTodos(List<String> clasesTodos) {
		this.clasesTodos = clasesTodos;
	}

	public String getClaseSeleccionada() {
		return claseSeleccionada;
	}

	public void setClaseSeleccionada(String claseSeleccionada) {
		this.claseSeleccionada = claseSeleccionada;
	}

	public List<RoValAttrb> getRoValAttrbsTodos() {
		return roValAttrbsTodos;
	}

	public void setRoValAttrbsTodos(List<RoValAttrb> roValAttrbsTodos) {
		this.roValAttrbsTodos = roValAttrbsTodos;
	}

	public RoValAttrb getRoValAttrbVista() {
		return roValAttrbVista;
	}

	public void setRoValAttrbVista(RoValAttrb roValAttrbVista) {
		this.roValAttrbVista = roValAttrbVista;
	}

	public boolean isBtnAnadirValAttrb() {
		return btnAnadirValAttrb;
	}

	public void setBtnAnadirValAttrb(boolean btnAnadirValAttrb) {
		this.btnAnadirValAttrb = btnAnadirValAttrb;
	}

	public boolean isPnlEditarValAttrb() {
		return pnlEditarValAttrb;
	}

	public void setPnlEditarValAttrb(boolean pnlEditarValAttrb) {
		this.pnlEditarValAttrb = pnlEditarValAttrb;
	}

	public boolean isMostrarPnlValAttrb() {
		return mostrarPnlValAttrb;
	}

	public void setMostrarPnlValAttrb(boolean mostrarPnlValAttrb) {
		this.mostrarPnlValAttrb = mostrarPnlValAttrb;
	}

	public boolean isTipoGuardarValor() {
		return tipoGuardarValor;
	}

	public void setTipoGuardarValor(boolean tipoGuardarValor) {
		this.tipoGuardarValor = tipoGuardarValor;
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
