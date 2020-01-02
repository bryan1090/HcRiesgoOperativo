package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gen_periodo database table.
 * 
 */
@Entity
@Table(name="gen_periodo")
@NamedQuery(name="GenPeriodo.findAll", query="SELECT g FROM GenPeriodo g")
public class GenPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_PERI")
	private int codigoPeri;

	@Column(name="NOMBRE_PERI")
	private String nombrePeri;

	//bi-directional many-to-one association to GenDetallePeriodo
	@OneToMany(mappedBy="genPeriodo")
	private List<GenDetallePeriodo> genDetallePeriodos;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	public GenPeriodo() {
	}

	public int getCodigoPeri() {
		return this.codigoPeri;
	}

	public void setCodigoPeri(int codigoPeri) {
		this.codigoPeri = codigoPeri;
	}

	public String getNombrePeri() {
		return this.nombrePeri;
	}

	public void setNombrePeri(String nombrePeri) {
		this.nombrePeri = nombrePeri;
	}

	public List<GenDetallePeriodo> getGenDetallePeriodos() {
		return this.genDetallePeriodos;
	}

	public void setGenDetallePeriodos(List<GenDetallePeriodo> genDetallePeriodos) {
		this.genDetallePeriodos = genDetallePeriodos;
	}

	public GenDetallePeriodo addGenDetallePeriodo(GenDetallePeriodo genDetallePeriodo) {
		getGenDetallePeriodos().add(genDetallePeriodo);
		genDetallePeriodo.setGenPeriodo(this);

		return genDetallePeriodo;
	}

	public GenDetallePeriodo removeGenDetallePeriodo(GenDetallePeriodo genDetallePeriodo) {
		getGenDetallePeriodos().remove(genDetallePeriodo);
		genDetallePeriodo.setGenPeriodo(null);

		return genDetallePeriodo;
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

}