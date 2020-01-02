package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoCatObjetivo;

@Stateless
public class ServicioRoCatObjetivo extends ServicioGenerico<RoCatObjetivo> {

	public ServicioRoCatObjetivo() {
		super(RoCatObjetivo.class, ServicioRoCatObjetivo.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<RoCatObjetivo> buscarCatObjetivoPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM RoCatObjetivo a WHERE a.ancestroCobj LIKE :paramCodigoPadre ORDER BY a.numeroCobj");
		q.setParameter("paramCodigoPadre", codigoPadre);

		return q.getResultList();

	}
	
	

	public RoCatObjetivo buscarCatObjetivoPorNombre(String nombreCatObjetivo) {
		Query q = em
				.createQuery("SELECT a FROM RoCatObjetivo a WHERE a.nombreCobj LIKE :paramNombreCatObjetivo");
		q.setParameter("paramNombreCatObjetivo", nombreCatObjetivo);

		return (RoCatObjetivo) q.getSingleResult();

	}

	public boolean existeCatObjetivoPorNombre(String nombreCatObjetivo) {
		Query q = em
				.createQuery("SELECT a FROM RoCatObjetivo a WHERE a.nombreCobj LIKE :paramNombreCatObjetivo");
		q.setParameter("paramNombreCatObjetivo", nombreCatObjetivo);
		
		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCatObjetivoPorNumero(String numeroCatObjetivo) {
		Query q = em
				.createQuery("SELECT a FROM RoCatObjetivo a WHERE a.numeroCobj LIKE :paramNumeroCatObjetivo");
		q.setParameter("paramNumeroCatObjetivo", numeroCatObjetivo);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCatObjetivoPorNombreEx(String nombreCatObjetivo,
			int codigoCatObjetivo) {
		Query q = em
				.createQuery("SELECT a FROM RoCatObjetivo a WHERE a.nombreCobj LIKE :paramNombreCatObjetivo AND a.codigoCobj NOT LIKE :paramCodigoCatObjetivo)");
		q.setParameter("paramNombreCatObjetivo", nombreCatObjetivo).setParameter(
				"paramCodigoCatObjetivo", codigoCatObjetivo);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCatObjetivoPorNumeroEx(String numeroCatObjetivo, int codigoCatObjetivo) {
		Query q = em
				.createQuery("SELECT a FROM RoCatObjetivo a WHERE a.numeroCobj LIKE :paramNumeroCatObjetivo AND a.codigoCobj NOT LIKE :paramCodigoCatObjetivo)");
		q.setParameter("paramNumeroCatObjetivo", numeroCatObjetivo).setParameter(
				"paramCodigoCatObjetivo", codigoCatObjetivo);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}
}
