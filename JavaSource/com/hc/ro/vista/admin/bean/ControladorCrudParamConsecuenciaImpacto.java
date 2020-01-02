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

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioParamConsecuenciaImpacto;

@ManagedBean
@ViewScoped
public class ControladorCrudParamConsecuenciaImpacto {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	@EJB
	ServicioParamConsecuenciaImpacto servicioParamConsecuenciaImpacto;

	// variables
	public final static String nombrePagina = "/paginas/ParamConsecuenciaImpacto.jsf";
	private ParamConsecuenciaImpacto paramConsecuenciaImpactoVista;
	private ParamConsecuenciaImpacto paramConsecuenciaImpactoControlador;
	private List<ParamConsecuenciaImpacto> paramsProbasTodos;

	private boolean btnAnadir;
	private boolean btnEditar;
	private boolean btnSubir;
	private boolean btnBajar;
	private boolean btnEliminar;
	private boolean pnlParam;
	private boolean tipoGuardar;

	public ControladorCrudParamConsecuenciaImpacto() {
		paramConsecuenciaImpactoVista = new ParamConsecuenciaImpacto();
		paramConsecuenciaImpactoControlador = new ParamConsecuenciaImpacto();
		paramsProbasTodos = new ArrayList<ParamConsecuenciaImpacto>();
	}

	@PostConstruct
	public void Postinit() {
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
		paramsProbasTodos = servicioParamConsecuenciaImpacto.buscarTodos();
		btnAnadir = false;
		btnEditar = false;
		btnSubir = false;
		btnBajar = false;
		btnEliminar = false;
		pnlParam = false;
	}

	public void nuevo() {
		paramConsecuenciaImpactoVista = new ParamConsecuenciaImpacto();
		btnAnadir = true;
		btnEditar = true;
		btnSubir = true;
		btnBajar = true;
		btnEliminar = true;
		pnlParam = true;
		tipoGuardar = true;
	}

	public void cancelar() {
		paramConsecuenciaImpactoVista = new ParamConsecuenciaImpacto();
		paramConsecuenciaImpactoControlador = new ParamConsecuenciaImpacto();
		btnAnadir = false;
		btnEditar = false;
		btnSubir = false;
		btnBajar = false;
		btnEliminar = false;
		pnlParam = false;
	}

	public void exitoGuardar() {
		cancelar();
		paramsProbasTodos = servicioParamConsecuenciaImpacto.buscarTodos();
	}

