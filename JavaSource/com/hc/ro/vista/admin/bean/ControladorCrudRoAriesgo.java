package com.hc.ro.vista.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoAriesgo;
import com.hc.ro.modelo.RoCalifRiesgo;
import com.hc.ro.modelo.RoConsecuenciaImpacto;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoProbabilidadEvento;
import com.hc.ro.negocio.ServicioParamConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioParamProbabilidadRiesgo;
import com.hc.ro.negocio.ServicioRoAriesgo;
import com.hc.ro.negocio.ServicioRoCalifRiesgo;
import com.hc.ro.negocio.ServicioRoConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioRoNegocio;
import com.hc.ro.negocio.ServicioRoProbabilidadEvento;

@ManagedBean
@ViewScoped
public class ControladorCrudRoAriesgo {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	// TipoAfecta
	@EJB
	ServicioRoAriesgo servicioRoAriesgo;

	@EJB
	ServicioRoCalifRiesgo servicioRoCalifRiesgo;

	@EJB
	ServicioRoProbabilidadEvento servicioRoProbabilidadEvento;

	@EJB
	ServicioRoConsecuenciaImpacto servicioRoConsecuenciaImpacto;

	@EJB
	ServicioRoNegocio servicioRoNegocio;
	@EJB
	ServicioParamConsecuenciaImpacto servicioParamConsecuenciaImpacto;
	@EJB
	ServicioParamProbabilidadRiesgo servicioParamProbabilidadRiesgo;

	// VARIABLES NUEVAS
	public final static String nombrePagina = "/paginas/CrudAriesgo.jsf";
	private int filas;
	private int columnas;
	private RoCalifRiesgo roCalifRiesgoVista;
	private RoCalifRiesgo roCalifRiesgoControlador;
	private RoAriesgo roAriesgoVista;
	private RoAriesgo roAriesgoControlador;
	private ParamConsecuenciaImpacto roParamConsecuenciaImpacto;
	private ParamProbabilidadRiesgo roParamProbabilidadRiesgo;
	private List<RoAriesgo> ariesgoTodos;
	private List<RoAriesgo> ariesgoAuxiliares;
	private List<ParamConsecuenciaImpacto> paramConsecuenciaImpactoTodos;
	private List<ParamProbabilidadRiesgo> paramProbabilidadRiesgoTodos;

	// VARIABLES ANTERIORES
	private List<RoProbabilidadEvento> probabilidadesTodos;
	private List<RoCalifRiesgo> calificacionesTodos;
	private List<RoConsecuenciaImpacto> consecuenciasTodos;
	private List<RoNegocio> negociosTodos;
	private String idAriesgoSeleccionado;
	private int idNegocioSeleccionado;
	private int idNegocioSeleccionadoTabla;
	private int idProbabilidadSeleccionada;
	private int idCalificacionSeleccionada;
	private int idConsecuenciaSeleccionada;

	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlAriesgo;

	//
	public ControladorCrudRoAriesgo() {
		super();
		// nuevas
		roCalifRiesgoControlador = new RoCalifRiesgo();
		roCalifRiesgoVista = new RoCalifRiesgo();
		roAriesgoControlador = new RoAriesgo();
		roAriesgoVista = new RoAriesgo();
		ariesgoTodos = new ArrayList<RoAriesgo>();
		ariesgoAuxiliares = new ArrayList<RoAriesgo>();
		paramConsecuenciaImpactoTodos = new ArrayList<ParamConsecuenciaImpacto>();
		paramProbabilidadRiesgoTodos = new ArrayList<ParamProbabilidadRiesgo>();
		roParamConsecuenciaImpacto = new ParamConsecuenciaImpacto();
		roParamProbabilidadRiesgo = new ParamProbabilidadRiesgo();
		// anteriores

		probabilidadesTodos = new ArrayList<RoProbabilidadEvento>();
		calificacionesTodos = new ArrayList<RoCalifRiesgo>();
		nombreSeleccionado = new String();
		negociosTodos = new ArrayList<RoNegocio>();
		consecuenciasTodos = new ArrayList<RoConsecuenciaImpacto>();
	}

