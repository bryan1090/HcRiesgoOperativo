package com.hc.ro.negocio;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoAccion;
import com.hc.ro.modelo.RoDetalleEvento;

@Stateless
public class ServicioRoAccion extends ServicioGenerico<RoAccion> {
	
	public ServicioRoAccion() {
		super(RoAccion.class, ServicioRoAccion.class);
		// TODO Auto-generated constructor stub
	}

	public boolean existeAccionPorNombre(String nombreAccion,
			RoDetalleEvento roDetalleEvento) {
		System.out.println(roDetalleEvento.getCodigoDeve());
		Query q = em
				.createQuery("SELECT a FROM RoAccion a WHERE a.nombreAcci LIKE :paramNombreAccion AND a.roDetalleEvento.codigoDeve LIKE :paramDetalleEvento");
		q.setParameter("paramNombreAccion", nombreAccion).setParameter(
				"paramDetalleEvento", roDetalleEvento.getCodigoDeve());

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean existeAccionPorNombreEx(String nombreAccion,
			int codigoAccion, RoDetalleEvento roDetalleEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoAccion a WHERE a.nombreAcci LIKE :paramNombreAccion AND a.codigoAcci NOT LIKE :paramCodigoAccion AND a.roDetalleEvento.codigoDeve LIKE :paramDetalleEvento");
		q.setParameter("paramNombreAccion", nombreAccion)
				.setParameter("paramCodigoAccion", codigoAccion)
				.setParameter("paramDetalleEvento",
						roDetalleEvento.getCodigoDeve());

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	@SuppressWarnings("unchecked")
	public List<RoAccion> buscarRoAccionPorEventoFecha(int codigoEvento,
			Date fechaInicio, Date fechaFin) {
		Query q = em
				.createQuery("SELECT a FROM RoAccion a WHERE a.roDetalleEvento.codigoDeve LIKE :paramDeve AND a.fechaInicioAcci BETWEEN :fechaInicio AND :fechaFin");
		q.setParameter("paramDeve", codigoEvento)
				.setParameter("fechaInicio", fechaInicio)
				.setParameter("fechaFin", fechaFin);

		return q.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public int buscarRoAccionPorCodigoEvento(int codigoEvento) {
		Query q = em
				.createQuery("SELECT COUNT(a.codigoAcci) FROM RoAccion a WHERE a.roDetalleEvento.codigoDeve LIKE :paramDeve");
		q.setParameter("paramDeve", codigoEvento);
		
		

		return (int)(long)q.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	public List<RoAccion> buscarRoAccionPorEventoResponsableFecha(
			int codigoEvento, int codigoUsuario, Date fechaInicio, Date fechaFin) {
		Query q = this.em
				.createQuery("SELECT a FROM RoAccion a WHERE a.roDetalleEvento.codigoDeve LIKE :paramDeve AND a.roResponsable.sisUsuario.codigoUsua = :idUsua AND a.fechaInicioAcci BETWEEN :fechaInicio AND :fechaFin");
		q.setParameter("paramDeve", Integer.valueOf(codigoEvento))
				.setParameter("idUsua", Integer.valueOf(codigoUsuario))
				.setParameter("fechaInicio", fechaInicio)
				.setParameter("fechaFin", fechaFin);
		return q.getResultList();
	}
	
	public Long numeroTotalRegistros(int codigoUsuario, Date fechaActual){		
		Query q = em
				.createQuery("SELECT COUNT(a) FROM RoAccion a WHERE a.roResponsable.sisUsuario.codigoUsua = :idUsua AND a.fechaFinAcci < :fechaActual");
		q.setParameter("idUsua", Integer.valueOf(codigoUsuario))
			.setParameter("fechaActual", fechaActual);		
		return (Long) q.getSingleResult();
	}	
	
	@SuppressWarnings("unchecked")
	public List<RoAccion> totalRegistrosPorResponsable(int codigoUsuario, Date fechaActual){
		Query q = em
				.createQuery("SELECT a FROM RoAccion a WHERE a.roResponsable.sisUsuario.codigoUsua = :idUsua AND a.fechaFinAcci < :fechaActual");
		q.setParameter("idUsua", Integer.valueOf(codigoUsuario))
			.setParameter("fechaActual", fechaActual);	
		return q.getResultList();
	}
}