	public void agregar() {
		if (tipoGuardar) {
			if (servicioParamConsecuenciaImpacto
					.existeConsecuenciaImpactoPorNombre(paramConsecuenciaImpactoVista
							.getNombrePconi())) {
				int longitud;
				longitud = paramsProbasTodos.size();
				paramConsecuenciaImpactoVista.setNumeroPconi(longitud + 1);
				servicioParamConsecuenciaImpacto
						.insertar(paramConsecuenciaImpactoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Parámetro Añadido",
						"El Tipo de Agencia ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre no se debe repetir"));
			}
		} else {
			if (servicioParamConsecuenciaImpacto
					.existeConsecuenciaImpactoPorNombreEx(
							paramConsecuenciaImpactoVista.getNombrePconi(),
							paramConsecuenciaImpactoVista.getCodigoPconi())) {
				servicioParamConsecuenciaImpacto
						.actualizar(paramConsecuenciaImpactoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Parámetro Actualizado",
						"El Parámetro ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre no se debe repetir"));
			}
		}

	}

	public void editar() {
		tipoGuardar = false;
		btnAnadir = true;
		btnEditar = true;
		btnSubir = true;
		btnBajar = true;
		btnEliminar = true;
		pnlParam = true;
	}

	public void subir() {
		int num;
		num = paramConsecuenciaImpactoVista.getNumeroPconi();
		if (num == 1) {

		} else {
			paramConsecuenciaImpactoControlador = servicioParamConsecuenciaImpacto
					.buscarConsecuenciaImpactoPorNumero(num - 1);
			paramConsecuenciaImpactoVista
					.setNumeroPconi(paramConsecuenciaImpactoControlador
							.getNumeroPconi());
			paramConsecuenciaImpactoControlador.setNumeroPconi(num);
			servicioParamConsecuenciaImpacto
					.actualizar(paramConsecuenciaImpactoControlador);
			servicioParamConsecuenciaImpacto
					.actualizar(paramConsecuenciaImpactoVista);
			exitoGuardar();
		}
	}

	public void subirEliminar() {
		int num;

		num = paramConsecuenciaImpactoVista.getNumeroPconi();

		if (num == 1) {

		} else {
			paramConsecuenciaImpactoControlador = servicioParamConsecuenciaImpacto
					.buscarConsecuenciaImpactoPorNumero(num - 1);
			paramConsecuenciaImpactoVista
					.setNumeroPconi(paramConsecuenciaImpactoControlador
							.getNumeroPconi());
			paramConsecuenciaImpactoControlador.setNumeroPconi(num);
			servicioParamConsecuenciaImpacto
					.actualizar(paramConsecuenciaImpactoControlador);
			servicioParamConsecuenciaImpacto
					.actualizar(paramConsecuenciaImpactoVista);
			exitoGuardar();
		}
	}

	public void bajar() {
		int num;
		num = paramConsecuenciaImpactoVista.getNumeroPconi();
		if (num == paramsProbasTodos.size()) {

		} else {
			paramConsecuenciaImpactoControlador = servicioParamConsecuenciaImpacto
					.buscarConsecuenciaImpactoPorNumero(num + 1);
			paramConsecuenciaImpactoVista
					.setNumeroPconi(paramConsecuenciaImpactoControlador
							.getNumeroPconi());
			paramConsecuenciaImpactoControlador.setNumeroPconi(num);
			servicioParamConsecuenciaImpacto
					.actualizar(paramConsecuenciaImpactoControlador);
			servicioParamConsecuenciaImpacto
					.actualizar(paramConsecuenciaImpactoVista);
			exitoGuardar();
		}
	}

	public void eliminar() {
		try {
			servicioParamConsecuenciaImpacto
					.eliminar(paramConsecuenciaImpactoVista);
			int numAux;
			numAux = paramConsecuenciaImpactoVista.getNumeroPconi();
			for (int i = numAux; i < paramsProbasTodos.size(); i++) {
				paramConsecuenciaImpactoControlador = servicioParamConsecuenciaImpacto
						.buscarConsecuenciaImpactoPorNumero(i + 1);
				paramConsecuenciaImpactoControlador.setNumeroPconi(i);
				servicioParamConsecuenciaImpacto
						.actualizar(paramConsecuenciaImpactoControlador);
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Parámetro Eliminado",
					"El Parámetro ha sido Eliminado con éxito"));
			exitoGuardar();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Parámetro en uso"));
		} finally {
			cancelar();
		}
	}

	// /getters setters

	public ParamConsecuenciaImpacto getParamConsecuenciaImpactoVista() {
		return paramConsecuenciaImpactoVista;
	}

	public void setParamConsecuenciaImpactoVista(
			ParamConsecuenciaImpacto paramConsecuenciaImpactoVista) {
		this.paramConsecuenciaImpactoVista = paramConsecuenciaImpactoVista;
	}

	public List<ParamConsecuenciaImpacto> getParamsProbasTodos() {
		return paramsProbasTodos;
	}

	public void setParamsProbasTodos(
			List<ParamConsecuenciaImpacto> paramsProbasTodos) {
		this.paramsProbasTodos = paramsProbasTodos;
	}

	public boolean isBtnAnadir() {
		return btnAnadir;
	}

	public void setBtnAnadir(boolean btnAnadir) {
		this.btnAnadir = btnAnadir;
	}

	public boolean isPnlParam() {
		return pnlParam;
	}

	public void setPnlParam(boolean pnlParam) {
		this.pnlParam = pnlParam;
	}

	public boolean isBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(boolean btnEditar) {
		this.btnEditar = btnEditar;
	}

	public boolean isBtnSubir() {
		return btnSubir;
	}

	public void setBtnSubir(boolean btnSubir) {
		this.btnSubir = btnSubir;
	}

	public boolean isBtnBajar() {
		return btnBajar;
	}

	public void setBtnBajar(boolean btnBajar) {
		this.btnBajar = btnBajar;
	}

	public boolean isBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(boolean btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public ParamConsecuenciaImpacto getParamConsecuenciaImpactoControlador() {
		return paramConsecuenciaImpactoControlador;
	}

	public void setParamConsecuenciaImpactoControlador(
			ParamConsecuenciaImpacto paramConsecuenciaImpactoControlador) {
		this.paramConsecuenciaImpactoControlador = paramConsecuenciaImpactoControlador;
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
