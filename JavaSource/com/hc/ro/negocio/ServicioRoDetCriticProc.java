package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoDetCriticProc;

@Stateless
public class ServicioRoDetCriticProc extends ServicioGenerico<RoDetCriticProc> {

	public ServicioRoDetCriticProc() {
		super(RoDetCriticProc.class, ServicioRoDetCriticProc.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoDetCriticProc buscarDetCriticProcPorNombre(String nombreDetCriticProc) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCriticProc a WHERE a.nombreDcpr LIKE :paramNombreDetCriticProc");
		q.setParameter("paramNombreDetCriticProc", nombreDetCriticProc);

		return (RoDetCriticProc) q.getSingleResult();
	}

	public boolean existeDetCriticProcPorNombre(String nombreDetCriticProc) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCriticProc a WHERE a.nombreDcpr LIKE :paramNombreDetCriticProc");
		q.setParameter("paramNombreDetCriticProc", nombreDetCriticProc);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeDetCriticProcPorNumero(String numeroDetCriticProc) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCriticProc a WHERE a.numeroDcpr LIKE :paramNombreDetCriticProc");
		q.setParameter("paramNombreDetCriticProc", numeroDetCriticProc);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeDetCriticProcPorNombreEx(String nombreDetCriticProc,
			int codigoDetCriticProc) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCriticProc a WHERE a.nombreDcpr LIKE :paramNombreDetCriticProc AND a.codigoDcpr NOT LIKE :paramCodigoDetCriticProc)");
		q.setParameter("paramNombreDetCriticProc", nombreDetCriticProc).setParameter(
				"paramCodigoDetCriticProc", codigoDetCriticProc);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeDetCriticProcPorNumeroEx(String numeroDetCriticProc, int codigoDetCriticProc) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCriticProc a WHERE a.numeroDcpr LIKE :paramNombreDetCriticProc AND a.codigoDcpr NOT LIKE :paramCodigoDetCriticProc)");
		q.setParameter("paramNombreDetCriticProc", numeroDetCriticProc).setParameter(
				"paramCodigoDetCriticProc", codigoDetCriticProc);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RoDetCriticProc> buscarDetCriticProcPorProceso(String nombreProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCriticProc a WHERE a.roProceso.nombrePros LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);

		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoDetCriticProc> buscarDetCriticProcPorProcesoCodigo(int codigoProceso) {
		Query q = em
				.createQuery("SELECT a FROM RoDetCriticProc a WHERE a.roProceso.codigoPros LIKE :paramNombreProcesoCodigo");
		q.setParameter("paramNombreProcesoCodigo", codigoProceso);

		return q.getResultList();
	}
}
