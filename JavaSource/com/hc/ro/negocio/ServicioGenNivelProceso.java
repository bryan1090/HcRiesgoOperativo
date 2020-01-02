package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.GenNivelProceso;

@Stateless
public class ServicioGenNivelProceso extends ServicioGenerico<GenNivelProceso> {

	public ServicioGenNivelProceso() {
		super(GenNivelProceso.class, ServicioGenNivelProceso.class);
		// TODO Auto-generated constructor stub
	}

	public GenNivelProceso buscarNivelProcesoPorNombre(String nombreProceso) {
		Query q = em
				.createQuery("SELECT a FROM GenNivelProceso a WHERE a.nombreGnip LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);

		return (GenNivelProceso) q.getSingleResult();

	}

	public boolean existeNivelProcesoPorNombre(String nombreProceso) {
		Query q = em
				.createQuery("SELECT a FROM GenNivelProceso a WHERE a.nombreGnip LIKE :paramNombreProceso");
		q.setParameter("paramNombreProceso", nombreProceso);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean existeNivelProcesoPorNombreEx(String nombreProceso,
			int codigoProceso) {
		Query q = em
				.createQuery("SELECT a FROM GenNivelProceso a WHERE a.nombreGnip LIKE :paramNombreProceso AND a.codigoGnip NOT LIKE :paramCodigoProceso)");
		q.setParameter("paramNombreProceso", nombreProceso).setParameter(
				"paramCodigoProceso", codigoProceso);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
