package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the ro_tipo_costos database table.
 * 
 */
@Entity
@Table(name = "ro_tipo_costos")
@NamedQuery(name = "RoTipoCosto.findAll", query = "SELECT r FROM RoTipoCosto r")
public class RoTipoCosto implements Serializable {

	public RoTipoCosto(int codigoTico, String nombreTico) {
		super();
		this.codigoTico = codigoTico;
		this.nombreTico = nombreTico;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_TICO")
	private int codigoTico;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_TICO")
	private Date fechaTico;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_TICO")
	private String nombreTico;

	@Min(value = 0, message = "El campo valor no debe ser menor a 0")
	@Column(name = "VALOR_TICO")
	private float valorTico;

	// bi-directional many-to-one association to RoEventoCosto
	@OneToMany(mappedBy = "roTipoCosto")
	private List<RoEventoCosto> roEventoCostos;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	public RoTipoCosto() {
	}

	public int getCodigoTico() {
		return this.codigoTico;
	}

	public void setCodigoTico(int codigoTico) {
		this.codigoTico = codigoTico;
	}

	public Date getFechaTico() {
		return this.fechaTico;
	}

	public void setFechaTico(Date fechaTico) {
		this.fechaTico = fechaTico;
	}

	public String getNombreTico() {
		return this.nombreTico;
	}

	public void setNombreTico(String nombreTico) {
		this.nombreTico = nombreTico;
	}

	public List<RoEventoCosto> getRoEventoCostos() {
		return this.roEventoCostos;
	}

	public void setRoEventoCostos(List<RoEventoCosto> roEventoCostos) {
		this.roEventoCostos = roEventoCostos;
	}

	public RoEventoCosto addRoEventoCosto(RoEventoCosto roEventoCosto) {
		getRoEventoCostos().add(roEventoCosto);
		roEventoCosto.setRoTipoCosto(this);

		return roEventoCosto;
	}

	public RoEventoCosto removeRoEventoCosto(RoEventoCosto roEventoCosto) {
		getRoEventoCostos().remove(roEventoCosto);
		roEventoCosto.setRoTipoCosto(null);

		return roEventoCosto;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public GenEstado getGenEstado() {
		return this.genEstado;
	}

	public void setGenEstado(GenEstado genEstado) {
		this.genEstado = genEstado;
	}

	public float getValorTico() {
		return valorTico;
	}

	public void setValorTico(float valorTico) {
		this.valorTico = valorTico;
	}

}