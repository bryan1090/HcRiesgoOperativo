package com.hc.ro.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoConstanteControl;


@Stateless
public class ServicioRoConstanteControl extends ServicioGenerico<RoConstanteControl>{

	public ServicioRoConstanteControl() {
		super(RoConstanteControl.class, ServicioRoConstanteControl.class);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<RoConstanteControl> buscarTodos() {
		Query q = em
				.createQuery("Select b from RoConstanteControl b Order by b.codigoCte asc");
	
		return q.getResultList();
	}

}
