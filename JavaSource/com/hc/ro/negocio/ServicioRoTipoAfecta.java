package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoAfecta;

@Stateless
public class ServicioRoTipoAfecta extends ServicioGenerico<RoTipoAfecta> {

	public ServicioRoTipoAfecta() {
		super(RoTipoAfecta.class, ServicioRoTipoAfecta.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoTipoAfecta buscarTipoAfectaPorNombre(String nombreTipoAfecta) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAfecta a WHERE a.nombreTafc LIKE :paramNombreTipoAfecta");
		q.setParameter("paramNombreTipoAfecta", nombreTipoAfecta);

		return (RoTipoAfecta) q.getSingleResult();

	}

	public boolean existeTipoAfectaPorNombre(String nombreTipoAfecta) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAfecta a WHERE a.nombreTafc LIKE :paramNombreTipoAfecta");
		q.setParameter("paramNombreTipoAfecta", nombreTipoAfecta);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoAfectaPorNumero(String numeroTipoAfecta) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAfecta a WHERE a.numeroTafc LIKE :paramNombreTipoAfecta");
		q.setParameter("paramNombreTipoAfecta", numeroTipoAfecta);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoAfectaPorNombreEx(String nombreTipoAfecta,
			int codigoTipoAfecta) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAfecta a WHERE a.nombreTafc LIKE :paramNombreTipoAfecta AND a.codigoTafc NOT LIKE :paramCodigoTipoAfecta)");
		q.setParameter("paramNombreTipoAfecta", nombreTipoAfecta).setParameter(
				"paramCodigoTipoAfecta", codigoTipoAfecta);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoAfectaPorNumeroEx(String numeroTipoAfecta, int codigoTipoAfecta) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAfecta a WHERE a.numeroTafc LIKE :paramNombreTipoAfecta AND a.codigoTafc NOT LIKE :paramCodigoTipoAfecta)");
		q.setParameter("paramNombreTipoAfecta", numeroTipoAfecta).setParameter(
				"paramCodigoTipoAfecta", codigoTipoAfecta);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
