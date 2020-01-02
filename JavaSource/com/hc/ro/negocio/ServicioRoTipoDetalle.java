package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoDetalle;

@Stateless
public class ServicioRoTipoDetalle extends ServicioGenerico<RoTipoDetalle> {

	public ServicioRoTipoDetalle() {
		super(RoTipoDetalle.class, ServicioRoTipoDetalle.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoTipoDetalle buscarTipoDetallePorNombre(String nombreTipoDetalle) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoDetalle a WHERE a.nombreTdro LIKE :paramNombreTipoDetalle");
		q.setParameter("paramNombreTipoDetalle", nombreTipoDetalle);

		return (RoTipoDetalle) q.getSingleResult();

	}

	public boolean existeTipoDetallePorNombre(String nombreTipoDetalle) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoDetalle a WHERE a.nombreTdro LIKE :paramNombreTipoDetalle");
		q.setParameter("paramNombreTipoDetalle", nombreTipoDetalle);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoDetallePorNumero(String numeroTipoDetalle) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoDetalle a WHERE a.numeroTdro LIKE :paramNombreTipoDetalle");
		q.setParameter("paramNombreTipoDetalle", numeroTipoDetalle);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoDetallePorNombreEx(String nombreTipoDetalle,
			int codigoTipoDetalle) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoDetalle a WHERE a.nombreTdro LIKE :paramNombreTipoDetalle AND a.codigoTdro NOT LIKE :paramCodigoTipoDetalle)");
		q.setParameter("paramNombreTipoDetalle", nombreTipoDetalle).setParameter(
				"paramCodigoTipoDetalle", codigoTipoDetalle);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoDetallePorNumeroEx(String numeroTipoDetalle, int codigoTipoDetalle) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoDetalle a WHERE a.numeroTdro LIKE :paramNombreTipoDetalle AND a.codigoTdro NOT LIKE :paramCodigoTipoDetalle)");
		q.setParameter("paramNombreTipoDetalle", numeroTipoDetalle).setParameter(
				"paramCodigoTipoDetalle", codigoTipoDetalle);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
