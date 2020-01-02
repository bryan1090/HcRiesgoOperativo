package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoControl;

@Stateless
public class ServicioRoControl extends ServicioGenerico<RoControl> {

	public ServicioRoControl() {
		super(RoControl.class, ServicioRoControl.class);
		// TODO Auto-generated constructor stub
	}

	public List<RoControl> buscarTodosNombreCodigo() {
		ArrayList<RoControl> respAgs = new ArrayList<RoControl>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.codigoCtrl, b.nombreControl FROM RoControl b ORDER BY b.nombreControl",
						Object[].class);
		for (Object[] result : q.getResultList()) {
			RoControl respAg = new RoControl((Integer) result[0],
					(String) result[1]);
			respAgs.add(respAg);
		}
		return respAgs;
	}

	@SuppressWarnings("unchecked")
	public List<RoControl> buscarEventoPorClase(String clase) {
		Query q = em
				.createQuery("SELECT a FROM RoControl a WHERE a.claseAttr LIKE :paramClase");
		q.setParameter("paramClase", clase);

		return q.getResultList();
	}

	public RoControl buscarPorNombreClase(String clase, String nombre) {
		Query q = em
				.createQuery("SELECT a FROM RoControl a WHERE a.claseAttr LIKE :paramClase AND a.nombreAttr LIKE paramNombre");
		q.setParameter("paramClase", clase).setParameter("paramNombre", nombre);

		return (RoControl) q.getSingleResult();
	}

	public boolean existeControlPorNombre(String nombreControl) {
		Query q = em
				.createQuery("SELECT a FROM RoControl a WHERE a.nombreControl LIKE :paramNombreAtributo");
		q.setParameter("paramNombreAtributo", nombreControl);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean existeControlPorNombreEx(String nombreControl,
			int codigoControl) {
		Query q = em
				.createQuery("SELECT a FROM RoControl a WHERE a.nombreControl LIKE :paramNombreAtributo AND a.codigoCtrl NOT LIKE :paramCodigoAtributo");
		q.setParameter("paramNombreAtributo", nombreControl).setParameter(
				"paramCodigoAtributo", codigoControl);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}
	
	
	
	
	
	
	
}