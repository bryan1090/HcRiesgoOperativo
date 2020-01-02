package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoAgencia;

@Stateless
public class ServicioRoAgencia extends ServicioGenerico<RoAgencia> {

	public ServicioRoAgencia() {
		super(RoAgencia.class, ServicioRoAgencia.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoAgencia> buscarAgenciaPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM RoAgencia a WHERE a.ancestroAgia LIKE :paramCodigoPadre ORDER BY a.numeroAgia");
		q.setParameter("paramCodigoPadre", codigoPadre);

		return q.getResultList();

	}


	public List<RoAgencia> buscarAgenciaPorPadre_nombreAgia(String codigoPadre) {
		ArrayList<RoAgencia> agencias = new ArrayList<RoAgencia>();
		TypedQuery<String> q = em
				.createQuery(
						"SELECT a.nombreAgia FROM RoAgencia a WHERE a.ancestroAgia LIKE :paramCodigoPadre",
						String.class);
		q.setParameter("paramCodigoPadre", codigoPadre);
		for (String result : q.getResultList()) {
			RoAgencia agencia = new RoAgencia(result);
			agencias.add(agencia);
		}
		return agencias;
	}

	public RoAgencia buscarAgenciaPorNombre(String nombreAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoAgencia a WHERE a.nombreAgia LIKE :paramNombreAgencia");
		q.setParameter("paramNombreAgencia", nombreAgencia);

		return (RoAgencia) q.getSingleResult();

	}

	public boolean existeAgenciaPorNombre(String nombreAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoAgencia a WHERE a.nombreAgia LIKE :paramNombreAgencia");
		q.setParameter("paramNombreAgencia", nombreAgencia);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeAgenciaPorNumero(String numeroAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoAgencia a WHERE a.numeroAgia LIKE :paramNombreAgencia");
		q.setParameter("paramNombreAgencia", numeroAgencia);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeAgenciaPorNombreEx(String nombreAgencia,
			int codigoAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoAgencia a WHERE a.nombreAgia LIKE :paramNombreAgencia AND a.codigoAgia NOT LIKE :paramCodigoAgencia)");
		q.setParameter("paramNombreAgencia", nombreAgencia).setParameter(
				"paramCodigoAgencia", codigoAgencia);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeAgenciaPorNumeroEx(String numeroAgencia,
			int codigoAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoAgencia a WHERE a.numeroAgia LIKE :paramNombreAgencia AND a.codigoAgia NOT LIKE :paramCodigoAgencia)");
		q.setParameter("paramNombreAgencia", numeroAgencia).setParameter(
				"paramCodigoAgencia", codigoAgencia);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
	public List<Integer> buscarCodigosAgenciaTodos() {
		Query q = em
				.createQuery("SELECT a.codigoAgia FROM RoAgencia a");


		return q.getResultList();
	}
	
	
}
