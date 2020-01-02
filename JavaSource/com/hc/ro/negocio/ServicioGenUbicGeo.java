package com.hc.ro.negocio;

import javax.ejb.Stateless;

import com.hc.ro.modelo.GenUbicGeo;

@Stateless
public class ServicioGenUbicGeo extends ServicioGenerico<GenUbicGeo>{
	public ServicioGenUbicGeo() {
		super(GenUbicGeo.class, ServicioGenUbicGeo.class);
		// TODO Auto-generated constructor stub
	}
}
