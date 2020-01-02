package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoNegocio;

@Stateless
public class ServicioRoNegocio extends ServicioGenerico<RoNegocio> {

	public ServicioRoNegocio() {
		super(RoNegocio.class, ServicioRoNegocio.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoNegocio> buscarNegocioPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM RoNegocio a WHERE a.ancestroNego LIKE :paramCodigoPadre ORDER BY a.numeroNego");
		q.setParameter("paramCodigoPadre", codigoPadre);

		return q.getResultList();

	}

	public List<RoNegocio> buscarNegocioPorPadre_nombreNego(String codigoPadre) {
		ArrayList<RoNegocio> negocios = new ArrayList<RoNegocio>();
		TypedQuery<String> q = em
				.createQuery(
						"SELECT a.nombreNego FROM RoNegocio a WHERE a.ancestroNego LIKE :paramCodigoPadre",
						String.class);
		q.setParameter("paramCodigoPadre", codigoPadre);
		for (String result : q.getResultList()) {
			RoNegocio negocio = new RoNegocio(result);
			negocios.add(negocio);
		}
		return negocios;
	}

	public RoNegocio buscarNegocioPorNombre(String nombreNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoNegocio a WHERE a.nombreNego LIKE :paramNombreNegocio");
		q.setParameter("paramNombreNegocio", nombreNegocio);

		return (RoNegocio) q.getSingleResult();

	}

	public boolean existeNegocioPorNombre(String nombreNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoNegocio a WHERE a.nombreNego LIKE :paramNombreNegocio");
		q.setParameter("paramNombreNegocio", nombreNegocio);

		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeNegocioPorNumero(String numeroNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoNegocio a WHERE a.numeroNego LIKE :paramNumeroNegocio");
		q.setParameter("paramNumeroNegocio", numeroNegocio);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeNegocioPorNombreEx(String nombreNegocio,
			int codigoNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoNegocio a WHERE a.nombreNego LIKE :paramNombreNegocio AND a.codigoNego NOT LIKE :paramCodigoNegocio)");
		q.setParameter("paramNombreNegocio", nombreNegocio).setParameter(
				"paramCodigoNegocio", codigoNegocio);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeNegocioPorNumeroEx(String numeroNegocio,
			int codigoNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoNegocio a WHERE a.numeroNego LIKE :paramNumeroNegocio AND a.codigoNego NOT LIKE :paramCodigoNegocio)");
		q.setParameter("paramNumeroNegocio", numeroNegocio).setParameter(
				"paramCodigoNegocio", codigoNegocio);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
}
