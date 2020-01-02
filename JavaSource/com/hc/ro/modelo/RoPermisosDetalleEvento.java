package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ro_detalle_negocio database table.
 * 
 */
@Entity
@Table(name = "ro_permisos_detalle_evento")
@NamedQuery(name = "RoPermisosDetalleEvento.findAll", query = "SELECT r FROM RoPermisosDetalleEvento r")
public class RoPermisosDetalleEvento implements Serializable {
	public RoPermisosDetalleEvento() {
	}

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODIGO_PEDE")
	private int codigoPede;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_PERF")
	private SisPerfil sisPerfil;

	@Column(name = "COL_CODIGO")
	private String colCodigo;

	//@Column(name = "COL_INDICADOR_RIESGO")
	//private String colIndicadorRiesgo;

	@Column(name = "COL_AGENCIA")
	private String colAgencia;

	@Column(name = "COL_EVENTO")
	private String colEvento;

	@Column(name = "COL_PROCESO")
	private String colProceso;
	
	@Column(name = "COL_SUBPROCESO")
	private String colSubProceso;

	@Column(name = "COL_MONEDA")
	private String colMoneda;

	@Column(name = "COL_NEGOCIO")
	private String colNegocio;

	@Column(name = "COL_DEPARTAMENTO")
	private String colDepartamento;

	@Column(name = "COL_FACTOR_RIESGO")
	private String colFactorRiesgo;

	@Column(name = "COL_VALOR")
	private String colValor;

	@Column(name = "COL_PTO_CONTROL")
	private String colPtoControl;

	@Column(name = "COL_MONTO")
	private String colMonto;

	@Column(name = "COL_VALOR_CAMBIO")
	private String colValorCambio;

	@Column(name = "COL_FECHA_OCURRENCIA")
	private String colFechaOcurrencia;

	@Column(name = "COL_FECHA_DESCUBRIMIENTO")
	private String colFechaDescubrimiento;

	@Column(name = "COL_FECHA_REGISTRO")
	private String colFechaRegistro;

	@Column(name = "COL_COSTO")
	private String colCosto;

	@Column(name = "COL_REAL")
	private String colReal;

	@Column(name = "COL_CUENTA")
	private String colCuenta;

	@Column(name = "COL_OTROS_GASTOS")
	private String colOtrosGastos;

	@Column(name = "COL_OP_EDICION")
	private String colOpEdicion;

	@Column(name = "COL_BTN_EDITAR")
	private String colBtnEditar;

	@Column(name = "COL_TIPO_PERDIDA")
	private String colTipoPerdida;

	@Column(name = "COL_CAUSA_PROBABLE")
	private String colCausaProbable;

	@Column(name = "COL_DESCRIPCION")
	private String colDescripcion;

	@Column(name = "COL_DESCRIPCION_DETALLADA")
	private String colDescripcionDetallada;

	@Column(name = "COL_BLOQUEO")
	private String colBloqueo;

	@Column(name = "COL_RECUP_REAL")
	private String colRecupReal;

	@Column(name = "COL_PERD_RESIDUAL")
	private String colPerdResidual;

	@Column(name = "COL_USUARIO")
	private String colUsuario;

	@Column(name = "COL_PANEL_ADICIONALES")
	private String colPanelAdicionales;

	@Column(name = "COL_PANEL_COSTOS")
	private String colPanelCostos;

	@Column(name = "COL_NUMOCUR")
	private String colNumOcur;
	
	@Column(name = "COL_PANEL_RECUPERACIONES")
	private String colPanelRecuperaciones;

	@Column(name = "COL_PANEL_INDICADORES")
	private String colPanelIndicadores;
	
	@Column(name = "DIS_PANEL_CUALITATIVO")
	private String disPanelCualitativo;

	@Column(name = "COL_PANEL_CUALITATIVO")
	private String colPanelCualitativo;
	
	@Column(name = "COL_PROMEDIO")
	private String colPromedio;

	@Column(name = "DIS_CODIGO")
	private String disCodigo;

	@Column(name = "DIS_AGENCIA")
	private String disAgencia;

	@Column(name = "DIS_EVENTO")
	private String disEvento;

