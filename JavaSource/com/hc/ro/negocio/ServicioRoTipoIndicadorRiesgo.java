package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoTipoIndicadorRiesgo;

@Stateless
public class ServicioRoTipoIndicadorRiesgo extends ServicioGenerico<RoTipoIndicadorRiesgo>{

	public ServicioRoTipoIndicadorRiesgo() {
		super(RoTipoIndicadorRiesgo.class, ServicioRoTipoIndicadorRiesgo.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RoTipoIndicadorRiesgo> buscarTodos() {
		Query q = em
				.createQuery("Select b from RoTipoIndicadorRiesgo b Order by b.codigoTinri asc");
		return q.getResultList();
	}

	public int numeroOcurrencias(){
		Query q = em
				.createQuery("Select b.numOcurr from RoTipoIndicadorRiesgo b Order by b.codigoTinri asc");
		if (!q.getResultList().isEmpty()) {
			int numOcur = (Integer) q.getResultList().get(0);
			return numOcur;
		}else{
			return 0;
		}
	}
	public int indicador(){
		Query q = em
				.createQuery("Select b.limite from RoTipoIndicadorRiesgo b Order by b.codigoTinri asc");
		
		if (!q.getResultList().isEmpty()) {
			Integer limite = (Integer) q.getResultList().get(0);
			return limite;
		}else{
			return 0;
		}
	}
	
	public RoTipoIndicadorRiesgo getPrimero(){
		Query q=em.createQuery("Select b from RoTipoIndicadorRiesgo b Order by b.codigoTinri asc limit 1");
	
		return (RoTipoIndicadorRiesgo) q.getSingleResult();
	}
	
	public List<RoTipoIndicadorRiesgo> buscarRoTipoIndicadorRiesgoPorProcesoDeve(RoProceso roProceso) {
		Query q = em
				.createQuery("Select b from RoTipoIndicadorRiesgo b where b.roProceso LIKE :paramRoProceso Order by b.codigoTinri asc");
		q.setParameter("paramRoProceso", roProceso);
		return q.getResultList();
	}
	
	public List<RoTipoIndicadorRiesgo> buscarRoTipoIndicadorRiesgoPorNombreProceso(String nombreProceso) {
		
		Query q = em
				.createQuery("Select b from RoTipoIndicadorRiesgo b where b.roProceso.nombrePros LIKE :paramNombreProceso Order by b.codigoTinri asc");
		q.setParameter("paramNombreProceso", nombreProceso);
		return q.getResultList();
	}

}
