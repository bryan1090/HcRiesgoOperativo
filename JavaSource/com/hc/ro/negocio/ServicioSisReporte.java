
package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisReporte;

@Stateless
public class ServicioSisReporte extends ServicioGenerico<SisReporte> {

	public ServicioSisReporte() {
		super(SisReporte.class, ServicioSisReporte.class);
		// TODO Auto-generated constructor stub
	}	
	

	public SisReporte buscarReportePorNombre(String nombreReporte) {
		Query q = em
				.createQuery("SELECT a FROM SisReporte a WHERE a.nombreRepo LIKE :paramNombreReporte");
		q.setParameter("paramNombreReporte", nombreReporte);

		return (SisReporte) q.getSingleResult();

	}

	public boolean existeReportePorNombre(String nombreReporte) {
		Query q = em
				.createQuery("SELECT a FROM SisReporte a WHERE a.nombreRepo LIKE :paramNombreReporte");
		q.setParameter("paramNombreReporte", nombreReporte);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeReportePorNombreEx(String nombreReporte,
			int codigoReporte) {
		Query q = em
				.createQuery("SELECT a FROM SisReporte a WHERE a.nombreRepo LIKE :paramNombreReporte AND a.codigoRepo NOT LIKE :paramCodigoReporte)");
		q.setParameter("paramNombreReporte", nombreReporte).setParameter(
				"paramCodigoReporte", codigoReporte);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}
}