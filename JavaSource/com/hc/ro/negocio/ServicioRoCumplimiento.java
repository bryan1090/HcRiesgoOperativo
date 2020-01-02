package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoCumplimiento;

@Stateless
public class ServicioRoCumplimiento extends ServicioGenerico<RoCumplimiento> {

	public ServicioRoCumplimiento() {
		super(RoCumplimiento.class, ServicioRoCumplimiento.class);
		// TODO Auto-generated constructor stub
	}

	public RoCumplimiento buscarCumplimientoPorNombre(String nombreCumplimiento) {
		Query q = em
				.createQuery("SELECT a FROM RoCumplimiento a WHERE a.nombreCump LIKE :paramNombreCumplimiento");
		q.setParameter("paramNombreCumplimiento", nombreCumplimiento);

		return (RoCumplimiento) q.getSingleResult();

	}

	public boolean existeCumplimientoPorNombre(String nombreCumplimiento) {
		Query q = em
				.createQuery("SELECT a FROM RoCumplimiento a WHERE a.nombreCump LIKE :paramNombreCumplimiento");
		q.setParameter("paramNombreCumplimiento", nombreCumplimiento);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeCumplimientoPorNombreEx(String nombreCumplimiento,
			int codigoCumplimiento) {
		Query q = em
				.createQuery("SELECT a FROM RoCumplimiento a WHERE a.nombreCump LIKE :paramNombreCumplimiento AND a.codigoCump NOT LIKE :paramCodigoCumplimiento)");
		q.setParameter("paramNombreCumplimiento", nombreCumplimiento)
				.setParameter("paramCodigoCumplimiento", codigoCumplimiento);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

}
