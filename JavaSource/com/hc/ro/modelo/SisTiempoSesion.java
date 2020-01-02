package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="SIS_TIEMPO_SESION")
@NamedQuery(name="SisTiempoSesion.findAll", query="SELECT s FROM SisTiempoSesion s")
public class SisTiempoSesion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_SESION")
	private int codigoSesion;

	@Column(name="TIEMPO_SESION")
	private String tiempoSesion;

	public SisTiempoSesion() {
	}
	
	public SisTiempoSesion(String tiempoSesion) {
		super();
		this.tiempoSesion = tiempoSesion;
	}	
	
	public int getCodigoSesion() {
		return this.codigoSesion;
	}
		
	public void setCodigoSesion(int codigoSesion) {
		this.codigoSesion = codigoSesion;
	}
	
	public String getTiempoSesion() {
		return tiempoSesion;
	}

	public void setTiempoSesion(String tiempoSesion) {
		this.tiempoSesion = tiempoSesion;
	}


	

}