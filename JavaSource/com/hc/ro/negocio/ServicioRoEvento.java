package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoEvento;

@Stateless
public class ServicioRoEvento extends ServicioGenerico<RoEvento> {

	public ServicioRoEvento() {
		super(RoEvento.class, ServicioRoEvento.class);
		// TODO Auto-generated constructor stub
	}

	// Me devuelve todos los campos de la tabla
	@SuppressWarnings("unchecked")
	public List<RoEvento> buscarEventoPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM RoEvento a WHERE a.ancestroEven LIKE :paramCodigoPadre ORDER BY a.numeroEven");
		q.setParameter("paramCodigoPadre", codigoPadre);
		return q.getResultList();
	}

	// Me devuelve algunos campos de la tabla, con TypeQuery
	public RoEvento buscarEventoPorNombre_codigoEven_nombreEven_ancestroEven(
			String nombre) {
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoEven, a.nombreEven, a.ancestroEven FROM RoEvento a WHERE a.nombreEven LIKE :paramNombre",
						Object[].class);
		q.setParameter("paramNombre", nombre);
		RoEvento evento = new RoEvento((Integer) q.getSingleResult()[0],
				(String) q.getSingleResult()[1],
				(String) q.getSingleResult()[2]);
		return evento;
	}

	public List<RoEvento> buscarEventoPorPadre_nombreEven(String codigoPadre) {
		ArrayList<RoEvento> eventos = new ArrayList<RoEvento>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoEven, a.nombreEven FROM RoEvento a WHERE a.ancestroEven LIKE :paramCodigoPadre",
						Object[].class);
		q.setParameter("paramCodigoPadre", codigoPadre);
		for (Object[] result : q.getResultList()) {
			RoEvento evento = new RoEvento((Integer) result[0],
					(String) result[1]);
			eventos.add(evento);
		}
		return eventos;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<RoEvento> buscarTodos() {
		Query q = em
				.createQuery("SELECT a FROM RoEvento a ORDER BY a.nombreEven");
		return q.getResultList();

	}

	public List<RoEvento> buscarTodosNombreCodigo() {
		ArrayList<RoEvento> respAgs = new ArrayList<RoEvento>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.codigoEven, b.nombreEven FROM RoEvento b ORDER BY b.nombreEven",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			RoEvento respAg = new RoEvento((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}
		return respAgs;
	}
	
//	public List<RoEvento> buscarTodosNombre() {
//		ArrayList<RoEvento> respAgs = new ArrayList<RoEvento>();
//		TypedQuery<Object[]> q = em
//				.createQuery(
//						"SELECT b.nombreEven FROM RoEvento b ORDER BY b.nombreEven",
//						Object[].class);
//		for (Object[] result : q.getResultList()) {
//			RoEvento respAg = new RoEvento((String) result[1]);
//			respAgs.add(respAg);
//		}
//		return respAgs;
//	}

	public RoEvento buscarEventoPorNombre(String nombreEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoEvento a WHERE a.nombreEven LIKE :paramNombreEvento");
		q.setParameter("paramNombreEvento", nombreEvento);
		return (RoEvento) q.getSingleResult();
	}

	public boolean existeEventoPorNombre(String nombreEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoEvento a WHERE a.nombreEven LIKE :paramNombreEvento");
		q.setParameter("paramNombreEvento", nombreEvento);

		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeEventoPorNumero(String numeroEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoEvento a WHERE a.numeroEven LIKE :paramNumeroEvento");
		q.setParameter("paramNumeroEvento", numeroEvento);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeEventoPorNombreEx(String nombreEvento, int codigoEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoEvento a WHERE a.nombreEven LIKE :paramNombreEvento AND a.codigoEven NOT LIKE :paramCodigoEvento)");
		q.setParameter("paramNombreEvento", nombreEvento).setParameter(
				"paramCodigoEvento", codigoEvento);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeEventoPorNumeroEx(String numeroEvento, int codigoEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoEvento a WHERE a.numeroEven LIKE :paramNumeroEvento AND a.codigoEven NOT LIKE :paramCodigoEvento)");
		q.setParameter("paramNumeroEvento", numeroEvento).setParameter(
				"paramCodigoEvento", codigoEvento);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
}
