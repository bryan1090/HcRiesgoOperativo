package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoArchivoDetalleAccion;

@Stateless
public class ServicioRoArchivoDetalleAccion extends
		ServicioGenerico<RoArchivoDetalleAccion> {

	public ServicioRoArchivoDetalleAccion() {
		super(RoArchivoDetalleAccion.class,
				ServicioRoArchivoDetalleAccion.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoArchivoDetalleAccion> buscarPorIdDetalleAccion(
			int codigoDetalleAccion) {
		Query q = em
				.createQuery("SELECT a FROM RoArchivoDetalleAccion a WHERE a.roDetalleAccion.codigoDeac LIKE :paramDeve");
		q.setParameter("paramDeve", codigoDetalleAccion);

		return q.getResultList();

	}
}