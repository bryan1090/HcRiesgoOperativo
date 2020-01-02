package com.hc.ro.negocio;

import javax.ejb.Stateless;

import com.hc.ro.modelo.GenEstado;

@Stateless
public class ServicioGenEstado extends ServicioGenerico<GenEstado>{
	
	public ServicioGenEstado() {
		super(GenEstado.class, ServicioGenEstado.class);
		// TODO Auto-generated constructor stub
	}
}
