package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoTipoPerdida;

@Stateless
public class ServicioRoTipoPerdida extends ServicioGenerico<RoTipoPerdida> {

	public ServicioRoTipoPerdida() {
		super(RoTipoPerdida.class, ServicioRoTipoPerdida.class);
		// TODO Auto-generated constructor stub
	}

	public RoTipoPerdida buscarTipoPerdidaPorNombre(String nombreTipoPerdida) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoPerdida a WHERE a.nombreTipe LIKE :paramNombreTipoPerdida");
		q.setParameter("paramNombreTipoPerdida", nombreTipoPerdida);

		return (RoTipoPerdida) q.getSingleResult();
	}

	public ArrayList<RoTipoPerdida> buscarTodos_codigoTipe_nombreTipe() {
		ArrayList<RoTipoPerdida> tipes = new ArrayList<RoTipoPerdida>();
		TypedQuery<Object[]> q = em.createQuery(
				"SELECT a.codigoTipe, a.nombreTipe FROM RoTipoPerdida a",
				Object[].class);
		for (Object[] result : q.getResultList()) {
			RoTipoPerdida tipe = new RoTipoPerdida((Integer) result[0],
					result[1].toString());
			tipes.add(tipe);
		}
		return tipes;
	}

	public String buscarNombrePorId(String tipe) {
		int idTipe = Integer.parseInt(tipe);
		TypedQuery<String> q = em
				.createQuery(
						"SELECT a.nombreTipe FROM RoTipoPerdida a WHERE a.codigoTipe LIKE :paramIdTipe",
						String.class);
		q.setParameter("paramIdTipe", idTipe);
		return q.getSingleResult();
	}

	public boolean existeTipoPerdidaPorNombre(String nombreTipoPerdida) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoPerdida a WHERE a.nombreTipe LIKE :paramNombreTipoPerdida");
		q.setParameter("paramNombreTipoPerdida", nombreTipoPerdida);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<RoTipoPerdida> buscarPorTipoRegistro(String tipoRegistro) {
		int tipoRegistroNum;
		if (tipoRegistro.equals("Cuantitativo")) {
			tipoRegistroNum = 1;
		} else {
			tipoRegistroNum = 0;
		}
		Query q = em
				.createQuery("SELECT a FROM RoTipoPerdida a WHERE a.tipoRegistro = :paramNombreTipoRegistro");
		q.setParameter("paramNombreTipoRegistro", tipoRegistroNum);

		return q.getResultList();

	}

	public List<RoTipoPerdida> buscarPorTipoRegistroAux(String tipoRegistro) {
		int tipoRegistroNum;
		if (tipoRegistro.equals("Cuantitativo")) {
			tipoRegistroNum = 1;
		} else {
			tipoRegistroNum = 0;
		}
		ArrayList<RoTipoPerdida> respAgs = new ArrayList<RoTipoPerdida>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoTipe, a.nombreTipe FROM RoTipoPerdida a WHERE a.tipoRegistro = :paramNombreTipoRegistro",
						Object[].class);
		q.setParameter("paramNombreTipoRegistro", tipoRegistroNum);
		for (Object[] result : q.getResultList()) {
			RoTipoPerdida respAg = new RoTipoPerdida((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}

		return respAgs;
	}

	public boolean existeTipoPerdidaPorNumero(String numeroTipoPerdida) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoPerdida a WHERE a.numeroTipe LIKE :paramNombreTipoPerdida");
		q.setParameter("paramNombreTipoPerdida", numeroTipoPerdida);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoPerdidaPorNombreEx(String nombreTipoPerdida,
			int codigoTipoPerdida) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoPerdida a WHERE a.nombreTipe LIKE :paramNombreTipoPerdida AND a.codigoTipe NOT LIKE :paramCodigoTipoPerdida)");
		q.setParameter("paramNombreTipoPerdida", nombreTipoPerdida)
				.setParameter("paramCodigoTipoPerdida", codigoTipoPerdida);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoPerdidaPorNumeroEx(String numeroTipoPerdida,
			int codigoTipoPerdida) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoPerdida a WHERE a.numeroTipe LIKE :paramNombreTipoPerdida AND a.codigoTipe NOT LIKE :paramCodigoTipoPerdida)");
		q.setParameter("paramNombreTipoPerdida", numeroTipoPerdida)
				.setParameter("paramCodigoTipoPerdida", codigoTipoPerdida);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

}
