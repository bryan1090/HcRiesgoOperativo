package com.hc.ro.negocio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import com.hc.ro.modelo.SisTiempoSesion;

@Stateless
public class ServicioSisTiempoSesion extends ServicioGenerico<SisTiempoSesion> {

	public ServicioSisTiempoSesion() {
		super(SisTiempoSesion.class, ServicioSisTiempoSesion.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SisTiempoSesion> buscarTodos() {
		Query q = em
				.createQuery("Select b from SisTiempoSesion b Order by b.codigoSesion asc");
		return q.getResultList();
	}

	public String timeSesion(){
		Query q = em
				.createQuery("Select b.tiempoSesion from SisTiempoSesion b Order by b.codigoSesion asc");
		if (!q.getResultList().isEmpty()) {
			String tiempo = (String) q.getResultList().get(0);
			return tiempo;
		}else{
			return null;
		}
	}
	
	public boolean existeTiempoSesionEx(String tiempoSesion, int codigoSesion) {
		Query q = em
				.createQuery("SELECT a FROM SisTiempoSesion a WHERE a.tiempoSesion LIKE :paramTiempoDeSesion AND a.codigoSesion NOT LIKE :paramCodigoTiempoSesion)");
		q.setParameter("paramTiempoDeSesion", tiempoSesion).setParameter(
				"paramCodigoTiempoSesion", codigoSesion);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean existeTiempoSesion(String tiempoSesion) {
		Query q = em
				.createQuery("SELECT a FROM SisTiempoSesion a WHERE a.tiempoSesion LIKE :paramTiempoDeSesion");
		q.setParameter("paramTiempoDeSesion", tiempoSesion);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
