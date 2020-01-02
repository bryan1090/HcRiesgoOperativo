package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoTipoAgencia;

@Stateless
public class ServicioRoTipoAgencia extends ServicioGenerico<RoTipoAgencia> {

	public ServicioRoTipoAgencia() {
		super(RoTipoAgencia.class, ServicioRoTipoAgencia.class);
		// TODO Auto-generated constructor stub
	}	
	

	// public RoTipoAgencia buscarTipoAgenciaPorNombre(String nombreTipoAgencia)
	// {
	// Query q = em
	// .createQuery("SELECT a FROM RoTipoAgencia a WHERE a.nombreTiag LIKE :paramNombreTipoAgencia");
	// q.setParameter("paramNombreTipoAgencia", nombreTipoAgencia);
	//
	// return (RoTipoAgencia) q.getSingleResult();
	//
	// }

	public boolean existeTipoAgenciaPorNombre(String nombreTipoAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAgencia a WHERE a.nombreTiag LIKE :paramNombreTipoAgencia");
		q.setParameter("paramNombreTipoAgencia", nombreTipoAgencia);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeTipoAgenciaPorNumero(String numeroTipoAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAgencia a WHERE a.numeroTiag LIKE :paramNombreTipoAgencia");
		q.setParameter("paramNombreTipoAgencia", numeroTipoAgencia);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoAgenciaPorNombreEx(String nombreTipoAgencia,
			int codigoTipoAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAgencia a WHERE a.nombreTiag LIKE :paramNombreTipoAgencia AND a.codigoTiag NOT LIKE :paramCodigoTipoAgencia)");
		q.setParameter("paramNombreTipoAgencia", nombreTipoAgencia).setParameter(
				"paramCodigoTipoAgencia", codigoTipoAgencia);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeTipoAgenciaPorNumeroEx(String numeroTipoAgencia, int codigoTipoAgencia) {
		Query q = em
				.createQuery("SELECT a FROM RoTipoAgencia a WHERE a.numeroTiag LIKE :paramNombreTipoAgencia AND a.codigoTiag NOT LIKE :paramCodigoTipoAgencia)");
		q.setParameter("paramNombreTipoAgencia", numeroTipoAgencia).setParameter(
				"paramCodigoTipoAgencia", codigoTipoAgencia);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
