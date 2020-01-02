package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisDetalleMenu;
import com.hc.ro.modelo.SisPerfil;
import com.hc.ro.modelo.SisPermiso;

@Stateless
public class ServicioSisPermiso extends ServicioGenerico<SisPermiso> {

	public ServicioSisPermiso() {
		super(SisPermiso.class, ServicioSisPermiso.class);
		// TODO Auto-generated constructor stub
	}

	public SisPermiso buscarPermisoPorNombre(String nombrePermiso) {
		Query q = em
				.createQuery("SELECT a FROM SisPermiso a WHERE a.nombrePerm LIKE :paramNombrePermiso");
		q.setParameter("paramNombrePermiso", nombrePermiso);

		return (SisPermiso) q.getSingleResult();

	}

	public boolean existePermiso(SisPermiso permiso) {
		Query q = em
				.createQuery("SELECT a FROM SisPermiso a WHERE a LIKE :paramPermiso");
		q.setParameter("paramPermiso", permiso);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean existePermisoPorNombreEx(String nombrePermiso,
			int codigoPermiso) {
		Query q = em
				.createQuery("SELECT a FROM SisPermiso a WHERE a.nombrePerm LIKE :paramNombrePermiso AND a.codigoPerm NOT LIKE :paramCodigoPermiso)");
		q.setParameter("paramNombrePermiso", nombrePermiso).setParameter(
				"paramCodigoPermiso", codigoPermiso);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public List<SisPermiso> buscarPermisoPorPerfil(String nombrePerfil) {
		Query q = em
				.createQuery("SELECT a FROM SisPermiso a WHERE a.sisPerfil.nombrePerf LIKE :paramNombrePerfil");
		q.setParameter("paramNombrePerfil", nombrePerfil);

		return q.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<SisPermiso> buscarPermisoPorPerfilPorTipo(String nombrePerfil,
			int tipoPerfil) {
		Query q = em
				.createQuery("SELECT a FROM SisPermiso a WHERE a.sisPerfil.nombrePerf LIKE :paramNombrePerfil AND a.tipoPerm LIKE :paramTipoPerfil");
		q.setParameter("paramNombrePerfil", nombrePerfil).setParameter(
				"paramTipoPerfil", tipoPerfil);

		return q.getResultList();

	}

	public boolean existeDenegacion(SisDetalleMenu deme, SisPerfil perfil) {
		Query q = em
				.createQuery("SELECT a FROM SisPermiso a WHERE a.sisPerfil LIKE :paramPerfil AND a.sisDetalleMenu LIKE :paramDeme)");
		q.setParameter("paramPerfil", perfil).setParameter("paramDeme", deme);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}
	/**
	 * El metodo recibe los paramétros nombrePagina y perfil
	 * Busca en la tabla SisPermiso si tiene restriccion no me va dejar entrar a ese nombre de página
	 * @param nombrePagina
	 * @param perfil
	 * @return
	 * %true% si encuentra restriccion
	 * %false% si no encuentra restriccion
	 */
	public boolean tienePermiso(String nombrePagina, SisPerfil perfil) {
		Query q = em
				.createQuery("SELECT a.sisPerfil.codigoPerf, a.sisDetalleMenu.accionDeme FROM SisPermiso a WHERE a.sisPerfil.codigoPerf LIKE :paramPerfil AND a.sisDetalleMenu.accionDeme LIKE :paramDeme)");
		q.setParameter("paramPerfil", perfil.getCodigoPerf()).setParameter("paramDeme",
				nombrePagina);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

}