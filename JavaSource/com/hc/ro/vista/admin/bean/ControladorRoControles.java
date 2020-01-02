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
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoControles;
import com.hc.ro.modelo.RoProbabilidadEvento;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.negocio.ServicioParamProbabilidadRiesgo;
import com.hc.ro.negocio.ServicioRoControles;
import com.hc.ro.negocio.ServicioRoProbabilidadEvento;
import com.hc.ro.negocio.ServicioRoResponsable;

@ManagedBean
@ViewScoped
public class ControladorRoControles {

	@ManagedProperty("#{controladorMenuPrincipal}")
	ControladorMenuPrincipal controladorMenuPrincipal;

	@ManagedProperty("#{dataManagerSesion}")
	DataManagerSesion dataManagerSesion;

	public final static String nombrePagina = "/paginas/CrudRoControles.jsf";

	@EJB
	ServicioRoControles servicioRoControles;
	@EJB
	ServicioParamProbabilidadRiesgo servicioParamProbabilidadRiesgo;
	@EJB
	ServicioRoProbabilidadEvento servicioRoProbabilidadEvento;
	@EJB
	ServicioRoResponsable servicioRoResponsable;

	private RoControles roControlesControlador;
	private RoControles roControlesVista;
	private List<RoControles> listaRoControles;

	private ParamProbabilidadRiesgo paramProbabilidadRiesgoControlador;
	private ParamProbabilidadRiesgo paramProbabilidadRiesgoVista;
	private List<ParamProbabilidadRiesgo> listaParamProbabilidadRiesgo;

	private RoProbabilidadEvento roProbabilidadEventoControlador;
	private RoProbabilidadEvento roProbabilidadEventoVista;
	private List<RoProbabilidadEvento> listaRoProbabilidadEvento;

	private RoResponsable roResponsableControlador;
	private RoResponsable roResponsableVista;
	private List<RoResponsable> listaRoResponsable;

	public boolean tipoGuardarControl;
	private boolean pnlRoTipoControl;
	private boolean btnAñadirControl;
	private boolean btnGuardarControl;
	private boolean btnCancelarControl;

	
	
	private int idCalcProbSeleccionado;
	private int calculoProbSeleccionado;
	private int idTipoRiesgo;
	private int idResponsable;
	private int valor = 1;
	private double riesgoResidual = 0.00f;
	private String calculoDenomDelControl;
	private String clasificacionFinalDelRiesgo;

	private BigDecimal calcIm = new BigDecimal("0.00");
	private BigDecimal mult = new BigDecimal("0.00");
	private BigDecimal valoracion = new BigDecimal("0.00");
	private BigDecimal valoracionDelControl = new BigDecimal("0.00");
	private BigDecimal calP = new BigDecimal("1.00");
	private BigDecimal calT = new BigDecimal("1.00");
	private BigDecimal calF = new BigDecimal("1.00");
	private BigDecimal calD = new BigDecimal("5.00");
	private BigDecimal calEF = new BigDecimal("5.00");
	private BigDecimal calSA = new BigDecimal("5.00");

	public ControladorRoControles() {
		super();
		roControlesControlador = new RoControles();
		roControlesVista = new RoControles();
		listaRoControles = new ArrayList<RoControles>();

		paramProbabilidadRiesgoControlador = new ParamProbabilidadRiesgo();
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		listaParamProbabilidadRiesgo = new ArrayList<ParamProbabilidadRiesgo>();

		roProbabilidadEventoControlador = new RoProbabilidadEvento();
		roProbabilidadEventoVista = new RoProbabilidadEvento();
		listaRoProbabilidadEvento = new ArrayList<RoProbabilidadEvento>();

		roResponsableControlador = new RoResponsable();
		roResponsableVista = new RoResponsable();
		listaRoResponsable = new ArrayList<RoResponsable>();
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
		// Lista de Controles

		listaRoControles = servicioRoControles.buscarTodos();
		listaRoProbabilidadEvento = servicioRoProbabilidadEvento.buscarTodos();
		listaParamProbabilidadRiesgo = servicioParamProbabilidadRiesgo
				.buscarTodos();
		listaRoResponsable = servicioRoResponsable.buscarTodos();

		pnlRoTipoControl = false;
		btnAñadirControl = false;
		btnGuardarControl = true;
		btnCancelarControl = true;
	}

