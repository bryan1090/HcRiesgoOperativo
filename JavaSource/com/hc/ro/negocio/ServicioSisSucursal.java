package com.hc.ro.negocio;



import javax.ejb.Stateless;

import com.hc.ro.modelo.SisSucursal;

@Stateless
public class ServicioSisSucursal extends ServicioGenerico<SisSucursal>{
	public ServicioSisSucursal() {
		super(SisSucursal.class, ServicioSisSucursal.class);
		// TODO Auto-generated constructor stub
	}
	
	
}
