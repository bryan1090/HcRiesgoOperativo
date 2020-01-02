package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoEventoIndicador;

@Stateless
public class ServicioRoEventoIndicador extends ServicioGenerico<RoEventoIndicador>{

	public ServicioRoEventoIndicador() {
		super(RoEventoIndicador.class, ServicioRoEventoIndicador.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoEventoIndicador> buscarTodosPorCodigoDetalleEvento(int codigo)
	{
		Query q=em.createQuery("Select a from RoEventoIndicador a where a.roDetalleEvento.codigoDeve LIKE :paramCodigo order by a.codigoEvin asc");
		q.setParameter("paramCodigo", codigo);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoEventoIndicador> buscarPorCodigoDetalleEventoFiltroFecha(int codigo, Date fechaInicioInri, Date fechaFinInri)
	{
		Query q=em.createQuery("Select a from RoEventoIndicador a where a.roDetalleEvento.codigoDeve LIKE :paramCodigo AND a.fechaEvin BETWEEN :fechaInicioInri AND :fechaFinInri order by a.codigoEvin asc");
		q.setParameter("paramCodigo", codigo).setParameter("fechaInicioInri", fechaInicioInri).setParameter("fechaFinInri", fechaFinInri);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoEventoIndicador> buscarPorCodigoDetalleEventoFiltroFecha(int codigo, Date fechaInicioInri, Date fechaFinInri, List<String> tipoIndicadoresFiltro)
	{
		Query q=em.createQuery("Select a from RoEventoIndicador a where a.roDetalleEvento.codigoDeve LIKE :paramCodigo AND a.fechaEvin BETWEEN :fechaInicioInri AND :fechaFinInri order by a.codigoEvin asc");
		q.setParameter("paramCodigo", codigo).setParameter("fechaInicioInri", fechaInicioInri).setParameter("fechaFinInri", fechaFinInri);
		
		List<RoEventoIndicador> eventosIndicadoresAux=new ArrayList<RoEventoIndicador>();
		List<RoEventoIndicador> eventosIndicadores=new ArrayList<RoEventoIndicador>();
		eventosIndicadoresAux=q.getResultList();
		for(RoEventoIndicador eventInri: eventosIndicadoresAux)
		{
			if(tipoIndicadoresFiltro.contains(Integer.toString(eventInri.getRoTipoIndicadorRiesgo().getcodigoTinri())))
			{
				eventosIndicadores.add(eventInri);
			}
		}
		return eventosIndicadores;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RoEventoIndicador> buscarPorDeve(int codigoDeve)
	{
		Query q=em.createQuery("Select a from RoEventoIndicador a where a.roDetalleEvento LIKE :paramCodigoDeve");

		
		q.setParameter("paramCodigoDeve", codigoDeve);
		return q.getResultList();
	}
}
