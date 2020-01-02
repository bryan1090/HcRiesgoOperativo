package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisMenu;

@Stateless
public class ServicioSisMenu extends ServicioGenerico<SisMenu> {

	public ServicioSisMenu() {
		super(SisMenu.class, ServicioSisMenu.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<SisMenu> buscarMenuPorPadre(String codigoPadre) {
		Query q = em
				.createQuery("SELECT a FROM SisMenu a WHERE a.ancestroMenu LIKE :paramCodigoPadre");
		q.setParameter("paramCodigoPadre", codigoPadre);

		return q.getResultList();

	}
	
	

	public SisMenu buscarMenuPorNombre(String nombreMenu) {
		Query q = em
				.createQuery("SELECT a FROM SisMenu a WHERE a.nombreMenu LIKE :paramNombreMenu");
		q.setParameter("paramNombreMenu", nombreMenu);

		return (SisMenu) q.getSingleResult();

	}

	public boolean existeMenuPorNombre(String nombreMenu) {
		Query q = em
				.createQuery("SELECT a FROM SisMenu a WHERE a.nombreMenu LIKE :paramNombreMenu");
		q.setParameter("paramNombreMenu", nombreMenu);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeMenuPorNombreEx(String nombreMenu,
			int codigoMenu) {
		Query q = em
				.createQuery("SELECT a FROM SisMenu a WHERE a.nombreMenu LIKE :paramNombreMenu AND a.codigoMenu NOT LIKE :paramCodigoMenu)");
		q.setParameter("paramNombreMenu", nombreMenu).setParameter(
				"paramCodigoMenu", codigoMenu);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
