package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoTipoCosto;

@Stateless
public class ServicioRoTipoCosto extends ServicioGenerico<RoTipoCosto> {

	public ServicioRoTipoCosto() {
		super(RoTipoCosto.class, ServicioRoTipoCosto.class);
		// TODO Auto-generated constructor stub
	}

	public RoTipoCosto buscarTipoCostoPorNombre(String nombreTipoCosto) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoCosto a WHERE a.nombreTico LIKE :paramNombreTipoCosto");
		q.setParameter("paramNombreTipoCosto", nombreTipoCosto);

		return (RoTipoCosto) q.getSingleResult();
	}

	public boolean existeTipoCostoPorNombre(String nombreTipoCosto) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoCosto a WHERE a.nombreTico LIKE :paramNombreTipoCosto");
		q.setParameter("paramNombreTipoCosto", nombreTipoCosto);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoCostoPorNumero(String numeroTipoCosto) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoCosto a WHERE a.numeroTico LIKE :paramNombreTipoCosto");
		q.setParameter("paramNombreTipoCosto", numeroTipoCosto);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoCostoPorNombreEx(String nombreTipoCosto,
			long codigoTipoCosto) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoCosto a WHERE a.nombreTico LIKE :paramNombreTipoCosto AND a.codigoTico NOT LIKE :paramCodigoTipoCosto)");
		q.setParameter("paramNombreTipoCosto", nombreTipoCosto).setParameter(
				"paramCodigoTipoCosto", codigoTipoCosto);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoCostoPorNumeroEx(String numeroTipoCosto,
			long codigoTipoCosto) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoCosto a WHERE a.numeroTico LIKE :paramNombreTipoCosto AND a.codigoTico NOT LIKE :paramCodigoTipoCosto)");
		q.setParameter("paramNombreTipoCosto", numeroTipoCosto).setParameter(
				"paramCodigoTipoCosto", codigoTipoCosto);
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<RoTipoCosto> buscarTodosAux() {
		ArrayList<RoTipoCosto> respAgs = new ArrayList<RoTipoCosto>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.codigoTico, b.nombreTico FROM RoTipoCosto b ORDER BY b.nombreTico",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			RoTipoCosto respAg = new RoTipoCosto((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}

		return respAgs;
	}

}
