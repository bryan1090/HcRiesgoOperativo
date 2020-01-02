package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.ParamProbabilidadRiesgo;

@Stateless
public class ServicioParamProbabilidadRiesgo extends
		ServicioGenerico<ParamProbabilidadRiesgo> {

	public ServicioParamProbabilidadRiesgo() {
		super(ParamProbabilidadRiesgo.class,
				ServicioParamProbabilidadRiesgo.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParamProbabilidadRiesgo> buscarTodos() {
		Query q = em
				.createQuery("SELECT a FROM ParamProbabilidadRiesgo a ORDER BY a.numeroPprr");
		return q.getResultList();
	}

	public List<ParamProbabilidadRiesgo> buscarTodos_codigoPprr_nombrePprr() {
		ArrayList<ParamProbabilidadRiesgo> pprrs = new ArrayList<ParamProbabilidadRiesgo>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoPprr, a.nombrePprr FROM ParamProbabilidadRiesgo a ORDER BY a.nombrePprr",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			ParamProbabilidadRiesgo pprr = new ParamProbabilidadRiesgo(
					(Integer) result[0], result[1].toString());
			pprrs.add(pprr);
		}

		return pprrs;
	}

	@SuppressWarnings("unchecked")
	public List<ParamProbabilidadRiesgo> buscarTodosDesc() {
		Query q = em
				.createQuery("SELECT a FROM ParamProbabilidadRiesgo a ORDER BY a.numeroPprr DESC");
		return q.getResultList();
	}

	public ParamProbabilidadRiesgo buscarProbabilidadRiesgoPorNumero(
			int numeroProbabilidadRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM ParamProbabilidadRiesgo a WHERE a.numeroPprr LIKE :paramNumeroProbabilidadRiesgo");
		q.setParameter("paramNumeroProbabilidadRiesgo",
				numeroProbabilidadRiesgo);
		return (ParamProbabilidadRiesgo) q.getSingleResult();

	}

	public ParamProbabilidadRiesgo buscarProbabilidadRiesgoPorNombre(
			String nombreProbabilidadRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM ParamProbabilidadRiesgo a WHERE a.nombrePprr LIKE :paramNombreProbabilidadRiesgo");
		q.setParameter("paramNombreProbabilidadRiesgo",
				nombreProbabilidadRiesgo);

		return (ParamProbabilidadRiesgo) q.getSingleResult();

	}

	public boolean existeProbabilidadRiesgoPorNombre(
			String nombreProbabilidadRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM ParamProbabilidadRiesgo a WHERE a.nombrePprr LIKE :paramNombreProbabilidadRiesgo");
		q.setParameter("paramNombreProbabilidadRiesgo",
				nombreProbabilidadRiesgo);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeProbabilidadRiesgoPorNombreEx(
			String nombreProbabilidadRiesgo, int codigoProbabilidadRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM ParamProbabilidadRiesgo a WHERE a.nombrePprr LIKE :paramNombreProbabilidadRiesgo AND a.codigoPprr NOT LIKE :paramCodigoProbabilidadRiesgo)");
		q.setParameter("paramNombreProbabilidadRiesgo",
				nombreProbabilidadRiesgo).setParameter(
				"paramCodigoProbabilidadRiesgo", codigoProbabilidadRiesgo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
}
