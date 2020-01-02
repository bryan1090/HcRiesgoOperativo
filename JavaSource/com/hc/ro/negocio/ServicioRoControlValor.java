package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoControl;
import com.hc.ro.modelo.RoControlValor;

@Stateless
public class ServicioRoControlValor extends ServicioGenerico<RoControlValor> {

	public ServicioRoControlValor() {
		super(RoControlValor.class, ServicioRoControlValor.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoControlValor> buscarEventoPorControl(RoControl control) {
		Query q = em
				.createQuery("SELECT a FROM RoControlValor a WHERE a.roControl LIKE :paramAttrb");
		q.setParameter("paramAttrb", control);

		return q.getResultList();
	}

	public List<RoControlValor> buscarPorCodigoControl(int control) {
		ArrayList<RoControlValor> respAgs = new ArrayList<RoControlValor>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoCtva, a.tipoCtva FROM RoControlValor a WHERE a.roControl.codigoCtrl =:paramAttrb",
						Object[].class);
		q.setParameter("paramAttrb", control);
		for (Object[] result : q.getResultList()) {
			RoControlValor respAg = new RoControlValor((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}
		return respAgs;
	}

	public String buscarPorIdControlAux(int control) {
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.controlCtva FROM RoControlValor a WHERE a.roControl.codigoCtrl =:paramAttrb",
						Object[].class);
		q.setParameter("paramAttrb", control);
		return (String) q.getSingleResult()[0];
	}
	
	public String buscarPorIdAux(int controlValor) {
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.controlCtva FROM RoControlValor a WHERE a.codigoCtva =:paramAttrb",
						Object[].class);
		q.setParameter("paramAttrb", controlValor);
		return (String) q.getSingleResult()[0];
	}

	public boolean existeControlValorPorNombreControl(
			String nombreControlValor, RoControl roControl) {
		Query q = em
				.createQuery("SELECT a FROM RoControlValor a WHERE a.tipoCtva LIKE :paramNombreAtributo AND a.roControl LIKE :pControl");
		q.setParameter("paramNombreAtributo", nombreControlValor).setParameter(
				"pControl", roControl);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean existeControlValorPorNombreControlEx(
			String nombreControlValor, int codigoControlValor,
			RoControl roControl) {
		Query q = em
				.createQuery("SELECT a FROM RoControlValor a WHERE a.tipoCtva LIKE :paramNombreAtributo AND a.roControl LIKE :pControl AND a.codigoCtva NOT LIKE :paramCodigoAtributo");
		q.setParameter("paramNombreAtributo", nombreControlValor)
				.setParameter("paramCodigoAtributo", codigoControlValor)
				.setParameter("pControl", roControl);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

}