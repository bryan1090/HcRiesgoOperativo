/*package com.hc.ro.negocio;


import javax.ejb.Stateless;
import javax.persistence.Query;

import com.hc.ro.modelo.SisUsuario;

@Stateless
public class ServicioSisUsuario extends ServicioGenerico<SisUsuario>{


	public ServicioSisUsuario() {
		super(SisUsuario.class, ServicioSisUsuario.class);
		// TODO Auto-generated constructor stub
	}

	public SisUsuario buscarUsuarioNombreUsua(String nombreUsua) throws Exception {
		Query q = em
				.createQuery("SELECT u FROM SisUsuario u WHERE u.nombreUsua LIKE :paramNombreUsua");
		q.setParameter("paramNombreUsua", nombreUsua);
		if (q.getSingleResult() == null) {
			throw new Exception();
		} else {
			return (SisUsuario) q.getSingleResult();
		}
	}
	
	//public void aumentarIntento()
}
 */

package com.hc.ro.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.SisUsuario;

@Stateless
public class ServicioSisUsuario extends ServicioGenerico<SisUsuario> {

	public ServicioSisUsuario() {
		super(SisUsuario.class, ServicioSisUsuario.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<SisUsuario> buscarTodos() {
		Query q = em.createQuery(
				"SELECT a FROM SisUsuario a ORDER BY a.nombreUsua ASC");
		
		return q.getResultList();
	}
	
	public ArrayList<SisUsuario> buscarTodos_codigoUsua_nombreUsua() {
		ArrayList<SisUsuario> usuarios = new ArrayList<SisUsuario>();
		TypedQuery<Object[]> q = em.createQuery(
				"SELECT a.codigoUsua, a.nombreUsua FROM SisUsuario a",
				Object[].class);
		for (Object[] result : q.getResultList()) {
			SisUsuario usuario = new SisUsuario((Integer) result[0],
					result[1].toString());
			usuarios.add(usuario);
		}
		return usuarios;
	}

	public SisUsuario buscarUsuarioPorNombre(String nombreUsuario) {
		Query q = em
				.createQuery("SELECT a FROM SisUsuario a WHERE a.nombreUsua LIKE :paramNombreUsuario");
		q.setParameter("paramNombreUsuario", nombreUsuario);
		return (SisUsuario) q.getSingleResult();
	}

	public String buscarNombrePorId(String usuario) {
		int idUsuario = Integer.parseInt(usuario);
		TypedQuery<String> q = em
				.createQuery(
						"SELECT a.nombreUsua FROM SisUsuario a WHERE a.codigoUsua LIKE :paramIdUsuario",
						String.class);
		q.setParameter("paramIdUsuario", idUsuario);
		return q.getSingleResult();
	}

	public boolean existeUsuarioPorNombre(String nombreUsuario) {
		Query q = em
				.createQuery("SELECT a FROM SisUsuario a WHERE a.nombreUsua LIKE :paramNombreUsuario");
		q.setParameter("paramNombreUsuario", nombreUsuario);

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + q.toString());
		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean existeUsuarioPorNombreEx(String nombreUsuario,
			int codigoUsuario) {
		Query q = em
				.createQuery("SELECT a FROM SisUsuario a WHERE a.nombreUsua LIKE :paramNombreUsuario AND a.codigoUsua NOT LIKE :paramCodigoUsuario)");
		q.setParameter("paramNombreUsuario", nombreUsuario).setParameter(
				"paramCodigoUsuario", codigoUsuario);

		if (q.getResultList().size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public SisUsuario buscarUsuarioPorNombreApellidoResponsable(
			String nombreResp, String apellidoResp) {
		String aux = "";
		aux = apellidoResp.trim();
		apellidoResp = aux;
		String apellidoNombre = apellidoResp.concat(" ").concat(nombreResp);
		System.out.println("responsable:" + apellidoNombre);
		Query q = null;
		q = em.createQuery("SELECT a FROM SisUsuario a WHERE a.nombreCompletoUsua LIKE :paramApellidoNombre");
		q.setParameter("paramApellidoNombre", apellidoNombre);

		return (SisUsuario) q.getSingleResult();

	}

	public Boolean existeUsuarioPorNombreApellidoResponsable(String nombreResp,
			String apellidoResp) {
		String aux = "";
		aux = apellidoResp.trim();
		apellidoResp = aux;
		String apellidoNombre = apellidoResp.concat(" ").concat(nombreResp);
		Query q = null;
		q = em.createQuery("SELECT a FROM SisUsuario a WHERE a.nombreCompletoUsua LIKE :paramApellidoNombre");
		q.setParameter("paramApellidoNombre", apellidoNombre);

		if (q.getResultList().size() == 0) {
			return false;
		} else {
			return true;
		}

	}

}
