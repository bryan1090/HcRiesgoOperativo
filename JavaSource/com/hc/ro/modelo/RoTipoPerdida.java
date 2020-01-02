package com.hc.ro.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "ro_tipo_perdida")
@NamedQuery(name = "RoTipoPerdida.findAll", query = "SELECT r FROM RoTipoPerdida r")
public class RoTipoPerdida implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CODIGO_TIPE")
	private int codigoTipe;

	@Pattern(message = "El campo Nombre solo debe contener letras y números", regexp = "^[A-Za-z0-9ÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_TIPE")
	private String nombreTipe;

	@Column(name = "DESCRIPCION_TIPE")
	private String descripcionTipe;

	@Column(name = "TIPO_REGISTRO")
	private int tipoRegistro;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	// bi-directional many-to-one association to RoEventoPerdida
	@OneToMany(mappedBy = "roTipoPerdida")
	private List<RoDetalleEvento> roDetalleEventos;

	public RoTipoPerdida() {
		super();
	}

	public RoTipoPerdida(int codigoTipe, String nombreTipe) {
		super();
		this.codigoTipe = codigoTipe;
		this.nombreTipe = nombreTipe;
	}

	public int getCodigoTipe() {
		return codigoTipe;
	}

	public void setCodigoTipe(int codigoTipe) {
		this.codigoTipe = codigoTipe;
	}

	public String getNombreTipe() {
		return nombreTipe;
	}

	public void setNombreTipe(String nombreTipe) {
		this.nombreTipe = nombreTipe;
	}

	public String getDescripcionTipe() {
		return descripcionTipe;
	}

	public void setDescripcionTipe(String descripcionTipe) {
		this.descripcionTipe = descripcionTipe;
	}

	public GenEstado getGenEstado() {
		return genEstado;
	}

	public void setGenEstado(GenEstado genEstado) {
		this.genEstado = genEstado;
	}

	public List<RoDetalleEvento> getRoDetalleEventos() {
		return roDetalleEventos;
	}

	public void setRoDetalleEventos(List<RoDetalleEvento> roDetalleEventos) {
		this.roDetalleEventos = roDetalleEventos;
	}

	public int getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(int tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

}
