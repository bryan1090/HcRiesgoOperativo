package com.hc.ro.vista.admin.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.primefaces.context.RequestContext;

import com.hc.ro.dataManager.DataManagerSesion;
import com.hc.ro.modelo.RoPermisosDetalleEvento;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.SisDetalleMenu;
import com.hc.ro.modelo.SisMenu;
import com.hc.ro.modelo.SisPerfil;
import com.hc.ro.modelo.SisPermiso;
import com.hc.ro.modelo.SisProcedimiento;
import com.hc.ro.modelo.SisReporte;
import com.hc.ro.negocio.ServicioRoPermisosDetalleEvento;
import com.hc.ro.negocio.ServicioRoProceso;
import com.hc.ro.negocio.ServicioSisDetalleMenu;
import com.hc.ro.negocio.ServicioSisMenu;
import com.hc.ro.negocio.ServicioSisPerfil;
import com.hc.ro.negocio.ServicioSisPermiso;
import com.hc.ro.negocio.ServicioSisProcedimiento;
import com.hc.ro.negocio.ServicioSisReporte;

@ManagedBean
@ViewScoped
public class ControladorCrudSisPerfil implements Converter {
	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudPerfil.jsf";
	// SisPerfil
	@EJB
	ServicioSisPerfil servicioSisPerfil;
	@EJB
	ServicioSisMenu servicioSisMenu;
	@EJB
	ServicioSisDetalleMenu servicioSisDetalleMenu;
	@EJB
	ServicioSisReporte servicioSisReporte;
	@EJB
	ServicioRoProceso servicioRoProceso;
	@EJB
	ServicioSisPermiso servicioSisPermiso;
	@EJB
	ServicioSisProcedimiento servicioSisProcedimiento;
	@EJB
	ServicioRoPermisosDetalleEvento servicioRoPermisosDetalleEvento;

	// VARIABLES
	private SisPerfil sisPerfilVista;
	private SisPerfil sisPerfilControlador;
	private List<SisPerfil> sisPerfilesTodos;
	private List<SisMenu> sisMenusTodos;
	private List<SisDetalleMenu> sisDetalleMenusTodos;
	private List<SisDetalleMenu> sisDetallesMenu;
	private List<SisDetalleMenu> sisDetallesPorMenu;
	private List<String> detallesSeleccionadosPorMenu;
	private List<SisReporte> sisReportesTodos;
	private List<RoProceso> roProcesosTodos;
	private List<SisProcedimiento> sisProcedimientosTodos;
	private List<SisPermiso> sisPermisosTodos;
	private List<SisPermiso> sisPermisosPerfil;
	private List<SisPermiso> permisosDemeSeleccionados;
	private int idPerfilSeleccionado;
	private int idDetalleSeleccionado;
	private int idMenuSeleccionado;
	private int idMenuSeleccionadoAnt;
	private List<String> camposVisiblesSeleccionados;
	private List<String> camposEditablesSeleccionados;
	private List<String> menusSeleccionados;
	private List<String> reportesSeleccionados;
	private List<String> procesosSeleccionados;
	private List<String> procedimientosSeleccionados;
	private int idPermisoSeleccionado;
	private boolean tipoPermiso;

	private RoPermisosDetalleEvento roPermisosDetalleEventoAux;

	private boolean tipoGuardar;
	private String nombreSeleccionado;
	private String contraseniaRepetir;
	// botones,cajas,dialogos
	private boolean btnAuditable;
	private boolean btnBloqueado;
	private boolean btnAnadir;
	private boolean btnCancelar;
	private boolean btnCambiarContrasenia;
	private boolean btnGuardar;
	private boolean pnlSisPerfil;
	private boolean setContraseniaPerfil;

	//Botones de selección multiple(Todos o ninguno)
	private boolean bTodosMenus;
	private boolean bTodosCamposVisibles;
	private boolean bTodosCamposEditables;
	
	

	//
	public ControladorCrudSisPerfil() {
		super();
		sisPerfilControlador = new SisPerfil();
		sisPerfilVista = new SisPerfil();
		sisPerfilesTodos = new ArrayList<SisPerfil>();
		nombreSeleccionado = new String();
		roProcesosTodos = new ArrayList<RoProceso>();
		sisProcedimientosTodos = new ArrayList<SisProcedimiento>();
		sisMenusTodos = new ArrayList<SisMenu>();
		sisDetalleMenusTodos = new ArrayList<SisDetalleMenu>();
		// sisPermisosTodos = new ArrayList<SisPermiso>();
		sisPermisosPerfil = new ArrayList<SisPermiso>();
		sisDetallesMenu = new ArrayList<SisDetalleMenu>();
		menusSeleccionados = new ArrayList<String>();
		reportesSeleccionados = new ArrayList<String>();
		procesosSeleccionados = new ArrayList<String>();
		procedimientosSeleccionados = new ArrayList<String>();
		permisosDemeSeleccionados = new ArrayList<SisPermiso>();
		camposVisiblesSeleccionados = new ArrayList<String>();
		camposEditablesSeleccionados = new ArrayList<String>();
		roPermisosDetalleEventoAux = new RoPermisosDetalleEvento();
	}

	@PostConstruct
	public void PostControladorCrudSisPerfil() {
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
		sisPerfilesTodos = servicioSisPerfil.buscarTodos();
		roProcesosTodos = servicioRoProceso.buscarTodos();
		sisProcedimientosTodos = servicioSisProcedimiento.buscarTodos();
		sisMenusTodos = servicioSisMenu.buscarTodos();
		btnAnadir = false;
		btnCambiarContrasenia = false;
		btnCancelar = true;
		btnGuardar = true;
		pnlSisPerfil = false;
		
		
		
		

	}

	// METODOS