	@Column(name = "DIS_PROCESO")
	private String disProceso;
	
	@Column(name = "DIS_SUBPROCESO")
	private String disSubProceso;

	@Column(name = "DIS_MONEDA")
	private String disMoneda;

	@Column(name = "DIS_NEGOCIO")
	private String disNegocio;

	@Column(name = "DIS_DEPARTAMENTO")
	private String disDepartamento;

	@Column(name = "DIS_FACTOR_RIESGO")
	private String disFactorRiesgo;

	@Column(name = "DIS_VALOR")
	private String disValor;

	@Column(name = "DIS_PTO_CONTROL")
	private String disPtoControl;

	@Column(name = "DIS_MONTO")
	private String disMonto;

	@Column(name = "DIS_VALOR_CAMBIO")
	private String disValorCambio;

	@Column(name = "DIS_FECHA_OCURRENCIA")
	private String disFechaOcurrencia;

	@Column(name = "DIS_FECHA_DESCUBRIMIENTO")
	private String disFechaDescubrimiento;

	@Column(name = "DIS_FECHA_REGISTRO")
	private String disFechaRegistro;

	@Column(name = "DIS_COSTO")
	private String disCosto;

	@Column(name = "DIS_REAL")
	private String disReal;

	@Column(name = "DIS_CUENTA")
	private String disCuenta;

	@Column(name = "DIS_OTROS_GASTOS")
	private String disOtrosGastos;

	@Column(name = "DIS_OP_EDICION")
	private String disOpEdicion;

	@Column(name = "DIS_BTN_EDITAR")
	private String disBtnEditar;

	@Column(name = "DIS_TIPO_PERDIDA")
	private String disTipoPerdida;

	@Column(name = "DIS_CAUSA_PROBABLE")
	private String disCausaProbable;

	@Column(name = "DIS_DESCRIPCION")
	private String disDescripcion;

	@Column(name = "DIS_DESCRIPCION_DETALLADA")
	private String disDescripcionDetallada;

	@Column(name = "DIS_BLOQUEO")
	private String disBloqueo;

	@Column(name = "DIS_RECUP_REAL")
	private String disRecupReal;

	@Column(name = "DIS_PERD_RESIDUAL")
	private String disPerdResidual;

	@Column(name = "DIS_USUARIO")
	private String disUsuario;

	@Column(name = "DIS_PANEL_ADICIONALES")
	private String disPanelAdicionales;

	@Column(name = "DIS_PANEL_COSTOS")
	private String disPanelCostos;

	@Column(name = "DIS_PANEL_RECUPERACIONES")
	private String disPanelRecuperaciones;
	
	@Column(name = "DIS_PANEL_INDICADORES")
	private String disPanelIndicadores;
	
	@Column(name = "DIS_NUMOCUR")
	private String disNumOcur;

	public SisPerfil getSisPerfil() {
		return sisPerfil;
	}

	public void setSisPerfil(SisPerfil sisPerfil) {
		this.sisPerfil = sisPerfil;
	}

	public String getColCodigo() {
		return colCodigo;
	}

	public void setColCodigo(String colCodigo) {
		this.colCodigo = colCodigo;
	}

	public String getColAgencia() {
		return colAgencia;
	}

	public void setColAgencia(String colAgencia) {
		this.colAgencia = colAgencia;
	}

	public String getColEvento() {
		return colEvento;
	}

	public void setColEvento(String colEvento) {
		this.colEvento = colEvento;
	}

	public String getColProceso() {
		return colProceso;
	}

	public void setColProceso(String colProceso) {
		this.colProceso = colProceso;
	}

	public String getColMoneda() {
		return colMoneda;
	}

	public void setColMoneda(String colMoneda) {
		this.colMoneda = colMoneda;
	}

	public String getColNegocio() {
		return colNegocio;
	}

	public void setColNegocio(String colNegocio) {
		this.colNegocio = colNegocio;
	}

	public String getColDepartamento() {
		return colDepartamento;
	}

	public void setColDepartamento(String colDepartamento) {
		this.colDepartamento = colDepartamento;
	}

	public String getColFactorRiesgo() {
		return colFactorRiesgo;
	}

