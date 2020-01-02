package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoEjecucion;

@Stateless
public class ServicioRoTipoEjecucion extends ServicioGenerico<RoTipoEjecucion> {

	public ServicioRoTipoEjecucion() {
		super(RoTipoEjecucion.class, ServicioRoTipoEjecucion.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoTipoEjecucion buscarTipoEjecucionPorNombre(String nombreTipoEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoEjecucion a WHERE a.nombreTiej LIKE :paramNombreTipoEjecucion");
		q.setParameter("paramNombreTipoEjecucion", nombreTipoEjecucion);

		return (RoTipoEjecucion) q.getSingleResult();

	}

	public boolean existeTipoEjecucionPorNombre(String nombreTipoEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoEjecucion a WHERE a.nombreTiej LIKE :paramNombreTipoEjecucion");
		q.setParameter("paramNombreTipoEjecucion", nombreTipoEjecucion);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoEjecucionPorNumero(String numeroTipoEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoEjecucion a WHERE a.numeroTiej LIKE :paramNombreTipoEjecucion");
		q.setParameter("paramNombreTipoEjecucion", numeroTipoEjecucion);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoEjecucionPorNombreEx(String nombreTipoEjecucion,
			int codigoTipoEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoEjecucion a WHERE a.nombreTiej LIKE :paramNombreTipoEjecucion AND a.codigoTiej NOT LIKE :paramCodigoTipoEjecucion)");
		q.setParameter("paramNombreTipoEjecucion", nombreTipoEjecucion).setParameter(
				"paramCodigoTipoEjecucion", codigoTipoEjecucion);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoEjecucionPorNumeroEx(String numeroTipoEjecucion, int codigoTipoEjecucion) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoEjecucion a WHERE a.numeroTiej LIKE :paramNombreTipoEjecucion AND a.codigoTiej NOT LIKE :paramCodigoTipoEjecucion)");
		q.setParameter("paramNombreTipoEjecucion", numeroTipoEjecucion).setParameter(
				"paramCodigoTipoEjecucion", codigoTipoEjecucion);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
