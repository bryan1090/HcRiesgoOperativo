package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the ro_resp_pros database table.
 * 
 */
@Entity
@Table(name="ro_resp_pros")
@NamedQuery(name="RoRespPro.findAll", query = "SELECT r FROM RoRespPro r")
public class RoRespPro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_RESP_PROS")
	private int CODIGO_resp_pros;

	// bi-directional many-to-one association to Proceso
	@ManyToOne 
	@JoinColumn(name = "CODIGO_PROS")
	private RoProceso roProceso;

	// bi-directional many-to-one association to Responsable
	@ManyToOne 
	@JoinColumn(name = "CODIGO_RESP")
	private RoResponsable roResponsable;
	
	public RoRespPro() {
	}
	
	public RoRespPro(Integer integer, RoProceso roProceso2) {
		this.CODIGO_resp_pros = integer;
		this.roProceso = roProceso2;
	}

	public RoProceso getRoProceso() {
		return roProceso;
	}

	public void setRoProceso(RoProceso roProceso) {
		this.roProceso = roProceso;
	}

	public RoResponsable getRoResponsable() {
		return roResponsable;
	}

	public void setRoResponsable(RoResponsable roResponsable) {
		this.roResponsable = roResponsable;
	}	

	public int getCODIGO_resp_pros() {
		return this.CODIGO_resp_pros;
	}

	public void setCODIGO_resp_pros(int CODIGO_resp_pros) {
		this.CODIGO_resp_pros = CODIGO_resp_pros;
	}

}