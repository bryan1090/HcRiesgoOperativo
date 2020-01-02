package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoCargo;

@Stateless
public class ServicioRoTipoCargo extends ServicioGenerico<RoTipoCargo> {

	public ServicioRoTipoCargo() {
		super(RoTipoCargo.class, ServicioRoTipoCargo.class);
	}
	
	public boolean existeRoTipoCargoPorCodigoEx(String nombreCargo, Integer codigoCargo) {
		
		Query q = em
				.createQuery("SELECT r FROM RoTipoCargo r WHERE r.nombreCargo LIKE :paramNombreTipoCargo AND r.codigoCargo NOT LIKE :paramCodigoTipoCargo)");
		q.setParameter("paramNombreTipoCargo", nombreCargo).setParameter(
				"paramCodigoTipoCargo", codigoCargo);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean existeRoTipoCargoPorNombre(String nombreCargo) {
		Query q = em
				.createQuery("SELECT r FROM RoTipoCargo r WHERE r.nombreCargo LIKE :paramNombreTipoCliente");
		q.setParameter("paramNombreTipoCliente", nombreCargo);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
