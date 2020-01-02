package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoArchivoAccion;

@Stateless
public class ServicioRoArchivoAccion extends ServicioGenerico<RoArchivoAccion> {

	public ServicioRoArchivoAccion() {
		super(RoArchivoAccion.class, ServicioRoArchivoAccion.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoArchivoAccion> buscarPorIdAccion(int codigoAccion) {
		Query q = em
				.createQuery("SELECT a FROM RoArchivoAccion a WHERE a.roAccion.codigoAcci LIKE :paramDeve");
		q.setParameter("paramDeve", codigoAccion);

		return q.getResultList();

	}
}
