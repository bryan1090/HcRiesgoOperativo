package com.hc.ro.negocio;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoConsecuenciaImpacto;
import com.hc.ro.modelo.RoNegocio;

@Stateless
public class ServicioRoConsecuenciaImpacto extends
		ServicioGenerico<RoConsecuenciaImpacto> {

	public ServicioRoConsecuenciaImpacto() {
		super(RoConsecuenciaImpacto.class, ServicioRoConsecuenciaImpacto.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoConsecuenciaImpacto> buscarTodos() {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a ORDER BY a.roNegocio,a.codigoPconi");

		return q.getResultList();
	}

	public RoConsecuenciaImpacto buscarConsecuenciaImpactoPorNombre(
			String nombreConsecuenciaImpacto) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.NOMBRE_cons LIKE :paramNombreConsecuencia");
		q.setParameter("paramNombreConsecuencia", nombreConsecuenciaImpacto);

		return (RoConsecuenciaImpacto) q.getSingleResult();

	}

	public RoConsecuenciaImpacto buscarConsecuenciaImpactoPorNegocioValor(
			String nombreNegocio, double valorPerdida) {
		Double max = new Double(0);
		// System.out.println("negocio:"+ nombreNegocio);
		// System.out.println("valorPerdida:"+ valorPerdida);
		if (valorPerdida == 0.0) {
			valorPerdida = valorPerdida + 1;
		}

		Query q2 = em
				.createQuery("SELECT MAX(a.HASTA_cons) FROM RoConsecuenciaImpacto a WHERE a.roNegocio.nombreNego LIKE :paramCodigoNegocio");
		q2.setParameter("paramCodigoNegocio", nombreNegocio);
		max = (Double) q2.getSingleResult();
		if (valorPerdida > max) {
			// Si el valor de pérdida es mayor al maximo parametrizado en la
			// tabla, cambio el valor de pérdida por el max para poder hacer la
			// siguiente consulta (q)
			valorPerdida = max;
		}

		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.roNegocio.nombreNego LIKE :paramCodigoNegocio AND :costo>a.DESDE_cons AND :costo<=a.HASTA_cons");
		q.setParameter("paramCodigoNegocio", nombreNegocio).setParameter(
				"costo", valorPerdida);

		return (RoConsecuenciaImpacto) q.getSingleResult();

	}

	public RoConsecuenciaImpacto buscarConsecuenciaImpactoPorPconiNego(
			int codigoNego, int codigoPconi) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.roNegocio.codigoNego LIKE :pNego AND :pPconi LIKE a.codigoPconi");
		q.setParameter("pNego", codigoNego).setParameter("pPconi", codigoPconi);

		return (RoConsecuenciaImpacto) q.getSingleResult();

	}

	public boolean buscarConsecuenciaImpactoPorNombreCosto(
			String nombreConsecuenciaImpacto, float costo, RoNegocio negocio) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.NOMBRE_cons LIKE :paramNombreConsecuencia AND :costo>a.DESDE_cons AND :costo<=a.HASTA_cons AND a.roNegocio LIKE:negocio");
		q.setParameter("paramNombreConsecuencia", nombreConsecuenciaImpacto)
				.setParameter("negocio", negocio).setParameter("costo", costo);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean existeConsecuenciaImpactoPorNombre(
			String nombreConsecuenciaImpacto) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.NOMBRE_cons LIKE :paramNombreConsecuencia");
		q.setParameter("paramNombreConsecuencia", nombreConsecuenciaImpacto);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeConsecuenciaImpactoPorPconiNego(int codigoPconi,
			int idNego) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.codigoPconi LIKE :paramNombreConsecuencia AND a.roNegocio.codigoNego LIKE :paramNego");
		q.setParameter("paramNombreConsecuencia", codigoPconi).setParameter(
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
	public List<RoConsecuenciaImpacto> buscarPorNegocio(int codigoNego) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.roNegocio.codigoNego LIKE :paramCodigoNego");
		q.setParameter("paramCodigoNego", codigoNego);

		return q.getResultList();

	}

	public boolean existePorNombreEx(String nombre, int codigo) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.NOMBRE_cons LIKE :paramNombre AND a.CODIGO_cons NOT LIKE :paramCodigo");
		q.setParameter("paramNombre", nombre).setParameter("paramCodigo",
				codigo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existePorPconiNegoEx(int codigoPconi, int codigo, int idNego) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a WHERE a.codigoPconi LIKE :paramNombre AND a.CODIGO_cons NOT LIKE :paramCodigo AND a.roNegocio.codigoNego LIKE :paramNego");
		q.setParameter("paramNombre", codigoPconi)
				.setParameter("paramNego", idNego)
				.setParameter("paramCodigo", codigo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeNegocio(String nego) {
		Query q = em
				.createQuery("SELECT a FROM RoConsecuenciaImpacto a ORDER BY a.roNegocio,a.codigoPconi");

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
