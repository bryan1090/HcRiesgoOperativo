package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoFactorRiesgo;

@Stateless
public class ServicioRoFactorRiesgo extends ServicioGenerico<RoFactorRiesgo> {

	public ServicioRoFactorRiesgo() {
		super(RoFactorRiesgo.class, ServicioRoFactorRiesgo.class);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<RoFactorRiesgo> buscarTodos_codigoFaro_nombreFaro() {
		ArrayList<RoFactorRiesgo> faros = new ArrayList<RoFactorRiesgo>();
		TypedQuery<Object[]> q = em.createQuery(
				"SELECT a.codigoFaro, a.nombreFaro FROM RoFactorRiesgo a",
				Object[].class);
		for (Object[] result : q.getResultList()) {
			RoFactorRiesgo faro = new RoFactorRiesgo((Integer) result[0],
					result[1].toString());
			faros.add(faro);
		}
		return faros;
	}

	public String buscarNombrePorId(String faro) {
		int idFaro = Integer.parseInt(faro);
		TypedQuery<String> q = em
				.createQuery(
						"SELECT a.nombreFaro FROM RoFactorRiesgo a WHERE a.codigoFaro LIKE :paramIdFaro",
						String.class);
		q.setParameter("paramIdFaro", idFaro);
		return q.getSingleResult();
	}

	public RoFactorRiesgo buscarFactorRiesgoPorNombre(String nombreFactorRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoFactorRiesgo a WHERE a.nombreFaro LIKE :paramNombreFactorRiesgo");
		q.setParameter("paramNombreFactorRiesgo", nombreFactorRiesgo);

		return (RoFactorRiesgo) q.getSingleResult();

	}

	public boolean existeFactorRiesgoPorNombre(String nombreFactorRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoFactorRiesgo a WHERE a.nombreFaro LIKE :paramNombreFactorRiesgo");
		q.setParameter("paramNombreFactorRiesgo", nombreFactorRiesgo);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeFactorRiesgoPorNumero(String numeroFactorRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoFactorRiesgo a WHERE a.numeroFaro LIKE :paramNombreFactorRiesgo");
		q.setParameter("paramNombreFactorRiesgo", numeroFactorRiesgo);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeFactorRiesgoPorNombreEx(String nombreFactorRiesgo,
			int codigoFactorRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoFactorRiesgo a WHERE a.nombreFaro LIKE :paramNombreFactorRiesgo AND a.codigoFaro NOT LIKE :paramCodigoFactorRiesgo)");
		q.setParameter("paramNombreFactorRiesgo", nombreFactorRiesgo)
				.setParameter("paramCodigoFactorRiesgo", codigoFactorRiesgo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeFactorRiesgoPorNumeroEx(String numeroFactorRiesgo,
			int codigoFactorRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoFactorRiesgo a WHERE a.numeroFaro LIKE :paramNombreFactorRiesgo AND a.codigoFaro NOT LIKE :paramCodigoFactorRiesgo)");
		q.setParameter("paramNombreFactorRiesgo", numeroFactorRiesgo)
				.setParameter("paramCodigoFactorRiesgo", codigoFactorRiesgo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<RoFactorRiesgo> buscarTodosAux() {
		ArrayList<RoFactorRiesgo> respAgs = new ArrayList<RoFactorRiesgo>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.codigoFaro, b.nombreFaro FROM RoFactorRiesgo b ORDER BY b.nombreFaro",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			RoFactorRiesgo respAg = new RoFactorRiesgo((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}
		return respAgs;
	}

}