	public void nuevoControl() {
		roControlesVista = new RoControles();
		roProbabilidadEventoVista = new RoProbabilidadEvento();
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		roResponsableVista = new RoResponsable();
		pnlRoTipoControl = true;
		tipoGuardarControl = true;
		btnAñadirControl = true;
		try {
			idCalcProbSeleccionado = listaRoProbabilidadEvento.get(0)
					.getCODIGO_prob();
			idTipoRiesgo = listaParamProbabilidadRiesgo.get(0).getCodigoPprr();
			idResponsable = listaRoResponsable.get(0).getCodigoResp();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public BigDecimal calcImpact() {
		BigDecimal cte = new BigDecimal("0.25");
		BigDecimal cteN = new BigDecimal("0.35");

		BigDecimal Rep = new BigDecimal(roControlesVista.getParamImpRep()
				.getValorRep().floatValue());
		BigDecimal Bcp = new BigDecimal(roControlesVista.getBcp().toString());
		BigDecimal ImpactEcon = new BigDecimal(roControlesVista.getImpactEcon()
				.toString());

		BigDecimal d1 = new BigDecimal("0.00");
		BigDecimal d2 = new BigDecimal("0.00");
		BigDecimal d3 = new BigDecimal("0.00");
		BigDecimal d4 = new BigDecimal("0.00");
		d1 = Rep.multiply(cte);
		d2 = Bcp.multiply(cte);
		d3 = ImpactEcon.multiply(cteN);
		d4 = d1.add(d2);
		calcIm = d4.add(d3);
		return calcIm;
	}

	public BigDecimal calcProducto() {
		calculoProbSeleccionado = servicioRoControles
				.buscarCalculoProb(this.idCalcProbSeleccionado);
		BigDecimal a1 = new BigDecimal(this.calculoProbSeleccionado);
		BigDecimal aux = new BigDecimal("0.00");
		aux = a1.multiply(calcIm);
		Double dod = Math.ceil(Double.parseDouble(aux.toString()));
		mult = new BigDecimal(dod.toString());
		return mult;
	}

	public Integer calcEfectividad() {
		if (roControlesVista.getEfectividad().equals("Ninguna")) {
			valor = 1;
		} else if (roControlesVista.getEfectividad().equals(
				"Mal diseñado no cumple con el fin")) {
			valor = 2;
		} else if (roControlesVista.getEfectividad().equals(
				"Reduce solo algún aspescto del riesgo")) {
			valor = 3;
		} else if (roControlesVista.getEfectividad().equals(
				"Su diseño reduce los principales efectos del riesgo")) {
			valor = 4;
		} else if (roControlesVista.getEfectividad().equals(
				"Su diseño elimina en su totalidad el riesgo")) {
			valor = 5;
		}
		return valor;
	}

	public BigDecimal valoracionEficiencia() {
		BigDecimal aw = new BigDecimal(this.calP.toString());
		BigDecimal ax = new BigDecimal(this.calT.toString());
		BigDecimal ay = new BigDecimal(this.calF.toString());
		BigDecimal a = new BigDecimal("0.30");
		BigDecimal b = new BigDecimal("0.35");
		valoracion = aw.multiply(b).add(ax.multiply(b)).add(ay.multiply(a));
		return valoracion;
	}

	public BigDecimal valoracionDelControl() {
		BigDecimal aux = new BigDecimal("0.00");

		BigDecimal a = new BigDecimal("0.10");
		BigDecimal b = new BigDecimal("0.20");
		BigDecimal c = new BigDecimal("0.30");

		BigDecimal ap = new BigDecimal(this.valor);
		BigDecimal az = new BigDecimal(this.calD.toString());
		BigDecimal ba = new BigDecimal(this.calEF.toString());
		BigDecimal bb = new BigDecimal(this.calSA.toString());
		BigDecimal bd = new BigDecimal(this.valoracion.toString());

		// ap.multiply(c);
		// az.multiply(a);
		// ba.multiply(a);
		// bb.multiply(b);
		// bd.multiply(c);

		aux = ap.multiply(c).add(az.multiply(a)).add(ba.multiply(a))
				.add(bb.multiply(b)).add(bd.multiply(c));

		Double aauxx = Math.floor(Double.parseDouble(aux.toString()));

		valoracionDelControl = new BigDecimal(aauxx.toString());
		return valoracionDelControl;
	}

	public String calculoDenominacionDelControl() {
		double val = this.valoracionDelControl.doubleValue();
		if (val <= 1) {
			calculoDenomDelControl = "NINGUNO";
		} else if (val <= 2) {
			calculoDenomDelControl = "DEFICIENTE";
		} else if (val <= 3) {
			calculoDenomDelControl = "VULNERABLE";
		} else if (val <= 4) {
			calculoDenomDelControl = "MODERADO";
		} else if (val <= 5) {
			calculoDenomDelControl = "OPTIMO";
		}
		return calculoDenomDelControl;
	}

	public Double calculoRiesgoResidual() {
		double a = this.mult.doubleValue();
		double b = this.valoracionDelControl.doubleValue();
		if (a <= 3) {
			riesgoResidual = (a / b);
		}
		return riesgoResidual;
	}

	public String calculoClasificacionFinalDelRiesgo() {
		if (this.riesgoResidual <= 3) {
			clasificacionFinalDelRiesgo = "RIESGO INUSUAL";
		} else if (this.riesgoResidual <= 6) {
			clasificacionFinalDelRiesgo = "RIESGO BAJO";
		} else if (this.riesgoResidual <= 12) {
			clasificacionFinalDelRiesgo = "RIESGO MEDIO";
		} else if (this.riesgoResidual <= 16) {
			clasificacionFinalDelRiesgo = "RIESGO ALTO";
		} else if (this.riesgoResidual <= 25) {
			clasificacionFinalDelRiesgo = "RIESGO EXTREMO";
		}
		return clasificacionFinalDelRiesgo;
	}

	public BigDecimal calculoP() {
		if (roControlesVista.getPeriodicidad().equals("NINGUNA")) {
			calP = new BigDecimal("1.00");
		} else if (roControlesVista.getPeriodicidad().equals(
				"POR LO MENOS UNA VEZ AL AÑO")) {
			calP = new BigDecimal("2.00");
		} else if (roControlesVista.getPeriodicidad().equals(
				"OCASIONAL O CUANDO SE REQUIERA")) {
			calP = new BigDecimal("3.00");
		} else if (roControlesVista.getPeriodicidad().equals(
				"PERIODICO QUE INVOLUCRA ALGUNAS VECES EN EL AÑO")) {
			calP = new BigDecimal("4.00");
		} else if (roControlesVista.getPeriodicidad().equals("PERMANENTE")) {
			calP = new BigDecimal("5.00");
		}
		return calP;
	}

	public BigDecimal calculoT() {
		if (roControlesVista.getTipo().equals("NINGUNO")) {
			calT = new BigDecimal("1.00");
		} else if (roControlesVista.getTipo().equals("CORRECTIVO")) {
			calT = new BigDecimal("2.00");
		} else if (roControlesVista.getTipo().equals("DETECTIVO")) {
			calP = new BigDecimal("3.00");
		} else if (roControlesVista.getTipo().equals("PREVENTIVO")) {
			calT = new BigDecimal("5.00");
		}
		return calT;
	}

	public BigDecimal calculoF() {
		if (roControlesVista.getForma().equals("NINGUNO")) {
			calF = new BigDecimal("1.00");
		} else if (roControlesVista.getForma().equals("MANUAL")) {
			calF = new BigDecimal("2.00");
		} else if (roControlesVista.getForma().equals("MIXTO")) {
			calF = new BigDecimal("3.00");
		} else if (roControlesVista.getForma().equals("AUTOMÁTICO")) {
			calF = new BigDecimal("5.00");
		}
		return calF;
	}

	public BigDecimal calculoD() {
		if (roControlesVista.getEstaDoc().equals("SI")) {
			calD = new BigDecimal("5.00");
		} else if (roControlesVista.getEstaDoc().equals("NO")) {
			calD = new BigDecimal("1.00");
		}
		return calD;
	}

	public BigDecimal calculoEF() {
		if (roControlesVista.getEstaFor().equals("SI")) {
			calEF = new BigDecimal("5.00");
		} else if (roControlesVista.getEstaFor().equals("NO")) {
			calEF = new BigDecimal("1.00");
		}
		return calEF;
	}

	public BigDecimal calculoSA() {
		if (roControlesVista.getSeAplica().equals("SI")) {
			calSA = new BigDecimal("5.00");
		} else if (roControlesVista.getSeAplica().equals("NO")) {
			calSA = new BigDecimal("1.00");
		}
		return calSA;
	}

	public void exitoGuardar() {
		roControlesVista = new RoControles();
		roProbabilidadEventoVista = new RoProbabilidadEvento();
		paramProbabilidadRiesgoVista = new ParamProbabilidadRiesgo();
		roResponsableVista = new RoResponsable();
		listaRoControles = servicioRoControles.buscarTodos();
		tipoGuardarControl = true;
		pnlRoTipoControl = false;
		btnAñadirControl = true;
	}

	public void guardarRoTipoControl() {
		if (tipoGuardarControl) {
			roControlesVista.setCodigoControles(new BigDecimal(0));
			roControlesVista
					.setRoProbabilidadEvento(servicioRoProbabilidadEvento
							.buscarPorId(idCalcProbSeleccionado));
			// roControlesVista.setImpactEcon(this.calcIm);
			roControlesVista.setCalcImpact(this.calcIm);
			roControlesVista.setProducto(this.mult);
			roControlesVista
					.setParamProbabilidadRiesgo(servicioParamProbabilidadRiesgo
							.buscarPorId(idTipoRiesgo));
			roControlesVista.setRoResponsable(servicioRoResponsable
					.buscarPorId(idResponsable));
			roControlesVista.setCalcEfect(new BigDecimal(this.valor));
			roControlesVista.setValorEfic(this.valoracion);
			roControlesVista.setValorControl(this.valoracionDelControl);
			roControlesVista.setCalcPerio(this.calP);
			roControlesVista.setCalcTipo(this.calT);
			roControlesVista.setCalcForm(this.calF);
			roControlesVista.setCalcEstaDocu(this.calD);
			roControlesVista.setCalcEstaFormal(this.calEF);
			roControlesVista.setCalcSeAplica(this.calSA);

			// Campos que siempre se va a repetir
			roControlesVista.setPromedioControl(new BigDecimal("2.00"));
			roControlesVista.setDenomControl("Prueba");
			roControlesVista.setRiesgoResi(new BigDecimal("2.00"));
			roControlesVista.setClasifiFinal("Prueba");

			servicioRoControles.insertar(roControlesVista);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Control Añadido",
					"El Control ha sido Añadido con Exito"));
			exitoGuardar();
		} else {
			roControlesVista.setCodigoControles(new BigDecimal(0));
			roControlesVista
					.setRoProbabilidadEvento(servicioRoProbabilidadEvento
							.buscarPorId(idCalcProbSeleccionado));
			// roControlesVista.setImpactEcon(this.calcIm);
			roControlesVista.setCalcImpact(this.calcIm);
			roControlesVista.setProducto(this.mult);
			roControlesVista
					.setParamProbabilidadRiesgo(servicioParamProbabilidadRiesgo
							.buscarPorId(idTipoRiesgo));
			roControlesVista.setRoResponsable(servicioRoResponsable
					.buscarPorId(idResponsable));
			roControlesVista.setCalcEfect(new BigDecimal(this.valor));
			roControlesVista.setValorEfic(this.valoracion);
			roControlesVista.setValorControl(this.valoracionDelControl);
			roControlesVista.setCalcPerio(this.calP);
			roControlesVista.setCalcTipo(this.calT);
			roControlesVista.setCalcForm(this.calF);
			roControlesVista.setCalcEstaDocu(this.calD);
			roControlesVista.setCalcEstaFormal(this.calEF);
			roControlesVista.setCalcSeAplica(this.calSA);

			// Campos que siempre se va a repetir
			roControlesVista.setPromedioControl(new BigDecimal("2.00"));
			roControlesVista.setDenomControl("Prueba");
			roControlesVista.setRiesgoResi(new BigDecimal("2.00"));
			roControlesVista.setClasifiFinal("Prueba");

			servicioRoControles.actualizar(roControlesVista);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					"Tipo de Control Actualizado",
					"El Control ha sido Añadido con Exito"));
			exitoGuardar();
		}
	}

	public void cancelarControl() {
		listaRoControles = servicioRoControles.buscarTodos();
		tipoGuardarControl = false;
		pnlRoTipoControl = false;
		btnAñadirControl = false;
	}

	public void editarRoControles() {
		tipoGuardarControl = false;
		pnlRoTipoControl = true;
		btnAñadirControl = true;
	}

	public void borrarControl() {
		try {
			servicioRoControles.eliminar(roControlesVista);
			listaRoControles = servicioRoControles.buscarTodos();
			pnlRoTipoControl = false;
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Control Eliminado",
					"El registro ha sido Eliminado con éxito"));
		} catch (Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error al eliminar:",
					"El Control está en uso"));
		}
	}

	public RoControles getRoControlesControlador() {
		return roControlesControlador;
	}

	public void setRoControlesControlador(RoControles roControlesControlador) {
		this.roControlesControlador = roControlesControlador;
	}

	public RoControles getRoControlesVista() {
		return roControlesVista;
	}

	public void setRoControlesVista(RoControles roControlesVista) {
		this.roControlesVista = roControlesVista;
	}

	public List<RoControles> getListaRoControles() {
		return listaRoControles;
	}

	public void setListaRoControles(List<RoControles> listaRoControles) {
		this.listaRoControles = listaRoControles;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoControlador() {
		return paramProbabilidadRiesgoControlador;
	}

	public void setParamProbabilidadRiesgoControlador(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoControlador) {
		this.paramProbabilidadRiesgoControlador = paramProbabilidadRiesgoControlador;
	}

	public ParamProbabilidadRiesgo getParamProbabilidadRiesgoVista() {
		return paramProbabilidadRiesgoVista;
	}

	public void setParamProbabilidadRiesgoVista(
			ParamProbabilidadRiesgo paramProbabilidadRiesgoVista) {
		this.paramProbabilidadRiesgoVista = paramProbabilidadRiesgoVista;
	}

	public List<ParamProbabilidadRiesgo> getListaParamProbabilidadRiesgo() {
		return listaParamProbabilidadRiesgo;
	}

	public void setListaParamProbabilidadRiesgo(
			List<ParamProbabilidadRiesgo> listaParamProbabilidadRiesgo) {
		this.listaParamProbabilidadRiesgo = listaParamProbabilidadRiesgo;
	}

	public RoProbabilidadEvento getRoProbabilidadEventoControlador() {
		return roProbabilidadEventoControlador;
	}

	public void setRoProbabilidadEventoControlador(
			RoProbabilidadEvento roProbabilidadEventoControlador) {
		this.roProbabilidadEventoControlador = roProbabilidadEventoControlador;
	}

	public RoProbabilidadEvento getRoProbabilidadEventoVista() {
		return roProbabilidadEventoVista;
	}

	public void setRoProbabilidadEventoVista(
			RoProbabilidadEvento roProbabilidadEventoVista) {
		this.roProbabilidadEventoVista = roProbabilidadEventoVista;
	}

	public List<RoProbabilidadEvento> getListaRoProbabilidadEvento() {
		return listaRoProbabilidadEvento;
	}

	public void setListaRoProbabilidadEvento(
			List<RoProbabilidadEvento> listaRoProbabilidadEvento) {
		this.listaRoProbabilidadEvento = listaRoProbabilidadEvento;
	}

	public RoResponsable getRoResponsableControlador() {
		return roResponsableControlador;
	}

	public void setRoResponsableControlador(
			RoResponsable roResponsableControlador) {
		this.roResponsableControlador = roResponsableControlador;
	}

	public RoResponsable getRoResponsableVista() {
		return roResponsableVista;
	}

	public void setRoResponsableVista(RoResponsable roResponsableVista) {
		this.roResponsableVista = roResponsableVista;
	}

	public List<RoResponsable> getListaRoResponsable() {
		return listaRoResponsable;
	}

	public void setListaRoResponsable(List<RoResponsable> listaRoResponsable) {
		this.listaRoResponsable = listaRoResponsable;
	}

	public boolean isPnlRoTipoControl() {
		return pnlRoTipoControl;
	}

	public void setPnlRoTipoControl(boolean pnlRoTipoControl) {
		this.pnlRoTipoControl = pnlRoTipoControl;
	}

	public boolean isBtnAñadirControl() {
		return btnAñadirControl;
	}

	public void setBtnAñadirControl(boolean btnAñadirControl) {
		this.btnAñadirControl = btnAñadirControl;
	}

	public boolean isBtnGuardarControl() {
		return btnGuardarControl;
	}

	public void setBtnGuardarControl(boolean btnGuardarControl) {
		this.btnGuardarControl = btnGuardarControl;
	}

	public boolean isBtnCancelarControl() {
		return btnCancelarControl;
	}

	public void setBtnCancelarControl(boolean btnCancelarControl) {
		this.btnCancelarControl = btnCancelarControl;
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

	public int getIdCalcProbSeleccionado() {
		return idCalcProbSeleccionado;
	}

	public void setIdCalcProbSeleccionado(int idCalcProbSeleccionado) {
		this.idCalcProbSeleccionado = idCalcProbSeleccionado;
	}

	public BigDecimal getCalcIm() {
		return calcIm;
	}

	public void setCalcIm(BigDecimal calcIm) {
		this.calcIm = calcIm;
	}

	public BigDecimal getMult() {
		return mult;
	}

	public void setMult(BigDecimal mult) {
		this.mult = mult;
	}

	public int getCalculoProbSeleccionado() {
		return calculoProbSeleccionado;
	}

	public void setCalculoProbSeleccionado(int calculoProbSeleccionado) {
		this.calculoProbSeleccionado = calculoProbSeleccionado;
	}

	public int getIdTipoRiesgo() {
		return idTipoRiesgo;
	}

	public void setIdTipoRiesgo(int idTipoRiesgo) {
		this.idTipoRiesgo = idTipoRiesgo;
	}

	public int getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(int idResponsable) {
		this.idResponsable = idResponsable;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public BigDecimal getValoracion() {
		return valoracion;
	}

	public void setValoracion(BigDecimal valoracion) {
		this.valoracion = valoracion;
	}

	public BigDecimal getValoracionDelControl() {
		return valoracionDelControl;
	}

	public void setValoracionDelControl(BigDecimal valoracionDelControl) {
		this.valoracionDelControl = valoracionDelControl;
	}

	public String getCalculoDenomDelControl() {
		return calculoDenomDelControl;
	}

	public void setCalculoDenomDelControl(String calculoDenomDelControl) {
		this.calculoDenomDelControl = calculoDenomDelControl;
	}

	public double getRiesgoResidual() {
		return riesgoResidual;
	}

	public void setRiesgoResidual(double riesgoResidual) {
		this.riesgoResidual = riesgoResidual;
	}

	public String getClasificacionFinalDelRiesgo() {
		return clasificacionFinalDelRiesgo;
	}

	public void setClasificacionFinalDelRiesgo(
			String clasificacionFinalDelRiesgo) {
		this.clasificacionFinalDelRiesgo = clasificacionFinalDelRiesgo;
	}

	public BigDecimal getCalP() {
		return calP;
	}

	public void setCalP(BigDecimal calP) {
		this.calP = calP;
	}

	public BigDecimal getCalT() {
		return calT;
	}

	public void setCalT(BigDecimal calT) {
		this.calT = calT;
	}

	public BigDecimal getCalF() {
		return calF;
	}

	public void setCalF(BigDecimal calF) {
		this.calF = calF;
	}

	public BigDecimal getCalD() {
		return calD;
	}

	public void setCalD(BigDecimal calD) {
		this.calD = calD;
	}

	public BigDecimal getCalEF() {
		return calEF;
	}

	public void setCalEF(BigDecimal calEF) {
		this.calEF = calEF;
	}

	public BigDecimal getCalSA() {
		return calSA;
	}

	public void setCalSA(BigDecimal calSA) {
		this.calSA = calSA;
	}

	public boolean isTipoGuardarControl() {
		return tipoGuardarControl;
	}

	public void setTipoGuardarControl(boolean tipoGuardarControl) {
		this.tipoGuardarControl = tipoGuardarControl;
	}



}
