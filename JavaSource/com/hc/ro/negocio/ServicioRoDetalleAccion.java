package com.hc.ro.negocio;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoDetalleAccion;

@Stateless
public class ServicioRoDetalleAccion extends ServicioGenerico<RoDetalleAccion> {
	public ServicioRoDetalleAccion() {
		super(RoDetalleAccion.class, ServicioRoDetalleAccion.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleAccion> buscarRoDetalleAccionPorAccion(int codigoAccion) {
		Query q = em
				.createQuery("SELECT a FROM RoDetalleAccion a WHERE a.roAccion.codigoAcci LIKE :paramDeve");
		q.setParameter("paramDeve", codigoAccion);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleAccion> buscarRoDetalleAccionPorSupervisorFecha(
			int codigoResponsableSupervisor, Date fechaInicio, Date fechaFin) {
		Query q = this.em
				.createQuery("SELECT a FROM RoDetalleAccion a WHERE a.roResponsableSupervisor.sisUsuario.codigoUsua LIKE :paramDeve AND a.fechaInicioDeac BETWEEN :fechaInicio AND :fechaFin");
		q.setParameter("paramDeve",
				Integer.valueOf(codigoResponsableSupervisor))
				.setParameter("fechaInicio", fechaInicio)
				.setParameter("fechaFin", fechaFin);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleAccion> buscarRoDetalleAccionPorResponsableFecha(
			int codigoResponsable, Date fechaInicio, Date fechaFin) {
		Query q = this.em
				.createQuery("SELECT a FROM RoDetalleAccion a WHERE a.roResponsable.sisUsuario.codigoUsua LIKE :paramDeve AND a.fechaInicioDeac BETWEEN :fechaInicio AND :fechaFin");
		q.setParameter("paramDeve", Integer.valueOf(codigoResponsable))
				.setParameter("fechaInicio", fechaInicio)
				.setParameter("fechaFin", fechaFin);

		return q.getResultList();
	}
	
	public Long numeroTotalRegistrosRoDetalle(int codigoUsuario, Date fechaActual){		
		Query q = em
				.createQuery("SELECT COUNT(a) FROM RoDetalleAccion a WHERE a.roResponsable.sisUsuario.codigoUsua = :idUsua AND a.fechaFinDeac < :fechaActual");
		q.setParameter("idUsua", Integer.valueOf(codigoUsuario))
			.setParameter("fechaActual", fechaActual);		
		return (Long) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoDetalleAccion> listaTotalRegistrosRoDetalle(int codigoUsuario, Date fechaActual){		
		Query q = em
				.createQuery("SELECT a FROM RoDetalleAccion a WHERE a.roResponsable.sisUsuario.codigoUsua = :idUsua AND a.fechaFinDeac < :fechaActual");
		q.setParameter("idUsua", Integer.valueOf(codigoUsuario))
			.setParameter("fechaActual", fechaActual);		
		return q.getResultList();
	}
}
