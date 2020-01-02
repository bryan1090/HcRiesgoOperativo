package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoRespDepa;
import com.hc.ro.modelo.RoRespPro;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.SisUsuario;

@Stateless
public class ServicioRoRespDepa extends ServicioGenerico<RoRespDepa> {

	public ServicioRoRespDepa() {
		super(RoRespDepa.class, ServicioRoRespDepa.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoRespDepa> buscarRespDepaPorDepa(String nombreDepa) {
		Query q = em
				.createQuery("SELECT a FROM RoRespDepa a WHERE a.roDepartamento.nombreDept LIKE :paramNombreDepa");
		q.setParameter("paramNombreDepa", nombreDepa);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RoRespDepa> buscarRespDepaPorUsuario(SisUsuario usuario) {
		Query q = em
				.createQuery("SELECT a FROM RoRespDepa a WHERE a.roResponsable.sisUsuario LIKE :paramUsuario ORDER BY a.roDepartamento.nombreDept");
		q.setParameter("paramUsuario", usuario);

		return q.getResultList();
	}

	public List<RoRespDepa> buscarRespDepaPorUsuarioEvaluacion(
			SisUsuario usuario) {
		ArrayList<RoRespDepa> respDeps = new ArrayList<RoRespDepa>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.CODIGO_resp_depa, b.roDepartamento.codigoDept, b.roDepartamento.nombreDept, b.roDepartamento.ancestroDept FROM RoRespDepa b WHERE b.roResponsable.sisUsuario LIKE :paramUsuario ORDER BY b.roDepartamento.nombreDept",
						Object[].class);
		q.setParameter("paramUsuario", usuario);
		for (Object[] result : q.getResultList()) {
			RoRespDepa respDep = new RoRespDepa((Integer) result[0],
					new RoDepartamento((Integer) result[1], (String) result[2],
							(String) result[3]));
			respDeps.add(respDep);
		}

		return respDeps;
	}
	
	
	public List<RoRespDepa> buscarRespDepartamentoPorResponsable(RoResponsable responsable) {
		Query q = em
				.createQuery("SELECT a FROM RoRespDepa a WHERE a.roResponsable LIKE :paramResponsable");
		q.setParameter("paramResponsable", responsable);

		return q.getResultList();
	}
	
	
	
	public List<Integer> buscarCodigosDepartamentoPorResponsable(RoResponsable responsable) {
		Query q = em
				.createQuery("SELECT a.roDepartamento.codigoDept FROM RoRespDepa a WHERE a.roResponsable LIKE :paramResponsable");
		q.setParameter("paramResponsable", responsable);

		return q.getResultList();
	}
	
	

}
