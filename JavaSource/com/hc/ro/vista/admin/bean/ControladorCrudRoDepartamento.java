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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.GenEstado;
import com.hc.ro.modelo.GenNivelArbol;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoRespDepa;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.negocio.ServicioGenEstado;
import com.hc.ro.negocio.ServicioGenNivelArbol;
import com.hc.ro.negocio.ServicioRoDepartamento;
import com.hc.ro.negocio.ServicioRoRespDepa;
import com.hc.ro.negocio.ServicioRoResponsable;

@ManagedBean
@ViewScoped
public class ControladorCrudRoDepartamento {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;
	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;
	public final static String nombrePagina = "/paginas/CrudDepartamento.jsf";
	// Departamento
	@EJB
	ServicioRoDepartamento servicioRoDepartamento;
	@EJB
	ServicioGenEstado servicioGenEstado;
	@EJB
	ServicioGenNivelArbol servicioGenNivelArbol;
	@EJB
	ServicioRoResponsable servicioRoResponsable;
	@EJB
	ServicioRoRespDepa servicioRoRespDepa;

	// VARIABLES
	private RoDepartamento roDepartamentoVista;
	private RoDepartamento roDepartamentoControlador;
	private List<RoDepartamento> DepartamentosTodos;
	private List<RoDepartamento> subDepartamentosTodos;
	private List<GenNivelArbol> nivelesArbolTodos;
	private List<GenEstado> estadosTodos;
	private List<RoResponsable> responsablesTodos;
	private List<RoRespDepa> responsablesDepa;
	private int idDepartamentoSeleccionado;
	private int idEstadoSeleccionado;
	private int idTipoDepartamentoSeleccionado;
	private int idNivelArbolSeleccionado;
	private boolean tipoGuardar;
	private TreeNode arbolDepartamentoVista;
	private TreeNode arbolDepartamentoSeleccionado;
	private boolean estaExpandido;
	
	private boolean expandirTodoArbol;
	
	private String nombreSeleccionado;
	private List<TreeNode> nodosTodos;
	// private String numeroPadre;
	// private String numeroHijo;
	// botones,cajas,dialogos
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnGuardar;
	private boolean pnlDepartamento;
	private boolean txtNumero;

	private List<String> responsablesSeleccionados;

	//
	public ControladorCrudRoDepartamento() {
		super();
		roDepartamentoControlador = new RoDepartamento();
		roDepartamentoVista = new RoDepartamento();
		DepartamentosTodos = new ArrayList<RoDepartamento>();
		subDepartamentosTodos = new ArrayList<RoDepartamento>();
		nivelesArbolTodos = new ArrayList<GenNivelArbol>();
		estadosTodos = new ArrayList<GenEstado>();
		arbolDepartamentoVista = new DefaultTreeNode("Root", null);
		arbolDepartamentoSeleccionado = new DefaultTreeNode();
		nombreSeleccionado = new String();
		// numeroPadre = new String();
		responsablesDepa = new ArrayList<RoRespDepa>();
		responsablesSeleccionados = new ArrayList<String>();
		responsablesTodos = new ArrayList<RoResponsable>();
	}

	@PostConstruct
	public void PostControladorCrudRoDepartamento() {
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
		responsablesTodos = servicioRoResponsable.buscarTodos();
		DepartamentosTodos = servicioRoDepartamento.buscarTodos();
		nivelesArbolTodos = servicioGenNivelArbol.buscarTodos();
		estadosTodos = servicioGenEstado.buscarTodos();
		estaExpandido = false;
		btnAnadir = true;
		btnCancelar = true;
		btnGuardar = true;
		pnlDepartamento = false;
		txtNumero = true;
		llenarArbol();
		expandirTodoArbol=false;
	}

	// METODOS

