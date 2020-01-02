package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoResponsable;

@Stateless
public class ServicioRoResponsable extends ServicioGenerico<RoResponsable> {

	public ServicioRoResponsable() {
		super(RoResponsable.class, ServicioRoResponsable.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoResponsable buscarResponsablePorNombre(String nombreResponsable) {
		Query q = em
				.createQuery("SELECT a FROM RoResponsable a WHERE a.nombreResp LIKE :paramNombreResponsable");
		q.setParameter("paramNombreResponsable", nombreResponsable);

		return (RoResponsable) q.getSingleResult();

	}

	public boolean existeResponsablePorNombre(String nombreResponsable) {
		Query q = em
				.createQuery("SELECT a FROM RoResponsable a WHERE a.nombreResp LIKE :paramNombreResponsable");
		q.setParameter("paramNombreResponsable", nombreResponsable);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeResponsablePorNumero(String numeroResponsable) {
		Query q = em
				.createQuery("SELECT a FROM RoResponsable a WHERE a.numeroResp LIKE :paramNombreResponsable");
		q.setParameter("paramNombreResponsable", numeroResponsable);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeResponsablePorNombreEx(String nombreResponsable,
			int codigoResponsable) {
		Query q = em
				.createQuery("SELECT a FROM RoResponsable a WHERE a.nombreResp LIKE :paramNombreResponsable AND a.codigoResp NOT LIKE :paramCodigoResponsable)");
		q.setParameter("paramNombreResponsable", nombreResponsable).setParameter(
				"paramCodigoResponsable", codigoResponsable);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeResponsablePorNumeroEx(String numeroResponsable, int codigoResponsable) {
		Query q = em
				.createQuery("SELECT a FROM RoResponsable a WHERE a.numeroResp LIKE :paramNombreResponsable AND a.codigoResp NOT LIKE :paramCodigoResponsable)");
		q.setParameter("paramNombreResponsable", numeroResponsable).setParameter(
				"paramCodigoResponsable", codigoResponsable);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}
		
	}
	
	public RoResponsable buscarResponsablePorNombreApellido(String nombreResponsable, String apellidoResponsable) {
		Query q = em
				.createQuery("SELECT a FROM RoResponsable a WHERE a.nombreResp LIKE :paramNombreResponsable and a.apellidoResp LIKE :paramapellidoResponsable");
		q.setParameter("paramNombreResponsable", nombreResponsable);
		q.setParameter("paramapellidoResponsable", apellidoResponsable);

		return (RoResponsable) q.getSingleResult();
	}


	

}