	public void setColFactorRiesgo(String colFactorRiesgo) {
		this.colFactorRiesgo = colFactorRiesgo;
	}

	public String getColValor() {
		return colValor;
	}

	public void setColValor(String colValor) {
		this.colValor = colValor;
	}

	public String getColPtoControl() {
		return colPtoControl;
	}

	public void setColPtoControl(String colPtoControl) {
		this.colPtoControl = colPtoControl;
	}

	public String getColMonto() {
		return colMonto;
	}

	public void setColMonto(String colMonto) {
		this.colMonto = colMonto;
	}

	public String getColValorCambio() {
		return colValorCambio;
	}

	public void setColValorCambio(String colValorCambio) {
		this.colValorCambio = colValorCambio;
	}

	public String getColFechaOcurrencia() {
		return colFechaOcurrencia;
	}

	public void setColFechaOcurrencia(String colFechaOcurrencia) {
		this.colFechaOcurrencia = colFechaOcurrencia;
	}

	public String getColFechaDescubrimiento() {
		return colFechaDescubrimiento;
	}

	public void setColFechaDescubrimiento(String colFechaDescubrimiento) {
		this.colFechaDescubrimiento = colFechaDescubrimiento;
	}

	public String getColFechaRegistro() {
		return colFechaRegistro;
	}

	public void setColFechaRegistro(String colFechaRegistro) {
		this.colFechaRegistro = colFechaRegistro;
	}

	public String getColCosto() {
		return colCosto;
	}

	public void setColCosto(String colCosto) {
		this.colCosto = colCosto;
	}

	public String getColReal() {
		return colReal;
	}

	public void setColReal(String colReal) {
		this.colReal = colReal;
	}

	public String getColCuenta() {
		return colCuenta;
	}

	public void setColCuenta(String colCuenta) {
		this.colCuenta = colCuenta;
	}

	public String getColOtrosGastos() {
		return colOtrosGastos;
	}

	public void setColOtrosGastos(String colOtrosGastos) {
		this.colOtrosGastos = colOtrosGastos;
	}

	public String getColOpEdicion() {
		return colOpEdicion;
	}

	public void setColOpEdicion(String colOpEdicion) {
		this.colOpEdicion = colOpEdicion;
	}

	public String getColBtnEditar() {
		return colBtnEditar;
	}

	public void setColBtnEditar(String colBtnEditar) {
		this.colBtnEditar = colBtnEditar;
	}

	public String getColTipoPerdida() {
		return colTipoPerdida;
	}

	public void setColTipoPerdida(String colTipoPerdida) {
		this.colTipoPerdida = colTipoPerdida;
	}

	public String getColCausaProbable() {
		return colCausaProbable;
	}

	public void setColCausaProbable(String colCausaProbable) {
		this.colCausaProbable = colCausaProbable;
	}

	public String getColDescripcion() {
		return colDescripcion;
	}

	public void setColDescripcion(String colDescripcion) {
		this.colDescripcion = colDescripcion;
	}

	public String getColDescripcionDetallada() {
		return colDescripcionDetallada;
	}

	public void setColDescripcionDetallada(String colDescripcionDetallada) {
		this.colDescripcionDetallada = colDescripcionDetallada;
	}

	public String getColBloqueo() {
		return colBloqueo;
	}

	public void setColBloqueo(String colBloqueo) {
		this.colBloqueo = colBloqueo;
	}

	public String getColRecupReal() {
		return colRecupReal;
	}

	public void setColRecupReal(String colRecupReal) {
		this.colRecupReal = colRecupReal;
	}

	public String getColPerdResidual() {
		return colPerdResidual;
	}

	public void setColPerdResidual(String colPerdResidual) {
		this.colPerdResidual = colPerdResidual;
	}

	public String getColUsuario() {
		return colUsuario;
	}

	public void setColUsuario(String colUsuario) {
		this.colUsuario = colUsuario;
	}

	public String getColPanelAdicionales() {
		return colPanelAdicionales;
	}

	public void setColPanelAdicionales(String colPanelAdicionales) {
		this.colPanelAdicionales = colPanelAdicionales;
	}

