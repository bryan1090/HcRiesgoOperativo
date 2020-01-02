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

import org.primefaces.context.RequestContext;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoProbabilidadEvento;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.negocio.ServicioParamProbabilidadRiesgo;
import com.hc.ro.negocio.ServicioRoProbabilidadEvento;
import com.hc.ro.negocio.ServicioRoNegocio;

@ManagedBean
@ViewScoped
public class ControladorCrudRoProbabilidadEvento {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudProbabilidadEvento.jsf";

	// TipoAfecta
	@EJB
	ServicioRoProbabilidadEvento servicioRoProbabilidadEvento;
	@EJB
	ServicioRoNegocio servicioNegocio;
	@EJB
	ServicioParamProbabilidadRiesgo servicioParamProbabilidadRiesgo;

	// VARIABLES
	private RoProbabilidadEvento roProbabilidadVista;
	private RoProbabilidadEvento roConsecuenciaControlador;
	private List<RoProbabilidadEvento> consecuenciaImpactoTodos;
	private List<RoNegocio> negocioTodos;
	private List<ParamProbabilidadRiesgo> paramProbabilidadRiesgoTodos;
	private ParamProbabilidadRiesgo paramProbabilidadRiesgoVista;
	private int idConsecuenciaSeleccionado;
	private int idNegocioSeleccionadoTabla;
	private int idNegocioSeleccionado;
	private boolean tipoGuardar;
	private String nombreSeleccionado;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlConsecuencia;

	//
	public ControladorCrudRoProbabilidadEvento() {
		super();
		roConsecuenciaControlador = new RoProbabilidadEvento();
		roProbabilidadVista = new RoProbabilidadEvento();
		consecuenciaImpactoTodos = new ArrayList<RoProbabilidadEvento>();
		paramProbabilidadRiesgoTodos = new ArrayList<ParamProbabilidadRiesgo>();
		negocioTodos = new ArrayList<RoNegocio>();
		nombreSeleccionado = new String();
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
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
		consecuenciaImpactoTodos = servicioRoProbabilidadEvento.buscarTodos();
		paramProbabilidadRiesgoTodos = servicioParamProbabilidadRiesgo
				.buscarTodos();
		negocioTodos = servicioNegocio.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlConsecuencia = false;
		nombrarPprr();
	}

