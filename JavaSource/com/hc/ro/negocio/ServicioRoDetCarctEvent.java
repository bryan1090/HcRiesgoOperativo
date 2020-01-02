package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoDetCarctEvent;
import com.hc.ro.modelo.RoDetalleEvento;

@Stateless
public class ServicioRoDetCarctEvent extends ServicioGenerico<RoDetCarctEvent> {

	public ServicioRoDetCarctEvent() {
		super(RoDetCarctEvent.class, ServicioRoDetCarctEvent.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RoDetCarctEvent> buscarPorDetalleEvento(RoDetalleEvento detalleEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCarctEvent a WHERE a.roDetalleEvento LIKE :paramDetalleEvento");
		q.setParameter("paramDetalleEvento", detalleEvento);
		return q.getResultList();
	}
}
