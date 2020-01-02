package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoControlEvento;
import com.hc.ro.modelo.RoDetalleEvento;

@Stateless
public class ServicioRoControlEvento extends ServicioGenerico<RoControlEvento> {

	public ServicioRoControlEvento() {
		super(RoControlEvento.class, ServicioRoControlEvento.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoControlEvento> buscarPorDetalleEvento(
			RoDetalleEvento detalleEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoControlEvento a WHERE a.roDetalleEvento LIKE :paramDetalleEvento");
		q.setParameter("paramDetalleEvento", detalleEvento);
		return q.getResultList();
	}
}
