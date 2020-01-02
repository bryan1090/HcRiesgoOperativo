package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoRespAgencia;
import com.hc.ro.modelo.RoRespPro;
import com.hc.ro.modelo.RoResponsable;
import com.hc.ro.modelo.SisUsuario;

@Stateless
public class ServicioRoRespAgencia extends ServicioGenerico<RoRespAgencia> {

	public ServicioRoRespAgencia() {
		super(RoRespAgencia.class, ServicioRoRespAgencia.class);
		// TODO Auto-generated constructor stub
	}

	public RoRespAgencia buscarRespAgenciaPorNombre(String nombreRespAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoRespAgencia a WHERE a.nombreReag LIKE :paramNombreRespAgencia");
		q.setParameter("paramNombreRespAgencia", nombreRespAgencia);

		return (RoRespAgencia) q.getSingleResult();

	}

	public boolean existeRespAgenciaPorNombre(String nombreRespAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoRespAgencia a WHERE a.nombreReag LIKE :paramNombreRespAgencia");
		q.setParameter("paramNombreRespAgencia", nombreRespAgencia);

		/**/System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeRespAgenciaPorNumero(String numeroRespAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoRespAgencia a WHERE a.numeroReag LIKE :paramNumeroRespAgencia");
		q.setParameter("paramNumeroRespAgencia", numeroRespAgencia);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeRespAgenciaPorNombreEx(String nombreRespAgencia,
			int codigoRespAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoRespAgencia a WHERE a.nombreReag LIKE :paramNombreRespAgencia AND a.codigoReag NOT LIKE :paramCodigoRespAgencia)");
		q.setParameter("paramNombreRespAgencia", nombreRespAgencia)
				.setParameter("paramCodigoRespAgencia", codigoRespAgencia);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean existeRespAgenciaPorNumeroEx(String numeroRespAgencia,
			int codigoRespAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoRespAgencia a WHERE a.numeroReag LIKE :paramNumeroRespAgencia AND a.codigoReag NOT LIKE :paramCodigoRespAgencia)");
		q.setParameter("paramNumeroRespAgencia", numeroRespAgencia)
				.setParameter("paramCodigoRespAgencia", codigoRespAgencia);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<RoRespAgencia> buscarRespAgenciasPorAgencias(
			RoAgencia nombreAgencia) {
		System.out
				.println("ENTRO A SERVICIO RESP AGENCIAS <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
						+ nombreAgencia);
		Query q = em
				.createQuery("SELECT b FROM RoRespAgencia b WHERE b.roAgencia LIKE :paramNombreAgcia");
		q.setParameter("paramNombreAgcia", nombreAgencia);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RoRespAgencia> buscarRespAgenciasPorUsuario(SisUsuario usuario) {
		Query q = em
				.createQuery("SELECT b FROM RoRespAgencia b WHERE b.roResponsable.sisUsuario LIKE :paramUsuario ORDER BY b.roAgencia.nombreAgia");
		q.setParameter("paramUsuario", usuario);

		return q.getResultList();
	}

	public List<RoRespAgencia> buscarRespAgenciasPorUsuarioEvaluacion(
			SisUsuario usuario) {
		ArrayList<RoRespAgencia> respAgs = new ArrayList<RoRespAgencia>();
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT b.codigoReag, b.roAgencia.codigoAgia, b.roAgencia.nombreAgia, b.roAgencia.ancestroAgia FROM RoRespAgencia b WHERE b.roResponsable.sisUsuario LIKE :paramUsuario ORDER BY b.roAgencia.nombreAgia",
						Object[].class);
		q.setParameter("paramUsuario", usuario);
		for (Object[] result : q.getResultList()) {
			RoRespAgencia respAg = new RoRespAgencia((Integer) result[0],
					new RoAgencia((Integer) result[1], (String) result[2],
							(String) result[3]));
			respAgs.add(respAg);
		}
		return respAgs;
	}
	
	
	public List<RoRespAgencia> buscarRespAgenciaPorResponsable(RoResponsable responsable) {
		Query q = em
				.createQuery("SELECT a FROM RoRespAgencia a WHERE a.roResponsable LIKE :paramResponsable");
		q.setParameter("paramResponsable", responsable);

		return q.getResultList();
	}
	
	
	
	
	public List<Integer> buscarCodigosAgenciaPorResponsable(RoResponsable responsable) {
		Query q = em
				.createQuery("SELECT a.roAgencia.codigoAgia FROM RoRespAgencia a WHERE a.roResponsable LIKE :paramResponsable");
		q.setParameter("paramResponsable", responsable);

		return q.getResultList();
	}
	

}
