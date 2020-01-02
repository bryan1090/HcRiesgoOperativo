package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoCalifRiesgo;

@Stateless
public class ServicioRoCalifRiesgo extends ServicioGenerico<RoCalifRiesgo> {

	public ServicioRoCalifRiesgo() {
		super(RoCalifRiesgo.class, ServicioRoCalifRiesgo.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<RoCalifRiesgo> buscarCalifRiesgoPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM RoCalifRiesgo a WHERE a.ancestroClrs LIKE :paramCodigoPadre ORDER BY a.numeroClrs");
		q.setParameter("paramCodigoPadre", codigoPadre);

		return q.getResultList();

	}
	
	

	public RoCalifRiesgo buscarCalifRiesgoPorNombre(String nombreCalifRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoCalifRiesgo a WHERE a.nombreClrs LIKE :paramNombreCalifRiesgo");
		q.setParameter("paramNombreCalifRiesgo", nombreCalifRiesgo);

		return (RoCalifRiesgo) q.getSingleResult();

	}

	public boolean existeCalifRiesgoPorNombre(String nombreCalifRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoCalifRiesgo a WHERE a.nombreClrs LIKE :paramNombreCalifRiesgo");
		q.setParameter("paramNombreCalifRiesgo", nombreCalifRiesgo);
		
		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCalifRiesgoPorNumero(String numeroCalifRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoCalifRiesgo a WHERE a.numeroClrs LIKE :paramNumeroCalifRiesgo");
		q.setParameter("paramNumeroCalifRiesgo", numeroCalifRiesgo);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCalifRiesgoPorNombreEx(String nombreCalifRiesgo,
			int codigoCalifRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoCalifRiesgo a WHERE a.nombreClrs LIKE :paramNombreCalifRiesgo AND a.codigoClrs NOT LIKE :paramCodigoCalifRiesgo)");
		q.setParameter("paramNombreCalifRiesgo", nombreCalifRiesgo).setParameter(
				"paramCodigoCalifRiesgo", codigoCalifRiesgo);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeCalifRiesgoPorNumeroEx(String numeroCalifRiesgo, int codigoCalifRiesgo) {
		Query q = em
				.createQuery("SELECT a FROM RoCalifRiesgo a WHERE a.numeroClrs LIKE :paramNumeroCalifRiesgo AND a.codigoClrs NOT LIKE :paramCodigoCalifRiesgo)");
		q.setParameter("paramNumeroCalifRiesgo", numeroCalifRiesgo).setParameter(
				"paramCodigoCalifRiesgo", codigoCalifRiesgo);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}
	
	
}
