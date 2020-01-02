package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoAriesgo;

@Stateless
public class ServicioRoAriesgo extends ServicioGenerico<RoAriesgo> {

	public ServicioRoAriesgo() {
		super(RoAriesgo.class, ServicioRoAriesgo.class);
		// TODO Auto-generated constructor stub
	}

	public List<RoAriesgo> buscarTodos_paramConsecuenciaImpacto_paramProbabilidadRiesgo_roCalifRiesgo__nombreClrs_roCalifRiesgo__colorClrs() {
		ArrayList<RoAriesgo> ariesgos = new ArrayList<RoAriesgo>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.paramProbabilidadRiesgo, a.paramConsecuenciaImpacto, a.roCalifRiesgo.nombreClrs, a.roCalifRiesgo.colorClrs FROM RoAriesgo a",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			RoAriesgo ariesgo = new RoAriesgo(
					(ParamProbabilidadRiesgo) result[0],
					(ParamConsecuenciaImpacto) result[1], result[2].toString(),
					result[3].toString());
			ariesgos.add(ariesgo);
		}
		return ariesgos;
	}

	public RoAriesgo buscarTipoAfectaPorNombre(String descAriesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoAriesgo a WHERE a.descAriesgo LIKE :paramDescAriesgo");
		q.setParameter("paramDescAriesgo", descAriesgo);
		return (RoAriesgo) q.getSingleResult();
	}

	public boolean existeRiesgoClavePorNombreEx(String nombreRiesgoClave,
			int codigoRiesgoClave) {
		Query q = em
				.createQuery("SELECT a FROM RoRiesgoClave a WHERE a.nombreRicl LIKE :paramNombreRiesgoClave AND a.codigoRicl NOT LIKE :paramCodigoRiesgoClave)");
		q.setParameter("paramNombreRiesgoClave", nombreRiesgoClave)
				.setParameter("paramCodigoRiesgoClave", codigoRiesgoClave);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public RoAriesgo buscarTipoAfectaPorCodigo(String codiAriesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoAriesgo a WHERE a.codAriesgo LIKE :paramcodAriesgo");
		q.setParameter("paramcodAriesgo", codiAriesgo);

		return (RoAriesgo) q.getSingleResult();

	}

	public boolean existeAriesgoPorCodigo(int codiAriesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoAriesgo a WHERE a.codAriesgo LIKE :paramcodAriesgo");
		q.setParameter("paramcodAriesgo", codiAriesgo);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public List<RoAriesgo> buscarPorNegocio(int codigoNego) {
		Query q = em
				.createQuery("SELECT a FROM RoAriesgo a WHERE a.roNegocio.codigoNego LIKE :paramCodigoNego");
		q.setParameter("paramCodigoNego", codigoNego);

		return q.getResultList();

	}

	public boolean existePorNombreEx(String nombre, int codigo) {
		Query q = em
				.createQuery("SELECT a FROM RoAriesgo a WHERE a.descAriesgo LIKE :paramNombre AND a.codAriesgo NOT LIKE :paramCodigo");
		q.setParameter("paramNombre", nombre).setParameter("paramCodigo",
				codigo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existePorNombreNegoEx(String nombre, int codigo, int idNego) {
		Query q = em
				.createQuery("SELECT a FROM RoAriesgo a WHERE a.descAriesgo LIKE :paramNombre AND a.codAriesgo NOT LIKE :paramCodigo AND a.roNegocio.codigoNego LIKE :paramNego");
		q.setParameter("paramNombre", nombre).setParameter("paramNego", idNego)
				.setParameter("paramCodigo", codigo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existePorNombreNego(String nombreConsecuenciaImpacto,
			int idNego) {
		Query q = em
				.createQuery("SELECT a FROM RoAriesgo a WHERE a.descAriesgo LIKE :paramNombreConsecuencia AND a.roNegocio.codigoNego LIKE :paramNego");
		q.setParameter("paramNombreConsecuencia", nombreConsecuenciaImpacto)
				.setParameter("paramNego", idNego);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public RoAriesgo buscarPorParams_Prob_Consec(int codigoParamProb,int codigoParamConsec)
	{
		Query q=em.createQuery("SELECT a FROM RoAriesgo a WHERE a.paramProbabilidadRiesgo.codigoPprr LIKE :paramCodigoParamProb AND a.paramConsecuenciaImpacto.codigoPconi LIKE :paramCodigoParamConsec");
		q.setParameter("paramCodigoParamProb", codigoParamProb);
		q.setParameter("paramCodigoParamConsec", codigoParamConsec);
		RoAriesgo aries=new RoAriesgo();
		for (Object s: q.getResultList()) {
			if(((RoAriesgo) s).getRoCalifRiesgo().getColorClrs()!=null)
				aries=(RoAriesgo)s;
		}
		
		q.setMaxResults(1).getResultList();
		return (RoAriesgo) q.getSingleResult();
	}
	
//	public RoAriesgo buscarPorParams_Prob_Consec(ParamProbabilidadRiesgo roParamProb,ParamConsecuenciaImpacto roParamConsec)
//	{
//		Query q=em.createQuery("SELECT a FROM RoAriesgo a WHERE a.paramProbabilidadRiesgo LIKE :paramRoParamProb AND a.paramConsecuenciaImpacto LIKE :paramRoParamConsec");
//		q.setParameter("paramRoParamProb", roParamProb);
//		q.setParameter("paramRoParamConsec", roParamConsec);
//		
//		return (RoAriesgo) q.getSingleResult();
//	}
}
