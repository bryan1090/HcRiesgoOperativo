package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoTipoRecupera;

@Stateless
public class ServicioRoTipoRecupera extends ServicioGenerico<RoTipoRecupera> {

	public ServicioRoTipoRecupera() {
		super(RoTipoRecupera.class, ServicioRoTipoRecupera.class);
		// TODO Auto-generated constructor stub
	}

	public RoTipoRecupera buscarTipoRecuperaPorNombre(String nombreTipoRecupera) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoRecupera a WHERE a.nombreTrec LIKE :paramNombreTipoRecupera");
		q.setParameter("paramNombreTipoRecupera", nombreTipoRecupera);

		return (RoTipoRecupera) q.getSingleResult();

	}

	public boolean existeTipoRecuperaPorNombre(String nombreTipoRecupera) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoRecupera a WHERE a.nombreTrec LIKE :paramNombreTipoRecupera");
		q.setParameter("paramNombreTipoRecupera", nombreTipoRecupera);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoRecuperaPorNumero(String numeroTipoRecupera) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoRecupera a WHERE a.numeroTrec LIKE :paramNombreTipoRecupera");
		q.setParameter("paramNombreTipoRecupera", numeroTipoRecupera);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoRecuperaPorNombreEx(String nombreTipoRecupera,
			int codigoTipoRecupera) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoRecupera a WHERE a.nombreTrec LIKE :paramNombreTipoRecupera AND a.codigoTrec NOT LIKE :paramCodigoTipoRecupera)");
		q.setParameter("paramNombreTipoRecupera", nombreTipoRecupera)
				.setParameter("paramCodigoTipoRecupera", codigoTipoRecupera);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoRecuperaPorNumeroEx(String numeroTipoRecupera,
			int codigoTipoRecupera) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoRecupera a WHERE a.numeroTrec LIKE :paramNombreTipoRecupera AND a.codigoTrec NOT LIKE :paramCodigoTipoRecupera)");
		q.setParameter("paramNombreTipoRecupera", numeroTipoRecupera)
				.setParameter("paramCodigoTipoRecupera", codigoTipoRecupera);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<RoTipoRecupera> buscarTodosAux() {
		ArrayList<RoTipoRecupera> respAgs = new ArrayList<RoTipoRecupera>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.codigoTrec, b.nombreTrec FROM RoTipoRecupera b ORDER BY b.nombreTrec",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			RoTipoRecupera respAg = new RoTipoRecupera((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}

		return respAgs;
	}

}
