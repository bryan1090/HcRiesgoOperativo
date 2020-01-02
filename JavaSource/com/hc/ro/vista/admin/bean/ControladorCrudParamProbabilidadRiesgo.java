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
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.negocio.ServicioParamProbabilidadRiesgo;

@ManagedBean
@ViewScoped
public class ControladorCrudParamProbabilidadRiesgo {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	@EJB
	ServicioParamProbabilidadRiesgo servicioParamProbabilidadRiesgo;

	// variables
	public final static String nombrePagina = "/paginas/ParamProbabilidadRiesgo.jsf";
	private ParamProbabilidadRiesgo paramProbabilidadRiesgoVista;
	private ParamProbabilidadRiesgo paramProbabilidadRiesgoControlador;
	private List<ParamProbabilidadRiesgo> paramsProbasTodos;

	private String letra;

	private boolean btnAnadir;
	private boolean btnEditar;
	private boolean btnSubir;
	private boolean btnBajar;
	private boolean btnEliminar;
	private boolean pnlParam;
	private boolean tipoGuardar;

	public ControladorCrudParamProbabilidadRiesgo() {
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		paramProbabilidadRiesgoControlador = new ParamProbabilidadRiesgo();
		paramsProbasTodos = new ArrayList<ParamProbabilidadRiesgo>();
	}

	@PostConstruct
	public void init() {
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
		paramsProbasTodos = servicioParamProbabilidadRiesgo.buscarTodos();
		btnAnadir = false;
		btnEditar = false;
		btnSubir = false;
		btnBajar = false;
		btnEliminar = false;
		pnlParam = false;
	}

	public void nuevo() {
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		btnAnadir = true;
		btnEditar = true;
		btnSubir = true;
		btnBajar = true;
		btnEliminar = true;
		pnlParam = true;
		tipoGuardar = true;
	}

	public void cancelar() {
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		paramProbabilidadRiesgoControlador = new ParamProbabilidadRiesgo();
		btnAnadir = false;
		btnEditar = false;
		btnSubir = false;
		btnBajar = false;
		btnEliminar = false;
		pnlParam = false;
	}

	public void exitoGuardar() {
		cancelar();
		paramsProbasTodos = servicioParamProbabilidadRiesgo.buscarTodos();
	}

