package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisPerfil;

@Stateless
public class ServicioSisPerfil extends ServicioGenerico<SisPerfil> {

	public ServicioSisPerfil() {
		super(SisPerfil.class, ServicioSisPerfil.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<SisPerfil> buscarPerfilPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM SisPerfil a WHERE a.ancestroPerf LIKE :paramCodigoPadre ORDER BY a.numeroPerf");
		q.setParameter("paramCodigoPadre", codigoPadre);

		return q.getResultList();

	}
	
	public SisPerfil buscarPerfilPorNombre(String nombrePerfil) {
		Query q = em
				.createQuery("SELECT a FROM SisPerfil a WHERE a.nombrePerf LIKE :paramNombrePerfil");
		q.setParameter("paramNombrePerfil", nombrePerfil);

		return (SisPerfil) q.getSingleResult();

	}

	public boolean existePerfilPorNombre(String nombrePerfil) {
		Query q = em
				.createQuery("SELECT a FROM SisPerfil a WHERE a.nombrePerf LIKE :paramNombrePerfil");
		q.setParameter("paramNombrePerfil", nombrePerfil);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}
	
	public boolean tieneUsuarios(SisPerfil perfil) {
		Query q = em
				.createQuery("SELECT a FROM SisUsuario a WHERE a.sisPerfil LIKE :perfil");
		q.setMaxResults(1);
		q.setParameter("perfil", perfil);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return false;
		}else
		{
			return true;
		}

	}

	public boolean existePerfilPorNombreEx(String nombrePerfil,
			int codigoPerfil) {
		Query q = em
				.createQuery("SELECT a FROM SisPerfil a WHERE a.nombrePerf LIKE :paramNombrePerfil AND a.codigoPerf NOT LIKE :paramCodigoPerfil)");
		q.setParameter("paramNombrePerfil", nombrePerfil).setParameter(
				"paramCodigoPerfil", codigoPerfil);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existePerfilPorNumeroEx(String numeSisPerfil, int codigoPerfil) {
		Query q = em
				.createQuery("SELECT a FROM SisPerfil a WHERE a.numeroPerf LIKE :paramNombrePerfil AND a.codigoPerf NOT LIKE :paramCodigoPerfil)");
		q.setParameter("paramNombrePerfil", numeSisPerfil).setParameter(
				"paramCodigoPerfil", codigoPerfil);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}
	}

}
