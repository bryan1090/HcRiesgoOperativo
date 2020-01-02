package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.ParamConsecuenciaImpacto;

@Stateless
public class ServicioParamConsecuenciaImpacto extends
		ServicioGenerico<ParamConsecuenciaImpacto> {

	public ServicioParamConsecuenciaImpacto() {
		super(ParamConsecuenciaImpacto.class,
				ServicioParamConsecuenciaImpacto.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParamConsecuenciaImpacto> buscarTodos() {
		Query q = em
				.createQuery("SELECT a FROM ParamConsecuenciaImpacto a ORDER BY a.numeroPconi");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ParamConsecuenciaImpacto> buscarTodosDesc() {
		Query q = em
				.createQuery("SELECT a FROM ParamConsecuenciaImpacto a ORDER BY a.numeroPconi DESC");
		return q.getResultList();
	}

	public List<ParamConsecuenciaImpacto> buscarTodos_codigoPconi_nombrePconi_numeroPconi() {
		ArrayList<ParamConsecuenciaImpacto> pconis = new ArrayList<ParamConsecuenciaImpacto>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoPconi, a.nombrePconi, a.numeroPconi FROM ParamConsecuenciaImpacto a ORDER BY a.nombrePconi",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			ParamConsecuenciaImpacto pconi = new ParamConsecuenciaImpacto(
					(Integer) result[0], (String) result[1],
					(Integer) result[2]);
			pconis.add(pconi);
		}

		return pconis;
	}

	public ParamConsecuenciaImpacto buscarConsecuenciaImpactoPorNumero(
			int numeroConsecuenciaImpacto) {
		Query q = em
				.createQuery("SELECT a FROM ParamConsecuenciaImpacto a WHERE a.numeroPconi LIKE :paramNumeroConsecuenciaImpacto");
		q.setParameter("paramNumeroConsecuenciaImpacto",
				numeroConsecuenciaImpacto);
		return (ParamConsecuenciaImpacto) q.getSingleResult();

	}

	public ParamConsecuenciaImpacto buscarConsecuenciaImpactoPorNombre(
			String nombreConsecuenciaImpacto) {
		Query q = em
				.createQuery("SELECT a FROM ParamConsecuenciaImpacto a WHERE a.nombrePconi LIKE :paramNombreConsecuenciaImpacto");
		q.setParameter("paramNombreConsecuenciaImpacto",
				nombreConsecuenciaImpacto);

		return (ParamConsecuenciaImpacto) q.getSingleResult();

	}

	public boolean existeConsecuenciaImpactoPorNombre(
			String nombreConsecuenciaImpacto) {
		Query q = em
				.createQuery("SELECT a FROM ParamConsecuenciaImpacto a WHERE a.nombrePconi LIKE :paramNombreConsecuenciaImpacto");
		q.setParameter("paramNombreConsecuenciaImpacto",
				nombreConsecuenciaImpacto);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeConsecuenciaImpactoPorNombreEx(
			String nombreConsecuenciaImpacto, int codigoConsecuenciaImpacto) {
		Query q = em
				.createQuery("SELECT a FROM ParamConsecuenciaImpacto a WHERE a.nombrePconi LIKE :paramNombreConsecuenciaImpacto AND a.codigoPconi NOT LIKE :paramCodigoConsecuenciaImpacto)");
		q.setParameter("paramNombreConsecuenciaImpacto",
				nombreConsecuenciaImpacto).setParameter(
				"paramCodigoConsecuenciaImpacto", codigoConsecuenciaImpacto);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
}
