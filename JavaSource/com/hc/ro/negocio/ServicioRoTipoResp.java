package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoResp;

@Stateless
public class ServicioRoTipoResp extends ServicioGenerico<RoTipoResp> {

	public ServicioRoTipoResp() {
		super(RoTipoResp.class, ServicioRoTipoResp.class);
		// TODO Auto-generated constructor stub
	}

	public RoTipoResp buscarTipoRespPorNombre(String nombreTipoResp) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoResp a WHERE a.nombreTres LIKE :paramNombreTres");
		q.setParameter("paramNombreTres", nombreTipoResp);

		return (RoTipoResp) q.getSingleResult();

	}

	public boolean existeTipoRespPorNombre(String nombreTipoResp) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoResp a WHERE a.nombreTres LIKE :paramNombreTres");
		q.setParameter("paramNombreTres", nombreTipoResp);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existePorNombreEx(String nombre, int codigo) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoResp a WHERE a.nombreTres LIKE :paramNombre AND a.codigoTres NOT LIKE :paramCodigo");
		q.setParameter("paramNombre", nombre).setParameter("paramCodigo",
				codigo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
	
	


}
