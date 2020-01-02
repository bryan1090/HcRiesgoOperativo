package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoNegocio;

@Stateless
public class ServicioRoTipoNegocio extends ServicioGenerico<RoTipoNegocio> {

	public ServicioRoTipoNegocio() {
		super(RoTipoNegocio.class, ServicioRoTipoNegocio.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoTipoNegocio buscarTipoNegocioPorNombre(String nombreTipoNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoNegocio a WHERE a.nombreTneg LIKE :paramNombreTipoNegocio");
		q.setParameter("paramNombreTipoNegocio", nombreTipoNegocio);

		return (RoTipoNegocio) q.getSingleResult();

	}

	public boolean existeTipoNegocioPorNombre(String nombreTipoNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoNegocio a WHERE a.nombreTneg LIKE :paramNombreTipoNegocio");
		q.setParameter("paramNombreTipoNegocio", nombreTipoNegocio);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoNegocioPorNumero(String numeroTipoNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoNegocio a WHERE a.numeroTneg LIKE :paramNombreTipoNegocio");
		q.setParameter("paramNombreTipoNegocio", numeroTipoNegocio);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoNegocioPorNombreEx(String nombreTipoNegocio,
			int codigoTipoNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoNegocio a WHERE a.nombreTneg LIKE :paramNombreTipoNegocio AND a.codigoTneg NOT LIKE :paramCodigoTipoNegocio)");
		q.setParameter("paramNombreTipoNegocio", nombreTipoNegocio).setParameter(
				"paramCodigoTipoNegocio", codigoTipoNegocio);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoNegocioPorNumeroEx(String numeroTipoNegocio, int codigoTipoNegocio) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoNegocio a WHERE a.numeroTneg LIKE :paramNombreTipoNegocio AND a.codigoTneg NOT LIKE :paramCodigoTipoNegocio)");
		q.setParameter("paramNombreTipoNegocio", numeroTipoNegocio).setParameter(
				"paramCodigoTipoNegocio", codigoTipoNegocio);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
