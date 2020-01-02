package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoNegoPro;
import com.hc.ro.modelo.RoNegocio;

@Stateless
public class ServicioRoNegoPros extends ServicioGenerico<RoNegoPro> {

	public ServicioRoNegoPros() {
		super(RoNegoPro.class, ServicioRoNegoPros.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoNegoPro> buscarPorNegocio(String nombreNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoNegoPro a WHERE a.roNegocio.nombreNego LIKE :paramNombreNegocio");
		q.setParameter("paramNombreNegocio", nombreNegocio);

		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoNegoPro> buscarPorProceso(int idProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoNegoPro a WHERE a.roProceso.codigoPros LIKE :paramProceso ORDER BY a.roNegocio.nombreNego");
		q.setParameter("paramProceso", idProceso);

		return q.getResultList();
	}
	
	public List<RoNegoPro> buscarPorProcesoEvaluacion(int idProceso) {
		ArrayList<RoNegoPro> negoPros = new ArrayList<RoNegoPro>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.CODIGO_nego_pros, a.roNegocio.codigoNego, a.roNegocio.nombreNego, a.roNegocio.ancestroNego FROM RoNegoPro a WHERE a.roProceso.codigoPros LIKE :paramProceso ORDER BY a.roNegocio.nombreNego",
						Object[].class);
		q.setParameter("paramProceso", idProceso);
		for (Object[] result : q.getResultList()) {
			RoNegoPro negoPro = new RoNegoPro((Integer) result[0],
					new RoNegocio((Integer) result[1], (String) result[2],
							(String) result[3]));
			negoPros.add(negoPro);
		}

		return negoPros;
	}

	// public void borrarPorNegocio(String nombreNegocio) {
	// System.out.println("entrooooooooo a borrrrrrar");
	// Query q = em
	// .createQuery("delete FROM RoNegoPro a WHERE a.roNegocio.nombreNego LIKE :paramNombreNegocio");
	// int deleted = q.setParameter("paramNombreNegocio",
	// nombreNegocio).executeUpdate();
	//
	// }

	public int contarPorNegocio(String nombreNegocio) {
		try {
			Query q = em
					.createQuery("SELECT a FROM RoNegoPro a WHERE a.roNegocio.nombreNego LIKE :paramNombreNegocio");
			q.setParameter("paramNombreNegocio", nombreNegocio);
			return q.getResultList().size();
		} catch (Exception e) {
			return 0;
		}

		
	}

}