	public void guardarResponsablesDepa() {
		roDepartamentoVista = servicioRoDepartamento
				.buscarDepartamentoPorNombre(roDepartamentoVista
						.getNombreDept());
		List<RoRespDepa> iterable = servicioRoRespDepa
				.buscarRespDepaPorDepa(roDepartamentoVista.getNombreDept());
		for (RoRespDepa item : iterable) {
			servicioRoRespDepa.eliminar(item);
		}

		setResponsablesDepa(new ArrayList<RoRespDepa>());
		for (String item : responsablesSeleccionados) {

			RoRespDepa respDepas = new RoRespDepa();
			respDepas.setRoDepartamento(roDepartamentoVista);
			respDepas.setRoResponsable(servicioRoResponsable
					.buscarPorId(Integer.parseInt(item)));
			responsablesDepa.add(respDepas);
		}

		for (RoRespDepa item1 : responsablesDepa) {

			servicioRoRespDepa.insertar(item1);
		}
		responsablesDepa = new ArrayList<RoRespDepa>();
	}

	/**
	 * Guarda el Departamento, en caso de que sea uno nuevo lo inserta de lo
	 * contrario lo actualiza.
	 */

	public void recursivaArbol(List<RoDepartamento> Departamentos,
			TreeNode padre) {
		if (Departamentos.isEmpty() == false) {
			RoDepartamento Departamento = new RoDepartamento();
			for (int i = 0; i < Departamentos.size(); i++) {
				Departamento = Departamentos.get(i);
				nodosTodos.add(new DefaultTreeNode(
						Departamento.getNombreDept(), padre));
				List<RoDepartamento> DepartamentosAux = new ArrayList<RoDepartamento>();
				DepartamentosAux = servicioRoDepartamento
						.buscarDepartamentoPorPadre(Departamento
								.getNombreDept());
				if (DepartamentosAux != null) {
					if (nodosTodos.get(nodosTodos.size() - 1).getData()
							.equals(nombreSeleccionado)) {
						nodosTodos.get(nodosTodos.size() - 1).setSelected(true);
						arbolDepartamentoSeleccionado = nodosTodos
								.get(nodosTodos.size() - 1);

						nodosTodos.get(nodosTodos.size() - 1).setExpanded(
								estaExpandido);
					}
					recursivaArbol(DepartamentosAux,
							nodosTodos.get(nodosTodos.size() - 1));
				}
			}
			
		}
	

	}

	
	
	
	
	
	public void seleccionarDepartamento() {

		roDepartamentoVista = new RoDepartamento();

		/*
		 * roDepartamentoVista = servicioRoDepartamento
		 * .buscarDepartamentoPorNombre(arbolDepartamentoSeleccionado.getData()
		 * .toString()); if(roDepartamentoVista.equals(null)){
		 * roDepartamentoVista=new RoDepartamento(); }
		 */

		subDepartamentosTodos = servicioRoDepartamento
				.buscarDepartamentoPorPadre(arbolDepartamentoSeleccionado
						.getData().toString());

		nombreSeleccionado = arbolDepartamentoSeleccionado.getData().toString();

		// if (nombreSeleccionado.equals("Departamentos")) {
		// numeroPadre = "";
		// } else {
		// numeroPadre = servicioRoDepartamento.buscarDepartamentoPorNombre(
		// nombreSeleccionado).getNumeroDept();
		// }
		btnAnadir = false;
		pnlDepartamento = false;

	}

