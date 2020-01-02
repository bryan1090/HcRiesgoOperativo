package com.hc.ro.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ro_det_val_cart_event database table.
 * 
 */
@Entity
@Table(name="ro_det_val_cart_event")
@NamedQuery(name="RoDetValCartEvent.findAll", query="SELECT r FROM RoDetValCartEvent r")
public class RoDetValCartEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int CODIGO_dvacev;

	
	private String VALOR_dvacev;

	//bi-directional many-to-one association to RoAttrbAdicionale
		@ManyToOne
		@JoinColumn(name="CODIGO_DCEV")
		private RoDetCarctEvent roDetCarctEvent;
	
	//bi-directional many-to-one association to RoAttrbAdicionale
	@ManyToOne
	@JoinColumn(name="COD_attr")
	private RoAttrbAdicionale roAttrbAdicionale;

	//bi-directional many-to-one association to RoValAttrb
	@ManyToOne
	@JoinColumn(name="CODIGO_val_attr")
	private RoValAttrb roValAttrb;

	public RoDetValCartEvent() {
	}

	public int getCODIGO_dvacev() {
		return this.CODIGO_dvacev;
	}

	public void setCODIGO_dvacev(int CODIGO_dvacev) {
		this.CODIGO_dvacev = CODIGO_dvacev;
	}

	public RoDetCarctEvent getRoDetCarctEvent() {
		return roDetCarctEvent;
	}

	public void setRoDetCarctEvent(RoDetCarctEvent roDetCarctEvent) {
		this.roDetCarctEvent = roDetCarctEvent;
	}

	public String getVALOR_dvacev() {
		return this.VALOR_dvacev;
	}

	public void setVALOR_dvacev(String VALOR_dvacev) {
		this.VALOR_dvacev = VALOR_dvacev;
	}

	public RoAttrbAdicionale getRoAttrbAdicionale() {
		return this.roAttrbAdicionale;
	}

	public void setRoAttrbAdicionale(RoAttrbAdicionale roAttrbAdicionale) {
		this.roAttrbAdicionale = roAttrbAdicionale;
	}

	public RoValAttrb getRoValAttrb() {
		return this.roValAttrb;
	}

	public void setRoValAttrb(RoValAttrb roValAttrb) {
		this.roValAttrb = roValAttrb;
	}

}