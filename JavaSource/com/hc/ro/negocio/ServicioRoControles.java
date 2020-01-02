package com.hc.ro.negocio;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoControles;
import com.hc.ro.modelo.RoDetCarctEvent;
import com.hc.ro.modelo.RoDetalleEvento;

@Stateless
public class ServicioRoControles extends ServicioGenerico<RoControles> {

	public ServicioRoControles() {
		super(RoControles.class, ServicioRoControles.class);
		// TODO Auto-generated constructor stub
	}
	
	public Integer buscarCalculoProb(int idCalculo){
		Query q = this.em
				.createQuery("SELECT a.calculoProb FROM RoProbabilidadEvento a WHERE a.CODIGO_prob LIKE :paramCalc");
		q.setParameter("paramCalc", idCalculo);
		return (Integer) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoControles> buscarControlesPorEvento (RoDetalleEvento attrb){
		Query q = em.
				createQuery("SELECT a FROM RoControles a WHERE a.roDetalleEvento LIKE :paramAttrb");
		q.setParameter("paramAttrb", attrb);
		return q.getResultList();
	}
	
	public Double promedioValorControles(RoDetalleEvento attrb){
		Query q = em.
				createQuery("SELECT AVG(a.valorControl) FROM RoControles a WHERE a.roDetalleEvento LIKE :paramAttrb");	
		q.setParameter("paramAttrb", attrb);
		if(q.getSingleResult()!=null)
		{
			return (Double) q.getSingleResult();	
		}
		else{
			return 0.0;
		}
	}
	
	
	
	public Double promedioRiesgoResidualControles(RoDetalleEvento attrb){
		Query q = em.
				createQuery("SELECT AVG(a.riesgoResi) FROM RoControles a WHERE a.roDetalleEvento LIKE :paramAttrb");	
		q.setParameter("paramAttrb", attrb);
		if(q.getSingleResult()!=null)
		{
			return (Double) q.getSingleResult();	
		}
		else{
			return 0.0;
		}
	}
	
	public Double promedioRiesgoInherenteControles(RoDetalleEvento attrb){
		Query q = em.
				createQuery("SELECT AVG(a.riesgoInhe) FROM RoControles a WHERE a.roDetalleEvento LIKE :paramAttrb");	
		q.setParameter("paramAttrb", attrb);
		if(q.getSingleResult()!=null)
		{
			return (Double) q.getSingleResult();	
		}
		else{
			return 0.0;
		}
	}
	
	public void actualizarCampo(BigDecimal campo, RoDetalleEvento attrb){
		Query q = em.
				createQuery("UPDATE RoControles a SET a.promedioControl = :paramAttrb WHERE a.roDetalleEvento LIKE :param");
		q.setParameter("paramAttrb", campo);
		q.setParameter("param", attrb);
		q.executeUpdate();		
	}
	
	public BigDecimal traerPrimerCampoPromedio (RoDetalleEvento attrb){
		Query q = em.
				createQuery("SELECT TOP 1 a.promedioControl FROM RoControles a WHERE a.roDetalleEvento LIKE :paramAttrb");
		q.setParameter("paramAttrb", attrb);
		return (BigDecimal) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<RoControles> buscarPorDetalleEvento(RoDetalleEvento detalleEvento) {
		Query q = em
				.createQuery("SELECT a FROM RoControles a WHERE a.roDetalleEvento LIKE :paramDetalleEvento");
		q.setParameter("paramDetalleEvento", detalleEvento);
		return q.getResultList();
	}
	
}