	public void agregar() {
		if (tipoGuardar) {
			if (servicioParamProbabilidadRiesgo
					.existeProbabilidadRiesgoPorNombre(paramProbabilidadRiesgoVista
							.getNombrePprr())) {
				int longitud;
				longitud = paramsProbasTodos.size();
				paramProbabilidadRiesgoVista.setNumeroPprr(longitud + 1);
				setLetra("");
				asignarLetra(paramProbabilidadRiesgoVista.getNumeroPprr());
				paramProbabilidadRiesgoVista.setLetraPprr(letra);
				servicioParamProbabilidadRiesgo
						.insertar(paramProbabilidadRiesgoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage("Parámetro Añadido",
						"El Parámetro ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre no se debe repetir"));
			}
		} else {
			if (servicioParamProbabilidadRiesgo
					.existeProbabilidadRiesgoPorNombreEx(
							paramProbabilidadRiesgoVista.getNombrePprr(),
							paramProbabilidadRiesgoVista.getCodigoPprr())) {
				servicioParamProbabilidadRiesgo
						.actualizar(paramProbabilidadRiesgoVista);
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

	public void asignarLetra(int num) {
		if (num > 26) {
			int aux;
			aux = (num - 1) / 26;
			setLetra(getLetra() + (Character.toString((char) (aux + 64))));
			aux = num % 26;
			asignarLetra(aux);
		} else {

			if (num == 0) {
				num += 26;
			}
			setLetra(getLetra() + (Character.toString((char) (num + 64))));
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
		String letraAux = new String("");
		num = paramProbabilidadRiesgoVista.getNumeroPprr();
		letraAux = paramProbabilidadRiesgoVista.getLetraPprr();
		if (num == 1) {

		} else {
			paramProbabilidadRiesgoControlador = servicioParamProbabilidadRiesgo
					.buscarProbabilidadRiesgoPorNumero(num - 1);
			paramProbabilidadRiesgoVista
					.setNumeroPprr(paramProbabilidadRiesgoControlador
							.getNumeroPprr());
			paramProbabilidadRiesgoVista
					.setLetraPprr(paramProbabilidadRiesgoControlador
							.getLetraPprr());
			paramProbabilidadRiesgoControlador.setNumeroPprr(num);
			paramProbabilidadRiesgoControlador.setLetraPprr(letraAux);
			servicioParamProbabilidadRiesgo
					.actualizar(paramProbabilidadRiesgoControlador);
			servicioParamProbabilidadRiesgo
					.actualizar(paramProbabilidadRiesgoVista);
			exitoGuardar();
		}
	}

	public void subirEliminar() {
		int num;
		String letraAux = new String("");
		num = paramProbabilidadRiesgoVista.getNumeroPprr();
		letraAux = paramProbabilidadRiesgoVista.getLetraPprr();
		if (num == 1) {

		} else {
			paramProbabilidadRiesgoControlador = servicioParamProbabilidadRiesgo
					.buscarProbabilidadRiesgoPorNumero(num - 1);
			paramProbabilidadRiesgoVista
					.setNumeroPprr(paramProbabilidadRiesgoControlador
							.getNumeroPprr());
			paramProbabilidadRiesgoVista
					.setLetraPprr(paramProbabilidadRiesgoControlador
							.getLetraPprr());
			paramProbabilidadRiesgoControlador.setNumeroPprr(num);
			paramProbabilidadRiesgoControlador.setLetraPprr(letraAux);
			servicioParamProbabilidadRiesgo
					.actualizar(paramProbabilidadRiesgoControlador);
			servicioParamProbabilidadRiesgo
					.actualizar(paramProbabilidadRiesgoVista);
			exitoGuardar();
		}
	}

	public void bajar() {
		int num;
		String letraAux = new String("");
		num = paramProbabilidadRiesgoVista.getNumeroPprr();
		letraAux = paramProbabilidadRiesgoVista.getLetraPprr();
		if (num == paramsProbasTodos.size()) {

		} else {
			paramProbabilidadRiesgoControlador = servicioParamProbabilidadRiesgo
					.buscarProbabilidadRiesgoPorNumero(num + 1);
			paramProbabilidadRiesgoVista
					.setNumeroPprr(paramProbabilidadRiesgoControlador
							.getNumeroPprr());
			paramProbabilidadRiesgoVista
					.setLetraPprr(paramProbabilidadRiesgoControlador
							.getLetraPprr());
			paramProbabilidadRiesgoControlador.setNumeroPprr(num);
			paramProbabilidadRiesgoControlador.setLetraPprr(letraAux);
			servicioParamProbabilidadRiesgo
					.actualizar(paramProbabilidadRiesgoControlador);
			servicioParamProbabilidadRiesgo
					.actualizar(paramProbabilidadRiesgoVista);
			exitoGuardar();
		}
	}

	public void eliminar() {
		try {
			servicioParamProbabilidadRiesgo
					.eliminar(paramProbabilidadRiesgoVista);
			int numAux;
			numAux = paramProbabilidadRiesgoVista.getNumeroPprr();
			for (int i = numAux; i < paramsProbasTodos.size(); i++) {
				paramProbabilidadRiesgoControlador = servicioParamProbabilidadRiesgo
						.buscarProbabilidadRiesgoPorNumero(i + 1);
				paramProbabilidadRiesgoControlador.setNumeroPprr(i);
				letra = "";
				asignarLetra(i);
				paramProbabilidadRiesgoControlador.setLetraPprr(letra);
				servicioParamProbabilidadRiesgo
						.actualizar(paramProbabilidadRiesgoControlador);
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

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoVista() {
		return paramProbabilidadRiesgoVista;
	}

	public void setParamProbabilidadRiesgoVista(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoVista) {
		this.paramProbabilidadRiesgoVista = paramProbabilidadRiesgoVista;
	}

	public List<ParamProbabilidadRiesgo> getParamsProbasTodos() {
		return paramsProbasTodos;
	}

	public void setParamsProbasTodos(
			List<ParamProbabilidadRiesgo> paramsProbasTodos) {
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

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoControlador() {
		return paramProbabilidadRiesgoControlador;
	}

	public void setParamProbabilidadRiesgoControlador(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoControlador) {
		this.paramProbabilidadRiesgoControlador = paramProbabilidadRiesgoControlador;
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
