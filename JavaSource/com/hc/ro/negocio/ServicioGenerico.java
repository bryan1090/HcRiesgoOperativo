package com.hc.ro.negocio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

public abstract class ServicioGenerico<T> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> tipoBean;

	@SuppressWarnings("unused")
	private Class<?> tipoServicio;

	// CONSTRUCTOR
	public ServicioGenerico(Class<T> tipoBean, Class<?> tipoServicio) {
		this.tipoBean = tipoBean;
		this.tipoServicio = tipoServicio;
	}

	// METODOS
	public void insertar(T entidad) {
		em.persist(entidad);
	}

	public void actualizar(T entidad) {
		em.merge(entidad);
	}

	public void eliminar(T entidad) {
		em.remove(em.merge(entidad));
	}

	public void eliminar2(T entidad) {
		em.remove(entidad);
	}

	public T buscarPorId(long id) {
		return em.find(tipoBean, id);
	}

	public T buscarPorId(Short id) {
		return em.find(tipoBean, id);
	}

	public T buscarPorId(int id) {
		return em.find(tipoBean, id);
	}

	public void flushBase() {
		em.flush();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> buscarTodos() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(tipoBean));
		return em.createQuery(cq).getResultList();
	}

}
