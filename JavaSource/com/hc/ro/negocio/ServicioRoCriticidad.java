package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoCriticidad;

@Stateless
public class ServicioRoCriticidad extends ServicioGenerico<RoCriticidad> {

	public ServicioRoCriticidad() {
		super(RoCriticidad.class, ServicioRoCriticidad.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoCriticidad buscarCriticidadPorNombre(String nombreCriticidad) {
		Query q = em
				.createQuery("SELECT a FROM RoCriticidad a WHERE a.nombreCrit LIKE :paramNombreCriticidad");
		q.setParameter("paramNombreCriticidad", nombreCriticidad);

		return (RoCriticidad) q.getSingleResult();

	}

	public boolean existeCriticidadPorNombre(String nombreCriticidad) {
		Query q = em
				.createQuery("SELECT a FROM RoCriticidad a WHERE a.nombreCrit LIKE :paramNombreCriticidad");
		q.setParameter("paramNombreCriticidad", nombreCriticidad);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCriticidadPorNumero(String numeroCriticidad) {
		Query q = em
				.createQuery("SELECT a FROM RoCriticidad a WHERE a.numeroCrit LIKE :paramNombreCriticidad");
		q.setParameter("paramNombreCriticidad", numeroCriticidad);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCriticidadPorNombreEx(String nombreCriticidad,
			int codigoCriticidad) {
		Query q = em
				.createQuery("SELECT a FROM RoCriticidad a WHERE a.nombreCrit LIKE :paramNombreCriticidad AND a.codigoCrit NOT LIKE :paramCodigoCriticidad)");
		q.setParameter("paramNombreCriticidad", nombreCriticidad).setParameter(
				"paramCodigoCriticidad", codigoCriticidad);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCriticidadPorNumeroEx(String numeroCriticidad, int codigoCriticidad) {
		Query q = em
				.createQuery("SELECT a FROM RoCriticidad a WHERE a.numeroCrit LIKE :paramNombreCriticidad AND a.codigoCrit NOT LIKE :paramCodigoCriticidad)");
		q.setParameter("paramNombreCriticidad", numeroCriticidad).setParameter(
				"paramCodigoCriticidad", codigoCriticidad);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