	public String getColPanelCostos() {
		return colPanelCostos;
	}

	public void setColPanelCostos(String colPanelCostos) {
		this.colPanelCostos = colPanelCostos;
	}

	public String getColPanelRecuperaciones() {
		return colPanelRecuperaciones;
	}

	public void setColPanelRecuperaciones(String colPanelRecuperaciones) {
		this.colPanelRecuperaciones = colPanelRecuperaciones;
	}

	public String getDisCodigo() {
		return disCodigo;
	}

	public void setDisCodigo(String disCodigo) {
		this.disCodigo = disCodigo;
	}

	public String getDisAgencia() {
		return disAgencia;
	}

	public void setDisAgencia(String disAgencia) {
		this.disAgencia = disAgencia;
	}

	public String getDisEvento() {
		return disEvento;
	}

	public void setDisEvento(String disEvento) {
		this.disEvento = disEvento;
	}

	public String getDisProceso() {
		return disProceso;
	}

	public void setDisProceso(String disProceso) {
		this.disProceso = disProceso;
	}

	public String getDisMoneda() {
		return disMoneda;
	}

	public void setDisMoneda(String disMoneda) {
		this.disMoneda = disMoneda;
	}

	public String getDisNegocio() {
		return disNegocio;
	}

	public void setDisNegocio(String disNegocio) {
		this.disNegocio = disNegocio;
	}

	public String getDisDepartamento() {
		return disDepartamento;
	}

	public void setDisDepartamento(String disDepartamento) {
		this.disDepartamento = disDepartamento;
	}

	public String getDisFactorRiesgo() {
		return disFactorRiesgo;
	}

	public void setDisFactorRiesgo(String disFactorRiesgo) {
		this.disFactorRiesgo = disFactorRiesgo;
	}

	public String getDisValor() {
		return disValor;
	}

	public void setDisValor(String disValor) {
		this.disValor = disValor;
	}

	public String getDisPtoControl() {
		return disPtoControl;
	}

	public void setDisPtoControl(String disPtoControl) {
		this.disPtoControl = disPtoControl;
	}

	public String getDisMonto() {
		return disMonto;
	}

	public void setDisMonto(String disMonto) {
		this.disMonto = disMonto;
	}

	public String getDisValorCambio() {
		return disValorCambio;
	}

	public void setDisValorCambio(String disValorCambio) {
		this.disValorCambio = disValorCambio;
	}

	public String getDisFechaOcurrencia() {
		return disFechaOcurrencia;
	}

	public void setDisFechaOcurrencia(String disFechaOcurrencia) {
		this.disFechaOcurrencia = disFechaOcurrencia;
	}

	public String getDisFechaDescubrimiento() {
		return disFechaDescubrimiento;
	}

	public void setDisFechaDescubrimiento(String disFechaDescubrimiento) {
		this.disFechaDescubrimiento = disFechaDescubrimiento;
	}

	public String getDisFechaRegistro() {
		return disFechaRegistro;
	}

	public void setDisFechaRegistro(String disFechaRegistro) {
		this.disFechaRegistro = disFechaRegistro;
	}

	public String getDisCosto() {
		return disCosto;
	}

	public void setDisCosto(String disCosto) {
		this.disCosto = disCosto;
	}

	public String getDisReal() {
		return disReal;
	}

	public void setDisReal(String disReal) {
		this.disReal = disReal;
	}

	public String getDisCuenta() {
		return disCuenta;
	}

	public void setDisCuenta(String disCuenta) {
		this.disCuenta = disCuenta;
	}

	public String getDisOtrosGastos() {
		return disOtrosGastos;
	}

	public void setDisOtrosGastos(String disOtrosGastos) {
		this.disOtrosGastos = disOtrosGastos;
	}

	public String getDisOpEdicion() {
		return disOpEdicion;
	}

	public void setDisOpEdicion(String disOpEdicion) {
		this.disOpEdicion = disOpEdicion;
	}

	public String getDisBtnEditar() {
		return disBtnEditar;
	}

	public void setDisBtnEditar(String disBtnEditar) {
		this.disBtnEditar = disBtnEditar;
	}

