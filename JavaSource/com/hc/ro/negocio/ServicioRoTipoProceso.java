package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoProceso;

@Stateless
public class ServicioRoTipoProceso extends ServicioGenerico<RoTipoProceso> {

	public ServicioRoTipoProceso() {
		super(RoTipoProceso.class, ServicioRoTipoProceso.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoTipoProceso buscarTipoProcesoPorNombre(String nombreTipoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoProceso a WHERE a.nombreTipr LIKE :paramNombreTipoProceso");
		q.setParameter("paramNombreTipoProceso", nombreTipoProceso);

		return (RoTipoProceso) q.getSingleResult();

	}

	public boolean existeTipoProcesoPorNombre(String nombreTipoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoProceso a WHERE a.nombreTipr LIKE :paramNombreTipoProceso");
		q.setParameter("paramNombreTipoProceso", nombreTipoProceso);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoProcesoPorNumero(String numeroTipoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoProceso a WHERE a.numeroTipr LIKE :paramNombreTipoProceso");
		q.setParameter("paramNombreTipoProceso", numeroTipoProceso);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoProcesoPorNombreEx(String nombreTipoProceso,
			int codigoTipoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoProceso a WHERE a.nombreTipr LIKE :paramNombreTipoProceso AND a.codigoTipr NOT LIKE :paramCodigoTipoProceso)");
		q.setParameter("paramNombreTipoProceso", nombreTipoProceso).setParameter(
				"paramCodigoTipoProceso", codigoTipoProceso);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoProcesoPorNumeroEx(String numeroTipoProceso, int codigoTipoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoProceso a WHERE a.numeroTipr LIKE :paramNombreTipoProceso AND a.codigoTipr NOT LIKE :paramCodigoTipoProceso)");
		q.setParameter("paramNombreTipoProceso", numeroTipoProceso).setParameter(
				"paramCodigoTipoProceso", codigoTipoProceso);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
