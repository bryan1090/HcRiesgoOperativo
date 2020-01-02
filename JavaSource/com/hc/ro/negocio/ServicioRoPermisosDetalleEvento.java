package com.hc.ro.negocio;


import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoPermisosDetalleEvento;

@Stateless
public class ServicioRoPermisosDetalleEvento extends
		ServicioGenerico<RoPermisosDetalleEvento> {

	public ServicioRoPermisosDetalleEvento() {
		super(RoPermisosDetalleEvento.class,
				ServicioRoPermisosDetalleEvento.class);
		// TODO Auto-generated constructor stub
	}

	public RoPermisosDetalleEvento buscarPermisoPorPerfil(String nombrePerfil) {
		Query q = em
				.createQuery("SELECT a FROM RoPermisosDetalleEvento a WHERE a.sisPerfil.nombrePerf LIKE :paramNombrePerfil");
		q.setParameter("paramNombrePerfil", nombrePerfil);
		return (RoPermisosDetalleEvento) q.getSingleResult();

	}
}