	public void leerPermisosCampos() {
		camposVisiblesSeleccionados = new ArrayList<String>();
		camposEditablesSeleccionados = new ArrayList<String>();
		try {
			roPermisosDetalleEventoAux = servicioRoPermisosDetalleEvento
					.buscarPermisoPorPerfil(sisPerfilVista.getNombrePerf());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		System.out.println("sisPerfilVista nombre: "
				+ sisPerfilVista.getNombrePerf());
		if (roPermisosDetalleEventoAux.getColCodigo().equals("1")) {
			camposVisiblesSeleccionados.add("1");
		}
		if (roPermisosDetalleEventoAux.getColAgencia().equals("1")) {
			camposVisiblesSeleccionados.add("3");
		}
		if (roPermisosDetalleEventoAux.getColEvento().equals("1")) {
			camposVisiblesSeleccionados.add("4");
		}
		if (roPermisosDetalleEventoAux.getColProceso().equals("1")) {
			camposVisiblesSeleccionados.add("5");
		}
		if (roPermisosDetalleEventoAux.getColNegocio().equals("1")) {
			camposVisiblesSeleccionados.add("7");
		}
		if (roPermisosDetalleEventoAux.getColDepartamento().equals("1")) {
			camposVisiblesSeleccionados.add("8");
		}
		if (roPermisosDetalleEventoAux.getColFactorRiesgo().equals("1")) {
			camposVisiblesSeleccionados.add("9");
		}
		if (roPermisosDetalleEventoAux.getColTipoPerdida().equals("1")) {
			camposVisiblesSeleccionados.add("22");
		}
		if (roPermisosDetalleEventoAux.getColPtoControl().equals("1")) {
			camposVisiblesSeleccionados.add("10");
		}
		if (roPermisosDetalleEventoAux.getColOtrosGastos().equals("1")) {
			camposVisiblesSeleccionados.add("19");
		}
		if (roPermisosDetalleEventoAux.getColFechaOcurrencia().equals("1")) {
			camposVisiblesSeleccionados.add("13");
		}
		if (roPermisosDetalleEventoAux.getColFechaDescubrimiento().equals("1")) {
			camposVisiblesSeleccionados.add("14");
		}
		if (roPermisosDetalleEventoAux.getColFechaRegistro().equals("1")) {
			camposVisiblesSeleccionados.add("15");
		}
		if (roPermisosDetalleEventoAux.getColCausaProbable().equals("1")) {
			camposVisiblesSeleccionados.add("23");
		}
		if (roPermisosDetalleEventoAux.getColDescripcion().equals("1")) {
			camposVisiblesSeleccionados.add("24");
		}
		if (roPermisosDetalleEventoAux.getColDescripcionDetallada().equals("1")) {
			camposVisiblesSeleccionados.add("25");
		}
		System.out.println("roPermisosDetalleEventoAux colBloqueo: "
				+ roPermisosDetalleEventoAux.getColBloqueo());
		if (roPermisosDetalleEventoAux.getColBloqueo().equals("1")) {
			camposVisiblesSeleccionados.add("26");
		}
		if (roPermisosDetalleEventoAux.getColValor().equals("1")) {
			camposVisiblesSeleccionados.add("33");
		}
		if (roPermisosDetalleEventoAux.getColMonto().equals("1")) {
			camposVisiblesSeleccionados.add("11");
		}
		if (roPermisosDetalleEventoAux.getColCosto().equals("1")) {
			camposVisiblesSeleccionados.add("16");
		}
		if (roPermisosDetalleEventoAux.getColRecupReal().equals("1")) {
			camposVisiblesSeleccionados.add("17");
		}
		if (roPermisosDetalleEventoAux.getColPerdResidual().equals("1")) {
			camposVisiblesSeleccionados.add("28");
		}
		if (roPermisosDetalleEventoAux.getColUsuario().equals("1")) {
			camposVisiblesSeleccionados.add("29");
		}
		if (roPermisosDetalleEventoAux.getColBtnEditar().equals("1")) {
			camposVisiblesSeleccionados.add("21");
		}
		if (roPermisosDetalleEventoAux.getColPanelAdicionales().equals("1")) {
			camposVisiblesSeleccionados.add("30");
		}
		if (roPermisosDetalleEventoAux.getColPanelCostos().equals("1")) {
			camposVisiblesSeleccionados.add("31");
		}
		if (roPermisosDetalleEventoAux.getColPanelRecuperaciones().equals("1")) {
			camposVisiblesSeleccionados.add("32");
		}
		if (roPermisosDetalleEventoAux.getColPanelCualitativo().equals("1")) {
			camposVisiblesSeleccionados.add("50");
		}
		if (roPermisosDetalleEventoAux.getDisCodigo().equals("1")) {
			camposEditablesSeleccionados.add("1");
		}
		if (roPermisosDetalleEventoAux.getDisAgencia().equals("1")) {
			camposEditablesSeleccionados.add("3");
		}
		if (roPermisosDetalleEventoAux.getDisEvento().equals("1")) {
			camposEditablesSeleccionados.add("4");
		}
		if (roPermisosDetalleEventoAux.getDisProceso().equals("1")) {
			camposEditablesSeleccionados.add("5");
		}
		if (roPermisosDetalleEventoAux.getDisNegocio().equals("1")) {
			camposEditablesSeleccionados.add("7");
		}
		if (roPermisosDetalleEventoAux.getDisDepartamento().equals("1")) {
			camposEditablesSeleccionados.add("8");
		}
		if (roPermisosDetalleEventoAux.getDisFactorRiesgo().equals("1")) {
			camposEditablesSeleccionados.add("9");
		}
		if (roPermisosDetalleEventoAux.getDisTipoPerdida().equals("1")) {
			camposEditablesSeleccionados.add("22");
		}
		if (roPermisosDetalleEventoAux.getDisPtoControl().equals("1")) {
			camposEditablesSeleccionados.add("10");
		}
		if (roPermisosDetalleEventoAux.getDisOtrosGastos().equals("1")) {
			camposEditablesSeleccionados.add("19");
		}
		if (roPermisosDetalleEventoAux.getDisFechaOcurrencia().equals("1")) {
			camposEditablesSeleccionados.add("13");
		}
		if (roPermisosDetalleEventoAux.getDisFechaDescubrimiento().equals("1")) {
			camposEditablesSeleccionados.add("14");
		}
		if (roPermisosDetalleEventoAux.getDisFechaRegistro().equals("1")) {
			camposEditablesSeleccionados.add("15");
		}
		if (roPermisosDetalleEventoAux.getDisCausaProbable().equals("1")) {
			camposEditablesSeleccionados.add("23");
		}
		if (roPermisosDetalleEventoAux.getDisDescripcion().equals("1")) {
			camposEditablesSeleccionados.add("24");
		}
		if (roPermisosDetalleEventoAux.getDisDescripcionDetallada().equals("1")) {
			camposEditablesSeleccionados.add("25");
		}
		if (roPermisosDetalleEventoAux.getDisBloqueo().equals("1")) {
			camposEditablesSeleccionados.add("26");
		}
		if (roPermisosDetalleEventoAux.getDisValor().equals("1")) {
			camposEditablesSeleccionados.add("33");
		}
		if (roPermisosDetalleEventoAux.getDisMonto().equals("1")) {
			camposEditablesSeleccionados.add("11");
		}
		if (roPermisosDetalleEventoAux.getDisCosto().equals("1")) {
			camposEditablesSeleccionados.add("16");
		}
		if (roPermisosDetalleEventoAux.getDisRecupReal().equals("1")) {
			camposEditablesSeleccionados.add("17");
		}
		if (roPermisosDetalleEventoAux.getDisPerdResidual().equals("1")) {
			camposEditablesSeleccionados.add("28");
		}
		if (roPermisosDetalleEventoAux.getDisUsuario().equals("1")) {
			camposEditablesSeleccionados.add("29");
		}
		if (roPermisosDetalleEventoAux.getDisBtnEditar().equals("1")) {
			camposEditablesSeleccionados.add("21");
		}
		if (roPermisosDetalleEventoAux.getDisPanelAdicionales().equals("1")) {
			camposEditablesSeleccionados.add("30");
		}
		if (roPermisosDetalleEventoAux.getDisPanelCostos().equals("1")) {
			camposEditablesSeleccionados.add("31");
		}
		if (roPermisosDetalleEventoAux.getDisPanelRecuperaciones().equals("1")) {
			camposEditablesSeleccionados.add("32");
		}
		if (roPermisosDetalleEventoAux.getDisPanelCualitativo().equals("1")) {
			camposVisiblesSeleccionados.add("50");
		}
	}

	public void encerarPermisos() {
		roPermisosDetalleEventoAux.setColAgencia("0");
		roPermisosDetalleEventoAux.setColBloqueo("0");
		roPermisosDetalleEventoAux.setColBtnEditar("0");
		roPermisosDetalleEventoAux.setColCausaProbable("0");
		roPermisosDetalleEventoAux.setColCodigo("0");
		roPermisosDetalleEventoAux.setColCosto("0");
		roPermisosDetalleEventoAux.setColCuenta("0");
		roPermisosDetalleEventoAux.setColDepartamento("0");
		roPermisosDetalleEventoAux.setColDescripcion("0");
		roPermisosDetalleEventoAux.setColDescripcionDetallada("0");
		roPermisosDetalleEventoAux.setColEvento("0");
		roPermisosDetalleEventoAux.setColFactorRiesgo("0");
		roPermisosDetalleEventoAux.setColFechaDescubrimiento("0");
		roPermisosDetalleEventoAux.setColFechaOcurrencia("0");
		roPermisosDetalleEventoAux.setColFechaRegistro("0");
		roPermisosDetalleEventoAux.setColMoneda("0");
		roPermisosDetalleEventoAux.setColMonto("0");
		roPermisosDetalleEventoAux.setColNegocio("0");
		roPermisosDetalleEventoAux.setColOpEdicion("0");
		roPermisosDetalleEventoAux.setColOtrosGastos("0");
		roPermisosDetalleEventoAux.setColPanelAdicionales("0");
		roPermisosDetalleEventoAux.setColPanelCostos("0");
		roPermisosDetalleEventoAux.setColPanelRecuperaciones("0");
		roPermisosDetalleEventoAux.setColPerdResidual("0");
		roPermisosDetalleEventoAux.setColProceso("0");
		roPermisosDetalleEventoAux.setColPtoControl("0");
		roPermisosDetalleEventoAux.setColReal("0");
		roPermisosDetalleEventoAux.setColRecupReal("0");
		roPermisosDetalleEventoAux.setColTipoPerdida("0");
		roPermisosDetalleEventoAux.setColUsuario("0");
		roPermisosDetalleEventoAux.setColValor("0");
		roPermisosDetalleEventoAux.setColValorCambio("0");
		roPermisosDetalleEventoAux.setColPanelCualitativo("0");

		roPermisosDetalleEventoAux.setDisAgencia("0");
		roPermisosDetalleEventoAux.setDisBloqueo("0");
		roPermisosDetalleEventoAux.setDisBtnEditar("0");
		roPermisosDetalleEventoAux.setDisCausaProbable("0");
		roPermisosDetalleEventoAux.setDisCodigo("0");
		roPermisosDetalleEventoAux.setDisCosto("0");
		roPermisosDetalleEventoAux.setDisCuenta("0");
		roPermisosDetalleEventoAux.setDisDepartamento("0");
		roPermisosDetalleEventoAux.setDisDescripcion("0");
		roPermisosDetalleEventoAux.setDisDescripcionDetallada("0");
		roPermisosDetalleEventoAux.setDisEvento("0");
		roPermisosDetalleEventoAux.setDisFactorRiesgo("0");
		roPermisosDetalleEventoAux.setDisFechaDescubrimiento("0");
		roPermisosDetalleEventoAux.setDisFechaOcurrencia("0");
		roPermisosDetalleEventoAux.setDisFechaRegistro("0");
		roPermisosDetalleEventoAux.setDisMoneda("0");
		roPermisosDetalleEventoAux.setDisMonto("0");
		roPermisosDetalleEventoAux.setDisNegocio("0");
		roPermisosDetalleEventoAux.setDisOpEdicion("0");
		roPermisosDetalleEventoAux.setDisOtrosGastos("0");
		roPermisosDetalleEventoAux.setDisPanelAdicionales("0");
		roPermisosDetalleEventoAux.setDisPanelCostos("0");
		roPermisosDetalleEventoAux.setDisPanelRecuperaciones("0");
		roPermisosDetalleEventoAux.setDisPerdResidual("0");
		roPermisosDetalleEventoAux.setDisProceso("0");
		roPermisosDetalleEventoAux.setDisPtoControl("0");
		roPermisosDetalleEventoAux.setDisReal("0");
		roPermisosDetalleEventoAux.setDisRecupReal("0");
		roPermisosDetalleEventoAux.setDisTipoPerdida("0");
		roPermisosDetalleEventoAux.setDisUsuario("0");
		roPermisosDetalleEventoAux.setDisValor("0");
		roPermisosDetalleEventoAux.setDisValorCambio("0");
		roPermisosDetalleEventoAux.setDisPanelCualitativo("0");
	}

	public void escribirPermisosCampos() {
		encerarPermisos();

		selNego();
		for (String item : camposVisiblesSeleccionados) {
			switch (Integer.parseInt(item)) {
			case 1:
				roPermisosDetalleEventoAux.setColCodigo("1");
				break;

			case 3:
				roPermisosDetalleEventoAux.setColAgencia("1");
				break;

			case 4:
				roPermisosDetalleEventoAux.setColEvento("1");
				break;

			case 5:
				roPermisosDetalleEventoAux.setColProceso("1");
				break;

			case 7:
				roPermisosDetalleEventoAux.setColNegocio("1");
				break;

			case 8:
				roPermisosDetalleEventoAux.setColDepartamento("1");
				break;

			case 9:
				roPermisosDetalleEventoAux.setColFactorRiesgo("1");
				break;

			case 22:
				roPermisosDetalleEventoAux.setColTipoPerdida("1");
				break;

			case 10:
				roPermisosDetalleEventoAux.setColPtoControl("1");
				break;

			case 19:
				roPermisosDetalleEventoAux.setColOtrosGastos("1");
				break;

			case 13:
				roPermisosDetalleEventoAux.setColFechaOcurrencia("1");
				break;

			case 14:
				roPermisosDetalleEventoAux.setColFechaDescubrimiento("1");
				break;

			case 15:
				roPermisosDetalleEventoAux.setColFechaRegistro("1");
				break;

			case 23:
				roPermisosDetalleEventoAux.setColCausaProbable("1");
				break;

			case 24:
				roPermisosDetalleEventoAux.setColDescripcion("1");
				break;

			case 25:
				roPermisosDetalleEventoAux.setColDescripcionDetallada("1");
				break;

			case 26:
				roPermisosDetalleEventoAux.setColBloqueo("1");
				break;

			case 33:
				roPermisosDetalleEventoAux.setColValor("1");
				break;

			case 11:
				roPermisosDetalleEventoAux.setColMonto("1");
				break;

			case 16:
				roPermisosDetalleEventoAux.setColCosto("1");
				break;

			case 17:
				roPermisosDetalleEventoAux.setColRecupReal("1");
				break;

			case 28:
				roPermisosDetalleEventoAux.setColPerdResidual("1");
				break;

			case 29:
				roPermisosDetalleEventoAux.setColUsuario("1");
				break;

			case 21:
				roPermisosDetalleEventoAux.setColBtnEditar("1");
				break;

			case 30:
				roPermisosDetalleEventoAux.setColPanelAdicionales("1");
				break;

			case 31:
				roPermisosDetalleEventoAux.setColPanelCostos("1");
				break;

			case 32:
				roPermisosDetalleEventoAux.setColPanelRecuperaciones("1");
				break;

			case 50:
				roPermisosDetalleEventoAux.setColPanelCualitativo("1");
				break;

			}

		}

		// Campos añadidos a la fuerza
		roPermisosDetalleEventoAux.setColPromedio("1");
		roPermisosDetalleEventoAux.setColNumOcur("1");
		roPermisosDetalleEventoAux.setColNumOcur("1");
		roPermisosDetalleEventoAux.setColPanelIndicadores("1");

		roPermisosDetalleEventoAux.setDisPanelIndicadores("0");
		roPermisosDetalleEventoAux.setDisNumOcur("0");

		for (String item : camposEditablesSeleccionados) {
			switch (Integer.parseInt(item)) {
			case 1:
				roPermisosDetalleEventoAux.setDisCodigo("1");
				break;

			case 3:
				roPermisosDetalleEventoAux.setDisAgencia("1");
				break;

			case 4:
				roPermisosDetalleEventoAux.setDisEvento("1");
				break;

			case 5:
				roPermisosDetalleEventoAux.setDisProceso("1");
				break;

			case 7:
				roPermisosDetalleEventoAux.setDisNegocio("1");
				break;

			case 8:
				roPermisosDetalleEventoAux.setDisDepartamento("1");
				break;

			case 9:
				roPermisosDetalleEventoAux.setDisFactorRiesgo("1");
				break;

			case 22:
				roPermisosDetalleEventoAux.setDisTipoPerdida("1");
				break;

			case 10:
				roPermisosDetalleEventoAux.setDisPtoControl("1");
				break;

			case 19:
				roPermisosDetalleEventoAux.setDisOtrosGastos("1");
				break;

			case 13:
				roPermisosDetalleEventoAux.setDisFechaOcurrencia("1");
				break;

			case 14:
				roPermisosDetalleEventoAux.setDisFechaDescubrimiento("1");
				break;

			case 15:
				roPermisosDetalleEventoAux.setDisFechaRegistro("1");
				break;

			case 23:
				roPermisosDetalleEventoAux.setDisCausaProbable("1");
				break;

			case 24:
				roPermisosDetalleEventoAux.setDisDescripcion("1");
				break;

			case 25:
				roPermisosDetalleEventoAux.setDisDescripcionDetallada("1");
				break;

			case 26:
				roPermisosDetalleEventoAux.setDisBloqueo("1");
				break;

			case 33:
				roPermisosDetalleEventoAux.setDisValor("1");
				break;

			case 11:
				roPermisosDetalleEventoAux.setDisMonto("1");
				break;

			case 16:
				roPermisosDetalleEventoAux.setDisCosto("1");
				break;

			case 17:
				roPermisosDetalleEventoAux.setDisRecupReal("1");
				break;

			case 28:
				roPermisosDetalleEventoAux.setDisPerdResidual("1");
				break;

			case 29:
				roPermisosDetalleEventoAux.setDisUsuario("1");
				break;

			case 21:
				roPermisosDetalleEventoAux.setDisBtnEditar("1");
				break;

			case 30:
				roPermisosDetalleEventoAux.setDisPanelAdicionales("1");
				break;

			case 31:
				roPermisosDetalleEventoAux.setDisPanelCostos("1");
				break;

			case 32:
				roPermisosDetalleEventoAux.setDisPanelRecuperaciones("1");
				break;
			case 50:
				roPermisosDetalleEventoAux.setDisPanelCualitativo("1");
				break;

			}
		}

	}

	public void seleccionarTodosMenus() {
		if (bTodosMenus) {
			menusSeleccionados = new ArrayList<String>();
			for (SisMenu menu : sisMenusTodos) {
				menusSeleccionados.add(Integer.toString(menu.getCodigoMenu()));
			}
		}
		else{
			menusSeleccionados = new ArrayList<String>();
		}
	}

	public void seleccionarTodosCamposVisiblesDetalleEvento()
	{
		if(bTodosCamposVisibles)
		{
			camposVisiblesSeleccionados=new ArrayList<String>();
			camposVisiblesSeleccionados.add("1");
			camposVisiblesSeleccionados.add("3");
			camposVisiblesSeleccionados.add("4");
			camposVisiblesSeleccionados.add("5");
			camposVisiblesSeleccionados.add("7");
			camposVisiblesSeleccionados.add("8");
			camposVisiblesSeleccionados.add("9");
			camposVisiblesSeleccionados.add("10");
			camposVisiblesSeleccionados.add("11");
			camposVisiblesSeleccionados.add("13");
			camposVisiblesSeleccionados.add("14");
			camposVisiblesSeleccionados.add("15");
			camposVisiblesSeleccionados.add("16");
			camposVisiblesSeleccionados.add("17");
			camposVisiblesSeleccionados.add("19");
			camposVisiblesSeleccionados.add("33");
			camposVisiblesSeleccionados.add("21");
			camposVisiblesSeleccionados.add("22");
			camposVisiblesSeleccionados.add("23");
			camposVisiblesSeleccionados.add("24");
			camposVisiblesSeleccionados.add("25");
			camposVisiblesSeleccionados.add("26");
			camposVisiblesSeleccionados.add("28");
			camposVisiblesSeleccionados.add("29");
			camposVisiblesSeleccionados.add("30");
			camposVisiblesSeleccionados.add("50");
			camposVisiblesSeleccionados.add("31");
			camposVisiblesSeleccionados.add("32");
			
			
		}
		else
		{
			camposVisiblesSeleccionados=new ArrayList<String>();
		}
	}
	
	public void seleccionarTodosCamposEditablesDetalleEvento()
	{
		if(bTodosCamposEditables)
		{
			
			camposEditablesSeleccionados=new ArrayList<String>();
			camposEditablesSeleccionados.add("1");
			camposEditablesSeleccionados.add("3");
			camposEditablesSeleccionados.add("4");
			camposEditablesSeleccionados.add("5");
			camposEditablesSeleccionados.add("7");
			camposEditablesSeleccionados.add("8");
			camposEditablesSeleccionados.add("9");
			camposEditablesSeleccionados.add("10");
			camposEditablesSeleccionados.add("11");
			camposEditablesSeleccionados.add("13");
			camposEditablesSeleccionados.add("14");
			camposEditablesSeleccionados.add("15");
			camposEditablesSeleccionados.add("16");
			camposEditablesSeleccionados.add("17");
			camposEditablesSeleccionados.add("19");
			camposEditablesSeleccionados.add("33");
			camposEditablesSeleccionados.add("21");
			camposEditablesSeleccionados.add("22");
			camposEditablesSeleccionados.add("23");
			camposEditablesSeleccionados.add("24");
			camposEditablesSeleccionados.add("25");
			camposEditablesSeleccionados.add("26");
			camposEditablesSeleccionados.add("28");
			camposEditablesSeleccionados.add("29");
			camposEditablesSeleccionados.add("30");
			camposEditablesSeleccionados.add("50");
			camposEditablesSeleccionados.add("31");
			camposEditablesSeleccionados.add("32");
			
			
		}
		else
		{
			camposEditablesSeleccionados=new ArrayList<String>();
		}
	}
	
	public void guardarPermiso() {

		// Elimino los permisos ya existentes, para luego agregar los nuevos.
		List<SisPermiso> permisosPerf = servicioSisPermiso
				.buscarPermisoPorPerfil(sisPerfilVista.getNombrePerf());
		for (SisPermiso item : permisosPerf) {
			servicioSisPermiso.eliminar(item);
		}

		sisPermisosPerfil = new ArrayList<SisPermiso>();
		for (String menu : menusSeleccionados) {
			// Por cada menu seleccionado se crea un permiso
			SisPermiso permiso = new SisPermiso();
			permiso.setSisPerfil(sisPerfilVista);
			// busco el menu en la base segun el string
			permiso.setSisMenu(servicioSisMenu.buscarPorId(Integer
					.parseInt(menu)));
			permiso.setTipoPerm(1);
			sisPermisosPerfil.add(permiso);

		}

		/*
		 * for (String item : procedimientosSeleccionados) { SisPermiso permiso
		 * = new SisPermiso(); permiso.setSisPerfil(sisPerfilVista);
		 * permiso.setSisProcedimiento(servicioSisProcedimiento
		 * .buscarPorId(Integer.parseInt(item))); permiso.setTipoPerm(2);
		 * sisPermisosPerfil.add(permiso); }
		 */
		desmarcarDetallesPorMenu();
		for (SisPermiso item : permisosDemeSeleccionados) {
			item.setSisPerfil(sisPerfilVista);
			sisPermisosPerfil.add(item);

		}

		for (SisPermiso permiso : sisPermisosPerfil) {

			servicioSisPermiso.insertar(permiso);

		}

		sisPermisosPerfil = new ArrayList<SisPermiso>();
	}

	public void guardarSisPerfil() {

		escribirPermisosCampos();
		if (tipoGuardar) {
			if (servicioSisPerfil.existePerfilPorNombre(sisPerfilVista
					.getNombrePerf())) {
				FacesContext context = FacesContext.getCurrentInstance();

				servicioSisPerfil.insertar(sisPerfilVista);

				sisPerfilVista = servicioSisPerfil
						.buscarPerfilPorNombre(sisPerfilVista.getNombrePerf());
				roPermisosDetalleEventoAux.setSisPerfil(sisPerfilVista);
				servicioRoPermisosDetalleEvento
						.insertar(roPermisosDetalleEventoAux);
				context.addMessage(null, new FacesMessage("Perfil Añadido",
						"El Perfil ha sido Añadido con éxito"));

				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Perfil no se debe repetir"));
			}
		} else {

			if (servicioSisPerfil.existePerfilPorNombreEx(
					sisPerfilVista.getNombrePerf(),
					sisPerfilVista.getCodigoPerf())) {

				servicioSisPerfil.actualizar(sisPerfilVista);

				FacesContext context = FacesContext.getCurrentInstance();
				servicioRoPermisosDetalleEvento
						.actualizar(roPermisosDetalleEventoAux);
				context.addMessage(null, new FacesMessage("Perfil Actualizado",
						"El Perfil ha sido Actualizado con éxito"));
				exitoGuardar();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();

				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error al guardar:",
						"El nombre de Perfil no se debe repetir"));
				sisPerfilVista.setNombrePerf(sisPerfilControlador
						.getNombrePerf());
			}
		}

	}

	public void exitoGuardar() {
		// try {
		guardarPermiso();

		// } catch (Exception e) {
		// FacesContext context = FacesContext.getCurrentInstance();
		//
		// context.addMessage(
		// null,
		// new FacesMessage(FacesMessage.SEVERITY_ERROR,
		// "Error al guardar:",
		// "Perfil Guardado con errores en Permisos (Vuelva a intentarlo): "+e.getMessage()));
		//
		// }
		sisPerfilVista = new SisPerfil();
		sisPerfilesTodos = servicioSisPerfil.buscarTodos();
		btnGuardar = true;
		btnCancelar = true;
		btnAnadir = false;
		pnlSisPerfil = false;
	}

	public void eliminarSisPerfil() {
		if (!servicioSisPerfil.tieneUsuarios(sisPerfilVista)) {
			sisPermisosPerfil = new ArrayList<SisPermiso>();

			RoPermisosDetalleEvento roPermisosDetalleEvento = new RoPermisosDetalleEvento();
			try {
				roPermisosDetalleEvento = servicioRoPermisosDetalleEvento
						.buscarPermisoPorPerfil(sisPerfilVista.getNombrePerf());
				servicioRoPermisosDetalleEvento
						.eliminar(roPermisosDetalleEvento);
			} catch (Exception e) {
				// TODO: handle exception
			}
			sisPermisosPerfil = servicioSisPermiso
					.buscarPermisoPorPerfil(sisPerfilVista.getNombrePerf());
			if (sisPermisosPerfil.size() > 0) {
				for (SisPermiso item : sisPermisosPerfil) {

					servicioSisPermiso.eliminar(item);

				}
			}

			try {

				servicioSisPerfil.flushBase();
				sisPerfilVista = servicioSisPerfil.buscarPorId(sisPerfilVista
						.getCodigoPerf());
				servicioSisPerfil.eliminar(sisPerfilVista);
				sisPerfilVista = new SisPerfil();
				sisPerfilesTodos = servicioSisPerfil.buscarTodos();

				// idEstadoSeleccionado = 1;
				btnGuardar = true;
				btnCancelar = true;
				pnlSisPerfil = false;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Perfil Eliminado",
						"El Perfil ha sido Eliminado con éxito"));
			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error al eliminar:", e.getMessage()));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Error al eliminar:",
							"Este perfil esta siendo usado por uno o varios usuarios, no se puede eliminar un perfil en uso"));
		}
	}

	public void selNego() {
		int siete = 0;
		int ocho = 0;
		try {
			for (String item : camposVisiblesSeleccionados) {
				if (item.equals("7")) {
					siete++;

				}
				if (item.equals("5")) {
					ocho++;
				}
			}
			if (siete == 1 && ocho == 0) {
				camposVisiblesSeleccionados.add("5");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void nuevoSisPerfilVista() {
		roPermisosDetalleEventoAux = new RoPermisosDetalleEvento();
		setContraseniaPerfil = true;
		tipoGuardar = true;
		sisPerfilVista = new SisPerfil();
		btnBloqueado = false;
		permisosDemeSeleccionados = new ArrayList<SisPermiso>();
		camposVisiblesSeleccionados = new ArrayList<String>();
		camposEditablesSeleccionados = new ArrayList<String>();
		marcarDetallesPorMenu();
		idMenuSeleccionado = sisMenusTodos.get(0).getCodigoMenu();
		sisDetallesPorMenu = servicioSisDetalleMenu
				.buscarDetalleMenuPorMenu(idMenuSeleccionado);
		procedimientosSeleccionados = new ArrayList<String>();
		menusSeleccionados = new ArrayList<String>();
		btnAuditable = true;
		btnGuardar = false;
		btnCancelar = false;
		pnlSisPerfil = true;
		btnCambiarContrasenia = false;
	}

	public void cancelar() {
		nuevoSisPerfilVista();
		btnGuardar = true;
		btnAnadir = false;
		btnCancelar = true;
		pnlSisPerfil = false;
		RequestContext.getCurrentInstance().update("formSisPerfil");
	}

	public void marcarDetallesPorMenu() {
		idMenuSeleccionadoAnt = idMenuSeleccionado;
		detallesSeleccionadosPorMenu = new ArrayList<String>();
		for (SisPermiso itemDM : permisosDemeSeleccionados) {
			if (idMenuSeleccionado == itemDM.getSisDetalleMenu().getSisMenu()
					.getCodigoMenu()) {
				detallesSeleccionadosPorMenu.add(Integer.toString(itemDM
						.getSisDetalleMenu().getCodigoDeme()));
			}
		}
	}

	public void desmarcarDetallesPorMenu() {
		try {
			int a = permisosDemeSeleccionados.size();
			List<SisPermiso> listaBorrar = new ArrayList<SisPermiso>();
			for (int i = 0; i < a; i++) {
				if (idMenuSeleccionadoAnt == permisosDemeSeleccionados.get(i)
						.getSisDetalleMenu().getSisMenu().getCodigoMenu()) {
					listaBorrar.add(permisosDemeSeleccionados.get(i));
				}
			}

			for (SisPermiso item : listaBorrar) {
				permisosDemeSeleccionados.remove(item);
			}
		} catch (Exception e) {
		}

		try {
			for (String item : detallesSeleccionadosPorMenu) {
				SisPermiso permisoPru = new SisPermiso();
				permisoPru.setTipoPerm(3);
				permisoPru.setSisPerfil(sisPerfilVista);
				permisoPru.setSisDetalleMenu(servicioSisDetalleMenu
						.buscarPorId(Integer.parseInt(item)));
				permisosDemeSeleccionados.add(permisoPru);
			}

		} catch (Exception e) {

		}

	}

	public void desMarcar() {
		desmarcarDetallesPorMenu();

		sisDetallesPorMenu = servicioSisDetalleMenu
				.buscarDetalleMenuPorMenu(idMenuSeleccionado);
		try {
			marcarDetallesPorMenu();
		} catch (Exception e) {
		}

	}

	public void editarSisPerfil() {
		leerPermisosCampos();
		menusSeleccionados = new ArrayList<String>();
		permisosDemeSeleccionados = new ArrayList<SisPermiso>();
		procedimientosSeleccionados = new ArrayList<String>();
		sisPermisosPerfil = servicioSisPermiso
				.buscarPermisoPorPerfil(sisPerfilVista.getNombrePerf());
		for (SisPermiso item : sisPermisosPerfil) {
			switch (item.getTipoPerm()) {
			case 1:
				menusSeleccionados.add(Integer.toString(item.getSisMenu()
						.getCodigoMenu()));
				break;
			case 3:
				permisosDemeSeleccionados.add(item);
				break;
			default:
				break;
			}
		}
		
		if(menusSeleccionados.size()==sisMenusTodos.size()){
			bTodosMenus=true;	
		}
		
		idMenuSeleccionado = sisMenusTodos.get(1).getCodigoMenu();
		sisDetallesPorMenu = servicioSisDetalleMenu
				.buscarDetalleMenuPorMenu(idMenuSeleccionado);
		try {
			marcarDetallesPorMenu();
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error general:",
					"Se ha producido un error"));
		}

		setContraseniaPerfil = false;
		sisPerfilControlador.setNombrePerf(sisPerfilVista.getNombrePerf());
		tipoGuardar = false;

		// idEstadoSeleccionado =
		// SisPerfilVista.getGenEstado().getCodigoEsta();

		btnGuardar = false;
		btnCancelar = false;
		pnlSisPerfil = true;
		btnAnadir = true;
		btnCambiarContrasenia = true;

	}

	public void cambiarContrasenia() {
		btnCambiarContrasenia = false;
		setContraseniaPerfil = true;
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

	public SisPerfil getSisPerfilVista() {
		return sisPerfilVista;
	}

	public void setSisPerfilVista(SisPerfil sisPerfilVista) {
		this.sisPerfilVista = sisPerfilVista;
	}

	public SisPerfil getSisPerfilControlador() {
		return sisPerfilControlador;
	}

	public void setSisPerfilControlador(SisPerfil sisPerfilControlador) {
		this.sisPerfilControlador = sisPerfilControlador;
	}

	public List<SisPerfil> getSisPerfilsTodos() {
		return sisPerfilesTodos;
	}

	public void setSisPerfilsTodos(List<SisPerfil> sisPerfilsTodos) {
		this.sisPerfilesTodos = sisPerfilsTodos;
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

	public boolean isPnlSisPerfil() {
		return pnlSisPerfil;
	}

	public void setPnlSisPerfil(boolean pnlSisPerfil) {
		this.pnlSisPerfil = pnlSisPerfil;
	}

	public int getIdPerfilSeleccionado() {
		return idPerfilSeleccionado;
	}

	public void setIdPerfilSeleccionado(int idPerfilSeleccionado) {
		this.idPerfilSeleccionado = idPerfilSeleccionado;
	}

	public String getContraseniaRepetir() {
		return contraseniaRepetir;
	}

	public void setContraseniaRepetir(String contraseniaRepetir) {
		this.contraseniaRepetir = contraseniaRepetir;
	}

	public boolean isBtnBloqueado() {
		return btnBloqueado;
	}

	public void setBtnBloqueado(boolean btnBloqueado) {
		this.btnBloqueado = btnBloqueado;
	}

	public boolean isBtnAuditable() {
		return btnAuditable;
	}

	public void setBtnAuditable(boolean btnAuditable) {
		this.btnAuditable = btnAuditable;
	}

	public boolean isSetContraseniaPerfil() {
		return setContraseniaPerfil;
	}

	public void setSetContraseniaPerfil(boolean setContraseniaPerfil) {
		this.setContraseniaPerfil = setContraseniaPerfil;
	}

	public boolean isBtnCambiarContrasenia() {
		return btnCambiarContrasenia;
	}

	public void setBtnCambiarContrasenia(boolean btnCambiarContrasenia) {
		this.btnCambiarContrasenia = btnCambiarContrasenia;
	}

	public List<SisPerfil> getSisPerfilesTodos() {
		return sisPerfilesTodos;
	}

	public void setSisPerfilesTodos(List<SisPerfil> sisPerfilesTodos) {
		this.sisPerfilesTodos = sisPerfilesTodos;
	}

	public List<SisMenu> getSisMenusTodos() {
		return sisMenusTodos;
	}

	public void setSisMenusTodos(List<SisMenu> sisMenusTodos) {
		this.sisMenusTodos = sisMenusTodos;
	}

	public List<SisDetalleMenu> getSisDetalleMenusTodos() {
		return sisDetalleMenusTodos;
	}

	public void setSisDetalleMenusTodos(
			List<SisDetalleMenu> sisDetalleMenusTodos) {
		this.sisDetalleMenusTodos = sisDetalleMenusTodos;
	}

	public List<SisReporte> getSisReportesTodos() {
		return sisReportesTodos;
	}

	public void setSisReportesTodos(List<SisReporte> sisReportesTodos) {
		this.sisReportesTodos = sisReportesTodos;
	}

	public List<RoProceso> getRoProcesosTodos() {
		return roProcesosTodos;
	}

	public void setRoProcesosTodos(List<RoProceso> roProcesosTodos) {
		this.roProcesosTodos = roProcesosTodos;
	}

	public int getIdDetalleSeleccionado() {
		return idDetalleSeleccionado;
	}

	public void setIdDetalleSeleccionado(int idDetalleSeleccionado) {
		this.idDetalleSeleccionado = idDetalleSeleccionado;
	}

	public boolean isTipoPermiso() {
		return tipoPermiso;
	}

	public void setTipoPermiso(boolean tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}

	public List<SisPermiso> getSisPermisosTodos() {
		return sisPermisosTodos;
	}

	public void setSisPermisosTodos(List<SisPermiso> sisPermisosTodos) {
		this.sisPermisosTodos = sisPermisosTodos;
	}

	public int getIdPermisoSeleccionado() {
		return idPermisoSeleccionado;
	}

	public void setIdPermisoSeleccionado(int idPermisoSeleccionado) {
		this.idPermisoSeleccionado = idPermisoSeleccionado;
	}

	public List<String> getProcesosSeleccionados() {
		return procesosSeleccionados;
	}

	public void setProcesosSeleccionados(List<String> procesosSeleccionados) {
		this.procesosSeleccionados = procesosSeleccionados;
	}

	public List<String> getMenusSeleccionados() {
		return menusSeleccionados;
	}

	public void setMenusSeleccionados(List<String> menusSeleccionados) {
		this.menusSeleccionados = menusSeleccionados;
	}

	public List<String> getReportesSeleccionados() {
		return reportesSeleccionados;
	}

	public void setReportesSeleccionados(List<String> reportesSeleccionados) {
		this.reportesSeleccionados = reportesSeleccionados;
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		return null;
	}

	public int getIdMenuSeleccionado() {
		return idMenuSeleccionado;
	}

	public void setIdMenuSeleccionado(int idMenuSeleccionado) {
		this.idMenuSeleccionado = idMenuSeleccionado;
	}

	public List<SisPermiso> getSisPermisosPerfil() {
		return sisPermisosPerfil;
	}

	public void setSisPermisosPerfil(List<SisPermiso> sisPermisosPerfil) {
		this.sisPermisosPerfil = sisPermisosPerfil;
	}

	public List<SisDetalleMenu> getSisDetallesMenu() {
		return sisDetallesMenu;
	}

	public void setSisDetallesMenu(List<SisDetalleMenu> sisDetallesMenu) {
		this.sisDetallesMenu = sisDetallesMenu;
	}

	public List<SisProcedimiento> getSisProcedimientosTodos() {
		return sisProcedimientosTodos;
	}

	public void setSisProcedimientosTodos(
			List<SisProcedimiento> sisProcedimientosTodos) {
		this.sisProcedimientosTodos = sisProcedimientosTodos;
	}

	public List<String> getProcedimientosSeleccionados() {
		return procedimientosSeleccionados;
	}

	public void setProcedimientosSeleccionados(
			List<String> procedimientosSeleccionados) {
		this.procedimientosSeleccionados = procedimientosSeleccionados;
	}

	public List<SisDetalleMenu> getSisDetallesPorMenu() {
		return sisDetallesPorMenu;
	}

	public void setSisDetallesPorMenu(List<SisDetalleMenu> sisDetallesPorMenu) {
		this.sisDetallesPorMenu = sisDetallesPorMenu;
	}

	public List<String> getDetallesSeleccionadosPorMenu() {
		return detallesSeleccionadosPorMenu;
	}

	public void setDetallesSeleccionadosPorMenu(
			List<String> detallesSeleccionadosPorMenu) {
		this.detallesSeleccionadosPorMenu = detallesSeleccionadosPorMenu;
	}

	public List<SisPermiso> getPermisosDemeSeleccionados() {
		return permisosDemeSeleccionados;
	}

	public void setPermisosDemeSeleccionados(
			List<SisPermiso> permisosDemeSeleccionados) {
		this.permisosDemeSeleccionados = permisosDemeSeleccionados;
	}

	public int getIdMenuSeleccionadoAnt() {
		return idMenuSeleccionadoAnt;
	}

	public void setIdMenuSeleccionadoAnt(int idMenuSeleccionadoAnt) {
		this.idMenuSeleccionadoAnt = idMenuSeleccionadoAnt;
	}

	public List<String> getCamposVisiblesSeleccionados() {
		return camposVisiblesSeleccionados;
	}

	public void setCamposVisiblesSeleccionados(
			List<String> camposVisiblesSeleccionados) {
		this.camposVisiblesSeleccionados = camposVisiblesSeleccionados;
	}

	public List<String> getCamposEditablesSeleccionados() {
		return camposEditablesSeleccionados;
	}

	public void setCamposEditablesSeleccionados(
			List<String> camposEditablesSeleccionados) {
		this.camposEditablesSeleccionados = camposEditablesSeleccionados;
	}

	public RoPermisosDetalleEvento getRoPermisosDetalleEventoAux() {
		return roPermisosDetalleEventoAux;
	}

	public void setRoPermisosDetalleEventoAux(
			RoPermisosDetalleEvento roPermisosDetalleEventoAux) {
		this.roPermisosDetalleEventoAux = roPermisosDetalleEventoAux;
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

	public boolean isbTodosMenus() {
		return bTodosMenus;
	}

	public void setbTodosMenus(boolean bTodosMenus) {
		this.bTodosMenus = bTodosMenus;
	}

	public boolean isbTodosCamposVisibles() {
		return bTodosCamposVisibles;
	}

	public void setbTodosCamposVisibles(boolean bTodosCamposVisibles) {
		this.bTodosCamposVisibles = bTodosCamposVisibles;
	}

	public boolean isbTodosCamposEditables() {
		return bTodosCamposEditables;
	}

	public void setbTodosCamposEditables(boolean bTodosCamposEditables) {
		this.bTodosCamposEditables = bTodosCamposEditables;
	}

}
