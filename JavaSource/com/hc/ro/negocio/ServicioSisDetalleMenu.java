package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisDetalleMenu;

@Stateless
public class ServicioSisDetalleMenu extends ServicioGenerico<SisDetalleMenu> {

	public ServicioSisDetalleMenu() {
		super(SisDetalleMenu.class, ServicioSisDetalleMenu.class);
		// TODO Auto-generated constructor stub
	}

	
	public SisDetalleMenu buscarDetalleMenuPorNombre(String nombreDetalleMenu) {
		Query q = em
				.createQuery("SELECT a FROM SisDetalleMenu a WHERE a.nombreDeme LIKE :paramNombreDetalleMenu");
		q.setParameter("paramNombreDetalleMenu", nombreDetalleMenu);

		return (SisDetalleMenu) q.getSingleResult();

	}
	
	@SuppressWarnings("unchecked")
	public List<SisDetalleMenu> buscarDetalleMenuPorMenu(int codigoMenu) {
		Query q = em
				.createQuery("SELECT a FROM SisDetalleMenu a WHERE a.sisMenu.codigoMenu LIKE :paramNombreMenu");
		q.setParameter("paramNombreMenu", codigoMenu);

		return q.getResultList();

	}

	public boolean existeDetalleMenuPorNombre(String nombreDetalleMenu) {
		Query q = em
				.createQuery("SELECT a FROM SisDetalleMenu a WHERE a.nombreDeme LIKE :paramNombreDetalleMenu");
		q.setParameter("paramNombreDetalleMenu", nombreDetalleMenu);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}


}