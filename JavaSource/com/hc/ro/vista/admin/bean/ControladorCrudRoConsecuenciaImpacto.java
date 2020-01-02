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
import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.RoConsecuenciaImpacto;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.negocio.ServicioParamConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioRoConsecuenciaImpacto;
import com.hc.ro.negocio.ServicioRoNegocio;
//import com.hc.ro.utils.SiguienteValorNumero;

@ManagedBean
@ViewScoped
public class ControladorCrudRoConsecuenciaImpacto {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudConsecuenciaImpacto.jsf";
	// TipoAfecta
	@EJB
	ServicioRoConsecuenciaImpacto servicioRoConsecuenciaImpacto;
	@EJB
	ServicioRoNegocio servicioNegocio;
	@EJB
	ServicioParamConsecuenciaImpacto servicioParamConsecuenciaImpacto;

	// VARIABLES
	private RoConsecuenciaImpacto roConsecuenciaVista;
	private RoConsecuenciaImpacto roConsecuenciaControlador;
	private List<RoConsecuenciaImpacto> consecuenciaImpactoTodos;
	private List<RoNegocio> negocioTodos;
	private List<ParamConsecuenciaImpacto> paramConsecuenciaImpactoTodos;
	private ParamConsecuenciaImpacto paramConsecuenciaImpactoVista;
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
	
	
	//private SiguienteValorNumero siguienteNumero;

	//
	public ControladorCrudRoConsecuenciaImpacto() {
		super();
		roConsecuenciaControlador = new RoConsecuenciaImpacto();
		roConsecuenciaVista = new RoConsecuenciaImpacto();
		consecuenciaImpactoTodos = new ArrayList<RoConsecuenciaImpacto>();
		paramConsecuenciaImpactoTodos = new ArrayList<ParamConsecuenciaImpacto>();
		negocioTodos = new ArrayList<RoNegocio>();
		nombreSeleccionado = new String();
		paramConsecuenciaImpactoVista = new ParamConsecuenciaImpacto();
		
		//SiguienteValorNumero siguienteNumero =new SiguienteValorNumero();
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
		consecuenciaImpactoTodos = servicioRoConsecuenciaImpacto.buscarTodos();
		paramConsecuenciaImpactoTodos = servicioParamConsecuenciaImpacto
				.buscarTodos();
		negocioTodos = servicioNegocio.buscarTodos();
		btnAnadir = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlConsecuencia = false;
		nombrarPconi();
	}

	public void nombrarPconi() {
		try {
			for (RoConsecuenciaImpacto item : consecuenciaImpactoTodos) {
				item.setNombrePconi(servicioParamConsecuenciaImpacto
						.buscarConsecuenciaImpactoPorNumero(
								item.getCodigoPconi()).getNombrePconi());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	// METODOS

	public void cambiarNegocioTabla() {
		if (idNegocioSeleccionadoTabla != 0) {
			consecuenciaImpactoTodos = servicioRoConsecuenciaImpacto
					.buscarPorNegocio(idNegocioSeleccionadoTabla);
			nombrarPconi();
		} else {
			consecuenciaImpactoTodos = servicioRoConsecuenciaImpacto
					.buscarTodos();
			nombrarPconi();
		}

	}

	public void cambiarNegocioPanel() {
		limitarListas();
	}

	public void guardarTipoAfecta() {

		roConsecuenciaVista.setRoNegocio(servicioNegocio
				.buscarPorId(idNegocioSeleccionado));
		roConsecuenciaVista.setCodigoPconi(paramConsecuenciaImpactoVista
				.getNumeroPconi());
		if (roConsecuenciaVista.getDESDE_cons() < roConsecuenciaVista
				.getHASTA_cons()) {
			if (tipoGuardar) {

				if (servicioRoConsecuenciaImpacto
						.existeConsecuenciaImpactoPorPconiNego(
								roConsecuenciaVista.getCodigoPconi(),
								idNegocioSeleccionado)) {
					servicioRoConsecuenciaImpacto.insertar(roConsecuenciaVista);
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(
							null,
							new FacesMessage("Consecuencia - Impacto Añadida",
									"La Consecuencia - Impacto ha sido Añadida con Exito"));
					exitoGuardar();
				} else {
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"El Parametro Consecuencia - Impacto no se debe repetir en el mismo Negocio"));
				}
			} else {

				if (servicioRoConsecuenciaImpacto.existePorPconiNegoEx(
						roConsecuenciaVista.getCodigoPconi(),
						roConsecuenciaVista.getCODIGO_cons(),
						idNegocioSeleccionado)) {

					try {
						servicioRoConsecuenciaImpacto
								.actualizar(roConsecuenciaVista);
						FacesContext context = FacesContext
								.getCurrentInstance();

						context.addMessage(
								null,
								new FacesMessage(
										"Consecuencia - Impacto Añadida",
										"La Consecuencia - Impacto ha sido Añadida con Exito"));
						try {
							roConsecuenciaControlador = servicioRoConsecuenciaImpacto
									.buscarConsecuenciaImpactoPorPconiNego(
											idNegocioSeleccionado,
											roConsecuenciaVista
													.getCodigoPconi() + 1);
							
							//siguienteNumero.siguienteValorDouble(roConsecuenciaVista.getHASTA_cons());
							//System.out.println("numero esss"+siguienteNumero.siguienteValorDouble(roConsecuenciaVista.getHASTA_cons()));
							roConsecuenciaControlador.setDESDE_cons(roConsecuenciaVista.getHASTA_cons());
							servicioRoConsecuenciaImpacto
									.actualizar(roConsecuenciaControlador);
						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}

						exitoGuardar();
					} catch (Exception e) {
						e.printStackTrace();
						FacesContext context = FacesContext
								.getCurrentInstance();

						context.addMessage(null, new FacesMessage(
								"Error al Actualizar", "Error al Actualizar"));
					}
				} else {
					FacesContext context = FacesContext.getCurrentInstance();

					context.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error al guardar:",
									"El Parametro Consecuencia - Impacto no se debe repetir en el mismo Negocio"));
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

		roConsecuenciaVista = new RoConsecuenciaImpacto();
		idNegocioSeleccionado = negocioTodos.get(0).getCodigoNego();
		consecuenciaImpactoTodos = servicioRoConsecuenciaImpacto.buscarTodos();
		nombrarPconi();
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlConsecuencia = false;
	}

	public void eliminarTipoAfecta() {

		try {
			servicioRoConsecuenciaImpacto.eliminar(roConsecuenciaVista);
			roConsecuenciaVista = new RoConsecuenciaImpacto();
			consecuenciaImpactoTodos = servicioRoConsecuenciaImpacto
					.buscarTodos();
			nombrarPconi();
			idNegocioSeleccionado = 1;
			btnGuardar = true;
			btnCancelar = true;
			pnlConsecuencia = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Consecuencia - Impacto Eliminado",
					"La Consecuencia - Impacto ha sido Eliminada con Exito"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"Consecuencia - Impacto en Uso"));
		}
	}

