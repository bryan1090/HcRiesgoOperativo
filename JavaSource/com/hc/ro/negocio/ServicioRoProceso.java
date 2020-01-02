package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoResponsable;

@Stateless
public class ServicioRoProceso extends ServicioGenerico<RoProceso> {

	public ServicioRoProceso() {
		super(RoProceso.class, ServicioRoProceso.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoProceso> buscarProcesoPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM RoProceso a WHERE a.ancestroPros LIKE :paramCodigoPadre ORDER BY a.numeroPros");
		q.setParameter("paramCodigoPadre", codigoPadre);
		return q.getResultList();

	}

	public List<RoProceso> buscarProcesoPorPadre_nombrePros(String codigoPadre) {
		ArrayList<RoProceso> procesos = new ArrayList<RoProceso>();
		TypedQuery<String> q = em
				.createQuery(
						"SELECT a.nombrePros FROM RoProceso a WHERE a.ancestroPros LIKE :paramCodigoPadre",
						String.class);
		q.setParameter("paramCodigoPadre", codigoPadre);
		for (String result : q.getResultList()) {
			RoProceso proceso = new RoProceso(result);
			procesos.add(proceso);
		}
		return procesos;
	}

	public RoProceso buscarProcesoPorNombre(String nombreProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoProceso a WHERE a.nombrePros LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);
		return (RoProceso) q.getSingleResult();
	}

	public boolean existeProcesoPorNombre(String nombreProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoProceso a WHERE a.nombrePros LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);

		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
//	public boolean existeProcesoPorNombre(String nombreProceso) {
//		Query q = em
//				.createQuery("SELECT a FROM RoProceso a WHERE a.nombrePros LIKE :paramNombreProceso");
//		q.setParameter("paramNombreProceso", nombreProceso);
//
//		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
//				+ q.toString());
//		if (q.getResultList().size() == 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	public boolean existeProcesoPorNumero(String numeroProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoProceso a WHERE a.numeroPros LIKE :paramNumeroProceso");
		q.setParameter("paramNumeroProceso", numeroProceso);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

//	public boolean existeProcesoPorNombreEx(int codigoProceso) {
//		Query q = em
//				.createQuery("SELECT a FROM RoProceso a WHERE a.codigoPros NOT LIKE :paramCodigoProceso)");
//		q.setParameter("paramCodigoProceso", codigoProceso);
//
//		if (q.getResultList().size() == 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
	public boolean existeProcesoPorNombreEx(String nombreProceso,
			int codigoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoProceso a WHERE a.nombrePros LIKE :paramNombreProceso AND a.codigoPros NOT LIKE :paramCodigoProceso)");
		q.setParameter("paramNombreProceso", nombreProceso).setParameter(
				"paramCodigoProceso", codigoProceso);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	

	public boolean existeProcesoPorNumeroEx(String numeroProceso,
			int codigoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoProceso a WHERE a.numeroPros LIKE :paramNumeroProceso AND a.codigoPros NOT LIKE :paramCodigoProceso)");
		q.setParameter("paramNumeroProceso", numeroProceso).setParameter(
				"paramCodigoProceso", codigoProceso);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}	
	
	public String buscarProcesoPorIdProceso(int idProcesoSeleccionado){
		Query q = this.em
				.createQuery("SELECT a.nombrePros FROM RoProceso a WHERE a.codigoPros LIKE :paramProceso");
		q.setParameter("paramProceso", idProcesoSeleccionado);		
		return (String) q.getSingleResult();
	}
	
	public List<Integer> buscarCodigosProcesoTodos() {
		Query q = em
				.createQuery("SELECT a.codigoPros FROM RoProceso a");


		return q.getResultList();
	}

	
}