	public void llenarArbol() {
		DepartamentosTodos = servicioRoDepartamento.buscarTodos();
		arbolDepartamentoVista = new DefaultTreeNode("Root", null);
		TreeNode arbolVirtual = new DefaultTreeNode("Departamentos",
				arbolDepartamentoVista);
		if (nombreSeleccionado.equals("Departamentos")) {
			arbolVirtual.setExpanded(estaExpandido);
			arbolVirtual.setSelected(true);
		}
		nodosTodos = new ArrayList<TreeNode>();
		recursivaArbol(
				servicioRoDepartamento
						.buscarDepartamentoPorPadre("Departamentos"),
				arbolVirtual);

		
		
		
		 
		
		
		
	}
	
	

	
	public void guardarDepartamento() {
		// roDepartamentoVista.setNumeroDept(numeroPadre.concat(numeroHijo));
		roDepartamentoVista.setGenEstado(servicioGenEstado
				.buscarPorId(idEstadoSeleccionado));
		roDepartamentoVista.setGenNivelArbol(servicioGenNivelArbol
				.buscarPorId(idNivelArbolSeleccionado));
		roDepartamentoVista.setAncestroDept(arbolDepartamentoSeleccionado
				.getData().toString());
		if (tipoGuardar) {
			if (servicioRoDepartamento
					.existeDepartamentoPorNombre(roDepartamentoVista
							.getNombreDept())) {
				servicioRoDepartamento.insertar(roDepartamentoVista);
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Departamento Añadido",
						"El Departamento ha sido Añadido con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre y número de Departamento no se deben repetir"));
			}
		} else {

			if (servicioRoDepartamento.existeDepartamentoPorNumeroEx(
					roDepartamentoVista.getNombreDept(),
					roDepartamentoVista.getCodigoDept())) {

				servicioRoDepartamento.actualizar(roDepartamentoVista);
				List<RoDepartamento> listaDepartamentosHijos = servicioRoDepartamento
						.buscarDepartamentoPorPadre(roDepartamentoControlador
								.getNombreDept());
				if (listaDepartamentosHijos.size() != 0) {

					for (RoDepartamento hijo : listaDepartamentosHijos) {
						hijo.setAncestroDept(roDepartamentoVista
								.getNombreDept());
						servicioRoDepartamento.actualizar(hijo);
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						"Departamento Actualizado",
						"El Departamento ha sido Actualizado con éxito"));

				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al guardar:",
								"El nombre y número de Departamento no se deben repetir"));
				roDepartamentoVista.setNombreDept(roDepartamentoControlador
						.getNombreDept());
				// roDepartamentoVista.setNumeroDept(roDepartamentoControlador
				// .getNumeroDept());
				// numeroHijo = roDepartamentoVista.getNombreDept().substring(
				// numeroPadre.length());
			}

		}

	}

	public void exitoGuardar() {
		guardarResponsablesDepa();
		estaExpandido = arbolDepartamentoSeleccionado.isExpanded();
		llenarArbol();
		roDepartamentoVista = new RoDepartamento();
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idTipoDepartamentoSeleccionado = 1;
		TreeNode arbolAux;
		arbolAux = arbolDepartamentoSeleccionado;
		subDepartamentosTodos = servicioRoDepartamento
				.buscarDepartamentoPorPadre(arbolDepartamentoSeleccionado
						.getData().toString());
		while (!arbolAux.getData().toString().equals("Departamentos")) {
			arbolAux = arbolAux.getParent();
			arbolAux.setExpanded(true);
		}
		btnAnadir = false;
		btnGuardar = true;
		btnCancelar = true;
		pnlDepartamento = false;
		
		
	}

	public void eliminarDepartamento() {
		if (servicioRoDepartamento.buscarDepartamentoPorPadre(
				roDepartamentoVista.getNombreDept()).size() == 0) {

			responsablesDepa = new ArrayList<RoRespDepa>();
			responsablesDepa = servicioRoRespDepa
					.buscarRespDepaPorDepa(roDepartamentoVista.getNombreDept());
			for (RoRespDepa item : responsablesDepa) {
				servicioRoRespDepa.eliminar(item);
			}
			roDepartamentoVista = servicioRoDepartamento
					.buscarPorId(roDepartamentoVista.getCodigoDept());
			try {
				servicioRoDepartamento.eliminar(roDepartamentoVista);
				roDepartamentoVista = new RoDepartamento();
				subDepartamentosTodos = servicioRoDepartamento
						.buscarDepartamentoPorPadre(arbolDepartamentoSeleccionado
								.getData().toString());
				estaExpandido = arbolDepartamentoSeleccionado.isExpanded();
				llenarArbol();
				idEstadoSeleccionado = 1;
				idNivelArbolSeleccionado = 1;
				idTipoDepartamentoSeleccionado = 1;
				TreeNode arbolAux;
				arbolAux = arbolDepartamentoSeleccionado;

				while (!arbolAux.getData().toString().equals("Departamentos")) {
					arbolAux = arbolAux.getParent();
					arbolAux.setExpanded(true);
				}
				btnGuardar = true;
				btnCancelar = true;
				pnlDepartamento = false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						"Departamento Eliminado",
						"El Departamento ha sido Eliminado con éxito"));
			} catch (Exception e) {
				for (RoRespDepa item : responsablesDepa) {
					servicioRoRespDepa.insertar(item);
				}

				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
						"No se puede eliminar un Departamento en uso"));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"No se puede eliminar un Departamento que tenga sub-Departamentos"));
		}
	}

	public void nuevoDepartamentoVista() {
		responsablesSeleccionados = new ArrayList<String>();
		tipoGuardar = true;
		roDepartamentoVista = new RoDepartamento();
		idEstadoSeleccionado = 1;
		idEstadoSeleccionado = 1;
		idNivelArbolSeleccionado = 1;
		idTipoDepartamentoSeleccionado = 1;
		btnGuardar = false;
		btnCancelar = false;
		pnlDepartamento = true;
		txtNumero = false;
	}

	public void cancelar() {
		nuevoDepartamentoVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlDepartamento = false;
		RequestContext.getCurrentInstance().update("formDepartamento");
	}

	public void editarDepartamentoVista() {

		// numeroHijo = roDepartamentoVista.getNumeroDept().substring(
		// numeroPadre.length());
		roDepartamentoControlador.setNombreDept(roDepartamentoVista
				.getNombreDept());
		// roDepartamentoControlador.setNumeroDept(roDepartamentoVista
		// .getNumeroDept());

		tipoGuardar = false;

		responsablesDepa = servicioRoRespDepa
				.buscarRespDepaPorDepa(roDepartamentoVista.getNombreDept());
		responsablesSeleccionados = new ArrayList<String>();
		for (RoRespDepa item : responsablesDepa) {
			responsablesSeleccionados.add(Integer.toString(item
					.getRoResponsable().getCodigoResp()));
		}

		idEstadoSeleccionado = (int) roDepartamentoVista.getGenEstado()
				.getCodigoEsta();
		idNivelArbolSeleccionado = roDepartamentoVista.getGenNivelArbol()
				.getCodigoGniv();

		btnGuardar = false;
		btnCancelar = false;
		pnlDepartamento = true;
		btnAnadir = true;
		if (servicioRoDepartamento.buscarDepartamentoPorPadre(
				roDepartamentoVista.getNombreDept()).size() == 0) {
			txtNumero = false;
		} else {
			txtNumero = true;
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

	public RoDepartamento getRoDepartamentoVista() {
		return roDepartamentoVista;
	}

	public void setRoDepartamentoVista(RoDepartamento roDepartamentoVista) {
		this.roDepartamentoVista = roDepartamentoVista;
	}

	public RoDepartamento getRoDepartamentoControlador() {
		return roDepartamentoControlador;
	}

	public void setRoDepartamentoControlador(
			RoDepartamento roDepartamentoControlador) {
		this.roDepartamentoControlador = roDepartamentoControlador;
	}

	public List<RoDepartamento> getDepartamentosTodos() {
		return DepartamentosTodos;
	}

	public void setDepartamentosTodos(List<RoDepartamento> DepartamentosTodos) {
		this.DepartamentosTodos = DepartamentosTodos;
	}

	public int getIdDepartamentoSeleccionado() {
		return idDepartamentoSeleccionado;
	}

	public void setIdDepartamentoSeleccionado(int idDepartamentoSeleccionado) {
		this.idDepartamentoSeleccionado = idDepartamentoSeleccionado;
	}

	public boolean isTipoGuardar() {
		return tipoGuardar;
	}

	public void setTipoGuardar(boolean tipoGuardar) {
		this.tipoGuardar = tipoGuardar;
	}

	public TreeNode getArbolDepartamentoVista() {
		return arbolDepartamentoVista;
	}

	public void setArbolDepartamentoVista(TreeNode arbolDepartamentoVista) {
		this.arbolDepartamentoVista = arbolDepartamentoVista;
	}

	public TreeNode getArbolDepartamentoSeleccionado() {
		return arbolDepartamentoSeleccionado;
	}

	public void setArbolDepartamentoSeleccionado(
			TreeNode arbolDepartamentoSeleccionado) {
		this.arbolDepartamentoSeleccionado = arbolDepartamentoSeleccionado;
	}

	public List<GenNivelArbol> getNivelesArbolTodos() {
		return nivelesArbolTodos;
	}

	public void setNivelesArbolTodos(List<GenNivelArbol> nivelesArbolTodos) {
		this.nivelesArbolTodos = nivelesArbolTodos;
	}

	public List<GenEstado> getEstadosTodos() {
		return estadosTodos;
	}

	public void setEstadosTodos(List<GenEstado> estadosTodos) {
		this.estadosTodos = estadosTodos;
	}

	public int getIdEstadoSeleccionado() {
		return idEstadoSeleccionado;
	}

	public void setIdEstadoSeleccionado(int idEstadoSeleccionado) {
		this.idEstadoSeleccionado = idEstadoSeleccionado;
	}

	public int getIdTipoDepartamentoSeleccionado() {
		return idTipoDepartamentoSeleccionado;
	}

	public void setIdTipoDepartamentoSeleccionado(
			int idTipoDepartamentoSeleccionado) {
		this.idTipoDepartamentoSeleccionado = idTipoDepartamentoSeleccionado;
	}

	public int getIdNivelArbolSeleccionado() {
		return idNivelArbolSeleccionado;
	}

	public void setIdNivelArbolSeleccionado(int idNivelArbolSeleccionado) {
		this.idNivelArbolSeleccionado = idNivelArbolSeleccionado;
	}

	public List<TreeNode> getNodosTodos() {
		return nodosTodos;
	}

	public void setNodosTodos(List<TreeNode> nodosTodos) {
		this.nodosTodos = nodosTodos;
	}

	public List<RoDepartamento> getSubDepartamentosTodos() {
		return subDepartamentosTodos;
	}

	public void setSubDepartamentosTodos(
			List<RoDepartamento> subDepartamentosTodos) {
		this.subDepartamentosTodos = subDepartamentosTodos;
	}

	public boolean isEstaExpandido() {
		return estaExpandido;
	}

	public void setEstaExpandido(boolean estaExpandido) {
		this.estaExpandido = estaExpandido;
	}

	public String getNombreSeleccionado() {
		return nombreSeleccionado;
	}

	public void setNombreSeleccionado(String nombreSeleccionado) {
		this.nombreSeleccionado = nombreSeleccionado;
	}

	public boolean isPnlDepartamento() {
		return pnlDepartamento;
	}

	public void setPnlDepartamento(boolean pnlDepartamento) {
		this.pnlDepartamento = pnlDepartamento;
	}

	// public String getNumeroPadre() {
	// return numeroPadre;
	// }
	//
	// public void setNumeroPadre(String numeroPadre) {
	// this.numeroPadre = numeroPadre;
	// }
	//
	// public String getNumeroHijo() {
	// return numeroHijo;
	// }
	//
	// public void setNumeroHijo(String numeroHijo) {
	// this.numeroHijo = numeroHijo;
	// }

	public boolean isTxtNumero() {
		return txtNumero;
	}

	public void setTxtNumero(boolean txtNumero) {
		this.txtNumero = txtNumero;
	}

	public List<RoResponsable> getResponsablesTodos() {
		return responsablesTodos;
	}

	public void setResponsablesTodos(List<RoResponsable> responsablesTodos) {
		this.responsablesTodos = responsablesTodos;
	}

	public List<RoRespDepa> getResponsablesDepa() {
		return responsablesDepa;
	}

	public void setResponsablesDepa(List<RoRespDepa> responsablesDepa) {
		this.responsablesDepa = responsablesDepa;
	}

	public List<String> getResponsablesSeleccionados() {
		return responsablesSeleccionados;
	}

	public void setResponsablesSeleccionados(
			List<String> responsablesSeleccionados) {
		this.responsablesSeleccionados = responsablesSeleccionados;
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

	public boolean isExpandirTodoArbol() {
		return expandirTodoArbol;
	}

	public void setExpandirTodoArbol(boolean expandirTodoArbol) {
		this.expandirTodoArbol = expandirTodoArbol;
	}

}