	public void nombrarPprr() {
		try {
			for (RoProbabilidadEvento item : consecuenciaImpactoTodos) {
				item.setNombrePprr(servicioParamProbabilidadRiesgo
						.buscarProbabilidadRiesgoPorNumero(item.getCodigoPprr())
						.getNombrePprr());
				item.setLetraPprr(servicioParamProbabilidadRiesgo
						.buscarProbabilidadRiesgoPorNumero(item.getCodigoPprr())
						.getLetraPprr());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// METODOS

	public void cambiarNegocioTabla() {
		if (idNegocioSeleccionadoTabla != 0) {
			consecuenciaImpactoTodos = servicioRoProbabilidadEvento
					.buscarPorNegocio(idNegocioSeleccionadoTabla);
			nombrarPprr();
		} else {
			consecuenciaImpactoTodos = servicioRoProbabilidadEvento
					.buscarTodos();
			nombrarPprr();
		}

	}

	public void cambiarNegocioPanel() {
		limitarListas();
	}

	public void guardarTipoAfecta() {

		roProbabilidadVista.setRoNegocio(servicioNegocio
				.buscarPorId(idNegocioSeleccionado));
		roProbabilidadVista.setCodigoPprr(paramProbabilidadRiesgoVista
				.getNumeroPprr());
		if (roProbabilidadVista.getDesdeProb() < roProbabilidadVista
				.getHastaProb()) {
			if (tipoGuardar) {

				if (servicioRoProbabilidadEvento
						.existeProbabilidadEventoPorPprrNego(
								roProbabilidadVista.getCodigoPprr(),
								idNegocioSeleccionado)) {
					servicioRoProbabilidadEvento.insertar(roProbabilidadVista);
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(null, new FacesMessage(
							"Registro Añadido",
							"El Registro ha sido Añadido con éxito"));
					exitoGuardar();
				} else {
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"El Parametro Probabilidad de Ocurrencia no se debe repetir en el mismo Negocio"));
				}
			} else {

				if (servicioRoProbabilidadEvento.existePorPprrNegoEx(
						roProbabilidadVista.getCodigoPprr(),
						roProbabilidadVista.getCODIGO_prob(),
						idNegocioSeleccionado)) {

					try {
						servicioRoProbabilidadEvento
								.actualizar(roProbabilidadVista);
						FacesContext context = FacesContext
								.getCurrentInstance();

						context.addMessage(
								null,
								new FacesMessage(
										"Probabilidad de Ocurrencia Añadida",
										"La Probabilidad de Ocurrencia ha sido Añadida con Exito"));
						try {
							roConsecuenciaControlador = servicioRoProbabilidadEvento
									.buscarProbabilidadEventoPorPprrNego(
											idNegocioSeleccionado,
											roProbabilidadVista.getCodigoPprr() + 1);
							roConsecuenciaControlador
									.setDesdeProb(roProbabilidadVista
											.getHastaProb());
							servicioRoProbabilidadEvento
									.actualizar(roConsecuenciaControlador);
						} catch (Exception e) {
							// TODO: handle exception
						}

						exitoGuardar();
					} catch (Exception e) {
						FacesContext context = FacesContext
								.getCurrentInstance();

						context.addMessage(null, new FacesMessage(
								"Error al Actualizar", "Error al Actualizar"));
					}
				} else {
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"El Parametro Probabilidad de Ocurrencia no se debe repetir en el mismo Negocio"));
				}
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al guardar:",
					"El Campo Hasta debe ser mayor que el campo Desde"));
		}

	}

	public void exitoGuardar() {

		roProbabilidadVista = new RoProbabilidadEvento();
		idNegocioSeleccionado = negocioTodos.get(0).getCodigoNego();
		consecuenciaImpactoTodos = servicioRoProbabilidadEvento.buscarTodos();
		nombrarPprr();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlConsecuencia = false;
	}

	public void eliminarTipoAfecta() {

		try {
			servicioRoProbabilidadEvento.eliminar(roProbabilidadVista);
			roProbabilidadVista = new RoProbabilidadEvento();
			consecuenciaImpactoTodos = servicioRoProbabilidadEvento
					.buscarTodos();
			nombrarPprr();
			idNegocioSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlConsecuencia = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage("Probabilidad de Ocurrencia Eliminada",
							"La Probabilidad de Ocurrencia ha sido Eliminada con Exito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Probabilidad de Ocurrencia en Uso"));
		}
	}

	public void nuevoProbabilidadEventoVista() {
		tipoGuardar = true;
		roProbabilidadVista = new RoProbabilidadEvento();

		try {
			idNegocioSeleccionado = negocioTodos.get(0).getCodigoNego();
		} catch (Exception e) {
			// TODO: handle exception
		}
		limitarListas();
		btnGuardar = false;
		btnCancelar = false;
		pnlConsecuencia = true;
	}

	public void limitarListas() {
		List<RoProbabilidadEvento> consecuenciaImpactosAux;
		consecuenciaImpactosAux = new ArrayList<RoProbabilidadEvento>();
		consecuenciaImpactosAux = servicioRoProbabilidadEvento
				.buscarPorNegocio(idNegocioSeleccionado);
		try {
			paramProbabilidadRiesgoVista = servicioParamProbabilidadRiesgo
					.buscarProbabilidadRiesgoPorNumero(consecuenciaImpactosAux
							.size() + 1);
			if (paramProbabilidadRiesgoVista.getNumeroPprr() > 1) {
				roProbabilidadVista.setDesdeProb(servicioRoProbabilidadEvento
						.buscarProbabilidadEventoPorPprrNego(
								idNegocioSeleccionado,
								consecuenciaImpactosAux.size()).getHastaProb());
			} else {
				roProbabilidadVista.setDesdeProb(0);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void cancelar() {
		nuevoProbabilidadEventoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlConsecuencia = false;
		RequestContext.getCurrentInstance().update("formTipoAfecta");
	}

	public void editarTipoAfectaVista() {

		tipoGuardar = false;
		paramProbabilidadRiesgoVista = servicioParamProbabilidadRiesgo
				.buscarProbabilidadRiesgoPorNumero(roProbabilidadVista
						.getCodigoPprr());
		idNegocioSeleccionado = roProbabilidadVista.getRoNegocio()
				.getCodigoNego();

		btnGuardar = false;
		btnCancelar = false;
		pnlConsecuencia = true;
		btnAnadir = true;

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

	public int getIdTipoAfectaSeleccionado() {
		return idConsecuenciaSeleccionado;
	}

	public void setIdTipoAfectaSeleccionado(int idTipoAfectaSeleccionado) {
		this.idConsecuenciaSeleccionado = idTipoAfectaSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public int getIdEstadoSeleccionado() {
		return idNegocioSeleccionado;
	}

	public RoProbabilidadEvento getRoConsecuenciaVista() {
		return roProbabilidadVista;
	}

	public void setRoConsecuenciaVista(RoProbabilidadEvento roProbabilidadVista) {
		this.roProbabilidadVista = roProbabilidadVista;
	}

	public RoProbabilidadEvento getRoConsecuenciaControlador() {
		return roConsecuenciaControlador;
	}

	public void setRoConsecuenciaControlador(
			RoProbabilidadEvento roConsecuenciaControlador) {
		this.roConsecuenciaControlador = roConsecuenciaControlador;
	}

	public List<RoProbabilidadEvento> getProbabilidadEventoTodos() {
		return consecuenciaImpactoTodos;
	}

	public void setProbabilidadEventoTodos(
			List<RoProbabilidadEvento> consecuenciaImpactoTodos) {
		this.consecuenciaImpactoTodos = consecuenciaImpactoTodos;
	}

	public List<RoNegocio> getNegocioTodos() {
		return negocioTodos;
	}

	public void setNegocioTodos(List<RoNegocio> negocioTodos) {
		this.negocioTodos = negocioTodos;
	}

	public int getIdConsecuenciaSeleccionado() {
		return idConsecuenciaSeleccionado;
	}

	public void setIdConsecuenciaSeleccionado(int idConsecuenciaSeleccionado) {
		this.idConsecuenciaSeleccionado = idConsecuenciaSeleccionado;
	}

	public int getIdNegocioSeleccionado() {
		return idNegocioSeleccionado;
	}

	public void setIdNegocioSeleccionado(int idNegocioSeleccionado) {
		this.idNegocioSeleccionado = idNegocioSeleccionado;
	}

	public void setIdEstadoSeleccionado(int idEstadoSeleccionado) {
		this.idNegocioSeleccionado = idEstadoSeleccionado;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlTipoAfecta() {
		return pnlConsecuencia;
	}

	public void setPnlTipoAfecta(boolean pnlTipoAfecta) {
		this.pnlConsecuencia = pnlTipoAfecta;
	}

	public int getIdNegocioSeleccionadoTabla() {
		return idNegocioSeleccionadoTabla;
	}

	public void setIdNegocioSeleccionadoTabla(int idNegocioSeleccionadoTabla) {
		this.idNegocioSeleccionadoTabla = idNegocioSeleccionadoTabla;
	}

	public boolean isPnlConsecuencia() {
		return pnlConsecuencia;
	}

	public void setPnlConsecuencia(boolean pnlConsecuencia) {
		this.pnlConsecuencia = pnlConsecuencia;
	}

	public List<ParamProbabilidadRiesgo> getParamProbabilidadRiesgoTodos() {
		return paramProbabilidadRiesgoTodos;
	}

	public void setParamProbabilidadRiesgoTodos(
			List<ParamProbabilidadRiesgo> paramProbabilidadRiesgoTodos) {
		this.paramProbabilidadRiesgoTodos = paramProbabilidadRiesgoTodos;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoVista() {
		return paramProbabilidadRiesgoVista;
	}

	public void setParamProbabilidadRiesgoVista(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoVista) {
		this.paramProbabilidadRiesgoVista = paramProbabilidadRiesgoVista;
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

	public RoProbabilidadEvento getRoProbabilidadVista() {
		return roProbabilidadVista;
	}

	public void setRoProbabilidadVista(RoProbabilidadEvento roProbabilidadVista) {
		this.roProbabilidadVista = roProbabilidadVista;
	}

	public List<RoProbabilidadEvento> getConsecuenciaImpactoTodos() {
		return consecuenciaImpactoTodos;
	}

	public void setConsecuenciaImpactoTodos(
			List<RoProbabilidadEvento> consecuenciaImpactoTodos) {
		this.consecuenciaImpactoTodos = consecuenciaImpactoTodos;
	}

}