	public String getDisTipoPerdida() {
		return disTipoPerdida;
	}

	public void setDisTipoPerdida(String disTipoPerdida) {
		this.disTipoPerdida = disTipoPerdida;
	}

	public String getDisCausaProbable() {
		return disCausaProbable;
	}

	public void setDisCausaProbable(String disCausaProbable) {
		this.disCausaProbable = disCausaProbable;
	}

	public String getDisDescripcion() {
		return disDescripcion;
	}

	public void setDisDescripcion(String disDescripcion) {
		this.disDescripcion = disDescripcion;
	}

	public String getDisDescripcionDetallada() {
		return disDescripcionDetallada;
	}

	public void setDisDescripcionDetallada(String disDescripcionDetallada) {
		this.disDescripcionDetallada = disDescripcionDetallada;
	}

	public String getDisBloqueo() {
		return disBloqueo;
	}

	public void setDisBloqueo(String disBloqueo) {
		this.disBloqueo = disBloqueo;
	}

	public String getDisRecupReal() {
		return disRecupReal;
	}

	public void setDisRecupReal(String disRecupReal) {
		this.disRecupReal = disRecupReal;
	}

	public String getDisPerdResidual() {
		return disPerdResidual;
	}

	public void setDisPerdResidual(String disPerdResidual) {
		this.disPerdResidual = disPerdResidual;
	}

	public String getDisUsuario() {
		return disUsuario;
	}

	public void setDisUsuario(String disUsuario) {
		this.disUsuario = disUsuario;
	}

	public String getDisPanelAdicionales() {
		return disPanelAdicionales;
	}

	public void setDisPanelAdicionales(String disPanelAdicionales) {
		this.disPanelAdicionales = disPanelAdicionales;
	}

	public String getDisPanelCostos() {
		return disPanelCostos;
	}

	public void setDisPanelCostos(String disPanelCostos) {
		this.disPanelCostos = disPanelCostos;
	}

	public String getDisPanelRecuperaciones() {
		return disPanelRecuperaciones;
	}

	public void setDisPanelRecuperaciones(String disPanelRecuperaciones) {
		this.disPanelRecuperaciones = disPanelRecuperaciones;
	}

	public String getDisPanelCualitativo() {
		return disPanelCualitativo;
	}

	public void setDisPanelCualitativo(String disPanelCualitativo) {
		this.disPanelCualitativo = disPanelCualitativo;
	}

	public String getColPanelCualitativo() {
		return colPanelCualitativo;
	}

	public void setColPanelCualitativo(String colPanelCualitativo) {
		this.colPanelCualitativo = colPanelCualitativo;
	}

	public String getColSubProceso() {
		return colSubProceso;
	}

	public void setColSubProceso(String colSubProceso) {
		this.colSubProceso = colSubProceso;
	}

	public String getDisSubProceso() {
		return disSubProceso;
	}

	public void setDisSubProceso(String disSubProceso) {
		this.disSubProceso = disSubProceso;
	}

	public String getColPromedio() {
		return colPromedio;
	}

	public void setColPromedio(String colPromedio) {
		this.colPromedio = colPromedio;
	}

	public String getColNumOcur() {
		return colNumOcur;
	}

	public void setColNumOcur(String colNumOcur) {
		this.colNumOcur = colNumOcur;
	}

	public String getDisNumOcur() {
		return disNumOcur;
	}

	public void setDisNumOcur(String disNumOcur) {
		this.disNumOcur = disNumOcur;
	}

	public String getColPanelIndicadores() {
		return colPanelIndicadores;
	}

	public void setColPanelIndicadores(String colPanelIndicadores) {
		this.colPanelIndicadores = colPanelIndicadores;
	}

	public String getDisPanelIndicadores() {
		return disPanelIndicadores;
	}

	public void setDisPanelIndicadores(String disPanelIndicadores) {
		this.disPanelIndicadores = disPanelIndicadores;
	}

//	public String getColIndicadorRiesgo() {
//		return colIndicadorRiesgo;
//	}
//
//	public void setColIndicadorRiesgo(String colIndicadorRiesgo) {
//		this.colIndicadorRiesgo = colIndicadorRiesgo;
//	}

}