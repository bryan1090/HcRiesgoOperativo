package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoFrecEjecucion;

@Stateless
public class ServicioRoFrecEjecucion extends ServicioGenerico<RoFrecEjecucion> {
	public ServicioRoFrecEjecucion() {
		super(RoFrecEjecucion.class, ServicioRoFrecEjecucion.class);
		// TODO Auto-generated constructor stub
	}

	public RoFrecEjecucion buscarFrecEjecucionPorNombre(
			String nombreFrecEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoFrecEjecucion a WHERE a.nombreFrej LIKE :paramNombreFrecEjecucion");
		q.setParameter("paramNombreFrecEjecucion", nombreFrecEjecucion);

		return (RoFrecEjecucion) q.getSingleResult();

	}

	public boolean existeFrecEjecucionPorNombre(String nombreFrecEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoFrecEjecucion a WHERE a.nombreFrej LIKE :paramNombreFrecEjecucion");
		q.setParameter("paramNombreFrecEjecucion", nombreFrecEjecucion);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeFrecEjecucionPorNumero(String numeroFrecEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoFrecEjecucion a WHERE a.numeroFrej LIKE :paramNombreFrecEjecucion");
		q.setParameter("paramNombreFrecEjecucion", numeroFrecEjecucion);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeFrecEjecucionPorNombreEx(String nombreFrecEjecucion,
			int codigoFrecEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoFrecEjecucion a WHERE a.nombreFrej LIKE :paramNombreFrecEjecucion AND a.codigoFrej NOT LIKE :paramCodigoFrecEjecucion)");
		q.setParameter("paramNombreFrecEjecucion", nombreFrecEjecucion)
				.setParameter("paramCodigoFrecEjecucion", codigoFrecEjecucion);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeFrecEjecucionPorNumeroEx(String numeroFrecEjecucion,
			int codigoFrecEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoFrecEjecucion a WHERE a.numeroFrej LIKE :paramNombreFrecEjecucion AND a.codigoFrej NOT LIKE :paramCodigoFrecEjecucion)");
		q.setParameter("paramNombreFrecEjecucion", numeroFrecEjecucion)
				.setParameter("paramCodigoFrecEjecucion", codigoFrecEjecucion);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
}
