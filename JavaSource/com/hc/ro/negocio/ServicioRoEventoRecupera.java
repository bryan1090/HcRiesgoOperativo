package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoEventoRecupera;
import com.hc.ro.modelo.RoDetalleEvento;

@Stateless
public class ServicioRoEventoRecupera extends ServicioGenerico<RoEventoRecupera> {

	public ServicioRoEventoRecupera() {
		super(RoEventoRecupera.class, ServicioRoEventoRecupera.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RoEventoRecupera> buscarPorDetalleEvento(RoDetalleEvento detalleEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoEventoRecupera a WHERE a.roDetalleEvento LIKE :paramDetalleEvento");
		q.setParameter("paramDetalleEvento", detalleEvento);
		return q.getResultList();
	}
}
