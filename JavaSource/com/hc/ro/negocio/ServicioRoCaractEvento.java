package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoCaractEvento;

@Stateless
public class ServicioRoCaractEvento extends ServicioGenerico<RoCaractEvento> {

	public ServicioRoCaractEvento() {
		super(RoCaractEvento.class, ServicioRoCaractEvento.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoCaractEvento buscarCaractEventoPorNombre(String nombreCaractEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoCaractEvento a WHERE a.nombreCaev LIKE :paramNombreCaractEvento");
		q.setParameter("paramNombreCaractEvento", nombreCaractEvento);

		return (RoCaractEvento) q.getSingleResult();

	}

	public boolean existeCaractEventoPorNombre(String nombreCaractEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoCaractEvento a WHERE a.nombreCaev LIKE :paramNombreCaractEvento");
		q.setParameter("paramNombreCaractEvento", nombreCaractEvento);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCaractEventoPorNumero(String numeroCaractEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoCaractEvento a WHERE a.numeroCaev LIKE :paramNombreCaractEvento");
		q.setParameter("paramNombreCaractEvento", numeroCaractEvento);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCaractEventoPorNombreEx(String nombreCaractEvento,
			int codigoCaractEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoCaractEvento a WHERE a.nombreCaev LIKE :paramNombreCaractEvento AND a.codigoCaev NOT LIKE :paramCodigoCaractEvento)");
		q.setParameter("paramNombreCaractEvento", nombreCaractEvento).setParameter(
				"paramCodigoCaractEvento", codigoCaractEvento);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCaractEventoPorNumeroEx(String numeroCaractEvento, int codigoCaractEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoCaractEvento a WHERE a.numeroCaev LIKE :paramNombreCaractEvento AND a.codigoCaev NOT LIKE :paramCodigoCaractEvento)");
		q.setParameter("paramNombreCaractEvento", numeroCaractEvento).setParameter(
				"paramCodigoCaractEvento", codigoCaractEvento);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
