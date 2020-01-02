package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoAttrbAdicionale;
import com.hc.ro.modelo.RoValAttrb;

@Stateless
public class ServicioRoValAttrb extends ServicioGenerico<RoValAttrb> {

	public ServicioRoValAttrb() {
		super(RoValAttrb.class, ServicioRoValAttrb.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoValAttrb> buscarEventoPorAttrb(RoAttrbAdicionale attrb) {
		Query q = em
				.createQuery("SELECT a FROM RoValAttrb a WHERE a.roAttrbAdicionales LIKE :paramAttrb");
		q.setParameter("paramAttrb", attrb);

		return q.getResultList();
	}

	public List<RoValAttrb> buscarPorAttrbCodigoAtributo(int attrb) {
		ArrayList<RoValAttrb> respAgs = new ArrayList<RoValAttrb>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoValorAttr, a.variableValAttr FROM RoValAttrb a WHERE a.roAttrbAdicionales.codigoAttr =:paramAttrb",
						Object[].class);
		q.setParameter("paramAttrb", attrb);
		for (Object[] result : q.getResultList()) {
			RoValAttrb respAg = new RoValAttrb((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}
		return respAgs;
	}

	public boolean existeValAttrbPorNombreAttrbClase(String nombreValAttrb,
			RoAttrbAdicionale roValAttrb, String clase) {
		Query q = em
				.createQuery("SELECT a FROM RoValAttrb a WHERE a.variableValAttr LIKE :paramNombreAtributo AND a.roAttrbAdicionales LIKE :pControl AND a.roAttrbAdicionales.claseAttr LIKE :pClase");
		q.setParameter("paramNombreAtributo", nombreValAttrb)
				.setParameter("pControl", roValAttrb)
				.setParameter("pClase", clase);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean existeValAttrbPorNombreAttrbClaseEx(String nombreValAttrb,
			RoAttrbAdicionale roValAttrb, String clase, int codigo) {
		Query q = em
				.createQuery("SELECT a FROM RoValAttrb a WHERE a.variableValAttr LIKE :paramNombreAtributo AND a.roAttrbAdicionales LIKE :pControl AND a.roAttrbAdicionales.claseAttr LIKE :pClase AND a.codigoValorAttr NOT LIKE :pCodigo");
		q.setParameter("paramNombreAtributo", nombreValAttrb)
				.setParameter("pControl", roValAttrb)
				.setParameter("pClase", clase).setParameter("pCodigo", codigo);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}
	}
}