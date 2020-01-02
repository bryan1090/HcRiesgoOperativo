package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gen_nivel_arbol database table.
 * 
 */
@Entity
@Table(name="gen_nivel_arbol")
@NamedQuery(name="GenNivelArbol.findAll", query="SELECT g FROM GenNivelArbol g")
public class GenNivelArbol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_GNIV")
	private int codigoGniv;

	@Column(name="NOMBRE_GNIV")
	private String nombreGniv;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to GenUbicGeo
	@OneToMany(mappedBy="genNivelArbol")
	private List<GenUbicGeo> genUbicGeos;

	//bi-directional many-to-one association to RoAgencia
	@OneToMany(mappedBy="genNivelArbol")
	private List<RoAgencia> roAgencias;

	//bi-directional many-to-one association to RoDepartamento
	@OneToMany(mappedBy="genNivelArbol")
	private List<RoDepartamento> roDepartamentos;

	//bi-directional many-to-one association to RoEvento
	@OneToMany(mappedBy="genNivelArbol")
	private List<RoEvento> roEventos;

	//bi-directional many-to-one association to RoNegocio
	@OneToMany(mappedBy="genNivelArbol")
	private List<RoNegocio> roNegocios;

	//bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy="genNivelArbol")
	private List<RoProceso> roProcesos;

	public GenNivelArbol() {
	}

	public int getCodigoGniv() {
		return this.codigoGniv;
	}

	public void setCodigoGniv(int codigoGniv) {
		this.codigoGniv = codigoGniv;
	}

	public String getNombreGniv() {
		return this.nombreGniv;
	}

	public void setNombreGniv(String nombreGniv) {
		this.nombreGniv = nombreGniv;
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

	public List<GenUbicGeo> getGenUbicGeos() {
		return this.genUbicGeos;
	}

	public void setGenUbicGeos(List<GenUbicGeo> genUbicGeos) {
		this.genUbicGeos = genUbicGeos;
	}

	public GenUbicGeo addGenUbicGeo(GenUbicGeo genUbicGeo) {
		getGenUbicGeos().add(genUbicGeo);
		genUbicGeo.setGenNivelArbol(this);

		return genUbicGeo;
	}

	public GenUbicGeo removeGenUbicGeo(GenUbicGeo genUbicGeo) {
		getGenUbicGeos().remove(genUbicGeo);
		genUbicGeo.setGenNivelArbol(null);

		return genUbicGeo;
	}

	public List<RoAgencia> getRoAgencias() {
		return this.roAgencias;
	}

	public void setRoAgencias(List<RoAgencia> roAgencias) {
		this.roAgencias = roAgencias;
	}

	public RoAgencia addRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().add(roAgencia);
		roAgencia.setGenNivelArbol(this);

		return roAgencia;
	}

	public RoAgencia removeRoAgencia(RoAgencia roAgencia) {
		getRoAgencias().remove(roAgencia);
		roAgencia.setGenNivelArbol(null);

		return roAgencia;
	}

	public List<RoDepartamento> getRoDepartamentos() {
		return this.roDepartamentos;
	}

	public void setRoDepartamentos(List<RoDepartamento> roDepartamentos) {
		this.roDepartamentos = roDepartamentos;
	}

	public RoDepartamento addRoDepartamento(RoDepartamento roDepartamento) {
		getRoDepartamentos().add(roDepartamento);
		roDepartamento.setGenNivelArbol(this);

		return roDepartamento;
	}

	public RoDepartamento removeRoDepartamento(RoDepartamento roDepartamento) {
		getRoDepartamentos().remove(roDepartamento);
		roDepartamento.setGenNivelArbol(null);

		return roDepartamento;
	}

	public List<RoEvento> getRoEventos() {
		return this.roEventos;
	}

	public void setRoEventos(List<RoEvento> roEventos) {
		this.roEventos = roEventos;
	}

	public RoEvento addRoEvento(RoEvento roEvento) {
		getRoEventos().add(roEvento);
		roEvento.setGenNivelArbol(this);

		return roEvento;
	}

	public RoEvento removeRoEvento(RoEvento roEvento) {
		getRoEventos().remove(roEvento);
		roEvento.setGenNivelArbol(null);

		return roEvento;
	}

	public List<RoNegocio> getRoNegocios() {
		return this.roNegocios;
	}

	public void setRoNegocios(List<RoNegocio> roNegocios) {
		this.roNegocios = roNegocios;
	}

	public RoNegocio addRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().add(roNegocio);
		roNegocio.setGenNivelArbol(this);

		return roNegocio;
	}

	public RoNegocio removeRoNegocio(RoNegocio roNegocio) {
		getRoNegocios().remove(roNegocio);
		roNegocio.setGenNivelArbol(null);

		return roNegocio;
	}

	public List<RoProceso> getRoProcesos() {
		return this.roProcesos;
	}

	public void setRoProcesos(List<RoProceso> roProcesos) {
		this.roProcesos = roProcesos;
	}

	public RoProceso addRoProceso(RoProceso roProceso) {
		getRoProcesos().add(roProceso);
		roProceso.setGenNivelArbol(this);

		return roProceso;
	}

	public RoProceso removeRoProceso(RoProceso roProceso) {
		getRoProcesos().remove(roProceso);
		roProceso.setGenNivelArbol(null);

		return roProceso;
	}

}