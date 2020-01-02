package com.hc.ro.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.hc.ro.modelo.ParamConsecuenciaImpacto;
import com.hc.ro.modelo.ParamProbabilidadRiesgo;
import com.hc.ro.modelo.RoAgencia;
import com.hc.ro.modelo.RoDepartamento;
import com.hc.ro.modelo.RoDetalleEvento;
import com.hc.ro.modelo.RoEvento;
import com.hc.ro.modelo.RoFactorRiesgo;
import com.hc.ro.modelo.RoNegocio;
import com.hc.ro.modelo.RoProceso;
import com.hc.ro.modelo.RoTipoPerdida;
import com.hc.ro.modelo.SisUsuario;

@Stateless
public class ServicioRoDetalleEvento extends ServicioGenerico<RoDetalleEvento> {

	public ServicioRoDetalleEvento() {
		super(RoDetalleEvento.class, ServicioRoDetalleEvento.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleEvento> buscarTodosDesc() {
		Query q = em.createQuery("SELECT a FROM RoDetalleEvento a order by a.codigoDeve desc");

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleEvento> buscarEventoPorUsuario(SisUsuario usuario) {
		Query q = em
				.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.sisUsuario LIKE :paramUsuario");
		q.setParameter("paramUsuario", usuario);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleEvento> buscarEventoFiltro(Date fechaInicio,
			Date fechaFin, int idAgencia, short idDepartamento) {

		Query q = em
				.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.fechaRegisDeve BETWEEN :fechaInicio AND :fechaFin AND a.roDepartamento.codigoDept = :idDepartamento AND a.roAgencia.codigoAgia = :idAgencia");
		q.setParameter("fechaInicio", fechaInicio)
				.setParameter("fechaFin", fechaFin)
				.setParameter("idAgencia", idAgencia)
				.setParameter("idDepartamento", idDepartamento);
		return q.getResultList();
	}

	public List<RoDetalleEvento> buscarEventosFiltroRegistro(Date fechaInicio,
			Date fechaFin, int tipoFiltro, String tipoRegistro) {
		// tipoFiltro= fecha registro ó fecha de ocurrencia ó fecha de
		// descubrimiento
		List<RoDetalleEvento> deves = new ArrayList<RoDetalleEvento>();
		String tipoFecha = "fechaRegisDeve";
		String complementoQuery = "";
		tipoFecha = "fechaRegisDeve";
		if (tipoFiltro == 1) {
		} else {
			if (tipoFiltro == 2) {
				tipoFecha = "fechaOcurDeve";
			} else {
				tipoFecha = "fechaDescDeve";
			}
		}
		if (tipoRegistro.equals("Cualitativo")) {
			complementoQuery = ", a.paramConsecuenciaImpactoAntes.codigoPconi, a.paramConsecuenciaImpactoAntes.nombrePconi,"
					+ " a.paramConsecuenciaImpactoAntes.numeroPconi, a.paramConsecuenciaImpactoDespues.codigoPconi,"
					+ " a.paramConsecuenciaImpactoDespues.nombrePconi, a.paramConsecuenciaImpactoDespues.numeroPconi,"
					+ " a.paramProbabilidadRiesgoAntes.codigoPprr,a.paramProbabilidadRiesgoAntes.nombrePprr,"
					+ " a.paramProbabilidadRiesgoAntes.numeroPprr,a.paramProbabilidadRiesgoAntes.letraPprr,"
					+ " a.paramProbabilidadRiesgoDespues.codigoPprr,a.paramProbabilidadRiesgoDespues.nombrePprr,"
					+ " a.paramProbabilidadRiesgoDespues.numeroPprr,a.paramProbabilidadRiesgoDespues.letraPprr";
		} else {
			complementoQuery = "";
		}
		System.out.println("entroooo a consultar");
		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoDeve, a.costoRecupDeve, a.cuentaAfecDeve, a.fechaOcurDeve, "
								+ "a.fechaDescDeve, a.fechaRegisDeve, a.causaDeve, a.descripcionDeve, a.tipoCalifDeve, a.descripcionDetalladaDeve,"
								+ " a.montoRecupDeve, a.otrosGastosDeve, a.ptoCtrlProcDeve, a.realRecupDeve, a.tipoCostoDeve, "
								+ "a.perdidaResidualDeve, a.valorCambioDeve, a.valorDeve,a.numOcur, a.promedio, a.denominacion, a.riesgoResidual, a.clasificacion, a.bloqueo,a.riesgoInherente,"
								+ "a.roTipoPerdida.codigoTipe, "
								+ "a.roTipoPerdida.nombreTipe, "
								+ "a.sisUsuario.codigoUsua, a.sisUsuario.nombreUsua, a.sisUsuario.nombreCompletoUsua, a.roEvento.codigoEven,"
								+ " a.roEvento.nombreEven, a.roEvento.ancestroEven, a.roDepartamento.codigoDept, a.roDepartamento.nombreDept,"
								+ " a.roDepartamento.ancestroDept, a.roNegocio1.codigoNego, a.roNegocio1.nombreNego, a.roNegocio1.ancestroNego,"
								+ " a.roProceso.codigoPros, a.roProceso.nombrePros, a.roProceso.ancestroPros, a.roAgencia.codigoAgia, "
								+ "a.roAgencia.nombreAgia, a.roAgencia.ancestroAgia, a.roFactorRiesgo.codigoFaro, a.roFactorRiesgo.nombreFaro "
								+ complementoQuery
								+ " FROM RoDetalleEvento a WHERE a."
								+ tipoFecha
								+ " BETWEEN"
								+ " :fechaInicio AND :fechaFin AND a.tipoCalifDeve LIKE :tipoCalifDeve",
						Object[].class)
				.setParameter("fechaInicio", fechaInicio)
				.setParameter("fechaFin", fechaFin)
				.setParameter("tipoCalifDeve", tipoRegistro);
		if (tipoRegistro.equals("Cualitativo")) {
			for (Object[] result : q.getResultList()) {

				RoDetalleEvento deve = new RoDetalleEvento((Integer) result[0],
						(Float) result[1], (String) result[2],
						(Date) result[3], (Date) result[4], (Date) result[5],
						(String) result[6], (String) result[7],
						(String) result[8], (String) result[9],
						(Float) result[10], (Float) result[11],
						(String) result[12], (Float) result[13],
						(Integer) result[14], (Float) result[15],
						(Float) result[16], (Float) result[17],
						(Integer) result[18], (Float) result[19],
						(String) result[20], (Float) result[21],
						(String) result[22], (Integer) result[23],
						(Float) result[24],

						new RoTipoPerdida((Integer) result[25],
								(String) result[26]), new SisUsuario(
								(Integer) result[27], (String) result[28],
								(String) result[29]), new RoEvento(
								(Integer) result[30], (String) result[31],
								(String) result[32]), new RoDepartamento(
								(Integer) result[33], (String) result[34],
								(String) result[35]), new RoNegocio(
								(Integer) result[36], (String) result[37],
								(String) result[38]), new RoProceso(
								(Integer) result[39], (String) result[40],
								(String) result[41]), new RoAgencia(
								(Integer) result[42], (String) result[43],
								(String) result[44]), new RoFactorRiesgo(
								(Integer) result[45], (String) result[46]),
						new ParamConsecuenciaImpacto((Integer) result[47],
								(String) result[48], (Integer) result[49]),
						new ParamConsecuenciaImpacto((Integer) result[50],
								(String) result[51], (Integer) result[52]),
						new ParamProbabilidadRiesgo((Integer) result[53],
								(String) result[54], (Integer) result[55],
								(String) result[56]),
						new ParamProbabilidadRiesgo((Integer) result[57],
								(String) result[58], (Integer) result[59],
								(String) result[60]));
				deves.add(deve);
			}

			// System.out.println("terminoooo de consultar");
			// System.out.println("longitud: " + q.getResultList().size());
			return deves;
		} else {
			for (Object[] result : q.getResultList()) {

				RoDetalleEvento deve = new RoDetalleEvento((Integer) result[0],
						(Float) result[1], (String) result[2],
						(Date) result[3], (Date) result[4], (Date) result[5],
						(String) result[6], (String) result[7],
						(String) result[8], (String) result[9],
						(Float) result[10], (Float) result[11],
						(String) result[12], (Float) result[13],
						(Integer) result[14], (Float) result[15],
						(Float) result[16], (Float) result[17],
						(Integer) result[18], (Float) result[19],
						(String) result[20], (Float) result[21],
						(String) result[22], (Integer) result[23],
						(Float) result[24],

						new RoTipoPerdida((Integer) result[25],
								(String) result[26]), new SisUsuario(
								(Integer) result[27], (String) result[28],
								(String) result[29]), new RoEvento(
								(Integer) result[30], (String) result[31],
								(String) result[32]), new RoDepartamento(
								(Integer) result[33], (String) result[34],
								(String) result[35]), new RoNegocio(
								(Integer) result[36], (String) result[37],
								(String) result[38]), new RoProceso(
								(Integer) result[39], (String) result[40],
								(String) result[41]), new RoAgencia(
								(Integer) result[42], (String) result[43],
								(String) result[44]), new RoFactorRiesgo(
								(Integer) result[45], (String) result[46]),
						null, null, null, null);
				deves.add(deve);
			}

			// System.out.println("terminoooo de consultar");
			// System.out.println("longitud: " + q.getResultList().size());
			return deves;
		}

	}

	// 2018/06/13 esta consulta(buscarTodosAux) se estaba usando en la pantalla
	// detalle evento,
	// pero solo funciona para eventos cuantitativos ya que no consulta ni
	// devuelve los valores de probabilidad y de consecuencia. Opté por usar el
	// método "buscarTodosDesc"
	public List<RoDetalleEvento> buscarTodosAux() {
		List<RoDetalleEvento> deves = new ArrayList<RoDetalleEvento>();

		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoDeve, a.costoRecupDeve, a.cuentaAfecDeve, a.fechaOcurDeve, "
								+ "a.fechaDescDeve, a.fechaRegisDeve, a.causaDeve, a.descripcionDeve, a.tipoCalifDeve, a.descripcionDetalladaDeve,"
								+ " a.montoRecupDeve, a.otrosGastosDeve, a.ptoCtrlProcDeve, a.realRecupDeve, a.tipoCostoDeve, "
								+ "a.perdidaResidualDeve, a.valorCambioDeve, a.valorDeve, a.numOcur,a.promedio, a.denominacion, a.riesgoResidual, a.clasificacion, a.bloqueo, a.riesgoInherente,"
								+ "a.roTipoPerdida.codigoTipe, "
								+ "a.roTipoPerdida.nombreTipe, "
								+ "a.sisUsuario.codigoUsua, a.sisUsuario.nombreUsua, a.sisUsuario.nombreCompletoUsua, a.roEvento.codigoEven,"
								+ " a.roEvento.nombreEven, a.roEvento.ancestroEven, a.roDepartamento.codigoDept, a.roDepartamento.nombreDept,"
								+ " a.roDepartamento.ancestroDept, a.roNegocio1.codigoNego, a.roNegocio1.nombreNego, a.roNegocio1.ancestroNego,"
								+ " a.roProceso.codigoPros, a.roProceso.nombrePros, a.roProceso.ancestroPros, a.roAgencia.codigoAgia, "
								+ "a.roAgencia.nombreAgia, a.roAgencia.ancestroAgia, a.roFactorRiesgo.codigoFaro, a.roFactorRiesgo.nombreFaro "
								+ " FROM RoDetalleEvento a order by a.codigoDeve desc",
						Object[].class);

		for (Object[] result : q.getResultList()) {
			RoDetalleEvento deve = new RoDetalleEvento(
					(Integer) result[0],
					(Float) result[1],
					(String) result[2],
					(Date) result[3],
					(Date) result[4],
					(Date) result[5],
					(String) result[6],
					(String) result[7],
					(String) result[8],
					(String) result[9],
					(Float) result[10],
					(Float) result[11],
					(String) result[12],
					(Float) result[13],
					(Integer) result[14],
					(Float) result[15],
					(Float) result[16],
					(Float) result[17],
					(Integer) result[18],
					(Float) result[19],
					(String) result[20],
					(Float) result[21],
					(String) result[22],
					(Integer) result[23],
					(Float) result[24],

					new RoTipoPerdida((Integer) result[25], (String) result[26]),
					new SisUsuario((Integer) result[27], (String) result[28],
							(String) result[29]), new RoEvento(
							(Integer) result[30], (String) result[31],
							(String) result[32]), new RoDepartamento(
							(Integer) result[33], (String) result[34],
							(String) result[35]), new RoNegocio(
							(Integer) result[36], (String) result[37],
							(String) result[38]), new RoProceso(
							(Integer) result[39], (String) result[40],
							(String) result[41]), new RoAgencia(
							(Integer) result[42], (String) result[43],
							(String) result[44]), new RoFactorRiesgo(
							(Integer) result[45], (String) result[46]), null,
					null, null, null);
			deves.add(deve);
		}
		// System.out.println("terminoooo de consultar");
		// System.out.println("longitud: " + q.getResultList().size());
		return deves;
	}

	public List<RoDetalleEvento> buscarEventoPorUsuarioAux(SisUsuario usuario) {
		List<RoDetalleEvento> deves = new ArrayList<RoDetalleEvento>();

		TypedQuery<Object[]> q = em
				.createQuery(
						"SELECT a.codigoDeve, a.costoRecupDeve, a.cuentaAfecDeve, a.fechaOcurDeve, "
								+ "a.fechaDescDeve, a.fechaRegisDeve, a.causaDeve, a.descripcionDeve, a.tipoCalifDeve, a.descripcionDetalladaDeve,"
								+ " a.montoRecupDeve, a.otrosGastosDeve, a.ptoCtrlProcDeve, a.realRecupDeve, a.tipoCostoDeve, "
								+ "a.perdidaResidualDeve, a.valorCambioDeve, a.valorDeve, a.numOcur,a.promedio, a.denominacion, a.riesgoResidual, a.clasificacion, a.bloqueo,a.riesgoInherente,"
								+ "a.roTipoPerdida.codigoTipe, "
								+ "a.roTipoPerdida.nombreTipe, "
								+ "a.sisUsuario.codigoUsua, a.sisUsuario.nombreUsua, a.sisUsuario.nombreCompletoUsua, a.roEvento.codigoEven,"
								+ " a.roEvento.nombreEven, a.roEvento.ancestroEven, a.roDepartamento.codigoDept, a.roDepartamento.nombreDept,"
								+ " a.roDepartamento.ancestroDept, a.roNegocio1.codigoNego, a.roNegocio1.nombreNego, a.roNegocio1.ancestroNego,"
								+ " a.roProceso.codigoPros, a.roProceso.nombrePros, a.roProceso.ancestroPros, a.roAgencia.codigoAgia, "
								+ "a.roAgencia.nombreAgia, a.roAgencia.ancestroAgia, a.roFactorRiesgo.codigoFaro, a.roFactorRiesgo.nombreFaro"
								+ " FROM RoDetalleEvento a WHERE a.sisUsuario.codigoUsua LIKE :paramUsuario",
						Object[].class);

		q.setParameter("paramUsuario", usuario.getCodigoUsua());
		for (Object[] result : q.getResultList()) {

			RoDetalleEvento deve = new RoDetalleEvento(
					(Integer) result[0],
					(Float) result[1],
					(String) result[2],
					(Date) result[3],
					(Date) result[4],
					(Date) result[5],
					(String) result[6],
					(String) result[7],
					(String) result[8],
					(String) result[9],
					(Float) result[10],
					(Float) result[11],
					(String) result[12],
					(Float) result[13],
					(Integer) result[14],
					(Float) result[15],
					(Float) result[16],
					(Float) result[17],
					(Integer) result[18],
					(Float) result[19],
					(String) result[20],
					(Float) result[21],
					(String) result[22],
					(Integer) result[23],
					(Float) result[24],

					new RoTipoPerdida((Integer) result[25], (String) result[26]),
					new SisUsuario((Integer) result[27], (String) result[28],
							(String) result[29]), new RoEvento(
							(Integer) result[30], (String) result[31],
							(String) result[32]), new RoDepartamento(
							(Integer) result[33], (String) result[34],
							(String) result[35]), new RoNegocio(
							(Integer) result[36], (String) result[37],
							(String) result[38]), new RoProceso(
							(Integer) result[39], (String) result[40],
							(String) result[41]), new RoAgencia(
							(Integer) result[42], (String) result[43],
							(String) result[44]), new RoFactorRiesgo(
							(Integer) result[45], (String) result[46]), null,
					null, null, null);
			deves.add(deve);
		}
		// System.out.println("terminoooo de consultar");
		// System.out.println("longitud: " + q.getResultList().size());
		return deves;

	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleEvento> buscarEventosTodosFiltro(Date fechaInicio,
			Date fechaFin, int tipoFiltro) {

		if (tipoFiltro == 1) {

			Query q = em
					.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.fechaRegisDeve BETWEEN :fechaInicio AND :fechaFin");
			q.setParameter("fechaInicio", fechaInicio).setParameter("fechaFin",
					fechaFin);
			return q.getResultList();

		} else {
			if (tipoFiltro == 2) {
				Query q = em
						.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.fechaOcurDeve BETWEEN :fechaInicio AND :fechaFin");
				q.setParameter("fechaInicio", fechaInicio).setParameter(
						"fechaFin", fechaFin);
				return q.getResultList();
			} else {
				Query q = em
						.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.fechaDescDeve BETWEEN :fechaInicio AND :fechaFin");
				q.setParameter("fechaInicio", fechaInicio).setParameter(
						"fechaFin", fechaFin);
				return q.getResultList();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleEvento> buscarEventosCuantitativosFiltro2(
			Date fechaInicio, Date fechaFin, int tipoFiltro) {
		if (tipoFiltro == 1) {
			Query q = em
					.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.fechaRegisDeve BETWEEN :fechaInicio AND :fechaFin AND a.tipoCalifDeve LIKE :tipoCalifDeve");
			q.setParameter("fechaInicio", fechaInicio)
					.setParameter("fechaFin", fechaFin)
					.setParameter("tipoCalifDeve", "Cuantitativo");
			return q.getResultList();
		} else {
			if (tipoFiltro == 2) {
				Query q = em
						.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.fechaOcurDeve BETWEEN :fechaInicio AND :fechaFin AND a.tipoCalifDeve LIKE :tipoCalifDeve");
				q.setParameter("fechaInicio", fechaInicio)
						.setParameter("fechaFin", fechaFin)
						.setParameter("tipoCalifDeve", "Cuantitativo");
				return q.getResultList();
			} else {
				Query q = em
						.createQuery("SELECT a FROM RoDetalleEvento a WHERE a.fechaDescDeve BETWEEN :fechaInicio AND :fechaFin AND a.tipoCalifDeve LIKE :tipoCalifDeve");
				q.setParameter("fechaInicio", fechaInicio)
						.setParameter("fechaFin", fechaFin)
						.setParameter("tipoCalifDeve", "Cuantitativo");
				return q.getResultList();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<RoDetalleEvento> buscarEventosPlanDeAccion(int codigoUsua) {
		Query q = this.em
				.createQuery("SELECT a FROM RoDetalleEvento a WHERE (SELECT COUNT(b) FROM RoAccion b WHERE b.roDetalleEvento.codigoDeve = a.codigoDeve AND b.roResponsable.sisUsuario.codigoUsua = :idUsua) >=0 AND a.sisUsuario.codigoUsua LIKE :idUsua AND a.roTipoPerdida.codigoTipe!=null AND a.roNegocio1.codigoNego!=null ORDER BY a.codigoDeve desc");
		
		q.setParameter("idUsua", Integer.valueOf(codigoUsua));

		return q.getResultList();
	}

	// @SuppressWarnings("unchecked")
	// public List<RoDetalleEvento> buscarEventosPlanDeAccion(SisUsuario
	// usuario) {
	// Query q = this.em
	// .createQuery("SELECT a FROM RoDetalleEvento a WHERE (SELECT COUNT(b) FROM RoAccion b WHERE b.roDetalleEvento.codigoDeve = a.codigoDeve AND b.roResponsable.sisUsuario.codigoUsua = :idUsua) >=0 AND a.sisUsuario.codigoUsua LIKE :idUsua");
	// q.setParameter("idUsua", usuario.getCodigoUsua());
	// return q.getResultList();
	// }

	public void actualizarPromedio(Float campo, int attrb) {
		Query q = em
				.createQuery("UPDATE RoDetalleEvento a SET a.promedio = :paramAttrb WHERE a.codigoDeve LIKE :param");
		q.setParameter("paramAttrb", campo);
		q.setParameter("param", attrb);
		q.executeUpdate();
	}

	public void actualizarDenominacion(String valor, int attrb) {
		Query q = em
				.createQuery("UPDATE RoDetalleEvento a SET a.denominacion = :paramAttrb WHERE a.codigoDeve LIKE :param");
		q.setParameter("paramAttrb", valor);
		q.setParameter("param", attrb);
		q.executeUpdate();
	}

	public void actualizarRiesgoResidual(float campo, int attrb) {
		Query q = em
				.createQuery("UPDATE RoDetalleEvento a SET a.riesgoResidual = :paramAttrb WHERE a.codigoDeve LIKE :param");
		q.setParameter("paramAttrb", campo);
		q.setParameter("param", attrb);
		q.executeUpdate();
	}

	public void actualizarClasificacion(String campo, int attrb) {
		Query q = em
				.createQuery("UPDATE RoDetalleEvento a SET a.clasificacion = :paramAttrb WHERE a.codigoDeve LIKE :param");
		q.setParameter("paramAttrb", campo);
		q.setParameter("param", attrb);
		q.executeUpdate();
	}

	public void actualizarRiesgoInherente(float campo, int attrb) {
		Query q = em
				.createQuery("UPDATE RoDetalleEvento a SET a.riesgoInherente = :paramAttrb WHERE a.codigoDeve LIKE :param");
		q.setParameter("paramAttrb", campo);
		q.setParameter("param", attrb);
		q.executeUpdate();
	}

}
