package com.hc.ro.negocio;


import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisParametro;

@Stateless
public class ServicioSisParametro extends ServicioGenerico<SisParametro> {

	public ServicioSisParametro() {
		super(SisParametro.class, ServicioSisParametro.class);
		// TODO Auto-generated constructor stub
	}

	public void verificarParametrosSistema() {
		if (existePorNombre("ipFTP")) {
			SisParametro paramAux = new SisParametro();
			paramAux.setNombrePara("ipFTP");
			paramAux.setValorPara("127.0.0.1");
			insertar(paramAux);

		}
		if (existePorNombre("userFTP")) {
			SisParametro paramAux = new SisParametro();
			paramAux.setNombrePara("userFTP");
			paramAux.setValorPara("anonymous");
			insertar(paramAux);

		}
		if (existePorNombre("passFTP")) {
			SisParametro paramAux = new SisParametro();
			paramAux.setNombrePara("passFTP");
			paramAux.setValorPara("root");
			insertar(paramAux);

		}
	}

	public boolean existePorNombre(String nombreParametro) {
		Query q = em
				.createQuery("SELECT a FROM SisParametro a WHERE a.nombrePara LIKE :paramNombre");
		q.setParameter("paramNombre", nombreParametro);
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public SisParametro buscarPorNombre(String nombreParametro) {
		Query q = em
				.createQuery("SELECT a FROM SisParametro a WHERE a.nombrePara LIKE :paramNombre");
		q.setParameter("paramNombre", nombreParametro);
		return (SisParametro) q.getSingleResult();
	}

	public boolean existePorNombreEx(String nombreParametro, int codigoParametro) {
		Query q = em
				.createQuery("SELECT a FROM SisParametro a WHERE a.nombrePara LIKE :paramNombre AND a.codigoPara NOT LIKE :paramCodigo");
		q.setParameter("paramNombre", nombreParametro).setParameter(
				"paramCodigo", codigoParametro);
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
