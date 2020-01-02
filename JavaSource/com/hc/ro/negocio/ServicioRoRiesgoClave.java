package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoRiesgoClave;

@Stateless
public class ServicioRoRiesgoClave extends ServicioGenerico<RoRiesgoClave> {

	public ServicioRoRiesgoClave() {
		super(RoRiesgoClave.class, ServicioRoRiesgoClave.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoRiesgoClave buscarRiesgoClavePorNombre(String nombreRiesgoClave) {
		Query q = em
				.createQuery("SELECT a FROM RoRiesgoClave a WHERE a.nombreRicl LIKE :paramNombreRiesgoClave");
		q.setParameter("paramNombreRiesgoClave", nombreRiesgoClave);

		return (RoRiesgoClave) q.getSingleResult();
	}

	public boolean existeRiesgoClavePorNombre(String nombreRiesgoClave) {
		Query q = em
				.createQuery("SELECT a FROM RoRiesgoClave a WHERE a.nombreRicl LIKE :paramNombreRiesgoClave");
		q.setParameter("paramNombreRiesgoClave", nombreRiesgoClave);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeRiesgoClavePorNumero(String numeroRiesgoClave) {
		Query q = em
				.createQuery("SELECT a FROM RoRiesgoClave a WHERE a.numeroRicl LIKE :paramNombreRiesgoClave");
		q.setParameter("paramNombreRiesgoClave", numeroRiesgoClave);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeRiesgoClavePorNombreEx(String nombreRiesgoClave,
			int codigoRiesgoClave) {
		Query q = em
				.createQuery("SELECT a FROM RoRiesgoClave a WHERE a.nombreRicl LIKE :paramNombreRiesgoClave AND a.codigoRicl NOT LIKE :paramCodigoRiesgoClave)");
		q.setParameter("paramNombreRiesgoClave", nombreRiesgoClave).setParameter(
				"paramCodigoRiesgoClave", codigoRiesgoClave);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeRiesgoClavePorNumeroEx(String numeroRiesgoClave, int codigoRiesgoClave) {
		Query q = em
				.createQuery("SELECT a FROM RoRiesgoClave a WHERE a.numeroRicl LIKE :paramNombreRiesgoClave AND a.codigoRicl NOT LIKE :paramCodigoRiesgoClave)");
		q.setParameter("paramNombreRiesgoClave", numeroRiesgoClave).setParameter(
				"paramCodigoRiesgoClave", codigoRiesgoClave);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RoRiesgoClave> buscarRiesgoClavePorProceso(String nombreProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoRiesgoClave a WHERE a.roProceso.nombrePros LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);

		return q.getResultList();
	}

}