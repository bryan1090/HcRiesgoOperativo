package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoProbabilidadEvento;
import com.hc.ro.modelo.RoNegocio;

@Stateless
public class ServicioRoProbabilidadEvento extends
		ServicioGenerico<RoProbabilidadEvento> {

	public ServicioRoProbabilidadEvento() {
		super(RoProbabilidadEvento.class, ServicioRoProbabilidadEvento.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoProbabilidadEvento> buscarTodos() {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a ORDER BY a.roNegocio,a.codigoPprr");

		return q.getResultList();
	}

	public RoProbabilidadEvento buscarProbabilidadEventoPorNombre(
			String nombreProbabilidadEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.NOMBRE_prob LIKE :paramNombreConsecuencia");
		q.setParameter("paramNombreConsecuencia", nombreProbabilidadEvento);

		return (RoProbabilidadEvento) q.getSingleResult();

	}

	public RoProbabilidadEvento buscarProbabilidadEventoPorPprrNego(
			int codigoNego, int codigoPprr) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.roNegocio.codigoNego LIKE :pNego AND :pPprr LIKE a.codigoPprr");
		q.setParameter("pNego", codigoNego).setParameter("pPprr", codigoPprr);

		return (RoProbabilidadEvento) q.getSingleResult();

	}

	public boolean buscarProbabilidadEventoPorNombreCosto(
			String nombreProbabilidadEvento, float costo, RoNegocio negocio) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.NOMBRE_prob LIKE :paramNombreConsecuencia AND :costo>a.DESDE_prob AND :costo<=a.HASTA_ AND a.roNegocio LIKE:negocio");
		q.setParameter("paramNombreConsecuencia", nombreProbabilidadEvento)
				.setParameter("negocio", negocio).setParameter("costo", costo);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean existeProbabilidadEventoPorNombre(
			String nombreProbabilidadEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.NOMBRE_cons LIKE :paramNombreConsecuencia");
		q.setParameter("paramNombreConsecuencia", nombreProbabilidadEvento);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeProbabilidadEventoPorPprrNego(int codigoPprr,
			int idNego) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.codigoPprr LIKE :paramNombreConsecuencia AND a.roNegocio.codigoNego LIKE :paramNego");
		q.setParameter("paramNombreConsecuencia", codigoPprr).setParameter(
				"paramNego", idNego);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public List<RoProbabilidadEvento> buscarPorNegocio(int codigoNego) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.roNegocio.codigoNego LIKE :paramCodigoNego");
		q.setParameter("paramCodigoNego", codigoNego);

		return q.getResultList();

	}

	public boolean existePorNombreEx(String nombre, int codigo) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.NOMBRE_prob LIKE :paramNombre AND a.CODIGO_prob NOT LIKE :paramCodigo");
		q.setParameter("paramNombre", nombre).setParameter("paramCodigo",
				codigo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existePorPprrNegoEx(int codigoPprr, int codigo, int idNego) {
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.codigoPprr LIKE :paramNombre AND a.CODIGO_prob NOT LIKE :paramCodigo AND a.roNegocio.codigoNego LIKE :paramNego");
		q.setParameter("paramNombre", codigoPprr)
				.setParameter("paramNego", idNego)
				.setParameter("paramCodigo", codigo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public RoProbabilidadEvento buscarProbabilidadEventoPorNegocioFrecuencia(
			String nombreNegocio, double frecuencia) {
		if (frecuencia == 0) {
			frecuencia -= 1;
		}
		Query q = em
				.createQuery("SELECT a FROM RoProbabilidadEvento a WHERE a.roNegocio.nombreNego LIKE :paramCodigoNegocio AND :costo>a.desdeProb AND :costo<=a.hastaProb");
		q.setParameter("paramCodigoNegocio", nombreNegocio).setParameter(
				"costo", frecuencia);

		return (RoProbabilidadEvento) q.getSingleResult();

	}
}
