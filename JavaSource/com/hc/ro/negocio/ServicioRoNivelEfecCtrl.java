package com.hc.ro.negocio;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.RoNivelEfecCtrl;

@Stateless
public class ServicioRoNivelEfecCtrl extends ServicioGenerico<RoNivelEfecCtrl> {

	public ServicioRoNivelEfecCtrl() {
		super(RoNivelEfecCtrl.class, ServicioRoNivelEfecCtrl.class);
		// TODO Auto-generated constructor stub
	}	
	

	public RoNivelEfecCtrl buscarNivelEfecCtrlPorNombre(String nombreNivelEfecCtrl) {
		Query q = em
				.createQuery("SELECT a FROM RoNivelEfecCtrl a WHERE a.nombreNect LIKE :paramNombreNivelEfecCtrl");
		q.setParameter("paramNombreNivelEfecCtrl", nombreNivelEfecCtrl);

		return (RoNivelEfecCtrl) q.getSingleResult();

	}

	public boolean existeNivelEfecCtrlPorNombre(String nombreNivelEfecCtrl) {
		Query q = em
				.createQuery("SELECT a FROM RoNivelEfecCtrl a WHERE a.nombreNect LIKE :paramNombreNivelEfecCtrl");
		q.setParameter("paramNombreNivelEfecCtrl", nombreNivelEfecCtrl);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeNivelEfecCtrlPorNumero(String numeroNivelEfecCtrl) {
		Query q = em
				.createQuery("SELECT a FROM RoNivelEfecCtrl a WHERE a.numeroNect LIKE :paramNombreNivelEfecCtrl");
		q.setParameter("paramNombreNivelEfecCtrl", numeroNivelEfecCtrl);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+q.toString());
		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeNivelEfecCtrlPorNombreEx(String nombreNivelEfecCtrl,
			int codigoNivelEfecCtrl) {
		Query q = em
				.createQuery("SELECT a FROM RoNivelEfecCtrl a WHERE a.nombreNect LIKE :paramNombreNivelEfecCtrl AND a.codigoNect NOT LIKE :paramCodigoNivelEfecCtrl)");
		q.setParameter("paramNombreNivelEfecCtrl", nombreNivelEfecCtrl).setParameter(
				"paramCodigoNivelEfecCtrl", codigoNivelEfecCtrl);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

	public boolean existeNivelEfecCtrlPorNumeroEx(String numeroNivelEfecCtrl, int codigoNivelEfecCtrl) {
		Query q = em
				.createQuery("SELECT a FROM RoNivelEfecCtrl a WHERE a.numeroNect LIKE :paramNombreNivelEfecCtrl AND a.codigoNect NOT LIKE :paramCodigoNivelEfecCtrl)");
		q.setParameter("paramNombreNivelEfecCtrl", numeroNivelEfecCtrl).setParameter(
				"paramCodigoNivelEfecCtrl", codigoNivelEfecCtrl);

		if(q.getResultList().size()==0){
			return true;
		}else
		{
			return false;
		}

	}

}
