package com.hc.ro.negocio;

import javax.ejb.Stateless;

import com.hc.ro.modelo.GenMoneda;

@Stateless
public class ServicioGenMoneda extends ServicioGenerico<GenMoneda> {

	public ServicioGenMoneda() {
		super(GenMoneda.class, ServicioGenMoneda.class);
		// TODO Auto-generated constructor stub
	}

}