	public void nuevoConsecuenciaImpactoVista() {
		tipoGuardar = true;
		roConsecuenciaVista = new RoConsecuenciaImpacto();

		try {
			idNegocioSeleccionado = negocioTodos.get(0).getCodigoNego();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		limitarListas();
		btnGuardar = false;
		btnCancelar = false;
		pnlConsecuencia = true;
	}

	public void limitarListas() {
		List<RoConsecuenciaImpacto> consecuenciaImpactosAux;
		consecuenciaImpactosAux = new ArrayList<RoConsecuenciaImpacto>();
		consecuenciaImpactosAux = servicioRoConsecuenciaImpacto
				.buscarPorNegocio(idNegocioSeleccionado);
		try {
			paramConsecuenciaImpactoVista = servicioParamConsecuenciaImpacto
					.buscarConsecuenciaImpactoPorNumero(consecuenciaImpactosAux
							.size() + 1);
			if (paramConsecuenciaImpactoVista.getNumeroPconi() > 1) {
				roConsecuenciaVista
						.setDESDE_cons(servicioRoConsecuenciaImpacto
								.buscarConsecuenciaImpactoPorPconiNego(
										idNegocioSeleccionado,
										consecuenciaImpactosAux.size())
								.getHASTA_cons());
			} else {
				roConsecuenciaVista.setDESDE_cons(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void cancelar() {
		nuevoConsecuenciaImpactoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlConsecuencia = false;
		RequestContext.getCurrentInstance().update("formTipoAfecta");
	}

	public void editarTipoAfectaVista() {

		tipoGuardar = false;
		paramConsecuenciaImpactoVista = servicioParamConsecuenciaImpacto
				.buscarConsecuenciaImpactoPorNumero(roConsecuenciaVista
						.getCodigoPconi());
		idNegocioSeleccionado = roConsecuenciaVista.getRoNegocio()
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

	public RoConsecuenciaImpacto getRoConsecuenciaVista() {
		return roConsecuenciaVista;
	}

	public void setRoConsecuenciaVista(RoConsecuenciaImpacto roConsecuenciaVista) {
		this.roConsecuenciaVista = roConsecuenciaVista;
	}

	public RoConsecuenciaImpacto getRoConsecuenciaControlador() {
		return roConsecuenciaControlador;
	}

	public void setRoConsecuenciaControlador(
			RoConsecuenciaImpacto roConsecuenciaControlador) {
		this.roConsecuenciaControlador = roConsecuenciaControlador;
	}

	public List<RoConsecuenciaImpacto> getConsecuenciaImpactoTodos() {
		return consecuenciaImpactoTodos;
	}

	public void setConsecuenciaImpactoTodos(
			List<RoConsecuenciaImpacto> consecuenciaImpactoTodos) {
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

	public List<ParamConsecuenciaImpacto> getParamConsecuenciaImpactoTodos() {
		return paramConsecuenciaImpactoTodos;
	}

	public void setParamConsecuenciaImpactoTodos(
			List<ParamConsecuenciaImpacto> paramConsecuenciaImpactoTodos) {
		this.paramConsecuenciaImpactoTodos = paramConsecuenciaImpactoTodos;
	}

	public ParamConsecuenciaImpacto getParamConsecuenciaImpactoVista() {
		return paramConsecuenciaImpactoVista;
	}

	public void setParamConsecuenciaImpactoVista(
			ParamConsecuenciaImpacto paramConsecuenciaImpactoVista) {
		this.paramConsecuenciaImpactoVista = paramConsecuenciaImpactoVista;
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
