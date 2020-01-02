package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoAttrbAdicionale;

@Stateless
public class ServicioRoAttrbAdicionale extends
		ServicioGenerico<RoAttrbAdicionale> {

	public ServicioRoAttrbAdicionale() {
		super(RoAttrbAdicionale.class, ServicioRoAttrbAdicionale.class);
		// TODO Auto-generated constructor stub
	}

	public List<RoAttrbAdicionale> buscarTodosNombreCodigo() {
		ArrayList<RoAttrbAdicionale> respAgs = new ArrayList<RoAttrbAdicionale>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.codigoAttr, b.nombreAttr FROM RoAttrbAdicionale b WHERE b.claseAttr = 'Detalle de Evento' ORDER BY b.nombreAttr",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			RoAttrbAdicionale respAg = new RoAttrbAdicionale(
					(Integer) result[0], (String) result[1]);
			respAgs.add(respAg);
		}
		return respAgs;
	}

	@SuppressWarnings("unchecked")
	public List<RoAttrbAdicionale> buscarEventoPorClase(String clase) {
		Query q = em
				.createQuery("SELECT a FROM RoAttrbAdicionale a WHERE a.claseAttr LIKE :paramClase");
		q.setParameter("paramClase", clase);

		return q.getResultList();
	}

	public RoAttrbAdicionale buscarPorNombreClase(String clase, String nombre) {
		Query q = em
				.createQuery("SELECT a FROM RoAttrbAdicionale a WHERE a.claseAttr LIKE :paramClase AND a.nombreAttr LIKE paramNombre");
		q.setParameter("paramClase", clase).setParameter("paramNombre", nombre);

		return (RoAttrbAdicionale) q.getSingleResult();
	}

	public boolean existeAtributoPorNombrePorClase(String nombreAtributo,
			String clase) {
		Query q = em
				.createQuery("SELECT a FROM RoAttrbAdicionale a WHERE a.nombreAttr LIKE :paramNombreAtributo AND a.claseAttr LIKE :paramClase");
		q.setParameter("paramNombreAtributo", nombreAtributo).setParameter(
				"paramClase", clase);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean existeAtributoPorNombreEx(String nombreAtributo,
			int codigoAtributo, String clase) {
		Query q = em
				.createQuery("SELECT a FROM RoAttrbAdicionale a WHERE a.nombreAttr LIKE :paramNombreAtributo AND a.codigoAttr NOT LIKE :paramCodigoAtributo AND a.claseAttr LIKE :paramClase");
		q.setParameter("paramNombreAtributo", nombreAtributo)
				.setParameter("paramCodigoAtributo", codigoAtributo)
				.setParameter("paramClase", clase);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}
}