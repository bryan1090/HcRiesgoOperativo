package com.hc.ro.negocio;

import javax.ejb.Stateless;

import com.hc.ro.modelo.GenNivelArbol;

@Stateless
public class ServicioGenNivelArbol extends ServicioGenerico<GenNivelArbol>{

	public ServicioGenNivelArbol() {
		super(GenNivelArbol.class, ServicioGenNivelArbol.class);
		// TODO Auto-generated constructor stub
	}

}
