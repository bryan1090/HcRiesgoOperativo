package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gen_nivel_proceso database table.
 * 
 */
@Entity
@Table(name="gen_nivel_proceso")
@NamedQuery(name="GenNivelProceso.findAll", query="SELECT g FROM GenNivelProceso g")
public class GenNivelProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_GNIP")
	private int codigoGnip;

	@Column(name="NOMBRE_GNIP")
	private String nombreGnip;

	//bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name="CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	//bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name="CODIGO_SUCU")
	private SisSucursal sisSucursal;

	//bi-directional many-to-one association to RoProceso
	@OneToMany(mappedBy="genNivelProceso")
	private List<RoProceso> roProcesos;

	public GenNivelProceso() {
	}

	public int getCodigoGnip() {
		return this.codigoGnip;
	}

	public void setCodigoGnip(int codigoGnip) {
		this.codigoGnip = codigoGnip;
	}

	public String getNombreGnip() {
		return this.nombreGnip;
	}

	public void setNombreGnip(String nombreGnip) {
		this.nombreGnip = nombreGnip;
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

	public List<RoProceso> getRoProcesos() {
		return this.roProcesos;
	}

	public void setRoProcesos(List<RoProceso> roProcesos) {
		this.roProcesos = roProcesos;
	}

}