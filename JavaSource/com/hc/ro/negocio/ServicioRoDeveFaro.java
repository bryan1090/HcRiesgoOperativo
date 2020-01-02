package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoDeveFaro;

@Stateless
public class ServicioRoDeveFaro extends ServicioGenerico<RoDeveFaro> {

	public ServicioRoDeveFaro() {
		super(RoDeveFaro.class, ServicioRoDeveFaro.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoDeveFaro> buscarPorCodigoDeve(int codigoDeve) {
		Query q = em
				.createQuery("SELECT a FROM RoDeveFaro a WHERE a.roDetalleEvento.codigoDeve LIKE :param");
		q.setParameter("param", codigoDeve);
		return q.getResultList();
	}

	public List<Integer> buscarPorDeve(RoDetalleEvento deven) {
		TypedQuery<Integer> q = em
				.createQuery(
						"SELECT a.codigoDefa FROM RoDeveFaro a WHERE a.roDetalleEvento.codigoDeve LIKE :parametro",
						Integer.class);
		q.setParameter("parametro", deven.getCodigoDeve());
		return q.getResultList();
	}

	public List<RoDeveFaro> buscarPorDeveEntidad(RoDetalleEvento deven) {
		TypedQuery<RoDeveFaro> q = em
				.createQuery(
						"SELECT a FROM RoDeveFaro a WHERE a.roDetalleEvento.codigoDeve LIKE :parametro",
						RoDeveFaro.class);
		q.setParameter("parametro", deven.getCodigoDeve());
		return q.getResultList();
	}

}