	@PostConstruct
	public void PostControladorCrudRoTipoAfecta() {
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

		// nuevo
		ariesgoAuxiliares = servicioRoAriesgo.buscarTodos();
		paramConsecuenciaImpactoTodos = servicioParamConsecuenciaImpacto
				.buscarTodos();
		paramProbabilidadRiesgoTodos = servicioParamProbabilidadRiesgo
				.buscarTodosDesc();
		calificacionesTodos = servicioRoCalifRiesgo.buscarTodos();
		filas = paramConsecuenciaImpactoTodos.size();

		columnas = paramConsecuenciaImpactoTodos.size();

		for (ParamProbabilidadRiesgo varPprr : paramProbabilidadRiesgoTodos) {
			for (ParamConsecuenciaImpacto varPconi : paramConsecuenciaImpactoTodos) {
				RoAriesgo roAriesgoAux = new RoAriesgo();
				roAriesgoAux.setParamConsecuenciaImpacto(varPconi);
				roAriesgoAux.setParamProbabilidadRiesgo(varPprr);
				for (RoAriesgo varAriesgo : ariesgoAuxiliares) {
					try {
						if (varAriesgo.getParamConsecuenciaImpacto()
								.getNumeroPconi() == varPconi.getNumeroPconi()
								&& varAriesgo.getParamProbabilidadRiesgo()
										.getNumeroPprr() == varPprr
										.getNumeroPprr()) {
							roAriesgoAux = varAriesgo;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
				ariesgoTodos.add(roAriesgoAux);
			}
		}

		// anteriores

		// consecuenciasTodos = servicioRoConsecuenciaImpacto.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlAriesgo = false;
	}

	// METODOS

	public void seleccionar() {
		roAriesgoVista.setRoCalifRiesgo(roCalifRiesgoControlador);
	}

	public void aceptarCalifRiesgo() {
		servicioRoAriesgo.actualizar(roAriesgoVista);
	}

	public void cancelarCalifRiesgo() {
		roAriesgoVista = new RoAriesgo();
	}

	// anteriores

	// public void guardarTipoAfecta() {
	// if (tipoGuardar) {
	// roAriesgoVista.setRoCalifRiesgo(servicioRoCalifRiesgo
	// .buscarPorId(idCalificacionSeleccionada));
	// roAriesgoVista
	// .setRoConsecuenciaImpacto(servicioRoConsecuenciaImpacto
	// .buscarPorId(idConsecuenciaSeleccionada));
	// roAriesgoVista.setRoNegocio(servicioRoNegocio
	// .buscarPorId(idNegocioSeleccionado));
	// roAriesgoVista.setRoProbabilidadEvento(servicioRoProbabilidadEvento
	// .buscarPorId(idProbabilidadSeleccionada));
	// if (servicioRoAriesgo.existePorNombreNego(
	// roAriesgoVista.getDescAriesgo(), idNegocioSeleccionado)) {
	// servicioRoAriesgo.insertar(roAriesgoVista);
	// FacesContext context = FacesContext.getCurrentInstance();
	//
	// context.addMessage(null, new FacesMessage(
	// "Asignación de Riesgo Añadido",
	// "Asignación de Riesgo ha sido Añadido con Exito"));
	// exitoGuardar();
	// } else {
	// FacesContext context = FacesContext.getCurrentInstance();
	//
	// context.addMessage(
	// null,
	// new FacesMessage(FacesMessage.SEVERITY_ERROR,
	// "Error al guardar:",
	// "El nombre de Asignaaci�n de Riesgo no se deben repetir"));
	// }
	// } else {
	//
	// if (servicioRoAriesgo.existePorNombreNegoEx(
	// roAriesgoVista.getDescAriesgo(),
	// roAriesgoVista.getCodAriesgo(), idNegocioSeleccionado)) {
	//
	// servicioRoAriesgo.actualizar(roAriesgoVista);
	//
	// FacesContext context = FacesContext.getCurrentInstance();
	//
	// context.addMessage(
	// null,
	// new FacesMessage("Aisgnaci�n de Riesgo Actualizado",
	// "La Asignaci�n de Riesgo ha sido Actualizada con Exito"));
	// exitoGuardar();
	// } else {
	// FacesContext context = FacesContext.getCurrentInstance();
	//
	// context.addMessage(
	// null,
	// new FacesMessage(FacesMessage.SEVERITY_ERROR,
	// "Error al guardar:",
	// "El nombre de Asignaaci�n de Riesgo no se deben repetir"));
	// roAriesgoVista.setDescAriesgo(roAriesgoControlador
	// .getDescAriesgo());
	// }
	// }
	// }
	//
	// public void exitoGuardar() {
	//
	// roAriesgoVista = new RoAriesgo();
	// ariesgoTodos = servicioRoAriesgo.buscarTodos();
	// btnAnadir = false;
	// btnGuardar = true;
	// btnCancelar = true;
	// pnlAriesgo = false;
	// }
	//
	// public void eliminarAriesgo() {
	//
	// try {
	// servicioRoAriesgo.eliminar(roAriesgoVista);
	// roAriesgoVista = new RoAriesgo();
	// ariesgoTodos = servicioRoAriesgo.buscarTodos();
	// btnGuardar = true;
	// btnCancelar = true;
	// pnlAriesgo = false;
	// FacesContext context = FacesContext.getCurrentInstance();
	// context.addMessage(null, new FacesMessage(
	// "Asignaci�n de Riesgo Eliminado",
	// "Asignaci�n de Riesgo ha sido Eliminado con Exito"));
	// } catch (Exception e) {
	// FacesContext context = FacesContext.getCurrentInstance();
	// context.addMessage(null, new FacesMessage(
	// FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
	// "Asignaci�n de Riesgo en Uso"));
	// }
	// }
	//
	// public void cambiarNegocio() {
	// probabilidadesTodos = servicioRoProbabilidadEvento
	// .buscarPorNegocio(idNegocioSeleccionado);
	// consecuenciasTodos = servicioRoConsecuenciaImpacto
	// .buscarPorNegocio(idNegocioSeleccionado);
	// }
	//
	// public void cambiarNegocioTabla() {
	// if (idNegocioSeleccionadoTabla != 0) {
	// ariesgoTodos = servicioRoAriesgo
	// .buscarPorNegocio(idNegocioSeleccionadoTabla);
	// } else {
	// ariesgoTodos = servicioRoAriesgo.buscarTodos();
	// }
	//
	// }
	//
	// public void nuevoAriesgoVista() {
	// tipoGuardar = true;
	// roAriesgoVista = new RoAriesgo();
	// btnGuardar = false;
	// btnCancelar = false;
	// pnlAriesgo = true;
	// try {
	// probabilidadesTodos = servicioRoProbabilidadEvento
	// .buscarPorNegocio(negociosTodos.get(0).getCodigoNego());
	// consecuenciasTodos = servicioRoConsecuenciaImpacto
	// .buscarPorNegocio(negociosTodos.get(0).getCodigoNego());
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// }
	//
	// public void cancelar() {
	// nuevoAriesgoVista();
	// btnGuardar = true;
	// btnAnadir = false;
	// btnCancelar = true;
	// pnlAriesgo = false;
	// RequestContext.getCurrentInstance().update("formTipoAfecta");
	// }
	//
	// public void editarAriesgoVista() {
	// roAriesgoControlador.setDescAriesgo(roAriesgoVista.getDescAriesgo());
	// tipoGuardar = false;
	//
	// btnGuardar = false;
	// btnCancelar = false;
	// pnlAriesgo = true;
	// btnAnadir = true;
	// cambiarNegocio();
	//
	// }

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

	public RoAriesgo getRoAriesgoVista() {
		return roAriesgoVista;
	}

	public RoAriesgo getRoAriesgoControlador() {
		return roAriesgoControlador;
	}

	public void setRoAriesgoControlador(RoAriesgo roAriesgoControlador) {
		this.roAriesgoControlador = roAriesgoControlador;
	}

	public List<RoAriesgo> getAriesgoTodos() {
		return ariesgoTodos;
	}

	public void setAriesgoTodos(List<RoAriesgo> ariesgoTodos) {
		this.ariesgoTodos = ariesgoTodos;
	}

	public String getIdAriesgoSeleccionado() {
		return idAriesgoSeleccionado;
	}

	public void setIdAriesgoSeleccionado(String idAriesgoSeleccionado) {
		this.idAriesgoSeleccionado = idAriesgoSeleccionado;
	}

	public void setRoAriesgoVista(RoAriesgo roAriesgoVista) {
		this.roAriesgoVista = roAriesgoVista;
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

	public boolean isPnlAriesgo() {
		return pnlAriesgo;
	}

	public void setPnlAriesgo(boolean pnlAriesgo) {
		this.pnlAriesgo = pnlAriesgo;
	}

	public int getIdNegocioSeleccionado() {
		return idNegocioSeleccionado;
	}

	public void setIdNegocioSeleccionado(int idNegocioSeleccionado) {
		this.idNegocioSeleccionado = idNegocioSeleccionado;
	}

	public int getIdProbabilidadSeleccionada() {
		return idProbabilidadSeleccionada;
	}

	public void setIdProbabilidadSeleccionada(int idProbabilidadSeleccionada) {
		this.idProbabilidadSeleccionada = idProbabilidadSeleccionada;
	}

	public int getIdCalificacionSeleccionada() {
		return idCalificacionSeleccionada;
	}

	public void setIdCalificacionSeleccionada(int idCalificacionSeleccionada) {
		this.idCalificacionSeleccionada = idCalificacionSeleccionada;
	}

	public int getIdConsecuenciaSeleccionada() {
		return idConsecuenciaSeleccionada;
	}

	public void setIdConsecuenciaSeleccionada(int idConsecuenciaSeleccionada) {
		this.idConsecuenciaSeleccionada = idConsecuenciaSeleccionada;
	}

	public List<RoProbabilidadEvento> getProbabilidadesTodos() {
		return probabilidadesTodos;
	}

	public void setProbabilidadesTodos(
			List<RoProbabilidadEvento> probabilidadesTodos) {
		this.probabilidadesTodos = probabilidadesTodos;
	}

	public List<RoCalifRiesgo> getCalificacionesTodos() {
		return calificacionesTodos;
	}

	public void setCalificacionesTodos(List<RoCalifRiesgo> calificacionesTodos) {
		this.calificacionesTodos = calificacionesTodos;
	}

	public List<RoConsecuenciaImpacto> getConsecuenciasTodos() {
		return consecuenciasTodos;
	}

	public void setConsecuenciasTodos(
			List<RoConsecuenciaImpacto> consecuenciasTodos) {
		this.consecuenciasTodos = consecuenciasTodos;
	}

	public List<RoNegocio> getNegociosTodos() {
		return negociosTodos;
	}

	public void setNegociosTodos(List<RoNegocio> negociosTodos) {
		this.negociosTodos = negociosTodos;
	}

	public int getIdNegocioSeleccionadoTabla() {
		return idNegocioSeleccionadoTabla;
	}

	public void setIdNegocioSeleccionadoTabla(int idNegocioSeleccionadoTabla) {
		this.idNegocioSeleccionadoTabla = idNegocioSeleccionadoTabla;
	}

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public List<ParamConsecuenciaImpacto> getParamConsecuenciaImpactoTodos() {
		return paramConsecuenciaImpactoTodos;
	}

	public void setParamConsecuenciaImpactoTodos(
			List<ParamConsecuenciaImpacto> paramConsecuenciaImpactoTodos) {
		this.paramConsecuenciaImpactoTodos = paramConsecuenciaImpactoTodos;
	}

	public List<ParamProbabilidadRiesgo> getParamProbabilidadRiesgoTodos() {
		return paramProbabilidadRiesgoTodos;
	}

	public void setParamProbabilidadRiesgoTodos(
			List<ParamProbabilidadRiesgo> paramProbabilidadRiesgoTodos) {
		this.paramProbabilidadRiesgoTodos = paramProbabilidadRiesgoTodos;
	}

	public RoCalifRiesgo getRoCalifRiesgoVista() {
		return roCalifRiesgoVista;
	}

	public void setRoCalifRiesgoVista(RoCalifRiesgo roCalifRiesgoVista) {
		this.roCalifRiesgoVista = roCalifRiesgoVista;
	}

	public RoCalifRiesgo getRoCalifRiesgoControlador() {
		return roCalifRiesgoControlador;
	}

	public void setRoCalifRiesgoControlador(
			RoCalifRiesgo roCalifRiesgoControlador) {
		this.roCalifRiesgoControlador = roCalifRiesgoControlador;
	}

	public ParamConsecuenciaImpacto getRoParamConsecuenciaImpacto() {
		return roParamConsecuenciaImpacto;
	}

	public void setRoParamConsecuenciaImpacto(
			ParamConsecuenciaImpacto roParamConsecuenciaImpacto) {
		this.roParamConsecuenciaImpacto = roParamConsecuenciaImpacto;
	}

	public ParamProbabilidadRiesgo getRoParamProbabilidadRiesgo() {
		return roParamProbabilidadRiesgo;
	}

	public void setRoParamProbabilidadRiesgo(
			ParamProbabilidadRiesgo roParamProbabilidadRiesgo) {
		this.roParamProbabilidadRiesgo = roParamProbabilidadRiesgo;
	}

	public List<RoAriesgo> getAriesgoAuxiliares() {
		return ariesgoAuxiliares;
	}

	public void setAriesgoAuxiliares(List<RoAriesgo> ariesgoAuxiliares) {
		this.ariesgoAuxiliares = ariesgoAuxiliares;
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
