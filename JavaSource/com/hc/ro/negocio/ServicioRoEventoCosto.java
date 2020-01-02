package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoEventoCosto;
import com.hc.ro.modelo.RoDetalleEvento;

@Stateless
public class ServicioRoEventoCosto extends ServicioGenerico<RoEventoCosto> {

	public ServicioRoEventoCosto() {
		super(RoEventoCosto.class, ServicioRoEventoCosto.class);
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RoEventoCosto> buscarPorDetalleEvento(RoDetalleEvento detalleEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoEventoCosto a WHERE a.roDetalleEvento LIKE :paramDetalleEvento");
		q.setParameter("paramDetalleEvento", detalleEvento);
		return q.getResultList();
	}
}
