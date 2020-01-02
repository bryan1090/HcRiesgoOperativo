package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoRespPro;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.SisUsuario;

@Stateless
public class ServicioRoRespPro extends ServicioGenerico<RoRespPro> {

	public ServicioRoRespPro() {
		super(RoRespPro.class, ServicioRoRespPro.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoRespPro> buscarRespProcesoPorProceso(String nombreProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoRespPro a WHERE a.roProceso.nombrePros LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);

		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoRespPro> buscarRespProcesoPorProcesoCodigo(int codigoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoRespPro a WHERE a.roProceso.codigoPros LIKE :paramNombreProcesoCodigo");
		q.setParameter("paramNombreProcesoCodigo", codigoProceso);

		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoRespPro> buscarRespProcesoPorProceso(RoProceso nombreProceso) {
		Query q = em
				.createQuery("SELECT b FROM RoRespPro b WHERE b.roResponsable LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RoRespPro> buscarRespProcesoPorUsuario(SisUsuario usuario) {
		Query q = em
				.createQuery("SELECT a FROM RoRespPro a WHERE a.roResponsable.sisUsuario LIKE :paramUsuario ORDER BY a.roProceso.nombrePros");
		q.setParameter("paramUsuario", usuario);

		return q.getResultList();
	}
	
	
	
	

	public List<RoRespPro> buscarRespProcesoPorUsuarioEvaluacion(
			SisUsuario usuario) {

		
		ArrayList<RoRespPro> respPros = new ArrayList<RoRespPro>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.CODIGO_resp_pros, a.roProceso.codigoPros, a.roProceso.nombrePros, a.roProceso.ancestroPros FROM RoRespPro a WHERE a.roResponsable.sisUsuario LIKE :paramUsuario  ORDER BY a.roProceso.nombrePros",
						Object[].class);
		//and a.roProceso.genNivelProceso = 1
		q.setParameter("paramUsuario", usuario);
		for (Object[] result : q.getResultList()) {
			RoRespPro respPro = new RoRespPro((Integer) result[0],
					new RoProceso((Integer) result[1], (String) result[2],
							(String) result[3]));
			respPros.add(respPro);
		}

		return respPros;
	}
	
	public List<RoRespPro> buscarRespProcesoPorUsuarioEvaluacion(
			int codigoUsuario) {

		
		ArrayList<RoRespPro> respPros = new ArrayList<RoRespPro>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.CODIGO_resp_pros, a.roProceso.codigoPros, a.roProceso.nombrePros, a.roProceso.ancestroPros FROM RoRespPro a WHERE a.roResponsable.sisUsuario.codigoUsua LIKE :paramCodigoUsuario  ORDER BY a.roProceso.nombrePros",
						Object[].class);
		//and a.roProceso.genNivelProceso = 1
		q.setParameter("paramCodigoUsuario", codigoUsuario);
		
		for (Object[] result : q.getResultList()) {
			RoRespPro respPro = new RoRespPro((Integer) result[0],
					new RoProceso((Integer) result[1], (String) result[2],
							(String) result[3]));
			respPros.add(respPro);
		}

		return respPros;
	}
	
	public List<RoRespPro> buscarRepSubProcesoPorUsuarioYPorProceso(String nombreProceso){
		ArrayList<RoRespPro> respPros = new ArrayList<RoRespPro>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.CODIGO_resp_pros, a.roProceso.codigoPros, a.roProceso.nombrePros, a.roProceso.ancestroPros FROM RoRespPro a WHERE a.roProceso.ancestroPros LIKE :paramProceso ORDER BY a.roProceso.codigoPros",
						Object[].class);
		q.setParameter("paramProceso", nombreProceso);
		for (Object[] result : q.getResultList()) {
			RoRespPro respPro = new RoRespPro((Integer) result[0],
					new RoProceso((Integer) result[1], (String) result[2],
							(String) result[3]));
			respPros.add(respPro);
		}

		return respPros;
	}
	
	public List<RoRespPro> buscarRespProcesoPorResponsable(RoResponsable responsable) {
		Query q = em
				.createQuery("SELECT a FROM RoRespPro a WHERE a.roResponsable LIKE :paramResponsable");
		q.setParameter("paramResponsable", responsable);

		return q.getResultList();
	}
	
//	public List<Integer> buscarCodigosProcesoTodos() {
//		Query q = em
//				.createQuery("SELECT a.roProceso.codigoPros FROM RoRespPro a");
//
//		return q.getResultList();
//	}
	
	public List<Integer> buscarCodigosProcesoPorResponsable(RoResponsable responsable) {
		Query q = em
				.createQuery("SELECT a.roProceso.codigoPros FROM RoRespPro a WHERE a.roResponsable LIKE :paramResponsable");
		q.setParameter("paramResponsable", responsable);

		return q.getResultList();
	}
	
	
//	public int eliminarRespProcesoPorCodigoResponsable(int codigoResponsable){
//		
//		
//			TypedQuery<Object[]> q=
//				em.createQuery("Select a from RoRespPro a where a.RoResponsable=:codigoResponsable",Object[].class);
//		q.setParameter("codigoResponsable", codigoResponsable);
//		//int borrado=q.executeUpdate();
//		//return borrado;
//		for(Object[] result :q.getResultList())
//		{
//			
//		}
//	}

	
	
//	public List<RoRespPro> buscarRespProcesoPorUsuarioEvaluacion(
//			SisUsuario usuario) {
//		ArrayList<RoRespPro> respPros = new ArrayList<RoRespPro>();
//		TypedQuery<Object[]> q = em
//				.createQuery(
//						"SELECT a.CODIGO_resp_pros, a.roProceso.codigoPros, a.roProceso.nombrePros, a.roProceso.ancestroPros FROM RoRespPro a WHERE a.roResponsable.sisUsuario LIKE :paramUsuario and a.roProceso.genNivelProceso = 3 ORDER BY a.roProceso.codigoPros",
//						Object[].class);
//		q.setParameter("paramUsuario", usuario);
//		for (Object[] result : q.getResultList()) {
//			RoRespPro respPro = new RoRespPro((Integer) result[0],
//					new RoProceso((Integer) result[1], (String) result[2],
//							(String) result[3]));
//			respPros.add(respPro);
//		}
//
//		return respPros;
//	}

}
