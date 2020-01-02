package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoDepartamento;

@Stateless
public class ServicioRoDepartamento extends ServicioGenerico<RoDepartamento> {

	public ServicioRoDepartamento() {
		super(RoDepartamento.class, ServicioRoDepartamento.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoDepartamento> buscarDepartamentoPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM RoDepartamento a WHERE a.ancestroDept LIKE :paramCodigoPadre ORDER BY a.numeroDept");
		q.setParameter("paramCodigoPadre", codigoPadre);

		return q.getResultList();

	}

	public List<RoDepartamento> buscarDepartamentoPorPadre_nombreDept(
			String codigoPadre) {
		ArrayList<RoDepartamento> departamentos = new ArrayList<RoDepartamento>();
		TypedQuery<String> q = em
				.createQuery(
						"SELECT a.nombreDept FROM RoDepartamento a WHERE a.ancestroDept LIKE :paramCodigoPadre",
						String.class);
		q.setParameter("paramCodigoPadre", codigoPadre);
		for (String result : q.getResultList()) {
			RoDepartamento departamento = new RoDepartamento(result);
			departamentos.add(departamento);
		}
		return departamentos;
	}

	public RoDepartamento buscarDepartamentoPorNombre(String nombreDepartamento) {
		Query q = em
				.createQuery("SELECT a FROM RoDepartamento a WHERE a.nombreDept LIKE :paramNombreDepartamento");
		q.setParameter("paramNombreDepartamento", nombreDepartamento);

		return (RoDepartamento) q.getSingleResult();

	}

	public boolean existeDepartamentoPorNombre(String nombreDepartamento) {
		Query q = em
				.createQuery("SELECT a FROM RoDepartamento a WHERE a.nombreDept LIKE :paramNombreDepartamento");
		q.setParameter("paramNombreDepartamento", nombreDepartamento);

		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeDepartamentoPorNumero(String numeroDepartamento) {
		Query q = em
				.createQuery("SELECT a FROM RoDepartamento a WHERE a.numeroDept LIKE :paramNumeroDepartamento");
		q.setParameter("paramNumeroDepartamento", numeroDepartamento);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeDepartamentoPorNombreEx(String nombreDepartamento,
			int l) {
		short codigoSDepartamento = (short) l;
		Query q = em
				.createQuery("SELECT a FROM RoDepartamento a WHERE a.nombreDept LIKE :paramNombreDepartamento AND a.codigoDept NOT LIKE :paramCodigoDepartamento)");
		q.setParameter("paramNombreDepartamento", nombreDepartamento)
				.setParameter("paramCodigoDepartamento", codigoSDepartamento);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeDepartamentoPorNumeroEx(String numeroDepartamento,
			int i) {
		int codigoSDepartamento = i;
		Query q = em
				.createQuery("SELECT a FROM RoDepartamento a WHERE a.numeroDept LIKE :paramNumeroDepartamento AND a.codigoDept NOT LIKE :paramCodigoDepartamento)");
		q.setParameter("paramNumeroDepartamento", numeroDepartamento)
				.setParameter("paramCodigoDepartamento", codigoSDepartamento);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}
	
	public List<Integer> buscarCodigosDepartamentoTodos() {
		Query q = em
				.createQuery("SELECT a.codigoDept FROM RoDepartamento a");


		return q.getResultList();
	}
	
}
