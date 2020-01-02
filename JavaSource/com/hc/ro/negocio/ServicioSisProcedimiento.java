
package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisProcedimiento;

@Stateless
public class ServicioSisProcedimiento extends ServicioGenerico<SisProcedimiento> {

	public ServicioSisProcedimiento() {
		super(SisProcedimiento.class, ServicioSisProcedimiento.class);
		// TODO Auto-generated constructor stub
	}	
	

	public SisProcedimiento buscarProcedimientoPorNombre(String nombreProcedimiento) {
		Query q = em
				.createQuery("SELECT a FROM SisProcedimiento a WHERE a.nombreProc LIKE :paramNombreProcedimiento");
		q.setParameter("paramNombreProcedimiento", nombreProcedimiento);

		return (SisProcedimiento) q.getSingleResult();

	}

	public boolean existeProcedimientoPorNombre(String nombreProcedimiento) {
		Query q = em
				.createQuery("SELECT a FROM SisProcedimiento a WHERE a.nombreProc LIKE :paramNombreProcedimiento");
		q.setParameter("paramNombreProcedimiento", nombreProcedimiento);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeProcedimientoPorNombreEx(String nombreProcedimiento,
			int codigoProcedimiento) {
		Query q = em
				.createQuery("SELECT a FROM SisProcedimiento a WHERE a.nombreProc LIKE :paramNombreProcedimiento AND a.codigoProc NOT LIKE :paramCodigoProcedimiento)");
		q.setParameter("paramNombreProcedimiento", nombreProcedimiento).setParameter(
				"paramCodigoProcedimiento", codigoProcedimiento);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}
	
}